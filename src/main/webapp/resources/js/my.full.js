/**
 *基础工具和定义 
 */
var Mu = {
		//全局变量
		globvar:{
			isSubmitNow:false,//当前是否出于提交状态
			developMode:true,
			hasStatusBar:false
		},
		navCheck:function(check){
			return navigator.userAgent.indexOf(check) !=-1;
		},
		/*获得页面分辨率*/
		getPageResolution:function(){
			var width =window.document.body.offsetWidth;
			var height=document.body.offsetHeight;
			var resolution={width:width,height:height};
			return resolution;
		},
		getScreenResolution:function(){
			var width = window.screen.width;
			var height=window.screen.height;
			var resolution={width:width,height:height};
			return resolution;
		},
		isEmpty:function(obj){
			if(null==obj||""==obj||"undefined"==obj||typeof obj=="undefined"){
				return true;
			}
			return false; 
		},
		/**
		 * 检测浏览器的私有变量
		 */
		getVendor:(function(){
			var dummyStyle = document.createElement('div').style;
			var vendors = 'a,webkitA,MozA,msA,OA'.split(','),
			t,
			i = 0,
			l = vendors.length;
			for ( ; i < l; i++ ) {
				t = vendors[i] + 'nimation';
				if ( t in dummyStyle ) {
					return vendors[i].substr(0, vendors[i].length - 1);
				}
			}
			return false;
		})(),
		prefixStyle:function (style) {
			if (Mu.getVendor) {
				style = style.charAt(0).toUpperCase() + style.substr(1);
				return Mu.getVendor + style;
				
			}
			return style;
		},
		/**
		 * 页面载入完成后执行回调函数fn
		 * @param fn
		 */
		afterPageLoaded:function(fn){
			var t = setInterval(function(){
				var h = $("body").height();
				if(h>0){
					clearInterval(t);
					fn();
				}
				},50);
		},

		parseString2Json:function(string){
			var option;
			  if(string.indexOf("[")==0&&string.indexOf("]")>0){
				  option=eval("("+string+")");
			  } else if(string.indexOf("{")==0&&string.indexOf("}")>0){
					option=eval("("+string+")");
				}else{
					option=eval("({"+string+"})");
				}
				return option;
		},
		parseJson2String:function(o){
			if(o==null || o=='undefined')return null;
		    var r = [];  
		    if (typeof o == "string") return o;  
		    if (typeof o == "object") {  
		        if (!o.sort) {  
		            for (var i in o) {
		            	if(typeof o[i]=='string' || typeof o[i] =='number'){
		            	  if(o[i] != undefined){
		            	  	r.push("\"" + i + "\":\"" +o[i].toString().replace(/\"/g,"\\\"")+"\""); 
		            	  }else{
		            	  	r.push("\"" + i + "\":null"); 
		            	  }
		                }else{
		                  r.push("\"" + i + "\":" + this.parseJson2String(o[i]));
		                }
		            }
		            if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {  
		                r.push("toString:" + o.toString.toString());  
		            }  
		            r = "{" + r.join() + "}" ;
		        } else {  
		            for (var i = 0; i < o.length; i++)
		                r.push(this.parseJson2String(o[i]));
		            r = "[" + r.join() + "]" ;
		        }  
		        return r;  
		    }  
		    return o.toString();  
		},
		/**
		 * 解析指定对象的option
		 * @param obj
		 */
		parseOption:function(t){
			var opt = t.attr("option");
			if(this.isEmpty(opt)){
				return {};
			}
			return this.parseString2Json(opt);
		},
		/**
		 *  返回特定json数组中包含指定key的值
		 * @param options
		 * @param keys,以⃣️逗号分割
		 */
		getNewJsonWithKeys:function(options,keys){
			if(this.isEmpty(options)){
				return {};
			}
			if(this.isEmpty(keys)){
				return options;
			}
			var opt=[];
			for(var i in keys){
				var k = keys[i];
				var v = options[k];
				if(!this.isEmpty(v)){
					opt[key]=v;
				}
			}
			return opt;
		},
		appendMask:function(id,callback){
			if (!$("#"+id).attr("class")) {
				var mask = '<div id="'+id+'" class="mu-mask"></div>';
				$(mask).prependTo($("body"));
			}
			return $("#"+id).unbind("click").bind("click", function() {
				if (typeof callback == "function") {
					callback();
				}
			});
		},
		/****************************************
		*将数字转换为金额显示，每三位逗号隔开
		* @param money 数字
		* @param decimal 小数位
		* @symbol symbol 金额前缀，如￥或$
		****************************************/
		moneyFormat: function(money, decimal, symbol){
			if (!money || isNaN(money)) return "";
			var num = parseFloat(money);
			num = String(num.toFixed(decimal ? decimal : 0));
			var re = /(-?\d+)(\d{3})/;
			while (re.test(num)){
				num = num.replace(re, "$1,$2");
			}
			return symbol ? symbol + num : num;
		},
		/******************************************
		*@description  数字转人民币
		*@param num{number} 要转化的数值
		*@return cnMoney{string} 转化后的人民币字符串
		*******************************************/
		number2RMB:function(num){
			var cnMoney = "零元整";
			var strOutput = "";  
			var strUnit = '仟佰拾亿仟佰拾万仟佰拾元角分';  
			money += "00";  
			var intPos = money.indexOf('.');  
			if (intPos >= 0){
				money = money.substring(0, intPos) + money.substr(intPos + 1, 2);  
			}
			strUnit = strUnit.substr(strUnit.length - money.length);  
			for (var i=0; i < money.length; i++){
				strOutput += '零壹贰叁肆伍陆柒捌玖'.substr(money.substr(i,1),1) + strUnit.substr(i,1); 
			}
			cnMoney = strOutput.replace(/零角零分$/, '整')
					  .replace(/零[仟佰拾]/g, '零')
					  .replace(/零{2,}/g, '零')
					  .replace(/零([亿|万])/g, '$1')
					  .replace(/零+元/, '元')
					  .replace(/亿零{0,3}万/, '亿')
					  .replace(/^元/, "零元");
			return cnMoney;
		}
		
		
};
/**
 * 对于js基础类的扩展
 * */
/**
 * 为String增加全替换
 */
String.prototype.replaceAll = function(s1,s2){   
	return this.replace(new RegExp(s1,"gm"),s2);  
};
/**
 * 数字格式的日期类型的格式化
 */
Number.prototype.dateStrFormat = function(format){
	var date = String(this);
	var o = {
//			"y+":this.substr(0,4),
			"M+" : date.substr(4,2), //month
			"d+" : date.substr(6,2)//, //day
//			"h+" : this.getHours(), //hour
//			"m+" : this.getMinutes(), //minute
//			"s+" : this.getSeconds(), //second
//			"q+" : Math.floor((this.getMonth()+3)/3), //quarter
//			"S" : this.getMilliseconds() //millisecond
			};
	
			if(/(y+)/.test(format)) {
				format=format.replace(RegExp.$1,
			(date.substr(0,4)+"").substr(4-RegExp.$1.length));}
			for(var k in o)if(new RegExp("("+ k +")").test(format))
			format = format.replace(RegExp.$1,
			RegExp.$1.length==1? o[k] :
			("00"+ o[k]).substr((""+ o[k]).length));
			return format;
};

