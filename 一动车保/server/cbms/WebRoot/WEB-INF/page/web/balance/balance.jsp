<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String base = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>一动车保</title>
<link
	href="${pageContext.request.contextPath}/resource/chain/css/style.css"
	type="text/css" rel="stylesheet" />
<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/chain/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/chain/js/function.js"></script> --%>
<style type="text/css">
/******form*******/
.divselectcon {
	clear: both;
	padding: 10px 0 20px;
	position: relative;
}

.divselect {
	float: left;
}

.divselectcon input {
	width: 238px;
	height: 35px;
	margin: 0 20px;
	position: relative;
	z-index: 10000;
	border: 1px solid #b1b1b1;
	border-top: 2px solid #b1b1b1;
	line-height: 35px;
	display: inline-block;
	color: #807a62;
	font-style: normal;
	background: url(images/corners2.png) no-repeat right center;
}

.divselectcon input.input1 {
	border: 1px solid #ffae00;
	border-top: 2px solid #ffae00;
	margin-left: 0;
	width: 228px;
	height: 35px;
	line-height: 35px;
	display: inline-block;
	color: #807a62;
	font-style: normal;
	background: url(images/corners1.png) no-repeat right center;
}

#button2 button {
	float: right;
	width: 169px;
	height: 43px;
	background: url(images/go.png) 0 0 no-repeat;
	border: none;
	position: absolute;
	top: 9px;
	cursor: pointer;
}

.bdsug_copy {
	display: none;
}

.zhifubao {
	/* background:
		url(${pageContext.request.contextPath}/resource/page/images/ma_9.png)
		0 0 no-repeat; */
}

.yinlian {
	/* background:
		url(${pageContext.request.contextPath}/resource/page/images/ma_10.png)
		0 0 no-repeat; */
}

.weixin {
	
}

.close{
	background:
		url(${pageContext.request.contextPath}/resource/page/images/login_2.png)
		0 0 no-repeat;
width: 40px;
height: 40px;
display: inline-block;
position: absolute;
top: 0px;
right: 0px;
cursor: pointer;

}


.payLabel {
	width: 40px;
	height: 40px;
	font-size: 14px;
	border: 1px solid #d1d1d1;
	padding: 10px;
	cursor: pointer;
}
.payLabel:HOVER{
	border: 1px solid rgb(255,174,0);
}
#mg1 {
	width: 100%;
	height: 100%;
	top: 0px;
	left: 0px;
	position: fixed;
	filter: alpha(opacity=50);
	opacity: 0.5;
	background: #000000;
	z-index: 99997;
}
.ma_buy_total0 {
clear: both;
border-top: 1px solid #ddd;
height: auto;
padding: 30px 20px;
padding: 10px 0 30px;
margin-bottom: 20px;
z-index: 99999;
position: fixed;
top: 10%;
left: 33%;
background-color: rgb(247,247,247);
box-shadow: 0 0 0 10px rgba(28, 28, 28, 0.3);
}
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
<script src="${pageContext.request.contextPath}/resource/js/myform.js"
	type="text/javascript"></script>
<!-- 微信二维码生成 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/weixin/qrcode/jquery.qrcode.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/weixin/qrcode/qrcode.js"></script>
</head>

