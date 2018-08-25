package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.WareTypeSrc;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface WareTypeSrcDao {
	public int deleteWareTypeSrc(Integer id);

    public int addWareTypeSrc(WareTypeSrc record);

    public List<WareTypeSrc> getWareTypeSrcList(DataTablesRequest dtRequest);
    
    public WareTypeSrc getWareTypeSrc(Integer id);
    
    public Integer countAll(DataTablesRequest dtRequest);

    public int updateWareTypeSrc(WareTypeSrc record);
    
    public List<WareTypeSrc> getWareTypeSrcSelect();
}
