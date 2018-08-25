package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.Prod;
import com.gzsoftware.pet.entity.po.Shop;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface ShopDao {
    public int deleteShop(Integer id);

    public int addShop(Shop record);

    public List<Shop> getShopList(DataTablesRequest dtRequest);
    
    public Shop getShop(Integer id);
    
    public Shop getShopByUser(Integer userId);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateShop(Shop record);
    
    public List<Shop> getShopSelect();
    
    public int addShopVisitCnt(int id);
    
    public int addShopFavCnt(int id);
    
    public int reduceShopFavCnt(Shop shop);
    
    public List<Shop> getShopApplyList(Integer length);
}