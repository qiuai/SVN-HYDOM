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
		            <li><a href="">商品品牌列表</a></li>
		          </ul>
		        </div>
		      </div><!-- media -->
		    </div>
		    <form id="pageList" action="${pageContext.request.contextPath}/manage/productBrand/list" method="post">
			    <div class="contentpanel">
			      <div class="search-header">
			        <div class="btn-list">
			         <%--  <button class="btn btn-danger" id="deleteButton" type="button" val="<%=path %>/manage/productBrand" disabled>删除</button> --%>
			          <button class="btn btn-success" id="refreshButton">刷新</button>
					  <button id="add" type="button" class="btn btn-primary" val="<%=path %>/manage/productBrand">添加</button>
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
								<!-- <th class="check">
									<input type="checkbox" id="selectAll"/>
								</th> -->
								<th>
									名称
								</th>
								<th>
									logo
								</th>
								<th>
									网址
								</th>
								<th>
									排序
								</th>
								<th>
									操作
								</th>
							</tr>
						</thead>
						<c:forEach items="${pageView.records}" var="productBrand" >  
							<tr class="productBrand_tr">
								<%-- <td>
									<input type="checkbox" name="ids" value="${productBrand.id}" />
								</td> --%>
								<td>
									${productBrand.name}
								</td>
								<td>
									<c:if test="${empty productBrand.imgPath}">暂无</c:if>
									<c:if test="${!empty productBrand.imgPath}">
										<a href="<%=path %>/${productBrand.imgPath}" target="_blank">查看</a>
									</c:if>
								</td>
								<td>
									<a href="${productBrand.fullUrl }" target="_blank">${productBrand.url }</a>
								</td>
								<td>
									${productBrand.order }
								</td>
								<td>
									<a href="<%=base %>manage/productBrand/edit?id=${productBrand.id}">[编辑]</a>
									<a href="javascript:void(0);" onclick="del('${productBrand.id}',this)">[删除]</a>
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
   		function del(obj,e){
   			if(!confirm("是否删除该品牌?")){
   				return;
   			}
   			var tr = $(e).closest("tr.productBrand_tr");
   			var url = "<%=path %>/manage/productBrand/delete";
   			var data = {
   				id:obj	
   			}
   			$.post(url,data,function(result){
   				if(result.status == "success"){
   					alert("成功");
   					tr.remove();
   				}
   			},"json");
   		}
   </script>
</body>
</html>
