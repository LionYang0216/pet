package com.gzsoftware.pet.dao;

import java.util.List;

import com.gzsoftware.pet.entity.po.Game;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface GameDao {

	public Game getGame(int id);

	public List<Game> getGameList(DataTablesRequest dtRequest);

	public Integer countAll(DataTablesRequest dtRequest);

	public int addGame(Game game);

	public int deleteGame(int id);
	
	public int addGameVisitCnt(int id);
	
	public int updateGame (Game game);

}