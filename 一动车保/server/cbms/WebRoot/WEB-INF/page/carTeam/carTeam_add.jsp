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

<title>车队添加</title>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/chain/js/laydate/laydate.js"></script>

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
	
	var i=1;
	var areaNum = 2;
	
	function check(v){
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
	
	function checkArea(){
		$(".areaSelectId").each(function(){
			if($(this).val()==""){
				$(this).next().next().html("请选择地区");
				$(this).next().next().show();
				$(this).next().val("");
			}else{
				$(this).next().next().html("");
				$(this).next().next().hide();
				$(this).next().val("success");
			}
		});
	}
	
	$(document).ready(function(){
		$("#reset").bind("click",function() {
			$("#inputForm")[0].reset();
		});
	});

	function saveType(){
		$(function(){
			check($("#headMember"));
// 			check($("#headPhone"));
			checkArea();
			var flag = true;
			$(".repeat").each(function(){
				if($(this).val()!="success"){
					flag = false;
					return;
				}
			});
			if(flag){
				$("#inputForm").submit();
			}
		});
	}
	
	//选择地区
	var base = "<%=basePath%>";
	function getAreaList(e){
		delSelect(e);
		var option = $(e).find("option:selected");
		var id = option.val();
		$($(e).parent().parent().children(".areaSelectId")).val(id);
		if(!option.hasClass("hasNext")){
			return;
		}
		var url = base + "/manage/area/findArea";
		$.post(url,{id:id},function(result){
			if(result.status == "success"){
				var values = result.message;
				addSelectElement(values,e);
			}
		},"json");
	}
	//设置地区
	function delSelect(e){
		$(e).nextAll().remove();
	}
	
	//添加选择元素
	function addSelectElement(value,e){
		var options = "<option value=''>请选择</option>";
		for(var i in value){
			options += "<option value='"+value[i].id+"' class='"+value[i].hasNext+"'>"+value[i].text+"</option>";
		}
		var select = "<select onchange='getAreaList(this);'>"+options+"</select>";
		$($(e).parent()).append(select);
	}
	
	//添加地区
	function addArea(){
		var div = 
			"<div class='form-group'>"+
				"<label class='col-sm-4 control-label'><a onclick='delArea(this)' style='float:left;'>删除</a> 地区"+areaNum+"</label>"+
				"<div class='col-sm-8' id='area_div_select'>"+
					"<select onchange='getAreaList(this);'>"+
						"<option value=''>请选择</option>"+
						"<c:forEach var='area' items='${areas }'>"+
							"<option value='${area.id }' "+
							"<c:if test='${area.children.size()>0 }'>class='hasNext'</c:if>>${area.name }</option>"+
						"</c:forEach>"+
					"</select>"+
				"</div>"+
				"<input type='hidden' class='areaSelectId' name='area["+i+"].id'/>"+
				"<input type='hidden' class='repeat'/>"+
				"<span class='errorStyle areaError'></span>"+
			"</div>";
		$("#infoDiv").append(div);
		areaNum ++;
		i++;
	}
	
	//删除地区
	function delArea(e){
		$(e).parent().parent().remove();
	}
	
	//输入框限制
	function inputRestrict(e){
		var v=$(e).val();
		var r = /^([1]{1}[0-9]{10})?$/;
		if(!r.test(v)){
			$(e).val("");
			$("#headPhone_error").html("请输入正确格式的手机号码");
			$(e).next().val("");
		}else if(v==""){
			$("#headPhone_error").html("手机号码不能为空");
			$(e).next().val("");
		}else{
			$("#headPhone_error").html("");
			$(e).next().val("success");
		}
	}
</script>
<STYLE type="text/css">
.form-bordered div.form-group {
	width: 49%;
	padding: 5px 10px;
	border-top: 0px dotted #d3d7db;
}
#serviceType {
	padding: 10px;
	padding-left: 6px;
}
#area_div_select select{
	padding: 10px;
}
.areaError{
	padding-left: 35%;
	display:block;
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
								<li><a href="">车队添加</a></li>
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
							action="<%=basePath%>manage/carTeam/save" method="POST">
							<div class="panel-body nopadding" id="infoDiv">
								<div class="form-group">
									<label class="col-sm-4 control-label">车队负责人</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="headMember" CHname="负责人" 
											onBlur="check(this)" placeholder="请填写负责人姓名" id="headMember">
										<input type="hidden" class="repeat"/>
										<span class="errorStyle" id="headMember_error"></span>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-4 control-label">负责人手机号码</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="headPhone" CHname="手机号码"
											onBlur="inputRestrict(this);" placeholder="请填写负责人手机号码" id="headPhone" maxlength="11">
										<input type="hidden" class="repeat"/>
										<span class="errorStyle" id="headPhone_error"></span>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-4 control-label">备注</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="remark" placeholder="请填写备注">
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-4 control-label"></label>
									<div class="col-sm-8">
										<a onclick="addArea()">添加地区</a>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-4 control-label">地区1</label>
									<div class="col-sm-8" id="area_div_select">
										<select onchange="getAreaList(this);">
											<option value=''>请选择</option>
											<c:forEach var="area" items="${areas }">
												<option value="${area.id }" <c:if test="${area.children.size()>0 }">class="hasNext"</c:if>
												>${area.name }</option>
											</c:forEach>
										</select>
									</div>
									<input type="hidden" class="areaSelectId" name="area[0].id" value=""/>
									<input type="hidden" class="repeat"/>
									<span class="errorStyle areaError"></span>
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
	<script type="text/javascript">
		$('[data-toggle="tooltip"]').popover();
		
		//设置时间
		function getLaydate(obj){
			var option = {
				elem : '#'+obj,
				format : 'YYYY-MM-DD hh:mm:ss',
				istime : true,
				istoday : false
			};
			laydate(option);
		}
	</script>
</body>
</html>
