<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>${systemName} - 管理系统</title>
    <meta http-equiv='X-UA-Compatible' content='IE=8'>
    <link href="<%=base%>template/admin/css/admin.css" rel="stylesheet" type="text/css" />
	<link href="<%=base%>template/admin/css/base.css" rel="stylesheet" type="text/css" />
	<link href="<%=base%>template/admin/css/welcome.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<jsp:include page="include.jsp"></jsp:include>
	<script type="text/javascript">
	$().ready( function() {
		<%if (request.getSession().getAttribute("message") != null) {%>
		$.dialog({type: "error", content: "<%=request.getSession().getAttribute("message")%>", modal: true, autoCloseTime: 3000});
		<%}%>
	});
	</script>
  </head>
  <body>
  	<div class="tongyong_main">
	<div class="map">
        <div class="pro_map">管理信息 > 首页</div>
    </div>
    </div>
    <div class="list_main">
    	<div class="list_main01">
        	<div class="list_main02">
            	<div class="top_info">
                	<div class="top_info_01">欢迎信息</div>
                </div>
                <div class="add_mian">
                	<ul>
                    	<li>欢迎回来，${session_user.username} <span style="padding-left:40px; font-size:12px; color:#1759c0;">
                    	<!-- <a href="">日志信息</a> --></span></li>
<!--                         <li>上次登陆时间：2014-02-13 14：25：36</li> -->
<!--                         <li>上次登陆IP：192.168.0.1</li> -->
<!--                         <li>未处理信息：<span style="color:#1759c0"><a href="">订单（5）</a></span>    <span style="color:#1759c0; padding-left:20px;"><a href="">审核中（8）</a></span> </li> -->
                    </ul>
                </div>
                <div class="top_info">
                	<div class="top_info_01">常用功能</div>
                </div>
                <div class="add_mian01">
                	<ul>
                    	<!--  <li class="li_cy">
                        	<a href="<%=base %>admin/product!list.action" target="mainFrame">
                        	<span><img src="<%=base%>template/admin/images/cy01.png" alt="" width="66" height="66" /></span>
                            <span style="line-height:30px; font-size:12px;text-align:center;">产品列表</span>
                            </a>
                        </li>
                        -->
                        <li class="li_cy">
                        	<a href="javascript:void(0)" target="mainFrame">
                        	<span><img src="<%=base%>template/admin/images/cy02.png" alt="" width="66" height="66" /></span>
                            <span style="line-height:30px; font-size:12px;text-align:center;">教学统计</span>
                            </a>
                        </li>
                       <%--  <li class="li_cy">
                        	<a  href="<%=base %>admin/goods!list.action" target="mainFrame">
                        	<span><img src="<%=base%>template/admin/images/cy03.png" alt="" width="66" height="66" /></span>
                            <span style="line-height:30px; font-size:12px;text-align:center;">商品列表</span>
                            </a>
                        </li> --%>
                       <!--  <c:if test="${SESSION_USER.userType == 'ADMIN' }">
                        <li class="li_cy">
                        	<a  href="<%=base %>admin/user!list.action" target="mainFrame">
                        	<span><img src="<%=base%>template/admin/images/cy04.png" alt="" width="66" height="66" /></span>
                            <span style="line-height:30px; font-size:12px;text-align:center;">用户信息</span>
                            </a>
                        </li>
                        <li class="li_cy">
                        	<a href="<%=base %>admin/setup!view.action" target="mainFrame">
                        	<span><img src="<%=base%>template/admin/images/cy05.png" alt="" width="66" height="66" /></span>
                            <span style="line-height:30px; font-size:12px;text-align:center;">系统设置</span>
                            </a>
                        </li>
                        </c:if> -->
                        <%-- <li class="li_cy">
                        	<a href="<%=base %>admin/comment!list.action" target="mainFrame">
                        	<span><img src="<%=base%>template/admin/images/cy06.png" alt="" width="66" height="66" /></span>
                            <span style="line-height:30px; font-size:12px;text-align:center;">评论列表</span>
                            </a>
                        </li> --%>
                    </ul>
                </div>
            </div>
        </div> 
   	 </div>
	</div>
		<jsp:include page="prompt.jsp"></jsp:include>
  </body>
</html>
