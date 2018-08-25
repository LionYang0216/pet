package com.gzsoftware.pet.controller;


import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * 这里放前端页面
 * @author pango leung
 *
 */
@Controller
@RequestMapping("/front")
public class FrontController extends BaseController{

	private static Log log = LogFactory.getLog(FrontController.class);
	
	/**
	 * 前端 -首页
	 * @return
	 */
	@RequestMapping(value = "/frontIndex", method = RequestMethod.GET)
	public String frontIndex(HttpSession session) {
			return "/frontIndex";
	}

	


	
	

}
