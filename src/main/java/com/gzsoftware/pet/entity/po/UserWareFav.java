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
 * 用户商品收藏关系表(USER_WARE_FAV)
 * 
 * @author bianj
 * @version 1.0.0 2017-10-14
 */
public class UserWareFav extends BaseEntity {
    /** 版本号 */
    private static final long serialVersionUID = 1351471549646822995L;

    /**  */
    private Integer id;

    /** 用户ID */
    private Integer userId;

    /** 商品ID */
    private Integer wareId;
    
    private User user;
    
    private Ware ware;

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
     * 获取商品ID
     * 
     * @return 商品ID
     */
    public Integer getWareId() {
        return this.wareId;
    }

    /**
     * 设置商品ID
     * 
     * @param wareId
     *          商品ID
     */
    public void setWareId(Integer wareId) {
        this.wareId = wareId;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Ware getWare() {
		return ware;
	}

	public void setWare(Ware ware) {
		this.ware = ware;
	}
    
    
}