String.prototype.dateStrFormat = function(format){
	if(this==""){
		return this;
	}
	var date = this.replace(/[ ]/g,"").replace(/[^x00-xff]/g,"").replaceAll("-","").replaceAll(":","");
	var o = {
//			"y+":this.substr(0,4),
			"M+" : date.substr(4,2), //month
			"d+" : date.substr(6,2),//, //day
			"h+" : date.substr(8,2), //hour
			"m+" :date.substr(10,2), //minute
			"s+" : date.substr(12,2) //second
//			"q+" : Math.floor((this.getMonth()+3)/3), //quarter
//			"S" : this.getMilliseconds() //millisecond
			};
	
			if(/(y+)/.test(format)) {
				format=format.replace(RegExp.$1,
			(date.substr(0,4)+"").substr(4-RegExp.$1.length));}
			for(var k in o)if(new RegExp("("+ k +")").test(format))
			format = format.replace(RegExp.$1,
			RegExp.$1.length==1? o[k] :
			("00"+ o[k]).substr((""+ o[k]).length));
			return format;
};

Date.prototype.format =function(format)
{
var o = {
"M+" : this.getMonth()+1, //month
"d+" : this.getDate(), //day
"h+" : this.getHours(), //hour
"m+" : this.getMinutes(), //minute
"s+" : this.getSeconds(), //second
"q+" : Math.floor((this.getMonth()+3)/3), //quarter
"S" : this.getMilliseconds() //millisecond
};
if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
(this.getFullYear()+"").substr(4- RegExp.$1.length));
for(var k in o)if(new RegExp("("+ k +")").test(format))
format = format.replace(RegExp.$1,
RegExp.$1.length==1? o[k] :
("00"+ o[k]).substr((""+ o[k]).length));
return format;
};
Mu.hideMask=function(){
	$("#mu-loading-mask").each(function(){
		$(this).animate({opacity:0},300,function(){
			$(this).remove();
		});
		$("#mu-mask").animate({opacity:0},300).remove();
	});
};

Mu.showMask=function(options){
	var mask = Mu.appendMask("mu-mask"),
	html=[];
	html.push("<div id='mu-loading-mask' class='mu-pageloading' style='opacity:0;'>");
	html.push("<div class='mu-pageloading-box' >");
 	html.push("<div class='mu-pageloading-img mu-circleBar' >");
 	var l = new Mu.loading("circleBar",{size:60,width:16,height:4});
 	html.push(l.html());
 	 html.push("</div>");
 	 if(options.msg){
 		 html.push("<div class='mu-pageloading-content'>"+options.msg+"</div>");
 	 }
	 html.push("</div>");
	 html.push("</div>");
	 mask.before(html.join(""));
	 mask.css("opacity",0).show().animate({opacity:0.5},300);
	mask.prev().animate({opacity:1},300);
	
};
/**
 * 处理页面载入的动画效果
 * 所有设置在自定义的属性option中，目前支持的情况有：
 * 	showMsg:需要显示的文字，
 *  loadingType:显示的动画类型，参数有：circleDot－实心圆点圆周循环，circleBow －1/4弧形的圆周循环，
 *  			signalBar-手机信号条,默认为circleBar
 */
(function(w,u){
	Mu.loading=function(type,opts){
		var it =this;
		it.closeEllipseLoading=false;
		it.options=opts;
		it.type=type;
		return it;
	};
	function _showellipseDotLoading(jq,t){
		var container= jq.children().children();
		//中心点横坐标
        var dotLeft = (container.width()-16)/2;
        //中心点纵坐标
        var dotTop = (container.height()-16)/2;
        //椭圆长边
        a = 36;
        //椭圆短边
        b = 20;
        //起始角度
      //  var stard = 0;

        //每一个BOX对应的角度;
        var avd = 120;//360/$(".container img").length;
        //每一个BOX对应的弧度;
        var ahd = avd*Math.PI/180;
        //运动的速度
        var speed = 6;
        //图片的宽高
       /*  var wid =30;
        var hei =30; */
        //总的TOP值
        var totTop = dotTop;
        //设置圆的中心点的位置
	      //  $(".dot").css({"left":dotLeft,"top":dotTop});
        var move =3;
        //运动函数
        var fun_animat = function(){
            
            speed = speed<360?speed:2;
            
            //运运的速度
            speed+=1;
            //运动距离，即运动的弧度数;
            var ainhd = speed*move*Math.PI/180;
          ///  console.log(Math.sin((ahd+ainhd))*a);
            //按速度来定位DIV元素
           container.children().each(function(index, element){
            //	var left = $(this).css("margin-left");
           	//	var top = $(this).css("margin-top"); 
                var allpers = (Math.sin((ahd*index+ainhd))*b+dotTop)/totTop;
                var wpers = Math.max(1.0,allpers);
               // var hpers = Math.max(1.0,allpers);
                $(this).css({
                    "margin-left":Math.cos((ahd*index+ainhd))*a+dotLeft,
                    "margin-top":Math.sin((ahd*index+ainhd))*b+dotTop,
                   "-webkit-transform": "scale("+wpers+")",
                   "-moz-transform": "scale("+wpers+")",
                   "-o-transform": "scale("+wpers+")",
                   "-ms-transform": "scale("+wpers+")",
                   "transform": "scale("+wpers+")"
                });
             });
           
        };
        
        
        //定时调用运动函数
       t.setAnimate = setInterval(function(){fun_animat();
        if(t.closeEllipseLoading){
        	t.closeEllipseLoading=false;
        	clearInterval(t.setAnimate);
        }
        },20);
	};
	function _createEllipseDot(){
		var h = [];
		h.push('<div class="mu-ellipse-img-white ">');
		h.push('  	</div>');
		h.push('  	<div class="mu-ellipse-img-blue ">');
		h.push('  	</div>');
		h.push('  	<div class="mu-ellipse-img-red ">');
		h.push(' 	</div>');
		return h.join("");
	};
	function _createCircleBar(options){
		var total=3;
		var seconds=total*4;
		var v = Mu.getVendor?("-"+Mu.getVendor+"-"):"";
		var h=[];
		for(var i=1;i<total+1;i++){
			var deg = 30*(i-1);
			h.push("<div class='mu-loading-circleBar mu-circleBar-content"+i+"' style='"+v+"transform: rotateZ("+deg+"deg);'>");
			for(var j=1;j<5;j++){
				var delay = ((j-1)*total+(i-1)-seconds)/10;	
	 			var style;
	 			if(j%2==1){
	 				style="width:"+options.width+"px;height:"+options.height+"px;top:"+(options.size-options.height)/2+"px;";
	 			}else{
	 				style="width:"+options.height+"px;height:"+options.width+"px;left:"+(options.size-options.height)/2+"px;";
	 			}
	 			h.push("<div class='mu-circleBar"+j+"' style='"+style+v+"animation-delay:"+delay+"s;'></div>");
 				
			}
			h.push("</div>");
		}
		return h.join("");
	};
	
	function _createCircleDto(){
		var h=[];
		var total = 5,seconds= total*4;
		var v = Mu.getVendor?("-"+Mu.getVendor+"-"):"";
	 	for(var i=1;i<total+1;i++){
	 		var deg = 18*(i-1);
	 		h.push("<div class='mu-pageloading-circleDot mu-circleDot-content"+i+"' style='"+v+"transform: rotateZ("+deg+"deg);'>");
	 		for(var j=1;j<5;j++){
	 			var delay = ((j-1)*total+(i-1)-seconds)/10;	
	 				h.push("<div class='mu-circleDot"+j+"' style='"+v+"animation-delay:"+delay+"s;'></div>");
	 				
	 			}
	 		h.push("</div>");
	 	}
	 return h.join("");
	};
	Mu.loading.prototype ={
		html:function(){
			if(this.type=="circleBar"){
				return _createCircleBar(this.options);
			}else if(this.type=="circleDot"){
				return _createCircleDto();
			}else if(this.type == "ellipseDot"){
				return _createEllipseDot();
			}
		},
		show:function(jq,t){
			if(this.type=="ellipseDot"){
				_showellipseDotLoading(jq,t);
			}
		},
		hide:function(){
			if(this.type=="ellipseDot"){
				this.closeEllipseLoading=true;
			}
		}
		
			
	};
})(window,undefined);
(function($){
	function _hidden(jq){
		var loading = $(jq).data("loading");
		loading.hide();
		$(jq).next().animate({opacity:0},500,function(){
			 $(this).remove();
		 });
		 $(jq).animate({opacity:0},500,function(){
			 $(this).remove();
		 });
		 $(jq).next().addClass("fadeOut").delay(500).remove();
	};
	
	function _appendLoadingImg(t,type){
		var loading,loadingOpts={};
		var h=[];
		h.push("<div class='mu-pageloading-box'>");
		h.push("<div class='mu-pageloading-img mu-"+type+"'>");
		
			if(type=="circleBar"){
				loadingOpts = {size:60,width:16,height:4};
			}
		loading = new Mu.loading(type,loadingOpts);
		h.push(loading.html());
		h.push("</div>");
		h.push("</div>");
		$(h.join("")).appendTo(t);
		
		return loading;
	};
	function _appendMsg(t,msg){
		if(msg){
			var box = "<div class='mu-pageloading-content'>"+msg+"</div>";
			t.children().append(box);
		}
	};
	function _init(jq,opts){
		var t = $(jq);
		t.after("<div class='mu-pageloading-container'></div>");
		var _opts = parseOption(jq);
		if(opts){
			_opts = $.extend(_opts,opts);
		}
		t.data("options",_opts);
		var loading = _appendLoadingImg(t,_opts.loadingType);
		t.data("loading",loading);
		_appendMsg(t,_opts.showMsg);
		loading.show(t,loading);
		return t;
	};
	
	$.fn.pageloading=function(mtd,opts){
		if(typeof mtd =="string"){
			var m = methods[mtd];
			if(m){
				return m(this,opts);
			}
		}
	};
	var methods={
		init:function(jq,opt){
			return _init(jq,opt);
		},
		hidden:function(jq){
			_hidden(jq);
		}
	};
	var defaults={
			loadingType:'circleDot',
			showMsg:'页面载入中，请稍后...'	
	};
	function parseOption(t){
		return $.extend({},defaults,Mu.parseOption(t));
	};
	
	$(".mu-pageloading").each(function(){
		$(this).pageloading("init");
	});
})(jQuery);
/****************************
*异步交互的方法
*@author hanson
*@date 2013-07-30
*
*****************************/
/**
 * @description 设置所有按钮所在面板是否可用
 * @private
 * @param {boolean} enable
 */
