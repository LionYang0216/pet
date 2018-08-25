// list ctrl
angular.module('backApp').controller('prodListCtrl', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, $http) {

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
		var prodListDataTable = new Datatable().init({
            src: $("#prodListDataTable"),
            dataTable: {
    			"language": dtLanguage,
    			"bStateSave" : true, // save datatable
    			"serverSide" : true,
    			"ordering" : true,// 是否允许用户排序
    			"paging" : true,
    			"lengthMenu" : [ [ 5, 10, 15, 20, 50 ],[ 5, 10, 15, 20, 50 ]],// change per page
    			"pageNumber" : 1,
    			"pageSize" : 10, // default record count per page
    			//"fnServerData": retrieveData, // 获取数据的处理函数
    			"ajax" : {
    				"type" : "POST",
    				"url" : "../prod/getProdList", // ajax
    				"contentType": "application/json; charset=utf-8",
    				"data": function (dtRequest) {
    					  dtRequest = $.extend({}, dtRequest, {condition:$scope.prodCondition}); // merge the form data into current Query
    					  var reqData = JSON.stringify(dtRequest);
    				      return reqData;
                     }
    			},
    			"columns" : [  {
    				"data" : null
    			},{
    				"data" : "majorPic"
    			},{
    				"data" : "id"
    			}, {
    				"data" : "nameCn"
    			}, {
    				"data" : "isNew"
    			}, {
    				"data" : "prodTypePhyId"
    			},{
    				"data" : "prodTypeAreId"
    			},{
    				"data" : "prodTypePicId"
    			},{
    				"data" : "prodTypeBizId"
    			},{
    				"data" : "prodTypeJarId"
    			},{
    				"data" : "prodTypeNekId"
    			},{
    				"data" : "prodTypeConId"
    			},{
    				"data" : "prodTypeHigId"
    			},{
    				"data" : "prodTypeWidId"
    			},{
    				"data" : "prodTypeSouId"
    			},{
    				"data" : "prodCostId"
    			},{
    				"data" : "visitCnt"
    			},{
    				"data" : "admin.adminName"
    			},{
    				"data" : "user.userName"
    			}, {
    				"data" : "lastUpdateTime"
    			},{
    				"data" : "isEnable"
    			},{
    				"data" : "id"
    			} ],
    			"columnDefs" : [
    			{
    				"render" : function(data, type, row, meta) {
    					var startIndex = meta.settings._iDisplayStart;
    					return startIndex + meta.row + 1;
    				},
    				"targets" : 0,
    				'searchable' : false,
    				'orderable' : false
    			},
				{
    				"render" : function(data, type, row, meta){
    					//console.log(data);
    					data = data.picFile.filePath;
    					var str = '<a href="../upload/'+data+'" target="_blank"><img  src="../upload/'+data+'" style="max-height:40px" border=1 /></a>';
    					return str;
    				},
    				"targets" : 1,
    				'searchable' : false,
    				'orderable' : false
    			},
    			{
    				"render" : function(data, type, row, meta){
    					//var link = WEB_ROOT_PATH + "/wechat/views/prod/prodDetail.html?id=" + data;
    					var link = WEB_ROOT_PATH + "/wechat/wechatIndex.html?toDetail&id=" + data;	//	用微信版的detail链接，更适合手机尺寸
    					link = encodeURIComponent(link);
    					var imgUrl = WEB_ROOT_PATH + "/qrCode/generate?url="+link;
    					var str = '<a href="'+imgUrl+'&width=400&height=400" target="_blank"><img src="'+imgUrl+'&width=200&height=200" style="max-height:50px" border=1 /></a>';
    					return str;
    				},
    				"targets" : 2,
    				'searchable' : false,
    				'orderable' : false
    			},{
    				"render" : function(data, type, row, meta) {
    					var str = "";
    					str=data+"<br/><font  style='color:grey;font-size:10px'>"+row.extCnLayout + "</font>";
    					return str;
    				},
    				"targets" : 3,
    				'searchable' : false,
    				'orderable' : false
    			},
    			{
    				"render" : function(data, type, row, meta) {
    					var str = "";
    					if (data == 1) {
    						str = "是";
    					} else {
    						str = "否";
    					}
    					return str;
    				},
    				"targets" : 4,
    				'searchable' : false,
    				'orderable' : false
    			},{
    				"render" : function(data, type, row, meta) {
    					if(row.prodTypePhy!=undefined){
        					return row.prodTypePhy.nameCn;
    					}else{
    						return "未定义"
    					}
    				},
    				"targets" : 5,
    				'searchable' : false,
    				'orderable' : true
    			},{
    				"render" : function(data, type, row, meta) {
    					if(row.prodTypeAre!=undefined){
        					return row.prodTypeAre.nameCn;
    					}else{
    						return "未定义"
    					}
    				},
    				"targets" : 6,
    				'searchable' : false,
    				'orderable' : true
    			},{
    				"render" : function(data, type, row, meta) {
    					if(row.prodTypePic!=undefined){
        					return row.prodTypePic.nameCn;
    					}else{
    						return "未定义"
    					}
    				},
    				"targets" : 7,
    				'searchable' : false,
    				'orderable' : true
    			},{
    				"render" : function(data, type, row, meta) {
    					if(row.prodTypeBiz!=undefined){
        					return row.prodTypeBiz.nameCn;
    					}else{
    						return "未定义"
    					}
    				},
    				"targets" : 8,
    				'searchable' : false,
    				'orderable' : true
    			},{
    				"render" : function(data, type, row, meta) {
    					if(row.prodTypeJar!=undefined){
        					return row.prodTypeJar.nameCn;
    					}else{
    						return "未定义"
    					}
    				},
    				"targets" : 9,
    				'searchable' : false,
    				'orderable' : true
    			},{
    				"render" : function(data, type, row, meta) {
    					if(row.prodTypeNek!=undefined){
        					return row.prodTypeNek.nameCn;
    					}else{
    						return "未定义"
    					}
    				},
    				"targets" : 10,
    				'searchable' : false,
    				'orderable' : true
    			},{
    				"render" : function(data, type, row, meta) {
    					if(row.prodTypeCon!=undefined){
        					return row.prodTypeCon.nameCn;
    					}else{
    						return "未定义"
    					}
    				},
    				"targets" : 11,
    				'searchable' : false,
    				'orderable' : true
    			},{
    				"render" : function(data, type, row, meta) {
    					if(row.prodTypeHig!=undefined){
        					return row.prodTypeHig.nameCn;
    					}else{
    						return "未定义"
    					}
    				},
    				"targets" : 12,
    				'searchable' : false,
    				'orderable' : true
    			},{
    				"render" : function(data, type, row, meta) {
    					if(row.prodTypeWid!=undefined){
        					return row.prodTypeWid.nameCn;
    					}else{
    						return "未定义"
    					}
    				},
    				"targets" : 13,
    				'searchable' : false,
    				'orderable' : true
    			},{
    				"render" : function(data, type, row, meta) {
    					if(row.prodTypeSou!=undefined){
        					return row.prodTypeSou.nameCn;
    					}else{
    						return "未定义"
    					}
    				},
    				"targets" : 14,
    				'searchable' : false,
    				'orderable' : true
    			},{
    				"render" : function(data, type, row, meta) {
    					if(row.prodCost!=undefined){
        					return row.prodCost.name;
    					}else{
    						return "未定义"
    					}
    				},
    				"targets" : 15,
    				'searchable' : false,
    				'orderable' : true
    			},
    			{
    				"render" : function(data, type, row, meta) {
    				 if(data==null){
    					 return "";
    				 }
    				 return data;
    				},
    				"targets" : [17,18],
    				'searchable' : false,
    				'orderable' : false
    			},{
    				"render" : function(data, type, row, meta) {
    					var str="";
    					
    					if(data){
    						str	= dateTimeString(data); 
    					}    			
    					return str;
    				},
    				"targets" : 19,
    				'searchable' : false,
    				'orderable' : true
    			},
    			{
    				"render" : function(data, type, row, meta) {
    					var str = "";
    					if (data == 1) {
    						str = "<font color=green>上架</font>";
    					}if(data == 2)  {
    						str = "<font color=blue>待确认</font>";
    					}if(data == 0){
    						str = "<font color=red>下架</font>";
    					}
    					return str;
    				},
    				"targets" : 20,
    				'searchable' : false,
    				'orderable' : true
    			},	{
    				"render" : function(data, type, row, meta) {
    					var str = '<a class="btn default" href="#/prod/form/' + data + '">编辑</a>';
    					return str;
    				},
    				"targets" : 21,
    				'searchable' : false,
    				'orderable' : false
    			} ],
    			"order" : [ [19, "desc" ] ]
    		// set first column as a default sort by asc
    		}
        });
    	
	});
	$scope.reloadTable = function(){
		var prodListDataTable= $('#prodListDataTable').DataTable();
		    prodListDataTable.ajax.reload();
	};
}]);

