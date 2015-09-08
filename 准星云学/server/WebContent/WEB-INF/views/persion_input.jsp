<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath() + "/";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>修改个人资料</title>
<link href="<%=basePath%>bootstrap/css/bootstrap.css" rel="stylesheet"	media="screen" />
<link href="<%=basePath%>bootstrap/css/style.default.css" rel="stylesheet" />
<link href="<%=basePath%>res/css/pager.css" rel="stylesheet" />
<link rel="stylesheet"	href="<%=basePath%>bootstrap/css/jquery.gritter.css" />
<link rel="stylesheet" type="text/css"	href="<%=basePath%>bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"	href="<%=basePath%>bootstrap/css/select2.css" />
<link rel="stylesheet" type="text/css"	href="<%=basePath%>bootstrap/css/style.css" />

<script src="<%=basePath%>bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery.cookies.js"></script>
<script src="<%=basePath%>bootstrap/js/pace.min.js"></script>
<script src="<%=basePath%>bootstrap/js/retina.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery.gritter.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery.fcbkcomplete.js"></script>
<script src="<%=basePath%>bootstrap/js/custom.js"></script>
<script src="<%=basePath%>res/js/common.js"></script>
<script src="<%=basePath%>res/js/list.js"></script>

<script src="<%=basePath%>bootstrap/js/jquery-ui-1.10.3.min.js"></script>
<script src="<%=basePath%>bootstrap/js/modernizr.min.js"></script>
<script src="<%=basePath%>bootstrap/js/select2.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery.validate.min.js"></script>



</head>
<body>
	<div class="panel panel-primary-head">
		<form id="inputForm" class="form-horizontal" action="<c:if test="${!empty persion.id }"><%=basePath%>persion/update.do?id=${persion.id } & gwipkid=${persion.gwinfo.id }</c:if>" method="post">
			<div class="panel panel-primary">
				<div class="panel-heading panel-title-name">
					<h3 class="panel-title col-sm-10  pull-left">基本信息</h3>
				</div>
				<!--基本信息-->
				<div class="panel-body">
					<div class="col-md-12">
						<div class="col-sm-6">
							<div class="form-group">
								<label class="col-sm-3 control-label"> 姓名： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="pername"
										name="pername" title="请填写个人姓名" value="${persion.pername }"
										placeholder="请填写个人姓名..." required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 员工编号： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="workcode"
										name="workcode" title="请填写员工编号"
										value="${persion.workcode }" placeholder="员工编号..." required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 岗位： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="gwiname"
										name="gwiname" title="请填写岗位"
										value="${persion.gwinfo.gwiname }" placeholder="有效岗位..."
										required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 性别： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="sex" name="sex"
										title="请填写性别" value="${persion.sex }" placeholder="请输入性别"
										required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 出身年月： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="birthday1"
										name="birthday1" title="请填写出身年月" value="<fmt:formatDate value="${persion.birthday }" pattern="yyyy-MM-dd"/>"
										placeholder="出身年月" required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 身份证号码： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="sfz"
										name="sfz" title="请填写身份证号码" value="${persion.sfz }"
										placeholder="证件号码" required />
								</div>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label class="col-sm-3 control-label"> 联系电话： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="pertel"
										name="pertel" title="请填写联系电话" value="${persion.pertel }"
										placeholder="个人联系电话 " required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 联系邮箱： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="permail"
										name="permail" title="请输入邮箱" value="${persion.permail }"
										placeholder="请输入邮箱" required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 入职时间： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="indate1"
										name="indate1" title="请填写入职之间" value="<fmt:formatDate value="${persion.indate }" pattern="yyyy-MM-dd"/>"
										placeholder="入职时间" required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 相片： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="perpic"
										name="perpic" title="请上传头像" value="${persion.perpic }"
										placeholder="头像" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 在职状态： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="currflag"
										name="currflag" title="请填写在职状态" value="${persion.currflag }"
										placeholder="在职状态" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 联系地址： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="peradd"
										name="peradd" title="请填写联系地址" value="${persion.peradd }"
										placeholder="联系地址" />
								</div>
							</div>
						</div>
					</div>
				</div>

				<!--信息描述-->
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">描述信息</h3>
					</div>
				</div>
				<div class="panel-body">

					<div class="form-group">
						<label class="col-sm-1 control-label"> 备注： </label>
						<div class="col-sm-10">
							<textarea class="form-control" rows="5" name="bz">${persion.bz }</textarea>
						</div>
					</div>
				</div>
				<div class="panel-footer">
					<div class="row">
						<div class="col-sm-0 col-sm-offset-5">
							<button type="submit" class="btn btn-primary mr5">保存</button>
							<!-- <button type="reset" class="btn btn-dark" >重置</button> -->
							<button class="btn btn-danger deleteButton"
								onclick="location.href='javascript:history.go(-1);'">取消</button>
							<!-- 红色按钮 class="btn btn-danger deleteButton" -->
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>


	<script>
	jQuery(document).ready(function(){
		 jQuery("#inputForm").validate({
        	highlight: function(element) {
            	jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        	},
        	success: function(element) {
            	jQuery(element).closest('.form-group').removeClass('has-error');
        	},
        	errorLabelContainer: jQuery("#inputForm div.errorForm")
    	});
		jQuery("#pubindustry, #publictags").select2({
            minimumResultsForSearch: -1
        });
		init();

	});
	function init(){
		$("#dintionDiv").html("<select class='form-control' id='diction' name='diction' ></select>");
		$("#diction").fcbkcomplete({
			json_url: "<%=basePath%>
		dictionary/conList.do",
				filter_selected : true,
				addontab : true,
				height : 3
			});
		};
	</script>
</body>
</html>