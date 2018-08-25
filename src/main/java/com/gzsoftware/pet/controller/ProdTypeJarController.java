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
import com.gzsoftware.pet.entity.po.ProdTypeJar;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.ProdTypeJarService;
@Controller
@RequestMapping("/prodTypeJar")
public class ProdTypeJarController extends BaseController {
	private static Log log = LogFactory.getLog(ProdTypeJarController.class);
	
	@Resource
	private ProdTypeJarService prodTypeJarService;
	/**
	 * 获取所有prodPhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeJarList", method = RequestMethod.POST)
	public  Result getProdTypeJarList(@RequestBody DataTablesRequest dtRequest)  {
		List<ProdTypeJar> phyList = prodTypeJarService.getProdTypeJarList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList,prodTypeJarService.countAll(dtRequest),prodTypeJarService.countAll(dtRequest));
	}
	/**
	 * 获取所有prodJar
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeJarSelect", method = RequestMethod.GET)
	public  Result getProdTypeJarSelect()  {
		List<ProdTypeJar> phyList = prodTypeJarService.getProdTypeJarSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList);
	}
	
	/**
	 * 根据ID获取prodTypeJar
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeJar", method = RequestMethod.GET)
	public Result getProdTypeJar(@RequestParam("id") int id) {
		ProdTypeJar prodTypeJar = prodTypeJarService.getProdTypeJar(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,prodTypeJar);
	}

	
	/**
	 * 新增 ProdTypeJar
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addProdTypeJar", method = RequestMethod.POST)
	public Result addProdTypeJar(@RequestBody ProdTypeJar prodTypeJar,HttpSession session) {
		prodTypeJar.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		//默认是启用
		if(prodTypeJar.getIsEnable()==null){
			prodTypeJar.setIsEnable("1");
		}
		prodTypeJar.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = prodTypeJarService.addProdTypeJar(prodTypeJar);
		if(effCnt > 0){
			log.debug("添加物理类目成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",prodTypeJar);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",prodTypeJar);
		}
		
	
	}
	
	
	/**
	 * 更新 ProdTypeJar
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateProdTypeJar", method = RequestMethod.POST)
	public Result updateProdTypeJar(@RequestBody ProdTypeJar prodTypeJar,HttpSession session) {
		prodTypeJar.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		prodTypeJar.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =prodTypeJarService.updateProdTypeJar(prodTypeJar);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",prodTypeJar);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",prodTypeJar);
		}
	}
	
	
	/**
	 * 删除 ProdTypeJar
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteProdTypeJar", method = RequestMethod.GET)
	public Result deleteProdTypeJar(@RequestParam("id") int id) {
		int effCnt = prodTypeJarService.deleteProdTypeJar(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
}
