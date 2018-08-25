// list ctrl
angular.module('backApp').controller('marqueeListCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	$scope.$on('$viewContentLoaded', function() {  				
		var adminListDataTable = new Datatable().init({
            src: $("#marqueeListDataTable"),
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
    				"url" : "../marquee/getMarqueeList", // ajax
    				"contentType": "application/json; charset=utf-8",
    				"data": function (dtRequest) {
    					  dtRequest = $.extend({}, dtRequest, {condition:$scope.marqueeCondition}); // merge the form data into current Query
    					  var reqData = JSON.stringify(dtRequest);
    				      return reqData;
                     }
    			},
    			"columns" : [  {
    				"data" : null
    			},{
    				"data" : "picFile.filePath"
    			}, {
    				"data" : "nameCn"
    			},{
    				"data" : "code"
    			}, {
    				"data" : "visitCnt"
    			},{
    				"data" : "priority"
    			}, {
    				"data" : "admin.adminName"
    			},{
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
    			},{
    				"render" : function(data, type, row, meta) {
					var str='<a href="'+row.url+'" target="_blank">'+data+'</a>';
					return str;
				},
    				"targets" : 2,
    				'searchable' : false,
    				'orderable' : false
    			},{
    				"render" : function(data, type, row, meta){
    					var str ="";
    					if(data=="home"){
    						str	= "首页";
    					}else{
    						str	= "商城";
    					}
    					return str;
    				},
     				  "targets" :3,
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
    				"targets" : 7,
    				'searchable' : false,
    				'orderable' : true
    			},{
   				  "targets" : 6,
   				  'searchable' : false,
   				  'orderable' : false
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
    				"targets" : 8,
    				'searchable' : false,
    				'orderable' : false
    			},{
    				"render" : function(data, type, row, meta) {
    					var str = '<a class="btn default" href="#/marquee/form/' + data + '">编辑</a>';
    					return str;
    				},
    				"targets" : 9,
    				'searchable' : false,
    				'orderable' : false
    			} ],
    			"order" : [ [7, "desc" ] ]
    		// set first column as a default sort by asc
    		}
        });
    	
	});
	$scope.reloadTable = function(){
		var marqueeListDataTable= $('#marqueeListDataTable').DataTable();
		marqueeListDataTable.ajax.reload();
	}
	
}]);

angular.module('backApp').controller('marqueeFormCtrl',  ['$rootScope', '$scope','$stateParams', 'FileUploader', function($rootScope, $scope,  $stateParams, FileUploader) {
	
  $scope.$on('$viewContentLoaded', function() { 
		 $scope.initPicFileUploader();
			if($stateParams.id > 0){ // is update
				var reqData= $stateParams;
				var url = "../marquee/getMarquee";
				jsonGet(url, reqData, function(result) {
					if (result.flag == RESULT_FLAG_SUCCESS) {
						$scope.marquee = result.data;
						$scope.showDelBtn = true;
						$scope.$apply();
					} else {
						toastr.error(result.msg, COMMON_LABEL_ERROR);
					}
			    }, true, false);
				
			}else{
				$scope.showDelBtn = false;
				$scope.marquee={priority:1};
				$scope.marquee.code= 'home';
			}
	});
	
	// update or add an admin
	$scope.toSaveMarquee = function(){
		var reqData = $scope.marquee;
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
		if($stateParams.id > 0){ // is update
			var url = "../marquee/updateMarquee";
			jsonPost(url, reqData, callback, true, false);
			function callback(result){
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('marqueeList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    };
			
		}else{ // is add
			var url = "../marquee/addMarquee";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('marqueeList');
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
			errorMsg += "中文名称不能为空<br>";
			$('#nameCn').parent().addClass("has-error");
		}else{
			$('#nameCn').parent().removeClass("has-error");
		}
		var picFileId = $scope.marquee.picFileId;
		if(typeof(picFileId) == "undefined" || picFileId ==""){
	    	errorMsg += "广告图片不能为空<br>";
			$('#picFileId').parent().addClass("has-error");
		}else{
			$('#picFileId').parent().removeClass("has-error");
		}
		var Expression=/^((ht|f)tps?):\/\/[\w\-]+(\.[\w\-]+)+([\w\-\.,@?^=%&:\/~\+#]*[\w\-\@?^=%&\/~\+#])?$/;
		var urlVal=$('#url').val();
		if(urlVal==""){
			errorMsg += "URL地址不能为空<br>";
			$('#url').parent().addClass("has-error");
		}else{
			if(!Expression.test(urlVal)){
				errorMsg += "URL地址格式不正确<br>";
				$('#url').parent().addClass("has-error");
			}else{
			    $('#url').parent().removeClass("has-error");
			}
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
	};
	
	
	// delete an Marquee
	$scope.toDeleteMarquee = function(){
		if($stateParams.id){
			bootbox.confirm("是否删除?", function (result) {
				if(result){
					var reqData= $stateParams;
					var url = "../marquee/deleteMarquee";
					jsonGet(url, reqData, callback, true, false);
					function callback(result) {
						if (result.flag == RESULT_FLAG_SUCCESS) {
							toastr.success(result.msg, COMMON_LABEL_SUCCESS);
							$rootScope.$state.go('marqueeList');
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
				$scope.marquee.picFileId = retFile.id;
				$scope.marquee.picFile = retFile;
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
		}
		
	};
}]);
