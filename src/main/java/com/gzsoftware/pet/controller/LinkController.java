package com.gzsoftware.pet.controller;

import java.io.IOException;
import java.util.Date;
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
import com.gzsoftware.pet.entity.po.Link;
import com.gzsoftware.pet.entity.po.Link;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.LinkService;
import com.gzsoftware.pet.utils.CommonUtil;

@Controller
@RequestMapping("link")
public class LinkController  extends BaseController{
	
private static Log log = LogFactory.getLog(LinkController.class);
	
	@Resource
	private LinkService linkService;
	
	/**
	 * 获取所有links
	 * @param dtRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getLinkList", method = RequestMethod.POST)
	public  Result getLinkList(@RequestBody DataTablesRequest dtRequest)  {
		List<Link> linkList = linkService.getLinkList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,linkList,linkService.countAll(dtRequest),linkService.countAll(dtRequest));
	}
	
	/**
	 * 根据ID获取link
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getLink", method = RequestMethod.GET)
	public Result getLink(@RequestParam("id") int id) {
		Link link = linkService.getLink(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,link);
	}
	
	/**
	 * 新增 Link
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addLink", method = RequestMethod.POST)
	public Result addLink(@RequestBody Link link,HttpServletRequest request) {
		 Admin currentAdmin = super.getCurrentAdmin(request.getSession());
		 if(currentAdmin==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		 link.setLastUpdateTime(new Date());
		 link.setLastUpdateAdminId(currentAdmin.getId());
		 link.setNameEn(CommonUtil.isStrEmpty(link.getNameEn())?link.getNameCn():link.getNameEn());

		int effCnt = linkService.addLink(link);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",link);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",link);
		}
		
	
	}

	
	/**
	 * 更新 Link
	 * @throws IOException 
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateLink", method = RequestMethod.POST)
	public Result updateLink(@RequestBody Link link,HttpServletRequest request) {
		 Admin currentAdmin = super.getCurrentAdmin(request.getSession());
		 if(currentAdmin==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		 link.setLastUpdateTime(new Date());
		 link.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =linkService.updateLink(link);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",link);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",link);
		}
	}


	
	/**
	 * 删除 Link
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/deleteLink", method = RequestMethod.GET)
	public Result deleteLink(@RequestParam("id") int id) {
		int effCnt = linkService.deleteLink(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"删除成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"删除失败！！");
		}
	}

}
