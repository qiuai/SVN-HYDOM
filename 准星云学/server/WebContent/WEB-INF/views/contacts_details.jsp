<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath() + "/";
	String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>联系人列表</title>

	<link href="<%=basePath %>bootstrap/css/bootstrap.css" rel="stylesheet" media="screen" /> 
	<link href="<%=basePath %>bootstrap/css/style.default.css" rel="stylesheet" />
	<link href="<%=basePath %>res/css/pager.css" rel="stylesheet" />
     <link rel="stylesheet" href="<%=basePath %>bootstrap/css/jquery.gritter.css" />
     <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/select2.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/style.css"/>
    
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
<body class="list">
	<!-- 客户/联系人/客户池 -->
	<div class="panel panel-primary-head">
		<ul class="nav nav-tabs nav-line">
			<li class="" onclick="toCompany()"><a href="#following" data-toggle="tab"><strong>单位</strong></a></li>
			<li class="active" onclick="toContacts()"><a href="#following" data-toggle="tab"><strong>联系人</strong></a></li>
			<li class="" onclick="toCompanyPool()"><a href="#following" data-toggle="tab"><strong>客户池</strong></a></li>
			<li class="" onclick="toCount()"><a href="#following"
				data-toggle="tab"><strong>统计</strong></a></li>
		</ul>
		<script type="text/javascript">
			// 跳转客户(公司)
	        function toCompany() {
	        	location.href = "<%=basePath %>company/list.do";
	        }
			// 跳转联系人
	        function toContacts() {
	        	location.href = "<%=basePath %>contacts/list.do";
	        }
			
			// 跳转客户池
			function toCompanyPool() {
	        	location.href = "<%=basePath %>company/pool.do";
			}
			
			// 去统计
			function toCount() {
				location.href = "<%=basePath %>count/total.do";
			}
			function toContactsEdit(){
				location.href = "<%=basePath%>contacts/edit.do?id=${contacts.id}";
			}
			/*点击联系记录，查看联系的事件*/
	    	function getEventByConId() {
	    		location.href ="<%=basePath %>event/listc.do?ConId=${contacts.id}";
			}
		</script>
	</div>
	<div class="btn-list">
		<button class="btn btn-default deleteButton" onclick="toContacts()"><strong>返回</strong></button>
<%--		<button class="btn btn-default deleteButton"><strong>联系记录(0)</strong></button>--%>
			<c:if test="${event_pager_size==0 }">
			<button class="btn btn-default deleteButton" onclick="getEventByConId()" disabled="disabled">
				<strong >联系记录(${event_pager_size })</strong>
			</button>
		</c:if>
		<c:if test="${event_pager_size!=0 }">
			<button class="btn btn-default deleteButton" onclick="getEventByConId()">
				<strong >联系记录(${event_pager_size })</strong>
			</button>
		</c:if>
		<button class="btn btn-default deleteButton" onclick="toContactsEdit()"><strong>编辑</strong></button>
	</div>
	<div class="panel panel-primary-head">
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<!-- <div class="panel-btns"> -->
							<h3 class="panel-title">基本信息</h3>
						<!-- </div> -->
					</div>
				</div>
			</div>

			<div class="panel-body">
				<!-- <form class="form-horizontal"> -->
					<!-- <div class="col-md-12"> -->
						<div class="col-sm-5">
							<!-- <div class="form-group"> -->
								<div class="col-sm-8"><label class="col-sm-6 control-label"> 姓名： </label>
								${contacts.conname}</div>
								<div class="col-sm-8"><label class="col-sm-6 control-label"> 编码： </label>
								${contacts.concode}</div>
							<!-- </div>
							<div class="form-group"> -->
								<div class="col-sm-8"><label class="col-sm-6 control-label"> 客户名称： </label>
								<%-- ${contacts.company.pubname } --%>
								<c:forEach var="company" items="${contacts.companySet}">${company.pubname }&nbsp&nbsp  </c:forEach></div>
							<!-- </div>
							<div class="form-group"> -->
								<div class="col-sm-8"><label class="col-sm-6 control-label"> 办公电话： </label>
								${contacts.contel}</div>
							<!-- </div>
							<div class="form-group"> -->
								<div class="col-sm-8"><label class="col-sm-6 control-label"> 手机： </label>
								${contacts.conmobile}</div>
							<!-- </div>
							<div class="form-group"> -->
								<div class="col-sm-8"><label class="col-sm-6 control-label"> Email： </label>
								${contacts.conmobile}</div>
							<!-- </div>
							<div class="form-group"> -->
								<div class="col-sm-8"><label class="col-sm-6 control-label"> QQ： </label>
								${contacts.conqq}</div>
							<!-- /div>
							<div class="form-group"> -->
								<div class="col-sm-8"><label class="col-sm-6 control-label"> 标签： </label>
								
									<span>${contacts.contags}</span>
								</div>
							<!-- </div> -->
						</div>

						<div class="col-sm-5">
							<!-- <div class="form-group"> -->
								<div class="col-sm-12"><label class="col-sm-6 control-label"> 职务： </label>
								${contacts.conjob}</div>
								<div class="col-sm-12"><label class="col-sm-6 control-label"> 上级： </label>
								${contacts.rootname}</div>
							<!-- </div>
							<div class="form-group"> -->
								<div class="col-sm-12"><label class="col-sm-6 control-label"> 部门： </label>
								${contacts.conrole}</div>
							<!-- </div>
							<div class="form-group"> -->
								<div class="col-sm-12"><label class="col-sm-6 control-label"> 创建人 </label>
								创建人</div>
							<!-- </div>
							<div class="form-group"> -->
								<div class="col-sm-12"><label class="col-sm-6 control-label"> 负责人： </label>
								负责人</div>
							<!-- </div>
							<div class="form-group"> -->
								<div class="col-sm-12"><label class="col-sm-6 control-label"> 创建时间： </label>
								${contacts.createDate}</div>
							<!-- </div> -->
						</div>

					<!-- </div> -->
			</div>



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
						<textarea class="form-control" rows="5">${contacts.bz}</textarea>
					</div>
				</div>
				<!-- <div class="btn-list btns col-sm-2">
					<button class="btn btn-primary deleteButton">保存</button>
					<button class="btn btn-danger deleteButton">取消</button>
				</div> -->
			</div>

			<!-- </form> -->
		</div>
	</div>
</body>
</html>