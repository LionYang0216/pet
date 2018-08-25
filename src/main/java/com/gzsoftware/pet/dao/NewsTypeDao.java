package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.NewsType;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface NewsTypeDao {

	public NewsType getNewsType(int id);

	public List<NewsType> getNewsTypeList(DataTablesRequest dtRequest);

	public Integer countAll(DataTablesRequest dtRequest);

	public int addNewsType(NewsType newsType);

	public int deleteNewsType(int id);
	
	public int updateNewsType (NewsType newsType);

	public List<NewsType> getNewsTypeListForSelect();


}