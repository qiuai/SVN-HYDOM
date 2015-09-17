<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<title>个人中心</title>
	<link href="<%=basePath %>resource/page/css/style.css" type="text/css" rel="stylesheet" />	
	<script type="text/javascript" src="<%=basePath %>resource/page/js/jquery.js"></script>
	<style type="text/css">
		/******form*******/
		.divselectcon {clear: both; padding: 10px 0 20px; position: relative; }
		.divselect {float: left; }
		.divselectcon input {width:238px; height: 35px; margin: 0 20px; position:relative; z-index:10000; border: 1px solid #b1b1b1; border-top: 2px solid #b1b1b1; line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(images/corners2.png) no-repeat right center;}
		.divselectcon input.input1 {border:1px solid #ffae00; border-top: 2px solid #ffae00; margin-left: 0; width: 228px; height:35px;line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(images/corners1.png) no-repeat right center;}
		#button2 button {float: right; width: 169px; height: 43px; background: url(images/go.png) 0 0 no-repeat; border: none; position: absolute; top: 9px;  cursor: pointer; }
		.bdsug_copy{display:none;}		
	</style>
	<script type="text/javascript">
		function history(){
			$("#couponHistoryForm").submit();
		}
		
		function buyCouponPackage(){
			var url ="<%=basePath%>user/couponPackage/buy";
			var data = {
				cpId : $("#cpId").val(),
				payWay : $("input[name='payWay']:checked").val()
			};
			$.post(url,data,function(result){
				if(result.result!="001"){
					alert(result.result);
				}else{
					if(result.payWay=="1"){
						if(result.payAction=="2") alert("支付完成！");
						if(result.payAction=="3") alert("余额不足！");
						location.reload();
					}else if(result.payWay=="2"){
						var openUrl = "<%=basePath%>alipay/alipayapi2.jsp?orderNum="+result.orderNum+"&orderPrice="+result.orderPrice+
							"&orderName=会员卡购买&address=alipay_recharge_return&returnAddress=<%=basePath%>user/couponPackage/list";
						window.location.href=openUrl;
					}else if(result.payWay=="3"){
						$("html").html(result.html);
					}else if(result.payWay=="4"){
						alert("微信支付暂未开通！");
					}else{
						alert(result.result);
					}
				}
				
			},"json");
		}
		
		function show(cpId){
			$("#cancel1").show();
			$("#mabg2").show();
			$("#stbg2").show();
			$("#cpId").val(cpId);
		}
		
		function hide(){
			$("#cancel1").hide();
			$("#mabg2").hide();
			$("#stbg2").hide();
		}
		
	</script>
</head>

<body>
<!--上部开始-->
<jsp:include page="../header.jsp"/>
<!--上部结束-->
<hr>
<!--中部开始-->
<div class="box3">
<div class="mid box0">
	<div class="steward1-content">
		<div class="steward1-menu" id="steward1-menu">
			<div class="menu-title menu-order active">
				<h3>订单中心</h3>
			</div>
			<ul class="menu-list">
				<li class="menu-item myOrder">
					<a href="${pageContext.request.contextPath}/user/order/list">我的订单</a>
				</li>
				<li class="menu-item cancelOrder">
					<a href="${pageContext.request.contextPath}/user/order/cancellist">已取消的订单</a>
				</li>
				<li class="menu-item myCoupon">
					<a href="${pageContext.request.contextPath}/user/myCoupon/list">我的优惠券</a>
				</li>
				<li class="menu-item couponPackage on">
					<a href="${pageContext.request.contextPath}/user/couponPackage/list">我的会员卡</a>
				</li>
			</ul>
			<div class="menu-title menu-home">
				<h3>我的车管家</h3>
			</div>
			<ul class="menu-list">
				<li class="menu-item myCarModel">
					<a href="${pageContext.request.contextPath}/user/carSteward/list">我的车型库</a>
				</li>
			</ul>
			<div class="menu-title menu-center">
				<h3>账户中心</h3>
			</div>
			<ul class="menu-list">
				<li class="menu-item accountBal">
					<a href="${pageContext.request.contextPath}/user/balance/view">账户余额</a>
				</li>
				<li class="menu-item basicInfo">
					<a href="${pageContext.request.contextPath}/user/information/info">基本信息</a>
				</li>
				<li class="menu-item feedBack">
					<a href="${pageContext.request.contextPath}/user/feedback/add">意见反馈</a>
				</li>
			</ul>
		</div>
		<div class="steward1-detail" id="steward1-detail">
			<div id="myOrderContent" class="myOrderContent myOrderDetail" style="border: none; "></div>
			<div id="cancelOrderContent" class="cancelOrderContent myOrderDetail" style="border: none; "></div>
			<div id="myCouponContent" class="myCouponContent myOrderDetail">
				<div class="orderDetails">
					<div class="orderDetailsTop">
						<form action="<%=basePath%>user/myCoupon/history" id="couponHistoryForm" method="post">
							<input type="hidden" name="memberId" value="${sessionScope.member_session.id }">
						</form>
						<div class="right"><a href="JavaScript:history()">查看历史记录&gt;&gt;</a></div>
					</div>
					<div class="orderDetailsContent">
						<ul class="couponsList">
							<c:forEach items="${couponPackages }" var="couponPackage" >
								<li><a href="javascript:show('${couponPackage.id }');"> <img src="<%=basePath %>${couponPackage.imgPath }" /></a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			
			<!--弹出层-->
			<div class="mabg2" id="mabg2" style="display: none;"></div> <!--底背景-->
			<div class="stbg2" id="stbg2" style="display: none;"></div>
			<div class="cancel1" id="cancel1" style="display: none;">
				<div class="cancelTop">
					<a class="ste1Close" id="ste1Close" href="javascript:hide()"><img src="<%=basePath %>resource/page/images/login_2.png" /></a>
				</div>
				<div class="cancelContent">
					<input type="radio" name="payWay" value="1" checked="checked">会员卡
					<input type="radio" name="payWay" value="2">支付宝
<!-- 					<input type="radio" name="payWay" value="3">银联 -->
					<input type="hidden" id="cpId">
				</div>
				<div class="cancelBottom">
					<a href="javascript:hide()" class="cancelBottomno"><img src="<%=basePath %>resource/page/images/myOrder6.png" /></a>
					<a id="yes" href="javascript:buyCouponPackage()"><img src="<%=basePath %>resource/page/images/myOrder7.png" /></a>
				</div>
			</div>
		</div>
	</div>
	
	
</div>
</div>
<!--中部结束-->
<!--底部开始-->
<jsp:include page="../footer.jsp"/>
<!--底部结束-->

<script>
	$(function(){
	function tabs(tabTit,on,active,tabCon){ 
		$(tabCon).each(function(){ 
			$(this).children().eq(2).show();  
		});
		$(tabTit).each(function(){
			//$(this).children().eq(2).addClass(on);
		});
		$(tabTit).children().click(function(){ 
			$(this).addClass(on).siblings().removeClass(on);
			var index = $(tabTit).children().index(this);
			$(tabCon).children().eq(index).show().siblings().hide();
			$(this).siblings().addClass(on);			
			$(this).siblings().removeClass(on);
			$(this).parent().siblings().children().removeClass(on);
			
			$(this).parent().siblings().removeClass(active);
			$(this).parent().prev().addClass(active);			
		});
	}	
	tabs(".menu-list","on","active","#steward1-detail");
	})


	$(function(){
		function tabs(tabTit,liSelected,tabCon){ 
			$(tabCon).each(function(){ 
				$(this).children().eq(0).show();  
			});
			$(tabTit).each(function(){
				//$(this).children().eq(0).addClass(liSelected);
			});
			$(tabTit).children().click(function(){ 
				$(this).addClass(liSelected).siblings().removeClass(liSelected);
				var index = $(tabTit).children().index(this);
				$(tabCon).children().eq(index).show().siblings().hide();
				$(this).siblings().addClass(liSelected);			
				$(this).siblings().removeClass(liSelected);
				$(this).parent().siblings().children().removeClass(liSelected);				
			});
		}
		tabs(".totalTitle","liSelected",".orderListsContent");
	})
	
	
			
	$(document).ready(function(){
		$(".selectInput").focus(function(){
			$(this).addClass("selected");
		});
		$(".selectInput").blur(function(){
			$(this).removeClass("selected");
		});
	});
		
	$(function(){  
 
		//判断浏览器是否支持placeholder属性
		supportPlaceholder='placeholder'in document.createElement('input'),
 
		placeholder=function(input){
	 
			var text = input.attr('placeholder'),
			defaultValue = input.defaultValue;
		 
			if(!defaultValue){
		 
			  input.val(text).addClass("phcolor");
			}
	 
			input.focus(function(){
		 
			  if(input.val() == text){
		   
				$(this).val("");
			  }
			});
	 
	  
			input.blur(function(){
		 
			  if(input.val() == ""){
			   
				$(this).val(text).addClass("phcolor");
			  }
			});
	 
			//输入的字符不为灰色
			input.keydown(function(){
		  
			  $(this).removeClass("phcolor");
			});
		  };
 
		  //当浏览器不支持placeholder属性时，调用placeholder函数
		  if(!supportPlaceholder){
		 
			$('input').each(function(){
		 
			  text = $(this).attr("placeholder");
		 
			  if($(this).attr("type") == "text"){
		 
				placeholder($(this));
			  }
			});
		  }
 
	});	
	</script>
</body>
</html>