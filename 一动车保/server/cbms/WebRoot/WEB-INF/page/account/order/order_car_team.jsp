<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

 <div class="table-responsive">
       <table id="listTable" class="table table-info" >
       	<thead>
			<tr>
				<th class="check">
					<!-- <input type="checkbox" id="selectAll"/> -->
				</th>
				<th>车队负责人</th>
                <th>负责人电话</th>
                <th>备注</th>
                <th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entry" items="${pageView.records }">
				<tr id="tr_${entry.id}">
	          	     <td><input type="radio" name="teamIds" value="${entry.id}"></td>
	           		 <td>${entry.headMember}</td>
	           		 <td>${entry.headPhone}</td>
	           		 <td>${entry.remark}</td>
	           		 <td><a href="${pageContext.request.contextPath}/manage/carTeam/edit?id=${entry.id}">修改</a></td>
	          	</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<jsp:include page="fenye.jsp" />