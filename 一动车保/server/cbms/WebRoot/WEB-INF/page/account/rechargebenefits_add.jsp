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

<title>充值返现/送优惠券添加</title>
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

function checkMoney() {
	
	var money = $("#money").val();
	var reg = /^\d+(.\d{1,2})?$/;
	if(money == "" || money == null){
		$("#money_error").html("金额不能为空");
		$("#money").next().val("");
	}else{$
		.post(
				"${pageContext.request.contextPath}/manage/rechargebenefits/check",
				{
					money : money
				},
				function(data) {
				 				if (data == true) {
								$("#money_error").html("充值金额已经存在");
								$("#money").next().val("");
							} else if(!reg.test(money)){
								$("#money_error").html("请输入正确格式的金额");
								$("#money").next().val("");
							}else{
								$("#money_error").html("");
								$("#money").next().val("success");
							
						};
				});}
	
}	

function checkScale() {
	var pattern =/^\d+(.\d{1,2})?$/;
	var scale = $("#scale").val();
		//alert("返现率"+$("#scale").is(":visible"));
		if ($("#scale").is(":visible")) {
			if (scale == "" || scale == null) {
				$("#scale_error").html("返现率不能为空");
				$("#scale").next().next().val("");
			} else if(!pattern.test(scale)){
				$("#scale_error").html("请输入正确的返现率(必须为正数)");
				$("#scale").next().next().val("");
			}
			else{
				$("#scale_error").html("");
				$("#scale").next().val("success");
			}
		}else{
			$("#scale").next().val("success");
			}
	}
	
function checkNum() {
	var reg = /^[1-9]\d*$/;
	var Num = $("#couponNumber").val();
		/* alert("数量"+$("#couponNumber").is(":visible"));  */
		if ($("#couponNumber").is(":visible")) {
			if (Num == "" || Num == null) {
				$("#couponNumber_error").html("优惠券数量不能为空");
				$("#couponNumber").next().val("");
			} else if(!reg.test(Num)){
				$("#couponNumber_error").html("请填写正确的优惠券数量");
				$("#couponNumber").next().val("");
				}
			else{
				$("#couponNumber_error").html("");
				$("#couponNumber").next().val("success");
			}
		}else{
			$("#couponNumber").next().val("success");}
	}

	function useTypeChange(e) {
		var url = "${pageContext.request.contextPath}/manage/sendCoupon/getCoupon";
		var data = {
			useTypeId : $(e).val()
		};
		$.post(url, data, function(result) {
			var op = "";
			for ( var i in result) {
				op += "<option value="+result[i].id+">" + result[i].name
						+ "</option>";
			}
			$("#coupon").html(op);
		}, "json");
	}

	function selectType(s) {

		if ($(s).val() == 2) {
			$("#one").show();
			$("#two").show();
			$("#four").show();
			$("#three").hide();
		} else {
			$("#one").hide();
			$("#two").hide();
			$("#four").hide();
			$("#three").show();
		}
	}

	$(document).ready(function() {
		$("#reset").bind("click", function() {
			$("#inputForm")[0].reset();
		});
	});

	function saveType() {
		checkMoney();
		checkScale();
		checkNum();
		$(function(){
			var flag = true;
			$(".repeat").each(function(){
				//alert($(this).attr("id")+":"+$(this).val());
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
								<li><a href="">充值优惠添加</a></li>
							</ul>
						</div>
					</div>
					<!-- media -->
				</div>
				<!-- pageheader -->

				<div class="contentpanel">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">优惠设置</h4>
						</div>
						<form class="form-horizontal form-bordered" id="inputForm"
							action="<%=basePath%>manage/rechargebenefits/save" method="POST">
							<div class="panel-body nopadding">
								<div class="form-group">
									<label class="col-sm-4 control-label">充值金额</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="money" onBlur="checkMoney()" placeholder="请填写充值金额，最多保存小数点后两位" id="money">
										<input type="hidden" class="repeat"/>
										<span class="errorStyle" id="money_error"></span>
									</div>
								</div>
							
								<div class="form-group">
										<label class="col-sm-4 control-label">优惠类型</label>
										<div class="col-sm-8">
											<select id="type" name="type" class="selects" onchange="selectType(this)">
												<option value="1" selected="selected">充值返现</option>
												<option value="2">充值送优惠券</option>
											</select>
										</div>
									</div>

								<div class="form-group" id="three">
									<label class="col-sm-4 control-label">返现率</label>
									<div class="col-sm-8" style="position: relative;">
										<input type="text" class="form-control" name="scale"
										 onBlur="checkScale()" placeholder="请输入返现比例，如：50%就输入0.5" id="scale">
										<input type="hidden" class="repeat" id="scaleval"/>
										<span class="errorStyle" id="scale_error"></span>
										
									</div>
								</div>
							
								<div class="form-group" id="one" style="display: none">
									<label class="col-sm-4 control-label">优惠券使用类型</label>
									<div class="col-sm-8">
										<select id="useTypeId" name="useTypeId" class="selects" onchange="useTypeChange(this);">
											<option value="1" selected="selected">洗车优惠券</option>
											<option value="2">保养优惠券</option>
											<option value="3">商品优惠券</option>
										</select>
									</div>
								</div>

								<div class="form-group" id="two" style="display: none">
									<label class="col-sm-4 control-label">优惠券名称</label>
									<div class="col-sm-8">
										<select id="coupon" name="coupon.id" class="selects">
											<c:forEach items="${coupons }" var="coupon" >
											<option value="${coupon.id }">${coupon.name }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								
								<div class="form-group" id="four" style="display: none">
									<label class="col-sm-4 control-label">优惠券数量</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" name="couponNumber"
											onBlur="checkNum()" placeholder="请填写优惠券数量，必须为正整数" id="couponNumber">
										<input type="hidden" class="repeat"/>
										<span class="errorStyle" id="couponNumber_error"></span>
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
	<script type="text/javascript">
		$('[data-toggle="tooltip"]').popover();
	</script>
</body>
</html>

