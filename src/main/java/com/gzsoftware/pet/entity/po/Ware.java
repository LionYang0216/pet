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
 * 商品信息表(WARE)
 * 
 * @author bianj
 * @version 1.0.0 2017-10-14
 */
public class Ware extends BaseEntity {
    /** 版本号 */
    private static final long serialVersionUID = -2911462600941629212L;

    /**  */
    private Integer id;

    /** 管理员ID （管理员维护产品） */
    private Integer shopId;

    /** 硬件图片 */
    private Integer picFileId;

    /** 类型名称 */
    private String nameCn;

    /**  */
    private String nameEn;

    /**  */
    private String descriptionEn;

    /** 描述 */
    private String descriptionCn;



    /** 价格 */
    private Integer price;
    /** 产地 */
    private String locationCn;
    
    private String locationEn;

    /** 运费说明 */
    private String expressCn;
    private String expressEn;

    /** 售后 */
    private String afterServiceCn;
    private String afterServiceEn;

    /** 适用型号 */
    private String adaptMachineCn;
    private String adaptMachineEn;

    /** 生产能力 */
    private String produceAbilityCn;
    private String produceAbilityEn;

    /** 品牌 */
    private String brandCn;
    private String brandEn;

    /** 型号 */
    private String modalCn;
    private String modalEn;

    /** 流道 */
    private String runnerCn;
    private String runnerEn;

    /** 产品 */
    private String produceProductCn;
    private String produceProductEn;

    /** 塑造模式 */
    private String makeModeCn;
    private String makeModeEn;

    /** 生产材料 */
    private String produceSourceCn;
    private String produceSourceEn;

    /** 更新时间 */
    private Date lastUpdateTime;

    /** 排序 */
    private Integer priority;

    /** 是否上架 */
    private Integer isEnable;

    /** 是否新产品 */
    private Integer isNew;

    /** 访问数量： 前台点击+1 */
    private Integer visitCnt;

    /** 点赞数量：点赞+1 */
    private Integer upCnt;

    /** 收藏数量: 没收藏多1人 +1 */
    private Integer favCnt;
    
    private Integer lastUpdateUserId; 

    /**  */
    private Integer wareTypeSrcId;

    /**  */
    private Integer wareTypeMchId;

    /**  */
    private Integer wareTypeSteId;
    private Integer wareTypePrdId;
    
    private UploadFile picFile;
    
    private WareTypeMch wareTypeMch;
    private WareTypeSrc wareTypeSrc;
    private WareTypeSte wareTypeSte;
    private WareTypePrd wareTypePrd;
   
    private Shop shop;
    
    private User user;

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
     * 获取管理员ID （管理员维护产品）
     * 
     * @return 管理员ID （管理员维护产品）
     */
    public Integer getShopId() {
        return this.shopId;
    }

    /**
     * 设置管理员ID （管理员维护产品）
     * 
     * @param shopId
     *          管理员ID （管理员维护产品）
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取硬件图片
     * 
     * @return 硬件图片
     */
    public Integer getPicFileId() {
        return this.picFileId;
    }

