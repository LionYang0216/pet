package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.UserShopFav;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface UserShopFavDao {

	public int addShopFav(UserShopFav prodFav);
	
	public List<UserShopFav> getShopFavList(DataTablesRequest dtRequest);

	public Integer countAll(DataTablesRequest dtRequest);
	
	public UserShopFav getUserShopFav(DataTablesRequest dtRequest);
	
	public int deleteShopFav(Integer id);
	
	public int deleteShopFavByShopId(Integer shopId);
}
