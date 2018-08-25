package com.gzsoftware.pet.entity.po;

import java.util.Date;

public class UserBalanceLog extends BaseEntity{

	private Integer id;
	private Integer userId;
	private double changeAmount;
	private  Short changeType;
	private Date changeTime;
	private Integer prodId;
	private String chargeOrderNumber;
	private String description;
	private User user;
	private Prod prod;
	
	
	
	public Prod getProd() {
		return prod;
	}
	public void setProd(Prod prod) {
		this.prod = prod;
	}
	private Integer effId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public double getChangeAmount() {
		return changeAmount;
	}
	public void setChangeAmount(double changeAmount) {
		this.changeAmount = changeAmount;
	}
	public Short getChangeType() {
		return changeType;
	}
	public void setChangeType(Short changeType) {
		this.changeType = changeType;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public Integer getEffId() {
		return effId;
	}
	public void setEffId(Integer effId) {
		this.effId = effId;
	}
	public Integer getProdId() {
		return prodId;
	}
	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}
	
	public String getChargeOrderNumber() {
		return chargeOrderNumber;
	}
	public void setChargeOrderNumber(String chargeOrderNumber) {
		this.chargeOrderNumber = chargeOrderNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
