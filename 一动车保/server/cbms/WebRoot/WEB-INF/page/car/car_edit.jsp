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

<title>车型修改</title>
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
		$.post("${pageContext.request.contextPath}/manage/car/check",{name : name},function(data) {
			var n = "${car.name }";
			if(name == "" || name == null){
				$("#name_error").html("车型名称不能为空");
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

	$(document).ready(function(){
		$("#reset").bind("click",function() {
			$("#inputForm")[0].reset();
		});
	});

	function saveCar(){
		if($("#carBrandId").val()==""){
			alert("请选择车辆品牌");
			return;
		}
		
		if($("#carTypeId").val()==""){
			alert("请选择所属车系");
			return;
		}
		
		if($("#name").val() == ""){
			alert("请输入车辆名称");
			return;
		}
		
		if(!$("#name").next().val()=="success"){
			alert("请重新输入车辆名称");
			return;
		}
		
		
	/* 	checkName();
		var flag = true;
		$(".repeat").each(function(){
			if($(this).val()!="success"){
				flag = false;
			}
		});
		alert(1);
		alert(flag);
		if(!flag){
			alert(2);
			return;
		} */
		$("#inputForm").submit();
	}
	
	//获取车系
	function getCarType(e){
		var brandId = $(e).val();
		if(brandId == ""){
			addCarTypeHTML("");
			return;
		}
		var url = "<%=basePath%>/manage/car/getCarType";
		var data = {
			carBrandId:	brandId
		};
		$.post(url,data,function(result){
			if(result.status == "success"){
				addCarTypeHTML(result.message);
			}
		},"json");
		/* 
		var url ="${pageContext.request.contextPath}/manage/carType/add?carBrandId="+$(e).val();
		location.href=url; */
	}
	
	function addCarTypeHTML(value){
		var html = "<option value=''>请选择车系</option>";
		if(value != ""){
			for(var i in value){
				var subDa = value[i].subDate;
				var vhtml = "";
				for(var n in subDa){
					vhtml += "<option value='"+subDa[n].id+"'>"+subDa[n].name+"</option>";
				}
				var str = "<optgroup label='"+value[i].name+"'>"+vhtml+"</optgroup>";
				html += str;
			}
		}
		$("#carTypeId").html(html);
	}
	
</script>
<%-- <script type="text/javascript">	
	$(document).ready(function(){
		$("#carBrandId").change(function(){
			var h = "<%=basePath %>manage/car/edit?carBrandId="+$(this).val()+"&name="+$("#name").val()+"&id="+$("#id").val();
			location.href=h;
		});
	});
</script> --%>
<STYLE type="text/css">
.form-bordered div.form-group {
	width: 49%;
	padding: 5px 10px;
	border-top: 0px dotted #d3d7db;
}

.selects {
	height: 40px;
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
								<li><a href="">车系修改</a></li>
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
							action="<%=basePath %>manage/car/update" method="POST">
							<input type="hidden" value="${car.id }" name="id" id="id">
							<div class="panel-body nopadding">
								<div class="form-group">
									<label class="col-sm-4 control-label">车型名称</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="name"  value="${car.name }"
											onBlur="checkName()" placeholder="请填写车型名称" id="name">
										<input type="hidden" class="repeat"/>
										<span class="errorStyle" id="name_error"></span>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-4 control-label">所属品牌</label>
									<div class="col-sm-8">
										<select id="carBrandId" name="carBrandId" class="selects">
											<c:forEach items="${carBrands }" var="carBrand" >
												<option value="${carBrand.id}" <c:if test="${carBrand.id eq car.carBrand.id}">selected="selected"</c:if>>${carBrand.name }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-4 control-label">所属车系</label>
									<div class="col-sm-8">
										<select id="carTypeId" name="carTypeId" class="selects" onclick="selectCarType()">
												<option value="">请选择车型</option>
												<c:forEach var="value" items="${carTypes}">
													<optgroup label="${value.name }">
														<c:forEach var="subValue" items="${value.carTypeSet }">
															<option value="${subValue.id }"
																<c:if test="${subValue.id eq car.carType.id}">
																	selected="selected"
																</c:if>
															>${subValue.name }</option>
														</c:forEach>
													</optgroup>
												</c:forEach>
										</select>
										<input type="hidden" class="repeat"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-4 control-label">车型图片</label>
									<div class="col-sm-8">
										<div class="img_div">
											<img alt="" src="<%=basePath %>/${car.imgPath }" onerror="<%=basePath %>resource/image/default.png" id="show_img"/>
											<input type="hidden" name="imgPath" value="${car.imgPath}"/>
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
									<button id="addCate" class="btn btn-primary mr5" onclick="saveCar()" type="button">提交</button>
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
