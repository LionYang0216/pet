package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.ProdTypeNek;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface ProdTypeNekDao {
    public int deleteProdTypeNek(Integer id);

    public int addProdTypeNek(ProdTypeNek record);

    public List<ProdTypeNek> getProdTypeNekList(DataTablesRequest dtRequest);
    
    public ProdTypeNek getProdTypeNek(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateProdTypeNek(ProdTypeNek record);
    
    public List<ProdTypeNek> getProdTypeNekSelect();
}