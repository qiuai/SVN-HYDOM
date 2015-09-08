<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
	<title>一动车保首页</title>
	<link href="<%=basePath %>resource/page/css/style.css" type="text/css" rel="stylesheet" />	
	<script
	src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="js/function.js"></script>
	<style type="text/css">
		/******form*******/
		.divselectcon {clear: both; padding: 10px 0 20px; position: relative; }
		.divselect {float: left; }
		.divselectcon input {width:238px; height: 35px; margin: 0 20px; position:relative; z-index:10000; border: 1px solid #b1b1b1; border-top: 2px solid #b1b1b1; line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(<%=basePath %>resource/page/images/corners2.png) no-repeat right center;}
		.divselectcon input.input1 {border:1px solid #ffae00; border-top: 2px solid #ffae00; margin-left: 0; width: 228px; height:35px;line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(<%=basePath %>resource/page/images/corners1.png) no-repeat right center;}
		#button2 button {float: right; width: 169px; height: 43px; background: url(<%=basePath %>resource/page/images/go.png) 0 0 no-repeat; border: none; position: absolute; top: 9px;  cursor: pointer; }
		.bdsug_copy{display:none;}
		<%-- .top_right .third a{
		color: #ffae00;
		}
		.top_right li b.b3{
			background:url(<%=basePath %>resource/page/images/3.png) 0 0 no-repeat;
		}	 --%>
		
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
		
		.top_right li.second a {
			color: #6b6b6b;
		}
			
		.top_right li b.b2 {
			display: block;
			width: 35px;
			height: 25px;
			position: absolute;
			top: 0px;
			background: url(<%=basePath %>resource/page/images/2-2.png) 0 0 no-repeat;
		}	
	</style>
</head>

<body>
<!--上部开始-->
<jsp:include page="/WEB-INF/page/web/header.jsp"></jsp:include>
<!--上部结束-->
<hr></hr>
<!--中部开始-->
<div class="mid box1">
	<div class="service">
		<div class="item">
			<ul class="service_left" id="service_left">
				<li <c:if test="${server.type eq 0 }">class="se_li1 hover"</c:if><c:if test="${server.type !=  0 }">class="se_li1"</c:if> ><a href="<%=basePath%>web/server/detail?id=0"><span>关于我们</span></a></li>
				<li <c:if test="${server.type eq 1 }">class="se_li1 hover"</c:if><c:if test="${server.type !=  1 }">class="se_li1"</c:if> ><a href="<%=basePath%>web/server/detail?id=1"><span>服务范围</span></a></li>
				<li <c:if test="${server.type eq 2 }">class="se_li1 hover"</c:if><c:if test="${server.type !=  2 }">class="se_li1"</c:if> ><a href="<%=basePath%>web/server/detail?id=2"><span>服务简介</span></a></li>
				<li <c:if test="${server.type eq 3 }">class="se_li1 hover"</c:if><c:if test="${server.type !=  3 }">class="se_li1"</c:if> ><a href="<%=basePath%>web/server/detail?id=3"><span>市场合作</span></a></li>
				<li <c:if test="${server.type eq 4 }">class="se_li1 hover"</c:if><c:if test="${server.type !=  4 }">class="se_li1"</c:if> ><a href="<%=basePath%>web/server/detail?id=4"><span>联系我们</span></a></li>
			</ul>
			<div class="service_right" id="service_right">
				<div class="service_right_con0" >
					<p style="text-indent: 30px;line-height: 30px;margin-top: 10px;font-size: 16px">${server.content }</p>
				</div>
			</div>	
		</div>		
		<script>
		/* $(document).ready(function(){
			var sers=$(".top_right").find("li");
			
			for (var i = 0; i < sers.length; i++) {
				var ser = sers.eq(i);
				if(ser.attr("class")!="third"){
					ser.removeClass();
				}
			}
		}) */
		function tab(m){
			var li = document.getElementById("service_left").getElementsByTagName("li");
			var div = document.getElementById("service_right").getElementsByTagName("div");
			for (i = 0; i < li.length; i++){
				li[i].className = i==m ? "hover" : "" ;
				div[i].style.display = i==m ? "block" : "none";				
			}
		}
		</script>
	</div>
</div>
<!--中部结束-->
<!--底部开始-->
<jsp:include page="/WEB-INF/page/web/footer.jsp"></jsp:include>
<!--底部结束-->
</body>
</html>