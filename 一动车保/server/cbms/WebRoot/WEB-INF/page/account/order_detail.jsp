<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String base = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<title>Chain Responsive Bootstrap3 Admin</title>
<link
	href="${pageContext.request.contextPath}/resource/chain/css/style.default.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resource/css/manage.common.css"
	rel="stylesheet">

<script
	src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/jquery-migrate-1.2.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/modernizr.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/pace.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/retina.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/jquery.cookies.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/myform.js"
	type="text/javascript"></script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.min.js"></script>
	<![endif]-->
<style type="text/css">
#chooseTeamContent label {
	margin-right: 15px;
}
.left_panel_body{
	display: inline-block;
	width: 100%;
}
div.form-group{
	 /* width: 49%;
	 display: inline-block;  */
}
.form-bordered .form-group {
  margin: 0;
  padding: 0px 10px;
 }
</style>

</head>

<body>
	<%@ include file="/WEB-INF/page/common/head.jsp"%>
	<section>
		<div class="mainwrapper">
			<%@ include file="/WEB-INF/page/common/left.jsp"%>
			<div class="mainpanel">
				<div class="pageheader">
					<div class="media">
						<div class="media-body">
							<ul class="breadcrumb">
								<li><a href=""><i class="glyphicon glyphicon-home"></i></a></li>
								<li><a href="">订单详情</a></li>
							</ul>
						</div>
					</div>
					<!-- media -->
				</div>
				<div class="contentpanel form-horizontal form-bordered">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">订单编号:${entity.num }</h4>
						</div>
						<div class="left_panel_body">
							<div class="panel-body nopadding">
								<div class="form-group">
									<label class="col-sm-2 control-label">订单联系人</label>
									<div class="col-sm-8">
										<label class="control-label">${entity.contact }</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">订单联系电话</label>
									<div class="col-sm-8">
										<label class="control-label">${entity.phone }</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">服务车型</label>
									<div class="col-sm-8">
										<label class="control-label">
											${entity.car.carBrand.name }&nbsp;${entity.car.carType.name }&nbsp;${entity.car.name }
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">车辆颜色</label>
									<div class="col-sm-8">
										<label class="control-label">
											${entity.carColor }
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">车牌号</label>
									<div class="col-sm-8">
										<label class="control-label">
											${entity.carNum }
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">行驶路程</label>
									<div class="col-sm-8">
										<label class="control-label">
											${entity.drange } km
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">服务地址</label>
									<div class="col-sm-8">
										<label class="control-label">
											${entity.address }
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">预约时间</label>
									<div class="col-sm-8">
										<label class="control-label">
											${entity.dateTimeMap }
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">订单类型</label>
									<div class="col-sm-8">
										<label class="control-label">
											<c:if test="${entity.type eq 1}">
												洗车订单
											</c:if>
											<c:if test="${entity.type eq 2}">
												保养订单
											</c:if>
											<c:if test="${entity.type eq 3}">
												商品订单
											</c:if>
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">商品金额</label>
									<div class="col-sm-8">
										<label class="control-label">
											${productSum }
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">服务金额</label>
									<div class="col-sm-8">
										<label class="control-label">
											${serverSum}
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">优惠价</label>
									<div class="col-sm-8">
										<label class="control-label">
											${youhuiSum }${youhuiRemark }
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">实付金额</label>
									<div class="col-sm-8">
										<label class="control-label">
											${entity.price }
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">支付方式</label>
									<div class="col-sm-8">
										<label class="control-label">
											<c:if test="${entity.payWay eq 1}">会员卡支付</c:if>
											<c:if test="${entity.payWay eq 2}">支付宝</c:if>
											<c:if test="${entity.payWay eq 3}">银联</c:if>
											<c:if test="${entity.payWay eq 4}">微信</c:if>
											<c:if test="${entity.payWay eq 5}">现在支付</c:if>
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">订单状态</label>
									<div class="col-sm-8">
										<label class="control-label">
											${entity.statusString }
										</label>
									</div>
								</div>
								<c:choose>
									<c:when test="${entity.type eq 1}">
										<div class="form-group">
											<label class="col-sm-2 control-label">
												清洗方式
											</label>
											<div class="col-sm-8">
												<label class="control-label">
													<c:if test="${entity.cleanType eq 1}">
														内部清洗
													</c:if>
													<c:if test="${entity.cleanType eq 2}">
														内外清洗
													</c:if>
												</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">
												服务技师
											</label>
											<div class="col-sm-8">
												<label class="control-label">
													<c:if test="${!empty entity.techMember}">
														${entity.techMember.name }
													</c:if>
													<c:if test="${empty entity.techMember}">
														暂无服务技师
													</c:if>
												</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">接单时间</label>
											<div class="col-sm-8">
												<label class="control-label">
													<fmt:formatDate value="${entity.ordersDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
												</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">服务时间</label>
											<div class="col-sm-8">
												<label class="control-label">
													<fmt:formatDate value="${entity.makeStartDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
												</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">结束服务时间</label>
											<div class="col-sm-8">
												<label class="control-label">
													<fmt:formatDate value="${entity.makeEndDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
												</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">
												接单距离(公里)
											</label>
											<div class="col-sm-8">
												<label class="control-label">
													${entity.distance }
												</label>
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<div class="form-group">
											<label class="col-sm-2 control-label">
												服务时间
											</label>
											<div class="col-sm-8">
												<label class="control-label">
													<fmt:formatDate value="${entity.makeStartDate }" pattern="yyyy-MM-dd HH:mm"/> - <fmt:formatDate value="${entity.makeEndDate }" pattern="yyyy-MM-dd HH:mm"/>
												</label>
											</div>
										</div>
									   	<div class="form-group">
											<label class="col-sm-2 control-label">
												服务车队
											</label>
											<div class="col-sm-8">
												<label class="control-label">
													<c:if test="${empty entity.carTeam }">
														<span style="color:red;">暂无</span>
													</c:if>
													<c:if test="${empty entity.carTeam }">
														${entity.carTeam.headMember }
													</c:if>
												</label>
											</div>
										</div>
										<c:if test="${entity.serverOrderDetail.size() > 0}">
											<div class="form-group" style="width: 100%;"><!--  style="width: 17%;" -->
												<label class="col-sm-2 control-label">商品列表</label>
												<div class="col-sm-8">
													<div class="table-responsive">
				       									<table id="listTable" class="table table-info" >
					       									<thead>
					       										<tr>
					       											<th>商品编号</th>
																	<th>商品名称</th>
																	<th>商品单价</th>
																	<th>商品数量</th>
																	<th>金额合计</th>
					       										</tr>
															</thead>
															<c:forEach var="value" items="${entity.serverOrderDetail }">
																<tr>
																	<td>${value.product.sn }</td>
																	<td>${value.name }</td>
																	<td>${value.price }</td>
																	<td>${value.count }</td>
																	<td>${value.sum }</td>
																</tr>	
															</c:forEach>
														</table>
													</div>
												</div>
											</div>
										</c:if>
										<c:if test="${entity.serverOrder.size() > 0}">
											<div class="form-group" style="width: 100%;"><!--  style="width: 16%;" -->
												<label class="col-sm-2 control-label">服务列表</label>
												<div class="col-sm-8">
													<div class="table-responsive">
				       									<table id="listTable" class="table table-info" >
					       									<thead>
					       										<tr>
					       											<th>服务</th>
																	<th>金额</th>
					       										</tr>
															</thead>
															<c:forEach var="value" items="${entity.serverOrder }">
																<tr>
																	<td>${value.name }</td>
																	<td>${value.price }</td>
																</tr>	
															</c:forEach>
														</table>
													</div>
												</div>
											</div>
										</c:if>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<%-- <div class="panel-heading" style="border-top: 1px solid #d1d1d1">
							<h4 class="panel-title">用户评论</h4>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<div class="table-responsive">
      									<table id="listTable" class="table table-info" >
       									<thead>
       										<tr>
       											<!-- <th>编号</th> -->
       											<th>商品/服务</th>
												<th>评论内容</th>
												<th>评论时间</th>
												<th>操作</th>
       										</tr>
										</thead>
										<c:if test="${entity.serverOrder.size() > 0}">
											<c:forEach var="value" items="${entity.serverOrder }">
												<tr>
													<td>${value.name }</td>
													<td>${value.price }</td>
												</tr>	
											</c:forEach>
										</c:if>
									</table>
								</div>
							</div>
						</div> --%>
					</div>
				</div>
			</div>
		</div>
		<!-- mainwrapper -->
	</section>
</body>
</html>
