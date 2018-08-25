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
import com.gzsoftware.pet.entity.po.WareTypeSte;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.WareTypeSteService;
@Controller
@RequestMapping("/wareTypeSte")
public class WareTypeSteController extends BaseController {
	private static Log log = LogFactory.getLog(WareTypeSteController.class);
	
	@Resource
	private WareTypeSteService wareTypeSteService;
	/**
	 * 获取所有prodPhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWareTypeSteList", method = RequestMethod.POST)
	public  Result getWareTypeSteList(@RequestBody DataTablesRequest dtRequest)  {
		List<WareTypeSte> phyList = wareTypeSteService.getWareTypeSteList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList,wareTypeSteService.countAll(dtRequest),wareTypeSteService.countAll(dtRequest));
	}
	
	/**
	 * 获取所有prodAre
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWareTypeSteSelect", method = RequestMethod.GET)
	public  Result getWareTypeSteSelect()  {
		List<WareTypeSte> phyList = wareTypeSteService.getWareTypeSteSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList);
	}
	
	/**
	 * 根据ID获取wareTypeSte
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWareTypeSte", method = RequestMethod.GET)
	public Result getWareTypeSte(@RequestParam("id") int id) {
		WareTypeSte wareTypeSte = wareTypeSteService.getWareTypeSte(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,wareTypeSte);
	}

	
	/**
	 * 新增 WareTypeSte
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addWareTypeSte", method = RequestMethod.POST)
	public Result addWareTypeSte(@RequestBody WareTypeSte wareTypeSte,HttpSession session) {
		wareTypeSte.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		//默认是启用
		if(wareTypeSte.getIsEnable()==null){
			wareTypeSte.setIsEnable(1);
		}
		wareTypeSte.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = wareTypeSteService.addWareTypeSte(wareTypeSte);
		if(effCnt > 0){
			log.debug("添加步法类目成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",wareTypeSte);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",wareTypeSte);
		}
		
	
	}
	
	
	/**
	 * 更新 WareTypeSte
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateWareTypeSte", method = RequestMethod.POST)
	public Result updateWareTypeSte(@RequestBody WareTypeSte wareTypeSte,HttpSession session) {
		wareTypeSte.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		wareTypeSte.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =wareTypeSteService.updateWareTypeSte(wareTypeSte);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",wareTypeSte);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",wareTypeSte);
		}
	}
	
	
	/**
	 * 删除 WareTypeSte
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteWareTypeSte", method = RequestMethod.GET)
	public Result deleteWareTypeSte(@RequestParam("id") int id) {
		int effCnt = wareTypeSteService.deleteWareTypeSte(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
}
