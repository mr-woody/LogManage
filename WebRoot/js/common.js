//函数说明 ：创建局部提交请求。
//参数1：paramData 提交的数据, 可以为null。
//参数2：pageUrl 页面名称 一般是web服务。
//参数3：fnCallBack 回调处理方法 可以为null。不叫postback 是兼顾提交失败
//       格式为function(result){}
//参数4：blIsAsync 是否为异步 默认为true 可以为null, null时 表示true
//参数5：blIGnoreError 是否忽略异常或失败操作 默认为false 可以为null，null时表示为false
//参数6：fnOnError 异常事件 必须为function 可以为null
//返回：无
var CreatePost = function(paramData,pageUrl,fnCallBack,blIsAsync,blIGnoreError,fnOnError){
	 $.ajax({
	        async: blIsAsync != null && blIsAsync == false ? false : true,
	        type: "post",
	        url: pageUrl,
	        data:paramData,
	        dataType: 'json',
	        complex: function (xhr, textStatus) {
	        },
	        success: function (data, textStatus) {
	        	var tmpResult  = jQuery.parseJSON(data.result);
                onFinish(tmpResult, blIGnoreError, fnOnError);
	        },
	        error: function (xhr, textStatus, errorThrown) {
	            var tmpResult = {};
	            tmpResult.number = -1;
	            tmpResult.message = "失败的Ajax请求";
	            tmpResult.message = xhr.responseText;
	            onFinish(tmpResult, blIGnoreError, fnOnError);
	        }
	    });
	 
	 function onFinish(prmResult, prmIgnoreError, prmOnError) {
		    if (prmIgnoreError || typeof (prmResult.number) === "undefined" || prmResult.number === 1) {
		        //忽略处理异常
		        if (typeof fnCallBack == "function")
		        	fnCallBack(prmResult);
		        return;
		    }
		    switch (prmResult.number) {
		        case -10:
		            FunSessionOut();
		            break;
		        case -1:
		            break;
		        case 0:
		            break;
		    }
		    if (typeof prmOnError == "function") {
		        prmOnError();
		    }
	 }
};

function BindFormValueByJSON(jsonData){
	for (var propName in jsonData) {
		if(jsonData[propName] != null && $.trim(jsonData[propName]).length > 0){
			$('[name='+propName+']').val(jsonData[propName]).trigger("click").trigger("focus");
		}
	}
};



Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	}
	if (/(y+)/.test(format)){
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for ( var k in o){
		if (new RegExp("(" + k + ")").test(format)){
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

String.format = function() {
    if( arguments.length == 0 )
        return null;

    var str = arguments[0]; 
    for(var i=1;i<arguments.length;i++) {
        var re = new RegExp('\\{' + (i-1) + '\\}','gm');
        str = str.replace(re, arguments[i]);
    }
    return str;
};

// 获取url参数值
function getParam(paramName) {
    paramValue = "";
    isFound = false;
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&");
        i = 0;
        while (i < arrSource.length && !isFound) {
            if (arrSource[i].indexOf("=") > 0) {
                if (arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase()) {
                    paramValue = arrSource[i].split("=")[1];
                    isFound = true;
                }
            }
            i++;
        }
    }
    return paramValue;
};