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
    	新增银行
</title>
</head>
<body>
	<div class="panel panel-primary widget-newsletter">
		<div class="panel-heading">
			<h4 class="panel-title">新增银行账户</h4>
		</div>
		<div class="panel-body">
			<form name="inputForm" id="inputForm"
				action="<c:if test="${empty bank.id }"><%=basePath %>bank/save.do?companyid=${companyid }</c:if><c:if test="${!empty bank.id }"><%=basePath %>bank/update.do</c:if>" method="post">
				<div class="form-group">
					<label class="col-sm-3 control-label"> 银行名称： </label>
					<div class="col-sm-5">
						<input class="form-control" type="text" id="bankname" name="bankname" title="请输入银行名称" placeholder="银行名称" required>
					</div>
				</div>
							<input class="form-control" type="hidden" id="publicid" name="company.id" value="${companyid }"/>

				<div class="form-group">
					<label class="col-sm-3 control-label"> 开户行： </label>
					<div class="col-sm-5">
						<input class="form-control" type="text" id="bankadd" name="bankadd" title="开户行地址" placeholder="开户行" required>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label"> 开户人： </label>
					<div class="col-sm-5">
						<input class="form-control" type="text" id="skcorp" name="skcorp" title="收款单位?" placeholder="开户人" required>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label"> 账户： </label>
					<div class="col-sm-5">
						<input class="form-control" type="text"
							id="bankzh" name="bankzh" title="请填写收款账户" placeholder="账号号码" required>
					</div>
				</div>
				<!-- <div class="form-group">
					<label class="col-sm-3 control-label"> 类型： </label>
					<div class="col-sm-5">
						<input class="form-control" id="" name="" title="" placeholder="">
					</div>
				</div> -->
				<div class="form-group">
						<label class="col-sm-3 control-label">
							附件：
						</label>
						<div class="col-sm-4">
							<input type="file" name="file" id="file">						
						</div>
						<div class="col-sm-4"><input type="button" value="上传" id="upload"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"></label>
						<div class="col-sm-4" id="files"></div>
					</div>
						<input class="form-control" type="hidden" id="bankatt" name="bankatt"/>

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
					<label class="col-sm-3 control-label"> 备注： </label>
					<div class="col-sm-8">
						<textarea class="form-control" rows="5" id="bz" name="bz"></textarea>
					</div>
				</div>
				<div class="btn-list col-sm-8 col-md-offset-5">
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
<script src="<%=basePath %>bootstrap/js/ajaxfileupload.js"></script>
<script type="text/javascript">
jQuery(document).ready(function() {
       // Error Message In One Container
       $("#inputForm").validate({
           errorLabelContainer: jQuery("#inputForm div.errorForm")
       });
       $("#upload").click(function(){
	   		upload();
			});
      $("#isdefault").attr("checked",true);
      $("#isdefault").click(function(){
    	  if($(this).attr("checked")==undefined){
    		  $(this).val("false");
    	  }
    	 // alert($(this).val());
      });
});
var attid ="";
function upload(){
	   var myfile = $("#file").val();
		if(myfile!=""){
			//异步上传
			$.ajaxFileUpload({
					url:"<%=basePath%>upload.do",
					type : "post",
					fileElementId : "file",
					dataType : "JSON",
					secureuri : false,
					success : function(data) {
						var obj = eval("(" + data + ")");
						attid += obj.data.id +",";
						$("#bankatt").val(attid);
						//alert("上传成功!");
						var filename = obj.data.attname;

						var filepath = obj.data.smaillpath;
						$("#files").append("<img name='"+filename+"' src='../"+filepath+"' class='img-thumbnail' style='width:100px;height:50px;'/>");
					}
				});
	}else{
		alert("请选择要上传的文件!");
	}
}
</script>

</body>
</html>