package com.gzsoftware.pet.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.UserWareFavDao;
import com.gzsoftware.pet.entity.po.Ware;
import com.gzsoftware.pet.entity.po.UserShopFav;
import com.gzsoftware.pet.entity.po.UserWareFav;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("wareFavService")
public class UserWareFavService extends BaseService {
    
	@Resource
	private UserWareFavDao wareFavDao;
	@Resource
	private WareService wareService;
	
	/***
	 * 店铺收藏
	 * @param wareFav
	 * @return
	 */
	public int addWareFav(UserWareFav wareFav){
		int effCnt=wareFavDao.addWareFav(wareFav);
		if(effCnt>0){
			wareService.addWareFavCnt(wareFav.getWareId());
		}
		return effCnt;
	}
	
	public List<UserWareFav> getWareFavList(DataTablesRequest dtRequest){
		return wareFavDao.getWareFavList(dtRequest);
	}

	public Integer countAll(DataTablesRequest dtRequest){
		return wareFavDao.countAll(dtRequest);
	}
	
	/**
	 * 
	 * @param userId
	 * @param wareId
	 * @return
	 */
	public UserWareFav getUserWareFav(Integer userId,Integer wareId){
		DataTablesRequest dtRequest=new DataTablesRequest();
		Map condition = new HashMap();
		condition.put("userId", userId);
		condition.put("wareId", wareId);
		dtRequest.setCondition(condition);
		return wareFavDao.getUserWareFav(dtRequest);
	}
	
	/***
	 * 取消收藏
	 * @param prodFav
	 * @return
	 */
	public int removeWareFav(UserWareFav wareFav){
		int effCnt=wareFavDao.deleteWareFav(wareFav.getId());
		if(effCnt>0){
			wareService.reduceWareFavCnt(wareFav.getWareId());
		}
		return effCnt;
	}
	
	public int deleteWareFavByWareId(Integer wareId){
		return wareFavDao.deleteWareFavByWareId(wareId);
	}
}
