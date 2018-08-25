package com.gzsoftware.pet.controller;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzsoftware.pet.entity.po.Admin;
import com.gzsoftware.pet.entity.po.Node;
import com.gzsoftware.pet.entity.po.Role;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.entity.vo.JsTreeNode;
import com.gzsoftware.pet.service.NodeService;
import com.gzsoftware.pet.service.RoleService;
import com.gzsoftware.pet.utils.CommonUtil;
import com.gzsoftware.pet.utils.MapBeanUtil;

@Controller
@RequestMapping("node")
public class NodeController extends BaseController{

	private static Log log = LogFactory.getLog(NodeController.class);
	
	@Resource
	private NodeService nodeService;
	
	@Resource
	private RoleService roleService;
	
	/**
	 * 获取所有Node
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getNodeList", method = RequestMethod.POST)
	public  Result getNodeList(@RequestBody DataTablesRequest dtRequest)  {
		List<Node> nodeList = nodeService.getNodeList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,nodeList,nodeService.countAll(dtRequest),nodeService.countAll(dtRequest));
	}
	
	
	/**
	 * 根据ID获取Node
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getNode", method = RequestMethod.GET)
	public Result getNode(@RequestParam("id") int id) {
		Node node = nodeService.getNode(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,node);
	}

	
	/**
	 * 新增 Node
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addNode", method = RequestMethod.POST)
	public Result addNode(@RequestBody Node node) {
		int effCnt = nodeService.addNode(node);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",node);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",node);
		}
		
	
	}
	
	
	/**
	 * 更新 Node
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateNode", method = RequestMethod.POST)
	public Result updateNode(@RequestBody Node node) {
		int effCnt =nodeService.updateNode(node);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",node);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",node);
		}
	}
	
	
	/**
	 * 删除 Node
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteNode", method = RequestMethod.GET)
	public Result deleteNode(@RequestParam("id") int id) {
		int effCnt = nodeService.deleteNode(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
	/**
	 *获取菜单 Node
	 * **/
	@ResponseBody
	@RequestMapping(value = "/getNodeIsMenu", method = RequestMethod.GET)
	public Result getNodeIsMenu(){
		List<Node> nodeList = this.nodeService.getNodeIsMenu();
		return new Result(Result.RESULT_FLAG_SUCCESS,nodeList);
	}
	
	
	/**
	 * 
	 * 获取树用户 导航
	 */
	@ResponseBody
	@RequestMapping(value = "/getParentList", method = RequestMethod.POST)
	public  Result getParentList(HttpServletRequest request)  {
		 Admin currentAdmin = super.getCurrentAdmin(request.getSession());
		 if(currentAdmin==null){
			 return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登陆！！");
		 }
		List<Role> roleList = currentAdmin.getRoleList();
		List<Node> nodeParentList = new ArrayList<Node>();
		Set<Node> nodeSet = new HashSet<Node>();
		if(!roleList.isEmpty()){
			for(Role role : roleList){
				if(!role.getNodeList().isEmpty()){
					 for(Node node : role.getNodeList()){
						   nodeSet.add(node);
					   }
				}
			}
		}
		if(!nodeSet.isEmpty()){
			for(Node node : nodeSet){
				if(node.getParent()==null){
					nodeParentList.add(node);
					List<Node> nodeChilrenList = nodeService.getChildrenNodeList(node.getId());
					node.setChildrenNodeList(nodeChilrenList);
				}
			}
		}
		Collections.sort(nodeParentList,new Comparator<Node>(){
			 public int compare(Node o1, Node o2) {  
		          
		            //按照学生的年龄进行升序排列  
		            if(o1.getPriority() > o2.getPriority()){  
		                return 1;  
		            }  
		            if(o1.getPriority() == o2.getPriority()){  
		                return 0;  
		            }  
		            return -1;  
		        }  
		});
		return new Result(Result.RESULT_FLAG_SUCCESS,nodeParentList);
	}
	
	/**
	 * 根据角色获取树节点
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getNodeTree", method = RequestMethod.POST)
	public Result getNodeTree(@RequestParam("roleId") int roleId){
		List<Node> nodeList = nodeService.getParentList();
		List<JsTreeNode> nodeTreeList = nodeService.getNodeTree(nodeList);
		if(roleId > 0){
			Role role = this.roleService.getRole(roleId);
			if(role!=null){
				List<Node> list = role.getNodeList();
				for(Node n : list){
					this.setNodeChilrenTree(nodeTreeList, n);
				}
			}
		}
		return new Result(Result.RESULT_FLAG_SUCCESS,nodeTreeList);
	}


	/**
	 * 迭代设置子节点
	 * @param nodeTreeList
	 * @param n
	 */
	private void setNodeChilrenTree(List<JsTreeNode> nodeTreeList, Node n) {
		for(JsTreeNode tree : nodeTreeList){
			HashMap state = new HashMap<String , Object>();
			if(tree.getId().equals(n.getId().toString())){
				state.put("selected", true);
				tree.setState(state);
			}
			if(tree.getChildren()!=null){
				this.setNodeChilrenTree(tree.getChildren(), n);
			}
		}
	}

}
