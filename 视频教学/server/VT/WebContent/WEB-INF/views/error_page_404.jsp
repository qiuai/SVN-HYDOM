<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>404提示信息</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<jsp:include page="include.jsp"></jsp:include>
</head>
<body class="message">
	<div align="center" class="body">
		<div class="messageBox">
			<div class="boxTop">
				<div class="boxTitle">提示信息&nbsp;</div>
				<a class="boxClose windowClose" href="#" hidefocus="true"></a>
			</div>
			<div class="boxMiddle">
				<div class="messageContent">
					<span class="icon error">&nbsp;</span>
					<span class="messageText">
						未找到指定页面!
					</span>
				</div>
				<input type="button" class="formButton messageButton" onclick="window.history.back(); return false;" value="确  定"/>
			</div>
			<div class="boxBottom"></div>
		</div>
	</div>
</body>
</html>