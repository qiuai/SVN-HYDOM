<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">
	var showType = "${type}";
	$(document).ready(function(){
		var table = $(".productDetailTableDIV").find("table");
		var product_trs = $(table).find("tr.product_tr");
		for(var i = 0; i < product_trs.length; i++){
			var productId = $(product_trs[i]).attr("id");
			console.log(productId);
			var button = $("ul.popup_con_list button."+productId);
			if(showType == "1"){
				button.removeClass("genhuan").addClass("nogenhuan");
			}else{
				button.removeClass("goumai").addClass("nogoumai");
			}
			button.prop("disabled",true);
		}
	});
	
</script>
<ul class="popup_con_list" style="height: 410px;">
	<c:forEach var="value" items="${pageView.records }">
		<li class="list_1">
			<dl>
				<dd class="dd_0">
					<a href="<%=basePath%>${value.imgPath}" target="_blank"><img src="<%=basePath%>${value.imgPath}" alt=""
						style="height: 60px;width: 60px;" /></a>
				</dd>
				<dd class="dd_1">
					<a href="javascript:gotoProductDetail('${value.id }','${type}');"><span>${value.name }</span></a>
				</dd>
				<dd class="dd_2">
					<span>￥${value.marketPrice }</span>
				</dd>
				<dd class="dd_3">
					<c:choose>
						<c:when test="${type eq 1}">
							<button class="genhuan ${value.id}" onclick="showPageBtn('${value.id}','${value.marketPrice }','${value.name }','${type }')"></button>
						</c:when>
						<c:otherwise>
							<button type="button" class="goumai ${value.id}" onclick="showPageBtn('${value.id}','${value.marketPrice }','${value.name }','${type }')"></button>
						</c:otherwise>
					</c:choose>
					
					<%-- <button type="button" class="noUse" onclick="showPageBtn('${value.id}','${value.marketPrice }','${value.name }','${type }')"></button> --%>
					<%-- <c:choose>
						<c:when test="${value.id ne product.id }">
							<a href="javascript:void(0);" onclick="showPageBtn('${value.id}','${value.marketPrice }','${value.name }','${type }')"><img src="${pageContext.request.contextPath}/resource/page/images/t_6_1.png" alt="" /></a>
						</c:when>
						<c:otherwise>
							<a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/resource/page/images/t_6_1.png" alt="" /></a>
						</c:otherwise>
					</c:choose> --%>
				</dd>
			</dl>
		</li>
	</c:forEach>
</ul>
<div style="position:relative;height: 58px;">
	<jsp:include page="fenye.jsp" />
</div>