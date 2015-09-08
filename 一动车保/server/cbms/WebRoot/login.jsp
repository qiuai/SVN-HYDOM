<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script
	src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#getcode").bind("click",function(){
				$.ajax({
					type:'post',
					url:'manage/member/getcode',
					data:'mobile='+$("input[name='mobile']").val(),
					success:function(msg){
						alkert(msg)
					}
				})
			})
		})
	
	
	</script>
  </head>
  
  <body>
    <form action="manage/member/login" method="post">
    	<table>
    		<tr>
    			<td>
    				用户名<input type="text" name="mobile" />&nbsp;<input id="getcode" type="button" value="获取验证码" />
    			</td>
    		</tr>
    		<tr>
    			<td>
    				验证码<input type="text" name="code" />
    			</td>
    		</tr>
    		<tr>
    			<td>
    			${message}
    			</td>
    		</tr>
    		<tr>
    			<td>
    			验证码<input type="submit" value="提交" />
    			</td>
    		</tr>
    	</table>
    
    </form>
  </body>
</html>
