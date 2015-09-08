<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
	<title>地图显示</title>
	<style type="text/css">
		body{margin:0;height:100%;width:100%;position:absolute;}
		#mapContainer{height:100%;width:100%;}
	</style>
</head>
<body>
	<div style="height: 600px; width: 100%;display: inline-block;position: relative;">
		<div id="mapContainer"></div>
	</div>
	<div> 
		<div>需要动态标志物 点击获取坐标事件</div>
			<div>经度:<input id="lng"/></div>
			<div>纬度:<input id="lat"/></div>
			<div>详细地址:<input id="address" style="width: 400px;"/></div>
		
			<div><button onclick="addMarkerValue();">添加标志物</button></div>
	</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=cce8fefdd06bf061a11a979c7e997922"></script>
	<script type="text/javascript">
		var map,geolocation,userMarker;
		var markers = new Array();
		$(document).ready(function(){
			init();
			
		});
		
		function init(){
			map = new AMap.Map('mapContainer', {
				resizeEnable: true
			});
			//地图中添加地图操作ToolBar插件
			map.plugin(['AMap.ToolBar','AMap.Geolocation'],function(){
				//设置地位标记为自定义标记
				var toolBar = new AMap.ToolBar(); 
				map.addControl(toolBar);
				//浏览器定位
				geolocation = new AMap.Geolocation({
					enableHighAccuracy: true,//是否使用高精度定位，默认:true
					timeout: 10000,          //超过10秒后停止定位，默认：无穷大
					maximumAge: 0,           //定位结果缓存0毫秒，默认：0
					convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
					showButton: false,        //显示定位按钮，默认：true
					showMarker: false,        //定位成功后在定位到的位置显示点标记，默认：true
					showCircle: false,        //定位成功后用圆圈表示定位精度范围，默认：true
					panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
					zoomToAccuracy:false      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
				});
		        map.addControl(geolocation);
				AMap.event.addListener(geolocation, "complete", onComplete);//返回定位信息
				AMap.event.addListener(geolocation, "error", onError);      //返回定位出错信息
				//浏览器定位
				geolocation.getCurrentPosition();
			});	
			
			//绑定点击事件
			AMap.event.addListener(map,'click',function(e){
				userMarker.setMap(null);
				var lng = e.lnglat.getLng();
				var lat = e.lnglat.getLat();  
				geocoder(lng,lat);
				addUserMarker(lng,lat);
			});
		}
		//监控当前位置并获取当前位置信息
		function watchPosition () {
			geolocation.watchPosition();
		};
		
		//解析定位结果
		function onComplete (data) {
			var lng = data.position.getLng();
			var lat = data.position.getLat();
			$("#lng").val(lng);
			$("#lat").val(lat);
			geocoder(lng,lat);
			addUserMarker(lng,lat);
			//addMarker(lng,lat);
			/* var str = '<div>定位成功</div>';
			str += '<div>经度：' + data.position.getLng() + '</div>';
			str += '<div>纬度：' + data.position.getLat() + '</div>'; 
			str += '<div>精度：' + data.accuracy + ' 米</div>';
			str += '<div>是否经过偏移：' + (data.isConverted ? '是' : '否') + '</div>';
			result.innerHTML = str; */
		};
		//解析定位错误信息
		function onError (data) {
			var str = "<p>定位失败</p>";
			str += "<p>错误信息：";
			switch(data.info) {
				case "PERMISSION_DENIED":
					str += "浏览器阻止了定位操作";
					break;
				case "POSITION_UNAVAILBLE":
					str += "无法获得当前位置";
					break;
				case 'TIMEOUT':
					str += "定位超时";
					break;
				default:
					str += "未知错误";
					break;
			}
			str += "</p>";
			alert(str);
		};
		function addMarkerValue(){
			var lng = $("#lng").val();
			var lat = $("#lat").val();
			addMarker(lng,lat);
		}
		
		//添加用户标志物
		function addUserMarker(lng,lat){
			  var lnglatXY = new AMap.LngLat(lng,lat);
			userMarker = new AMap.Marker({				  
				icon:"http://webapi.amap.com/images/marker_sprite.png",
				position:lnglatXY,
			   	draggable:true, //点标记可拖拽
			    cursor:'move',  //鼠标悬停点标记时的鼠标样式
		        raiseOnDrag:true//鼠标拖拽点标记时开启点标记离开地图的效果
			});
			userMarker.setMap(map);  //在地图上添加点
			map.setFitView(); //调整到合理视野
			map.setZoomAndCenter(18, lnglatXY);
			//用户标志物 拖拽结束事件
			AMap.event.addListener(userMarker,'dragend',function(e){
				console.log(e);
				var lng = e.lnglat.lng;
				var lat = e.lnglat.lat;  
				$("#lng").val(lng);
				$("#lat").val(lat);
				geocoder(lng,lat);
			});
		}
		
		//添加标志物
		function addMarker(lng,lat){
			var marker = new AMap.Marker({				  
				/* icon:"http://webapi.amap.com/images/marker_sprite.png", */
				position:new AMap.LngLat(lng,lat)
			});
			marker.setMap(map);  //在地图上添加点
			map.setFitView(); //调整到合理视野
		}
		//坐标变成地址
		function geocoder(lng,lat) {
		    var lnglatXY = new AMap.LngLat(lng,lat);
		    //加载地理编码插件
		    AMap.service(["AMap.Geocoder"], function() {        
		    	var MGeocoder = new AMap.Geocoder({ 
		            extensions: "base"
		        });
		        //逆地理编码
		        MGeocoder.getAddress(lnglatXY, function(status, result){
		        	if(status === 'complete' && result.info === 'OK'){
		        		geocoder_CallBack(result);
		        	}
		        });
		    });
		}
		//坐标变成地址 回调函数
		function geocoder_CallBack(data) {
		  /*   var resultStr = "";
		    var poiinfo="";
		    var address; */
		    //返回地址描述
		   var address = data.regeocode.formattedAddress;
		   $("#address").val(address);
		   /*  //返回周边兴趣点信息
		    poiinfo += "<table style='width:300px;cursor:pointer;'>";
		    for(var j=0;j<data.regeocode.pois.length;j++){
		        var color = j % 2 === 0 ? '#fff' : '#eee';
		        poiinfo += "<tr onmouseover='onMouseOver(\"" + data.regeocode.pois[j].location.toString() + "\")' style='background-color:" + color + "; margin:0; padding:0;'><td>" + data.regeocode.pois[j].name + "</td><td>距离：" + data.regeocode.pois[j].distance + "米</td></tr>";
		    }
		    poiinfo += "</table>";
		    //返回结果拼接输出
		    resultStr = "<div style=\"font-size: 12px;padding:0px 0 4px 2px; border-bottom:1px solid #C1FFC1;\">"+"<b>地址</b>："+ address + "<hr/><b>周边兴趣点信息</b>：<br/>" + poiinfo +"</div>";
		    document.getElementById("result").innerHTML = resultStr; */
		}  
	</script>
</body>
</html>