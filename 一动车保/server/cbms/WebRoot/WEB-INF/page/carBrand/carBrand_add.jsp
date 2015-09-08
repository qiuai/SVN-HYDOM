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

<title>汽车品牌添加</title>
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
	
<!-- 公共JS -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resource/chain/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/respond.min.js"></script>
        <![endif]-->
<script type="text/javascript">
	function checkName() {
		var name = $("#name").val();
		$.post("${pageContext.request.contextPath}/manage/carBrand/check",{name : name},function(data) {
				if(name == "" || name == null){
					$("#name_error").html("品牌不能为空");
					$("#name").next().val("");
				} else if (data == true) {//表示品牌存在
					$("#name_error").html("品牌已经存在");
					$("#name").next().val("");
				} else {
					$("#name_error").html("");
					$("#name").next().val("success");
				}
				getInitial();
				getAbridge();
				getPinYin();
		});
	}
	
	function getInitial() {
		var name = $("#name").val();
		$
				.post(
						"${pageContext.request.contextPath}/manage/carBrand/getInitial",
						{
							name : name
						},
						function(data) {
							$("#initial").val(data.toUpperCase());
						});
	}
	
	function getAbridge() {
		var name = $("#name").val();
		$
				.post(
						"${pageContext.request.contextPath}/manage/carBrand/getAbridge",
						{
							name : name
						},
						function(data) {
							$("#jp").val(data);
						});
	}
	
	function getPinYin() {
		var name = $("#name").val();
		$
				.post(
						"${pageContext.request.contextPath}/manage/carBrand/getPinYin",
						{
							name : name
						},
						function(data) {
							$("#qp").val(data);
						});
	}
	
	function checkNull(v){
		if($(v).is(':visible')){
			if($(v).val() == ''){
				$("#"+$(v).attr("name")+"_error").html($(v).attr("CHname")+"不能为空");
				$(v).next().val("");
		    } else {
				$("#"+$(v).attr("name")+"_error").html("");
				$(v).next().val("success");
			}
		}
		if($(v).attr("name")=="initial") $(v).val($(v).val().toUpperCase());
	}
	
	$(document).ready(function(){
		$("#reset").bind("click",function() {
			$("#inputForm")[0].reset();
		});
	});

	function saveType(){
		$(function(){
			checkName();
			checkNull($("#initial"));
			checkNull($("#jp"));
			checkNull($("#qp"));
				
			var name = $("#name").val();
			$.post("${pageContext.request.contextPath}/manage/carBrand/check",{name : name},function(data) {
					if(name == "" || name == null){
						$("#name_error").html("品牌不能为空");
						$("#name").next().val("");
					} else if (data == true) {//表示品牌存在
						$("#name_error").html("品牌已经存在");
						$("#name").next().val("");
					} else {
						$("#inputForm").submit();
					}
			});
			
			//$("#inputForm").submit();
			
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
								<li><a href="">品牌添加</a></li>
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
							action="<%=basePath%>manage/carBrand/save" method="POST">
							<div class="panel-body nopadding">
								<div class="form-group">
									<label class="col-sm-4 control-label">品牌名称</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="name"
											onBlur="checkName()" placeholder="请填写汽车品牌名称" id="name">
										<input type="hidden" class="repeat"/>
										<span class="errorStyle" id="name_error"></span>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-4 control-label">拼音首字母</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="initial" maxlength="1" onblur="checkNull(this)" 
										onkeyup="checkNull(this)" placeholder="请填写品牌拼音首字母" id="initial" CHname="拼音首字母">
										<input type="hidden" class="repeat"/>
										<span class="errorStyle" id="initial_error"></span>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-4 control-label">拼音缩写</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="jp" onblur="checkNull(this)" CHname="拼音缩写" 
										onkeyup="checkNull(this)" placeholder="请填写品牌拼音缩写" id="jp">
										<input type="hidden" class="repeat"/>
										<span class="errorStyle" id="jp_error"></span>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-4 control-label">全拼</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="qp" onblur="checkNull(this)" CHname="全拼"
										onkeyup="checkNull(this)" placeholder="请填写品牌全拼" id="qp">
										<input type="hidden" class="repeat"/>
										<span class="errorStyle" id="qp_error"></span>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-4 control-label">车标图片</label>
									<div class="col-sm-8">
										<div class="img_div">
											<img alt="" src="" onerror="<%=basePath %>/resource/image/default.png" id="show_img">
											<input type="hidden" name="imgPath"/>
										</div>
										<label> <%-- style="position: absolute;  float: right;  margin-left: 20%;" --%>
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
	<!-- 上传图片页面 -->
	<jsp:include page="../common/imgUpload.jsp"></jsp:include>
	<script type="text/javascript">
		$('[data-toggle="tooltip"]').popover();
	</script>
</body>
</html>