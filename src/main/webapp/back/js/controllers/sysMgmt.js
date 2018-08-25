angular.module('backApp').controller('sysMgmtCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	$scope.$on('$viewContentLoaded', function() { 
		$scope.getNotUsedFileCnt();
	});
	
	// delete 
	$scope.deleteNotUsedFile = function(){
		bootbox.confirm("是否清理?", function (result) {
			if(result){
				var url = "../file/deleteNotUsedFile";
				jsonPost(url, null, callback, true, false);
				function callback(result) {
					if (result.flag == RESULT_FLAG_SUCCESS) {
						toastr.success(result.msg, COMMON_LABEL_SUCCESS);
						$scope.getNotUsedFileCnt();
						$rootScope.$state.go('sysMgmt');
					} else {
						toastr.error(result.msg, COMMON_LABEL_ERROR);
					}
			    }
			}
		});
	
	}
	
	// getNotUsedFileCnt 
	$scope.getNotUsedFileCnt = function(){
		var url = "../file/getNotUsedFileCnt";
		jsonPost(url, null, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.fileCnt=result.data; 
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	
	}
	
	
}]);
	