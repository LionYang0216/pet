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
import com.gzsoftware.pet.entity.po.ProdTypeNek;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.ProdTypeNekService;
@Controller
@RequestMapping("/prodTypeNek")
public class ProdTypeNekController extends BaseController {
	private static Log log = LogFactory.getLog(ProdTypeNekController.class);
	
	@Resource
	private ProdTypeNekService prodTypeNekService;
	/**
	 * 获取所有prodPhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeNekList", method = RequestMethod.POST)
	public  Result getProdTypeNekList(@RequestBody DataTablesRequest dtRequest)  {
		List<ProdTypeNek> phyList = prodTypeNekService.getProdTypeNekList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList,prodTypeNekService.countAll(dtRequest),prodTypeNekService.countAll(dtRequest));
	}
	
	/**
	 * 获取所有prodNek
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeNekSelect", method = RequestMethod.GET)
	public  Result getProdTypeNekSelect()  {
		List<ProdTypeNek> phyList = prodTypeNekService.getProdTypeNekSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList);
	}
	/**
	 * 根据ID获取prodTypeNek
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeNek", method = RequestMethod.GET)
	public Result getProdTypeNek(@RequestParam("id") int id) {
		ProdTypeNek prodTypeNek = prodTypeNekService.getProdTypeNek(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,prodTypeNek);
	}

	
	/**
	 * 新增 ProdTypeNek
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addProdTypeNek", method = RequestMethod.POST)
	public Result addProdTypeNek(@RequestBody ProdTypeNek prodTypeNek,HttpSession session) {
		prodTypeNek.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		//默认是启用
		if(prodTypeNek.getIsEnable()==null){
			prodTypeNek.setIsEnable("1");
		}
		prodTypeNek.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = prodTypeNekService.addProdTypeNek(prodTypeNek);
		if(effCnt > 0){
			log.debug("添加物理类目成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",prodTypeNek);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",prodTypeNek);
		}
		
	
	}
	
	
	/**
	 * 更新 ProdTypeNek
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateProdTypeNek", method = RequestMethod.POST)
	public Result updateProdTypeNek(@RequestBody ProdTypeNek prodTypeNek,HttpSession session) {
		prodTypeNek.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		prodTypeNek.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =prodTypeNekService.updateProdTypeNek(prodTypeNek);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",prodTypeNek);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",prodTypeNek);
		}
	}
	
	
	/**
	 * 删除 ProdTypeNek
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteProdTypeNek", method = RequestMethod.GET)
	public Result deleteProdTypeNek(@RequestParam("id") int id) {
		int effCnt = prodTypeNekService.deleteProdTypeNek(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
}
