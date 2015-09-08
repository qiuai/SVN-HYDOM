<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath() + "/";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>
    	<c:choose>
    		<c:when test="${isNew }"><spring:message code="action.add"></spring:message></c:when>
    		<c:otherwise><spring:message code="action.edit"></spring:message></c:otherwise>
    	</c:choose>
		数据字典
</title>
</head>
<body>
<div class="panel panel-primary widget-newsletter">
    <div class="panel-heading">
        <h4 class="panel-title">
        	<c:choose>
        		<c:when test="${empty dictionary.id }"><spring:message code="action.add"></spring:message></c:when>
        		<c:otherwise><spring:message code="action.edit"></spring:message></c:otherwise>
        	</c:choose>
        	<spring:message code="Dictionary.type${dictionary.dictype }"></spring:message>
        </h4>
    </div>
    <form name="inputForm" id="inputForm" action="<c:if test="${empty dictionary.id }">save.do</c:if><c:if test="${!empty dictionary.id }">update.do</c:if>" method="post">
    <div class="panel-body">
        <p>请完成数据字典名称与序号后保存,序号越大排序越前(默认0)</p>
        <div class="errorForm"></div>
        <input name="dicname" class="form-control mt10 mb10" placeholder="名称" maxlength="20" type="text" value="${dictionary.dicname }" title="<spring:message code="message_001" arguments='名称' />" required />
        <input name="dicindex" class="form-control mb10" placeholder="序号" type="number" value="${dictionary.dicindex }" min="0" max="99" title="请输入0-99之间的整数!" required />
        <input name="dictype" type="hidden" value="${dictionary.dictype}"/>
        <input name="ispublic" type="hidden" value="${dictionary.ispublic}"/>
        <input name="id" type="hidden" value="${dictionary.id}"/>
        <button class="btn btn-success btn-block">保存</button>
    </div><!-- panel-body -->
    </form>
</div>

<script src="<%=basePath %>bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath %>bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath %>bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath %>bootstrap/js/modernizr.min.js"></script>
<script src="<%=basePath %>bootstrap/js/pace.min.js"></script>
<script src="<%=basePath %>bootstrap/js/retina.min.js"></script>
<script src="<%=basePath %>bootstrap/js/jquery.gritter.min.js"></script>
<script src="<%=basePath %>bootstrap/js/jquery.cookies.js"></script>
<script src="<%=basePath %>bootstrap/js/jquery.validate.min.js"></script>
<script src="<%=basePath %>bootstrap/js/jquery.form.js"></script>
<script src="<%=basePath %>bootstrap/js/custom.js"></script>
<script type="text/javascript">
jQuery(document).ready(function() {
       // Error Message In One Container
       $("#inputForm").validate({
           errorLabelContainer: jQuery("#inputForm div.errorForm")
       });
       
       var url;
       var isNew = false;
       <c:if test="${empty dictionary.id }">isNew = true</c:if>;
       if (isNew) {
    	   url = '<%=basePath %>dictionary/save.do';
       } else {
    	   url = '<%=basePath %>dictionary/update.do';
       }
       
       // ajax提交
       $("#inputForm").ajaxForm({
    	   'url':url,
    	   success:function(retstr) {
			   var ret = eval("(" + retstr + ")");
    		   if (ret.status == 0) {
    			 $("#inputDialog").modal("hide");
    			 jQuery.gritter.add({
    			         title: '成功!',
    			         text: '添加<spring:message code="Dictionary.type${dictionary.dictype }"></spring:message>成功.',
    			         class_name: 'growl-info',
    					 image: '../bootstrap/images/screen.png',
    			         sticky: false,
    			         time: ''
    			  });
    			 location.reload(true);
    		   } else {
      			 jQuery.gritter.add({
			         title: '失败!',
			         text: ret.msg,
			         class_name: 'growl-info',
					 image: '../bootstrap/images/screen.png',
			         sticky: false,
			         time: ''
			  	 });
    		   }
    	   }
       });
       

});
</script>

</body>
</html>