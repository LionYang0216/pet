var RESULT_FLAG_SUCCESS = 1;
var RESULT_FLAG_FAIL = 0;
var COMMON_LABEL_SUCCESS = 'SUCCESS 成功';
var COMMON_LABEL_WARNING = 'WARNING 注意';
var COMMON_LABEL_INFO = 'INFO 提示';
var COMMON_LABEL_ERROR = 'ERROR 错误';
//商店审批状态
var SHOP_STATUS_REQUEST = '0'; //申请中
var SHOP_STATUS_APPROVED = '1';//已审批
var SHOP_STATUS_REJECT = '2';//拒绝




var dtLanguage =  {
	"sLengthMenu": "每页 _MENU_ 条",
	"sZeroRecords": "对不起，查询不到任何相关数据",
	"sInfo": " &nbsp;&nbsp;当前显示 _START_ 到 _END_ 条，共 _TOTAL_条记录",
	"sInfoEmtpy": "找不到相关数据",
	"sInfoFiltered": "数据表中共为 _MAX_ 条记录)",
	"sProcessing": "正在加载中...",
	"sSearch": "搜索",
	"oPaginate": {
		"sFirst": "第一页",
		"sPrevious":" 上一页 ",
		"sNext": " 下一页 ",
		"sLast": " 最后一页 "
	}
}
if(typeof(bootbox) != "undefined"){
	bootbox.setDefaults("locale","zh_CN");  
}

if(typeof(toastr) != "undefined"){
	var positionCls = "toast-bottom-right";
	if(window.location.href.toString().indexOf("front")>-1){
		positionCls = "toast-bottom-right";
	}
	toastr.options = {
	  "closeButton": true,
	  "debug": false,
	  "positionClass": positionCls,
	  "onclick": null,
	  "showDuration": "1000",
	  "hideDuration": "1000",
	  "timeOut": "5000",
	  "extendedTimeOut": "1000",
	  "showEasing": "swing",
	  "hideEasing": "linear",
	  "showMethod": "fadeIn",
	  "hideMethod": "fadeOut"
	}
}

//是否发布到服务器？
function isServer(){
	// 如果是发布版，跳转到前台页面
	if(window.location.href.toString().indexOf("petpack.com.cn")>-1){
		return true;
	}
	if(window.location.href.toString().indexOf("pet.gz-software.com")>-1){
		return true;
	}
	return false;
}

//如果是发布版，客户要求屏蔽所有鼠标右键的操作，他们的图片和资料敏感
function isEnableRightClick(){
	if(isServer()){
		return false;
	}else{
		return true;
	}
}


var defaultTimeout = 59000; //超时时间设置，单位毫秒

function getWebRootPath() {
	var strFullPath=window.document.location.href;
	var strPath=window.document.location.pathname;
	var pos=strFullPath.indexOf(strPath);
	var prePath=strFullPath.substring(0,pos);
	var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
	
	// test if server or not:
	if(strFullPath.indexOf('/pet/')>-1){ // local mode
		return prePath + postPath
	}else{ // server mode
		return prePath;
	}

}

// 获取request后面的字符串：var request = new getRequestQueryStr(); alert(request.id);
function getRequestQueryStr()
{
    var url = window.location.href;
    var num = url.indexOf("?");
    var str = url.substr(num + 1);
    var arr = str.split("&");
    var name = "";
    var value = "";
    for(var i = 0; i < arr.length; i++)
    {
        num = arr[i].indexOf("=");
        if(num > 0)
        {
            name = arr[i].substring(0, num);
            value = arr[i].substr(num + 1);
            this[name] = value;
        }
    }
}


var WEB_ROOT_PATH = getWebRootPath(); // 网站项目根路径

function log(str) {
    console.log(str);
}

function debug(str) {
    console.debug(str);
}

