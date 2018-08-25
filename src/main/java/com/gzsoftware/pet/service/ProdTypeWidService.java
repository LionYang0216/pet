package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.ProdTypeWidDao;
import com.gzsoftware.pet.entity.po.ProdTypeWid;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("prodTypeWidService")
public class ProdTypeWidService extends BaseService {
	
	@Resource
	private ProdTypeWidDao prodTypeWidDao;
	
	@Resource
	private ProdService prodService;
	
	public Integer countAll(DataTablesRequest dtRequest){
		return prodTypeWidDao.countAll(dtRequest);
	}
	
    public List<ProdTypeWid> getProdTypeWidList(DataTablesRequest dtRequest){
    	return prodTypeWidDao.getProdTypeWidList(dtRequest);
    }
    
    public ProdTypeWid getProdTypeWid(Integer id){
    	return prodTypeWidDao.getProdTypeWid(id);
    }
    
    public int addProdTypeWid(ProdTypeWid record){
    	return prodTypeWidDao.addProdTypeWid(record);
    }
    
    public int updateProdTypeWid(ProdTypeWid record){
    	return prodTypeWidDao.updateProdTypeWid(record);
    }
	
    public int deleteProdTypeWid(Integer id){
    	prodService.updateProdForProdTypeDelete("prodTypeWidId",id);
    	return prodTypeWidDao.deleteProdTypeWid(id);
    }
    
    public List<ProdTypeWid> getProdTypeWidSelect(){
    	return prodTypeWidDao.getProdTypeWidSelect();
    }
}
