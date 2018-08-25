package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.ProdCost;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface ProdCostDao {
    public int deleteProdCost(Integer id);

    public int addProdCost(ProdCost record);

    public List<ProdCost> getProdCostList(DataTablesRequest dtRequest);
    
    public ProdCost getProdCost(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateProdCost(ProdCost record);
    
    public List<ProdCost> getProdCostSelect();
}
