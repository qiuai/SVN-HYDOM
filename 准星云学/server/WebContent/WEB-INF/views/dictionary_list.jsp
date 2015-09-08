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
<title>设置</title>
	<link href="<%=basePath %>bootstrap/css/style.default.css" rel="stylesheet" />
	<link href="<%=basePath %>res/css/pager.css" rel="stylesheet" />
    <link rel="stylesheet" href="../bootstrap/css/jquery.gritter.css" /> 
	<style type="text/css">
		.table{
			margin-bottom: 0px;
		}
	</style>
</head>
<body>
	<div class="contentpanel">
		<div class="panel panel-primary-head">
			<ul class="nav nav-tabs nav-line">
                 <li class="<c:if test='${pType == 0}'>active</c:if>" onclick="changeType(0)"><a href="#" data-toggle="tab"><strong>个人</strong></a></li>
                 <li class="<c:if test='${pType == 1}'>active</c:if>" onclick="changeType(1)"><a href="#" data-toggle="tab"><strong>公共</strong></a></li>
			</ul>
		</div>
		
		<div class="row">
			<!-- 客户标签 -->
			<div class="col-md-6">
				<div class="panel panel-primary widget-todo">
                     <div class="panel-heading">
                     	<div class="panel-btns" style="display: none;">
							<a href="<%=basePath %>dictionary/add.do?dicType=0&pType=${pType}" class="new-msg tooltips" data-toggle="modal" data-target=".add-panel" data-original-title="新增"><i class="fa fa-edit"></i></a>
                            <a href="#" class="tooltips panel-minimize" data-toggle="tooltip" data-original-title="最小化"><i class="fa fa-minus"></i></a>
                         </div>
                         <h3 class="panel-title">客户标签</h3>
                       </div>
                       <div class="panel-body" style="padding: 0px">
							<div class="table-responsive">
                                 <table class="table table-hidaction table-hover mb30">
                                      <thead>
<!-- 	                                        <tr> -->
<!-- 	                                          <th>#</th> -->
<!-- 	                                          <th>标签</th> -->
<!-- 	                                          <th></th> -->
<!-- 	                                        </tr> -->
                                      </thead>
                                      <tbody>
                                  		<c:forEach var="dic" items="${companyTags}" varStatus="status">
                                        <tr id="tr_${dic.id}">
                                          <td>${status.index + 1}</td>
                                          <td>${dic.dicname}</td>
                                          <td class="table-action"">
                                          		<div style="float: right;">
													<a href="<%=basePath %>dictionary/edit.do?id=${dic.id}" data-toggle="modal" data-target=".add-panel" class="tooltips" data-original-title="编辑">
														<i class="fa fa-pencil"></i>
													</a>
													&nbsp;
													<a href="javascript:deleteDic('${dic.id}');"  title="" class="delete-row tooltips" data-original-title="删除">
														<i class="fa fa-trash-o"></i>
													</a>
												</div>
											</td>
                                        </tr>
                                        </c:forEach>
                                      </tbody>
                                  </table>
                             </div>
                       </div>
                   </div>		
			</div>
			
			<!-- 联系人标签 -->
			<div class="col-md-6">
				<div class="panel panel-warning-alt widget-todo">
                     <div class="panel-heading">
                     	<div class="panel-btns" style="display: none;">
							<a href="<%=basePath %>dictionary/add.do?dicType=1&pType=${pType}" class="new-msg tooltips" data-toggle="modal" data-target=".add-panel" data-original-title="新增"><i class="fa fa-edit"></i></a>
                            <a href="#" class="tooltips panel-minimize" data-toggle="tooltip" data-original-title="最小化"><i class="fa fa-minus"></i></a>
                         </div>
                         <h3 class="panel-title">联系人标签</h3>
                       </div>
                       <div class="panel-body" style="padding: 0px">
							<div class="table-responsive">
                                 <table class="table table-hidaction table-hover mb30">
                                     <thead>
<!-- 	                                        <tr> -->
<!-- 	                                          <th>#</th> -->
<!-- 	                                          <th>标签</th> -->
<!-- 	                                          <th></th> -->
<!-- 	                                        </tr> -->
                                     </thead>
                                     <tbody>
                                     <c:forEach var="dic" items="${contractTags}" varStatus="status">
                                       <tr id="tr_${dic.id}">
                                         <td>${status.index + 1 }</td>
                                         <td>${dic.dicname }</td>
                                         <td class="table-action">
                                         		<div style="float: right;">
												<a href="<%=basePath %>dictionary/edit.do?id=${dic.id}" data-toggle="modal" data-target=".add-panel" class="tooltips" data-original-title="编辑">
													<i class="fa fa-pencil"></i>
												</a>
												&nbsp;
												<a href="javascript:deleteDic('${dic.id}');"  title="" class="delete-row tooltips" data-original-title="删除">
													<i class="fa fa-trash-o"></i>
												</a>
											</div>
										</td>
                                       </tr>
                                      </c:forEach>
                                     </tbody>
                                 </table>
                            </div>
                       </div>
                   </div>		
			</div>			
			
			<!-- 动态类型 -->
			<div class="col-md-6">
				<div class="panel panel-info-alt widget-todo">
                     <div class="panel-heading">
                     	<div class="panel-btns" style="display: none;">
							<a href="<%=basePath %>dictionary/add.do?dicType=3&pType=${pType}" class="new-msg tooltips" data-toggle="modal" data-target=".add-panel" data-original-title="新增"><i class="fa fa-edit"></i></a>
                            <a href="#" class="tooltips panel-minimize" data-toggle="tooltip" data-original-title="最小化"><i class="fa fa-minus"></i></a>
                         </div>
                         <h3 class="panel-title">动态类别</h3>
                       </div>
                       <div class="panel-body" style="padding: 0px">
							<div class="table-responsive">
	                                  <table class="table table-hidaction table-hover mb30">
	                                      <thead>
