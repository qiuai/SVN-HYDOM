<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String base = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${systemName} - 管理系统</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
  <jsp:include page="include.jsp"></jsp:include>
<link href="<%=base%>template/admin/css/header.css" rel="stylesheet"
	type="text/css" />
<link href="<%=base%>template/admin/css/top.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
function exit(){
	if (confirm("确定退出?") == true) {
		window.location.href=baseUrl+"user/logout.do";
	}
}
</script>
</head>
<body>
<div class="top_main">
	<div class=""></div>
    <div class="top_wenzi">
    	<div class="top_right_01">${session_user.username}</div>
        <div class="top_right_02">
          	 	<a href="javascript:void()"  target="mainFrame" title="修改密码">修改密码</a>  |
         		<a href="javascript:exit()"title="退出">退出登录</a>
        </div>
    </div>
</div>
</body>
</html>
