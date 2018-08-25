package com.gzsoftware.pet.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.WareDao;
import com.gzsoftware.pet.entity.po.Ware;
import com.gzsoftware.pet.entity.po.Ware;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("wareService")
public class WareService extends BaseService {

	@Resource
	private WareDao wareDao;
	@Resource
	private UploadFileService uploadFileService;
	
	@Resource
	private UserWareFavService userWareFavService;

	public Integer countAll(DataTablesRequest dtRequest) {
		return wareDao.countAll(dtRequest);
	}

	public List<Ware> getWareList(DataTablesRequest dtRequest) {
		return wareDao.getWareList(dtRequest);
	}

	public Ware getWare(Integer id) {
		Ware ware = wareDao.getWare(id);

		return ware;
	}

	public int addWare(Ware record) {
		int eff = wareDao.addWare(record);
		return eff;
	}

	public int updateWare(Ware record) {
		int efft = wareDao.updateWare(record);
		return efft;
	}

	public int deleteWare(Integer id) {
		userWareFavService.deleteWareFavByWareId(id);//删除商品的收藏记录
		return wareDao.deleteWare(id);
	}

	public int updateWareForWareTypeDelete(String typeIdName, Integer typeValue) {
		Map map = new HashMap();
		map.put(typeIdName, typeValue);
		return wareDao.updateWareForWareTypeDelete(map);
	}

	public int addWareVisitCnt(int id) {
		return wareDao.addWareVisitCnt(id);
	}

	public int addWareUpCnt(int id) {
		return wareDao.addWareUpCnt(id);
	}

	public int addWareFavCnt(int id) {
		return wareDao.addWareFavCnt(id);
	}

    public Integer getWareFavTotal(Integer shopId){
    	return wareDao.getWareFavTotal(shopId);
    }
    
    public Integer getWareVisitTotal(Integer shopId){
    	return wareDao.getWareVisitTotal(shopId);
    }
    
    public int reduceWareFavCnt(int id) {
		Ware ware=wareDao.getWare(id);
		if(ware.getFavCnt()<=1){
			ware.setFavCnt(0);
		}else{
			ware.setFavCnt(ware.getFavCnt()-1);
		}
		return wareDao.reduceWareFavCnt(ware);
	}
    
    public List<Map> getTopVisitWareList(Integer length){
    	return wareDao.getTopVisitWareList(length);
    }
    
    public List<Ware> getShopWareList(Integer shopId){
    	return wareDao.getShopWareList(shopId);
    }
}
