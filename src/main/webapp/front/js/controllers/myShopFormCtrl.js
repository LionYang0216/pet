//shop form ctrl
angular.module('frontApp').controller('myShopFormCtrl', ['$rootScope', '$scope','$stateParams','$state', 'settings','FileUploader', function($rootScope, $scope,$stateParams,$state, $http,FileUploader) {
	
	
	$scope.$on('$includeContentLoaded', function() { 

		$scope.initLogoFileUploader();
		$scope.initRegFileUploader();
				
		
		$scope.levels = [{ id: 1, name: '1级'}, { id: 2, name: '2级'}, { id: 3, name: '3级'},{ id: 4, name: '4级'},{ id: 5, name: '5级'}];
	
		if($scope.curShop){//已申请或已有店铺
			if($scope.curShop.status == 0){			// 审批中
				$scope.shop = $scope.curShop;		// 赋予上一次的申请记录
				$scope.shopBtnText = "等待审批";
				$("#shopInfoForm").fadeIn();		// 显示申请表单
				$("#saveShopBtn").fadeOut();		// 隐藏提交按钮
				$("#backShopBtn").fadeOut();			// 隐藏后退按钮
				$("#shopIsRequestDiv").fadeIn();	// 显示正在申请字样
			}else if($scope.curShop.status == 1){	// 已通过，编辑状态
				$scope.shop = $scope.curShop;		// 赋予上一次的申请记录
				$scope.shopBtnText = "更新店铺信息";
				$("#shopInfoForm").fadeIn();		// 显示申请表单
				$("#shopIsRejectDiv").fadeOut();	// 显示拒绝信息
				$("#saveShopBtn").fadeIn();			// 显示保存按钮
				$("#backShopBtn").fadeIn();			// 显示后退按钮
				$scope.shop.status = 1;				// reset 状态为提交申请状态
			}else if($scope.curShop.status == 2){	// 被拒绝
				$scope.shop = $scope.curShop;		// 赋予上一次的申请记录
				$scope.shopBtnText = "重新提交申请";
				$("#shopInfoForm").fadeIn();		// 显示申请表单
				$("#shopIsRejectDiv").fadeIn();		// 显示拒绝信息
				$("#saveShopBtn").fadeIn();			// 显示保存按钮
				$("#backShopBtn").fadeOut();		// 隐藏后退按钮
				$scope.shop.status = 0;				// reset 状态为提交申请状态
			}	
		}else{										// 从来没申请
			$("#shopIsNoneDiv").fadeIn();			// 显示可以申请信息
			$("#shopInfoForm").fadeIn();			// 显示申请表单
			$("#backShopBtn").fadeOut();			// 隐藏后退按钮
			$scope.shopBtnText = "提交申请";
			$scope.shop={status:0,level:1,userId:$scope.curUserId,visitCnt:1,favCnt:1};		// 初始隐藏值

		}
		
		CKEDITOR.replace('introEnt');
		CKEDITOR.replace('introLog');
		CKEDITOR.replace('introSrv');
	});
	
	$scope.toSaveShop = function(){
		$scope.shop.introEnt = CKEDITOR.instances.introEnt.getData();
		$scope.shop.introLog = CKEDITOR.instances.introLog.getData();
		$scope.shop.introSrv = CKEDITOR.instances.introSrv.getData();
		var reqData = $scope.shop;
//		log(reqData);
		if($scope.hasFormError()){ // if there is form input error
			return;
		}

		if($rootScope.curShop){ // is update
			var url = "../shop/updateShop";
			jsonPost(url, reqData, callback, true, false);
			function callback(result){
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					if($scope.shop.status==0){
						$scope.shopBtnText = "等待审批";
						$("#shopInfoForm").fadeIn();		// 显示申请表单
						$("#saveShopBtn").fadeOut();		// 隐藏提交按钮
						$("#backShopBtn").fadeOut();		// 隐藏提交按钮
						$("#shopIsRejectDiv").fadeOut();	
						$("#shopIsRequestDiv").fadeIn();	// 显示正在申请字样
					}else if($scope.shop.status==1){
						$scope.shopBtnText = "更新店铺信息";
						$("#shopInfoForm").fadeIn();		// 显示申请表单
						$("#shopIsRejectDiv").fadeOut();	// 显示拒绝信息
						$("#saveShopBtn").fadeIn();			// 显示保存按钮
						$("#backShopBtn").fadeIn();			// 显示提交按钮
					}
					$scope.curShop = $scope.shop;			// 更新scope.curShop
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    };
			
		}else{ // is add
			var url = "../shop/addShop";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$("#saveShopBtn").fadeOut();		// 隐藏提交按钮
					$("#backShopBtn").fadeOut();		// 隐藏提交按钮
					$("#shopIsRequestDiv").fadeIn();	// 显示正在申请字样
					$("#shopIsNoneDiv").fadeOut();	
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}
	};
	

	
	
	//logo上传
	$scope.initLogoFileUploader = function(){
		$scope.logoFileUploader = new FileUploader();
		initUploader($scope.logoFileUploader,callback);
		function callback(result){
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				var retFile = result.data;
				$scope.shop.logoFileId = retFile.id;
				$scope.shop.logoFile = retFile;
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
		}
		
	};
	//注册文件上传
	$scope.initRegFileUploader = function(){
		$scope.regFileUploader = new FileUploader();
		initUploader($scope.regFileUploader,function(result){
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				var retFile = result.data;
				$scope.shop.regFileId = retFile.id;
				$scope.shop.regFile = retFile;
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
		});
		
		
	}
	
	// validate the form input while submit
	$scope.hasFormError = function(){
		var errorMsg = "";
		if($('#shopNameCn').val()==""){
			errorMsg += "中文名不能为空<br>";
			$('#shopNameCn').parent().addClass("has-error");
		}else{
			$('#shopNameCn').parent().removeClass("has-error");
		}
		var regFileId = $scope.shop.regFileId;
		if(typeof(regFileId) == "undefined" || regFileId ==""){
	    	errorMsg += "执照不能为空<br>";
			$('#regFileId').parent().addClass("has-error");
		}else{
			$('#regFileId').parent().removeClass("has-error");
		}
	
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	};
	
	$scope.toMyWareList =function(){
		$scope.switchTab('myWareList');
	}
	
}]);
