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
import com.gzsoftware.pet.entity.po.WareTypeMch;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.WareTypeMchService;
@Controller
@RequestMapping("/wareTypeMch")
public class WareTypeMchController extends BaseController {
	private static Log log = LogFactory.getLog(WareTypeMchController.class);
	
	@Resource
	private WareTypeMchService wareTypeMchService;
	/**
	 * 获取所有prodPhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWareTypeMchList", method = RequestMethod.POST)
	public  Result getWareTypeMchList(@RequestBody DataTablesRequest dtRequest)  {
		List<WareTypeMch> phyList = wareTypeMchService.getWareTypeMchList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList,wareTypeMchService.countAll(dtRequest),wareTypeMchService.countAll(dtRequest));
	}
	
	/**
	 * 获取所有prodAre
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWareTypeMchSelect", method = RequestMethod.GET)
	public  Result getWareTypeMchSelect()  {
		List<WareTypeMch> phyList = wareTypeMchService.getWareTypeMchSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList);
	}
	
	/**
	 * 根据ID获取wareTypeMch
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWareTypeMch", method = RequestMethod.GET)
	public Result getWareTypeMch(@RequestParam("id") int id) {
		WareTypeMch wareTypeMch = wareTypeMchService.getWareTypeMch(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,wareTypeMch);
	}

	
	/**
	 * 新增 WareTypeMch
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addWareTypeMch", method = RequestMethod.POST)
	public Result addWareTypeMch(@RequestBody WareTypeMch wareTypeMch,HttpSession session) {
		wareTypeMch.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		//默认是启用
		if(wareTypeMch.getIsEnable()==null){
			wareTypeMch.setIsEnable(1);
		}
		wareTypeMch.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = wareTypeMchService.addWareTypeMch(wareTypeMch);
		if(effCnt > 0){
			log.debug("添加机械类目成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",wareTypeMch);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",wareTypeMch);
		}
		
	
	}
	
	
	/**
	 * 更新 WareTypeMch
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateWareTypeMch", method = RequestMethod.POST)
	public Result updateWareTypeMch(@RequestBody WareTypeMch wareTypeMch,HttpSession session) {
		wareTypeMch.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		wareTypeMch.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =wareTypeMchService.updateWareTypeMch(wareTypeMch);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",wareTypeMch);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",wareTypeMch);
		}
	}
	
	
	/**
	 * 删除 WareTypeMch
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteWareTypeMch", method = RequestMethod.GET)
	public Result deleteWareTypeMch(@RequestParam("id") int id) {
		int effCnt = wareTypeMchService.deleteWareTypeMch(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
}
