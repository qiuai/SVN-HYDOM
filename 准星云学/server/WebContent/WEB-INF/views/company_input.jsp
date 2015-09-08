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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<title>新建单位</title>   
	
	<link href="<%=basePath %>bootstrap/css/style.default.css" rel="stylesheet" />
	<link href="<%=basePath %>res/css/pager.css" rel="stylesheet" />
    <link rel="stylesheet" href="<%=basePath %>bootstrap/css/jquery.gritter.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/select2.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>bootstrap/css/style.css"/>
    
    <link href="<%=basePath%>bootstrap/css/jquery.tagsinput.css" rel="stylesheet" />
    
    <link href="<%=basePath%>res/css/head.css" rel="stylesheet" /><!-- 头部 跟 左部的样式 -->
    <link href="<%=basePath %>bootstrap/css/style_account.css" rel="stylesheet" />
    
    <jsp:include page="include/common.jsp" /><!-- 共同的JS -->
    
	<script src="<%=basePath %>res/js/list.js"></script>
    <script src="<%=basePath %>bootstrap/js/modernizr.min.js"></script>
    <script src="<%=basePath %>bootstrap/js/select2.min.js"></script>
    <script src="<%=basePath %>bootstrap/js/jquery.validate.min.js"></script>
    <script src="<%=basePath %>bootstrap/js/bootstrap.js"></script>
    <script src="<%=basePath %>bootstrap/js/ajaxfileupload.js"></script>
    <script src="<%=basePath %>bootstrap/js/jquery.fcbkcomplete.js"></script>
    
    
 <style type="text/css">
	.select-basic{
		width:337px;
	}
	.pull-left{
		width: 120px;
		display: inline-block;
		text-align: right;
	}
	label.control-label{
		display: inline-block;
		text-align: right;
		width: 120px;
	}
	.col-sm-12{
		padding-left: 0px;
		padding-right: 0px;
	}
	.rdio-primary{
		display: inline-block;
		margin-top: 10px;
		margin-right: 20px;
	}
	.iconsStyle{
		padding-right: 10px;
	}
	.iconsStyle span{
		cursor: pointer;
		padding-right: 10px;
	}
	.document{
		height: 34px;
		padding: 6px 12px;
		font-size: 14px;
		line-height: 1.42857143;
		color: #555;
		background-color: #fff;
		background-image: none;
		border: 1px solid #ccc;
		border-radius: 4px;
		-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
		box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
		-webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow
		 ease-in-out .15s;
		-o-transition: border-color ease-in-out .15s, box-shadow ease-in-out
		 .15s;
		transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
		margin-right: 5px;
	}

</style>
</head>
<body>
<jsp:include page="include/head.jsp" />
<div class="complexBox">
	<!-- 个人事件/其他事件 -->
	<jsp:include page="include/left.jsp" />
	<div class="customer">
