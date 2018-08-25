//list ctrl
angular.module('backApp').controller('picListCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	
	$scope.$on('$viewContentLoaded', function() {  				
		var picListDataTable = new Datatable().init({
            src: $("#picListDataTable"),
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
    				"url" : "../prodTypePic/getProdTypePicList", // ajax
    				"contentType": "application/json; charset=utf-8",
    				"data": function (dtRequest) {
    					  dtRequest = $.extend({}, dtRequest, {condition:$scope.picCondition}); // merge the form data into current Query
    					  var reqData = JSON.stringify(dtRequest);
    				      return reqData;
                     }
    			},
    			"columns" : [  {
    				"data" : null
    			}, {
    				"data" : "nameCn"
    			}, {
    				"data" : "nameEn"
    			}, {
    				"data" : "priority"
    			},
    			{
    				"data" : "lastUpdateAdmin.adminName"
    			}
    			, {
    				"data" : "lastUpdateTime"
    			}, {
    				"data" : "isEnable"
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
    					return data;
    				},
    				"targets" : 4,
    				'orderable' : false
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
    				"targets" : 6,
    				'searchable' : false,
    				'orderable' : false
    			},	{
    				"render" : function(data, type, row, meta) {
    					var str = '<a class="btn default" href="#/prodTypePic/form/' + data + '">编辑</a>';
    					return str;
    				},
    				"targets" : 7,
    				'searchable' : false,
    				'orderable' : false
    			} ],
    			"order" : [ [ 5, "desc" ] ]
    		// set first column as a default sort by asc
    		}
        });
    	
	});
	
	
	$scope.reloadTable = function(){
		var picListDataTable= $('#picListDataTable').DataTable();
		picListDataTable.ajax.reload();
	}
	

	
}]);

//form ctrl
angular.module('backApp').controller('picFormCtrl', ['$rootScope', '$scope','$stateParams','$state', 'settings', function($rootScope, $scope,$stateParams,$state, $http) {
	// page load: retrieve admin
	$scope.$on('$viewContentLoaded', function() { 
		if($stateParams.id > 0){ // is update
			var reqData= $stateParams;
			var url = "../prodTypePic/getProdTypePic";
			jsonGet(url, reqData, callback, true, false);
			
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					$scope.prodTypePic = result.data;
					$scope.showDelBtn = true;
					$scope.$apply();
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}else{ // is add
			$scope.prodTypePic={priority:1};
			$scope.showDelBtn = false;
		}
		
	});
	
	// validate the form input while submit
	$scope.hasFormError = function(){
		var errorMsg = "";
		if($('#nameCn').val()==""){
			errorMsg += "类目中文名不能为空<br>";
			$('#nameCn').parent().addClass("has-error");
		}else{
			$('#nameCn').parent().removeClass("has-error");
		}
		if($('#nameEn').val()==""){
			errorMsg += "类目英文名不能为空<br>";
			$('#nameEn').parent().addClass("has-error");
		}else{
			$('#nameEn').parent().removeClass("has-error");
		}
		
		var reg=/^[1-9]\d*$/;
		var priorityVal=$('#priority').val();
		if(priorityVal==""){
			errorMsg += "排序号不能为空<br>";
			$('#priority').parent().addClass("has-error");
		}else{
			if(!reg.test(priorityVal)){
				errorMsg += "排序号为正整数<br>";
				$('#priority').parent().addClass("has-error");
			}else{
			$('#priority').parent().removeClass("has-error");
			}
		}
		
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	}
	
	// update or add an prodTypePic
	$scope.toSaveProdTypePic = function(){

		var reqData = $scope.prodTypePic;
		
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
		
		if($stateParams.id > 0){ // is update
			var url = "../prodTypePic/updateProdTypePic";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('prodTypePicList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}else{ // is add
			var url = "../prodTypePic/addProdTypePic";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('prodTypePicList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}
	}
	
	// delete an prodTypePic
	$scope.toDeleteProdTypePic = function(){
		if($stateParams.id){
			bootbox.confirm("是否删除?", function (result) {
				if(result){
					var reqData= $stateParams;
					var url = "../prodTypePic/deleteProdTypePic";
					jsonGet(url, reqData, callback, true, false);
					function callback(result) {
						if (result.flag == RESULT_FLAG_SUCCESS) {
							toastr.success(result.msg, COMMON_LABEL_SUCCESS);
							$rootScope.$state.go('prodTypePicList');
						} else {
							toastr.error(result.msg, COMMON_LABEL_ERROR);
						}
				    }
				}
			})
		}
	}
	
}]);