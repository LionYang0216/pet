package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.ProdTypePic;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface ProdTypePicDao {
    public int deleteProdTypePic(Integer id);

    public int addProdTypePic(ProdTypePic record);

    public List<ProdTypePic> getProdTypePicList(DataTablesRequest dtRequest);
    
    public ProdTypePic getProdTypePic(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateProdTypePic(ProdTypePic record);
    
    public List<ProdTypePic> getProdTypePicSelect();
}