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

<title>系统参数</title>
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
 <link href="${pageContext.request.contextPath}/resource/chain/css/bootstrap-timepicker.min.css" rel="stylesheet" />

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

<script
	src="${pageContext.request.contextPath}/resource/chain/js/bootstrap-timepicker.min.js"></script>
<!-- 公共JS -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resource/chain/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/respond.min.js"></script>
        <![endif]-->

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
								<li><a href="">系统参数</a></li>
							</ul>
						</div>
					</div>
					<!-- media -->
				</div>
				<!-- pageheader -->

				<div class="contentpanel">
					<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">系统服务时间</h4>
							</div>
							<form action="<%=basePath %>/manage/system/update" class="form-horizontal form-bordered">
								<div class="panel-body nopadding">
									<div class="form-group">
										<label class="col-sm-1 control-label">开始时间</label>
										<div class="col-sm-8">
											 <div class="input-group mb15 col-sm-2">
											 	<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
	                                         	<div class="bootstrap-timepicker"><input id="timepicker1" type="text" class="form-control"/></div>
											 </div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-1 control-label">结束时间</label>
										<div class="col-sm-8">
											 <div class="input-group mb15 col-sm-2">
											 	<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
	                                         	<div class="bootstrap-timepicker"><input id="timepicker2" type="text" class="form-control"/></div>
											 </div>
										</div>
									</div>
								</div>
								<div class="panel-heading" style="border-top: 1px solid #ddd;">
									<h4 class="panel-title">洗车服务区域说明</h4>
								</div>
								<div class="form-group">
									<div class="col-sm-8">
										<input type="text" name="content" class="form-control" maxlength="200" value="${entity.content }" id="content"/>
									</div>
								</div>
								<div class="panel-heading" style="border-top: 1px solid #ddd;">
									<h4 class="panel-title">app版本号</h4>
								</div>
								<div class="form-group">
									<div class="col-sm-8">
										<input type="text" name="version" class="form-control" maxlength="200" value="${entity.version }" id="version"/>
									</div>
								</div>
								<div class="panel-footer">
									<div class="row">
										<div class="col-sm-9 col-sm-offset-3">
											<button id="addCate" class="btn btn-primary mr5"
												onclick="saveType()" type="button">提交</button>
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
	
		$(document).ready(function(){
			jQuery('#timepicker1').timepicker({
				minuteStep: 1,
			    showMeridian: false,
			    defaultTime: '${entity.startDate}'
			 });
			
	   		jQuery('#timepicker2').timepicker({
	   			minuteStep: 1,
	   		    showMeridian: false,
	   		    defaultTime: '${entity.endDate}'
	   		 });
		});
		
		function saveType(){
			var url = "<%=basePath %>manage/system/update";
			var startDate = $("#timepicker1").val();
			var endDate = $("#timepicker2").val();
			var content = $("#content").val();
			var version = $("#version").val();
			var data = {
				startDate:startDate,
				endDate:endDate,
				content:content,
				version:version
			}
			$.post(url,data,function(result){
				if(result.status == "success"){
					alert("保存成功");
				}
			},"json");
		}
		
	</script>
</body>
</html>
