angular.module('frontApp').controller('contentCtrl', ['$rootScope', '$scope','$stateParams','$state', 'settings', function($rootScope, $scope,$stateParams,$state, $http) {
	var code=$stateParams.id;
	$scope.$on('$viewContentLoaded', function() { 
		
		getContent();
	});
	
	//静态内容 
	function getContent(){
		var reqData= {
			"code":code	
		}
		var url = "../content/getContentByCode";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.content=result.data;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
}]);	