
/* Setup Layout Part - Header */
frontApp.controller('headerCtrl', ['$scope','$rootScope', '$translate',function($scope,$rootScope,$translate) {
    $scope.$on('$includeContentLoaded', function() {
        //Layout.initHeader(); // init header
    	//切换语言
    	
    	var language=getCookie("userLangSelect");
    	
    	if(language){//默认语言已存在
    		$scope.langSelect=language;
    	}else{//默认语言不存在
    		$scope.langSelect = "zh"; // 默认值
    		setCookie("userLangSelect",$scope.langSelect,1);
    	}
    	
		
		$translate.use($scope.langSelect)
		/**
		*@event 选择语言这个下拉框
		*/
		$scope.changeLang = function(langSelect){
			$rootScope.curLang = langSelect;
			setCookie("userLangSelect",langSelect,1);
			$translate.use(langSelect).then(function(){
				if($rootScope.curUser==null){
					$rootScope.curUserName = $translate.instant("HEAD_TITLE_PLEASELOGIN");
				}
			});
			$scope.$state.reload();
			
			//$scope.$state.transitionTo($scope.$state.current, null, {'reload':true});
		};
		
		if(!isPC()){
			$("#headerMenuDiv").hide();
		}
    });
    
    $scope.logout = function(){
    	var url = "../user/logout";
    	jsonPost(url, null, callback, true, false);
    	function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$rootScope.curUserName = $translate.instant("HEAD_TITLE_PLEASELOGIN");
				$rootScope.curUser = null; // 全局保存这个session user , 便于其他地方用
				$rootScope.curUserId = null;
				$rootScope.curShopId = null;
				$rootScope.curShop = null;
				
				$scope.$apply();
				$('#logoutLi').hide();
				$('#userBtn').attr("href","./#userLoginModal"); // 点击右上用户弹出登录框体
				$('#userBtn').attr("data-toggle","modal");
				
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				
			
				if(document.location.href.toString().indexOf("my")>-1){	// 如果当前在MY系列页面下，就要登出到首页的
					window.location.href = "#/main/home";
				}
				
				
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
    }
    
    // 判断是手机还是PC
    function isPC() {
        var userAgentInfo = navigator.userAgent;
        var Agents = ["Android", "iPhone",
                    "SymbianOS", "Windows Phone",
                    "iPad", "iPod"];
        var flag = true;
        for (var v = 0; v < Agents.length; v++) {
            if (userAgentInfo.indexOf(Agents[v]) > 0) {
                flag = false;
                break;
            }
        }
        return flag;
    }
    
}]);