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
        <title>角色修改</title>
        <link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/morris.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/select2.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
		<script src="${pageContext.request.contextPath}/resource/art/artDialog.js?skin=blue"></script>
        <script src="${pageContext.request.contextPath}/resource/art/plugins/iframeTools.js"></script>
        <script src="${pageContext.request.contextPath}/resource/my97/WdatePicker.js"></script>
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resource/chain/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/respond.min.js"></script>
        <![endif]-->
        <script type="text/javascript">
		function del(id){
			if (confirm('您确定要删除此信息吗')) {
			  $.get("${pageContext.request.contextPath}/manage/account/account_delete.action", 
			  {accid:id},
			  function(data) {
		      	if(data==1){
		      		$("#tr_"+id).css("display","none");
		       	}
			   });
			}
			}
        </script>
        <style type="text/css">
        	.sps{
        		border:1px solid #ded3d3;
        		padding:8px 15px;
        		margin-left: 20px;
        		margin-bottom: 10px;
        	}
        	.allsps{
        		border: 1px solid #d5d5d5;
        		padding:10px 20px;
        	}
        	.sptitle{
        		font-size: 13px;margin-left: 15px;font-weight: bold;
        	}
        </style>
    </head>

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
                                    <li>role eit</li>
                                </ul>
                                <h4>角色修改</h4>
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->
                    
                    <div class="contentpanel">
                    	<div class="row">
                    		<div class="col-md-6" style="border: 1px solid #d3d3d3;">
                    			<form id="myform" action ="${pageContext.request.contextPath}/manage/account/group_edit.action"  method="post" >
                    				<input type="hidden" name="gid" value="${group.id}"/>
                    				<div class="form-group">
                    					<label class="col-sm-2 control-label">角色名称</label>
                    					<div class="col-sm-6">
                    						<input type="text" class="form-control" name="group.name" value="${group.name}" />
	                    					<span></span>
                    					</div>
                    					<div class="col-sm-2" >
                    						<div class="form-label text-danger"><c:if test="${param.error!=null}">角色名已存在</c:if></div>
                    					</div>
                    				</div>
                    				<div class="form-group">
                    					<label class="col-sm-2 control-label">角色权限</label>
                    					<div class="allsps col-sm-10" >
											<div class="row" ><div class="col-md-12"><input type="checkbox" class="sr-only"><span class="sptitle">工单管理</span></div></div>
											<div class="sps row" >
												<c:forEach items="${sps1}" var="privilege" varStatus="s" >
													<div class="col-md-3"><input type="checkbox"  name="privilegeIds" value="${privilege.id}" <c:if test="${fn:contains(gps, privilege.id)}">checked="checked"</c:if> > ${privilege.name }</div>
												</c:forEach>
											</div>
											
											<div class="row" ><div class="col-md-12"><input type="checkbox" class="sr-only"><span class="sptitle">用户管理</span></div></div>
											<div class="sps row" >
												<c:forEach items="${sps2}" var="privilege" varStatus="s" >
													<div class="col-md-3"><input type="checkbox" name="privilegeIds" value="${privilege.id}" <c:if test="${fn:contains(gps, privilege.id)}">checked="checked"</c:if> > ${privilege.name }</div>
												</c:forEach>
											</div>
											
											<div class="row" ><div class="col-md-12"><input type="checkbox" class="sr-only"><span class="sptitle">奖品管理</span></div></div>
											<div class="sps row" >
												<c:forEach items="${sps3}" var="privilege" varStatus="s" >
													<div class="col-md-3"><input type="checkbox" name="privilegeIds" value="${privilege.id}" <c:if test="${fn:contains(gps, privilege.id)}">checked="checked"</c:if> > ${privilege.name }</div>
												</c:forEach>
											</div>
											
											<div class="row" ><div class="col-md-12"><input type="checkbox" class="sr-only"><span class="sptitle">系统管理</span></div></div>
											<div class="sps row" >
												<c:forEach items="${sps4}" var="privilege" varStatus="s" >
													<div class="col-md-3"><input type="checkbox" name="privilegeIds" value="${privilege.id}" <c:if test="${fn:contains(gps, privilege.id)}">checked="checked"</c:if> > ${privilege.name }</div>
												</c:forEach>
											</div>
											
											<div class="row" ><div class="col-md-12"><input type="checkbox" class="sr-only"><span class="sptitle">系统设置</span></div></div>
											<div class="sps row" >
												<c:forEach items="${sps5}" var="privilege" varStatus="s" >
													<div class="col-md-3"><input type="checkbox" name="privilegeIds" value="${privilege.id}" <c:if test="${fn:contains(gps, privilege.id)}">checked="checked"</c:if> > ${privilege.name }</div>
												</c:forEach>
											</div>
	                   					</div>
                    				</div>
									<div class="form-group">
										<div class="col-md-6 col-md-push-5">
											<button type="reset" class="btn btn-primary">重置</button>
											<button type="submit" class="btn btn-primary">确认</button>
										</div>
									</div>
						    </form>
                    		</div>
                    	</div>

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
  	    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/validate/privilegeGroup.js"></script>

        <script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/dashboard.js"></script>
    </body>
</html>