<!-- 	                                        <tr> -->
<!-- 	                                          <th>#</th> -->
<!-- 	                                          <th>标签</th> -->
<!-- 	                                          <th></th> -->
<!-- 	                                        </tr> -->
	                                      </thead>
	                                      <tbody>
	                                      	<c:forEach var="dic" items="${eventTags}" varStatus="status">
	                                        <tr id="tr_${dic.id}">
	                                          <td>${status.index + 1}</td>
	                                          <td>${dic.dicname }</td>
	                                          <td class="table-action">
	                                          		<div style="float: right;">
														<a href="<%=basePath %>dictionary/edit.do?id=${dic.id}" data-toggle="modal" data-target=".add-panel" class="tooltips" data-original-title="编辑">
															<i class="fa fa-pencil"></i>
														</a>
														&nbsp;
														<a href="javascript:deleteDic('${dic.id}');"  title="" class="delete-row tooltips" data-original-title="删除">
															<i class="fa fa-trash-o"></i>
														</a>
													</div>
												</td>
	                                        </tr>
	                                        </c:forEach>
	                                      </tbody>
	                                  </table>
	                             </div>
                       </div>
                   </div>		
			</div>
			
			<c:if test="${pType == 1}">
			<!-- 行业 -->
			<div class="col-md-6">
				<div class="panel panel-info widget-todo">
                     <div class="panel-heading">
                     	<div class="panel-btns" style="display: none;">
							<a href="<%=basePath %>dictionary/add.do?dicType=2&pType=${pType}" class="new-msg tooltips" data-toggle="modal" data-target=".add-panel" data-original-title="新增"><i class="fa fa-edit"></i></a>
                            <a href="#" class="tooltips panel-minimize" data-toggle="tooltip" data-original-title="最小化"><i class="fa fa-minus"></i></a>
                         </div>
                         <h3 class="panel-title">行业类别</h3>
                       </div>
                       <div class="panel-body" style="padding: 0px">
							<div class="table-responsive">
                                 <table class="table table-hidaction table-hover mb30">
                                     <thead>
<!-- 	                                        <tr> -->
<!-- 	                                          <th>#</th> -->
<!-- 	                                          <th>标签</th> -->
<!-- 	                                          <th></th> -->
<!-- 	                                        </tr> -->
                                     </thead>
                                     <tbody>
                                     <c:forEach var="dic" items="${industrys }" varStatus="status">
                                       <tr id="tr_${dic.id}">
                                         <td>${status.index + 1}</td>
                                         <td>${dic.dicname }</td>
                                         <td class="table-action" >
                                         		<div style="float: right;">
												<a href="<%=basePath %>dictionary/edit.do?id=${dic.id}" data-toggle="modal" data-target=".add-panel" class="tooltips" data-original-title="编辑">
													<i class="fa fa-pencil"></i>
												</a>
												&nbsp;
												<a href="javascript:deleteDic('${dic.id}');"  title="" class="delete-row tooltips" data-original-title="删除">
													<i class="fa fa-trash-o"></i>
												</a>
											</div>
										</td>
                                       </tr>
                                       </c:forEach>
                                     </tbody>
                                 </table>
                            </div>
                       </div>
                   </div>		
			</div>			
			</c:if>	
		</div>
	</div>			
	<!-- 添加Panel -->
	<div class="modal fade add-panel" id="inputDialog" tabindex="-1" role="dialog" style="display: none;" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content"></div>
        </div>
    </div>
    
    <!-- 删除确认对话框 -->
	<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
              <h4 class="modal-title" id="myModalLabel">确认删除</h4>
            </div>
            <div class="modal-body">
              	您确认要删除该项吗?
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
              <button type="button" class="btn btn-primary" id="confirmDelete" onclick="confirmDelete()">删除</button>
              <input type="hidden" id="waitdelete" value="">
            </div>
          </div><!-- modal-content -->
        </div><!-- modal-dialog -->
     </div>
    <!-- 删除确认对话框 -->
     <!-- 公开或私有 -->
	<input type="hidden" id="pType" value="${pType }" />
    <script src="<%=basePath %>bootstrap/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath %>bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
    <script src="<%=basePath %>bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=basePath %>bootstrap/js/jquery.cookies.js"></script>
    <script src="<%=basePath %>bootstrap/js/pace.min.js"></script>
    <script src="<%=basePath %>bootstrap/js/retina.min.js"></script>
    <script src="<%=basePath %>bootstrap/js/jquery.gritter.min.js"></script>
    <script src="<%=basePath %>bootstrap/js/custom.js"></script>
	<script src="<%=basePath %>res/js/common.js"></script>
	<script type="text/javascript">
		function changeType(pType) {
			location.href = "list.do?pType=" + pType;
		}
		
		/**
		 * 删除.
		 */
		function deleteDic(id) {
			$("#waitdelete").val(id);
			$('#deleteModal').modal({
			      keyboard: true
		   });
		}
		
		/**
		 * 确认删除.
		 */
		function confirmDelete() {
			//hideModal$
			$("#deleteModal").modal("hide");
			var id = $("#waitdelete").val();
			$.ajax({
				  type: 'POST',
				  url: '<%=basePath%>dictionary/delete.do',
				  data: {'id':id},
				  dataType: 'json',
				  success: function() {
					    $("#tr_" + id).remove();
				  }
				});
		}		
	</script>
</body>
</html>