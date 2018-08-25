package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.UserProdFav;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface UserProdFavDao {

	public int addProdFav(UserProdFav prodFav);
	
	public List<UserProdFav> getProdFavList(DataTablesRequest dtRequest);

	public Integer countAll(DataTablesRequest dtRequest);
	
	public UserProdFav getUserProdFav(DataTablesRequest dtRequest);
	
	public int deleteProdFav(Integer id);
	
	public int deleteProdFavByProdId(Integer prodId);
}
