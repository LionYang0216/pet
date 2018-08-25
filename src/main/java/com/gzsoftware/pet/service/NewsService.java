package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.controller.BackController;
import com.gzsoftware.pet.dao.NewsDao;
import com.gzsoftware.pet.entity.po.News;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;


@Service("newsService")
public class NewsService extends BaseService{

	private static Log log = LogFactory.getLog(NewsService.class);
	
	@SuppressWarnings("restriction")
	@Resource
	private NewsDao newsDao;

	public News getNews(int id) {
		return newsDao.getNews(id);
	}
	
	public int addNews(News news) {
		return newsDao.addNews(news);
	}

	public int deleteNews(int id) {
		return newsDao.deleteNews(id);
	}
	
	public int updateNews(News news) {
	   return newsDao.updateNews(news);
	}
	
	public int addNewsVisitCnt(int id) {
		return newsDao.addNewsVisitCnt(id);
	}
	

	public Integer countAll(DataTablesRequest dtRequest) {
		return newsDao.countAll(dtRequest);
	}

	public List<News> getNewsList(DataTablesRequest dtRequest) {
		return newsDao.getNewsList(dtRequest);
	}

	public int addNewsUpCnt(int id) {
		// TODO Auto-generated method stub
		return newsDao.addNewsUpCnt(id);
	}

}
