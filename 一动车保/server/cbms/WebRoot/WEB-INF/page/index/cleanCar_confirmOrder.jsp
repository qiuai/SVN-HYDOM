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
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<title>一动车保</title>
	
	<link href="${pageContext.request.contextPath}/resource/page/css/style.css" type="text/css" rel="stylesheet" />	
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/js/common.other.js"></script>
	<!-- 微信二维码生成 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/weixin/qrcode/jquery.qrcode.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/weixin/qrcode/qrcode.js"></script>
<style type="text/css">
	/**网上支付提示**/
	#mabg8 {width:100%; height: 100%; top:0px;left:0px; position: fixed; filter: alpha(opacity=50);opacity:0.3; background:#000000; /* display:none; */ z-index: 99997;}
	#mabg9 {position: fixed; z-index: 99998; width: 550px; height: 270px; left: 45%; top: 46%; margin: -142px 0 0 -176px; background-color: #000; /* display:none;  */opacity: 0.3; filter:alpha(opacity=30);}
	.payReminders {width: 530px; height: 250px; background: #fff; border: 1px solid #ddd; position: fixed; left: 40%; top: 34%; margin: -28px 0 0 -85px; background-color: #fff; /* display: none; */ z-index: 99999;}
	.payRemindersTitle {heihgt: 40px; background-color: #f7f7f7;}
	.payRemindersTitle span{font-size: 18px; line-height: 40px; padding-left: 10px; color: #6b6b6b;  }
	.payRemindersTitle .closePayRem{float: right; }
	.payRemindersCon {clear:both; font-size: 18px; color: #6b6b6b; padding-top: 15px; }
	.payRemindersCon p {padding-left: 95px; padding-top: 5px; }
	.payRemindersCon .reminder {background: url(${pageContext.request.contextPath}/resource/page/images/payrem1.png) 0 0 no-repeat; width: 53px; height: 53px; position: relative; top: -50px; left: 25px;}
	.payRemindersBottom div {display: inline-block; width: 122px; heihgt: 40px; padding: 10px; font-size: 16px; border-radius: 8px; behavior: url(js/PIE.htc); /*兼容*/text-align: center;}
	.payRemindersBottom div a {color: #fff;}
	.payRemindersBottom .left {margin-left: 100px; margin-right: 70px;  background-color: #c9c9c9;}
	.payRemindersBottom .right {background-color: #ffae00;}
	
	.weixinBody .bodyTable {
		display: inline-block;
		margin-left: 145px;
		margin-bottom: 10px;
		margin-top: 10px;
	}
	
</style>	
</head>

<body>
<!--上部开始-->
<!--上部开始-->
	<jsp:include page="../web/header.jsp" />
<!--上部结束-->
<!--上部结束-->
<hr>
<!--中部开始-->
<div class="mid box0">
	<div class="shopping">
		<ul class="steps_3">
			<li class="b_li1"><a href="#"><span>01.</span>确定您的车型</a></li>
			<li class="b_li2"><a href="#"><span>02.</span>订单信息</a></li>
			<li class="b_li3 stepColor"><a href="#"><span>03.</span>核对订单信息</a></li>
			<li class="b_li3"><a href="#"><span>04.</span>下单成功</a></li>
		</ul>
		<div class="orderInfo">
		
			<div class="orderInfoTop">
				<c:if test="${order.isPay eq false }">
					<div <c:if test="${!freeTech}">style="display:none;"</c:if> class="hasTechnician">
						<b class="ma4_1_1" style="left: 340px; "></b>
						<p>附近有技师为您提供服务！</p>				
					</div>
					<div <c:if test="${freeTech}">style="display:none;"</c:if> class="hasNoTechnician">
						<b class="ma4_1"></b>
						<p>现在没有空闲的技师为您提供服务！</p>	
					</div>
					<a href="javascript:refreshFreeTech();" style="position:absolute; right: 320px; "><img src="${pageContext.request.contextPath}/resource/page/images/ma4_2_1.png" /></a>		
				</c:if>
			</div>
		
			<div class="orderInfoCon">
				<div class="jiao"><b class="jiaoTop"></b></div>
				<div class="orderInfoConDe">
					<ul>
						<li><b>姓名：</b>${order.contact }</li>
						<li><b>手机号：</b>${order.phone }</li>
					</ul>
					<ul>
						<li><b>服务车型：</b>${carBrand.name }&nbsp; ${carType.name }&nbsp; ${car.name }</li>
						<li><b>车身颜色：</b>${order.carColor }</li>
						<li><b>车牌号：</b>${order.carNum }</li>
					</ul>
					<ul>
						<li><b>清洗方式：</b><c:if test="${order.cleanType eq 1}">外观清洗</c:if><c:if test="${order.cleanType eq 2}">内外清洗</c:if></li>
						<li><b>服务地址：</b>${order.address}</li>
					</ul>
					<ul>
						<li><b>支付方式：</b>
							<c:if test="${order.payWay eq 1}">会员卡支付</c:if>
							<c:if test="${order.payWay eq 2}">支付宝</c:if>
							<c:if test="${order.payWay eq 3}">银联</c:if>
							<c:if test="${order.payWay eq 4}">微信</c:if>
						</li>
						<li><b>服务费用：</b><i>￥${order.amount_money }</i></li>
						<li><b>优惠券：</b><i>-￥${order.amount_paid }</i>（<em>
							<c:if test="${empty memberCoupon }">
								无优惠
							</c:if>
							<c:if test="${!empty memberCoupon }">
								${memberCoupon }
							</c:if></em>）</li>
						<li><b>实付金额：</b><i>￥${order.price }</i></li>
					</ul>					
				</div>
				<div class="jiao"><b class="jiaoButton"></b></div>
			</div>
			
				<c:if test="${order.isPay eq false }">
					<dl class="modify">
						<dd><a href="javascript:void(0);" onclick="modifyOrder();"><img src="${pageContext.request.contextPath}/resource/page/images/ma4_5.png" /></a></dd>
						<dd <c:if test="${freeTech}">style="display:none;"</c:if> class="hasNoTechnician"><a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/resource/page/images/ma4_6.png" /></a></dd>
						<dd <c:if test="${!freeTech}">style="display:none;"</c:if> class="hasTechnician"><a href="javascript:void(0);" onclick="payOrder();"><img src="${pageContext.request.contextPath}/resource/page/images/ma4_6_1.png" /></a></dd>
					</dl>
				</c:if>
				<c:if test="${order.isPay eq true }">
					<dl class="modify" style="padding-left: 0px;text-align: center;color: green;">
						<dd>该订单支付已完成</dd>
					</dl>
				</c:if>
			
		</div>			
	</div>
</div>

<div class="success" style="display: none;">
	<div class="mabg6" id="mabg6"></div>
	<div class="mabg2" id="mabg2"></div>
	<div class="malist" id="malist" style="margin:-23px 0 0 -82px;">
		<div class="malistTop">
			<h2><span id="time">3</span>秒自动跳转首页</h2>
			<a href="javascript:gotoIndex();" class="malistClose" id="malistClose"><img src="${pageContext.request.contextPath}/resource/page/images/login_2.png" /></a>
		</div>
		<div class="malistDown">
			<img src="${pageContext.request.contextPath}/resource/page/images/down_pic.png" />
			<p class="p1">很高兴能为您服务，祝工作顺利！</p>
			<p class="p2">我们正在派单中，谢谢！</p>
		</div>
	</div>
</div>
<div class="pay" style="display: none;">
		<div id="mabg8"></div>
		<div id="mabg9"></div>
		<div class="payReminders" style="margin: -21px 0 0 -83px;">
			<div class="payRemindersTitle">
				<span>网上支付提示</span>
				<a href="javascript:closeDiv();" class="closePayRem"><img src="${pageContext.request.contextPath}/resource/page/images/login_2.png" /></a>
			</div>
			<div class="payRemindersCon">
				<p>支付完成前，请不要关闭此支付验证窗口。</p>
				<p>支付完成后，请根据您支付的情况点击下面按钮。</p>
				<div class="reminder"></div>
			</div>
			<div class="payRemindersBottom">
				<div class="left"><a href="#">支付遇到问题</a></div>
				<div class="right "><a href="javascript:orderSuccess();">支付完成</a></div>
			</div>
		</div>
	</div>
	<div class="weixinPay" style="display: none;">
		<!-- <div id="mabg9"></div> -->
		<div id="mabg8"></div>
		<div class="payReminders" style="height: auto;">
			<div class="payRemindersTitle">
				<span>微信支付提示</span>
				<a href="javascript:closeDiv();" class="closePayRem"><img src="${pageContext.request.contextPath}/resource/page/images/login_2.png" /></a>
			</div>
			<div class="weixinBody">
				<p style="text-align: center;margin-top: 5px;">请用微信扫描二维码</p>
				<div id="qrcodeTable" class="bodyTable"></div>
			</div>
			<div class="payRemindersBottom" style="margin-bottom: 10px;">
				<div class="left"><a href="#">支付遇到问题</a></div>
				<div class="right "><a href="javascript:orderSuccess();">支付完成</a></div>
			</div>
		</div>
	</div>


<!--中部结束-->
<!--底部开始-->
	<jsp:include page="../web/footer.jsp" />
<!--底部结束-->
<script type="text/javascript">

	var sessionId = "${member.id}";
	var sessionMoney = "${member.money}";
	var orderPrice = "${order.price }";

	var confirmId = "${confimId}";
	var payWay = "${order.payWay}";
	var isPay = "${order.isPay}";
	//修改订单
	function modifyOrder(){
		var url = "<%=basePath%>/web/modify";
		var data = {
			confimId:confirmId
		};
		$.post(url,data,function(result){
			window.history.back(-1);
		},"json");
	}
	
	$(document).ready(function(){
		if(isPay == "true"){
			$(".success").show();
			$(".pay").hide();
			$(".weixinPay").hide();
			setInttervalTime();
		}
	});
	
	
	//将当前支付
	function payOrder(){
		
		if(payWay == "1"){//会员卡支付
			if(sessionId != ""){
				saveOrder();
			}else{
				alert("请先登录,才能使用会员卡支付");
				return;
			}			
		}else if(payWay == "4"){//微信支付
			weixinPay();
		}else if(payWay == "2"){//支付宝支付
			//$(".pay").show();
			alipay();
		}else if(payWay == "3"){//银联支付
			//unionPay();
		}
	}
	
	function alipay(){
		var url ="<%=basePath%>/web/payOrder";
		var data = {
			confimId:confirmId	
		};
		$.post(url,data,function(result){
			if(result.status == "success"){
				var value = result.message;
				var orderNum = value.orderNum;
				var orderPrice = value.orderPrice;
				var orderName = value.orderName;
				var address = "alipay_return";
				var returnAddress = "gotoConfirm";
				var openUrl = "<%=basePath%>alipay/alipayapi.jsp?orderNum="+orderNum+"&orderPrice="+orderPrice+"&orderName="+orderName+"&address="+address+"&returnAddress="+returnAddress;
				window.location.href = openUrl;
			//	window.open(openUrl);	
			}else{
				alert(result.message);
				$(".pay").hide();
				modifyOrder();
			}
		},"json");
	}
	
	function orderSuccess(){
		var url ="<%=basePath%>/web/findOrderNum";
		var data = {
			confimId:confirmId	
		};
		$.post(url,data,function(result){
			if(result.status == "success"){
				$(".success").show();
				$(".pay").hide();
				$(".weixinPay").hide();
				setInttervalTime();
			}else{
				alert("支付失败");
				$(".pay").hide();
				$(".weixinPay").hide();
			}
		},"json");
	}
	
	//保存订单
	function saveOrder(){
		var url = "<%=basePath%>/web/saveCleanCarOrder";
		var data = {
			confimId:confirmId
		};
		$.post(url,data,function(result){
			if(result.status == "success"){
				$(".success").show();
				setInttervalTime();
			}else{
				alert(result.message);
			}
		},"json");
	}
	
	//定时器
	var intterval;
	function setInttervalTime(){
		intterval = setInterval(function(){
			var timeOut = $("#time").text();
			timeOut = timeOut*1 - 1;
			if(timeOut <= 0){
				clearInterval(intterval);
				gotoIndex();
			}else{
				$("#time").text(timeOut);
			}
		},1000);
	}
	
	//跳转到首页
	function gotoIndex(){
		window.location.href="<%=basePath%>";
	}
	
	//刷新
	function refreshFreeTech(){
		var url = "<%=basePath%>web/getFreeTechnicain";
		var data = {confirmId:confirmId};
		$.post(url,data,function(result){
			if(result.status == "success"){
				$(".hasTechnician").show();
				$(".hasNoTechnician").hide();
			}else{
				$(".hasTechnician").hide();
				$(".hasNoTechnician").show();
			}
		});
	}
	
	function closeDiv(){
		$(".success").hide();
		$(".pay").hide();
		$(".weixinPay").hide();
		$("#mabg8").hide();
	}
	
	//微信支付
	function weixinPay(){
		var url = "<%=basePath%>web/pay/weixin/payOrder";
		var data =  {order_num:confirmId};
		$.post(url,data,function(result){
			if(result.status == "success"){
				var erwei = result.message;
				initQrcode(erwei);
				$(".weixinPay").show();
				$("#mabg8").show();
			}
		},"json");
	}
	
	//微信生成二维码
	function initQrcode(value){
		$("#qrcodeTable").html("");
		jQuery('#qrcodeTable').qrcode({
			render : "table",
			text : value
		});
	}
	
	//银联支付
	function unionPay(){
		var url = "<%=basePath%>web/pay/union/payOrder?order_num="+confirmId+"&returnAddress=gotoConfirm";
		window.location.href=url;
	}
	
</script>
</body>
</html>