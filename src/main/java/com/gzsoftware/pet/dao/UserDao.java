package com.gzsoftware.pet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gzsoftware.pet.entity.po.User;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface UserDao {
	
	public User login(User user);
	
	public void updateUserLoginInfo(User user);
	
	public User getUser(int id);
	
	public List<User> getUserList(DataTablesRequest dtRequest);

	public Integer countAll(DataTablesRequest dtRequest);

    public int addUser(User user);

    public int deleteUser(int id);

    public int updateUser(User user);

	public User getUserByAccount(String account);

	public List<User> getUserListByUserName(String skeyword);

	public List<User> getUserListForSelect();

	public int updateUserBalanceTotal(@Param("id")Integer id, @Param("balanceTotal") double balanceTotal);

	public User getUserByUserName(String userName);
    
}
