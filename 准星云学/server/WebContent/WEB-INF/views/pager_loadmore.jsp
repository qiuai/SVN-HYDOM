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
<body>
	<form id="listForm" name="listForm" method="post">
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
							<label for="" style=""> <%=request.getSession().getAttribute("userName")%></label>
							<label for="" style="padding-top:20px;margin-left: 10px;">${event.eventsubject}</label>
							<label for="" style="margin-left: 10px;"><a href="<%=basePath %>event/list.do?companyId=${event.company.id}"><span style="cursor: pointer;"class="company" >${event.company.pubname}</span> </a></label> 
						    <label for="" style="margin-left: 35px;"> <a href="javascript:void(0);"style="text-decoration: none;cursor: default;">${event.eventtags}</a></label>
							<label for="" style="float:right; margin-right: 175px;margin-top: 18px;">${event.eventaddr }</label>
							<c:if
										test="${event.persionType=='creator' }">
										<label style=" margin-left: 88%;margin-top: -19px;">
										 <a href="javascript:editEvent('${event.id }');">编辑</a> 
										<a	href="<%=basePath%>event/shareview.do?id=${event.id }"	data-toggle="modal" data-target=".add-panel" class="tooltips">分享</a>
											<a href="javascript:deleteEvent('${event.id }');">作废</a> </label>
							</c:if>
						</p>
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
									<p style="white-space:normal;">${event.eventsummary }</p> 
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
		<div class="more" id="tr_${pager.pageNumber+1}">
		
		</div>
			<div>
				<p>
					<%--	判断从数据库查询出来的集合是否为空		onclick="loadmoredate();"--%>
					<c:if test="${!empty pager.list && (pager_list_size==pageSize)}">
						<button id="loadmore" onclick="loadmoredate();"
							class="btn btn-primary btn-block">点击加载更多</button>
					</c:if>
				</p>
			</div>
	</form>

<script src="<%=basePath%>res/zoomimage/js/jquery.js"></script>
<script src="<%=basePath%>res/zoomimage/js/eye.js"></script>
<script	src="<%=basePath%>res/zoomimage/js/utils.js"></script>
<script	src="<%=basePath%>res/zoomimage/js/zoomimage.js"></script>
<script	src="<%=basePath%>res/zoomimage/js/layout.js"></script>
	
<script type="text/javascript">
	$(document).ready(function(){
		
		//var size = "${pager.list.size()}";
		//alert(size);
		//var hei = size*1*210;
		//parent.dyniframesize();
		
		//判断日期进行显示
		names= document.getElementsByName("createDate");
		for(var i=0; i<names.length; i++){
			$("#"+names[i].value).show();
	}
		
	//处理图片放大	
	$(".customGal").zoomimage({
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
	
	
});
</script>
</body>
</html>