// list ctrl
angular.module('backApp').controller('shopListCtrl', ['$rootScope', '$scope', 'settings','$stateParams', function($rootScope, $scope, $http,$stateParams) {
	if($stateParams.shopId > 0){
		 $scope.shopCondition ={id:$stateParams.shopId};
	}
	$scope.$on('$viewContentLoaded', function() {  				
		var adminListDataTable = new Datatable().init({
            src: $("#shopListDataTable"),
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
    				"url" : "../shop/getShopList", // ajax
    				"contentType": "application/json; charset=utf-8",
    				"data": function (dtRequest) {
    					  dtRequest = $.extend({}, dtRequest, {condition:$scope.shopCondition}); // merge the form data into current Query
    					  var reqData = JSON.stringify(dtRequest);
    				      return reqData;
                     }
    			},
    			"columns" : [  {
    				"data" : null
    			},{
    				"data" : "logoFile.filePath"
    			}, {
    				"data" : "nameCn"
    			}, {
    				"data" : "user.userName"
    			},{
    				"data" : "tel"
    			}, {
    				"data" : "regTime"
    			}, {
    				"data" : "updateTime"
    			},{
    				"data" : "status"
    			},{
    				"data" : "id"
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
    				"render" : function(data, type, row, meta){
    					var str = '<a href="../upload/'+data+'" target="_blank"><img  src="../upload/'+data+'" style="height:30px; width:50px"/></a>';
    					return str;
    				},
    				"targets" : 1,
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
    				"targets" : [5,6],
    				'searchable' : false,
    				'orderable' : true
    			},
    			{
    				"render" : function(data, type, row, meta) {
    					var str = "";
    					if (data == 1) {
    						str = "<font color=green>已审批</font>";
    					} else if(data == 0){
    						str = "<font color=orange>未审批</font>";
    					}else if(data == 2){
    						str = "<font color=red>禁用</font>";
    					}
    					return str;
    				},
    				"targets" : 7,
    				'searchable' : false,
    				'orderable' : false
    			},	{
    				"render" : function(data, type, row, meta) {
    					var str = '<a class="btn default" href="#/ware/list/' + data + '">查看</a>';
    					return str;
    				},
    				"targets" : 8,
    				'searchable' : false,
    				'orderable' : false
    			} ,	{
    				"render" : function(data, type, row, meta) {
    					var str = '<a class="btn default" href="#/shop/form/' + data + '">编辑</a>';
    					return str;
    				},
    				"targets" : 9,
    				'searchable' : false,
    				'orderable' : false
    			} ],
    			"order" : [ [6, "desc" ] ]
    		// set first column as a default sort by asc
    		}
        });
    	
	});
	$scope.reloadTable = function(){
		var shopListDataTable= $('#shopListDataTable').DataTable();
		shopListDataTable.ajax.reload();
	}
	
}]);

angular.module('backApp').controller('shopFormCtrl',  ['$rootScope', '$scope','$stateParams', 'FileUploader', function($rootScope, $scope,  $stateParams, FileUploader) {
	
  $scope.$on('$viewContentLoaded', function() { 
		 $scope.initLogoFileUploader();
		 $scope.initRegFileUploader();
		 $scope.initUserSelection();
		 $scope.levels = [{ id: 1, name: '1级'}, { id: 2, name: '2级'}, { id: 3, name: '3级'},{ id: 4, name: '4级'},{ id: 5, name: '5级'}];
			if($stateParams.id > 0){ // is update
				var reqData= $stateParams;
				var url = "../shop/getShop";
				jsonGet(url, reqData, function(result) {
					if (result.flag == RESULT_FLAG_SUCCESS) {
						$scope.shop = result.data;
						CKEDITOR.replaceAll(); 	// initial all ckeditors
						$scope.showDelBtn = true;
						$scope.$apply();
					} else {
						toastr.error(result.msg, COMMON_LABEL_ERROR);
					}
			    }, true, false);
				
			}else{
				$scope.showDelBtn = false;
				$scope.shop={level:1,userId:25};
				$scope.shop.visitCnt=0;
				$scope.shop.status=0;
				$scope.shop.favCnt=0;
				CKEDITOR.replaceAll();
			}
	});
	
	// update or add an admin
	$scope.toSaveShop = function(){
		$scope.shop.introEnt = CKEDITOR.instances.introEnt.getData();
		$scope.shop.introLog = CKEDITOR.instances.introLog.getData();
		$scope.shop.introSrv = CKEDITOR.instances.introSrv.getData();
		var reqData = $scope.shop;
//		log(reqData);
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
	
		if($stateParams.id > 0){ // is update
			var url = "../shop/updateShop";
			jsonPost(url, reqData, callback, true, false);
			function callback(result){
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('shopList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    };
			
		}else{ // is add
			var url = "../shop/addShop";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('shopList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}
	};
	
	
	// validate the form input while submit
	$scope.hasFormError = function(){
		var errorMsg = "";
		if($('#nameCn').val()==""){
			errorMsg += "中文名不能为空<br>";
			$('#nameCn').parent().addClass("has-error");
		}else{
			$('#nameCn').parent().removeClass("has-error");
		}
		var regFileId = $scope.shop.regFileId;
		if(typeof(regFileId) == "undefined" || regFileId ==""){
	    	errorMsg += "执照不能为空<br>";
			$('#regFileId').parent().addClass("has-error");
		}else{
			$('#regFileId').parent().removeClass("has-error");
		}
	
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	};
	
	
	// delete an admin
	$scope.toDeleteShop = function(){
		if($stateParams.id){
			bootbox.confirm("删除该店铺将会删除旗下所有设备，以及与之关联的关系，是否继续?", function (result) {
				if(result){
					var reqData= $stateParams;
					var url = "../shop/deleteShop";
					jsonGet(url, reqData, callback, true, false);
					function callback(result) {
						if (result.flag == RESULT_FLAG_SUCCESS) {
							toastr.success(result.msg, COMMON_LABEL_SUCCESS);
							$rootScope.$state.go('shopList');
						} else {
							toastr.error(result.msg, COMMON_LABEL_ERROR);
						}
				    }
				}
			})
		}
	};
	$scope.initUserSelection=function(){
		  var url = WEB_ROOT_PATH + "/user/getUserListForSelect";
		   jsonPost(url, '', callback, false, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					$scope.userList=result.data;
				
				}
		    }
	}
	
	//logo上传
	$scope.initLogoFileUploader = function(){
		$scope.logoFileUploader = new FileUploader();
		initUploader($scope.logoFileUploader,callback);
		function callback(result){
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				var retFile = result.data;
				$scope.shop.logoFileId = retFile.id;
				$scope.shop.logoFile = retFile;
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
		}
		
	};
	
	$scope.toShowReject=function(){
			$scope.showReject = 'show';
		
	}
	
	$scope.toHideReject=function(){
			$scope.showReject = 'hide';
			$scope.shop.rejectReason=null;
	}
	//注册文件上传
	$scope.initRegFileUploader = function(){
		$scope.regFileUploader = new FileUploader();
		initUploader($scope.regFileUploader,function(result){
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				var retFile = result.data;
				$scope.shop.regFileId = retFile.id;
				$scope.shop.regFile = retFile;
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
		});
		
		
	}
	
}]);