<body>
	<jsp:include page="../header.jsp"></jsp:include>
	<!--上部结束-->
	<hr>
	<!--中部开始-->
	<div class="box3">
		<div class="mid box0">
			<div class="steward1-content">
				<div class="steward1-menu" id="steward1-menu">
					<div class="menu-title menu-order">
						<h3>订单中心</h3>
					</div>
					<ul class="menu-list">
						<li class="menu-item myOrder"><a
							href="${pageContext.request.contextPath}/user/order/list">我的订单</a>
						</li>
						<li class="menu-item cancelOrder"><a
							href="${pageContext.request.contextPath}/user/order/cancellist">已取消的订单</a>
						</li>
						<li class="menu-item myCoupon"><a
							href="${pageContext.request.contextPath}/user/myCoupon/list">我的优惠券</a>
						</li>
						<li class="menu-item couponPackage">
							<a href="${pageContext.request.contextPath}/user/couponPackage/list">我的会员卡</a>
						</li>
					</ul>
					<div class="menu-title menu-home">
						<h3>我的车管家</h3>
					</div>
					<ul class="menu-list">
						<li class="menu-item myCarModel"><a
							href="${pageContext.request.contextPath}/user/carSteward/list">我的车型库</a>
						</li>
					</ul>
					<div class="menu-title menu-center active">
						<h3>账户中心</h3>
					</div>
					<ul class="menu-list">
						<li class="menu-item accountBal"><a
							href="${pageContext.request.contextPath}/user/balance/view">账户余额</a>
						</li>
						<li class="menu-item basicInfo"><a
							href="${pageContext.request.contextPath}/user/information/info">基本信息</a>
						</li>
						<!-- <li class="menu-item changePass on">
					<a href="#">修改密码</a>
				</li> -->
						<li class="menu-item feedBack"><a
							href="${pageContext.request.contextPath}/user/feedback/add">意见反馈</a>
						</li>
					</ul>
				</div>
				<div class="steward1-detail" id="steward1-detail">

					<div id="accountBalContent" class="accountBalContent myOrderDetail">
						<div class="accountDetails">
							<div class="accountDetailsTop">
								<div class="left">
									<a href="#"><img
										src="${pageContext.request.contextPath}/resource/page/images/steward_4.png" /></a><span
										class="span1">|</span><span>账户余额</span>
								</div>
								<div class="right">
									<a href="${pageContext.request.contextPath}/user/balance/list">记录&gt;&gt;</a>
								</div>
							</div>
							<div class="accountDetailsContent">
								<div class="currentAccount">
									<p>
										当前账户余额：<span>￥${member.money }</span>
									</p>
									<a href="javascript:void(0);" onclick="showPayDiv();"><img
										src="${pageContext.request.contextPath}/resource/page/images/account1.png" /></a>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="mg1" style="display: none;"></div>
	<div class="zhifu" style="display: none;">
		<div class="ma_buy_total0">
			<div class="close" onclick="closePayDiv();"></div>
			<div class="ma_buy_total_left">
				<h2>1、请输入充值金额</h2>
				<ul>
					<li><input type="text" name='priceAccount'/></li>
				</ul>
			</div>
			<div class="ma_buy_total_left" style="clear: both;">
				<h2>2、选择充值方式</h2>
				<ul>
					<li><label class="zhifubao payLabel" >支付宝</label></li>
					<li><label class="yinlian payLabel">银联</label></li>
					<%-- <li><label class="weixin payLabel">微信</label></li> --%>
				</ul>
			</div>
		</div>
	</div><!--  style="display: none;" -->
	<div class="pay" style="display: none;">
		<div class="payReminders">
			<div class="payRemindersTitle">
				<span>网上支付提示</span>
				<a href="javascript:closePayDiv();" class="closePayRem"><img src="${pageContext.request.contextPath}/resource/page/images/login_2.png" /></a>
			</div>
			<div class="payRemindersCon">
				<p>充值完成前，请不要关闭此支付验证窗口。</p>
				<p>充值完成后，请根据您支付的情况点击下面按钮。</p>
				<div class="reminder"></div>
			</div>
			<div class="payRemindersBottom">
				<div class="left"><a href="#">充值遇到问题</a></div>
				<div class="right "><a href="javascript:orderSuccess();">充值完成</a></div>
			</div>
		</div>
	</div>
	<div class="weixinPay" style="display: none;">
		<!-- <div id="mabg9"></div> -->
		<div class="payReminders" style="height: auto;">
			<div class="payRemindersTitle">
				<span>微信充值提示</span>
				<a href="javascript:closePayDiv();" class="closePayRem"><img src="${pageContext.request.contextPath}/resource/page/images/login_2.png" /></a>
			</div>
			<div class="weixinBody">
				<p style="text-align: center;margin-top: 5px;">请用微信扫描二维码</p>
				<div id="qrcodeTable" class="bodyTable"></div>
			</div>
			<div class="payRemindersBottom" style="margin-bottom: 10px;">
				<div class="left"><a href="#">充值遇到问题</a></div>
				<div class="right "><a href="javascript:orderSuccess();">充值完成</a></div>
			</div>
		</div>
	</div>
	<!--中部结束-->
	<!--底部开始-->
	<jsp:include page="../footer.jsp"></jsp:include>
	<!--底部结束-->
	<script type="text/javascript">
		$(document).ready(function(){
			bindElement();
		});
		function bindElement(){
			$(document).on("click","label.payLabel",function(){
				var element = $(this);
				if(element.hasClass("zhifubao")){//支付宝
					alipay();
				}else if(element.hasClass("yinlian")){
					alert("该支付方式不可用,请选择其他充值方式");
					//unionPay();
				}else if(element.hasClass("weixin")){
					weixinPay();
				}
			});
		}
		
		function showPayDiv(){
			$("#mg1").show();
			$(".zhifu").show();
		}
		
		function closePayDiv(){
			$("#mg1").hide();
			$(".zhifu").hide();
			$(".pay").hide();
			$(".weixinPay").hide();
		}
		
		function orderSuccess(){
			window.location.reload(true);
		}
		
		function checkScale(value){
			var v = parseFloat(value);
			if(v > 0){
				return true;
			}
			return false;
		}
		
		function alipay(){
			var url ="<%=base%>/user/balance/pay/alipay";
			var priceAccount = $("input[name='priceAccount']").val();
			
			if(!checkScale(priceAccount)){
				alert("请填写大于0的数");
				return;
			}
			
			var data = {
				price: priceAccount,
				type:2
			}
			
			$(".zhifu").hide();
			//$(".pay").show();
			
			$.post(url,data,function(result){
				if(result.status == "success"){
					var value = result.message;
					var orderNum = value.orderNum;
					var orderPrice = value.orderPrice;
					var orderName = value.orderName;
					var address = "alipay_recharge_return";
					var returnAddress = "<%=base%>user/balance/view";
					var openUrl = "<%=base%>alipay/alipayapi.jsp?orderNum="+orderNum+"&orderPrice="+orderPrice+"&orderName="+orderName+"&address="+address+"&returnAddress="+returnAddress;
					window.location.href=openUrl;
					//window.open(openUrl);					
				}else{
					alert(result.message);
					$(".pay").hide();
					$("#mg1").hide();
					$(".weixinPay").show();
				}
			},"json");
		}
		
		//微信支付
		function weixinPay(){
			var url = "<%=base%>/user/balance/pay/weixinpay";
			var priceAccount = $("input[name='priceAccount']").val();
			if(!checkScale(priceAccount)){
				alert("请填写大于0的数");
				return;
			}
			var data = {
				price: priceAccount,
				type:4
			}
			$.post(url,data,function(result){
				if(result.status == "success"){
					var erwei = result.message;
					initQrcode(erwei);
					$(".zhifu").hide();
					$(".weixinPay").show();
					$("#mg1").show();
				}
			},"json");
		}
		//微信二维码生成
		function initQrcode(value){
			$("#qrcodeTable").html("");
			jQuery('#qrcodeTable').qrcode({
				render : "table",
				text : value
			});
		}
		
		//银联支付
		function unionPay(){
			var url = "<%=base%>/user/balance/pay/unionpay";
			var priceAccount = $("input[name='priceAccount']").val();
			if(!checkScale(priceAccount)){
				alert("请填写大于0的数");
				return;
			}
			var html = url + "?price="+priceAccount+"&type=3";
			window.location.href= url + "?price="+priceAccount;			
		}
		
	</script>
</body>
</html>