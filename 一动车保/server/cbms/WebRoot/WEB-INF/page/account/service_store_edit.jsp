<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

<title>服务门店添加</title>
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
<link href="${pageContext.request.contextPath}/resource/chain/css/bootstrap-timepicker.min.css" rel="stylesheet" />

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
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resource/chain/js/bootstrap-timepicker.min.js"></script>
<!-- 公共JS -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resource/chain/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/respond.min.js"></script>
        <![endif]-->
<script type="text/javascript">
	function checkUsername() {
		var username = $("#username").val();
		$.post("${pageContext.request.contextPath}/manage/serviceType/checkUsername.action",
		{
			username : username
		},
		function(data) {
			if (data == 0 && username != "" && username != null) {//表示 帐户存在
				$("#username_error").html("用户名已经存在");
				$("#repeat").val("");
			} else {
				$("#username_error").html("");
				$("#repeat").val("success");
			}
		});
	}
	
	var base = "<%=basePath%>";

	//选择地区
function getAreaList(e,obj){
	delSelect(e,obj);
	var option = $(e).find("option:selected");
	var id = option.val();
	
	if(!option.hasClass("hasNext")){
		return;
	}
	
	if(id == ""){
		return;
	}
	
	var url = base + "/manage/area/findArea";
	$.post(url,{id:id},function(result){
		if(result.status == "success"){
			var values = result.message;
			addSelectElement(values,obj);
		}
	},"json");
}

//设置地区
function delSelect(e,obj){
	if(obj == 1){
		$("#county").html("<option value=''>县区</option>");
		$("#street").html("<option value=''>街道</option>");
	}else if(obj == 2){
		$("#street").html("<option value=''>街道</option>");
	}
}

//添加选择元素
function addSelectElement(value,obj){
	var options = "";
	for(var i in value){
		options += "<option value='"+value[i].id+"' class='"+value[i].hasNext+"'>"+value[i].text+"</option>";
	}
	if(obj == 1){
		var m = "<option value=''>县区</option>";
		$("#county").html(m+options);
	}else if(obj == 2){
		var m = "<option value=''>街道</option>";
		$("#street").html(options);
	}
}
</script>
<STYLE type="text/css">
.form-plan-div{
	width: 49%;
	display: inline-block;
}

