package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.controller.BackController;
import com.gzsoftware.pet.dao.ContentDao;
import com.gzsoftware.pet.entity.po.Content;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;


@Service("contentService")
public class ContentService extends BaseService{

	private static Log log = LogFactory.getLog(ContentService.class);
	
	@SuppressWarnings("restriction")
	@Resource
	private ContentDao contentDao;

	public Content getContent(int id) {
		return contentDao.getContent(id);
	}
	public int updateContent(Content content) {
	   return contentDao.updateContent(content);
	}
	public Integer countAll(DataTablesRequest dtRequest) {
		return contentDao.countAll(dtRequest);
	}

	public List<Content> getContentList(DataTablesRequest dtRequest) {
		return contentDao.getContentList(dtRequest);
	}
	public Content getContentByCode(String code) {
		return contentDao.getContentByCode(code);
	}

}
