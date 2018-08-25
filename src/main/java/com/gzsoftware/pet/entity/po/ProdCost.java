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
 * 产品明细查看费用表 ( 费用设置常量表 )： 里面的记录比如: 1.免费 2.光身瓶库收费1元 3. 概念图库收费4元 (PROD_COST)
 * 
 * @author bianj
 * @version 1.0.0 2017-09-17
 */
public class ProdCost extends BaseEntity {
    /**  */
    private Integer id;

    /** 查看详细时的费用 */
    private Integer cost;

    /** 名称描述：比如免费 */
    private String name;

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
     * 获取查看详细时的费用
     * 
     * @return 查看详细时的费用
     */
    public Integer getCost() {
        return this.cost;
    }

    /**
     * 设置查看详细时的费用
     * 
     * @param cost
     *          查看详细时的费用
     */
    public void setCost(Integer cost) {
        this.cost = cost;
    }

    /**
     * 获取名称描述：比如免费
     * 
     * @return 名称描述
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置名称描述：比如免费
     * 
     * @param name
     *          名称描述
     */
    public void setName(String name) {
        this.name = name;
    }
}