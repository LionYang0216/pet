// wx prod ctrl
var prodModule = angular.module('prodApp', ['ngSanitize']);
prodModule.controller('prodListController', function($scope, $http ,$timeout,$sce) {
	
	//配置  
    $scope.count = 0;  //总页数
    //变量   
    $scope.pCurrent = 1;  
    $scope.pAllPage =0;  
    $scope.pages = [];  
    $scope.length = 6;
	
	var prodCon = {
			"isEnabled":1
	}; // prodCondition
	
	// ready event
	$(document).ready(function(){
		getData();
		pullUpAction()
	});
	
	// to detail page
	$scope.toDetail = function(id){
		//log(id);
		window.location = "prodDetail.html?id=" + id+"&ran="+Math.random();
	}
	
	$scope.changeData=function(event,prop){
		//alert(3)
		var targetObj = $(event.target); 
		var value=targetObj.context.value;
		if(!value){
			value="";
		}
		prodCon[prop] = value;
		getData($scope.length);
		
	}
	
	// select change
	$scope.selectChange=function(value,prop){
		prodCon[prop] = value;
		getData($scope.length);
	}
	
	
	// set the product condition properties value
	$scope.setProdCon = function(event,prop,value){
		prodCon[prop] = value; // let product condtion set this prop search value
		var targetObj = $(event.target); 
		//log(targetObj);
		targetObj.parent().parent().find('button').removeClass("btn-primary"); // let other btn as same parent reset default
		targetObj.addClass("btn-primary"); // let this btn highlight
		//log(prodCon)
	}
	
	// reset the menu selected search btns
	$scope.reset = function(){
		reqData.condition = prodCon = {}; // clear all
		$("button[name='all']").parent().parent().find('button').removeClass("btn-primary"); // let other btn as same parent reset default
		$("button[name='all']").addClass("btn-primary"); // let this btn highlight
	}
	
	// search by prod Con
	$scope.search = function(){
		prodCon["nameCn"] = $("#nameCnText").val();
		getData($scope.length);
		
	}
	
	// close menu
	$scope.closeMenu = function(){
		if(offCanvasWrapper.offCanvas().visible){
			offCanvasWrapper.offCanvas('close');
		}
	}
	

	
	// 侧滑容器父节点
	var offCanvasWrapper;var offCanvasInner;var offCanvasSide;
	
    // 加载菜单内容
    $scope.initMenu = function(){
		mui.init();
		// 侧滑容器父节点
		offCanvasWrapper = mui('#offCanvasWrapper');
		// 主界面容器
		offCanvasInner = offCanvasWrapper[0].querySelector('.mui-inner-wrap');
		// 菜单容器
		offCanvasSide = document.getElementById("offCanvasSide");
		
		if (!mui.os.android) {
			
			var spans = document.querySelectorAll('.android-only');
			for (var i = 0, len = spans.length; i < len; i++) {
				spans[i].style.display = "none";
			}
		}
		// 移动效果是否为整体移动
		var moveTogether = false;
		// 侧滑容器的class列表，增加.mui-slide-in即可实现菜单移动、主界面不动的效果；
		var classList = offCanvasWrapper[0].classList;
		// 变换侧滑动画移动效果；
		
			
		offCanvasSide.classList.remove('mui-transitioning');
		offCanvasSide.setAttribute('style', '');
		classList.remove('mui-slide-in');
		classList.remove('mui-scalable');
		if (moveTogether) {
			// 仅主内容滑动时，侧滑菜单在off-canvas-wrap内，和主界面并列
			offCanvasWrapper[0].insertBefore(offCanvasSide,
					offCanvasWrapper[0].firstElementChild);
		}
		offCanvasWrapper.offCanvas().refresh();
				
			
		// 主界面‘显示侧滑菜单’按钮的点击事件
		document.getElementById('offCanvasShow').addEventListener('tap', function() {
			offCanvasWrapper.offCanvas('show');
		});
		// 菜单界面，‘关闭侧滑菜单’按钮的点击事件
		document.getElementById('offCanvasHide').addEventListener('tap', function() {
			offCanvasWrapper.offCanvas('close');
		});
		// 主界面和侧滑菜单界面均支持区域滚动；
		mui('#offCanvasSideScroll').scroll();
		mui('#offCanvasContentScroll').scroll();
		// 实现ios平台原生侧滑关闭页面；
		if (mui.os.plus && mui.os.ios) {
			mui.plusReady(function() { // 5+
										// iOS暂时无法屏蔽popGesture时传递touch事件，故该demo直接屏蔽popGesture功能
				plus.webview.currentWebview().setStyle({
					'popGesture' : 'none'
				});
			});
		}
		
		initScroll();
		
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
		
		setTimeout(function(){
			$scope.$apply();
		},3000);
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
	    "length": $scope.length,//每页数据条数
	    "search": {
	        "value": "",
	        "regex": false
	    },
	    "condition": prodCon
	};
	
	
	
	
    
    //获取数据  
    function getData(length){  
    	//log(reqData)
    	var url =WEB_ROOT_PATH + "/prod/getProdList";
    	if(length){
    		reqData.length=length;
    	}
    	reqData.draw+=1;
    	reqData.start=0;//获取数据的起始位置=每页数据总数*(当前页码-1)
		jsonPost(url, reqData, callback, false, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.prodList = result.data;
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
    }  
    
	
	
	
	
	
	
	
	var myScroll, pullDownEl, pullDownOffset, pullUpEl, pullUpOffset, generatedCount = 0;

	function pullDownAction() {
		setTimeout(function() { // <-- Simulate network congestion, remove setTimeout from production!
			var el, li, i;
			el = document.getElementById('thelist');

			//添加下拉刷新事件

			myScroll.refresh(); // Remember to refresh when contents are loaded (ie: on ajax completion)
		}, 1000); // <-- Simulate network congestion, remove setTimeout from production!
	}

	function pullUpAction() {
		setTimeout(function() { // <-- Simulate network congestion, remove setTimeout from production!
			var el, li, i;
			el = document.getElementById('thelist');

			//添加上拉加载事件
			$scope.length += 6;
			getData($scope.length);
			$scope.$apply();
			myScroll.refresh(); // Remember to refresh when contents are loaded (ie: on ajax completion)
		}, 1); // <-- Simulate network congestion, remove setTimeout from production!
	}

	function initScroll() {
		//pullDownEl = document.getElementById('pullDown');
		//pullDownOffset = pullDownEl.offsetHeight;
		pullUpEl = document.getElementById('pullUp');
		pullUpOffset = pullUpEl.offsetHeight;

		myScroll = new iScroll(
				'wrapper',
				{
					useTransition : false,
					topOffset : pullDownOffset,
					onRefresh : function() {
						//} else 
						if (pullUpEl.className.match('loading')) {
							pullUpEl.className = '';
							pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉显示更多数据...';
						}
					},
					onScrollMove : function() {
					 if (this.y < (this.maxScrollY - 5)
								&& !pullUpEl.className.match('flip')) {
							pullUpEl.className = 'flip';
							pullUpEl.querySelector('.pullUpLabel').innerHTML = '正在刷新...';
							this.maxScrollY = this.maxScrollY;
						} else if (this.y > (this.maxScrollY + 5)
								&& pullUpEl.className.match('flip')) {
							pullUpEl.className = '';
							pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉显示更多数据...';
							this.maxScrollY = pullUpOffset;
						}
					},
					onScrollEnd : function() {
						 if (pullUpEl.className.match('flip')) {
							pullUpEl.className = 'loading';
							pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
							pullUpAction(); // Execute custom function (ajax call?)
						}
					}
				});

		setTimeout(function() {
			document.getElementById('wrapper').style.left = '0';
		}, 800);
	}

	document.addEventListener('touchmove', function(e) {
		e.preventDefault();
	}, false);

	document.addEventListener('DOMContentLoaded', function() {
		setTimeout(loaded, 200);
	}, false);


});


prodModule.controller('prodDetailController', function($scope, $http ,$sce) {	
	
	var id;
	$(document).ready(function(){
		
		var request = new getRequestQueryStr();
		id = request.id;
		//log(request.id);
		
		getData();
		
		
		
	});
	
	//产品关联功能
	$scope.getRefProdList=function(refCode){

		var url =  WEB_ROOT_PATH + "/prod/getRefProdList?randomNum="+Math.random()+"&refCode="+refCode;
		jsonGet(url, null, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.refProdList=result.data;
				$scope.$apply();
			} else {
				
			}
	    }
	}
	
	$scope.goBack=function(){
		window.history.back(); 
	}
	
	
	  //获取数据  
    function getData(){  

    	var url =WEB_ROOT_PATH + "/prod/getProd?randomNum="+Math.random()+"&id="+id;

    	jsonGet(url, null, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				
				var prod=result.data;

				prod.descriptionCn=replaceAll(prod.descriptionCn);  
				$scope.prod =prod;
				$scope.getRefProdList($scope.prod.refCode);
				$scope.$apply();
				RevosliderInit.initRevoSlider();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
    }  
    
	//跳转关联产品
	$scope.goRefProd=function(id){
		window.location.href="prodDetail.html?id=" + id;
	}
	
	function replaceAll(str)  
	{  
	    if(str!=null)  
	    str = str.replace(/upload/g,"../../upload");  
	    return str;  
	}  
	

});

//处理滚动屏：因为每个滚动屏都不同，我们不要做成公共函数
var RevosliderInit = function () {

    return {
        initRevoSlider: function () {
        		//log(jQuery('.fullwidthbanner'))
                  jQuery('.fullwidthbanner').show().revolution({ 
                      delay:2000,
                      startheight:600,
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
                      fullWidth:"on"                          // Turns On or Off the Fullwidth Image Centering in FullWidth Modus
                  });
        }
    };

}();


