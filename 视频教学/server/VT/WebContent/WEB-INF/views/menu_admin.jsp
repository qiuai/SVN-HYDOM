<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${systemName} - 管理员管理</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<jsp:include page="include.jsp"></jsp:include>
<link href="<%=base %>template/admin/css/menu.css" rel="stylesheet" type="text/css" />
</head>
<body class="menu">
	<div class="menuContent">
		<dl>
			<dt>
				<span>管理员管理</span>
			</dt>
			<dd>
				<a href="<%=base %>admin/admin!list.action" target="mainFrame">管理员列表</a>
			</dd>
		</dl>
	</div>
</body>
</html>
