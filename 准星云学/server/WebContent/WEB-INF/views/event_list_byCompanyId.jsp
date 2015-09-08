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
<link href="<%=basePath%>bootstrap/css/bootstrap.css" rel="stylesheet"
	media="screen" />
<link href="<%=basePath%>bootstrap/css/style.default.css"
	rel="stylesheet" />
<link href="<%=basePath%>res/css/pager.css" rel="stylesheet" />
<link rel="stylesheet"
	href="<%=basePath%>bootstrap/css/jquery.gritter.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>bootstrap/css/select2.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>bootstrap/css/style.css" />
<link rel="stylesheet" href="<%=basePath%>res/css/head.css" media="screen" />

<script src="<%=basePath%>bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery.cookies.js"></script>
<script src="<%=basePath%>bootstrap/js/pace.min.js"></script>
<script src="<%=basePath%>bootstrap/js/retina.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery.gritter.min.js"></script>
<script src="<%=basePath%>bootstrap/js/custom.js"></script>
<script src="<%=basePath%>res/js/common.js"></script>
<script src="<%=basePath%>res/js/list.js"></script>
<script src="<%=basePath%>res/js/jquery.pager.js"></script>
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
			<li class="active" onclick="toCompany()"><a href="#following" data-toggle="tab"><strong>单位</strong></a></li>
			<li class="" onclick="toContacts()"><a href="#following" data-toggle="tab"><strong>联系人</strong></a></li>
			<li class="" onclick="toCompanyPool()"><a href="#following" data-toggle="tab"><strong>客户池</strong></a></li>
			<li class="" onclick="toCount()"><a href="#following" data-toggle="tab"><strong>统计</strong></a></li>
		</ul>
		<script type="text/javascript">
			// 跳转客户(公司)
	        function toCompany() {
	        	location.href = "<%=basePath%>company/list.do";
	        }
			// 跳转联系人
	        function toContacts() {
	        	location.href = "<%=basePath%>contacts/list.do";
	        }
			
			// 跳转客户池
			function toCompanyPool() {
	        	location.href = "<%=basePath%>company/pool.do";
			}
			
			// 去统计
			function toCount() {

				location.href = "<%=basePath %>count/total.do?ec=员工";


			}
			
		</script>
		</div>
<form id="listForm" name="listForm"	action="<%=basePath%>event/list.do?companyId="+'<%=request.getSession().getAttribute("companyId")%>' method="post">
		<div class="row">
			<div class="col-md-12">

				<div class="table-responsive table-lists_con">
					<table class="table table-primary table-hover mb30">
						<thead>
							<tr >
								<th width="56px">序号</th>
								<th width="70px;">客户</th>
								<th width="56px;">主题</th>
								<th width="796px;">摘要</th>
<%--								<th>标签</th>--%>
								<th width="75px;">创建人</th>
								<th width="90px;">时间</th>
								<!-- <th>操作</th> -->
							</tr>
						</thead>
						<tbody>
							<c:forEach var="event" items="${pager.list }" varStatus="index">
								<tr>
									<td>${index.index +1 }</td>
									<td>${event.company.pubname }</td>
									<td>${event.eventsubject }</td>
									<td>${event.eventsummary }</td>
<%--									<td>${event.eventtags }</td>--%>
									<td>${event.creator.pername }</td>
									<td><fmt:formatDate value="${event.createDate }"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<!-- <td><a href="#">详情</a></td> -->
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<jsp:include page="pager.jsp" />
				</div>
			</div>
		</div>
	</form>
</div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		$("input[name=dicnames]").attr("checked",true);
	});
	
	function check(){
		//alert("?");
		var dicnames = document.getElementsByName("dicnames");
		var dicnames_no = document.getElementsByName("dicnames_no");
		 var dics="";
		for(var i=0;i<dicnames.length;i++){
			if(dicnames[i].checked){
				dics += dicnames[i].value + ",";
			}
		}
		for(var i=0;i<dicnames_no.length;i++){
			if(dicnames_no[i].checked){
				dics += dicnames_no[i].value + ",";
			}
		}
		location.href = "<%=basePath%>count/exports.do?dicnames="+dics+"&ids=${ids}&ec=${ec}";
	}
</script>
</body>
</html>

