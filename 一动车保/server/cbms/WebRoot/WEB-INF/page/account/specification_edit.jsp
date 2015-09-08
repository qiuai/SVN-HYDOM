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
#memberRankSelect{
	margin-top: 10px;
}
#addAttributeValues{
	position: absolute;
	right: 20px;
	top:15px;
}
.delSpan{
	margin-top: 10px;
	display: inline-block;
}
.file_div{
	display: inline-block;
	margin-top: 10px;
}
.mg10{
	margin-top: 8px;
}
.div_img{
	display: inline-block;
	width: 20px;
	height: 20px;
}
.div_img img{
	width: 20px;
	height: 20px;
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
								<li><a href="">规格编辑</a></li>
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
						<form class="form-horizontal form-bordered" id="inputForm" action="<%=basePath%>manage/specification/save" method="POST" enctype="multipart/form-data">
							<div class="panel-body nopadding">
								<input type="hidden" value="${entity.id }" name="id"/>
								<div class="form-group">
									<label class="col-sm-4 control-label">商品分类</label>
									<div class="col-sm-8">
										<label class="control-label">${entity.productCategory.name }</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">名称</label>
									<div class="col-sm-8">
										<input type="text" name="name" class="form-control" maxlength="200" value="${entity.name }"/>
										<label id="nameLabel">
											<span class="success"></span>
										</label>	
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">类型</label>
									<div class="col-sm-8">
										<select name="type" style="margin-top: 8px;" onchange="selectChange(this);" id="selectType">
											<c:choose>
												<c:when test="${entity.type eq 2}">
													<option value="2">图片</option>
												</c:when>
												<c:otherwise>
													<option value="1">文本</option>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">备注</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="memo" value="${entity.memo }"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">排序</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="order" value="${entity.order }"/>
									</div>
								</div>
							</div>
							<div class="panel-heading" style="border-top:1px solid #ddd;">
								<h4 class="panel-title">可选项</h4>
								<button type="button" id="addAttributeValues" onclick="addAttributeValue();">新增可选项</button>								
							</div>
							<div class="panel-body nopadding">
								<div class="table-responsive">
							        <table id="listTable" class="table table-info" >
							        		<tr>
							        			<th>规格值名称</th>
							        			<th>规格值图片</th>
							        			<th>规格值排序</th>
							        			<th>操作</th>
							        		</tr>
							        		<c:forEach var="value" items="${entity.specificationValues }">
												<tr class="specification_value">
													<td>
														<label>${value.name }</label>
													</td>
													<td>
														<c:if test="${entity.type eq 2}">
															<div class="div_img">
																<a href="<%=basePath %>/${value.image}" title="点击查看大图" target="_blank"><img alt="" src="<%=basePath %>/${value.image}"></a>
															</div>
														</c:if>
													</td>
													<td>
														<label>${value.order }</label>
													</td>
													<td>
														<a href="javascript:void(0);" onclick="deletOption(this,'${value.id}');">[删除]</a>
													</td>
												</tr> 						        			
							        		</c:forEach>
									</table>
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
	</section>
	<script type="text/javascript">
		var base = "<%=basePath%>";
		$(document).ready(function(){
			
		});
		var entityId = "${entity.id}";
		var productCategoryId = "${entity.productCategory.id }";
		var entityName = "${entity.name}";
		
		function setErrorMessage(value,type){
			if(type == 1){
				var html = "<span style='color:red' class='error'>"+value+"</span>";
				$("#nameLabel").html(html);
			}else if (type == 2){
				var html = "<span style='color:green' class='success'>"+value+"</span>";
				$("#nameLabel").html(html);
			}
		}
		
		function checkName() {
			setErrorMessage("",2);
			var name = $("input[name='name']").val();
			if(name == ""){
				return;
			}		
			if(entityName == name){
				return;
			}
			var url = "<%=basePath%>manage/specification/checkName";
			var data = {
				productCategory:productCategoryId,
				name:name
			};
			
			$.post(url,data,function(result){
				if(result.status == "success"){
					setErrorMessage(result.message,2);
				}else{
					setErrorMessage(result.message,1);
				}
			},"json");
		}
		
		
		function saveForm(){
			
			var name = $("input[name='name']").val();
			if(name==""){
				alert("请输入名称");
				return;
			}
			
			var inputs = $(".specification_value").find("input[name='specificationNames']");
			for(var i = 0; i < inputs.length; i++){
				if($(inputs[i]).val().trim() == ""){
					alert("请填写规格值名称");
					return;
				}
			}
			
			if(entityName == name){
				$("#inputForm").submit();
				return;
			}
			
			var url = "<%=basePath%>manage/specification/checkName";
			var data = {
				productCategory:productCategoryId,
				name:name
			};
			
			$.post(url,data,function(result){
				if(result.status == "success"){
					$("#inputForm").submit();
				}else{
					setErrorMessage(result.message,1);
				}
			},"json");
			
			
		}
		function addAttributeValue(){
			var value = $("#selectType").val();
			var hm = "";
			if(value == "1"){
				hm += "disabled='disabled'";
			}
			var html = "<tr class='specification_value'>"+
				"<td>"+
					"<input type='text' name='specificationNames' class='form-control' maxlength='200' />"+
				"</td>"+
				"<td><div class='file_div'>"+
					"<input type='file' name='specificationFiles' class='fileInput' "+hm+"/>"+
				"</div></td>"+
				"<td>"+
					"<input type='text' name='specificationOrders' class='form-control'/>"+
				"</td>"+
				"<td><div class='file_div'>"+
					"<a href='javascript:void(0);' onclick='deletTr(this);'>[删除]</a>"+
				"</div></td>"+
			"</tr> ";
			/* var html = "<tr class='attribute_value'><td><div class='col-sm-8'>"+
						"<input type='text' name='attributeValues' class='form-control' maxlength='200' />"+
					"</div><span class='delSpan'>"+
						"<a href='javascript:void(0);' onclick='delAttributeValue(this);'>[删除]</a>"+
					"</span></td></tr>"; */
			$("#listTable").append(html);
		}
		
		function deletTr(e){
			var tr = $(e).closest("tr.specification_value");
			tr.remove();
		}
		
		function selectChange(e){
			var value = $(e).val();
			if(value == "1"){
				$(".fileInput").prop("disabled",true);
			}else{
				$(".fileInput").prop("disabled",false);
			}
		}
		
		function deletOption(obj,id){
			if(!confirm("确定删除该数据？")){
				return;
			}
			var data = {id:id};
			var url = base + "manage/specification/deleteValue";
			$.post(url,data,function(result){
				if(result.status == "success"){
					deletTr(obj);
				}else{
					alert("删除失败");
				}
			},"json");
		}
	</script>
</body>
</html>
