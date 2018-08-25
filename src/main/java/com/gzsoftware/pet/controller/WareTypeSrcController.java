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
import com.gzsoftware.pet.entity.po.WareTypeSrc;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.WareTypeSrcService;
@Controller
@RequestMapping("/wareTypeSrc")
public class WareTypeSrcController extends BaseController {
	private static Log log = LogFactory.getLog(WareTypeSrcController.class);
	
	@Resource
	private WareTypeSrcService wareTypeSrcService;
	/**
	 * 获取所有prodPhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWareTypeSrcList", method = RequestMethod.POST)
	public  Result getWareTypeSrcList(@RequestBody DataTablesRequest dtRequest)  {
		List<WareTypeSrc> phyList = wareTypeSrcService.getWareTypeSrcList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList,wareTypeSrcService.countAll(dtRequest),wareTypeSrcService.countAll(dtRequest));
	}
	
	/**
	 * 获取所有prodAre
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWareTypeSrcSelect", method = RequestMethod.GET)
	public  Result getWareTypeSrcSelect()  {
		List<WareTypeSrc> phyList = wareTypeSrcService.getWareTypeSrcSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList);
	}
	
	/**
	 * 根据ID获取wareTypeSrc
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWareTypeSrc", method = RequestMethod.GET)
	public Result getWareTypeSrc(@RequestParam("id") int id) {
		WareTypeSrc wareTypeSrc = wareTypeSrcService.getWareTypeSrc(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,wareTypeSrc);
	}

	
	/**
	 * 新增 WareTypeSrc
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addWareTypeSrc", method = RequestMethod.POST)
	public Result addWareTypeSrc(@RequestBody WareTypeSrc wareTypeSrc,HttpSession session) {
		wareTypeSrc.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		//默认是启用
		if(wareTypeSrc.getIsEnable()==null){
			wareTypeSrc.setIsEnable(1);
		}
		wareTypeSrc.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = wareTypeSrcService.addWareTypeSrc(wareTypeSrc);
		if(effCnt > 0){
			log.debug("添加原材料类目成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",wareTypeSrc);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",wareTypeSrc);
		}
		
	
	}
	
	
	/**
	 * 更新 WareTypeSrc
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateWareTypeSrc", method = RequestMethod.POST)
	public Result updateWareTypeSrc(@RequestBody WareTypeSrc wareTypeSrc,HttpSession session) {
		wareTypeSrc.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		wareTypeSrc.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =wareTypeSrcService.updateWareTypeSrc(wareTypeSrc);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",wareTypeSrc);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",wareTypeSrc);
		}
	}
	
	
	/**
	 * 删除 WareTypeSrc
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteWareTypeSrc", method = RequestMethod.GET)
	public Result deleteWareTypeSrc(@RequestParam("id") int id) {
		int effCnt = wareTypeSrcService.deleteWareTypeSrc(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
}