Mu._enableButtonLayout = function(enable){
	if(enable){
		Mu.globvar.isSubmitNow = true;
	}else{
		Mu.globvar.isSubmitNow = false;
		Mu.hideMask();
	}
};
/*******************************
*@description 异步提交方法
*@param url 提交的路径
*@param ids 提交的表单id
*@param param 自定义参数，json格式
*@param valiudator 提交前校验函数，当返回值为真时，进入下一步
*@param isAutoValidate 是否进行自动校验 true/false
*@param cache 是否使用缓存默认true
*@param callback 后台成都执行完成后回调函数
*Mu.submit({url:'',ids:'id1,id2',param:{key1:value1,key2:value2}})
********************************/
var submitDefaultOptions = {cache:false,isAutoValidate:true};
//url,ids,param,validator,isAutoValidate,cache,callback
Mu.submit=function(options){
	//
	var opts =$.extend({},submitDefaultOptions,options);
	if(Mu.isInclude(opts.ids)){
		
		var flag  = true;
		var param = opts.param;
		if(!Mu.isEmpty(opts.validator)){
			if(typeof validator=="function"){
				flag = eval(opts.validator);
			}else{
				flag = eval(opts.validator+"()");
			}
			
		}
		if(flag&&opts.isAutoValidate){
				flag = Mu.validate(opts.ids);
			}
		if(flag){
			if(opts.msg){
				Mu.showMask({msg:opts.msg});
			}
			 var data =Mu.getObj2Json(opts.ids);
			 if(Mu.isEmpty(data)){
			 	data={};
			 }
			if(!Mu.isEmpty(param)){
				for(var key in param){
					data[key]=param[key];
				}
			}
			var _param = data;
			var url = opts.url;
			if(url.indexOf("ction")>0){
				url=Mu.globvar.host+url;
			}
			$.ajax({
			url:url,
			type:'post',
			success:function(_data,dataType){
//				console.log(_data);
				Mu._dealData(_data);
				//My.hideMask();
				if(options.success){
					options.success(_data,dataType);
				}
//				Mu._enableButtonLayout(false);
			},
			error:function(re){
				//Mu.alert({msg:"请求超时,请检查网络!",type:'error'});
//				if(failCallback){
//					failCallback(e);
//				}
				console.log(re);
				if(typeof opts.error=='function'){
					opts.error();
				}else{
//					Mu.alert({msg:"请求超时,请检查网络!",type:'error'});
				}
			},
			dataType:'json',
			data:eval("("+JSON.stringify(_param)+")")
			
			});
		}else{
			Mu.hideMask();
		}
	}
};
/*******************************
*@description 查询方法，针对datagri的查询
*@param url 提交的路径
*@param gridid grid的id
*@param submitids 提交的表单id
*@param param 自定义参数，json格式
*@param valiudator 提交前校验函数，当返回值为真时，进入下一步
*@param isAutoValidate 是否进行自动校验 true/false
*@param successcallback 后台成功执行完成后回调函数
*@param failcallback
********************************/

Mu.query4Datagrid = function(url,gridid,submitids,param,validator,isAutoValidate,successcallback,failcallback){
	if(Mu.isInclude(submitids)){
		var flag  = true;
		if(!Mu.isEmpty(validator)){
			if(typeof validator=="function"){
				flag = eval(validator);
			}else{
				flag = eval(validator+"()");
			}
			
		}
		if(flag&&isAutoValidate){
				flag = Mu.validate(submitids);
			}
		if(flag){
			
			 var data =Mu.getObj2Json(submitids);
			 if(Mu.isEmpty(data)){
			 	data={};
			 }
			if(!Mu.isEmpty(param)){
				for(var key in param){
					data[key]=param[key];
				}
			}
			var opts = $("#"+gridid).datagrid("options");
				$("#"+gridid).datagrid({
					url:url,
					queryParams:eval("("+JSON.stringify(data)+")"),
					onLoadSuccess:successcallback,
					onLoadError:failcallback
				});
			
		}else{
			Mu.hideMask();
		}
	}
}



