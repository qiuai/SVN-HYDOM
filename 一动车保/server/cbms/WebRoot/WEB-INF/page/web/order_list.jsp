<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
	<meta name="description" content="">
	<title>Chain Responsive Bootstrap3 Admin</title>
	<link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resource/css/manage.common.css" rel="stylesheet">
	
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
		/* $(function(){
			if($(".flag").length!=0){
				$(".abc").text("已评论");
			}else{
				$(".abc").html("未评论");
			}
		}); */
		
		function del(id){
			alert(id);
			if (confirm('您确定删除该订单吗？')) {
				$.ajax({
					url:"<%=base%>web/order/del",
					type: "POST",
					data:{id:id},
					dataType: "json",
					success: function(data) {
						
							alert(data.message);
						
					} 
					
				});
// 			 	var url = "${pageContext.request.contextPath}/web/order/delete";
// 				var data = {id:id};
// 				$.post(url,data,function(data) {
// 			      	if(data.status=="success"){
// 			      		alert(data.message);
// 			       	}
// 				},"json");
				location.reload();
				}
			}
	</script>
  </head>
  
  <body>
  1111111111111111111111
  <table>
    <c:forEach items="${pageView.records}" var="order" >  
<%--      --%>
							<tr>
								<td>
									<input type="checkbox" name="ids" value="${order.id}" /><a>${order.id}</a>&nbsp;
									<button id="deleteOrder" type="button" onclick="del('${order.id}')">删除</button>
								</td>
								<td>
									${order.price}
								</td>
								
								
								
								<c:forEach items="${order.serverOrder }" var="sOrder">
									<tr>
									<td>${sOrder.name }</td>
									<td>${sOrder.serviceType.name }</td>
									<td><a>评论</a></td>
									</tr>
								</c:forEach>
								<c:forEach items="${order.serverOrderDetail }" var="pOrder">
									<tr>
									<td>${pOrder.product.fullName}</td>
									<td>${pOrder.product.price }</td>
									<td><a>评论</a></td>
									</tr>
								</c:forEach>
								<td><span class="abc"></span></td>
							</tr>
							
						  </c:forEach>
						  </table>
  </body>
</html>
