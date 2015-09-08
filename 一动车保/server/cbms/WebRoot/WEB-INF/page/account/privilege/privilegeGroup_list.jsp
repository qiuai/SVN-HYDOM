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
        
        <meta name="author" content="">
        <title>角色定义</title>
        <link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/morris.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/select2.css" rel="stylesheet" />
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resource/chain/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/respond.min.js"></script>
        <![endif]-->
        <script type="text/javascript">
		function del(id){
			if (confirm('您确定要删除吗')) {
			  $.get("${pageContext.request.contextPath}/manage/account/group/delete", 
			  {gid:id},
			  function(data) {
		      	if(data==1){
		      		$("#tr_"+id).css("display","none");
		       	}else if(data==0){
					alert("系统角色，不能删除");
			    }
			   });
			}
			}
        </script>
        <style type="text/css">
        	.sps{
        		border:1px solid #ded3d3;
        		padding:8px 15px;
        		margin-left: 15px;
        		margin-bottom: 10px;
        	}
        	.allsps{
        		border: 1px solid #d5d5d5;
        		padding:10px 20px;
        	}
        	.sptitle{
        		font-size: 13px;margin-left: 15px;font-weight: bold;
        	}
        	.errorStyle {
				font-size: 13px;
				color: red;
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
                                    <li>role list</li>
                                </ul>
                                <h4>角色定义</h4>
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->
                    
                    <div class="contentpanel">
                    	<div class="row">
                    		<div class="col-sm-6 col-md-6">
                    			<form  action="${pageContext.request.contextPath}/manage/account/group/list" method="post" id="pageList"> 
		                         <s:hidden name="page" />
		                         <s:hidden name="m" />
		    					 <table class="table table-info" >
		    					 	 <thead>
									 <tr >
		                                    <th>#</th>
		                                    <th>角色名称</th>
		                                    <th>创建时间</th>
		                                    <th>操作</th>
		                              </tr>
		                              </thead>
		                           	  <c:forEach items="${pageView.records}" var="entry" varStatus="s">  
		                           	  	<tr id="tr_${entry.id}">
		                           		 <td>${s.index+1}</td> 
		                           		 <td>
		                           		 	<a class="pls" href="#" data-toggle="tooltip"  data-placement="bottom"  data-trigger="focus" 
								      		data-content="
								      		<c:forEach items="${entry.privileges}"  var="p" >
													【${p.name}】    
											</c:forEach> 
											" >${entry.name}
											</a>
		                           		 </td> 
		                           		 <td><fmt:formatDate value="${entry.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>  </td> 
		                           		 <td>
		                           		 <c:if test="${!entry.initSign}" >
		                           			 <a href='${pageContext.request.contextPath}/manage/account/group/edit?gid=${entry.id}'>修改</a>
		                           			 <a href="javascript:del('${entry.id}')">删除</a>
		                           		 </c:if>
		                           		 <c:if test="${entry.initSign}" >
		                           			 <span title="不能进行修改或删除">系统初始角色</span>
		                           		 </c:if>
		                           		 </td> 
		                           	  	</tr>
		                           	  </c:forEach>
								 </table>
								</form>
		                       	<div class="fenye"><%@ include file="/WEB-INF/page/common/fenye.jsp" %></div>
                    		</div>
                    		
                    		<div class="col-sm-6 col-md-6" style="border: 1px solid #d3d3d3;padding-top: 8px;">
                    			<form  id="myform" action ="${pageContext.request.contextPath}/manage/account/group/save"  method="post" >
                    				<div class="form-group">
                    					<label class="col-md-2 control-label">角色名称</label>
                    					<div class="col-md-5">
                    						<input type="text" class="form-control" name="name" />
                    						<span></span>
                    					</div>
                    				</div>
                    				<div class="form-group">
                    					<label class="col-md-12 control-label">角色权限</label>
                    					<div class="allsps col-md-12" >
                    						<c:forEach items="${pmap}" var="mp"> 
												<div class="row" >
													<div class="col-md-12"><input type="checkbox" class="sr-only">
														<span class="sptitle">${mp.key}</span>
													</div>
												</div>
												<div class="sps row" >
												<c:forEach items="${mp.value}" var="privilege" varStatus="s" >
													<div class="col-sm-6 col-md-4">
														  <input type="checkbox" name="privilegeIds" value="${privilege.id}" />
		                                                  <label >${privilege.name }</label>
													</div>
												</c:forEach>
												</div>
                    						</c:forEach>
	                   					</div>
                    				</div>
									<div class="form-group">
										<div class="col-md-12 text-center">
											<button id="addCate" class="btn btn-primary mr5" type="submit">提交</button>
											<button class="btn btn-dark" type="reset">重置</button> 
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
        
        <script src="${pageContext.request.contextPath}/resource/chain/js/jquery.sparkline.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/morris.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/raphael-2.1.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/bootstrap-wizard.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/select2.min.js"></script>
		
	    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
		<!-- 验证框架 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.validate.min.js"></script>
  	    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.maskedinput-1.0.js"></script>
  	    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/validate/privilegeGroup.js"></script>
		
        <script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
        <script type="text/javascript">
			$('[data-toggle="tooltip"]').popover();
		</script>
    </body>
</html>
