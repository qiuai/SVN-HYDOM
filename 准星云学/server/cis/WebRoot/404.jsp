<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>404</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/bootstrap.min.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/bootstrap.js"></script>
</head>

<body>
	您访问的页面不存在！ <a href="javascript:history.back()">返回</a>
</body>
</html>