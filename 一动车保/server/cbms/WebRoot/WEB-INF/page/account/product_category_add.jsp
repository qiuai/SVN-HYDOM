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
.rdio {
	margin-top: 10px;
	width: 50px;
	display: inline-block;
}
#categoryDiv select{
	margin-top: 10px;
	margin-right: 10px;
}
#brandDiv label{
	  margin-top: 5px;
	  margin-right: 10px;
}
#hotProductCategory label{
	  margin-top: 5px;
	  margin-right: 10px;
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
								<li><a href="">商品分类添加</a></li>
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
						<form class="form-horizontal form-bordered" id="inputForm" action="<%=basePath%>manage/productCategory/save" method="POST">
							
							<div class="panel-body nopadding">
								<div class="form-group">
									<label class="col-sm-4 control-label">名称</label>
									<div class="col-sm-8">
										<input type="text" name="name" class="form-control" maxlength="200" value="" onchange="checkName();"/>
										<label id="labelName">
											<span class='success'></span>
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">上级分类</label>
									<div class="col-sm-8" id="categoryDiv">
										<select name="parent.id" id="parentId">
											<option value="">顶级分类</option>
											<c:forEach var="category" items="${categorys }">
												<option value="${category.id }">
													<c:if test="${category.grade gt 0}">
														<c:forEach begin="1" end="${category.grade }">
															&nbsp;&nbsp;
														</c:forEach>
													</c:if>
													${category.name}
												</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">绑定服务</label>
									<div class="col-sm-8" id="categoryDiv">
										<select name="serviceType.id">
											<option value="">无</option>
											<c:forEach var="serviceType" items="${serviceTypes }">
												<option value="${serviceType.id }" class="<c:if test="${entity.productCategorySet ge 0 }">hasValue</c:if>">
													${serviceType.name}
												</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">筛选品牌</label>
									<div class="col-sm-8" id="brandDiv">
										<c:forEach var="brand" items="${brands }">
											<label><input type="checkbox" name="brands" value="${brand.id }"/>${brand.name }</label>
										</c:forEach>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">展示图片</label>
									<div class="col-sm-8">
										<div class="img_div">
											<img alt="" src="
											<c:if test="${!empty entity}"><%=basePath %>/${entity.imgPath }</c:if>
											" onerror="<%=basePath %>/resource/image/default.png" id="show_img">
											<input type="hidden" name="imgPath" value="${entity.imgPath }"/>
										</div>
										<label> <%-- style="position: absolute;  float: right;  margin-left: 20%;" --%>
											<span id="spanButtonPlaceholder"></span>
										</label>
									</div>
								 </div>
								<div class="form-group">
									<label class="col-sm-4 control-label">类型</label>
									<div class="col-sm-8" id="hotProductCategory">
										<label><input type="checkbox" value="1" name="hotProductCategory"/>热卖分类</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">排序</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="order" value=""/>
									</div>
								</div>
							</div>
							<div class="panel-footer">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button id="addCate" class="btn btn-primary mr5"
											onclick="saveForm()" type="button">提交</button>
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
		<jsp:include page="../common/imgUpload.jsp"></jsp:include>
	</section>
	<script type="text/javascript">
		var base = "<%=basePath%>";
		$('[data-toggle="tooltip"]').popover();
		$(document).ready(function(){
			
		});
		
		function setErrorMessage(value,type){
			if(type == 1){
				var html = "<span style='color:red' class='error'>"+value+"</span>";
				$("#labelName").html(html);
			}else if (type == 2){
				var html = "<span style='color:green' class='success'>"+value+"</span>";
				$("#labelName").html(html);
			}else{
				
			};
		}
		
		function checkName() {
			setErrorMessage("",2);
			var name = $("input[name='name']").val();
			var parentId = $("#parentId").val();
			if(name == ""){
				return;
			}
			var url = "<%=basePath%>manage/productCategory/checkName";
			var data = {
				name : name,
				parentId:parentId
			};
			$.post(url,data,function(result){
				if(result.status == "success"){
					setErrorMessage(result.message,2);
				}else{
					setErrorMessage(result.message,1);
				}
			},"json");
		};
		
		function saveForm(){
			var name = $("input[name='name']").val();
			if(name == ""){
				alert("请输入名称");
				return;
			}
			var brands = $("input[name='brands']:checked");
			if(brands.length <= 0){
				alert("请选择品牌");
				return;
			}
			
			setErrorMessage("",2);
			var parentId = $("#parentId").val();
			var url = "<%=basePath%>manage/productCategory/checkName";
			var data = {
				name : name,
				parentId:parentId
			};
			$.post(url,data,function(result){
				if(result.status == "success"){
					$("#inputForm").submit();
				}else{
					alert("此名称已存在，请重新输入");
				}
			},"json");
		}
		
	</script>
</body>
</html>
