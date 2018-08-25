angular.module('frontApp').controller('myWareListCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	
	//配置  
    $scope.count = 0;  //总页数
    $scope.pPernum =10;  //每页数据条数
    //变量   
    $scope.pCurrent = 1;  
    $scope.pAllPage =0;  
    $scope.pages = []; 
    $scope.shopId=0;
    
    $scope.$on('$includeContentLoaded', function() { 
		
    	var userId = $rootScope.curUserId;

    	$scope.shop = $rootScope.curShop;
    	
    	getWareVisitTotal();
    	getWareFavTotal();
    	
    	
		$scope.getWareList(1,$scope.pPernum,function(){ }); 

		//首页  
	    $scope.p_index = function(){  
	        $scope.getWareList(1,$scope.pPernum,function(){ });  
	    }  
	    //尾页  
	    $scope.p_last = function(){  
	        var page=$scope.pAllPage;
	        $scope.getWareList(page,$scope.pPernum,function(){ });  
	    }  
	    //加载某一页  
	    $scope.loadPage = function(page){  
	        $scope.getWareList(page,$scope.pPernum,function(){ });  
	    }; 
	});
    
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
	            "data": "lastUpdateTime",
	            "name": "",
	            "searchable": false,
	            "orderable": true,
	            "search": {
	                "value": "",
	                "regex": false
	            }
	        },
	        {
	            "data": "isEnable",
	            "name": "",
	            "searchable": false,
	            "orderable": true,
	            "search": {
	                "value": "",
	                "regex": false
	            }
	        },
	        {
	            "data": "id",
	            "name": "",
	            "searchable": false,
	            "orderable": false,
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
	    	"shopId":"",
	        "isEnable": "1"
	    }
	}
	
	
	// set the product condition properties value
	$scope.setProdCon = function(event,prop,value){
		reqData.condition.isEnable= value; // let  condtion set this prop search value
		var targetObj = $(event.target); 
		//log(targetObj);
		targetObj.parent().parent().parent().find('a').removeClass("selectActive"); // let other btn as same parent reset default
		targetObj.addClass("selectActive"); // let this btn highlight
		
		$scope.getWareList(1,$scope.pPernum,function(){ });  
	}
	
	
	$rootScope.reloadWareList=function(){
		$scope.getWareList(1,$scope.pPernum,function(){ }); 
	}
	
	 $scope.goShop= function(){
		 window.location.href="#/ware/wareList/"+$rootScope.curShopId;
	 }
	
	 //获取数据  
    $scope.getWareList = function(page,size,callback){ 
    	var url = "../ware/getWareList";
    	reqData.draw+=1;
    	reqData.condition.shopId=$rootScope.curShopId;
    	reqData.start=size*(page-1);//获取数据的起始位置=每页数据总数*(当前页码-1)
		jsonPost(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.count=result.recordsTotal; 
                $scope.pCurrent = page;  
                $scope.pAllPage =Math.ceil($scope.count/$scope.pPernum); 
				$scope.pages=calculateIndexes($scope.pCurrent,$scope.pAllPage,$scope.pPernum);  
	
				$scope.wareList =result.data;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
    }  
	

	$scope.addWare=function (){
		$scope.switchTab('myWareForm');
		$rootScope.reloadWare(0);	// div 跳转法
	}
	
	$scope.updateWare=function (wareId){
		$scope.switchTab('myWareForm');
		$rootScope.reloadWare(wareId);	// div 跳转法
	}
	
	
	$scope.updateShop=function(){
		$scope.switchTab('myShopForm');
	}
	
	$scope.updateWareDisEnable=function(ware){
		updateWareById(ware,0);
	}
	
	
	$scope.updateWareEnable=function(ware){
		updateWareById(ware,1);
	}
	
	//商品上 /下架 
	function updateWareById(ware,isEnable){
		var req=ware;
		req.isEnable=isEnable;
		delete req.$$hashKey;  //删除一个不必要的参数
		var url = "../ware/updateWare";
		jsonPost(url, req, callback, false, false);
		function callback(result){
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$scope.getWareList(1,$scope.pPernum,function(){ }); 
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    };
		
	}
	
	//删除商品
	$scope.toDeleteWare = function(wareId){
		if(wareId){
			bootbox.confirm("是否删除?", function (result) {
				if(result){
					var reqData= {
							"id":wareId
					};
					var url = "../ware/deleteWare";
					jsonGet(url, reqData, callback, false, false);
					function callback(result) {
						if (result.flag == RESULT_FLAG_SUCCESS) {
							toastr.success(result.msg, COMMON_LABEL_SUCCESS);
							$scope.getWareList(1,$scope.pPernum,function(){ });
						} else {
							toastr.error(result.msg, COMMON_LABEL_ERROR);
						}
				    }
				}
			})
		}
	};
	
	
	
	
	//根据userid获取商店信息
	function getShopByUser(){
		var req={'userId':$rootScope.curUserId};
		var url = "../shop/getShopByUser";
		jsonGet(url, req, callback,false, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.shop= result.data;
			} else {
				//toastr.error("", COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	
	//根据shopid获取商品访问总量
	function getWareVisitTotal(){
		if(!$rootScope.curShopId){
			return;
		}
		var req={'shopId':$rootScope.curShopId};
		var url = "../ware/getWareVisitTotal";
		jsonGet(url, req, callback,true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.wareVisitTotal= result.data;
			} else {
				//toastr.error("", COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	//根据shopid获取商品收藏量
	function getWareFavTotal(){
		if(!$rootScope.curShopId){
			return;
		}
		var req={'shopId':$rootScope.curShopId};
		var url = "../ware/getWareFavTotal";
		jsonGet(url, req, callback,true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.wareFavTotal= result.data;
			} else {
				//toastr.error("", COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
}]);