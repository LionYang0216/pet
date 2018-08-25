package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.ProdTypeAreDao;
import com.gzsoftware.pet.entity.po.ProdTypeAre;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("prodTypeAreService")
public class ProdTypeAreService extends BaseService {
	
	@Resource
	private ProdTypeAreDao prodTypeAreDao;
	@Resource
	private ProdService prodService;
	
	public Integer countAll(DataTablesRequest dtRequest){
		return prodTypeAreDao.countAll(dtRequest);
	}
	
    public List<ProdTypeAre> getProdTypeAreList(DataTablesRequest dtRequest){
    	return prodTypeAreDao.getProdTypeAreList(dtRequest);
    }
    
    public ProdTypeAre getProdTypeAre(Integer id){
    	return prodTypeAreDao.getProdTypeAre(id);
    }
    
    public int addProdTypeAre(ProdTypeAre record){
    	return prodTypeAreDao.addProdTypeAre(record);
    }
    
    public int updateProdTypeAre(ProdTypeAre record){
    	return prodTypeAreDao.updateProdTypeAre(record);
    }
	
    public int deleteProdTypeAre(Integer id){
    	prodService.updateProdForProdTypeDelete("prodTypeAreId",id);
    	return prodTypeAreDao.deleteProdTypeAre(id);
    }
    
    public List<ProdTypeAre> getProdTypeAreSelect(){
    	return prodTypeAreDao.getProdTypeAreSelect();
    }
}
