package com.gzsoftware.pet.controller;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzsoftware.pet.entity.po.Admin;
import com.gzsoftware.pet.entity.po.Game;
import com.gzsoftware.pet.entity.po.UploadFile;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.GameService;
import com.gzsoftware.pet.service.UploadFileService;
import com.gzsoftware.pet.utils.CommonUtil;

@Controller
@RequestMapping("game")
public class GameController extends BaseController{

	private static Log log = LogFactory.getLog(GameController.class);
	
	@Resource
	private GameService gameService;
	
	@Resource
	private UploadFileService uploadFileService;
	
	
	/**
	 * 新增 Game
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addGame", method = RequestMethod.POST)
	public Result addGame(@RequestBody Game game,HttpServletRequest request) {
		 Admin currentAdmin = super.getCurrentAdmin(request.getSession());
		 if(currentAdmin==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		 game.setLastUpdateTime(new Date());
		 game.setLastUpdateAdminId(currentAdmin.getId());
		 game.setNameEn(CommonUtil.isStrEmpty(game.getNameEn())?game.getNameCn():game.getNameEn());
		 game.setDescriptionEn(CommonUtil.isStrEmpty(game.getDescriptionEn())?game.getDescriptionCn():game.getDescriptionEn());
		 game.setPriority(game.getPriority()==null?1:game.getPriority());
		int effCnt = gameService.addGame(game);
		if(effCnt > 0){
			uploadFileService.updateFileToUsed(game.getPicFileId());
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",game);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",game);
		}
		
	
	}

	/**
	 * 获取所有games
	 * @param dtRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGameList", method = RequestMethod.POST)
	public  Result getGameList(@RequestBody DataTablesRequest dtRequest)  {
		List<Game> gameList = gameService.getGameList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,gameList,gameService.countAll(dtRequest),gameService.countAll(dtRequest));
	}
	
	
	/**
	 * 根据ID获取game
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getGame", method = RequestMethod.GET)
	public Result getGame(@RequestParam("id") int id) {
		Game game = gameService.getGame(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,game);
	}

	
	/**
	 * 更新 Game
	 * @throws IOException 
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateGame", method = RequestMethod.POST)
	public Result updateGame(@RequestBody Game game,HttpServletRequest request) {
		 Admin currentAdmin = super.getCurrentAdmin(request.getSession());
		 if(currentAdmin==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		 game.setLastUpdateTime(new Date());
		 game.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =gameService.updateGame(game);
		if(effCnt > 0){
			uploadFileService.updateFileToUsed(game.getPicFileId());
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",game);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",game);
		}
	}


	
	/**
	 * 删除 Game
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/deleteGame", method = RequestMethod.GET)
	public Result deleteGame(@RequestParam("id") int id) {
		Game game = gameService.getGame(id);
		if(game.getPicFileId()!=null){
			uploadFileService.updateFileToUnUsed(game.getPicFileId());
		}
	    if(game.getDocFileId()!=null){
	    	uploadFileService.updateFileToUnUsed(game.getDocFileId());
	    }
		int effCnt = gameService.deleteGame(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"赛事删除成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"赛事删除失败！！");
		}
	}
	
	
	
	/**
	 *  更新Game访问次数
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/addGameVisitCnt", method = RequestMethod.GET)
	public Result addGameVisitCnt(@RequestParam("id") int id) {
		int effCnt = gameService.addGameVisitCnt(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"赛事访问次数增加成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"赛事访问次数增加失败！！");
		}
	}
	
	
}
