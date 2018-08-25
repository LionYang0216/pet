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
 * 商家表(SHOP)
 * 
 * @author bianj
 * @version 1.0.0 2017-10-14
 */
public class Shop extends BaseEntity {
    /** 版本号 */
    private static final long serialVersionUID = 877148826162168642L;
    
    public int SHOP_STATUS_REQUEST = 0 ; // 申请中
    public int SHOP_STATUS_APPROVED = 1; // 已批核
    public int SHOP_STATUS_REJECT = 2; // 禁用或拒绝

    /** ID */
    private Integer id;

    /** 归属用户ID */
    private Integer userId;

    /** 商铺名字 */
    private String nameCn;

    /**  */
    private String nameEn;

    /**  */
    private Integer logoFileId;

    /** 商家等级 */
    private Integer level;

    /** 审批状态： 0为未审批（申请状态）  1为已审批可开通  2为下架禁用该商家 */
    private Integer status;

    /** 商家地址 */
    private String location;

    /** 经营模式 */
    private String operMode;
    /**拒绝原因*/
    private String rejectReason;
    
    private Integer favCnt;
    
    private Integer visitCnt;

    /** 联系电话 */
    private String tel;
    
    /** 公司网站 */
    private String url;



	/**  */
    private Integer regFileId;

    /** 登记时间 */
    private Date regTime;

    /** 商铺企业介绍 */
    private String introEnt;

    /** 物流发货介绍 */
    private String introLog;

    /** 售后服务介绍 */
    private String introSrv;

    /** 更新时间 */
    private Date updateTime;
    
    private UploadFile regFile;
    
    private UploadFile logoFile;
    
    private User user;

    public UploadFile getRegFile() {
		return regFile;
	}

	public void setRegFile(UploadFile regFile) {
		this.regFile = regFile;
	}

	public UploadFile getLogoFile() {
		return logoFile;
	}

	public void setLogoFile(UploadFile logoFile) {
		this.logoFile = logoFile;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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
     * 获取归属用户ID
     * 
     * @return 归属用户ID
     */
    public Integer getUserId() {
        return this.userId;
    }

    /**
     * 设置归属用户ID
     * 
     * @param userId
     *          归属用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取商铺名字
     * 
     * @return 商铺名字
     */
    public String getNameCn() {
        return this.nameCn;
    }

    /**
     * 设置商铺名字
     * 
     * @param nameCn
     *          商铺名字
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
     * 获取
     * 
     * @return 
     */
    public Integer getLogoFileId() {
        return this.logoFileId;
    }

    /**
     * 设置
     * 
     * @param logoFileId
     */
    public void setLogoFileId(Integer logoFileId) {
        this.logoFileId = logoFileId;
    }

    /**
     * 获取商家等级
     * 
     * @return 商家等级
     */
    public Integer getLevel() {
        return this.level;
    }

    /**
     * 设置商家等级
     * 
     * @param level
     *          商家等级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取审批状态： 0为未审批（申请状态）  1为已审批可开通  2为下架禁用该商家
     * 
     * @return 审批状态
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置审批状态： 0为未审批（申请状态）  1为已审批可开通  2为下架禁用该商家
     * 
     * @param status
     *          审批状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取商家地址
     * 
     * @return 商家地址
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * 设置商家地址
     * 
     * @param location
     *          商家地址
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 获取经营模式
     * 
     * @return 经营模式
     */
    public String getOperMode() {
        return this.operMode;
    }

    /**
     * 设置经营模式
     * 
     * @param operMode
     *          经营模式
     */
    public void setOperMode(String operMode) {
        this.operMode = operMode;
    }

    /**
     * 获取联系电话
     * 
     * @return 联系电话
     */
    public String getTel() {
        return this.tel;
    }

    /**
     * 设置联系电话
     * 
     * @param tel
     *          联系电话
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 获取
     * 
     * @return 
     */
    public Integer getRegFileId() {
        return this.regFileId;
    }

    /**
     * 设置
     * 
     * @param regFileId
     */
    public void setRegFileId(Integer regFileId) {
        this.regFileId = regFileId;
    }

    /**
     * 获取登记时间
     * 
     * @return 登记时间
     */
    public Date getRegTime() {
        return this.regTime;
    }

    /**
     * 设置登记时间
     * 
     * @param regTime
     *          登记时间
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    /**
     * 获取商铺企业介绍
     * 
     * @return 商铺企业介绍
     */
    public String getIntroEnt() {
        return this.introEnt;
    }

    /**
     * 设置商铺企业介绍
     * 
     * @param introEnt
     *          商铺企业介绍
     */
    public void setIntroEnt(String introEnt) {
        this.introEnt = introEnt;
    }

    /**
     * 获取物流发货介绍
     * 
     * @return 物流发货介绍
     */
    public String getIntroLog() {
        return this.introLog;
    }

    /**
     * 设置物流发货介绍
     * 
     * @param introLog
     *          物流发货介绍
     */
    public void setIntroLog(String introLog) {
        this.introLog = introLog;
    }

    /**
     * 获取售后服务介绍
     * 
     * @return 售后服务介绍
     */
    public String getIntroSrv() {
        return this.introSrv;
    }

    /**
     * 设置售后服务介绍
     * 
     * @param introSrv
     *          售后服务介绍
     */
    public void setIntroSrv(String introSrv) {
        this.introSrv = introSrv;
    }

    /**
     * 获取更新时间
     * 
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置更新时间
     * 
     * @param updateTime
     *          更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Integer getFavCnt() {
		return favCnt;
	}

	public void setFavCnt(Integer favCnt) {
		this.favCnt = favCnt;
	}

	public Integer getVisitCnt() {
		return visitCnt;
	}

	public void setVisitCnt(Integer visitCnt) {
		this.visitCnt = visitCnt;
	}
    
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
}