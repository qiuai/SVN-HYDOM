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
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>联系人列表</title>
<link href="<%=basePath %>bootstrap/css/bootstrap.css" rel="stylesheet" media="screen" /> 
	<link href="<%=basePath %>bootstrap/css/style.default.css" rel="stylesheet" />
	<link href="<%=basePath %>res/css/pager.css" rel="stylesheet" />
     <link rel="stylesheet" href="<%=basePath %>bootstrap/css/jquery.gritter.css" />
     <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/select2.css"/>
   <%--  <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/style.css"/> --%>
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
                   <li class="" onclick="toCount()"><a href="#following" data-toggle="tab"><strong>统计</strong></a></li>
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
				location.href="<%=basePath%>count/total.do?ec=员工";
			}
			
		</script>
	</div>
	<!-- 客户/联系人/客户池 -->
	<form id="listForm" name="listForm" action="list.do" method="post">
					<div class="form-search selAdd">
						  <div class="input-append">
						  	<div class="input-group mb15" style="width:240px">
							    <input type="text" class="form-control" name="keyword" value="${pager.keyword }" placeholder="关键字"/>
							  
							    <span class="input-group-btn">
                              	  <button type="button" class="btn btn-info" id="searchButton">搜索</button>
                                </span>
						  	</div>
<!-- 						    <input type="search" class="form-controly" placeholder="搜索"/> -->
<!-- 						    <button type="submit" class="btn">Search</button> -->
						  </div>
						  <c:forEach var="dictionary" items="${contactsTags }" varStatus="index">
						  <a href="<%=basePath %>contacts/listatus.do?contags=${dictionary.dicname}">${dictionary.dicname}</a>
						  </c:forEach>
						  <a href="<%=basePath %>contacts/add.do" style="float:right; margin-right:40px; margin-top:5px;">新增联系人>></a>
					</div>
						<div class="table-responsive">
                            <table class="table table-info table-hover mb10 ">
                                <thead>
                                  <tr>
                                    <th><input type="checkbox" id="checkAll" class="allCheck"/></th>
                                    <th>联系人姓名</th>
                                    <th>性别</th>
                                    <th>电话</th>
                                    <th>职位</th>
                                    <th>座机</th>
                                    <th>邮箱</th>
                                    <th>传真</th>
                                    <th>创建时间</th>
                                    <th>标签</th>
                                    <th></th>
                                  </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="contacts" items="${pager.list}" varStatus="index">
                                  <tr id="tr_${contacts.id }">
                                    <td><input type="checkbox" name="ids" id="checkItem" value="${contacts.id }" /></td>
                                    <td><a href="<%=basePath %>contacts/details.do?id=${contacts.id}" class="tooltips" data-original-title="点击查看${contacts.conname }详情" title="点击查看${contacts.conname }详情">${contacts.conname }</a></td>
                                   	<%-- <td><spring:message code="Sex.${contacts.consex }"></spring:message></td> --%>
                                   	<td>${contacts.consex=='male'?"男":"女" }</td>
                                    <td>${contacts.conmobile }</td>
                                    <td>${contacts.conjob }</td>
                                    <td>${contacts.contel }</td>
                                    <td>${contacts.conmail }</td>
                                    <td>${contacts.confax }</td>
                                    <td><fmt:formatDate value="${contacts.createDate }" pattern="yyyy-MM-dd" /></td>
                                    <td>${contacts.contags }</td>
                                    <td class="table-action-hide">
