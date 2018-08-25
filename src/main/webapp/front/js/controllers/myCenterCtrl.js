angular.module('frontApp').controller('myCenterCtrl', ['$rootScope', '$scope', 'settings','$timeout', function($rootScope, $scope, $http,$timeout) {
	

	
	$scope.$on('$viewContentLoaded', function() { 
		var curTab = getCookie("curTab");
		if((curTab==undefined)||(curTab=="")){
			curTab = "myWorkList";
		}
		
		 $timeout(function () {
			$scope.switchTab(curTab);
		 }, 500);
		
	});
	
	
	$rootScope.switchTab=function(tab){
		$scope.switchTab(tab)
	}
	
	$scope.switchTab = function(tab){
		$scope.hideAllDiv();
	
		
		
		if(tab=="myShop"){
			if( ($rootScope.curShop) && ($scope.curShop.status == 1) ){//有商店并且审批通过
				$("#myWareListDiv").fadeIn();
			}else{
				$("#myShopFormDiv").fadeIn();
			}
			
		}
		else{
			$("#"+tab+"Div").fadeIn();
		}
		
		if(tab=="myWareForm"){ // 如果在wareForm下，则刷新时直接回到SHOP
			tab = "myShop";
		}
		
		if(tab=="myWorkForm"){ // 如果在workForm下，则刷新时直接回到workList
			tab = "myWorkList";
		}
		
		if((tab=="myWareList")||(tab=="myShopForm")){ // 店铺两种情况
			tab = "myShop";
		}
		
		if(tab=="myDrawForm"){ // 提现
			$("#myDrawFormDiv").fadeIn();
		}
		
		if(tab=="myPayForm"){ // 充值
			$("#myPayFormDiv").fadeIn();
		}
		
		setCookie("curTab",tab);	// 设置当前选择的DIV，让用户刷新页面时，可以自动回到那个MY系列的DIV
		
		// 让TAB选中的变灰效果
		var targetObj = $("#" + tab + "Tab"); 
		targetObj.parent().parent().parent().find('a').removeClass("selectActive"); // let other btn as same parent reset default
		targetObj.addClass("selectActive"); // let this btn highlight
	}
	
	$scope.hideAllDiv = function(){		
		//批量选项div隐藏
		$("#myDivList").find("[name='myDiv']").hide();
	}
	
	
	
	
}]);