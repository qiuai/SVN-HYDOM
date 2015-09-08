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
<title>新增联系人</title>
<link href="<%=basePath%>bootstrap/css/bootstrap.css" rel="stylesheet"
	media="screen" />
<link href="<%=basePath%>bootstrap/css/style.default.css"
	rel="stylesheet" />
<link href="<%=basePath%>res/css/pager.css" rel="stylesheet" />
<link rel="stylesheet"
	href="<%=basePath%>bootstrap/css/jquery.gritter.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>bootstrap/css/select2.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>bootstrap/css/style.css" />
	
<link href="<%=basePath%>bootstrap/css/jquery.tagsinput.css" rel="stylesheet" />
<style>
	div#contags_tagsinput.tagsinput{
		height:34px;
	}
	.select-basic{
		width:337px;
	}
	div#s2id_select-basic.select2-container{
		height:34px;
		width:323px;
	}
	
</style>
<script src="<%=basePath%>bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery.cookies.js"></script>
<script src="<%=basePath%>bootstrap/js/pace.min.js"></script>
<script src="<%=basePath%>bootstrap/js/retina.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery.gritter.min.js"></script>
<script src="<%=basePath%>bootstrap/js/custom.js"></script>
<script src="<%=basePath%>res/js/common.js"></script>
<script src="<%=basePath%>res/js/list.js"></script>

<script src="<%=basePath%>bootstrap/js/jquery-ui-1.10.3.min.js"></script>
<script src="<%=basePath%>bootstrap/js/modernizr.min.js"></script>
<script src="<%=basePath%>bootstrap/js/select2.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery.validate.min.js"></script>

<script src="<%=basePath%>bootstrap/js/jquery.tagsinput.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery.autogrow-textarea.js"></script>
        <script src="<%=basePath%>bootstrap/js/jquery.mousewheel.js"></script>
        <script src="<%=basePath%>bootstrap/js/toggles.min.js"></script>
        <script src="<%=basePath%>bootstrap/js/bootstrap-timepicker.min.js"></script>
        <script src="<%=basePath%>bootstrap/js/jquery.maskedinput.min.js"></script>
        <script src="<%=basePath%>bootstrap/js/colorpicker.js"></script>
        <script src="<%=basePath%>bootstrap/js/dropzone.min.js"></script>
        
         <script src="<%=basePath %>bootstrap/js/jquery-ui-1.10.3.min.js"></script>
        <script src="<%=basePath %>bootstrap/js/modernizr.min.js"></script>
        <script src="<%=basePath %>bootstrap/js/select2.min.js"></script>
        <script src="<%=basePath %>bootstrap/js/jquery.validate.min.js"></script>
        
        <script src="<%=basePath %>bootstrap/js/bootstrap.js"></script>
        
        <script src="<%=basePath %>bootstrap/js/ajaxfileupload.js"></script>
        
        <script src="<%=basePath %>bootstrap/js/jquery.fcbkcomplete.js"></script>
         
         <link href="<%=basePath %>bootstrap/css/style_account.css" rel="stylesheet" />

