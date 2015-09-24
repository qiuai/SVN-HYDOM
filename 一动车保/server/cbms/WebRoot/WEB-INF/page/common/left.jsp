<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/classes/META-INF/td.tld" prefix="td"%>

<div class="leftpanel">
	<div class="media profile-left">
		<a class="pull-left profile-thumb" 	href='' >
			<img class="img-circle" src="${pageContext.request.contextPath}/resource/chain/images/photos/profile.png" alt=""> 
		</a>
		<div class="media-body">
			<h4 class="media-heading">${loginAccount.nickname}</h4>
		</div>
	</div>
	<!-- media -->

	<h3 class="leftpanel-title">
		菜单
	</h3>
	<ul class="nav nav-pills nav-stacked">
		<li <c:if test="${m==null}">class="active"</c:if> >
			<a href=""><i class="fa fa-home"></i> <span>首页</span>
			</a>
		</li>
		<li class="<c:if test="${param.m==1||m==1}">active</c:if> parent" >
			<a href=""><i class="fa fa-bars"></i><span>商品管理</span></a>
			<ul class="children">
				<td:permission privilegeValue="manage/product/list">
					<li><a href='${pageContext.request.contextPath}/manage/product/list'>商品管理</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/productAttribute/list">
					<li><a href='${pageContext.request.contextPath}/manage/productAttribute/list'>商品筛选属性</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/parameterGroup/list">
					<li><a href='${pageContext.request.contextPath}/manage/parameterGroup/list'>商品参数</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/specification/list">
					<li><a href='${pageContext.request.contextPath}/manage/specification/list'>商品规格</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/productCategory/list">
					<li><a href='${pageContext.request.contextPath}/manage/productCategory/list'>商品分类</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/productBrand/list">
					<li><a href='${pageContext.request.contextPath}/manage/productBrand/list'>商品品牌</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/productLabel/list">
					<li><a href='${pageContext.request.contextPath}/manage/productLabel/list'>商品售后服务</a></li>
				</td:permission>
				
			</ul>
		</li>
		<li class="<c:if test="${param.m==2||m==2}">active</c:if> parent" >
			<a href=""><i class="fa fa-bars"></i><span>车型管理</span></a>
			<ul class="children">
				<td:permission privilegeValue="manage/carBrand/list">
					<li><a href='${pageContext.request.contextPath}/manage/carBrand/list'>品牌管理</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/carType/lis">
					<li><a href='${pageContext.request.contextPath}/manage/carType/list'>车系管理</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/car/list">
					<li><a href='${pageContext.request.contextPath}/manage/car/list'>车型管理</a></li>
				</td:permission>
			</ul>
		</li>
		<li class="<c:if test="${param.m==4||m==4}">active</c:if> parent" >
			<a href=""><i class="fa fa-bars"></i><span>服务管理</span></a>
			<ul class="children">
				<td:permission privilegeValue="manage/serviceType/list">
					<li><a href='${pageContext.request.contextPath}/manage/serviceType/list'>服务列表</a></li>
				</td:permission>
				<%-- <li><a href='${pageContext.request.contextPath}/manage/serviceStore/list'>服务门店</a></li> --%>
			</ul>
		</li>
		<li class="<c:if test="${param.m==3||m==3}">active</c:if> parent" >
			<a href=""><i class="fa fa-bars"></i><span>帐号管理</span></a>
			<ul class="children">
				<td:permission privilegeValue="manage/account/list">
					<li><a href='${pageContext.request.contextPath}/manage/account/list'>系统帐号管理</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/account/group/list">
					<li><a href='${pageContext.request.contextPath}/manage/account/group/list'>角色定义</a></li>
				</td:permission>
			</ul>
		</li>
		<li class="<c:if test="${param.m==5||m==5}">active</c:if> parent" >
			<a href=""><i class="fa fa-bars"></i><span>订单管理</span></a>
			<ul class="children">
				<td:permission privilegeValue="manage/order/list">
					<li><a href='${pageContext.request.contextPath}/manage/order/list'>订单管理</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/order/list#endOrder=true">
					<li><a href='${pageContext.request.contextPath}/manage/order/list?endOrder=true'>已完结订单</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/order/market_list">
					<li><a href='${pageContext.request.contextPath}/manage/order/market_list'>市场部带审核列表</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/order/finance_list">
					<li><a href='${pageContext.request.contextPath}/manage/order/finance_list'>财务部带审核列表</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/order/cancel_list">
					<li><a href='${pageContext.request.contextPath}/manage/order/cancel_list'>退款成功列表</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/comment/list">
					<li><a href='${pageContext.request.contextPath}/manage/comment/list'>订单评论列表</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/order/repush/list">
					<li><a href='${pageContext.request.contextPath}/manage/order/repush/list'>订单手动推送</a></li>
				</td:permission>
			</ul>
		</li>
		
		<li class="<c:if test="${param.m==6||m==6}">active</c:if> parent" >
			<a href=""><i class="fa fa-bars"></i><span>地区管理</span></a>
			<ul class="children">
				<td:permission privilegeValue="manage/area/list">
					<li><a href='${pageContext.request.contextPath}/manage/area/list'>地区列表</a></li>
				</td:permission>
			</ul>
		</li>
		
		<li class="<c:if test="${param.m==7||m==7}">active </c:if> parent" >
			<a href=""><i class="fa fa-bars"></i><span>会员管理</span></a>
			<ul class="children">
				<td:permission privilegeValue="manage/member/list">
					<li><a href='${pageContext.request.contextPath}/manage/member/list'>会员列表</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/memberRank/list">
					<li><a href='${pageContext.request.contextPath}/manage/memberRank/list'>会员等级</a></li>
				</td:permission>
			</ul>
		</li>
		
		<%-- <li class="<c:if test="${param.m==8||m==8}">active</c:if> parent" >
			<a href=""><i class="fa fa-bars"></i><span>内容管理</span></a>
			<ul class="children">
				<li><a href='${pageContext.request.contextPath}/manage/navigation/list'>导航管理</a></li>
				<li><a href='${pageContext.request.contextPath}/manage/ad/list'>广告管理</a></li>
			</ul>
		</li> --%>
		<li class="<c:if test="${param.m==9||m==9}">active</c:if> parent" >
			<a href=""><i class="fa fa-bars"></i><span>优惠券管理</span></a>
			<ul class="children">
				<td:permission privilegeValue="manage/coupon/list">
					<li><a href='${pageContext.request.contextPath}/manage/coupon/list'>优惠券管理</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/sendCoupon/add">
					<li><a href='${pageContext.request.contextPath}/manage/sendCoupon/add'>优惠券发放</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/rechargebenefits/list">
					<li><a href='${pageContext.request.contextPath}/manage/rechargebenefits/list'>充值返现/送优惠券</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/couponPackage/list">
					<li><a href='${pageContext.request.contextPath}/manage/couponPackage/list'>会员卡管理</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/firstSpendSendCoupon/list">
					<li><a href='${pageContext.request.contextPath}/manage/firstSpendSendCoupon/list'>首次消费赠送优惠券管理</a></li>
				</td:permission>
			</ul>
		</li>
		<li class="<c:if test="${param.m==10||m==10}">active</c:if> parent" >
			<a href=""><i class="fa fa-bars"></i><span>车队/技师管理</span></a>
			<ul class="children">
				<td:permission privilegeValue="manage/carTeam/list">
					<li><a href='${pageContext.request.contextPath}/manage/carTeam/list'>车队管理</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/technician/list">
					<li><a href='${pageContext.request.contextPath}/manage/technician/list'>技师帐号管理</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/electronicMap/list">
					<li><a href='${pageContext.request.contextPath}/manage/electronicMap/list'>电子地图</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/workLog/list">
					<li><a href='${pageContext.request.contextPath}/manage/workLog/list'>考勤管理</a></li>
				</td:permission>
			</ul>
		</li>
		<li class="<c:if test="${param.m==11||m==11}">active</c:if> parent" >
			<a href=""><i class="fa fa-bars"></i><span>新闻管理</span></a>
			<ul class="children">
				<td:permission privilegeValue="manage/news/list">
					<li><a href='${pageContext.request.contextPath}/manage/news/list'>新闻管理</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/news/add">
					<li><a href='${pageContext.request.contextPath}/manage/news/add'>新增新闻</a></li>
				</td:permission>
				
			</ul>
		</li>
		<li class="<c:if test="${param.m==13||m==13}">active</c:if> parent" >
			<a href=""><i class="fa fa-bars"></i><span>广告管理</span></a>
			<ul class="children">
				<td:permission privilegeValue="manage/advert/list">
					<li><a href='${pageContext.request.contextPath}/manage/advert/list'>广告管理</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/advert/add">
					<li><a href='${pageContext.request.contextPath}/manage/advert/add'>添加广告</a></li>
				</td:permission>
			</ul>
		</li>
		<li class="<c:if test="${param.m==12||m==12}">active</c:if> parent" >
			<a href=""><i class="fa fa-bars"></i><span>系统设置</span></a>
			<ul class="children">
				<td:permission privilegeValue="manage/system/view">
					<li><a name="memberId" href='${pageContext.request.contextPath}/manage/system/view'>系统参数</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/system/deleteView">
					<li><a name="memberId" href='${pageContext.request.contextPath}/manage/system/deleteView'>技师app图片删除</a></li>
				</td:permission>
				<td:permission privilegeValue="manage/server/list">
					<li><a name="memberId" href='${pageContext.request.contextPath}/manage/server/list'>关于我们</a></li>
				</td:permission>
			</ul>
		</li>
		<li class="<c:if test="${param.m==14||m==14}">active</c:if> parent" >
			<a href=""><i class="fa fa-bars"></i><span>用户反馈</span></a>
			<ul class="children">
				<td:permission privilegeValue="manage/feedback/list">
					<li><a name="memberId" href='${pageContext.request.contextPath}/manage/feedback/list'>用户反馈</a></li>
				</td:permission>
			</ul>
		</li>
	</ul>
</div>
<script type="text/javascript">
	
</script>
<!-- leftpanel -->