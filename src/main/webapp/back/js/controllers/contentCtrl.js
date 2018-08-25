// list ctrl
angular.module('backApp').controller('contentHtmlListCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	$scope.$on('$viewContentLoaded', function() {  				
		var contentListDataTable = new Datatable().init({
            src: $("#contentListDataTable"),
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
    				"url" : "../content/getContentList", // ajax
    				"contentType": "application/json; charset=utf-8",
    				"data": function (dtRequest) {
    					  dtRequest = $.extend({}, dtRequest, {condition:$scope.contentCondition}); // merge the form data into current Query
    					  var reqData = JSON.stringify(dtRequest);
    				      return reqData;
                     }
    			},
    			"columns" : [  {
    				"data" : null
    			}, {
    				"data" : "nameCn"
    			},{
    				"data" : "code"
    			},{
    				"data" : "contentUrl"
    			},{
    				"data" : "admin.adminName"
    			}, {
    				"data" : "lastUpdateTime"
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
    			},	{
    				"render" : function(data, type, row, meta) {
    					var str = '<a href="' + data + '">'+data+'</a>';
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
    				"targets" : 5,
    				'searchable' : false,
    				'orderable' : true
    			},	{
    				"render" : function(data, type, row, meta) {
    					var str = '<a class="btn default" href="#/content/form/' + data + '">编辑</a>';
    					return str;
    				},
    				"targets" : 6,
    				'searchable' : false,
    				'orderable' : false
    			} ],
    			"order" : [ [5, "desc" ] ]
    		// set first column as a default sort by asc
    		}
        });
    	
	});
	$scope.reloadTable = function(){
		var contentListDataTable= $('#contentListDataTable').DataTable();
		contentListDataTable.ajax.reload();
	}
	
}]);

angular.module('backApp').controller('contentFormCtrl',  ['$rootScope', '$scope','$stateParams', 'FileUploader', function($rootScope, $scope,  $stateParams, FileUploader) {
	
  $scope.$on('$viewContentLoaded', function() { 
			if($stateParams.id > 0){ // is update
				var reqData= $stateParams;
				var url = "../content/getContentById";
				jsonGet(url, reqData, function(result) {
					if (result.flag == RESULT_FLAG_SUCCESS) {
						$scope.content = result.data;
						CKEDITOR.replaceAll(); 	// initial all ckeditors
						$scope.showDelBtn = true;
						$scope.$apply();
					} else {
						toastr.error(result.msg, COMMON_LABEL_ERROR);
					}
			    }, true, false);
				
			}else{
				$scope.showDelBtn = false;
				$scope.content={priority:1};
				CKEDITOR.replaceAll();
			}
	});
	
	// update or add an admin
	$scope.toSaveContent = function(){
		$scope.content.descriptionCn = CKEDITOR.instances.descriptionCn.getData();
		$scope.content.descriptionEn = CKEDITOR.instances.descriptionEn.getData();
		var reqData = $scope.content;
//		log(reqData);
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
	
		if($stateParams.id > 0){ // is update
			var url = "../content/updateContent";
			jsonPost(url, reqData, callback, true, false);
			function callback(result){
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('contentHtmlList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    };
			
		}else{ // is add
			var url = "../content/addContent";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('contentHtmlList');
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
	
	
}]);
