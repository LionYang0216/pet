package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.Content;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface ContentDao {

	public Content getContent(int id);

	public List<Content> getContentList(DataTablesRequest dtRequest);

	public Integer countAll(DataTablesRequest dtRequest);
	
	public int updateContent (Content content);

	public Content getContentByCode(String code);

}