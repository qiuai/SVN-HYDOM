<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String basePath = request.getContextPath() + "/";
%>
<div class="left_nav">
	<div class="userinfo">
		<ul>
			<li style="padding: 20px 0px;">
			<div class="userHeader">
				<!-- <a href=""> -->
						<img src="<%=basePath %><%=request.getSession().getAttribute("userHeader")%>" onerror="this.src='<%=basePath %>/res/images/profile.png'"/>
				<!-- </a>-->
			</div>
			<div class="userName">
				<span><%=request.getSession().getAttribute("userName")%></span>
				<span><%=request.getSession().getAttribute("gwiname")%></span>
			</div>
		</li>
		<li style="border-top:1px solid #e7e7e7;"><a target="ifm" href="<%=basePath %>persion/details.do?id=<%=request.getSession().getAttribute("userId")%>">
			<i class="fa fa-user fa-4"></i>  个人资料</a></li>
			<li><a href="<%=basePath %>event/dynamic.do"><i class="fa fa-arrow-circle-o-left fa-5"></i> <span>事&nbsp&nbsp&nbsp&nbsp件</span></a></li>
<li><a href="<%=basePath %>count/total.do?ec=员工" target="ifm"><i class="fa fa-list-alt fa-5"></i> <span>统&nbsp&nbsp&nbsp&nbsp 计</span></a></li>
		</ul>
	</div>
</div>
