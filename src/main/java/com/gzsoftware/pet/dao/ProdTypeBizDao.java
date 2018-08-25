package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.ProdTypeBiz;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface ProdTypeBizDao {
    public int deleteProdTypeBiz(Integer id);

    public int addProdTypeBiz(ProdTypeBiz record);

    public List<ProdTypeBiz> getProdTypeBizList(DataTablesRequest dtRequest);
    
    public ProdTypeBiz getProdTypeBiz(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateProdTypeBiz(ProdTypeBiz record);
    
    public List<ProdTypeBiz> getProdTypeBizSelect();
}