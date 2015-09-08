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
        <title>用户管理</title>
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
    	function show(tid,uid) {
	   	   	 var url ="${pageContext.request.contextPath}/manage/task/task_show.action";
	   		 art.dialog.open(url,{width:400 ,height: 500 , title: '区块笔迹',id:'task_'+tid});
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
                                    <li class="active">用户查看</li>
                                </ul>
                                <h4>用户查看</h4>
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->
                    
                    <div class="contentpanel">
                       <s:form action="user_list" namespace="/manage/account" method="post" id="pageList"> 
                         <s:hidden name="page" />
                         <s:hidden name="m" />
						  <div style="margin-bottom: 10px;"> 
                         	<span class="text-primary hidden" >查询选项 </span>
                         	<input type="text" style="width: 220px;display: inline-block;" name="query_phone" value="${query_phone}" class="form-control"  placeholder="手机号"  >
                         	<input type="text" style="width: 220px;display: inline-block;height: 38px;" name="query_createTime" value="${query_createTime}" class="Wdate"   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="创建时间"  >
                         	<input type="text" style="width: 220px;display: inline-block;height: 38px;" name="query_lastTime" value="${query_lastTime}" class="Wdate"   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="最近登录"  >
                            <input type="button" onclick="javascript:confirmQuery()" style="margin: 0 50px;"  class="btn btn-primary"   value="查 询"  >
                         </div>
    					 <table class="table table-bordered table-striped" >
							 <tr>
                                    <th>#</th>
                                    <th>用户ID</th>
                                    <th>手机号</th>
                                    <th>积分</th>
                                    <th>最近一月识别数</th>
                                    <th>识别总数</th>
                                    <th>正确率</th>
                                    <th>平均处理速度</th>
                                    <th>注册时间</th>
                                    <th>最近登录</th>
                                    <th>操作</th>
                              </tr>
                          	  <c:forEach items="${pageView.records}" var="entry" varStatus="s">  
                           	  	<tr id="tr_${entry.id}">
                           		 <td>${s.index+1}</td> 
                           		 <td>${entry.id}</td> 
                           		 <td>${entry.phone}</td> 
                           		 <td>${entry.score}</td> 
                           		 <td>${entry.count_month}</td> 
                           		 <td>${entry.count_all}</td> 
                           		 <td>${entry.count_rightPercent}%</td> 
                           		 <td>${entry.count_processTime}</td> 
                           		 <td><fmt:formatDate value="${entry.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>  </td> 
                           		 <td><fmt:formatDate value="${entry.lastSigninTime}" pattern="yyyy-MM-dd HH:mm:ss"/>  </td> 
                           		 <td>
                           		  <a href='<s:url action="scoreRecord_listheap" namespace="/manage/credit" />?accid=${entry.id}'>积分详细</a>
                           		  <a href='<s:url action="scoreRecord_listuse" namespace="/manage/credit" />?accid=${entry.id}'>消费详细</a>
                           		 </td> 
                           	  	</tr>
                           	  </c:forEach>
						 </table>
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
