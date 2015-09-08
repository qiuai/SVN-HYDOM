<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>显示/隐藏菜单</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<jsp:include page="include.jsp"></jsp:include>
<link href="<%=base %>template/admin/css/middle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready(function() {

	$(".main").click( function() {
		var mainFrameset = window.parent.window.document.getElementById("mainFrameset");
		if(mainFrameset.cols == "191,6,*") {
			mainFrameset.cols = "0,6,*";
			$(".main").removeClass("leftArrow");
			$(".main").addClass("rightArrow");
		} else {
			mainFrameset.cols = "191,6,*";
			$(".main").removeClass("rightArrow");
			$(".main").addClass("leftArrow");
		}
	})
})
</script>
</head>
<body class="middle">
	<div class="main leftArrow"></div>
</body>
</html>
