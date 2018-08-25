// user login ctrl
frontApp.controller('userLoginCtrl', ['$rootScope', '$scope', 'settings','$translate', function($rootScope, $scope, $http, $translate) {
    $scope.$on('$includeContentLoaded', function() {
    	
    	$scope.back();
    	
    	// 如果未登录，初始化登录输入框，通过cookie
    	$scope.initCookieUser = function(){
        	var account = 	getCookie("userLoginAccount");
        	var pwd 	= 	getCookie("userLoginPwd");
        	if(account){
        		$('#userLoginAccount').val(account);
        		$('#userLoginPwd').val(pwd);
        	}
    	}
    	
    	// 查找当前 session 有没有登录用户
		$scope.initSessionUser = function(){
			if($rootScope.curUser  == undefined ){
				var url = "../user/getSessionUser";
    			jsonGet(url, null, callback, true, false);
    			function callback(result) {
    				if (result.flag == RESULT_FLAG_SUCCESS) {
    					$rootScope.curUser  = result.data;
    					$rootScope.curUserName = $rootScope.curUser.userName; 	// 这里用于赋值你的header当前登录人那里用 
    					$rootScope.curUserId = $rootScope.curUser.id; 			// 这里用于赋值你的header当前登录人那里用 
    					$('#userBtn').attr("href","#/my/myCenter"); 			// 点击右上用户去用户中心
    					$('#userBtn').removeAttr("data-toggle");
    					$("#logoutLi").show();									// 显示LOGOUT btn
    					$scope.initUserShop();									// 看看他有没商家	
    				} else {
    					// still not login
    					$rootScope.curUserName = $translate.instant("HEAD_TITLE_PLEASELOGIN") 						// 这里用于赋值你的header当前登录人那里用
    					$rootScope.curUser  = null;
    					$rootScope.curUserId = null;
    					$rootScope.curShop 	= null;
    					$rootScope.curShopId = null;
        				$('#userBtn').attr("href","./#userLoginModal"); 		// 点击右上用户弹出登录框体
    					$scope.initCookieUser(); 								// 初始化输入框cookie
    					$("#logoutLi").hide();
    				}
    		    }
			}
		}// end initSessionUser
		
		$scope.initSessionUser();
		   		
    });
    
    
    $scope.toForget = function(){
    	$("#loginBtn").hide();
    	$("#userLoginDiv").hide();
    	$("#forgetBtn").show();
    	$("#backBtn").show();
    	$("#forgetA").hide();
    	$("#loginMsg").hide();
    }
    
    $scope.back = function(){
    	$("#loginBtn").show();
    	$("#userLoginDiv").show();
    	$("#forgetBtn").hide();
    	$("#backBtn").hide();
    	$("#forgetA").show();
    	$("#loginMsg").hide();
    }
    
    
    
    $scope.toReg = function(){
    	$('#userLoginModal').modal('hide');
    	window.location.href = "#/user/userReg";	
    }
    
    $scope.initUserShop = function(){
    	
    	if(!$rootScope.curUser||!$rootScope.curUserId){
    		return;
    	}
    	
		var req={'userId':$rootScope.curUserId};
		var url = "../shop/getShopByUser";
		jsonGet(url, req, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {				
				if(result.data){
					$rootScope.curShop		= 	result.data;
					$rootScope.curShopId	=	result.data.id;
				}
			} else {
				//toastr.error("", COMMON_LABEL_ERROR);
			}
	    }
	}
    
   
    
    $scope.login = function(){
    	var url = "../user/login";
    	
    	var account = $("#userLoginAccount").val();
    	var pwd = $("#userLoginPwd").val();
    	
	    if(account==""||pwd==""){	
	    	$('#userLoginAccount').parent().addClass("has-error");
	    	$('#userLoginPwd').parent().addClass("has-error");
	    	toastr.error("帐号密码不能为空", COMMON_LABEL_ERROR);
        }else{
        	$('#userLoginAccount').parent().removeClass("has-error");
	    	$('#userLoginPwd').parent().removeClass("has-error");
        	reqData={
        		'account':account,
        		'password':pwd
        	};
        	
        	jsonPost(url, reqData, callback, true, false);
        }
		
		function callback(result) {
			
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$rootScope.curUser  	= result.data; 			// 全局保存这个session user , 便于其他地方用
				$rootScope.curUserId	= result.data.id;
				$rootScope.curUserName  = result.data.userName; // 全局保存这个session user , 便于其他地方用
				setCookie("userLoginAccount", result.data.account,1);
				setCookie("userLoginPwd", result.data.password,1);
				$scope.initUserShop();							// initial user shop
				$scope.$apply();
				$('#logoutLi').show();
				$('#userLoginModal').modal('hide');
				$('#userBtn').attr("href","#/my/myCenter"); // 点击右上用户去用户中心
				$('#userBtn').removeAttr("data-toggle");
				window.location.reload();
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
			} else {
				$('#logoutLi').hide();
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
    }
    
    
    $scope.forgetPwd=function(){
    	var url = "../user/forgetPwd";
    	var account = $("#userLoginAccount").val();
    	
		if(account==""){	
	    	$('#userLoginAccount').parent().addClass("has-error");
	    	toastr.error("帐号不能为空", COMMON_LABEL_ERROR);
	    }else{
	      	$('#userLoginAccount').parent().removeClass("has-error");
	      	var reqData={"account":account};
			jsonGet(url, reqData, callback, true, false);
	    }
  		
  		function callback(result) {
  			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$("#loginMsg").html(result.msg);
				$("#loginMsg").show();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
  			
  		}
    	
    }
    
}]);

//user reg ctrl
angular.module('frontApp').controller('userRegCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	$scope.$on('$viewContentLoaded', function() {  		
		// this is user reg ctrl 注册处理
		$scope.user = {};
	});
	
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
		
		if($('#userRegTel').val()==""){
			errorMsg += "手机/电话不能为空<br>";
			$('#userRegTel').parent().addClass("has-error");
		}else{
			$('#userRegTel').parent().removeClass("has-error");
		}
		
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	}
	
	$scope.reset = function(){
		$scope.user = {};
		$scope.$apply();
	}
	
	$scope.reg = function(){

		if($scope.hasFormError()){ // if there is form input error
			return;
		}
		
		var url = "../user/reg";
    	var reqData = $scope.user;
		
    	var account=$("#loginName").val();
    	var pwd=$("#loginPwd").val();
    	
	    
	  jsonPost(url, reqData, callback, true, false);
	    
		function callback(result) {
			$("#loginMsg").hide();
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				window.location.href = "#/main/home";
				window.location.reload();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
    }
	

	
}]);