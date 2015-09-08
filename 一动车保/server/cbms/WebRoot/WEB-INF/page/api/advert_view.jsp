<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>广告查看</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
		<link href="${pageContext.request.contextPath}/resource/chain/css/bootstrap.min.css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/bootstrap.min.js"></script>
	</head>

    <body>
    	<div style="padding: 5px 10px;"> 
		    <h3>${advert.title}</h3>
		    <img src="${advert.imgPath}" />
		    <div><fmt:formatDate value="${advert.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /> </div>
		    <div style="border-top: 1px dashed ddd;">
		    	${advert.content}
		    </div>
    	</div>
    </body>
</html>
