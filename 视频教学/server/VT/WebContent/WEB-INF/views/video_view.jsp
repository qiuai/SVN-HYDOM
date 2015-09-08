<%@ page language="java" import="java.util.*;" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String base = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>课程详情</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />

<link href="<%=base%>template/admin/css/welcome.css" rel="stylesheet"
	type="text/css" />
<link href="<%=base%>template/admin/css/user_view.css" rel="stylesheet" />
<script type="text/javascript" src="<%=base%>template/common/js/jquery-2.0.0.js"></script>
<script src="<%=base%>/template/common/js/jwplayer.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
	  jwplayer('container').setup({
	        flashplayer: '<%=base%>/template/common/js/player.swf',
	        file: '<%=base%>${video.url }',
	        width: 800,
	        height: 480,
	        dock: false
	    });
	});
</script>


<style type="text/css">
#filelist {
	margin-top: 3px;
	margin-left: 135px;
}

.divOne {
	height: auto;
	width: 99%;
	/* margin-bottom: 15px; */
}

.divTwo {
	height: 5px;
	width: 99%;
	border: 1px solid #f0f0f0;
}

.divThree {
	height: 5px;
	background-color: green;
}

#container span {
	/* display: none; */
	cursor: pointer;
	margin-left: 5px;
}

#drag-area {
	border: 1px solid #ccc;
	height: 150px;
	line-height: 150px;
	text-align: center;
	color: #aaa;
	width: auto;
	margin: 10px auto;
}

.add_mian ul li {
	line-height: 20px;
}
</style>
</head>

<body class="input">

	<div class="tongyong_main">
		<div class="map">
			<div class="pro_map">管理信息 > 课程详情</div>
		</div>
	</div>
	<div class="content">
		<div class="list_main">
			<div class="list_main01">
			</div>
			<div style="width: 800px;margin: 0 auto;">
				<div class="list_main02" id="container">
				</div>
			</div>
		</div>
	</div>
</body>
</html>


