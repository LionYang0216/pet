package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.WareTypePrdDao;
import com.gzsoftware.pet.entity.po.WareTypePrd;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("wareTypePrdService")
public class WareTypePrdService extends BaseService {
	
	@Resource
	private WareTypePrdDao wareTypePrdDao;

	
	public Integer countAll(DataTablesRequest dtRequest){
		return wareTypePrdDao.countAll(dtRequest);
	}
	
    public List<WareTypePrd> getWareTypePrdList(DataTablesRequest dtRequest){
    	return wareTypePrdDao.getWareTypePrdList(dtRequest);
    }
    
    public WareTypePrd getWareTypePrd(Integer id){
    	return wareTypePrdDao.getWareTypePrd(id);
    }
    
    public int addWareTypePrd(WareTypePrd record){
    	return wareTypePrdDao.addWareTypePrd(record);
    }
    
    public int updateWareTypePrd(WareTypePrd record){
    	return wareTypePrdDao.updateWareTypePrd(record);
    }
	
    public int deleteWareTypePrd(Integer id){
    	return wareTypePrdDao.deleteWareTypePrd(id);
    }
    
    public List<WareTypePrd> getWareTypePrdSelect(){
    	return wareTypePrdDao.getWareTypePrdSelect();
    }
}
