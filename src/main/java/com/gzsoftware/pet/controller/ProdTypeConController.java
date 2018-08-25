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
import com.gzsoftware.pet.entity.po.ProdTypeCon;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.ProdTypeConService;
@Controller
@RequestMapping("/prodTypeCon")
public class ProdTypeConController extends BaseController {
	private static Log log = LogFactory.getLog(ProdTypeConController.class);
	
	@Resource
	private ProdTypeConService prodTypeConService;
	/**
	 * 获取所有prodPhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeConList", method = RequestMethod.POST)
	public  Result getProdTypeConList(@RequestBody DataTablesRequest dtRequest)  {
		List<ProdTypeCon> phyList = prodTypeConService.getProdTypeConList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList,prodTypeConService.countAll(dtRequest),prodTypeConService.countAll(dtRequest));
	}
	
	/**
	 * 获取所有prodCon
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeConSelect", method = RequestMethod.GET)
	public  Result getProdTypeConSelect()  {
		List<ProdTypeCon> phyList = prodTypeConService.getProdTypeConSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList);
	}
	/**
	 * 根据ID获取prodTypeCon
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeCon", method = RequestMethod.GET)
	public Result getProdTypeCon(@RequestParam("id") int id) {
		ProdTypeCon prodTypeCon = prodTypeConService.getProdTypeCon(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,prodTypeCon);
	}

	
	/**
	 * 新增 ProdTypeCon
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addProdTypeCon", method = RequestMethod.POST)
	public Result addProdTypeCon(@RequestBody ProdTypeCon prodTypeCon,HttpSession session) {
		prodTypeCon.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		//默认是启用
		if(prodTypeCon.getIsEnable()==null){
			prodTypeCon.setIsEnable("1");
		}
		prodTypeCon.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = prodTypeConService.addProdTypeCon(prodTypeCon);
		if(effCnt > 0){
			log.debug("添加物理类目成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",prodTypeCon);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",prodTypeCon);
		}
		
	
	}
	
	
	/**
	 * 更新 ProdTypeCon
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateProdTypeCon", method = RequestMethod.POST)
	public Result updateProdTypeCon(@RequestBody ProdTypeCon prodTypeCon,HttpSession session) {
		prodTypeCon.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		prodTypeCon.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =prodTypeConService.updateProdTypeCon(prodTypeCon);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",prodTypeCon);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",prodTypeCon);
		}
	}
	
	
	/**
	 * 删除 ProdTypeCon
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteProdTypeCon", method = RequestMethod.GET)
	public Result deleteProdTypeCon(@RequestParam("id") int id) {
		int effCnt = prodTypeConService.deleteProdTypeCon(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
}
