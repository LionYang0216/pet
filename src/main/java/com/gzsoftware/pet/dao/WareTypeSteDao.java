package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.WareTypeSte;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface WareTypeSteDao {
	public int deleteWareTypeSte(Integer id);

    public int addWareTypeSte(WareTypeSte record);

    public List<WareTypeSte> getWareTypeSteList(DataTablesRequest dtRequest);
    
    public WareTypeSte getWareTypeSte(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateWareTypeSte(WareTypeSte record);
    
    public List<WareTypeSte> getWareTypeSteSelect();
}
