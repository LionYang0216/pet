package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.ProdTypeHigDao;
import com.gzsoftware.pet.entity.po.ProdTypeHig;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("prodTypeHigService")
public class ProdTypeHigService extends BaseService {
	
	@Resource
	private ProdTypeHigDao prodTypeHigDao;
	
	@Resource
	private ProdService prodService;
	
	public Integer countAll(DataTablesRequest dtRequest){
		return prodTypeHigDao.countAll(dtRequest);
	}
	
    public List<ProdTypeHig> getProdTypeHigList(DataTablesRequest dtRequest){
    	return prodTypeHigDao.getProdTypeHigList(dtRequest);
    }
    
    public ProdTypeHig getProdTypeHig(Integer id){
    	return prodTypeHigDao.getProdTypeHig(id);
    }
    
    public int addProdTypeHig(ProdTypeHig record){
    	return prodTypeHigDao.addProdTypeHig(record);
    }
    
    public int updateProdTypeHig(ProdTypeHig record){
    	return prodTypeHigDao.updateProdTypeHig(record);
    }
	
    public int deleteProdTypeHig(Integer id){
    	prodService.updateProdForProdTypeDelete("prodTypeHigId",id);
    	return prodTypeHigDao.deleteProdTypeHig(id);
    }
    
    public List<ProdTypeHig> getProdTypeHigSelect(){
    	return prodTypeHigDao.getProdTypeHigSelect();
    }
}
