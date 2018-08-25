package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.ProdTypeHig;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface ProdTypeHigDao {
    public int deleteProdTypeHig(Integer id);

    public int addProdTypeHig(ProdTypeHig record);

    public List<ProdTypeHig> getProdTypeHigList(DataTablesRequest dtRequest);
    
    public ProdTypeHig getProdTypeHig(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateProdTypeHig(ProdTypeHig record);
    
    public List<ProdTypeHig> getProdTypeHigSelect();
}