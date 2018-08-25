angular.module('frontApp').controller('homeCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {
	
	
	$scope.$on('$viewContentLoaded', function() { 
		// this is home page ctrl
		initMarqueeList();
		initNewProdList0();
		$scope.getNewsTypeList();
		$scope.getNewWareList();
		getContent();
		
		
		var newsCon={
		   "isEnabled":1
		};
			
		// set the condition properties value
		$scope.setNewsCon = function(event,prop,value){
			newsCon[prop] = value; // let product condtion set this prop search value
			var targetObj = $(event.target); 
			//log(targetObj);
			targetObj.parent().parent().find('a').removeClass("selectActive"); // let other btn as same parent reset default
			targetObj.addClass("selectActive"); // let this btn highlight

			reqData.condition=newsCon;			
			$scope.getNewsList(1,$scope.pPernum,function(){ });  
		}
		
		var reqNewsData={
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
		    "length": 4,
		    "search": {
		        "value": "",
		        "regex": false
		    },
		    "condition": newsCon
				
		};
		
		
		  //获取行业动态数据  
	    $scope.getNewsList = function(page,size,callback){  
	    	var url = "../news/getNewsList";
	    	
			jsonPost(url, reqNewsData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					
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
	    
		// initial
		$scope.getNewsList(1,$scope.pPernum,function(){ }); 
		
	});
	

	
	$scope.switchFlow = function(event,flowIndex){
		var targetObj = $(event.target); 
		targetObj.parent().parent().parent().find('a').removeClass("selectActive"); // let other btn as same parent reset default
		targetObj.addClass("selectActive"); // let this btn highlight
		
		
		
		$("#flowDiv0").hide();
		$("#flowDiv1").hide();
		$("#flowDiv2").hide();
		$("#flowDiv"+flowIndex).show();
		
		if(flowIndex=="0"){
			initNewProdList0();
		}
		
		if(flowIndex=="1"){
			initNewProdList1();
		}
		
		if(flowIndex=="2"){
			initNewProdList2();
		}
		
		
		
	}
	
	// initial the marquee list..
	function initMarqueeList(){
		var url = "../marquee/getMarqueeSelect?code=home"; // 只取home的滚动版
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
	
	var reqData={
		    "draw": 1,
		    "columns": [
		        {
		            "data": "isEnabled",
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
		    "length": 12,
		    "search": {
		        "value": "",
		        "regex": false
		    },
		    "condition": {
		    	"isNew":1,
		    	"prodTypePhyId":""
		    }
		};
	
	// 三个img flow 要预先加载并渲染，没办法, 要查三次，坑爹的imageFlow.js
	var imgFlow0 = new ImageFlow();
	var imgFlow1 = new ImageFlow();
	var imgFlow2 = new ImageFlow();
	
	var init0 = false;
	var init1 = false;
	var init2 = false;
	
	// initial the marquee list..
	function initNewProdList0(){
		
		if(init0){
			return;
		}
		
		//$("#myImageFlow").empty();
		reqData.condition.prodTypePhyId = "";
		var url = "../prod/getProdList";
		jsonPost(url, reqData, callback0, true, false);
		function callback0(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				init0 = true;
				var prodList=result.data;
				$scope.prodList0 = prodList;
				$scope.$apply();
				imgFlow0.init({ ImageFlowID: 'myImageFlow0'});
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	function initNewProdList1(){
		
		if(init1){
			return;
		}
		
		reqData.condition.prodTypePhyId = "1";
		var url = "../prod/getProdList";
		jsonPost(url, reqData, callback1, true, false);
		function callback1(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				init1 = true;
				var prodList=result.data;
				$scope.prodList1 = prodList;
				$scope.$apply();
				imgFlow1.init({ ImageFlowID: 'myImageFlow1'});
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	function initNewProdList2(){
		
		if(init2){
			return;
		}
		
		reqData.condition.prodTypePhyId = "2";
		var url = "../prod/getProdList";
		jsonPost(url, reqData, callback2, true, false);
		function callback2(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				init2 = true;
				var prodList=result.data;
				$scope.prodList2 = prodList;
				$scope.$apply();
				imgFlow2.init({ ImageFlowID: 'myImageFlow2'});
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
		
	}
	
	
    
	$scope.goNewsDetail=function(id){
		window.location.href="#/news/newsDetail/"+id;
	}
	
	
	var reqNewsType={
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
	    "length": 5,
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
		jsonPost(url, reqNewsType, callback, true, false);
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
	
	
	
	
	var reqWare={
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
	    "length": 3,
	    "search": {
	        "value": "",
	        "regex": false
	    },
	    "condition":{
	    	"isEnabled":1,
	    	"isNew":1
	    }
	};
			
	//获取最新产品
	$scope.getNewWareList=function(){
		
		var url = "../ware/getWareList";
		jsonPost(url, reqWare, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				var newWareList=result.data;
				$scope.newWareList =newWareList;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
	
	$scope.toPWareDetail=function(id){
		window.location.href="#/ware/wareDetail/"+id;
	}
	
	
	
	
	//静态内容 
	function getContent(){
		var reqData= {
			"code":"HOME_BRAND_AREA"	
		}
		var url = "../content/getContentByCode";
		jsonGet(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.content=result.data;
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
	}
	
}]);





// 处理滚动屏：因为每个滚动屏都不同，我们不要做成公共函数
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
                      fullWidth:"off"                          // Turns On or Off the Fullwidth Image Centering in FullWidth Modus
                  });
        }
    };

}();



