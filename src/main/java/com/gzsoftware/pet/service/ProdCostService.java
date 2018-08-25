package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.ProdCostDao;
import com.gzsoftware.pet.entity.po.ProdCost;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("prodCostService")
public class ProdCostService extends BaseService {
	
	@Resource
	private ProdCostDao prodCostDao;
	
	@Resource
	private ProdService prodService;
	
	public Integer countAll(DataTablesRequest dtRequest){
		return prodCostDao.countAll(dtRequest);
	}
	
    public List<ProdCost> getProdCostList(DataTablesRequest dtRequest){
    	return prodCostDao.getProdCostList(dtRequest);
    }
    
    public ProdCost getProdCost(Integer id){
    	return prodCostDao.getProdCost(id);
    }
    
    public int addProdCost(ProdCost record){
    	return prodCostDao.addProdCost(record);
    }
    
    public int updateProdCost(ProdCost record){
    	return prodCostDao.updateProdCost(record);
    }
	
    public int deleteProdCost(Integer id){
    	prodService.updateProdForProdTypeDelete("prodCostId",id);
    	return prodCostDao.deleteProdCost(id);
    }
    
    public List<ProdCost> getProdCostSelect(){
    	return prodCostDao.getProdCostSelect();
    }
    
}
