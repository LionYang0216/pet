// list ctrl
angular.module('backApp').controller('userListCtrl', ['$rootScope', '$scope', 'settings','$stateParams', function($rootScope, $scope, $http,$stateParams) {
	$scope.$on('$viewContentLoaded', function() {  		
		if($stateParams.filterUserId > 0){
		 $scope.userCondition ={id:$stateParams.filterUserId};
		}
		var userListDataTable = new Datatable().init({
            src: $("#userListDataTable"),
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
    				"url" : "../user/getUserList", // ajax
    				"contentType": "application/json; charset=utf-8",
    				"data": function (dtRequest) {
    					  dtRequest = $.extend({}, dtRequest, {condition:$scope.userCondition}); // merge the form data into current Query
    					  var reqData = JSON.stringify(dtRequest);
    				      return reqData;
                     }
    			},
    			"columns" : [  {
    				"data" : null
    			},{
    				"data" : "headFilePathLayout"
    			},{
    				"data" : "userName"
    			}, {
    				"data" : "sexLayout"
    			}, {
    				"data" : "account"
    			}, {
    				"data" : "balanceTotal"
    			},{
    				"data" : "regTime"
    			},{
    				"data" : "lastLoginIp"
    			},{
    				"data" : "lastLoginTime"
    			},{
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
    				"render" : function(data, type, row, meta){
    					var str = '<a href="#/userBalanceLog/list/' + row.id + '">'+ data +'</a>';
    					return str;
    				},
    				"targets" : 5,
    				'searchable' : false,
    				'orderable' : false
    			}
    			,{
    				"render" : function(data, type, row, meta) {
    					var str="";
    					
    					if(data){
    						str	= dateTimeString(data); 
    					}    			
    					return str;
    				},
    				"targets" : 6,
    				'searchable' : false,
    				'orderable' : true
    			},
    			{
    				"render" : function(data, type, row, meta) {
    					var str="";
    					
    					if(data){
    						str	= dateTimeString(data); 
    					}    			
    					return str;
    				},
    				"targets" : 8,
    				'searchable' : false,
    				'orderable' : true
    			},{
    				"render" : function(data, type, row, meta) {
    					var str = "";
    					if (data == 1) {
    						str = "<font color=green>启用</font>";
    					} else {
    						str = "<font color=red>禁用</font>";
    					}
    					return str;
    				},
    				"targets" :9,
    				'searchable' : false,
    				'orderable' : false
    			},	{
    				"render" : function(data, type, row, meta) {
    					var str = '<a class="btn default" href="#/user/form/' + data + '">编辑</a>';
    					return str;
    				},
    				"targets" : 10,
    				'searchable' : false,
    				'orderable' : false
    			} ],
    			"order" : [ [ 8, "desc" ] ]
    		// set first column as a default sort by asc
    		}
        });
    	
	});
	$scope.reloadTable = function(){
		if(($scope.userCondition!=undefined)&&($scope.userCondition.id!=undefined)){
			$scope.userCondition.id = null;
		}
		var userListDataTable= $('#userListDataTable').DataTable();
		userListDataTable.ajax.reload();
	}
	
}]);

angular.module('backApp').controller('userFormCtrl',  ['$rootScope', '$scope','$stateParams', 'FileUploader', function($rootScope, $scope,  $stateParams, FileUploader) {
	
  $scope.$on('$viewContentLoaded', function() { 
		 $scope.initPicFileUploader();
			if($stateParams.id > 0){ // is update
				var reqData= $stateParams;
				var url = "../user/getUser";
				jsonGet(url, reqData, function(result) {
					if (result.flag == RESULT_FLAG_SUCCESS) {
						$scope.user = result.data;
						$scope.showDelBtn = true;
						$scope.disabledText =true;
						$scope.$apply();
					} else {
						toastr.error(result.msg, COMMON_LABEL_ERROR);
					}
			    }, true, false);
				
			}else{
				$scope.showDelBtn = false;
				$scope.disabledText =false;
				$scope.user ={};
				$scope.user.headFilePathLayout ="../assets/pages/img/avatars/default.png";
			}
	});
	
	// update or add an user
	$scope.toSaveUser = function(){
		var reqData = $scope.user;
		if($stateParams.id > 0){ // is update
			if($scope.hasFormError()){ // if there is form input error
				return;
			}
			var url = "../user/updateUser";
			jsonPost(url, reqData, callback, true, false);
			function callback(result){
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('userList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    };
		}else{ // is add
			if($scope.hasUserAccount()|| $scope.hasFormError()){ // if there is form input error
				return;
			}
			var url = "../user/addUser";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('userList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}
	};
	
	$scope.hasUserAccount = function(){
		var account = $('#account').val();
        var url = "../user/getAccount";
        var b = false;
		if(account==""){
			toastr.error("登陆账号不能为空!!", COMMON_LABEL_ERROR);
			b = true;
		}else{
			var reqData={"account":account};
			jsonGet(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == 0) {
					toastr.error("登陆账号已经存在！！", COMMON_LABEL_ERROR);
					b = true;
				}
		    }
		}
		return b;
	};
	
	// validate the form input while submit
	$scope.hasFormError = function(){
		var errorMsg = "";
		if($('#userName').val()==""){
			errorMsg += "用户昵称不能为空<br>";
			$('#userName').parent().addClass("has-error");
		}else{
			$('#userName').parent().removeClass("has-error");
		}
		if($('#password').val()==""){
			errorMsg += "登陆密码不能为空<br>";
			$('#password').parent().addClass("has-error");
		}else{
			$('#password').parent().removeClass("has-error");
		}
		var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if($('#userName').val()=="" || !myreg.test($('#email').val())){
			errorMsg += "请输入正确的邮箱！！<br>";
			$('#email').parent().addClass("has-error");
		}else{
			$('#email').parent().removeClass("has-error");
		}
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	};
	
	// delete an admin
	$scope.toDeleteUser = function(){
		if($stateParams.id){
			bootbox.confirm("是否删除?", function (result) {
				if(result){
					var reqData= $stateParams;
					var url = "../user/deleteUser";
					jsonGet(url, reqData, callback, true, false);
					function callback(result) {
						if (result.flag == RESULT_FLAG_SUCCESS) {
							toastr.success(result.msg, COMMON_LABEL_SUCCESS);
							$rootScope.$state.go('userList');
						} else {
							toastr.error(result.msg, COMMON_LABEL_ERROR);
						}
				    }
				}
			})
		}
	};
	
	//图片上传
	$scope.initPicFileUploader = function(){
		$scope.picFileUploader = new FileUploader();
		initUploader($scope.picFileUploader,callback);
		function callback(result){
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				var retFile = result.data;
				$scope.user.headFileId = retFile.id;
				$scope.user.headFile = retFile;
				$scope.user.headFilePathLayout = "../upload/" + retFile.filePath;
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
		}
		
	};
	
}]);
