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
<title>客户管理</title>

<jsp:include page="include/common.jsp" />

<link rel="stylesheet" href="<%=basePath%>bootstrap/css/jquery.gritter.css" />
<link rel="stylesheet" href="<%=basePath%>res/font/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=basePath%>res/css/head.css"/>
<link rel="stylesheet" href="<%=basePath%>res/font/css/font-style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/css/style.default.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>res/css/pager.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/css/jquery.gritter.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/css/select2.css" />
<link rel="stylesheet" href="<%=basePath%>bootstrap/css/bootstrap.min.css" media="screen" />
<style type="text/css">
.popcontent {
	background: #fff;
	width: 40%;
	position: absolute;
	top: 20%;
	left: 30%;
	padding-bottom: 20px;
}

.popcontent ul {
	width: 80%;
	margin: 0 auto;
}

.popcontent h4 {
	padding-top: 10px;
	text-align: center;
	padding-bottom: 10px;
	border-bottom: 1px solid #e5e5e5;
}

.popcontent ul li {
	width: 100%;
}

.popcontent ul li label {
	display: inline-block;
	width: 100px;
}

.popcontent .btn-settings {
	text-align: center;
}

.notes {
	height: 60px;
	width: 60%;
	resize: none;
}

.popcontent .btn-settings button {
	color: #fff;
	background-color: #428bca;
	border-color: #285e8e;
	width: 100px;
	height: 40px;
	border: none;
	outline: none;
	border-radius: 3px;
	-webkit-transition: all 0.2s ease-out 0s;
	transition: all 0.2s ease-out 0s;
	margin-right: 10px;
}

.popcontent button:hover {
	color: #fff;
	background-color: #3071a9;
	border-color: #285e8e;
}
.relative{
	position: relative;
}
.absolute{
	position: absolute;
	top:0px;
	right: 0px;
}
.row{
	height: 45px;
	margin-bottom: 5px;
	margin-top: 5px;
}
.navEnable li{
	border: 1px solid #f0f0f0;
}
	.options-pull select {
height: 34px;
border-sizing: border-box;
color: #555;
border: 1px solid #ccc;
border-radius: 4px;
}

