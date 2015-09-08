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
	<link href="${pageContext.request.contextPath}/resource/chain/css/select2.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resource/css/manage.common.css" rel="stylesheet">
	
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/modernizr.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/pace.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/retina.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery.cookies.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/select2.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
	<script src="${pageContext.request.contextPath}/resource/js/myform.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/laydate/laydate.js"></script>
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.min.js"></script>
	<![endif]-->
	<style type="text/css">
		#chooseTeamContent label{
			margin-right: 15px;
		}
		div.form-group{
			width: 49%;
			display: inline-block;
			border-top: 0px dotted #d3d7db
		}
		div.form-bordered .form-group{
			
		}
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
		            <li><a href="">评论列表</a></li>
		          </ul>
		        </div>
		      </div><!-- media -->
		    </div>
		    <form id="pageList" action="${pageContext.request.contextPath}/manage/comment/list" method="post" class="form-horizontal form-bordered">
			    <div class="contentpanel">
			      <div class="search-header">
			        <div class="form-group" style="border-top: 0px dotted #d3d7db">
						<label class="col-sm-2 control-label">开始时间</label>
						<div class="col-sm-8">
							<div class="input-group">
                             	 <input type="text" class="form-control" placeholder="开始时间" id="datepicker" onclick="initPicker('datepicker');" name="startDate" value="<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd" />">
                             	 <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                       	    </div>
						</div>
					</div>
					 <div class="form-group" style="border-top: 0px dotted #d3d7db">
						<label class="col-sm-2 control-label">结束时间</label>
						<div class="col-sm-8">
							 <div class="input-group">
                             	 <input type="text" class="form-control" placeholder="结束时间" id="datepicker1" onclick="initPicker('datepicker1');" name="endDate" value="<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd" />">
                             	 <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                       	 	 </div>
						</div>
					</div>
					 <div class="form-group" style="border-top: 0px dotted #d3d7db">
						<label class="col-sm-2 control-label">服务</label>
						<div class="col-sm-8"  style="margin-top: 5px;">
							<c:if test="${serviceTypes.size() > 0 }">
								<select id="serviceType" class="col-sm-12 form-control select2-container" name="serviceTypeId">
									<option value="">请选择</option>
									<c:forEach var="value" items="${serviceTypes }">
										<option value="${value.id }" <c:if test="${value.id eq serviceTypeId}"> selected="selected" </c:if> >
											${value.name }
										</option>
									</c:forEach>
								</select>
							</c:if>
						</div>
					</div>
					<div class="form-group" style="border-top: 0px dotted #d3d7db">
						<label class="col-sm-2 control-label">商品编号</label>
						<div class="col-sm-8">
							<input type="text" name="productNum" class="form-control" maxlength="200" value=""/>
						</div>
					</div>
					<div class="btn-list">
			         <%--  <button class="btn btn-danger" id="deleteButton" type="button" val="<%=path %>/manage/order" disabled>删除</button> --%>
			          <button class="btn btn-success" id="refreshButton">刷新</button>
			          <button type="button" class="btn btn-info btn-metro" onclick="confirmQuery();">查询</button>
					 <%--  <button id="add" type="button" class="btn btn-primary" val="<%=path %>/manage/order">添加</button> --%>
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
								<th>
									评论账户
								</th>
								<th>
									评论内容
								</th>
								<th>
									服务
								</th>
								<th>
									商品
								</th>
								<th>
									评论时间
								</th>
								<th>
									回复人
								</th>
								<th>
									回复内容
								</th>
								<th>
									回复时间
								</th>
								<th>
									操作
								</th>
							</tr>
						</thead>
						<c:forEach items="${pageView.records}" var="value">  
							<tr>
								<td>${value.member.mobile }</td>							
								<td>${value.content }</td>							
								<td>
									${value.serverOrder.serviceType.name }
								</td>							
								<td>
									${value.serverOrderDetail.product.name }
								</td>
								<td>
									<fmt:formatDate value="${value.createDate }" pattern="yyyy-MM-dd HH:mm"/>
								</td>
								<td>
									${value.account.username }
								</td>
								<td>
									${value.reply }
								</td>
								<td>
									<fmt:formatDate value="${value.replyDate }" pattern="yyyy-MM-dd HH:mm"/>
								</td>
								<td>
									<a href="javascript:void(0);" onclick="orderDetail('${value.id}')">[订单详情]</a>
									<c:if test="${empty value.account}">
										<a href="javascript:void(0);" onclick="showModal('${value.id}');">[回复]</a>
									</c:if>
									<a href="javascript:void(0);" onclick="deleteComment('${value.id}',this)">[删除]</a>
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
   
   <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
              <div class="modal-header">
                  <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                  <h4 class="modal-title">评论回复</h4>
              </div>
              <input type="hidden" value="" id="commentId"/>
              <div class="modal-body" id="chooseTeamContent">
              		<input type="text" id="replyContent"/>
              </div>
              <div class="modal-footer">
             		<button data-dismiss="modal" type="button" aria-hidden="true" class="btn btn-dark">关闭</button>
              		<button type="button" onclick="commentReply();" class="btn btn-primary mr5">确定</button>
              </div>
          </div>
        </div>
    </div>
   <script type="text/javascript">
   		var base = "<%=base %>";
   		function initPicker(obj){
   			var pricker = {
   				elem : '#'+obj,
   				format : 'YYYY-MM-DD',
   				istime : true,
   				istoday : false
   			};
   			laydate(pricker);
   		}
   		
   		$(document).ready(function(){
   			$("#serviceType").select2();
   		});
   		
   		//订单详情
   		function orderDetail(obj){
   			window.location.href= base + "manage/comment/detial?commentId="+obj;
   		}
   		
   		//客服回复
   		function commentReply(){
   			var commentId = $("#commentId").val();
   			var commentContent = $("#replyContent").val();
   			
   			var url = base + "manage/comment/reply";
   			var data = {
   				id:	commentId,
   				content:commentContent
   			};
   			$.post(url,data,function(result){
   				if(result.status == "success"){
   					alert("评论成功");
   					$(".close").click();
   					window.location.reload(true);
   				}else{
   					alert(result.message);
   				}
   			},"json");
   		}
   		
   		function showModal(obj){
   			$("#commentId").val(obj);
   			/* var url = base + "manage/order/getCarTeam";
   			var data = {orderId:obj};
   			$.post(url,data,function(result){
   				if(result.status == "success"){
   					addLabel(result.message);
   				}else{
   					addError();
   				}
   			},"json"); */
   			
   			$(".bs-example-modal-lg").modal();
   		}
   		
   		//删除评价
   		function deleteComment(obj,e){
   			if(!confirm("确定删除该数据?")){
   				return ;
   			}
   			var url = base + "manage/comment/delete";
   			var data = {
   				id:obj	
   			};
   			$.post(url,data,function(result){
   				if(result.status == "success"){
   					alert("删除成功");
   					$(e).closest("tr").remove();
   				}else{
   					alert("删除失败");
   				}
   			},"json");
   		}
   		
   </script>
</body>
</html>
