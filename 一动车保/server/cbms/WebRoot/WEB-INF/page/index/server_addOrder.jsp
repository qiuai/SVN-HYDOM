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

<link href="<%=basePath%>resource/page/css/style.css" type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/common.other.js"></script>

<style type="text/css">
	 .washBottom {float: right; position: relative; margin-right: 15px;/*  margin-top: 30px; */ }
	.washBottom .washBottomDiv {position: relative; font-size: 14px; color: #6b6b6b; clear: both; padding: 2px; margin-top: 30px; }
	.washBottom .washBottomDiv .priceDiv {  color: #cc0000;float: right;}
	.washBottom .washBottomDiv .serve {position: absolute; right: 65px; }
	.washBottom .washBottomDiv .coupons {/* position: relative; */ margin-right: 68px; top: 25px; }
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
	$(document).on("click","div.serviceTypeDiv",function(){
		$(".serviceTypeDiv").find("label").removeClass("select");
		$(".serviceTypeDiv").find("div.gouimg").addClass("display");
		
		$(this).find("label").addClass("select");
		$(this).find("div.gouimg").removeClass("display");
		
		var type = $(this).attr("typeValue");
		$("#serverWay").val(type);
	});
}

function saveOrder() {
	
	if (!checkName()) {
		alert("请填写联系人");
		return;
	}
	if (!checkPhone()) {
		alert("请输入正确的的手机号码");
		return;
	}
	if (!checkAddress()) {
		alert("请输入服务地址");
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
	<form action="<%=basePath%>/web/existServerOrder" method="post" id="formInput">
		<div class="mid box0">
			<div class="shopping">
				<ul class="steps_2">
					<li class="b_li1"><a href="#"><span>01.</span>确定您的车型</a></li>
					<li class="b_li2 stepColor"><a href="#"><span>02.</span>订单信息</a></li>
					<li class="b_li3"><a href="#"><span>03.</span>核对订单信息</a></li>
					<li class="b_li3"><a href="#"><span>04.</span>下单成功</a></li>
				</ul>
				<div class="shopping_con main_1">
				
					<input name="carId" type="hidden" value="${carId}" />
					<input name="userCarId" type="hidden" value="${userCarId}" />
					<input name="content" type="hidden" value='${content }'/>
					<%-- <input name="memberCouponId" type="hidden" value="${memberCouponId}" /> --%>
					<input name="chooseServer" type="hidden" value="${chooseServer }" />
					<input name="carColor" type="hidden" value="${carColor }"/>
					<input name="carNum" type="hidden" value="${carNum }"/>
					<input name="bespeakDate" type="hidden" value="${bespeakDate }" />
					<input type="hidden" id="areaSelectId" name="areaId"/>
					
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
							<div>
								<span>预约时间：</span>
								<label>${bespeakDate }</label>
								<select name="dateTimeSelect">
									<c:forEach var="value" items="${dateTimeMap }">
										<option value="${value.key }">${value.value.mapDate }</option>
									</c:forEach>
								</select>
							</div>
							<script type="text/javascript">
								//选择地区
								function getAreaList(e){
									delSelect(e);
									var option = $(e).find("option:selected");
									var id = option.val();
									$("#areaSelectId").val(id);
									if(!option.hasClass("hasNext")){
										return;
									}
									var url = base + "/web/findArea";
									$.post(url,{id:id},function(result){
										if(result.status == "success"){
											var values = result.message;
											addSelectElement(values);
										}
									},"json");
								}
								
								//设置地区
								function delSelect(e){
									$(e).nextAll().remove();
								}
								//添加选择元素
								function addSelectElement(value){
									var options = "<option value=''>请选择</option>";
									for(var i in value){
										options += "<option value='"+value[i].id+"' class='"+value[i].hasNext+"'>"+value[i].text+"</option>";
									}
									var select = "<select onchange='getAreaList(this);'>"+options+"</select>";
									$("#area_div_select").append(select);
									
								}
								
								//如果是纯服务就隐藏服务价格
								$(function(){
									if(!$(".lists").hasClass("pb")) $(".lists").find(".dd31").css('opacity','0');
								});
							</script>
							<div>
								<span>上门地址：</span>
								<div style="display: inline-block;" id="area_div_select">
									<select onchange="getAreaList(this);">
										<option value=''>请选择</option>
										<c:forEach var="area" items="${areas }">
											<option value="${area.id }" <c:if test="${area.children.size()>0 }">class="hasNext"</c:if>
											>${area.name }</option>
										</c:forEach>
									</select>
								</div>
								<input type="text" name="address" class="tel mian_input" id="address" value=""/>
							</div>
						</div>
					</div>
					<div class="productDetailDiv">
						<h2>商品清单</h2>
						<ul class="productDetailContent">
							<li class="title">
								<dl>
									<dd class="dd1">商品</dd>
									<dd class="dd2">价格</dd>
									<dd class="dd2">数量</dd>
									<dd class="dd3">小计</dd>
								</dl>
							</li>
							<c:forEach var="value" items="${productServer }">
								<li class="lists">
									<dl>
										<dd class="dd11">${value.serverName }(服务费)</dd>
										<dd class="dd21"></dd>
										<dd class="dd21"></dd>
										<dd class="dd31">￥${value.serverPrice }</dd>
									</dl>
								</li>
								<c:forEach var="subValue" items="${value.serverOrderProductBeans }">
									<li class="lists pb">
										<dl>
											<dd class="dd11">${subValue.name}</dd>
											<dd class="dd21">￥${subValue.price }</dd>
											<dd class="dd21">${subValue.count }</dd>
											<dd class="dd31">￥${subValue.sum }</dd>
										</dl>
									</li>
									
								</c:forEach>
							</c:forEach>	
						</ul>
					</div>
					<div class="ma_buy_total0" style="position: relative;">
						<div class="ma_buy_total_left">
							<h2>支付方式</h2>
							<ul>
								<li>
									<label>
										<input type="radio" name="payWay" class="payInput" value="2"/>支付宝
									</label>
								</li>
								<li>
									<label>
										<input type="radio" name="payWay" class="payInput" value="3"/>银联
									</label>
								</li>
								<%-- <li>
									<label>
										<input type="radio" name="payWay" class="payInput" value="4"/>微信
									</label>
								</li> --%>
								<li>
									<label>
										<input type="radio" name="payWay" class="payInput" value="5"/>现场支付
									</label>
								</li>
								<c:choose>
									<c:when test="${!empty sessionScope.member_session }">
										<li>
											<label>
												<input type="radio" name="payWay" class="payInput" value="1" checked="checked" />余额支付
											</label>
										</li>
									</c:when>
								</c:choose>
							</ul>
						</div>
						<div class="washBottom">
							<div class="washBottomDiv">
								<span class="serve">商品费用：</span>
								<span class="priceDiv">￥<fmt:formatNumber value="${productSum }" pattern="0.00" /></span>
								<input type="hidden" name="productPrice" value="${productSum }" id="productPrice"/>
							</div>
							<div class="washBottomDiv">
								<span class="serve">服务费用：</span>
								<span class="priceDiv">￥<fmt:formatNumber value="${serverSum }" pattern="0.00" /></span>
								<input type="hidden" name="servicePrice" value="${serverSum }" id="servicePrice"/>
							</div>
							<div class="washBottomDiv" style="position: relative;">
								<div class="coupons" id="coupons">
									<span class="">优惠券：</span>
									<span class="selecet">
										<select name="memberCouponId" onchange="couponSum(this);">
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
						</div>
					</div>
					<div class="buy_total2">
						<div class="buy_total2_right">
							<p>
								合计金额：
								<span>
									￥<span id="total">${total }</span>
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
			}else if(type == "4"){//会员等级优惠
				var sumPrice = getSum();//获取中和
				var couponPrice = parseFloat( (1 - parseFloat(rate)) * parseFloat(sumPrice)).toFixed(2);//优惠金额
				countSum(couponPrice);
			}
		}
		//获取总金额
		function getSum(){
			var product = $("#productPrice").val();
			var servicePrice = $("#servicePrice").val();
			return parseFloat(parseFloat(product) + parseFloat(servicePrice));
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
</body>
</html>