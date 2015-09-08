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
	<%-- <script src="${pageContext.request.contextPath}/resource/js/startban.js" type="text/javascript"></script> --%>
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.min.js"></script>
	<![endif]-->
	<script type="text/javascript">
	
	
	$(document).ready(function(){

	  	
		var $start = $("#start");
		var $ban = $("#ban");
		var $selectAll = $("#selectAll");
		var $ids = $("#listTable input[name='ids']");
		$start.click( function() {
			var $this = $(this);
			if ($this.hasClass("disabled")) {
				return false;
			}
			var $checkedIds = $("#listTable input[name='ids']:enabled:checked");
			if (confirm("您确定要启用吗？") == true) {
				$.ajax({
					url: "${pageContext.request.contextPath}/manage/rechargebenefits/start",
					type: "POST",
					data: $checkedIds.serialize(),
					dataType: "json",
					cache: false,
					success: function(data) {
						if (data.status == "success") {
							alert(data.message);
							setTimeout(function() {
								location.reload(true);
							}, 0);
						
							if ($listTable.find("tr").size() <= 1) {
								setTimeout(function() {
									location.reload(true);
								}, 0);
							}
						}else{
							alert(data.message);
							setTimeout(function() {
								location.reload(true);
							}, 0);
						}
					}
				});
			}
		});
		
		$ban.click( function() {
			var $this = $(this);
			if ($this.hasClass("disabled")) {
				return false;
			}
			var $checkedIds = $("#listTable input[name='ids']:enabled:checked");
			if (confirm("您确定要禁用吗？") == true) {
				$.ajax({
					url: "${pageContext.request.contextPath}/manage/rechargebenefits/ban",
					type: "POST",
					data: $checkedIds.serialize(),
					dataType: "json",
					cache: false,
					success: function(data) {
						if (data.status == "success") {
							alert(data.message);
							setTimeout(function() {
								location.reload(true);
							}, 0);
							if ($listTable.find("tr").size() <= 1) {
								setTimeout(function() {
									location.reload(true);
								}, 0);
							}
						}else{
							alert(data.message);
							setTimeout(function() {
								location.reload(true);
							}, 0);
						}
					}
				});
			}
		});
		
		// 全选
		$selectAll.click( function() {
			var $this = $(this);
			var $enabledIds = $("#listTable input[name='ids']:enabled");
			if ($this.prop("checked")) {
				$enabledIds.prop("checked", true);
				if ($enabledIds.filter(":checked").size() > 0) {
					//$deleteButton.removeClass("disabled");
					$start.removeAttr("disabled");
					$ban.removeAttr("disabled");
					$contentRow.addClass("selected");
				} else {
					//$deleteButton.addClass("disabled");
					$start.attr("disabled", true);
					$ban.attr("disabled", true);
				}
			} else {
				$enabledIds.prop("checked", false);
				//$deleteButton.addClass("disabled");
				$start.attr("disabled", true);
				$ban.attr("disabled", true);
				$contentRow.removeClass("selected");
			}
		});
		// 选择
		$ids.click( function() {
			var $this = $(this);
			if ($this.prop("checked")) {
				$this.closest("tr").addClass("selected");
				//$deleteButton.removeClass("disabled");
				$start.removeAttr("disabled");
				$ban.removeAttr("disabled");
			} else {
				$this.closest("tr").removeClass("selected");
				if ($("#listTable input[name='ids']:enabled:checked").size() > 0) {
					//$deleteButton.removeClass("disabled");
					$start.removeAttr("disabled");
					$ban.removeAttr("disabled");
				} else {
					//$deleteButton.addClass("disabled");
					$start.attr("disabled", true);
					$ban.attr("disabled", true);
				}
			}
		});
		
	});
			
	</script>
</head>

<body>
	<%@ include file="/WEB-INF/page/common/head.jsp" %>
    <section>
    <div class="mainwrapper">
		<%@ include file="/WEB-INF/page/common/left.jsp" %>
         <div class="mainpanel">
		    <div class="pageheader">
		      <div class="media">
		        <div class="media-body">
		          <ul class="breadcrumb">
		            <li><a href=""><i class="glyphicon glyphicon-home"></i></a></li>
		            <li><a href="">充值优惠管理</a></li>
		          </ul>
		        </div>
		      </div><!-- media -->
		    </div>
		    <form id="pageList" action="${pageContext.request.contextPath}/manage/rechargebenefits/list" method="post">
			    <div class="contentpanel">
			      <div class="search-header">
			        <div class="btn-list">
			          <button class="btn btn-danger" id="deleteButton" type="button" val="<%=path %>/manage/rechargebenefits" disabled>删除</button>
			          <button class="btn btn-success" id="refreshButton">刷新</button>
					  <button id="add" type="button" class="btn btn-primary" val="<%=path %>/manage/rechargebenefits">添加</button>
			          <div style="float: right;max-width: 340px;height: 37px;">
			            <div class="input-group" style="float: left;max-width: 240px;">
			              <%-- <input id="searchValue" placeholder="关键字查询" name="queryContent" value="${queryContent}" type="text" class="form-control" maxlength="50" style="height: 37px"> --%>
			            </div>
			            <div style="float: right">
			            <button class="btn btn-danger" id="ban" type="button" disabled>禁用</button>
			            <button class="btn btn-success" id="start" type="button" disabled>启用</button>
			            	
			            </div>
			          </div>
			        </div>
			      </div>
			
			      <div class="table-responsive">
			        <table id="listTable" class="table table-info" >
			        	<thead>
							<tr>
								<th class="check">
									<input type="checkbox" id="selectAll"/>
								</th>
								<th>
									类型
								</th>
								<th>
									金额 
								</th>
								<th>
									是否启用
								</th>
								<th>
									操作
								</th>
							</tr>
						</thead>
						<c:forEach items="${pageView.records}" var="rechargeBenefits" >  
							<tr>
								<td>
									<input type="checkbox" name="ids" value="${rechargeBenefits.id}" />
								</td>
								<td>
								<c:if test="${rechargeBenefits.type==1}">充值返现</c:if>
								<c:if test="${rechargeBenefits.type==2}">充值送优惠券</c:if>
								</td>
								<td>
									${rechargeBenefits.money}
								</td>
								<td>
								<c:if test="${true eq rechargeBenefits.enable}">启用</c:if>
								<c:if test="${false eq rechargeBenefits.enable}">禁用</c:if>
								</td>
								<td>
								<%-- <button type="button" val="<%=path %>/manage/rechargebenefits">禁用</button>
			            		<button type="button">启用</button> --%>
									<a href="<%=base %>manage/rechargebenefits/edit?id=${rechargeBenefits.id}">修改</a>
								</td>
							</tr>
						  </c:forEach>
					</table>
		            <%@ include file="/WEB-INF/page/common/fenye.jsp" %>
			      </div>
			    </div>
		   	 </form>
		  </div>       
    </div> <!-- mainwrapper -->
    </section>
   
</body>
</html>
