package com.gzsoftware.pet.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.UserProdFavDao;
import com.gzsoftware.pet.entity.po.Prod;
import com.gzsoftware.pet.entity.po.UserProdFav;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("prodFavService")
public class UserProdFavService extends BaseService {

	@Resource
	private UserProdFavDao prodFavDao;
	@Resource
	private ProdService prodService;

	/***
	 * 产品收藏
	 * 
	 * @param prodFav
	 * @return
	 */
	public int addProdFav(UserProdFav prodFav) {
		int effCnt = prodFavDao.addProdFav(prodFav);
		if (effCnt > 0) {
			prodService.addProdFavCnt(prodFav.getProdId());
		}
		return effCnt;
	}

	/***
	 * 取消收藏
	 * 
	 * @param prodFav
	 * @return
	 */
	public int removeProdFav(UserProdFav prodFav) {
		int effCnt = prodFavDao.deleteProdFav(prodFav.getId());
		if (effCnt > 0) {
			prodService.reduceProdFavCnt(prodFav.getProdId());
		}
		return effCnt;
	}

	public List<UserProdFav> getProdFavList(DataTablesRequest dtRequest) {
		return prodFavDao.getProdFavList(dtRequest);
	}

	public Integer countAll(DataTablesRequest dtRequest) {
		return prodFavDao.countAll(dtRequest);
	}

	/**
	 * 
	 * @param userId
	 * @param prodId
	 * @return
	 */
	public UserProdFav getUserProdFav(Integer userId, Integer prodId) {
		DataTablesRequest dtRequest = new DataTablesRequest();
		Map condition = new HashMap();
		condition.put("userId", userId);
		condition.put("prodId", prodId);
		dtRequest.setCondition(condition);
		return prodFavDao.getUserProdFav(dtRequest);
	}
	
	public int deleteProdFavByProdId(Integer prodId){
		return prodFavDao.deleteProdFavByProdId(prodId);
	}
}
