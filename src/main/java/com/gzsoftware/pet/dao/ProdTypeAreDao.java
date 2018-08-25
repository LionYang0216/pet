package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.ProdTypeAre;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface ProdTypeAreDao {
    public int deleteProdTypeAre(Integer id);

    public int addProdTypeAre(ProdTypeAre record);

    public List<ProdTypeAre> getProdTypeAreList(DataTablesRequest dtRequest);
    
    public ProdTypeAre getProdTypeAre(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateProdTypeAre(ProdTypeAre record);
    
    public List<ProdTypeAre> getProdTypeAreSelect();
}