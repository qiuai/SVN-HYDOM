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
<title>统计</title>
<link href="<%=basePath %>bootstrap/css/bootstrap.css" rel="stylesheet" media="screen" /> 
	<link href="<%=basePath %>bootstrap/css/style.default.css" rel="stylesheet" />
	<link href="<%=basePath %>res/css/pager.css" rel="stylesheet" />
     <link rel="stylesheet" href="<%=basePath %>bootstrap/css/jquery.gritter.css" />
     <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/select2.css"/>
    <%-- <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/style.css"/> --%>
<link rel="stylesheet" href="<%=basePath%>res/css/head.css" media="screen" />

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
<script src="<%=basePath%>res/js/laydate/laydate.js"></script>
</head>
<body class="list">
<jsp:include page="include/head.jsp" />
<div class="complexBox">
	<!-- 个人事件/其他事件 -->
	<jsp:include page="include/left.jsp" />
	<div class="customer">
	<!-- 客户/联系人/客户池 -->
	<div class="panel panel-primary-head">
		<ul class="nav nav-tabs nav-line">
			<!-- <li class="" onclick="toCompany()"><a href="#following"
				data-toggle="tab"><strong>单位</strong></a></li>
			<li class="" onclick="toContacts()"><a href="#following"
				data-toggle="tab"><strong>联系人</strong></a></li>
			<li class="" onclick="toCompanyPool()"><a href="#following"
				data-toggle="tab"><strong>客户池</strong></a></li> -->
			<li class="active" onclick="toCount()"><a href="#following"
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
				location.href = "<%=basePath%>count/total.do?ec=员工";
			}		
					
			
			
		</script>
		<style>
			.table-lists_con .first_th{
				width: 120px!important;
				display: inline-block;
			}
		</style>
		</div>	
		<form id="listForm" name="listForm"
		action="<%=basePath%>count/detail.do?pids=${pids }&dicid=${dicid}&ec=${ec}" method="post">
		<div class="well" style="height: 200px;">

			<div class="col-ms-12">
				<div class="form-group" style="height: 50px;">
					<label class="col-sm-1 control-label" style="width: 10%;">
						关键字：
					</label>
					<div class="col-sm-4">
						<input class="form-control" type="text" name="keyword"
							placeholder="请输入关键字" value=""/>
					</div>
					<div class="btn-list">
					<button class="btn btn-primary btn-metro" id="searchButton" style="margin-left: 33px;">搜索</button>
					<%--	 style="margin-left: 60px;"              --%>
				</div>
				</div>
				<div class="form-group" style="height: 50px;">
					<label class="col-sm-1 control-label" style="width: 10%;">
						日期：
					</label>
					<div class="col-sm-2" id="col-sm-2" style="width: 183px;">
						<div class="input-group">
							<input type="text" class="form-control hasDatepicker"placeholder="" id="start" name="keyword" />
								<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i>
							</span>
						</div>
					</div>
					<div class="" style="width:2px;margin-left:-3px;height:34px;line-height:34px; float:left;">至</div>
					<div class="col-sm-2" id="col-sm-2"	style="margin-left: 8px;width: 183px;">
						<div class="input-group">
							<input type="text" class="form-control hasDatepicker"placeholder="" id="end" name="keyword" />
								<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group" style="height: 50px;">
					<label class="col-sm-1 control-label" style="width: 10%;"> 统计方式： </label>
					<div class="col-sm-8" style="width: 120px;" >
						<div class="rdio rdio-primary">
                             <input type="radio" name="radio" value="1" id="byday">
                             <label for="byday">按日查询</label>
                       </div>
					</div>
					<div class="col-sm-8" style="width: 120px;" >
						<div class="rdio rdio-primary">
                             <input type="radio" name="radio" value="1" id="byweek">
                             <label for="byweek">按周查询</label>
                       </div>
					</div>
					<div class="col-sm-8" style="width: 120px;" >
						<div class="rdio rdio-primary">
                             <input type="radio" name="radio" value="1" id="bymonth">
                             <label for="bymonth">按月查询</label>
                       </div>
					</div>
				</div>
				
				
				
				<!-- <div class="form-group">
						<label class="col-sm-1 control-label">
							客户：
						</label>
						<div class="col-sm-4">
							<input class="form-control" type="text" placeholder="XX科技有限公司"/>
						</div>
						<label class="col-sm-1 control-label">
							联系人：
						</label>
						<div class="col-sm-4">
							<input class="form-control" type="text" placeholder="张三"/>
						</div>
				</div>
				<div class="form-group">
					<label class="col-sm-1 control-label">
						统计方式：
					</label>
					<div class="col-sm-4">
						<label class="radio-inline">
							<input type="radio" value="option2"/> 按员工
						</label>
						<label class="radio-inline">
							<input type="radio" value="option2"/> 按客户
						</label>
					</div>
				</div> -->
			</div>
		
	</div>
	
	<div class="row">
		 <div class="col-md-12">
		 	<div class="col-md-11">
		 		<label>${dictionary }</label>
		 	</div>
		 	<!-- <div class="col-md-3" style="padding-left: 0;padding-bottom: 10px;">
		 	<a href="javascript:month();" class="btn btn-warning">按日查看</a>
		 	<a href="javascript:month();" class="btn btn-warning">按周查看</a>
		 	<a href="javascript:month();" class="btn btn-warning">按月查看</a>
		 	</div> -->
		 	<!-- <div class="col-md-1">
		 		<a href="javascript:month();" class="btn btn-warning">按月查看</a>
		 	</div> -->
		 </div>
		 <div class="col-md-12">
			
		 	<div class="table-responsive table-lists_con">
		 		<table class="table table-primary table-hover mb30">
					<thead>
					<tr>
						<th class="first_th" style="">
							<c:if test="${ec=='员工' }">姓名</c:if>
							<c:if test="${ec=='单位' }">单位</c:if>
						</th>
						<c:forEach var="date" items="${time }" varStatus="index">
						<th>${date }</th>
						</c:forEach>
			
					</tr>
					</thead>
					<tbody>
					<c:forEach var="map" items="${result }" varStatus="index">
						<tr><th style="width:62px;heigh:37px;">${map.name }</th>
						<c:forEach var="obj" items="${map.datetimes }" varStatus="index">
						<th>${obj }</th>
						</c:forEach>
						</tr>
					</c:forEach>
						<tr><th>合计</th>
						<c:forEach var="to" items="${total }" varStatus="index">
							<th>${to }</th>
						</c:forEach></tr>
					</tbody>
		 		</table>
		 	</div>
		 	
		 </div>
	</div>

	</form>	
		
	<script type="text/javascript">
	function month(){
		window.location.href = "<%=basePath%>count/detail.do?pids=${pids }&dicid=${dicid}&ec=${ec}&m=month";
		
	}
	//时间点击选择配置
	var start = {
		    elem: '#start',
		    format: 'YYYY-MM-DD',
		    istime: true,
		    istoday: false,
		    choose: function(datas){
		         end.min = datas; //开始日选好后，重置结束日的最小日期
		         end.start = datas //将结束日的初始值设定为开始日
		    }
		};
		var end = {
		    elem: '#end',
		    format: 'YYYY-MM-DD',
		    istime: true,
		    istoday: false,
		    choose: function(datas){
		        start.max = datas; //结束日选好后，重置开始日的最大日期
		    }
		};
		laydate(start);
		laydate(end);
	</script>
</body>
</html>

