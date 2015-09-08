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
	
	<script src="${pageContext.request.contextPath}/resource/chain/js/laydate/laydate.js"></script>
	
	<script src="${pageContext.request.contextPath}/resource/js/myform.js" type="text/javascript"></script>
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.min.js"></script>
	<![endif]-->
	
	<style type="text/css">
		#chooseTeamContent label{
			margin-right: 15px;
		}
	</style>
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
		            <li><a href="">市场部退款订单列表</a></li>
		          </ul>
		        </div>
		      </div><!-- media -->
		    </div>
		    <form id="pageList" action="${pageContext.request.contextPath}/manage/order/market_list" method="post" class="form-horizontal form-bordered">
		    	<input type="hidden" name="searchProp" value="${searchProp }"/>
			    <div class="contentpanel">
			      <div class="search-header">
			      
			      	<div class="form-group" style="border-top: 0px dotted #d3d7db;width: 49%;display: inline-block;">
						<label class="col-sm-3 control-label">订单编号</label>
						<div class="col-sm-8">
							<input type="text" name="orderNum" class="form-control" maxlength="200" value="${orderNum }"/>
						</div>
					</div>
			      	<div class="form-group" style="border-top: 0px dotted #d3d7db;width: 49%;display: inline-block;">
						<label class="col-sm-3 control-label">收货人电话</label>
						<div class="col-sm-8">
							<input type="text" name="orderPhone" class="form-control" maxlength="200" value="${orderPhone }"/>
						</div>
					</div>
					<div class="form-group" style="border-top: 0px dotted #d3d7db;width: 49%;display: inline-block;">
						<label class="col-sm-3 control-label">开始时间</label>
						<div class="col-sm-8">
							<div class="input-group">
                               <input type="text" class="form-control" placeholder="开始时间" id="datepicker" onclick="initPicker('datepicker');" name="startDate" value="${startDate }">
                               <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                           </div>
						</div>
					</div>
			      	<div class="form-group" style="border-top: 0px dotted #d3d7db;width: 49%;display: inline-block;">
						<label class="col-sm-3 control-label">结束时间</label>
						<div class="col-sm-8">
							<div class="input-group">
	                           <input type="text" class="form-control" placeholder="结束时间" id="datepicker1" onclick="initPicker('datepicker1');" name="endDate" value="${endDate }">
	                           <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                           </div>
						</div>
					</div>
			      
			        <div class="btn-list">
			        <%--   <button class="btn btn-danger" id="deleteButton" type="button" val="<%=path %>/manage/order" disabled>删除</button> --%>
			          <button class="btn btn-success" id="refreshButton">刷新</button>
			          <button type="button" class="btn btn-info btn-metro" onclick="confirmQuery();">查询</button>
					 <%--  <button id="add" type="button" class="btn btn-primary" val="<%=path %>/manage/order">添加</button> --%>
			          <%-- <div style="float: right;max-width: 340px;height: 37px;">
			            <div class="input-group" style="float: left;max-width: 240px;">
			              <input id="searchValue" placeholder="关键字查询" name="queryContent" value="${queryContent}" type="text" class="form-control" maxlength="50" style="height: 37px">
			            </div>
			            <div style="float: right">
			            	<button type="button" class="btn btn-info btn-metro" onclick="confirmQuery();">查询</button>
			            </div>
			          </div> --%>
			        </div>
			      </div>
			
			      <div class="table-responsive">
			        <table id="listTable" class="table table-info" >
			        	<thead>
							<tr>
								<!-- <th class="check">
									<input type="checkbox" id="selectAll"/>
								</th> -->
								<th>
									订单编号
								</th>
								<th>
									客户名称
								</th>
								<th>
									客户电话
								</th>
								<th>
									订单金额
								</th>
								<th>
									服务类型
								</th>
								<th>
									地区
								</th>
								<th>
									预约时间
								</th>
								<th>
									状态
								</th>
								<th>
									跟进车队
								</th>
								<th>
									创建时间
								</th>
								<th>
									操作
								</th>
							</tr>
						</thead>
						<c:forEach items="${pageView.records}" var="order">  
							<tr class="order ${order.id }">
								<%-- <td>
									<input type="checkbox" name="ids" value="${order.id}" />
								</td> --%>
								<td>
									<a href="<%=base %>/manage/order/detail?id=${order.id}" title="详情">${order.num }</a>
								</td>
								<td>
									${order.contact }
								</td>
								<td>
									${order.phone }
								</td>
								<td>
									${order.price }
								</td>
								<td>
									<%-- 类型 1洗车订单 2保养订单 3纯商品订单 --%>
									<c:if test="${order.type eq 1}">
										洗车订单
									</c:if>
									<c:if test="${order.type eq 2}">
										保养订单
									</c:if>
									<c:if test="${order.type eq 3}">
										商品订单
									</c:if>
								</td>
								<td>
									${order.area.treeFull }
								</td>
								<td>
									${order.dateTimeMap }
								</td>
								<td>
									${order.statusString }
								</td>
								<td>
									${order.carTeam.headMember}
								</td>
								<td>
									<span><fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd"/></span>
								</td>
								<td><!-- data-toggle="modal" data-target=".bs-example-modal-lg" -->
									
								<%-- 	<c:if test="${order.status eq 2}"> --%>
									<a href="javascript:agreeOrder('${order.id}');">[同意]</a>
								<%-- 	</c:if> --%>
									<a href="javascript:overOrder('${order.id}');">[驳回]</a>
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
   
   <script type="text/javascript">
   		var base = "<%=base %>";
   		
   		function initPicker(obj){
   			var pricker = {
   				elem : '#'+obj,
   				format : 'YYYY-MM-DD',
   				istime : true,
   				istoday : false
   			};
   			laydate(pricker);
   		}
   		
   		//完结订单
   		function agreeOrder(obj){
   			if(!confirm("是否同意该订单退费？")){
   				return;
   			}
   			//var tr = $(this).closest("tr.order");
   			//console.log(tr);
   			var url = base + "manage/order/marketAgree";
   			var data = {
   					orderId:obj
   			};
   			$.post(url,data,function(result){
   				if(result.status == "success"){
   					//tr.remove();
   					//if($("tr.order").length <= 0){
   						window.location.reload(true);
   					//}
   				}
   			},"json");
   		}
   		
   		//驳回的订单
   		function overOrder(obj){
   			if(!confirm("确定驳回该订单的退费操作?")){
   				return;
   			}
   			//var tr = $(this).closest("tr.order");
   			var url = base + "manage/order/overOrder";
   			var data = {
   					orderId:obj
   			};
   			$.post(url,data,function(result){
   				if(result.status == "success"){
   					//tr.remove();
   				//	if($("tr.order").length <= 0){
   						window.location.reload(true);
   					//}
   				}
   			},"json");
   		}
   </script>
</body>
</html>
