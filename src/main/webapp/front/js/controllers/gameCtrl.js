// game ctrl
var hisPage = 1; 	// 用于返回到列表页时现场的保存： 选中页面
var hisProdCon;		// 用于返回到列表页时现场的保存： 选中条件
angular.module('frontApp').controller('gameListCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	$scope.$on('$viewContentLoaded', function() {  
		
		//配置  
	    $scope.count = 0;  //总页数
	    $scope.pPernum =4;  //每页数据条数
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
		        },
		        {
		            "data": "nameCn",
		            "name": "",
		            "searchable": true,
		            "orderable": true,
		            "search": {
		                "value": "",
		                "regex": false
		            }
		        },
		        {
		            "data": "isNew",
		            "name": "",
		            "searchable": false,
		            "orderable": false,
		            "search": {
		                "value": "",
		                "regex": false
		            }
		        },
		        {
		            "data": "prodTypePhy.nameCn",
		            "name": "",
		            "searchable": false,
		            "orderable": false,
		            "search": {
		                "value": "",
		                "regex": false
		            }
		        },
		        {
		            "data": "prodTypeAre.nameCn",
		            "name": "",
		            "searchable": false,
		            "orderable": false,
		            "search": {
		                "value": "",
		                "regex": false
		            }
		        },
		        {
		            "data": "prodTypePic.nameCn",
		            "name": "",
		            "searchable": false,
		            "orderable": false,
		            "search": {
		                "value": "",
		                "regex": false
		            }
		        },
		        {
		            "data": "prodTypeBiz.nameCn",
		            "name": "",
		            "searchable": false,
		            "orderable": false,
		            "search": {
		                "value": "",
		                "regex": false
		            }
		        },
		        {
		            "data": "prodTypeJar.nameCn",
		            "name": "",
		            "searchable": false,
		            "orderable": false,
		            "search": {
		                "value": "",
		                "regex": false
		            }
		        },
		        {
		            "data": "prodTypeNek.nameCn",
		            "name": "",
		            "searchable": false,
		            "orderable": false,
		            "search": {
		                "value": "",
		                "regex": false
		            }
		        },
		        {
		            "data": "prodTypeCon.nameCn",
		            "name": "",
		            "searchable": false,
		            "orderable": false,
		            "search": {
		                "value": "",
		                "regex": false
		            }
		        },
		        {
		            "data": "prodTypeHig.nameCn",
		            "name": "",
		            "searchable": false,
		            "orderable": false,
		            "search": {
		                "value": "",
		                "regex": false
		            }
		        },
		        {
		            "data": "prodTypeWid.nameCn",
		            "name": "",
		            "searchable": false,
		            "orderable": false,
		            "search": {
		                "value": "",
		                "regex": false
		            }
		        },
		        {
		            "data": "prodTypeSou.nameCn",
		            "name": "",
		            "searchable": false,
		            "orderable": false,
		            "search": {
		                "value": "",
		                "regex": false
		            }
		        },
		        {
		            "data": "prodCost.name",
		            "name": "",
		            "searchable": false,
		            "orderable": false,
		            "search": {
		                "value": "",
		                "regex": false
		            }
		        },
		        {
		            "data": "visitCnt",
		            "name": "",
		            "searchable": true,
		            "orderable": true,
		            "search": {
		                "value": "",
		                "regex": false
		            }
		        },
		        {
		            "data": "admin.adminName",
		            "name": "",
		            "searchable": false,
		            "orderable": false,
		            "search": {
		                "value": "",
		                "regex": false
		            }
		        },
		        {
		            "data": "user.userName",
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
		    "length": $scope.pPernum,
		    "search": {
		        "value": "",
		        "regex": false
		    },
		    "condition":{
		    	"isEnabled":1
		    }
		}
		
	    
	    //获取数据  
	    $scope.getData = function(page,size,callback){  
	    	var url = "../game/getGameList";
	    	reqData.draw+=1;
	    	reqData.start=size*(page-1);//获取数据的起始位置=每页数据总数*(当前页码-1)
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					$scope.count=result.recordsTotal; 
	                $scope.pCurrent = page;  
	                $scope.pAllPage =Math.ceil($scope.count/$scope.pPernum); 
					$scope.pages=calculateIndexes($scope.pCurrent,$scope.pAllPage,$scope.pPernum);  
					log($scope.pages)
					var gameList=result.data;
					for(var i in gameList){
						gameList[i].lastUpdateTimeLayout= dateTimeString(gameList[i].lastUpdateTime);
					}
					$scope.gameList =gameList;
					$scope.$apply();
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
	    }  
	    
	    
		//首页  
	    $scope.p_index = function(){  
	        $scope.getData(1,$scope.pPernum,function(){ });  
	    }  
	    //尾页  
	    $scope.p_last = function(){  
	        var page=$scope.pAllPage;
	        $scope.getData(page,$scope.pPernum,function(){ });  
	    }  
	    
	    //加载某一页  
	    $scope.loadPage = function(page){  
	    	hisPage = page;
	        $scope.getData(page,$scope.pPernum,function(){ });  
	    }; 
	    
	    // initial
		$scope.getData(hisPage,$scope.pPernum,function(){ }); 
		
		hisPage = 1;		//	执行查询完毕后还原为初始状态
	    	    
	});
	
	
	$scope.goDetail=function(id){
		window.location.href="#/game/gameDetail/"+id;
	}
	
}]);


angular.module('frontApp').controller('gameDetailCtrl', ['$rootScope', '$sce','$scope','$stateParams','$state', 'settings', function($rootScope,$sce, $scope,$stateParams,$state, $http) {
	$scope.$on('$viewContentLoaded', function() { 
		if($stateParams.id > 0){ // is update
			var reqData= $stateParams;
			var url = "../game/getGame";
			jsonGet(url, reqData, function(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					var game=result.data;
					game.lastUpdateTimeLayout= dateTimeString(game.lastUpdateTime);
					$scope.game = game;
					$scope.game.descriptionCn =  $sce.trustAsHtml(game.descriptionCn);
					$scope.game.descriptionEn =  $sce.trustAsHtml(game.descriptionEn);
					addGameVisitCnt();
					$scope.$apply();
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }, true, false);
			
		}else{
			
		}
	});
	
	
	function addGameVisitCnt(){
		var reqData= $stateParams;
		var url = "../game/addGameVisitCnt";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				//toastr.success(result.msg, COMMON_LABEL_SUCCESS);
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	$scope.goBack=function(){
		window.history.go(-1);
	}
		
}]);
	

