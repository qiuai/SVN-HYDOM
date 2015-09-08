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
<title>统计</title>
<link href="<%=basePath%>bootstrap/css/bootstrap.css" rel="stylesheet"
	media="screen" />
<link href="<%=basePath%>bootstrap/css/style.default.css"
	rel="stylesheet" />
<link href="<%=basePath%>res/css/pager.css" rel="stylesheet" />
<link rel="stylesheet"
	href="<%=basePath%>bootstrap/css/jquery.gritter.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>bootstrap/css/select2.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>bootstrap/css/style.css" />
<link rel="stylesheet" href="<%=basePath%>res/css/head.css" media="screen" />


<script src="<%=basePath%>bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery.cookies.js"></script>
<script src="<%=basePath%>bootstrap/js/pace.min.js"></script>
<script src="<%=basePath%>bootstrap/js/retina.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery.gritter.min.js"></script>
<script src="<%=basePath%>bootstrap/js/custom.js"></script>
<script src="<%=basePath%>res/js/common.js"></script>
<script src="<%=basePath%>res/js/list.js"></script>
<script src="<%=basePath%>res/js/laydate/laydate.js"></script>
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
			<!-- <li class="" onclick="toCompany()"><a href="#following"
				data-toggle="tab"><strong>单位</strong>
			</a>
			</li>
			<li class="" onclick="toContacts()"><a href="#following"
				data-toggle="tab"><strong>联系人</strong>
			</a>
			</li>
			<li class="" onclick="toCompanyPool()"><a href="#following"
				data-toggle="tab"><strong>客户池</strong>
			</a>
			</li> -->
			<li class="active" onclick="toCount()"><a href="#following"
				data-toggle="tab"><strong>统计</strong>
			</a>
			</li>
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
				location.href="<%=basePath%>count/total.do?ec=员工";
			}
			//流水
			function toExport_per(){

				var names = document.getElementsByName("perids");
				var dicnames = document.getElementsByName("dicnames");
				var ids = "";
				var dics ="";
					for(var i=0; i<names.length; i++){
						if(names[i].checked){

							 ids += names[i].value +",";
						}
					}
					for(var i=0;i<dicnames.length;i++){
						if(dicnames[i].checked){
							dics += dicnames[i].value + ",";
						}
					}
				location.href = "<%=basePath%>count/exports.do?ids="+ids+"&dicnames="+dics+"&ec=员工";
			}
			
			function toExport_com(){
				var names = document.getElementsByName("comids");
				var dicnames = document.getElementsByName("dicnames");
				var ids = "";
				var dics ="";
					for(var i=0; i<names.length; i++){
						if(names[i].checked){

							 ids += names[i].value +",";
						}
					}
					for(var i=0;i<dicnames.length;i++){
						if(dicnames[i].checked){
							dics += dicnames[i].value + ",";
						}
					}
				location.href = "<%=basePath%>count/exports.do?ids="+ids+"&dicnames="+dics+"&ec=单位";
			}
			
			//汇总
			function toDistribution(){
				location.href = "<%=basePath%>count/distribution.do";
			}
			
			//明细
			function toDetail(){
				location.href = "<%=basePath%>count/detail.do";
			}
		</script>
	</div>
	<!-- 客户/联系人/客户池 -->
	<div class="well" style="height: 194px;">
		<div class="col-ms-12">
			<div class="form-group" style="height: 50px;">
				<label class="col-sm-1 control-label" style="width: 10%;"> 关键字： </label>
				<div class="col-sm-4">
					<input class="form-control" type="text" name="keyword"
							placeholder="请输入关键字" value=""/>
				</div>
				<div class="btn-list">
				<c:if test="${ec=='员工' }">
					<button class="btn btn-primary btn-metro" id="searchButton" style="margin-left: 33px;" onclick="count_per()">搜索</button>
					</c:if>
					<c:if test="${ec=='单位' }">
					<button class="btn btn-primary btn-metro" id="searchButton" style="margin-left: 33px;" onclick="count_com()">搜索</button>
					</c:if>
					<c:if test="${ec eq '类型' }">
					<button class="btn btn-primary btn-metro" id="searchButton" style="margin-left: 33px;" onclick="count_type()">搜索</button>
					</c:if>
					<%--	 style="margin-left: 60px;"              --%>
				</div>
			</div>
			<div class="form-group" style="height: 50px;">
					<label class="col-sm-1 control-label" style="width: 10%;">
						日期：
					</label>
					<div class="col-sm-2" id="col-sm-2" style="width: 183px;">
						<div class="input-group">
							<input type="text" class="form-control hasDatepicker"placeholder=""  id="start"/>
								<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i>
							</span>
						</div>
					</div>
					<div class="" style="width:2px;margin-left:-3px;height:34px;line-height:34px; float:left;">至</div>
					<div class="col-sm-2" id="col-sm-2"	style="margin-left: 8px;width: 183px;">
						<div class="input-group">
							<input type="text" class="form-control hasDatepicker"placeholder=""  id="end"/>
								<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i>
							</span>
						</div>
					</div>
				</div>
			<div class="form-group" style="height: 50px;">
				<label class="col-sm-2 control-label"  style="width: 10%;"> 统计方式： </label>
				<div class="col-sm-4">
					<label class="radio-inline"> <input type="radio" id="emp"
						name="ec" onclick="to_change()" value="员工" /> 按员工 </label> <label
						class="radio-inline"> 
						<input type="radio" id="cus" name="ec" onclick="to_change()" value="单位" /> 按单位 </label>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="col-md-9">
				<!-- <button class="btn btn-danger">员工筛选</button> -->
			</div>
			<div class="col-md-3">
				<!-- <button class="btn btn-primary" >导出</button> -->
				<c:if test="${ec=='员工' }">
					<button class="btn btn-warning" onclick="toExport_per()">流水</button>
				</c:if>
				<c:if test="${ec=='单位' }">
					<button class="btn btn-warning" onclick="toExport_com()">流水</button>
				</c:if>
				<c:if test="${ec eq '类型' }">
					<button class="btn btn-warning" onclick="toExport_type()">流水</button>
				</c:if>
				<button class="btn btn-info" onclick="toDistribution()">查看分布</button>
				<!-- <button class="btn btn-success" onclick="toDetail()">查看明细</button> -->
			</div>
		</div>
		<div class="col-md-12">
			<div class="table-responsive table-lists_con">
				<table class="table_title table table-primary table-hover mb30">
					<thead style="position: relative">

						<tr id="search_tr">				
							<th id='search_th'>
							<div id="div_per" class="dropdown">
							<c:if test="${ec=='员工' }">姓名</c:if>
							<c:if test="${ec=='单位' }">单位</c:if>
							<c:if test="${ec eq '类型' }">类型</c:if>
							<button class="btn btn-danger dropdown-toggle btn-xs"
									type="button" style="margin-top:0px;margin-left:0px;position: relative"
									id="perdropdownMenu" data-toggle="dropdown">
									<c:if test="${ec=='员工' }">员工筛选</c:if>
									<c:if test="${ec=='单位' }">单位筛选</c:if>
									<c:if test="${ec eq '类型' }">类型筛选</c:if>
							</button>
									<%-- <c:if test="${ec=='员工' }"><a href="javascript:count_per();" data-toggle="tooltip" title="员工筛选" class="tooltips btn-primary"
								data-original-title="员工筛选"> <i class="glyphicon glyphicon-search"></i></a></c:if>
								<c:if test="${ec=='单位' }"><a href="javascript:count_com();" data-toggle="tooltip" title="单位筛选" class="tooltips btn-primary"
								data-original-title="单位筛选"> <i class="glyphicon glyphicon-search"></i></a></c:if> --%>
									<ul class="dropdown-menu" role="menu" id="per_ul"
									aria-labelledby="perdropdownMenu" style="padding-right:5px;">
									<li role="presentation">
										<label class="radio-inline pull-right">
											<c:if test="${ec=='员工' }"><a href="javascript:count_per();" class="btn btn-primary">确定</a></c:if> 
											<c:if test="${ec=='单位' }"><a href="javascript:count_com();" class="btn btn-primary">确定</a></c:if> 
											<c:if test="${ec eq '类型' }"><a href="javascript:count_type();" class="btn btn-primary">确定</a></c:if> 
										</label>
									</li>
									</ul>


								</div>
							</th>

							<c:if test="${ec=='员工' }">
								<c:forEach var="dictionary" items="${listdic_per }"
									varStatus="index">
									<th id="th_${dictionary.id }"><a href="javascript:dicdetailper('${dictionary.id }');"
										class="btn-primary">${dictionary.dicname }</a></th>
								</c:forEach>
							</c:if>
							<c:if test="${ec=='单位' }">
								<c:forEach var="dictionary" items="${listdic_com }"
									varStatus="index">
									<th id="th_${dictionary.id }"><a href="javascript:dicdetailcom('${dictionary.id }');"
										class="btn-primary">${dictionary.dicname }</a></th>
								</c:forEach>
							</c:if>
							<%-- <div class="pull_btn dropdown"
								style="position: absolute;right:15px;top:2px;">
								<button class="btn btn-danger dropdown-toggle btn-xs"
									type="button" style="margin-top:5px;margin-right:15px; position: relative"
									id="dropdownMenu1" data-toggle="dropdown">类型</button>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1" style="padding-left:5px; position: absolute;left:-130px;">
								<li role="presentation"><label class="radio-inline pull-right">
											<c:if test="${ec=='员工' }"><a href="javascript:count_per();" class="btn btn-primary">确定</a></c:if> 
											<c:if test="${ec=='单位' }"><a href="javascript:count_com();" class="btn btn-primary">确定</a></c:if> </label></li>
									<li role="presentation"><label class="radio-inline">
											<input type="checkbox" id="checkAll" /> 全部 </label></li>
									<c:if test="${ec=='员工' }">
										<c:forEach var="dictionary" items="${listdic_per }" varStatus="index">
											<li role="presentation"><label class="radio-inline">
													<input type="checkbox" name="dicnames"
													value="${dictionary.dicname }" />${dictionary.dicname } </label></li>
										</c:forEach>
									</c:if>
									<c:if test="${ec=='单位' }">
										<c:forEach var="dictionary" items="${listdic_com }"
											varStatus="index">
											<li role="presentation"><label class="radio-inline">
													<input type="checkbox" name="dicnames"
													value="${dictionary.dicname }" />${dictionary.dicname } </label></li>
										</c:forEach>
									</c:if>
								</ul>
							</div> --%>

						</tr>

					</thead>
					<tbody id="tbody_id">

					</tbody>
				</table>
			</div>
		</div>
	</div>
	</div>
