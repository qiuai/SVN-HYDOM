<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath() + "/";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>统计</title>
<link href="<%=basePath %>bootstrap/css/bootstrap.css" rel="stylesheet" media="screen" /> 
	<link href="<%=basePath %>bootstrap/css/style.default.css" rel="stylesheet" />
	<link href="<%=basePath %>res/css/pager.css" rel="stylesheet" />
     <link rel="stylesheet" href="<%=basePath %>bootstrap/css/jquery.gritter.css" />
     <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/select2.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/style.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/css/head.css" media="screen" />


<script src="<%=basePath %>bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath %>bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath %>bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath %>bootstrap/js/jquery.cookies.js"></script>
<script src="<%=basePath %>bootstrap/js/pace.min.js"></script>
<script src="<%=basePath %>bootstrap/js/retina.min.js"></script>
<script src="<%=basePath %>bootstrap/js/jquery.gritter.min.js"></script>
<script src="<%=basePath %>bootstrap/js/custom.js"></script>
<script src="<%=basePath %>res/js/common.js"></script>
<script src="<%=basePath %>res/js/list.js"></script>
</head>
<body class="list">
<jsp:include page="include/head.jsp" />
<div class="complexBox">
	<!-- 个人事件/其他事件 -->
	<jsp:include page="include/left.jsp" />
	<div class="customer">
	<!-- 客户/联系人/客户池 -->
	<div class="panel panel-primary-head">
		<ul class="nav nav-tabs nav-line">
			<!-- <li class="" onclick="toCompany()"><a href="#following"
				data-toggle="tab"><strong>单位</strong></a></li>
			<li class="" onclick="toContacts()"><a href="#following"
				data-toggle="tab"><strong>联系人</strong></a></li>
			<li class="" onclick="toCompanyPool()"><a href="#following"
				data-toggle="tab"><strong>客户池</strong></a></li> -->
			<li class="active" onclick="toCount()"><a href="#following"
				data-toggle="tab"><strong>统计</strong></a></li>
		</ul>
		<script type="text/javascript">
			// 跳转客户(公司)
	        function toCompany() {
	        	location.href = "<%=basePath %>company/list.do";
	        }
			// 跳转联系人
	        function toContacts() {
	        	location.href = "<%=basePath %>contacts/list.do";
	        }
			
			// 跳转客户池
			function toCompanyPool() {
	        	location.href = "<%=basePath %>company/pool.do";
			}
			
			// 去统计
			function toCount() {
				location.href = "<%=basePath%>count/total.do?ec=员工";
			}		
					
			
			
		</script>
		</div>
		<div class="row">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="panel-btns">
                        <h3 class="panel-title">地域分布</h3>
                    </div>
				</div>
			</div>
			<div class="panel-body">
				<div class="table-responsive table-lists_con">
		 			<table class="table table-bordered mb30">
		 				<tr>
		 					<th colspan="2">地区</th>
		 					<th>客户数量</th>		 					
		 				</tr>
		 				 <c:forEach var="city" items="${city_set }" varStatus="index">
		 					<tr><td rowspan="${mm[city]}">${city }</td></tr>
		 					 <c:forEach var="area" items="${shi_qu_set }">
		 					 	<c:if test="${area.key == city}">
		 							<c:forEach var="a" items="${area.value }">
		 								<tr><td>${a }</td><td>${count_company[a]}</td></tr>
		 							</c:forEach>
		 						</c:if>
		 					</c:forEach>
		 				</c:forEach>
		 			</table>
		 		</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>

