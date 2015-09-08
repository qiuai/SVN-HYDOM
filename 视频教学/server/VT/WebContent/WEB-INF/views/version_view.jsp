<%@ page language="java" import="java.util.*;" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String base = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${userName} - 版本详情</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="<%=base%>template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<link href="<%=base%>template/admin/css/all_detail.css" rel="stylesheet" />
<link href="<%=base%>template/admin/css/base.css" rel="stylesheet"
	type="text/css" />
<jsp:include page="include.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=base%>template/common/js/swfupload.js"></script>
<script type="text/javascript"
	src="<%=base%>template/common/js/swfupload.queue.js"></script>
<script type="text/javascript"
	src="<%=base%>template/common/js/fileprogress.js"></script>
<script type="text/javascript"
	src="<%=base%>template/common/js/handlers.js"></script>
</head>
<body class="input">
	<div class="tongyong_main">
		<div class="map">
			<div class="pro_map">
				管理信息 > <a href="<%=base%>admin/version!list.action">版本信息</a> > 版本详情
			</div>
		</div>
		<div class="list_main">
			<div class="list_main01">
				<div class="list_main02">
					<div class="top_info">
						<div class="top_info_01">详细信息</div>
					</div>
					<div class="add_mian">
						<ul>
							<li style="background-color:#f0f5f7;"><span
								class="add_wenzi">APP类型：</span> 
									<span class="add_srk_01">
										<c:if test="${version.type == 0}">用户APP</c:if>
										<c:if test="${version.type == 1}">商家APP</c:if>
									</span>
							</li>
							<li><span class="add_wenzi">版本名称：</span> 
							<span class="add_srk_01">${version.version}</span></li>
							<li style="background-color:#f0f5f7;"><span
								class="add_wenzi">版本编号：</span> <span class="add_srk_01">${version.code}</span>
							</li>
							<li style="background-color:#f0f5f7;"><span
								class="add_wenzi">是否发布：</span> 
									<span class="add_srk_01">
										<c:if test="${version.isPublish == false}">已发布</c:if>
										<c:if test="${version.isPublish == true}">未发布</c:if>
									</span>
							</li>
							<li style="background-color:#f0f5f7;"><span
								class="add_wenzi">版本描述：</span> <span class="add_srk_01">${version.describ}</span></li>
							<li style="background-color:#f0f5f7;"><span
								class="add_wenzi">创建时间：</span> <span class="add_srk_01">
									<fmt:formatDate value="${version.createDate}"
										pattern="yyyy-MM-dd" />
							</span></li>
							<li><span class="detail_save_bg"
								onclick="window.history.back(); return false;">返回</span></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
