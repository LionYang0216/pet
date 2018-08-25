angular.module('frontApp').controller('myFavCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	
	
	//配置  
    $scope.count = 0;  //总页数
    $scope.pPernum =10;  //每页数据条数
    //变量   
    $scope.pCurrent = 1;  
    $scope.pAllPage =0;  
    $scope.pages = []; 
    $scope.showType=1;
	
	$scope.$on('$includeContentLoaded', function() { 
		
		
		$scope.getProdFavList(1,$scope.pPernum,function(){ }); 

	    //首页  
	    $scope.pIndex = function(){  
	    	if($scope.showType==1){
	    		 $scope.getProdFavList(1,$scope.pPernum,function(){ });  //产品
	    	}else if($scope.showType==2){
	    		 $scope.getWareFavList(1,$scope.pPernum,function(){ });  //商品
	    	}else if($scope.showType==3){
	    		 $scope.getShopFavList(1,$scope.pPernum,function(){ });  //商店
	    	}
	    }  
	    //尾页  
	    $scope.pLast = function(){  
	    	var page=$scope.pAllPage;
	    	if($scope.showType==1){
	    		 $scope.getProdFavList(page,$scope.pPernum,function(){ });  //产品
	    	}else if($scope.showType==2){
	    		 $scope.getWareFavList(page,$scope.pPernum,function(){ });  //商品
	    	}else if($scope.showType==3){
	    		 $scope.getShopFavList(page,$scope.pPernum,function(){ });  //商店
	    	}
	    }  
	    //加载某一页  
	    $scope.loadPage = function(page){  
	    	if($scope.showType==1){
	    		 $scope.getProdFavList(page,$scope.pPernum,function(){ });  //产品
	    	}else if($scope.showType==2){
	    		 $scope.getWareFavList(page,$scope.pPernum,function(){ });  //商品
	    	}else if($scope.showType==3){
	    		 $scope.getShopFavList(page,$scope.pPernum,function(){ });  //商店
	    	}
	      
	    }; 
		
	});
	
	

	$scope.selectFav = function(event,value){
		$scope.showType=value;
		var targetObj = $(event.target); 

		targetObj.parent().parent().find('a').removeClass("myFavActive"); // let other btn as same parent reset default
		targetObj.addClass("myFavActive"); // let this btn highlight
		
		
		if($scope.showType==1){
   		 	$scope.getProdFavList(1,$scope.pPernum,function(){ });  //产品
	   	}else if($scope.showType==2){
	   		 $scope.getWareFavList(1,$scope.pPernum,function(){ });  //商品
	   	}else if($scope.showType==3){
	   		 $scope.getShopFavList(1,$scope.pPernum,function(){ });  //商店
	   	}
				
	}
	
	var reqData={
	    "draw": 1,
	    "columns": [
	        {
	            "data": null,
	            "name": "",
	            "searchable": false,
	            "orderable": false,
	            "search": {
	                "value": "",
	                "regex": false
	            }
	        },
	        {
	            "data": "id",
	            "name": "",
	            "searchable": false,
	            "orderable": true,
	            "search": {
	                "value": "",
	                "regex": false
	            }
	        }
	    ],
	    "order": [
	        {
	            "column": 1,
	            "dir": "desc"
	        }
	    ],
	    "start": 0,
	    "length": 10,
	    "search": {
	        "value": "",
	        "regex": false
	    },
	    "condition": {

	    }

	}
	
	
	
	 //获取收藏产品数据  
    $scope.getProdFavList = function(page,size,callback){ 
    	var url = "../user/getProdFavList";
    	reqData.draw+=1;
    	reqData.start=size*(page-1);//获取数据的起始位置=每页数据总数*(当前页码-1)
		jsonPost(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.count=result.recordsTotal; 
                $scope.pCurrent = page;  
                $scope.pAllPage =Math.ceil($scope.count/$scope.pPernum); 
				$scope.pages=calculateIndexes($scope.pCurrent,$scope.pAllPage,$scope.pPernum);  
	
				$scope.favList =result.data;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
    }  
	
	
	 //获取收藏商品数据  
    $scope.getWareFavList = function(page,size,callback){ 
    	var url = "../user/getWareFavList";
    	reqData.draw+=1;
    	reqData.start=size*(page-1);//获取数据的起始位置=每页数据总数*(当前页码-1)
		jsonPost(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.count=result.recordsTotal; 
                $scope.pCurrent = page;  
                $scope.pAllPage =Math.ceil($scope.count/$scope.pPernum); 
				$scope.pages=calculateIndexes($scope.pCurrent,$scope.pAllPage,$scope.pPernum);  
	
				$scope.favList =result.data;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
    }  
	
	
	
	
	 //获取收藏商店数据  
    $scope.getShopFavList = function(page,size,callback){ 
    	var url = "../user/getShopFavList";
    	reqData.draw+=1;
    	reqData.start=size*(page-1);//获取数据的起始位置=每页数据总数*(当前页码-1)
		jsonPost(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.count=result.recordsTotal; 
                $scope.pCurrent = page;  
                $scope.pAllPage =Math.ceil($scope.count/$scope.pPernum); 
				$scope.pages=calculateIndexes($scope.pCurrent,$scope.pAllPage,$scope.pPernum);  
	
				$scope.favList =result.data;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
    }  
    
    
  //取消收藏店铺
	$scope.removeShopFavCnt=function(shopId){
		var reqData= {
			"id":shopId
		}
		var url = "../shop/removeShopFavCnt";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$scope.getShopFavList(1,$scope.pPernum,function(){ });  //商店
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	//取消收藏商品
	$scope.removeWareFavCnt=function(wareId){
		var reqData= {
			"id":wareId
		}
		var url = "../ware/removeWareFavCnt";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$scope.getWareFavList(1,$scope.pPernum,function(){ });  //商品
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	//取消收藏产品
	$scope.removeProdFavCnt=function(prodId){
		var reqData= {
			"id":prodId
		}
		var url = "../prod/removeProdFavCnt";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$scope.getProdFavList(1,$scope.pPernum,function(){ });  //产品
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
}]);