<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath() + "/";
	String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>联系人列表</title>
<style type="text/css">
label.col-sm-4.control-label {
width: 33%;
margin-top: 0px;
}
.row {
width: 99%;
}
#persion {
width: 37.333333%;
}
.wellDIV{
	width:15px;height:34px;line-height:34px; float:left;
}
</style>

	<link href="<%=basePath %>bootstrap/css/bootstrap.css" rel="stylesheet" media="screen" /> 
	<link href="<%=basePath %>bootstrap/css/style.default.css" rel="stylesheet" />
	<link href="<%=basePath %>res/css/pager.css" rel="stylesheet" />
	<link rel="stylesheet" href="<%=basePath%>res/css/head.css" media="screen" />
     <link rel="stylesheet" href="<%=basePath %>bootstrap/css/jquery.gritter.css" />
     <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/select2.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/style.css"/>
    
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
	<div class="panel panel-primary-head" >
	
		<div class="row" style="margin-top: 10px;">
			<div class="col-md-12">
				<div class="panel panel-primary"style="margin-left: -1%;">
					<div class="panel-heading">
							<h3 class="panel-title">个人资料</h3>
					</div>
				</div>
			</div>

			<div class="panel-body">
				<!-- <form class="form-horizontal"> -->
					<div class="col-md-12">
						<div class="col-sm-4" id="persion">
							<div class="form-group">
								<label class="col-sm-4 control-label"> 姓名： </label>
								<div class="col-sm-8">${persion.pername }</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label"> 员工编号： </label>
								<div class="col-sm-8">${persion.workcode }</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label"> 部门/岗位： </label>
								<div class="col-sm-8">${persion.gwinfo.gwiname }</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label"> 性别： </label>
								<div class="col-sm-8">${persion.sex }</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">出身年月： </label>
								<div class="col-sm-8"><fmt:formatDate value="${persion.birthday }" pattern="yyyy-MM-dd"/></div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">身份证： </label>
								<div class="col-sm-8">${persion.sfz }</div>
							</div>
						</div> 
						
						<div class="col-sm-4 col-md-offset-2" id="persion">
								<div class="form-group">
								<label class="col-sm-4 control-label"> 联系电话： </label>
								<div class="col-sm-8">${persion.pertel }</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">  联系邮箱： </label>
								<div class="col-sm-8">${persion.permail }</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label"> 上级： </label>
								<div class="col-sm-8">${persion.gwinfo.root.gwiname }</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label"> 入职时间： </label>
								<div class="col-sm-8"><fmt:formatDate value="${persion.indate }" pattern="yyyy-MM-dd"/></div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label"> 在职状态： </label>
								<c:if test="${persion.currflag==-1 }">
									<div class="col-sm-8">开除</div>
								</c:if>
								<c:if test="${persion.currflag==0 }">
									<div class="col-sm-8">离职</div>
								</c:if>
								<c:if test="${persion.currflag==1 }">
									<div class="col-sm-8">在职(试用)</div>
								</c:if>
								<c:if test="${persion.currflag==2 }">
									<div class="col-sm-8">在职</div>
								</c:if>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label"> 联系地址： </label>
								<div class="col-sm-8">${persion.peradd }</div>
							</div>
						</div>
						
					</div>
			</div>

			<div class="col-md-12">
				<div class="panel panel-primary"style="margin-left: -1%;">
					<div class="panel-heading">
							<h3 class="panel-title">描述信息</h3>
					</div>
				</div>
			</div>
			<div class="panel-body">
				<div class="form-group">
					<label class="col-sm-1 control-label"> 备注： </label>
					<div class="col-sm-10">
						<textarea class="form-control" rows="5" readonly="readonly">${persion.bz }</textarea>
					</div>
				</div>
				<!-- <div class="btn-list btns col-sm-2">
					<button class="btn btn-primary deleteButton">保存</button>
					<button class="btn btn-danger deleteButton">取消</button>
				</div> -->
			</div>
		</div>
	</div>
	</div>
</div>
</body>
</html>