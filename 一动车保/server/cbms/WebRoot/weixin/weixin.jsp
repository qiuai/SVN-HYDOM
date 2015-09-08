<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ page import="com.alipay.config.*"%>
<%@ page import="com.alipay.util.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.util.Properties"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/weixin/qrcode/jquery.qrcode.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/weixin/qrcode/qrcode.js"></script>
</head>
<body>
	<div id="qrcodeTable"></div>
	<%
		String value = request.getParameter("render");
	%>
	<script>
		$(function(){
			String value = "<%= value%>";
			initQrcode(value);
		});
		function getPost(){
			var url = "http://192.168.0.61:8080/cbms/web/pay/wenxin/payOrder";
			var data = {
					
			};
			$.post(url,data,function(result){
				if(result.status == "success"){
					initQrcode(result.message);
				}
			},"json");
		}
		//jQuery('#qrcode').qrcode("this plugin is great");
		function initQrcode(value){
			jQuery('#qrcodeTable').qrcode({
				render : "table",
				text : value
			});
		}
	</script>
</body>
</html>
