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
				data-toggle="tab"><strong>单位</strong> </a>
			</li>
			<li class="" onclick="toContacts()"><a href="#following"
				data-toggle="tab"><strong>联系人</strong> </a>
			</li>
			<li class="" onclick="toCompanyPool()"><a href="#following"
				data-toggle="tab"><strong>客户池</strong> </a>
			</li> -->
			<li class="active" onclick="toCount()"><a href="#following"
				data-toggle="tab"><strong>统计</strong> </a>
			</li>
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
				location.href = "<%=basePath%>count/total.do?ec=员工";
			}
		</script>
	</div>
	<div class="well" style="height: 140px;">
		<div class="col-ms-12">
			<div class="form-group" style="height: 50px;">
				<label class="col-sm-1 control-label"> 关键字： </label>
				<div class="col-sm-4">
					<input class="form-control" type="text" placeholder="" />
				</div>
				<div class="btn-list">
					<button class="btn btn-primary btn-metro" id="searchButton"
						style="margin-left: 33px;" onclick="check()">搜索</button>
					<%--	 style="margin-left: 60px;"              --%>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-1 control-label"> 类型： </label>
				<div class="col-sm-11">
					<c:forEach var="dic" items="${type }">
						<label class="radio-inline" style="padding-left:0;margin-left:0;"> <input type="checkbox"
							name="dicnames" value="${dic.dicname }" />${dic.dicname }</label>
					</c:forEach>
					<c:forEach var="dic" items="${type_no }">
						<label class="radio-inline" style="padding-left:0;margin-left:0;"> <input type="checkbox"
							name="dicnames_no" value="${dic.dicname }" />${dic.dicname }</label>
					</c:forEach>
					<!-- <label class="radio-inline"> <input type="radio"
								value="option2" /> 客户拜访
							</label> <label class="radio-inline"> <input type="radio"
								value="option2" /> 电话联系
							</label> <label class="radio-inline"> <input type="radio"
								value="option2" /> 发报家
							</label> <label class="radio-inline"> <input type="radio"
								value="option2" /> 招待
							</label> -->
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">

			<div class="table-responsive table-lists_con">
				<table class="table table-primary table-hover mb30">
					<thead>
						<tr>
							<th style="width: 5%;">序号</th>
							<th style="width: 5%;">主题</th>
							<th style="width: 8%;">客户</th>
							<th style="width: 8%;">联系人</th>
							<th style="width: 8%;">类型</th>
							<th style="width: 40%;">摘要</th>
							<th style="width: 8%;">创建人</th>
							<th style="width: 8%;">时间</th>
							<!-- <th>操作</th> -->
						</tr>
					</thead>
					<tbody>
						<c:forEach var="persionEvent" items="${pager.list }"
							varStatus="index">
							<tr>
								<td>${index.index +1 }</td>
								<td>${persionEvent.eventsubject }</td>
								<td>${persionEvent.company.pubname }</td>
								<td><c:forEach var="contacts"
										items="${persionEvent.contactsSet}" varStatus="index">
										${contacts.conname }&nbsp;
									</c:forEach>
								</td>
								<td>${persionEvent.eventtags }</td>
								<td>${persionEvent.eventsummary }</td>
								<td>${persionEvent.creator.pername }</td>
								<td><fmt:formatDate value="${persionEvent.createDate }"
										pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<jsp:include page="pager.jsp" />
			</div>
		</div>
	</div>
	</div>
	</div>
</body>
</html>

