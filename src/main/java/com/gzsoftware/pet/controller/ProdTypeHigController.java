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
import com.gzsoftware.pet.entity.po.ProdTypeHig;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.ProdTypeHigService;
@Controller
@RequestMapping("/prodTypeHig")
public class ProdTypeHigController extends BaseController {
	private static Log log = LogFactory.getLog(ProdTypeHigController.class);
	
	@Resource
	private ProdTypeHigService prodTypeHigService;
	/**
	 * 获取所有prodPhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeHigList", method = RequestMethod.POST)
	public  Result getProdTypeHigList(@RequestBody DataTablesRequest dtRequest)  {
		List<ProdTypeHig> phyList = prodTypeHigService.getProdTypeHigList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList,prodTypeHigService.countAll(dtRequest),prodTypeHigService.countAll(dtRequest));
	}
	
	/**
	 * 获取所有prodHig
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeHigSelect", method = RequestMethod.GET)
	public  Result getProdTypeHigSelect()  {
		List<ProdTypeHig> phyList = prodTypeHigService.getProdTypeHigSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList);
	}
	/**
	 * 根据ID获取prodTypeHig
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeHig", method = RequestMethod.GET)
	public Result getProdTypeHig(@RequestParam("id") int id) {
		ProdTypeHig prodTypeHig = prodTypeHigService.getProdTypeHig(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,prodTypeHig);
	}

	
	/**
	 * 新增 ProdTypeHig
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addProdTypeHig", method = RequestMethod.POST)
	public Result addProdTypeHig(@RequestBody ProdTypeHig prodTypeHig,HttpSession session) {
		prodTypeHig.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		//默认是启用
		if(prodTypeHig.getIsEnable()==null){
			prodTypeHig.setIsEnable("1");
		}
		prodTypeHig.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = prodTypeHigService.addProdTypeHig(prodTypeHig);
		if(effCnt > 0){
			log.debug("添加物理类目成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",prodTypeHig);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",prodTypeHig);
		}
		
	
	}
	
	
	/**
	 * 更新 ProdTypeHig
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateProdTypeHig", method = RequestMethod.POST)
	public Result updateProdTypeHig(@RequestBody ProdTypeHig prodTypeHig,HttpSession session) {
		prodTypeHig.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		prodTypeHig.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =prodTypeHigService.updateProdTypeHig(prodTypeHig);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",prodTypeHig);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",prodTypeHig);
		}
	}
	
	
	/**
	 * 删除 ProdTypeHig
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteProdTypeHig", method = RequestMethod.GET)
	public Result deleteProdTypeHig(@RequestParam("id") int id) {
		int effCnt = prodTypeHigService.deleteProdTypeHig(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
}
