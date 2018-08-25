package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.ProdTypePhyDao;
import com.gzsoftware.pet.entity.po.ProdTypePhy;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("prodTypePhyService")
public class ProdTypePhyService extends BaseService {
	
	@Resource
	private ProdTypePhyDao prodTypePhyDao;
	@Resource
	private ProdService prodService;
	
	public Integer countAll(DataTablesRequest dtRequest){
		return prodTypePhyDao.countAll(dtRequest);
	}
	
    public List<ProdTypePhy> getProdTypePhyList(DataTablesRequest dtRequest){
    	return prodTypePhyDao.getProdTypePhyList(dtRequest);
    }
    
    public ProdTypePhy getProdTypePhy(Integer id){
    	return prodTypePhyDao.getProdTypePhy(id);
    }
    
    public int addProdTypePhy(ProdTypePhy record){
    	return prodTypePhyDao.addProdTypePhy(record);
    }
    
    public int updateProdTypePhy(ProdTypePhy record){
    	return prodTypePhyDao.updateProdTypePhy(record);
    }
	
    public int deleteProdTypePhy(Integer id){
    	prodService.updateProdForProdTypeDelete("prodTypePhyId",id);
    	return prodTypePhyDao.deleteProdTypePhy(id);
    }
    
    public List<ProdTypePhy> getProdTypePhySelect(){
    	return prodTypePhyDao.getProdTypePhySelect();
    }
}
