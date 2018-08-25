package com.gzsoftware.pet.controller;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
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

import com.gzsoftware.pet.entity.po.ProdCost;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.ProdCostService;
@Controller
@RequestMapping("/prodCost")
public class ProdCostController extends BaseController {
	private static Log log = LogFactory.getLog(ProdCostController.class);
	
	@Resource
	private ProdCostService prodCostService;
	/**
	 * 获取所有prodPhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdCostList", method = RequestMethod.POST)
	public  Result getProdCostList(@RequestBody DataTablesRequest dtRequest)  {
		List<ProdCost> phyList = prodCostService.getProdCostList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList,prodCostService.countAll(dtRequest),prodCostService.countAll(dtRequest));
	}
	
	/**
	 * 获取所有prodCost
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdCostSelect", method = RequestMethod.GET)
	public  Result getProdCostSelect()  {
		List<ProdCost> prodCostList = prodCostService.getProdCostSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,prodCostList);
	}

	
	/**
	 * 根据ID获取prodCost
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdCost", method = RequestMethod.GET)
	public Result getProdCost(@RequestParam("id") int id) {
		ProdCost prodCost = prodCostService.getProdCost(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,prodCost);
	}

	
	/**
	 * 新增 ProdCost
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addProdCost", method = RequestMethod.POST)
	public Result addProdCost(@RequestBody ProdCost prodCost,HttpSession session) {
		int effCnt = prodCostService.addProdCost(prodCost);
		if(effCnt > 0){
			log.debug("添加费用成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",prodCost);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",prodCost);
		}
		
	
	}
	
	
	/**
	 * 更新 ProdCost
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateProdCost", method = RequestMethod.POST)
	public Result updateProdCost(@RequestBody ProdCost prodCost) {
		int effCnt =prodCostService.updateProdCost(prodCost);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",prodCost);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",prodCost);
		}
	}
	
	
	/**
	 * 删除 ProdCost
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteProdCost", method = RequestMethod.GET)
	public Result deleteProdCost(@RequestParam("id") int id) {
		int effCnt = prodCostService.deleteProdCost(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
}
