function initProdTypePhySelection($scope){
	  var url = WEB_ROOT_PATH + "/prodTypePhy/getProdTypePhySelect";
	   jsonGet(url, '', callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.prodTypePhyList=result.data;
				//$scope.$apply();
			}
	    }
}
function initProdTypeAreSelection($scope){
	  var url = WEB_ROOT_PATH +"/prodTypeAre/getProdTypeAreSelect";
	   jsonGet(url, '', callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.prodTypeAreList=result.data;
				//$scope.$apply();
			}
	    }
}
function initProdTypeBizSelection($scope){
	  var url = WEB_ROOT_PATH +"/prodTypeBiz/getProdTypeBizSelect";
	   jsonGet(url, '', callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.prodTypeBizList=result.data;
				//$scope.$apply();
			}
	    }
}
function initProdTypeConSelection($scope){
	  var url = WEB_ROOT_PATH +"/prodTypeCon/getProdTypeConSelect";
	   jsonGet(url, '', callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.prodTypeConList=result.data;
				//$scope.$apply();
			}
	    }
}
function initProdTypeHigSelection($scope){
	  var url = WEB_ROOT_PATH +"/prodTypeHig/getProdTypeHigSelect";
	   jsonGet(url, '', callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.prodTypeHigList=result.data;
				//$scope.$apply();
			}
	    }
}
function initProdTypeJarSelection($scope){
	  var url = WEB_ROOT_PATH +"/prodTypeJar/getProdTypeJarSelect";
	   jsonGet(url, '', callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.prodTypeJarList=result.data;
				//$scope.$apply();
			}
	    }
}
function initProdTypeNekSelection($scope){
	  var url = WEB_ROOT_PATH +"/prodTypeNek/getProdTypeNekSelect";
	   jsonGet(url, '', callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.prodTypeNekList=result.data;
				//$scope.$apply();
			}
	    }
}
function initProdTypePicSelection($scope){
	  var url = WEB_ROOT_PATH +"/prodTypePic/getProdTypePicSelect";
	   jsonGet(url, '', callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.prodTypePicList=result.data;
				//$scope.$apply();
			}
	    }
}
function initProdTypeSouSelection($scope){
	  var url = WEB_ROOT_PATH +"/prodTypeSou/getProdTypeSouSelect";
	   jsonGet(url, '', callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.prodTypeSouList=result.data;
				//$scope.$apply();
			}
	    }
}
function initProdTypeWidSelection($scope){
	  var url = WEB_ROOT_PATH +"/prodTypeWid/getProdTypeWidSelect";
	   jsonGet(url, '', callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.prodTypeWidList=result.data;
				//$scope.$apply();
			}
	    }
}
function initProdCostSelection($scope){
	  var url = WEB_ROOT_PATH +"/prodCost/getProdCostSelect";
	   jsonGet(url, '', callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.prodCostList=result.data;
				//$scope.$apply();
			}
	    }
}