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
	<title>一动车保</title>
	<link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resource/css/manage.common.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resource/chain/css/select2.css" rel="stylesheet">
		
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/modernizr.min.js"></script>
		<script src="${pageContext.request.contextPath}/resource/chain/js/select2.min.js"></script>
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
	  	$(document).ready(function(){
	  		$("#productCategorys").select2();
	  		$("#productBrandId").select2();
	  	});
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
		            <li><a href="">商品列表</a></li>
		          </ul>
		        </div>
		      </div><!-- media -->
		    </div>
		    <form id="pageList" action="${pageContext.request.contextPath}/manage/product/list" method="post" class="form-horizontal form-bordered">
			    <div class="contentpanel">
			      <div class="search-header">
			      <div class="form-group" style="border-top: 0px dotted #d3d7db;width: 33%;display: inline-block;">
						<label class="col-sm-3 control-label">商品分类</label>
						<div class="col-sm-8">
							<select name="productCategoryId" id="productCategorys" style="width: 100%;">
								<option value="">选择分类</option>
								<c:forEach var="category" items="${productCategorys }">
									<option value="${category.id }" 
										<c:if test="${productCategoryId eq category.id}">selected="selected"</c:if>
									>
										<c:if test="${category.grade gt 0}">
											<c:forEach begin="1" end="${category.grade }">
												&nbsp;&nbsp;
											</c:forEach>
										</c:if>
										${category.name}
									</option>
								</c:forEach>
							</select>
						</div>
					</div>
			      	<div class="form-group" style="border-top: 0px dotted #d3d7db;width: 33%;display: inline-block;">
						<label class="col-sm-3 control-label">商品品牌</label>
						<div class="col-sm-8">
							<select name="productNum" id="productBrandId" style="width: 100%;">
								<option value="">选择品牌</option>
								<c:forEach var="value" items="${productBrands }">
									<option value="${value.id }" 
										<c:if test="${productNum eq value.id}">selected="selected"</c:if>
									>
										${value.name}
									</option>
								</c:forEach>
							</select>
						</div>
					</div>
					 <div class="form-group" style="border-top: 0px dotted #d3d7db;width: 33%;display: inline-block;">
						<label class="col-sm-3 control-label">商品名称</label>
						<div class="col-sm-8">
							<input type="text" name="productName" class="form-control" maxlength="200" value="${productName }" id="productName"/>
						</div>
					</div>
			        <div class="btn-list">
			          <button class="btn btn-danger" id="deleteButton" type="button" val="<%=path %>/manage/product" disabled>删除</button>
			          <button class="btn btn-success" id="refreshButton">刷新</button>
					  <button id="add" type="button" class="btn btn-primary" val="<%=path %>/manage/product">添加</button>
					  <button type="button" class="btn btn-info btn-metro" onclick="confirmQuery();">查询</button>
			         <%--  <div style="float: right;max-width: 340px;height: 37px;">
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
								<th class="check">
									<input type="checkbox" id="selectAll"/>
								</th>
								<th>
									商品编号
								</th>
								<th>
									商品名称
								</th>
								<th>
									商品图片
								</th>
								<th>
									商品分类
								</th>
								<th>
									品牌
								</th>
								<th>
									市场价
								</th>
								<th>
									赠送积分
								</th>
								<th>
									操作
								</th>
							</tr>
						</thead>
						<c:forEach items="${pageView.records}" var="product" >  
							<tr>
								<td>
									<input type="checkbox" name="ids" value="${product.id}" />
								</td>
								<td>
									${product.sn}
								</td>
								<td>
									${product.name}
								</td>
								<td>
									<c:if test="${!empty product.imgPath}">
										<a href="${pageContext.request.contextPath}/${product.imgPath }" target="_blank">[查看]</a>
									</c:if>
									<c:if test="${empty product.imgPath }">
										暂无
									</c:if>
								</td>
								<td>
									${product.productCategory.name }
								</td>
								<td>
									${product.productBrand.name }
								</td>
								<td>
									<fmt:formatNumber value="${product.marketPrice }" pattern="0.00"></fmt:formatNumber> 
								</td>
								<td>
									<fmt:formatNumber value="${product.point }" pattern="0.00"></fmt:formatNumber> 
								</td>
								<td>
									<a href="javascript:gotoEdit('${product.id }');">[编辑]</a>
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
   		function gotoEdit(obj){
   			var productCategoryId = $("#productCategorys option:selected").val();
   			var productNum = $("#productBrandId option:selected").val();
   			var productName = $("#productName").val();
   			
   			var inputPage = $("#inputPage").val();
   			
   			var url = "<%=base %>manage/product/edit?id="+obj
   					+"&productCategoryId="+productCategoryId
   					+"&productNum="+productNum
   					+"&productName="+productName
   					+"&page="+inputPage;
   			
   			window.location.href=url;
   		}
   </script>
</body>
</html>
