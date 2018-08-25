//list ctrl
angular.module('backApp').controller('prodCostListCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	
	$scope.$on('$viewContentLoaded', function() {  				
		var areListDataTable = new Datatable().init({
            src: $("#prodCostListDataTable"),
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
    				"url" : "../prodCost/getProdCostList", // ajax
    				"contentType": "application/json; charset=utf-8",
    				"data": function (dtRequest) {
    					  var reqData = JSON.stringify(dtRequest);
    				      return reqData;
                     }
    			},
    			"columns" : [  {
    				"data" : null
    			}, {
    				"data" : "cost"
    			}, {
    				"data" : "name"
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
    					var str = '<a class="btn default" href="#/prodCost/form/' + data + '">编辑</a>';
    					return str;
    				},
    				"targets" : 3,
    				'searchable' : false,
    				'orderable' : false
    			} ],
    			"order" : [ [ 1, "desc" ] ]
    		// set first column as a default sort by asc
    		}
        });
    	
	});
	
}]);

//form ctrl
angular.module('backApp').controller('prodCostFormCtrl', ['$rootScope', '$scope','$stateParams','$state', 'settings', function($rootScope, $scope,$stateParams,$state, $http) {
	// page load: retrieve admin
	$scope.$on('$viewContentLoaded', function() { 
		if($stateParams.id > 0){ // is update
			var reqData= $stateParams;
			var url = "../prodCost/getProdCost";
			jsonGet(url, reqData, callback, true, false);
			
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					$scope.prodCost = result.data;
					$scope.showDelBtn = true;
					$scope.$apply();
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}else{ // is add
			$scope.prodCost={cost:0};
			$scope.showDelBtn = false;
		}
		
	});
	
	// validate the form input while submit
	$scope.hasFormError = function(){
		var errorMsg = "";
		if($('#cost').val()==""){
			errorMsg += "费用不能为空<br>";
			$('#cost').parent().addClass("has-error");
		}else{
			$('#cost').parent().removeClass("has-error");
		}


		
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	}
	
	// update or add an prodCost
	$scope.toSaveProdCost = function(){

		var reqData = $scope.prodCost;
		
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
		
		if($stateParams.id > 0){ // is update
			var url = "../prodCost/updateProdCost";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('prodCostList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}else{ // is add
			var url = "../prodCost/addProdCost";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('prodCostList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}
	}
	
	// delete an prodCost
	$scope.toDeleteProdCost = function(){
		if($stateParams.id){
			bootbox.confirm("是否删除?", function (result) {
				if(result){
					var reqData= $stateParams;
					var url = "../prodCost/deleteProdCost";
					jsonGet(url, reqData, callback, true, false);
					function callback(result) {
						if (result.flag == RESULT_FLAG_SUCCESS) {
							toastr.success(result.msg, COMMON_LABEL_SUCCESS);
							$rootScope.$state.go('prodCostList');
						} else {
							toastr.error(result.msg, COMMON_LABEL_ERROR);
						}
				    }
				}
			})
		}
	}
	
}]);