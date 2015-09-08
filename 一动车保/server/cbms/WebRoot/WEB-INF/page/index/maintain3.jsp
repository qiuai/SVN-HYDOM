<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>一动车保</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/chain/js/laydate/need/laydate.css">
<link href="<%=basePath%>resource/page/css/style.main3.css" type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/common.other.js"></script>
<script src="${pageContext.request.contextPath}/resource/chain/js/laydate/laydate.js"></script>
<!-- <style type="text/css">
		/******form*******/
		.divselectcon {clear: both; padding: 10px 0 20px; position: relative; }
		.divselect {float: left; }
		.divselectcon input {width:238px; height: 35px; margin: 0 20px; position:relative; z-index:10000; border: 1px solid #b1b1b1; border-top: 2px solid #b1b1b1; line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(images/corners2.png) no-repeat right center;}
		.divselectcon input.input1 {border:1px solid #ffae00; border-top: 2px solid #ffae00; margin-left: 0; width: 228px; height:35px;line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(images/corners1.png) no-repeat right center;}
		#button2 button {float: right; width: 169px; height: 43px; background: url(images/go.png) 0 0 no-repeat; border: none; position: absolute; top: 9px;  cursor: pointer; }
		.bdsug_copy{display:none;}		
	</style> -->

</head>
<script type="text/javascript">
function getLaydate(obj){
	  var date = new Date();
	 var nowDate = date.format("yyyy-MM-dd");
		var option = {
			elem : '#'+obj,
			format : 'YYYY-MM-DD',
			istime : false,
			min: nowDate,
			choose : function(date){
				var value = $("#street").val();
				if(value == ""){
					$("#"+obj).val("");
					alert("请选择完整的地区");
					return;
				}
				getTime();
			}
		};
		laydate(option);
}
$().ready(function(){
	bindElement();
	$("#intallStore").hide();
});
function bindElement(){
	$(document).on("click","div.serviceTypeDiv",function(){
		$(".serviceTypeDiv").find("label").removeClass("select");
		$(".serviceTypeDiv").find("div.gouimg").addClass("display");
		
		$(this).find("label").addClass("select");
		$(this).find("div.gouimg").removeClass("display");
		
		var type = $(this).attr("typeValue");
		$("#serverWay").val(type);
	});
}
var base = "<%=basePath%>";

	//选择地区
	function getAreaList(e, obj) {
		delSelect(e, obj);
		var option = $(e).find("option:selected");
		var id = option.val();

		if (!option.hasClass("hasNext")) {
			return;
		}

		if (id == "") {
			return;
		}

		var url = base + "/web/serverType/findArea";
		$.post(url, {
			id : id
		}, function(result) {
			if (result.status == "success") {
				var values = result.message;
				addSelectElement(values, obj);
			}
		}, "json");
	}

	//设置地区
	function delSelect(e, obj) {
		if (obj == 1) {
			$("#county").html("<option value=''>县区</option>");
			$("#street").html("<option value=''>街道</option>");
			$("#intallStore").hide();
		} else if (obj == 2) {
			$("#street").html("<option value=''>街道</option>");
			$("#intallStore").hide();
		}
	}

	//添加选择元素
	function addSelectElement(value, obj) {
		var options = "";
		for ( var i in value) {
			options += "<option value='"+value[i].id+"' class='"+value[i].hasNext+"'>" + value[i].text + "</option>";
		}
		if (obj == 1) {
			var m = "<option value=''>县区</option>";
			$("#county").html(m + options);
		} else if (obj == 2) {
			var m = "<option value=''>街道</option>";
			$("#street").html(m + options);
		}
	}

	function checkIsNull(e, msg) {
		var name = $(e).attr("name");
		var span = $("." + name + "_text");
		if ($(e).val().trim() != "") {
			span.text("");
			return true;
		}
		span.text(msg);
		return false;
	}

	function getTime() {
		var time = $("#reportDay").val();
		var serviceType = $("#serviceType").val();
		var areaId = $("#street").val();
		if (areaId == "" || time == "" || serviceType == "") {
			return;
		}
		var url = base + "/web/serverType/getTimeMap";
		var data = {
			areaId : areaId,
			serviceTypeId : serviceType,
			date : time
		};
		$.post(url, data, function(result) {
			if (result.status == "success") {
				addTimeMap(result.message);
			}
		});
	}

	function addTimeMap(msg) {
		var select = $("#timeMap");
		var timeMap = "";
		for ( var i in msg) {
			timeMap += "<option value='"+msg[i].dateMap+"'>" + msg[i].date + "</option>";
		}
		select.html(timeMap);
	}

	function saveOrder() {
		setSelectValue();
		if ($("#street").val() == ""){
			alert("请选择地区");
			return;
		}
		if (!validStore()) {
			alert("请选择门店");
			return;
		}
		if (!checkName()) {
			alert("请填写联系人");
			return;
		}
		if (!checkPhone()) {
			alert("请输入正确的的手机号码");
			return;
		}
		if (!checkAddress()) {
			alert("请输入详细地址");
			return;
		}
		$("#formInput").submit();
	}
	function getServiceStore() {
		var areaId = $("#street").val();
		if (areaId == '') {
			$("#intallStore").hide();
		} else {
			$
					.ajax({
						type : "POST",
						url : base + "/web/serverType/findServiceStore",
						data : "id=" + areaId,
						success : function(result) {
							if (result.status == "success") {
								$("#lists").empty();
								var serviceStores = result.message;
								for (var i = 0; i < serviceStores.length; i++) {
									$("#lists")
											.append(
													"<li><dl><dt><a href='javascript:void(0);' id='"
															+ serviceStores[i].id
															+ "' onclick='a(this)'>"
															+ "<img src='${pageContext.request.contextPath}/resource/page/images/ma_5.png' alt='' class='storesSelect'/></a></dt>"
															+ "<dd>" + serviceStores[i].name + "</dd><dd><a href='#'>" + serviceStores[i].address
															+ "</a></dd></dl></li>");
								}
							}
						}
					});
			$("#intallStore").show();
		}
	}
	function a(a) {
		var img = $("#" + a.id).find("img");
		var yess = "${pageContext.request.contextPath}/resource/page/images/ma_6.png";
		var no = "${pageContext.request.contextPath}/resource/page/images/ma_5.png";
		var src = (img.attr("src") == no) ? yess : no;

		var ops = $("#lists dl dt a");
		for (var i = 0; i < ops.length; i++) {
			var op = ops[i];
			if (op.id == a.id) {
				img.attr("src", src);
				$("#serviceStoreId").val(a.id);
			} else {
				$("#" + op.id).find("img").attr("src", no);
			}

		}
	}
	function setSelectValue() {
		var cityVal = $("#city").val();
		var countyVal = $("#county").val();
		var streetVal = $("#street").val();
		var areaName = "";
		if (cityVal != '' && countyVal != '' && streetVal != '') {
			areaName = $("#city").find("option:selected").text() + $("#county").find("option:selected").text()
					+ $("#street").find("option:selected").text();
			$("#areaName").val(areaName);
		}
	}
	function validStore() {
		var yess = "ma_6.png";
		var imgs = $(".storesSelect");
		for (var i = 0; i < imgs.length; i++) {
			debugger;
			var src = imgs[i].src;
			var index = src.lastIndexOf("ma_");
			var str = src.substring(index);
			if (str == yess)
				return true;
		}
		return false;
	}
</script>
<body>
	<!--上部开始-->
	<jsp:include page="../web/header.jsp" />
	<!--上部结束-->
	<hr>
	<!--中部开始-->
	<form action="<%=basePath%>/web/serverType/saveOrder" method="post" id="formInput">
		<div class="mid box0">
			<div class="shopping">
				<ul class="steps_2">
					<li class="b_li1">
						<a href="#">
							<span>01.</span>
							确定您的车型
						</a>
					</li>
					<li class="b_li2">
						<a href="#">
							<span>02.</span>
							选择保养项目和产品
						</a>
					</li>
					<li class="b_li3">
						<a href="#">
							<span>03.</span>
							确定服务类型
						</a>
					</li>
				</ul>
				<div class="shopping_con">
					<input name="areaName" type="hidden" value="" id="areaName" />
					<input name="serviceStore.id" type="hidden" value="" id="serviceStoreId" />
					<input name="serviceType.id" type="hidden" value="${serviceType.id }" id="serviceType" />
					<input name="car.id" type="hidden" value="${car.id }" />
					<input name="serverWay" type="hidden" id="serverWay" value="1" />
					<div class="main_1">
						<h2>请选择服务方式</h2>
						<div class="main_11">
							<span class="main_span1 main_11_1">服务方式：</span>
							<c:if test="${serviceType.payType==1 }">
								<div class="serviceTypeDiv" typeValue="1">
									<label class="main_11_1_1 chooseServiceType select">上门服务</label>
									<div class="gouimg"></div>
								</div>
								<div class="serviceTypeDiv" typeValue='2'>
									<label class="main_11_1_1 chooseServiceType">门店服务</label>
									<div class="gouimg display"></div>
								</div>
							</c:if>
							<c:if test="${serviceType.payType==2 }">
								<div class="serviceTypeDiv" typeValue="1">
									<label class="main_11_1_1 chooseServiceType select">上门服务</label>
									<div class="gouimg"></div>
								</div>
							</c:if>
							<c:if test="${serviceType.payType==3 }">
								<div class="serviceTypeDiv" typeValue="2">
									<label class="main_11_1_1 chooseServiceType select">门店服务</label>
									<div class="gouimg"></div>
								</div>
							</c:if>
							<!-- 	<a href="#"><img src="images/ma_1.png" alt="" /></a>
					<a class="a2" href="#"><img src="images/ma_2.png" alt="" /></a> -->
						</div>

						<div class="main_13">
							<span class="main_span3 main_11_1">所在地区：</span>
							<div class="ma_divselect">
								<select onchange="getAreaList(this,1);" id="city">
									<option value="">市</option>
									<c:forEach var="area" items="${areas }">
										<option value="${area.id }" <c:if test="${area.children.size()>0 }">class="hasNext"</c:if>>${area.name }</option>
									</c:forEach>
								</select>
								<select id="county" onchange="getAreaList(this,2);" id="area">
									<option value="">县区</option>
								</select>
								<select id="street" name="area.id" onchange="getServiceStore()">
									<option value="">街道</option>
								</select>
							</div>
						</div>
						<div class="main_12">
							<span class="main_span2 main_11_1">预约时间：</span>
							<input id="reportDay" name="registdate" onclick="getLaydate('reportDay')" style="margin: 5xp">
							<select id="timeMap" name="timeMap">

							</select>
						</div>
						<div class="main_14" style="position: relative;" id="intallStore">
							<span class="main_span4 main_11_1" style="top: 0;">安装门店：</span>
							<ul class="ma_lists" id="lists">
							</ul>
						</div>
					</div>
					<div class="mian_20">
						<div class="mian_2">
							<h2>联系人信息</h2>
							<div>
								<span>用户姓名：</span>
								<input type="text" name="user" class="user mian_input" id="username" />
							</div>
							<div>
								<span>联系电话：</span>
								<input type="text" name="tel" class="tel mian_input" id="phone" />
							</div>
							<!-- <div><span>验&nbsp;&nbsp;证&nbsp;码：</span><input type="text" name="code" class="code mian_input" /><button type="button" style="margin-left: 5px;" onclick="getPhoneCode();">获取验证码</button></div> -->
							<div>
								<span>详细地址：</span>
								<input class="address mian_input" name="address" id="address" />
							</div>
						</div>
					</div>

					<%-- <div class="shopping_con_bottom box2">				
				<h2 class="ma_h2">商品清单</h2>
				<ul class="ma_buy_title">
					<li><span>商品</span></li>
					<li class="ma_buy_title_li1"><span>详情</span></li>
					<li><span>价格</span></li>
					<li><span>数量</span></li>
					<li><span>小计</span></li>
				</ul>
				<ul class="ma_buy_con">
					<li class="ma_buy_con_li1">${serviceType.name }</li>
					<li class="ma_buy_con_li2">服务费</li>
					<li class="ma_buy_con_li3"><span>￥<fmt:formatNumber value="${serviceType.price}" pattern="0.00"/></span></li>
					<li class="ma_buy_con_li4"></li>
					<li class="ma_buy_con_li5"><span>￥<fmt:formatNumber value="${serviceType.price}" pattern="0.00"/></span></li>
				</ul>				
			</div> --%>
					<div class="ma_buy_total0" style="position: relative;">
						<div class="ma_buy_total_left">
							<h2>支付方式</h2>
							<form>
								<ul>
									<%-- <li><input type="radio" checked name="pay" /><img src="${pageContext.request.contextPath}/resource/page/images/ma_9.png" alt="" />
							</li>
							<li><input type="radio" name="pay" /><img src="${pageContext.request.contextPath}/resource/page/images/ma_10.png" alt="" />
							</li> --%>
									<li>
										<label class="shangmenzhifu">
											<input type="radio" name="pay" class="payInput" value="1" checked="checked" />
										</label>
									</li>
								</ul>
							</form>
						</div>
						<!-- <div class="ma_buy_total_right">
					<p><span class="ma_buy_1 ma_buy_11">商品总金额：</span><span class="ma_buy_2">￥995.00</span></p>
					<p><span class="ma_buy_1 ma_buy_12">安装费（<a href="#">安装费用标准说明</a>）:</span><span class="ma_buy_2">￥50.001</span></p>
				</div> -->
						<div class="serivcePrice">
							<p>
								<span class="ma_buy_1 ma_buy_11">服务金额：</span>
								<span class="ma_buy_2">
									￥
									<fmt:formatNumber value="${serviceType.price}" pattern="0.00" />
								</span>
							</p>
						</div>
					</div>
					<div class="buy_total2">
						<div class="buy_total2_right">
							<p>
								合计金额：
								<span>
									￥
									<fmt:formatNumber value="${serviceType.price}" pattern="0.00" />
								</span>
							</p>
							<button type="button" onclick="saveOrder();"></button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<script type="text/javascript">
		function getPhoneCode() {
			var phone = $("#phone").val();

		}

		//验证用户名
		function checkName() {
			var value = $("#username").val();
			if (value.trim() == "") {
				return false;
			}
			return true;
		}

		//验证手机号码
		function checkPhone() {
			var value = $("#phone").val();
			var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
			if (reg.test(value)) {
				return true;
			} else {
				return false;
			}
			;
		}

		//验证详细地址
		function checkAddress() {
			var value = $("#address").val();
			if (value.trim() == "") {
				return false;
			}
			return true;
		}
	</script>
	<!--中部结束-->
	<!--底部开始-->
	<jsp:include page="../web/footer.jsp" />
	<!--底部结束-->
</body>
</html>