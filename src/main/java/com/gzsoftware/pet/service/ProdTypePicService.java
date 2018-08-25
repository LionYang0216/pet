package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.ProdTypePicDao;
import com.gzsoftware.pet.entity.po.ProdTypePic;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("prodTypePicService")
public class ProdTypePicService extends BaseService {
	
	@Resource
	private ProdTypePicDao prodTypePicDao;
	
	@Resource
	private ProdService prodService;
	
	public Integer countAll(DataTablesRequest dtRequest){
		return prodTypePicDao.countAll(dtRequest);
	}
	
    public List<ProdTypePic> getProdTypePicList(DataTablesRequest dtRequest){
    	return prodTypePicDao.getProdTypePicList(dtRequest);
    }
    
    public ProdTypePic getProdTypePic(Integer id){
    	return prodTypePicDao.getProdTypePic(id);
    }
    
    public int addProdTypePic(ProdTypePic record){
    	return prodTypePicDao.addProdTypePic(record);
    }
    
    public int updateProdTypePic(ProdTypePic record){
    	return prodTypePicDao.updateProdTypePic(record);
    }
	
    public int deleteProdTypePic(Integer id){
    	prodService.updateProdForProdTypeDelete("prodTypePicId",id);
    	return prodTypePicDao.deleteProdTypePic(id);
    }
    
    public List<ProdTypePic> getProdTypePicSelect(){
    	return prodTypePicDao.getProdTypePicSelect();
    }
}
