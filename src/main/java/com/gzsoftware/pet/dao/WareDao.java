package com.gzsoftware.pet.dao;

import java.util.List;
import java.util.Map;

import com.gzsoftware.pet.entity.po.Shop;
import com.gzsoftware.pet.entity.po.Ware;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface WareDao {
    public int deleteWare(Integer id);

    public int addWare(Ware record);

    public List<Ware> getWareList(DataTablesRequest dtRequest);
    
    public Ware getWare(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateWare(Ware record);
    
    public int updateWareForWareTypeDelete(Map map);
    
    public int addWareVisitCnt(int id);
    
    public int addWareUpCnt(int id);
    
    public int addWareFavCnt(int id);
    
    public Integer getWareFavTotal(Integer shopId);
    
    public Integer getWareVisitTotal(Integer shopId);
    
    public int reduceWareFavCnt(Ware ware);
    
    public List<Map> getTopVisitWareList(Integer length);
    
    public List<Ware> getShopWareList(Integer shopId);
}
