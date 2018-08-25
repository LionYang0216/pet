package com.gzsoftware.pet.controller;


import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/portal")
public class PortalController extends BaseController{

	private static Log log = LogFactory.getLog(PortalController.class);
	
	/**
	 * 前端 -首页
	 * @return
	 */
	@RequestMapping(value = "/portalIndex", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
			//String requestUrl = request.getRemoteAddr();
			String host = request.getHeader("Host");  
			//log.warn(host);
			if(host.indexOf("petpack.com.cn")>-1){
				return "redirect:http://www.petpack.com.cn/front/frontIndex#/main/home";
			}
			if(host.indexOf("gz-software.com")>-1){
				return "redirect:http://pet.gz-software.com/front/frontIndex#/main/home";
			}
			
			// another project
			if(host.indexOf("tetrapak.gz-software.com")>-1){
				return "redirect:http://tetrapak.gz-software.com/wechat/views/tetrapak/trace.html";
			}
			
			
			
			if(true){
				//return "forward:http://www.petpack.com.cn/front/frontIndex";
			}
			
			return "/portalIndex";
	}




	
	

}
