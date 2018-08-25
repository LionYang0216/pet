package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.ProdTypeNekDao;
import com.gzsoftware.pet.entity.po.ProdTypeNek;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("prodTypeNekService")
public class ProdTypeNekService extends BaseService {
	
	@Resource
	private ProdTypeNekDao prodTypeNekDao;
	
	@Resource
	private ProdService prodService;
	
	public Integer countAll(DataTablesRequest dtRequest){
		return prodTypeNekDao.countAll(dtRequest);
	}
	
    public List<ProdTypeNek> getProdTypeNekList(DataTablesRequest dtRequest){
    	return prodTypeNekDao.getProdTypeNekList(dtRequest);
    }
    
    public ProdTypeNek getProdTypeNek(Integer id){
    	return prodTypeNekDao.getProdTypeNek(id);
    }
    
    public int addProdTypeNek(ProdTypeNek record){
    	return prodTypeNekDao.addProdTypeNek(record);
    }
    
    public int updateProdTypeNek(ProdTypeNek record){
    	return prodTypeNekDao.updateProdTypeNek(record);
    }
	
    public int deleteProdTypeNek(Integer id){
    	prodService.updateProdForProdTypeDelete("prodTypeNekId",id);
    	return prodTypeNekDao.deleteProdTypeNek(id);
    }
    
    public List<ProdTypeNek> getProdTypeNekSelect(){
    	return prodTypeNekDao.getProdTypeNekSelect();
    }
}
