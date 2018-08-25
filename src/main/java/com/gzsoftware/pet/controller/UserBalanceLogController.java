package com.gzsoftware.pet.controller;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzsoftware.pet.entity.po.User;
import com.gzsoftware.pet.entity.po.UserBalanceLog;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.UserBalanceLogService;
import com.gzsoftware.pet.service.UserService;
import com.gzsoftware.pet.utils.CommonUtil;
import com.gzsoftware.pet.utils.MapBeanUtil;

@Controller
@RequestMapping("userBalanceLog")
public class UserBalanceLogController extends BaseController{

	private static Log log = LogFactory.getLog(UserBalanceLogController.class);
	
	@Resource
	private UserBalanceLogService userBalanceLogService;
	@Resource
	private UserService userService;
	
	
	/**
	 * 获取所有userBalanceLog
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getUserBalanceLogList", method = RequestMethod.POST)
	public  Result getUserBalanceLogList(@RequestBody DataTablesRequest dtRequest)  {
		List<UserBalanceLog> userBalanceLogList = userBalanceLogService.getUserBalanceLogList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,userBalanceLogList,userBalanceLogService.countAll(dtRequest),userBalanceLogService.countAll(dtRequest));
	}
	
	
	/**
	 * 根据ID获取userBalanceLog
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getUserBalanceLog", method = RequestMethod.GET)
	public Result getUserBalanceLog(@RequestParam("id") int id) {
		UserBalanceLog userBalanceLog = userBalanceLogService.getUserBalanceLog(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,userBalanceLog);
	}

	
	/**
	 * @param 后台添加用户消费记录
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addUserBalanceLog", method = RequestMethod.POST)
	public Result addUserBalanceLog(@RequestBody UserBalanceLog userBalanceLog) {
		String userName = userBalanceLog.getUser().getUserName();
		if(!userName.isEmpty()){
		    User user = this.userService.getUserByUserName(userName);
		    if(user!=null){
		    	userBalanceLog.setUserId(user.getId());
		    	int effCnt = userBalanceLogService.addUserBalanceLog(userBalanceLog);
				if(effCnt > 0){
					return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",userBalanceLog);
				}else{
					return new Result(Result.RESULT_FLAG_FAIL,"添加失败",userBalanceLog);
				}
		    }else{
				return new Result(Result.RESULT_FLAG_FAIL,"请选择正确的会员",null);
			}
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"请选择正确的会员名称",null);
		}
	
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveUserBalanceLog", method = RequestMethod.POST)
	public Result saveUserBalanceLog(@RequestBody UserBalanceLog userBalanceLog,HttpServletRequest request) {
		 User currentUser = super.getCurrentUser(request.getSession());
		 if(currentUser==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		 userBalanceLog.setUserId(currentUser.getId());
		 userBalanceLog.setChangeTime(new Date());
		 
		 if((userBalanceLog.getDescription()==null)||userBalanceLog.getDescription().toString().equals("")) {
			 if(userBalanceLog.getChangeType()==1) {
				 userBalanceLog.setDescription("查看某些瓶库信息做成的消费 ");
			 } 
			 else if(userBalanceLog.getChangeType()==2) {
				 userBalanceLog.setDescription("线下提现");
			 }
			 else if(userBalanceLog.getChangeType()==6) {
				 userBalanceLog.setDescription("微信充值");
			 }
			 else if (userBalanceLog.getChangeType()==7) {
				 userBalanceLog.setDescription("支付宝充值");
			 }
			 else if (userBalanceLog.getChangeType()==8) {
				 userBalanceLog.setDescription("线下充值");
			 }
		 }
		
		 
		int effCnt = userBalanceLogService.addUserBalanceLog(userBalanceLog);
			if(effCnt > 0){
					return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",userBalanceLog);
				}else{
					return new Result(Result.RESULT_FLAG_FAIL,"添加失败",userBalanceLog);
				}
	}
	
	/**
	 * 更新 userBalanceLog
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateUserBalanceLog", method = RequestMethod.POST)
	public Result updateUserBalanceLog(@RequestBody UserBalanceLog userBalanceLog) {
		int effCnt = userBalanceLogService.updateUserBalanceLog(userBalanceLog);
		if(effCnt > 0){
			this.userBalanceLogService.updateUserBalanceTotal(userBalanceLog.getUser().getId());
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",userBalanceLog);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",userBalanceLog);
		}
	}
	
	
	/**
	 * 删除 userBalanceLog
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteUserBalanceLog", method = RequestMethod.GET)
	public Result deleteUserBalanceLog(@RequestParam("id") int id) {
		UserBalanceLog userBalanceLog = userBalanceLogService.getUserBalanceLog(id);
		int effCnt = userBalanceLogService.deleteUserBalanceLog(id);
		if(effCnt > 0){
			this.userBalanceLogService.updateUserBalanceTotal(userBalanceLog.getUserId());
			return new Result(Result.RESULT_FLAG_SUCCESS,"删除成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"删除失败！！");
		}
	}
	
	
	/**更新用户的余额
	 * @param id
	 */
	public void updateUserBalanceTotal(@RequestParam("id") int id){
		double balanceTotal =  userBalanceLogService.getUserBalanceTotal(id);
		int effCnt = userService.updateUserBalanceTotal(id, balanceTotal); 
	}
	
	/***
	 * 查看用户是否付费查看过 1表示未看过
	 * @param prodId
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getLog", method = RequestMethod.GET)
	public Result getLog(@RequestParam("prodId") int prodId,HttpSession session){
		User currentUser=super.getCurrentUser(session);
		if(currentUser==null){
			return new Result(Result.RESULT_FLAG_FAIL,"用户未登录，不能使用本操作");
		}
		Integer countLogCnt=userBalanceLogService.countLogCnt(currentUser.getId(), prodId);
		if(countLogCnt>0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"付费看过");
		}
		return new Result(Result.RESULT_FLAG_FAIL,"未查看过");
	}
	
	
	/**
	 * 获取所有用户的充值数
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllUserBalanceTotal", method = RequestMethod.GET)
	public Result getAllUserBalanceTotal(){
		double allUserBalanceTotal = this.userBalanceLogService.getAllUserBalanceTotal();
		return new Result(Result.RESULT_FLAG_SUCCESS,allUserBalanceTotal);
	}
	
	/**
	 * 通过订单号码检查是否充值成功,1为该表含有这个chargeOrderNumber的记录说明充值成功
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkChargeOrderNumber", method = RequestMethod.GET)
	public Result checkChargeOrderNumber(@RequestParam("chargeOrderNumber") String chargeOrderNumber,HttpSession session){
		 User currentUser = super.getCurrentUser(session);
		 if(currentUser==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		    Integer effCnt =  this.userBalanceLogService.checkChargeOrderNumber(chargeOrderNumber);
			if(effCnt>0){
				return new Result(Result.RESULT_FLAG_SUCCESS,"充值成功！！");
			}
			return new Result(Result.RESULT_FLAG_FAIL,"支付失败或您还未扫描支付！！！");
		
	}
	
}
