package com.gzsoftware.pet.entity.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Admin extends BaseEntity {

	
	private Integer id;

	private String account;
	
	private String password;

	private String adminName;
	
	private Date updateTime;
	
	private Date lastLoginTime;
	
	private String lastLoginIp;
	
	private Integer headFileId;
	
	private UploadFile headFile;
	
	private int loginCount;
	
	private String isEnabled;

	private String[] roles;
	
	private String roleListLayout;
	
	public String getRoleListLayout() {
		String roleListLayout="";
		if((this.getRoleList()!=null)&&(!this.getRoleList().isEmpty())){
			for (Role n : this.getRoleList()){
				roleListLayout += n.getName() + ",";
			}

		}
		if(!roleListLayout.equals("")){
			roleListLayout = roleListLayout.substring(0,roleListLayout.length()-1);
		}
		return roleListLayout;
	}
	
	
	private String  headFilePathLayout;
	
	public String getHeadFilePathLayout() {
		if(this.getHeadFile() == null){
			return "../assets/pages/img/avatars/default.png";
		}else{
			return "../upload/" + this.headFile.getFilePath();
		}
	}

	private List<Role> roleList;
	
	
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public String[] getRoles() {
		return roles;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
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

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
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

	

}
