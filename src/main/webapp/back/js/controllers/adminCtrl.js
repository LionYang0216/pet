// list ctrl
angular.module('backApp').controller('adminListCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	$scope.$on('$viewContentLoaded', function() {  				
		var adminListDataTable = new Datatable().init({
            src: $("#adminListDataTable"),
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
    				"url" : "../admin/getAdminList", // ajax
    				"contentType": "application/json; charset=utf-8",
    				"data": function (dtRequest) {
    					  dtRequest = $.extend({}, dtRequest, {condition:$scope.adminCondition}); // merge the form data into current Query
    					  var reqData = JSON.stringify(dtRequest);
    				      return reqData;
                     }
    			},
    			"columns" : [  {
    				"data" : null
    			},{
    				"data" : "headFilePathLayout"
    			}, {
    				"data" : "account"
    			}, {
    				"data" : "adminName"
    			}, {
    				"data" : "updateTime"
    			}, {
    				"data" : "lastLoginTime"
    			}, {
    				"data" : "lastLoginIp"
    			}, {
    				"data" : "loginCount"
    			}, {
    				"data" : "roleListLayout"
    			}, {
    				"data" : "isEnabled"
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
    			},{
    				"render" : function(data, type, row, meta){
    					var str = '<div align="center"><a href="'+data+'" target="_blank"><img class="img-circle" src="'+data+'" style="height:29px; width:29px"/></a></div>';
    					return str;
    				},
    				"targets" : 1,
    				'searchable' : false,
    				'orderable' : false
    			},
    			{
    				"render" : function(data, type, row, meta) {
    					var str="";
    					
    					if(data){
    						str	= dateTimeString(data); 
    					}    			
    					return str;
    				},
    				"targets" : 4,
    				'searchable' : false,
    				'orderable' : true
    			},	{
    				"render" : function(data, type, row, meta) {
    					var str="";
    					if(data){
    						str	= dateTimeString(data); 
    					}    			
    					return str;
    				},
    				"targets" : 5,
    				'searchable' : false,
    				'orderable' : true
    			},
    			{
    				"render" : function(data, type, row, meta) {
    					var str = "";
    					if (data == 1) {
    						str = "<font color=green>启用</font>";
    					} else {
    						str = "<font color=red>禁用</font>";
    					}
    					return str;
    				},
    				"targets" : 9,
    				'searchable' : false,
    				'orderable' : false
    			},	{
    				"render" : function(data, type, row, meta) {
    					var str = '<a class="btn default" href="#/admin/form/' + data + '" >编辑</a>';
    					if(row.id ==1){
    						str = '';
    					}
    					return str;
    				},
    				"targets" : 10,
    				'searchable' : false,
    				'orderable' : false
    			} ],
    			"order" : [ [ 4, "desc" ] ]
    		// set first column as a default sort by asc
    		}
        });
    	
	});
	
	
	$scope.reloadTable = function(){
		var adminListDataTable= $('#adminListDataTable').DataTable();
		adminListDataTable.ajax.reload();
	}
	

	
}]);
	
// form ctrl
angular.module('backApp').controller('adminFormCtrl',  ['$rootScope', '$scope','$stateParams', '$state', 'settings','FileUploader', function($rootScope,$scope,$stateParams,$state, $http,FileUploader) {
	// page load: retrieve admin
	var curAdmin=$rootScope.curAdmin;
	$scope.$on('$viewContentLoaded', function() { 
		$scope.initPicFileUploader();
		if($stateParams.id > 0){ // is update
			var reqData= $stateParams;
			var url = "../admin/getAdmin";
			jsonGet(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					$scope.admin = result.data;
					$scope.getRoleSelect();
					$scope.showDelBtn = true;
					$scope.$apply();
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}else{
			$scope.admin = {};
			$scope.admin.id = 0;
			$scope.admin.headFilePathLayout ="../assets/pages/img/avatars/default.png";
			$scope.getRoleSelect();
			$scope.showDelBtn = false;
		}

	});
	
	// validate the form input while submit
	$scope.hasFormError = function(){
		var errorMsg = "";
		if($('#account').val()==""){
			errorMsg += "账户不能为空<br>";
			$('#account').parent().addClass("has-error");
		}else{
			$('#account').parent().removeClass("has-error");
		}
		if($('#password').val()==""){
			errorMsg += "密码不能为空<br>";
			$('#password').parent().addClass("has-error");
		}else{
			$('#password').parent().removeClass("has-error");
		}
		if($('#adminName').val()==""){
			errorMsg += "管理员名称不能为空<br>";
			$('#adminName').parent().addClass("has-error");
		}else{
			$('#adminName').parent().removeClass("has-error");
		}
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	}
	
	// update or add an admin
	$scope.toSaveAdmin = function(){
		var roles=$("#roleTree").jstree("get_checked"); //使用get_checked方法 
		$scope.admin.roles = roles;
		var reqData = $scope.admin;
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
		if($stateParams.id > 0){ // is update
			var url = "../admin/updateAdmin";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					if(result.data.id==curAdmin.id){
						$rootScope.curAdmin=result.data;
					}
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('adminList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}else{ // is add
			var url = "../admin/addAdmin";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('adminList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}
	}
	
	// delete an admin
	$scope.toDeleteAdmin = function(){
		if($stateParams.id){
			bootbox.confirm("是否删除?", function (result) {
				if(result){
					var reqData= $stateParams;
					var url = "../admin/deleteAdmin";
					jsonGet(url, reqData, callback, true, false);
					function callback(result) {
						if (result.flag == RESULT_FLAG_SUCCESS) {
							toastr.success(result.msg, COMMON_LABEL_SUCCESS);
							$rootScope.$state.go('adminList');
						} else {
							toastr.error(result.msg, COMMON_LABEL_ERROR);
						}
				    }
				}
			})
		}
	}
	
	//图片上传
	$scope.initPicFileUploader = function(){
		$scope.picFileUploader = new FileUploader();
		initUploader($scope.picFileUploader,callback);
		function callback(result){
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				var retFile = result.data;
				$scope.admin.headFileId = retFile.id;
				$scope.admin.headFile = retFile;
				$scope.admin.headFilePathLayout = "../upload/" + retFile.filePath;
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
		}
		
	};
	
	$scope.getRoleSelect = function(){
		var roleData = null;
		$('#roleTree').jstree({  
	        'core' : {  
	            "multiple" : true,  
	            'data' : function(obj, callback){
	            	$.ajax({  
	            	    url : '../role/getRoleForSelect',  
	            	    type : "post", 
	            	    async: false,  
	            	    data:{"adminId":$scope.admin.id},
	            	    success : function(re) {  
	            	        roleData =  re.data;
	            	    }  
	            	});
	            	 callback.call(this, roleData);
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
