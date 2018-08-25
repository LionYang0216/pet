package com.gzsoftware.pet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gzsoftware.pet.entity.po.Admin;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface AdminDao {
	
	public Admin login(Admin admin);
	
	public void updateAdminLoginInfo(Admin admin);
	
	public Admin getAdmin(int id);
	
	public Admin getSmAdmin(int id);
	
	public List<Admin> getAdminList(DataTablesRequest dtRequest);

	public Integer countAll(DataTablesRequest dtRequest);

    public int addAdmin(Admin admin);

    public int deleteAdmin(int id);

    public int updateAdmin(Admin admin);
    
    
}
