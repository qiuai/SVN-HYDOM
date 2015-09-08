<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hydom.util.WebUtil"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("uname",WebUtil.getCookieValueByName(request,"username"));
	request.setAttribute("pwd",WebUtil.getCookieValueByName(request,"password"));
	request.setAttribute("sign",WebUtil.getCookieValueByName(request,"rememberMe"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="cn">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>摇钱数 登录</title>

        <link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
		<style type="text/css">
			.errorStyle{
				font-size: 13px;
				color: red;
			}
		</style>
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->
    </head>

    <body class="signin">
        <section>
            <div class="panel panel-signin">
                <div class="panel-body">
                    <div class="logo text-center">
                        <img src="${pageContext.request.contextPath}/resource/chain/images/logo-primary.png" alt="Chain Logo" >
                    </div>
                    <br />
                    <h4 class="text-center mb5" >后台管理系统</h4>
                    <p class="text-center">帐号登录</p>
                    
                    <div class="mb30"></div>

                    <form action="signin.action" method="post" id="myform">
                        <div><p id="ser" class="text-center" style="color: red;">${error}</p></div>
                        <input type="hidden" id="remberPwd" value="${pwd}">
                        <div class="input-group mb15">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input type="text" class="form-control" placeholder="Username" name="username" value="${uname }">
                        </div><!-- input-group -->
                        <div></div>
                        <div class="input-group mb15">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input type="password" class="form-control" placeholder="Password" name="password" value="${pwd}">
                        </div><!-- input-group -->
                        <div></div>
                        
                        <div class="clearfix">
                            <div class="pull-left">
                                <div class="ckbox ckbox-primary mt10">
                                    <input type="checkbox" id="rememberMe" value="1" name="rememberMe" <c:if test="${sign==1}"> checked="checked"</c:if> >
                                    <label for="rememberMe">Remember Me </label>
                                </div>
                            </div>
                            <div class="pull-right">
                                <button type="submit" class="btn btn-success">Sign In <i class="fa fa-angle-right ml5"></i></button>
                            </div>
                        </div>                      
                    </form>
                    
                </div><!-- panel-body -->
                <div class="panel-footer">
                    <a href="#" class="btn btn-primary btn-block">摇钱数</a>
                </div><!-- panel-footer -->
            </div><!-- panel -->
        </section>

        <script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/modernizr.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/pace.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/retina.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/jquery.cookies.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
		<!-- 验证框架 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.validate.min.js"></script>
  	    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.maskedinput-1.0.js"></script>
  	    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/md5.js"></script>
		<script type="text/javascript">
		/*页面校验*/
		$(function() {
			var validator = $("#myform").validate( {
				debug : true,
				errorClass : "errorStyle",
				submitHandler : function(form) {
					var pwdRember = document.getElementById("remberPwd").value;
					if(pwdRember!=null && ''!=pwdRember){
						form.password.value = pwdRember;
					}else{
						form.password.value = hex_md5(form.password.value);
					}
					form.submit();
				},
				rules : {
					"username" : {
						required : true
					},
					"password" : {
						required : true
					}
				},
				messages : {
					"username" : {
						required : "用户名不能为空"
					},
					"password" : {
						required : "密码不能为空"
					}
				},
				errorPlacement : function(error, element) {
					//error.appendTo(element.parent().prev("div"));
					error.appendTo(element.parent().next("div"));
				},
				highlight : function(element, errorClass) {
					$(element).addClass(errorClass);
				}
			});
			//
			$("input").click(function(){$("#ser").hide()})
		});
		</script>
    </body>
</html>
