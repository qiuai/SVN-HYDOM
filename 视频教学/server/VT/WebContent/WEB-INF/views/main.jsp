<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${systemName} - 管理系统</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
</head>
<frameset id="parentFrameset" rows="87,*" cols="*" frameborder="no" border="0" framespacing="0">
	<frame id="headerFrame" name="headerFrame" src="<%=base %>main/header.do" frameborder="no" scrolling="no" noresize="noresize" />
	<frameset id="mainFrameset" name="mainFrameset" cols="191,6,*" frameborder="no" border="0" framespacing="0">
		<frame id="menuFrame" name="menuFrame" src="<%=base %>main/left.do" frameborder="no"  noresize="noresize" scrolling="auto" />
		<frame id="middleFrame" name="middleFrame" src="<%=base %>main/middle.do" frameborder="no" scrolling="no" noresize="noresize" />
		<frame id="mainFrame" name="mainFrame" src="<%=base %>main/index.do" frameborder="no" noresize="noresize" />
	</frameset>
</frameset>
<noframes>
	<body>
	</body>
</noframes>
</html>
