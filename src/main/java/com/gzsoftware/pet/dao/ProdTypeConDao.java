package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.ProdTypeCon;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface ProdTypeConDao {
    public int deleteProdTypeCon(Integer id);

    public int addProdTypeCon(ProdTypeCon record);

    public List<ProdTypeCon> getProdTypeConList(DataTablesRequest dtRequest);
    
    public ProdTypeCon getProdTypeCon(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateProdTypeCon(ProdTypeCon record);
    
    public List<ProdTypeCon> getProdTypeConSelect();
}