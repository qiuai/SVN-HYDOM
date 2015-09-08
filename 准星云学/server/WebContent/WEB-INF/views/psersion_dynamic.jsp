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
<style type="">
.well {
	height: 50px;
	margin-top: -12px;
}

.col-ms-12 {
	margin-top: -26px;
}

#col-sm-1 {
	width: 91px;
	margin-top: 6px;
}

div#col-sm-4 {
	margin-left: -23px;
	width: 21.333333%;
}
.wellDIV{
	width:15px;height:34px;line-height:34px; float:left;
}
</style>
<title>首页列表</title>
<jsp:include page="include/common.jsp" />
<link rel="stylesheet" href="<%=basePath%>bootstrap/css/bootstrap.min.css" media="screen" /> 
<link rel="stylesheet" href="<%=basePath%>bootstrap/css/jquery.gritter.css" />
<link rel="stylesheet" href="<%=basePath%>res/font/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=basePath%>res/css/head.css" media="screen" />
<link rel="stylesheet" href="<%=basePath%>res/font/css/font-style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/css/select2.css" />
<link rel="stylesheet" media="screen" type="text/css" href="<%=basePath%>res/zoomimage/css/layout.css" />
<link rel="stylesheet" media="screen" type="text/css" href="<%=basePath%>res/zoomimage/css/zoomimage.css" />
<link rel="stylesheet" media="screen" type="text/css" href="<%=basePath%>res/zoomimage/css/custom.css" />

<script type="text/javascript" src="<%=basePath%>res/js/list.js" ></script>
<script type="text/javascript" src="<%=basePath%>res/js/lightbox.js" ></script>
<script type="text/javascript" src="<%=basePath%>res/js/jquery.scrollfollow.js" ></script>
<script type="text/javascript" src="<%=basePath%>res/js/laydate/laydate.js" ></script>

<script type="text/javascript" src="<%=basePath%>res/zoomimage/js/eye.js"></script>
<script type="text/javascript" src="<%=basePath%>res/zoomimage/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>res/zoomimage/js/layout.js"></script>
<script type="text/javascript" src="<%=basePath%>res/zoomimage/js/utils.js"></script>
<script type="text/javascript" src="<%=basePath%>res/zoomimage/js/zoomimage.js"></script>

<script type="text/javascript">
		/**
		 * 点击加载更多状态切换.
		 */
		var but_count=1;
		 function loadmoredate(){
	    	  but_count++;
	    	  var words="";
	    	  keywordss= document.getElementsByName("keyword");
	    	  for ( var i = 0; i < keywordss.length; i++) {
				words+=keywordss[i].value+",";
			  }
		      $("#loadmore").replaceWith(" <button class='btn btn-primary btn-block' id='loadimg'><img alt='' src='<%=basePath%>bootstrap/images/loaders/loader5.gif'></button>");
		      $("#loadimg").remove();
			  $("#tr_"+but_count).load("<%=basePath%>event/dynamic1.do",{
					"pageNumber":but_count,
					"keyword":words
			  });
		 }
		 /**
		 * 编辑事件.
		 */
		function editEvent(eventId) {
			
		}
		/**
		 * 删除事件.
		 */
		function deleteEvent(id) {
			if (confirm("你确定删除此条记录吗？")) {
				$.ajax({
					type:'POST',
					url:'<%=basePath%>event/delete.do',
					data : {
						'id' : id
					},
					dataType : 'json',
					success : function(data) {
						$("#tr_" + id).remove();
					}
					
				});
			}
		} 
		
		// 个人事件
        function getEvent() {
        	location.href = "<%=basePath%>event/dynamic.do?activeEvent=1";
        }
		// 其他事件
        function getOtherEvent() {
        	location.href = "<%=basePath%>event/dynamic.do?activeEvent=2";
        }
		
		function showContacts(ConId) {
			location.href = "<%=basePath%>event/listc.do?ConId="+ConId;
		}
		/**
		 * 点击加载更多状态切换.
		 */
		 var but_count=1;
		 function loadmoredate(){
		    	  but_count++;
		    	  var words="";
		    	  keywordss= document.getElementsByName("keyword");
		    	  for ( var i = 0; i < keywordss.length; i++) {
				words+=keywordss[i].value+",";
				}
		           $("#loadmore").replaceWith(" <button class='btn btn-primary btn-block' id='loadimg'><img alt='' src='<%=basePath%>bootstrap/images/loaders/loader5.gif'></button>");
		       	$("#loadimg").remove();
				$("#tr_"+but_count).load(
						"<%=basePath%>event/dynamic1.do",{
						"pageNumber":but_count,
						"keyword":words,
<%--						callback : function(responseText,textStatus,XMLHttpRequest){--%>
<%--							      $("#loadimg").remove();--%>
<%--							      alert("加载成功")--%>
<%--						}--%>
				});
		 }
		 
		 //处理图片放大
		 $("a.customGal").zoomimage({
				controlsTrigger: 'mouseover',
				className: 'custom',
				shadow: 40,
				controls: false,
				opacity: 1,
				beforeZoomIn: function(boxID) {
					$('#' + boxID)
						.find('img')
						.css('opacity', 0)
						.animate(
							{'opacity':1},
							{ duration: 500, queue: false }
						);
				},
			});
