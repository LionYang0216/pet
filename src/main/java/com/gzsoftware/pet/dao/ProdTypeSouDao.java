package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.ProdTypeSou;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface ProdTypeSouDao {
    public int deleteProdTypeSou(Integer id);

    public int addProdTypeSou(ProdTypeSou record);

    public List<ProdTypeSou> getProdTypeSouList(DataTablesRequest dtRequest);
    
    public ProdTypeSou getProdTypeSou(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateProdTypeSou(ProdTypeSou record);
    
    public List<ProdTypeSou> getProdTypeSouSelect();
}