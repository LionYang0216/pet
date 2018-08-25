package com.gzsoftware.pet.entity.po;

import java.util.Date;
import java.util.List;

import com.gzsoftware.pet.utils.CommonUtil;


public class Role extends BaseEntity{

	
	private Integer id;

	private String name;
	
	private String description;
	
	private String nodeListLayout;
	
	private String[] nodes;
	
    public String[] getNodes() {
		return nodes;
	}

	private List<Node> nodeList;
    

	public String getNodeListLayout() {
		String nodeListLayout="";
		if(!this.getNodeList().isEmpty()){
			for (Node n : this.getNodeList()){
				nodeListLayout += n.getName() + ",";
			}

		}
		if(!nodeListLayout.equals("")){
			nodeListLayout = nodeListLayout.substring(0,nodeListLayout.length()-1);
		}
		
		return nodeListLayout;
	}

	public List<Node> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
