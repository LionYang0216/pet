package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.ProdTypeJar;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface ProdTypeJarDao {
    public int deleteProdTypeJar(Integer id);

    public int addProdTypeJar(ProdTypeJar record);

    public List<ProdTypeJar> getProdTypeJarList(DataTablesRequest dtRequest);
    
    public ProdTypeJar getProdTypeJar(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateProdTypeJar(ProdTypeJar record);
    
    public List<ProdTypeJar> getProdTypeJarSelect();
}