<%--										<a href="javascript:void(0)" data-toggle="tooltip" title="" class="delete-row tooltips" data-original-title="分享">--%>
<%--											<span class="glyphicon glyphicon-share-alt"></span>--%>
<%--										</a>--%>
										<a href="<%=basePath%>contacts/distribuView.do?id=${contacts.id}"  data-toggle="modal"	data-target=".add-panel"  title="分配" class="tooltips" data-original-title="分配">
											<span class="fa fa-wrench"></span>
										</a>
										&nbsp;
										<a href="<%=basePath%>contacts/edit.do?id=${contacts.id}" data-toggle="tooltip" title="编辑" class="tooltips" data-original-title="编辑">
											<i class="fa fa-pencil"></i>
										</a>
										&nbsp;
										<a href="javascript:deleteContact('${contacts.id }')" data-toggle="tooltip" title="删除" class="tooltips" data-original-title="删除">
											<i class="fa fa-trash-o"></i>
										</a>
									</td>
                                  </tr>
								 
								  </c:forEach>
                                </tbody>
                            </table>
                             </div>
              <!-- 添加Panel -->
					<div class="modal fade add-panel" id="inputDialog" tabindex="-1"
						role="dialog" style="display: none;" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content" style="height: 86px;"></div>
						</div>
					</div>
						</form>
                            <div class="btn-list">
<%--                            	<button class="btn btn-primary deleteButton" onclick="shareCompany()" disabled="disabled">分享</button>--%>
								<button class="btn btn-primary deleteButton" onclick="distriContacts()" disabled="disabled" class="tooltips" data-toggle="modal" data-target=".add-panel">分配</button>
                            	<button class="btn btn-danger deleteButton" onclick="deleteContacts()" disabled="disabled">删除</button>
                            	<jsp:include page="pager.jsp" />
                            </div>
                       
	<script type="text/javascript">

	        jQuery(document).ready(function(){
	            
	            // Delete row in a table
	            jQuery('.delete-row').click(function(){
	                var c = confirm("Continue delete?");
	                if(c)
	                    jQuery(this).closest('tr').fadeOut(function(){
	                    jQuery(this).remove();
	                });
	                return false;
	            });
	            
	            // Show aciton upon row hover
	            jQuery('.table tbody tr').hover(function(){
	                jQuery(this).find('.table-action-hide a').animate({opacity: 1},100);
	            },function(){
	                jQuery(this).find('.table-action-hide a').animate({opacity: 0},100);
	            });
	
	        });
	        
	        // 分配
			function distriContacts() {

					var names = document.getElementsByName("ids");
					var ids = "";
						for(var i=0; i<names.length; i++){
							if(names[i].checked){
								ids += names[i].value +",";
							}
					}
				$(".modal-content").load("<%=basePath%>contacts/distribuViews.do?ids="+ids);
			}
<%--	        function shareCompany() {--%>
<%--	       	 jQuery.gritter.add({--%>
<%--	             title: '分享!',--%>
<%--	             text: '点击这里分享客户信息.',--%>
<%--	             class_name: 'growl-info',--%>
<%--	    		 image: '../bootstrap/images/screen.png',--%>
<%--	             sticky: false,--%>
<%--	             time: ''--%>
<%--	      });--%>
<%--	        }--%>
	        
	     // 删除
			function deleteContact(id) {
				if (confirm("您确定要删除吗？")) {
					$.ajax({
						  type: 'POST',
						  url: '<%=basePath%>contacts/delete.do',
						  data: {'id':id},
						  dataType: 'json',
						  success: function(data) {
							    $("#tr_" + id).remove();
						  }
						});
				}
			}
	     
			//批量删除
			function deleteContacts() {
				var names = document.getElementsByName("ids");
				/* var list = new Array(); */
				var ids = "";
					for(var i=0; i<names.length; i++){
						if(names[i].checked){
							 /* list.push(names[i].value); */
							 ids += names[i].value +",";
						}
					}
					if (confirm("您确定要删除选中的所有客户吗？")) {
						$.ajax({
							  type: 'POST',
							  url: '<%=basePath%>contacts/deletes.do',
							  data: {'ids':ids},
							  dataType: 'json',
							  success: function(data) {
								  BatchDeletion(data.deleteids);
							  },
							  error: function(e){
								  console.log("test");
								  console.log(e);
							  }
							});
					}
			}
			function BatchDeletion(data){
				for(var i = 0; i<data.length; i++){
					$("#tr_" + data[i]).remove();
				}
			}
		</script>
</body>
</html>