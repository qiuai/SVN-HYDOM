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

<title>查看日志</title>

<link href="<%=basePath%>bootstrap/css/bootstrap.min.css" />
<link href="<%=basePath%>bootstrap/css/style.default.css"
	rel="stylesheet" />
<link href="<%=basePath%>res/css/pager.css" rel="stylesheet" />
<link rel="stylesheet"
	href="<%=basePath%>bootstrap/css/jquery.gritter.css" />
<link rel="stylesheet" href="<%=basePath%>res/css/head.css" media="screen" />
<style type="text/css">
.list {
	width: 100%;
	margin-top: 25px;
}

.well {
	height: 50px;
	margin-top: 9px;
}

.col-ms-12 {
	margin-top: -26px;
}

#col-sm-1 {
	width: 91px;
	margin-top: 25px;
}

div#col-sm-4 {
	margin-left: -23px;
	width: 17.333333%;
	margin-top: 12px;
}
.wellDIV{
	width:15px;height:34px;line-height:34px; float:left;
}
.form-group {
margin-bottom: -39px;
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
<script src="<%=basePath%>res/js/list.js"></script>
<script src="<%=basePath%>res/js/laydate/laydate.js"></script>
</head>

<body >
<jsp:include page="include/head.jsp" />
<div class="complexBox">
	<!-- 个人事件/其他事件 -->
	<jsp:include page="include/left.jsp" />
	<div class="customer">
	<form id="listForm" name="listForm"
		action="<%=basePath%>journal/list.do" method="post">
		<%--条件搜索	--%>
		<div class="well">
			<div class="col-ms-12">
				<div class="form-group">
					<label class="col-sm-1 control-label" id="col-sm-1"> 关键字： </label>
					<div class="col-sm-4" id="col-sm-4">
						<input class="form-control" type="text" name="keyword"
							placeholder="请输入操作人员名称" value="${keywordj}">
					</div>
				</div>
				<div class="form-group" style="margin-bottom: -204px;">
					<label class="col-sm-1 control-label" id="col-sm-1"
						style="margin-left: 337px;margin-top: -193px;"> 日期： </label>
					<div class="col-sm-2" id="col-sm-2"
						style="margin-left: 387px;width: 270px;margin-top: -205px;">
						<div class="input-group">
							<input type="text" class="form-control hasDatepicker"
								placeholder="yyyy-mm-dd" id="datepicker" name="keyword"
								value="${date1 }" onclick="laydate()"> <span
								class="input-group-addon"><i
								class="glyphicon glyphicon-calendar"></i> </span>
						</div>
					</div>
					<div class=""style="width: 2px;margin-left: 659px;margin-top: -201px;height: 34px;line-height: 34px;float: left;">至</div>
					<div class="col-sm-2" id="col-sm-2"
						style="margin-left: 674px;width: 270px;margin-top: -205px;">
						<div class="input-group">
							<input type="text" class="form-control hasDatepicker"
								placeholder="yyyy-mm-dd" id="datepicker" name="keyword"
								value="${date2 }" onclick="laydate()"> <span
								class="input-group-addon"><i
								class="glyphicon glyphicon-calendar"></i> </span>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="btn-list"
					style="margin-left: 980px;margin-top: -53px;">
					<button class="btn btn-info btn-metro" id="searchButton"
						style="margin-left: 33px;margin-top: -39px;">搜索</button>
				</div>
			</div>
		</div>
		<div class="table-responsive ">
			<table class="table table-info table-hover mb10"
				style="font-size: 13px;">
				<thead>
					<tr>
						<th><input type="checkbox" id="checkAll" class="allCheck" />
						</th>
						<th>操作人员</th>
						<th>操作类型</th>
						<th>操作内容</th>
						<th>操作系统</th>
						<th>登录IP</th>
						<th>操作时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pager.list}" var="journal" varStatus="index">
						<tr id="tr_${journal.id}">
							<td><input type="checkbox" name="ids" id="checkItem"
								value="${journal.id}">
							</td>
							<td>${journal.operationman}</td>
							<td>${journal.operationtype}</td>
							<td>${journal.operationtext}</td>
							<td>${journal.operationsys}</td>
							<td>${journal.loginip}</td>
							<td><fmt:formatDate value="${journal.lastupdate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="table-action">
								&nbsp;&nbsp; <a href="javascript:deletevJournal('${journal.id}');"
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
				onclick="deletejournals()" disabled="disabled">删除</button>
			<jsp:include page="pager.jsp" />
		</div>
		
	</form>
	</div>
	</div>
		
	<script type="text/javascript">
	//单个文档删除
	function deletevJournal(id) {
		if (confirm("您确定将此文件作废吗？")) {
			$.ajax({
				  type: 'POST',
				  url: '<%=basePath%>journal/delete.do',
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
	function deletejournals() {
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
					  url: '<%=basePath%>journal/deletes.do',
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
	</script>
</body>
</html>
