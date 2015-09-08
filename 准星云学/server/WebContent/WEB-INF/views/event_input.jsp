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
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>新建单位</title>
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

<link href="<%=basePath%>bootstrap/css/jquery.tagsinput.css"
	rel="stylesheet" />
<link rel="stylesheet" href="<%=basePath%>res/css/head.css" media="screen" />

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

<script src="<%=basePath%>bootstrap/js/modernizr.min.js"></script>
<script src="<%=basePath%>bootstrap/js/select2.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery.validate.min.js"></script>

<script src="<%=basePath%>bootstrap/js/bootstrap.js"></script>

<script src="<%=basePath%>bootstrap/js/ajaxfileupload.js"></script>

<script src="<%=basePath%>bootstrap/js/jquery.fcbkcomplete.js"></script>

<%--开始--%>
 <script src="<%=basePath%>bootstrap/js/jquery-ui-1.10.3.min.js"></script>
 
 <script src="<%=basePath%>bootstrap/js/jquery.autogrow-textarea.js"></script>
 <script src="<%=basePath%>bootstrap/js/jquery.mousewheel.js"></script>
 <script src="<%=basePath%>bootstrap/js/jquery.tagsinput.min.js"></script>
 <script src="<%=basePath%>bootstrap/js/toggles.min.js"></script>
 <script src="<%=basePath%>bootstrap/js/bootstrap-timepicker.min.js"></script>
 <script src="<%=basePath%>bootstrap/js/jquery.maskedinput.min.js"></script>
 <script src="<%=basePath%>bootstrap/js/colorpicker.js"></script>
 <script src="<%=basePath%>bootstrap/js/dropzone.min.js"></script>

<%--结束  --%>

<link href="<%=basePath%>bootstrap/css/style_account.css"
	rel="stylesheet" />
<style type="text/css">
.select-basic {
	width: 337px;
	
}
.col-sm-5 {
width: 108.666667%;
margin-left: -13px;
}
</style>
</head>
<body>
<jsp:include page="include/head.jsp" />
<div class="complexBox">
	<!-- 个人事件/其他事件 -->
	<jsp:include page="include/left.jsp" />
	<div class="customer">
<!-- 个人事件/其他事件 -->
	<div class="panel panel-primary-head">
		<ul class="nav nav-tabs nav-line">
			<li class="active" onclick="toEvent()"><a href="#following"
				data-toggle="tab" style="color: #555;"><strong>本人事件</strong> </a>
			</li>
			<li class="" onclick="toContacts()"><a href="#following"
				data-toggle="tab" style="color: #555;"><strong>分享事件</strong> </a>
			</li>
		</ul>
	</div>
	<div class="panel panel-primary-head">
		<form id="inputForm" class="form-horizontal" action="<%=basePath%>event/save.do" method="post">
			<div class="panel panel-primary">
				<div class="panel-heading panel-title-name">
					<h3 class="panel-title col-sm-10  pull-left">事件信息</h3>
				</div>
				<!--基本信息-->
				<div class="panel-body">
					<div class="col-md-12">
						<div class="col-sm-6">
							<div class="form-group">
								<label class="col-sm-3 control-label"> 主题： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="pubname"
										name="eventsubject" title="请填写事件主题" value="${event.eventsubject }"
										placeholder="请填写事件主题..." required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 标签： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="pubregcapital"
										name="eventtags" title="请填写事件标签"
										value="${event.eventtags }" placeholder="请填写事件标签..."
										required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 单位： </label>
								<div class="col-sm-8 zidingyi">
<%--									<input class="form-control" type="text" id="pubman"--%>
<%--										name="company.id" title="请填写单位名称" value="${company.pubman }"--%>
<%--										placeholder="请填写单位名称..." required />--%>
									<select class="form-control" name="company">
										<c:forEach items="${companies }" var="company">
										<%--	单位简称	--%>
											<option value="${company.id }">${company.pubname }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 参与人： </label>
								<div class="col-sm-8 options-pull">
<%--									<select class="col-sm-12" name="joinSet.id" id="persionid" required>--%>
<%--										<option value="0">选择参与人</option>--%>
<%--									</select>--%>
									<div class="col-sm-5" id="dictionDiv">
									<select class="form-control" id="dicname" name="dicname" required></select>
								</div>
								</div>
								
							</div>
							
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label class="col-sm-3 control-label"> 摘要： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="pubtel"
										name="eventsummary" title="请填写事件摘要" value="${event.eventsummary }"
										placeholder="请填写事件摘要... " required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 地址： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="pubadd"
										name="eventaddr" title="请选择事件地址"
										value="${event.eventaddr }" placeholder="请选择事件地址"
										required />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 联系人： </label>
