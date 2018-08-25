package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.MarqueeDao;
import com.gzsoftware.pet.entity.po.Marquee;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;


@Service("marqueeService")
public class MarqueeService extends BaseService{

	private static Log log = LogFactory.getLog(MarqueeService.class);
	
	@SuppressWarnings("restriction")
	@Resource
	private MarqueeDao marqueeDao;

	public Marquee getMarquee(int id) {
		return marqueeDao.getMarquee(id);
	}
	
	public int addMarquee(Marquee marquee) {
		return marqueeDao.addMarquee(marquee);
	}

	public int deleteMarquee(int id) {
		return marqueeDao.deleteMarquee(id);
	}
	
	public int updateMarquee(Marquee marquee) {
	   return marqueeDao.updateMarquee(marquee);
	}

	public Integer countAll(DataTablesRequest dtRequest) {
		return marqueeDao.countAll(dtRequest);
	}

	public List<Marquee> getMarqueeList(DataTablesRequest dtRequest) {
		return marqueeDao.getMarqueeList(dtRequest);
	}

	public List<Marquee> getMarqueeSelect(String code) {
		return marqueeDao.getMarqueeSelect(code);
	}

}
