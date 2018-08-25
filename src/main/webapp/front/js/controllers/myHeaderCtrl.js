angular.module('frontApp').controller('myHeaderCtrl', ['$rootScope', '$scope','$stateParams','$state', 'settings','FileUploader', function($rootScope, $scope,$stateParams,$state, $http,FileUploader) {
	
	$scope.$on('$includeContentLoaded', function() { 
		$scope.user=$rootScope.curUser;
		if(!$scope.user.headFileId){
			$scope.user.headFilePathLayout ="../assets/pages/img/avatars/default.png";
		}
		$scope.initHeaderFileUploader();
	});
	
	
	$scope.changeHeadImg=function(){
		if(!$rootScope.curUserId||!$scope.user.headFileId){
			return;
		}
		var reqData= {
			"userId":$rootScope.curUserId,	
			"updateFileId":$scope.user.headFileId
		};
		var url = "../user/updateUserHeadFile";
		
		jsonGet(url, reqData, callback, true, false);
		
		function callback(result){
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$scope.$apply();
				$("#btnChangeHeader").hide();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    };
	}
	
	//图片上传
	$scope.initHeaderFileUploader = function(){
		$scope.headerFileUploader = new FileUploader();
		initUploader($scope.headerFileUploader,callback);
		function callback(result){
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				var retFile = result.data;
				$scope.user.headFileId = retFile.id;
				$scope.user.headFile = retFile;
				$scope.user.headFilePathLayout = "../upload/" + retFile.filePath;
				$scope.changeHeadImg();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
		}
		
	};
	
	
    $scope.addWork=function (){
		$scope.switchTab('myWorkForm');
		$rootScope.reloadWork(0);
	}
	
}]);