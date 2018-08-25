// news ctrl
var hisPage = 1; 	// 用于返回到列表页时现场的保存： 选中页面
var hisProdCon;		// 用于返回到列表页时现场的保存： 选中条件
var targetObjList=new Array(); // 用于返回到列表页时现场的保存： 选中的控件
angular.module('frontApp').controller('newsListCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	$scope.$on('$viewContentLoaded', function() {  
		
		//配置  
	    $scope.count = 0;  //总页数
	    $scope.pPernum =10;  //每页数据条数
	    //变量   
	    $scope.pCurrent = 1;  
	    $scope.pAllPage =0;  
	    $scope.pages = [];  
	    
	    
		var newsCon={
			   "isEnabled":1
		};
		
		// set the condition properties value
		$scope.setNewsCon = function(event,prop,value){
			newsCon[prop] = value; // let product condtion set this prop search value
			var targetObj = $(event.target); 
			
			if(targetObjList.length>0){//是否存在目标,确保每一个类型都只存在一个,并且在新增时把旧的去掉
				for(var i = targetObjList.length-1;i>=0;i--){		
					if(targetObjList[i].context.name==targetObj.context.name){
						targetObjList.splice(i, 1);
					}
			    }
			}
			
			//新增点击目标
			targetObjList.push(targetObj);
			//log(targetObj);
			targetObj.parent().find('a').removeClass("selectActive"); // let other btn as same parent reset default
			targetObj.addClass("selectActive"); // let this btn highlight
			//log(newsCon)
			
			hisProdCon = newsCon;
			reqData.condition=newsCon;			
			$scope.getNewsList(1,$scope.pPernum,function(){ });  
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
		            "data": "lastUpdateTime",
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
		    "length": $scope.pPernum,
		    "search": {
		        "value": "",
		        "regex": false
		    },
		    "condition": newsCon
				
		};
		
		
		  //获取行业动态数据  
	    $scope.getNewsList = function(page,size,callback){  
	    	var url = "../news/getNewsList";
	    	reqData.draw+=1;
	    	reqData.start=size*(page-1);//获取数据的起始位置=每页数据总数*(当前页码-1)
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					$scope.count=result.recordsTotal; 
	                $scope.pCurrent = page;  
	                $scope.pAllPage =Math.ceil($scope.count/$scope.pPernum); 
					$scope.pages=calculateIndexes($scope.pCurrent,$scope.pAllPage,$scope.pPernum);  
					var newsList=result.data;
					for(var i in newsList){
						newsList[i].lastUpdateTimeLayout= dateTimeString(newsList[i].lastUpdateTime);
						newsList[i].lastUpdateTimeLayout= getDateDiff(newsList[i].lastUpdateTimeLayout);
					}
					$scope.newsList =newsList;
					$scope.$apply();
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
	    }  
	    
	    
	  //首页  
	    $scope.p_index = function(){  
	        $scope.getNewsList(1,$scope.pPernum,function(){ });  
	    }  
	    //尾页  
	    $scope.p_last = function(){  
	        var page=$scope.pAllPage;
	        $scope.getNewsList(page,$scope.pPernum,function(){ });  
	    }  
	    
	    //加载某一页  
	    $scope.loadPage = function(page){  
	    	hisPage = page;
	        $scope.getNewsList(page,$scope.pPernum,function(){ });  
	    }; 
	    
	    
	    if(hisProdCon!=undefined){
	    	 // 从上一次访问中搜索是否有列表状态信息，比如查询提交
	    	$("a").removeClass("selectActive");	// 去除默认选中状态
	    	reqData.condition = hisProdCon;
	    }
	    
	    
	   
	    // initial
		$scope.getNewsList(hisPage,$scope.pPernum,function(){ }); 
		$scope.getNewsTypeList();
		
		 setTimeout(function(){
			$scope.$apply();
			 if(targetObjList.length>0){//// 从上一次访问中搜索是否有查询状态信息 
				for(var i =0;i< targetObjList.length;i++){
					$("a[name^='"+targetObjList[i].context.name+"']").removeClass("selectActive");// let other btn as same parent reset default
					var id =targetObjList[i].context.id;
					$("#"+id).addClass("selectActive"); // let this btn highlight
			    }
			}
		},500);
	
		hisPage = 1;		//	执行查询完毕后还原为初始状态
		hisProdCon = null;	//	执行查询完毕后还原为初始状态
	    	    
		$scope.goNewsDetail=function(id){
			window.location.href="#/news/newsDetail/"+id;
		}
			
	});
	
	
	var req={
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
	            "searchable": true,
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
	    "condition":{
	    	"isEnabled":1
	    }
	};
	
	//获取行业动态类型
	$scope.getNewsTypeList=function(){
		
		var url = "../newsType/getNewsTypeList";
		jsonPost(url, req, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				var newsTypeList=result.data;
				$scope.newsTypeList =newsTypeList;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	
	
	
}]);





angular.module('frontApp').controller('newsDetailCtrl', ['$rootScope','$sce','$scope','$stateParams','$state', 'settings', function($rootScope, $sce, $scope,$stateParams,$state, $http) {
	
	var newsId= $stateParams.id;
	
	$scope.$on('$viewContentLoaded', function() { 
		if(newsId> 0){ 
			var reqData= {
				"id":newsId
			}
			var url = "../news/getNews";
			jsonGet(url, reqData, function(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					var news=result.data;
					news.lastUpdateTimeLayout= dateTimeString(news.lastUpdateTime);
					$scope.news = news;
					$scope.news.descriptionCn =  $sce.trustAsHtml(news.descriptionCn);
					$scope.news.descriptionEn =  $sce.trustAsHtml(news.descriptionEn);
					addNewsVisitCnt();
					$scope.$apply();
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }, true, false);
			
		}else{
			
		}

	});
	
	//新增访问数量
	function addNewsVisitCnt(){
		var reqData= $stateParams;
		var url = "../news/addNewsVisitCnt";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				//toastr.success(result.msg, COMMON_LABEL_SUCCESS);
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	//新闻点赞
	$scope.addNewsUpCnt=function(){
		var reqData= {
				"id":$stateParams.id 
			}
			var url = "../news/addNewsUpCnt";
			jsonGet(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$scope.$apply();
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
	}
	
	$scope.back=function(){
		window.history.go(-1);
	}
	
	

}]);