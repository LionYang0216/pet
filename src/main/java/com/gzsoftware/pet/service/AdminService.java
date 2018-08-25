package com.gzsoftware.pet.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.controller.BackController;
import com.gzsoftware.pet.dao.AdminDao;
import com.gzsoftware.pet.entity.po.Admin;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;


@Service("adminService")
public class AdminService extends BaseService{

	private static Log log = LogFactory.getLog(AdminService.class);
	
	@Resource
	private AdminDao adminDao;

	public Admin login(Admin admin) {
		return adminDao.login(admin);
	}

	public void updateAdminLoginInfo(Admin admin) {
		adminDao.updateAdminLoginInfo(admin);
	}

	public Admin getAdmin(int id) {
		return adminDao.getAdmin(id);
	}

	public List<Admin> getAdminList(DataTablesRequest dtRequest) {
		return adminDao.getAdminList(dtRequest);
	}
	

	public Integer countAll(DataTablesRequest dtRequest) {
		return adminDao.countAll(dtRequest);
	}
	
	
	

	public int addAdmin(Admin admin) {
		return adminDao.addAdmin(admin);
	}

	public int deleteAdmin(int id) {
		return adminDao.deleteAdmin(id);
	}

	public int updateAdmin(Admin admin) {
	   return adminDao.updateAdmin(admin);
	}

}
