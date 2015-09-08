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
	<script src="${pageContext.request.contextPath}/resource/chain/js/laydate/laydate.js"></script>
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
		            <li><a href="">订单列表</a></li>
		          </ul>
		        </div>
		      </div><!-- media -->
		    </div>
		    <form id="pageList" action="${pageContext.request.contextPath}/manage/order/list" method="post" class="form-horizontal form-bordered">
		    	<input type="hidden" name="searchProp" value="${searchProp }"/>
		    	<input type="hidden" name="endOrder" value="${endOrder}"/>
		    	
		    	
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
							<tr>
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
									<c:if test="${order.type eq 2 }"><%-- 说明是保养服务或者商品服务 --%>
										<c:if test="${order.status eq 11}">
											<a href="javascript:void(0);" onclick="showModal('${order.id}');">[分配车队]</a>
										</c:if>
										<c:if test="${order.status eq 12}">
											<a href="javascript:void(0);" <%-- onclick="endOrder('${order.id}');" --%> onclick="successOrder('${order.id}');">[完结]</a>
										</c:if>
									</c:if>
									<c:if test="${order.type eq 3}"><%-- 说明是商品服务 --%>
										<c:if test="${order.status eq 21}">
											<a href="javascript:void(0);" onclick="showModal('${order.id}');">[分配车队]</a>
										</c:if>
										<c:if test="${order.status eq 22}">
											<a href="javascript:void(0);" onclick="statusOrder('${order.id}');">[送货中]</a>
										</c:if>
										<c:if test="${order.status eq 23}">
											<a href="javascript:void(0);" onclick="successOrder('${order.id}');">[完结]</a>
										</c:if>
									</c:if>
									<c:if test="${order.type eq 1 && order.status ne 0 && order.status ne 35}">
										<a href="javascript:void(0);" onclick="successOrder('${order.id}');">[完结]</a>
									</c:if>
									<%-- <a href="javascript:void(0);" onclick="errorOrder('${order.id}');">[退费]</a> --%>
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
   
   <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
              <div class="modal-header">
                  <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                  <h4 class="modal-title">选择服务车队</h4>
              </div>
              <div class="form-horizontal form-bordered">
              	<div class="panel-body nopadding">
              	 <div class="form-group">
					<label class="col-sm-2 control-label"  style="text-align: right;">地区筛选</label>
						<div class="col-sm-8" id="area_div_select" style="padding-top: 10px;">
							<select onchange="getAreaList(this);" id="firstSelect" style="margin-right: 5px;">
								<option value=''>请选择</option>
								<c:forEach var="area" items="${areas }">
									<option value="${area.id }" <c:if test="${area.children.size()>0 }">class="hasNext"</c:if>
									>${area.name }</option>
								</c:forEach>
							</select>
						</div>
						<input type="hidden" value="" id="areaSelectId"/>
				  </div>
              	</div>
              </div>
              
              <input type="hidden" value="" id="chooseOrderId"/>
              <div class="modal-body" id="chooseTeamContent">
              		
              </div>
              <div class="modal-footer">
             		<button data-dismiss="modal" type="button" aria-hidden="true" class="btn btn-dark">关闭</button>
              		<button type="button" onclick="chooseTeam();" class="btn btn-primary mr5">确定</button>
              </div>
          </div>
        </div>
    </div>
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
   		function showModal(obj){
   			initSelect();
   			$("#chooseOrderId").val(obj);
   			$("#areaSelectId").val("");
   			loadCarTeam();
   			/* var url = base + "manage/order/getCarTeam";
   			var data = {orderId:obj};
   			$.post(url,data,function(result){
   				if(result.status == "success"){
   					addLabel(result.message);
   				}else{
   					addError();
   				}
   			},"json"); */
   			
   			$(".bs-example-modal-lg").modal();
   		}
   		
   		function initSelect(){
   			$("#firstSelect").nextAll().remove();
   			$("#firstSelect").find("option[value='']").prop("selected",true);
   		}
   		
   		function loadCarTeam(){
   			var inputPageValue = $("#inputTeamPage").val();
   			var areaId = $("#areaSelectId").val();
   			var data = {
   				areaId:areaId,
   				page:inputPageValue
   			}
   			var url = base + "manage/order/getCarTeam";
   			$("#chooseTeamContent").load(url,data,function(){});
   		}
		   		
   		//选择车队
   		function chooseTeam(){
   			var carTeamId = $("input[name='teamIds']:checked").val();
   			/* var carTeamId = "";
   			if(carTeam.length == 1){
   				carTeamId = $(carTeam[0]).val();
   			} */
   		//	alert(carTeamId == undefined);
   			if(carTeamId == undefined || carTeamId == ""){
   				alert("请选择车队");
   				return;
   			}
   			var orderId = $("#chooseOrderId").val();
   			var url = base + "manage/order/updateOrderToTeam";
   			var data = {
   					teamId:carTeamId,
   					orderId:orderId
   			};
   			$.post(url,data,function(result){
   				if(result.status == "success"){
   					window.location.reload(true);
   				}else{
   					alert("选择车队失败");
   				}
   			},"json");
   		
   		}
   		
   		//送货中
   		function statusOrder(obj){
   			var url = base + "manage/order/orderstatus";
   			var data = {
   					orderId:obj
   			};
   			$.post(url,data,function(result){
   				if(result.status == "success"){
   					window.location.reload(true);
   				}
   			},"json");
   		}
   		
   		//完结订单
   		function successOrder(obj){
   			var url = base + "manage/order/endOrder";
   			var data = {
   					orderId:obj
   			};
   			$.post(url,data,function(result){
   				if(result.status == "success"){
   					window.location.reload(true);
   				}
   			},"json");
   		}
   		
   		 //失败订单
   		function errorOrder(obj){
   			if(!confirm("该订单是否申请退费?")){
   				return;
   			}
   			var url = base + "manage/order/errorOrder";
   			var data = {
   					orderId:obj
   			};
   			$.post(url,data,function(result){
   				if(result.status == "success"){
   					window.location.reload(true);
   				}
   			},"json");
   		} 
   		
   	    //选择地区
		function getAreaList(e){
			delSelect(e);
			var option = $(e).find("option:selected");
			var id = option.val();
			$("#areaSelectId").val(id);
			loadCarTeam();
			if(!option.hasClass("hasNext")){
				return;
			}
			var url = base + "/manage/area/findArea";
			$.post(url,{id:id},function(result){
				if(result.status == "success"){
					var values = result.message;
					addSelectElement(values);
				}
			},"json");
		}
		
		//设置地区
		function delSelect(e){
			$(e).nextAll().remove();
		}
		
		//添加选择元素
		function addSelectElement(value){
			var options = "<option value=''>请选择</option>";
			for(var i in value){
				options += "<option value='"+value[i].id+"' class='"+value[i].hasNext+"'>"+value[i].text+"</option>";
			}
			var select = "<select onchange='getAreaList(this);' style='margin-right: 5px;'>"+options+"</select>";
			$("#area_div_select").append(select);
		}
		
		/* 分页三方法 */
		
		//跳转分页
		function topageTeam(page) {
			//var form = document.getElementById("pageList");
			$("#inputTeamPage").val(page);
			/*console.log(form);
			form.page.value = page;*/
			loadCarTeam();
		}

		// 跳转到指定页面
		function goTeam(totalPage) {
			var inputPageValue = $("#inputTeamPage").val();
			if (inputPageValue > totalPage) {
				alert("超过最大页数: " + totalPage);
			} else if (inputPageValue < 1) {
				alert("页码数必须大于等于1");
			} else {
				//var form = document.getElementById("pageList");
				//form.page.value = inputPageValue;
				loadCarTeam();
			}
		}
   </script>
</body>
</html>
