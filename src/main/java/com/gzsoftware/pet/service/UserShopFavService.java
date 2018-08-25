package com.gzsoftware.pet.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.UserShopFavDao;
import com.gzsoftware.pet.entity.po.Shop;
import com.gzsoftware.pet.entity.po.UserProdFav;
import com.gzsoftware.pet.entity.po.UserShopFav;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("shopFavService")
public class UserShopFavService extends BaseService {
    
	@Resource
	private UserShopFavDao shopFavDao;
	@Resource
	private ShopService shopService;
	
	/***
	 * 店铺收藏
	 * @param shopFav
	 * @return
	 */
	public int addShopFav(UserShopFav shopFav){
		int effCnt=shopFavDao.addShopFav(shopFav);
		if(effCnt>0){
			shopService.addShopFavCnt(shopFav.getShopId());
		}
		return effCnt;
	}
	
	public List<UserShopFav> getShopFavList(DataTablesRequest dtRequest){
		return shopFavDao.getShopFavList(dtRequest);
	}

	public Integer countAll(DataTablesRequest dtRequest){
		return shopFavDao.countAll(dtRequest);
	}
	
	
	/**
	 * 
	 * @param userId
	 * @param shopId
	 * @return
	 */
	public UserShopFav getUserShopFav(Integer userId,Integer shopId){
		DataTablesRequest dtRequest=new DataTablesRequest();
		Map condition = new HashMap();
		condition.put("userId", userId);
		condition.put("shopId", shopId);
		dtRequest.setCondition(condition);
		return shopFavDao.getUserShopFav(dtRequest);
	}
	
	/***
	 * 取消收藏
	 * @param prodFav
	 * @return
	 */
	public int removeShopFav(UserShopFav shopFav){
		int effCnt=shopFavDao.deleteShopFav(shopFav.getId());
		if(effCnt>0){
			shopService.reduceShopFavCnt(shopFav.getShopId());
		}
		return effCnt;
	}
	
	public int deleteShopFavByShopId(Integer shopId){
		return shopFavDao.deleteShopFavByShopId(shopId);
	}
}
