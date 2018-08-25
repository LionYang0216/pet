package com.gzsoftware.pet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gzsoftware.pet.entity.po.Node;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

public interface NodeDao {
	
	public Node getNode(int id);
	
	public List<Node> getNodeList(DataTablesRequest dtRequest);

	public Integer countAll(DataTablesRequest dtRequest);

    public int addNode(Node node);

    public int deleteNode(int id);

    public int updateNode(Node node);

	public List<Node> getNodeIsMenu();

	public List<Node> getChildrenNodeList(int id);

	public List<Node> getParentList();
	
	public List<Node> getNodeListByRoleId(int id);

	public List<Node> getAllList();
    
    
}
