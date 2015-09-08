<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String basePath = request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title><spring:message code="systemName" /></title>
	 <meta name="viewport" content="width=device-width, initial-scale=1.0">
	 <link href="<%=basePath %>bootstrap/css/bootstrap.css" rel="stylesheet" media="screen">
	 <link rel="stylesheet" href="<%=basePath %>res/font/css/font-awesome.min.css">
     <link rel="stylesheet" href="<%=basePath %>res/font/css/font-awesome-ie7.min.css"> 
     <link rel="stylesheet" href="<%=basePath %>res/font/css/font-style.css"> 
	 <link href="<%=basePath %>/res/css/MyHome.css" rel="stylesheet" media="screen">
</head>
<body class="pace-done">
	<div class="top">
		<div style="min-width: 1350px;">
			<div class="companyLogo">
				<label for=""><spring:message code="systemName" /></label>
			</div>
			<ul class="nav1">
				<li><a href="event/dynamic.do" target="ifm" onclick="iframeHeight();"><i class="fa fa-home fa-3"></i> 首页</a></li>	
				<li><a href="company/list.do" target="ifm" onclick="iframeHeight();"><i class="fa fa-user"></i> 单位</a></li>	
				<li><a href="vEventAtt/list.do" target="ifm" onclick="iframeHeight();"><i class="fa fa-archive"></i> 文档</a></li>	
				<li><a href="dictionary/list.do" target="ifm" onclick="iframeHeight();"><i class="fa fa-th"></i> 设置</a></li>	
				<li><a href="./frame/客户.html" target="ifm" onclick="iframeHeight();"><i class="fa fa-desktop"></i> 岗位</a></li>
				<li><a href="journal/list.do" target="ifm" onclick="iframeHeight();"><i class="fa fa-credit-card"></i>查看日志</a></li>		
			</ul>
			<div class="logout">
				<a href="javascript:logout();">
					<i class="glyphicon glyphicon-log-out"></i>退出
				</a>
			</div>
		</div>
		<ul class="nav1">
			<li><a href="event/dynamic.do" target="ifm"><i class="fa fa-home fa-3"></i> 首页</a></li>	
			<li><a  href="company/list.do" target="ifm"><i class="fa fa-user"></i> 单位/联系人</a></li>	
			<li><a  href="vEventAtt/list.do" target="ifm"><i class="fa fa-archive"></i> 文档</a></li>	
			<li><a  href="dictionary/list.do" target="ifm"><i class="fa fa-th"></i> 设置</a></li>	
			<li><a  href="./frame/客户.html" target="ifm"><i class="fa fa-desktop"></i> 岗位</a></li>
			<li><a  href="journal/list.do" target="ifm"><i class="fa fa-credit-card"></i>查看日志</a></li>		
		</ul>
		<div class="logout">
			<a href="javascript:logout();">
				<i class="glyphicon glyphicon-log-out"></i>退出
			</a>
		</div>
	</div>
	<div class="complexBox">
		<div class="left_nav">
			<div class="userinfo">
				<ul>
					<li style="padding: 20px 0px;">
						<div class="userHeader">
							<!-- <a href=""> -->
									<img src="<%=basePath %><%=request.getSession().getAttribute("userHeader")%>" onerror="this.src='<%=basePath %>/res/images/profile.png'"/>
		<%--							<img src="/res/images/profile.png"/>--%>
							<!-- </a>-->
						</div>
						<div class="userName">
							<span><%=request.getSession().getAttribute("userName")%></span>
							<span><%=request.getSession().getAttribute("gwiname")%></span>
						</div>
					</li>
					<li style="border-top:1px solid #e7e7e7;"><a target="ifm" href="<%=basePath %>persion/details.do?id=<%=request.getSession().getAttribute("userId")%>">
					<i class="fa fa-user fa-4"></i>  个人资料</a></li>
					<li><a href="event/dynamic.do" target="ifm"><i class="fa fa-arrow-circle-o-left fa-5"></i> <span>事&nbsp&nbsp&nbsp&nbsp 件</span></a></li>
					<li><a href="<%=basePath %>count/total.do?ec=员工" target="ifm"><i class="fa fa-list-alt fa-5"></i> <span>统&nbsp&nbsp&nbsp&nbsp 计</span></a></li>
				</ul>
			</div>
		</div>

		<div class="content">
			<ul class="time">
				<li style="margin-left:15px;"><a href="#">全部</a> <i>>></i></li>
				<li><a href="#">日期</a> <i>>></i></li>
				<li class="input-append">
				  <input class="span2" id="appendedInputButton" type="text">
				  <button class="btn" type="button">搜索</button>
			    </li>
			</ul>
		</div>

		<!-- 客户 -->
		<div class="customer">
			<div class="iframe">
				<iframe src ="event/dynamic.do"  style="max-width: 1600px;min-height:800px;" frameborder="0" id="ifm" name="ifm" onload="javascript:dyniframesize();" width="100%" class="frame" frameborder="0" scrolling="no"> 

				</iframe> 

				<script>
				
				</script>
			</div>
			<div style="clear:both;"></div>
		</div>
	
    <script src="<%=basePath %>bootstrap/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath %>bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
    <script src="<%=basePath %>bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=basePath %>bootstrap/js/modernizr.min.js"></script>
    <script src="<%=basePath %>bootstrap/js/pace.min.js"></script>
    <script src="<%=basePath %>bootstrap/js/retina.min.js"></script>
    <script src="<%=basePath %>bootstrap/js/jquery.cookies.js"></script>
	<script src="<%=basePath %>bootstrap/js/jquery.validate.min.js"></script>
	<script type="text/javascript">
		window.location.href="event/dynamic.do";
		// 退出
		function logout() {
			window.location.href = "user/logout.do";
		}
		function iframeHeight(){
			var ifm= document.getElementById("ifm");
			ifm.height = 800;
			window.scrollTo(0,0);
		}
		//i
		function dyniframesize(str) {
			var ifm= document.getElementById("ifm"); 
			var subWeb = document.frames ? document.frames["ifm"].document : ifm.contentDocument; 
			if(ifm != null && subWeb != null) {
				var heigth = 0;
				if(subWeb.body.scrollHeight < 800){//设置ifm的最小高度
					heigth = 800;
				}else{
					heigth = subWeb.body.scrollHeight;
				}
				console.log(heigth);
				if(str){
					ifm.height = heigth+str;
				}else{
					ifm.height = heigth; 
				}
			} 
		}
		
		window.onresize = dyniframesize ;
		
	</script>
</body>
</html>