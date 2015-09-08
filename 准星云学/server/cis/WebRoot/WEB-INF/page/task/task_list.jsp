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
        <title>工单区块</title>
        <link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/morris.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/select2.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
		<script src="${pageContext.request.contextPath}/resource/art/artDialog.js?skin=blue"></script>
        <script src="${pageContext.request.contextPath}/resource/art/plugins/iframeTools.js"></script>
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resource/chain/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/respond.min.js"></script>
        <![endif]-->
        <script type="text/javascript">
    	function show(taskId) {
	   	   	 var url ="${pageContext.request.contextPath}/manage/task/task_show.action?taskId="+taskId;
	   		 art.dialog.open(url,{width:400 ,height: 500 , title: '区块笔迹',id:'task_'+taskId});
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
                                     <li><a href="${pageContext.request.contextPath}/manage/index.action"><i class="glyphicon glyphicon-home"></i></a></li>
                                     <li><a href="${pageContext.request.contextPath}/manage/task/job_list.action">工单分配</a></li>
                                     <li class="active">工单区块</li>
                                </ul>
                                <h4>工单区块表</h4>
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->
                    
                    <div class="contentpanel">
                       <s:form action="task_list" namespace="/manage/task" method="post" id="pageList"> 
                         <s:hidden name="page" />
                         <s:hidden name="m" />
                         <s:hidden name="jobid" />
    					 <table   class="table table-bordered table-striped">
							 <tr>
                                    <th>#</th>
                                    <th>行号</th>
                                    <th>行内号</th>
                                    <th>已分配次数</th>
                                    <th>分配上限</th>
                                    <th>正确比例/实际比例</th>
                                    <th>分配时间</th>
                                    <th>完成时间</th>
                                    <th>超时时间</th>
                                    <th>操作</th>
                              </tr>
                              <c:forEach items="${pageView.records}" var="entry" varStatus="s">  
                           	  	<tr>
                           		 <td>${s.index+1}</td> 
                           		 <td>${entry.lineNo}</td> 
                           		 <td>${entry.inLineNo}</td> 
                           		 <td>${entry.matchedNum}</td> 
                           		 <td>${entry.matchNum}</td> 
                           		 <td>
                           		 	${entry.accuracy} ${entry.ration!=null?"/":""} ${entry.ration}
                           		 	<c:if test="${entry.ration!=null&&entry.ration>=entry.accuracy}">
                           		 	 	  <span style="color: green">√</span>
                           		 	</c:if> 
                           		 	<c:if test="${entry.ration!=null&&entry.ration<entry.accuracy}">
                           		 	 	  <span style="color: red">×</span>
                           		 	</c:if>
                           		 </td> 
                           		 <td><fmt:formatDate value="${entry.matchFirstTime}" pattern="yyyy-MM-dd HH:mm:ss"/>  </td> 
                           		 <td><c:if test="${entry.finishTime==null}">未完成</c:if> <fmt:formatDate value="${entry.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>  </td> 
                           		 <td>${entry.recycleTime}</td> 
                           		 <td>
									<a href='javascript:show(${entry.id})' >查看笔迹</a> 
                                	<a href='<s:url action="taskrecord_list" namespace="/manage/task" />?taskId=${entry.id}&jobid=${jobid}'>详细</a>
                           		 </td> 
                           	  	</tr>
                           	  </c:forEach>
                                	
						 </table>
						 <div>说明
						 	<ul style="list-style-type: decimal;">
						 		<li>分配时间：指给第一个用户分配的时间</li>
						 		<li>完成时间：指计算出结果的时间</li>
						 	</ul>
						 </div>
						</s:form>
                       	<div class="fenye"><%@ include file="/WEB-INF/page/common/fenye.jsp" %></div>
                            <!-- code block hydom -->
                        
                        
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

        <script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/dashboard.js"></script>

    </body>
</html>
