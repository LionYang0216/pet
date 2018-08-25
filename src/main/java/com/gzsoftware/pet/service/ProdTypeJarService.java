package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.ProdTypeJarDao;
import com.gzsoftware.pet.entity.po.ProdTypeJar;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("prodTypeJarService")
public class ProdTypeJarService extends BaseService {
	
	@Resource
	private ProdTypeJarDao prodTypeJarDao;
	
	@Resource
	private ProdService prodService;
	
	public Integer countAll(DataTablesRequest dtRequest){
		return prodTypeJarDao.countAll(dtRequest);
	}
	
    public List<ProdTypeJar> getProdTypeJarList(DataTablesRequest dtRequest){
    	return prodTypeJarDao.getProdTypeJarList(dtRequest);
    }
    
    public ProdTypeJar getProdTypeJar(Integer id){
    	return prodTypeJarDao.getProdTypeJar(id);
    }
    
    public int addProdTypeJar(ProdTypeJar record){
    	return prodTypeJarDao.addProdTypeJar(record);
    }
    
    public int updateProdTypeJar(ProdTypeJar record){
    	return prodTypeJarDao.updateProdTypeJar(record);
    }
	
    public int deleteProdTypeJar(Integer id){
    	prodService.updateProdForProdTypeDelete("prodTypeJarId",id);
    	return prodTypeJarDao.deleteProdTypeJar(id);
    }
    
    public List<ProdTypeJar> getProdTypeJarSelect(){
    	return prodTypeJarDao.getProdTypeJarSelect();
    }
}
