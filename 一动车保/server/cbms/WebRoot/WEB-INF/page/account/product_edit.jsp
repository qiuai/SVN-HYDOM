<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">

<title></title>
<link
	href="${pageContext.request.contextPath}/resource/chain/css/style.default.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resource/chain/css/morris.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resource/chain/css/select2.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resource/css/manage.common.css"
	rel="stylesheet">

<script
	src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/jquery-migrate-1.2.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/modernizr.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/pace.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/retina.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/jquery.cookies.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/select2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/chain/js/laydate/laydate.js"></script>

	
<!-- 验证框架 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/jquery.maskedinput-1.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/validate/account.js"></script>

<!-- 公共JS -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resource/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resource/ueditor/ueditor.all.js"></script>
  
  
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resource/chain/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/respond.min.js"></script>
        <![endif]-->

<STYLE type="text/css">
.form-bordered div.form-group {
	width: 49%;
	padding: 5px 10px;
	border-top: 0px dotted #d3d7db;
}

.img_div{
	display: inline-block;
	height: 60px;
	width: 60px;
}
.img_div img{
	display: inline-block;
	height: 60px;
	width: 60px;
}
.rdio {
	margin-top: 10px;
	width: 50px;
	display: inline-block;
}
#area_div_select select{
	margin-top: 10px;
}
#memberRankSelect{
	margin-top: 10px;
}
.mg10{
	margin-top: 8px;
}
div.car_div_label label{
	min-width: 140px;
}
#carType div.form-group{
	width: 100%;
}
div.car_div_label_type label{
	min-width: 33%;
}
</STYLE>
<script type="text/javascript">
	var base = "<%=basePath%>";
