package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.ProdPicDao;
import com.gzsoftware.pet.entity.po.ProdPic;

@Service("prodPicService")
public class ProdPicService extends BaseService {
	
	@Resource
	private ProdPicDao prodPicDao;
	
	
    public List<ProdPic> getProdPicList(Integer prodId){
    	return prodPicDao.getProdPicList(prodId);
    }
    
    public ProdPic getMajarProdPic(Integer prodId){
    	return prodPicDao.getMajarProdPic(prodId);
    }
    
    public int addProdPic(ProdPic record){
    	return prodPicDao.addProdPic(record);
    }
	
    public int deleteProdPic(Integer id){
    	return prodPicDao.deleteProdPic(id);
    }
    
    
}
