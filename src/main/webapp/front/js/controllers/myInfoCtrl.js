angular.module('frontApp').controller('myInfoCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	
	
	$scope.$on('$includeContentLoaded', function() { 
		initUserInfo();
	});
	
	
	function initUserInfo(){
		if(!$rootScope.curUserId){
			window.location.href="#/main/home";
			return;
		}
		var reqData={'id':$rootScope.curUserId};
		var url = "../user/getUser";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.user= result.data;
				
				$("#userRePwd").val(result.data.password);
				$scope.$apply();
			} else {
				toastr.error("您还没有登录账号或者访问已过期,请重新登录", COMMON_LABEL_ERROR);
			}
	    }
	}
	$scope.updateUser = function(){
		
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
	
		var url = "../user/updateUser";
		var reqData = $scope.user;
		
	    
	    jsonPost(url, reqData, callback, true, false);
	    
		function callback(result) {

			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.$apply();
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
			
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
		
	}
	
	
	$scope.hasFormError = function(){
		var errorMsg = "";
		if($('#userAccount').val()==""){
			errorMsg += "账户不能为空<br>";
			$('#userAccount').parent().addClass("has-error");
		}else{
			$('#userAccount').parent().removeClass("has-error");
		}
		if($('#userPwd').val()==""){
			errorMsg += "密码不能为空<br>";
			$('#userPwd').parent().addClass("has-error");
		}else{
			$('#userPwd').parent().removeClass("has-error");
		}
		if($('#userRePwd').val()==""){
			errorMsg += "再次输入密码不能为空<br>";
			$('#userRePwd').parent().addClass("has-error");
		}else{
			$('#userRePwd').parent().removeClass("has-error");
		}
		
		if( $('#userPwd').val()!="" && $('#userRePwd').val()!=""  ){
			if($('#userPwd').val()!=$('#userRePwd').val()){
				errorMsg += "两次输入密码不相同<br>";
				$('#userPwd').parent().addClass("has-error");
				$('#userRePwd').parent().addClass("has-error");
			}else{
				$('#userPwd').parent().removeClass("has-error");
				$('#userRePwd').parent().removeClass("has-error");
			}
		}
		if($('#userName').val()==""){
			errorMsg += "用户名不能为空<br>";
			$('#userName').parent().addClass("has-error");
		}else{
			$('#userName').parent().removeClass("has-error");
		}
		if($('#userEmail').val()==""){
			errorMsg += "电子邮箱不能为空<br>";
			$('#userEmail').parent().addClass("has-error");
		}else{
			$('#userEmail').parent().removeClass("has-error");
		}
		
		if($('#telephone').val()==""){
			errorMsg += "手机/电话不能为空<br>";
			$('#tel').parent().addClass("has-error");
		}else{
			$('#tel').parent().removeClass("has-error");
		}
		
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	}
	
	
	
}]);