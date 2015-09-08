<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
        <meta name="description" content="">
        <title>Chain Responsive Bootstrap3 Admin</title>
        <link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/css/manage.common.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/resource/js/myform.js" type="text/javascript"></script>
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->
       <script type="text/javascript">
    	function del(uid){
			if (confirm('您确定要禁用吗')) {
			  $.get("${pageContext.request.contextPath}/manage/user/delete", 
			  {uid:uid},
			  function(data) {
		      	if(data=='success'){
		      		$("#td_"+uid).html("禁用");
		       	}
			   });
			}
		}
        </script>
    </head>

<body>
	<%@ include file="/WEB-INF/page/common/head.jsp" %>
    <section>
    <div class="mainwrapper">
		<%@ include file="/WEB-INF/page/common/left.jsp" %>
                
        <div class="mainpanel">
             <div class="pageheader">
                 <div class="media">
                     <div class="pageicon pull-left">
                         <i class="fa fa-home"></i>
                     </div>
                     <div class="media-body">
                         <ul class="breadcrumb">
                             <li><a href=""><i class="glyphicon glyphicon-home"></i></a></li>
                             <li><a href="">Pages</a></li>
                             <li>Blank Page</li>
                         </ul>
                         <h4>Blank Page</h4>
                     </div>
                 </div><!-- media -->
             </div><!-- pageheader -->
                    
             <div class="contentpanel">    
             	 <div class="content" >
				      <div class="row">
				      	<form id="pageList" action="${pageContext.request.contextPath}/manage/user/list" method="post">
				      		<input type="hidden" name="page" value="${page}">
				      		 <div class="text-center" style="margin-bottom: 6px;"> 
	                         	<span class="text-primary hidden" >查询选项 </span>
	                         	<input type="text" style="width: 220px;display: inline-block;" name="queryContent" value="${queryContent}" class="form-control"  placeholder=""  >
	                       		<input type="button" style="margin: 0 50px;"  class="btn btn-primary"   value="查 询" onclick="confirmQuery()"  >
	                        	<a class="btn btn-success" href='/manage/account/new'>新增</a>
	                         </div>
				      	</form>
				      	<table class="table table-bordered table-striped">
                           <thead>
                                <tr>
                                    <th>#</th>
                                    <th>用户昵称</th>
                                    <th>邮箱</th>
                                    <th>密码</th>
                                    <th>创建时间</th>
                                    <th>用户类型</th>
                                    <th>用多分享数</th>
                                    <th>最大评论数</th>
                                    <th>最大上传SiZE</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                           </thead>
                            <tbody>
                                  <c:forEach items="${pageView.records}" var="entry" varStatus="s">  
	                           	  	<tr id="tr_${entry.id}"">
	                           		 <td>${s.index+1}</td> 
	                           		 <td>${entry.nickname}</td> 
	                           		 <td>${entry.email==null?'未绑定':entry.email}</td> 
	                           		 <td>${entry.password}</td> 
	                         		 <td><fmt:formatDate value="${entry.createDate}" pattern="yyyy-MM-dd hh:mm:ss "/></td> 
	                           		 <td>
	                           		 	<c:if test="${entry.lv==1}">普通用户</c:if>
	                           		 	<c:if test="${entry.lv==2}">邮箱用户</c:if>
	                           		 	<c:if test="${entry.lv==3}">核心用户</c:if>
	                           		 	<c:if test="${entry.lv==4}">vip用户</c:if>
	                           		 	<c:if test="${entry.lv==5}">高级用户</c:if>
	                           		 </td> 
	                           		 <td>${entry.maxShare} (${entry.shareds})</td>
	                           		 <td>${entry.maxComment } (${entry.comments})</td>
	                           		 <td>${entry.maxUploadSize } (${entry.uploadSize})</td>
	                           		 <td><span id="td_${entry.id}">${entry.visible?"正常":"禁用"}</span></td> 
	                           		 <td>
	                           		 	<a href="javascript:del('${entry.id}')">禁用</a>
	                           		 	<a href="${pageContext.request.contextPath}/manage/user/edit?uid=${entry.id}">修改</a>
	                           		 </td> 
	                           	  	</tr>
	                           	  </c:forEach>
                            </tbody>
                        </table>
                        <div> 
                        	<%@ include file="/WEB-INF/page/common/fenye.jsp" %>
                        </div>
				    </div>

             	 </div> <!-- content -->          
                 
             </div> <!-- contentpanel -->
             
             <%@ include file="/WEB-INF/page/common/bottom.jsp" %>
        </div> <!-- mainpanel -->
    </div> <!-- mainwrapper -->
    </section>


    <script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/chain/js/jquery-migrate-1.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/chain/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/chain/js/modernizr.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/chain/js/pace.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/chain/js/retina.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/chain/js/jquery.cookies.js"></script>
    <script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
</body>
</html>
