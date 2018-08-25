package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.WareTypePrd;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface WareTypePrdDao {
	public int deleteWareTypePrd(Integer id);

    public int addWareTypePrd(WareTypePrd record);

    public List<WareTypePrd> getWareTypePrdList(DataTablesRequest dtRequest);
    
    public WareTypePrd getWareTypePrd(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateWareTypePrd(WareTypePrd record);
    
    public List<WareTypePrd> getWareTypePrdSelect();
}