//url,param,callback
Mu.getJson = function(options){
	//var res;
//	var param = {};
//	if(options.ids){
//		
//	}
	var result =  $.ajax({
			url:options.url,
			type:'post',
			success:function(data){
				//res = data;
				//console.log(res);
				if (typeof options.callback == "function") {
					options.callback(data);
				}
			},
				dataType:'json',
				data:options.param,
				async: false
			
			});
			return result.responseJSON;
};


Mu._fnResponse = function(response,status,request){
	
};

$.ajaxSetup({
	beforeSend:function(request){
		//console.log(request);
		Mu._enableButtonLayout(true);
		},
		timeout:12000,
//		error:function(e){
//			console.log(e);
//		},
	complete:function(request,status){
		Mu.hideMask();
		Mu._checkRequestStatus(request);
		Mu._enableButtonLayout(false);
	}
});




Mu._checkRequestStatus = function(request){
	var flag = false,
	tip = true,
	msg="";
	
	if(request.readyState == 4){
		var status = request.status;
		if(status==500){
			tip = false;
			var errmsg = request.responseText;
			Mu._dealData({'success':false,'msgTitle':'系统错误','msg':'程序执行出错！','errorDetail':errmsg});
		}
		else if(status == 999){
			msg = "由于长时间未操作,空闲会话已超时!";
	
		}else if(status == 998){
			msg = "您的会话连接由于在其它窗口上被注销而失效!";
			
		}else if(status == -1){
			msg = "请求失败,超时或服务器无响应!"
		
		}else{
			flag = true;
			tip = false;
		}
		if(!flag&&tip){
			Mu.alert({msg:msg,type:'error',fun:function(){
				parent.location.href=Mu.host+"sys/loginAction/";
				
			}});
		}
		return flag;
	}

};

var xmlHttp;
Mu._verifyRequest = function(url,callback){
	if(window.XMLHttpRequest){
		xmlHttp = new XMLHttpRequest();
	}else if(window.ActiveXObject){
		 //针对ie6以下版本  
                var activexName = ["MSXML2.XMLHTTP","Microsoft.XMLHTTP"];  
                //两个可以用来创建XMLHTTPRequest对象的控件，进行尝试创建  
                for(var i=0; i < activexName.length; i++){  
                    try{  
                        xmlHttp = new ActiveXObject(activexName[i]);  
                        break;  
                    }catch(e){  
                          
                    }  
                }  
	}
	
	//最后 确认xmlhttprequest对象创建成功  
            if(!xmlHttp){  
                Mu.alert({msg:"请更换更新版本的浏览器"});  
                return;  
            }else{  
                //2.注册回调函数,只需要函数名不需要加括号  
                xmlHttp.onreadystatechange =callback;  
                //3.设置连接信息  
                xmlHttp.open("POST",url, true); 
              
                //4.发送数据，开始和服务器端进行交互  
                xmlHttp.send(null);  
                  
            }  
};

Mu._dealData = function(data){
	if(typeof data !="object")return;
	if(data.fieldData){
		Mu.setData(data.fieldData);
	}
	/*
		//有validateErrors数据
		if(data.validateErrors){
			var focus = null,_errors = data.validateErrors;
			for(var fieldId in _errors){
				if(!focus)
					focus = fieldId;
				Base.setInvalidField(fieldId,_errors[fieldId]);
			}
			//如果后台没有设置focus，并且有validateErrors数据的时候就把焦点置于第一个错误的地方
			if(focus && !data.focus){
				data.focus = focus;
			}
		}
		*/
		//list
		if(data.lists){
			var _list = data.lists;
			for(var list in _lists){
				//My._setGridData(list,_lists[list]);
			}
		}
		//operation
		if(data.operation){
			var _operation = data.operation;
			for(var i=0;i<_operation.length;i++){
				var op = _operation[i];
				switch(op.type){
					case 'readonly':
						Mu.setReadOnly(op.ids);
						break;
					case 'enable':
						Mu.setEnable(op.ids);
						break;
					case 'disabled':
						Mu.setDisabled(op.ids);
						break;					
					case 'select_tab':
						Mu.selectTab(op.ids);
						break;				
					case 'hide':
						Mu.hideObj(op.ids);
						break;
					case 'show':
						Mu.showObj(op.ids);
						break;
					case 'unvisible':
						Mu.hideObj(op.ids,true);
						break;					
					case 'resetForm':
						Mu.resetForm(op.ids[0]);
						break;
					case 'required':
						Mu.setRequired(op.ids);
						break;
					case 'disrequired':
						Mu.setDisRequired(op.ids);
						break;		
				}
			}
		}
		//
		//有msg
		if(data.msg){
			var focus = null;
			if(data.focus){
				focus = function(_fieldId){
					return function(){Mu.focus(_fieldId,100);}
				}(data.focus);
			}
			var _msg = data.msg;
			if(data.errorDetail){
				_msg += "&nbsp;&nbsp;&nbsp;<div><a onClick=\"top.$('<div style=overflow:auto>'+$('#_expwinerrmsg').html()+'</div>').appendTo('body').window({width:600,height:400,title:'详细信息(如果本人非开发人员，请将本页信息反馈给开发人员)',modal:true,zIndex:99999999})\">[查看详细]</a></div><div id='_expwinerrmsg' style='display:none'><hr>"+data.errorDetail+"</div>";
			}
			Mu.alert({msg:_msg,type:data.success?'ok':'error'});
			
		}
		if (data.msgBox) {
			var focus = null;
			if(data.focus){
				focus = function(_fieldId){
					return function(){Base.focus(_fieldId,100);}
				}(data.focus);
			}
			var msg = data.msgBox.msg;
			if(data.errorDetail){
				msg += "&nbsp;&nbsp;&nbsp;<div><a onClick=\"$('<div style=overflow:auto>'+$('#_expwinerrmsg').html()+'</div>').appendTo('body').window({width:600,zIndex:99999999,height:400,title:'详细信息',modal:true})\">[查看详细]</a></div><div id='_expwinerrmsg' style='display:none'><hr>"+data.errorDetail+"</div>";
			}
			Mu.alert(data.msgTitle,data.msg,data.msgBox.msgType,focus);
		}
		//没有msg，但是有focus
		if(!data.msg && data.focus){
			Mu.focus(data.focus,50);
		}
};


/***************************
*与窗口有关的方法
*hanson
*
*****************************/
/**
 * @description 弹出提示窗口
 * @param {String} title 标题
 * @param {String} msg 内容
 * @param {String} type 样式 ，info:消息，warn：警告，success：成功,error：失败，question：疑问等
 * @param {String} fun 点击按钮后触发的自定义函数，可为null
 */
Mu.alert=function(){
	var len  = arguments.length;
	if(len ==1){
		var o = arguments[0];
		if (typeof o=='string'){
			$.messager.alert('提示',arguments[0]);
		}else{
			$.messager.alert(o.msgTitle?o.msgTitle:'提示',o.msg,o.type,o.fun);
		}
	}else{
		
		switch (len){
//		case 1:$.messager.alert('提示',arguments[0]);
//		
//		break;
		case 2:$.messager.alert(arguments[0],arguments[1]);
		break;
		case 3:$.messager.alert(arguments[0],arguments[1],arguments[2]);
		break;
		case 4:$.messager.alert(arguments[0],arguments[1],arguments[2],arguments[3]);
		break;
		}
	}

}

