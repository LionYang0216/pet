/*
 * Welcome to use the TableGo Tools.
 * 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author:bianj
 * Email:edinsker@163.com
 * Version:5.0.0
 */

package com.gzsoftware.pet.entity.po;

import java.util.Date;

/**
 * 产品地区类型表：亚洲，非洲，大洋洲，欧洲等(PROD_TYPE_ARE)
 * 
 * @author bianj
 * @version 1.0.0 2017-09-17
 */
public class ProdTypeAre extends BaseEntity {
	/**  */
	private Integer id;

	/** 类型名称 */
	private String nameCn;

	/**  */
	private String nameEn;
	
    private Integer lastUpdateAdminId;

	/** 更新时间 */
	private Date lastUpdateTime;

	/** 排序 */
	private Integer priority;

	/**  */
	private String isEnable;
	
	private Admin lastUpdateAdmin;

	public Admin getLastUpdateAdmin() {
		return lastUpdateAdmin;
	}

	public void setLastUpdateAdmin(Admin lastUpdateAdmin) {
		this.lastUpdateAdmin = lastUpdateAdmin;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * 设置
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取类型名称
	 * 
	 * @return 类型名称
	 */
	public String getNameCn() {
		return this.nameCn;
	}

	/**
	 * 设置类型名称
	 * 
	 * @param nameCn
	 *            类型名称
	 */
	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public String getNameEn() {
		return this.nameEn;
	}

	/**
	 * 设置
	 * 
	 * @param nameEn
	 */
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	/**
	 * 获取更新时间
	 * 
	 * @return 更新时间
	 */
	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	/**
	 * 设置更新时间
	 * 
	 * @param lastUpdateTime
	 *            更新时间
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * 获取排序
	 * 
	 * @return 排序
	 */
	public Integer getPriority() {
		return this.priority;
	}

	/**
	 * 设置排序
	 * 
	 * @param priority
	 *            排序
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public String getIsEnable() {
		return this.isEnable;
	}

	/**
	 * 设置
	 * 
	 * @param isEnable
	 */
	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public Integer getLastUpdateAdminId() {
		return lastUpdateAdminId;
	}

	public void setLastUpdateAdminId(Integer lastUpdateAdminId) {
		this.lastUpdateAdminId = lastUpdateAdminId;
	}
	
}