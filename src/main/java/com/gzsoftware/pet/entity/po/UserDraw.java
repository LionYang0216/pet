package com.gzsoftware.pet.entity.po;

import java.util.Date;

public class UserDraw extends BaseEntity{

	private Integer id;
	private Integer userId;
	private Integer drawAmount;
	private Integer stauts;
	private String userRemark;
	private String adminRemark;
	private Date userRequestTime;
	private Date lastUpdateTime;
	private Integer lastUpdateAdminId;
	
	private String adminStr;
	private String userStr;
	
	
	public String getUserStr() {
		if(this.user!=null){
			return user.getUserName();
		}else{
			return "";
		}
	}
	public String getAdminStr() {
		if(this.admin!=null){
			return admin.getAdminName();
		}else{
			return "";
		}
		
	}
	private Admin admin;
	
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	private User user ;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getDrawAmount() {
		return drawAmount;
	}
	public void setDrawAmount(Integer drawAmount) {
		this.drawAmount = drawAmount;
	}
	public Integer getStauts() {
		return stauts;
	}
	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}
	public String getUserRemark() {
		return userRemark;
	}
	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}
	public String getAdminRemark() {
		return adminRemark;
	}
	public void setAdminRemark(String adminRemark) {
		this.adminRemark = adminRemark;
	}
	public Date getUserRequestTime() {
		return userRequestTime;
	}
	public void setUserRequestTime(Date userRequestTime) {
		this.userRequestTime = userRequestTime;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Integer getLastUpdateAdminId() {
		return lastUpdateAdminId;
	}
	public void setLastUpdateAdminId(Integer lastUpdateAdminId) {
		this.lastUpdateAdminId = lastUpdateAdminId;
	}
	
}
