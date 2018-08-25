package com.gzsoftware.pet.controller;

import java.io.IOException;
import java.util.Date;
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
import com.gzsoftware.pet.entity.po.Marquee;
import com.gzsoftware.pet.entity.po.UploadFile;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.MarqueeService;
import com.gzsoftware.pet.service.UploadFileService;
import com.gzsoftware.pet.utils.CommonUtil;

@Controller
@RequestMapping("marquee")
public class MarqueeController extends BaseController{

	private static Log log = LogFactory.getLog(MarqueeController.class);
	
	@Resource
	private MarqueeService marqueeService;
	
	@Resource
	private UploadFileService uploadFileService;
	
	
	
	/**
	 * 新增 Marquee
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addMarquee", method = RequestMethod.POST)
	public Result addMarquee(@RequestBody Marquee marquee,HttpServletRequest request) {
		 Admin currentAdmin = super.getCurrentAdmin(request.getSession());
		 if(currentAdmin==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		 marquee.setLastUpdateAdminId(currentAdmin.getId());
		 marquee.setLastUpdateTime(new Date());
		 marquee.setNameEn(CommonUtil.isStrEmpty(marquee.getNameEn())?marquee.getNameCn():marquee.getNameEn()); 
		int effCnt = marqueeService.addMarquee(marquee);
		if(effCnt > 0){
			if(marquee.getPicFileId()!=null){
				this.uploadFileService.updateFileToUsed(marquee.getPicFileId());
			}
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",marquee);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",marquee);
		}
		
	
	}

	
	/**
	 * 获取所有Marquees
	 * @param dtRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMarqueeList", method = RequestMethod.POST)
	public  Result getMarqueeList(@RequestBody DataTablesRequest dtRequest)  {
		List<Marquee> marqueeList = marqueeService.getMarqueeList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,marqueeList,marqueeService.countAll(dtRequest),marqueeService.countAll(dtRequest));
	}
	
	/**
	 * @param dtRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMarqueeSelect",method = RequestMethod.POST)
	public  Result getMarqueeSelect(@RequestParam("code") String code)  {
		List<Marquee> marqueeList = marqueeService.getMarqueeSelect(code);
		return new Result(Result.RESULT_FLAG_SUCCESS,marqueeList);
	}
	
	/**
	 * 根据ID获取Marquee
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getMarquee", method = RequestMethod.GET)
	public Result getMarquee(@RequestParam("id") int id) {
		Marquee marquee = marqueeService.getMarquee(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,marquee);
	}

	
	/**
	 * 更新 Marquee
	 * @throws IOException 
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateMarquee", method = RequestMethod.POST)
	public Result updateAdmin(@RequestBody Marquee marquee,HttpServletRequest request) {
		 Admin currentAdmin = super.getCurrentAdmin(request.getSession());
		 if(currentAdmin==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		 marquee.setLastUpdateTime(new Date());
		 marquee.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = marqueeService.updateMarquee(marquee);
		if(effCnt > 0){
			if(marquee.getPicFileId()!=null){
				this.uploadFileService.updateFileToUsed(marquee.getPicFileId());
			}
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",marquee);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",marquee);
		}
	}


	
	/**
	 * 删除 Marquee
	 * **/
	
	@ResponseBody
	@RequestMapping(value = "/deleteMarquee", method = RequestMethod.GET)
	public Result deleteMarquee(@RequestParam("id") int id) {
		Marquee marquee = marqueeService.getMarquee(id);
		if(marquee.getPicFileId()!= null){
			uploadFileService.updateFileToUnUsed(marquee.getPicFileId());
		}
		int effCnt = marqueeService.deleteMarquee(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"广告删除成功！！");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"广告删除失败！！");
		}
	}
	
}
