<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>BOCUT-接口文档</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery-3.1.0.min.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/my.full.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jsonFormat.js"></script>


<style type="text/css">

.api {
	font-size: 24px;
	background-color: #F0F8FF;
	border: 1px solid #ddd;
	border-radius: 8px;
}

.reqMethod {
	padding-left: 12px;
	border: 1px solid #ddd;
	border-radius: 8px;
	background-color: #87CEFA;
	width: 90px;
}

.apiTitle {
	font-size: 24px;
	font-weight: bold;
	margin-right: 20px;
}

.text1 {
	font-size: 24px;
	font-weight: bold;
}
#sidebar_out{
	position: fixed;
	width: 20%;
	height: 100%;
	min-height:500px;
	overflow: hidden;
}
#sidebar{
	
	padding-right:40px;
	width: 110%;
	height: 100%;
	min-height:500px;
	overflow-y:auto;
} 
.table tr>td:nth-child(6){
    max-width: 300px !important;
    overflow: hidden;
}
</style>
</head>
<body>
			<!-- 生成侧边栏 -->
			<div id="sidebar_out">
				<div id="sidebar" >
					<ul class="list-group">
						<a href="#"  class="list-group-item active" style="margin-top:80px;">接口文档</a>
						<c:forEach items="${list }" var="map" varStatus="status">
							<a href="#bookmark${status.index}" class="list-group-item ">${map['testConfig'].no }${map['testConfig'].title }
								<c:choose>
									<c:when test="${map['testConfig'].modifyFlag == 'normal'}"></c:when>
									
									<c:otherwise>
									
								<span class="badge" style="background-color: #DC143C;">${map['testConfig'].modifyFlag }</span>
									</c:otherwise>								
								</c:choose>
							</a>						
						</c:forEach>						
					</ul>
				</div>
			</div>


	<div class="container-fluid">
	
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">

			<div class="navbar-header">
				<a href="<%=path %>/" class="navbar-brand">接口文档</a>
			</div>
			<a href="testAll" class="btn btn-default navbar-right btn-warning" style="margin-right: 20px;margin-top: 8px;">全局测试</a>
			

		</nav>
		
		<div style="height: 80px;"></div>


		<div class="row">
			<div class="col-xs-2 col-xs-offset-1" >
				<div style="height: 20px;"></div>
				




			</div>
			<div class="col-xs-8">
				<c:forEach items="${list }" var="map" varStatus="status">
		
					<a name="bookmark${status.index}"></a>
					<div style="height:60px;"></div>
					<div class="row">
						<div class="col-xs-12">
							<span class="apiTitle">${map['testConfig'].no }${map['testConfig'].title }</span>  
							<%-- <span class="badge" >${map['configuration'].mess }</span> --%>
						</div>

				</div>
					<div class="row api">
						<div class="col-xs-2" style="padding: 0">
							<div class="reqMethod">${map['testConfig'].method }</div>
						</div>
						<div class="col-xs-6">${map['testConfig'].url }</div>
						<div class="col-xs-2">
							<c:if test="${map['testConfig'].testCases[0].judge  }">
								人工
							</c:if>
							<c:if test="${!map['testConfig'].testCases[0].judge  }">
								自动
							</c:if>

						</div>
						

						<div class="col-xs-1">
							<c:if test="${!map['testConfig'].testCases[0].judge  }">
								<button class="btn btn-default btn-info" onclick="toTestSingle(&quot;${map['beanName']}&quot;)" />测试</button>
							</c:if>

						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<table class="table table-striped table-hover">
								<thead>
									<tr>
										<th>请求参数</th>
										<th>变量名</th>
										<th>类型</th>
										<th>必填</th>
										<th>描述</th>
										<th>测试值</th>
									</tr>
								</thead>


								<tbody>

									<c:forEach items="${map['testConfig'].request}"
										var="reqParam">
											
											<tr 
											<c:if test="${reqParam.addtion}">
												style="color:red;"
											</c:if>
											>
											
											<td>
												<c:if test="${reqParam.deprecated}"><del></c:if>
												${reqParam.name }
												<c:if test="${reqParam.deprecated}"></del></c:if>		
											</td>
											<td>
												<c:if test="${reqParam.deprecated}"><del></c:if>
												${reqParam.key }
												<c:if test="${reqParam.deprecated}"></del></c:if>		
											</td>
											<td>
											<c:if test="${reqParam.deprecated}"><del></c:if>
											${reqParam.type }
											
											</td>
											<td>
											<c:if test="${reqParam.deprecated}"><del></c:if>
											${!reqParam.allowEmpty }
											<c:if test="${reqParam.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${reqParam.deprecated}"><del></c:if>
											${reqParam.desc }
											<c:if test="${reqParam.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${reqParam.deprecated}"><del></c:if>
											${reqParam.value }
											<c:if test="${reqParam.deprecated}"></del></c:if>
											</td>
											
											
									<td>
											</tr>
											
											<c:if test = "${not empty reqParam.children }">
											
										
										<tr><td colspan="6" style="text-align: center;">以下参数包含在【${reqParam.name }】中</td></tr>
												<c:forEach items="${reqParam.children }"
										var="reqParamChild">
										
										<tr 
											<c:if test="${reqParamChild.addtion}">
												style="color:red;"
											</c:if>
											>
											
											<td>
												<c:if test="${reqParamChild.deprecated}"><del></c:if>
												${reqParamChild.name }
												<c:if test="${reqParamChild.deprecated}"></del></c:if>		
											</td>
											<td>
												<c:if test="${reqParamChild.deprecated}"><del></c:if>
												${reqParamChild.key }
												<c:if test="${reqParamChild.deprecated}"></del></c:if>		
											</td>
											<td>
											<c:if test="${reqParamChild.deprecated}"><del></c:if>
											${reqParamChild.type }
											
											</td>
											<td>
											<c:if test="${reqParamChild.deprecated}"><del></c:if>
											${!reqParamChild.allowEmpty }
											<c:if test="${reqParamChild.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${reqParamChild.deprecated}"><del></c:if>
											${reqParamChild.desc }
											<c:if test="${reqParamChild.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${reqParamChild.deprecated}"><del></c:if>
											${reqParamChild.value }
											<c:if test="${reqParamChild.deprecated}"></del></c:if>
											</td>
											
											
									<td>
											</tr>
										</c:forEach>
											</c:if>
										
									</c:forEach>

								</tbody>
							</table>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<table class="table table-striped table-hover">
								<thead>
									<tr>
										<th>返回参数</th>
										<th>变量名</th>
										<th>类型</th>
										<th>必填</th>
										<th>描述</th>
										<th>示例</th>
									</tr>
								</thead>


								<tbody>
									<c:forEach items="${map['testConfig'].response}"
										var="resParam">
										
										
										<tr
										<c:if test="${resParam.addtion}">
												style="color:red;"
										</c:if>
										>
											
											<td>
											<c:if test="${resParam.deprecated}"><del></c:if>
											${resParam.name }
											<c:if test="${resParam.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${resParam.deprecated}"><del></c:if>
											${resParam.key }
											<c:if test="${resParam.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${resParam.deprecated}"><del></c:if>
											${resParam.type }
											<c:if test="${resParam.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${resParam.deprecated}"><del></c:if>
											${!resParam.allowEmpty }
											<c:if test="${resParam.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${resParam.deprecated}"><del></c:if>
											${resParam.desc }
											<c:if test="${resParam.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${resParam.deprecated}"><del></c:if>
											${resParam.value }
											<c:if test="${resParam.deprecated}"></del></c:if>
											</td>
										</tr>
										<c:if test="${not empty resParam.children }">
										
										<tr><td colspan="6" style="text-align: center;">以下参数包含在【${resParam.name }】中</td></tr>
											<c:forEach items="${resParam.children }" var="resChilrenParam">
										<tr class=""
										<c:if test="${resChilrenParam.addtion}">
												style="color:red;"
										</c:if>
										>
											
											<td>
											<c:if test="${resChilrenParam.deprecated}"><del></c:if>
											${resChilrenParam.name }
											<c:if test="${resChilrenParam.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${resChilrenParam.deprecated}"><del></c:if>
											${resChilrenParam.key }
											<c:if test="${resChilrenParam.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${resChilrenParam.deprecated}"><del></c:if>
											${resChilrenParam.type }
											<c:if test="${resChilrenParam.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${resChilrenParam.deprecated}"><del></c:if>
											${resChilrenParam.allowEmpty }
											<c:if test="${resChilrenParam.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${resChilrenParam.deprecated}"><del></c:if>
											${resChilrenParam.desc }
											<c:if test="${resChilrenParam.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${resChilrenParam.deprecated}"><del></c:if>
											${resChilrenParam.value }
											<c:if test="${resChilrenParam.deprecated}"></del></c:if>
											</td>
										</tr>
											<c:if test="${not empty resChilrenParam.children }">
										
										<tr><td colspan="6" style="text-align: center;">以下参数包含在【${resChilrenParam.name }】中</td></tr>
											<c:forEach items="${resChilrenParam.children }" var="resChilrenParam2">
										<tr class=""
										<c:if test="${resChilrenParam2.addtion}">
												style="color:red;"
										</c:if>
										>
											
											<td>
											<c:if test="${resChilrenParam2.deprecated}"><del></c:if>
											${resChilrenParam2.name }
											<c:if test="${resChilrenParam2.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${resChilrenParam2.deprecated}"><del></c:if>
											${resChilrenParam2.key }
											<c:if test="${resChilrenParam2.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${resChilrenParam2.deprecated}"><del></c:if>
											${resChilrenParam2.type }
											<c:if test="${resChilrenParam2.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${resChilrenParam2.deprecated}"><del></c:if>
											${resChilrenParam2.allowEmpty }
											<c:if test="${resChilrenParam2.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${resChilrenParam2.deprecated}"><del></c:if>
											${resChilrenParam2.desc }
											<c:if test="${resChilrenParam2.deprecated}"></del></c:if>
											</td>
											<td>
											<c:if test="${resChilrenParam2.deprecated}"><del></c:if>
											${resChilrenParam2.value }
											<c:if test="${resChilrenParam2.deprecated}"></del></c:if>
											</td>
										</tr>
										
										</c:forEach>
										</c:if>
										</c:forEach>
										</c:if>
								
									</c:forEach>

								</tbody>
							</table>
						</div>
					</div>


					<div class="row">
						<div class="col-md-12">
							<h5>返回结果：</h5>
							<pre style="white-space: pre-wrap;word-wrap: break-word;">
