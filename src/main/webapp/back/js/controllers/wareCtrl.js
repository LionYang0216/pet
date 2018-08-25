// list ctrl
angular.module('backApp').controller('wareListCtrl', ['$rootScope', '$scope', 'settings','$stateParams', function($rootScope, $scope, $http,$stateParams) {
	initShopSelection($scope);
	$scope.$on('$viewContentLoaded', function() {  	
		 console.log($stateParams);
		if($stateParams.filterShopId > 0){
			 $scope.wareCondition ={shopId:$stateParams.filterShopId};
		}
		
		var wareListDataTable = new Datatable().init({
            src: $("#wareListDataTable"),
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
    				"url" : "../ware/getWareList", // ajax
    				"contentType": "application/json; charset=utf-8",
    				"data": function (dtRequest) {
    					  dtRequest = $.extend({}, dtRequest, {condition:$scope.wareCondition}); // merge the form data into current Query
    					  var reqData = JSON.stringify(dtRequest);
    				      return reqData;
                     }
    			},
    			"columns" : [  {
    				"data" : null
    			},{
    				"data" : "picFile"
    			},{
    				"data" : "shopId"
    			}, {
    				"data" : "nameCn"
    			}, {
    				"data" : "isNew"
    			}, {
    				"data" : "price"
    			},{
    				"data" : "locationCn"
    			},{
    				"data" : "brandCn"
    			},{
    				"data" : "modalCn"
    			},{
    				"data" : "runnerCn"
    			},{
    				"data" : "produceProductCn"
    			},{
    				"data" : "makeModeCn"
    			},{
    				"data" : "wareTypeSrcId"
    			},{
    				"data" : "wareTypeMchId"
    			},{
    				"data" : "wareTypeSteId"
    			},{
    				"data" : "wareTypePrdId"
    			},{
    				"data" : "visitCnt"
    			},{
    				"data" : "upCnt"
    			},{
    				"data" : "favCnt"
    			},{
    				"data" : "user.userName"
    			}, {
    				"data" : "lastUpdateTime"
    			},{
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
    				"render" : function(data, type, row, meta){
    					//console.log(data);
    					data = data.filePath;
    					var str = '<a href="../upload/'+data+'" target="_blank"><img  src="../upload/'+data+'" style="max-height:40px" border=1 /></a>';
    					return str;
    				},
    				"targets" : 1,
    				'searchable' : false,
    				'orderable' : false
    			},
    			{
    				"render" : function(data, type, row, meta){
    					if(row.shop!=undefined){
        					return '<a class="default" href="#/shop/list/' + data + '">'+row.shop.nameCn+'</a>';
    					}else{
    						return ""
    					}
    				},
    				"targets" : 2,
    				'searchable' : true,
    				'orderable' : true
    			},
    			{
    				"render" : function(data, type, row, meta) {
    					var str = "";
    					if (data == 1) {
    						str = "是";
    					} else {
    						str = "否";
    					}
    					return str;
    				},
    				"targets" : 4,
    				'searchable' : false,
    				'orderable' : false
    			},{
    				"render" : function(data, type, row, meta) {
    					if(row.wareTypeSrc!=undefined){
        					return row.wareTypeSrc.nameCn;
    					}else{
    						return "未定义"
    					}
    				},
    				"targets" : 12,
    				'searchable' : false,
    				'orderable' : true
    			},{
    				"render" : function(data, type, row, meta) {
    					if(row.wareTypeMch!=undefined){
        					return row.wareTypeMch.nameCn;
    					}else{
    						return "未定义"
    					}
    				},
    				"targets" : 13,
    				'searchable' : false,
    				'orderable' : true
    			},{
    				"render" : function(data, type, row, meta) {
    					if(row.wareTypeSte!=undefined){
        					return row.wareTypeSte.nameCn;
    					}else{
    						return "未定义"
    					}
    				},
    				"targets" : 14,
    				'searchable' : false,
    				'orderable' : true
    			},{
    				"render" : function(data, type, row, meta) {
    					if(row.wareTypePrd!=undefined){
        					return row.wareTypePrd.nameCn;
    					}else{
    						return "未定义"
    					}
    				},
    				"targets" : 15,
    				'searchable' : false,
    				'orderable' : true
    			},
    			{
    				"render" : function(data, type, row, meta) {
    				 if(data==null){
    					 return "";
    				 }
    				 return data;
    				},
    				"targets" : [19],
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
    				"targets" : 20,
    				'searchable' : false,
    				'orderable' : true
    			},
    			{
    				"render" : function(data, type, row, meta) {
    					var str = "";
    					if (data == 1) {
    						str = "<font color=green>上架</font>";
    					} else {
    						str = "<font color=red>下架</font>";
    					}
    					return str;
    				},
    				"targets" : 21,
    				'searchable' : false,
    				'orderable' : true
    			},	{
    				"render" : function(data, type, row, meta) {
    					var str = '<a class="btn default" href="#/ware/form/' + data + '">编辑</a>';
    					return str;
    				},
    				"targets" : 22,
    				'searchable' : false,
    				'orderable' : false
    			} ],
    			"order" : [ [20, "desc" ] ]
    		// set first column as a default sort by asc
    		}
        });
    	
	});
	$scope.reloadTable = function(){
		if(($scope.wareCondition!=undefined)&&($scope.wareCondition.shopId!=undefined)){
			$scope.wareCondition.shopId = null;
		}
		var wareListDataTable= $('#wareListDataTable').DataTable();
		    wareListDataTable.ajax.reload();
	};
}]);

