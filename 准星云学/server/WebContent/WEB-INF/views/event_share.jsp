<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath() + "/";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">


<title>分享 事件</title>
<link href="<%=basePath%>bootstrap/css/style_account.css"
	rel="stylesheet" />
<link href="<%=basePath%>bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen" />

<link href="<%=basePath%>bootstrap/css/jquery.tagsinput.css"
	rel="stylesheet" />
<style type="text/css">
#share {
	margin-right: 20px;
	float: right;
	margin-top: -18px;
}

.share-div {
	margin-top: 11px;
	margin-bottom: -9px;
	/* border: 1px #cccccc solid; */
	border-radius: 4px;
	height: 42px;
}

.col-sm-5 {
	float: left;
	margin-left: 36px;
	margin-top: -28px;
	width: 99%;
	border-radius: 4px;
}

ul.holder {
	width: 99%;
}
</style>
</head>
<body>
	<div class="panel panel-primary widget-newsletter">
		<div class="panel-heading">
			<h1 class="panel-title">分享 事件</h1>
		</div>
		<form name="inputForm" id="inputForm" action="" method="post">
			<div class="panel-body" style="border: 0px white solid;">
				<p>请完成事件分享</p>

				<div class="errorForm"></div>
				<b><label>事件标题：</label> </b>
				<textarea rows="4" cols="" name="dicname"
					class="form-control mt10 mb10">${event.eventsummary}</textarea>
				<%--	获取事件的id				--%>
				<input class="eventId" id="eventId" type="hidden"
					value="${event.id}">
				<%--				输入查询，进行关键字搜索--%>
				<div class="share-div">
					<label>分享给:</label>
					<div class="col-sm-12">
						<div class="form-group">
							<div class="col-sm-5" id="dictionDiv">
								<select class="form-control" id="dicname" name="dicname" required></select>
							</div>
						</div>
					</div>
				</div>
				<button class="btn btn-success btn-block" onclick="share();"
					style="margin-top: 10px;">确认分享</button>
			</div>
		</form>
	</div>

	<script src="<%=basePath%>bootstrap/js/jquery-1.11.1.min.js"></script>
	<script src="<%=basePath%>bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>bootstrap/js/modernizr.min.js"></script>
	<script src="<%=basePath%>bootstrap/js/pace.min.js"></script>
	<script src="<%=basePath%>bootstrap/js/retina.min.js"></script>
	<script src="<%=basePath%>bootstrap/js/jquery.gritter.min.js"></script>
	<script src="<%=basePath%>bootstrap/js/jquery.cookies.js"></script>
	<script src="<%=basePath%>bootstrap/js/jquery.validate.min.js"></script>
	<script src="<%=basePath%>bootstrap/js/jquery.form.js"></script>
	<script src="<%=basePath%>bootstrap/js/custom.js"></script>

	<script src="<%=basePath%>bootstrap/js/jquery.fcbkcomplete.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			// Error Message In One Container
			$("#inputForm").validate({
				errorLabelContainer : jQuery("#inputForm div.errorForm")
			});
			 init(); 
			
		});
		
		<%--	自动查询，输入查询	--%>
		 function init(){
				$("#dictionDiv").html("<select class='form-control' id='dicname' name='dicname' ></select>");
				$("#dicname").fcbkcomplete({
					json_url: "<%=basePath%>persion/perList.do",
					filter_selected: true,
					addontab: true,                   
			        height: 3
				});
			}
			
			var eventId="";
			var persionIds="";
			function share(){
					eventId=$(".eventId").val();
				 persionIds+=$("#dicname").val();
					$.ajax({
						type: 'POST',
						url: '<%=basePath%>event/share.do',
						data : {
							'eventId' : eventId,
							'persionIds' : persionIds,
						},
						dataType : 'json',
						success : function(data) {
							jQuery.gritter.add({
							    title: '分享成功!',
							    class_name: 'growl-info',
								 image: '../bootstrap/images/screen.png',
							    sticky: false,
							    time: ''
							});
						}
<%--						error : function(data) {--%>
<%--							alert("分享失败");--%>
<%--						}--%>
					});
				}
						
	</script>

</body>
</html>