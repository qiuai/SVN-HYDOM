<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${systemName} - 提示信息</title>
<meta name="Author" content="Hydom Team" />
<meta name="Copyright" content="Hydom" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<jsp:include page="include.jsp"></jsp:include>
</head>
<body class="error">
	<div class="body">
		<div class="errorBox">
			<div class="errorDetail">
				<div class="errorContent">
					${errorContent}"您的操作出现错误!"
				</div>
				<div class="errorUrl">点击此处<a href="#" onclick="window.history.back(); return false;">返回</a>，或回到<a href="${base}/">首页</a></div>
			</div>
		</div>
	</div>
</body>
</html>
