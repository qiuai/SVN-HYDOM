<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<title>一动车保首页</title>
	<link href="${pageContext.request.contextPath}/resource/page/css/style.css" rel="stylesheet">
	<%-- <link href="<%=basePath %>resource/page/css/appdown.css" type="text/css" rel="stylesheet" /> --%>
	<script type="text/javascript" src="<%=basePath %>resource/page/js/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath %>resource/page/js/function.js"></script>
	<style type="text/css">
	/******form*******/
	.divselectcon {
		clear: both;
		padding: 10px 0 20px;
		position: relative;
	}
	
	.divselect {
		float: left;
	}
	
	.divselectcon input {
		width: 238px;
		height: 35px;
		margin: 0 20px;
		position: relative;
		z-index: 10000;
		border: 1px solid #b1b1b1;
		border-top: 2px solid #b1b1b1;
		line-height: 35px;
		display: inline-block;
		color: #807a62;
		font-style: normal;
		background: url(<%=basePath %>resource/image/corners2.png) no-repeat right center;
	}
	
	.divselectcon input.input1 {
		border: 1px solid #ffae00;
		border-top: 2px solid #ffae00;
		margin-left: 0;
		width: 228px;
		height: 35px;
		line-height: 35px;
		display: inline-block;
		color: #807a62;
		font-style: normal;
		background: url(<%=basePath %>resource/image/corners1.png) no-repeat right center;
	}
	
	#button2 button {
		float: right;
		width: 169px;
		height: 43px;
		background: url(<%=basePath %>resource/image/go.png) 0 0 no-repeat;
		border: none;
		position: absolute;
		top: 9px;
		cursor: pointer;
	}
	
	.bdsug_copy {
		display: none;
	}
	.top_right .second a{
		color: #ffae00;
	}
	.top_right li b.b2{
		background:url(<%=basePath %>resource/page/images/2.png) 0 0 no-repeat;
	}
	
	.top_right .third a {
		color: #6b6b6b;
	}
	.top_right li b.b3 {
		display: block;
		width: 35px;
		height: 25px;
		position: absolute;
		top: 0px;
		background: url(<%=basePath %>resource/page/images/3-3.png) 0 0 no-repeat;
	}
	
	.top_right li.last a {
		color: #6b6b6b;
	}
	.top_right li b.b4 {
		display: block;
		width: 35px;
		height: 25px;
		position: absolute;
		top: 0px;
		background: url(<%=basePath %>resource/page/images/4-4.png) 0 0 no-repeat;
	}
	</style>
</head>

<body>
<!--上部开始-->
<jsp:include page="../header.jsp"></jsp:include>
<!--上部结束--> 
<hr></hr>
<!--中部开始-->
<div class="mid box1">
	<div class="newslists">
		<div class="newslists_top">
			<ul>
				<li>当前位置：</li>
				<li><a href="#">首页&gt;</a></li>
				<li><a href="<%=basePath %>web/news/list">新闻动态&gt;</a></li>
				<li class="last">${news.title}</li>
			</ul>
		</div>
		<div class="newslists_con padding">
			<h2 class="newslists_con_title">${news.title }</h2>
			<p class="p0"><fmt:formatDate value="${news.createDate}" pattern="yyyy-MM-dd hh:mm:ss"/></p>
			<div class="newslists_cons">
			${news.content}
			</div>
		</div>
	</div>
</div>
<!--中部结束-->
 <!--底部开始-->
<jsp:include page="../footer.jsp"></jsp:include>
<!--底部结束-->
</body>
</html>