</script>
</head>
<body>
	<header>
		<%@ include file="/WEB-INF/page/common/head.jsp"%>
	</header>

	<section>
		<div class="mainwrapper">
			<%@ include file="/WEB-INF/page/common/left.jsp"%>

			<div class="mainpanel">
				<div class="pageheader">
					<div class="media">
						<div class="media-body">
							<ul class="breadcrumb">
								<li><a href=""><i class="glyphicon glyphicon-home"></i></a></li>
								<li><a href="">商品编辑</a></li>
							</ul>
						</div>
					</div>
					<!-- media -->
				</div>
				<!-- pageheader -->

				<div class="contentpanel">
				
					<div class="row">
                        <div class="col-md-12">
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#home" data-toggle="tab"><strong>基本信息</strong></a></li>
                                <li class=""><a href="#profile" data-toggle="tab"><strong>商品介绍</strong></a></li>
                                <li class=""><a href="#about" data-toggle="tab"><strong>商品效果图片</strong></a></li>
                                <li class=""><a href="#contact" data-toggle="tab"><strong>商品参数</strong></a></li>
                                <li class=""><a href="#attribute" data-toggle="tab"><strong>商品筛选属性</strong></a></li>
                                <li class=""><a href="#special" data-toggle="tab"><strong>商品规格</strong></a></li>
                                <li class=""><a href="#carType" data-toggle="tab"><strong>适用车型</strong></a></li>
                            </ul>
    
                            <!-- Tab panes -->
                            <form class="form-horizontal form-bordered" id="inputForm" action="<%=basePath%>manage/product/update" method="POST" enctype="multipart/form-data">
                            
                            <input type="hidden" name="productCategoryId" value="${productCategoryId }"/>
                            <input type="hidden" name="productNum" value="${productNum }"/>
                            <input type="hidden" name="productName" value="${productName }"/>
                            <input type="hidden" name="page" value="${page}"/>
                                                        
                            <div class="tab-content mb30">
                            	<!-- 商品介绍 -->
                            	<input type="hidden" name="productId" value="${entity.id }"/>
                            	<script type="text/javascript">
                            		//改变商品分类
                            		//1、改变品牌 2、改变商品参数 3、改变商品筛选属性 4、商品规格
                            		var productBrand = "${entity.productBrand.id}";
                            		var productId = "${entity.id}";
                            		function changeProductCategory(){
                            			var id = $("#productCategoryId").find("option:selected").val();
                            			if(id == ""){
                            				return;
                            			}
                            			changeBrand(id);
                            			changeParameter(id);
                            			changeAttribute(id);
                            			changeSpecification(id);
                            		}
                            		
                            		function changeBrand(id){
                            			var url = base + "manage/product/findBrand";
                            			var data = {productCategoryId:id};
                            			$.post(url,data,function(result){
                            				if(result.status == "success"){
                            					addBrandSelect(result.message);
                            				}else{
                            					var value = new Array();
                            					addBrandSelect(value);
                            				}
                            			},"json");
                            		}
                            		
                            		function addBrandSelect(value){
                            			var html = "<option value=''>请选择品牌</option>";
                            			for(var i in value){
											var select = "";                            				
                            				if(productBrand == value[i].id){
                            					select = "selected='selected'";   
                            				}
                            				html += "<option value='"+value[i].id+"' "+select+">"+value[i].name+"</option>";
                            			}
                            			$("#productBrandSelect").html(html);
                            		}
                            		//筛选条件
                            		function changeAttribute(id){
                            			
                            			var url = base + "manage/product/loadAttribute";
                            			var data = {
                            					productCategoryId:id,
                            					productId:productId
                            			};
                            			$("#attribute").load(url,data,function(){});
                            			
                            		}
                            		//商品参数
                            		function changeParameter(id){
                            			var url = base + "manage/product/loadParameter";
                            			var data = {productCategoryId:id,productId:productId};
                            			$("#contact").load(url,data,function(){});
                            		}
                            		//商品规格
                            		function changeSpecification(id){
                            			var url = base + "manage/product/loadEditSpecification";
                            			var data = {productCategoryId:id,productId:productId};
                            			$("#special").load(url,data,function(){});
                            		}
                            	</script>
                                <div class="tab-pane active " id="home">
                                    <div class="panel-body nopadding">
                                    	<div class="form-group">
											<label class="col-sm-4 control-label">商品分类</label>
											<div class="col-sm-8">
												<select name="productCategory.id" onchange="changeProductCategory(this);" class="mg10" id="productCategoryId">
													<option value="">请选择商品分类</option>
													<c:forEach var="category" items="${productCategorys }">
														<option value="${category.id }" <c:if test="${entity.productCategory.id eq category.id}">selected="selected"</c:if> >
															<c:if test="${category.grade gt 0}">
																<c:forEach begin="1" end="${category.grade }">
																	&nbsp;&nbsp;
																</c:forEach>
															</c:if>
															${category.name}
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">商品名称</label>
											<div class="col-sm-8">
												<input type="text" name="name" class="form-control" maxlength="200" value="${entity.name }"/>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">展示图片</label>
											<div class="col-sm-8">
												<div class="img_div">
													<img alt="" src="
													<c:if test="${!empty entity}"><%=basePath %>/${entity.imgPath }</c:if>
													" onerror="<%=basePath %>/resource/image/default.png" id="show_img">
													<input type="hidden" name="imgPath" value="${entity.imgPath }"/>
												</div>
												<label> <%-- style="position: absolute;  float: right;  margin-left: 20%;" --%>
													<span id="spanButtonPlaceholder"></span>
												</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">成本价</label>
											<div class="col-sm-8">
												<input type="text" name="cost" class="form-control" value="${entity.cost }"/>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">市场价</label>
											<div class="col-sm-8">
												<input type="text" name="marketPrice" class="form-control" value="${entity.marketPrice }"/>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">赠送积分</label>
											<div class="col-sm-8">
												<input type="text" name="point" class="form-control" value="${entity.point }"/>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">品牌</label>
											<div class="col-sm-8">
												<select name="productBrand.id" id="productBrandSelect" class="mg10">
													<option value="">请选择品牌</option>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">优惠卷</label>
											<div class="col-sm-8">
												<label class="mg10"><input type="checkbox" name="useCoupon" value="0" <c:if test="${entity.useCoupon eq 0}">checked="checked"</c:if>/>可以使用</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">售后服务</label>
											<div class="col-sm-8">
												<c:forEach var="value" items="${labels }">
													<label class="mg10"><input type="checkbox" name="labelIds" value="${value.id }" <c:if test="${fn:contains(labelSet,value.id) }">checked="checked"</c:if>/>${value.labelName }</label>
												</c:forEach>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">是否推荐</label>
											<div class="col-sm-8">
												<label class="mg10"><input type="checkbox" name="recommend" value="1" <c:if test="${entity.recommend eq 1}">checked="checked"</c:if> />推荐</label>
											</div>
										</div>
										<script type="text/javascript">
											function prouductUniqueTypeChange(obj){
												var selectValue = $(obj).val();
												closeHIDEdiv();
												if(selectValue == "2"){
													$("#productCountDIV").show();
												}else{
													$("#discountDIV").show();
												}
											}
											function closeHIDEdiv(){
												$("#productCountDIV").hide();
												$("#discountDIV").hide();
											}
											function blurInput(obj){
												var value = $(obj).val();
												var marketPrice = $("input[name='marketPrice']").val();
												var price = parseFloat(parseFloat(value) * parseFloat(marketPrice)).toFixed(2);
												$("#discountPrice").text(price);
											}
										</script>
										<div class="form-group">
											<label class="col-sm-4 control-label">特色市场</label>
											<div class="col-sm-8">
												<select name="prouductUniqueType" class="mg10" onchange="prouductUniqueTypeChange(this);">
													<option value="">无</option>
													<!-- <option value="1">品牌推荐 </option> -->
													<option value="2" <c:if test="${entity.prouductUniqueType eq 2}">selected="selected"</c:if> >限量精品</option>
													<option value="3" <c:if test="${entity.prouductUniqueType eq 3}">selected="selected"</c:if> >天天特价</option>
													<option value="4" <c:if test="${entity.prouductUniqueType eq 4}">selected="selected"</c:if> >绿色出行</option>
												</select>
											</div>
										</div>
										<div class="form-group" id="productCountDIV" style="display: none;">
											<label class="col-sm-4 control-label">限量数量</label>
											<div class="col-sm-8">
												<input type="text" name="productCount" class="form-control" value="${entity.productCount }"/>
											</div>
										</div>
										<div class="form-group" id="discountDIV" style="display: none;">
											<label class="col-sm-4 control-label">特价折扣</label>
											<div class="col-sm-8">
												<input type="text" name="discount" class="form-control" value="0" onblur="blurInput(this);" value="${entity.discount }"/>折后价<span id="discountPrice">0</span>
											</div>
										</div>
									</div>
                                </div><!-- tab-pane -->
                              	<!-- 商品介绍 -->
                                <div class="tab-pane" id="profile">
                                    <script id="editor" type="text/plain" style="width:100%;height:400px;"></script>
									<textarea class="form-control"  style="display:none;" id="content" rows="5" name="introduction" required title="内容为必填项!">${entity.introduction }</textarea>
                                </div><!-- tab-pane -->
                              	<script type="text/javascript">
                              		function addTableImage(){
                              			var html = "<tr><td>"+
                              				"<input type='file' name='files'/></td>"+
											"<td>"+
												"<a href='javascript:void(0);' onclick='deleteImage(this);'>[删除]</a>"+
											"</td></tr>";
											console.log($(".table_tbody"));
                              			$(".table_tbody").append(html);
                              			
                              		}
                              		function deleteImage(e){
                              			var tr = $(e).closest("tr");
                              			tr.remove();
                              		}
                              		//删除图片
                              		function deleteImg(imgId,e){
                              			if(!confirm("是否删除该图片?")){
                              				return;
                              			}
                              			var url = base + "manage/product/deleteProductImg";
                              			var data = {
                              				imgId:imgId
                              			};
                              			$.post(url,data,function(result){
                              				if(result.status == "success"){
                              					deleteImage(e);
                              				}
                              			});
                              		}
                              		
                              	</script>
                              	<!-- 商品图片 -->
                                <div class="tab-pane" id="about">
                               	 	<button type="button" onclick="addTableImage();" style="margin-bottom: 10px;">添加图片</button>
                                    <div class="panel-body nopadding">
										<div class="table-responsive">
									        <table id="listTable" class="table table-info table_tbody" >
												<thead>
													<tr>
														<th>图片路径</th>
														<th style="width: 10%;">操作</th>
													</tr>
												</thead>
												<c:forEach var="img" items="${entity.productImages }">
													<tr>
														<td>
															<a href="<%=basePath %>${img.source}" target="_blank">查看</a>
			                              				</td>
														<td>
															<a href="javascript:void(0);" onclick="deleteImg('${img.id}',this);">[删除]</a>
														</td>
													</tr>
												</c:forEach>
												
												<!-- <tbody class="table_tbody">
													<tr>
														<td>
															<input type="file" name="files"/>
														</td>
														<td>
															<a href="javascript:void(0);" onclick="deleteImage(this);">[删除]</a>
														</td>
													</tr>
													<tr>
														<td>
															<input type="file" name="files"/>
														</td>
														<td>
															<a href="javascript:void(0);">[删除]</a>
														</td>
													</tr>
													<tr>
														<td>
															<input type="file" name="files"/>
														</td>
														<td>
															<a href="javascript:void(0);">[删除]</a>
														</td>
													</tr>
												</tbody> -->
											</table>
									      </div>
									</div>
                                   	
                                </div><!-- tab-pane -->
                             	 <!-- 商品参数 -->
                                <div class="tab-pane" id="contact">
                                    <%-- <div class="form-group">
										<label class="col-sm-4 control-label">名称</label>
										<div class="col-sm-8">
											<input type="text" name="name" class="form-control" maxlength="200" value="${entity.name }"/>
										</div>
									</div> --%>
                                </div><!-- tab-pane -->
                                
                                <!-- 商品筛选属性 -->
                                 <div class="tab-pane" id="attribute">
                                    <%-- <div class="form-group">
										<label class="col-sm-4 control-label">名称</label>
										<div class="col-sm-8">
											<input type="text" name="name" class="form-control" maxlength="200" value="${entity.name }"/>
										</div>
									</div> --%>
                                </div><!-- tab-pane -->
                                
                                <!-- 商品规格 -->
                                 <div class="tab-pane" id="special">
                                   
									
                                </div><!-- tab-pane -->
                                <script type="text/javascript">
                                	//车型事件绑定
                                	function documentCarBind(){
                                		
                                		initProductCar();
                                		
                                		$(document).on("click","input[name='carBrandIds']",function(){
                                			var carBrandId = $(this).val();
                                			if(carBrandId == "-1"){
                                				$("#carTypeLabel").html("");
                                				$("#carLabel").html("");
                                				$("input[name='carBrandIds']").not("input[value='-1'][name='carBrandIds']").prop("checked",false);
                                				return;
                                			}
                                			$("input[value='-1'][name='carBrandIds']").prop("checked",false);
                                			if($(this).prop("checked")){//选中 
                                				addCarType(carBrandId);
                                			}else{//取消
                                				$("."+carBrandId).closest("label").remove();
                                			}
                                		});
                                		
                                		$(document).on("click","input[name='carTypeIds']",function(){
                                			var carTypeId = $(this).val();
                                			var parentId = $(this).attr("class");
                                			if($(this).prop("checked")){//选中 
                                				addCar(carTypeId,parentId);
                                			}else{//取消
                                				$("."+carTypeId).closest("label").remove();
                                			}
                                		});
                                		
                                		//选择所有的车型
                                		$(document).on("click","#allCarCheckbox",function(){
                                			if($(this).prop("checked")){
                                				$("input[name='carIds']").prop("checked",true);
                                			}else{
                                				$("input[name='carIds']").prop("checked",false);
                                			}
                                		});
                                	}
                                	//当起选择不限的时
                                	function setCarBrand(){
                                		var carBrands = $("input[name='carBrandIds']:checked");
                                		if(carBrands.length == 0){
                                			$("input[value='-1'][name='carBrandIds']").prop("checked",true);
                                		}
                                	}
                                	//车系
                                	function addCarType(obj){
                                		var url = base + "manage/product/getCarType";
                                		var data = {carBrandId : obj};
                                		$.post(url,data,function(result){
                                			if(result.status == "success"){
                                				if(result.message.length == 0){
                                					alert("该品牌没有车系");
                                					$("input[value='"+obj+"']").prop("checked",false);
                                					setCarBrand();
                                				}else{
                                					addCarTypeHTML(result.message,obj);
                                				}
                                			}else{
                                				alert("数据获取出错");
                                				$("input[value='"+obj+"']").prop("checked",false);
                                				setCarBrand();
                                			}
                                		},"json");
                                	}
                                	//车系 html添加 
                                	function addCarTypeHTML(value,obj){
                                		var html = "";
                                		for(var i in value){
                                			html += "<label><input value='"+value[i].id+"' type='checkbox' name='carTypeIds' class='"+obj+"'/>"+value[i].text+"</label>";
                                		}
                                		$("#carTypeLabel").append(html);
                                		checkedCarType();
                                	}
                                	//车型
                                	function addCar(obj,parentId){
                                		var url = base + "manage/product/getCar";
                                		var data = {carTypeId : obj};
                                		$.post(url,data,function(result){
                                			if(result.status == "success"){
                                				if(result.message.length == 0){
                                					alert("该车系没有车型");
                                					$("input[value='"+obj+"']").prop("checked",false);
                                				}else{
                                					addCarHTML(result.message,obj,parentId);
                                				}
                                			}else{
                                				alert("数据获取出错");
                                				$("input[value='"+obj+"']").prop("checked",false);
                                			}
                                		},"json");
                                	}
                                	function addCarHTML(value,obj,parentId){
                                		var html = "";
                                		for(var i in value){
                                			html += "<label><input value='"+value[i].id+"' type='checkbox' class='"+obj+" "+parentId+" carIds' name='carIds'/>"+value[i].text+"</label>";
                                		}
                                		$("#carLabel").append(html);
                                		checkedCar();
                                	}
                                	
                                	function checkCar(){
                                		var carBrands = $("input[name='carBrandIds']:checked");
                                		var b = false;
                                		for(var i = 0 ;i<carBrands.length;i++){
                                			var carBrand = $(carBrands[i]);
                                			if(carBrand.val() == "-1"){
                                				b = true;
                                			}
                                		}
                                		if(b){//说明 品牌不限  被选中
                                			return true;
                                		}else{//品牌不限 没有被选中 判断其后续车型是否有选中
                                			if(carBrands.length == 0){
                                				$("input[name='carBrandIds'][value='-1']").prop("checked",true);
                                			}else{
                                				var cars = $("input[name='carIds']");
                                				if(cars.length == 0){
                                					alert("请选择商品适用车型");
                                					return false;
                                				}else{
                                					return true;
                                				}
                                			}
                                		}
                                	}
                                	
                                	//选中品牌
                        			var chooseBrands = '${chooseBrandIds}';
                        			var brandsDate = eval("("+chooseBrands+")");
                        			
                        			//选中车系
                        			var chooseCarTypes = '${chooseCarTypeIds}';
                        			var carTypeDate = eval("("+chooseCarTypes+")");
                        			
                        			//选中车型
                        			var cars = '${chooseCar}';
                        			var carDate = eval("("+cars+")");
                        			
                                	function initProductCar(){
                                		console.log(brandsDate);
                            			console.log(carTypeDate);
                            			console.log(carDate);
                                		var useAllCar = "${entity.useAllCar}";
                                		if(useAllCar != "0"){//车辆不限
                                			$("#useAllCar").prop("checked",false);
                                			checkedBrand();
                                			//根据选中车辆品牌 获取 车系
                                			for(var i in brandsDate){
                                				addCarType(brandsDate[i]);
                                			}
                                			//选中车型
                                			for(var i in carTypeDate){
                                				addCar(carTypeDate[i].id, carTypeDate[i].parentId);
                                			}
                                			
                                		}
                                	}
                                	function checkedBrand(){
                            			for(var i in brandsDate){
                            				$("input[name='carBrandIds'][value='"+brandsDate[i]+"']").prop("checked",true);
                            			}
                            		}
                                	function checkedCarType(){
                                		for(var i in carTypeDate){
                            				$("input[name='carTypeIds'][value='"+carTypeDate[i].id+"']").prop("checked",true);
                            			}
                                	}
                                	function checkedCar(){
                                		for(var i in carDate){
                            				$("input[name='carIds'][value='"+carDate[i]+"']").prop("checked",true);
                            			}
                                	}
                                </script>
                                <!-- 适用车型 -->
                                 <div class="tab-pane" id="carType">
                                  <div class="panel-body nopadding">
										<div class="form-group">
											<label class="col-sm-2 " style="font-weight: bold;">车辆品牌</label>
											<div class="col-sm-12 car_div_label">
												<label><input type="checkbox" value="-1" name="carBrandIds" checked="checked" id="useAllCar"/>不限</label>
												<c:forEach var="value" items="${carBrands }">
													<label><input type="checkbox" value="${value.id }" name="carBrandIds"/>${value.name }</label>
												</c:forEach>
											</div>
										</div>
										<div class="form-group" id="carTypeIdDiv">
											<label class="col-sm-2 " style="font-weight: bold;">车系</label>
											<div class="col-sm-12 car_div_label" id="carTypeLabel">
												
											</div>
										</div>
										<div class="form-group" id="carIdDiv">
											<label class="col-sm-2 " style="font-weight: bold;"><input type="checkbox" id="allCarCheckbox"/>车型(全选)</label>
											<div class="col-sm-12 car_div_label_type" id="carLabel">
												
											</div>
										</div>
										<%-- <div class="form-group" id="carIdDiv">
											<label class="col-sm-4 control-label">已选车型</label>
											<div class="col-sm-8" id="chooseCarLabel">
												<c:choose>
													<c:when test="${entity.useAllCar eq 0}">
														<label><input type="checkbox" value="-1" name="carBrandIds" checked="checked"/>不限</label>
													</c:when>
													<c:otherwise>
														<label><input type="checkbox" value="-1" name="carBrandIds"/>不限</label>
														<c:forEach var="value" items="${entity.carSet }">
															<label><input type="checkbox" value="${value.id }" name="carIds" checked="checked"/>${value.name }</label>
														</c:forEach>
													</c:otherwise>	
												</c:choose>
												
											</div>
										</div> --%>
									</div>
                                 </div><!-- tab-pane -->
                            </div><!-- tab-content -->
                            <div class="panel-footer" style="border: 1px solid #ddd;">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button id="addCate" class="btn btn-primary mr5"
											onclick="saveForm()" type="button">提交</button>
										<button class="btn btn-dark" type="reset">重置</button>
									</div>
								</div>
							</div>
                            </form>
                        </div><!-- col-md-6 -->
                    </div>
				</div>
				<div class="bottomwrapper">
					<%@ include file="/WEB-INF/page/common/bottom.jsp"%>
				</div>
			</div>
			<!-- mainpanel -->
		</div>
		<!-- mainwrapper -->
	</section>
	<!-- 上传图片页面 -->
	<jsp:include page="../common/imgUpload.jsp"></jsp:include>
	<script type="text/javascript">
		var base = "<%=basePath%>";
		$('[data-toggle="tooltip"]').popover();
		$(document).ready(function(){
			$("#serviceType").select2({
				minimumResultsForSearch : -1,
				allClear : true
			});
			//适用车辆绑定事件
			documentCarBind();
			changeProductCategory();
		});
		
		function saveForm(){
			
			var productCategoryId = $("#productCategoryId").val();
			if(productCategoryId == ""){
				alert("请选择商品分类");
				return;
			}
			
			if($("input[name='name']").val() == ""){
				alert("请输入商品名称");
				return;
			}
			
			var productBrand = $("#productBrandSelect").val();
			if(productBrand == ""){
				alert("请选择品牌");
				return;
			}
			
			var cost = $("input[name='cost']").val();
			var marketPrice = $("input[name='marketPrice']").val();
			var point = $("input[name='point']").val();
			
			if(isNaN(cost)){
				alert("成本价请输入数字");
				return;
			}
			
			if(isNaN(marketPrice)){
				alert("市场价请输入数字");
				return;
			}
			
			if(isNaN(point)){
				alert("积分请输入数字");
				return;
			}
			
			if(!checkCar()){
				return;
			}
			
			//参数值
			setParameterDate();
			//设置规格值
			setSpecificationInputValue();
			//修改规格值
			setOldSpecificationInputValue();
			//alert(ue.getContent());
			$("#content").val(ue.getContent());
			
			if(!checkCar()){
				return;
			}
			$("#inputForm").submit();
		}
				
		 var ue;
	        createEditor(0);
	        ue.ready(function() {
	            var html = $("#content").val();
	           ue.setContent(html);
	        });
	        function createEditor(type) {
	        	if (type == 2) {
	        		ue = UE.getEditor('editor', {
	        			toolbars: [[
	        	            'fullscreen', 'source', '|', 'undo', 'redo', '|',
	        	            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
	        	            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
	        	            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
	        	            'directionalityltr', 'directionalityrtl', 'indent', '|',
	        	            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
	        	            'link', 'unlink', '|',
	        	            'horizontal', 'date', 'time', '|',
	        	            'print', 'preview', 'searchreplace', 'help'
	        	        ]]
	        		});
	        	} else if (type == 0) {
	        		ue = UE.getEditor('editor', {
	        			toolbars: [[
	        	            'fullscreen', 'source', '|', 'undo', 'redo', '|',
	        	            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
	        	            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
	        	            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
	        	            'directionalityltr', 'directionalityrtl', 'indent', '|',
	        	            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
	        	            'link', 'unlink', '|',
	        	            'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
	        	            'simpleupload', 'insertimage', 'insertvideo', '|',
	        	            'horizontal', 'date', 'time', '|',
	        	            'print', 'preview', 'searchreplace', 'help'
	        	        ]]
	        		});
	        	}
	        }
	</script>
</body>
</html>
