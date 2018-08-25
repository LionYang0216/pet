package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.Game;
import com.gzsoftware.pet.entity.po.Marquee;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface MarqueeDao {

	public Marquee getMarquee(int id);

	public List<Marquee> getMarqueeList(DataTablesRequest dtRequest);

	public Integer countAll(DataTablesRequest dtRequest);

	public int addMarquee(Marquee marquee);

	public int deleteMarquee(int id);

	public int updateMarquee (Marquee marquee);

	public List<Marquee> getMarqueeSelect(String code);

}