<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<title>一动车保首页</title>
	<link href="<%=basePath %>resource/page/css/style.css" type="text/css" rel="stylesheet" />	
	<script type="text/javascript" src="<%=basePath %>resource/page/js/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath %>resource/page/js/function.js"></script>
	<script type="text/javascript">
	/* $(document).ready(function(){
		var sers=$(".top_right").find("li");
		
		for (var i = 0; i < sers.length; i++) {
			var ser = sers.eq(i);
			if(ser.attr("class")!="last"){
				ser.removeClass();
			}
		}
	}) */
	</script>
	<style type="text/css">
		/******form*******/
		.divselectcon {clear: both; padding: 10px 0 20px; position: relative; }
		.divselect {float: left; }
		.divselectcon input {width:238px; height: 35px; margin: 0 20px; position:relative; z-index:10000; border: 1px solid #b1b1b1; border-top: 2px solid #b1b1b1; line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(<%=basePath %>resource/page/images/corners2.png) no-repeat right center;}
		.divselectcon input.input1 {border:1px solid #ffae00; border-top: 2px solid #ffae00; margin-left: 0; width: 228px; height:35px;line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(<%=basePath %>resource/page/images/corners1.png) no-repeat right center;}
		#button2 button {float: right; width: 169px; height: 43px; background: url(<%=basePath %>resource/page/images/go.png) 0 0 no-repeat; border: none; position: absolute; top: 9px;  cursor: pointer; }
		.bdsug_copy{display:none;}
		.top_right li.last a{
		color: #ffae00;
		}
		.top_right li b.b4{
			background:url(<%=basePath %>resource/page/images/4.png) 0 0 no-repeat;
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
		
		.top_right .third a {
			color: #6b6b6b;
		}
		.top_right li b.b3 {
			display: block;
			width: 35px;
			height: 25px;
			position: absolute;
			top: 0px;
			background: url(<%=basePath %>resource/page/images/3-3.png) 0 0 no-repeat;
		}
		.aap_bg{
			  width: 100%;
  			  display: inline-block;
		}
		div.mid{
			margin-top: 0px;
		}
	</style>
</head>

<body>
<!--上部开始-->
<jsp:include page="/WEB-INF/page/web/header.jsp"></jsp:include>
<!--上部结束-->
<!-- <hr></hr> -->
<!--中部开始-->
<%-- <div>
	<div class="span4_1">
		<div class="span4_1bg"><img class="aap_bg" src="<%=basePath %>resource/page/images/app_bg.png" alt="" /></div>
		<div class="app_txt">
			<p class="app_p1">因为用心，所以专业</p>
			<p class="app_p2">一动车保，您身边的“洗车养车”专家</p>
		</div>
		<div class="app_right">
			<div class="app_right_Div">
				<div class="app_right_left">
					<a href="#"><img class="appImg1" src="<%=basePath %>resource/page/images/app_right_3.png" alt="" /></a>
					<p>扫描二维码下载一动车保APP</p>
				</div>
				<div class="app_right_right">
					<a href="<%=basePath %>down/android"><img src="<%=basePath %>resource/page/images/app_right_4.png" alt="" /></a>
					<a href="<%=basePath %>down/iphone" class="a2"><img src="<%=basePath %>resource/page/images/app_right_5.png" alt="" /></a>
				</div>
			</div>
		</div>
	</div>
</div> --%>

<div class="span4_1">
	<div class="span4_1_body">
		<div class="area">
			<div class="appPic"><img src="<%=basePath %>resource/page/images/app1.png" /></div>
			<div class="app_txt">
				<p class="app_p1">因为用心，所以专业</p>
				<p class="app_p2">一动车保，您身边的“洗车养车”专家</p>
			</div>
			<div class="app_right">
				<div class="app_right_Div">
					<div class="app_right_left">
						<a href="#"><img src="<%=basePath %>resource/page/images/app_right_3.png" alt="" /></a>
						<p>扫描二维码下载一动车保APP</p>
					</div>
					<div class="app_right_right" style="padding-right: 15px;">
						<a href="javascript:alert('即将上市，敬请期待')"><img src="<%=basePath %>resource/page/images/app_right_4.png" /></a>
						
						<c:if test="${MQQBrowser!=null}" > 
							<a href="javascript:alert('点击右上角菜单选择在浏览器中浏览，然后再点下载。')" class="a2"><img src="<%=basePath %>resource/page/images/app_right_5.png" alt="" /></a>
						</c:if>
						<c:if test="${MQQBrowser==null}" > 
							<a href="<%=basePath %>down/android" class="a2"><img src="<%=basePath %>resource/page/images/app_right_5.png" alt="" /></a>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%-- <div class="mid">
	<div class="span4_1">
		<img class="aap_bg" src="<%=basePath %>resource/page/images/app_bg.png" alt="" />
		<div class="app_txt">
			<p class="app_p1">因为用心，所以专业</p>
			<p class="app_p2">一动车保，您身边的“洗车养车”专家</p>
		</div>
		<div class="app_right">
			<div class="app_right_left">
				<a href="#"><img src="<%=basePath %>resource/page/images/app_right_3.png" alt="" /></a>
			</div>
			<div class="app_right_right">
				<a href="<%=basePath %>down/android"><img src="<%=basePath %>resource/page/images/app_right_4.png" alt="" /></a>
				<a href="<%=basePath %>down/iphone"><img src="<%=basePath %>resource/page/images/app_right_5.png" alt="" /></a>
			</div>
		</div>
	</div>
</div> --%>
<!--中部结束-->
<!--底部开始-->
<jsp:include page="/WEB-INF/page/web/footer.jsp"></jsp:include>
<!--底部结束-->
</body>
</html>
