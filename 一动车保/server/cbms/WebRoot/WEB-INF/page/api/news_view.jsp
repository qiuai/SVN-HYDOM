<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
      
    <title>新闻查看</title>
    	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
		<link href="${pageContext.request.contextPath}/resource/chain/css/bootstrap.min.css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/bootstrap.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	function star(uid,token,nwid) {
			$.post("${pageContext.request.contextPath}/api/webpage/news/star",
					{
					uid:uid,
					token:token,
					nwid:nwid,
					},function(data) {
					if (data == 1) {//表示 帐户存在
						$("#switch").attr("src", "${pageContext.request.contextPath}/resource/image/app_news_like_after.png"); 
					} else {
						//alert("网络异常稍后再试");
					}
			});
	}
	</script>

  </head>
  
  <body>
  	<div class="container" style="padding: 5px 10px;">
	    <h3>${news.title}</h3> 
	    <div style="font-size: 12px;color: d3d3d3;"><fmt:formatDate value="${news.createDate}" pattern="yyyy-MM-dd" /></div>
	    <div><img class="img-responsive" src="${news.imgPath}" /></div>
	    <div style="border-top: 1px dashed ddd;">
	    	${news.content}
	    </div>
	    <p class="container text-center" style="width:100px;padding: 10px;" >
	    	<c:if test="${star!=null}" > 
	    			<img class="img-responsive" src="${pageContext.request.contextPath}/resource/image/app_news_like_after.png" />
	    	</c:if>
	    	<c:if test="${star==null && uid!=null && token!=null}" > 
	    		<a href="javascript:star('${uid}','${token}','${news.id}')">
	    			<img class="img-responsive" id="switch" src="${pageContext.request.contextPath}/resource/image/app_news_like_before.png" />
	    		</a>
	    	</c:if>
	    </p>
  	</div>
  </body>
</html>
