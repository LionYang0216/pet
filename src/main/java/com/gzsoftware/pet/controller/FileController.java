package com.gzsoftware.pet.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.gzsoftware.pet.entity.po.UploadFile;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.UploadFileService;
import com.gzsoftware.pet.utils.ImgCompressUtil;

@Controller
@RequestMapping("file")
public class FileController extends BaseController {

	@Resource
	private UploadFileService uploadFileService;
	@Resource
	private CommonsMultipartResolver Resolver;
	
	private static Log log = LogFactory.getLog(FileController.class);
	
	public static String SUB_UPLOAD_FOLDER = "/upload";
	
	public int maxUploadSize = 4048576; // 4M the max upload limit
	
	/**
	 * 处理所有单文件上传
	 * @param postFile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ExceptionHandler(Exception.class)         
	public Result upload(@RequestParam("file") MultipartFile postFile) {
		long fileSize = postFile.getSize();
		if(fileSize > maxUploadSize){
			return new Result(Result.RESULT_FLAG_FAIL, "上传失败!上传文件不能大于4M");
		}
		String fileName = postFile.getOriginalFilename();
		String fileType = "." + fileName.substring(fileName.lastIndexOf(".") + 1);
		File dst = null;
		UploadFile uploadFile = new UploadFile();
		try {
			// 保存物理文件
			String root = getWebLocalRoot(); // 获取系统根路径
			File uploadDir = new File(root, SUB_UPLOAD_FOLDER); // 创建一个指向tomcat/webapps/upload目录的对象
			if (!uploadDir.exists()) {
				uploadDir.mkdir(); // 如果不存在则创建upload目录
			}
			String filePath = UUID.randomUUID().toString() + fileType;
			dst = new File(uploadDir, filePath); // 创建一个指向upload目录下的文件对象，文件名随机生成
			postFile.transferTo(dst); // 创建文件并将上传文件复制过去
			if(fileSize>100000){//大于100k 压缩
				//压缩图片
				ImgCompressUtil.compressPic(dst.getPath(), dst.getPath());
			}
			// 保存到数据库
			uploadFile.setFileName(fileName);
			uploadFile.setFilePath(filePath);
			uploadFile.setIsUsed(false);
			uploadFileService.addUploadFile(uploadFile);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("上传失败:" + e.getMessage());
			return new Result(Result.RESULT_FLAG_FAIL, "上传失败:" + e.getMessage(), uploadFile);
		}
		return new Result(Result.RESULT_FLAG_SUCCESS, "上传成功", uploadFile);

	}
	

	/**ckEditor 图片上传
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/ckEditorUpload", method = RequestMethod.POST)
	public String upload(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("Utf-8");
		PrintWriter out = response.getWriter();
		// CKEditor提交的很重要的一个参数
		String callback = request.getParameter("CKEditorFuncNum");
		UploadFile uploadFile = new UploadFile();
		Iterator<String> iter = request.getFileNames();
		while (iter.hasNext()) {
			MultipartFile file = request.getFile(iter.next());
			String type = null;// 文件类型
			String fileName = file.getOriginalFilename();// 文件原名称
			// 判断文件类型
			type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()): null;
			String fileType = "." + type;
			long fileSize = file.getSize();
			if (fileSize > maxUploadSize) {
				out.println("<script type=\"text/javascript\">");
				out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件太大');");
				out.println("</script>");
				return null;
			}
			if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase())|| "JPG".equals(type.toUpperCase())) {
				String uploadPath = getWebLocalRoot(); // 获取系统根路径
				File uploadDir = new File(uploadPath, SUB_UPLOAD_FOLDER); // 创建一个指向tomcat/webapps/upload目录的对象
				if (!uploadDir.exists()) {
					uploadDir.mkdir(); // 如果不存在则创建upload目录
				}
				// 自定义的文件名称
				String trueFileName = UUID.randomUUID().toString() + fileType;
				File toFile = new File(uploadDir, trueFileName);
				file.transferTo(toFile);
				
				if(fileSize>100000){//大于100k 压缩
					//压缩图片
					ImgCompressUtil.compressPic(toFile.getPath(), toFile.getPath());
				}
				
				uploadFile.setFileName(fileName);
				uploadFile.setFilePath(trueFileName);
				uploadFile.setIsUsed(true);
				uploadFileService.addUploadFile(uploadFile);
				// 返回“图像”选项卡并显示图片
				out.println("<script type=\"text/javascript\">");
				out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'../upload/" + trueFileName + "','')"); // 相对路径用于显示图片
				out.println("</script>");
			} else {
				out.println("<script type=\"text/javascript\">");
				out.println("window.parent.CKEDITOR.tools.callFunction(" + callback
						+ ",'','文件格式不正确（必须为.jpg/.gif/.png文件）');");
				out.println("</script>");
				return null;
			}
		}
		return null;
	}

	/**ckEditor 批量图片上传
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/ckEditorUploadList", method = RequestMethod.POST)
	public String uploadUploadList(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("Utf-8");
		// CKEditor提交的很重要的一个参数
		String callback = request.getParameter("CKEditorFuncNum");
		UploadFile uploadFile = new UploadFile();
		Iterator<String> iter = request.getFileNames();
		while (iter.hasNext()) {
			MultipartFile file = request.getFile(iter.next());
			String type = null;// 文件类型
			String fileName = file.getOriginalFilename();// 文件原名称
			// 判断文件类型
			type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()): null;
			String fileType = "." + type;
			long fileSize = file.getSize();
			if (fileSize > maxUploadSize) {
				return "false,上传失败!上传文件不能大于4M";
			}
			if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase())|| "JPG".equals(type.toUpperCase())) {
				String uploadPath = getWebLocalRoot(); // 获取系统根路径
				File uploadDir = new File(uploadPath, SUB_UPLOAD_FOLDER); // 创建一个指向tomcat/webapps/upload目录的对象
				if (!uploadDir.exists()) {
					uploadDir.mkdir(); // 如果不存在则创建upload目录
				}
				// 自定义的文件名称
				String trueFileName = UUID.randomUUID().toString() + fileType;
				File toFile = new File(uploadDir, trueFileName);
				file.transferTo(toFile);
				
				if(fileSize>100000){//大于100k 压缩
					//压缩图片
					ImgCompressUtil.compressPic(toFile.getPath(), toFile.getPath());
				}
				
				uploadFile.setFileName(fileName);
				uploadFile.setFilePath(trueFileName);
				uploadFile.setIsUsed(true);
				uploadFileService.addUploadFile(uploadFile);
				return "true,../upload/" +trueFileName;
			} else {
				return "false,文件格式不正确（必须为.jpg/.gif/.png文件）";
			}
		}
		return null;
	}
	
	
	
	/**
	 * 删除没有使用的图片
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteNotUsedFile", method = RequestMethod.POST)
	public Result deleteNotUsedFile(){
		List<UploadFile> fileList = this.uploadFileService.getUploadFileAllNotUse();
		try {
			if(!fileList.isEmpty()){
				for(UploadFile uploadFile : fileList){
					 String uploadPath = getWebLocalRoot(); // 获取系统根路径
					 File uploadDir = new File(uploadPath, SUB_UPLOAD_FOLDER); // 创建一个指向tomcat/webapps/upload目录的对象
					 String fileName = uploadFile.getFilePath();
					 File file = new File(uploadDir, fileName);
					 if (file.exists() && file.isFile()){
						 if(file.delete()){
						    this.uploadFileService.deleteUploadFile(uploadFile.getId());
						 }
					 }
				
				}
				return new Result(Result.RESULT_FLAG_SUCCESS, "删除多余图片成功！");
			}else{
				return new Result(Result.RESULT_FLAG_SUCCESS, "没有需要清除的图片！");
			}
		} catch (Exception e) {
			return new Result(Result.RESULT_FLAG_SUCCESS, "删除失败！");
		}
	}
	
	/**
	 * 返回没有被使用的图片数量
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getNotUsedFileCnt", method = RequestMethod.POST)
	public Result getNotUsedFileCnt(){
		List<UploadFile> fileList = this.uploadFileService.getUploadFileAllNotUse();
		return new Result(Result.RESULT_FLAG_SUCCESS,fileList.size());
	}
	
}
