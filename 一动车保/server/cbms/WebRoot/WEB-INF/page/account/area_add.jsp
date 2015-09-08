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

<title>地区添加</title>
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

<!-- 公共JS -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resource/chain/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/respond.min.js"></script>
        <![endif]-->
        
<!-- 图片上传 -->
<script type="text/javascript"
	src="<%=basePath%>resource/swfupload/js/swfupload.js"></script>
<script type="text/javascript"
	src="<%=basePath%>resource/swfupload/js/swfupload.queue.js"></script>
<script type="text/javascript"
	src="<%=basePath%>resource/swfupload/js/fileprogress.js"></script>
<script type="text/javascript"
	src="<%=basePath%>resource/swfupload/js/handlers.js"></script>

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
		<jsp:include page="../common/head.jsp"></jsp:include>
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
								<li><a href=""><c:if test="${empty entity }">地区添加</c:if><c:if test="${!empty entity }">地区编辑</c:if></a></li>
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
							action="<%=basePath%>manage/area/save" method="POST">
							<input type="hidden" name="id" value="${entity.id }"/>
							<div class="panel-body nopadding">
								<div class="form-group">
									<label class="col-sm-4 control-label">上级地区</label>
									<div class="col-sm-8">
										<select id="serviceType" name="parent.id" style="width: 50%;">
											<option value="">顶级地区</option>
											<c:forEach items="${rootArea }" var="value">
												<optgroup label="${value.name }">
											    	<option value="${value.id }" <c:if test="${value.id eq entity.parent.id}">selected="selected"</c:if>>${value.name }</option>
											    	<c:if test="${value.children.size() > 0 }">
											    		<c:forEach items="${value.children }" var="childrenValue">
											    			<option value="${childrenValue.id }" <c:if test="${childrenValue.id eq entity.parent.id}">selected="selected"</c:if>>${childrenValue.name }</option>
											    		</c:forEach>
											    	</c:if>
											    </optgroup>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">地区名称</label>
									<div class="col-sm-8">
										<input type="text" name="name" class="form-control" maxlength="200" value="${entity.name }" onchange="checkAreaName();"/>
										<label id="nameLabel">
											<c:if test="${!empty entity }">
												<span class="success"></span>
											</c:if>
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">排序</label>
									<div class="col-sm-8">
										<input type="text" name="order" class="form-control" value="${entity.order }"/>
									</div>
								</div>
							</div>
							<div class="panel-footer">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button id="addCate" class="btn btn-primary mr5"
											onclick="saveType()" type="button" type="button">提交</button>
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
		
		var areaEntityName = "${entity.name}";
	
		$('[data-toggle="tooltip"]').popover();
		$(document).ready(function(){
			$("#serviceType").select2({
				
			});
		});
		function saveType(){
			
			var areaName = $("input[name='name']").val();
			if(areaName == ""){
				alert("请输入地区名称");
				return;
			}
			
			var id = $("input[name='id']").val();
			if(id != ""){//编辑
				if(areaEntityName != areaName){
					var m = $("#nameLabel").find("span.success");
					if(m.length<=0){
						alert("该名称已存在，请重新输入名称");
						return;
					}
				}
			}else{
				var m = $("#nameLabel").find("span.success");
				if(m.length<=0){
					alert("该名称已存在，请重新输入名称");
					return;
				}
			}
			
			$("#inputForm").submit();
		}
		function setErrorMessage(value,type){
			if(type == 1){
				var html = "<span style='color:red' class='error'>"+value+"</span>";
				$("#nameLabel").html(html);
			}else if (type == 2){
				var html = "<span style='color:green' class='success'>"+value+"</span>";
				$("#nameLabel").html(html);
			}
		}
		
		function checkAreaName() {
			setErrorMessage("",2);
			var username = $("input[name='name']").val();
			
			if(areaEntityName != ""){
				if(username == areaEntityName){
					return;
				}
			}
			
			var url = "<%=basePath%>manage/area/checkName";
			var parentId = $("#serviceType").val();
			var data = {
				prantId:parentId,
				content:username
			}
			$.post(url,data,function(result){
				if(result.status == "success"){
					setErrorMessage(result.message,2);
				}else{
					setErrorMessage(result.message,1);
				}
			},"json");
		}
		
	</script>
</body>
</html>
