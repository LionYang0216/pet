package com.gzsoftware.pet.entity.po;

import java.util.Date;

public class NewsType extends BaseEntity{
	
    private Integer id;
    
	private String nameCn;

    private String nameEn;
    
    private Integer lastUpdateAdminId;

    private Date lastUpdateTime;

    private Integer priority;
    
    private Short isEnable;
    
    private Admin admin;
    

    public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public Integer getLastUpdateAdminId() {
		return lastUpdateAdminId;
	}

	public void setLastUpdateAdminId(Integer lastUpdateAdminId) {
		this.lastUpdateAdminId = lastUpdateAdminId;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Short getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Short isEnable) {
		this.isEnable = isEnable;
	}

	

     

}