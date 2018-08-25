package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.controller.BackController;
import com.gzsoftware.pet.dao.NewsTypeDao;
import com.gzsoftware.pet.entity.po.NewsType;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;


@Service("newsTypeService")
public class NewsTypeService extends BaseService{

	private static Log log = LogFactory.getLog(NewsTypeService.class);
	
	@SuppressWarnings("restriction")
	@Resource
	private NewsTypeDao newsTypeDao;

	public NewsType getNewsType(int id) {
		return newsTypeDao.getNewsType(id);
	}
	
	public int addNewsType(NewsType NewsType) {
		return newsTypeDao.addNewsType(NewsType);
	}

	public int deleteNewsType(int id) {
		return newsTypeDao.deleteNewsType(id);
	}
	
	public int updateNewsType(NewsType NewsType) {
	   return newsTypeDao.updateNewsType(NewsType);
	}
	

	public Integer countAll(DataTablesRequest dtRequest) {
		return newsTypeDao.countAll(dtRequest);
	}

	public List<NewsType> getNewsTypeList(DataTablesRequest dtRequest) {
		return newsTypeDao.getNewsTypeList(dtRequest);
	}

	public List<NewsType> getNewsTypeListForSelect() {
		// TODO Auto-generated method stub
		return newsTypeDao.getNewsTypeListForSelect();
	}

}
