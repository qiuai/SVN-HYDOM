<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
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
        
        <title>系统帐号添加</title>
        <link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/morris.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/select2.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resource/css/manage.common.css" rel="stylesheet">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
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
                                    <li>user add</li>
                                </ul>
                                <h4>帐户添加</h4>
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->
                    
                    <div class="contentpanel">
                    	<div class="content-xs" >
                         <div>用户信息修改</div>
                         <div style="border-bottom: 1px solid #d5d5d5;margin-bottom: 10px;">&nbsp</div>
                         <form action="/manage/user/update" method="post"  > 
                         	<input type="hidden" name="id" value="${user.id}">
	    					<div class="form-horizontal">
		    					 <div class="form-group">
								    <label  class="col-sm-2 control-label">Email</label>
								    <div class="col-sm-4">
								      <input type="text" class="form-control" name="email" value="${user.email}"  placeholder="Email" >
								      <span class="errorStyle" id="username_error"></span>
 									  <s:hidden name="repeat" id="repeat" value=""/>
								    </div>
								    <label  class="col-sm-2 control-label">密码</label>
								    <div class="col-sm-4">
								      <input type="text" class="form-control" name="password"  value="${user.password}"  placeholder="密码">
								      <span></span>
								    </div>
								  </div>
		    					 <div class="form-group">
								    <label  class="col-sm-2 control-label">最多分享数</label>
								    <div class="col-sm-4">
								      <input type="text" class="form-control"  name="maxShare" value="${user.maxShare}"  placeholder="用户类型(填写数字)">
								      <span></span>
								    </div>
								    <label  class="col-sm-2 control-label">昵称</label>
								    <div class="col-sm-4">
								      <input type="text" class="form-control" name="nickname" value="${user.nickname}"  placeholder="昵称">
								      <span></span>
								    </div>
								  </div>
		    					 <div class="form-group">
								    <label  class="col-sm-2 control-label">最多评论数</label>
								    <div class="col-sm-4">
								      <input type="text" class="form-control"  name="maxComment" value="${user.maxComment}"  placeholder="用户类型(填写数字)">
								      <span></span>
								    </div>
								    <label  class="col-sm-2 control-label">用户类型</label>
								    <div class="col-sm-4" >
								      <select class="form-control" name="lv">
								      	<option ${user.lv==1?'selected="selected"':'' }  value="1">普通用户</option>
								      	<option ${user.lv==2?'selected="selected"':'' }  value="2">邮箱用户</option>
								      	<option ${user.lv==3?'selected="selected"':'' }  value="3">核心用户</option>
								      	<option ${user.lv==4?'selected="selected"':'' }  value="4">VIP用户</option>
								      	<option ${user.lv==5?'selected="selected"':'' }  value="5">高级用户</option>
								      </select>
								    </div>
								  </div>
		    					 <div class="form-group">
								    <label  class="col-sm-2 control-label">最大上传KB</label>
								    <div class="col-sm-4">
								      <input type="text" class="form-control"  name="maxUploadSize" value="${user.maxUploadSize}"  placeholder="用户类型(填写数字)">
								      <span></span>
								    </div>
								    <label  class="col-sm-2 control-label">用户状态</label>
								    <div class="col-sm-4">
								     <select class="form-control" name="visible">
								      	<option ${user.visible?'selected="selected"':'' }  value="true">正常</option>
								      	<option ${!user.visible?'selected="selected"':'' }  value="false">禁用</option>
								      </select>
								    </div>
								  </div>
	    					 	<div  class="col-md-12 text-center">
	    					 		<span><input type="reset" value="重置" class="btn btn-primary"/></span>
	    					 		<span><input type="submit" value="提交" class="btn btn-primary"/></span>
	    					 	</div>
	    					</div>
    					 </form>
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
	    <script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
		
		<!-- 验证框架 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.validate.min.js"></script>
  	    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.maskedinput-1.0.js"></script>
		
		<script type="text/javascript">
			$('[data-toggle="tooltip"]').popover();
		</script>
    </body>
</html>
