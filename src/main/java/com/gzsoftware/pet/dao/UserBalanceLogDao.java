package com.gzsoftware.pet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gzsoftware.pet.entity.po.UserBalanceLog;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface UserBalanceLogDao {
	
	public List<UserBalanceLog> getUserBalanceLogList(DataTablesRequest dtRequest);

	public Integer countAll(DataTablesRequest dtRequest);

    public int deleteUserBalanceLog(int id);

    public int addUserBalanceLog(UserBalanceLog userBalanceLog);
    
    public  UserBalanceLog getUserBalanceLog(int id);

	public int updateUserBalanceLog(UserBalanceLog userBalanceLog);

	public double getUserBalanceTotal(int id);
	
	public Integer countLogCnt(@Param("userId") Integer userId,@Param("prodId") Integer prodId);

	public double getAllUserBalanceTotal();

	public Integer checkChargeOrderNumber(String chargeOrderNumber);
}