function initProdCostSelection($scope){
	  var url = "../prodCost/getProdCostSelect";
	   jsonGet(url, '', callback, false, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				$scope.prodCostList=result.data;
			}
	    }
}

angular.module('backApp').controller('prodFormCtrl',  ['$rootScope', '$scope','$stateParams', 'FileUploader', function($rootScope, $scope,  $stateParams, FileUploader) {
	
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
		
		$scope.prod = {};
		$scope.prod.prodPicList=[];
			if($stateParams.id > 0){ // is update
				var reqData= $stateParams;
				var url = "../prod/getProd";
				jsonGet(url, reqData, function(result) {
					if (result.flag == RESULT_FLAG_SUCCESS) {
						var prod = result.data;
						if(prod&&prod.prodPicList&&prod.prodPicList.length>0){
							for(var i=0;i<prod.prodPicList.length;i++){
								$scope.initPicFileUploader(i);	
							}
						}else{
							$scope.initPicFileUploader(0);
						}
						if(prod.prodTypePicId==4){
							$scope.showTypeSou=true;
						}
						$scope.prod=prod;
						$scope.changePhy();
						CKEDITOR.replaceAll(); 	// initial all ckeditors
						$scope.showDelBtn = true;
						$scope.$apply();
					} else {
						toastr.error(result.msg, COMMON_LABEL_ERROR);
					}
			    }, true, false);
				
			}else{
				 $scope.initAddProd();
				CKEDITOR.replaceAll();
			}
	});
	
   $scope.initAddProd=function(){
	   
	    $scope.initPicFileUploader(0);
		$scope.prod.prodPicList[0].isMajor=1;//默认选中第一个为主要图片
		$scope.prod.prodCostId=$scope.prodCostList[0].id;//默认选中免费
		$scope.prod.isNew=0;//默认不是新品
		$scope.prod.isEnable=1;//默认上架
		$scope.showDelBtn = false;
		$scope.prod.priority=1;
		
		$scope.prod.visitCnt=0;
		$scope.prod.upCnt=0;
		$scope.prod.favCnt=0;
   }
  
	// update or add an admin
	$scope.toSaveProd = function(){
		$scope.prod.descriptionCn = CKEDITOR.instances.descriptionCn.getData();
		$scope.prod.descriptionEn = CKEDITOR.instances.descriptionEn.getData();
		var reqData = $scope.prod;
		if($scope.hasFormError()){ // if there is form input error
			return;
		}
	
		if($stateParams.id > 0){ // is update
			var url = "../prod/updateProd";
			jsonPost(url, reqData, callback, true, false);
			function callback(result){
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('prodList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    };
			
		}else{ // is add
			var url = "../prod/addProd";
			jsonPost(url, reqData, callback, true, false);
			function callback(result) {
				if (result.flag == RESULT_FLAG_SUCCESS) {
					toastr.success(result.msg, COMMON_LABEL_SUCCESS);
					$rootScope.$state.go('prodList');
				} else {
					toastr.error(result.msg, COMMON_LABEL_ERROR);
				}
		    }
		}
	};
	
	
	// validate the form input while submit
	$scope.hasFormError = function(){
		var errorMsg = "";
		if($('#nameCn').val()==""){
			errorMsg += "中文标题不能为空<br>";
			$('#nameCn').parent().addClass("has-error");
		}else{
			$('#nameCn').parent().removeClass("has-error");
		}
		
		var prodPicList=$scope.prod.prodPicList;
	
		//radio选中value为1，其它为0
		 var radio = document.getElementsByName("picIsMajor");  
		 var checkIdx;
		 for (i=0; i<radio.length; i++) {  
		        if (radio[i].checked) {  
		        	checkIdx=radio[i].dataset.picIndex;  
		        }else{
		        	$scope.prod.prodPicList[i].isMajor=0;
		        }  
		 }
		 
		if(typeof(checkIdx) == "undefined" || checkIdx ==""){
		    	errorMsg += "产品主要图片不能为空<br>";
				$('#picFileId0').parent().addClass("has-error");
	    }
		for(var i=0;i<prodPicList.length;i++){
			if(prodPicList[i]==null) continue;
			if(checkIdx==i){
				var picFileId = prodPicList[i].picFileId;
				if(typeof(picFileId) == "undefined" || picFileId ==""){
			    	errorMsg += "产品主要图片不能为空<br>";
					$('#picFileId'+i).parent().addClass("has-error");
				}else{
					$('#picFileId'+i).parent().removeClass("has-error");
				}
				
				break;
			}
		}

		 var reg=/^[1-9]\d*$/;
			var priorityVal=$('#priority').val();
			if(priorityVal==""){
				errorMsg += "排序号不能为空<br>";
				$('#priority').parent().addClass("has-error");
			}else{
				if(!reg.test(priorityVal)){
					errorMsg += "排序号为正整数<br>";
					$('#priority').parent().addClass("has-error");
				}else{
				$('#priority').parent().removeClass("has-error");
				}
		}
			
	    if(CKEDITOR.instances.descriptionCn.getData() == ""){
			errorMsg += "中文产品内容不能为空<br>";
			$('#descriptionCn').parent().addClass("has-error");
		}else{
			$('#descriptionCn').parent().removeClass("has-error");
		}
		if(errorMsg!=""){
			toastr.error(errorMsg, COMMON_LABEL_ERROR);
			return true;
		}
		return false;
	};
	
	
	// delete an admin
	$scope.toDeleteProd = function(){
		if($stateParams.id){
			bootbox.confirm("是否删除?", function (result) {
				if(result){
					var reqData= $stateParams;
					var url = "../prod/deleteProd";
					jsonGet(url, reqData, callback, true, false);
					function callback(result) {
						if (result.flag == RESULT_FLAG_SUCCESS) {
							toastr.success(result.msg, COMMON_LABEL_SUCCESS);
							$rootScope.$state.go('prodList');
						} else {
							toastr.error(result.msg, COMMON_LABEL_ERROR);
						}
				    }
				}
			})
		}
	};
	
	$scope.toAddPic = function(){
		var picIdx=$scope.prod.prodPicList.length-1;
		$scope.initPicFileUploader(picIdx+1);
	}
	
	$scope.toDeletePic = function(idx){
		var picLength=$scope.prod.prodPicList.length;
		if(picLength==1){
			toastr.error("产品需要保留一张主要图片", COMMON_LABEL_ERROR);
			return;
		}
		$scope.prod.prodPicList.splice(idx, 1);
		if(picLength==2){
			$scope.prod.prodPicList[0].isMajor=1;
		}
	}
	
	$scope.clickUploadBt = function(idx){
		var id="picFileUploader"+idx;;
		document.getElementById(id).click();

	}
	
	$scope.changePhy = function () { 
		   var newList=[];
		   var j=0;
	      if($scope.prod.prodTypePhyId!=1){
	    	var list=  $scope.prodTypePicList
	    	for(var i=0;i<list.length;i++){
	    		if(list[i].nameCn!="光身图库"){
	    			newList[j]=list[i];
	    			j++;
	    		}
	    	}
	    	   $scope.picTypeChange=true;
	    	   $scope.prodTypePicList=newList;
	      }else{
	    	  if($scope.picTypeChange){
	    		  initProdTypePicSelection($scope);
	    	  }
	      }
	} 
	
	$scope.changePic=function(){
		if($scope.prod.prodTypePicId==4){
			$scope.showTypeSou=true;
		}else{
			$scope.showTypeSou=false;
		}
	}
	//图片上传
	$scope.initPicFileUploader = function(idx){
		$scope.prod.prodPicList[idx]={};
		$scope["picFileUploader"+idx] = new FileUploader();
		initUploader($scope["picFileUploader"+idx],callback);
		function callback(result){
			if (result.flag == RESULT_FLAG_SUCCESS) {
				toastr.success(result.msg, COMMON_LABEL_SUCCESS);
				var retFile = result.data;
				$scope.prod.prodPicList[idx].picFileId = retFile.id;
				$scope.prod.prodPicList[idx].picFile = retFile;
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
		}
		
	};
	
}]);
