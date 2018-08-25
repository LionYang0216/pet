package com.gzsoftware.pet.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzsoftware.pet.entity.po.Prod;
import com.gzsoftware.pet.entity.po.Shop;
import com.gzsoftware.pet.entity.po.UserDraw;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.ProdService;
import com.gzsoftware.pet.service.ShopService;
import com.gzsoftware.pet.service.UserBalanceLogService;
import com.gzsoftware.pet.service.UserDrawService;
import com.gzsoftware.pet.service.UserService;
import com.gzsoftware.pet.service.WareService;

@Controller
@RequestMapping("dashboard")
public class DashBoardController {
	@Resource
	private ProdService prodService;
	@Resource
	private WareService wareService;
	@Resource
	private UserService userService;
	@Resource
	private ShopService shopService;
	@Resource
	private UserBalanceLogService userBalanceLogService;
	@Resource
	private UserDrawService userDrawService;

	@ResponseBody
	@RequestMapping(value = "/getDashBoardCnt", method = RequestMethod.GET)
	public Result getDashBoardCnt() {
		DataTablesRequest dtRequest = new DataTablesRequest();
		Integer prodCnt = prodService.countAll(dtRequest);
		Integer wareCnt = wareService.countAll(dtRequest);
		Integer userCnt = userService.countAll(dtRequest);
		double balance = userBalanceLogService.getAllUserBalanceTotal();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prodCnt", prodCnt);
		map.put("wareCnt", wareCnt);
		map.put("userCnt", userCnt);
		map.put("balance", balance);
		return new Result(Result.RESULT_FLAG_SUCCESS, map);
	}

	@ResponseBody
	@RequestMapping(value = "/getShopApplyList", method = RequestMethod.GET)
	public Result getShopApplyList(@RequestParam("length") Integer length) {
		if (length == null) {
			length = 8;
		}
		List<Shop> shopList = shopService.getShopApplyList(length);
		return new Result(Result.RESULT_FLAG_SUCCESS, shopList);
	}

	@ResponseBody
	@RequestMapping(value = "/getUserDrawApplyList", method = RequestMethod.GET)
	public Result getUserDrawApplyList(@RequestParam("length") Integer length) {
		if (length == null) {
			length = 8;
		}
		List<UserDraw> userDrawList = userDrawService.getUserDrawApplyList(length);
		return new Result(Result.RESULT_FLAG_SUCCESS, userDrawList);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getProdConList", method = RequestMethod.GET)
	public Result getProdConList(@RequestParam("length") Integer length) {
		if (length == null) {
			length = 8;
		}
		List<Prod> prodConList = prodService.getProdConList(length);
		return new Result(Result.RESULT_FLAG_SUCCESS, prodConList);
	}
}
