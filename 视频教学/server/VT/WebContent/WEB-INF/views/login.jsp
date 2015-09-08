<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>后台系统登陆</title>
<meta name="Author" content="HYDOM Team" />
<meta name="Copyright" content="HYDOM" />
<link href="<%=base%>template/admin/css/login.css" rel="stylesheet" type="text/css" />
<link href="<%=base%>template/admin/css/font-awesome.min.css" rel="stylesheet" />
<script type="text/javascript" src="<%=base %>template/common/js/jquery-2.0.0.js"></script>
<script type="text/javascript" src="<%=base %>template/common/js/jquery-html5Validate.js"></script>
<script type="text/javascript">
//登录页面若在框架内，则跳出框架
if (self != top) {
	top.location = self.location;
};
$().ready(function() {	
	$("#loginForm").html5Validate(function() {
		$("#submitBtn").attr("disabled", "disabled");
		console.log("已经提交");
		this.submit();
	}, {
		validate:function() {
			var result = true;
			try {
				$("#userName").each(function() {
					if ($(this).is(":visible") && $(this).val() == "") {
						$(this).testRemind("请输入用户名!");
						result = false;
					}
				});
				$("#password").each(function() {
					if ($(this).is(":visible") && $(this).val() == "") {
						$(this).testRemind("请输入密码!");
						result = false;
					}
				});
				
			} catch (e) {
				console.log(e);
				result = false;
			}
			if(result){
				load();
			}
			return result;
		}
	});
	
	// 跳转找回密码
	$(".forget").click(function() {
		window.location.href = "<%=base %>admin/findpwd!index.action";
	});
});
</script>
</head>
<body>

<div class="main">
	<div class="login_main">
        <div class="login">
        	<div class="login_srk">
        		<form id="loginForm" name="loginForm" action="<%=base %>user/login.do" method="post" >
	            	<ul>
            			<font color="red">${error}</font>
            		</ul>
	            	<ul>
	                	<li class="login_wz"><label for="userName">登录名：</label></li>
	                    <li class="login_input">
	                    	<span class="login_user"></span>
	                        <span class="u_p_input"><input type="text" id="userName" name="username" required data-min="2" data-max="25" /></span>
	                    </li>
	                </ul>
	                <ul>
	                	<li class="login_wz"><label for="password">密码：</label></li>
	                    <li class="login_input">
	                    	<span class="login_pass"></span>
	                        <span class="u_p_input"><input type="password" id="password" name="password" required data-min="2" data-max="20" /></span>
	                    </li>
	                </ul>
	               <!--  <dl>
	                	<dt class="login_wz_1"><input name="" type="checkbox" value="" />记住我<span>请勿在公共场合使用</span></dt>
	                </dl> -->
	                <dl>
	                	<dt class="login_wz_button"><input type="submit" id="submitBtn" value="登 录"/>
	                </dl>
                </form>
            </div>
        </div>
      <!--   <div class="bottom">网站地址: www.xxx.com</div>
        <div class="bottom_01">描述描述描述  © 2014 XXX 版权所有</div> -->
	</div>
</div>
</body>
</html>