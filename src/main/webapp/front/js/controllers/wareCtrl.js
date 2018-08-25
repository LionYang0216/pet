// ware ctrl
var hisPage = 1; 	// 用于返回到列表页时现场的保存： 选中页面
var hisProdCon;		// 用于返回到列表页时现场的保存： 选中条件
var targetObjList=new Array(); // 用于返回到列表页时现场的保存： 选中的控件
angular.module('frontApp').controller('wareListCtrl', ['$rootScope', '$scope','$stateParams','$state', 'settings', function($rootScope, $scope,$stateParams,$state, $http) {
	

	
	
	$scope.$on('$viewContentLoaded', function() {  
		initMarqueeList();
		$scope.shopFav=0;	
		$scope.shopId=$stateParams.id;
	  	initWareTypeSteSelection($scope);
	    initWareTypeSrcSelection($scope);
	    initWareTypeMchSelection($scope);
	    initWareTypePrdSelection($scope);
	    $scope.getUserShopFav();
	    
	  	

		//配置  
	    $scope.count = 0;  //总页数
	    $scope.pPernum =10;  //每页数据条数
	    //变量   
	    $scope.pCurrent = 1;  
	    $scope.pAllPage =0;  
	    $scope.pages = [];
	    
		var prodCon={
			"isEnabled":1
		};
		
		if($scope.shopId>0){
			prodCon["shopId"] = $scope.shopId;
			getShop();
		}
	    
	    $scope.toWareDetail=function(id){
	    	window.location.href="#/ware/wareDetail/"+id;
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
		    "length":$scope.pPernum,
		    "search": {
		        "value": "",
		        "regex": false
		    },
		    "condition": prodCon
		};
		
		// set the condition properties value
		$scope.setWareCon = function(event,prop,value){
			prodCon[prop] = value; // let product condtion set this prop search value
			var targetObj = $(event.target); 
			//log(targetObj);
			if(targetObjList.length>0){//是否存在目标,确保每一个类型都只存在一个,并且在新增时把旧的去掉
				for(var i = targetObjList.length-1;i>=0;i--){		
					if(targetObjList[i].context.name==targetObj.context.name){
						targetObjList.splice(i, 1);
					}
			    }
			}
			
			//新增点击目标
			targetObjList.push(targetObj);
			
			
			targetObj.parent().parent().parent().find('a').removeClass("selectActive"); // let other btn as same parent reset default
			targetObj.addClass("selectActive"); // let this btn highlight
			//log(prodCon)
			hisProdCon = prodCon;
			reqData.condition=prodCon;			
			$scope.getWareList(1,$scope.pPernum,function(){ });  
		}
		
		
		
		
			
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
	    	hisPage = page;
	        $scope.getWareList(page,$scope.pPernum,function(){ });  
	    }; 
	    
	   
    
		  //获取数据  
	    $scope.getWareList = function(page,size,callback){  
	    	var url = "../ware/getWareList";
	    	reqData.draw+=1;
	    	reqData.start=size*(page-1);//获取数据的起始位置=每页数据总数*(当前页码-1)
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					$scope.count=result.recordsTotal; 
	                $scope.pCurrent = page;  
	                $scope.pAllPage =Math.ceil($scope.count/$scope.pPernum); 
					$scope.pages=calculateIndexes($scope.pCurrent,$scope.pAllPage,$scope.pPernum);  
					var wareList=result.data;
				
					$scope.wareList =wareList;
					$scope.$apply();
					
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
	    }  
	    
		 if(hisProdCon!=undefined){
	    	 // 从上一次访问中搜索是否有列表状态信息，比如查询提交
	    	//$("a").removeClass("selectActive");	// 去除默认选中状态
	    	reqData.condition = hisProdCon;
	    }
	    
		 // initial
	    $scope.getWareList(hisPage,$scope.pPernum,function(){ }); 
		
	    setTimeout(function(){
			$scope.$apply();
			 if(targetObjList.length>0){//// 从上一次访问中搜索是否有查询状态信息 
				for(var i =0;i< targetObjList.length;i++){
					$("a[name^='"+targetObjList[i].context.name+"']").removeClass("selectActive");// let other btn as same parent reset default
					var id =targetObjList[i].context.id;
					$("#"+id).addClass("selectActive"); // let this btn highlight
			    }
			}
		},1000);
		
		hisPage = 1;		//	执行查询完毕后还原为初始状态
		hisProdCon = null;	//	执行查询完毕后还原为初始状态
				
	});
	
  

	// initial the marquee list..
	function initMarqueeList(){
		var url = "../marquee/getMarqueeSelect?code=ware"; // 只取ware的滚动版
		jsonPost(url, null, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.marqueeList = result.data;
				$scope.$apply();
				RevosliderInit.initRevoSlider();
				
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	//根据shopID获取商店信息
	function getShop() {
		if(!$scope.shopId){
			return;
		}
		var reqData= {
			"id":$scope.shopId
		}
		
		var url = "../shop/getShop";
		jsonGet(url, reqData, function(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.shop = result.data;

				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }, true, false);
	}
	
	//收藏店铺
	$scope.addShopFavCnt=function(){
		var reqData= {
				"id":$scope.shopId
		}
		var url = "../shop/addShopFavCnt";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$scope.shopFav=1;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	
	

	//取消收藏店铺
	$scope.removeShopFavCnt=function(){
		var reqData= {
			"id":$scope.shopId
		}
		var url = "../shop/removeShopFavCnt";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$scope.shopFav=0;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	//获取店铺收藏信息
	$scope.getUserShopFav=function(){
		if(!$rootScope.curUser){
			return;
		}
		var reqData= {
				"userId":$rootScope.curUserId,
				"shopId":$scope.shopId
		}
		var url = "../shop/getUserShopFav";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				if(result.data){
					$scope.shopFav=1;
					$scope.$apply();
				}else{
					$scope.shopFav=0;
					$scope.$apply();
				}

			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
}]);



//wareDetail ctrl
angular.module('frontApp').controller('wareDetailCtrl', ['$rootScope', '$scope','$stateParams','$state', 'settings', function($rootScope, $scope,$stateParams,$state, $http) {
	
	var wareId=$stateParams.id; 	
	$scope.$on('$viewContentLoaded', function() { 
		
		$scope.wareFav=0;
		$scope.shopFav=0;		
		
		if(wareId){
			getWare(wareId);			
		}
		getShop();
		addwareVisitCnt();
		$scope.getUserWareFav();
		$scope.getUserShopFav();
	});
	
	function getWare(id){
		var reqData= $stateParams;
		var url = "../ware/getWare";
		jsonGet(url, reqData, function(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				var ware=result.data;
				
				$scope.ware = ware;
				addwareVisitCnt();
				$scope.shopId=ware.shopId;
				//$scope.$apply();
				//RevosliderInit.initRevoSlider();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }, false, false);
	}
	
	
	//根据shopID获取商店信息
	function getShop() {
		if(!$scope.shopId){
			return;
		}
		var reqData= {
			"id":$scope.shopId
		}
		
		var url = "../shop/getShop";
		jsonGet(url, reqData, function(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.shop = result.data;
				var tdCon="";
				var shopLevelObj=$("#shopLevel");
				if($scope.shop.level>0){
					for(var i=0 ;i<$scope.shop.level;i++){
						shopLevelObj.append('<span class="glyphicon glyphicon-star" style="font-size:14px;color:orange;">')
					}
				}
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }, true, false);
	}
	
	//添加访问量
	function addwareVisitCnt(){
		var reqData= $stateParams;
		var url = "../ware/addWareVisitCnt";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				//toastr.success(result.msg, COMMON_LABEL_SUCCESS);
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	//收藏商品
	$scope.addWareFavCnt=function(){

		var reqData= {
				"id":wareId
		}
		var url = "../ware/addWareFavCnt";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$scope.wareFav=1;
				$scope.ware.favCnt=$scope.ware.favCnt+1;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	//取消收藏商品
	$scope.removeWareFavCnt=function(){
		var reqData= {
				"id":$stateParams.id 
		}
		var url = "../ware/removeWareFavCnt";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$scope.wareFav=0;
				$scope.ware.favCnt=$scope.ware.favCnt-1;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	//点赞
	$scope.addWareUpCnt=function(){
		var reqData= {
			"id":$stateParams.id 
		}
		var url = "../ware/addWareUpCnt";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$scope.wareUp=1;
				$scope.ware.upCnt=$scope.ware.upCnt+1;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	
	//获取商品收藏信息
	$scope.getUserWareFav=function(){
		if(!$rootScope.curUser){
			return;
		}
		var reqData= {
				"userId":$rootScope.curUserId,
				"wareId":$stateParams.id 
		}
		var url = "../ware/getUserWareFav";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				if(result.data){
					$scope.wareFav=1;
					$scope.$apply();
				}else{
					$scope.wareFav=0;
					$scope.$apply();
				}

			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	//收藏店铺
	$scope.addShopFavCnt=function(){
		var reqData= {
				"id":$scope.shopId
		}
		var url = "../shop/addShopFavCnt";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$scope.shopFav=1;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	
	

	//取消收藏店铺
	$scope.removeShopFavCnt=function(){
		var reqData= {
			"id":$scope.shopId
		}
		var url = "../shop/removeShopFavCnt";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$scope.shopFav=0;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	//获取店铺收藏信息
	$scope.getUserShopFav=function(){
		if(!$rootScope.curUser){
			return;
		}
		var reqData= {
				"userId":$rootScope.curUserId,
				"shopId":$scope.shopId
		}
		var url = "../shop/getUserShopFav";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				if(result.data){
					$scope.shopFav=1;
					$scope.$apply();
				}else{
					$scope.shopFav=0;
					$scope.$apply();
				}

			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	$scope.goBack=function(){
		window.history.back();
	}
	
}]);

//处理滚动屏：因为每个滚动屏都不同，我们不要做成公共函数
var RevosliderInit = function () {

    return {
        initRevoSlider: function () {
        		//log(jQuery('.fullwidthbanner'))
                  jQuery('.fullwidthbanner').show().revolution({ 
                      delay:2000,
                      startheight:450,
                      startwidth:1150,

                      hideThumbs:10,

                      thumbWidth:100,                         // Thumb With and Height and Amount (only if navigation Tyope set to thumb !)
                      thumbHeight:50,
                      thumbAmount:5,

                      navigationType:"bullet",                // bullet, thumb, none
                      navigationArrows:"solo",                // nexttobullets, solo (old name verticalcentered), none

                      navigationStyle:"round",                // round,square,navbar,round-old,square-old,navbar-old, or any from the list in the docu (choose between 50+ different item), custom

                      navigationHAlign:"center",              // Vertical Align top,center,bottom
                      navigationVAlign:"bottom",              // Horizontal Align left,center,right
                      navigationHOffset:0,
                      navigationVOffset:20,

                      soloArrowLeftHalign:"left",
                      soloArrowLeftValign:"center",
                      soloArrowLeftHOffset:20,
                      soloArrowLeftVOffset:0,

                      soloArrowRightHalign:"right",
                      soloArrowRightValign:"center",
                      soloArrowRightHOffset:20,
                      soloArrowRightVOffset:0,

                      touchenabled:"on",                      // Enable Swipe Function : on/off
                      onHoverStop:"on",                       // Stop Banner Timet at Hover on Slide on/off

                      stopAtSlide:-1,
                      stopAfterLoops:-1,

                      hideCaptionAtLimit:0,         // It Defines if a caption should be shown under a Screen Resolution ( Basod on The Width of Browser)
                      hideAllCaptionAtLilmit:0,       // Hide all The Captions if Width of Browser is less then this value
                      hideSliderAtLimit:0,          // Hide the whole slider, and stop also functions if Width of Browser is less than this value

                      shadow:1,                               //0 = no Shadow, 1,2,3 = 3 Different Art of Shadows  (No Shadow in Fullwidth Version !)
                      fullWidth:"on"                          // Turns On or Off the Fullwidth Image Centering in FullWidth Modus
                  });
        }
    };

}();