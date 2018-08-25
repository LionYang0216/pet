package com.gzsoftware.pet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gzsoftware.pet.entity.po.Role;
import com.gzsoftware.pet.entity.po.RoleNode;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface RoleNodeDao {
	
	
	public List<RoleNode> getRoleNodeList(DataTablesRequest dtRequest);


    public int addRoleNode(RoleNode roleNode);

    public int deleteRoleNode(int id);

    public int updateRoleNode(RoleNode roleNode);


	public int deleteRoleNodeByRoleId(Integer id);
    
    
}
