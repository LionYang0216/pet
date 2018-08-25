/**
 * @Description: 后台登录控制器
 * @author: lion
 * @date:2017-08-13
 */
angular.module('backApp').controller('loginCtrl', ['$scope', '$rootScope', function($scope, $rootScope) {
	 
	 $(document).ready(function(){
		 $scope.initCookieAdmin();
	 }); 
	 
	
	// 如果未登录，初始化登录输入框，通过cookie
	$scope.initCookieAdmin = function(){
    	var account = getCookie("adminLoginAccount");
    	var pwd = getCookie("adminLoginPwd");
    	if(account){
    		$("#adminLoginAccount").val(account); 
    		$("#adminLoginPwd").val(pwd);
    	}
	}
	
	
	
	$scope.toLogin = function() {
		var reqData = $scope.admin;
		var url = "../admin/toLogin";
		var account = $("#adminLoginAccount").val();
    	var pwd = $("#adminLoginPwd").val();
	    if(account==""||pwd==""){	
	    	toastr.error("账户密码不能为空！", COMMON_LABEL_ERROR);
        }else{
        	reqData={
        		'account':	account,
        		'password':pwd
        	};
        	
        	jsonPost(url, reqData, callback, false, false);
        }
		
		
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				var account = $("#adminLoginAccount").val();
				var pwd = $("#adminLoginPwd").val();
				setCookie("adminLoginAccount", account, 1);
				setCookie("adminLoginPwd",pwd, 1);
				sessionAdmin = result.data;
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				location.href = "./backIndex";
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	
}]);

	