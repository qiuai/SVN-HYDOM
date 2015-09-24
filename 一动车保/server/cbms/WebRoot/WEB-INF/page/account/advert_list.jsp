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
	<title>广告管理</title>
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
		            <li><a href="">广告管理</a></li>
		          </ul>
		        </div>
		      </div><!-- media -->
		    </div>
		    <form id="pageList" action="${pageContext.request.contextPath}/manage/advert/list" method="post">
			    <div class="contentpanel">
			      <div class="search-header">
			        <div class="btn-list">
			        <%--   <button class="btn btn-danger" id="deleteButton" type="button" val="<%=path %>/manage/carType" disabled>删除</button> --%>
			          <button class="btn btn-success" id="refreshButton">刷新</button>
					  <button id="add" type="button" class="btn btn-primary" val="<%=path %>/manage/advert">添加</button>
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
		                   <!--  <th ><input id="selectAll" type="checkbox" /></th> -->
		                    <th>标题 </th>
		                    <th>发布时间</th>
		                    <th>操作</th>
		                </tr>
			          </thead>
			          <tbody>
			          	<c:forEach items="${pageView.records}" var="entry" varStatus="s">
			           	  	<tr id="tr_${entry.id}" class="cartypetr">
			           	  		 <%-- <td><input type="checkbox" name="ids" value="${entry.id}" class="<c:if test="${entry.carList.size()>0}">hasCar</c:if>"/></td> --%>
				           		 <td>${entry.title}</td>
				           		 <td><fmt:formatDate value="${entry.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				           		 <td><a href="${pageContext.request.contextPath}/manage/advert/edit?id=${entry.id}">修改</a>
				           		 	<a href="javascript:delEntity('${entry.id }')">删除</a>
				           		 </td>
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
	<script type="text/javascript">
		function delEntity(obj){
			if(!confirm("是否删除该广告？")){
				return;
			}
			var url = "<%=base%>/manage/advert/delete";
			var data = {
				ids:obj	
			};
			$.post(url,data,function(result){
				if(result.status == "success"){
					$(this).closest("tr").remove();
// 					if($("tr.cartypetr").length <= 0){
						location.reload();
// 					}
				}else{
					alert(result.message);
				}
			},"json");
		}
	</script>
   
</body>
</html>
