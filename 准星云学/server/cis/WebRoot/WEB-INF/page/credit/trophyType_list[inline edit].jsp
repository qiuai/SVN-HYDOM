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
        <title>Chain Responsive Bootstrap3 Admin</title>
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
		function del(id){
			if (confirm('您确定要删除此信息吗')) {
			  $.get("${pageContext.request.contextPath}/manage/credit/trophyType_delete.action", 
			  {id:id},
			  function(data) {
		      	if(data==1){
		      		$("#tr_"+id).css("display","none");
		       	}
			   });
			}
		}
		function showEdit(id){
				//visibility: hidden visible
			document.getElementById("con_"+id).style.display="none";
			document.getElementById("edit_"+id).style.visibility="visible";
		}
		function postEdit(id){
		  var name = document.getElementById("name_"+id).value;
		  $.post("${pageContext.request.contextPath}/manage/credit/trophyType_edit.action", 
		  {id:id,name:name},
		  function(data) {
	      	if(data==1){
	      		if(window.navigator.userAgent.toLowerCase().indexOf("firefox")!=-1){
	      			document.getElementById("con_"+id).textContent=name;
	      		}else{
	      			document.getElementById("con_"+id).innerText=name;
	      		}	      		
	      		document.getElementById("con_"+id).style.display="inline";
				document.getElementById("edit_"+id).style.visibility="hidden";
	       	}
		  });
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
                                    <li>trophytype manage</li>
                                </ul>
                                <h4>奖品类别管理</h4>
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->
                    
                    <div class="contentpanel">
                     <div class="container">
				        <div class="row" style="margin-bottom: 10px;">
				            <div class="col-md-12">
				                <form class="form-inline" action="${pageContext.request.contextPath}/manage/credit/trophyType_add.action">
				                     <input type="text" name="type.name" class="form-control" name="" placeholder="类别名称">
                        			 <input type="submit" class="btn btn-primary" name="" value="提交">
				                </form>
				            </div>
				        </div>

				        <div class="row">
				            <div class="col-md-7">
				                <form action="${pageContext.request.contextPath}/manage/credit/trophyType_list.action" method="post" id="pageList">
				                	<s:hidden name="page" />
                        			<s:hidden name="m" />
			                      <table class="table table-bordered table-striped">
			                            <thead>
			                                <tr>
			                                    <th>#</th>
			                                    <th>名称</th>
			                                    <th>操作</th>
			                                </tr>
			                            </thead>
			                            <tbody>
			                                  <c:forEach items="${pageView.records}" var="entry" varStatus="s">  
				                           	  	<tr id="tr_${entry.id}"">
				                           		 <td>${s.index+1}</td> 
				                           		 <td >
				                           		 	<span id="con_${entry.id}" ondblclick="javascript:showEdit('${entry.id}')" title="双击修改" >${entry.name}</span>
				                           		 	
				                           		 </td> 
				                           		 <td><a href="javascript:del('${entry.id}')">删除</a>				                           		 </td> 
				                           	  	</tr>
				                           	  </c:forEach>
			                            </tbody>
			                      </table>
				                </form>
                       			<div class="fenye"><%@ include file="/WEB-INF/page/common/fenye.jsp" %></div>
				            </div>
				            <div class="col-md-5">
				            	<span id="edit_${entry.id}" style="visibility: hidden">
			                                            <input type="text" style="height: 30px;" id="name_${entry.id}" value="${entry.name}" >
			                                            <input type="button" class="btn btn-success btn-xs" onclick="javascript:postEdit('${entry.id}')" value="确定">
			                    </span>
				            </div>
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

        <script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/dashboard.js"></script>

    </body>
</html>
