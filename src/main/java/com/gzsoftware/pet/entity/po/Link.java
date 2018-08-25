package com.gzsoftware.pet.entity.po;

import java.util.Date;

public class Link extends BaseEntity {

	private Integer id;

	private String nameCn;

	private String nameEn;

	private String url;

	private Integer lastUpdateAdminId;

	private Date lastUpdateTime;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

}
