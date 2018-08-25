angular.module('frontApp').controller('myWorkListCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	
	var userId=$rootScope.curUserId;
	
	var prodCon={
		  "userId":userId
	};
	
	$scope.$on('$includeContentLoaded', function() { 
		
		initProdTypeSouSelection($scope);
		
		//配置  
	    $scope.count = 0;  //总页数
	    $scope.pPernum =10;  //每页数据条数
	    //变量   
	    $scope.pCurrent = 1;  
	    $scope.pAllPage =0;  
	    $scope.pages = [];  
	    
	    
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
    	        }, {
    	            "data": "id",
    	            "name": "",
    	            "searchable": false,
    	            "orderable": false,
    	            "search": {
    	                "value": "",
    	                "regex": false
    	            }
    	        }, {
    	            "data": "lastUpdateTime",
    	            "name": "",
    	            "searchable": false,
    	            "orderable": false,
    	            "search": {
    	                "value": "",
    	                "regex": false
    	            }
    	        }, {
    	            "data": "visitCnt",
    	            "name": "",
    	            "searchable": false,
    	            "orderable": false,
    	            "search": {
    	                "value": "",
    	                "regex": false
    	            }
    	        }, {
    	            "data": "favCnt",
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
    	            "column":1,
    	            "dir": "desc"
    	        }
    	    ],
    	    "start": 0,
    	    "length": 10,
    	    "search": {
    	        "value": "",
    	        "regex": false
    	    },
		    "condition":prodCon
    	};
	    
	    
	    $rootScope.reLoadProdList=function(){
	    	$scope.getProdList(1,$scope.pPernum,function(){ }); 
	    }
	    
	    
	  //获取数据  
	    $scope.getProdList= function(page,size,callback){  
	    	var url = "../prod/getProdList";
	    	reqData.draw+=1;
	    	reqData.start=size*(page-1);//获取数据的起始位置=每页数据总数*(当前页码-1)
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					$scope.count=result.recordsTotal; 
	                $scope.pCurrent = page;  
	                $scope.pAllPage =Math.ceil($scope.count/$scope.pPernum); 
					$scope.pages=calculateIndexes($scope.pCurrent,$scope.pAllPage,$scope.pPernum);  
					var prodList=result.data;
					for(var i in prodList){
						prodList[i].lastUpdateTimeLayout= dateTimeFormatYMD(prodList[i].lastUpdateTime);
					}
					$scope.prodList =prodList;
					$scope.$apply();
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
	    }  
	    
	    

		// initial
		$scope.getProdList(1,$scope.pPernum,function(){ }); 

		//首页  
	    $scope.p_index = function(){  
	        $scope.getProdList(1,$scope.pPernum,function(){ });  
	    }  
	    //尾页  
	    $scope.p_last = function(){  
	        var page=$scope.pAllPage;
	        $scope.getProdList(page,$scope.pPernum,function(){ });  
	    }  
	    
	    //加载某一页  
	    $scope.loadPage = function(page){  
	        $scope.getProdList(page,$scope.pPernum,function(){ });  
	    }; 
	    
	    
	    $scope.selectUI = function(event,prop,value){
			if(prop=="order"){
				reqData.order[0].column=value;
			}else{
				prodCon[prop] = value;
			}
		
			var targetObj = $(event.target); 
			//log(targetObj);
			targetObj.parent().parent().parent().find('a').removeClass("myWork-select-active"); // let other btn as same parent reset default
			targetObj.addClass("myWork-select-active"); // let this btn highlight
			
			$scope.getProdList(1,$scope.pPernum,function(){ });  

		}
	    

	});
	
	
	$scope.showProdDetail=function(id){
		window.location.href="#/prod/prodDetail/"+id;
	}
	
	
	// delete an admin
	$scope.toDeleteProd = function(id){
		if(id){
			bootbox.confirm("是否删除?", function (result) {
				if(result){
					var reqData= {
							"id":id
					};
					var url = "../prod/deleteProd";
					jsonGet(url, reqData, callback, true, false);
					function callback(result) {
						if (result.flag == RESULT_FLAG_SUCCESS) {
							toastr.success(result.msg, COMMON_LABEL_SUCCESS);
							$scope.getProdList(1,$scope.pPernum,function(){ });  
						} else {
							toastr.error(result.msg, COMMON_LABEL_ERROR);
						}
				    }
				}
			})
		}
	};
	
    
    $scope.addWork=function (){
		$scope.switchTab('myWorkForm');
		$rootScope.reloadWork(0);
	}
	
	$scope.updateWork=function (id){
		$scope.switchTab('myWorkForm');
		$rootScope.reloadWork(id);
	}
	
}]);