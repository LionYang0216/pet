package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.LinkDao;
import com.gzsoftware.pet.entity.po.Link;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("linkService")
public class LinkService extends BaseService {

	private static Log log = LogFactory.getLog(LinkService.class);

	@SuppressWarnings("restriction")
	@Resource
	private LinkDao linkDao;

	public Link getLink(int id) {
		return linkDao.getLink(id);
	}

	public int addLink(Link link) {
		return linkDao.addLink(link);
	}

	public int deleteLink(int id) {
		return linkDao.deleteLink(id);
	}

	public int updateLink(Link link) {
		return linkDao.updateLink(link);
	}


	public Integer countAll(DataTablesRequest dtRequest) {
		return linkDao.countAll(dtRequest);
	}

	public List<Link> getLinkList(DataTablesRequest dtRequest) {
		return linkDao.getLinkList(dtRequest);
	}

}
