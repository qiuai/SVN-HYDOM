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
        <title>积分中心</title>
        <link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/morris.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/select2.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
		<script src="${pageContext.request.contextPath}/resource/art/artDialog.js?skin=blue"></script>
        <script src="${pageContext.request.contextPath}/resource/art/plugins/iframeTools.js"></script>
        <script src="${pageContext.request.contextPath}/resource/my97/WdatePicker.js"></script>
        <script src="${pageContext.request.contextPath}/resource/mathjax/MathJax.js?config=TeX-AMS_HTML-full"></script>
	    
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resource/chain/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/respond.min.js"></script>
        <![endif]-->
        <script type="text/javascript">
			function del(id){
				if (confirm('您确定要删除此信息吗')) {
				  $.get("${pageContext.request.contextPath}/manage/credit/trophy_delete.action", 
				  {id:id},
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
                                    <li><a href="${pageContext.request.contextPath}/manage/index.action"><i class="glyphicon glyphicon-home"></i></a></li>
                                    <li class="active">score center</li>
                                </ul>
                                <h4>积分中心</h4>
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->
                    
                    <div class="contentpanel">
                       <s:form action="scoreRecord_list" namespace="/manage/credit" method="post" id="pageList"> 
                         <s:hidden name="page" />
                         <s:hidden name="m" />
                          <div style="margin-bottom: 10px;"> 
                         	<span class="text-primary hidden" >查询选项 </span>
                         	<input type="text" style="width: 220px;display: inline-block;" name="query_uid" value="${query_uid}" class="form-control"  placeholder="用户ID"  >
                         	<input type="text" style="width: 220px;display: inline-block;" name="query_phone" value="${query_phone}" class="form-control"  placeholder="手机号"  >
                         	<input type="text" style="width: 220px;display: inline-block;height: 38px;" name="query_createTime" value="${query_createTime}" class="Wdate"   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="积分变化时间"  >
                         	<input type="button" style="margin: 0 50px;"  class="btn btn-primary" onclick="javascript:confirmQuery()"  value="查 询"  >
                         </div>
    					 <table class="table table-bordered table-striped">
							 <tr>
                                    <th>#</th>
                                    <th>用户ID</th>
                                    <th>手机号</th>
                                    <th>积分</th>
                                    <th>时间</th>
                                    <th>积分变化事由</th>
                                    <th>操作</th>
                              </tr>
                              <c:forEach items="${pageView.records}" var="entry" varStatus="s">  
                              <tr id="tr_${entry.id}">
                                    <td>${s.index+1}</td>
                                    <td>${entry.account.id}</td>
                                    <td>${entry.account.phone}</td>
                                    <td>${entry.sign?"+":"-"}${entry.score}</td>
                                    <td><fmt:formatDate value="${entry.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>  </td> 
                                    <td>${entry.taskRecord!=null?"完成任务":"兑换"}</td>
                                    <td>
                                    	<a href="javascript:load(${entry.id},${entry.taskRecord!=null?"1":"0"})">详细</a>
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
                    
                    <!-- 模态框 -->
                    <div class="modal fade" id="myModal"  role="dialog" aria-label="myModalLabel" aria-hidden="true">
			        <div class="modal-dialog modal-md">
			            <div class="modal-content">
			                
			            </div>
			        </div>
			    	</div><!--modal fade  -->
			    
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
		<script type="text/javascript">
		function load(srid,sign){
			url="${pageContext.request.contextPath}/manage/credit/showTrophyRecord.action";
		   	if(sign==1){
				url="${pageContext.request.contextPath}/manage/credit/showTaskRecord.action";
			}
		    $.get(url,{srid:srid},
			function(data) {
	      	   $(".modal-content").text("");// 清空数据
	      	   $(".modal-content").append(data);

			   MathJax.Hub.Queue(["Typeset",MathJax.Hub]); //数据加载完成后重新解释Latex
	      		 $("#myModal").modal({
	      	        backdrop: true//点击空白处模态框消失
	      	     })
	      	     
		    });
		}
		
	    $("#myModal").on("hidden.bs.modal",function(e){
	    	//$(".modal-body").remove();// 清空数据
	    	//$(".modal-footer").remove();// 清空数据
	    })
		</script>
    </body>
</html>
