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
	<script src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/modernizr.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/pace.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/retina.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery.cookies.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
	
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
	</script>
	<style type="text/css">
		.div_feed{width:1153px;
				min-height: 69px;	
}
.contentpanel{width: 100%;}
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
		            <li><a href="">App客户意见反馈</a></li>
		          </ul>
		          
		        </div>
		      </div><!-- media -->
		    </div>
		   <form id="pageList" action="${pageContext.request.contextPath}/manage/feedback/list" method="post">
			    <div class="contentpanel">
			      <div class="search-header">
			        <div class="btn-list">
			        </div>
			      </div>
			      <div class="table-responsive">
			        <table id="listTable" class="feedtable" >
			        	<thead>
							<tr>
								<th class="check">
									
								</th>
								<td>
								</td>
							</tr>
						</thead>
						<c:forEach items="${pageView.records}" var="feedback" >  
							<tr>
								<td>
								</td>
								<td>
									<div class="div_feed">
									<div class="content"><p>${feedback.content }</p></div>
									
									<div style="float: left;width:45%;"><span>用户名:${feedback.member.nickname }</span></div>
									
									<div style="float: left;width:52%;"><span>评论时间:<fmt:formatDate value="${feedback.createDate }" type="both"/></span></div>
									
									<span><a href="${pageContext.request.contextPath}/manage/feedback/delete?id=${feedback.id}">删除</a></span>
									</div>
									<div style="background-color: #BDBDBD;width:100%;height: 1px"></div>
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
