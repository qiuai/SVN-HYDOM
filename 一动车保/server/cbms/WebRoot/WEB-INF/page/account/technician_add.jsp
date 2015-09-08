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

<title>技师帐号添加</title>
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
	document.getElementById("account").focus();
};
	
function checkAccount() {
	
	var account = $("#account").val();
	$
			.post(
					"${pageContext.request.contextPath}/manage/technician/checkAccount",
					{
						account : account
					},
					function(data) {
						if(account == "" || account == null){
							$("#account_error").html("帐号不能为空");
							$("#account").next().val("");
						} else if (data == true && account!="${technician.account }") {//表示品牌存在
							$("#account_error").html("帐号已经存在");
							$("#account").next().val("");
						} else {
							$("#account_error").html("");
							$("#account").next().val("success");
						}
					});
					
}
	
function checkName() {
	
	var name = $("#name").val();
						if(name == "" || name == null){
							$("#name_error").html("姓名不能为空");
							$("#name").next().val("");
						} else {
							$("#name_error").html("");
							$("#name").next().val("success");
						};
					
}

function checkLevel() {
	var level = $("#level").val();
	if(level =="0"){
		$("#level_error").html("请选择星级");
		$("#level").next().val("");
	}else{
		$("#level").next().val("success");
	}
}

function checkPhoneNumber() {
	var reg = /^1\d{10}$/;
	var phoneNumber = $("#phonenumber").val();
	$
			.post(
					"${pageContext.request.contextPath}/manage/technician/checkPhoneNumber",
					{
						phoneNumber : phoneNumber
					},
					function(data) {
						if(phoneNumber == "" || phoneNumber == null){
							$("#phonenumber_error").html("手机号码不能为空");
							$("#phonenumber").next().val("");
						} else if(phoneNumber.length!=11){
							$("#phonenumber_error").html("请输入11位手机号码");
							$("#phonenumber").next().val("");
						}else if(!reg.test(phoneNumber)){
							$("#phonenumber_error").html("请输入正确格式的手机号码");
							$("#phonenumber").next().val("");
						}else if (data == true && phoneNumber!="${technician.phonenumber }") {//表示品牌存在
							$("#phonenumber_error").html("手机号码已经存在");
							$("#phonenumber").next().val("");
						} else{
							$("#phonenumber_error").html("");
							$("#phonenumber").next().val("success");
						}
					});
					
}

	$(document).ready(function(){
		$("#reset").bind("click",function() {
			$("#inputForm")[0].reset();
		});
	});

	function saveType(){
		checkAccount();
		checkName();
		checkPhoneNumber();
		checkLevel();
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
								<li><a href="">技师帐号添加</a></li>
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
							action="<%=basePath%>manage/technician/save" method="POST">
							<div class="panel-body nopadding">
								<div class="form-group">
									<label class="col-sm-4 control-label">技师帐号</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="account"
											onBlur="checkAccount()" placeholder="请填技师帐号" id="account">
										<input type="hidden" class="repeat"/>
										<span class="errorStyle" id="account_error"></span>
									</div>
								</div>
							
							<div class="form-group">
									<label class="col-sm-4 control-label">技师姓名</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="name"
											onBlur="checkName()" placeholder="请填技师姓名" id="name">
										<input type="hidden" class="repeat"/>
										<span class="errorStyle" id="name_error"></span>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-4 control-label">联系电话</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="phonenumber"
											onBlur="checkPhoneNumber()" placeholder="请填技师电话" id="phonenumber">
										<input type="hidden" class="repeat"/>
										<span class="errorStyle" id="phonenumber_error"></span>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">技师星级</label>
									<div class="col-sm-8">
										<select class="form-control" id="level" name="level">
											<option value="5.0" selected="selected" >5.0</option>
											<option value="4.5" >4.5</option>
											<option value="4.0" >4.0</option>
											<option value="3.5" >3.5</option>
											<option value="3.0" >3.0</option>
											<option value="2.5" >2.5</option>
											<option value="2.0" >2.0</option>
											<option value="1.5" >1.5</option>
											<option value="1.0" >1.0</option>
											<option value="0.5" >0.5</option>
											<option value="0">0</option>
										</select>
										<input type="hidden" class="repeat"/>
										<span class="errorStyle" id="level_error"></span>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-4 control-label">头像</label>
									<div class="col-sm-8">
										<div class="img_div">
											<img alt="" src="/${technician.imgPath }" onerror="<%=basePath %>/resource/image/default.png" id="show_img"/>
											<input type="hidden" name="imgPath" value="${technician.imgPath }"/>
										</div>
										<label>
											<span id="spanButtonPlaceholder"></span>
										</label>
										
									</div>
								</div>
								
							</div>
						</form>
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button id="addCate" class="btn btn-primary mr5" onclick="saveType()">提交</button>
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
	<jsp:include page="../common/imgUpload.jsp"></jsp:include>
	<script type="text/javascript">
		$('[data-toggle="tooltip"]').popover();
	</script>
</body>
</html>

