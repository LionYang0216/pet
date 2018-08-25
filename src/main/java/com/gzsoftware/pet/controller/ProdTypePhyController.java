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
import com.gzsoftware.pet.entity.po.ProdTypePhy;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.ProdTypePhyService;
@Controller
@RequestMapping("/prodTypePhy")
public class ProdTypePhyController extends BaseController {
	private static Log log = LogFactory.getLog(ProdTypePhyController.class);
	
	@Resource
	private ProdTypePhyService prodTypePhyService;
	/**
	 * 获取所有prodPhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypePhyList", method = RequestMethod.POST)
	public  Result getProdTypePhyList(@RequestBody DataTablesRequest dtRequest)  {
		List<ProdTypePhy> phyList = prodTypePhyService.getProdTypePhyList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList,prodTypePhyService.countAll(dtRequest),prodTypePhyService.countAll(dtRequest));
	}
	
	/**
	 * 获取所有prodPhy
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypePhySelect", method = RequestMethod.GET)
	public  Result getProdTypePhySelect()  {
		List<ProdTypePhy> phyList = prodTypePhyService.getProdTypePhySelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList);
	}
	
	/**
	 * 根据ID获取prodTypePhy
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypePhy", method = RequestMethod.GET)
	public Result getProdTypePhy(@RequestParam("id") int id) {
		ProdTypePhy prodTypePhy = prodTypePhyService.getProdTypePhy(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,prodTypePhy);
	}

	
	/**
	 * 新增 ProdTypePhy
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addProdTypePhy", method = RequestMethod.POST)
	public Result addProdTypePhy(@RequestBody ProdTypePhy prodTypePhy,HttpSession session) {
		prodTypePhy.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		//默认是启用
		if(prodTypePhy.getIsEnable()==null){
			prodTypePhy.setIsEnable("1");
		}
		prodTypePhy.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = prodTypePhyService.addProdTypePhy(prodTypePhy);
		if(effCnt > 0){
			log.debug("添加物理类目成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",prodTypePhy);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",prodTypePhy);
		}
		
	
	}
	
	
	/**
	 * 更新 ProdTypePhy
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateProdTypePhy", method = RequestMethod.POST)
	public Result updateProdTypePhy(@RequestBody ProdTypePhy prodTypePhy,HttpSession session) {
		prodTypePhy.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		prodTypePhy.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =prodTypePhyService.updateProdTypePhy(prodTypePhy);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",prodTypePhy);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",prodTypePhy);
		}
	}
	
	
	/**
	 * 删除 ProdTypePhy
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteProdTypePhy", method = RequestMethod.GET)
	public Result deleteProdTypePhy(@RequestParam("id") int id) {
		int effCnt = prodTypePhyService.deleteProdTypePhy(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
}
