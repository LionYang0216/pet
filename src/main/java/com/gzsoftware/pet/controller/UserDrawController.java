package com.gzsoftware.pet.controller;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzsoftware.pet.entity.po.Admin;
import com.gzsoftware.pet.entity.po.UserDraw;
import com.gzsoftware.pet.entity.po.UploadFile;
import com.gzsoftware.pet.entity.po.User;
import com.gzsoftware.pet.entity.po.UserBalanceLog;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.UserDrawService;
import com.gzsoftware.pet.service.UploadFileService;
import com.gzsoftware.pet.service.UserBalanceLogService;
import com.gzsoftware.pet.utils.CommonUtil;

@Controller
@RequestMapping("userDraw")
public class UserDrawController extends BaseController{

	private static Log log = LogFactory.getLog(UserDrawController.class);
	
	@Resource
	private UserDrawService userDrawService;
	@Resource
	private UserBalanceLogService userBalanceLogService;
	
	
	/**
	 * 前台提交提现申请
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addUserDraw", method = RequestMethod.POST)
	public Result addUserDraw(@RequestBody UserDraw userDraw,HttpServletRequest request) {
		 User currentUser = super.getCurrentUser(request.getSession());
		 if(currentUser==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		 userDraw.setUserId(currentUser.getId());
		 userDraw.setUserRequestTime(new Date());
		 userDraw.setStauts(0);
		 int effCnt = userDrawService.addUserDraw(userDraw);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"申请成功",userDraw);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"申请失败",userDraw);
		}
		
	
	}

	/**
	 * 获取所有UserDraws
	 * @param dtRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserDrawList", method = RequestMethod.POST)
	public  Result getUserDrawList(@RequestBody DataTablesRequest dtRequest)  {
		List<UserDraw> userDrawList = userDrawService.getUserDrawList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,userDrawList,userDrawService.countAll(dtRequest),userDrawService.countAll(dtRequest));
	}
	
	
	/**
	 * 根据ID获取UserDraw
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getUserDraw", method = RequestMethod.GET)
	public Result getUserDraw(@RequestParam("id") int id) {
		UserDraw userDraw = userDrawService.getUserDraw(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,userDraw);
	}

	
	/**
	 * 后台管理员审批申请
	 * @throws IOException 
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateUserDraw", method = RequestMethod.POST)
	public Result updateUserDraw(@RequestBody UserDraw userDraw,HttpServletRequest request) {
		 Admin currentAdmin = super.getCurrentAdmin(request.getSession());
		 if(currentAdmin==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		 String remark = userDraw.getAdminRemark();
		 userDraw = this.userDrawService.getUserDraw(userDraw.getId());
		 double BalanceTotal =  userDraw.getUser().getBalanceTotal();
		 if(userDraw.getDrawAmount()> BalanceTotal ){
			 return new Result(Result.RESULT_FLAG_FAIL,"申请处理失败,提现金额大于财富金额！");
		 }
		 userDraw.setStauts(1);
		 userDraw.setLastUpdateTime(new Date());
		 userDraw.setLastUpdateAdminId(currentAdmin.getId());
		 userDraw.setAdminRemark(remark);
		int effCnt =userDrawService.updateUserDraw(userDraw);
		if(effCnt > 0){
			UserBalanceLog log = new UserBalanceLog();
			log.setUserId(userDraw.getUserId());
			log.setChangeAmount(-userDraw.getDrawAmount());
			log.setChangeTime(new Date());
			log.setChangeType((short) 2);//线下提现
			log.setDescription(userDraw.getUserRemark());
			userBalanceLogService.addUserBalanceLog(log);
			userBalanceLogService.updateUserBalanceTotal(userDraw.getUserId());
			return new Result(Result.RESULT_FLAG_SUCCESS,"申请处理成功",userDraw);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"申请处理失败",userDraw);
		}
	}


	
	/**
	 * 拒绝申请
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/noPassUserDraw", method = RequestMethod.POST)
	public Result noPassUserDraw(@RequestBody UserDraw userDraw,HttpServletRequest request) {
		 Admin currentAdmin = super.getCurrentAdmin(request.getSession());
		 if(currentAdmin==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		  }
		 //UserDraw draw = this.userDrawService.getUserDraw(userDraw.getId());
		 userDraw.setStauts(2);
		 userDraw.setLastUpdateTime(new Date());
		 userDraw.setLastUpdateAdminId(currentAdmin.getId());
		 userDraw.setAdminRemark(userDraw.getAdminRemark());
		int effCnt =userDrawService.updateUserDraw(userDraw);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"申请拒绝成功",userDraw);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"申请拒绝失败！！");
		}
	}
	
	
	
	
	
}
