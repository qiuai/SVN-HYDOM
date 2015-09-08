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
	
	<title>一动车保首页</title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
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
		background: url(<%=basePath %>resource/page/images/corners2.png) no-repeat right center;
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
		background: url(<%=basePath %>resource/page/images/corners1.png) no-repeat right center;
	}
	
	#button2 button {
		float: right;
		width: 169px;
		height: 43px;
		background: url(<%=basePath %>resource/page/images/go.png) 0 0 no-repeat;
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
<jsp:include page="../header.jsp"></jsp:include>
<hr></hr>
<!--中部开始-->
<div class="mid box1">
	<div class="newslists">
		<div class="newslists_top">
			<ul>
				<li>当前位置：</li>
				<li><a href="#">首页&gt;</a></li>
				<li class="last"><a href="#">新闻动态</a></li>
			</ul>
		</div>
		<div class="newslists_con">
		<c:forEach items="${list }" var="news">
			<ul>
				<li>
					<dl>
						<dt class="newslists_con_pic">
							<a href="#"><img src="<%=basePath %>${news.imgPath}" alt="" /></a>
						</dt>
						<div class="newslists_con_right">
							<dd class="title"><a href="#">${news.title }</a><span class="time"><fmt:formatDate value="${news.createDate}" pattern="yyyy-MM-dd hh:mm:ss"/></span></dd>
							<dd>${news.memo}
							<span class="read"><a href="<%=basePath%>web/news/info?id=${news.id}">[阅读全文]</a></span>
							</dd>
						</div>
					</dl>
				</li>
			</ul>
		</c:forEach>
		</div>
	</div>
</div>
<!--中部结束-->
<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>
