angular.module('backApp').controller('dashboardCtrl', function($rootScope, $scope, $http, $timeout) {
	
    $scope.$on('$viewContentLoaded', function() {   
    	$scope.initDashboardCnt();
    	$scope.getShopApplyList();
    	$scope.getUserDrawApplyList();
    	$scope.getProdConList();
    	$scope.getTopVisitProdList();
    	$scope.getTopVisitWareList();
        // initialize core components
        App.initAjax();
        
        var echartsPie1;  
    	var echartsPie2; 
    	
    	echartsPie1 = echarts.init(document.getElementById('echartsPie1'));
    	echartsPie2 = echarts.init(document.getElementById('echartsPie2'));
    	var prodList = $scope.prodList;
    	var wareList= $scope.wareList;
    	var prodListOption = {  
	        title : {  
	            text: '热门瓶库TOP10',  
	            subtext: '',  
	            x:'center'  
	        },  
	        tooltip : {  
	            trigger: 'item',  
	            formatter: "{a} <br/>{b} : {c} "  
	        },  
	        legend: {  
	            orient : 'vertical',  
	            x : 'left',  
	            data:$scope.prodNameList  
	        },  
	       
	        calculable : false,  
	        series : [  
	            {  
	                name:'瓶库',  
	                type:'pie',  
	                radius : '55%',//饼图的半径大小  
	                center: ['60%', '60%'],//饼图的位置  [(左右),(上下)]
	                data:prodList  
	            }  
	        ]  
	    };   
    	
    	var wareListOption = {  
	        title : {  
	            text: '热门商品TOP10',  
	            subtext: '',  
	            x:'center'  
	        },  
	        tooltip : {  
	            trigger: 'item',  
	            formatter: "{a} <br/>{b} : {c} "  
	        },  
	        legend: {  
	            orient : 'vertical',  
	            x : 'left',  
	            data:$scope.wareNameList  
	        },  
	       
	        calculable : false,  
	        series : [  
	            {  
	                name:'商品',  
	                type:'pie',  
	                radius : '55%',//饼图的半径大小  
	                center: ['60%', '60%'],//饼图的位置  [(左右),(上下)]
	                data:wareList  
	            }  
	        ]  
	    };   
            
        
      
  	  	echartsPie1.setOption(prodListOption); 
  	 	echartsPie2.setOption(wareListOption); 
    });

    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageContentWhite = true;
    $rootScope.settings.layout.pageBodySolid = false;
    $rootScope.settings.layout.pageSidebarClosed = false;
    
    $scope.initDashboardCnt = function(){
       var url = "../dashboard/getDashBoardCnt";
  	   jsonGet(url, '', callback, false, false);
  		function callback(result) {
  			if (result.flag == RESULT_FLAG_SUCCESS) {
  				$scope.dashboard=result.data;
  			}
  	    }
		
	};
	
	
	//访问量前10的产品
	 $scope.getTopVisitProdList  = function(){
	   var url = "../prod/getTopVisitProdList";
	   jsonGet(url, '', callback, false, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.prodList=result.data;
				var prodNameList=new Array()
				for(var i in $scope.prodList){
					prodNameList.push($scope.prodList[i].name);
				}
				$scope.prodNameList=prodNameList;
			}
	    }
		
	};
		
		
	//访问量前10的商品
	 $scope.getTopVisitWareList  = function(){
       var url = "../ware/getTopVisitWareList ";
  	   jsonGet(url, '', callback, false, false);
  		function callback(result) {
  			if (result.flag == RESULT_FLAG_SUCCESS) {
  				$scope.wareList=result.data;
  				var wareNameList=new Array()
				for(var i in $scope.wareList){
					wareNameList.push($scope.wareList[i].name);
				}
				$scope.wareNameList=wareNameList;
  			}
  	    }
		
	};
	
	 $scope.getShopApplyList = function(){
	       var url = "../dashboard/getShopApplyList";
	       var reqData={length:8};
	  	   jsonGet(url, reqData, callback, false, false);
	  		function callback(result) {
	  			if (result.flag == RESULT_FLAG_SUCCESS) {
	  				 var shopList=result.data;
	  				for(var i=0;i<shopList.length;i++){
	  					if(shopList[i].regTime!=null){
	  						shopList[i].regTime=getDateDiff(dateTimeString(shopList[i].regTime));
	  					}
	  				}
	  				$scope.shopList=shopList;
	  				log($scope.shopList);
	  			}
	  	    }
			
		};
		
		 $scope.getUserDrawApplyList = function(){
	       var url = "../dashboard/getUserDrawApplyList";
	       var reqData={length:8};
	  	   jsonGet(url, reqData, callback, false, false);
	  		function callback(result) {
	  			if (result.flag == RESULT_FLAG_SUCCESS) {
	  				 var userDrawList=result.data;
	  				for(var i=0;i<userDrawList.length;i++){
	  					if(userDrawList[i].userRequestTime!=null){
	  						userDrawList[i].userRequestTime=getDateDiff(dateTimeString(userDrawList[i].userRequestTime));
	  					}
	  				}
	  				$scope.userDrawList=userDrawList;
	  			}
	  	    }
			
		};
				//获取待确认的设计师作品
		 $scope.getProdConList = function(){
		       var url = "../dashboard/getProdConList";
		       var reqData={length:8};
		  	   jsonGet(url, reqData, callback, false, false);
		  		function callback(result) {
		  			if (result.flag == RESULT_FLAG_SUCCESS) {
		  				 var prodConList=result.data;
		  				for(var i=0;i<prodConList.length;i++){
		  					if(prodConList[i].lastUpdateTime!=null){
		  						prodConList[i].lastUpdateTime=getDateDiff(dateTimeString(prodConList[i].lastUpdateTime));
		  					}
		  				}
		  				$scope.prodConList=prodConList;
		  			}
		  	    }
				
			};
});