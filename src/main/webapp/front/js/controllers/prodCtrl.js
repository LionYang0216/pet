var hisPage = 1; 	// 用于返回到列表页时现场的保存： 选中页面
var hisProdCon;		// 用于返回到列表页时现场的保存： 选中条件
var targetObjList=new Array(); // 用于返回到列表页时现场的保存： 选中的控件
// prod ctrl
angular.module('frontApp').controller('prodListCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	$scope.$on('$viewContentLoaded', function() {  	
		
		initProdTypePhySelection($scope);
		initProdTypeAreSelection($scope);
		initProdTypeBizSelection($scope);
		initProdTypeConSelection($scope);
		initProdTypeHigSelection($scope);
		initProdTypeJarSelection($scope);
		initProdTypeNekSelection($scope);
		initProdTypePicSelection($scope);
		initProdTypeSouSelection($scope);
		initProdTypeWidSelection($scope);
		initProdCostSelection($scope);
		
		var prodCon={
			   "isEnable":1
		};

				
		// set the product condition properties value
		$scope.setProdCon = function(event,prop,value){
			
			prodCon[prop] = value; // let product condtion set this prop search value
			var targetObj = $(event.target); 
			log(targetObj);
			if(targetObjList.length>0){//是否存在目标,确保每一个类型都只存在一个,并且在新增时把旧的去掉
				for(var i = targetObjList.length-1;i>=0;i--){		
					if(targetObjList[i].context.name==targetObj.context.name){
						targetObjList.splice(i, 1);
					}
			    }
			}
			
			//新增点击目标
			targetObjList.push(targetObj);
			log(targetObjList);
			//log(targetObj);
			targetObj.parent().parent().parent().find('a').removeClass("selectActive"); // let other btn as same parent reset default
			targetObj.addClass("selectActive"); // let this btn highlight
			//log(prodCon)
			if(prodCon["prodTypePhyId"]==2){
				$("#picLi_3").css("display","none");
			}else{
				$("#picLi_3").css("display","");
			}
			
			hisProdCon = prodCon;
			reqData.condition = prodCon;			
			$scope.getData(1,$scope.pPernum,function(){ });  
		}
		
		
		//配置  
	    $scope.count = 0;  //总页数
	    $scope.pPernum =12;  //每页数据条数
	    //变量   
	    $scope.pCurrent = 1;  
	    $scope.pAllPage =0;  
	    $scope.pages = [];;
	    
	    $scope.toProdDetail=function(id){
	    	window.location.href="#/prod/prodDetail/"+id;
	    }
	    
		var reqData={
		    "draw": 1,
		    "columns": [
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
		    "condition": prodCon
		};
		
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
	    
	    //获取数据  
	    $scope.getData = function(page,size,callback){  
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
				
					$scope.prodList =prodList;
					$scope.$apply();
					
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
	    }  
	    
	    // initial
	   
	    if(hisProdCon!=undefined){
	    	 // 从上一次访问中搜索是否有列表状态信息，比如查询提交
	    	//$("a").removeClass("selectActive");	// 去除默认选中状态
	    	reqData.condition = hisProdCon;
	    }
	    
		$scope.getData(hisPage,$scope.pPernum,function(){ }); 

		setTimeout(function(){
			$scope.$apply();
			 if(targetObjList.length>0){//// 从上一次访问中搜索是否有查询状态信息 
				for(var i =0;i< targetObjList.length;i++){
					$("a[name^='"+targetObjList[i].context.name+"']").removeClass("selectActive");// let other btn as same parent reset default
					var id =targetObjList[i].context.id;
					$("#"+id).addClass("selectActive"); // let this btn highlight
			    }
			}
		},600);
	  
		
		hisPage = 1;		//	执行查询完毕后还原为初始状态
		hisProdCon = null;	//	执行查询完毕后还原为初始状态
		//targetObjList=new Array();
	});
}]);


