<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
        <meta name="description" content="">
        
        <meta name="author" content="">
        <title>My Profile</title>
        <link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/morris.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/select2.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
		<script src="${pageContext.request.contextPath}/resource/art/artDialog.js?skin=blue"></script>
        <script src="${pageContext.request.contextPath}/resource/art/plugins/iframeTools.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="${pageContext.request.contextPath}/resource/kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="${pageContext.request.contextPath}/resource/kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="${pageContext.request.contextPath}/resource/kindeditor/plugins/code/prettify.js"></script>
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resource/chain/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/respond.min.js"></script>
        <![endif]-->

    <body>
        <header>    
        <%@ include file="/WEB-INF/page/common/head.jsp" %>
        </header>
        
        <section>
            <div class="mainwrapper">
        	<%@ include file="/WEB-INF/page/common/left.jsp" %>
                
                <div class="mainpanel">
                    <div class="pageheader">
                        <div class="media">
                            <div class="pageicon pull-left">
                                <i class="fa  fa-list-alt"></i>
                            </div>
                            <div class="media-body">
                                <ul class="breadcrumb">
                                    <li><a href=""><i class="glyphicon glyphicon-home"></i></a></li>
                                    <li>My Profile</li>
                                </ul>
                                <h4>个人信息</h4>
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->
                    
                    <div class="contentpanel">
                    	<div class="content-m" style="width: 550px;">
                         <div>My Profile</div>
                         <div style="border-bottom: 1px solid #d5d5d5;margin-bottom: 10px;">&nbsp</div>
						      <div class="panel panel-primary">
						        <div class="panel-heading" style="padding: 10px 20px;">个人信息</div>
						        <div class="panel-body"></div>
						        	 <div class="form-group">
									    <label  class="col-sm-3 control-label">用户名</label>
									    <div class="col-sm-9">
									      <input type="text" class="form-control" value="${loginAccount.username}" disabled="disabled"  placeholder="用户名">
									   	  <span></span>
									    </div>
									  </div>
			    					 <div class="form-group">
									    <label  class="col-sm-3 control-label">密码</label>
									    <div class="col-sm-9">
									      <input type="password" class="form-control" value="${loginAccount.password}" disabled="disabled"  placeholder="密码">
									   	  <span></span>
									    </div>
									  </div>
			    					 <div class="form-group">
									    <label  class="col-sm-3 control-label">手机号</label>
									    <div class="col-sm-9">
									      <input type="text" class="form-control" value="${loginAccount.phone}" disabled="disabled" >
									   	  <span></span>
									    </div>
									  </div>
			    					 <div class="form-group">
									    <label  class="col-sm-3 control-label">昵称</label>
									    <div class="col-sm-9">
									      <input type="text" class="form-control" value="${loginAccount.nickname}" disabled="disabled">
									   	  <span></span>
									    </div>
									  </div>
			    					 <div class="form-group">
									    <label  class="col-sm-3 control-label">最近登录时间</label>
									    <div class="col-sm-9">
									      <input type="text" class="form-control" value='<fmt:formatDate value="${loginAccount.lastSigninTime}" pattern="yyyy-MM-dd HH:mm:ss"/> ' disabled="disabled">
									   	  <span></span>
									    </div>
									  </div>
			    					 <div class="form-group">
									    <label  class="col-sm-3 control-label">上次注销时间</label>
									    <div class="col-sm-9">
									      <input type="text" class="form-control" value='<fmt:formatDate value="${loginAccount.lastSignoutTime}" pattern="yyyy-MM-dd HH:mm:ss"/> ' disabled="disabled" >
									   	  <span></span>
									    </div>
									  </div>
						        <div class="panel-footer" style="padding: 10px 20px;">
										于 <small><fmt:formatDate value="${loginAccount.lastSigninTime}" pattern="yyyy-MM-dd HH:mm:ss"/></small> 
										在 <small title="登录IP：${loginAccount.lastSignIp}">${loginAccount.lastSignPosition}</small> 登录 
								    </div>
						        </div>
						    </div>
								  
						</div>  <!-- content  -->

                    <div class="bottomwrapper" >
						<%@ include file="/WEB-INF/page/common/bottom.jsp" %>
                    </div>
                    </div><!-- contentpanel -->
                </div><!-- mainpanel -->
            </div><!-- mainwrapper -->
        </section>

        <script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/modernizr.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/pace.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/retina.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/jquery.cookies.js"></script>
        
        <script src="${pageContext.request.contextPath}/resource/chain/js/flot/jquery.flot.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/flot/jquery.flot.resize.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/flot/jquery.flot.spline.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/jquery.sparkline.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/morris.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/raphael-2.1.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/bootstrap-wizard.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/select2.min.js"></script>
		
		<!-- 验证框架 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.validate.min.js"></script>
  	    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.maskedinput-1.0.js"></script>
  	    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/validate/account.js"></script>
		
        <script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
    	<script src="${pageContext.request.contextPath}/resource/chain/js/dashboard.js"></script>
		
		<script type="text/javascript">
			$('[data-toggle="tooltip"]').popover();
		</script>
    </body>
</html>