.form-bordered div.form-group {
	width:100%;
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
.allmap {
	  width: 100%;
	  height: 250px;
	  margin: 0;
	  font-family: "微软雅黑";
}
#allmap{
	width: 100%;
	height: 100%;
	margin: 0;
	font-family: "微软雅黑";
	display: inline-block;
}
.searchBaiduMap{
	width: 40px;
	height: 25px;
	margin-left: 5px;
	border-radius: 10px;
	outline: none;
	cursor: pointer;
}
</STYLE>
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
								<li><a href=""><c:if test="${!empty entity}">服务门店编辑</c:if><c:if test="${empty entity}">服务门店添加</c:if></a></li>
							</ul>
						</div>
					</div>
					<!-- media -->
				</div>
				<!-- pageheader -->
				
				<div class="contentpanel">
				<form class="form-horizontal form-bordered" id="inputForm" action="<%=basePath%>manage/serviceStore/save" method="POST">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">基本信息</h4>
							</div>
							<input type="hidden" name="id" value="${entity.id }"/>
							<div class="panel-body nopadding">
								<div class="form-plan-div">
									<div class="form-group">
										<label class="col-sm-4 control-label">门店名称</label>
										<div class="col-sm-8">
											<input type="text" name="name" class="form-control" maxlength="200" value="${entity.name }"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">区域</label>
										<div class="col-sm-8">
											<div class="ma_divselect" style="margin-top: 10px;">
												<select onchange="getAreaList(this,1);" >
									   				<option value="">市</option>
									   				<c:forEach var="area" items="${rootArea }">
									   					<option value="${area.id }" <c:if test="${area.children.size()>0 }">class="hasNext"</c:if> <c:if test="${area.id eq firstArea.id}">selected="selected"</c:if>>${area.name }</option>
									   				</c:forEach>
									   			</select>
									   			<select id="county" onchange="getAreaList(this,2);">
									   				<option value="">县区</option>
													<c:forEach var="area1" items="${firstArea.children }">
									   					<option value="${area1.id }" <c:if test="${area1.children.size()>0 }">class="hasNext"</c:if> <c:if test="${area1.id eq secendArea.id}">selected="selected"</c:if>>${area1.name }</option>
									   				</c:forEach>   				
									   			</select>
									   			<select id="street" name="area.id">
									   				<option value="">街道</option>
									   				<c:forEach var="area2" items="${secendArea.children }">
									   					<option value="${area2.id }" <c:if test="${area2.id eq entity.area.id}">selected="selected"</c:if>>${area2.name }</option>
									   				</c:forEach>
									   			</select>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">门店图片</label>
										<div class="col-sm-8">
											<div class="img_div">
												<img alt="" src="
												<c:if test="${!empty entity}"><%=basePath %>/${entity.imgPath }</c:if>
												" onerror="<%=basePath %>/resource/image/default.png" id="show_img">
												<input type="hidden" name="imgPath" value="${entity.imgPath }" />
											</div>
											<label> <%-- style="position: absolute;  float: right;  margin-left: 20%;" --%>
												<span id="spanButtonPlaceholder"></span>
											</label>
										</div>
									</div>
									<%--<div class="form-group">
										<label class="col-sm-4 control-label">支付方式</label>
										<div class="col-sm-8">
											<label><input type="radio" value="0" name="payType" checked="checked"/>全部</label>
											<label><input type="radio" value="1" name="payType"/>在线支付</label>
											<label><input type="radio" value="2" name="payType"/>线下支付</label>
										</div>
									</div> --%>
									<div class="form-group">
										<label class="col-sm-4 control-label">营业时间</label>
										<div class="col-sm-8">
											<div class="input-group mb15" style="width: 40%;float:left;">
			                                	<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
			                                	<div class="bootstrap-timepicker"><input id="start" name="startTime" type="text" class="form-control" placeholder="开始时间" /></div>
			                            	</div>
			                            	<div style="float:left;display: inline-block;margin-bottom: 15px; height: 40px;line-height: 40px;padding-left: 5px;padding-right: 5px;">
			                            		到
			                            	</div>
											<div class="input-group mb15" style="width: 40%;float:left;">
			                                	<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
			                                	<div class="bootstrap-timepicker"><input id="end" name="endTime" type="text" class="form-control" placeholder="结束时间" ></div>
			                            	</div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-4 control-label">支持服务</label>
										<div class="col-sm-8 labeldiv">
											<label><input type="checkbox" name="supportGoHome" value="true"  <c:if test="${entity.supportGoHome eq true}">checked="checked"</c:if> />上门服务</label>
											<label><input type="checkbox" name="supportClean" value="true" <c:if test="${entity.supportClean eq true}">checked="checked"</c:if> />洗车</label>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-4 control-label">保养</label>
										<div class="col-sm-8 labeldiv">
											<c:forEach var="value" items="${serviceTypes }">
												<c:if test="${value.type eq 1}">
													<label><input type="checkbox" name="serviceTypeIds" value="${value.id }"  <c:if test="${fn:contains(checkString,value.id) }">checked="checked"</c:if>/>${value.name }</label>
												</c:if>
											</c:forEach>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-4 control-label">查询地址</label>
										<div class="col-sm-8">
											<div style="width: 75%;display: inline-block;">
												<input type="text" class="form-control address" maxlength="200" value=""/>
											</div>
											<input type="button" class="button" value="查询" onclick="searchLocalMap();"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">详细地址</label>
										<div class="col-sm-8">
											<input type="text" class="form-control addressDetail" maxlength="200" value="${entity.address }" name="address"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">经度</label>
										<div class="col-sm-8">
											<input type="text" class="form-control mapLg" maxlength="200" name="lng" value="${entity.lng }" name="lng"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">纬度</label>
										<div class="col-sm-8">
											<input type="text" class="form-control mapLt" maxlength="200" name="lat" value="${entity.lat }" name="lat"/>
										</div>
									</div>
								</div>
								<div class="form-plan-div">
									<div class="allmap">
										<div id="allmap"></div>
										<div style="clear: both;"></div>
									</div>
								</div>
							</div>
							<%-- <div class="panel-heading" style="border-top: solid 1px #ddd;">
								<h4 class="panel-title">经营范围</h4>
								<div style="position: absolute;right: 25px;top: 15px;">
									<select id="serviceRoot" style="width: 100px;">
										<c:forEach items="${serviceTypes }" var="serviceEntity">
											<option value="${serviceEntity.id }">${serviceEntity.name }</option>
										</c:forEach>
									</select>
									<button type="button" onclick="addServiceTypeTr();">添加</button>
								</div>
							</div>
							<div class="table-responsive">
								 <table id="listTable" class="table table-info serivceTable" style="display: none;">
						        	<thead>
										<tr>
											<th>
												服务名称
											</th>
											<th>
												服务详情
											</th>
											<th>
												排序
											</th>
											<th>
												操作
											</th>
										</tr>
									</thead>
									<c:forEach var="manage" items="${entity.serviceStoreManage }">
										<tr class="dataTr">
											<td>
												<input type='hidden' name='manageId' value='${manage.id }' />
												<input type='hidden' name='serviceId' value='${manage.servicesType.id }' />
												<span style='height: 40px;line-height: 40px;'>${manage.servicesType.name }</span>
											</td>
											<td style="width: 60%;">
												<input type="text" class="form-control" maxlength="200" value="${manage.remark }" name="remark"/>
											</td>
											<td>
												<input type="text" class="form-control" value="${manage.order }" style="width: 50px;" name="order"/>
											</td>
											<td>
												<span style="height: 40px;line-height: 40px;">
													<a href='javascript:void(0);' onclick="deleteTr(this);">[删除]</a>
												</span>
											</td>
										</tr>
									</c:forEach>
									<tr>
										<td>
											<span></span>
										</td>
										<td style="width: 60%;">
											<input type="text" class="form-control" maxlength="200" value=""/>
										</td>
										<td>
											<input type="text" class="form-control" value="" style="width: 50px;"/>
										</td>
										<td>
											<span style="height: 40px;line-height: 40px;">
												<a href="javascript:delete(this);">[删除]</a>
											</span>
										</td>
									</tr>
								</table>
							</div>
							<input type="hidden" value="" name="manageContent" id="manageContent"/> --%>
							<div class="panel-footer">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button id="addCate" class="btn btn-primary mr5"
											onclick="submitForm();" type="button">提交</button>
										<button class="btn btn-dark" type="reset">重置</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="bottomwrapper">
					<%@ include file="/WEB-INF/page/common/bottom.jsp"%>
				</div>
			</div>
			<!-- mainpanel -->
		</div>
		<!-- mainwrapper -->
	</section>
	<jsp:include page="../common/imgUpload.jsp"></jsp:include>
	<script type="text/javascript">
	
		var startTime = "${startTime}";
		var entTime = "${endTime}";
		
		$('[data-toggle="tooltip"]').popover();
		$(document).ready(function(){
			jQuery('#start').timepicker({showMeridian: false, defaultTime:startTime+""});
            jQuery('#end').timepicker({showMeridian: false,defaultTime:entTime+""});
			$("#serviceType").select2({
				
			});
			$("#serviceRoot").select2({
				minimumResultsForSearch : -1
			});
			
			hideTable();
		});
		function saveType(){
			$("#inputForm").submit();
		}
		
		//时间类型
		function getLaydate(obj){
			var option = {
				elem : '#'+obj,
				format : 'hh:mm',
				istime : true,
				istoday : false
			};
			laydate(option);
		};
		
		//添加服务类型
		function addServiceTypeTr(){
			
			var optionId = $("#serviceRoot").val();
			var optionName = $("#serviceRoot").find("option:selected").text();
			
			var trHtml = "<tr class='dataTr'><td><input type='hidden' name='manageId' value='' />"+
			"<input type='hidden' name='serviceId' value='"+optionId+"' /><span style='height: 40px;line-height: 40px;'>"+optionName+"</span>"+
			"</td><td style='width: 60%;'>"+
			"<input type='text' class='form-control' maxlength='200' value='' name='remark'/>"+
			"</td><td>"+
			"<input type='text' class='form-control' value='' name='order' style='width: 50px;'/>"+
			"</td><td>"+
			"<span style='height: 40px;line-height: 40px;'>"+
				"<a href='javascript:void(0);' onclick='deleteTr(this);'>[删除]</a>"+
			"</span></td></tr>";
			
			$(".serivceTable").append(trHtml);
			$(".serivceTable").show();
		}
		//删除经营
		function deleteTr(obj){
			console.log(obj);
			var tr = $(obj).closest("tr.dataTr");
			console.log(tr);
			tr.remove();
			hideTable();
		}
		//隐藏table
		function hideTable(){
			if($(".serivceTable").find("tr.dataTr").length <= 0){
				$(".serivceTable").hide();
			}else{
				$(".serivceTable").show();
			}
		}
		
		//提交表单
		function submitForm(){
			var streetId = $("#street").val();  
			if(streetId == ""){
				alert("请选择完整的区域");
				return;
			}
			
			var manaContentElement = $(".serivceTable").find("tr.dataTr");
			var array = new Array();
		
			for(var i = 0; i<manaContentElement.length; i++){
				var value = manaContentElement[i];
				var id = $(value).find("input[name='manageId']").val();
				var serviceTypeId = $(value).find("input[name='serviceId']").val();
				var content = $(value).find("input[name='remark']").val();
				var orders = $(value).find("input[name='order']").val();
				var data = {
					id:id,
					serviceTypeId:serviceTypeId,
					content:content,
					orders:orders
				}
				array.push(data);
			};
		
			var jsonArray = JSON.stringify(array);
			
			$("#manageContent").val(jsonArray);
			
			$("#inputForm").submit();
		}
	</script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=9YwQ2uhIdo1tmMsy9E0Y9x6x"></script>
	<script type="text/javascript">
		var mapEntityLg = "${entity.lng}";
		var mapEntityLt = "${entity.lat}";
		var siteName = "${entity.name}";
		var pointDate = {};
		if(mapEntityLg!="" && mapEntityLt!=""){
			pointDate.lg = mapEntityLg;
			pointDate.lt = mapEntityLt;
		}else {
			pointDate = null;
		}
		$(document).ready(function() {
			init();
		});
		var map = new BMap.Map("allmap"); // 创建Map实例 
		var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
		var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
		var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮
		/*缩放控件type有四种类型:
		BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；BMAP_NAVIGATION_CONTROL_ZOOM：仅包含缩放按钮*/
		
		var marker;
		function init() {//如果是新增 就用浏览器定位  如果是修改 则用坐标定位
			if(pointDate != null){
				var point = new BMap.Point(pointDate.lg, pointDate.lt); // 创建点坐标
				map.centerAndZoom(point,14);
				//map.setCenter(point);
				map.enableScrollWheelZoom();                 //启用滚轮放大缩小
				$(".allmap").slideDown();
				addMarker(point,siteName);
				//point = map.getCenter();
				//map.enableAutoResize();
				//alert('您的位置：'+point.lng+','+point.lat);
			}else{
				//var point = new BMap.Point(116.404, 39.915); // 创建点坐标
				map.centerAndZoom("北京",14);    
				map.enableScrollWheelZoom();
				
				/* var myCity = new BMap.LocalCity();
				myCity.get(myFun); */
				
				 //根据浏览器 查找当前位置
				var geolocation = new BMap.Geolocation();
				geolocation.getCurrentPosition(function(r){
					if(this.getStatus() == BMAP_STATUS_SUCCESS){
						//var mk = new BMap.Marker(r.point);
						//map.addOverlay(mk);
						map.panTo(r.point);
						//alert('您的位置：'+r.point.lng+','+r.point.lat);
						//$(".allmap").slideDown();
					}else {
						alert("地图定位出错！！请重新刷新");
					}        
				},{enableHighAccuracy: true});     
			}
			//地图缩放控件
			map.addControl(top_left_control);        
			map.addControl(top_left_navigation);     
			map.addControl(top_right_navigation);  
			elementClick();
		}
		
		function myFun(result){
			var cityName = result.name;
			map.setCenter(cityName);
			//alert("当前定位城市:"+cityName);
		}
		
		var local = new BMap.LocalSearch(map, {
			renderOptions:{map: map}
		});
		
		function searchLocalMap(){
			/*	
				var searchValue = $("#subSelect").val();
				if(searchValue == ""){
					do_toast("请选择市区");
					return;
				}
				var value = $("#subSelect").find("option:selected").text();
			*/
			var value = $(".address").val();
			local.search(value);
		}
		//创建标注物
		function addMarker(point,msg){
			//var label = new BMap.Label("当前所选位置",{offset:new BMap.Size(20,-10)});
			map.removeOverlay(marker);//在地图上移除标志物
			marker = new BMap.Marker(point);//新建标注物
			map.addOverlay(marker);//地图上添加标志物
			//marker.setLabel(label);//添加备注
		}
		//点击事件
		function elementClick(){
			//点击事件
			var geoc = new BMap.Geocoder(); 
			map.addEventListener("click", function(e){        
				var pt = e.point;
				geoc.getLocation(pt, function(rs){
					var addComp = rs.addressComponents;
					var address = addComp.province + " " + addComp.city + " " + addComp.district + " " + addComp.street + " " + addComp.streetNumber; 
					//alert(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber);
					$(".addressDetail").val(address);
					//alert("marker的位置是" + pt.lng + "," + pt.lat);
					$(".maplocation").val(addComp.district + "," + addComp.street + "," + addComp.streetNumber);
					$(".mapLg").val(pt.lng);
					$(".mapLt").val(pt.lat);
				});
				addMarker(pt,$(".siteName").val());
			});
		}
	</script>
</body>
</html>
