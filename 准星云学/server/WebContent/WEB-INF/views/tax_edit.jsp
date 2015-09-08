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
<link href="<%=basePath %>bootstrap/css/bootstrap.css" rel="stylesheet"/>
<title>
    	编辑税务信息
</title>
</head>
<body>
	<div class="panel panel-primary widget-newsletter">
		<div class="panel-heading">
			<h4 class="panel-title">编辑税务信息</h4>
		</div>
		<div class="panel-body">
			<form name="inputForm" id="inputForm"
				action="<c:if test="${empty bill.id }"><%=basePath %>bill/save.do?companyid=${companyid }</c:if><c:if test="${!empty bill.id }"><%=basePath %>bill/update.do?id=${bill.id }&companyid=${companyid }</c:if>"
				method="post">
				<div class="form-group">
					<label class="col-sm-3 control-label"> 发票抬头： </label>
					<div class="col-sm-5">
						<input class="form-control" type="text" id="billcorpname" name="billcorpname" value="${bill.billcorpname }" title="请输入单位名称" placeholder="单位名称" required/>
					</div>
<!-- 					<div class="col-sm-3">
						<div class="checkbox block">
							<label> <input type="checkbox" id="companyname"> 同客户名称
							</label>
						</div>
					</div> -->
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label"> 税务类型： </label>
					<div class="col-sm-5">
						<input class="form-control" type="text" id="" name="" title="税务的类型" value="" placeholder=""/>
					</div>
				</div><div class="form-group">
					<label class="col-sm-3 control-label"> 税号： </label>
					<div class="col-sm-5">
						<input class="form-control" type="text" id="billsbh" name="billsbh" title="请填写纳税人识别号" value="${bill.billsbh }" placeholder="纳税人识别号" required/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label"> 开户行及账号： </label>
					<div class="col-sm-5">
						<input class="form-control" type="text" id="billbankandzh" name="billbankandzh" title="请填写开户银行以及账号号码" value="${bill.billbankandzh }" placeholder="开户银行以及账号号码"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label"> 地址与电话： </label>
					<div class="col-sm-5">
						<input class="form-control" type="text"
							id="billaddandtel" name="billaddandtel" title="请输入地址和电话" value="${bill.billaddandtel }" placeholder="地址与电话" required/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label"> 默认税务： </label>
					<div class="col-sm-5">
						<div class="checkbox block">
							<label> <input type="checkbox" id="isdefault" name="isdefault"> 勾选代表默认税务信息
							</label>
						</div>
					</div>
				</div>
				<div class="form-group">
						<div class="col-sm-6">
							<input class="form-control" type="hidden" id="publicid" name="company.id" value="${companyid }"/>
						</div>
					</div>
				<div class="form-group">
					<label class="col-sm-3 control-label"> 备注： </label>
					<div class="col-sm-8">
						<textarea class="form-control" rows="5" id="bz" name="bz">${bill.bz}</textarea>
					</div>
				</div>
				<div class="btn-list col-sm-3 col-md-offset-2">
					<button class="btn btn-primary deleteButton">保存</button>
				</div>
			</form>
		</div>
		<!-- panel-body -->
	</div>

	<script src="<%=basePath%>bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>bootstrap/js/modernizr.min.js"></script>
<script src="<%=basePath%>bootstrap/js/pace.min.js"></script>
<script src="<%=basePath%>bootstrap/js/retina.min.js"></script>
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
       $("#isdefault").attr("checked",true);
       $("#isdefault").click(function(){
     	  if($(this).attr("checked")==undefined){
     		  $(this).val("false");
     	  }
     	 // alert($(this).val());
       });
	  
});
</script>

</body>
</html>