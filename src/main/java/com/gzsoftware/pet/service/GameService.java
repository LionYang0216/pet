package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.controller.BackController;
import com.gzsoftware.pet.dao.AdminDao;
import com.gzsoftware.pet.dao.GameDao;
import com.gzsoftware.pet.entity.po.Admin;
import com.gzsoftware.pet.entity.po.Game;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;


@Service("gameService")
public class GameService extends BaseService{

	private static Log log = LogFactory.getLog(GameService.class);
	
	@SuppressWarnings("restriction")
	@Resource
	private GameDao gameDao;

	public Game getGame(int id) {
		return gameDao.getGame(id);
	}
	
	public int addGame(Game game) {
		return gameDao.addGame(game);
	}

	public int deleteGame(int id) {
		return gameDao.deleteGame(id);
	}
	
	public int updateGame(Game game) {
	   return gameDao.updateGame(game);
	}
	
	public int addGameVisitCnt(int id) {
		return gameDao.addGameVisitCnt(id);
	}
	

	public Integer countAll(DataTablesRequest dtRequest) {
		return gameDao.countAll(dtRequest);
	}

	public List<Game> getGameList(DataTablesRequest dtRequest) {
		return gameDao.getGameList(dtRequest);
	}

}
