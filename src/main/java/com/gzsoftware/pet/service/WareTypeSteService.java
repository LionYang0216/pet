package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.WareTypeSteDao;
import com.gzsoftware.pet.entity.po.WareTypeSte;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("wareTypeSteService")
public class WareTypeSteService extends BaseService {
	
	@Resource
	private WareTypeSteDao wareTypeSteDao;

	
	public Integer countAll(DataTablesRequest dtRequest){
		return wareTypeSteDao.countAll(dtRequest);
	}
	
    public List<WareTypeSte> getWareTypeSteList(DataTablesRequest dtRequest){
    	return wareTypeSteDao.getWareTypeSteList(dtRequest);
    }
    
    public WareTypeSte getWareTypeSte(Integer id){
    	return wareTypeSteDao.getWareTypeSte(id);
    }
    
    public int addWareTypeSte(WareTypeSte record){
    	return wareTypeSteDao.addWareTypeSte(record);
    }
    
    public int updateWareTypeSte(WareTypeSte record){
    	return wareTypeSteDao.updateWareTypeSte(record);
    }
	
    public int deleteWareTypeSte(Integer id){
    	return wareTypeSteDao.deleteWareTypeSte(id);
    }
    
    public List<WareTypeSte> getWareTypeSteSelect(){
    	return wareTypeSteDao.getWareTypeSteSelect();
    }
}
