package com.gzsoftware.pet.controller;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzsoftware.pet.entity.po.Admin;
import com.gzsoftware.pet.entity.po.ProdTypePic;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.ProdTypePicService;
@Controller
@RequestMapping("/prodTypePic")
public class ProdTypePicController extends BaseController {
	private static Log log = LogFactory.getLog(ProdTypePicController.class);
	
	@Resource
	private ProdTypePicService prodTypePicService;
	/**
	 * 获取所有prodPhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypePicList", method = RequestMethod.POST)
	public  Result getProdTypePicList(@RequestBody DataTablesRequest dtRequest)  {
		List<ProdTypePic> phyList = prodTypePicService.getProdTypePicList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList,prodTypePicService.countAll(dtRequest),prodTypePicService.countAll(dtRequest));
	}
	
	/**
	 * 获取所有prodPic
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypePicSelect", method = RequestMethod.GET)
	public  Result getProdTypePicSelect()  {
		List<ProdTypePic> phyList = prodTypePicService.getProdTypePicSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList);
	}
	/**
	 * 根据ID获取prodTypePic
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypePic", method = RequestMethod.GET)
	public Result getProdTypePic(@RequestParam("id") int id) {
		ProdTypePic prodTypePic = prodTypePicService.getProdTypePic(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,prodTypePic);
	}

	
	/**
	 * 新增 ProdTypePic
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addProdTypePic", method = RequestMethod.POST)
	public Result addProdTypePic(@RequestBody ProdTypePic prodTypePic,HttpSession session) {
		prodTypePic.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		//默认是启用
		if(prodTypePic.getIsEnable()==null){
			prodTypePic.setIsEnable("1");
		}
		prodTypePic.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = prodTypePicService.addProdTypePic(prodTypePic);
		if(effCnt > 0){
			log.debug("添加物理类目成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",prodTypePic);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",prodTypePic);
		}
		
	
	}
	
	
	/**
	 * 更新 ProdTypePic
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateProdTypePic", method = RequestMethod.POST)
	public Result updateProdTypePic(@RequestBody ProdTypePic prodTypePic,HttpSession session) {
		prodTypePic.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		prodTypePic.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =prodTypePicService.updateProdTypePic(prodTypePic);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",prodTypePic);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",prodTypePic);
		}
	}
	
	
	/**
	 * 删除 ProdTypePic
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteProdTypePic", method = RequestMethod.GET)
	public Result deleteProdTypePic(@RequestParam("id") int id) {
		int effCnt = prodTypePicService.deleteProdTypePic(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
}
