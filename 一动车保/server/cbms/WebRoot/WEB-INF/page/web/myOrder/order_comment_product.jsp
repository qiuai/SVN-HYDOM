<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<title>商品评论</title>
	<link href="${pageContext.request.contextPath}/resource/chain/css/style.css" type="text/css" rel="stylesheet" />
		
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/page/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/page/js/function.js"></script>
	<style type="text/css">
		/******form*******/
		.divselectcon {clear: both; padding: 10px 0 20px; position: relative; }
		.divselect {float: left; }
		.divselectcon input {width:238px; height: 35px; margin: 0 20px; position:relative; z-index:10000; border: 1px solid #b1b1b1; border-top: 2px solid #b1b1b1; line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(images/corners2.png) no-repeat right center;}
		.divselectcon input.input1 {border:1px solid #ffae00; border-top: 2px solid #ffae00; margin-left: 0; width: 228px; height:35px;line-height:35px; display:inline-block; color:#807a62; font-style:rmal; background:url(images/corners1.png) no-repeat right center;}
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
	<script type="text/javascript">
	function checkStar() {
		var star = $("#star").val();
		if(star == "" || star == null){
			$("#star_error").html("星级不能为空");
			$("#star_error").next().val("");
		}else{
			$("#star_error").next().val("success");}
	}
	function checkComment() {
		var comment = $("#comcon").val();
		if(comment == "" || comment == null){
			$("#comment_error").html("评论不能为空");
			$("#comcon").next().val("");
		}else{
			$("#comcon").next().val("success");}
	}
	
	function saveType() {
		checkStar();
		checkComment();
		
		$(function(){
			var flag = true;
			$(".repeat").each(function(){
				//alert($(this).attr("id")+":"+$(this).val());
				if($(this).val()!="success") flag = false;
			});
			if(flag){
				$("#productform").submit();
			}
		});
		
	}
	</script>
<STYLE type="text/css">

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
#star_error{
	color: red;
}
#comment_error{
	color: red;
}
</STYLE>
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
			<div class="menu-title menu-order active">
				<h3>订单中心</h3>
			</div>
			<ul class="menu-list">
				<li class="menu-item myOrder on">
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
				<!-- <li class="menu-item changePass">
					<a href="#">修改密码</a>
				</li> -->
				<li class="menu-item feedBack">
					<a href="${pageContext.request.contextPath}/user/feedback/add">意见反馈</a>
				</li>
			</ul>
		</div>
		<div class="steward1-detail" id="steward1-detail">
			<div id="myOrderContent" class="myOrderContent myOrderDetail" style="border: none; ">
				<div class="orderDetails">
					<div class="orderDetailsTop">
						<div class="left">
							<a href="#"><img src="${pageContext.request.contextPath}/resource/page/images/comments1.png" /></a><span class="span1">|</span><span>商品评论</span>
						</div>
						<div class="right"><a href="${pageContext.request.contextPath}/user/order/list">返回订单列表&gt;&gt;</a></div>
					</div>
					<div class="orderDetailsContent">
						<div class="left"><a href="#"><img src="<%=base%>${serverOrderDetail.product.imgPath }" height="165" width="165" /></a></div>
						<div class="right">
							<ul>
								<li><b>订单编号：</b>${serverOrderDetail.order.num }</li>
								<li><b>订单状态：</b>
								<c:if test="${serverOrderDetail.order.status==0 }">已完结</c:if>
												<c:if test="${serverOrderDetail.order.status==1 }">派单中</c:if>
												<c:if test="${serverOrderDetail.order.status==2 }">路途中</c:if>
												<c:if test="${serverOrderDetail.order.status==3 }">服务中</c:if>
												<c:if test="${serverOrderDetail.order.status==11 }">预约成功</c:if>
												<c:if test="${serverOrderDetail.order.status==12 }">已分配车队</c:if>
												<c:if test="${serverOrderDetail.order.status==21 }">已下单</c:if>
												<c:if test="${serverOrderDetail.order.status==22 }">配货中</c:if>
												<c:if test="${serverOrderDetail.order.status==23 }">送货中</c:if>
								</li>
								<li><b>下单时间：</b>${serverOrderDetail.order.createDate }</li>
							</ul>
							<ul>
								<li><b>商品名称：</b>${serverOrderDetail.product.fullName }</li>
								<li><b>价&nbsp;&nbsp;格：</b>￥${serverOrderDetail.product.price }</li>
								<li><b>数&nbsp;&nbsp;量：</b>${serverOrderDetail.count }</li>
							</ul>
							<ul>
								<li><b>支付方式：</b><c:if test="${serverOrderDetail.order.payWay==1 }">货到付款</c:if>
													<c:if test="${serverOrderDetail.order.payWay==2 }">支付宝</c:if>
													<c:if test="${serverOrderDetail.order.payWay==3 }">银联</c:if>
													<c:if test="${serverOrderDetail.order.payWay==4 }">微信</c:if>
													</li>
								<li><b>商品金额：</b><i>￥${serverOrderDetail.price*serverOrderDetail.count }</i></li>
								<li><b>服务费用：</b><i>￥${serverOrderDetail.serverOrder.serviceType.price }</i></li>
								<li><b>优&nbsp;惠&nbsp;券：</b><i>-￥${serverOrderDetail.order.amount_paid }</i></li>
								<li><b>实付金额：</b><i>￥${serverOrderDetail.price*serverOrderDetail.count+serverOrderDetail.serverOrder.serviceType.price-serverOrderDetail.order.amount_paid }</i></li>
							</ul>	
							
							<form id="productform" action="<%=base%>user/comment/saveproductcomment" method="post"  >
							 <ul class="scoreUl">
								<li><b>服务评分：</b><em title="2"></em><em title="4"></em><em title="6"></em><em title="8"></em><em title="10"></em>
								<span class="errorStyle" id="star_error"></span>
								<input type="hidden" class="repeat"/>
								
								</li>
							
								<li><b>商品评论：</b></li>
								<li class="commentsCon"><textarea id="comcon" name="content" rows="5" cols="46"></textarea>
								<input type="hidden" class="repeat"/>
								<span class="errorStyle" id="comment_error"></span>
								</li>
								<li><b>晒&nbsp;&nbsp;图：</b></li>
							</ul>
								<div class="img_div" id="imgs">	
										</div>
										<label>
											<span id="spanButtonPlaceholder"></span>
										</label>
										
								
								<input type="hidden" name="serverOrderDetailId" value="${serverOrderDetail.id }"/>
								<input type="hidden" name="star" id="star"/>
							</form>
																									
							<ul>
								<li class="post"><a href="javascript:saveType();"><img src="${pageContext.request.contextPath}/resource/page/images/comments6.png" /></a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				function addhtml(value){
					var li = "<li>"+
					"<img alt='' src='"+value+"' onerror='' id='show_img'/>"+
					"<input type='hidden' name='imgPath' value='"+value+"'/>"+
					"</li>";
					$("#imgs").append(li);
				}
				
				$(function(){
					//通过修改样式来显示不同的星级
					$("ul.scoreUl li em").bind("click",function(){
						var title = $(this).attr("title");
						var star = $("input[name=star]");
						star.val(title);
						
						var cl = $(this).attr("class");
						$(this).prevAll().addClass("no "+cl+"star");
						$(this).removeClass().addClass("no "+cl+"star");
						//$(this).blur();//去掉超链接的虚线框
					});
					
				})
			</script>
			
		</div>
		<jsp:include page="../../common/commentImgUpload.jsp"></jsp:include>
	</div>
	
	<script>
	$(function(){
	function tabs(tabTit,on,active,tabCon){ 
		$(tabCon).each(function(){ 
			$(this).children().eq(0).show();  
		});
		$(tabTit).each(function(){
			//$(this).children().eq(0).addClass(on);
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
<jsp:include page="../footer.jsp"></jsp:include>
<!--底部结束-->
</body>
</html>