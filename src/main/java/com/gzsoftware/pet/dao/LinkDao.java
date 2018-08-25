package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.Link;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface LinkDao {
	
	public Link getLink(int id);

	public List<Link> getLinkList(DataTablesRequest dtRequest);

	public Integer countAll(DataTablesRequest dtRequest);

	public int addLink(Link link);

	public int deleteLink(int id);
	
	public int addLinkVisitCnt(int id);
	
	public int updateLink (Link link);

}
