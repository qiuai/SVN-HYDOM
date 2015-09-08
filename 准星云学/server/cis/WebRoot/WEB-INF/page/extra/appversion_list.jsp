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
        <title>app版本管理</title>
        <link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/morris.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/select2.css" rel="stylesheet" />
		<script src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
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
			if (confirm('您确定要删除吗')) {
			  $.get("${pageContext.request.contextPath}/manage/extra/appversion_delete.action", 
			  {appid:id},
			  function(data) {
		      	if(data==1){
		      		$("#tr_"+id).css("display","none");
		       	}
			   });
			}
		}
        </script>
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
                                    <li>app version</li>
                                </ul>
                                <h4>app版本管理</h4>
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->
                    
                    <div class="contentpanel">
                    	<div class="row">
                    		<div class="col-sm-6 col-md-6">
                    			<s:form action="appversion_list" namespace="/manage/extra" method="post" id="pageList"> 
		                         <s:hidden name="page" />
		                         <s:hidden name="m" />
		    					 <table class="table table-bordered table-striped" >
									 <tr >
		                                    <th>#</th>
		                                    <th>版本号</th>
		                                    <th>上传时间</th>
		                                    <th>状态</th>
		                                    <th>操作</th>
		                              </tr>
		                           	  <c:forEach items="${pageView.records}" var="entry" varStatus="s">  
		                           	  	<tr id="tr_${entry.id}">
		                           		 <td>${s.index+1}</td> 
		                           		 <td>${entry.version}</td> 
		                           		 <td><fmt:formatDate value="${entry.uploadTime}" pattern="yyyy-MM-dd HH:mm:ss"/>  </td> 
		                           		 <td>${entry.state==1?"开启":"关闭" }</td> 
		                           		 <td>
		                           		 <a href='<s:url action="appversion_editUI" namespace="/manage/extra" />?appid=${entry.id}'>修改</a>
		                           		 <a href="javascript:del('${entry.id}')">删除</a>
		                           		 <c:if test="${entry.filePath!=null}">
		                           		 <a href='<s:url action="app_download" namespace="/manage/extra" />?appid=${entry.id}'>下载</a>
		                           		 </c:if>
		                           		 </td> 
		                           	  	</tr>
		                           	  </c:forEach>
								 </table>
								</s:form>
		                       	<div class="fenye"><%@ include file="/WEB-INF/page/common/fenye.jsp" %></div>
                    		</div>
                    		
                    		<div class="col-sm-6 col-md-3" style="border: 1px solid #d3d3d3;padding-top: 8px">
                    			<form id="myform" action ="${pageContext.request.contextPath}/manage/extra/appversion_add.action" enctype="multipart/form-data" method="post" >
                    				<div class="form-group">
                    					<label class="col-md-3 control-label">版本号</label>
                    					<div class="col-md-8">
                    						<input type="text" class="form-control" name="appVersion.version" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" />
                    						<span></span>
                    					</div>
                    				</div>
                    				<div class="form-group">
                    					<label class="col-md-3 control-label">版本状态</label>
                    					<div class="col-md-8">
                    						<select name="appVersion.state" class="form-control"  >
										      	<option value="1">开启</option>
										      	<option value="0">关闭</option>
										    </select>
                    					</div>
                    				</div>
                    				<div class="form-group">
                    					<label class="col-md-3 control-label">上传文件</label>
                    					<div class="col-md-8">
								     		 <input type="file" name="app" class="form-control"> 
								     		 <span></span>
                    					</div>
                    				</div>
                    				
									<div class="form-group">
										<div class="col-md-12 text-center">
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
  	    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/validate/appversion.js"></script>

        <script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/dashboard.js"></script>
    </body>
</html>
