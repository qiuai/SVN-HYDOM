<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<title>提示信息</title>
<link href="<%=base %>template/admin/css/email_zz_suc&f.css" type="text/css" rel="stylesheet"> 
</head>
<body>
       <div class="back_main">
            <div class="back_main_neir">
               <div class="back_img">
                  <img src="<%=base %>template/admin/images/bbm_right.png" width="64" height="64"> 
               </div>
               <div class="back_wenzhi">
               		<font color="green">${message == null ? "修改成功." : message}</font>
               </div>
            </div>
       </div>             
</body>
</html>