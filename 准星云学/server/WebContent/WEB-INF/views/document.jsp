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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>文档</title>

<link href="<%=basePath%>bootstrap/css/style.default.css"
	rel="stylesheet" />
<link href="<%=basePath%>res/css/pager.css" rel="stylesheet" />
<link rel="stylesheet"
	href="<%=basePath%>bootstrap/css/jquery.gritter.css" />
<link rel="stylesheet" href="<%=basePath%>res/css/head.css" media="screen" />
<style type="text/css">
.list {
	width: 100%;
<%--	margin-top: 25px;--%>
}
</style>
<script src="<%=basePath%>bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery.cookies.js"></script>
<script src="<%=basePath%>bootstrap/js/pace.min.js"></script>
<script src="<%=basePath%>bootstrap/js/retina.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery.gritter.min.js"></script>
<script src="<%=basePath%>bootstrap/js/custom.js"></script>
<script src="<%=basePath%>res/js/common.js"></script>
<script src="<%=basePath%>res/js/jquery.pager.js"></script>
<script src="<%=basePath%>res/js/list.js"></script>
</head>

<body class="list">
<jsp:include page="include/head.jsp" />
<div class="complexBox">
	<!-- 个人事件/其他事件 -->
	<jsp:include page="include/left.jsp" />
	<div class="customer">
	<form id="listForm" name="listForm"
		action="<%=basePath%>vEventAtt/list.do" method="post">
		<div class="form-search selAdd">
			<div class="input-append">
				<div class="input-group mb15" style="width:240px">
					<input type="text" class="form-control" name="keyword"
						value="${pager.keyword }" placeholder="请按title提示输入关键字" title="请输入附件名称/事件主题、标签/单位名称进行搜索"/> <span
						class="input-group-btn">
						<button type="button" class="btn btn-info" id="searchButton">搜索</button>
					</span>
				</div>
			</div>
		</div>
		<div class="table-responsive ">
			<table class="table table-info table-hover mb10"
				style="font-size: 13px;">
				<thead>
					<tr>
						<th><input type="checkbox" id="checkAll" class="allCheck"/>
						</th>
						<th>文档名称</th>
						<th>事件</th>
						<th>创建日期</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pager.list}" var="vEventAtt" varStatus="index">
						<tr id="tr_${vEventAtt.id}">
							<td><input type="checkbox" name="ids" id="checkItem"
								value="${vEventAtt.id}">
							</td>
							<c:if test="${vEventAtt.filetype==1 }">
								<td><a href="<%=basePath%>${vEventAtt.bigpath }" title="点击预览">${vEventAtt.attname}</a></td>
							</c:if>
							<c:if test="${vEventAtt.filetype!=1 }">
								<td><a href="<%=basePath%>vEventAtt/download.do?id=${vEventAtt.id}" title="点击下载">${vEventAtt.attname}</a></td>
							</c:if>
							<td>${vEventAtt.eventSubject}</td>
							<td><fmt:formatDate value="${vEventAtt.lastupdate}"
									type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="table-action"><a
								href="<%=basePath%>vEventAtt/download.do?id=${vEventAtt.id}"
								data-toggle="tooltip" title="" class="tooltips"
								data-original-title="下载"> <i class="fa fa-download"></i> </a>
								&nbsp; <a href="javascript:deletevEventAtt('${vEventAtt.id}');"
								data-toggle="tooltip" title="" class="tooltips"
								data-original-title="作废"> <i class="fa fa-trash-o"></i>
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	
	<div class="btn-list">
		<button class="btn btn-primary deleteButton"
			onclick="deletevEventAtts()" disabled="disabled">删除</button>
		<button class="btn btn-danger deleteButton"
			onclick="downloadvEventAtts()" disabled="disabled">下载</button>
		<jsp:include page="pager.jsp" />
	</div>
</form>
</div>
</div>
	<script type="text/javascript">
	//单个文档删除
	function deletevEventAtt(id) {
		if (confirm("您确定将此文件作废吗？")) {
			$.ajax({
				  type: 'POST',
				  url: '<%=basePath%>vEventAtt/delete.do',
					data : {
						'id' : id
					},
					dataType : 'json',
					success : function(data) {
						$("#tr_" + id).remove();
					}
				});
			}
		}

	
	//批量删除
	function deletevEventAtts() {
		var names = document.getElementsByName("ids");
		var ids = "";
			for(var i=0; i<names.length; i++){
				if(names[i].checked){
					 ids += names[i].value +",";
				}
			}
			if (confirm("您确定要删除所选文件吗？")) {
				$.ajax({
					  type: 'POST',
					  url: '<%=basePath%>vEventAtt/deletes.do',
					  data: {'ids':ids},
					  dataType: 'json',
					  success: function(data) {
						  BatchDeletion(data.deleteids);
					  },
					  error: function(e){
						  console.log("test");
						  console.log(e);
					  }
				});
			}
	}
	function BatchDeletion(data){
		for(var i = 0; i<data.length; i++){
			$("#tr_" + data[i]).remove();
		}
	}
	
	//批量下载文件
	function downloadvEventAtts() {
		var names = document.getElementsByName("ids");
		var ids="";
		for(var i=0; i<names.length; i++){
			if(names[i].checked){
				ids+=names[i].value+",";
			}
		location.href="<%=basePath%>vEventAtt/downloads.do?ids2="+ids;
		}
	}	

		
	</script>
</body>
</html>
