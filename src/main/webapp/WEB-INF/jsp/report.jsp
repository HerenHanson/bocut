<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="hd" uri="http://www.snailhd.com/test/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>BOCUT-测试报告</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-3.1.0.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/my.full.js"></script>
<script type="<%=request.getContextPath()%>/resources/js/jsonFormat.js"></script>


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
}

.text1 {
	font-size: 24px;
	font-weight: bold;
}

.apicode {
	font-size: 12px;
	float: right;
}

.isSuccess {
	float: right;
	padding: 5px 10px;
	background-color: #5cb85c;
	border-radius: 4px;
}

.isFail {
	float: right;
	padding: 5px 10px;
	background-color: #d9534f;
	border-radius: 4px;
}
.titleBtn{
	margin: 8px 20px;
}
#sidebar_out{
	position: fixed;
	width: 20%;
	height: 100%;
	min-height:500px;
	overflow: hidden;
}
#sidebar{
	margin-top:80px;
	padding-right:40px;
	width: 110%;
	height: 100%;
	min-height:500px;
	overflow-y:auto;
} 

</style>
</head>
<body>

<!-- 生成侧边栏 -->
			<div id="sidebar_out">
				<div id="sidebar" >
					<ul class="list-group">
					<li  >
						<a href="#" class="list-group-item active">接口列表</a>
					</li>	
						<c:forEach items="${configList }" var="map" varStatus="status">
						<li >	
							<a href="#bookmark${status.index}"class="list-group-item " >${map['testConfig'].no }${map['testConfig'].title }
								<br/>
							<span>total:${map.totalCount} </span>	
							<span>success:${map.successCount} </span>
							<span>error:${map.errorCount} </span>	
							<span>judge:${map.judgeCount} </span>			
							</a>	
						</li>	
						</c:forEach>						
					</ul>
				</div>
			</div> 
	<div class="container-fluid">
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">

			<div class="navbar-header">
				<a href="<%=path %>/" class="navbar-brand">BOCUT-测试报告</a>
			</div>
			
			
			
			
			<a href="testAll?docType=docJudge" class="btn  navbar-right btn-warning  titleBtn" >需要人工的结果</a>			
			<a href="testAll?docType=docError" class="btn  navbar-right btn-danger titleBtn">所有错误的结果</a>
			<a href="testAll?docType=docAll" class="btn  navbar-right btn-primary  titleBtn">所有结果</a>
			<a href="testAll?docType=docAll&retest=new" class="btn  navbar-right btn-info  titleBtn">重新测试</a>
			
			<div class="btn-group btn  navbar-right">
  <button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
   输出word文档 <span class="caret"></span>
  </button>
  <ul class="dropdown-menu">
    <li><a href="output?docType=docAll" target="_blank">所有测试用例</a></li>
    <li role="separator" class="divider"></li>
    <li><a href="output?docType=docError" target="_blank">未通过的用例</a></li>
    <li role="separator" class="divider"></li>
    <li><a href="output?docType=docJudge" target="_blank">需人工审核的用例</a></li>
    <li role="separator" class="divider"></li>
    <li><a href="#">务必在全局测试后再输出文档</a></li>
  </ul>
</div> 
<!-- Split button -->
<!-- <div class="btn-group">
  <button type="button" class="btn btn-danger">Action</button>
  <button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    <span class="caret"></span>
    <span class="sr-only">Toggle Dropdown</span>
  </button>
  <ul class="dropdown-menu">
    <li><a href="#">Action</a></li>
    <li><a href="#">Another action</a></li>
    <li><a href="#">Something else here</a></li>
   
  </ul>
</div> -->
		</nav>
		
		<div style="height: 80px;"></div>
		<div class="row">
<div class="col-xs-2 col-xs-offset-1" >
				<div style="height: 20px;"></div>
				




			</div>
			<div class="col-xs-8">


			<c:forEach items="${configList }" var="config" varStatus="status">
<a name="bookmark${status.index}"></a>
				<div style="height:60px;"></div>
				<div class="row">
					<div class="col-md-12">
						<span class="apiTitle">${config.no }${config.testConfig.title }</span>
					</div>
				</div>
				
				
			<div class="row api">
						<div class="col-xs-2" style="padding: 0">
							<div class="reqMethod">${config.testConfig.method }</div>
						</div>
						<div class="col-xs-6">${config.testConfig.url }</div>
						<div class="col-xs-2">
							<c:if test="${config.testConfig.testCases[0].judge  }">
								人工
							</c:if>
							<c:if test="${!config.testConfig.testCases[0].judge  }">
								自动
							</c:if>

						</div>
					</div>
				<div style="height: 20px;"></div>
				
				<div class="row">			
					<div class="col-md-12">
					
					 <c:forEach items="${config.testConfig.testCases }" var="testCase">
						<div class="panel panel-default">
							<div class="panel-heading">
							(${testCase.code})${testCase.title}							 
								  <c:if test="${testCase.success}">
								  		<span class="isSuccess">
								  			成功
								  		</span>	
								  </c:if>
								  <c:if test="${!testCase.success}">
								  		<span class="isFail">
								  			失败
								  		</span>	
								  </c:if>
								  <br>
								 
										
							</div>
							<div class="panel-body">
							
							<h5>用例描述:</h5>
							
							<pre style="white-space: pre-wrap;word-wrap: break-word;">${testCase.desc }
								</pre>
								<h5>请求参数：</h5>
								
								
								<pre style="white-space: pre-wrap;word-wrap: break-word;">${hd:format(testCase.requestParam)}
								</pre>
								<h5>返回值：</h5>
								<pre style="white-space: pre-wrap;word-wrap: break-word;">${hd:format(testCase.result)}
								</pre>
							
							</div>
						</div>
					</c:forEach>

					</div>
				</div>

				</c:forEach>

			</div>
		</div>
		
		
		</div>
	

</body>


</html>
