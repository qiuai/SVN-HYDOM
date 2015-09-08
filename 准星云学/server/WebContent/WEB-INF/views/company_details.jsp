<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath() + "/";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>单位详情</title>
<link href="<%=basePath %>bootstrap/css/style.default.css" rel="stylesheet" />
<link href="<%=basePath %>res/css/pager.css" rel="stylesheet" />
<link href="<%=basePath %>res/css/head.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=basePath %>bootstrap/css/jquery.gritter.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/select2.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/style.css" />
<style type="text/css">
.table {
	margin-bottom: 0px;
}

#inputDialog {
	 top: 100px; 
	/* margin: 1000px 0 0 0; */
}
label.control-label{
		display: inline-block;
		text-align: right;
		width: 120px;
	}
	table td a {
width: auto;
height: auto;
display: inline-block;
margin-right: 10px;
}
</style>

<script src="<%=basePath %>bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath %>bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath %>bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath %>bootstrap/js/jquery.cookies.js"></script>
<script src="<%=basePath %>bootstrap/js/pace.min.js"></script>
<script src="<%=basePath %>bootstrap/js/retina.min.js"></script>
<script src="<%=basePath %>bootstrap/js/jquery.gritter.min.js"></script>
<script src="<%=basePath %>bootstrap/js/custom.js"></script>
<script src="<%=basePath %>res/js/common.js"></script>
<script src="<%=basePath %>res/js/list.js"></script>

