package com.gzsoftware.pet.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.controller.BackController;
import com.gzsoftware.pet.dao.RoleDao;
import com.gzsoftware.pet.dao.RoleNodeDao;
import com.gzsoftware.pet.entity.po.Role;
import com.gzsoftware.pet.entity.po.RoleNode;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;


@Service("roleNodeService")
public class RoleNodeService extends BaseService{

	private static Log log = LogFactory.getLog(RoleNodeService.class);
	
	@Resource
	private RoleNodeDao roleNodeDao;



	public List<RoleNode> getRoleList(DataTablesRequest dtRequest) {
		return roleNodeDao.getRoleNodeList(dtRequest);
	}
	

	public int addRoleNode(RoleNode roleNode) {
		return roleNodeDao.addRoleNode(roleNode);
	}

	public int deleteRoleNode(int id) {
		return roleNodeDao.deleteRoleNode(id);
	}

	public int updateRoleNode(RoleNode roleNode) {
	   return roleNodeDao.updateRoleNode(roleNode);
	}


	public int deleteRoleNodeByRoleId(Integer id) {
		return roleNodeDao.deleteRoleNodeByRoleId(id);
		
	}

}
