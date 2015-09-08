<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<title>一动车保</title>
<link href="<%=basePath%>resource/page/css/style.main3.css" type="text/css"
	rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/common.other.js"></script>
<script type="text/javascript">
	setTimeout(function(){
		window.location.href="<%=basePath%>";
	},3000);//设置一个超时对象  --%>
	
	var intterval = setInterval(function(){
		var timeOut = $(".spanTime").text();
		timeOut = timeOut*1 - 1;
		if(timeOut <= 0){
			clearInterval(intterval);
		}
		$(".spanTime").text(timeOut);
	},1000);
	
</script>
</head>

<body>
<!--上部开始-->
<jsp:include page="../web/header.jsp" />
<!--上部结束-->
<hr>
<!--中部开始-->
<div class="mid box0">
	<div class="wash">
		<ul class="steps_3">
			<li class="b_li1"><a href="#"><span>01.</span>确定您的车型</a></li>
			<li class="b_li2"><a href="#"><span>02.</span>选择保养项目和产品</a></li>
			<li class="b_li3"><a href="#"><span>03.</span>确定服务类型</a></li>
		</ul>
		<div class="wash_con">
			<div style="text-align: right;"><span class="spanTime">3</span>秒后将自动跳到首页</div>
		</div>	
	</div>
</div>
<!--中部结束-->
<!--底部开始-->
<jsp:include page="../web/footer.jsp" />
<!--底部结束-->
</body>
</html>