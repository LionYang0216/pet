package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.ProdTypeSouDao;
import com.gzsoftware.pet.entity.po.ProdTypeSou;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("prodTypeSouService")
public class ProdTypeSouService extends BaseService {
	
	@Resource
	private ProdTypeSouDao prodTypeSouDao;
	
	@Resource
	private ProdService prodService;
	
	public Integer countAll(DataTablesRequest dtRequest){
		return prodTypeSouDao.countAll(dtRequest);
	}
	
    public List<ProdTypeSou> getProdTypeSouList(DataTablesRequest dtRequest){
    	return prodTypeSouDao.getProdTypeSouList(dtRequest);
    }
    
    public ProdTypeSou getProdTypeSou(Integer id){
    	return prodTypeSouDao.getProdTypeSou(id);
    }
    
    public int addProdTypeSou(ProdTypeSou record){
    	return prodTypeSouDao.addProdTypeSou(record);
    }
    
    public int updateProdTypeSou(ProdTypeSou record){
    	return prodTypeSouDao.updateProdTypeSou(record);
    }
	
    public int deleteProdTypeSou(Integer id){
    	prodService.updateProdForProdTypeDelete("prodTypeSouId",id);
    	return prodTypeSouDao.deleteProdTypeSou(id);
    }
    
    public List<ProdTypeSou> getProdTypeSouSelect(){
    	return prodTypeSouDao.getProdTypeSouSelect();
    }
}
