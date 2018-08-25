package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.WareTypeMchDao;
import com.gzsoftware.pet.entity.po.WareTypeMch;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("wareTypeMchService")
public class WareTypeMchService extends BaseService {
	
	@Resource
	private WareTypeMchDao wareTypeMchDao;
	
	public Integer countAll(DataTablesRequest dtRequest){
		return wareTypeMchDao.countAll(dtRequest);
	}
	
    public List<WareTypeMch> getWareTypeMchList(DataTablesRequest dtRequest){
    	return wareTypeMchDao.getWareTypeMchList(dtRequest);
    }
    
    public WareTypeMch getWareTypeMch(Integer id){
    	return wareTypeMchDao.getWareTypeMch(id);
    }
    
    public int addWareTypeMch(WareTypeMch record){
    	return wareTypeMchDao.addWareTypeMch(record);
    }
    
    public int updateWareTypeMch(WareTypeMch record){
    	return wareTypeMchDao.updateWareTypeMch(record);
    }
	
    public int deleteWareTypeMch(Integer id){
    	return wareTypeMchDao.deleteWareTypeMch(id);
    }
    
    public List<WareTypeMch> getWareTypeMchSelect(){
    	return wareTypeMchDao.getWareTypeMchSelect();
    }
}
