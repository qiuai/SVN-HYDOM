<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style type="text/css">
	
</style>
<c:forEach var="value" items="${userCars }">
	<li class="lists">
		<dl>
			<dd class="selectedLikes">
				<label><b class="<c:if test="${value.id eq defaultCarId }">selectedLike</c:if>" id="${value.id }"></b><a href="javascript:void(0);"><img
						src="<%=basePath %>/${value.car.carBrand.imgPath}" /></a></label>
			</dd>
			<dd>
				<ul>
					<li>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：<i>${value.car.carBrand.name }</i></li>
					<li>车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系：<i>${value.car.carType.name }</i></li>
					<li>车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：<i>${value.car.name }</i></li>
				</ul>
			</dd>
			<dd>
				<ul>
					<li>车身颜色：<i>${value.carColor }</i></li>
					<li>车&nbsp;牌&nbsp;&nbsp;号：<i>${value.carNum }</i></li>
					<li>排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：<i>${value.engines }</i></li>
				</ul>
			</dd>
			<dd>
				<ul>
					<li>油&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;耗：<i>${value.fuel }</i></li>
					<li>行驶里程：<i>${value.drange }</i></li>
					<li>上路时间：<i><fmt:formatDate value="${value.roadDate }" pattern="yyyy-MM-dd"/></i></li>
				</ul>
			</dd>
		</dl>
	</li>
</c:forEach>