</head>
<body>
<jsp:include page="include/head.jsp" />
<div class="complexBox">
	<!-- 个人事件/其他事件 -->
	<jsp:include page="include/left.jsp" />
	<div class="customer">
	<div class="panel panel-primary-head">
		<ul class="nav nav-tabs nav-line">
			<li class="active" onclick="toCompany()"><a href="#following"
				data-toggle="tab"><strong>单位</strong></a></li>
			<li class="" onclick="toContacts()"><a href="#following"
				data-toggle="tab"><strong>联系人</strong></a></li>
			<!-- <li class="" onclick="toCompanyPool()"><a href="#following"
				data-toggle="tab"><strong>客户池</strong></a></li> -->
			<li class="" onclick="toCount()"><a href="#following"
				data-toggle="tab"><strong>统计</strong></a></li>
		</ul>
	</div>

	<div class="btn-list">
		<button class="btn btn-default deleteButton" onclick="toCompany()">
			<strong>返回</strong>
		</button>
		<button class="btn btn-default deleteButton">
			<strong>联系人(${fn:length(company.contactsSet)} )</strong>
		</button>
		<c:if test="${event_pager_size==0 }">
			<button class="btn btn-default deleteButton" onclick="getEventByCompanyId()" disabled="disabled">
				<strong >联系记录(${event_pager_size })</strong>
			</button>
		</c:if>
		<c:if test="${event_pager_size!=0 }">
			<button class="btn btn-default deleteButton" onclick="getEventByCompanyId()">
				<strong >联系记录(${event_pager_size })</strong>
			</button>
		</c:if>
		<button class="btn btn-default deleteButton">
			<strong>共享</strong>
		</button>
		<button class="btn btn-default deleteButton" onclick="toCompanyEdit()">
			<strong>编辑</strong>
		</button>
		<button class="btn btn-default deleteButton pull-right">
			<strong>修改记录</strong>
		</button>
	</div>
	<script type="text/javascript">
			// 跳转客户(公司)
	        function toCompany() {
	        	location.href = "<%=basePath%>company/list.do";
	        }
			// 跳转联系人
	        function toContacts() {
	        	location.href = "<%=basePath%>contacts/list.do";
	        }
			
			// 跳转客户池
			function toCompanyPool() {
	        	location.href = "<%=basePath%>company/pool.do";
		}

		// 去统计
		function toCount() {
			location.href = "<%=basePath%>count/total.do?ec=员工";
		}
		function toCompanyEdit(){
			location.href ="<%=basePath %>company/edit.do?id=${company.id}";
		}
		
		/*点击联系记录，查看联系的事件*/
    	function getEventByCompanyId() {
    		location.href ="<%=basePath %>event/list.do?companyId=${company.id}";
		}
		
	</script>
	<div class="panel panel-primary">
		<div class="panel-heading panel-title-name">
				<h3 class="panel-title">基本信息</h3>
				<input class="companyId" type="hidden" value="${company.id}"/>
		</div>
		<!--基本信息-->
		<div class="panel-body">
				<div class="col-sm-5">
						<div class="col-sm-8">
							<label class="col-sm-6 control-label"> 单位名称： </label>${company.pubname}
						</div>
						<div class="col-sm-8">
							<label class="col-sm-6 control-label"> 注册资金： </label>${company.pubregcapital}
						</div>
						<div class="col-sm-8">
							<label class="col-sm-6 control-label"> 法人： </label>${company.pubman}
						</div>
						<div class="col-sm-12">
							<label class="col-sm-6 control-label"> 城市： </label>${company.city}
						</div>
						<div class="col-sm-8">
							<label class="col-sm-6 control-label"> 国籍： </label>${company.pubnation}
						</div>
						<div class="col-sm-8">
							<label class="col-sm-6 control-label"> 标签： </label>${company.publictags}
						</div>
						<div class="col-sm-8">
							<label class="col-sm-6 control-label"> 类型： </label>${company.pubtype=='enterprise'?"单位":"个人"}
						</div>
						<div class="col-sm-12">
							<label class="col-sm-6 control-label"> 创建人： </label>${company.creator}
						</div>
						<div class="col-sm-12"> 
							<label class="col-sm-6 control-label"> 创建时间： </label><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${company.createDate}"></fmt:formatDate>
						</div>
				</div>
				<div class="col-sm-5">
						<div class="col-sm-12">
							<label class="col-sm-6 control-label"> 公司简称： </label>${company.pubzjf}
						</div>
						<div class="col-sm-8">
							<label class="col-sm-6 control-label"> 注册码： </label>${company.pubzch}
						</div>
						<div class="col-sm-8">
							<label class="col-sm-6 control-label"> 纳税识别号： </label>${company.publsrsbh}
						</div>
						<div class="col-sm-8">
							<label class="col-sm-6 control-label"> 电话： </label>${company.pubtel}
						</div>
						<div class="col-sm-8">
							<label class="col-sm-6 control-label"> 地址： </label>${company.pubadd}
						</div>
						<div class="col-sm-12">
							<label class="col-sm-6 control-label"> 电子邮箱： </label>${company.pubmail}
						</div>
						<div class="col-sm-12">
							<label class="col-sm-6 control-label"> 负责人： </label>${company.admin.pername}
						</div>
						<div class="col-sm-8">
							<label class="col-sm-6 control-label"> 行业： </label>${company.pubindustry}
						</div>
						<div class="col-sm-12">
							<label class="col-sm-6 control-label"> 修改时间： </label><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${company.lastupdate}"></fmt:formatDate>
						</div>
				</div>

			<!-- </div> -->
		</div>
		<div class="panel-heading panel-title-name">
				<h3 class="panel-title">联系人</h3>
		</div>
		<div class="panel-body">
			<c:choose>
				<c:when test="${company.contactsSet.size() <= 0}">
					没有联系人信息
				</c:when>
				<c:otherwise>
					<div class="table-responsive">
						<table class="table table-striped mb30">
							<thead>
								<tr>
									<th>姓名</th>
									<th>部门</th>
									<th>岗位</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="contact" items="${company.contactsSet}" varStatus="index">
									<tr>
										<td>${contact.conname}</td>
										<td>${contact.conrole }</td>
										<td>${contact.conjob }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<%-- <div class="panel-body">
			<!-- <div class="form-group col-sm-12"> -->
			<div class="col-sm-12">
				<div class="col-sm-8"><label class="col-sm-2 control-label"> 标签： </label>
				
					<!-- <span>新客户</span><span>企业单位</span><span>有意向</span> -->
					${company.publictags}
				</div>
			<!-- </div>
			<div class="form-group col-sm-12"> -->
				<div class="col-sm-8"><label class="col-sm-2 control-label"> 法人： </label>
				${company.pubman}</div>
			<!-- </div>
			<div class="form-group col-sm-12"> -->
				<div class="col-sm-8"><label class="col-sm-2 control-label"> 经营范围： </label>
				
					<!-- <span>机械销售、货物进出口、技术开发</span> -->
					${company.pubindustry}
				</div>
			<!-- </div> -->
			</div>
		</div> --%>
		<!--最近联系-->
		<!-- <div class="panel panel-primary">
			<div class="panel-heading panel-title-name">
				<div class="panel-btns">
				<h3 class="panel-title col-sm-6">最近联系</h3>
				<button class="btn btn-default pull-right">更多</button>
				</div>
			</div>
		</div> -->
		<%-- <div class="panel-body">
			<div class="col-sm-12">
				<div class="col-sm-16">
					<div class="col-sm-3 "><label class="col-sm-5 control-label"> 负责人： </label>
						${company.admin.pername}
					</div>
					<div class="col-sm-3"><label class="col-sm-5 control-label"> 联系人： </label>
						<c:forEach var="contacts" items="${company.contactsSet }">
							${contacts.conname }&nbsp;&nbsp;
						</c:forEach>
					</div>
					<div class="col-sm-3"><label class="col-sm-5 control-label"> 类型： </label>
						<spring:message code="Publbname.${company.publbname }"></spring:message>
					</div>
				</div>
					<div class="col-sm-8"><label class="col-sm-4 control-label"> 时间： </label>
					${company.createDate}</div>
				<div class="col-sm-8">
					<label class="col-sm-4 control-label"> 主题： </label>
				</div>
					<div class="col-sm-8"><label class="col-sm-4 control-label"> 摘要信息： </label>规范的公司
				</div>
			</div>
		</div> --%>
	</div>

	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="panel-btns">
				<a href="#" class="panel-minimize tooltips maximize" data-toggle="tooltip"
					title="Minimize Panel"><i class="fa fa-plus"></i></a>
				<a href="<%=basePath %>bill/add.do?companyname=${company.pubname}&companyid=${company.id}"
					class="new-msg tooltips" data-toggle="modal"
					data-target=".add-panel" data-original-title="新增发票"><i
					class="fa fa-edit"></i></a>
			</div>
			<h3 class="panel-title">开票信息</h3>
		</div>
		<div class="panel-body" style="display: none;">
			<div class="table-responsive">
				<c:choose>
					<c:when test="${billPager.list.size()<=0 }">
						还没有开票信息
					</c:when>
					<c:otherwise>
						<table class="table table-hidaction table-info table-hover mb10 ">
							<tr>
								<th>税务号</th>
								<th>发票抬头</th>
								<th>税务类型</th>
								<th>备注</th>
								<th>操作</th>
							</tr>
							<c:forEach var="bill" items="${billPager.list}" varStatus="index">
								<tr id="billtr_${bill.id }">
									<td>${bill.billsbh }</td>
									<td>${bill.billcorpname }</td>
									<td>地税</td>
									<td>${bill.bz }</td>
									<td><a href="javascript:deleteBill('${bill.id }');"
										data-toggle="tooltip" class="delete-row tooltips" title="删除">
											<i style="color:#666;" class="glyphicon glyphicon-trash"></i>
									</a> <a
										href="<%=basePath %>bill/edit.do?id=${bill.id}&companyid=${company.id}"
										class="new-msg tooltips" data-toggle="modal"
										data-target=".add-panel" title="编辑"> <i style="color:#666;"
											class="glyphicon glyphicon-pencil"></i>
									</a></td>
								</tr>
							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<!-- panel-body -->
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="panel-btns">
				<a href="#" class="panel-minimize tooltips maximize" data-toggle="tooltip"
					title="Minimize Panel"><i class="fa fa-plus"></i></a>
				<a href="<%=basePath %>bank/add.do?companyid=${company.id}"
					class="new-msg tooltips" data-toggle="modal"
					data-target=".add-panel" data-original-title="新增银行卡账户">
					<i class="fa fa-edit"></i>
				</a>
			</div>
			<h3 class="panel-title">账户信息</h3>
		</div>
		<div class="panel-body" style="display: none;">
			<div class="table-responsive">
				<c:choose>
					<c:when test="${bankPager.list.size() <= 0}">
						还没有银行卡信息
					</c:when>
					<c:otherwise>
						<table class="table table-info table-hover mb10 ">
							<tr>
								<th>卡号</th>
								<th>银行名称</th>
								<th>户名</th>
								<th>开户行</th>
								<th>备注</th>
								<th>操作</th>
							</tr>
							<c:forEach var="bank" items="${bankPager.list}" varStatus="index">
								<tr id="banktr_${bank.id }">
									<td>${bank.bankzh }</td>
									<td>${bank.bankname }</td>
									<td>${bank.skcorp }</td>
									<td>${bank.bankadd }</td>
									<td>${bank.bz }</td>
									<td><a href="javascript:deleteBank('${bank.id }');"
										data-toggle="tooltip" class="delete-row tooltips" title="删除">
											<i style="color:#666;" class="glyphicon glyphicon-trash"></i>
									</a> <a
										href="<%=basePath %>bank/edit.do?id=${bank.id}&companyid=${company.id}"
										class="new-msg tooltips" data-toggle="modal"
										data-target=".add-panel" title="编辑"> <i style="color:#666;"
											class="glyphicon glyphicon-pencil"></i>
									</a></td>
								</tr>
							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<!-- panel-body -->
	</div>
	
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="panel-btns">
				<a href="" class="panel-minimize tooltips maximize" data-toggle="tooltip"
					title="Minimize Panel"><i class="fa fa-plus"></i></a><a
					href="<%=basePath %>goods/add.do?companyid=${company.id}"
					class="pull-right new-msg tooltips" data-toggle="modal"
					data-target=".add-panel" data-original-title="新增物流信息"><i
					class="fa fa-edit"></i></a>
			</div>
			<!-- panel-btns -->
			<h3 class="panel-title">物流信息</h3>
		</div>
		<div class="panel-body" style="display: none;">
			<div class="table-responsive">
				<c:choose>
					<c:when test="${goodsPager.list.size() <= 0}">
						还没有物流信息
					</c:when>
					<c:otherwise>
						<table class="table table-info table-hover mb10 ">
							<tr>
								<th>收货人</th>
								<th>电话</th>
								<th>邮编</th>
								<th>地址</th>
								<th>备注</th>
								<th>操作</th>
							</tr>
							<c:forEach var="goods" items="${goodsPager.list}" varStatus="index">
								<tr id="goodstr_${goods.id }">
									<td>${goods.goodsname }</td>
									<td>${goods.goodstel }</td>
									<td>${goods.goodspost }</td>
									<td>${goods.goodsadd }</td>
									<td>${goods.bz }</td>
									<td><a href="javascript:deleteGoods('${goods.id }');"
										data-toggle="tooltip" class="delete-row tooltips" title="删除">
											<i style="color:#666;" class="glyphicon glyphicon-trash"></i>
									</a> <a
										href="<%=basePath %>goods/edit.do?id=${goods.id}&companyid=${company.id}"
										class="new-msg tooltips" data-toggle="modal"
										data-target=".add-panel" title="编辑"> <i style="color:#666;"
											class="glyphicon glyphicon-pencil"></i>
									</a></td>
								</tr>
							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
				
			</div>
		</div>
		<!-- panel-body -->
	</div>
	
	<div class="panel panel-primary widget-messaging">
		<div class="panel-heading">
			<!-- panel-btns -->
			<div class="panel-btns">
				<a href="" class="panel-minimize tooltips maximize" data-toggle="tooltip"
					title="Minimize Panel"><i class="fa fa-plus"></i></a>
			</div>
			<h3 class="panel-title">自定义信息</h3>
		</div>
		<div class="panel-body" style="display: none;">
				<c:choose>
					<c:when test="${company.fieldsSet.size() <= 0}">
						没有自定义信息
					</c:when>
					<c:otherwise>
						<div class="table-responsive">
							<table class="table table-striped mb30">
								<thead>
									<tr>
										<th>字段名</th>
										<th>类型</th>
										<th>内容</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="value" items="${company.fieldsSet }">
										<tr>
											<c:if test="${value.fieldtype == 0}"> <!-- (0:文本 1:图片 2:附件) -->
												<td>${value.fieldname }</td>
												<td>文本</td>
												<td>${value.fieldval }</td>
		                         			</c:if>
		                         			<c:if test="${value.fieldtype == 1}"> <!-- (0:文本 1:图片 2:附件) -->
		                         				<td>${value.fieldname }</td>
												<td>图片</td>
												<td><a href="<%=basePath %>${value.fieldval }" title="点击查看" target="_black">点击查看图片</a></td>
		                         			</c:if>
		                         			<c:if test="${value.fieldtype == 2}"> <!-- (0:文本 1:图片 2:附件) -->
		                         				<td>${value.fieldname }</td>
												<td>附件</td>
												<td><a href="<%=basePath %>${value.fieldval }" title="点击查看" target="_black">点击查看图片</a></td>
		                         			</c:if>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
                         <%-- <ul class="list-group">
                         	<c:forEach var="value" items="${company.fieldsSet }">
                         		<li class="list-group-item">
                         			<c:if test="${value.fieldtype == 0}"> <!-- (0:文本 1:图片 2:附件) -->
                         				<small class="pull-right">文本</small>
	                                 	<h4 class="sender">${value.fieldname }</h4>
	                                 	<p>${value.fieldval }</p>
                         			</c:if>
                         			<c:if test="${value.fieldtype == 1}"> <!-- (0:文本 1:图片 2:附件) -->
                         				<small class="pull-right">图片</small>
	                                 	<h4 class="sender">${value.fieldname }</h4>
	                                 	<p><a href="<%=basePath %>${value.fieldval }" title="点击查看" target="_black">点击查看图片</a></p>
                         			</c:if>
                         			<c:if test="${value.fieldtype == 2}"> <!-- (0:文本 1:图片 2:附件) -->
                         				<small class="pull-right">附件</small>
	                                 	<h4 class="sender">${value.fieldname }</h4>
	                                 	<p><a href="<%=basePath %>${value.fieldval }" title="点击查看" target="_black">点击查看附件</a></p>
                         			</c:if>
                             	</li>
                         	</c:forEach>
                         </ul> --%>
					</c:otherwise>
				</c:choose>
				
			</div>
		</div>
		<!-- panel-body -->
	</div>
	
	
	<%-- <!--信息描述-->
	<div class="panel panel-primary">
		<div class="panel-heading">
			<!-- <div class="panel-btns"> -->
			<h3 class="panel-title">描述信息</h3>
			<!-- </div> -->
		</div>
	</div>
	<div class="panel-body">
		<div class="form-group">
			<label class="col-sm-1 control-label"> 备注： </label>
			<div class="col-sm-10">
				<textarea class="form-control" rows="5" name="bz">${company.bz }</textarea>
			</div>
		</div>
	</div>
	<!-- 添加Panel -->
	<div class="modal fade add-panel" id="inputDialog" tabindex="-1"
		role="dialog" style="display: none;" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div> --%>
</div>
<!-- </div> -->
	<script>
    	function deleteBill(id){
    		if (confirm("您确定要删除吗？")) {
				$.ajax({
					  type: 'POST',
					  url: '<%=basePath%>bill/delete.do',
					  data: {'id':id},
					  dataType: 'json',
					  success: function(data) {
						    $("#billtr_" + id).remove();
					  }
					});
			}
    	}
    	
    	function deleteBank(id){
    		if (confirm("您确定要删除吗？")) {
				$.ajax({
					  type: 'POST',
					  url: '<%=basePath%>bank/delete.do',
					  data: {'id':id},
					  dataType: 'json',
					  success: function(data) {
						    $("#banktr_" + id).remove();
					  }
					});
			}
    	}
    	
    	function deleteGoods(id){
    		if (confirm("您确定要删除吗？")) {
				$.ajax({
					  type: 'POST',
					  url: '<%=basePath%>goods/delete.do',
					  data: {'id':id},
					  dataType: 'json',
					  success: function(data) {
						    $("#goodstr_" + id).remove();
					  }
					});
			}
    	}
    	
    </script>
</body>
</html>