angular.module('frontApp').controller('prodDetailCtrl', ['$rootScope', '$sce','$scope','$stateParams','$state', 'settings', function($rootScope,$sce, $scope,$stateParams,$state, $http) {
	$scope.$on('$viewContentLoaded', function() { 
		divHide();
		$scope.fav=0;
		
		if($stateParams.id > 0){ // get prod detail data
			var reqData= $stateParams;
			var url = "../prod/getProd";
			jsonGet(url, reqData, function(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					var prod=result.data;
					$scope.prod = prod;
					$scope.prod.descriptionCn =  $sce.trustAsHtml(prod.descriptionCn);
					$scope.prod.descriptionEn =  $sce.trustAsHtml(prod.descriptionEn);
					if(!$scope.prod.prodCost.cost){//免费
						$("#prodDetailDiv").show();
					}else{//收费
						$scope.initUser();
						$("#prodCostDiv").show();
						if(!$rootScope.curUser){//会员未登录
							$("#needLoginDiv").show();
						}else{//已登录
							getUserBalanceLog();
						}
					}
					addProdVisitCnt();
					$scope.getRefProdList($scope.prod.refCode);
					$scope.$apply();
					setTimeout(function(){
						RevosliderInit.initRevoSlider();
					},800);
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }, true, false);
			
		}else{
			
		}
		
		$scope.getUserProdFav();
		
		//$(".tp-bgimg .defaultimg").css("backgroundSize","inherit");
		
	});
	
	$scope.login=function(){
		$('#userLoginModal').modal('show')  
	}
	
	
	function divHide(){
		$("#prodCostDiv").hide();
		$("#prodDetailDiv").hide();
		
		$("#needLoginDiv").hide();
		$("#needPayDiv").hide();
	}
	
	$scope.goBack=function(){
		window.history.back();
	}
	
	//查询会员与瓶库付费记录
	function getUserBalanceLog(){
		var reqData= {
				"prodId":$stateParams.id
		}
		var url = "../userBalanceLog/getLog";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$("#prodDetailDiv").show();
			} else {
				$("#needPayDiv").show();
			}
	    }
	}
	
	//支付查看费用
	$scope.chargeViewProd=function(){
		var reqData= {
				"prodId":$stateParams.id
		}
		var url = "../prod/chargeViewProd";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				divHide();
				$("#prodDetailDiv").show();
				$rootScope.curUser=result.data;
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	//添加访问量
	function addProdVisitCnt(){
		var reqData= $stateParams;
		var url = "../prod/addProdVisitCnt";
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
	$scope.addProdFavCnt=function(){
		var reqData= {
				"id":$stateParams.id 
		}
		var url = "../prod/addProdFavCnt";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$scope.fav=1;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	//取消收藏商品
	$scope.removeProdFavCnt=function(){
		var reqData= {
				"id":$stateParams.id 
		}
		var url = "../prod/removeProdFavCnt";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				$scope.fav=0;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	//获取收藏信息
	$scope.getUserProdFav=function(){
		if(!$rootScope.curUser){
			return;
		}
		var reqData= {
				"userId":$rootScope.curUserId,
				"prodId":$stateParams.id 
		}
		var url = "../prod/getUserProdFav";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				if(result.data){
					$scope.fav=1;
					$scope.$apply();
				}else{
					$scope.fav=0;
					$scope.$apply();
				}

			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	//点赞
	$scope.addProdUpCnt=function(){
		var reqData= {
				"id":$stateParams.id 
		}
		var url = "../prod/addProdUpCnt";
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
	
	//产品关联功能
	$scope.getRefProdList=function(refCode){
		var reqData= {
			"refCode":refCode
		}
		var url = "../prod/getRefProdList";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.refProdList=result.data;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	
	// 查找USER
	$scope.initUser = function(){
		if(!$rootScope.curUserId){
			return;
		}
		var url = "../user/getUser";
		var req={
			"id":$rootScope.curUserId	
		}
		jsonGet(url, req, callback, true, false);
		function callback(result) {
			$scope.getUser = result.data; 
			$scope.$apply();
	    }

	}
	
	//返回
	$scope.goBack=function(){
		window.history.go(-1);
	}
	
	
	//跳转关联产品
	$scope.goRefProd=function(id){
		window.location.href="#/prod/prodDetail/"+id;
	}
	
	$scope.toRecharge=function(){
		window.location.href="#/my/myCenter";
		$rootScope.switchTab('myPayForm');
	}
	
	
}]);

//处理滚动屏：因为每个滚动屏都不同，我们不要做成公共函数
var RevosliderInit = function () {
    return {
        initRevoSlider: function () {
        		//log(jQuery('.fullwidthbanner'))
                  jQuery('.fullwidthbanner').show().revolution({ 
                      delay:2000,
                      startheight:400,
                      startwidth:340,

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
                      fullWidth:"off"                          // Turns On or Off the Fullwidth Image Centering in FullWidth Modus
                  });
        }
    };

}();



