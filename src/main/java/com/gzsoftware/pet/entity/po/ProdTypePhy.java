package com.gzsoftware.pet.entity.po;

import java.util.Date;

public class ProdTypePhy extends BaseEntity {
    private Integer id;

    private String nameCn;

    private String nameEn;

    private Integer lastUpdateAdminId;

    private Date lastUpdateTime;

    private Integer priority;

    private String isEnable;
    
    private Admin lastUpdateAdmin;

    public Admin getLastUpdateAdmin() {
		return lastUpdateAdmin;
	}

	public void setLastUpdateAdmin(Admin lastUpdateAdmin) {
		this.lastUpdateAdmin = lastUpdateAdmin;
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
        this.nameCn = nameCn == null ? null : nameCn.trim();
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
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

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

   
}