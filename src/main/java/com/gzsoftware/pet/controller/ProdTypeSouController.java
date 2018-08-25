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
import com.gzsoftware.pet.entity.po.ProdTypeSou;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.ProdTypeSouService;
@Controller
@RequestMapping("/prodTypeSou")
public class ProdTypeSouController extends BaseController {
	private static Log log = LogFactory.getLog(ProdTypeSouController.class);
	
	@Resource
	private ProdTypeSouService prodTypeSouService;
	/**
	 * 获取所有prodPhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeSouList", method = RequestMethod.POST)
	public  Result getProdTypeSouList(@RequestBody DataTablesRequest dtRequest)  {
		List<ProdTypeSou> phyList = prodTypeSouService.getProdTypeSouList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList,prodTypeSouService.countAll(dtRequest),prodTypeSouService.countAll(dtRequest));
	}
	
	/**
	 * 获取所有prodSou
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeSouSelect", method = RequestMethod.GET)
	public  Result getProdTypeSouSelect()  {
		List<ProdTypeSou> phyList = prodTypeSouService.getProdTypeSouSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList);
	}
	/**
	 * 根据ID获取prodTypeSou
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeSou", method = RequestMethod.GET)
	public Result getProdTypeSou(@RequestParam("id") int id) {
		ProdTypeSou prodTypeSou = prodTypeSouService.getProdTypeSou(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,prodTypeSou);
	}

	
	/**
	 * 新增 ProdTypeSou
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addProdTypeSou", method = RequestMethod.POST)
	public Result addProdTypeSou(@RequestBody ProdTypeSou prodTypeSou,HttpSession session) {
		prodTypeSou.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		//默认是启用
		if(prodTypeSou.getIsEnable()==null){
			prodTypeSou.setIsEnable("1");
		}
		prodTypeSou.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = prodTypeSouService.addProdTypeSou(prodTypeSou);
		if(effCnt > 0){
			log.debug("添加物理类目成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",prodTypeSou);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",prodTypeSou);
		}
		
	
	}
	
	
	/**
	 * 更新 ProdTypeSou
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateProdTypeSou", method = RequestMethod.POST)
	public Result updateProdTypeSou(@RequestBody ProdTypeSou prodTypeSou,HttpSession session) {
		prodTypeSou.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		prodTypeSou.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =prodTypeSouService.updateProdTypeSou(prodTypeSou);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",prodTypeSou);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",prodTypeSou);
		}
	}
	
	
	/**
	 * 删除 ProdTypeSou
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteProdTypeSou", method = RequestMethod.GET)
	public Result deleteProdTypeSou(@RequestParam("id") int id) {
		int effCnt = prodTypeSouService.deleteProdTypeSou(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
}
