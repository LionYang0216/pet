package com.gzsoftware.pet.controller;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzsoftware.pet.entity.po.Admin;
import com.gzsoftware.pet.entity.po.Content;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.ContentService;
import com.gzsoftware.pet.service.UploadFileService;
import com.gzsoftware.pet.utils.CommonUtil;

@Controller
@RequestMapping("content")
public class ContentController extends BaseController{

	private static Log log = LogFactory.getLog(ContentController.class);
	
	
	@Resource
	private ContentService contentService;
	
	@Resource
	private UploadFileService uploadFileService;
	
	

	/**
	 * 获取所有contents
	 * @param dtRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getContentList", method = RequestMethod.POST)
	public  Result getcontentList(@RequestBody DataTablesRequest dtRequest)  {
		List<Content> contentList = contentService.getContentList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,contentList,contentService.countAll(dtRequest),contentService.countAll(dtRequest));
	}
	
	
	/**
	 * 根据ID获取content
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getContentById", method = RequestMethod.GET)
	public Result getContentById(@RequestParam("id") int id) {
		Content content = contentService.getContent(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,content);
	}

	
	/**
	 * 更新 content
	 * @throws IOException 
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateContent", method = RequestMethod.POST)
	public Result updateAdmin(@RequestBody Content content,HttpServletRequest request) {
		 Admin currentAdmin = super.getCurrentAdmin(request.getSession());
		 if(currentAdmin==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		 content.setLastUpdateTime(new Date());
		 content.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =contentService.updateContent(content);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",content);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",content);
		}
	}
	
	/**
	 * 通过code查询concent,供前台调用
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getContentByCode", method = RequestMethod.GET)
	public Result getContentByCode(@RequestParam("code") String code) {
		Content content = contentService.getContentByCode(code);
		return new Result(Result.RESULT_FLAG_SUCCESS,content);
	}
	
}
