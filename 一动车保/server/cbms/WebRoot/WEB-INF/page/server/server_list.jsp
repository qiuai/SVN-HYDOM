<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String base = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<title>服务管理</title>
<link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resource/css/manage.common.css" rel="stylesheet">

<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/chain/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/chain/js/modernizr.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/chain/js/pace.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/chain/js/retina.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/chain/js/jquery.cookies.js"></script>
<script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/myform.js" type="text/javascript"></script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.min.js"></script>
	<![endif]-->
</head>

<body>
	<%@ include file="/WEB-INF/page/common/head.jsp"%>
	<section>
		<div class="mainwrapper">
			<%@ include file="/WEB-INF/page/common/left.jsp"%>
			<div class="mainpanel">
				<div class="pageheader">
					<div class="media">
						<div class="media-body">
							<ul class="breadcrumb">
								<li>
									<a href="">
										<i class="glyphicon glyphicon-home"></i>
									</a>
								</li>
								<li>
									<a href="">服务管理</a>
								</li>
							</ul>
						</div>
					</div>
					<!-- media -->
				</div>
				<form id="pageList" action="${pageContext.request.contextPath}/manage/server/list" method="post">
					<div class="contentpanel">
						<div class="search-header">
							<!-- <div class="btn-list">
								<button class="btn btn-danger" id="deleteButton" type="button" val="<%=path%>/manage/server" disabled>删除</button>
								<button class="btn btn-success" id="refreshButton">刷新</button>
								<button id="add" type="button" class="btn btn-primary" val="<%=path%>/manage/car">添加</button>
							</div> -->
						</div>

						<div class="table-responsive">
							<table id="listTable" class="table table-info">
								<thead>
									<tr>
										<!-- <th>
											<input id="selectAll" type="checkbox" />
										</th> -->
										<th>服务类型</th>
										<th>创建日期</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${serverList}" var="server">
										<tr id="tr_${server.id}">
											<!-- <td>
												<input type="checkbox" name="ids" value="${server.id}">
											</td> -->
											<td>
												<c:if test="${server.type==0}">关于我们</c:if>
												<c:if test="${server.type==1}">服务范围</c:if>
												<c:if test="${server.type==2}">服务简介</c:if>
												<c:if test="${server.type==3}">市场合作</c:if>
												<c:if test="${server.type==4}">联系我们</c:if>
											</td>
											<td>
												<fmt:formatDate value="${server.createDate}" pattern="yyyy-MM-dd" />
											</td>
											<td>
												<a href="${pageContext.request.contextPath}/manage/server/edit?id=${server.id}">修改</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<%@ include file="/WEB-INF/page/common/fenye.jsp"%>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- mainwrapper -->
	</section>



</body>
</html>
