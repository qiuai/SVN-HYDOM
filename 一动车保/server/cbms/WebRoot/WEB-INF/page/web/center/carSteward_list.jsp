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
	<title>一动车保首页</title>
	<link href="<%=basePath %>resource/page/css/carSteward/style.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="<%=basePath %>resource/page/js/jquery.js"></script>
	<style type="text/css">
		/******form*******/
		.divselectcon {clear: both; padding: 10px 0 20px; position: relative; }
		.divselect {float: left; }
		.divselectcon input {width:238px; height: 35px; margin: 0 20px; position:relative; z-index:10000; border: 1px solid #b1b1b1; border-top: 2px solid #b1b1b1; line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(<%=basePath %>resource/page/images/corners2.png) no-repeat right center;}
		.divselectcon input.input1 {border:1px solid #ffae00; border-top: 2px solid #ffae00; margin-left: 0; width: 228px; height:35px;line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(<%=basePath %>resource/page/images/corners1.png) no-repeat right center;}
		#button2 button {float: right; width: 169px; height: 43px; background: url(<%=basePath %>resource/page/images/go.png) 0 0 no-repeat; border: none; position: absolute; top: 9px;  cursor: pointer; }
		.bdsug_copy{display:none;}
	</style>
	<style type="text/css">
		#cancel1,#mabg2,#stbg2{
			display: none;
		}
	</style>
	<script type="text/javascript">
		function setDefaultCar(userCarId){
			var url ="<%=basePath%>/user/carSteward/setDefaultCar";
			var data = {
				userCarId : userCarId
			};
			$.post(url,data,function(result){
				parent.location.reload();
			},"json");
		}
		function del(userCarId){
			$("#cancel1").show();
			$("#mabg2").show();
			$("#stbg2").show();
			$("#yes").click(function(){
				var url ="<%=basePath%>/user/carSteward/del";
				var data = {
					userCarId : userCarId
				};
				$.post(url,data,function(result){
					alert(result.message);
					parent.location.reload();
				},"json");
			});
		}
		function hide(){
			$("#cancel1").hide();
			$("#mabg2").hide();
			$("#stbg2").hide();
		}
	</script>
</head>

<body>
<jsp:include page="../header.jsp"/>
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
			<div class="menu-title menu-home active">
				<h3>我的车管家</h3>
			</div>
			<ul class="menu-list">
				<li class="menu-item myCarModel on">
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
			<div id="cancelOrderContent" class="cancelOrderContent myOrderDetail"></div>
			<div id="myCouponContent" class="myCouponContent myOrderDetail"></div>
			<div id="myCarModelContent" class="myCarModelContent myOrderDetail">
				<div class="orderDetails" style="border: none; ">
					<div class="orderDetailsTop">
						<div class="left">
							<a href="#"><img src="<%=basePath %>resource/page/images/steward_4.png" /></a><span class="span1">|</span><span>爱车基本信息</span>
						</div>
						<div class="right"><a href="<%=basePath %>user/carSteward/add">新增爱车&gt;&gt;</a></div>
					</div>
					<div class="orderDetailsContent">
						<div class="carBasicInfo" id="carBasicInfo">
							<ul class="carBasicInfoCon">
								<c:forEach items="${userCars }" var="userCar" >
								<li class="lists">
									<dl class="listsdl1">
										<dd class="selectedLikes">
											<label><a><img src="<%=basePath %>${userCar.car.carBrand.imgPath }" /></a></label>
										</dd>
										<dd class="listsdd">
											<ul>
												<li>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：<i>${userCar.car.carBrand.name }</i></li>
												<li>车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系：<i>${userCar.car.carType.name }</i></li>
												<li>车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：<i>${userCar.car.name }</i></li>
											</ul>
										</dd>
										<dd>
											<ul>
												<li>车身颜色：<i>${userCar.carColor }</i></li>
												<li>车&nbsp;牌&nbsp;&nbsp;号：<i>${userCar.carNum }</i></li>
												<li>排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：<i>${userCar.engines }L</i></li>
											</ul>
										</dd>
										<dd>
											<ul>
												<li>油&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;耗：<i>${userCar.fuel }L/100km</i></li>
												<li>行驶里程：<i>${userCar.drange }km</i></li>
												<li>上路时间：<i><fmt:formatDate value="${userCar.roadDate }" pattern="yyyy-MM-dd"/></i></li>
											</ul>
										</dd>
									</dl>
									<dl class="modify">
										<dd><a href="javascript:setDefaultCar('${userCar.id }')">
											<c:if test="${userCar.defaultCar eq true }"><img src="<%=basePath %>resource/page/images/selec1.png" /></c:if>
											<c:if test="${userCar.defaultCar eq false }"><img src="<%=basePath %>resource/page/images/selec2.png" /></c:if>默认
										</a></dd>
										<dd><a href="<%=basePath %>user/carSteward/update?userCarId=${userCar.id }">修改</a></dd>
										<dd><a href="javascript:del('${userCar.id }')">删除</a></dd>
									</dl>
								</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
				
				<!--弹出层-->
				<div class="mabg2" id="mabg2"></div> <!--底背景-->
				<div class="stbg2" id="stbg2"></div>
				<div class="cancel1" id="cancel1">
					<div class="cancelTop">
						<a class="ste1Close" id="ste1Close" href="javascript:hide()"><img src="<%=basePath %>resource/page/images/login_2.png" /></a>
					</div>
					<div class="cancelContent">确定要删除此条爱车记录？</div>
					<div class="cancelBottom"><a href="javascript:hide()" class="cancelBottomno"><img src="<%=basePath %>resource/page/images/myOrder6.png" /></a><a id="yes"><img src="<%=basePath %>resource/page/images/myOrder7.png" /></a></div>
				</div>
			</div>
			<div id="accountBalContent" class="accountBalContent myOrderDetail"></div>
			<div id="basicInfoContent" class="basicInfoContent myOrderDetail"></div>
			<div id="changePassContent" class="changePassContent myOrderDetail"></div>
			<div id="feedBackContent" class="feedBackContent myOrderDetail"></div>
		</div>
	</div>
	
	<script>
	$(function(){
	function tabs(tabTit,on,active,tabCon){ 
		$(tabCon).each(function(){ 
			$(this).children().eq(3).show();  
		});
		$(tabTit).each(function(){
			//$(this).children().eq(3).addClass(on);
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
</div>
</div>
<!--中部结束-->
<!--底部开始-->
<div class="down">	
	<div class="span5">
		<div class="box0">
			<div class="span5l">
				<div class="down_pic"><img src="<%=basePath %>resource/page/images/down_pic.png" alt="" />
				</div>
				<div class="down_txt">
					<ul>
						<li><a href="#">一动车保</a>|</li>
						<li><a href="#">关于我们</a>|</li>
						<li><a href="#">服务简介</a>|</li>
						<li><a href="#">市场合作</a>|</li>
						<li><a href="#">联系我们</a></li>
					</ul>
					<p>&copy;2015&nbsp;www.YIDONGCHEBAO.com&nbsp; 贵ICP备18888888号</p>
				</div>
			</div>
			<div class="span5r">
				<ul>
					<li class="l1">咨询热线：400-855-9999</li>
					<li class="l2">咨询QQ：4008559999</li>
					<li class="l3">公司地址：贵州省贵阳市高新区688号</li>
				</ul>
				<div class="weixin">
					<img src="<%=basePath %>resource/page/images/code.png" alt="" />
					<p>扫一扫有惊喜！</p>
				</div>
			</div>
		</div>
	</div>
</div>
<!--底部结束-->
</body>
</html>