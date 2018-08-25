package com.gzsoftware.pet.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.UserDao;
import com.gzsoftware.pet.entity.po.Prod;
import com.gzsoftware.pet.entity.po.User;
import com.gzsoftware.pet.entity.po.UserBalanceLog;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;


@Service("userService")
public class UserService extends BaseService{

	private static Log log = LogFactory.getLog(UserService.class);
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private UserBalanceLogService userBalanceLogService;

	public User login(User user) {
		return userDao.login(user);
	}

	public void updateUserLoginInfo(User user) {
		userDao.updateUserLoginInfo(user);
	}

	public User getUser(int id) {
		return userDao.getUser(id);
	}

	public List<User> getUserList(DataTablesRequest dtRequest) {
		return userDao.getUserList(dtRequest);
	}
	

	public Integer countAll(DataTablesRequest dtRequest) {
		return userDao.countAll(dtRequest);
	}
	
	
	

	public int addUser(User user) {
		return userDao.addUser(user);
	}

	public int deleteUser(int id) {
		return userDao.deleteUser(id);
	}

	public int updateUser(User user) {
	   return userDao.updateUser(user);
	}

	public User getUserByAccount(String account) {
		return userDao.getUserByAccount(account);
	}

	public List<User> getUserListByUserName(String skeyword) {
		// TODO Auto-generated method stub
		return userDao.getUserListByUserName(skeyword);
	}

	public List<User> getUserListForSelect() {
		// TODO Auto-generated method stub
		return userDao.getUserListForSelect();
	}

	public int updateUserBalanceTotal(int id, double balanceTotal) {
		
		return userDao.updateUserBalanceTotal(id,balanceTotal);
	}

	public User getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

	public User setChargeViewProd(User user,Prod prod){
		Integer amount=prod.getProdCost().getCost();
//		Double balanceTotal=user.getBalanceTotal()-amount;
//		user.setBalanceTotal(balanceTotal);
//		userDao.updateUserBalance(user);
		UserBalanceLog log=new UserBalanceLog();
		log.setProdId(prod.getId());
		log.setUserId(user.getId());
		log.setChangeAmount(-amount);
		log.setChangeType((short)1);
		log.setDescription("查看"+prod.getNameCn()+"扣费"+amount+"元成功");
		log.setChangeTime(new Date());
		userBalanceLogService.addUserBalanceLog(log);
		double balanceTotal = userBalanceLogService.getUserBalanceTotal(user.getId()); 
		this.updateUserBalanceTotal(user.getId(), balanceTotal);
		user = this.getUser(user.getId()); //reload
		return user;
		
	}

}
