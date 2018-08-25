package com.gzsoftware.pet.entity.po;

import java.util.Date;

public class User extends BaseEntity{

	private Integer id;
	private String account;
	private String userName;
	private String password;
	private Date updateTime;
	private String email;
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private Date regTime;
	
	private Integer headFileId;
	
	private Date lastLoginTime;
	
	private String lastLoginIp;
	
	private int loginCount;
	
	private Short isEnabled;
	
    private Short sex;
	
	private String pos;
	
	private String location;
	
	private String sign;
	
	private String intro;
	
	private String tel;
	
	private String wechat;
	
	private String qq;
	
	private String labels;
	
	private double balanceTotal;
	
	private UploadFile headFile;
	
	private String sexLayout;
	
	public String getSexLayout(){
		if(this.sex!=null && this.sex == 1){
			return "男";
		}else{
			return "女";
		}
	}
	
	private String  headFilePathLayout;
	
	public String getHeadFilePathLayout() {
		if(this.getHeadFile() == null){
			return "../assets/pages/img/avatars/default.png";
		}else{
			return "../upload/" + this.headFile.getFilePath();
		}
	}
	
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public Integer getHeadFileId() {
		return headFileId;
	}
	public void setHeadFileId(Integer headFileId) {
		this.headFileId = headFileId;
	}
	public UploadFile getHeadFile() {
		return headFile;
	}
	public void setHeadFile(UploadFile headFile) {
		this.headFile = headFile;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	public Short getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Short isEnabled) {
		this.isEnabled = isEnabled;
	}
	public Short getSex() {
		return sex;
	}
	public void setSex(Short sex) {
		this.sex = sex;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	public double getBalanceTotal() {
		return balanceTotal;
	}
	public void setBalanceTotal(double balanceTotal) {
		this.balanceTotal = balanceTotal;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
