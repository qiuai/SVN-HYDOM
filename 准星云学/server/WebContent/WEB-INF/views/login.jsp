<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String basePath = request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title><spring:message code="systemName" /> - <spring:message code="page.login" /></title>

        <link href="<%=basePath %>bootstrap/css/style.default.css" rel="stylesheet">

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
                        <h1><spring:message code='systemName' /></h1>
                    </div>
                    <br />
<!--                     <h4 class="text-center mb5">Already a Member?</h4> -->
                    <p class="text-center"><spring:message code='login.notice' /></p>
                    
<!--                     <div class="mb30"></div> -->
                    
                    <form id="loginForm" action="login.do" method="post">
                    	<div class="errorForm">
							<spring:hasBindErrors name="user">
								<c:forEach var="error" items="${errors.allErrors}" varStatus="index">
									<label for="username" class="error">${error.defaultMessage}</label>
								</c:forEach>
							</spring:hasBindErrors>
                    	</div>
                        <div class="input-group mb15">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input name="username" type="text" class="form-control" placeholder="<spring:message code='login.username_placeholder' />" class="form-control error" required title="<spring:message code="message_001" arguments='用户名' />">
                        </div><!-- input-group -->
                        <div class="input-group mb15">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input name="password" type="password" class="form-control" placeholder="<spring:message code='login.password_placeholder' />" class="form-control error" required title="<spring:message code="message_001" arguments='密码' />">
                        </div><!-- input-group -->
                        
                        <div class="clearfix">
                            <div class="pull-left">
                                <div class="ckbox ckbox-primary mt10">
                                    <input type="checkbox" id="rememberMe" value="1">
                                    <label for="rememberMe"><spring:message code='login.remberme' /></label>
                                </div>
                            </div>
                            <div class="pull-right">
                                <button name="login" type="submit" class="btn btn-success"><spring:message code='login.btn_login' /> <i class="fa fa-angle-right ml5"></i></button>
                            </div>
                        </div>                      
                    </form>
                    
                </div><!-- panel-body -->
<!--                 <div class="panel-footer"> -->
<!--                     <a href="signup" class="btn btn-primary btn-block">Not yet a Member? Create Account Now</a> -->
<!--                 </div>panel-footer -->
            </div><!-- panel -->
        </section>


        <script src="<%=basePath %>bootstrap/js/jquery-1.11.1.min.js"></script>
        <script src="<%=basePath %>bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="<%=basePath %>bootstrap/js/bootstrap.min.js"></script>
        <script src="<%=basePath %>bootstrap/js/modernizr.min.js"></script>
        <script src="<%=basePath %>bootstrap/js/pace.min.js"></script>
        <script src="<%=basePath %>bootstrap/js/retina.min.js"></script>
        <script src="<%=basePath %>bootstrap/js/jquery.cookies.js"></script>
		<script src="<%=basePath %>bootstrap/js/jquery.validate.min.js"></script>

        <script src="<%=basePath %>bootstrap/js/custom.js"></script>
		<script type="text/javascript">
		    jQuery(document).ready(function() {
	            // Error Message In One Container
	            jQuery("#loginForm").validate({
	                errorLabelContainer: jQuery("#loginForm div.errorForm")
	            });
	
		    });
		</script>
    </body>
</html>
