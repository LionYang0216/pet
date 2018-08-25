package com.gzsoftware.pet.controller;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.support.SessionStatus;

import com.gzsoftware.pet.entity.po.AdminRole;
import com.gzsoftware.pet.entity.po.Node;
import com.gzsoftware.pet.entity.po.Role;
import com.gzsoftware.pet.entity.po.RoleNode;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.entity.vo.JsTreeNode;
import com.gzsoftware.pet.service.AdminRoleService;
import com.gzsoftware.pet.service.NodeService;
import com.gzsoftware.pet.service.RoleNodeService;
import com.gzsoftware.pet.service.RoleService;
import com.gzsoftware.pet.utils.MapBeanUtil;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController{

	private static Log log = LogFactory.getLog(RoleController.class);
	
	@Resource
	private RoleService roleService;
	@Resource
	private RoleNodeService roleNodeService;
	@Resource
	private NodeService nodeService;
	

	@Resource
	private AdminRoleService adminRoleService;
	
	
	/**
	 * 获取所有Role
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
	public  Result getRoleList(@RequestBody DataTablesRequest dtRequest)  {
		List<Role> RoleList = roleService.getRoleList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,RoleList,roleService.countAll(dtRequest),roleService.countAll(dtRequest));
	}
	
	
	/**
	 * 根据ID获取Role
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getRole", method = RequestMethod.GET)
	public Result getRole(@RequestParam("id") int id) {
		Role role = roleService.getRole(id);
		List<JsTreeNode> nodeTreeList =  this.nodeService.getNodeTree(role.getNodeList());
		return new Result(Result.RESULT_FLAG_SUCCESS,role);
	}

	
	/**
	 * 新增 Role
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public Result addRole(@RequestBody Role role) {
		int effCnt = roleService.addRole(role);
		String[] nodeIds = role.getNodes();
		if(effCnt > 0){
			if(nodeIds.length > 0 ){
				for(String nodeId : nodeIds ){
					RoleNode roleNode = new RoleNode();
					roleNode.setRoleId(role.getId());
					roleNode.setNodeId(Integer.parseInt(nodeId));
					roleNodeService.addRoleNode(roleNode);
				}
			}
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",null);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",null);
		}
		
	
	}
	
	
	/**
	 * 更新 Role
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public Result updateRole(@RequestBody Role role) {
		int effCnt = roleService.updateRole(role);
		String[] nodeIds = role.getNodes();
		if(effCnt > 0){
			roleNodeService.deleteRoleNodeByRoleId(role.getId());
			if(nodeIds.length > 0 ){
				for(String nodeId : nodeIds ){
					RoleNode roleNode = new RoleNode();
					roleNode.setRoleId(role.getId());
					roleNode.setNodeId(Integer.parseInt(nodeId));
					roleNodeService.addRoleNode(roleNode);
				}
			}
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",role);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",role);
		}
	}
	
	
	/**
	 * 删除 Role
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteRole", method = RequestMethod.GET)
	public Result deleteRole(@RequestParam("id") int id) {
		roleNodeService.deleteRoleNodeByRoleId(id);
		int effCnt = roleService.deleteRole(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getRoleForSelect", method = RequestMethod.POST)
	public Result getRoleForSelect(@RequestParam("adminId") int adminId){
	    List<Role> roleList = this.roleService.getRoleForSelect();
		List<JsTreeNode> roleTreeList = new ArrayList<JsTreeNode>();
		
		if(!roleList.isEmpty()){
			for(Role role : roleList){
			    JsTreeNode node = new JsTreeNode();
			    HashMap state = new HashMap<String , Object>();
			    node.setId(role.getId().toString());
			    node.setText(role.getName());
			    node.setIcon("icon-user");
			    state.put("opened", true);
				if(adminId>0){
					List<AdminRole> adminRoleList = this.adminRoleService.getAminRoleListByAdminId(adminId);
					if(!adminRoleList.isEmpty()){
						for(AdminRole adminRole : adminRoleList){
							if(adminRole.getRoleId().equals(role.getId())){
								state.put("selected", true);
							}
							
						}
					}
				}
			    node.setState(state);
			    roleTreeList.add(node);
			}
		}
	    return new Result(Result.RESULT_FLAG_SUCCESS,roleTreeList);
		
	}
	
	
	
	/*@ResponseBody
	@RequestMapping(value = "/getRoleNodeByRoleId", method = RequestMethod.GET)
    public Result getRoleNodeByRoleId(@RequestParam("id") int id){
	  Role role = roleService.getRole(id);
	  List<jsTree> nodeTreeList =  this.nodeService.getNodeTree(role.getNodeList());
	  
	return null;
	  
  }*/

}
