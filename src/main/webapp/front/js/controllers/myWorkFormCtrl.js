angular.module('frontApp').controller('myWorkFormCtrl',  ['$rootScope', '$scope','$stateParams', 'FileUploader', function($rootScope, $scope,  $stateParams, FileUploader) {	
    
	$scope.prod = {};
	$scope.prod.prodPicList=[];
	
    $scope.$on('$includeContentLoaded', function() {
    
    });
	
	
	var workId;
	
	// 用于在LIST DIV通知这个DIV更新当前要UPDATE的资料
	$rootScope.reloadWork = function(paramId){
		workId = paramId;
		$scope.workId=workId;
		
		initProdTypeAreSelection($scope);
		initProdTypeBizSelection($scope);
		initProdTypeConSelection($scope);
		initProdTypeHigSelection($scope);
		initProdTypeJarSelection($scope);
		initProdTypeNekSelection($scope);
		initProdTypePicSelection($scope);

		initProdTypeWidSelection($scope);
		initProdCostSelection($scope);

		$scope.initPicFileUploader(0);
		
		if(workId > 0){ // is update get ware
			getProd();
		}else{
			$scope.initAddProd();

		}
		

	}
	
	
	function getProd(){
		var reqData= {
			"id":workId
		}
		var url = "../prod/getProd";
		jsonGet(url, reqData, function(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				var prod = result.data;
				if(prod&&prod.prodPicList&&prod.prodPicList.length>0){
					for(var i=0;i<prod.prodPicList.length;i++){
						$scope.initPicFileUploader(i);	
					}
				}else{
					$scope.initPicFileUploader(0);
				}
				if(prod.prodTypePicId==4){
					$scope.showTypeSou=true;
				}
				$scope.prod=prod;
				$scope.changePhy();

				CKEDITOR.replace('prodDescriptionCn');
				CKEDITOR.replace('prodDescriptionEn');
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }, false, false);
	


	}
	
	
	
	$scope.initAddProd=function(){
		
	    $scope.initPicFileUploader(0);
		$scope.prod.prodPicList[0].isMajor=1;//默认选中第一个为主要图片
		$scope.prod.prodCostId=$scope.prodCostList[0].id;//默认选中免费
		$scope.prod.nameCn="";
		$scope.prod.nameEn="";
		
		$scope.prod.prodTypeSouId=1;
		$scope.prod.prodTypePhyId=1;
		$scope.prod.isNew=0;//默认不是新品
		$scope.prod.visitCnt=0;//默认0 浏览
		$scope.prod.upCnt=0;//默认0 点击
		$scope.prod.favCnt=0;//默认0 收藏
		$scope.prod.isEnable=1;//默认上架

		$scope.prod.priority=1;
		
		CKEDITOR.replace('prodDescriptionCn');
		CKEDITOR.replace('prodDescriptionEn');
		
		CKEDITOR.instances.prodDescriptionCn.setData(" ");
		CKEDITOR.instances.prodDescriptionEn.setData(" ");

   }
  
	// update or add an admin
	$scope.toSaveProd = function(){
		$scope.prod.descriptionCn = CKEDITOR.instances.prodDescriptionCn.getData();
		$scope.prod.descriptionEn = CKEDITOR.instances.prodDescriptionEn.getData();
		$scope.prod.userId=$rootScope.curUserId;//产品归属登录者
		$scope.prod.prodTypePicId=4;
		var reqData = $scope.prod;
		
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
	
		if(workId > 0){ // is update
			var url = "../prod/updateProd";
			jsonPost(url, reqData, callback, true, false);
			function callback(result){
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$scope.switchTab('myWorkList');
					$rootScope.reLoadProdList();
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    };
			
		}else{ // is add
			var url = "../prod/addProd";
			$scope.prod.isEnable=2;
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$scope.switchTab('myWorkList');
					$rootScope.reLoadProdList();
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}
	};
	
	
	// validate the form input while submit
	$scope.hasFormError = function(){
		var errorMsg = "";
		if($('#nameCn').val()==""){
			errorMsg += "中文标题不能为空<br>";
			$('#nameCn').parent().addClass("has-error");
		}else{
			$('#nameCn').parent().removeClass("has-error");
		}
		
		var prodPicList=$scope.prod.prodPicList;
	
		//radio选中value为1，其它为0
		 var radio = document.getElementsByName("picIsMajor");  
		 var checkIdx=0;
		 for (i=0; i<radio.length; i++) {  
		        if (radio[i].checked) {  
		        	checkIdx=radio[i].dataset.picIndex;  
		        }else{
		        	$scope.prod.prodPicList[i].isMajor=0;
		        }  
		 }
		 
		for(var i=0;i<prodPicList.length;i++){
			if(prodPicList[i]==null) continue;
			if(checkIdx==i){
				var picFileId = prodPicList[i].picFileId;
				if(typeof(picFileId) == "undefined" || picFileId ==""){
			    	errorMsg += "产品主要图片不能为空<br>";
					$('#picFileId'+i).parent().addClass("has-error");
				}else{
					$('#picFileId'+i).parent().removeClass("has-error");
				}
				
				break;
			}
		}

			
	    if(CKEDITOR.instances.prodDescriptionCn.getData() == ""){
			errorMsg += "中文产品内容不能为空<br>";
			$('#prodDescriptionCn').parent().addClass("has-error");
		}else{
			$('#prodDescriptionCn').parent().removeClass("has-error");
		}
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	};
	
	
	// delete an admin
	$scope.toDeleteProd = function(){
		if($stateParams.id){
			bootbox.confirm("是否删除?", function (result) {
				if(result){
					var reqData= $stateParams;
					var url = "../prod/deleteProd";
					jsonGet(url, reqData, callback, true, false);
					function callback(result) {
						if (result.flag == RESULT_FLAG_SUCCESS) {
							toastr.success(result.msg, COMMON_LABEL_SUCCESS);
							$rootScope.$state.go('prodList');
						} else {
							toastr.error(result.msg, COMMON_LABEL_ERROR);
						}
				    }
				}
			})
		}
	};
	
	$scope.toAddPic = function(){
		var picIdx=$scope.prod.prodPicList.length-1;
		$scope.initPicFileUploader(picIdx+1);
	}
	
	$scope.toDeletePic = function(idx){
		var picLength=$scope.prod.prodPicList.length;
		if(picLength==1){
			toastr.error("产品需要保留一张主要图片", COMMON_LABEL_ERROR);
			return;
		}
		$scope.prod.prodPicList.splice(idx, 1);
		if(picLength==2){
			document.getElementsByName("picIsMajor")[0].checked=true;
		}
	}
	
	$scope.clickUploadBt = function(idx){
		var id="picFileUploader"+idx;;
		document.getElementById(id).click();

	}
	
	$scope.changePhy = function () { 
		   var newList=[];
		   var j=0;
	      if($scope.prod.prodTypePhyId!=1){
	    	var list=  $scope.prodTypePicList
	    	for(var i=0;i<list.length;i++){
	    		if(list[i].nameCn!="光身图库"){
	    			newList[j]=list[i];
	    			j++;
	    		}
	    	}
	    	   $scope.picTypeChange=true;
	    	   $scope.prodTypePicList=newList;
	      }else{
	    	  if($scope.picTypeChange){
	    		  initProdTypePicSelection($scope);
	    	  }
	      }
	} 
	
	$scope.changePic=function(){
		if($scope.prod.prodTypePicId==4){
			$scope.showTypeSou=true;
		}else{
			$scope.showTypeSou=false;
		}
	}
	
	//图片上传
	$scope.initPicFileUploader = function(idx){
		$scope.prod.prodPicList[idx]={};
		$scope["picFileUploader"+idx] = new FileUploader();
		initUploader($scope["picFileUploader"+idx],callback);
		function callback(result){
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				var retFile = result.data;
				$scope.prod.prodPicList[idx].picFileId = retFile.id;
				$scope.prod.prodPicList[idx].picFile = retFile;
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
		}
		
	};
	
	function initProdCostSelection($scope){
		  var url = "../prodCost/getProdCostSelect";
		   jsonGet(url, '', callback, false, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					$scope.prodCostList=result.data;
				}
		    }
	}
	
	$scope.toWorkList =function(){
		$scope.switchTab('myWorkList');
	}
 
}]);