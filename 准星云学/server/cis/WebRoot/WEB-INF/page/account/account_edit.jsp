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
        <title>系统帐号修改</title>
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
                                    <li>account edit</li>
                                </ul>
                                <h4>系统帐号修改</h4>
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->
                    
                    <div class="contentpanel">
                    	<div class="content-m" >
                         <div>帐号修改</div>
                         <div style="border-bottom: 1px solid #d5d5d5;margin-bottom: 10px;">&nbsp</div>
                         <s:form action="account_edit" id="myform" name="myform" namespace="/manage/account" method="post"  > 
	    					<s:hidden name="accid" />
	    					<div class="form-horizontal">
		    					 <div class="form-group">
								    <label  class="col-sm-3 control-label">用户名</label>
								    <div class="col-sm-9">
								      <input type="text" class="form-control" value="${account.username}" disabled="disabled" name="account.username"  placeholder="用户名">
								   	  <span></span>
								    </div>
								  </div>
		    					 <div class="form-group">
								    <label  class="col-sm-3 control-label">密码</label>
								    <div class="col-sm-9">
								      <input type="text" class="form-control" id="password" name="account.password"  placeholder="原密码已加密">
								   	  <span></span>
								    </div>
								  </div>
		    					 <div class="form-group">
								    <label  class="col-sm-3 control-label">手机号</label>
								    <div class="col-sm-9">
								      <input type="text" class="form-control" value="${account.phone}"  name="account.phone"  placeholder="手机号">
								   	  <span></span>
								    </div>
								  </div>
		    					 <div class="form-group">
								    <label  class="col-sm-3 control-label">昵称</label>
								    <div class="col-sm-9">
								      <input type="text" class="form-control" value="${account.nickname}"   name="account.nickname" placeholder="昵称">
								   	  <span></span>
								    </div>
								  </div>
	    					 	 <div class="form-group" style="border: 1px solid #d3d3dd;">
								    <label  class="col-sm-2 control-label">角色选择</label>
								    <div class="col-sm-10">
								      <c:forEach items="${groups}" var="group" varStatus="s">
								      	<div class="col-sm-6"> 
								      		<input type="checkbox" name="gids" value="${group.id}" ${fn:contains(ugs, group.id)?"checked='checked'":""}/>
								      		<a class="pls" href="#" data-toggle="tooltip"  data-placement="bottom" 
								      		data-title="${group.name}" data-trigger="focus"
								      		data-content="
								      		<c:forEach items="${group.privileges}"  var="p" >
													【${p.name}】    
											</c:forEach> 
											" >${group.name}</a>
								      	</div>
								      </c:forEach>
								    </div>
								  </div>
	    					 	<div style="line-height: 50px;text-align: center;">
	    					 		<span><input type="reset" value="重置" class="btn btn-primary"/></span>
	    					 		<span><input type="submit" value="提交" class="btn btn-primary"/></span>
	    					 	</div>
	    					</div>
    					 </s:form>
						</div>  <!-- content  -->

                    </div><!-- contentpanel -->
                    <div class="bottomwrapper" >
						<%@ include file="/WEB-INF/page/common/bottom.jsp" %>
                    </div>
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
  	    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/md5.js"></script>
  	    
		
        <script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
    	<script src="${pageContext.request.contextPath}/resource/chain/js/dashboard.js"></script>
		
		<script type="text/javascript">
			$('[data-toggle="tooltip"]').popover();
		</script>
    </body>
</html>
