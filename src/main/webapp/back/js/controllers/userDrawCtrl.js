// list ctrl
angular.module('backApp').controller('userDrawListCtrl', ['$rootScope', '$scope', 'settings','$stateParams', function($rootScope, $scope, $http,$stateParams) {
	$scope.$on('$viewContentLoaded', function() {  		
		var userDrawListDataTable = new Datatable().init({
            src: $("#userDrawListDataTable"),
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
    				"url" : "../userDraw/getUserDrawList", // ajax
    				"contentType": "application/json; charset=utf-8",
    				"data": function (dtRequest) {
    					  dtRequest = $.extend({}, dtRequest, {condition:$scope.userDrawCondition}); // merge the form data into current Query
    					  var reqData = JSON.stringify(dtRequest);
    				      return reqData;
                     }
    			},
    			"columns" : [  {
    				"data" : null
    			},{
    				"data" : "userStr"
    			},{
    				"data" : "drawAmount"
    			}, {
    				"data" : "stauts"
    			}, {
    				"data" : "userRemark"
    			}, {
    				"data" : "adminRemark"
    			},{
    				"data" : "userRequestTime"
    			},{
    				"data" : "lastUpdateTime"
    			},{
    				"data" : "adminStr"
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
    					var str="";
    					if(data == 0){
    						str	= "申请中"; 
    					}
    					if(data ==1 )  {
    						str = "<font color=green>已审批</font>";
    					}  
    					if(data ==2 )  {
    						str = "<font color=red>已拒绝</font>";
    					}  
    					return str;
    				},
    				"targets" : 3,
    				'searchable' : false,
    				'orderable' : false
    			},{
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
    				"targets" : 7,
    				'searchable' : false,
    				'orderable' : true
    			},{
    				"render" : function(data, type, row, meta) {
    					var str ="";
    					if(row.stauts == 0){
    					    str = '<a class="btn default" href="#/userDraw/form/' + data + '">审批</a>';
    					}
    					return str;
    				},
    				"targets" : 9,
    				'searchable' : false,
    				'orderable' : false
    			} ],
    			"order" : [ [ 7, "desc" ] ]
    		// set first column as a default sort by asc
    		}
        });
    	
	});
	$scope.reloadTable = function(){
		var userDrawListDataTable= $('#userDrawListDataTable').DataTable();
		userDrawListDataTable.ajax.reload();
	}
	
}]);

angular.module('backApp').controller('userDrawFormCtrl',  ['$rootScope', '$scope','$stateParams', 'FileUploader', function($rootScope, $scope,  $stateParams, FileUploader) {
	
  $scope.$on('$viewContentLoaded', function() { 
			if($stateParams.id > 0){ // is update
				var reqData= $stateParams;
				var url = "../userDraw/getUserDraw";
				jsonGet(url, reqData, function(result) {
					if (result.flag == RESULT_FLAG_SUCCESS) {
						$scope.userDraw = result.data;
						$scope.userDraw.userRequestTime = dateTimeString($scope.userDraw.userRequestTime);
						$scope.disabledText =true;
						$scope.$apply();
					} else {
						toastr.error(result.msg, COMMON_LABEL_ERROR);
					}
			    }, true, false);
			}
	});
	//审核申请
	// update or add an userDraw
	$scope.toSaveUserDraw = function(){
		$scope.userDraw.user = null;
		$scope.userDraw.admin = null;
		$scope.userDraw.lastUpdateTime =null;
		$scope.userDraw.userRequestTime =null;
		var reqData = $scope.userDraw;
		if($stateParams.id > 0){ // is update
			var url = "../userDraw/updateUserDraw";
			jsonPost(url, reqData, callback, true, false);
			function callback(result){
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('userDrawList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
					$scope.userDraw = result.data;
				}
		    };
		}
	};
	//拒绝
	$scope.noPassUserDraw = function(){
	    if($scope.hasFormError()){ // if there is form input error
			return;
		}
		 $scope.userDraw ={};
		 $scope.userDraw.id = $stateParams.id;
		 $scope.userDraw.adminRemark = $("#adminRemark").val();
		 var reqData = $scope.userDraw;
		if($stateParams.id > 0){ 
			var url = "../userDraw/noPassUserDraw";
			jsonPost(url, reqData, callback, true, false);
			function callback(result){
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('userDrawList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    };
		}
		
	};
	// validate the form input while submit
	$scope.hasFormError = function(){
		var errorMsg = "";
		if($("#adminRemark").val()==""){
			errorMsg += "请填写拒绝原因！<br>";
			$('#adminRemark').parent().addClass("has-error");
		}else{
			$('#adminRemark').parent().removeClass("has-error");
		}
		
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	};
	
}]);
