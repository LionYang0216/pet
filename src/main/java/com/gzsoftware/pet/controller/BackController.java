package com.gzsoftware.pet.controller;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.iterators.ObjectArrayIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gzsoftware.pet.entity.po.Admin;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.AdminService;
import com.gzsoftware.pet.utils.CommonUtil;
/**
 * base functions of ctrl put here
 * @author pango leung
 *
 */
@Controller
@RequestMapping("/back")
public class BackController extends BaseController{

	private static Log log = LogFactory.getLog(BackController.class);
	
	@Resource
	private AdminService adminService;
	

	/**
	 * 后台框架页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/backIndex", method = RequestMethod.GET)
	public String backIndex(HttpSession session) {
		// 如果没有session 重定向到login
		if (getCurrentAdmin(session)==null) {
			return "/login";
		} else {
			return "/backIndex";
		}

	}

	/**
	 * 后台登录页
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public String login() {
		return "/login";
	}
	
	


}
