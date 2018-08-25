// list ctrl
angular.module('backApp').controller('newsListCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	$scope.$on('$viewContentLoaded', function() {  				
		var adminListDataTable = new Datatable().init({
            src: $("#newsListDataTable"),
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
    				"url" : "../news/getNewsList", // ajax
    				"contentType": "application/json; charset=utf-8",
    				"data": function (dtRequest) {
    					  dtRequest = $.extend({}, dtRequest, {condition:$scope.newsCondition}); // merge the form data into current Query
    					  var reqData = JSON.stringify(dtRequest);
    				      return reqData;
                     }
    			},
    			"columns" : [  {
    				"data" : null
    			},{
    				"data" : "picFile.filePath"
    			},{
    				"data" : "titleCn"
    			},{
    				"data" : "newsType.nameCn"
    			},{
    				"data" : "priority"
    			},{
    				"data" : "visitCnt"
    			}, {
    				"data" : "upCnt"
    			},{
    				"data" : "admin.adminName"
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
    			},{
    				"render" : function(data, type, row, meta){
    					var str = '<a href="../upload/'+data+'" target="_blank"><img  src="../upload/'+data+'" style="height:30px; width:50px"/></a>';
    					return str;
    				},
    				"targets" : 1,
    				'searchable' : false,
    				'orderable' : false
    			},
    		     {
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
    				"targets" : 8,
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
    					var str = '<a class="btn default" href="#/news/form/' + data + '">编辑</a>';
    					return str;
    				},
    				"targets" : 10,
    				'searchable' : false,
    				'orderable' : false
    			} ],
    			"order" : [ [8, "desc" ] ]
    		// set first column as a default sort by asc
    		}
        });
    	
	});
	$scope.reloadTable = function(){
		var adminListDataTable= $('#newsListDataTable').DataTable();
		adminListDataTable.ajax.reload();
	}
	
}]);

angular.module('backApp').controller('newsFormCtrl',  ['$rootScope', '$scope','$stateParams', 'FileUploader', function($rootScope, $scope,  $stateParams, FileUploader) {
  $scope.$on('$viewContentLoaded', function() { 
	  $.ajax({
          type: "get",
          url: "../newsType/getNewsTypeListForSelect",
          success: function (result) {
          $scope.newsTypeList = result.data;
          }

      });
		 $scope.initPicFileUploader();
			if($stateParams.id > 0){ // is update
				var reqData= $stateParams;
				var url = "../news/getNews";
				jsonGet(url, reqData, function(result) {
					if (result.flag == RESULT_FLAG_SUCCESS) {
						$scope.news = result.data;
						CKEDITOR.replaceAll(); 	// initial all ckeditors
						$scope.showDelBtn = true;
						$scope.$apply();
					} else {
						toastr.error(result.msg, COMMON_LABEL_ERROR);
					}
			    }, true, false);
				
			}else{
				$scope.showDelBtn = false;
				$scope.news={priority:1};
				CKEDITOR.replaceAll();
			}
	});
	
	// update or add an admin
	$scope.toSaveNews = function(){
		$scope.news.descriptionCn = CKEDITOR.instances.descriptionCn.getData();
		$scope.news.descriptionEn = CKEDITOR.instances.descriptionEn.getData();
		var reqData = $scope.news;
//		log(reqData);
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
	
		if($stateParams.id > 0){ // is update
			var url = "../news/updateNews";
			jsonPost(url, reqData, callback, true, false);
			function callback(result){
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('newsList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    };
			
		}else{ // is addn
			var url = "../news/addNews";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('newsList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}
	};
	
	
	// validate the form input while submit
	$scope.hasFormError = function(){
		var errorMsg = "";
		if($('#titleCn').val()==""){
			errorMsg += "中文标题不能为空<br>";
			$('#titleCn').parent().addClass("has-error");
		}else{
			$('#titleCn').parent().removeClass("has-error");
		}
		var picFileId = $scope.news.picFileId;
		if(typeof(picFileId) == "undefined" || picFileId ==""){
	    	errorMsg += "标题图片不能为空<br>";
			$('#picFileId').parent().addClass("has-error");
		}else{
			$('#picFileId').parent().removeClass("has-error");
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
	    if(CKEDITOR.instances.descriptionCn.getData() == ""){
			errorMsg += "中文内容不能为空<br>";
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
	$scope.toDeleteNews = function(){
		if($stateParams.id){
			bootbox.confirm("是否删除?", function (result) {
				if(result){
					var reqData= $stateParams;
					var url = "../news/deleteNews";
					jsonGet(url, reqData, callback, true, false);
					function callback(result) {
						if (result.flag == RESULT_FLAG_SUCCESS) {
							toastr.success(result.msg, COMMON_LABEL_SUCCESS);
							$rootScope.$state.go('newsList');
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
				$scope.news.picFileId = retFile.id;
				$scope.news.picFile = retFile;
				log($scope.news.picFile);
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
		}
		
	};
}]);
