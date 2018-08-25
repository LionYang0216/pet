package com.gzsoftware.pet.dao;

import java.util.List;
import java.util.Map;

import com.gzsoftware.pet.entity.po.Prod;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface ProdDao {
    public int deleteProd(Integer id);

    public int addProd(Prod record);

    public List<Prod> getProdList(DataTablesRequest dtRequest);
    
    public Prod getProd(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateProd(Prod record);
    
    public int updateProdForProdTypeDelete(Map map);
    
    public int addProdVisitCnt(int id);
    
    public int addProdUpCnt(int id);
    
    public int addProdFavCnt(int id);
    
    public int reduceProdFavCnt(Prod prod);
    
    public List<Prod> getRefProdList(String refCode);
    
    public List<Map> getTopVisitProdList(Integer length);

	public List<Prod> getProdConList(Integer length);
}
