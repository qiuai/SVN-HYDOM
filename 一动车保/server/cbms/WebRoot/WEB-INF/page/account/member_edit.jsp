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

<title></title>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/chain/js/laydate/laydate.js"></script>

	
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
		$.post("${pageContext.request.contextPath}/manage/serviceType/checkUsername.action",
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
.form-panle-div{
	width: 49%;
	display: inline-block;
}
.form-panle-div div.form-group{
	width: 100%;
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
.rdio {
	margin-top: 10px;
	width: 50px;
	display: inline-block;
}
#area_div_select select{
	margin-top: 10px;
}
#memberRankSelect{
	margin-top: 10px;
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
								<li><a href="">会员编辑</a></li>
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
							action="<%=basePath%>manage/member/update" method="POST">
							<input type="hidden" name="id" value="${entity.id }"/>
							<div class="panel-body nopadding">
								<div class="form-panle-div">
									<div class="form-group">
										<label class="col-sm-4 control-label">手机号码</label>
										<div class="col-sm-8">
											<label class="control-label">${entity.mobile }</label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">邮箱</label>
										<div class="col-sm-8">
											<label class="control-label">${entity.email }</label>
										</div>
									</div>
									<%-- <div class="form-group">
										<label class="col-sm-4 control-label">密码</label>
										<div class="col-sm-8">
											<input type="password" name="password" class="form-control" value="${entity.password }"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">重复密码</label>
										<div class="col-sm-8">
											<input type="password" class="form-control" id="rePassword"/>
										</div>
									</div> --%>
									<div class="form-group">
										<label class="col-sm-4 control-label">积分</label>
										<div class="col-sm-8">
											<label class="control-label">${entity.amount }</label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">余额</label>
										<div class="col-sm-8">
											<label class="control-label">${entity.money }</label>
										</div>
									</div>
								</div>
								<div class="form-panle-div">
									<div class="form-group">
										<label class="col-sm-4 control-label">用户姓名</label>
										<div class="col-sm-8">
											<label class="control-label">${entity.name }</label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">性别</label>
										<div class="col-sm-8">
											<label class="control-label">
												<c:if test="${entity.gender eq 0}">男</c:if>
												<c:if test="${entity.gender eq 1}">女</c:if>
											</label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">出生日期</label>
										<div class="col-sm-8">
											<label class="control-label">
												<fmt:formatDate value="${entity.birth }" pattern="yyyy-MM-dd"/>
											</label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">地区</label>
										<div class="col-sm-8">
											<label class="control-label">
												${entity.area.treeFull }
											</label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">地址</label>
										<div class="col-sm-8">
											<label class="control-label">
												${entity.address }
											</label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">会员等级</label>
										<div class="col-sm-8">
											<select id="memberRankSelect" name="memberRank.id">
												<option value="">请选择</option>
												<c:forEach var="memberRank" items="${memberRanks }">
													<option value="${memberRank.id }" <c:if test="${memberRank.id eq entity.memberRank.id}">selected="selected"</c:if> >${memberRank.name }</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">设置</label>
									<div class="col-sm-8">
										<div class="rdio rdio-default" style="width: 140px;">
											<input type="radio" name="status" id="status-two" value="1" <c:if test="${entity.status eq 1}">checked="checked"</c:if>
												><label for="status-two">启用</label>
										</div>
										<div class="rdio rdio-default" style="width: 140px;">
											<input type="radio" name="status" id="status-One" value="0" <c:if test="${entity.status eq 0}">checked="checked"</c:if>
												><label for="status-One">停用</label>
										</div>
									</div>
								</div>
							</div>
							<div class="panel-heading" style="border-top: 1px solid #ddd;">
								<h4 class="panel-title">积分</h4>
							</div>
							<div class="panel-body nopadding">
								<div class="form-group">
									<label class="col-sm-4 control-label">当前积分</label>
									<div class="col-sm-8">
										<label class="control-label">
											${entity.amount }
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">积分调整(增/减)</label>
									<div class="col-sm-8">
										<input type="text" name="adjustAmount" class="form-control" placeholder="正数代表增加积分,负数代表减少积分"/>
									</div>
								</div>
							</div>
							<div class="panel-heading" style="border-top: 1px solid #ddd;">
								<h4 class="panel-title">预存款</h4>
							</div>
							<div class="panel-body nopadding">
								<div class="form-group">
									<label class="col-sm-4 control-label">当前存款</label>
									<div class="col-sm-8">
										<label class="control-label">
											${entity.money }
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">余额调整(充值/扣除)</label>
									<div class="col-sm-8">
										<input type="text" name="adjustMoney" class="form-control" placeholder="正数代表预存款充值,负数代表预存款扣除"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">备注</label>
									<div class="col-sm-8">
										<input type="text" name="adjustRemark" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="panel-footer">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button id="addCate" class="btn btn-primary mr5"
											onclick="saveForm()" type="button">提交</button>
										<button class="btn btn-dark" type="button" onclick="goBack();">返回列表</button>
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
		var base = "<%=basePath%>";
		$('[data-toggle="tooltip"]').popover();
		$(document).ready(function(){
			$("#serviceType").select2({
				minimumResultsForSearch : -1,
				allClear : true
			});
		});
		function saveForm(){
			
			
			/* var password = $("input[name='password']").val();
			if(password == ""){
				alert("请输入密码");
				return;
			}
			var rePassword = $("#rePassword").val();		
			
			if(password == rePassword){
				alert("两次输入的密码不一致，请重新输入");
				return;
			} */
			
			var amount = $("input[name='adjustAmount']").val();
			var money = $("input[name='adjustMoney']").val();
			
			if(isNaN(amount)){
				alert("调整积分请输入数字");
				return;
			}
			
			if(isNaN(money)){
				alert("调整余额请输入数字");
				return;
			}
			
			var url = $("#inputForm").attr("action");
			var data = $("#inputForm").serialize();
			
			$.post(url,data,function(result){
				if(result.status == "success"){
					window.location.reload(true);
				}else{
					alert("提交失败");
				}
			},"json");
		}
		function goBack(){
			window.history.back(-1);
		}
	
	</script>
</body>
</html>
