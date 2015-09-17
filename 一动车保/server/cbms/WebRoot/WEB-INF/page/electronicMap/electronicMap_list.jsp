<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
	<meta name="description" content="">
	<title>电子地图</title>
	<link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resource/css/manage.common.css" rel="stylesheet">
	
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/modernizr.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/pace.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/retina.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery.cookies.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
	<script src="${pageContext.request.contextPath}/resource/js/myform.js" type="text/javascript"></script>
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.min.js"></script>
	<![endif]-->
<!-- 	高德地图 -->
	<link rel="stylesheet" href="http://cache.amap.com/lbs/static/main.css"/>
  	<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=cce8fefdd06bf061a11a979c7e997922"></script>
  	<style type="text/css">
        #tip {
            height: 45px;
            background-color: #fff;
            padding-left: 10px;
            padding-right: 10px;
            border: 1px solid #969696;
            position: absolute;
            font-size: 12px;
            right: 10px;
            bottom: 20px;
            border-radius: 3px;
            line-height: 45px;
        }
    </style>
</head>

<body>
	<%@ include file="/WEB-INF/page/common/head.jsp"%>
	<section>
		<div class="mainwrapper">
			<%@ include file="/WEB-INF/page/common/left.jsp"%>
			<div class="mainpanel">
				<div class="pageheader">
					<div class="media">
						<div class="media-body">
							<ul class="breadcrumb">
								<li><a href=""><i class="glyphicon glyphicon-home"></i></a></li>
								<li><a href="">电子地图</a></li>
							</ul>
						</div>
					</div>
					<!-- media -->
				</div>
				<form id="pageList"
					action="${pageContext.request.contextPath}/manage/electronicMap/list"
					method="post">
					<div class="contentpanel">
						<div class="search-header">
							<div class="btn-list">
								<button class="btn btn-success" id="refreshButton">刷新</button>
								<div style="float: right;max-width: 340px;height: 37px;">
									<div class="input-group" style="float: left;max-width: 240px;">
										<input id="searchValue" placeholder="按技师姓名或手机号查询" name="queryContent" value="${queryContent}"
										type="text" class="form-control" maxlength="50" style="height: 37px">
									</div>
									<div style="float: right">
										<button type="button" class="btn btn-info btn-metro" onclick="findTech()">查询</button>
									</div>
								</div>
							</div>
						</div>
						<div class="table-responsive" style="height: 740px; width: 100%;display: inline-block;position: relative;">
							<div id="mapContainer"></div>
							<div id="tip">
								省：
								<select id='province' style="width:100px" onchange='findAreaChildren(this);searchArea(this)'>
									<c:forEach items="${rootArea}" var="entry">
										<option value="">请选择</option>
										<option value="${entry.id }">${entry.name }</option>
									</c:forEach>
								</select>
								市：<select id='city' style="width:100px" onchange='findAreaChildren(this);searchArea(this)'></select>
								区：<select id='district' style="width:100px" onchange='searchArea(this)'></select>
							</div>
							<script type="text/javascript">
								
								var map = new AMap.Map('mapContainer');
								
 								//设置城市
								function searchArea(e){
									map.setCity($(e).find("option:selected").text());
								}

								//添加点标记
								function addMarker(lng, lat, name, phonenumber, area) {
									marker = new AMap.Marker(
											{
												icon : "http://webapi.amap.com/images/marker_sprite.png",
												position : [ lng, lat ]
											});
									marker.setMap(map); // 在地图上添加点
									marker.setTitle(area);// 设置鼠标划过点标记显示的文字提示
									marker.setLabel({//label的父div默认蓝框白底右下角显示，样式className为：amap-marker-label
										offset : new AMap.Pixel(-30, -35),//修改父div相对于maker的位置
										content : name + "<br/>" + phonenumber
									});
								}

								$(function() {
									markTech();
// 									map.setLimitBounds(map.getBounds());
								});
								
								function findTech(){
									markTech();
									map.setFitView(); // 调整到合理视野
								}

								function markTech() {
									var url = "${pageContext.request.contextPath}/manage/electronicMap/getTech";
									var data = {
										queryContent : $("#searchValue").val()
									};
									$.post(url, data, function(result) {
										map.clearMap();
										for ( var i in result) {
											addMarker(result[i].lng, result[i].lat, result[i].name,
											result[i].phonenumber, result[i].area);
										}
									}, "json");
								}

// 								map.plugin([ "AMap.CitySearch" ], function() {
// 									var citysearch = new AMap.CitySearch();
// 									citysearch.getLocalCity();

// 									AMap.event.addListener(citysearch,"complete", function(result) {
// 										var citybounds;
// 										if (result && result.city && result.bounds) {
// 											citybounds = result.bounds;
// 											map.setBounds(citybounds);
// 										}
// 									});
// 								});
								
								function findAreaChildren(e){
									var url = "${pageContext.request.contextPath}/manage/electronicMap/findAreaChildren";
									var data = {
										rootAreaId : $(e).val()
									};
									$.post(url, data, function(result) {
										var child = "<option value=''>请选择</option>";
										for ( var i in result) {
											child += "<option value="+result[i].areaId+">"+result[i].areaName+"</option>";
										}
										$(e).next().html(child);
									}, "json");
								}
							</script>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- mainwrapper -->
	</section>

</body>
</html>