/************************************
*@description 确认对话框
*@param title 标题
*@param msg   内容
*@param fun 点击按钮后触发的事件
*************************************/
Mu.confirm=function(){
	var len  = arguments.length;
	switch (len){
		case 2:$.messager.confirm('确认提示',arguments[0],arguments[1]);
		break;
		case 3:$.messager.confirm(arguments[0],arguments[1],arguments[2]);
		break;
	}
}
/**
 * @description 打开窗口
 * @param {String} id 窗口id
 * @param {String} title 窗口标题
 * @param {String} url aciton地址
 * @param {map} param 入参 json格式对象，例如:{"dto['aac001']":"1000001","dto['aac002']":"01"}
 * @param {Number} width 宽度 不要加 px；也可设置成百分比，例如"80%"
 * @param {Number} height 高度 不要加 px；也可设置成百分比，例如"80%"
 * @param {Function} onLoad 窗口加载完毕回调，如果useIframe=true的话 这参数不起作用
 * @param {Funciton} onClose 窗口关闭的时候回调
 * @param {Boolean} isIframe 是否使用iframe的方式加载，默认为false，iframe方式会存在seesion丢失，应当避免;为true的时候，打开页面为一个完整的jsp页面
 * @param {Object} otherOptions window的创建参数
 */
Mu.openWindow=function(winId,title,url,param,width,height,onLoad,onClose,isIframe,otherOptions){
	if(!$("#"+winId).length){
		var windowDiv = "<div id='"+winId+"'></div>";
		$("body").append($(windowDiv));
	}
	var options={};
	title ? (options.title = title):null;
	width ? (options.width = width):(options.width = 200);
	height ? (options.height = height):(options.height = 200);
	onLoad ? (options.onLoad = onLoad):null;
	options.modal=true;//设置蒙层，使父页面不可操作
	options.minimizable = false;//取消最小化按钮
	if(param){
		if(url.indexOf('?') != -1){
			url += "&"+jQuery.param(param);
		}else{
			url += "?"+jQuery.param(param);
		}
	}
	if(url.indexOf('?') != -1){
		url += "&_r="+Math.random();
	}else{
		url += "?_r="+Math.random();
	}
	if(url && isIframe){
		options.content = '<iframe src="'+url+'" frameborder="0" style="width:100%;height:100%"></iframe>';
	}else{
		url ? (options.href = url):null;
	}
		options.onClose =function(_onClose){
		return function(){
			if(_onClose)
				_onClose(winId);
		}
	}(onClose);
	if(otherOptions)
		$.extend(options,otherOptions);
	$("#"+winId).window(options);
	if(!url){
		onLoad();
	}
}
Mu.closeWindow = function(winId){
	$("#"+winId).window("close");
}


/*******************************
 * @description 赋值
 * @param {String} id 标签对象的id
 * @param {String} value 值
 *********************************/
Mu.setValue=function(id,value){
	var obj = $("#"+id);
	if(typeof obj !="undefined"){
		if(obj.hasClass("easyui-combobox")){//下拉框
			obj.combobox('setValue', value);
		}else if(obj.hasClass("easyui-numberbox")){
			return obj.numberbox('setValue',value);
		}else if(obj.hasClass("easyui-datebox")){
			return obj.datebox('setValue',value.dateStrFormat("yyyy-MM-dd"));
		}else if(obj.hasClass("easyui-datetimebox")){
			if(value){
				
				return obj.datetimebox('setValue',value.dateStrFormat("yyyy-MM-dd hh:mm:ss"));
			}else{
				obj.datetimebox('setValue','');
			}
		}else if(obj.hasClass("easyui-combotree")){
			return obj.combotree('setValue',value);
		}else if(obj.hasClass("easyui-checkboxgroup")){
			return obj.checkboxgroup('setValue',value);
		}else if(obj.hasClass("easyui-filebox")){
			obj.filebox('setValue',value);
		}else if(obj.hasClass("easyui-textbox")){
			return obj.textbox('setValue',value);
		}else{
			obj.val(value);
		}
	}
}
/*******************************
 * @description 取值
 * @param {String} id 标签对象的id
 * @return {String} value 值
 *********************************/
Mu.getValue=function(id){
	var obj = Mu.getObj(id);
	if(obj.hasClass("easyui-combobox")){
		return obj.combobox('getValue');
	}else if(obj.hasClass("easyui-numberbox")){
		return obj.numberbox('getValue');
	}else if(obj.hasClass("easyui-combotree")){
		return obj.combotree('getValue');
	}else if(obj.hasClass("easyui-datebox")){
		return obj.datebox('getValue');
	}else if(obj.hasClass("easyui-datetimebox")){
		return obj.datetimebox('getValue');
	}else if(obj.hasClass("easyui-textbox")){
		return obj.textbox('getValue');
	}
	else{
		var re = obj.val();
		if(obj.hasClass("easyui-validatebox")){
			var opts = obj.validatebox("options");
			if(opts.hasInnerText){
				var innerText = obj.attr("innerText");
				if(re == innerText){
				re = '';
				}
			}
			
		}
		return re;
	}
}
/*******************************
 * @description 将焦点放置于元素上
 *ps:仅支持text，number、data、textarea,combobox、
 *按钮
 * @param {String} id 标签对象的id
 * @param {number} delay 延迟毫秒数
 *********************************/
