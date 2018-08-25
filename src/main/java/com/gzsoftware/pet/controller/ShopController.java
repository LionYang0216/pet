package com.gzsoftware.pet.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzsoftware.pet.entity.po.Shop;
import com.gzsoftware.pet.entity.po.User;
import com.gzsoftware.pet.entity.po.UserProdFav;
import com.gzsoftware.pet.entity.po.UserShopFav;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.ShopService;
import com.gzsoftware.pet.service.UserShopFavService;

@Controller
@RequestMapping("shop")
public class ShopController extends BaseController{

	private static Log log = LogFactory.getLog(ShopController.class);
	
	@Resource
	private ShopService shopService;
	
	@Resource
	private UserShopFavService userShopFavService;
	

	
	
	/**
	 * 新增 Shop
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addShop", method = RequestMethod.POST)
	public Result addShop(@RequestBody Shop shop,HttpSession session) {
		if(getCurrentUser(session)!=null){
			 shop.setUserId(getCurrentUser(session).getId());
		 }
		int effCnt = shopService.addShop(shop);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",shop);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",shop);
		}
		
	
	}

	/**
	 * 获取所有shops
	 * @param dtRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getShopList", method = RequestMethod.POST)
	public  Result getShopList(@RequestBody DataTablesRequest dtRequest)  {
		List<Shop> shopList = shopService.getShopList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,shopList,shopService.countAll(dtRequest),shopService.countAll(dtRequest));
	}
	
	@ResponseBody
	@RequestMapping(value = "/getShopSelect", method = RequestMethod.GET)
	public  Result getShopSelect()  {
		List<Shop> shopList = shopService.getShopSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,shopList);
	}
	
	/**
	 * 根据ID获取shop
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getShop", method = RequestMethod.GET)
	public Result getShop(@RequestParam("id") int id) {
		Shop shop = shopService.getShop(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,shop);
	}
	
	/**
	 * 根据UserID获取shop
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getShopByUser", method = RequestMethod.GET)
	public Result getShopByUser(@RequestParam("userId") int userId) {
		Shop shop = shopService.getShopByUser(userId);
		return new Result(Result.RESULT_FLAG_SUCCESS,shop);
	}

	
	/**
	 * 更新 Shop
	 * @throws IOException 
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateShop", method = RequestMethod.POST)
	public Result updateShop(@RequestBody Shop shop,HttpSession session) {
		shop.setUpdateTime(new Date());
		if(shop.getStatus()==1){
			shop.setRegTime(new Date());
		}
		if(getCurrentUser(session)!=null){
			 shop.setUserId(getCurrentUser(session).getId());
		 }
		int effCnt =shopService.updateShop(shop);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",shop);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",shop);
		}
	}


	
	/**
	 * 删除 Shop
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/deleteShop", method = RequestMethod.GET)
	public Result deleteShop(@RequestParam("id") int id) {
		int effCnt = shopService.deleteShop(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"删除成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"删除失败！！");
		}
	}
	
	
	
	/**
	 *  更新Shop访问次数
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/addShopVisitCnt", method = RequestMethod.GET)
	public Result addShopVisitCnt(@RequestParam("id") int id) {
		int effCnt = shopService.addShopVisitCnt(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"店铺访问次数增加成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"店铺访问次数增加失败！！");
		}
	}
	
	/**
	 *  更新Shop收藏次数
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/addShopFavCnt", method = RequestMethod.GET)
	public Result addShopFavCnt(@RequestParam("id") int id,HttpSession session) {
		 User currentUser = super.getCurrentUser(session);
		 if(currentUser==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"登陆超时,请重新登陆！！");
		 }
		UserShopFav shopFav=new UserShopFav();
		shopFav.setShopId(id);
		shopFav.setUserId(currentUser.getId());
		int effCnt = userShopFavService.addShopFav(shopFav);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"店铺收藏成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"店铺收藏失败！！");
		}
	}
	
	
	/**
	 *  取消shop收藏
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/removeShopFavCnt", method = RequestMethod.GET)
	public Result removeShopFavCnt(@RequestParam("id") int id,HttpSession session) {
		 User currentUser = super.getCurrentUser(session);
		 if(currentUser==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"登陆超时,请重新登陆！！");
		 }
		UserShopFav shopFav=userShopFavService.getUserShopFav(currentUser.getId(), id);
		if(shopFav==null){
			return new Result(Result.RESULT_FLAG_FAIL,"没有收藏记录");
		}
		int effCnt = userShopFavService.removeShopFav(shopFav);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"店铺取消收藏成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"店铺取消收藏失败！！");
		}
	}
	
	/**
	 * 获取收藏信息
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getUserShopFav", method = RequestMethod.GET)
	public Result getUserShopFav(@RequestParam("userId") int userId,@RequestParam("shopId") int shopId) {
		UserShopFav shopFav = userShopFavService.getUserShopFav(userId,shopId);
		return new Result(Result.RESULT_FLAG_SUCCESS,shopFav);
	}
	
}
