package com.gzsoftware.pet.controller;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.gzsoftware.pet.entity.po.Admin;
import com.gzsoftware.pet.entity.po.AdminRole;
import com.gzsoftware.pet.entity.po.RoleNode;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.AdminRoleService;
import com.gzsoftware.pet.service.AdminService;
import com.gzsoftware.pet.service.UploadFileService;
import com.gzsoftware.pet.utils.MapBeanUtil;

@Controller
@RequestMapping("admin")
public class AdminController extends BaseController{

	private static Log log = LogFactory.getLog(AdminController.class);
	
	@Resource
	private AdminService adminService;
	@Resource
	private UploadFileService uploadFileService;
	

	@Resource
	private AdminRoleService adminRoleService;
	
	/**
	 * 获取所有admin
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getAdminList", method = RequestMethod.POST)
	public  Result getAdminList(@RequestBody DataTablesRequest dtRequest)  {
		List<Admin> adminList = adminService.getAdminList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,adminList,adminService.countAll(dtRequest),adminService.countAll(dtRequest));
	}
	
	
	/**
	 * 根据ID获取admin
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getAdmin", method = RequestMethod.GET)
	public Result getAdmin(@RequestParam("id") int id) {
		Admin admin = adminService.getAdmin(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,admin);
	}

	
	/**
	 * 新增 Admin
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
	public Result addAdmin(@RequestBody Admin admin) {
		admin.setUpdateTime(new Date());
		admin.setLoginCount(0);
		String[] roleIds = admin.getRoles();
		int effCnt = adminService.addAdmin(admin);
		if(effCnt > 0){
			if(roleIds.length > 0 ){
				for(String roleId : roleIds ){
					AdminRole adminRole = new AdminRole();
					adminRole.setRoleId(Integer.parseInt(roleId));
					adminRole.setAdminId(admin.getId());
					adminRoleService.addAdminRole(adminRole);
				}
			}
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",admin);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",admin);
		}
		
	
	}
	
	
	/**
	 * 更新 Admin
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateAdmin", method = RequestMethod.POST)
	public Result updateAdmin(@RequestBody Admin admin) {
		admin.setUpdateTime(new Date());
		String[] roleIds = admin.getRoles();
		int effCnt =adminService.updateAdmin(admin);
		if(effCnt > 0){
			adminRoleService.deleteadminRoleByAdminId(admin.getId());
			if(roleIds.length > 0 ){
				for(String roleId : roleIds ){
					AdminRole adminRole = new AdminRole();
					adminRole.setRoleId(Integer.parseInt(roleId));
					adminRole.setAdminId(admin.getId());
					adminRoleService.addAdminRole(adminRole);
				}
			}
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",admin);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",admin);
		}
	}
	
	
	/**
	 * 删除 Admin
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteAdmin", method = RequestMethod.GET)
	public Result deleteAdmin(@RequestParam("id") int id) {
		adminRoleService.deleteadminRoleByAdminId(id);
		int effCnt = adminService.deleteAdmin(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
	
	/**
	 * 后台登出
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	public Result loginout(HttpSession session,SessionStatus sessionStatus) {
		//清除所有session
		session.removeAttribute("admin");
		sessionStatus.setComplete();
		return new Result(Result.RESULT_FLAG_SUCCESS,"登出成功");
	}
	

	/**
	 * @RequestParam根据参数名从URL中取得参数值
	 * @param username
	 * @param password
	 * @param model
	 *            一个域对象，可用于存储数据值
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/toLogin", method = RequestMethod.POST) // @RequestMapping 注解可以用指定的URL路径访问本控制层
	public Result login(@RequestBody Admin admin, HttpSession session)throws IOException {
		Admin validAdmin = adminService.login(admin);
		if (validAdmin!=null) {
			validAdmin.setLastLoginTime(new Date());
			validAdmin.setLastLoginIp(getRequestIP());
			adminService.updateAdminLoginInfo(validAdmin); // 更新登录者的IP和登录数
			setCurrentAdmin(session,validAdmin); // 保存session
			log.info("帐号:" + admin.getAccount() + " 密码：" +admin.getPassword() + " 登录成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"登录成功,欢迎您回来: " + validAdmin.getAdminName() ,validAdmin);
		} else {
			log.info("帐号:" + admin.getAccount() + " 密码：" +admin.getPassword() + " 登录失败");
			return new Result(Result.RESULT_FLAG_FAIL,"登录失败，帐号或密码有错");
		}
	}
	
	/**
	 * 返回session admin to json
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getSessionAdmin", method = RequestMethod.GET) // @RequestMapping 注解可以用指定的URL路径访问本控制层
	public Result getSessionAdmin(HttpSession session)throws IOException {
		Admin currentAdmin = super.getCurrentAdmin(session);
		if (currentAdmin!=null) {
			return new Result(Result.RESULT_FLAG_SUCCESS,currentAdmin);
		} else {
			return new Result(Result.RESULT_FLAG_FAIL, "get session admin failed");
		}
	}

}
