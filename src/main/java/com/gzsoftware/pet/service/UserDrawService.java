package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.controller.BackController;
import com.gzsoftware.pet.dao.UserDrawDao;
import com.gzsoftware.pet.entity.po.UserDraw;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;


@Service("userDrawService")
public class UserDrawService extends BaseService{

	private static Log log = LogFactory.getLog(UserDrawService.class);
	
	@SuppressWarnings("restriction")
	@Resource
	private UserDrawDao userDrawDao;

	public UserDraw getUserDraw(int id) {
		return userDrawDao.getUserDraw(id);
	}
	
	public int addUserDraw(UserDraw userDraw) {
		return userDrawDao.addUserDraw(userDraw);
	}

	public int deleteUserDraw(int id) {
		return userDrawDao.deleteUserDraw(id);
	}
	
	public int updateUserDraw(UserDraw UserDraw) {
	   return userDrawDao.updateUserDraw(UserDraw);
	}
	
	

	public Integer countAll(DataTablesRequest dtRequest) {
		return userDrawDao.countAll(dtRequest);
	}

	public List<UserDraw> getUserDrawList(DataTablesRequest dtRequest) {
		return userDrawDao.getUserDrawList(dtRequest);
	}
	
	public List<UserDraw> getUserDrawApplyList(Integer length){
		return userDrawDao.getUserDrawApplyList(length);
	}

}
