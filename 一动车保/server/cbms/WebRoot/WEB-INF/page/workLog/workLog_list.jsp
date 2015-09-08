<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
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
	<title>技师考勤查询</title>
	<link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resource/css/manage.common.css" rel="stylesheet">
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/chain/js/laydate/laydate.js"></script>
	
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
		//设置时间
		function getLaydate(obj){
			var option = {
				elem : '#'+obj,
				format : 'YYYY-MM-DD hh:mm:ss',
				istime : true,
				istoday : true
			};
			laydate(option);
		}
		function getLaydate2(obj){
			var option = {
				elem : '#'+obj,
				format : 'YYYY-MM-DD hh:mm:ss',
				istime : true,
				istoday : true
			};
			laydate(option);
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
		            <li><a href="">考勤查询</a></li>
		          </ul>
		        </div>
		      </div><!-- media -->
		    </div>
		    <form id="pageList" action="${pageContext.request.contextPath}/manage/workLog/list" method="post">
			    <div class="contentpanel">
			      <div class="search-header">
			        <div class="btn-list">
			          <%-- <button class="btn btn-danger" id="deleteButton" type="button" val="<%=path %>/manage/carBrand" disabled>删除</button> --%>
			          <button class="btn btn-success" id="refreshButton">刷新</button>
			          
			          <div style="float: right;max-width: 700px;height: 37px;">
			            <div class="input-group" style="float: left;max-width: 620px;">
			            	<input style="height: 37px;" type="text" class="form-control hasDatepicker" placeholder="请输入开始时间" id="opDate" name="opDate" onclick="getLaydate('opDate')" 
				            value="<fmt:formatDate value='${opDate}' pattern='yyyy-MM-dd HH:mm:ss'/>">
				            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
				            <input style="height: 37px;" type="text" class="form-control hasDatepicker" placeholder="请输入结束时间" id="edDate" name="edDate" onclick="getLaydate2('edDate')" 
				            value="<fmt:formatDate value='${edDate}' pattern='yyyy-MM-dd HH:mm:ss'/>">
							<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
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
		                   <!--  <th ><input id="selectAll" type="checkbox" /></th> -->
		                    <th>姓名</th>
		                    <th>状态</th>
		                    <th>电话</th>
		                    <th>时间</th>
		                </tr>
			          </thead>
			          <tbody>
			          	<c:forEach items="${pageView.records}" var="entry" varStatus="s">
			           	  	<tr id="tr_${entry.id}">
				           		 <td>${entry.technician.name}</td>
				           		 <td>
				           		 	<c:if test="${true eq entry.jobstatus}">上班</c:if>
				           		 	<c:if test="${false eq entry.jobstatus}">下班</c:if>
				           		 </td>
				           		 <td>${entry.technician.phonenumber}</td>
				           		 <td><fmt:formatDate value="${entry.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			           	  	</tr>
			           	  </c:forEach>
			          </tbody>
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
