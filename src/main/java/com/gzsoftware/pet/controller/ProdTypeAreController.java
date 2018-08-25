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
import com.gzsoftware.pet.entity.po.ProdTypeAre;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.ProdTypeAreService;
@Controller
@RequestMapping("/prodTypeAre")
public class ProdTypeAreController extends BaseController {
	private static Log log = LogFactory.getLog(ProdTypeAreController.class);
	
	@Resource
	private ProdTypeAreService prodTypeAreService;
	/**
	 * 获取所有prodPhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeAreList", method = RequestMethod.POST)
	public  Result getProdTypeAreList(@RequestBody DataTablesRequest dtRequest)  {
		List<ProdTypeAre> phyList = prodTypeAreService.getProdTypeAreList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList,prodTypeAreService.countAll(dtRequest),prodTypeAreService.countAll(dtRequest));
	}
	
	/**
	 * 获取所有prodAre
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeAreSelect", method = RequestMethod.GET)
	public  Result getProdTypeAreSelect()  {
		List<ProdTypeAre> phyList = prodTypeAreService.getProdTypeAreSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList);
	}
	
	/**
	 * 根据ID获取prodTypeAre
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeAre", method = RequestMethod.GET)
	public Result getProdTypeAre(@RequestParam("id") int id) {
		ProdTypeAre prodTypeAre = prodTypeAreService.getProdTypeAre(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,prodTypeAre);
	}

	
	/**
	 * 新增 ProdTypeAre
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addProdTypeAre", method = RequestMethod.POST)
	public Result addProdTypeAre(@RequestBody ProdTypeAre prodTypeAre,HttpSession session) {
		prodTypeAre.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时");
		}
		//默认是启用
		if(prodTypeAre.getIsEnable()==null){
			prodTypeAre.setIsEnable("1");
		}
		prodTypeAre.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = prodTypeAreService.addProdTypeAre(prodTypeAre);
		if(effCnt > 0){
			log.debug("添加物理类目成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",prodTypeAre);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",prodTypeAre);
		}
		
	
	}
	
	
	/**
	 * 更新 ProdTypeAre
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateProdTypeAre", method = RequestMethod.POST)
	public Result updateProdTypeAre(@RequestBody ProdTypeAre prodTypeAre,HttpSession session) {
		prodTypeAre.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时");
		}
		prodTypeAre.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =prodTypeAreService.updateProdTypeAre(prodTypeAre);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",prodTypeAre);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",prodTypeAre);
		}
	}
	
	
	/**
	 * 删除 ProdTypeAre
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteProdTypeAre", method = RequestMethod.GET)
	public Result deleteProdTypeAre(@RequestParam("id") int id) {
		int effCnt = prodTypeAreService.deleteProdTypeAre(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
}
