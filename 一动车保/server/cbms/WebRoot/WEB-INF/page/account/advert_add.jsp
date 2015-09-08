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

<title>首页广告添加</title>
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
<script type="text/javascript" charset="utf-8" src="<%=basePath %>resource/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath %>resource/ueditor/ueditor.all.min.js"></script>
<!-- 公共JS -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resource/chain/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/respond.min.js"></script>
        <![endif]-->
<script type="text/javascript">
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
	}
	
	$(document).ready(function(){
		$("#reset").bind("click",function() {
			$("#inputForm")[0].reset();
			$("#content").val("");
			ue.execCommand('cleardoc');
		});
	});

	function saveType(){
		$(function(){
			var html = ue.getContent();
	 		console.log(html);
	       	$("#content").val(html);
	        
			checkNull($("#title"));
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
/* .img_div{ */
/* 	display: inline-block; */
/* 	height: 60px; */
/* 	width: 60px; */
/* } */
/* .img_div img{ */
/* 	display: inline-block; */
/* 	height: 60px; */
/* 	width: 60px; */
/* } */
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
								<li><a href="">广告添加</a></li>
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
							action="<%=basePath%>manage/advert/save" method="POST">
							<div class="panel-body nopadding">
								<input type="hidden" name="id" id="id" value="${advert.id }"/>
								<div class="form-group">
									<label class="col-sm-4 control-label">标题</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="title" CHname="标题"
											onBlur="checkNull(this)" placeholder="请填写标题" id="title" value="${advert.title }">
										<input type="hidden" class="repeat"/>
										<span class="errorStyle" id="title_error"></span>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-4 control-label">内容</label>
									<div class="col-sm-8">
										<script id="editor" type="text/plain" style="width:600px;height:400px;"></script>
    									<textarea style="display:none;" id="content" name="content" >${advert.content}</textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-4 control-label">展示图</label>
									<div class="col-sm-8">
										<div class="img_div">
											<img alt="" src="<%=basePath %>${advert.imgPath}" onerror="" id="show_img">
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
									<button type="button" id="addCate" class="btn btn-primary mr5" onclick="saveType()">提交</button>
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
		var ue;
		//实例化编辑器
	    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	    //实例化编辑器
	    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	    createEditor(0);
        ue.ready(function() {
        var html = $("#content").val();
        ue.setContent(html);
        });
        function createEditor(type) {
        	if (type == 2) {
        		ue = UE.getEditor('editor', {
        			toolbars: [[
        	            'fullscreen', 'source', '|', 'undo', 'redo', '|',
        	            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
        	            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
        	            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
        	            'directionalityltr', 'directionalityrtl', 'indent', '|',
        	            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
        	            'link', 'unlink', '|',
        	            'horizontal', 'date', 'time', '|',
        	            'print', 'preview', 'searchreplace', 'help'
        	        ]]
        		});
        	} else if (type == 0) {
        		ue = UE.getEditor('editor', {
        			toolbars: [[
        	            'fullscreen', 'source', '|', 'undo', 'redo', '|',
        	            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
        	            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
        	            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
        	            'directionalityltr', 'directionalityrtl', 'indent', '|',
        	            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
        	            'link', 'unlink', '|',
        	            'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
        	            'simpleupload', 'insertimage', 'insertvideo', '|',
        	            'horizontal', 'date', 'time', '|',
        	            'print', 'preview', 'searchreplace', 'help'
        	        ]]
        		});
        	}
        }
	</script>
</body>
</html>