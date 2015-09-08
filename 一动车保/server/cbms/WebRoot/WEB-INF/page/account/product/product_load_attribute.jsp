<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<style>
	
</style>
<c:if test="${!empty attribute}">
	<div class="form-group">
		<input type="hidden" name="attributeIds" value="${attribute.id }"/>
		<label class="col-sm-4 control-label">${attribute.name }</label>
		<div class="col-sm-8">
			<select name="attributeValues" style="margin-top: 8px;">
				<c:forEach var="value" items="${attribute.options }">
					<option value="${value }" <c:if test="${attributeValue eq value}">selected="selected"</c:if> >${value }</option>
				</c:forEach>
			</select>
		</div>
	</div>
</c:if>



