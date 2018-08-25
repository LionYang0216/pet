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
import com.gzsoftware.pet.entity.po.NewsType;
import com.gzsoftware.pet.entity.po.UploadFile;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.NewsTypeService;
import com.gzsoftware.pet.service.UploadFileService;
import com.gzsoftware.pet.utils.CommonUtil;

@Controller
@RequestMapping("newsType")
public class NewsTypeController extends BaseController{

	private static Log log = LogFactory.getLog(NewsTypeController.class);
	
	@Resource
	private NewsTypeService newsTypeService;
	
	
	/**
	 * 新增 NewsType
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addNewsType", method = RequestMethod.POST)
	public Result addNewsType(@RequestBody NewsType newsType,HttpServletRequest request) {
		 Admin currentAdmin = super.getCurrentAdmin(request.getSession());
		 if(currentAdmin==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		 newsType.setLastUpdateTime(new Date());
		 newsType.setLastUpdateAdminId(currentAdmin.getId());
		 newsType.setNameEn(CommonUtil.isStrEmpty(newsType.getNameEn())?newsType.getNameCn():newsType.getNameEn());
		 newsType.setPriority(newsType.getPriority()==null?1:newsType.getPriority());
		int effCnt = newsTypeService.addNewsType(newsType);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",newsType);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",newsType);
		}
		
	
	}

	/**
	 * 获取所有NewsTypes
	 * @param dtRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getNewsTypeList", method = RequestMethod.POST)
	public  Result getNewsTypeList(@RequestBody DataTablesRequest dtRequest)  {
		List<NewsType> newsTypeList = newsTypeService.getNewsTypeList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,newsTypeList,newsTypeService.countAll(dtRequest),newsTypeService.countAll(dtRequest));
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getNewsTypeListForSelect", method = RequestMethod.GET)
	public  Result getNewsTypeListForSelect()  {
		List<NewsType> newsTypeList = newsTypeService.getNewsTypeListForSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,newsTypeList);
	}
	
	
	/**
	 * 根据ID获取NewsType
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getNewsType", method = RequestMethod.GET)
	public Result getNewsType(@RequestParam("id") int id) {
		NewsType newsType = newsTypeService.getNewsType(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,newsType);
	}

	
	/**
	 * 更新 NewsType
	 * @throws IOException 
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateNewsType", method = RequestMethod.POST)
	public Result updateAdmin(@RequestBody NewsType newsType,HttpServletRequest request) {
		 Admin currentAdmin = super.getCurrentAdmin(request.getSession());
		 if(currentAdmin==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		 newsType.setLastUpdateTime(new Date());
		 newsType.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = newsTypeService.updateNewsType(newsType);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",newsType);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",newsType);
		}
	}


	
	/**
	 * 删除 NewsType
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/deleteNewsType", method = RequestMethod.GET)
	public Result deleteNewsType(@RequestParam("id") int id) {
		int effCnt = newsTypeService.deleteNewsType(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"删除成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"删除失败！！");
		}
	}
	
	
	
}
