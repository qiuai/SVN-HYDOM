<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<link href="${pageContext.request.contextPath}/resource/chain/css/style.css" type="text/css" rel="stylesheet" />	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/page/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/page/js/function.js"></script>
	<style type="text/css">
		/******form*******/
		.divselectcon {clear: both; padding: 10px 0 20px; position: relative; }
		.divselect {float: left; }
		.divselectcon input {width:238px; height: 35px; margin: 0 20px; position:relative; z-index:10000; border: 1px solid #b1b1b1; border-top: 2px solid #b1b1b1; line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(images/corners2.png) no-repeat right center;}
		.divselectcon input.input1 {border:1px solid #ffae00; border-top: 2px solid #ffae00; margin-left: 0; width: 228px; height:35px;line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(images/corners1.png) no-repeat right center;}
		#button2 button {float: right; width: 169px; height: 43px; background: url(images/go.png) 0 0 no-repeat; border: none; position: absolute; top: 9px;  cursor: pointer; }
		.bdsug_copy{display:none;}		
	</style>
	
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/modernizr.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/pace.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/retina.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery.cookies.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
	<script src="${pageContext.request.contextPath}/resource/js/myform.js" type="text/javascript"></script>

</head>

<body>
<!--上部开始-->
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
				<li class="menu-item myOrder">
					<a href="${pageContext.request.contextPath}/user/order/list">我的订单</a>
				</li>
				<li class="menu-item cancelOrder">
					<a href="${pageContext.request.contextPath}/user/order/cancellist">已取消的订单</a>
				</li>
				<li class="menu-item myCoupon">
					<a href="${pageContext.request.contextPath}/user/myCoupon/list">我的优惠券</a>
				</li>
				<li class="menu-item couponPackage">
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
			<div class="menu-title menu-center active">
				<h3>账户中心</h3>
			</div>
			<ul class="menu-list">
				<li class="menu-item accountBal">
					<a href="${pageContext.request.contextPath}/user/balance/view">账户余额</a>
				</li>
				<li class="menu-item basicInfo">
					<a href="${pageContext.request.contextPath}/user/information/info">基本信息</a>
				</li>
				<!-- <li class="menu-item changePass">
					<a href="#">修改密码</a>
				</li> -->
				<li class="menu-item feedBack on">
					<a href="${pageContext.request.contextPath}/user/feedback/add">意见反馈</a>
				</li>
			</ul>
		</div>
		<div class="steward1-detail" id="steward1-detail">
		
			<div id="feedBackContent" class="feedBackContent myOrderDetail">
				<div class="feedBackDetails">
					<div class="feedBackDetailsTop">
						<div class="left">
							<a href="#"><img src="${pageContext.request.contextPath}/resource/page/images/comments1.png" /></a><span class="span1">|</span><span>意见反馈</span>
						</div>
					</div>
					<div class="feedBackDetailsContent">
						<div class="feedBackAccount">
						
							<p>尊敬的用户，您好！<br />非常感谢您对一动车保的喜爱，在使用中遇到什么不明白的地方吗？对一动车保有更好的意见或建议吗？<br />
请写下您对一动车保的宝贵意见，因为有您，我们将努力做到更好！<br />祝您工作愉快！</p>
<form action="${pageContext.request.contextPath}/user/feedback/savefeedback" method="post" id="savefeed">
<textarea name="Content" id="Content" style="border: 1px solid #d9d9d9; padding: 8px; width: 620px; height: 150px; color: #b3b2b2; font-size: 14px; line-height: 15px;" onfocus="if(this.value=='请点击这里写下您宝贵的意见……') {this.value='';}this.style.color='#6b6b6b';" onblur="if(this.value=='') {this.value='请点击这里写下您宝贵的意见……';this.style.color='#b3b2b2';}">请点击这里写下您宝贵的意见……</textarea>
							</form>
							<a href="javascript:savefeedback()"><img src="${pageContext.request.contextPath}/resource/page/images/account4.png" /></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function savefeedback(){
			var form = document.getElementById("savefeed");
			alert("反馈已提交！感谢您的支持！");
			form.submit();
			
		}
	</script>
	<script>
	$(function(){
	function tabs(tabTit,on,active,tabCon){ 
		$(tabCon).each(function(){ 
			$(this).children().eq(7).show();  
		});
		$(tabTit).each(function(){
			//$(this).children().eq(7).addClass(on);
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
	});


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
	});
	
			
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
</div>
</div>
<!--中部结束-->
<!--底部开始-->
<jsp:include page="../footer.jsp"></jsp:include>
<!--底部结束-->
</body>
</html>