</style>
<script src="<%=basePath%>res/js/list.js"></script>
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
			<li class="active" onclick="toCompany()"><a href="#following" data-toggle="tab"><strong>单位</strong></a></li>
			<li class="" onclick="toContacts()"><a href="#following" data-toggle="tab"><strong>联系人</strong></a></li>
			<!-- <li class="" onclick="toCompanyPool()"><a href="#following" data-toggle="tab"><strong>客户池</strong></a></li> -->
			<li class="" onclick="toCount()"><a href="#following" data-toggle="tab"><strong>统计</strong></a></li>
		</ul>
		<script type="text/javascript">
			// 跳转客户(公司)
	        function toCompany() {
	        	location.href = "<%=basePath%>company/list.do";
	        }
			// 跳转联系人
	        function toContacts() {
	        	location.href = "<%=basePath%>contacts/list.do";
	        }
			
			// 跳转客户池
			function toCompanyPool() {
	        	location.href = "<%=basePath%>company/pool.do";
			}
			
			// 去统计
			function toCount() {
				location.href = "<%=basePath %>count/total.do?ec=员工";
			}
			
		</script>
	</div>
	<!-- 客户/联系人/客户池 -->

	<form id="listForm" name="listForm" action="list.do" method="post">
		<div class="form-search selAdd relative">
			<div class="input-append">
				<div class="input-group mb15" style="width:240px">
					<input type="text" class="form-control" name="keyword"
						value="${pager.keyword }" placeholder="关键字" /> <span
						class="input-group-btn">
						<button type="button" class="btn btn-info" id="searchButton">搜索</button>
					</span>
				</div>
			</div>
			<div class="absolute">
				<button class="btn btn-primary btn-metro" onclick="addCompany()" type="button">新建单位>></button>
				<%-- <a href="<%=basePath%>company/add.do"
				style="float:right; margin-right:40px; margin-top:5px;">新建单位>></a> --%>
			</div>
		</div>
		<div class="form-search selAdd">
			<div>
				<c:forEach var="dictionary" items="${companyTags }" varStatus="index">
					<a href="<%=basePath %>company/listatus.do?publictags=${dictionary.dicname}">${dictionary.dicname}</a>
				</c:forEach>
			</div>
		</div>
		<div class="row">
			<div class="col-md-2" style="width: auto;">
				<div class="form-group" style="margin-top: 15px;">
					<div class="col-sm-12 options-pull">
						<select class="col-sm-12" id="enableSelect" onchange="enableList(this)">
							<option value="all">全部</option>
							<option value="true">有效</option>
						</select>
					</div>
			  </div>
              <!-- <ul class="nav nav-tabs nav-line navEnable">
                  <li onclick="enableList('all')" id="liAll"><a href="javascript:void(0)" data-toggle="tab"><strong>全部</strong></a></li>
                  <li onclick="enableList('true')" id="liOne"><a href="javascript:void(0)" data-toggle="tab"><strong>有效</strong></a></li>
              </ul> -->
              <input type="hidden" name="enableStatus" value="${enableStatus }" id="enableStatus"/>
	        </div>
			<div class="col-md-6 btnDIV" style="width: auto;margin-top: 15px;">
				<button class="btn btn-default" onclick="personList('0')" type="button" id="all0">全部</button>
				<button class="btn btn-default" onclick="personList('1')" type="button" id="all1">我负责</button>
				<button class="btn btn-default" onclick="personList('2')" type="button" id="all2">我创建</button>
				<button class="btn btn-default" onclick="personList('3')" type="button" id="all3">我参与</button>
				<button class="btn btn-default" onclick="personList('4')" type="button" id="all4">我下属</button>
				
	              <!-- <ul class="nav nav-tabs nav-line navEnable">
	                  <li onclick="personList('0')" id="all0"><a href="javascript:personList('0');" data-toggle="tab"><strong>全部</strong></a></li>
	                  <li onclick="personList('1')" id="all1"><a href="javascript:personList('1');" data-toggle="tab"><strong>我负责</strong></a></li>
	                  <li onclick="personList('2')" id="all2"><a href="javascript:personList('2');" data-toggle="tab"><strong>我创建</strong></a></li>
	                  <li onclick="personList('3')" id="all3"><a href="javascript:personList('3');" data-toggle="tab"><strong>我参与</strong></a></li>
	                  <li onclick="personList('4')" id="all4"><a href="javascript:personList('4');" data-toggle="tab"><strong>我下属 </strong></a></li>
	              </ul> -->
	              <input type="hidden" name="personStatus" value="${personStatus }" id="personStatus"/>
	        </div>
		</div>
		<div class="table-responsive">
			<table class="table table-info table-hover">
				<thead>
					<tr>
						<th><input type="checkbox" id="checkAll" class="allCheck" />
						</th>
						<th>单位名称</th>
						<!-- <th>类型</th>
						<th>电话</th> -->
						<th>城市</th>
						<th>标签</th>
						<th>行业</th>
						<th>创建时间</th>
						<th>国籍</th>
						<th>负责人</th>
						<th>创建人</th>
						<!-- <th>联系次数</th> -->
						<%-- <c:if test="${!empty company.fieldsSet}">
						<c:forEach var="fields" items="${company.fieldsSet }">
							<th>${fields.fieldname }</th>
							</c:forEach></c:if> --%>
						<th>状态</th>
						<th>操作</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="company" items="${pager.list}" varStatus="index">
						<tr id="tr_${company.id}">
							<td><input type="checkbox" name="ids" id="checkItem"
								value="${company.id}" />
							</td>
							<td><a
								href="<%=basePath %>company/details.do?id=${company.id}"
								class="tooltips" data-original-title="点击查看${company.pubname }详情" title="点击查看${company.pubname }详情">${company.pubname	}</a>
							</td>
							<%-- <td><spring:message code="Publbname.${company.publbname }"></spring:message>
							</td>
							<td>${company.pubtel }</td> --%>
							<td>${company.city }</td>
							<td>${company.publictags }</td>
							<td>${company.pubindustry }</td>
							<td><fmt:formatDate value="${company.createDate }"
									pattern="yyyy-MM-dd" />
							</td>
							<td>${company.pubnation }</td>
							<td>${company.admin.pername }</td>
							<td>${company.creator }</td>
							<td id="status">
								<c:choose>
									<c:when test="${company.enabled eq true }">
										有效
									</c:when>
									<c:otherwise>
										作废
									</c:otherwise>
								</c:choose>
							</td>
							<%-- <td>${company.contactstimes }</td> --%>
							<%-- <td>
							<c:forEach var="fields" items="${company.fieldsSet }">
							${fields.fieldname }
							</c:forEach>
							</td> --%>
							<%-- 	<c:if test="${!empty company.fieldsSet}">
						<c:forEach var="fields" items="${company.fieldsSet }">
							<th>${fields.fieldval }</th>
							</c:forEach></c:if> --%>
							<td class="table-action-hide"><a
								href="<%=basePath%>company/shareview.do?id=${company.id}"
							    data-toggle="modal"	data-target=".add-panel" class="tooltips" title="分享"
								data-original-title="分享"> <span
									class="glyphicon glyphicon-share-alt"></span> </a> &nbsp; <a
								href="<%=basePath %>company/edit.do?id=${company.id}"
								data-toggle="tooltip" title="编辑" class="tooltips"
								data-original-title="编辑"> <i class="fa fa-pencil"></i> </a>
								&nbsp; <a href="javascript:deleteCompany('${company.id}');"
								data-toggle="tooltip" title="作废" class="tooltips"
								data-original-title="作废"> <i class="fa fa-trash-o"></i> </a>
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
				<div class="modal-content" style="height: 84px;"></div>
			</div>
		</div>
	<div class="btn-list">
		<button class="btn btn-primary deleteButton" onclick="shareCompanys()"
			disabled="disabled" class="tooltips" data-toggle="modal" data-target=".add-panel">分享</button>
		<button class="btn btn-danger deleteButton" onclick="deleteCompanys()"
			disabled="disabled">删除</button>
		<jsp:include page="pager.jsp" />
	</div>
	</form>
	</div>
	</div>

	<script type="text/javascript">

	        jQuery(document).ready(function(){
	            
	        	//初始化数据条件
	        	//1、设置有效性
	        	var enableStatus = $("#enableStatus").val();
	        	if(enableStatus == "true"){
	        		$("#enableSelect").find("option[value='true']").prop("selected",true);
	        	}else{
	        		$("#enableSelect").find("option[value='all']").prop("selected",true);
	        	}
	        	
	        	//2、设置我所参与的
	        	var personStatus = $("#personStatus").val();
	        	var button = $(".btnDIV button");
	        	for(var i = 0 ;i<button.length ; i++){
	        		$(button[i]).removeClass("btn-primary");
	        	}
	        	
	        	
				if(personStatus != ""){
					$("#all"+personStatus).addClass("btn-primary");
				}else{
					$("#all0").addClass("btn-primary");
				}
	        	
	        	// Delete row in a table
	             jQuery('.delete-row').click(function(){
	                var c = confirm("您确定要删除?");
	                
					if (c) {
						jQuery(this).closest('tr').fadeOut(function() {
							jQuery(this).remove();
						});
					}
					return false;
				});

				// Show aciton upon row hover
				jQuery('.table tbody tr').hover(function() {
					jQuery(this).find('.table-action-hide a').animate({
						opacity : 1
					}, 100);
				}, function() {
					jQuery(this).find('.table-action-hide a').animate({
						opacity : 0
					}, 100);
				});

	        });

			// 分享
			function shareCompanys() {

					var names = document.getElementsByName("ids");
					var ids = "";
						for(var i=0; i<names.length; i++){
							if(names[i].checked){
								ids += names[i].value +",";
							}
					}
<%--			location.href="<%=basePath%>company/shareviews.do?ids="+ids;--%>
				var url = "<%=basePath%>company/shareviews.do?ids="+ids;
				$(".modal-content").load(url);
			}

			// 删除
			function deleteCompany(id) {
				if (confirm("您确定要作废该数据？")) {
					$.ajax({
						  type: 'POST',
						  url: '<%=basePath%>company/delete.do',
						  data: {'id':id},
						  dataType: 'json',
						  success: function(data) {
							  if(data.status == 0){
								  $("#tr_" + id).find("td[id='status']").text("作废");
							  }
						  }
						});
				}
			}
			
			//批量删除
			function deleteCompanys() {
				var names = document.getElementsByName("ids");
				/* var list = new Array(); */
				var ids = "";
					for(var i=0; i<names.length; i++){
						if(names[i].checked){
							 /* list.push(names[i].value); */
							 ids += names[i].value +",";
						}
					}
					if (confirm("您确定要作废选中的所有客户吗？")) {
						$.ajax({
							  type: 'POST',
							  url: '<%=basePath%>company/deletes.do',
							data : {
								'ids' : ids
							},
							dataType : 'json',
							success : function(data) {
								//alert(data)
								BatchDeletion(data.deleteids);
							},
							error : function(e) {
								console.log("test");
								console.log(e);
							}
						});
			}
		}
		function BatchDeletion(data) {
			for ( var i = 0; i < data.length; i++) {
				$("#tr_" + data[i]).find("td[id='status']").text("作废");
			}
		}
		//设置有效性
		function enableList(obj){
			var value = $(obj).val();
			$("#pageNumber").val("1");
			$("#enableStatus").val(value);
			$("#listForm").submit();
		}
		//设置人员选择
		function personList(obj){
			 $("#pageNumber").val("1");
			$("#personStatus").val(obj);
			$("#listForm").submit();
		}
		//新建单位
		function addCompany(){
			window.location.href = "<%=basePath%>company/add.do";
		}
	</script>
</body>
</html>