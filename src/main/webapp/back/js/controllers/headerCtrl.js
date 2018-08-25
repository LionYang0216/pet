/* Setup Layout Part - Header */
angular.module('backApp').controller('headerCtrl', ['$rootScope', '$scope', function($rootScope,$scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initHeader(); // init header
        $scope.curUser=$rootScope.curUser;
    });
    
 // call logout function
	$scope.toLogout = function(){    		
		var url = "../admin/logout";
		jsonPost(url, null, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$rootScope.curAdmin = null; // clear current js session admin
				location.href = "./login";
			} else {
				bootbox.alert(ret.msg);
			}
	    }
	}// end toLogout
    
}]);