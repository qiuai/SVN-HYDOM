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

<title>商品标签修改</title>
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
	window.onload = function(){
    	document.getElementById("name").focus();
	}

	function checkName() {
		
		var name = $("#name").val();
		$
				.post(
						"${pageContext.request.contextPath}/manage/productLabel/check",
						{
							name : name
						},
						function(data) {
							if(name == "" || name == null){
								$("#name_error").html("品牌不能为空");
								$("#name").next().val("");
							} else if (data == true && name!="${productlabel.labelName }") {//表示品牌存在
								$("#name_error").html("品牌已经存在");
								$("#name").next().val("");
							} else {
								$("#name_error").html("");
								$("#name").next().val("success");
							}
						});
						
	}
	
	
	
	$(document).ready(function(){
		$("#reset").bind("click",function() {
			$("#inputForm")[0].reset();
			checkName();
		});
		
		
	});

	function saveType(){
		
			checkName();
			$(function(){
				var flag = true;
				$(".repeat").each(function(){
					if($(this).val()!="success") flag = false;
				});
				if(flag){
					$("#inputForm").submit();
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
.img_div{
	display: inline-block;
	height: 60px;
	width: 60px;
}
.img_div img{
	display: inline-block;
	height: 60px;
	width: 60px;
}
.rdio{
	width:80px;
	float: left;
	top:8px;
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
								<li><a href="">标签修改</a></li>
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
						<form class="form-horizontal form-bordered" id="inputForm"
							action="<%=basePath%>manage/productLabel/update" method="POST">
							<input type="hidden" name="id" value="${productlabel.id }">
							<div class="panel-body nopadding">
								<div class="form-group">
									<label class="col-sm-4 control-label">标签名称</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="labelName"
											onBlur="checkName()" placeholder="请填写商品标签名称" id="name" value="${productlabel.labelName }">
										<input type="hidden" class="repeat"/>
										<span class="errorStyle" id="name_error"></span>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-4 control-label">是否启用</label>
									<div class="col-sm-8">
										<div class="rdio rdio-default">
											<input type="radio" name="labelStats" id="radioZH-One" value="1" <c:if test="${true eq productlabel.labelStats}">checked="checked"</c:if>>
											
											<label for="radioZH-One">是</label>
										</div>
										<div class="rdio rdio-default">
											<input type="radio" name="labelStats" id="radioZH-two" value="0"<c:if test="${false eq productlabel.labelStats}">checked="checked"</c:if>>
											<label for="radioZH-two">否</label>
										</div>
									</div>
								</div>
								
							</div>
						</form>
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button id="updateCate" class="btn btn-primary mr5" onclick="saveType()">提交</button>
									<button id="reset" class="btn btn-dark" type="reset">重置</button>
								</div>
							</div>
						</div>
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
</body>
</html>
