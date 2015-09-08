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
    	编辑银行信息(账户)
</title>
</head>
<body>
	<div class="panel panel-primary widget-newsletter">
		<div class="panel-heading">
			<h4 class="panel-title">新增银行账户</h4>
		</div>
		<div class="panel-body">
			<form name="inputForm" id="inputForm"
				action="<c:if test="${empty bank.id }"><%=basePath %>bank/save.do?companyid=${companyid }</c:if><c:if test="${!empty bank.id }"><%=basePath %>bank/update.do?id=${bank.id }&companyid=${companyid }</c:if>" method="post">
				<div class="form-group">
					<label class="col-sm-3 control-label"> 银行名称： </label>
					<div class="col-sm-5">
						<input class="form-control" type="text" id="bankname" name="bankname" value="${bank.bankname }" title="请输入银行名称" placeholder="银行名称" required>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label"> 开户行： </label>
					<div class="col-sm-5">
						<input class="form-control" type="text" id="bankadd" name="bankadd" value="${bank.bankadd }" title="开户行地址" placeholder="开户行" required>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label"> 开户人： </label>
					<div class="col-sm-5">
						<input class="form-control" type="text" id="skcorp" name="skcorp" value="${bank.skcorp }" title="收款单位?" placeholder="开户人">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label"> 账户： </label>
					<div class="col-sm-5">
						<input class="form-control" type="text"
							id="bankzh" name="bankzh" value="${bank.bankzh }" title="请填写收款账户" placeholder="账号号码" required>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label"> 类型： </label>
					<div class="col-sm-5">
						<input class="form-control" id="" name="" title="" placeholder="">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label"> 默认账户： </label>
					<div class="col-sm-5">
						<div class="checkbox block">
							<label> <input type="checkbox" id="isdefault" name="isdefault"> 勾选代表默认账户信息
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
						<textarea class="form-control" rows="5" id="bz" name="bz">${bank.bz }</textarea>
					</div>
				</div>
				<div class="btn-list col-sm-8 col-md-offset-3">
					<button class="btn btn-primary deleteButton">保存</button>
					<!-- <button class="btn btn-danger deleteButton">关闭</button> -->
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