<%--									<div class="col-sm-8 zidingyi">--%>
<%--										<div class="col-sm-5" id="dictionDiv">--%>
<%--										<select class="form-control" id="dicname" name="dicname" required></select>--%>
<%--										</div>--%>
<%--									</div>--%>
							
								<div class="col-sm-8">
                                     <input name="tags" id="tags" class="form-control" value="foo,bar,baz" style="display: none;">
                                     	<div id="tags_tagsinput" class="tagsinput" style="width: auto; height: 100px;">
	                                     	<span class="tag"><span>foo&nbsp;&nbsp;</span><a href="#" title="Removing tag">x</a></span>
	                                     	<span class="tag"><span>bar&nbsp;&nbsp;</span><a href="#" title="Removing tag">x</a></span>
	                                     	<div id="tags_addTag">
	                                     	<input id="tags_tag" value="" data-default="add a tag" style="color: rgb(102, 102, 102); width: 68px;"></div>
	                                     	<div class="tags_clear"></div>
                                     	</div>
                                 </div>
								
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 创建人： </label>
								<div class="col-sm-8 zidingyi">
									<input class="form-control" type="text" id="publsrsbh" readonly="readonly"
										value="<%=request.getSession().getAttribute("userName") %>"  />
										<input type="hidden" name="creator" value="<%=request.getSession().getAttribute("userId") %>"/>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--自定义信息-->
				<c:if test="${empty company.id }">
					<div class="panel panel-primary">
						<div class="panel-heading panel-title-name">
							<h3 class="panel-title col-sm-6">事件附件</h3>
							<a href="javascript:addField();"
								class="btn-primary pull-right tooltips"
								data-original-title="添加自定义信息">添加</a>
						</div>
					</div>
					<div id="body_field" class="panel-body">

						<div class="col-sm-12" id="havefield_one">
							
						</div>

					</div>
				</c:if>


			<div class="panel-footer">
				<div class="row">
					<div class="col-sm-0 col-sm-offset-5">
						<button type="submit" id="sub" class="btn btn-primary mr5" >保存</button>
						<!-- <button type="reset" class="btn btn-dark" >重置</button> -->
						<button class="btn btn-danger deleteButton" onclick="toCompany()">取消</button>
						<!-- 红色按钮 class="btn btn-danger deleteButton" -->
					</div>
			</div>
				</div>
			</div>
		</form>
	</div>
	</div></div>
	<script>
	var attid ="";
	var fieldid ="";
	var addfieldid = 0;
	var tagid = "";
	var x = "";
     
	jQuery(document).ready(function(){
		
		selectAllper();
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
	
	jQuery(document).ready(function() {
		// Error Message In One Container
		$("#inputForm").validate({
			errorLabelContainer : jQuery("#inputForm div.errorForm")
		});
		 init(); 
	});
	<%--	自动查询，输入查询	--%>
	 function init(){
<%--				$("#dictionDiv").html("<select class='form-control' id='dicname' name='dicname' ></select>");--%>
			$("#dicname").fcbkcomplete({
				json_url: "<%=basePath%>contacts/comList.do",
				filter_selected: true,
				addontab: true,                   
		        height: 3,
		        maxitimes:1
			});
		}
		

		var contactIds="";
		var persionId="";
		//保存添加的事件
		function save(){
			var names = document.getElementsByName("contactId");
				for(var i=0; i<names.length; i++){
					contactIds += names[i].value +",";
				
			}
				persionId=$("#dicname").val();
<%--					alert(persionId);--%>
				$.ajax({
					type: 'POST',
					url: '<%=basePath%>event/save.do?persionId='+persionId,
					data : {
						'contactIds' : contactIds,
<%--							'persionId' : persionId,--%>
					},
					dataType : 'json',
					success : function(data) {
<%--						jQuery.gritter.add({--%>
<%--						    title: '分配成功!',--%>
<%--						    class_name: 'growl-info',--%>
<%--							 image: '../bootstrap/images/screen.png',--%>
<%--						    sticky: false,--%>
<%--						    time: ''--%>
<%--						});--%>
					}
			});
	}
	</script>
</body>
</html>