// 前台生成支付类订单号码
function generateOrderNum(){
	var curDate= new Date();
	var dateStr = curDate.yyyymmddhhmmss();
	
	var num="";
	for(var i=0;i<4;i++)
	{
		num+=Math.floor(Math.random()*10);
	} 
	var orderNum = dateStr + num;
	return orderNum;
	
}

function generateRandomUrl(url) {
    //return url;
    var randomNum = Math.random();

    if (url.indexOf("Enum") > 0) {
        return url;
    }

    if (url.indexOf("?") > -1) {
        url += "&randomNum=" + randomNum
    } else {
        url += "?randomNum=" + randomNum
    }
    return url;
}

function initUploader(ngUploader,callback){
	ngUploader.url = "../file/upload";
	ngUploader.autoUpload = true;
	removeAfterUpload: true; //上传后删除文件
	ngUploader.onCompleteItem = function(fileItem, response, status, headers) { // upload success
	    //console.info('onCompleteItem', fileItem, response, status, headers);
		var result = response;
		 if (callback != undefined) {
             callback(result)
         } else {
        	 toastr.error("Sorry,You must defined a callback method", COMMON_LABEL_ERROR);
         }
	};
	
	ngUploader.onBeforeUploadItem = function(fileItem){
		if(fileItem.file!=undefined){
			toastr.info("上传中: " + fileItem.file.name,COMMON_LABEL_INFO)
		}
	};
	
	ngUploader.onErrorItem = function(fileItem, response, status, headers) {
        toastr.error("Sorry, Upload error: " + response, COMMON_LABEL_ERROR );
    };
	
}

function jsonPost(url, reqData, callback, async, isDebug) {
    jsonPost(url, reqData, callback, async, defaultTimeout, isDebug)
}

