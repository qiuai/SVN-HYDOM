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
        <title>消息管理</title>
        <link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/morris.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/select2.css" rel="stylesheet" />
		<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/resource/art/artDialog.js?skin=blue"></script>
        <script src="${pageContext.request.contextPath}/resource/art/plugins/iframeTools.js"></script>
        <script src="${pageContext.request.contextPath}/resource/my97/WdatePicker.js"></script>
		<script src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resource/chain/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/respond.min.js"></script>
        <![endif]-->
        <script type="text/javascript">
		function del(id){
			if (confirm('您确定要删除此信息吗')) {
			  $.get("${pageContext.request.contextPath}/manage/extra/message_delete.action", 
			  {msgId:id},
			  function(data) {
		      	if(data==1){
		      		$("#tr_"+id).css("display","none");
		       	}
			   });
			}
		}
        </script>
        <style type="text/css">
        	.mleft {
        		margin-bottom: 10px;
        		border: 1px solid #ddd;
        		padding: 10px;
        		min-height: 225px;
        	}
        	.mright{
        		margin-bottom: 10px;
        		border: 1px solid #ddd;
        		padding: 10px;
        		min-height: 225px;
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
                                    <li>message list</li>
                                </ul>
                                <h4>消息推送</h4>
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->
                    
                    <div class="contentpanel">
                      <div class="row">
                      		<div class="col-md-6">
                      			<s:form id="myform" action="message_add" namespace="/manage/extra" method="post" > 
		                         <div   class="form-horizontal mleft"> 
		                         	<div class="form-group">
		                         		 <label class="col-md-1 control-label sr-only">消息主题</label>
							             <div class="col-md-6">
							                <input type="text" class="form-control" name="message.title" placeholder="消息主题">
							             	<span></span>
							             </div>
							             <div class="col-md-4">
							             		<div class="input-group">
											      <div class="input-group-addon">消息保留时长</div>
												  <select name="message.pushTimeToLive" class="form-control">
													<option value="0">不保留:在线推送</option>
													<option value="60">1分钟</option>
													<option value="600" selected="selected">10分钟</option>
													<option value="3600">1小时</option>
													<option value="10800" >3小时</option>
													<option value="43200">12小时</option>
													<option value="86400">保留1天</option>
													<option value="864000">保留10天</option>
												  </select>
											    </div>
							             </div>
		                         	</div>
		                         	<div class="form-group">
		                         		 <label class="col-md-1 control-label sr-only">消息内容</label>
							             <div class="col-md-10">
							               <textarea row="4" class="form-control"  placeholder="消息内容" name="message.content"></textarea>
							               <span></span>
							             </div>
		                         	</div>
		                         	<div class="form-group">
		                         		<div class="col-md-4 col-md-push-3">
		                         		 <button type="reset" class="btn btn-primary">重置</button>
		                         		 <button type="submit" class="btn btn-primary">推送</button>
		                         		</div>
		                         	</div>
		                         </div>
		                       </s:form>
                      		</div>
                      		<div class="col-md-6">
                      			<div class="mright form-horizontal"> 
		                       		<h4>消息推送说明</h4>
		                       		<ul>
		                       			<li>消息保留时长
			                       			<ul>
			                       			<li>发送消息推送后，如果用户不在线，则会保存为离线消息，待该用户上线时会继续推送。</li>
			                       			<li>可以设定离线消息时长：不保留表示只推送消息给在线用户</li>
			                       			</ul>
		                       			</li>
		                       			
		                       			<li>删除操作
			                       			<ul>
			                       			<li>在本页面删除某条消息，第三方仍会进行推送该消息。</li>
			                       			<li>在本页面删除某条消息，手机端获取消息列表不会显示该消息。</li>
			                       			</ul>
		                       			</li>
		                       			<li>建议：在进行推送消息前先<mark>确认消息内容</mark>再推送</li>
		                       		</ul>
		                       </div>
                      		</div>
                      </div>	
                       
                       <s:form action="message_list" namespace="/manage/extra" method="post" id="pageList"> 
                         <s:hidden name="page" />
                         <s:hidden name="m" />
    					 <table class="table table-bordered table-striped" >
							 <tr >
                                    <th>#</th>
                                    <th>消息ID</th>
                                    <th>发送时间</th>
                                    <th>消息保留时长</th>
                                    <th>主题</th>
                                    <th>内容</th>
                                    <th>操作</th>
                              </tr>
                           	  <c:forEach items="${pageView.records}" var="entry" varStatus="s">  
                           	  	<tr id="tr_${entry.id}">
                           		 <td>${s.index+1}</td> 
                           		 <td>${entry.id}</td> 
                           		 <td><fmt:formatDate value="${entry.issueTime}" pattern="yyyy-MM-dd HH:mm:ss"/>  </td> 
                           		 <td>${entry.pushTimeToLive}</td> 
                           		 <td>${entry.title}</td> 
                           		 <td >
	                           		 <span style="width:200px;display:block;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;">
	                           		 	${entry.content }
	                           		 </span>
                           		 </td> 
                           		 <td>
	                             <a href="#" class="js-pop" data-toggle="popover" data-trigger="focus" data-placement="left" title="${entry.title}" data-content="${entry.content}">详细内容</a>
                           		 <a href="javascript:del('${entry.id}')">删除</a>
                           		 </td> 
                           	  	</tr>
                           	  </c:forEach>
						 </table>
						</s:form>
                       	<div class="fenye"><%@ include file="/WEB-INF/page/common/fenye.jsp" %></div>

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
  	    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/validate/message.js"></script>

        <script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/dashboard.js"></script>
		<script type="text/javascript">
			$(".js-pop").popover(); //工具提示框
		</script>
    </body>
</html>
