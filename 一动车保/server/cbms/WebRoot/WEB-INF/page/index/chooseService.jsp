<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>选择服务</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/chain/js/laydate/need/laydate.css">
	
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/js/common.other.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/laydate/laydate.js"></script>
	
  </head>
  
  <body>
  	<form action="<%=basePath %>web/addOrder" method="post">
	   	<div>
	   		<span>所选车型:</span>
	   		<span>${car.name }</span>
	   		<input name="carId" value="${car.id }" type="text"/>
	   	</div>
	   	<div>
	   		服务:
	   		<ul>
	   			<c:forEach var="serviceType" items="${serviceTypes }">
	   				<li><label><input type="radio" value="${serviceType.id }" name="serviceTypeId"/>${serviceType.name }</label></li>
	   			</c:forEach>
	   		</ul>
	   	</div>
	   	<div><button>下一步</button></div>
   	</form>
  </body>
</html>