function jsonPost(url, reqData, callback, async, requestTimeout, isDebug) {
    //isDebug = true;

    var callerMethod = jsonPost.caller.toString();
    var re = /function\s*(\w*)/i;
    var callerMethodName = re.exec(callerMethod);

    // url = generateRandomUrl(url);
    var reqStr = JSON.stringify(reqData);
    var returnData;
    if (isDebug) {
        debug("[" + callerMethodName + "]" + "[Param] " + reqStr);
    }
    $.ajax({
        type: "POST",
        url: url,
        data: reqStr,
        async: async,
        contentType: "application/json; charset=utf-8",
        timeout: requestTimeout, //超时时间设置，单位毫秒
        dataType : "json",
        success: function (result) {
            var resultStr = JSON.stringify(result);
            if (isDebug) {
                debug("[" + callerMethodName + "]" +  "[Return] " + result);
            }

            if (callback != undefined) {
                callback(result)
            } else {
                returnData = result;
                if (async) {
                    return returnData;
                }
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        	var errMsg = "JSON POST REQUEST ERROR<br> textStatus: " + textStatus + " <br>errorThrown: " + errorThrown;
        	errMsg+= "<br> url: " + url;
        	errMsg+= "<br> reqData: " + reqStr;
        	toastr.error(errMsg, COMMON_LABEL_ERROR);
        },
    });
    if (!async) {
        return returnData;
    }
}

function jsonUpload(url, formData, callback, async, isDebug) {
    //isDebug = true;
    var callerMethod = jsonUpload.caller.toString();
    var re = /function\s*(\w*)/i;
    var callerMethodName = re.exec(callerMethod);
    url = generateRandomUrl(url);
    var returnData;
    if (isDebug) {
        debug("[" + callerMethodName + "]" + "[FormData] " + formData);
    }

    $.toaster("上传中..Uploading..", Common_Label_Info, 'info');

    $.ajax({
        url: url,
        type: 'POST',
        data: formData,
        async: async,
        cache: false,
        contentType: false,
        processData: false,
        success: function (result) {
            var resultStr = JSON.stringify(result);
            if (isDebug) {
                debug("[" + callerMethodName + "]" + "[Return] " + result);
            }

            if (callback != undefined) {
                callback(result)
            } else {
                returnData = result;
                if (async) {
                    return returnData;
                }
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        	var errMsg = "JSON POST REQUEST ERROR<br> textStatus: " + textStatus + " <br>errorThrown: " + errorThrown;
        	errMsg+= "<br> url: " + url;
        	toastr.error(errMsg, COMMON_LABEL_ERROR);
           
        },
    });
    if (!async) {
        return returnData;
    }
    
}

function jsonGet(url, reqData, callback, async, requestTimeout, isDebug) {
    jsonGet(url, reqData, callback, async, defaultTimeout, isDebug);
}

function jsonGet(url, reqData, callback, async, requestTimeout, isDebug) {
    //isDebug = true;

    var callerMethod = jsonGet.caller.toString();
    var re = /function\s*(\w*)/i;
    var callerMethodName = re.exec(callerMethod);

    url = generateRandomUrl(url);

    var reqStr = JSON.stringify(reqData);
    var returnData;
    if (isDebug) {
        debug("[" + callerMethodName + "]" + "[Param] " + reqStr);
    }
    $.ajax({
        type: "GET",
        url:  url,
        data: reqData,
        dataType : "json",
        async: async,
        timeout: requestTimeout, //超时时间设置，单位毫秒
        success: function (result) {
            var resultStr = JSON.stringify(result);
            if (isDebug) {
                debug("[" + callerMethodName + "]" + "[Return] " + result);
            }

            if (callback != undefined) {
                callback(result)
            } else {
                returnData = result;
                if (async) {
                    return returnData;
                }
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        	var errMsg = "JSON GET REQUEST ERROR<br> textStatus: " + textStatus + " <br>errorThrown: " + errorThrown;
        	errMsg+= "<br> url: " + url;
        	errMsg+= "<br> reqData: " + reqStr;
        	toastr.error(errMsg, COMMON_LABEL_ERROR);
        },
    });
    if (!async) {
        return returnData;
    }
}

Date.prototype.yyyymmdd = function () {
    var yyyy = this.getFullYear();
    var mm = this.getMonth() < 9 ? "0" + (this.getMonth() + 1) : (this.getMonth() + 1); // getMonth() is zero-based
    var dd = this.getDate() < 10 ? "0" + this.getDate() : this.getDate();
    return "".concat(yyyy).concat(mm).concat(dd);
};

Date.prototype.yyyymmddhhmm = function () {
    var yyyy = this.getFullYear();
    var mm = this.getMonth() < 9 ? "0" + (this.getMonth() + 1) : (this.getMonth() + 1); // getMonth() is zero-based
    var dd = this.getDate() < 10 ? "0" + this.getDate() : this.getDate();
    var hh = this.getHours() < 10 ? "0" + this.getHours() : this.getHours();
    var min = this.getMinutes() < 10 ? "0" + this.getMinutes() : this.getMinutes();
    return "".concat(yyyy).concat(mm).concat(dd).concat(hh).concat(min);
};

Date.prototype.yyyymmddhhmmss = function () {
    var yyyy = this.getFullYear();
    var mm = this.getMonth() < 9 ? "0" + (this.getMonth() + 1) : (this.getMonth() + 1); // getMonth() is zero-based
    var dd = this.getDate() < 10 ? "0" + this.getDate() : this.getDate();
    var hh = this.getHours() < 10 ? "0" + this.getHours() : this.getHours();
    var min = this.getMinutes() < 10 ? "0" + this.getMinutes() : this.getMinutes();
    var ss = this.getSeconds() < 10 ? "0" + this.getSeconds() : this.getSeconds();
    return "".concat(yyyy).concat(mm).concat(dd).concat(hh).concat(min).concat(ss);
};


/**
 * 转化一个日期对象为 年-月-日格式
 * @return {Object} t时间类对象
 */
function dateTimeString(date) {

	var t = new Date(date);
	var yearStr = t.getFullYear();
	var monStr = (t.getMonth() + 1);
	if (monStr < 10) {
		monStr = "0" + monStr;
	}
	var dayStr = t.getDate();
	if (dayStr < 10) {
		dayStr = "0" + dayStr;
	}

	var hourStr = t.getHours();
	if (hourStr < 10) {
		hourStr = "0" + hourStr;
	}
	var minuStr = t.getMinutes();
	if (minuStr < 10) {
		minuStr = "0" + minuStr;
	}
	var secondStr = t.getSeconds();
	if (secondStr < 10) {
		secondStr = "0" + secondStr;
	}
	var allDate = yearStr + "-" + monStr + "-" + dayStr+" " + hourStr +":"+ minuStr+":"+secondStr;

	return allDate
}

/**
 * 转化一个日期对象为 年-月-日格式
 * @return {Object} t时间类对象
 */
function dateTimeFormatYMD(date) {

	var t = new Date(date);
	var yearStr = t.getFullYear();
	var monStr = (t.getMonth() + 1);
	if (monStr < 10) {
		monStr = "0" + monStr;
	}
	var dayStr = t.getDate();
	if (dayStr < 10) {
		dayStr = "0" + dayStr;
	}

	var hourStr = t.getHours();
	var minuStr = t.getMinutes();
	var secondStr = t.getSeconds();

	var allDate = yearStr + "-" + monStr + "-" + dayStr;

	return allDate
}





String.prototype.format = function (args) {
    if (arguments.length > 0) {
        var result = this;
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                var reg = new RegExp("({" + key + "})", "g");
                result = result.replace(reg, args[key]);
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] == undefined) {
                    return "";
                }
                else {
                    var reg = new RegExp("({[" + i + "]})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
        return result;
    }
    else {
        return this;
    }
}


/**
 * 时间戳转换为“刚刚”、“1分钟前”、“2小时前”“1天前”等格式
 * @param dateTimeStamp 时间字符串
 * @return {String} 返回常见的微信时间格式
 */
function getDateDiff(obj) {

	//JavaScript函数：
	var minute = 1000 * 60;
	var hour = minute * 60;
	var day = hour * 24;
	var halfamonth = day * 15;
	var month = day * 30;
	var dateTimeStamp = obj;
	//将格式化时间转化为时间戳
	dateTimeStamp = Date.parse(dateTimeStamp.replace(/-/gi, "/"));
	var now = new Date().getTime();
	var diffValue = now - dateTimeStamp;
	if(diffValue < 0) {
		//若日期不符则弹出窗口告之
		//alert("日期不符"+dateTimeStamp);
	}
	var monthC = diffValue / month;
	var weekC = diffValue / (7 * day);
	var dayC = diffValue / day;
	var hourC = diffValue / hour;
	var minC = diffValue / minute;
	if(monthC >= 1) {
		result = obj.split(" ")[0];
	} else if(weekC >= 1) {
		result = obj.split(" ")[0];
	} else if(dayC >= 1) {
		result = parseInt(dayC) + "天前";
	} else if(hourC >= 1) {
		result = parseInt(hourC) + "小时前";
	} else if(minC >= 1) {
		result = parseInt(minC) + "分钟前";
	} else
		result = "刚刚";
	return result;
}


//设置cookie  
function setCookie(cname, cvalue, exdays) {  
    var d = new Date();  
    d.setTime(d.getTime() + (exdays*24*60*60*1000));  
    var expires = "expires="+d.toUTCString();  
    document.cookie = cname + "=" + cvalue + "; " + expires; 
    //log("setCookie");
    //log(document.cookie);
}  
//获取cookie  
function getCookie(cname) {  
    var name = cname + "="; 
      //log("getCookie");
      //log(document.cookie);
    var ca = document.cookie.split(';');  
    for(var i=0; i<ca.length; i++) {  
        var c = ca[i];  
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);  
    }  
    return "";  
}  
//清除cookie    
function clearCookie(name) {    
    setCookie(name, "", -1);    
} 







