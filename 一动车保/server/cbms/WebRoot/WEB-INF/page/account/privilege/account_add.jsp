<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">

<title>系统帐号添加</title>
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
	
<!-- 验证框架 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/jquery.maskedinput-1.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/validate/account.js"></script>
	
<!-- 公共JS -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resource/chain/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/respond.min.js"></script>
        <![endif]-->
<script type="text/javascript">
	function checkUsername() {
		var username = $("#username").val();
		$
				.post(
						"${pageContext.request.contextPath}/manage/account/checkUsername",
						{
							username : username
						},
						function(data) {
							if (data == 0 && username != "" && username != null) {//表示 帐户存在
								$("#username_error").html("用户名已经存在");
								$("#repeat").val("");
							} else {
								$("#username_error").html("");
								$("#repeat").val("success");
							}
						});
	}
</script>
<STYLE type="text/css">
.form-bordered div.form-group {
	width: 49%;
	padding: 5px 10px;
	border-top: 0px dotted #d3d7db;
}
</STYLE>
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
								<li><a href="">帐号添加</a></li>
							</ul>
						</div>
					</div>
					<!-- media -->
				</div>
				<!-- pageheader -->

				<div class="contentpanel">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">基本信息</h4>
						</div>
						<form class="form-horizontal form-bordered" id="myform" 	action="${pageContext.request.contextPath}/manage/account/save" method="POST">
							<div class="panel-body nopadding">
								<div class="form-group">
									<label class="col-sm-4 control-label">用户名</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="username" onkeyup="checkUsername()" placeholder="请填写用户名" id="username">
										<span class="errorStyle" id="username_error"></span>
										<s:hidden name="repeat" id="repeat" value="" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-4 control-label">密码</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="password" placeholder="密码"> <span></span>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">昵称</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="nickname" placeholder="昵称"> <span></span>
									</div>
								</div>
							</div>
							<div class="panel-heading" style="border-top: 1px solid #ddd;">
								<h4 class="panel-title">角色选择</h4>
							</div>
							<div class="panel-body nopadding">
								<div class="form-group">
									<div class="col-md-12 col-sm-12" style="padding:35px;">
										<c:forEach items="${groups}" var="group" varStatus="s">
											<div class="col-md-3 col-sm-4">
												<input type="checkbox" name="gids" value="${group.id}" /> <a
													class="pls" href="#" data-toggle="tooltip"
													data-placement="bottom" data-title="${group.name}"
													data-trigger="focus"
													data-content="
					      		<c:forEach items="${group.privileges}"  var="p" >
										【${p.name}】    
								</c:forEach> 
								">${group.name}</a>
											</div>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="panel-footer">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button id="addCate" class="btn btn-primary mr5" type="submit">提交</button>
										<button class="btn btn-dark" type="reset">重置</button>
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
		$('[data-toggle="tooltip"]').popover();
	</script>
</body>
</html>