${map['testConfig'].result }
				</pre>

						</div>
					</div>
		<!-- 			<div class="row">
						<div class="col-md-12">
							<h5>备注：</h5>
							<pre style="white-space: pre-wrap;word-wrap: break-word;">					

							</pre>
						</div>
					</div> -->

				</c:forEach>
			</div>
		</div>




		<div id="bingo"></div>

	</div>
<!-- 配置线程数，请求数 -->
<div id="configConcurrent" class="modal" style="display: none;">
	<div class="modal-dialog" style="width:900px;height: 600px;">
		<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			<h4 class="modal-title">压力测试</h4>
		</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-12">
					<h5>请求设置</h5>
					<div class="row" style="    
    background-color: #E8E6E6;
    margin: 0;
    padding: 8px 0 8px 0;
    border-radius: 4px;">
						 <div class="col-lg-6">
   							 <div class="input-group">
   							 <label class="input-group-addon" id="basic-addon1">线程数</label>
								<input id="threadCount"  class="form-control" placeholder="线程数" value="20" required="required"/>
   							 </div>
   						</div>
   						 <div class="col-lg-6">
   							 <div class="input-group">
   							 <span class="input-group-addon" id="basic-addon1">循环次数</span>
								<input id="requestCount"  class="form-control" placeholder="每个线程发起的请求数" value="10000" aria-describedby="basic-addon2" required="required">
   							 </div>
   						</div>
						
					</div>
				</div>
			</div>
			<div class="row"></div>
			<div class="row">
					<div class="col-md-12">
					<h5>报文</h5>
					<pre style="white-space: pre-wrap;word-wrap: break-word;height:400px;overflow-y:scroll; ">
					<div  id="reqAndRes" style="    margin: 0;
    padding: 0;
    float: left;
    width: 100%;">
					
					</div>
					
				</pre>
					</div>
					
			</div>
		</div>
		
		<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="button" class="btn btn-primary" onclick="toStart()" >开始</button>
		<button type="button" class="btn btn-danger" onclick="toStop()" >停止</button>
		
		</div>
	</div></div>
