angular.module('frontApp').controller('myWareFormCtrl', ['$rootScope', '$scope','$stateParams','$state', 'settings','FileUploader', function($rootScope, $scope,$stateParams,$state, $http,FileUploader) {

	var wareId;
	
	// 用于在LIST DIV通知这个DIV更新当前要UPDATE的资料
	$rootScope.reloadWare = function(paramWareId){
		wareId = paramWareId;
		initWareTypeSteSelection($scope);
	    initWareTypeSrcSelection($scope);
	    initWareTypeMchSelection($scope);
	    $scope.initPicFileUploader();
		
		if(wareId > 0){ // is update get ware
			getWare();
		}else{
			$scope.ware={};
			$scope.ware.price = 10000;
			$scope.ware.shopId = $rootScope.curShop.id;
			$scope.ware.isNew=0;//默认不是新品
			$scope.ware.isEnable=1;//默认上架
			$scope.ware.priority=1;
			$scope.ware.visitCnt=0;
			$scope.ware.upCnt=0;
			$scope.ware.favCnt=0;
			CKEDITOR.replace('descriptionCn');
			CKEDITOR.replace('descriptionEn');
		}
		
		//CKEDITOR.replaceAll();

	}
	
	$scope.$on('$viewContentLoaded', function() { 
		
	    
	});
	
	// update or add an admin
	$scope.toSaveWare = function(){
		$scope.ware.descriptionCn = CKEDITOR.instances.descriptionCn.getData();
		$scope.ware.descriptionEn = CKEDITOR.instances.descriptionEn.getData();
		var reqData = $scope.ware;
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
	
		if(wareId > 0){ // is update
			var url = "../ware/updateWare";
			jsonPost(url, reqData, callback, true, false);
			function callback(result){
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.reloadWareList();
					$scope.toWareList();
				
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    };
			
		}else{ // is add
			var url = "../ware/addWare";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.reloadWareList()
					$scope.toWareList();
					
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}
	};
	
	// validate the form input while submit
	$scope.hasFormError = function(){
		var errorMsg = "";
		if($('#wareNameCn').val()==""){
			errorMsg += "中文标题不能为空<br>";
			$('#wareNameCn').parent().addClass("has-error");
		}else{
			$('#wareNameCn').parent().removeClass("has-error");
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
			
	    if(CKEDITOR.instances.descriptionCn.getData() == ""){
			errorMsg += "内容不能为空<br>";
			$('#descriptionCn').parent().addClass("has-error");
		}else{
			$('#descriptionCn').parent().removeClass("has-error");
		}
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	};
	
	
	
	//图片上传
	$scope.initPicFileUploader = function(){
		$scope.picFileUploader = new FileUploader();
		initUploader($scope.picFileUploader,callback);
		function callback(result){
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				var retFile = result.data;
				$scope.ware.picFileId = retFile.id;
				$scope.ware.picFile = retFile;
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
		}
		
	};
	
	//获取商品信息
	function getWare(){
		var req={'id':wareId};
		var url = "../ware/getWare";
		jsonGet(url, req, callback,true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.ware= result.data;
				CKEDITOR.replace('descriptionCn');
				CKEDITOR.replace('descriptionEn');
				$scope.$apply();
			} else {
				//toastr.error("", COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	$scope.toWareList=function(){
		$scope.switchTab('myWareList');
	}
	
}]);
	