<!-- 客户/联系人/客户池 -->
	<div class="panel panel-primary-head">
		<ul class="nav nav-tabs nav-line">
            <li class="active" onclick="toCompany()"><a href="#following" data-toggle="tab"><strong>单位</strong></a></li>
            <li class="" onclick="toContacts()"><a href="#following" data-toggle="tab"><strong>联系人</strong></a></li>
           <!--  <li class="" onclick="toCompanyPool()"><a href="#following" data-toggle="tab"><strong>客户池</strong></a></li> -->
            <li class="" onclick="toCount()"><a href="#following" data-toggle="tab"><strong>统计</strong></a></li>
		</ul>
		<script type="text/javascript">
			// 跳转客户(公司)
	        function toCompany() {
	        	location.href = "<%=basePath %>company/list.do";
	        }
			// 跳转联系人
	        function toContacts() {
	        	location.href = "<%=basePath %>contacts/list.do";
	        }
			
			// 跳转客户池
			function toCompanyPool() {
	        	location.href = "<%=basePath %>company/pool.do";
			}
			
			// 去统计
			function toCount() {
				location.href="<%=basePath%>count/total.do?ec=员工";
			}
			
		</script>
	</div>
	<!-- <div id="allmap"></div> -->
	<div class="panel panel-primary-head">
		<form id="inputForm" class="form-horizontal" action="<c:if test="${empty company.id }">save.do</c:if><c:if test="${!empty company.id }">update.do?id=${company.id}</c:if>" method="post">
		<div class="panel panel-primary">
			<div class="panel-heading panel-title-name">
				<h3 class="panel-title col-sm-10  pull-left">基本信息</h3>
				<div class="pull-right">
					<button id="subt" class="btn btn-default">保 存</button>
					<button class="btn btn-default" onclick="toCompany()">取 消</button>
				</div>
			</div>
			<!--基本信息-->
			<div class="panel-body">
					<div class="col-md-12">
						<div class="col-sm-6">
							<div class="form-group">
								<label class="col-sm-3 control-label"> 单位名称： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="pubname"
										name="pubname" title="请填写客户名称" value="${company.pubname }"
										placeholder="请输入客户名称..." required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 注册资金： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="pubregcapital"
										name="pubregcapital" title="请填写公司注册资金"
										value="${company.pubregcapital }" placeholder="注册资金..."
										required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 法人： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="pubman"
										name="pubman" title="请填写法人代表" value="${company.pubman }"
										placeholder="有效性法人..." required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 城市： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="city" name="city"
										title="请填写法人代表" value="${company.city }" placeholder="请输入所属城市"
										required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 国籍： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="pubnation"
										name="pubnation" title="请填写法人代表" value="${company.pubnation }"
										placeholder="所属国籍" required />
								</div>
							</div>
							
							<div class="form-group">
							<label class="col-sm-3 control-label">标签：</label>

							 <div class="col-sm-8" id="dictionDiv">
								<select class="form-control" id="dicname" name="dicname"></select>
							</div>
						</div>
							 <div class="form-group">
								<label for="gender" class="col-sm-4 control-label">类型：</label>
								<div class="col-sm-8">
									<div class="rdio rdio-primary">
										<input type="radio" id="male" name="gender" /> <label
											for="male">个人</label>
									</div>
									
									<div class="rdio rdio-primary">
										<input type="radio" id="female" name="gender" /> <label
											for="female">单位</label>
									</div>
									
									<label id="genderError" class="error"></label>
								</div>
							</div> 
							<!-- <div class="form-group">
								<label class="col-sm-3 control-label"> 状态： </label>
								<div class="col-sm-8 options-pull">
									<select class="col-sm-12" name="publictags" title="请选择客户状态"
										data-placeholder="请选择一项" required>
										<option value="">请选择一项</option>
										<option value="无意向">无意向</option>
										<option value="有意向">有意向</option>
										<option value="已签单">已签单</option>
									</select>
								</div>
							</div> -->
						</div>
						<div class="col-sm-6">
							<!-- <input type="hidden" id="fieldss" name="fieldss"/> -->
							<div class="form-group">
								<label class="col-sm-3 control-label"> 注册码： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="pubzjf"
										name="pubzch" title="公司注册码" value="${company.pubzch }"
										placeholder="公司注册码"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 纳税识别号： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="publsrsbh"
										name="publsrsbh" title="请输入纳税人识别号" value="${company.publsrsbh }"
										placeholder="纳税人识别号" required/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 电话： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="pubtel"
										name="pubtel" title="请填写公司电话" value="${company.pubtel }"
										placeholder="公司(客户)办公电话 " required/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 地址： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="pubadd"
										name="pubadd" title="请规范输入XX市XX区(县)地址" value="${company.pubadd }"
										 placeholder="请规范输入XX市XX区(县)注册地址" required/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 电子邮箱： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="pubmail"
										name="pubmail" title="请填写单位(客户)邮箱" value="${company.pubmail }"
										placeholder="请填写单位(客户)邮箱" required/>
								</div>
							</div>
							  <div class="form-group">
								<label class="col-sm-3 control-label"> 负责人： </label>
								<div class="col-sm-8 options-pull">
									<select class="col-sm-12" name="persionid" id="persionid" required>
										<option value="0">选择负责人</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 行业： </label>
								<div class="col-sm-8 options-pull">
									<select class=" col-sm-12" title="请选择所属行业"
										data-placeholder="请选择一项" name="pubindustry" id="pubindustry" required>
										<option value="">请选择所属行业</option>
										<option value="服装">服装</option>
										<option value="计算机IT">计算机IT</option>
										<option value="机械及行业设备">机械及行业设备</option>
									</select> <label class="error" for="pubindustry"></label>
								</div>
							</div>
						</div>
					</div>
			</div>
			<!--自定义信息-->
			<c:if test="${empty company.id }">
				<div class="panel panel-primary" id="">
					<div class="panel-heading panel-title-name">
						<h3 class="panel-title col-sm-6">自定义信息</h3>
					<a href="javascript:addField();" class="btn-primary pull-right tooltips"
								data-original-title="添加自定义信息"><!-- <i class="glyphicon glyphicon-plus"></i> -->添加</a>
					<input type="hidden" name="zidinyiValue" value="" id="zidinyi"/>
					</div>
				</div>
				<div id="body_field" class="panel-body">
					<!-- <div class="col-sm-12">
						<div class="form-group">
							<label class="col-sm-1 control-label">标签:</label>

							 <div class="col-sm-5" id="dictionDiv">
								<select class="form-control" id="dicname" name="dicname"></select>
							</div>
						</div>
					</div> -->

					<!-- <div class="col-sm-6"> -->
					
					<!-- <div class="col-sm-12" id ="havefield_one" >
						<div class="form-group">
							<label class="col-sm-1 control-label"></label>
							<div class="col-sm-2 control-label">
								<input class="form-control" type="text" id="fieldname"
									name="fieldsList[0].fieldname" placeholder="输入字段名称" />
							</div>
							<div class="col-sm-2  control-label options-pull">
								<select class="col-sm-12" name="fieldsList[0].fieldtype" id="fieldtype_one">
									<option value="">选择类型</option>
									<option value="0">文本</option>
									<option value="1">图片</option>
									<option value="2">附件</option>
								</select>
							</div>
							<div id="div_fieldval_one" class="col-sm-4  control-label options-pull">
								<input class="form-control" type="text" id="fieldval"
									name="fieldsList[0].fieldval" placeholder="输入自定义信息" />
								
							</div>
							<div class="control-label options-pull">
								<a href='javascript:deleteField("one");' class='pull-right control-label col-sm-1 tooltips ' data-original-title='删除' title="删除"><i class='fa fa-trash-o'></i></a>
							</div>
							<div class="control-label iconsStyle" style="padding-top: 15px;">
								<span class="glyphicon glyphicon-ok" title="保存" onclick="saveField('one')"></span>
								<span class="glyphicon glyphicon-remove" title="删除" onclick="deleteField('one')"></span>
							</div>
							
							<input class="" type="hidden" id="fieldatt" name="fieldsList[0].fieldatt" /> 
							
						</div>
						
					</div> -->
				</div>
				</c:if>
				<!--联系人信息-->
				<c:if test="${empty company.id }">
			<div class="panel panel-primary">
				<div class="panel-heading panel-title-name">
					<h3 class="panel-title col-sm-6">联系人信息</h3>
				</div>
			</div>
			<div class="panel-body">

				<div class="col-md-12">
					<div class="col-sm-6">
						<div class="form-group">
							<label class="col-sm-3 control-label"> 首要联系人： </label>
							<div class="col-sm-8 zidingyi">
								<input class="form-control" type="text" id="conname"
									name="conname" placeholder="首要联系人" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"> 办公电话： </label>
							<div class="col-sm-8 zidingyi">
								<input class="form-control" type="text" id="contel"
									name="contel" placeholder="办公电话" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"> 手机： </label>
							<div class="col-sm-8">
								<input class="form-control" type="text" id="conmobile"
									name="conmobile" placeholder="手机" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"> Email： </label>
							<div class="col-sm-8">
								<input class="form-control" type="text" id="conmail"
									name="conmail" placeholder="电子邮箱" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"> 传真： </label>
							<div class="col-sm-8">
								<input class="form-control" type="text" name="confax"
									placeholder="传真号码" title="请填写传真号码" value="${contacts.confax }"
									placeholder="请输入传真号码..." />
							</div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<label class="col-sm-3 control-label"> 职务： </label>
							<div class="col-sm-8 zidingyi">
								<input class="form-control" type="text" id="conjob"
									name="conjob" placeholder="职务" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"> 部门： </label>
							<div class="col-sm-8 zidingyi">
								<input class="form-control" type="text" id="conrole"
									name="conrole" placeholder="工作范围" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"> 上级： </label>
							<div class="col-sm-8 zidingyi">
								<input class="form-control" type="text" name="rootname"
									placeholder="直接上级" title="上级" value="${company.contacts.rootname }"
									placeholder="联系人上级" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"> QQ： </label>
							<div class="col-sm-8 zidingyi">
								<input class="form-control" type="text" name="conqq"
									placeholder="联系QQ" title="请填写联系QQ" value="${contacts.conqq }"
									placeholder="请输入联系QQ..." />
							</div>
						</div>
						<!-- <div class="form-group">
							<label class="col-sm-3 control-label"> 负责人： </label>
							<div class="col-sm-8 options-pull">
								<select class=" col-sm-12">
									<option>张三</option>
								</select>
							</div>
						</div> -->
					</div>
				</div>

			</div>
