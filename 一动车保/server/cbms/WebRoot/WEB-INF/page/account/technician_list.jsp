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
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.min.js"></script>
	<![endif]-->
	<script type="text/javascript">
	  	function del(accid){
		if (confirm('您确定要禁用吗')) {
			var url = "${pageContext.request.contextPath}/manage/account/delete";
			var data = {ids:accid};
			$.get(url,data,function(data) {
		      	if(data.status=="success"){
		      		$("#td_"+accid).html("禁用");
		       	}
			   },"json");
			}
		}
	  	
	  	function restPassWord(id){
	  		/* var id = $("input[name='id']").val(); */
	  		if(confirm('您确定要将密码初始化吗？')){
	  			var url = "<%=base%>manage/technician/restPassWord";
	  			var data = {
	  					id : id	
	  			};
	  			$.post(url,data,function(result){
	  				
	  			},"json");
	  			
	  			alert("密码已重置为:123456");
	  		}
	  	}	
			
			
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
		            <li><a href="">技师帐号管理列表</a></li>
		          </ul>
		        </div>
		      </div><!-- media -->
		    </div>
		    <form id="pageList" action="${pageContext.request.contextPath}/manage/technician/list" method="post">
			    <div class="contentpanel">
			      <div class="search-header">
			        <div class="btn-list">
			          <button class="btn btn-danger" id="deleteButton" type="button" val="<%=path %>/manage/technician" disabled>删除</button>
			          <button class="btn btn-success" id="refreshButton">刷新</button>
					  <button id="add" type="button" class="btn btn-primary" val="<%=path %>/manage/technician">添加</button>
			          <div style="float: right;max-width: 340px;height: 37px;">
			            <div class="input-group" style="float: left;max-width: 240px;">
			              <input id="searchValue" placeholder="关键字查询" name="queryContent" value="${queryContent}" type="text" class="form-control" maxlength="50" style="height: 37px">
			            </div>
			            <div style="float: right">
			            	<button type="button" class="btn btn-info btn-metro" onclick="confirmQuery();">查询</button>
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
									帐号
								</th>
								<th>
									名字 
								</th>
								<th>
									联系电话
								</th>
								<th>
									星级
								</th>
								<th>
									状态
								</th>
								<th>
									工作状态
								</th>
								<th>
									当前所在地
								</th>
								<th>
									操作
								</th>
							</tr>
						</thead>
						<c:forEach items="${pageView.records}" var="technician" >  
							<tr>
								<td>
									<input type="checkbox" name="ids" value="${technician.id}" />
								</td>
								<td>
									${technician.account}
								</td>
								<td>
									${technician.name}
								</td>
								<td>
									${technician.phonenumber}
								</td>
								<td>
									${technician.level}星
								</td>
								<td>
									<c:if test="${0==technician.stats}">空闲中</c:if>
									<c:if test="${1==technician.stats}">派单中</c:if>
									<c:if test="${2==technician.stats}">服务中</c:if>
								</td>
								<td>
									<c:if test="${false eq technician.jobstatus }">休息中</c:if>
									<c:if test="${true eq technician.jobstatus }">工作中</c:if>
								</td>
								<td>
									<div style="max-width:400px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${technician.area}</div>
								</td>
								<td>
									<a href="<%=base %>manage/technician/edit?id=${technician.id}">修改</a>
									<button id="restpassword" onclick="restPassWord('${technician.id}')" type="button">重置密码</button>
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
