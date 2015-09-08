<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<a href="?locale=zh_CN">中文</a>
	<a href="?locale=en">英文</a>
	<table>
		<tr>
			<th>ID</th>
			<th><spring:message code="test.title" /><spring:message code="login.username" /> </th>
			<th><spring:message code="test.link" /></th>
		</tr>
		<c:forEach var="music" items="${list}">
		<tr>
			<td>${music.id }</td>
			<td>${music.title }</td>
			<td>${music.link }</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>