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
import com.gzsoftware.pet.entity.po.News;
import com.gzsoftware.pet.entity.po.UploadFile;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.NewsService;
import com.gzsoftware.pet.service.UploadFileService;
import com.gzsoftware.pet.utils.CommonUtil;

@Controller
@RequestMapping("news")
public class NewsController extends BaseController{

	private static Log log = LogFactory.getLog(NewsController.class);
	
	@Resource
	private NewsService newsService;
	
	@Resource
	private UploadFileService uploadFileService;
	
	
	/**
	 * 新增 News
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addNews", method = RequestMethod.POST)
	public Result addNews(@RequestBody News news,HttpServletRequest request) {
		 Admin currentAdmin = super.getCurrentAdmin(request.getSession());
		 if(currentAdmin==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		 news.setLastUpdateTime(new Date());
		 news.setLastUpdateAdminId(currentAdmin.getId());
		 news.setTitleEn(CommonUtil.isStrEmpty(news.getTitleEn())?news.getTitleCn():news.getTitleEn());
		 news.setDescriptionEn(CommonUtil.isStrEmpty(news.getDescriptionEn())?news.getDescriptionCn():news.getDescriptionEn());
		 news.setPriority(news.getPriority()==null?1:news.getPriority());
		int effCnt = newsService.addNews(news);
		if(effCnt > 0){
			uploadFileService.updateFileToUsed(news.getPicFileId());
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",news);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",news);
		}
		
	
	}

	/**
	 * 获取所有Newss
	 * @param dtRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getNewsList", method = RequestMethod.POST)
	public  Result getNewsList(@RequestBody DataTablesRequest dtRequest)  {
		List<News> newsList = newsService.getNewsList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,newsList,newsService.countAll(dtRequest),newsService.countAll(dtRequest));
	}
	
	
	/**
	 * 根据ID获取News
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getNews", method = RequestMethod.GET)
	public Result getNews(@RequestParam("id") int id) {
		News news = newsService.getNews(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,news);
	}

	
	/**
	 * 更新 News
	 * @throws IOException 
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateNews", method = RequestMethod.POST)
	public Result updateAdmin(@RequestBody News news,HttpServletRequest request) {
		 Admin currentAdmin = super.getCurrentAdmin(request.getSession());
		 if(currentAdmin==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		 news.setLastUpdateTime(new Date());
		 news.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =newsService.updateNews(news);
		if(effCnt > 0){
			uploadFileService.updateFileToUsed(news.getPicFileId());
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",news);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",news);
		}
	}


	
	/**
	 * 删除 News
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/deleteNews", method = RequestMethod.GET)
	public Result deleteNews(@RequestParam("id") int id) {
		News news = newsService.getNews(id);
		if(news.getPicFileId()!=null){
			uploadFileService.updateFileToUnUsed(news.getPicFileId());
		}
		int effCnt =newsService.deleteNews(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"删除成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"删除失败！！");
		}
	}
	
	
	
	/**
	 *  News访问次数
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/addNewsVisitCnt", method = RequestMethod.GET)
	public Result addNewsVisitCnt(@RequestParam("id") int id) {
		int effCnt = newsService.addNewsVisitCnt(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"访问次数增加成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"访问次数增加失败！！");
		}
	}
	
	/**
	 *  News点赞次数
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/addNewsUpCnt", method = RequestMethod.GET)
	public Result addNewsUpCnt(@RequestParam("id") int id) {
		int effCnt = newsService.addNewsUpCnt(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"点赞次数增加成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"点赞次数增加失败！！");
		}
	}
}