</c:if>
<!--信息描述-->
			<!-- 
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">描述信息</h3>
				</div>
			</div>
			<div class="panel-body">

				<div class="form-group">
					<label class="col-sm-1 control-label"> 备注： </label>
					<div class="col-sm-10">
						<textarea class="form-control" rows="5" name="bz"></textarea>
					</div>
				</div>
			</div> -->
			<div class="panel-footer">
				<div class="row">
					<div class="col-sm-0 col-sm-offset-5">
						<button type="submit" id="sub" class="btn btn-primary mr5">保存</button>
						<!-- <button type="reset" class="btn btn-dark" >重置</button> -->
						<button class="btn btn-danger deleteButton" onclick="toCompany()">取消</button>
						<!-- 红色按钮 class="btn btn-danger deleteButton" -->
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>
</div>
</div>

	<script>
	var attid ="";
	var fieldid ="";
	var addfieldid = 0;
	var tagid = "";
	var x = "";
     
	jQuery(document).ready(function(){
		
		selectAllper();
		
		addField();//添加一个自定义信息
		initZidiyiID();
		$("#sub,#subt").click(function(){
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
		});
		
		

		  init();
		  fun();
		  
		  jQuery("#persionid").select2();
		/*  jQuery('#dicname').tagsInput({
		    	height:'34px',
		    	width:'auto',
		    	 onAddTag:"callback_function", 
		    	});  */
		    
		   $("body").on("blur", ".maininput", function () {
			   //var xx = $("#dicname").val();
		    	var dicname = "";
		    	var x1 = $("#dicname").val();

		    	if($(this).val() != ""){
		    		 setTimeout(function(){ 
		    			var x2 = $("#dicname").val();
		    			if(x1==null && x2 ==null){
		    				//if(confirm("添加:'"+$(".maininput").val()+"'标签?")){
			    				
			    				dicname = $(".maininput").val();
			    				$.ajax({
							  		type: 'POST',
							 	 	url: '<%=basePath%>company/savedic.do',
							  		data: {'dicname':dicname,'dictype':0},
							  		dataType: 'json',
							  		success: function(data) {
							  			if(data.status == 0){
							  				var dictionaryid = data.id;

							  				var dictionaryname = data.dicname;
							  				
							  				//$(".holder").append("<li class='bit-box' rel='"+dictionaryid+"'>"+dictionaryname+"<a class='closebutton' href='#'></a></li>");
							  				$("#dicname").append("<option value='"+dictionaryid+"' selected='selected' class='selected'>"+dictionaryname+"</option>");
							  				$("<li class='bit-box' rel='"+dictionaryid+"'>"+dictionaryname+"<a class='closebutton' href='#'></a></li>").insertBefore("#dicname_annoninput");
							  				$(".maininput").val("");
							  				//alert($("#dicname").val());
							  			}else if(data.status == -1){
							  				alert(data.msg);
							  			}
							  		},error:function(x){
										//alert(x.status);
										}
								});
			    			//}
		    			}else{
		    				if(x1.join("") === x2.join("")){
		    				//alert("?");
		    					//if(confirm("添加:'"+$(".maininput").val()+"'标签?")){
		    				
			    					dicname = $(".maininput").val();
			    					$.ajax({
							  			type: 'POST',
							 	 		url: '<%=basePath%>company/savedic.do',
							  			data: {'dicname':dicname,'dictype':0},
							  			dataType: 'json',
							  			success: function(data) {
							  				if(data.status == 0){
								  				var dictionaryid = data.id;							  	
								  				
								  				var dictionaryname = data.dicname;
								  				//$(".holder").append("<li class='bit-box' rel='"+dictionaryid+"'>"+dictionaryname+"<a class='closebutton' href='#'></a></li>");
								  				$("#dicname").append("<option value='"+dictionaryid+"' selected='selected' class='selected'>"+dictionaryname+"</option>");
								  				$("<li class='bit-box' rel='"+dictionaryid+"'>"+dictionaryname+"<a class='closebutton' href='#'></a></li>").insertBefore("#dicname_annoninput");
								  				$(".maininput").val("");
								  				//alert($("#dicname").val());
								  			}else if(data.status == -1){
								  				alert(data.msg);
								  			}
							  			},error:function(x){
											//alert(x.status);
											}
									});
			    				//}
		    				}
		    			}
		    		 },500); 
		    		
		    	}/* else{
		    		alert("请输入关键字!");
		    	} */

		    });  
		    	$("#female").attr("checked", true);
							
		$("#fieldtype_one").change(function(){
			
			selectFieldt($(this).val(),"one");
		});
	
		$("body").on("click", "#fieldval_upload_one", function () {
			var fieldval = $("#file").val();
			
			if(fieldval!=""){
				$.ajaxFileUpload({
					url:"<%=basePath%>upload.do",
					type : "post",
					fileElementId : "file",
					dataType : "JSON",
					secureuri : false,
					success : function(data) {

						var obj = eval("(" + data + ")");

						attid += obj.data.id +",";
						$("#fieldatt").val(attid);
						
						var sourcepath = obj.data.sourcepath;
						$("#fieldval").val(sourcepath);
						fieldtype = $("#fieldtype_one").val();
						//alert(fieldtype);
						fieldname = $("#fieldname").val();
						//alert(fieldname);
						//alert("上传图片或附件成功!点击右边保存按钮保存此自定义信息!");
						if(fieldtype==1){
							$("#div_append_one").append("<img  src='../"+sourcepath+"' class='img-thumbnail' style='width:100px;height:50px;'/>");
						}
						if(fieldtype==2){
							$("#div_append_one").append("  <span><i class='fa fa-file-text-o'></i></span>");
						}
					},
					error:function(x){
						//alert(x.status);
						}
				});
			}else{
				alert("请选择要保存的图片或附件!");
			};
		});


	});
	function fun(){
		document.all.pubindustry.value = "${company.pubindustry}";
		
		
		var list = eval(${diclist});
		if(list !=null){
		for(var i= 0;i<list.length;i++){
			var dictionary = list[i];
			//alert(dictionary.id);
			//alert(dictionary.dicname);
			//alert($("#dicname"));
			$("#dicname").append("<option value='"+dictionary.id+"' selected='selected' class='selected'>"+dictionary.dicname+"</option>");
			$("<li class='bit-box' rel='"+dictionary.id+"'>"+dictionary.dicname+"<a class='closebutton' href='#'></a></li>").insertBefore("#dicname_annoninput");
		};
		}
	}
	/* function biao(){
		$("#dicname").append("<option value='"+dictionaryid+"' selected='selected' class='selected'>"+dictionaryname+"</option>");
			$("<li class='bit-box' rel='"+dictionaryid+"'>"+dictionaryname+"<a class='closebutton' href='#'></a></li>").insertBefore("#dicname_annoninput");
	} */
	 function init(){
		$("#dictionDiv").html("<select class='form-control' id='dicname' name='dicname' ></select>");
		$("#dicname").fcbkcomplete({
			json_url: "<%=basePath%>dictionary/conList.do",
			filter_selected: true,
			addontab: true,                   
	        height: 3
		});
	}
	
	function initZidiyiID(){
		$("#zidinyi").val(randomString(32)+"");
		//$(".zidinyi").attr("id",randomString(32)+"");
	}
	
