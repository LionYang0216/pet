// list ctrl
angular.module('backApp').controller('nodeListCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	$scope.$on('$viewContentLoaded', function() {  				
		var nodeListDataTable = new Datatable().init({
            src: $("#nodeListDataTable"),
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
    				"url" : "../node/getNodeList", // ajax
    				"contentType": "application/json; charset=utf-8",
    				"data": function (dtRequest) {
    					  dtRequest = $.extend({}, dtRequest, {condition:$scope.nodeCondition}); // merge the form data into current Query
    					  var reqData = JSON.stringify(dtRequest);
    				      return reqData;
                     }
    			},
    			"columns" : [  {
    				"data" : null
    			}, {
    				"data" : "name"
    			}, {
    				"data" : "isMenu"
    			}, {
    				"data" : "parent.name"
    			}, {
    				"data" : "icon"
    			}, {
    				"data" : "url"
    			}, {
    				"data" : "description"
    			}, {
    				"data" : "priority"
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
    				"render" : function(data, type, row, meta) {
    					var str = "";
    					if (data == 1) {
    						str = "<font color=green>是</font>";
    					}if(data == 0) {
    						str = "<font color=red>否</font>";
    					}
    					return str;
    				},
    				"targets" :2,
    				'searchable' : false,
    				'orderable' : false
    			},
    			{
    				"render" : function(data, type, row, meta) {
    					var str = "无";
    					if (row.isMenu == "0") {
    						str = data
    					}
    					return str;
    				},
    				"targets" :3,
    				'searchable' : false,
    				'orderable' : false
    			},
    			{
    				"render" : function(data, type, row, meta) {
    					var str = '<a class="btn default" href="#/node/form/' + data + '">编辑</a>';
    					return str;
    				},
    				"targets" : 8,
    				'searchable' : false,
    				'orderable' : false
    			} ],
    			"order" : [ [ 7, "desc" ] ]
    		// set first column as a default sort by asc
    		}
        });
    	
	});
	
	
	$scope.reloadTable = function(){
		var nodeListDataTable= $('#nodeListDataTable').DataTable();
		nodeListDataTable.ajax.reload();
	}
	

	
}]);
	
// form ctrl
angular.module('backApp').controller('nodeFormCtrl', ['$rootScope', '$scope','$stateParams','$state', 'settings', function($rootScope, $scope,$stateParams,$state, $http) {
	// page load: retrieve node
	$scope.$on('$viewContentLoaded', function() { 
	      $.ajax({
              type: "GET",
              url: "../node/getNodeIsMenu",
              dataType: "json",
              contentType: "application/json; charset=utf-8",
              success: function (result) {
              $scope.nodeParentList = result.data;
              }

          });
		if($stateParams.id > 0){ // is update
			var reqData= $stateParams;
			var url = "../node/getNode";
			jsonGet(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					$scope.node = result.data;
					$scope.showDelBtn = true;
					$scope.$apply();
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}else{
			$scope.node={};
			$scope.node.isMenu = 1;
			$scope.disabledText = true;
			$scope.showDelBtn = false;
		}
		
	});
	
	// validate the form input while submit
	$scope.hasFormError = function(){
		var errorMsg = "";
		if($('#name').val()==""){
			errorMsg += "名称不能为空<br>";
			$('#name').parent().addClass("has-error");
		}else{
			$('#name').parent().removeClass("has-error");
		}
		if($('#url').val()==""){
			errorMsg += "url地址不能为空<br>";
			$('#url').parent().addClass("has-error");
		}else{
			$('#url').parent().removeClass("has-error");
		}
		if($('#icon').val()==""){
			errorMsg += "图标不能为空<br>";
			$('#icon').parent().addClass("has-error");
		}else{
			$('#icon').parent().removeClass("has-error");
		}
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	}
	
	// update or add an node
	$scope.toSaveNode = function(){
		var reqData = $scope.node;
		log(reqData);
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
		if($stateParams.id > 0){ // is update
			var url = "../node/updateNode";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('nodeList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}else{ // is add
			var url = "../node/addNode";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('nodeList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}
	}
	
	// delete an node
	$scope.toDeleteNode = function(){
		if($stateParams.id){
			bootbox.confirm("是否删除?", function (result) {
				if(result){
					var reqData= $stateParams;
					var url = "../node/deleteNode";
					jsonGet(url, reqData, callback, true, false);
					function callback(result) {
						if (result.flag == RESULT_FLAG_SUCCESS) {
							toastr.success(result.msg, COMMON_LABEL_SUCCESS);
							$rootScope.$state.go('nodeList');
						} else {
							toastr.error(result.msg, COMMON_LABEL_ERROR);
						}
				    }
				}
			})
		}
	}
	
	$scope.radioChecked = function(){
		if($scope.node.isMenu==0){
			$scope.disabledText = false;
		}
		if($scope.node.isMenu==1){
			$scope.disabledText = true;
		}
	}
}]);
