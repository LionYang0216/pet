package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.ProdTypePhy;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface ProdTypePhyDao {
    public int deleteProdTypePhy(Integer id);

    public int addProdTypePhy(ProdTypePhy record);

    public List<ProdTypePhy> getProdTypePhyList(DataTablesRequest dtRequest);
    
    public ProdTypePhy getProdTypePhy(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateProdTypePhy(ProdTypePhy record);
    
    public List<ProdTypePhy> getProdTypePhySelect();
}