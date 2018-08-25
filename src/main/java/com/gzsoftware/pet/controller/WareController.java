package com.gzsoftware.pet.controller;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.gzsoftware.pet.entity.po.User;
import com.gzsoftware.pet.entity.po.UserWareFav;
import com.gzsoftware.pet.entity.po.Ware;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.UserWareFavService;
import com.gzsoftware.pet.service.WareService;
import com.gzsoftware.pet.utils.CommonUtil;
@Controller
@RequestMapping("/ware")
public class WareController extends BaseController {
	private static Log log = LogFactory.getLog(WareController.class);
	
	@Resource
	private WareService wareService;
	
	@Resource
	private UserWareFavService userWareFavService;

	/**
	 * 获取所有warePhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWareList", method = RequestMethod.POST)
	public  Result getWareList(@RequestBody DataTablesRequest dtRequest)  {
		List<Ware> wareList = wareService.getWareList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,wareList,wareService.countAll(dtRequest),wareService.countAll(dtRequest));
	}
	

	
	/**
	 * 根据ID获取ware
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWare", method = RequestMethod.GET)
	public Result getWare(@RequestParam("id") int id) {
		Ware ware = wareService.getWare(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,ware);
	}
	

	/**
	 * 根据ShopID获取favTotal
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWareFavTotal", method = RequestMethod.GET)
	public Result getWareFavTotal(@RequestParam("shopId") int shopId) {
		Integer favTotal = wareService.getWareFavTotal(shopId);
		return new Result(Result.RESULT_FLAG_SUCCESS,favTotal);
	}
	
	/**
	 * 根据ShopID获取visitTotal
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getWareVisitTotal", method = RequestMethod.GET)
	public Result getWareVisitTotal(@RequestParam("shopId") int shopId) {
		Integer visitTotal = wareService.getWareVisitTotal(shopId);
		return new Result(Result.RESULT_FLAG_SUCCESS,visitTotal);
	}
	
	/**
	 * 新增 Ware
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addWare", method = RequestMethod.POST)
	public Result addWare(@RequestBody Ware ware,HttpSession session) {
		 Admin currentAdmin = super.getCurrentAdmin(session);
		 ware.setLastUpdateTime(new Date());
		 ware.setNameEn(CommonUtil.isStrEmpty(ware.getNameEn())?ware.getNameCn():ware.getNameEn());
		 ware.setDescriptionEn(CommonUtil.isStrEmpty(ware.getDescriptionEn())?ware.getDescriptionCn():ware.getDescriptionEn());
		 ware.setLocationEn(CommonUtil.isStrEmpty(ware.getLocationEn())?ware.getLocationCn():ware.getLocationEn());
		 ware.setAdaptMachineEn(CommonUtil.isStrEmpty(ware.getAdaptMachineEn())?ware.getAdaptMachineCn():ware.getAdaptMachineEn());
		 ware.setAfterServiceEn(CommonUtil.isStrEmpty(ware.getAfterServiceEn())?ware.getAfterServiceCn():ware.getAfterServiceEn());
		 ware.setBrandEn(CommonUtil.isStrEmpty(ware.getBrandEn())?ware.getBrandCn():ware.getBrandEn());
		 ware.setExpressEn(CommonUtil.isStrEmpty(ware.getExpressEn())?ware.getExpressCn():ware.getExpressEn());
		 ware.setMakeModeEn(CommonUtil.isStrEmpty(ware.getMakeModeEn())?ware.getMakeModeCn():ware.getMakeModeEn());
		 ware.setModalEn(CommonUtil.isStrEmpty(ware.getModalEn())?ware.getModalCn():ware.getModalEn());
		 ware.setProduceProductEn(CommonUtil.isStrEmpty(ware.getProduceProductEn())?ware.getProduceProductCn():ware.getProduceProductEn());
		 ware.setProduceAbilityEn(CommonUtil.isStrEmpty(ware.getProduceAbilityEn())?ware.getProduceAbilityCn():ware.getProduceAbilityEn());
		 ware.setProduceSourceEn(CommonUtil.isStrEmpty(ware.getProduceSourceEn())?ware.getProduceSourceCn():ware.getProduceSourceEn());
		 ware.setRunnerEn(CommonUtil.isStrEmpty(ware.getRunnerEn())?ware.getRunnerCn():ware.getRunnerEn());
		 if(getCurrentUser(session)!=null){
			 ware.setLastUpdateUserId(getCurrentUser(session).getId());
		 }
		int effCnt = wareService.addWare(ware);
		if(effCnt > 0){
			log.debug("添加产品成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",ware);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",ware);
		}
		
	
	}
	
	
	/**
	 * 更新 Ware
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateWare", method = RequestMethod.POST)
	public Result updateWare(@RequestBody Ware ware,HttpSession session) {
		 ware.setLastUpdateTime(new Date());
		 ware.setNameEn(CommonUtil.isStrEmpty(ware.getNameEn())?ware.getNameCn():ware.getNameEn());
		 ware.setDescriptionEn(CommonUtil.isStrEmpty(ware.getDescriptionEn())?ware.getDescriptionCn():ware.getDescriptionEn());
		 ware.setLocationEn(CommonUtil.isStrEmpty(ware.getLocationEn())?ware.getLocationCn():ware.getLocationEn());
		 ware.setAdaptMachineEn(CommonUtil.isStrEmpty(ware.getAdaptMachineEn())?ware.getAdaptMachineCn():ware.getAdaptMachineEn());
		 ware.setAfterServiceEn(CommonUtil.isStrEmpty(ware.getAfterServiceEn())?ware.getAfterServiceCn():ware.getAfterServiceEn());
		 ware.setBrandEn(CommonUtil.isStrEmpty(ware.getBrandEn())?ware.getBrandCn():ware.getBrandEn());
		 ware.setExpressEn(CommonUtil.isStrEmpty(ware.getExpressEn())?ware.getExpressCn():ware.getExpressEn());
		 ware.setMakeModeEn(CommonUtil.isStrEmpty(ware.getMakeModeEn())?ware.getMakeModeCn():ware.getMakeModeEn());
		 ware.setModalEn(CommonUtil.isStrEmpty(ware.getModalEn())?ware.getModalCn():ware.getModalEn());
		 ware.setProduceProductEn(CommonUtil.isStrEmpty(ware.getProduceProductEn())?ware.getProduceProductCn():ware.getProduceProductEn());
		 ware.setProduceAbilityEn(CommonUtil.isStrEmpty(ware.getProduceAbilityEn())?ware.getProduceAbilityCn():ware.getProduceAbilityEn());
		 ware.setProduceSourceEn(CommonUtil.isStrEmpty(ware.getProduceSourceEn())?ware.getProduceSourceCn():ware.getProduceSourceEn());
		 ware.setRunnerEn(CommonUtil.isStrEmpty(ware.getRunnerEn())?ware.getRunnerCn():ware.getRunnerEn());
		 if(getCurrentUser(session)!=null){
			 ware.setLastUpdateUserId(getCurrentUser(session).getId());
		 }
		int effCnt =wareService.updateWare(ware);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",ware);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",ware);
		}
	}
	
	
	/**
	 * 删除 Ware
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteWare", method = RequestMethod.GET)
	public Result deleteWare(@RequestParam("id") int id) {
		int effCnt = wareService.deleteWare(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
	
	/**
	 *  更新Ware访问次数
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/addWareVisitCnt", method = RequestMethod.GET)
	public Result addWareVisitCnt(@RequestParam("id") int id) {
		int effCnt = wareService.addWareVisitCnt(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"商品访问次数增加成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"商品访问次数增加失败！！");
		}
	}
	
	/**
	 *  更新Ware收藏次数
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/addWareFavCnt", method = RequestMethod.GET)
	public Result addWareFavCnt(@RequestParam("id") int id,HttpSession session) {
		 User currentUser = super.getCurrentUser(session);
		 if(currentUser==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"登陆超时,请重新登陆！！");
		 }
		UserWareFav wareFav=new UserWareFav();
		wareFav.setWareId(id);
		wareFav.setUserId(currentUser.getId());
		int effCnt = userWareFavService.addWareFav(wareFav);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"产品收藏成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"产品收藏失败！！");
		}
	}
	
	/**
	 *  更新Ware点赞次数
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/addWareUpCnt", method = RequestMethod.GET)
	public Result addWareUpCnt(@RequestParam("id") int id) {
		int effCnt = wareService.addWareUpCnt(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"产品点赞次数增加成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"产品访点赞数增加失败！！");
		}
	}
	
	/**
	 *  取消ware收藏
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/removeWareFavCnt", method = RequestMethod.GET)
	public Result removeWareFavCnt(@RequestParam("id") int id,HttpSession session) {
		 User currentUser = super.getCurrentUser(session);
		 if(currentUser==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"登陆超时,请重新登陆！！");
		 }
		UserWareFav wareFav=userWareFavService.getUserWareFav(currentUser.getId(), id);
		if(wareFav==null){
			return new Result(Result.RESULT_FLAG_FAIL,"没有收藏记录");
		}
		int effCnt = userWareFavService.removeWareFav(wareFav);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"商品取消收藏成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"商品取消收藏失败！！");
		}
	}
	
	/**
	 * 获取收藏信息
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getUserWareFav", method = RequestMethod.GET)
	public Result getUserWareFav(@RequestParam("userId") int userId,@RequestParam("wareId") int wareId) {
		UserWareFav wareFav = userWareFavService.getUserWareFav(userId,wareId);
		return new Result(Result.RESULT_FLAG_SUCCESS,wareFav);
	}
	
	/***
	 * 热门商品
	 * @param length
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTopVisitWareList", method = RequestMethod.GET)
	public  Result getTopVisitWareList(@RequestParam(value="length",required=false) Integer length)  {
		if(length==null){
			length=10;
		}
		List<Map> wareList = wareService.getTopVisitWareList(length);
		return new Result(Result.RESULT_FLAG_SUCCESS,wareList);
	}
}
