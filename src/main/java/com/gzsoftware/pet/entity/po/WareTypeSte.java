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
 * 商品步法类型表：瓶子募集(吹瓶) ， 瓶呸模具，(WARE_TYPE_STE)
 * 
 * @author bianj
 * @version 1.0.0 2017-10-14
 */
public class WareTypeSte extends BaseEntity {
    /** 版本号 */
    private static final long serialVersionUID = -2161618413629229293L;

    /** ID */
    private Integer id;

    /** 类型名称 */
    private String nameCn;

    /**  */
    private String nameEn;

    /** 更新时间 */
    private Date lastUpdateTime;

    /** 排序 */
    private Integer priority;

    /**  */
    private Integer isEnable;
    
    private Integer lastUpdateAdminId;
    
    private Admin lastUpdateAdmin;

    /**
     * 获取ID
     * 
     * @return ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置ID
     * 
     * @param id
     *          ID
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
     *          类型名称
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
     *          更新时间
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
     *          排序
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * 获取
     * 
     * @return 
     */
    public Integer getIsEnable() {
        return this.isEnable;
    }

    /**
     * 设置
     * 
     * @param isEnable
     */
    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

	public Integer getLastUpdateAdminId() {
		return lastUpdateAdminId;
	}

	public void setLastUpdateAdminId(Integer lastUpdateAdminId) {
		this.lastUpdateAdminId = lastUpdateAdminId;
	}

	public Admin getLastUpdateAdmin() {
		return lastUpdateAdmin;
	}

	public void setLastUpdateAdmin(Admin lastUpdateAdmin) {
		this.lastUpdateAdmin = lastUpdateAdmin;
	}
    
    
}