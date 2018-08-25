function initWareTypeMchSelection($scope){
	  var url =  WEB_ROOT_PATH +"/wareTypeMch/getWareTypeMchSelect";
	   jsonGet(url, '', callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.wareTypeMchList=result.data;
			}
	    }
}

function initWareTypeSrcSelection($scope){
	  var url =  WEB_ROOT_PATH +"/wareTypeSrc/getWareTypeSrcSelect";
	   jsonGet(url, '', callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.wareTypeSrcList=result.data;
			}
	    }
}

function initWareTypeSteSelection($scope){
	  var url =  WEB_ROOT_PATH +"/wareTypeSte/getWareTypeSteSelect";
	   jsonGet(url, '', callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.wareTypeSteList=result.data;
			}
	    }
}

function initWareTypePrdSelection($scope){
	  var url =  WEB_ROOT_PATH +"/wareTypePrd/getWareTypePrdSelect";
	   jsonGet(url, '', callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.wareTypePrdList=result.data;
			}
	    }
}