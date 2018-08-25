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
 * 用户产品收藏关系表(USER_PROD_FAV)
 * 
 * @author bianj
 * @version 1.0.0 2017-10-14
 */
public class UserProdFav extends BaseEntity {
    /** 版本号 */
    private static final long serialVersionUID = 5088667972865477285L;

    /**  */
    private Integer id;

    /**  */
    private Integer userId;

    /**  */
    private Integer prodId;
    
    private User user;
    
    private Prod prod;

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
     * 获取
     * 
     * @return 
     */
    public Integer getUserId() {
        return this.userId;
    }

    /**
     * 设置
     * 
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取
     * 
     * @return 
     */
    public Integer getProdId() {
        return this.prodId;
    }

    /**
     * 设置
     * 
     * @param prodId
     */
    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Prod getProd() {
		return prod;
	}

	public void setProd(Prod prod) {
		this.prod = prod;
	}
    
    
}