</div>

</body>
<script type="text/javascript">
$('#sidebar').BootSideMenu();
$('#sidebar').BootSideMenu({

	side:"left", // left or right
	autoClose:true // auto close when page loads

});   




function toTestSingle(beanName){

	$.ajax({
		type:"get",
		url:"toTestSingleApi",
		async:false,
		data:{beanName:beanName},
		dataType:"json",
		success:function(data){
			if(data.success){
				$("#bingo").html("");
				
			
			var h =[];
			
			h.push('<div id="dem" class="modal"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header">');
			h.push('<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>');
			h.push('<h4 class="modal-title">设置参数</h4></div>')
			h.push('<div class="modal-body">');
			var request = data.configuration.request;
			if(typeof request !="undefined"){
				h.push('<table class="table table-striped table-hover"><thead><tr><th>请求参数</th><th>类型</th><th>必填</th><th>描述</th><th>测试值</th></tr></thead><tbody>');
			
				for(var i=0;i<request.length;i++){
					var reqparam = request[i];
					if(reqparam.deprecated){
					continue;
					}
					h.push('<tr>');
					h.push('<td>');
					h.push(reqparam.name);
					h.push('</td>');
					h.push('<td>');
					h.push(reqparam.type);
					h.push('</td>');
					h.push('<td>');
					h.push(!reqparam.allowEmpty);
					h.push('</td>');
					h.push('<td>');
					h.push(reqparam.desc);
					h.push('</td>');
					h.push('<td><input id="');
					h.push(reqparam.key);
					h.push('" type="text" value="');
					h.push(reqparam.value);
					h.push('"></td></tr>');				
				}
				h.push('</tbody></table>');
			}else{
				h.push('<span class="text1">无参数，可以直接测试</span>');
			}
							
			h.push('<input id="beanName" value="');
			h.push(data.beanName);
			h.push('" hidden />');	
			h.push('</div><div class="modal-footer">');
			h.push('<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>');
			h.push('<button type="button" class="btn btn-primary" onclick="testSingle()" >测试</button>');
			h.push('<button type="button" class="btn btn-danger" onclick="testConcurrent()" >压力测试</button>');
			h.push('</div></div></div></div>');			
			$("#bingo").append(h.join(""));
				
			}
			$('#dem').modal('show')
			//$(document).ready(function(){ $('#dem').modal('show')})

		} 
		
	});
}
function testSingle(){
	Mu.submit({url:'testSingleApi',async:'false',ids:'dem',success:function(o){
		if(o.success){
			var n = [];
			n.push('<div id="demo1" class="modal fade">');
  			  n.push('<div class="modal-dialog" style="width:900px;">');
			    n.push('<div class="modal-content">');
			      n.push('<div class="modal-header">');
			        n.push('<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>');
			        n.push('<h4 class="modal-title">测试数据</h4>');
			      n.push('</div>');
			      n.push('<div class="modal-body">');
			      
			      var str =o.result;
			      
			        n.push('<pre id="reVal"  style="white-space: pre-wrap;word-wrap: break-word;"></pre>');
			      n.push('</div>');
			      n.push('<div class="modal-footer">');
			        n.push('<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>');
			      
			      n.push('</div>');
			    n.push('</div><!-- /.modal-content -->');
			  n.push('</div><!-- /.modal-dialog -->');
			n.push('</div><!-- /.modal -->');
			
			
			$("#bingo").append(n.join(""));
			$("#reVal").html(str);
			$('#demo1').modal('show');
		}else{
			Mu.alert("操作失败",o.appmsg,"error");
		}
		
	}});
	
}
var websoket;
function toStart(){
var threadCount =$("#threadCount").val();
var requestCount = $("#requestCount").val();
if(threadCount&&requestCount){
	var params = Mu.getObj2Json("dem");
	$("#reqAndRes").empty();
	createWebsocket({method:'startConcurrent',threadCount:threadCount,requestCount:requestCount,param:params});
	
	
	
}else{
	alert('请输入线程数和请求数')
}
}

function toStop(){
	
	websocket.send(Mu.parseJson2String({method:'stopConcurrent'}));
	//websocket.close();
}
function createWebsocket(obj){
	
	var host= '<%=request.getLocalAddr()+":"+request.getServerPort()+path%>';
	var add = "ws://"+host+"/test/concurrent";
	 websocket = new WebSocket(add);
	 websocket.onopen=function(e){
		 console.log(e);
		 websocket.send(Mu.parseJson2String(obj));
	 };
	 websocket.onmessage=function(e){
		 var msg = e.data;
		 var t= $("#reqAndRes");
		 t.append('<p>'+msg+'</p>');
		 var hs = t.height();
		 var ph = t.parent().height();
		 t.parent().scrollTop(hs-ph);
	 }
	 websocket.onerror=function(e){
		 console.log(e);
	 };
	 websocket.onclose=function(e){
		 console.error("请求关闭")
		 console.log(e);
	 };
}
function testConcurrent(){
	var modal = $("#configConcurrent").clone(true);
	
	$("#bingo").append(modal);
	modal.modal('show');
}


function searchText(){
	$("domID")
        .hide()
        .filter(":contains('" + $("domID").val() + "')")
        .show();
}
</script>

</html>
