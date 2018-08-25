package com.gzsoftware.pet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gzsoftware.pet.entity.po.Role;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface RoleDao {
	
	public Role getRole(int id);
	
	public List<Role> getRoleList(DataTablesRequest dtRequest);

	public Integer countAll(DataTablesRequest dtRequest);

    public int addRole(Role role);

    public int deleteRole(int id);

    public int updateRole(Role role);

	public List<Role> getRoleForSelect();
    
	public List<Role> getRoleListByAdminId(int id);
}
