package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.ProdTypeBizDao;
import com.gzsoftware.pet.entity.po.ProdTypeBiz;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("prodTypeBizService")
public class ProdTypeBizService extends BaseService {
	
	@Resource
	private ProdTypeBizDao prodTypeBizDao;
	@Resource
	private ProdService prodService;
	
	public Integer countAll(DataTablesRequest dtRequest){
		return prodTypeBizDao.countAll(dtRequest);
	}
	
    public List<ProdTypeBiz> getProdTypeBizList(DataTablesRequest dtRequest){
    	return prodTypeBizDao.getProdTypeBizList(dtRequest);
    }
    
    public ProdTypeBiz getProdTypeBiz(Integer id){
    	return prodTypeBizDao.getProdTypeBiz(id);
    }
    
    public int addProdTypeBiz(ProdTypeBiz record){
    	return prodTypeBizDao.addProdTypeBiz(record);
    }
    
    public int updateProdTypeBiz(ProdTypeBiz record){
    	return prodTypeBizDao.updateProdTypeBiz(record);
    }
	
    public int deleteProdTypeBiz(Integer id){
    	prodService.updateProdForProdTypeDelete("prodTypeBizId",id);
    	return prodTypeBizDao.deleteProdTypeBiz(id);
    }
    
    public List<ProdTypeBiz> getProdTypeBizSelect(){
    	return prodTypeBizDao.getProdTypeBizSelect();
    }
}
