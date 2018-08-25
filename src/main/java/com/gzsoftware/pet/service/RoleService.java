package com.gzsoftware.pet.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.controller.BackController;
import com.gzsoftware.pet.dao.RoleDao;
import com.gzsoftware.pet.entity.po.Role;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;


@Service("roleService")
public class RoleService extends BaseService{

	private static Log log = LogFactory.getLog(RoleService.class);
	
	@Resource
	private RoleDao roleDao;


	public Role getRole(int id) {
		return roleDao.getRole(id);
	}

	public List<Role> getRoleList(DataTablesRequest dtRequest) {
		return roleDao.getRoleList(dtRequest);
	}
	

	public Integer countAll(DataTablesRequest dtRequest) {
		return roleDao.countAll(dtRequest);
	}
	

	public int addRole(Role role) {
		return roleDao.addRole(role);
	}

	public int deleteRole(int id) {
		return roleDao.deleteRole(id);
	}

	public int updateRole(Role role) {
	   return roleDao.updateRole(role);
	}

	public List<Role> getRoleForSelect() {
		return roleDao.getRoleForSelect();
	}

}
