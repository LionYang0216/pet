package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.UserBalanceLogDao;
import com.gzsoftware.pet.entity.po.UserBalanceLog;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;


@Service("userBalanceLogService")
public class UserBalanceLogService extends BaseService{

	private static Log log = LogFactory.getLog(UserBalanceLogService.class);
	@Resource
	private UserBalanceLogService userBalanceLogService;
	@Resource
	private UserBalanceLogDao userBalanceLogDao;
	
	@Resource
	private UserService userService;

	public List<UserBalanceLog> getUserBalanceLogList(DataTablesRequest dtRequest) {
		return userBalanceLogDao.getUserBalanceLogList(dtRequest);
	}
	

	public Integer countAll(DataTablesRequest dtRequest) {
		return userBalanceLogDao.countAll(dtRequest);
	}

	public int addUserBalanceLog(UserBalanceLog userBalanceLog) {
		int effCnt = userBalanceLogDao.addUserBalanceLog(userBalanceLog);
		if(effCnt>0){
			this.userBalanceLogService.updateUserBalanceTotal(userBalanceLog.getUserId());
		}
		return effCnt ;
	}

	public int deleteUserBalanceLog(int id) {
		return userBalanceLogDao.deleteUserBalanceLog(id);
	}


	public UserBalanceLog getUserBalanceLog(int id) {
		return userBalanceLogDao.getUserBalanceLog(id);
	}


	public int updateUserBalanceLog(UserBalanceLog userBalanceLog) {
		// TODO Auto-generated method stub
		return userBalanceLogDao.updateUserBalanceLog(userBalanceLog);
	}


	public double getUserBalanceTotal(int id) {
		return userBalanceLogDao.getUserBalanceTotal(id);
	}
	public Integer countLogCnt(Integer userId,Integer prodId){
		return userBalanceLogDao.countLogCnt(userId, prodId);
	}


	public double getAllUserBalanceTotal() {
		// TODO Auto-generated method stub
		return userBalanceLogDao.getAllUserBalanceTotal();
	}
	
	public int updateUserBalanceTotal(int userId){
		double balanceTotal = this.getUserBalanceTotal(userId);
		int effCnt = userService.updateUserBalanceTotal(userId, balanceTotal);
		return effCnt; 
	}


	public Integer checkChargeOrderNumber(String chargeOrderNumber) {
		// TODO Auto-generated method stub
		return userBalanceLogDao.checkChargeOrderNumber(chargeOrderNumber);
	}
	
}
