<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>${systemName} - 管理系统</title>
	<link rel="icon" href="favicon.ico" type="image/x-icon" />
	<jsp:include page="include.jsp"></jsp:include>
	<link href="<%=base %>template/admin/css/input.css" rel="stylesheet" type="text/css" />
	<link href="<%=base %>template/admin/css/prompt.css" rel="stylesheet" type="text/css" />
	<script>
		function loadPrompt(){
			// 提示
			var height = 0 - $('#prompt_sucess').height() - 10;
			$('#prompt_sucess').css('bottom', height);
		}
		function messageReceived(message) {
			$('#prompt_sucess').show();
			show_prompt(message);
	    }
// 		收藏提示
		function show_prompt(message) {
			$('#prompt_message').html(message);
			$("#prompt_sucess").animate({
				bottom: '10px',
			},
			1000);
		
			setTimeout('hidden_prompt()', 10000);
		}
		function hidden_prompt() {
			var height = 0 - $('#prompt_sucess').height() - 10;
			$("#prompt_sucess").animate({
				bottom: height
			},
			1000);
		}
	</script>
	</head>
</html>
