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
 * 用户店铺收藏关系表(USER_SHOP_FAV)
 * 
 * @author bianj
 * @version 1.0.0 2017-10-14
 */
public class UserShopFav extends BaseEntity {
    /** 版本号 */
    private static final long serialVersionUID = 1035528810848383355L;

    /**  */
    private Integer id;

    /** 用户ID */
    private Integer userId;

    /** 商铺ID */
    private Integer shopId;
    
    private User user;
    
    private Shop shop;

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
     * 获取用户ID
     * 
     * @return 用户ID
     */
    public Integer getUserId() {
        return this.userId;
    }

    /**
     * 设置用户ID
     * 
     * @param userId
     *          用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取商铺ID
     * 
     * @return 商铺ID
     */
    public Integer getShopId() {
        return this.shopId;
    }

    /**
     * 设置商铺ID
     * 
     * @param shopId
     *          商铺ID
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
    
    
}