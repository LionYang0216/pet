package com.gzsoftware.pet.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.controller.BackController;
import com.gzsoftware.pet.dao.NodeDao;
import com.gzsoftware.pet.entity.po.Node;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.JsTreeNode;


@Service("nodeService")
public class NodeService extends BaseService{

	private static Log log = LogFactory.getLog(NodeService.class);
	
	@Resource
	private NodeDao nodeDao;


	public Node getNode(int id) {
		return nodeDao.getNode(id);
	}

	public List<Node> getNodeList(DataTablesRequest dtRequest) {
		return nodeDao.getNodeList(dtRequest);
	}
	

	public Integer countAll(DataTablesRequest dtRequest) {
		return nodeDao.countAll(dtRequest);
	}
	

	public int addNode(Node Node) {
		return nodeDao.addNode(Node);
	}

	public int deleteNode(int id) {
		return nodeDao.deleteNode(id);
	}

	public int updateNode(Node Node) {
	   return nodeDao.updateNode(Node);
	}

	public List<Node> getNodeIsMenu() {
		return nodeDao.getNodeIsMenu();
	}

	public List<Node> getChildrenNodeList(int id) {
		return nodeDao.getChildrenNodeList(id);
	}

	public List<Node> getParentList() {
		return nodeDao.getParentList();
	}

	public List<Node> getAllList() {
		return nodeDao.getAllList();
	}

    public List<JsTreeNode> getNodeTree(List<Node> nodeList){
    	List<JsTreeNode> nodeTreeList = new ArrayList<JsTreeNode>();
        if(!nodeList.isEmpty()){
		for(Node node : nodeList){
		    JsTreeNode tree = new JsTreeNode();
		    HashMap state = new HashMap<String , Object>();
		    tree.setId(node.getId().toString());
		    tree.setText(node.getName());
		    tree.setIcon(node.getIcon());
		    state.put("opened", false);
		    tree.setState(state);
		    List<Node> nodeChilrenList = this.getChildrenNodeList(node.getId());
		    if(!nodeChilrenList.isEmpty()){
		        this.nodeChilren(nodeChilrenList, tree);
		    }
		    nodeTreeList.add(tree);
		}
    	
    }
		return nodeTreeList;
  }
    
    /**
	 * 递归tree
	 * @param nodeChilrenList
	 * @param tree
	 */
	private void nodeChilren(List<Node> nodeChilrenList, JsTreeNode tree) {
		List<JsTreeNode> nodeTreeChilrenList = new ArrayList<JsTreeNode>();
		for(Node node : nodeChilrenList){
			 HashMap state = new HashMap<String , Object>();
			 JsTreeNode treeChilren = new JsTreeNode();
			 treeChilren.setId(node.getId().toString());
			 treeChilren.setText(node.getName());
			 treeChilren.setIcon(node.getIcon());
			 state.put("opened", true);
			 treeChilren.setState(state);
			 nodeTreeChilrenList.add(treeChilren);
			 List<Node> nodeChilrenList2 = this.getChildrenNodeList(node.getId());
			 if(!nodeChilrenList2.isEmpty()){
				 this.nodeChilren(nodeChilrenList2, treeChilren);
			 }
		}
		tree.setChildren(nodeTreeChilrenList);
	}
}
