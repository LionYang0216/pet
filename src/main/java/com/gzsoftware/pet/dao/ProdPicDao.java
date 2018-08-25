package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.ProdPic;

public interface ProdPicDao {
    public int deleteProdPic(Integer id);

    public int addProdPic(ProdPic record);

    public List<ProdPic> getProdPicList(Integer prodId);
    
    public ProdPic getMajarProdPic(Integer prodId);
   
}
