// list ctrl
angular.module('backApp').controller('userBalanceLogListCtrl', ['$rootScope', '$scope', 'settings','$stateParams', function($rootScope, $scope, $http,$stateParams) {
	$scope.$on('$viewContentLoaded', function() {  	
		if($stateParams.filterUserId > 0){
		 $scope.userBalanceLogCondition ={id:$stateParams.filterUserId};
		}
	    $scope.datap();
		var userBalanceLogListDataTable = new Datatable().init({
            src: $("#userBalanceLogDataTable"),
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
    				"url" : "../userBalanceLog/getUserBalanceLogList", // ajax
    				"contentType": "application/json; charset=utf-8",
    				"data": function (dtRequest) {
    					  dtRequest = $.extend({}, dtRequest, {condition:$scope.userBalanceLogCondition}); // merge the form data into current Query
    					  var reqData = JSON.stringify(dtRequest);
    				      return reqData;
                     }
    			},
    			"columns" : [  {
    				"data" : null
    			},{
    				"data" : "user.userName"
    			},{
    				"data" : "changeAmount"
    			}, {
    				"data" : "changeType"
    			}, {
    				"data" : "prod.nameCn"
    			},{
    				"data" : "chargeOrderNumber"
    			},{
    				"data" : "description"
    			},{
    				"data" : "changeTime"
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
    					var str = '';
    					if(row.changeType>5){
    						str = '<div  style="color:#F00">'+data+'</div>'
    					}
    					if(row.changeType<5){
    						str = '<div  style="color:#3c3">'+data+'</div>'
    					}
    					return str;
    				},
    				"targets" : 2,
    				'searchable' : false,
    				'orderable' : false
    			}
    			,{
    				"render" : function(data, type, row, meta) {
    					var str = '';
    					switch (data)
    					{
    					case 1:
    					    str="查看瓶库";
    					  break;
    					case 2:
    						str="线下提现";
    					  break;
    					case 6:
    						str="微信充值";
    					  break;
    					case 7:
    						str="支付宝充值";
    					  break;
    					case 8:
    						str="线下充值";
    					  break;
    					}
    					return str;
    				},
    				"targets" : 3,
    				'searchable' : false,
    				'orderable' : true
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
    				"render" : function(data, type, row, meta) {
    					var str="";
    					if(row.changeType == "1"){
    						str = data 
    					}  			
    					return str;
    				},
    				"targets" : 4,
    				'searchable' : false,
    				'orderable' : false
    			},{
    				"render" : function(data, type, row, meta) {
    					var str = '<a class="btn default" href="#/userBalanceLog/form/' + data + '">编辑</a>';
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
		if(($scope.userBalanceLogCondition!=undefined)&&($scope.userBalanceLogCondition.id!=undefined)){
			$scope.userBalanceLogCondition.id = null;
		}
		var userBalanceLogListDataTable= $('#userBalanceLogDataTable').DataTable();
		userBalanceLogListDataTable.ajax.reload();
	}
	$scope.datap = function(){
		$('#inputStartTime').datepicker({
			language: 'zh-CN'
			
		});
		$('#inputEndTime').datepicker({
			language: 'zh-CN'
		});
		
	};
}]);

angular.module('backApp').controller('userBalanceLogFormCtrl',  ['$rootScope', '$scope','$stateParams', 'FileUploader', function($rootScope, $scope,  $stateParams, FileUploader) {
  $scope.$on('$viewContentLoaded', function() { 
	  $.ajax({
          type: "POST",
          url: "../user/getUserListForSelect",
          success: function (result) {
          $scope.userList = result.data;
          }

      });
	        $scope.datap();
	        $scope.disabledSelect = true;
			if($stateParams.id > 0){ // is update
				var reqData= $stateParams;
				var url = "../userBalanceLog/getUserBalanceLog";
				jsonGet(url, reqData, function(result) {
					if (result.flag == RESULT_FLAG_SUCCESS) {
						$scope.userBalanceLog = result.data;
						$scope.userBalanceLog.changeTime = dateTimeString($scope.userBalanceLog.changeTime);
						$scope.showDelBtn = true;
						$scope.disabledText =true;
						$scope.$apply();
						$("#selectName").remove();
					} else {
						toastr.error(result.msg, COMMON_LABEL_ERROR);
					}
			    }, true, false);
				
			}else{
				$("#InputName").remove();
				$scope.showDelBtn = false;
				$scope.disabledText =false;
				$scope.userBalanceLog ={"changeType" : 0};
			}
	});
	// update or add an userBalanceLog
	$scope.toSaveuserBalanceLog = function(){
		var datetimestamp = Date.parse($scope.userBalanceLog.changeTime).toString();  
		$scope.userBalanceLog.changeTime = datetimestamp;
		var reqData = $scope.userBalanceLog;
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
		if($stateParams.id > 0){ // is update
			var url = "../userBalanceLog/updateUserBalanceLog";
			jsonPost(url, reqData, callback, true, false);
			function callback(result){
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('userBalanceLogList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    };
		}else{ // is add
			var url = "../userBalanceLog/addUserBalanceLog";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('userBalanceLogList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}
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
		if($('#changeType').val()==""){
			errorMsg += "请选择消费类型<br>";
			$('#changeType').parent().addClass("has-error");
		}else{
			$('#changeType').parent().removeClass("has-error");
		}
		if($('#changeAmount').val()==""){
			errorMsg += "改变金额不能为空<br>";
			$('#changeAmount').parent().addClass("has-error");
		}else{
			$('#changeAmount').parent().removeClass("has-error");
		}
		if($('#changeType').val()<5 && $('#changeAmount').val()>0){
			 errorMsg += "线下提现，金额必须为负数！！<br>";
			$('#changeAmount').parent().addClass("has-error");
		}else{
			$('#changeAmount').parent().removeClass("has-error");
		}
		if($('#changeTime').val()==""){
			errorMsg += "改变时间不能为空<br>";
			$('#changeTime').parent().addClass("has-error");
		}else{
			$('#changeTime').parent().removeClass("has-error");
		}
		if($('#chargeOrderNumber').val()==""){
			errorMsg += "单号不能为空<br>";
			$('#chargeOrderNumber').parent().addClass("has-error");
		}else{
			$('#chargeOrderNumber').parent().removeClass("has-error");
		}
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	};
	
	// delete an admin
	$scope.toDeleteuserBalanceLog = function(){
		if($stateParams.id){
			bootbox.confirm("是否删除?", function (result) {
				if(result){
					var reqData= $stateParams;
					var url = "../userBalanceLog/deleteUserBalanceLog";
					jsonGet(url, reqData, callback, true, false);
					function callback(result) {
						if (result.flag == RESULT_FLAG_SUCCESS) {
							toastr.success(result.msg, COMMON_LABEL_SUCCESS);
							$rootScope.$state.go('userBalanceLogList');
						} else {
							toastr.error(result.msg, COMMON_LABEL_ERROR);
						}
				    }
				}
			})
		}
	};
	
$scope.changeType = function(){
	 var changeType = $("#changeType").val();
	 if(changeType<5){
		 $("#changeAmount").attr("max","0"); 
		 $("#changeAmount").attr("min","-99999"); 
	 }
	 if(changeType>5){
		 $("#changeAmount").attr("min","1"); 
		 $("#changeAmount").attr("max","99999");
	 }
};
	$scope.changeSelect = function(){
		   var right_id = "userName";  
           var url = "../user/getUserListByUserName";  
           var skeyword = $("#userName").val();
               $.ajax({
               	   type: "POST",
                   url: url,
                   data: {"skeyword": skeyword},
                   success: function (result) {
                   	var data = result.data;
                   	var result = new Array();  
                   	$.each(data, function(i, item){  
                        result.push(item.userName);  
                    });  
                    $('#userName').autocomplete({  
                        source: result  
                    }); 
                   }

               });
	};
	
	$scope.datap = function(){
		$('#changeTime').datetimepicker({
			defaultDate : new Date(),
			language: 'zh-CN'
		});
	};
}]);
