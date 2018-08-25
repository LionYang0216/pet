angular.module('frontApp').controller('myBalanceCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {

	
	$scope.$on('$includeContentLoaded', function() { 

		var userId=$rootScope.curUserId;
		
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
		        },
		        {
		            "data": "changeTime",
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
		    "condition":{
		    	id:userId
		    }
		}
		
	    
	    //获取数据  
	    $scope.getBalanceData = function(page,size,callback){  
	    	var url = "../userBalanceLog/getUserBalanceLogList";
	    	reqData.draw+=1;
	    	reqData.start=size*(page-1);//获取数据的起始位置=每页数据总数*(当前页码-1)
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					$scope.count=result.recordsTotal; 
	                $scope.pCurrent = page;  
	                $scope.pAllPage =Math.ceil($scope.count/$scope.pPernum); 
					$scope.pages=calculateIndexes($scope.pCurrent,$scope.pAllPage,$scope.pPernum);  
					var balanceLogList=result.data;
					for(var i in balanceLogList){
						balanceLogList[i].changeTimeLayout= dateTimeString(balanceLogList[i].changeTime);
    					if (balanceLogList[i].changeType==1){
    						balanceLogList[i].changeTypeLayout="查看瓶库";
    					}else if(balanceLogList[i].changeType==2){
    						balanceLogList[i].changeTypeLayout="线下提现";
    					}else if(balanceLogList[i].changeType==6){
    						balanceLogList[i].changeTypeLayout="微信充值";
    					}else if(balanceLogList[i].changeType==7){
    						balanceLogList[i].changeTypeLayout="支付宝充值";
    					}else if(balanceLogList[i].changeType==8){
    						balanceLogList[i].changeTypeLayout="线下充值";
    					}	 
					}
					$scope.balanceLogList =balanceLogList;
					$scope.$apply();
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
	    }  
	    
	    

		// initial
		$scope.getBalanceData(1,$scope.pPernum,function(){ }); 

		$rootScope.reloadBalanceList=function(){
			$scope.getBalanceData(1,$scope.pPernum,function(){ }); 
		}
	    
		//首页  
	    $scope.p_index = function(){  
	        $scope.getBalanceData(1,$scope.pPernum,function(){ });  
	    }  
	    //尾页  
	    $scope.p_last = function(){  
	        var page=$scope.pAllPage;
	        $scope.getBalanceData(page,$scope.pPernum,function(){ });  
	    }  
	    //加载某一页  
	    $scope.loadPage = function(page){  
	        $scope.getBalanceData(page,$scope.pPernum,function(){ });  
	    }; 

	    $scope.initUser();
	});
	
	
	// 查找USER
	$scope.initUser = function(){

		var url = "../user/getUser";
		var req={
			"id":$rootScope.curUserId	
		}
		jsonGet(url, req, callback, true, false);
		function callback(result) {
			$scope.user = result.data; 
			$scope.$apply();
	    }

	}
		
}]);


angular.module('frontApp').controller('myPayFormCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {

	var chargeOrderNumber ="0";
	
	$scope.$on('$includeContentLoaded', function() { 
		
		$scope.userBalanceLog={};
		$scope.userBalanceLog.changeType = "6" ; // default select wechat pay
		$scope.initUser();
		$scope.back();
		
	});
	
	
	$scope.goNext=function(){
		$("#wechatPayForm").show();
		$("#payForm").hide();
		$("#aliPayForm").hide();
	}
	
	$scope.back=function(){
		$("#payForm").show();
		$("#wechatPayForm").hide();
		$("#aliPayForm").hide();
	}
	
	$scope.toMybalance=function(){
		$scope.switchTab('myBalance');
	}
	
	
	//调用 微信支付 或者 支付宝支付 
	$scope.next=function(){
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
		
		
		if($scope.userBalanceLog.changeType == 6){
			$scope.wechatPay();
		}
		
		if($scope.userBalanceLog.changeType == 7){
			$scope.aliPay();
		}
		
		
		

	}
	
	$scope.wechatPay = function(){
		//alert("this is wechatpay")
		var url = "../wechat/pay";
		chargeOrderNumber = generateOrderNum(); // 前台生成订单号码 yyyymmddhhmmss+4ramnum
		//alert(orderNum)
		
		
		var req={
			"totalFee":$('#changeAmount').val(),
			"orderNum": chargeOrderNumber
		}
		
		jsonGet(url, req, callback, true, false);
		function callback(result) {
			if(result.flag){
				var url =result.data.qrCodeUrl;
				link = encodeURIComponent(url);
				$scope.QRcode = WEB_ROOT_PATH + "/qrCode/generate?url="+link;
				//chargeOrderNumber=result.data.outTradeNo; //不再需要后台返回，前台预先生成
				$scope.$apply();
				$("#payForm").hide();
				$("#wechatPayForm").show();
			}else{
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
			
			
		}
	}
	
	$scope.aliPay = function(){
		//alert("this is alipay")
		var url = "../alipay/pay";
		chargeOrderNumber = generateOrderNum(); // 前台生成订单号码 yyyymmddhhmmss+4ramnum

		var src= "../alipay/pay?totalFee="+$('#changeAmount').val()+"&orderNum="+chargeOrderNumber+"&width=200";
		log(src);
		$('#aliCode').attr('src',src);  
		
		$("#payForm").hide();
		$("#aliPayForm").show();


		
	}
	
	
	//新增用户消费记录
	$scope.addUserBalanceLog = function(){

		var url = "../userBalanceLog/saveUserBalanceLog";
		var req = $scope.userBalanceLog;
		jsonPost(url, req, callback, true, false);
		function callback(result) {
			toastr.success("充值成功 Pay charge succeed!", COMMON_LABEL_SUCCESS);
			$rootScope.reloadBalanceList();
			$scope.switchTab('myBalance');
			$scope.$apply();
	    }

	}
	
	
	$scope.hasFormError = function(){
		var errorMsg = "";

		if($('#changeAmount').val()==""){
			errorMsg += "充值金额不能为空<br>";
			$('#changeAmount').parent().addClass("has-error");
		}else{
			$('#changeAmount').parent().removeClass("has-error");
		}
		
		var re = /^[0-9]+$/ ;
	    
		if(!re.test($('#changeAmount').val())){
			errorMsg += "充值金额必须是整数<br>";
			$('#changeAmount').parent().addClass("has-error");
		}else{
			$('#changeAmount').parent().removeClass("has-error");
		}

	
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	}
	
	//已支付
	$scope.paid=function(){

		var url = "../userBalanceLog/checkChargeOrderNumber";
		var req ={
			"chargeOrderNumber":chargeOrderNumber	
		}
		jsonGet(url, req, callback, true, false);
		function callback(result) {
			if(result.flag>0){
				toastr.success("已经支付成功, 支付单号是:"+chargeOrderNumber, COMMON_LABEL_SUCCESS);
				$rootScope.reloadBalanceList();
				$scope.initUser();
				$scope.switchTab('myBalance');
				$scope.$apply();
			}else{
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
			
	    }
	}
	
	// 查找USER
	$scope.initUser = function(){

		var url = "../user/getUser";
		var req={
			"id":$rootScope.curUserId	
		}
		jsonGet(url, req, callback, true, false);
		function callback(result) {
			$scope.user = result.data; 
			$scope.$apply();
	    }

	}
	
}]);