function initShopSelection($scope){
	  var url = "../shop/getShopSelect";
	   jsonGet(url, '', callback, false, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.shopList=result.data;
			}
	    }
}


angular.module('backApp').controller('wareFormCtrl',  ['$rootScope', '$scope','$stateParams', 'FileUploader', function($rootScope, $scope,  $stateParams, FileUploader) {
	
  $scope.$on('$viewContentLoaded', function() { 
	  	initShopSelection($scope);
	  	initWareTypeSteSelection($scope)
	    initWareTypeSrcSelection($scope)
	    initWareTypeMchSelection($scope)
	    initWareTypePrdSelection($scope)
	    $scope.initPicFileUploader();
			if($stateParams.id > 0){ // is update
				var reqData= $stateParams;
				var url = "../ware/getWare";
				jsonGet(url, reqData, function(result) {
					if (result.flag == RESULT_FLAG_SUCCESS) {
						var ware = result.data;
					
						$scope.ware=ware;
						CKEDITOR.replaceAll(); 	// initial all ckeditors
						$scope.showDelBtn = true;
						$scope.$apply();
					} else {
						toastr.error(result.msg, COMMON_LABEL_ERROR);
					}
			    }, true, false);
				
			}else{
				$scope.ware={};
				var defaultShopId=$scope.shopList[0].id;
				$scope.ware.shopId=defaultShopId;
				$scope.ware.isNew=0;//默认不是新品
				$scope.ware.isEnable=1;//默认上架
				$scope.ware.priority=1;
				$scope.ware.visitCnt=0;
				$scope.ware.upCnt=0;
				$scope.ware.favCnt=0;
				CKEDITOR.replaceAll();
			}
	});
	
  
  
	// update or add an admin
	$scope.toSaveWare = function(){
		$scope.ware.descriptionCn = CKEDITOR.instances.descriptionCn.getData();
		$scope.ware.descriptionEn = CKEDITOR.instances.descriptionEn.getData();
		var reqData = $scope.ware;
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
	
		if($stateParams.id > 0){ // is update
			var url = "../ware/updateWare";
			jsonPost(url, reqData, callback, true, false);
			function callback(result){
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('wareList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    };
			
		}else{ // is add
			var url = "../ware/addWare";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('wareList');
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
			errorMsg += "中文标题不能为空<br>";
			$('#nameCn').parent().addClass("has-error");
		}else{
			$('#nameCn').parent().removeClass("has-error");
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
			
			var picFileId = $scope.ware.picFileId;
			if(typeof(picFileId) == "undefined" || picFileId ==""){
		    	errorMsg += "商品图片不能为空<br>";
				$('#picFileId').parent().addClass("has-error");
			}else{
				$('#picFileId').parent().removeClass("has-error");
			}
			
	    if(CKEDITOR.instances.descriptionCn.getData() == ""){
			errorMsg += "内容不能为空<br>";
			$('#descriptionCn').parent().addClass("has-error");
		}else{
			$('#descriptionCn').parent().removeClass("has-error");
		}
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	};
	
	
	// delete an admin
	$scope.toDeleteWare = function(){
		if($stateParams.id){
			bootbox.confirm("是否删除?", function (result) {
				if(result){
					var reqData= $stateParams;
					var url = "../ware/deleteWare";
					jsonGet(url, reqData, callback, true, false);
					function callback(result) {
						if (result.flag == RESULT_FLAG_SUCCESS) {
							toastr.success(result.msg, COMMON_LABEL_SUCCESS);
							$rootScope.$state.go('wareList');
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
				$scope.ware.picFileId = retFile.id;
				$scope.ware.picFile = retFile;
				log($scope.ware.picFile);
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
		}
		
	};
	
}]);