Mu.focus=function(id,delay){
	var obj = Mu._getObj(id);
	if(!obj)return;
	if(obj.hasClass("easyui-textbox"))
		obj = obj.next().children("input:visible");
	if(delay){
		obj.delay(delay).focus();
	}else{
		obj.focus();
	}
}
/*******************************
 * @description 隐藏对象
 * ps:对于隐藏对象，但仍保持其的占位情况，在实际应用中控制
 * @param {String} ids 标签对象的ids,多个id以逗号隔开
 * 各个元素之间不能嵌套
*********************************/
Mu.hideObj=function(ids){
	if(Mu.isEmpty(ids)){
		return;
	}else{
		var _ids = ids;
		if(typeof ids =="string"){
			_ids = ids.split(","); 
		}
		for(var i in _ids){
			var obj = Mu.getObj(_ids[i]);
			if(obj.is("input")){
			//	obj.css("display","none").parent().parent().parent().css("display","none");	
				obj.parent().parent().hide();
			}else{
				obj.hide()
			}
			
		}
	}
}
/*******************************
 * @description 显示对象
 * @param {String} ids 标签对象的ids,多个id以逗号隔开
 * 各个元素之间不能嵌套
*********************************/
Mu.showObj=function(ids){
	if(Mu.isEmpty(ids)){
		return;
	}else{
		var _ids = ids;
		if(typeof ids =="string"){
			_ids = ids.split(","); 
		}
		for(var i in _ids){
			var obj = Mu.getObj(_ids[i]);
			if(obj.is("input")){
				obj.parent().parent().show();	
			}else{
				obj.show();
			}
			
		}
	}
}
/****************************
*@description 对表单赋值
*@param data{map} json格式
****************************/
Mu.setData = function(data){

	for(var key in data){
		
		
		Mu.setValue(key,data[key]);
	}
	
}
/*******************************
 * @description 使表单、按钮只读
 * @param {String} ids 标签对象的ids,多个id以逗号隔开
 * 各个元素之间不能嵌套
*********************************/
Mu.readonly=function(ids){
	if(Mu.isEmpty(ids)){
		return;
	}else{
		var _ids = ids;
		if(typeof ids =="string"){
			_ids = ids.split(","); 
		}
		for(var i in _ids){
			var obj = Mu.getObj(_ids[i]);
			//obj.css("display","block");
			if(obj.is("div")){
				//如果是层级元素，则遍历其下的所有表单、按钮
			var _item= obj.find("input[id],textarea[id],a[id][class *= '1-btn']");
			_item.each(function(){
				Mu._setReadonly($(this).attr("id"));
			});
			}else{
				Mu._setReadonly(_ids[i]);
			}
		}
	}
}
/****************************
*@description 使表单、按钮只读
* @param {String} id 标签对象的id,
****************************/
Mu._setReadonly=function(id){
	if(Mu.isEmpty(id)){
		return;
	}else{
		var obj = Mu.getObj(id);
		if(obj.hasClass("easyui-validatebox")){
			var opt = obj.validatebox("options");
			opt.readonly = true;
		}else if(obj.hasClass("easyui-combobox")){
			var opt = obj.combobox("options");
			opt.readonly = true;
		}else if(obj.hasClass("easyui-numberbox")){
			var opt = obj.numberbox("options");
			opt.readonly = true;
		}else if(obj.hasClass("easyui-datebox")){
			var opt = obj.datebox("options");
			opt.readonly = true;
		}else if(obj.hasClass("easyui-datetimebox")){
			var opt = obj.datetimebox("options");
			opt.readonly = true;
		}else if(obj.hasClass("1-btn")){
			var opt = obj.linkbutton("options");
			opt.readonly = true;
			var hotkey = obj.attr("hotkey");
			if(hotkey&&hotKeyregister){
				
					hotKeyregister.remove(hotkey);
				
			}
			//obj.attr("disabled","disabled");
		}else if(obj.hasClass("easyui-textarea")){
			var opt = obj.textarea("options");
			opt.readonly = true;
		}else if(obj.hasClass("easyui-checkbox")){
			//var opt = obj.checkbox("options");
			//opt.readonly = true;
		}
		
			Mu._getObj(id).attr("readonly","readonly");
			obj.attr("readonly","readonly");
		
	}
}
/*******************************
 * @description 使表单、按钮可用
 * @param {String} ids 标签对象的ids,多个id以逗号隔开
 * 各个元素之间不能嵌套
*********************************/
Mu.enable=function(ids){
	if(Mu.isEmpty(ids)){
		return;
	}else{
		var _ids = ids;
		if(typeof ids =="string"){
			_ids = ids.split(","); 
		}
		for(var i in _ids){
			var obj = Mu.getObj(_ids[i]);
			//obj.css("display","block");
			if(obj.is("div")){
				//如果是层级元素，则遍历其下的所有表单、按钮
			var _item= obj.find("input[id],textarea[id],a[id][class *= '1-btn']");
			_item.each(function(){
				Mu._setEnable($(this).attr("id"),true);
			});
			}else{
				Mu._setEnable(_ids[i],true);
			}
		}
	}
}
/*******************************
 * @description 使表单、按钮不可用
 * @param {String} ids 标签对象的ids,多个id以逗号隔开
 * 各个元素之间不能嵌套
*********************************/
Mu.disabled=function(ids){
	if(Mu.isEmpty(ids)){
		return;
	}else{
		var _ids = ids;
		if(typeof ids =="string"){
			_ids = ids.split(","); 
		}
		for(var i in _ids){
			var obj = Mu.getObj(_ids[i]);
			//obj.css("display","block");
			if(obj.is("div")){
				//如果是层级元素，则遍历其下的所有表单、按钮
			var _item= obj.find("input[id],textarea[id],a[id][class *= '1-btn']");
			_item.each(function(){
				Mu._setEnable($(this).attr("id"),false);
			});
			}else{
				Mu._setEnable(_ids[i],false);
			}
		}
	}
}

/****************************
*@description 使表单、按钮可用或不可用
* @param {String} id 标签对象的id,
* @param {boolean} enable: true 启用，false 
****************************/
Mu._setEnable=function(id,enable){
	if(Mu.isEmpty(id)){
		return;
	}else{
		var obj = Mu.getObj(id);
		if(obj.hasClass("easyui-validatebox")){
			var opt = obj.validatebox("options");
			opt.disabled = enable;
		}else if(obj.hasClass("easyui-combobox")){
			var opt = obj.combobox("options");
			opt.disabled = enable;
		}else if(obj.hasClass("easyui-numberbox")){
			var opt = obj.numberbox("options");
			opt.disabled = enable;
		}else if(obj.hasClass("easyui-datebox")){
			var opt = obj.date("options");
			opt.disabled = enable;
		}else if(obj.hasClass("easyui-datetimebox")){
			var opt = obj.datetime("options");
			opt.disabled = enable;
		}else if(obj.hasClass("1-btn")){
			var opt = obj.linkbutton("options");
			opt.disabled = enable;
			var hotkey = obj.attr("hotkey");
			if(hotkey&&hotKeyregister){
				if(enable){
					hotKeyregister.add(hotkey,function(){obj.click();obj.focus();return false;});
				}else{
					hotKeyregister.remove(hotkey);
				}
			}
			//obj.attr("disabled","disabled");
		}else if(obj.hasClass("easyui-textarea")){
			var opt = obj.textarea("options");
			opt.disabled = enable;
		}else if(obj.hasClass("easyui-checkbox")){
			//var opt = obj.checkbox("options");
			//opt.disabled = enable;
		}
		if(enable){	
			obj.removeAttr("disabled")
			Mu._getObj(id).removeAttr("disabled");
			Mu._getObj(id).removeAttr("readonly");
			obj.removeAttr("readonly");
		}else{
			Mu._getObj(id).attr("disabled","disabled");
			obj.attr("disabled","disabled");
		}
	}
}
/*******************************
 * @description 使表单必输
 * @param {String} ids 标签对象的ids,多个id以逗号隔开
 * 各个元素之间不能嵌套
*********************************/
Mu.required=function(ids,required){
	if(Mu.isEmpty(ids)){
		return;
	}else{
		var _ids = ids;
		if(typeof ids =="string"){
			_ids = ids.split(","); 
		}
		for(var i in _ids){
			var obj = Mu.getObj(_ids[i]);
			if(obj.is("div")){
				//如果是层级元素，则遍历其下的所有表单
			var _item= obj.find("input[id],textarea[id]");
			_item.each(function(){
				Mu._setRequired($(this).attr("id"),required);
			});
			}else{
				Mu._setRequired(_ids[i],required);
			}
		}
	}
}
/****************************
*@description 使表单为必录项或取消必录
* @param {String} id 标签对象的id,
* @param {boolean} required: true 启用，false 
****************************/
Mu._setRequired=function(id,required){
	if(Mu.isEmpty(id)){
		return;
	}else{
		var obj = Mu.getObj(id);
		if(obj.hasClass("easyui-validatebox")){
			var opt = obj.validatebox("options");
			opt.required = required;
		}else if(obj.hasClass("easyui-combobox")){
			var opt = obj.combobox("options");
			opt.required = required;
		}else if(obj.hasClass("easyui-numberbox")){
			var opt = obj.numberbox("options");
			opt.required = required;
		}else if(obj.hasClass("easyui-datebox")){
			var opt = obj.date("options");
			opt.required = required;
		}else if(obj.hasClass("easyui-datetimebox")){
			var opt = obj.datetime("options");
			opt.required = required;
		}else if(obj.hasClass("easyui-textarea")){
			var opt = obj.datetime("options");
			opt.required = required;
		}
		var label = obj.parent().prev().find("label");
		var redStar = "<span style='color:red'>*</span>";
		var labelText = label.text();
		if(required){
			//加红星
			if(labelText.indexOf('*')<0){
				label.empty().append(redStar+labelText.trim());
			}
		}else{
			if(labelText.indexOf('*')>-1){
				//label.empty().append(labelText.trim());
				$(">span:first",label).remove();
			}
		}
		
	}
}
/****************************
*@description 清空表单值，不包含对datagrid的清除
*@param ids{String} 对象id窜，以逗号分隔
****************************/
Mu.clearData = function(ids){
if(Mu.isEmpty(ids)){
		
}else{
	var _ids= ids.split(",");
	//var key = null;
	for(var i in _ids){
		var obj = Mu.getObj(_ids[i]);
		if(obj.is("div")&&!(obj.hasClass("datagrid"))){
		//console.log(obj);
			var _input= obj.find("input[id],textarea[id]");
			_input.each(function(){
				if($(this).hasClass("easyui-checkbox")){
					$(this).prop("checked",false);
		 			$(this).attr("checked",false);
				}else{
					Mu.setValue($(this).attr("id"),'');
				}
			});
		}else if((obj.is("input")||obj.is("textarea"))&&(obj.attr("disabled")!="disabled")){
			Mu.setValue(obj.attr("id"),'');
		}
	}
	//console.log(JSON.stringify(return_));
	}
}
/****************************
*@description 清空表单值，不包含对datagrid的清除
*@param ids{String} 对象id窜，以逗号分隔
****************************/

