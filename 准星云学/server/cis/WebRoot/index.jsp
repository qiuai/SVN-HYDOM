<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<link href="${pageContext.request.contextPath}/resource/mathjax/ace.min.css" rel="stylesheet" >
    <link href="${pageContext.request.contextPath}/resource/mathjax/mathquill.css"  rel="stylesheet" >
	<script src="${pageContext.request.contextPath}/resource/mathjax/MathJax.js?config=TeX-AMS_HTML-full"  type="text/javascript" ></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    .....<%=new Date() %>> <br>
  </body>
</html>
