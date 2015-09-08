<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>${systemName} - 成功提示</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
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
					<span class="icon success">&nbsp;</span>
					<span class="messageText">
						您的操作已成功!<br/>
						<a href="${path}">${content}</a>
					</span>
				</div>
				<input type="button" class="formButton messageButton" 
				onclick="<%if(request.getAttribute("redirectionUrl")==null) {%>window.history.go(-1); return false;<%}else{%>window.location.href='<%=base + request.getAttribute("redirectionUrl") %>'<%} %>" value="确  定" hidefocus="true" />
			</div>
			<div class="boxBottom"></div>
		</div>
	</div>
</body>
</html>