Mu.clearDataFilter=function (ids,nids){
	if(Mu.isEmpty(ids)){
		
	}else{
		var _ids= ids.split(","),
		_nids =nids.split(",") ;
		
		for(var i in _ids){
			var obj = Mu.getObj(_ids[i]);
			if(obj.is("div")&&!(obj.hasClass("datagrid"))){
			
				var _input= obj.find("input[id],textarea[id]");
				
				_input.each(function(){
					var t= $(this);
					if(_nids.indexOf(t.attr("id"))<0){
						if(t.hasClass("mu-checkbox")){
							t.prop("checked",false);
				 			t.attr("checked",false);
						}else{
							Mu.setValue(t.attr("id"),'');
						}
					}
					
				});
			}else if((obj.is("input")||obj.is("textarea"))&&(obj.attr("disabled")!="disabled")){
				Mu.setValue(obj.attr("id"),'');
			}
		}
		//console.log(JSON.stringify(return_));
		}
}
/****************************
* 获取对象的值
****************************/
Mu.getValue8Obj=function(obj){
	
	if(obj.hasClass("easyui-combobox")){
		return obj.combobox('getValue');
	}else if(obj.hasClass("easyui-numberbox")){
		return obj.numberbox('getValue');
	}else if(obj.hasClass("easyui-combotree")){
		return obj.combotree('getValue');
	}else if(obj.hasClass("easyui-datebox")){
		return obj.datebox('getValue');
	}else if(obj.hasClass("easyui-datetimebox")){
		return obj.datetimebox('getValue');
	}else if(obj.hasClass("easyui-checkboxgroup")){
		return obj.checkboxgroup("getChecked");
	}else{
		return obj.val();
	}
}
/******************************
*@description 根据元素id获取对象,实际的对象
*@param id{string} 元素id
*@return obj{object} 元素对象
******************************/
Mu.getObj = function(id){
	return $("#"+id);
}
/******************************
*@description 根据元素id获取对象,显示的对象，主要针对combo,date,datetime,number型表单
*@param id{string} 元素id
*@return obj{object} 元素对象
******************************/
Mu._getObj = function(id){
	var obj = Mu.getObj(id);
	if(obj.hasClass("combo-f")){
		return obj.next().find("input :eq(0)");
	}else{
		return obj;
	}
}
/******************************
*将表单内容按json格式返回
*ids 以逗号分隔
******************************/
Mu.getObj2Json=function(ids){

var return_={};
if(Mu.isEmpty(ids)){
		
}else{
	var _ids= ids.split(",");
	var key = null;
	for(var i in _ids){
		var obj = Mu.getObj(_ids[i]);
		if(obj.is("div")&&!(obj.hasClass("datagrid"))){

			var _input= obj.find("input[disabled!='disabled'][id]:not([type='checkbox'],[type='radio']),textarea[disabled!='disabled'][id],div[class*='easyui-checkboxgroup'][disabled!='disabled'][id],div[class*='easyui-radiogroup'][disabled!='disabled'][id]");
			for(var j = 0;j<_input.length;j++){
		//	console.log(_input.eq(j));
				/*if(_input.eq(j).hasClass("easyui-numberbox")){
					key = _input.eq(j).attr("numberboxname");
				}else if(_input.eq(j).hasClass("easyui-combobox")){
					key = _input.eq(j).attr("comboname");
				}else{
					key=_input.eq(j).attr("name");
				}*/
				key = _input.eq(j).attr("id");
				return_[key]=Mu.getValue8Obj(_input.eq(j));
			}
		}else if((obj.is("input")||obj.is("textarea"))&&(obj.attr("disabled")!="disabled")){
			key = obj.attr("id");
			return_[key] = Mu.getValue8Obj(obj);
		}else if(obj.hasClass("easyui-datagrid")){
				var pager = obj.datagrid("options");
				console.log(pager);
				return_['page']=  pager.pageNumber;
				return_['rows'] = pager.pageSize;
		}
	}
	//console.log(JSON.stringify(return_));
	}
	return return_;
}
/**
 * @description 获取当前输入对象的后面一个输入对象或按钮
 * @param {String} curid 当前id
 * @return {Object} 后一个组件对象
 */
