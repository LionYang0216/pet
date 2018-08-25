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
import com.gzsoftware.pet.entity.po.ProdTypeWid;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.ProdTypeWidService;
@Controller
@RequestMapping("/prodTypeWid")
public class ProdTypeWidController extends BaseController {
	private static Log log = LogFactory.getLog(ProdTypeWidController.class);
	
	@Resource
	private ProdTypeWidService prodTypeWidService;
	/**
	 * 获取所有prodPhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeWidList", method = RequestMethod.POST)
	public  Result getProdTypeWidList(@RequestBody DataTablesRequest dtRequest)  {
		List<ProdTypeWid> phyList = prodTypeWidService.getProdTypeWidList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList,prodTypeWidService.countAll(dtRequest),prodTypeWidService.countAll(dtRequest));
	}
	
	/**
	 * 获取所有prodWid
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeWidSelect", method = RequestMethod.GET)
	public  Result getProdTypeWidSelect()  {
		List<ProdTypeWid> phyList = prodTypeWidService.getProdTypeWidSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList);
	}
	
	/**
	 * 根据ID获取prodTypeWid
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeWid", method = RequestMethod.GET)
	public Result getProdTypeWid(@RequestParam("id") int id) {
		ProdTypeWid prodTypeWid = prodTypeWidService.getProdTypeWid(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,prodTypeWid);
	}

	
	/**
	 * 新增 ProdTypeWid
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addProdTypeWid", method = RequestMethod.POST)
	public Result addProdTypeWid(@RequestBody ProdTypeWid prodTypeWid,HttpSession session) {
		prodTypeWid.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		//默认是启用
		if(prodTypeWid.getIsEnable()==null){
			prodTypeWid.setIsEnable("1");
		}
		prodTypeWid.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = prodTypeWidService.addProdTypeWid(prodTypeWid);
		if(effCnt > 0){
			log.debug("添加物理类目成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",prodTypeWid);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",prodTypeWid);
		}
		
	
	}
	
	
	/**
	 * 更新 ProdTypeWid
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateProdTypeWid", method = RequestMethod.POST)
	public Result updateProdTypeWid(@RequestBody ProdTypeWid prodTypeWid,HttpSession session) {
		prodTypeWid.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		prodTypeWid.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =prodTypeWidService.updateProdTypeWid(prodTypeWid);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",prodTypeWid);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",prodTypeWid);
		}
	}
	
	
	/**
	 * 删除 ProdTypeWid
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteProdTypeWid", method = RequestMethod.GET)
	public Result deleteProdTypeWid(@RequestParam("id") int id) {
		int effCnt = prodTypeWidService.deleteProdTypeWid(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
}
