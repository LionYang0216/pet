package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.WareTypeMch;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface WareTypeMchDao {
	public int deleteWareTypeMch(Integer id);

    public int addWareTypeMch(WareTypeMch record);

    public List<WareTypeMch> getWareTypeMchList(DataTablesRequest dtRequest);
    
    public WareTypeMch getWareTypeMch(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateWareTypeMch(WareTypeMch record);
    
    public List<WareTypeMch> getWareTypeMchSelect();
}