Mu._getNextObj=function(curid){
	if(Mu.isEmpty(curid)){
		return;
	}
	var ids = [];
	$("body").find("input[id],a[id],textarea[id]").each(function(){
		ids.push($(this).attr("id"));
	});
	if(typeof curid =="object")
		curid = curid[0].id;
	var next = null;
	for(var i = 0;i<ids.length;i++){
		if(ids[i]==curid){
			var m=i+1;
			if(i==(ids.length-1)){//当前对象恰好在最后
				m=0;
			}
			for(;m<ids.length;m++){
				var obj  = Mu.getObj(ids[m]);
				if(!obj.disabled&&!obj.readonly){
					next = obj;
					break;
				}
			}
			
	}
}
	return next;
}
/****************************************
* 在query 组件中控制addtion的显示与隐藏
* @param obj 当前按钮
* @param basicId basic 的id,用于查找addtion
****************************************/
Mu._toggleAddtion = function(obj,qid){
	var q  = Mu.getObj(qid),
	basic = $(obj),
	addtion = q.children(":last"),
	offset = 0;
	if(addtion.is(":hidden")){
		basic.removeClass("icon-arrow-a3-down").addClass("icon-arrow-a3-up");
		addtion.css("display","block");
		 offset -= addtion.height();
	}else{
		basic.removeClass("icon-arrow-a3-up").addClass("icon-arrow-a3-down");
		offset =addtion.height(); 
		addtion.css("display","none");
	}
	var p = q.parent();
	console.log(p);
	if(p[0].tagName=="BODY"){
		Mu._resetDatagridHeight2Fit(offset);
	}//else if(p.hasClass(""))
	
	
}
Mu._resetDatagridHeight2Fit=function(offset){
	var datagrid = $(".easyui-datagrid");
	datagrid.each(function(){
		var panel = $.data(this,"datagrid").panel;
		var old_panel = panel.height();
		panel.css("height",old_panel+offset);
		var old_body = panel.find(".datagrid-body").height();
		
		panel.find(".datagrid-body").css("height",old_body+offset);
		var old_view = panel.find(".datagrid-view").height();
		panel.find(".datagrid-view").css("height",old_view+offset);
	});
}
/******************************
*判断当前是否处于开发模式
*******************************/
Mu.isDevelopModel = function(){
	var user = username;
	var runModel = Mu.globvar.developMode;
	if(user == "developer"&&runModel=="1"){
		return true;
	}
	return false;
}

/******************************
*判断ids之间是否存在包含关系
*ids 以逗号分隔
******************************/
Mu.isInclude = function(ids){
var tips="";
if(!Mu.isEmpty(ids)){
	var _ids = ids.split(",");
	if(_ids.length>1){
		for(var i = 0;i<_ids.length;i++){
			var obj_i = Mu.getObj(_ids[i]);
			if(obj_i.is("div")&&!(obj_i.hasClass("datagrid"))){
				for(var j=0;j<_ids.length;j++){
					if(i!=j){
						var obj_j= Mu.getObj(_ids[j]);
						var _obj_j = obj_i.find("#"+obj_j.attr("id"));
						if( _obj_j.attr("id")){
							
							tips +="<li>id为["+obj_i.attr("id")+"]的层级元素包含了id为["+obj_j.attr("id")+"]的元素</li>";
						}
					}
				}
			}
		}
		if(Mu.isEmpty(tips)){
			return true;
		}else{
			Mu.alert('id重复',tips,'error');
			return false;
		}
	}else{
		return true;
	}
	
  }else{
  	return true;
  }
}

/******************************************
*表单自动校验
******************************************/
Mu.validate = function(ids){
	if(!Mu.isEmpty(ids)){
		var _ids = ids.split(",");
	if(_ids.length>0){
		var flag = true;
		for(var i = 0;i<_ids.length;i++){
			var obj_i = Mu.getObj(_ids[i]);
			if(obj_i.is("div")&&!(obj_i.hasClass("datagrid"))){
				//var _input= 
				if(obj_i.hasClass("easyui-checkboxgroup")){
					if(!Mu.validateObj(obj_j)){
						flag = false;
					}
				}else{
					obj_i.find("input[disabled!='disabled'][id],textarea[disabled!='disabled'][id],div[class*='easyui-checkboxgroup'][disabled!='disabled']").each(function(){
						var obj_j = $(this);
						if(!Mu.validateObj(obj_j)){
							flag = false;
						}
						
					});
				}
				
			}else if((obj_i.is("input")||obj_i.is("textarea"))&&(obj_i.attr("disabled")!="disabled")){
				if(!Mu.validateObj(obj_i)){
						flag = false;
					}
			}
		}
		return flag;
		
	}else{
		return true;
	}
	
  }else{
  	return true;
  }
	
}
Mu.validateObj=function(obj){
	var flag = true;
	if(obj.hasClass("easyui-textbox")){
		flag = obj.textbox("isValid");
	}else if(obj.hasClass("easyui-validatebox")){
		flag = obj.validatebox("isValid");
	}else if(obj.hasClass("easyui-checkboxgroup")){
		flag = obj.checkboxgroup("isValid");
	}else if(obj.hasClass("easyui-textarea")){
		flag = obj.textarea("isValid");
	}else if(obj.hasClass("easyui-numberbox")){
		flag = obj.numberbox("isValid");
	}else if(obj.hasClass("easyui-combobox")){
		flag = obj.combobox("isValid");
	}else if(obj.hasClass("easyui-datebox")){
		flag = obj.datebox("isValid");
	}else if(obj.hasClass("easyui-datetimebox")){
		flag = obj.datetimebox("isValid");
	}
	//else{
	//	flag = false;
		//My.alert('提示','未知的表单类型','error');
	//}
	return flag;
	
}


Mu.getTabIndex = function(tabId){
	//var index = 1;
	
	var index =  Mu.getObj(tabId).parent().prevAll().length;
	if(index ==0){
		index=1;
	}
	return index;
}
/*********************************
*@description 指定的tab可用
*@param tabsId 指定tab的父级容器id
*@param tabId 指定的tab的id
**********************************/
Mu.activeTab = function(tabsId,tabId){
	var tab = Mu.getObj(tabId);
	if(Mu.isEmpty(tab)){
		Mu.alert("id为"+tabId+"的tab不存在");
		return;
	}else{
		var index = Mu.getTabIndex(tabId);
		Mu.getObj(tabsId).tabs("select",index);
	}
}



/****************************
*对datagrid 的一些处理
*@author hanson
*@date 2013-07-30
*
*****************************/
/*******************************
*@description 对datagrid 赋值
*@param gridId 被赋值的grid对象的id，便于获取对象
*@param data {list.json} 值内容
*@param pager {map.json}关于分页的参数
********************************/
Mu.setDatagridList=function(gridId,data,pager){
	var obj = Mu.getObj(gridId);
	//alert('111');
	console.log(data);
	obj.datagrid("loadData",data);
}
/*******************************
*@description 获得datagrid 的所有行
*@param gridId grid对象的id，便于获取对象
*@return 返回当前页的所有行
********************************/
Mu.getDatagridRows = function(gridId){
	var obj = Mu.getObj(gridId);
	return obj.datagrid("getRows");
}
/*******************************
*@description 获得datagrid 的所有选中的行
*@param gridId grid对象的id，便于获取对象
*@return 返回当前页的所有选中行
********************************/
Mu.getDatagridSelectedRows = function(gridId){
	var obj = Mu.getObj(gridId);
	return obj.datagrid("getSelections");
}
Mu._initData = function (ids,datas){
	for(var i in ids){
		var id = ids[i],
		data = Mu.parseString2Json(datas[i]),
		obj = Mu.getObj(id);
		if(obj.hasClass("easyui-combotree")){
			obj.combotree("loadData",data);
		}else if(obj.hasClass("easyui-tree")){
			obj.tree("loadData",data);
		}
	}
	
}

Mu.showTip=function(msg,sec){
	$.messager.show({msg:msg,
		timeout:sec?sec:3,
		showType:'fade',
		style:{
            right:'',
            top:document.body.scrollTop+document.documentElement.scrollTop,
            bottom:''
        }});
}