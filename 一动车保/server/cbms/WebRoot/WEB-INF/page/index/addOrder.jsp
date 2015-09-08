<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>填写预约信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/chain/js/laydate/need/laydate.css">
	
	<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resource/js/common.other.js"></script>
	<script src="${pageContext.request.contextPath}/resource/chain/js/laydate/laydate.js"></script>
	<script src="${pageContext.request.contextPath}/resource/js/common.other.js"></script>
	
  </head>
  
  <body>
  	<form action="<%=basePath %>web/save">
  		<div>车型 :${car.name } ，所选服务   ${serviceType.name }</div>
  		<input name="serviceType.id" type="text" value="${serviceType.id }" id="serviceType"/>
  		<input name="car.id" type="text" value="${car.id }"/>
  		<div>配送方式:
  			<label><input type="radio" name="serverWay" value="1"/>上门服务</label>
	  		<c:if test="${serviceType.type eq 1}">
	  			<label><input type="radio" name="serverWay" value="2"/>门店安装</label>
	  		</c:if>
  		</div>
  		<div>
  			支付方式:
  			<label><input type="radio" name="payWay" value="1"/>货到付款</label>
	  		<c:if test="${serviceType.type eq 1}">
	  			<label><input type="radio" name="payWay" value="2"/>支付宝</label>
	  			<label><input type="radio" name="payWay" value="3"/>银联</label>
	  		</c:if>
  		</div>
	   	<div>
	   		预约信息:
	   		<div>您的姓名：<input value="" name="contact"/><span class="contact_text"></span></div>
	   		<div>您的电话：<input value="" name="phone"/><span class="phone_text"></span></div>
	   		<!-- 	<div>验证码：<input value="" name="code"/></div> -->
	   		<div>预约地点：
	   			<select onchange="getAreaList(this,1);" >
	   				<option value="">市</option>
	   				<c:forEach var="area" items="${areas }">
	   					<option value="${area.id }" <c:if test="${area.children.size()>0 }">class="hasNext"</c:if> >${area.name }</option>
	   				</c:forEach>
	   			</select>
	   			<select id="county" onchange="getAreaList(this,2);">
	   				<option value="">县区</option>
	   			</select>
	   			<select id="street" name="area.id">
	   				<option value="">街道</option>
	   			</select>
	   			<span class="name_text"></span></div>
	   		<div>详细地址：<input value="" name="address"/><span class="address_text"></span></div>
	   		<div>可预约时间：
					<input id="reportDay" name="registdate" onclick="getLaydate('reportDay')" >
					<select id="timeMap" name="timeMap">
						
					</select>
				<span class="name_text"></span></div>
	   	</div>
	   	<button>预约服务</button>
   	</form>
  </body>
  <script type="text/javascript">
   var base = "<%=basePath%>";
  	$(document).ready(function(){
		  		
  	});
  //设置时间
  	function getLaydate(obj){
	  var date = new Date();
	 var nowDate = date.format("yyyy-MM-dd");
  		var option = {
  			elem : '#'+obj,
  			format : 'YYYY-MM-DD',
  			istime : false,
  			min: nowDate,
  			choose : function(date){
  				getTime();
  			}
  		};
  		laydate(option);
  	}
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
  	
  	function checkIsNull(e,msg){
  		var name = $(e).attr("name");
  		var span = $("."+name+"_text");
  		if($(e).val().trim()!=""){
  			span.text("");
  			return true;
  		}
  		span.text(msg);
  		return false;
  	}
  	
  	function getTime(){
  		var time = $("#reportDay").val();
  		var serviceType = $("#serviceType").val();
  		var areaId = $("#street").val();
  		if(areaId == "" || time=="" || serviceType==""){
  			return;
  		}
  		var url = base + "/web/getTimeMap";
  		var data = {
  			areaId:areaId,
  			serviceTypeId:serviceType,
  			date:time
  		};
  		$.post(url,data,function(result){
  			if(result.status == "success"){
  				addTimeMap(result.message);
  			}
  		});
  	}
  	
  	function addTimeMap(msg){
  		var select = $("#timeMap");
  		var timeMap = "";
  		for(var i in msg){
  			timeMap += "<option value='"+msg[i].dateMap+"'>"+msg[i].date+"</option>";
  		}
  		select.html(timeMap);
  	}
  </script>
</html>
