package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.News;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface NewsDao {

	public News getNews(int id);

	public List<News> getNewsList(DataTablesRequest dtRequest);

	public Integer countAll(DataTablesRequest dtRequest);

	public int addNews(News news);

	public int deleteNews(int id);
	
	public int addNewsVisitCnt(int id);
	
	public int addNewsUpCnt(int id);
	
	public int updateNews (News News);

}