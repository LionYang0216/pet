package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.WareTypeSrcDao;
import com.gzsoftware.pet.entity.po.WareTypeSrc;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("wareTypeSrcService")
public class WareTypeSrcService extends BaseService {
	
	@Resource
	private WareTypeSrcDao wareTypeSrcDao;
	
	
	public Integer countAll(DataTablesRequest dtRequest){
		return wareTypeSrcDao.countAll(dtRequest);
	}
	
    public List<WareTypeSrc> getWareTypeSrcList(DataTablesRequest dtRequest){
    	return wareTypeSrcDao.getWareTypeSrcList(dtRequest);
    }
    
    public WareTypeSrc getWareTypeSrc(Integer id){
    	return wareTypeSrcDao.getWareTypeSrc(id);
    }
    
    public int addWareTypeSrc(WareTypeSrc record){
    	return wareTypeSrcDao.addWareTypeSrc(record);
    }
    
    public int updateWareTypeSrc(WareTypeSrc record){
    	return wareTypeSrcDao.updateWareTypeSrc(record);
    }
	
    public int deleteWareTypeSrc(Integer id){
    	return wareTypeSrcDao.deleteWareTypeSrc(id);
    }
    
    public List<WareTypeSrc> getWareTypeSrcSelect(){
    	return wareTypeSrcDao.getWareTypeSrcSelect();
    }
}