function selectAllper(){
		
		$.ajax({
			  type: 'POST',
			  url: '<%=basePath%>count/selectAllper.do',
			  //data: {'id':id},
			  dataType: 'json',
			  success: function(data) {

				  /*   $.each(data,function(i,company){
				    	$("#select-basic").append("<option value='"+company.id+"'>"+company.pubname+"</option>");
				    }); */
				    var va="${company.admin.id}";
				    var v=eval(data);
				    for(var i=0;i<v.length;i++){
						var values=v[i];
						var html = "";
						if(values.id == va){
							$("#select2-chosen-1").text(values.pername);
							html = "selected=selected";
						}
						$("#persionid").append("<option value='"+values.id+"' "+html+">"+values.pername+"</option>");
					}
				
			  },
			});
		
	}
	
	function selectFieldt(val,addfieldid){
		if(val==1){
			$("#div_fieldval_"+addfieldid).css("width","auto");
			var html = "<input type='file' id='file"+addfieldid+"' name='file' style='display: inline-block;' accept='.jpe,.jpeg,.jpg,.png,.gif'/>";
			html += "<input type='hidden' id='fieldval"+addfieldid+"' name='fieldsList["+addfieldid+"].fieldval'/>";
			html +="<span id='div_append_one' class='control-label'><input type='button'  id='fieldval_upload_"+addfieldid+"' value='上传'/></span>";
			$("#div_fieldval_"+addfieldid).html(html);
			
			/* $("#div_fieldval_"+addfieldid+" > input").remove();
			$("#div_fieldval_"+addfieldid).html("<input type='file' id='file"+addfieldid+"' name='file'/>");
			$("#div_fieldval_"+addfieldid).append("<input type='hidden' id='fieldval"+addfieldid+"' name='fieldsList["+addfieldid+"].fieldval'/>");
			$("#div_append_"+addfieldid).html("<input type='button'  id='fieldval_upload_"+addfieldid+"' value='上传'/>"); */
		}
		if(val ==2){
			$("#div_fieldval_"+addfieldid).css("width","auto");
			var html = "<input type='file' id='file"+addfieldid+"' name='file' style='display: inline-block;' />";
			html += "<input type='hidden' id='fieldval"+addfieldid+"' name='fieldsList["+addfieldid+"].fieldval'/>";
			html +="<span id='div_append_one' class='control-label'><input type='button'  id='fieldval_upload_"+addfieldid+"' value='上传'/></span>";
			$("#div_fieldval_"+addfieldid).html(html);
			
			/* $("#div_fieldval_"+addfieldid+" > input").remove();
			$("#div_fieldval_"+addfieldid).html("<input type='file' id='file"+addfieldid+"' name='file'/>");
			$("#div_fieldval_"+addfieldid).append("<input type='hidden' id='fieldval"+addfieldid+"' name='fieldsList["+addfieldid+"].fieldval'/>");
			$("#div_append_"+addfieldid).html("<input type='button'  id='fieldval_upload_"+addfieldid+"' value='上传'/>"); */
		} 
		if(val ==0){
			$("#div_fieldval_"+addfieldid).css("width","33.33333333%");
			if($("#div_fieldval_"+addfieldid+" > *")!= null && $("#div_append_one > *")!= null ){
				$("#div_fieldval_"+addfieldid+" > *").remove();
				$("#div_append_"+addfieldid+" > *").remove();
				$("#div_fieldval_"+addfieldid).html("<input class='form-control' type='text' id='fieldval' name='fieldsList["+addfieldid+"].fieldval' placeholder='输入自定义信息' />");
			}
		}
	}
	 function selectField(val){
		if(val==1){
			/* $("#div_fieldval_one > input").remove(); */
			$("#div_fieldval_one").css("width","auto");
			var html = "<input type='file' id='file' name='file' style='display: inline-block;' accept='.jpe,.jpeg,.jpg,.png,.gif'/>";
			html += "<input type='hidden' id='fieldval' name='fieldsList["+addfieldid+"].fieldval'/>";
			/* html += "<input class='document' type='text' id='fieldval' name='fieldsList["+addfieldid+"].fieldval' placeholder='输入描述信息' />"; */
			html += "<span id='div_append_one' class='control-label'><input type='button'  id='fieldval_upload_one' value='上传'/></span>";
			
			$("#div_fieldval_one").html(html);
			
			/* $("#div_fieldval_one").html("<input type='file' id='file' name='file'/>");
			$("#div_fieldval_one").append("");
			$("#div_append_one").html("<input type='button'  id='fieldval_upload_one' value='上传'/>"); */
		}
		if(val ==2){
			$("#div_fieldval_one").css("width","auto");
			var html = "<input type='file' id='file' name='file' style='display: inline-block;'/>";
			html += "<input type='hidden' id='fieldval' name='fieldsList["+addfieldid+"].fieldval'/>";
			/* html += "<input class='document' type='text' id='fieldval' name='fieldsList["+addfieldid+"].fieldval' placeholder='输入描述信息' />"; */
			html +="<span id='div_append_one' class='control-label'><input type='button'  id='fieldval_upload_one' value='上传'/></span>";
			$("#div_fieldval_one").html(html);
			
			/* $("#div_fieldval_one > input").remove();
			$("#div_fieldval_one").html("<input type='file' id='file' name='file'/>");
			$("#div_fieldval_one").append("<input type='hidden' id='fieldval' name='fieldsList["+addfieldid+"].fieldval'/>");
			$("#div_append_one").html("<input type='button'  id='fieldval_upload_one' value='上传'/>"); */
		} 
		if(val ==0){
			$("#div_fieldval_one").css("width","33.33333333%");
			if($("#div_fieldval_one > *")!= null && $("#div_append_one > *")!= null ){
				$("#div_fieldval_one > *").remove();
				$("#div_append_one > *").remove();
				$("#div_fieldval_one").html("<input class='form-control' type='text' id='fieldval' name='fieldsList["+addfieldid+"].fieldval' placeholder='输入自定义信息' />");
			}
		}
	} 
	
	function addField(){
		addfieldid++;
		$("#body_field").append(
				"<div class='col-sm-12' id ='havefield_"+addfieldid+"'>"
				+"<div class='form-group'>"
				//+"<label class='col-sm-1 control-label'></label>"
				+"<div class='col-sm-2 control-label'>"
				+"<input class='form-control' type='text' id='fieldname"+addfieldid+"'"
				+"			name='fieldname' placeholder='输入字段名称' />"
				+"	</div>"
				+"	<div class='col-sm-2  control-label options-pull'>"
				+"		<select class='col-sm-12' name='fieldtype' id='fieldtype_"+addfieldid+"'>"
				+"			<option value='0'>文本</option>"
				+"			<option value='1'>图片</option>"
				+"			<option value='2'>附件</option>"
				+"		</select>"
				+"	</div>"
				+"	<div id='div_fieldval_"+addfieldid+"' class='col-sm-4  control-label options-pull'>"
				+"		<input class='form-control' type='text' id='fieldval"+addfieldid+"'"
				+"			name='fieldval' placeholder='输入自定义信息' />"
				+"	</div>"
				+"<div class='control-label iconsStyle' style='padding-top: 15px;'>"
				+"	<span class='glyphicon glyphicon-ok glyphicon_"+addfieldid+"' title='保存' onclick='saveField("+addfieldid+")'></span>"
				+"	<span class='glyphicon glyphicon-remove' title='删除' onclick='deleteField("+addfieldid+")'></span>"
				+"</div>"
				+"	<input class='' type='hidden' id='fieldatt"+addfieldid+"' name='fieldatt' /> "
				/* +"	<span id='div_append_"+addfieldid+"' class='control-label'></span> " */
				/* +"<a href='javascript:deleteField("+addfieldid+");' class='pull-right control-label col-sm-1 tooltips' data-original-title='删除' title='删除'>"
				+" <i class='fa fa-trash-o'></i></a>	" */
				+"</div>"
				+"</div>"
		);
		bindaddField(addfieldid);
		uploadFields(addfieldid);
	}
	function deleteField(id){
		$("#havefield_"+id).remove();
	}
	function bindaddField(c){

			 $("body").on("change","#fieldtype_"+c,function(){

				 selectFieldt($(this).val(),c);
				 			 
			 });
			

	}
	
	//上传文件
	function uploadFields(tab){

		$("body").on("click", "#fieldval_upload_"+tab, function () {
			var fieldval = $("#file"+tab).val();
			if(fieldval!=""){
				$.ajaxFileUpload({
					url:"<%=basePath%>upload.do",
					type : "post",
					fileElementId : "file"+tab,
					//data : {'tab':tab},
					dataType : "JSON",
					secureuri : false,
					success : function(data) {

						var obj = eval("(" + data + ")");

						attid += obj.data.id +",";
						$("#fieldatt"+tab).val(attid);
						
						var sourcepath = obj.data.sourcepath;
						$("#fieldval"+tab).val(sourcepath);
						fieldtype = $("#fieldtype_"+tab).val();
						//alert(fieldtype);
						fieldname = $("#fieldname"+tab).val();
						//alert(fieldname);
						//alert(fieldtype);
						if(fieldtype==1){
							$("#div_append_"+tab).append("<img  src='../"+sourcepath+"' class='img-thumbnail' style='width:100px;height:50px;'/>");
						}
						if(fieldtype==2){
							$("#div_append_"+tab).append("<span><i class='fa fa-file-text-o'></i></span>");
						}
					},
					error:function(x){
						//alert(x.status);
						}
				});
			}else{
				alert("请选择要保存的图片或附件!");
			}
		});

	}
	//保存自定义信息
	
	function saveField(obj){
		
		var id = $("#zidinyi").val();
		
		var fileName = $("#fieldname"+obj).val();
		var fileType = $("#fieldtype_"+obj).val();
		var fileContent = $("#fieldval"+obj).val();
		
		var url = "<%=basePath%>company/saveFildeList.do";
		var data = {
			fieldname:fileName,
			fieldtype:fileType,
			fieldval:fileContent,
			fildNum:id
		};
		$.get(url,data,function(result){
			//alert(obj);
			var json = eval("("+result+")");
			//console.log(json);
			if(json.status == "0"){
				$(".glyphicon_"+obj).removeClass("glyphicon-ok");
				$(".glyphicon_"+obj).removeClass("glyphicon");
				$(".glyphicon_"+obj).removeAttr("onclick");
				$(".glyphicon_"+obj).text("已保存");
			}else{
				
			}
		});
	}
	
	</script>
</body>
</html>