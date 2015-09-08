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
<link href="${pageContext.request.contextPath}/resource/page/css/style.css" type="text/css" rel="stylesheet" />	
<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/chain/js/laydate/need/laydate.css"> --%>
<%-- <link href="<%=basePath%>resource/page/css/style.main3.css" type="text/css" rel="stylesheet" /> --%>
<%-- <script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/common.other.js"></script> --%>

<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/chain/js/laydate/need/laydate.css">
<script src="${pageContext.request.contextPath}/resource/chain/js/laydate/laydate.js"></script> --%>
<%-- <link href="<%=basePath%>resource/page/css/style.main3.css" type="text/css" rel="stylesheet" /> --%>
<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/common.other.js"></script>

<!-- <style type="text/css">
		/******form*******/
		.divselectcon {clear: both; padding: 10px 0 20px; position: relative; }
		.divselect {float: left; }
		.divselectcon input {width:238px; height: 35px; margin: 0 20px; position:relative; z-index:10000; border: 1px solid #b1b1b1; border-top: 2px solid #b1b1b1; line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(images/corners2.png) no-repeat right center;}
		.divselectcon input.input1 {border:1px solid #ffae00; border-top: 2px solid #ffae00; margin-left: 0; width: 228px; height:35px;line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(images/corners1.png) no-repeat right center;}
		#button2 button {float: right; width: 169px; height: 43px; background: url(images/go.png) 0 0 no-repeat; border: none; position: absolute; top: 9px;  cursor: pointer; }
		.bdsug_copy{display:none;}		
	</style> -->
<style type="text/css">
	.washBottom {float: right; position: relative; margin-right: 15px;/*  margin-top: 30px; */ }
	.washBottom .washBottomDiv {position: relative; font-size: 14px; color: #6b6b6b; clear: both; padding: 2px; margin-top: 30px; }
	.washBottom .washBottomDiv .priceDiv {  color: #cc0000;float: right;}
	.washBottom .washBottomDiv .serve {position: absolute; right: 65px; }
	.washBottom .washBottomDiv .coupons {/* position: relative; */ margin-right: 68px; top: 25px; }
	
	#mapContainer{height:100%;width:100%;}
	img.brandImage{
		width: 75px;
		height: 30px;
	}
</style>

</head>
<script type="text/javascript">
var base = "<%=basePath%>";
$().ready(function(){
	//事件绑定
	bindElement();
});

function bindElement(){
	//清洗方式选择
	$(document).on("click","div.clearMethod",function(){
		$(".clearMethod").find("label").removeClass("selectClear");
		$(".clearMethod").find("div.gouimg").addClass("display");
		
		$(this).find("label").addClass("selectClear");
		$(this).find("div.gouimg").removeClass("display");
		
		var type = $(this).attr("typeValue");
		$("#serverWay").val(type);
	});
}

function saveOrder() {
	
	var carColor = $("input[name='carColor']").val();
	if(carColor == ""){
		alert("车辆颜色不能为空");
		return;
	}
	
	
	//判断是否还有车牌号
	var carNum = $("input[name='carNum']").val();
	if(carNum == ""){
		alert("车牌号不能为空");
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
		alert("请选择服务地址");
		return;
	}
	
	var sum  = getSum();
	$("#amount_money").val(sum);
	
	if($("input[name='payWay']:checked").length <= 0){
		alert("请选择支付方式");
		return;
	}
	
	$("#formInput").submit();
}
	

</script>
<body>
	<!--上部开始-->
	<jsp:include page="../web/header.jsp" />
	<!--上部结束-->
	<hr>
	<!--中部开始-->
	<form action="<%=basePath%>/web/existOrder" method="post" id="formInput">
		<div class="mid box0">
			<div class="shopping">
				<ul class="steps_2">
					<li class="b_li1"><a href="#"><span>01.</span>确定您的车型</a></li>
					<li class="b_li2 stepColor"><a href="#"><span>02.</span>订单信息</a></li>
					<li class="b_li3"><a href="#"><span>03.</span>核对订单信息</a></li>
					<li class="b_li3"><a href="#"><span>04.</span>下单成功</a></li>
				</ul>
				
					<div class="shopping_con main_1">
						<input name="serviceType.id" type="hidden" value="${serviceType.id }" id="serviceType" />
						<input name="cleanType" type="hidden" value="1" id="serverWay" />
						<input name="car.id" type="hidden" value="${cleanCar.car.id }" id="carHiddenId"/>
						<input name="userCarId" type="hidden" value="${cleanCar.userCar.id  }"/>
						<c:choose>
							<c:when test="${empty cleanCar.userCar}"><%-- 没有默认车型  点击重选 跳转到选择车型页面 --%>
								<div class="shopping_con_top">
									<div class="text">
										<a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/${cleanCar.carBrand.imgPath}" id="carBrandImg" class="brandImage"/></a>
										<p id="carName">${cleanCar.carBrand.name}&nbsp;${cleanCar.carType.name}&nbsp;${cleanCar.car.name }</p>
									</div>
									<a href="javascript:showChooseCar();"><img src="${pageContext.request.contextPath}/resource/page/images/buy_2.png" alt="" /></a>
									<div class="carColor"><input name="carColor" type="text" value="${cleanCar.carColor }"/></div>
									<div class="carNum"><input name="carNum" type="text" value="${cleanCar.carNum }"/></div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="shopping_con_top"><%-- 没有默认车型  点击重选 选择其车管家中的车辆信息 --%>
									<div class="text" id="userCarText">
										<a href="#"><img src="${pageContext.request.contextPath}/${cleanCar.carBrand.imgPath}" class="brandImage"/></a>
										<p id="carName">${cleanCar.carBrand.name}&nbsp;${cleanCar.carType.name}&nbsp;${cleanCar.car.name }</p>
										<p class="pc">车身颜色：<span>${cleanCar.carColor }</span></p>
										<p class="pc">车牌号：<span>${cleanCar.carNum }</span></p>
										<a href="javascript:loadUserCarList();"><img src="${pageContext.request.contextPath}/resource/page/images/buy_2.png" alt="" /></a>
									</div>
									<input name="carColor" type="hidden" value="${cleanCar.carColor }"/>
									<input name="carNum" type="hidden" value="${cleanCar.carNum }"/>
								</div>
							</c:otherwise>
						</c:choose>
						<script type="text/javascript">
							<%-- 文件夹cleanCar 中页面  --%>
							function installCarView(data){
								$("#carHiddenId").val(data.carId);
								$("#carBrandImg").attr("src","<%=basePath%>"+data.brandImg);
								var fullName = data.brandName +"&nbsp;" + data.carTypeName + "&nbsp;" + data.carName;
								$("#carName").html(fullName);
							}
							function showChooseCar(){
								$("#chooseCar").show();
							}
							
							function hideChooseCar(){
								$("#chooseCar").hide();
							}
							
							//加载列表
							function loadUserCarList(){
								var  url= "<%=basePath%>web/loadUserCarList";
								var data = {};
								$("#selectMyCarCon").load(url,data,function(){
									showChooseUserCar();
									selectMyCar();
								});
							}
							
							//用户存在 并且有默认车型的时候
							function showChooseUserCar(){
								$("#chooseUserCar").show();
							}
							
							function hideChooseUserCar(){
								$("#chooseUserCar").hide();
							}
							
							function initChooseUserCar(value){
								var html = "<a href='#'><img src='${pageContext.request.contextPath}/"+value.brandImg+"' class='brandImage'/></a>"+
								"<p id='carName'>"+value.brandName+"&nbsp;"+value.typeName+"&nbsp;"+value.carName+"</p>"+
								"<p class='pc'>车身颜色：<span>"+value.carColor+"</span></p>"+
								"<p class='pc'>车牌号：<span>"+value.carNum+"</span></p>"+
								"<a href='javascript:loadUserCarList();'><img src='${pageContext.request.contextPath}/resource/page/images/buy_2.png' alt='' /></a>";
								$("#userCarText").html(html);
								
								$("#carHiddenId").val(value.carId);
								$("input[name='carColor']").val(value.carColor);
								$("input[name='carNum']").val(value.carNum);
							}
						</script>
						<%-- 
						车牌:<input name="carNum" type="text" value="${cleanCar.carNum }"/>
						车颜色:<input name="carColor" type="text" value="${cleanCar.carColor }"/> --%>
						<h2>清洗方式和地址</h2>
						<div class="main_11">
							<h3>清洗方式：</h3>
							<div class="clearMethodCon">
								<div class="clearMethod serviceTypeDiv" typeValue="1">
									<label class="selectClear">外观清洗</label>
									<div class="gouimg"></div>
								</div>
								<div class="clearMethod serviceTypeDiv" typeValue="2">
									<label class="">内外清洗</label>
									<div class="gouimg display"></div>
								</div>
							</div>
						</div>
						<div class="main_12">
							<h3>服务地址：</h3>
							<div class="addr"><a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/resource/page/images/addr.png" /></a><span id="addressDIV">地图正在加载。。。</span></div>		
							<div class="addr_con main_13">
								<div style="position: relative;height: 400px;width: 100%;display: inline-block;">
									<div style="height: 100%; width: 80%;display: inline-block;position: absolute;">
										<div id="mapContainer"></div>
									</div>
								</div>
								<jsp:include page="map.jsp" />
								<div>
									[${serviceContent }]
								</div>
							</div>
							<input class="address mian_input" name="address" id="address" type="hidden"/>
							<input id="lng" type="hidden" name="lng"/>
							<input id="lat" type="hidden" name="lat"/>
							<div class="clearfloat"></div>
						</div>
					</div>
					<div class="mian_20">
						<div class="mian_2">
							<h2>联系人信息</h2>
							<div>
								<span>用户姓名：</span>
								<input type="text" name="contact" class="user mian_input" id="username" value="${member.username }"/>
							</div>
							<div>
								<span>联系电话：</span>
								<input type="text" name="phone" class="tel mian_input" id="phone" value="${member.phone }"/>
							</div>
						</div>
					</div>
					<div class="ma_buy_total0" style="position: relative;">
						<div class="ma_buy_total_left">
							<h2>支付方式</h2>
							<ul>
								<li>
									<label>
										<input type="radio" name="payWay" class="payInput" value="2" />支付宝
									</label>
								</li>
								<li>
									<label>
										<input type="radio" name="payWay" class="payInput" value="3" />银联
									</label>
								</li>
								<%-- <li>
									<label>
										<input type="radio" name="payWay" class="payInput" value="4"/>微信
									</label>
								</li> --%>
								<c:choose>
									<c:when test="${!empty sessionScope.member_session }">
										<li>
											<label>
												<input type="radio" name="payWay" class="payInput" value="1" checked="checked" />会员卡支付
											</label>
										</li>
									</c:when>
								</c:choose>
							</ul>
						</div>
						<!-- <div class="ma_buy_total_right"> -->
							<div class="washBottom">
								<div class="washBottomDiv">
									<span class="serve">服务费用：</span>
									<span class="priceDiv">￥<fmt:formatNumber value="${serviceType.price}" pattern="0.00" /></span>
									<input type="hidden" name="servicePrice" value="${serviceType.price}" id="servicePrice"/>
								</div>
								<div class="washBottomDiv" style="position: relative;">
									<div class="coupons" id="coupons">
										<span class="">优惠券：</span>
										<span class="selecet">
											<select name="memberCouponSelect" onchange="couponSum(this);">
												<option value="" price="0" couponType="3">无</option>
											<%-- 	<option value="" price="0.9" type="1">打折</option>
												<option value="" price="10" type="2">减免</option> --%>
												<c:forEach var="value" items="${memberCoupon }">
													<option value="${value.id }" price="${value.discount }" couponType="${value.type }" rate="${value.rate }">${value.coupon.name }</option>
												</c:forEach>
												<c:if test="${!empty memberRank.scale }">
													<option value="memberRank" price="" couponType="4" rate="${memberRank.scale }">${memberRank.name }</option>
												</c:if>		
											</select>
										</span>
									</div>
									<div class="priceDiv" style="line-height: 25px;position: absolute;top: 0px;right: 0px;">-￥<span id="coupon">0.00</span></div>
									<input type="hidden" name="amount_money" value="" id="amount_money"/>
									<input type="hidden" name="amount_paid"	value="0" id="amount_paid"/>
								</div>
							<!-- </div> -->
						</div>
					</div>
					<div class="buy_total2">
						<div class="buy_total2_right">
							<p>
								合计金额：
								<span>
									￥<span id="total"><fmt:formatNumber value="${serviceType.price}" pattern="0.00" /></span>
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
		
		//优惠卷选择
		function couponSum(obj){
			var selectedOption = $(obj).find("option:selected");
			var type= selectedOption.attr("couponType");
			var price = selectedOption.attr("price");
			var rate = selectedOption.attr("rate");
			if(type == "1"){//满额打折
				var sumPrice = getSum();//获取中和
				var couponPrice = parseFloat( (1 - parseFloat(rate)) * parseFloat(sumPrice)).toFixed(2);//优惠金额
				countSum(couponPrice);
			}else if(type == "2"){//满额减免
				var couponPrice = parseFloat(price).toFixed(2);//优惠金额
				countSum(couponPrice);
			}else if(type == "3"){//免额
				var couponPrice = parseFloat(price).toFixed(2);//优惠金额
				countSum(couponPrice);
			}else if(type == "4"){//会员优惠
				var sumPrice = getSum();//获取中和
				var couponPrice = parseFloat( (1 - parseFloat(rate)) * parseFloat(sumPrice)).toFixed(2);//优惠金额
				countSum(couponPrice);
			}
		}
		//获取总金额
		function getSum(){
			var servicePrice = $("#servicePrice").val();
			return servicePrice;
		}
		//计算总金额
		function countSum(value){//value 金额
			$("#coupon").text(value);
			$("#amount_paid").val(value);
			var sumPrice = getSum();//获取中和
			var sum = "0";
			if(parseFloat(sumPrice) >= parseFloat(value)){//优惠金额
				sum = parseFloat(parseFloat(sumPrice) - parseFloat(value)).toFixed(2);
			}
			$("#total").text(sum);
		}
	</script>
	<!--中部结束-->
	<!--底部开始-->
	<jsp:include page="../web/footer.jsp" />
	<!--底部结束-->
	<%-- 选择车型弹出层 --%>
	<!--弹出层-->
	<c:choose>
		<c:when test="${empty cleanCar.userCar}"><%-- 没有默认车型  点击重选 跳转到选择车型页面 --%>
			<div id="chooseCar" style="display: none;">
				<jsp:include page="cleanCar/cleanCar_addOrder_chooseCar.jsp" />
			</div>
		</c:when>
		<c:otherwise>
			<div id="chooseUserCar" style="display: none;">
				<jsp:include page="cleanCar/cleanCar_addOrder_chooseUserCar.jsp" />
			</div>
		</c:otherwise>
	</c:choose>

	<script type="text/javascript">
		
	</script>
</body>
</html>