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

/**
 * 产品大图表：每个产品可以有1~5张大图，前台轮播(PROD_PIC)
 * 
 * @author bianj
 * @version 1.0.0 2017-09-26
 */
public class ProdPic extends BaseEntity {
    /** 版本号 */
    private static final long serialVersionUID = 6994892233607502912L;

    /**  */
    private Integer id;

    /** 产品ID */
    private Integer prodId;

    /** 图片路径：物理或网络路径 */
    private Integer picFileId;

    /** 是否置顶图片，默认为否，如果为是，则作为列表区的主要图片 */
    private Integer isMajor;

    /** 图片描述 */
    private String nameCn;

    /**  */
    private String nameEn;
    
    private UploadFile picFile;

    public UploadFile getPicFile() {
    	
		return picFile;
	}

	public void setPicFile(UploadFile picFile) {
		this.picFile = picFile;
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
     * 获取产品ID
     * 
     * @return 产品ID
     */
    public Integer getProdId() {
        return this.prodId;
    }

    /**
     * 设置产品ID
     * 
     * @param prodId
     *          产品ID
     */
    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    /**
     * 获取图片路径：物理或网络路径
     * 
     * @return 图片路径
     */
    public Integer getPicFileId() {
        return this.picFileId;
    }

    /**
     * 设置图片路径：物理或网络路径
     * 
     * @param picFileId
     *          图片路径
     */
    public void setPicFileId(Integer picFileId) {
        this.picFileId = picFileId;
    }

    /**
     * 获取是否置顶图片，默认为否，如果为是，则作为列表区的主要图片
     * 
     * @return 是否置顶图片
     */
    public Integer getIsMajor() {
        return this.isMajor;
    }

    /**
     * 设置是否置顶图片，默认为否，如果为是，则作为列表区的主要图片
     * 
     * @param isMajor
     *          是否置顶图片
     */
    public void setIsMajor(Integer isMajor) {
        this.isMajor = isMajor;
    }

    /**
     * 获取图片描述
     * 
     * @return 图片描述
     */
    public String getNameCn() {
        return this.nameCn;
    }

    /**
     * 设置图片描述
     * 
     * @param nameCn
     *          图片描述
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
}