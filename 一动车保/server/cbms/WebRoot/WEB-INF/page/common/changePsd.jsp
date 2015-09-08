<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="/WEB-INF/classes/META-INF/td.tld" prefix="td"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>Chain Responsive Bootstrap3 Admin</title>
<link
	href="${pageContext.request.contextPath}/resource/chain/css/style.default.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resource/chain/css/morris.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resource/chain/css/select2.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resource/css/manage.common.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/jquery-migrate-1.2.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/modernizr.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/pace.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/retina.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/jquery.cookies.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/select2.min.js"></script>
</head>
<body>
	<header>
		<%@ include file="/WEB-INF/page/common/head.jsp"%>
	</header>
	<section>
		<div class="mainwrapper">
			<%@ include file="/WEB-INF/page/common/left.jsp"%>

			<div class="mainpanel">
				<div class="pageheader">
					<div class="media">
						<div class="media-body">
							<ul class="breadcrumb">
								<li><a href=""><i class="glyphicon glyphicon-home"></i></a></li>
								<li><a href="">修改密码</a></li>
							</ul>
						</div>
					</div>
					<!-- media -->
				</div>
				<!-- pageheader -->
				<div class="contentpanel">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">修改密码</h4>
						</div>
					<form class="form-horizontal form-bordered" id="inputForm"
							action="" method="POST">
							<input type="hidden" name="id" value="${entity.id }"/>
							<div class="panel-body nopadding">
								<div class="form-group" style="width: 49%;">
									<label class="col-sm-4 control-label">旧密码</label>
									<div class="col-sm-8">
										<input type="password" name="oldpass" class="form-control" value="${entity.order }"/>
									</div>
								</div>
								<div class="form-group" style="width: 49%;">
									<label class="col-sm-4 control-label">新密码</label>
									<div class="col-sm-8">
										<input type="password" name="newpass" class="form-control" value="${entity.order }"/>
									</div>
								</div>
								<div class="form-group" style="width: 49%;">
									<label class="col-sm-4 control-label">再次输入密码</label>
									<div class="col-sm-8">
										<input type="password" name="repass" class="form-control" value="${entity.order }"/>
									</div>
								</div>
							</div>
							<div class="panel-footer">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button id="addCate" class="btn btn-primary mr5"
											onclick="saveChangePad()" type="button" type="button">确定</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="bottomwrapper">
					<%@ include file="/WEB-INF/page/common/bottom.jsp"%>
				</div>
			</div>
			<!-- mainpanel -->
		</div>
		<!-- mainwrapper -->
	</section>
	<script type="text/javascript">
		function saveChangePad(){
			var url = "<%=basePath%>manage/account/changePassword";
			var oldPass = $("input[name='oldpass']").val();
			var newPass = $("input[name='newpass']").val();
			var rePass = $("input[name='repass']").val();
			if(newPass == ""){
				return;
			}
			if(newPass != rePass){
				alert("两次输入的密码不一致");
				return;
			}
			var data = {
				oldPass:oldPass,
				newPass:newPass
			};
			$.post(url,data,function(result){
				if(result.status == "success"){
					alert("密码修改成功,请重新登录");
					exit(0);
				}else{
					alert(result.message);
				}
			},"json");
		}
	
	</script>
</body>
</html>