</div>
	<!-- 统计事件详情 -->

	<%-- <form id="listForm" name="listForm" action="" method="post">
		<div class="table-responsive">
			<table class="table table-primary mb30 ">
				<thead>
					<tr>
						<th><input type="checkbox" id="checkAll" class="allCheck" />
						</th>
						<th>主题</th>
						<th>事件摘要</th>
						<th>事件标签</th>
						<th>创建人</th>
						<th>事件地址</th>
						<th>经度</th>
						<th>纬度</th>
						<th>客户</th>
						<th>类型</th>
						<th>事件所属用户</th>
						<th>联系人</th>
						<th>参与人</th>
						<th>附件列表</th>
						<th>创建时间</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="event" items="${persionEvents}" varStatus="index">
						<tr id="tr_${event.id}">
							<td><input type="checkbox" name="ids" id="checkItem"
								value="${event.id}" />
							</td>
							<td>${event.eventsubject }</td>
							<td>${event.eventsummary }</td>
							<td>${event.eventtags }</td>
							<td>${event.creator.pername}</td>
							<td>${event.eventaddr }</td>
							<td>${event.lng }</td>
							<td>${event.lat}</td>
							<td>${event.company.pubname }</td>
							<td>${event.persionType }</td>
							<td>${event.peopleid }</td>
							<td>
								<c:forEach items="${event.contactsSet }" varStatus="contacts">
									<span style="cursor: pointer;"
												onclick="showContacts('${contacts.id}')">${contacts.conname}</span>&nbsp;&nbsp;
								</c:forEach>
							
							</td>
							<td>
								<c:forEach var="people" items="${event.joinSet}">
									<span style="cursor: pointer;"
												onclick="showContacts('${people.id }')">${people.pername }</span>&nbsp;&nbsp;
								</c:forEach>
							
							</td>
							<td>
								<c:forEach items="${event.attachmentsSet}" var="attachment">
									<span style="cursor: pointer;"
												onclick="showContacts('')">${attachment.attname}</span>&nbsp;&nbsp;
								</c:forEach>
							
							</td>
							<td>${event.createDate }</td>
							<td class="table-action-hide"><a href="javascript:void(0)"
								data-toggle="tooltip" title="" class="delete-row tooltips"
								data-original-title="分享"> <span
									class="glyphicon glyphicon-share-alt"></span> </a> &nbsp; <a
								href="javascript:void(0)" data-toggle="tooltip" title=""
								class="tooltips" data-original-title="编辑"> <i
									class="fa fa-pencil"></i> </a> &nbsp; <a
								href="javascript:deleteCompany('${company.id}');"
								data-toggle="tooltip" title="" class="tooltips"
								data-original-title="删除"> <i class="fa fa-trash-o"></i> </a></td>
						</tr>

					</c:forEach>
				</tbody>
			</table>
		</div>
	</form>
	 <jsp:include page="pager.jsp" />  --%>
	<script>

	$(document).ready(function() {
		$("#checkAll").attr("checked",true);
		$("input[name=dicnames]").attr("checked",true);
		$("#checkAll").click(function(){
			if ($(this).attr("checked") == "checked") {
				$("input[name=dicnames]").attr("checked",true);
			} else {
				$("input[name=dicnames]").attr("checked",false);
			}
		});
		if (${ec =='员工'}) {
			$("#emp").attr("checked", true);
			selectAllper();
		}
		if (${ec == '单位'}) {
			$("#cus").attr("checked", true);
			selectAllcom();
		}
		if (${ec == '类型'}) {
			$("#type").attr("checked", true);
			selectAlltype();
		}
		

		$(".dropdown-menu").mouseover(function(){
			$("#dropdownMenu1").removeAttr("data-toggle");
			$("#perdropdownMenu").removeAttr("data-toggle");
			
			
		});
		$(".dropdown-menu").mouseout(function(){
			$("#dropdownMenu1").attr("data-toggle","dropdown");
			$("#perdropdownMenu").attr("data-toggle","dropdown");

		});
	}); 
	//按员工 按单位 按类型
	function to_change() {
		var dics="";
		var dicnames = document.getElementsByName("dicnames");
		for(var i=0;i<dicnames.length;i++){
			if(dicnames[i].checked){
				dics += dicnames[i].value + ",";
			}
		}
		if ($("input[name='ec']").get(0).checked) {
			//alert($("#emp").checked);
			
			location.href = "<%=basePath%>count/total.do?dicnames="+dics+"&ec=员工";
        }
        if($("input[name='ec']").get(1).checked){
        	location.href="<%=basePath%>count/total.do?dicnames="+dics+"&ec=单位";
        }
        if($("input[name='ec']").get(2).checked){
        	location.href="<%=basePath%>count/total.do?dicnames="+dics+"&ec=类型";
        }
    }
	
	//得到所有用户名及ID
	function selectAllper(){	
		$.ajax({
			url:'<%=basePath%>count/selectAllper.do',
			dataType:"json",
			type:"POST",
			success:function(data){
				$.each(data,function(i,persion){
					$("#per_ul").append("<li role='presentation'><label class='radio-inline'><input type='checkbox' name='perids' value='"+persion.id+"' />"+persion.pername+"</label></li>");		
				});					
			},
			error:function(x){
				alert(x.status);
			}
		});
	}
	//得到所有类型
	function selectAlltype(){	
		$.ajax({
			url:'<%=basePath%>count/selectAlltype.do',
			dataType:"json",
			type:"POST",
			success:function(data){
				$.each(data,function(i,type){
					$("#per_ul").append("<li role='presentation'><label class='radio-inline'><input type='checkbox' name='types' value='"+type.id+"' />"+type.dicname+"</label></li>");		
				});					
			},
			error:function(x){
				alert(x.status);
			}
		});
	}
	
	//得到所有单位名及ID
	function selectAllcom(){	
		$.ajax({
			url:'<%=basePath%>company/selectAllcmp.do',
			dataType:"json",
			type:"POST",
			success:function(data){
				$.each(data,function(i,company){
					$("#per_ul").append("<li role='presentation'><label class='radio-inline'><input type='checkbox' name='comids' value='"+company.id+"' />"+company.pubname+"</label></li>");		
				});					
			},
			error:function(x){
				alert(x.status);
			}
		});
	}
	
	//给选中的用户做统计请求
	function count_per(){
		var names = document.getElementsByName("perids");
		var dicnames = document.getElementsByName("dicnames");
		var keywords = document.getElementsByName("keyword");
		var ids = "";
		var dics ="";
		var keyword ="";
			for(var i=0; i<names.length; i++){
				if(names[i].checked){

					 ids += names[i].value +",";
				}
			}
			for(var i=0;i<dicnames.length;i++){
				if(dicnames[i].checked){
					dics += dicnames[i].value + ",";
				}
			}
			for(var i =0;i<keywords.length;i++){
				keyword += keywords[i].value +",";
			}
			 if(ids!=""){
				$.ajax({
					type: 'POST',
					url: '<%=basePath%>count/selectper.do',
					data : {'ids' : ids,'dicnames' :dics,'keyword':keyword},
					dataType : 'json',
					success : function(obj) {
						var data= obj.result;
						var total = obj.total;
						var isdelete = obj.isdelete; //没勾选事件
						var notdelete = obj.notdelete;//已勾选事件
					/* if(isdelete.length != 0){ */
							for(var i=0;i<isdelete.length;i++){
								$("#th_"+isdelete[i].id).remove();
							}
							for(var i=0;i<notdelete.length;i++){
								$("#th_"+notdelete[i].id).remove();
							}
					/* }  */
						for(var i=0;i<notdelete.length;i++){
							$("#search_tr").append("<th id='th_"+notdelete[i].id+"'><a href='javascript:dicdetailper(\""+notdelete[i].id+"\");' class='btn-primary'>"+notdelete[i].dicname+"</a></th>");
						}
						$("#tbody_id > *").remove();
						var len = 0;
						$.each(data,function(){
						 	var list = this;
						 	len = list.length;
						 	$("#tbody_id").append("<tr id='tr_"+list[0]+"'><th>"+list[0]+"</th></tr>");
						 	for(var i=1;i<len;i++){
							 	$("<th>"+list[i]+"次</th>").appendTo("#tr_"+list[0]);
						 	}						 
						});
						$("#tbody_id").append("<tr id='tr_合计'><th>合计</th></tr>");
						for(var i=0;i<total.length;i++){
							$("<th>"+total[i]+"次</th>").appendTo("#tr_合计");
						}
					},
					error : function(e) {
						console.log(e);
				}
			});
			 }else{
				alert("请在员工筛选处选择员工!");
			} 
	}
	
	//给选中的单位做统计请求
	function count_com(){
		var names = document.getElementsByName("comids");
		var dicnames = document.getElementsByName("dicnames");
		
		var dics ="";

		var ids = "";
			for(var i=0; i<names.length; i++){
				if(names[i].checked){

					 ids += names[i].value +",";
				}
			}
			for(var i=0;i<dicnames.length;i++){
				if(dicnames[i].checked){
					dics += dicnames[i].value + ",";
				}
			}
			if(ids!=""){
				$.ajax({
					type: 'POST',
					url: '<%=basePath%>count/selectcom.do',
					data : {'ids' : ids,'dicnames' :dics},
					dataType : 'json',
					success : function(obj) {
						var data= obj.result;
						var total = obj.total;
					
						var isdelete = obj.isdelete; //没勾选事件
						var notdelete = obj.notdelete;//已勾选事件
					/* if(isdelete.length != 0){ */
						for(var i=0;i<isdelete.length;i++){
							$("#th_"+isdelete[i].id).remove();
						}
						for(var i=0;i<notdelete.length;i++){
							$("#th_"+notdelete[i].id).remove();
						}
					/* }  */
						for(var i=0;i<notdelete.length;i++){
						$("#search_tr").append("<th id='th_"+notdelete[i].id+"'><a href='javascript:dicdetailcom(\""+notdelete[i].id+"\");' class='btn-primary'>"+notdelete[i].dicname+"</a></th>");
						}
						$("#tbody_id > *").remove();
						var len = 0;
						$.each(data,function(){
						 	var list = this;
						 	len = list.length;
						 	$("#tbody_id").append("<tr id='tr_"+list[0]+"'><th>"+list[0]+"</th></tr>");
						 	for(var i=1;i<len;i++){
								 $("<th>"+list[i]+"次</th>").appendTo("#tr_"+list[0]);
						 	}						 
						});
						$("#tbody_id").append("<tr id='tr_合计'><th>合计</th></tr>");
						for(var i=0;i<total.length;i++){
							$("<th>"+total[i]+"次</th>").appendTo("#tr_合计");
						}
					},
					error : function(e) {
						console.log(e);
				}
			});
			}else{
				alert("请在单位筛选处选择要查询的单位!");
			}
	}
	
	function dicdetailper(id){
		
		var names = document.getElementsByName("perids");
		var pids = "";
			for(var i=0; i<names.length; i++){
				if(names[i].checked){

					 pids += names[i].value +",";
				}
			}
		
		location.href="<%=basePath%>count/detail.do?pids="+pids+"&dicid="+id+"&ec=员工";
	}
	function dicdetailcom(id){
		
		var names = document.getElementsByName("comids");
		var pids = "";
			for(var i=0; i<names.length; i++){
				if(names[i].checked){

					 pids += names[i].value +",";
				}
			}
		
		location.href="<%=basePath%>count/detail.do?pids=" + pids + "&dicid="+ id + "&ec=单位";
	}
	
	//时间点击选择配置
	var start = {
		    elem: '#start',
		    format: 'YYYY-MM-DD',
		    istime: true,
		    istoday: false,
		    choose: function(datas){
		         end.min = datas; //开始日选好后，重置结束日的最小日期
		         end.start = datas //将结束日的初始值设定为开始日
		    }
		};
		var end = {
		    elem: '#end',
		    format: 'YYYY-MM-DD',
		    istime: true,
		    istoday: false,
		    choose: function(datas){
		        start.max = datas; //结束日选好后，重置开始日的最大日期
		    }
		};
		laydate(start);
		laydate(end);
</script>
</body>
</html>