</head>
<body>
	<!-- <div class="pace  pace-inactive"> -->
	<div class="panel panel-primary-head">
		<ul class="nav nav-tabs nav-line">
			<li class="" onclick="toCompany()"><a href="#following"
				data-toggle="tab"><strong>单位</strong></a></li>
			<li class="active" onclick="toContacts()"><a href="#following"
				data-toggle="tab"><strong>联系人</strong></a></li>
			<li class="" onclick="toCompanyPool()"><a href="#following"
				data-toggle="tab"><strong>客户池</strong></a></li>
			<li class="" onclick="toCount()"><a href="#following"
				data-toggle="tab"><strong>统计</strong></a></li>
		</ul>
	</div>
	<script type="text/javascript">
			// 跳转客户(公司)
	        function toCompany() {
	        	location.href = "<%=basePath%>company/list.do";
	        }
			// 跳转联系人
	        function toContacts() {
	        	location.href = "<%=basePath%>contacts/list.do";
	        }
			
			// 跳转客户池
			function toCompanyPool() {
	        	location.href = "<%=basePath%>company/pool.do";
			}
			
			// 去统计
			function toCount() {
				location.href="<%=basePath%>count/total.do?ec=员工";
		}
	</script>
	<div class="panel-default">
		<form id="inputForm" class="form-horizontal"
			action="<c:if test="${empty contacts.id }">save.do</c:if><c:if test="${!empty contacts.id }">update.do?id=${contacts.id}</c:if>"
			method="post">
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<!-- <div class="panel-btns"> -->
							<h3 class="panel-title">联系人信息</h3>
							<!-- </div> -->
						</div>
					</div>
				</div>
			</div>

			<div class="panel-body">
				<div class="col-md-12">
					<div class="col-sm-6">
						<div class="form-group">
							<label class="col-sm-3 control-label"> 单位名称： </label>
							<div class="col-sm-6">
								<select id="select-basic" data-placeholder="请选择单位(客户)" name="companyid" ><!-- name="company.id" -->
									<option value="0">请选择单位(客户)</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"> 姓名： </label>
							<div class="col-sm-8">
								<input class="form-control" type="text" name="conname"
									placeholder="请输入联系人" title="请填写联系人姓名"
									value="${contacts.conname }" placeholder="请输入客户名称..." required />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label"> 职务： </label>
							<div class="col-sm-8">
								<input class="form-control" type="text" name="conjob"
									placeholder="请输入联系人职务" title="所任职务" value="${contacts.conjob }"
									placeholder="请输入客户名称..." required />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"> 部门： </label>
							<div class="col-sm-8">
								<input class="form-control" type="text" name="conrole"
									placeholder="所在部门" title="所属部门" value="${contacts.conrole }"
									placeholder="请输入客户名称..." required />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label"> 标签： </label>
							<%-- <div class="col-sm-8">
								<input class="form-control" id="contags" name="contags"
									value="${contacts.contags }" placeholder="联系人标签" title="联系人标签"
									style="width: auto; height: 34px;" placeholder="请输入联系人标签..." />
							</div> --%>
							<div class="col-sm-8" id="dictionDiv">
								<select class="form-control" id="contags" name="contags"></select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"> 上级： </label>
							<div class="col-sm-8">
								<input class="form-control" type="text" name="rootname"
									placeholder="直接上级" title="上级" value="${contacts.rootname }"
									placeholder="联系人上级" required />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"> 首要联系人： </label>
							<div class="col-sm-8">
								<div class="checkbox block">
									<label> <input type="checkbox" id="isdefault" name="isdefault"/> 选中代表是首要联系人
									</label>
								</div>
							</div>
						</div>

					</div>


					<div class="col-sm-6">
						<div class="form-group">
							<label class="col-sm-3 control-label"> 办公电话： </label>
							<div class="col-sm-8">
								<input class="form-control" type="text" id="contel"
									name="contel" placeholder="办公电话" title="办公电话"
									value="${contacts.contel }" placeholder="请输入办公电话..." required />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"> 传真： </label>
							<div class="col-sm-8">
								<input class="form-control" type="text" name="confax"
									placeholder="传真号码" title="请填写传真号码" value="${contacts.confax }"
									placeholder="请输入传真号码..." required />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"> 手机： </label>
							<div class="col-sm-8">
								<input class="form-control" type="text" name="conmobile"
									placeholder="联系电话" title="请填写手机号"
									value="${contacts.conmobile }" placeholder="请输入手机号码..."
									required />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"> Email： </label>
							<div class="col-sm-8">
								<input class="form-control" type="text" name="conmail"
									placeholder="请输入电子邮箱" title="请填写电子邮箱"
									value="${contacts.conmail }" placeholder="请输入电子邮箱..." required />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"> QQ： </label>
							<div class="col-sm-8">
								<input class="form-control" type="text" name="conqq"
									placeholder="联系QQ" title="请填写联系QQ" value="${contacts.conqq }"
									placeholder="请输入联系QQ..." required />
							</div>
						</div>
						<input type="hidden" id="fieldss" name="fieldss"/>
						<!-- <div class="form-group">
							<label class="col-sm-3 control-label"> 负责人： </label>
							<div class="col-sm-8 options-pull">
								<select class="col-sm-12">
									<option>张三</option>
								</select>
							</div>
						</div> -->
					</div>
					<div class="col-md-12">
						
					</div>
				</div>
			</div>

			<!-- <div class="col-md-12">
				<div class="form-group">
					<label class="col-sm-1 control-label"> 自定义标签： </label>
					<div class="col-sm-4 options-pull">
						<select id="select-search-hide" data-placeholder="Choose One"
							class="form-control">
							<option value="UT">Utah</option>
							<option value="WY">Wyoming</option>
						</select>
					</div>
				</div>
			</div> -->
			<c:if test="${empty contacts.id }">
			<div class="panel panel-primary">
					<div class="panel-heading panel-title-name">
						<h3 class="panel-title col-sm-6">自定义信息</h3>
						<a href="javascript:addField();" class="btn-primary pull-right tooltips"
								data-original-title="添加自定义信息"><!-- <i class="glyphicon glyphicon-plus"></i> -->添加</a>
					</div>
				</div>
			<div id="body_field" class="panel-body">
				<div class="col-sm-12" id ="havefield_one" >
						<div class="form-group">
							<label class="col-sm-1 control-label"></label>
							<div class="col-sm-2 control-label">
								<input class="form-control" type="text" id="fieldname"
									name="fieldsList[0].fieldname" placeholder="输入自定义字段名称" />
							</div>
							<div class="col-sm-2  control-label options-pull">
								<select class="col-sm-12" name="fieldsList[0].fieldtype" id="fieldtype_one">
									<!-- <option value="">选择类型</option> -->
									<option value="0">文本</option>
									<option value="1">图片</option>
									<option value="2">附件</option>
								</select>
							</div>
							<div id="div_fieldval_one" class="col-sm-4  control-label options-pull">
								<input class="form-control" type="text" id="fieldval"
									name="fieldsList[0].fieldval" placeholder="输入自定义信息" />
							</div>
							<a href='javascript:deleteField("one");' class='pull-right control-label col-sm-1 tooltips' data-original-title='删除' title="删除"><i class='fa fa-trash-o'></i></a>
							<input class="" type="hidden" id="fieldatt" name="fieldsList[0].fieldatt" /> 
							<span id="div_append_one" class="control-label"></span> 
							<!-- <a href="javascript:addField();" class="pull-right control-label col-sm-1 tooltips"
								data-original-title="添加自定义信息"><i class="glyphicon glyphicon-plus"></i></a> -->

						</div>
						
					</div>
			</div>
			</c:if>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="panel-btns">
						<!-- <a href="" class="panel-minimize tooltips" data-toggle="tooltip"
							title="Minimize Panel"><i class="fa fa-minus"></i></a> <a href=""
							class="panel-close tooltips" data-toggle="tooltip"
							title="Close Panel"><i class="fa fa-times"></i></a> -->
					</div>
					<!-- panel-btns -->
					<h3 class="panel-title">描述信息</h3>
				</div>
			</div>
			<div class="panel-body">
				<label class="col-sm-1 control-label"> 备注： </label>
				<div class="col-sm-10">
					<textarea class="form-control" name="bz" rows="5">${contacts.bz }</textarea>
				</div>
			</div>
			<!-- panel-body -->


			<!-- <div class="btn-list btns col-sm-2">
				<button id="sub" class="btn btn-primary deleteButton">保存</button>
				<button class="btn btn-danger deleteButton" onclick="toContacts()">取消</button>
			</div> -->
			<div class="panel-footer">
				<div class="row">
					<div class="col-sm-0 col-sm-offset-5">
						<button type="submit" id="sub" class="btn btn-primary mr5">保存</button>
						<!-- <button type="reset" class="btn btn-dark" >重置</button> -->
						<button class="btn btn-danger deleteButton" onclick="toContacts()">取消</button>
						<!-- 红色按钮 class="btn btn-danger deleteButton" -->
					</div>
				</div>
			</div>


		</form>
	</div>

	<script>
	var attid ="";
	var fieldid ="";

	var addfieldid = 0;
		jQuery(document).ready(function() {
			selectAllcmp();
			$("#sub").click(function(){
		   jQuery("#inputForm").validate({highlight : function(element) {
				jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
				},
				success : function(element) {
					jQuery(element).closest('.form-group').removeClass('has-error');
					},
				errorLabelContainer : jQuery("#inputForm div.errorForm")
			});});
 
		   init();
		   fun();
		   jQuery("#select-basic").select2();
		   
		   $("#isdefault").attr("checked",true);
		   $("#isdefault").click(function(){

			   if($(this).attr("checked")==undefined){

				   $(this).val("false");
			   }
			   
		   });
		   
		   $("body").on("blur", ".maininput", function () {
			   //var xx = $("#dicname").val();
		    	var dicname = "";
		    	//var _this = this;
		    	var x1 = $("#dicname").val();

		    	if($(this).val() != ""){
		    		 setTimeout(function(){ 
		    			var x2 = $("#dicname").val();
		    			if(x1==null && x2 ==null){	
			    				
			    				dicname = $(".maininput").val();
			    				$.ajax({
							  		type: 'POST',
							 	 	url: '<%=basePath%>contacts/savedic.do',
							  		data: {'dicname':dicname,'dictype':1},
							  		dataType: 'json',
							  		success: function(data) {
							  			if(data.status == 0){
							  				var dictionaryid = data.id;
							  				var dictionaryname = data.dicname;
							  				
							  				//$(".holder").append("<li class='bit-box' rel='"+dictionaryid+"'>"+dictionaryname+"<a class='closebutton' href='#'></a></li>");
							  				$("#contags").append("<option value='"+dictionaryid+"' selected='selected' class='selected'>"+dictionaryname+"</option>");
							  				$("<li class='bit-box' rel='"+dictionaryid+"'>"+dictionaryname+"<a class='closebutton' href='#'></a></li>").insertBefore("#contags_annoninput");
							  				$(".maininput").val("");
							  			}else if(data.status == -1){
							  				alert(data.msg);
							  			}
							  		},error:function(x){
										
										}
								});

		    			}else{
		    				if(x1.join("") === x2.join("")){
		    				//alert("?");
		    					//if(confirm("添加:'"+$(".maininput").val()+"'标签?")){
		    				
			    					dicname = $(".maininput").val();
			    					$.ajax({
							  			type: 'POST',
							 	 		url: '<%=basePath%>contacts/savedic.do',
							  			data: {'dicname':dicname,'dictype':1},
							  			dataType: 'json',
							  			success: function(data) {
							  				if(data.status == 0){
								  				var dictionaryid = data.id;
								  				var dictionaryname = data.dicname;
								  				//$(".holder").append("<li class='bit-box' rel='"+dictionaryid+"'>"+dictionaryname+"<a class='closebutton' href='#'></a></li>");
								  				$("#contags").append("<option value='"+dictionaryid+"' selected='selected' class='selected'>"+dictionaryname+"</option>");
								  				$("<li class='bit-box' rel='"+dictionaryid+"'>"+dictionaryname+"<a class='closebutton' href='#'></a></li>").insertBefore("#dicname_annoninput");
								  				$(".maininput").val("");
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


		   $("#fieldtype_one").change(function(){
				
				selectField($(this).val());
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
							
							fieldname = $("#fieldname").val();
							
							if(fieldtype==1){
								$("#div_append_one").append("<img  src='../"+sourcepath+"' class='img-thumbnail' style='width:100px;height:50px;'/>");
							}
							if(fieldtype==2){
								$("#div_append_one").append("<span><i class='fa fa-file-text-o'></i></span>");
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


		}); 
		
	function init(){
		$("#dictionDiv").html("<select class='form-control' id='contags' name='contags' ></select>");
		$("#contags").fcbkcomplete({
			json_url: "<%=basePath%>contacts/conList.do",
			filter_selected: true,
			addontab: true,                   
		       height: 3
		});
	}		   

	function fun(){
		var list = eval(${diclist});
		if(list != null){
		for(var i= 0;i<list.length;i++){
			var dictionary = list[i];
			
			$("#contags").append("<option value='"+dictionary.id+"' selected='selected' class='selected'>"+dictionary.dicname+"</option>");
			$("<li class='bit-box' rel='"+dictionary.id+"'>"+dictionary.dicname+"<a class='closebutton' href='#'></a></li>").insertBefore("#contags_annoninput");
		};
		}
	}
	
	function selectAllcmp(){
		
		$.ajax({
			  type: 'POST',
			  url: '<%=basePath%>company/selectAllcmp.do',
			  //data: {'id':id},
			  dataType: 'json',
			  success: function(data) {

				  /*   $.each(data,function(i,company){
				    	$("#select-basic").append("<option value='"+company.id+"'>"+company.pubname+"</option>");
				    }); */
				    var va="${company.id}";
				    var v=eval(data);
				    for(var i=0;i<v.length;i++){
						var values=v[i];
						var html = "";
						if(values.id == va){
							$("#select2-chosen-1").text(values.pubname);
							html = "selected=selected";
						}
						$("#select-basic").append("<option value='"+values.id+"' "+html+">"+values.pubname+"</option>");
					}
				  //  var va="${company.id}";

					//$("#select-basic").find("option[value='"+va+"']").attr("selected",true); 
					/* $("#select-basic option").each(function(){
						var v=$(this).attr("value");
						if(v==va){
							$(this).Attr(); */
						//	$("#select2-chosen-1").text(va);
					/* 	}
					}); */
			  },
			});
		
	}	
	
	//保存自定义信息
	function selectFieldt(val,addfieldid){
		if(val==1){
			$("#div_fieldval_"+addfieldid+" > input").remove();
			$("#div_fieldval_"+addfieldid).html("<input type='file' id='file"+addfieldid+"' name='file'/>");
			$("#div_fieldval_"+addfieldid).append("<input type='hidden' id='fieldval"+addfieldid+"' name='fieldsList["+addfieldid+"].fieldval'/>");
			$("#div_append_"+addfieldid).html("<input type='button'  id='fieldval_upload_"+addfieldid+"' value='上传'/>");
		}
		if(val ==2){
			$("#div_fieldval_"+addfieldid+" > input").remove();
			$("#div_fieldval_"+addfieldid).html("<input type='file' id='file"+addfieldid+"' name='file'/>");
			$("#div_fieldval_"+addfieldid).append("<input type='hidden' id='fieldval"+addfieldid+"' name='fieldsList["+addfieldid+"].fieldval'/>");
			$("#div_append_"+addfieldid).html("<input type='button'  id='fieldval_upload_"+addfieldid+"' value='上传'/>");
		} 
		if(val ==0){
			if($("#div_fieldval_"+addfieldid+" > *")!= null && $("#div_append_one > *")!= null ){
				$("#div_fieldval_"+addfieldid+" > *").remove();
				$("#div_append_"+addfieldid+" > *").remove();
				$("#div_fieldval_"+addfieldid).html("<input class='form-control' type='text' id='fieldval' name='fieldsList["+addfieldid+"].fieldval' placeholder='输入自定义信息' />");
			}
		}
	}
	 function selectField(val){
		if(val==1){
			$("#div_fieldval_one > input").remove();
			$("#div_fieldval_one").html("<input type='file' id='file' name='file'/>");
			$("#div_fieldval_one").append("<input type='hidden' id='fieldval' name='fieldsList["+addfieldid+"].fieldval'/>");
			$("#div_append_one").html("<input type='button'  id='fieldval_upload_one' value='上传'/>");
		}
		if(val ==2){
			$("#div_fieldval_one > input").remove();
			$("#div_fieldval_one").html("<input type='file' id='file' name='file'/>");
			$("#div_fieldval_one").append("<input type='hidden' id='fieldval' name='fieldsList["+addfieldid+"].fieldval'/>");
			$("#div_append_one").html("<input type='button'  id='fieldval_upload_one' value='上传'/>");
		} 
		if(val ==0){
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
				+"<label class='col-sm-1 control-label'></label>"
				+"<div class='col-sm-2 control-label'>"
				+"<input class='form-control' type='text' id='fieldname"+addfieldid+"'"
				+"			name='fieldsList["+addfieldid+"].fieldname' placeholder='输入自定义字段名称' />"
				+"	</div>"
				+"	<div class='col-sm-2  control-label options-pull'>"
				+"		<select class='col-sm-12' name='fieldsList["+addfieldid+"].fieldtype' id='fieldtype_"+addfieldid+"'>"

				+"			<option value='0'>文本</option>"
				+"			<option value='1'>图片</option>"
				+"			<option value='2'>附件</option>"
				+"		</select>"
				+"	</div>"
				+"	<div id='div_fieldval_"+addfieldid+"' class='col-sm-4  control-label options-pull'>"
				+"		<input class='form-control' type='text' id='fieldval"+addfieldid+"'"
				+"			name='fieldsList["+addfieldid+"].fieldval' placeholder='输入自定义信息' />"
				+"	</div>"
				+"	<input class='' type='hidden' id='fieldatt"+addfieldid+"' name='fieldsList["+addfieldid+"].fieldatt' /> "
				+"	<span id='div_append_"+addfieldid+"' class='control-label'></span> "
				+"<a href='javascript:deleteField("+addfieldid+");' class='pull-right control-label col-sm-1 tooltips' data-original-title='删除' title='删除'>"
				+" <i class='fa fa-trash-o'></i></a>	"

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

						fieldname = $("#fieldname"+tab).val();

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
	</script>
</body>
</html>