</script>
 <%-- 此处引用js必不可少，指定下面的$来自于jquery-1.11.1.min.js--%>
 <script src="<%=basePath%>bootstrap/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
			/**
			 * 判断时间是否相等进行显示.
			*/
		var names;
		$(document).ready(function(){
				names= document.getElementsByName("createDate");
				for(var i=0; i<names.length; i++){
					$("#"+names[i].value).show();
			}
		});

</script>
</head>
<body>
<jsp:include page="include/head.jsp" />
<div class="complexBox">
	<!-- 个人事件/其他事件 -->
	<jsp:include page="include/left.jsp" />
	<div class="customer">
		<div class="panel panel-primary-head">
			<ul class="nav nav-tabs nav-line" style="height: 42px;">
				<li class="active" onclick="getEvent()" id="event"><a href="#following"
					data-toggle="tab" style="color: #555;"><strong>本人事件(<%=request.getAttribute("totalCount1") %>)</strong> </a>
				</li>
				<li class="" onclick="getOtherEvent()" id="otherEvent"><a href="#following"
					data-toggle="tab" style="color: #555;"><strong>分享事件(<%=request.getAttribute("totalCount2") %>)</strong> </a>
				</li>
			</ul>
			<a href="<%=basePath%>event/goadd.do" style="float:right; margin-right: 53px;margin-top: -27px;">新建事件&gt;&gt;</a>
		</div>
		
		<form id="listForm" name="listForm"	action="<%=basePath%>event/dynamic.do" method="post">
		<%--条件搜索	--%>
		<div class="well">
			<div class="col-ms-12">
				<div class="form-group">
					<label class="col-sm-1 control-label" id="col-sm-1"> 关键字： </label>
					<div class="col-sm-4" id="col-sm-4">
						<input class="form-control" type="text" name="keyword"
							placeholder="请输入关键字" value="${keywordj}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-1 control-label" id="col-sm-1"
						style="margin-left: 5px;"> 日期： </label>
					<div class="col-sm-2" id="col-sm-2"
						style="margin-left: -35px;width: 270px;">
						<div class="input-group">
							<input type="text" class="form-control hasDatepicker"
								placeholder="yyyy-mm-dd" id="datepicker" name="keyword" value="${date1 }"
								onclick="laydate()"> <span class="input-group-addon"><i
								class="glyphicon glyphicon-calendar"></i> </span>
						</div>
					</div>
					<div class=""
						style="width:2px;margin-left:-3px;height:34px;line-height:34px; float:left;">至</div>
					<div class="col-sm-2" id="col-sm-2"
						style="margin-left: 8px;width: 270px;">
						<div class="input-group">
							<input type="text" class="form-control hasDatepicker"
								placeholder="yyyy-mm-dd" id="datepicker" name="keyword" value="${date2 }"
								onclick="laydate()"> <span class="input-group-addon"><i
								class="glyphicon glyphicon-calendar"></i> </span>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="btn-list">
					<button class="btn btn-primary btn-metro" id="searchButton"
						style="margin-left: 85px;">搜索</button>
					<%--	 style="margin-left: 60px;"              --%>
				</div>
			</div>
		</div>
		<c:forEach var="event" items="${pager.list}">
			<div class="page-content" id="tr_${event.id}">
				<div
					style="width: 40px;height: 40px;text-align: center;line-height: 40px;float: left;color: #fff;font-size: 14px;font-weight: bold;
						font-family: "寰蒋闆呴粦";border-radius: 40px;margin-top: 8px;">
					<div class="infoTime"
						id="<fmt:formatDate value="${event.createDate }" pattern="yy-MM-dd"/>"style="display: none;">
						<p style="margin-top: -6px;">
							<fmt:formatDate value="${event.createDate }" pattern="yy-MM-dd" /><br />
						</p>
				<label for="" style="margin-top: -37px;"><fmt:formatDate value="${event.createDate }" pattern="HH:mm" /> </label> 
						<input class="createDate" name="createDate" type="hidden"
							value="<fmt:formatDate value="${event.createDate }" pattern="yy-MM-dd" />" />
					</div>
				</div>
				<div class="info-mainBox">
					<div class="setp">
						<p>
