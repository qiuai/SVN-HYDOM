<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
		function backList(){
			$("#backListFrom").submit();
		}
		function topage(page){
			$("#page").val(page);
			$("#toPageFrom").submit();
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
<div class="box0 mid">
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
				<li class="menu-item myCoupon on">
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
				<li class="menu-item feedBack">
					<a href="${pageContext.request.contextPath}/user/feedback/add">意见反馈</a>
				</li>
			</ul>
		</div>
		<div class="steward1-detail" id="steward1-detail">
			<div id="myOrderContent" class="myOrderContent myOrderDetail" style="border: none; "></div>
			<div id="cancelOrderContent" class="cancelOrderContent myOrderDetail"></div>
			<div id="myCouponContent" class="myCouponContent myOrderDetail" style="border: none; ">
				<div class="orderDetails" style="border: none; ">
					<div class="orderDetailsTop">
						<form action="<%=basePath%>user/myCoupon/list" id="backListFrom" method="get">
						</form>
						<form action="<%=basePath%>user/myCoupon/history" id="toPageFrom" method="post">
							<input type="hidden" name="page" value="${page }" id="page">
						</form>
						<div class="right"><a href="javascript:backList();">返回我的优惠券&gt;&gt;</a></div>
					</div>
					<div class="orderDetailsContent">
						<table class="couponsTable">
							<thead>
								<tr>
									<th>名称</th>
									<th>获取方式</th>
									<th>是否已使用</th>
									<th>使用时间</th>
									<th>到期时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageView.records}" var="entry" >
								<tr rowspan="2">
									<td>${entry.name }</td>
									<td>
										<c:if test="${entry.gainWay eq 1 }"><fmt:formatNumber value="${entry.point }" type="number" pattern=""/>积分兑换</c:if>
										<c:if test="${entry.gainWay eq 2 }">系统发放</c:if>
										<c:if test="${entry.gainWay eq 3 }">充值赠送</c:if>
										<c:if test="${entry.gainWay eq 4 }">购买会员卡</c:if>
										<c:if test="${entry.gainWay eq 5 }">首次消费赠送</c:if>
									</td>
									<td>
										<c:if test="${2 eq entry.status}">已过期</c:if>
										<c:if test="${1 eq entry.status}">已使用</c:if>
										<c:if test="${0 eq entry.status}">未使用</c:if>
									</td>
									<td><fmt:formatDate value="${entry.useDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td><fmt:formatDate value="${entry.endDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								</tr>
								</c:forEach>
							</tbody>
						</table>						
					</div>
				</div>
				<div class="comment3">
					<ul class="couponsStep">
					<c:if test="${pageView.totalrecord > 0}">
						<c:if test="${(pageView.currentPage-1)<1}" >
							<li class="pre"><a>&lt;&lt;上一页</a></li>
						</c:if>
						<c:if test="${(pageView.currentPage-1)>=1}" >
							<li class="pre"><a href="javascript:topage(${pageView.currentPage-1})">&lt;&lt;上一页</a></li>
						</c:if>
						<li><a href="javascript:topage(1)">1</a></li>
						<c:if test="${pageView.pageIndex.startindex>2}" >
							<li> <a>...</a> </li> 
						</c:if>
						<c:forEach begin="${pageView.pageIndex.startindex}" end="${pageView.pageIndex.endindex}" var="per">
							<li><a href="javascript:topage(${per})">${per}</a></li>
						</c:forEach>
						<c:if test="${pageView.pageIndex.endindex<pageView.totalPage-1}" >
							<li> <a>...</a> </li>
						</c:if>
						<c:if test="${pageView.totalPage>=2}">
							<li> <a href='javascript:topage(${pageView.totalPage})' >${pageView.totalPage}</a> </li>
						</c:if>
						<c:if test="${(pageView.currentPage+1)>pageView.totalPage}" >
							<li class="next"><a>&gt;&gt;下一页</a></li>
						</c:if>
						<c:if test="${(pageView.currentPage+1)<=pageView.totalPage}" >
							<li class="next"><a href="javascript:topage(${pageView.currentPage+1})">&gt;&gt;下一页</a></li>
						</c:if>
					</c:if>
					<c:if test="${pageView.totalrecord <= 0 }">
						<div style="color: #CC0001; text-align: center;">
							<span>对不起，没有相关记录!</span>
						</div>
					</c:if>
					</ul>
				</div>
			</div>
		</div>
	</div>
	
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
</div>
</div>
<!--中部结束-->
<!--底部开始-->
<jsp:include page="../footer.jsp"/>
<!--底部结束-->
</body>
</html>