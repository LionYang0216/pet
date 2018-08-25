package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.UserDraw;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface UserDrawDao {

	public UserDraw getUserDraw(int id);

	public List<UserDraw> getUserDrawList(DataTablesRequest dtRequest);

	public Integer countAll(DataTablesRequest dtRequest);

	public int addUserDraw(UserDraw userDraw);

	public int deleteUserDraw(int id);
	
	
	public int updateUserDraw (UserDraw userDraw);
	
	public List<UserDraw> getUserDrawApplyList(Integer length);

}