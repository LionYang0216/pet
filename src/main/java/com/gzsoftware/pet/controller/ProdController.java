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
import com.gzsoftware.pet.entity.po.Prod;
import com.gzsoftware.pet.entity.po.User;
import com.gzsoftware.pet.entity.po.UserProdFav;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.ProdPicService;
import com.gzsoftware.pet.service.ProdService;
import com.gzsoftware.pet.service.UserProdFavService;
import com.gzsoftware.pet.service.UserService;
import com.gzsoftware.pet.utils.CommonUtil;
@Controller
@RequestMapping("/prod")
public class ProdController extends BaseController {
	private static Log log = LogFactory.getLog(ProdController.class);
	
	@Resource
	private ProdService prodService;
	@Resource
	private ProdPicService prodPicService;
	@Resource
	private UserProdFavService userProdFavService;
	@Resource
	private UserService userService;
	/**
	 * 获取所有prodPhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdList", method = RequestMethod.POST)
	public  Result getProdList(@RequestBody DataTablesRequest dtRequest)  {
		List<Prod> prodList = prodService.getProdList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,prodList,prodService.countAll(dtRequest),prodService.countAll(dtRequest));
	}
	
	/***
	 * 关联编码的产品列表
	 * @param refCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRefProdList", method = RequestMethod.GET)
	public  Result getRefProdList(@RequestParam("refCode") String refCode)  {
		if(refCode==null||refCode=="") {
			return new Result(Result.RESULT_FLAG_SUCCESS,"");
		}else {
			List<Prod> prodList = prodService.getRefProdList(refCode);
			return new Result(Result.RESULT_FLAG_SUCCESS,prodList);
		}

	}

	
	/**
	 * 根据ID获取prod
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProd", method = RequestMethod.GET)
	public Result getProd(@RequestParam("id") int id) {
		Prod prod = prodService.getProd(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,prod);
	}

	
	/**
	 * 新增 Prod
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addProd", method = RequestMethod.POST)
	public Result addProd(@RequestBody Prod prod,HttpSession session) {
		 Admin currentAdmin = super.getCurrentAdmin(session);
		 prod.setLastUpdateTime(new Date());
		 if(currentAdmin!=null) {
			 prod.setLastUpdateAdminId(currentAdmin.getId());
		 }
		 if(getCurrentUser(session)!=null){
			 prod.setUserId(getCurrentUser(session).getId());
		 }
		 prod.setNameEn(CommonUtil.isStrEmpty(prod.getNameEn())?prod.getNameCn():prod.getNameEn());
		 prod.setDescriptionEn(CommonUtil.isStrEmpty(prod.getDescriptionEn())?prod.getDescriptionCn():prod.getDescriptionEn());
		int effCnt = prodService.addProd(prod);
		if(effCnt > 0){
			log.debug("添加产品成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",prod);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",prod);
		}
		
	
	}
	
	
	/**
	 * 更新 Prod
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateProd", method = RequestMethod.POST)
	public Result updateProd(@RequestBody Prod prod,HttpSession session) {
		 Admin currentAdmin = super.getCurrentAdmin(session);
		 prod.setLastUpdateTime(new Date());
		 if(currentAdmin!=null) {
			 prod.setLastUpdateAdminId(currentAdmin.getId());
		 }
		 if(getCurrentUser(session)!=null){
			 prod.setUserId(getCurrentUser(session).getId());
		 }
		 prod.setNameEn(CommonUtil.isStrEmpty(prod.getNameEn())?prod.getNameCn():prod.getNameEn());
		 prod.setDescriptionEn(CommonUtil.isStrEmpty(prod.getDescriptionEn())?prod.getDescriptionCn():prod.getDescriptionEn());
		int effCnt =prodService.updateProd(prod);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",prod);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",prod);
		}
	}
	
	
	/**
	 * 删除 Prod
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteProd", method = RequestMethod.GET)
	public Result deleteProd(@RequestParam("id") int id) {
		int effCnt = prodService.deleteProd(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
	
	/**
	 *  更新Prod访问次数
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/addProdVisitCnt", method = RequestMethod.GET)
	public Result addProdVisitCnt(@RequestParam("id") int id) {
		int effCnt = prodService.addProdVisitCnt(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"产品访问次数增加成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"产品访问次数增加失败！！");
		}
	}
	
	/**
	 *  更新Prod收藏次数
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/addProdFavCnt", method = RequestMethod.GET)
	public Result addProdFavCnt(@RequestParam("id") int id,HttpSession session) {
		 User currentUser = super.getCurrentUser(session);
		 if(currentUser==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"登陆超时,请重新登陆！！");
		 }
		UserProdFav prodFav=new UserProdFav();
		prodFav.setProdId(id);
		prodFav.setUserId(currentUser.getId());
		int effCnt = userProdFavService.addProdFav(prodFav);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"产品收藏成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"产品收藏失败！！");
		}
	}
	/**
	 *  取消Prod收藏
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/removeProdFavCnt", method = RequestMethod.GET)
	public Result removeProdFavCnt(@RequestParam("id") int id,HttpSession session) {
		 User currentUser = super.getCurrentUser(session);
		 if(currentUser==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"登陆超时,请重新登陆！！");
		 }
		UserProdFav prodFav=userProdFavService.getUserProdFav(currentUser.getId(), id);
		if(prodFav==null){
			return new Result(Result.RESULT_FLAG_FAIL,"没有收藏记录");
		}
		int effCnt = userProdFavService.removeProdFav(prodFav);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"产品取消收藏成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"产品取消收藏失败！！");
		}
	}
	
	/**
	 * 获取收藏信息
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getUserProdFav", method = RequestMethod.GET)
	public Result getUserProdFav(@RequestParam("userId") int userId,@RequestParam("prodId") int prodId) {
		UserProdFav prodFav = userProdFavService.getUserProdFav(userId,prodId);
		return new Result(Result.RESULT_FLAG_SUCCESS,prodFav);
	}
	
	
	/**
	 *  更新Prod点赞次数
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/addProdUpCnt", method = RequestMethod.GET)
	public Result addProdUpCnt(@RequestParam("id") int id) {
		int effCnt = prodService.addProdUpCnt(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"产品点赞次数增加成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"产品访点赞数增加失败！！");
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/chargeViewProd", method = RequestMethod.GET)
	public Result chargeViewProd(@RequestParam("prodId") int prodId,HttpSession session) {
		User currentUser=super.getCurrentUser(session);
		if(currentUser==null){
			return new Result(Result.RESULT_FLAG_FAIL,"用户未登录，不能使用本操作");
		}
		Prod prod=prodService.getProd(prodId);
		Integer cost=prod.getProdCost().getCost();
		if(currentUser.getBalanceTotal()<cost){
			return new Result(Result.RESULT_FLAG_FAIL,"余额不足，当前余额: "+currentUser.getBalanceTotal()+" 元，建议充值");
		}
		User user=userService.setChargeViewProd(currentUser, prod);
		if(user!=null){
			session.setAttribute("user", user);
			return new Result(Result.RESULT_FLAG_SUCCESS,"你支付查看"+prod.getNameCn()+"，需要费用"  + cost + "元，支付成功，余额是"+user.getBalanceTotal()+"元",user);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"扣费失败！！");
		}
	}
	
	/***
	 * 热门瓶库
	 * @param length
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTopVisitProdList", method = RequestMethod.GET)
	public  Result getTopVisitProdList(@RequestParam(value="length",required=false) Integer length)  {
		if(length==null){
			length=10;
		}
		List<Map> prodList = prodService.getTopVisitProdList(length);
		return new Result(Result.RESULT_FLAG_SUCCESS,prodList);
	}
}
