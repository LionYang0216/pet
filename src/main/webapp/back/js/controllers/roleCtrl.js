// list ctrl
angular.module('backApp').controller('roleListCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	$scope.$on('$viewContentLoaded', function() {  				
		var roleListDataTable = new Datatable().init({
            src: $("#roleListDataTable"),
            dataTable: {
    			"language": dtLanguage,
    			"bStateSave" : true, // save datatable
    			"serverSide" : true,
    			"ordering" : true,// 是否允许用户排序
    			"paging" : true,
    			"lengthMenu" : [ [ 5, 10, 15, 20, 50 ],[ 5, 10, 15, 20, 50 ]],// change per page
    			"pageNumber" : 1,
    			"pageSize" : 10, // default record count per page
    			//"fnServerData": retrieveData, // 获取数据的处理函数
    			"ajax" : {
    				"type" : "POST",
    				"url" : "../role/getRoleList", // ajax
    				"contentType": "application/json; charset=utf-8",
    				"data": function (dtRequest) {
    					  dtRequest = $.extend({}, dtRequest, {condition:$scope.roleCondition}); // merge the form data into current Query
    					  var reqData = JSON.stringify(dtRequest);
    				      return reqData;
                     }
    			},
    			"columns" : [  {
    				"data" : null
    			}, {
    				"data" : "name"
    			}, {
    				"data" : "description"
    			}, {
    				"data" : "nodeListLayout"
    			},{
    				"data" : "id"
    			} ],
    			"columnDefs" : [
    			{
    				"render" : function(data, type, row, meta) {
    					var startIndex = meta.settings._iDisplayStart;
    					return startIndex + meta.row + 1;
    				},
    				"targets" : 0,
    				'searchable' : false,
    				'orderable' : false
    			},
    			{
    				"render" : function(data, type, row, meta) {
    					var str = '<a class="btn default" href="#/role/form/' + data + '">编辑</a>';
    					if(row.id ==2){
    						str = '';
    					}
    					return str;
    				},
    				"targets" : 4,
    				'searchable' : false,
    				'orderable' : false
    			} ],
    			"order" : [ [ 1, "desc" ] ]
    		// set first column as a default sort by asc
    		}
        });
    	
	});
	
	
	$scope.reloadTable = function(){
		var roleListDataTable= $('#roleListDataTable').DataTable();
		roleListDataTable.ajax.reload();
	}
	

	
}]);
	
// form ctrl
angular.module('backApp').controller('roleFormCtrl', ['$rootScope', '$scope','$stateParams','$state', 'settings', function($rootScope, $scope,$stateParams,$state, $http) {
	// page load: retrieve role
	$scope.$on('$viewContentLoaded', function() { 
		if($stateParams.id > 0){ // is update
			var reqData= $stateParams;
			var url = "../role/getRole";
			jsonGet(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					$scope.role = result.data;
					$scope.getNodeTree();
					$scope.showDelBtn = true;
					$scope.$apply();
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}else{
			$scope.role = {};
			$scope.role.id = 0;
			$scope.getNodeTree();
			$scope.showDelBtn = false;
		}
	
	});
	
	// validate the form input while submit
	$scope.hasFormError = function(){
		var errorMsg = "";
		if($('#name').val()==""){
			errorMsg += "角色名称不能为空！！<br>";
			$('#name').parent().addClass("has-error");
		}else{
			$('#name').parent().removeClass("has-error");
		}
		var nodes=$("#roleNodeTree").jstree("get_checked"); //使用get_checked方法 
		if(nodes.length == 0){
			errorMsg += "请选择角色权限！！<br>";
			$('#roleNodeTree').parent().addClass("has-error");
		}else{
			$('#roleNodeTree').parent().removeClass("has-error");
		}
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	}
	
	// update or add an role
	$scope.toSaveRole = function(){
		var nodes=$("#roleNodeTree").jstree("get_checked"); //使用get_checked方法 
		$scope.role.nodes = nodes;
		var reqData = $scope.role;
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
		if($stateParams.id > 0){ // is update
			var url = "../role/updateRole";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('roleList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}else{ // is add
			var url = "../role/addRole";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('roleList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}
	}
	
	// delete an role
	$scope.toDeleteRole = function(){
		if($stateParams.id){
			bootbox.confirm("是否删除?", function (result) {
				if(result){
					var reqData= $stateParams;
					var url = "../role/deleteRole";
					jsonGet(url, reqData, callback, true, false);
					function callback(result) {
						if (result.flag == RESULT_FLAG_SUCCESS) {
							toastr.success(result.msg, COMMON_LABEL_SUCCESS);
							$rootScope.$state.go('roleList');
						} else {
							toastr.error(result.msg, COMMON_LABEL_ERROR);
						}
				    }
				}
			})
		}
	}
	
	$scope.getNodeTree = function(){
		var ay_mssys = null;
		$('#roleNodeTree').jstree({  
	        'core' : {  
	            "multiple" : true,  
	            'data' : function(obj, callback){
	            	$.ajax({  
	            	    url : '../node/getNodeTree',  
	            	    type : "post", async: false,  
	            	    data:{"roleId":$scope.role.id},
	            	    success : function(re) {  
	            	        ay_mssys =  re.data;
	            	    }  
	            	});
	            	 callback.call(this, ay_mssys);
	            },  
	            'dblclick_toggle': false,        //禁用tree的双击展开  
	            "checkbox" : {  
	                "keep_selected_style" : false  
	              }, 
	        },  
	        "plugins" : ["search","checkbox"]   
	 });
		
	}
}]);
