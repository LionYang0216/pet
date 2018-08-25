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
import com.gzsoftware.pet.entity.po.WareTypePrd;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.WareTypePrdService;
@Controller
@RequestMapping("/wareTypePrd")
public class WareTypePrdController extends BaseController {
	private static Log log = LogFactory.getLog(WareTypePrdController.class);
	
	@Resource
	private WareTypePrdService wareTypePrdService;
	/**
	 * 获取所有
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWareTypePrdList", method = RequestMethod.POST)
	public  Result getWareTypePrdList(@RequestBody DataTablesRequest dtRequest)  {
		List<WareTypePrd> phyList = wareTypePrdService.getWareTypePrdList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList,wareTypePrdService.countAll(dtRequest),wareTypePrdService.countAll(dtRequest));
	}
	
	/**
	 * 获取所有wareTypePrd
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWareTypePrdSelect", method = RequestMethod.GET)
	public  Result getWareTypePrdSelect()  {
		List<WareTypePrd> phyList = wareTypePrdService.getWareTypePrdSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList);
	}
	
	/**
	 * 根据ID获取wareTypePrd
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWareTypePrd", method = RequestMethod.GET)
	public Result getWareTypePrd(@RequestParam("id") int id) {
		WareTypePrd wareTypePrd = wareTypePrdService.getWareTypePrd(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,wareTypePrd);
	}

	
	/**
	 * 新增 WareTypePrd
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addWareTypePrd", method = RequestMethod.POST)
	public Result addWareTypePrd(@RequestBody WareTypePrd wareTypePrd,HttpSession session) {
		wareTypePrd.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		//默认是启用
		if(wareTypePrd.getIsEnable()==null){
			wareTypePrd.setIsEnable(1);
		}
		wareTypePrd.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = wareTypePrdService.addWareTypePrd(wareTypePrd);
		if(effCnt > 0){
			log.debug("添加制品成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",wareTypePrd);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",wareTypePrd);
		}
		
	
	}
	
	
	/**
	 * 更新 WareTypePrd
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateWareTypePrd", method = RequestMethod.POST)
	public Result updateWareTypePrd(@RequestBody WareTypePrd wareTypePrd,HttpSession session) {
		wareTypePrd.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		wareTypePrd.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =wareTypePrdService.updateWareTypePrd(wareTypePrd);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",wareTypePrd);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",wareTypePrd);
		}
	}
	
	
	/**
	 * 删除 WareTypePrd
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteWareTypePrd", method = RequestMethod.GET)
	public Result deleteWareTypePrd(@RequestParam("id") int id) {
		int effCnt = wareTypePrdService.deleteWareTypePrd(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
}
