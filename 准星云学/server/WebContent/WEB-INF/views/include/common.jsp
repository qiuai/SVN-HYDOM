<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String basePath = request.getContextPath() + "/";
%>
<!-- 共同css 和 js -->
<script type="text/javascript" src="<%=basePath %>bootstrap/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath %>bootstrap/js/modernizr.min.js"></script>
<script type="text/javascript" src="<%=basePath %>bootstrap/js/pace.min.js"></script>
<script type="text/javascript" src="<%=basePath %>bootstrap/js/retina.min.js"></script>
<script type="text/javascript" src="<%=basePath %>bootstrap/js/jquery.cookies.js"></script>
<script type="text/javascript" src="<%=basePath %>bootstrap/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=basePath %>bootstrap/js/jquery.gritter.min.js"></script>
<script type="text/javascript" src="<%=basePath %>bootstrap/js/custom.js"></script>
<script type="text/javascript" src="<%=basePath%>res/js/common.js" ></script>