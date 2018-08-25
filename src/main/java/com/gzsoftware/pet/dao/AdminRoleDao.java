package com.gzsoftware.pet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gzsoftware.pet.entity.po.AdminRole;
import com.gzsoftware.pet.entity.po.Role;
import com.gzsoftware.pet.entity.po.RoleNode;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface AdminRoleDao {
	
	
	public List<AdminRole> getAdminRoleList(DataTablesRequest dtRequest);


    public int addAdminRole(AdminRole adminRole);

    public int deleteAdminRole(int id);

    public int updateAdminRole(AdminRole adminRole);


	public int deleteAdminRoleByAdminId(Integer id);


	public List<AdminRole> getAminRoleListByAdminId(Integer id);
    
    
}
