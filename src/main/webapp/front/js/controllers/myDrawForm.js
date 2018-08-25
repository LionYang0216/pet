angular.module('frontApp').controller('myDrawFormCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	
	var userId=$rootScope.curUserId;
	var userBalance=$rootScope.curUser.balanceTotal;

	$scope.$on('$includeContentLoaded', function() { 
		$scope.userDraw={};
		$scope.initUser();
	});
	
	
	$scope.toMybalance=function(){
		$scope.switchTab('myBalance');
	}
	

	$scope.addUserDraw=function(){
		
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
	
		var url = "../userDraw/addUserDraw";
		var reqData = $scope.userDraw;
		
		jsonPost(url, reqData, callback, true, false);
		    
		function callback(result) {

			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.$apply();
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$scope.switchTab('myBalance');
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	
	}
	
	
	$scope.hasFormError = function(){
		var errorMsg = "";
		if($('#drawAmount').val()==""){
			errorMsg += "转账金额不能为空<br>";
			$('#drawAmount').parent().addClass("has-error");
		}else{
			$('#drawAmount').parent().removeClass("has-error");
		}
		
		if(isNaN($('#drawAmount').val())) {
			errorMsg += "转账金额必须是数字<br>";
		} 

		if(userBalance && userBalance < $('#drawAmount').val()){
			errorMsg += "转账金额超出余额<br>";
		}
		
		if($('#userRemark').val()==""){
			errorMsg += "备注不能为空<br>";
			$('#userRemark').parent().addClass("has-error");
		}else{
			$('#userRemark').parent().removeClass("has-error");
		}
		
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		
		
		return false;
		
	}
	
	// 查找USER
	$scope.initUser = function(){

		var url = "../user/getUser";
		var req={
			"id":$rootScope.curUserId	
		}
		jsonGet(url, req, callback, true, false);
		function callback(result) {
			$scope.user = result.data; 
			$scope.$apply();
	    }

	}
	

}]);