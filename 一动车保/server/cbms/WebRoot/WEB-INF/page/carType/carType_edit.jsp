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

<title>车型管理</title>
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
		$
				.post(
						"${pageContext.request.contextPath}/manage/carType/check",
						{
							name : name
						},
						function(data) {
							var n = "${carType.name}";
							if(name == "" || name == null){
								$("#name_error").html("车系名称不能为空");
								$("#name").next().val("");
							} else if (data == true && n!=$("#name").val()) {//表示品牌存在
								$("#name_error").html("车型名称已经存在");
								$("#name").next().val("");
							} else {
								$("#name_error").html("");
								$("#name").next().val("success");
							}
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

	$(document).ready(function(){
		$("#reset").bind("click",function() {
			$("#inputForm")[0].reset();
		});
		$("#name").blur(function(){
			getAbridge();
			getPinYin();
		});
	});
	
	function checkJp(v){
		if(v.value == ''){
			$("#jp_error").html("拼音缩写不能为空");
			$(v).next().val("");
        	return false;
	    } else {
			$("#jp_error").html("");
			$(v).next().val("success");
		}
	}
	
	function checkQp(v){
		if(v.value == ''){
			$("#qp_error").html("全拼不能为空");
			$(v).next().val("");
        	return false;
	    } else {
			$("#qp_error").html("");
			$(v).next().val("success");
		}
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

	function saveType(){
		if($("#entityLevel").val() == "2"){//小分类
			if($("#parentId").val() == ""){
				$("#carType_error").html("该分类为子分类，请选择上级分类");
				return;
			}
		}
		checkName();
		checkQp($("#qp")[0]);
		
		var inputs = $(".repeat");

		for(var i=0;i<inputs.length;i++){
			var input = $(inputs[i]).val();
			if(input == ""){
				setErrorHTML(i);
				return;
			}
		}
		$("#inputForm").submit();
	}
	
	function setErrorHTML(obj){
		if(obj == 0){
			$("#name_error").html("名称不能为空");
		}
		if(obj == 1){
			$("#jp_error").html("缩写不能为空");
		}
		if(obj == 2){
			$("#qp_error").html("全拼不能为空");
		}
	}
	//获取车系
	function getCarType(e){
		if($("#entityLevel").val() != "2"){
			return;
		}
		var brandId = $(e).val();
		if(brandId == ""){
			addCarTypeHTML("");
			return;
		}
		var url = "<%=basePath%>/manage/carType/getCarType";
		var data = {
			carBrandId:	brandId
		};
		$.post(url,data,function(result){
			if(result.status == "success"){
				addCarTypeHTML(result.message);
			}
		},"json");
	}
	
	function addCarTypeHTML(value){
		var html = "<option value=''>顶级分类</option>";
		if(value != ""){
			for(var i in value){
				html += "<option value='"+value[i].id+"'>"+value[i].name+"</option>";
			}
		}
		$("#parentId").html(html);
	}
</script>
<STYLE type="text/css">
.form-bordered div.form-group {
	width: 49%;
	padding: 5px 10px;
	border-top: 0px dotted #d3d7db;
}

.selects {
	height: 40px;
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
								<li><a href="">车系编辑</a></li>
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
							action="<%=basePath %>manage/carType/update" method="POST">
							<input type="hidden" value="${carType.id }" name="id" id="id"/>
							<input type="hidden" value="${carType.level }" id="entityLevel"/>
							<div class="panel-body nopadding">
								<div class="form-group">
									<label class="col-sm-4 control-label">车系名称</label>
									<div class="col-sm-8">
										<input type="text" class="form-control repeat" name="name"
											onBlur="checkName()" placeholder="请填写车系名称" id="name" value="${carType.name}">
										<span class="errorStyle" id="name_error"></span>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-4 control-label">拼音缩写</label>
									<div class="col-sm-8">
										<input type="text" class="form-control repeat" name="jp" maxlength="1" onblur="checkJp(this)" 
										onkeyup="checkJp(this)" placeholder="请填写品牌拼音缩写" id="jp" value="${carType.jp }">
										<span class="errorStyle" id="jp_error"></span>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-4 control-label">全拼</label>
									<div class="col-sm-8">
										<input type="text" class="form-control repeat" name="qp" onblur="checkQp(this)" 
										onkeyup="checkQp(this)" placeholder="请填写品牌全拼" id="qp" value="${carType.qp }">
										<span class="errorStyle" id="qp_error"></span>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-4 control-label">所属品牌</label>
									<div class="col-sm-8">
										<select id="carBrandId" name="carBrandId" class="selects" onchange="getCarType(this)">
											<c:forEach items="${carBrands }" var="carBrand" >
												<option value="${carBrand.id}" 
													<c:if test="${carBrand.id eq carType.carBrand.id}">selected="selected"</c:if>
												>${carBrand.name }</option>
											</c:forEach>
										</select>
										<span class="errorStyle" id="carBrand_error"></span>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-4 control-label">上级分类</label>
									<div class="col-sm-8">
										<select id="parentId" name="parentId" class="selects">
											<option value="" selected="selected">顶级分类</option>
											<c:if test="${carTypes.size() > 0}">
												<c:forEach items="${carTypes }" var="parent" >
													<option value="${parent.id}" <c:if test="${parent.id eq carType.parent.id}">selected="selected"</c:if>>${parent.name }</option>
												</c:forEach>
											</c:if>
										</select>
										<span class="errorStyle" id="carType_error"></span>
									</div>
								</div>
							</div>
						</form>
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button id="addCate" class="btn btn-primary mr5" onclick="saveType()" type="button">提交</button>
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
	<script type="text/javascript">
		$('[data-toggle="tooltip"]').popover();
	</script>
</body>
</html>
