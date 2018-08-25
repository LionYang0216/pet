package com.gzsoftware.pet.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.controller.BackController;
import com.gzsoftware.pet.dao.AdminRoleDao;
import com.gzsoftware.pet.entity.po.AdminRole;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;


@Service("adminRoleService")
public class AdminRoleService extends BaseService{

	private static Log log = LogFactory.getLog(AdminRoleService.class);
	
	@Resource
	private AdminRoleDao adminRoleDao;

	public List<AdminRole> getAdminRoleList(DataTablesRequest dtRequest) {
		return adminRoleDao.getAdminRoleList(dtRequest);
	}
	

	public int addAdminRole(AdminRole adminRole) {
		return adminRoleDao.addAdminRole(adminRole);
	}

	public int deleteAdminRole(int id) {
		return adminRoleDao.deleteAdminRole(id);
	}

	public int updateAdminRole(AdminRole adminRole) {
	   return adminRoleDao.updateAdminRole(adminRole);
	}


	public int deleteadminRoleByAdminId(Integer id) {
		return adminRoleDao.deleteAdminRoleByAdminId(id);
		
	}
	public List<AdminRole> getAminRoleListByAdminId(Integer id) {
		return adminRoleDao.getAminRoleListByAdminId(id);
		
	}
	

}
