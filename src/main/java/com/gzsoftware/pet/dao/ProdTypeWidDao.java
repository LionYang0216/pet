package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.ProdTypeWid;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface ProdTypeWidDao {
    public int deleteProdTypeWid(Integer id);

    public int addProdTypeWid(ProdTypeWid record);

    public List<ProdTypeWid> getProdTypeWidList(DataTablesRequest dtRequest);
    
    public ProdTypeWid getProdTypeWid(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateProdTypeWid(ProdTypeWid record);
    
    public List<ProdTypeWid> getProdTypeWidSelect();
}