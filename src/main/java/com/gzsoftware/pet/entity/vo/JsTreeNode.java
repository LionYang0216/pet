package com.gzsoftware.pet.entity.vo;

import java.util.HashMap;
import java.util.List;

/**
 * For Backend JS Tree
 * @author pango leung
 *
 */
public class JsTreeNode {
	
	private String id;
	private String text;
	private String icon;
	private HashMap<String, Object> state;
	private List<JsTreeNode> children;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public HashMap<String, Object> getState() {
		return state;
	}
	public void setState(HashMap<String, Object> state) {
		this.state = state;
	}
	public List<JsTreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<JsTreeNode> children) {
		this.children = children;
	}
	
	
	

}
