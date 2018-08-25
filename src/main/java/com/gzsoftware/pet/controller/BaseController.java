package com.gzsoftware.pet.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gzsoftware.pet.entity.po.Admin;
import com.gzsoftware.pet.entity.po.User;

/**
 * root class of controllers
 * @author pango leung
 *
 */
public class BaseController {

	private static Log log = LogFactory.getLog(BaseController.class);
	
	/**
	 * 返回 session admin
	 * @return
	 */
	public Admin getCurrentAdmin(HttpSession session){
		if(session.getAttribute("admin")!=null){
			return (Admin) session.getAttribute("admin");
		}else{
			return null;
		}
	}
	
	/**
	 * 检查后台管理员是否有登录
	 * @param session
	 * @return
	 */
	public boolean isCurrentAdminExist(HttpSession session){
		if(this.getCurrentAdmin(session)!=null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 设置session admin
	 * @param session
	 * @param admin
	 */
	public void setCurrentAdmin(HttpSession session,Admin admin){
		if(session!=null){
			session.setAttribute("admin", admin);
		}
	}
	
	/**
	 * 返回前台已登录的user
	 * @param session
	 * @return
	 */
	public User getCurrentUser(HttpSession session){
		if(session.getAttribute("user")!=null){
			return (User) session.getAttribute("user");
		}else{
			return null;
		}
	}
	
	/**
	 * 检查前台用户是否有登录
	 * @param session
	 * @return
	 */
	public boolean isCurrentUserExist(HttpSession session){
		if(this.getCurrentUser(session)!=null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 设置前台登录的session user
	 * @param session
	 * @param user
	 */
	public void setCurrentUser(HttpSession session,User user){
		if(session!=null){
			session.setAttribute("user", user);
		}
	}
	
	
	
	
	
	/**
	 * get the request ip address
	 * @return ex: "192.168.1.2"
	 */
	public static String getRequestIP(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		 String ip = request.getHeader("x-forwarded-for");   
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	           ip = request.getHeader("Proxy-Client-IP");   
	       }   
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	           ip = request.getHeader("WL-Proxy-Client-IP");   
	       }   
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	           ip = request.getRemoteAddr();   
	           if(ip.equals("127.0.0.1")){     
	               //根据网卡取本机配置的IP     
	               InetAddress inet=null;     
	               try {     
	                   inet = InetAddress.getLocalHost();     
	               } catch (UnknownHostException e) {     
	                   e.printStackTrace();     
	               }     
	               ip= inet.getHostAddress();     
	           }  
	       }   
	       // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
	       if(ip != null && ip.length() > 15){    
	           if(ip.indexOf(",")>0){     
	               ip = ip.substring(0,ip.indexOf(","));     
	           }     
	       }     
	       return ip;  
	}
	
	/**
	 * get the web http root path
	 * @return ex: "http://127.0.0.1:8888/pet/"
	 */
	public static String getWebHttpRoot(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		StringBuffer url = request.getRequestURL();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString() + "" + request.getContextPath() + "/";
	}
	
	/**
	 * return the web system root physical path
	 * @return ex: "D://xxx//xxx//xxxxx//pet"
	 */
	public static String getWebLocalRoot(){
		String root = System.getProperty("web.local.root"); // 获取系统根路径
		return root;
	}
}
