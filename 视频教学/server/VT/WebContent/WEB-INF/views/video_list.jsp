<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String base = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=base%>">
<meta http-equiv='X-UA-Compatible' content='IE=8'>
<title>视频列表</title>

<jsp:include page="include.jsp"></jsp:include>
<script type="text/javascript">
	function ajax(url, data, callback) {

		$.ajax({
			type : "POST",
			url : url,
			data : data,
			dataType : "json",
			success : callback
		});
	}
	function del(id) {

		if (confirm("确定删除?")) {
			ajax(baseUrl + 'video/del.do', 'id=' + id, function(data) {
				alert(data.msg);
				if (data.status == "success") {
					alert(data.status);
					window.location.reload();
				}
			});
		}
	}

	function edit(id) {
		window.location = baseUrl + 'video/view.do?id=' + id;
	}
	function sub() {
		$("#listForm").submit();
	}
</script>
<style type="text/css">
.list table {
	width: 100%;
}

.list_main {
	width: 99%;
	margin-top: 5px;
}

.list label {
	cursor: pointer;
	color: #36c;
	text-decoration: underline;
	margin-left: 5px;
}

.width01 {
	width: 40px;
}

.width02 {
	width: 70px;
}

.width03 {
	width: 80px;
}

.width04 {
	width: 120px;
}
.width05 {
	width: 90px;
}
#_left {
	text-align: left;
}

.list table tr td {
	word-break: break-all;
}
</style>
</head>

<body>
	<div class="tongyong_main">
		<div class="map">
			<div class="pro_map">管理信息 > 视频管理</div>
		</div>
	</div>
	<div class="list_main list_main01">
		<form id="listForm" name="listForm" action="video/list.do"
			method="post">
			<div class="pro_top">
				<div class="search_div">

					<div class="search_anniu" style="margin-right: 10px; float: left;">
						<input type="text" name="keyword" value="${keyword}" style="width: 220px; height: 33px;" />
					</div>
					<div class="add">
						<a href="javascript:sub()">
							<img src="<%=base%>template/admin/images/search.png" alt="" width="79" height="34" />
						</a>
					</div>
				</div>
			</div>
			<div class="list_main">
				<div class="user_info list">
					<table cellpadding="0" cellspacing="0" style="table-layout:fixed">
						<tr>
							<th class="width05">视频名称</th>
							<th class="width05">所属课程</th>
							<th class="width05">所属老师</th>
							<th class="width03">操作</th>
						</tr>
						<c:forEach var="video" items="${pager.list}">
							<tr>
								<td class="width05">${video.name }</td>
								<td class="width05">${video.course.name }</td>
								<td class="width05">${video.user.nickname }</td>
								<td class="width03">
									<label onclick="edit('${video.id}')">播放</label>
									<label onclick="del('${video.id}')">删除</label>
								</td>
							</tr>
						</c:forEach>
					</table>

					<div class="pagerBar">
						<jsp:include page="pager.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>