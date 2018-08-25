/* Setup Layout Part - Sidebar */
angular.module('backApp').controller('siderbarCtrl',
		[ '$rootScope', '$scope', function($rootScope, $scope) {
			$scope.$on('$includeContentLoaded', function() {
				Layout.initSidebar(); // init sidebar
				// log($rootScope.curAdmin); // 这里可以查出sessionAdmin
				// 里面的roleList以及每个role里面的nodeList, 后面会用到
				
				var url = "../node/getParentList";
				var reqData = null;
				jsonPost(url, reqData, callback, false, false);
				function callback(result) {
					$scope.nodeParentList = result.data;
					//log(result.data);
			    }
				
				
				
			});
		} ]);