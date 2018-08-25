package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.UserWareFav;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface UserWareFavDao {

	public int addWareFav(UserWareFav prodFav);
	
	public List<UserWareFav> getWareFavList(DataTablesRequest dtRequest);

	public Integer countAll(DataTablesRequest dtRequest);
	
	public UserWareFav getUserWareFav(DataTablesRequest dtRequest);
	
	public int deleteWareFav(Integer id);
	
	public int deleteWareFavByWareId(Integer wareId);
}
