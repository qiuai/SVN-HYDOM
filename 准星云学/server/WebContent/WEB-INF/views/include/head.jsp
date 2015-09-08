<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
%>
<script type="text/javascript">
	// 退出
	function logout() {
		window.location.href = "<%=basePath%>user/logout.do";
	}
	function iframeHeight(){
		var ifm= document.getElementById("ifm");
		ifm.height = 800;
		window.scrollTo(0,0);
	}
/* 	//i
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
	window.onresize = dyniframesize ; */
</script>
<div class="top">
	<div style="min-width: 1350px;">
	<div class="companyLogo">
		<label for=""><spring:message code="systemName" /></label>
	</div>
	<ul class="nav1"><!--  target="ifm" onclick="iframeHeight();" -->
		<li><a href="<%=basePath%>event/dynamic.do"><i class="fa fa-home fa-3"></i> 首页</a></li>	
		<li><a href="<%=basePath%>company/list.do"><i class="fa fa-user"></i> 单位/联系人</a></li>	
		<li><a href="<%=basePath%>vEventAtt/list.do"><i class="fa fa-archive"></i> 文档</a></li>	
		<li><a href="<%=basePath%>dictionary/list.do"><i class="fa fa-th"></i> 设置</a></li>	
		<li><a href="./frame/客户.html"><i class="fa fa-desktop"></i> 岗位</a></li>
		<li><a href="<%=basePath%>journal/list.do"><i class="fa fa-credit-card"></i>查看日志</a></li>		
		</ul>
		<div class="logout">
			<a href="javascript:logout();">
				<i class="glyphicon glyphicon-log-out"></i>退出
			</a>
		</div>
	</div>
</div>