<%--					<div class="zdydaohang">   <span style="cursor: pointer;"class="company" >--%>
							<label > <%=request.getSession().getAttribute("userName")%></label>
							<label style="padding-top:20px;margin-left: 10px;">${event.eventsubject}</label>
							<label style="margin-left: 10px;"><a href="<%=basePath %>event/list.do?companyId=${event.company.id}"><span style="cursor: pointer;"class="company" >${event.company.pubname}</span> </a></label> 
						    <label style="margin-left: 35px;"> <a href="javascript:void(0);"style="text-decoration: none;cursor: default;">${event.eventtags}</a></label>
							<label style="float:right; margin-right: 175px;margin-top: 18px;">${event.eventaddr }</label>
							<label style=" margin-left: 88%;margin-top: -19px;">
								<a href="javascript:editEvent('${event.id }');">编辑</a> 
								<a	href="<%=basePath%>event/shareview.do?id=${event.id }"	data-toggle="modal" data-target=".add-panel" class="tooltips">分享</a>
								<a href="javascript:deleteEvent('${event.id }');">作废</a> 
							</label>
						</p>
	<%--			</div>--%>
						<div class="talk">
							<ul>
								<li><c:forEach var="people" items="${event.joinSet}">
										<a href="#" title="${people.pername }" data-toggle="popover"
											data-container="body" data-placement="right"
											data-original-title="Right"
											data-ajaxload="<%=basePath %>persion/popover.do?id=${people.id}">@${people.pername}</a>&nbsp;&nbsp;
								</c:forEach></li>
							<%--	onclick="showCompany('${event.company.id}')"			--%>
								<li>
									<div style="clear:both;"></div></li>
								<li ><label for=""> <c:forEach var="contacts"
											items="${event.contactsSet }">
											<span style="cursor: pointer;"
												onclick="showContacts('${contacts.id}')">${contacts.conname}</span>&nbsp;&nbsp;
									</c:forEach> </label> 
									<div style="clear:both;"></div></li>
								<li class="talkContent" style="width: 100%">
									<p>${event.eventsummary }</p> 
									<%--			处理图片放大查看						--%>
										<ul class="navigationTabs">
											<a href="#about" rel="about"></a>
										</ul>
									<div class="img">
										<c:forEach items="${event.attachmentsSet}" var="attachment">
											<%--										<p>${attachment.bigpath }</p>--%>
											<div style="margin-left: 20px;">
												<%--		文件类型(0:其它 1:图片 2:文档)					--%>
												<c:if test="${attachment.filetype==0 }">
													<a href="<%=basePath %>${attachment.sourcepath }"><img
														src="<%=basePath %>res/images/psersion_dynamic_other.png"
														alt="${attachment.attname}" height="50px" width="50px"
														title="${attachment.attname}" /> </a>
												</c:if>
												<c:if test="${attachment.filetype==1 }">

													<a href="<%=basePath %>${attachment.bigpath }"
														title="${attachment.attname}" class="customGal"	><img
														src="<%=basePath %>${attachment.smaillpath }"
														alt="${attachment.attname}" height="50px" width="50px"
														title="点击查看大图" /> </a>
												</c:if>
												<%-- onMouseOut="toolTip()" onMouseOver="toolTip('<img src=\'<%=basePath %>${attachment.bigpath }\'>')"--%>

												<c:if test="${attachment.filetype==2 }">
													<a href="<%=basePath %>${attachment.sourcepath }"><img
														src="<%=basePath %>res/images/psersion_dynamic_file.png"
														alt="${attachment.attname}" height="50px" width="50px"
														title="${attachment.attname}" />
													</a>
												</c:if>
											</div>
										</c:forEach>
									</div></li>
<%--								<li><label for=""><fmt:formatDate--%>
<%--											value="${event.createDate }" pattern="HH:mm" /> </label> --%>
<%--									<div style="clear:both;"></div>--%>
<%--								</li>--%>
							</ul>
						</div>
					</div>
					<div style="clear:both;"></div>
				</div>

			</div>
		</c:forEach>
		<div class="more" id="tr_2"></div>
		<!-- 添加Panel -->
		<div class="modal fade add-panel" id="inputDialog" tabindex="-1"
			role="dialog" style="display: none;" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content" style="height: 82px;"></div>
			</div>
		</div>
		<div>
			<div>
				<%--	判断从数据库查询出来的集合是否为空	   onclick="loadmoredate();"	--%>
				<c:if test="${!empty pager.list && (pager_list_size==pageSize)}">
					<p>
						<button id="loadmore" onclick="loadmoredate();"
							class="btn btn-primary btn-block">点击加载更多</button>
					</p>
				</c:if>
			</div>
		</div>

	</form>
	</div>
</div>
</body>
</html>