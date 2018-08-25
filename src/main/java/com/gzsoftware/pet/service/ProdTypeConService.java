package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.ProdTypeConDao;
import com.gzsoftware.pet.entity.po.ProdTypeCon;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("prodTypeConService")
public class ProdTypeConService extends BaseService {
	
	@Resource
	private ProdTypeConDao prodTypeConDao;
	
	@Resource
	private ProdService prodService;
	
	public Integer countAll(DataTablesRequest dtRequest){
		return prodTypeConDao.countAll(dtRequest);
	}
	
    public List<ProdTypeCon> getProdTypeConList(DataTablesRequest dtRequest){
    	return prodTypeConDao.getProdTypeConList(dtRequest);
    }
    
    public ProdTypeCon getProdTypeCon(Integer id){
    	return prodTypeConDao.getProdTypeCon(id);
    }
    
    public int addProdTypeCon(ProdTypeCon record){
    	return prodTypeConDao.addProdTypeCon(record);
    }
    
    public int updateProdTypeCon(ProdTypeCon record){
    	return prodTypeConDao.updateProdTypeCon(record);
    }
	
    public int deleteProdTypeCon(Integer id){
    	prodService.updateProdForProdTypeDelete("prodTypeConId",id);
    	return prodTypeConDao.deleteProdTypeCon(id);
    }
    
    public List<ProdTypeCon> getProdTypeConSelect(){
    	return prodTypeConDao.getProdTypeConSelect();
    }
}