    /**
     * 设置硬件图片
     * 
     * @param picFileId
     *          硬件图片
     */
    public void setPicFileId(Integer picFileId) {
        this.picFileId = picFileId;
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
     * 获取
     * 
     * @return 
     */
    public String getDescriptionEn() {
        return this.descriptionEn;
    }

    /**
     * 设置
     * 
     * @param descriptionEn
     */
    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    /**
     * 获取描述
     * 
     * @return 描述
     */
    public String getDescriptionCn() {
        return this.descriptionCn;
    }

    /**
     * 设置描述
     * 
     * @param descriptionCn
     *          描述
     */
    public void setDescriptionCn(String descriptionCn) {
        this.descriptionCn = descriptionCn;
    }

   
    /**
     * 获取价格
     * 
     * @return 价格
     */
    public Integer getPrice() {
        return this.price;
    }

    /**
     * 设置价格
     * 
     * @param price
     *          价格
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLocationCn() {
		return locationCn;
	}

	public void setLocationCn(String locationCn) {
		this.locationCn = locationCn;
	}

	public String getLocationEn() {
		return locationEn;
	}

	public void setLocationEn(String locationEn) {
		this.locationEn = locationEn;
	}

	public String getExpressCn() {
		return expressCn;
	}

	public void setExpressCn(String expressCn) {
		this.expressCn = expressCn;
	}

	public String getExpressEn() {
		return expressEn;
	}

	public void setExpressEn(String expressEn) {
		this.expressEn = expressEn;
	}

	public String getAfterServiceCn() {
		return afterServiceCn;
	}

	public void setAfterServiceCn(String afterServiceCn) {
		this.afterServiceCn = afterServiceCn;
	}

	public String getAfterServiceEn() {
		return afterServiceEn;
	}

	public void setAfterServiceEn(String afterServiceEn) {
		this.afterServiceEn = afterServiceEn;
	}

	public String getAdaptMachineCn() {
		return adaptMachineCn;
	}

	public void setAdaptMachineCn(String adaptMachineCn) {
		this.adaptMachineCn = adaptMachineCn;
	}

	public String getAdaptMachineEn() {
		return adaptMachineEn;
	}

	public void setAdaptMachineEn(String adaptMachineEn) {
		this.adaptMachineEn = adaptMachineEn;
	}

	public String getProduceAbilityCn() {
		return produceAbilityCn;
	}

	public void setProduceAbilityCn(String produceAbilityCn) {
		this.produceAbilityCn = produceAbilityCn;
	}

	

	public String getProduceAbilityEn() {
		return produceAbilityEn;
	}

	public void setProduceAbilityEn(String produceAbilityEn) {
		this.produceAbilityEn = produceAbilityEn;
	}

	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	public String getBrandEn() {
		return brandEn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	public String getModalCn() {
		return modalCn;
	}

	public void setModalCn(String modalCn) {
		this.modalCn = modalCn;
	}

	public String getModalEn() {
		return modalEn;
	}

	public void setModalEn(String modalEn) {
		this.modalEn = modalEn;
	}

	public String getRunnerCn() {
		return runnerCn;
	}

	public void setRunnerCn(String runnerCn) {
		this.runnerCn = runnerCn;
	}

	public String getRunnerEn() {
		return runnerEn;
	}

	public void setRunnerEn(String runnerEn) {
		this.runnerEn = runnerEn;
	}

	

	public String getProduceProductCn() {
		return produceProductCn;
	}

	public void setProduceProductCn(String produceProductCn) {
		this.produceProductCn = produceProductCn;
	}

	public String getProduceProductEn() {
		return produceProductEn;
	}

	public void setProduceProductEn(String produceProductEn) {
		this.produceProductEn = produceProductEn;
	}

	public String getMakeModeCn() {
		return makeModeCn;
	}

	public void setMakeModeCn(String makeModeCn) {
		this.makeModeCn = makeModeCn;
	}

	public String getMakeModeEn() {
		return makeModeEn;
	}

	public void setMakeModeEn(String makeModeEn) {
		this.makeModeEn = makeModeEn;
	}

	public String getProduceSourceCn() {
		return produceSourceCn;
	}

	public void setProduceSourceCn(String produceSourceCn) {
		this.produceSourceCn = produceSourceCn;
	}

	public String getProduceSourceEn() {
		return produceSourceEn;
	}

	public void setProduceSourceEn(String produceSourceEn) {
		this.produceSourceEn = produceSourceEn;
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
     * 获取是否上架
     * 
     * @return 是否上架
     */
    public Integer getIsEnable() {
        return this.isEnable;
    }

    /**
     * 设置是否上架
     * 
     * @param isEnable
     *          是否上架
     */
    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    /**
     * 获取是否新产品
     * 
     * @return 是否新产品
     */
    public Integer getIsNew() {
        return this.isNew;
    }

    /**
     * 设置是否新产品
     * 
     * @param isNew
     *          是否新产品
     */
    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    /**
     * 获取访问数量： 前台点击+1
     * 
     * @return 访问数量
     */
    public Integer getVisitCnt() {
        return this.visitCnt;
    }

    /**
     * 设置访问数量： 前台点击+1
     * 
     * @param visitCnt
     *          访问数量
     */
    public void setVisitCnt(Integer visitCnt) {
        this.visitCnt = visitCnt;
    }

    /**
     * 获取点赞数量：点赞+1
     * 
     * @return 点赞数量
     */
    public Integer getUpCnt() {
        return this.upCnt;
    }

    /**
     * 设置点赞数量：点赞+1
     * 
     * @param upCnt
     *          点赞数量
     */
    public void setUpCnt(Integer upCnt) {
        this.upCnt = upCnt;
    }

    /**
     * 获取收藏数量: 没收藏多1人 +1
     * 
     * @return 收藏数量
     */
    public Integer getFavCnt() {
        return this.favCnt;
    }

    /**
     * 设置收藏数量: 没收藏多1人 +1
     * 
     * @param favCnt
     *          收藏数量
     */
    public void setFavCnt(Integer favCnt) {
        this.favCnt = favCnt;
    }

    /**
     * 获取
     * 
     * @return 
     */
    public Integer getWareTypeSrcId() {
        return this.wareTypeSrcId;
    }

    /**
     * 设置
     * 
     * @param wareTypeSrcId
     */
    public void setWareTypeSrcId(Integer wareTypeSrcId) {
        this.wareTypeSrcId = wareTypeSrcId;
    }

    /**
     * 获取
     * 
     * @return 
     */
    public Integer getWareTypeMchId() {
        return this.wareTypeMchId;
    }

    /**
     * 设置
     * 
     * @param wareTypeMchId
     */
    public void setWareTypeMchId(Integer wareTypeMchId) {
        this.wareTypeMchId = wareTypeMchId;
    }

    /**
     * 获取
     * 
     * @return 
     */
    public Integer getWareTypeSteId() {
        return this.wareTypeSteId;
    }

    /**
     * 设置
     * 
     * @param wareTypeSteId
     */
    public void setWareTypeSteId(Integer wareTypeSteId) {
        this.wareTypeSteId = wareTypeSteId;
    }

	public UploadFile getPicFile() {
		return picFile;
	}

	public void setPicFile(UploadFile picFile) {
		this.picFile = picFile;
	}

	public WareTypeMch getWareTypeMch() {
		return wareTypeMch;
	}

	public void setWareTypeMch(WareTypeMch wareTypeMch) {
		this.wareTypeMch = wareTypeMch;
	}

	public WareTypeSrc getWareTypeSrc() {
		return wareTypeSrc;
	}

	public void setWareTypeSrc(WareTypeSrc wareTypeSrc) {
		this.wareTypeSrc = wareTypeSrc;
	}

	public WareTypeSte getWareTypeSte() {
		return wareTypeSte;
	}

	public void setWareTypeSte(WareTypeSte wareTypeSte) {
		this.wareTypeSte = wareTypeSte;
	}
	

	public Integer getWareTypePrdId() {
		return wareTypePrdId;
	}

	public void setWareTypePrdId(Integer wareTypePrdId) {
		this.wareTypePrdId = wareTypePrdId;
	}

	public WareTypePrd getWareTypePrd() {
		return wareTypePrd;
	}

	public void setWareTypePrd(WareTypePrd wareTypePrd) {
		this.wareTypePrd = wareTypePrd;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Integer getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(Integer lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    
}