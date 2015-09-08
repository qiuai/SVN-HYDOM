<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<style type="text/css">
.input_search_result {
	position: absolute;
	height: 125px;
	/* 		margin-left: 20px; */
	border: 1px solid #ddd;
	overflow-y: auto;
	width: 94%;
	background-color: white;
	display: none;
	z-index: 100;
}

.input_search_result ul li {
	cursor: pointer;
}

.input_search_result ul li:HOVER {
	background-color: beige;
}

#selectCarCon{
	height: 530px;
}
.selectMyCar{
	height: auto;
}
.selectedLikes img{
	width: 70px;
	height: 35px;
}
</style>

<!--弹出层-->
<div class="mabg2" id="mabg2"></div>
<div class="mabg4 userCarListDIV" id="mabg4" style="height: auto;"></div>
<div class="selectMyCar userCarListDIV" id="selectMyCar">
	<div class="selectMyCarTop">
		<b></b>
		<h2>选择我的爱车</h2>
		<a href="javascript:hideChooseUserCar();" class="close"><img src="${pageContext.request.contextPath}/resource/page/images/login_2.png" /></a>
	</div>
	<ul class="selectMyCarCon" id="selectMyCarCon">
		<%-- 车列表 --%>
	</ul>
	<div class="selectMyCarBto">
		<a href="javascript:showAddUserCarDiv();"><img src="${pageContext.request.contextPath}/resource/page/images/like5.png" /></a>
		<a href="javascript:suerUserCar();"><img src="${pageContext.request.contextPath}/resource/page/images/like6.png" /></a>
	</div>
</div>
<script>

function selectMyCar(){
	$(document).on("click",".selectedLikes",function(){
		$(".selectedLikes").find("b").removeClass("selectedLike");
		$(this).find("b").addClass("selectedLike");
	});
}

</script>

<%--添加车辆弹出层 --%>
<div class="mabg3 addUserCarDIV" id="mabg3" style="height: 542px;display:none;"></div>
<div class="selectUserCarCon addUserCarDIV" id="selectCarCon" style="display:none;">
	<div class="selectCarTop">
		<b></b>
		<h2>新增爱车</h2>
		<a href="javascript:hideAddUserCarDiv();" class="close"><img
			src="${pageContext.request.contextPath}/resource/page/images/login_2.png" /></a>
	</div>
	<div class="selectBrand padding4" style="position: relative;">
		<input type="text" placeholder="选择品牌" class="carLike carBrand"
			onkeyup="getValue(this);" onclick="inputFocus(this)"
			id="carBrandInput" />
		<div class="input_search_result">
			<ul id="carBrand"></ul>
		</div>
		<input type="hidden" id="carBrandId" />
	</div>
	<div class="selectCar padding4" style="position: relative;">
		<input type="text" placeholder="选择车系" class="carLike carType"
			id="carTypeInput" onkeyup="getValue(this);"
			onclick="inputFocus(this)" />
		<div class="input_search_result">
			<ul id="carType"></ul>
		</div>
		<input type="hidden" id="carTypeId" />
	</div>
	<div class="selectType padding4" style="position: relative;">
		<input type="text" placeholder="选择车型" class="carLike car"
			id="carInput" onkeyup="getValue(this);" onclick="inputFocus(this)" />
		<div class="input_search_result">
			<ul id="car"></ul>
		</div>
		<input type="hidden" id="carId" />
	</div>
	<div class="selectType padding4" style="position: relative;">
		<input type="text" placeholder="车辆颜色" class="carLike carColor"
			id="usercarColor" />
	</div>
	<div class="selectType padding4" style="position: relative;">
		<input type="text" placeholder="车牌号" class="carLike carNum"
			id="usercarNum" />
	</div>
	<div class="sure">
		<a href="javascript:addUserCar();"><img
			src="${pageContext.request.contextPath}/resource/page/images/ma32.png" /></a>
	</div>
</div>
<script type="text/javascript">
	function getValue(e) {
		var keyWord = $(e).val();
		if ($(e).hasClass("carBrand")) {
			getCarBrand(keyWord);
		} else if ($(e).hasClass("carType")) {
			getCarType(null, $("#carBrandId").val(), keyWord);
		}
	}
	//获取品牌
	function getCarBrand(keyWord) {
		var url = "${pageContext.request.contextPath}/web/car/getCarBrand";
		var data = {
			keyWord : keyWord
		};
		$.post(url, data, function(result) {
			var li = "";
			for ( var i in result) {
				li += "<li onclick='getCarType(this,\"" + result[i].id
						+ "\",null)' imgPath='" + result[i].imgPath
						+ "' brandName='" + result[i].name + "' value='"
						+ result[i].id + "'>" + result[i].name + "</li>";
			}
			$("#carBrand").html(li);
			$("#carBrand").parent().show();
		}, "json");
	}
	//获取车系
	function getCarType(e, carBrandId, keyWord) {
		if (e != null) {
			$("#carBrandInput").val($(e).html());
		}
		$("#carBrandId").val(carBrandId);
		var url = "${pageContext.request.contextPath}/web/car/getCarType";
		var data = {
			carBrandId : carBrandId,
			keyWord : keyWord
		};
		$.post(url, data, function(result) {
			var li = "";
			for ( var i in result) {
				li += "<li onclick='getCar(this,\"" + result[i].id
						+ "\")' typeName='" + result[i].name + "' value='"
						+ result[i].id + "'>" + result[i].name + "</li>";
			}
			$("#carType").html(li);
			$("#carBrand").parent().hide();
			$("#carType").parent().show();
		}, "json");
	}
	//获取车型
	function getCar(e, carTypeId) {
		if (e != null) {
			$("#carTypeInput").val($(e).html());
		}
		$("#carTypeId").val(carTypeId);
		var url = "${pageContext.request.contextPath}/web/car/getCar";
		var data = {
			carTypeId : carTypeId
		};
		$.post(url, data, function(result) {
			var li = "";
			for ( var i in result) {
				li += "<li onclick='getCarId(this,\"" + result[i].id
						+ "\")' value='" + result[i].id + "' carName='"
						+ result[i].name + "'>" + result[i].name + "</li>";
			}
			$("#car").html(li);
			$("#carType").parent().hide();
			$("#car").parent().show();
		}, "json");
	}
	function getCarId(e, carId) {
		$("#carInput").val($(e).html());
		$("#carId").val(carId);
		$(e).parent().parent().hide();
	}
	function inputFocus(e) {
		$(".carSelectInput").removeClass("input1");
		$(e).addClass("input1");
		$(".input_search_result").hide();
		if ($(e).next().children().children().size() != 0) {
			$(e).next().show();
		}
		if ($(e).hasClass("carBrand")) {
			getCarBrand(null);
		}
	}
	
	
	
	
	
	function showAddUserCarDiv(){
		$(".userCarListDIV").hide();
		$(".addUserCarDIV").show();
	}
	
	function hideAddUserCarDiv(){
		$(".userCarListDIV").show();
		$(".addUserCarDIV").hide();
	}
	
	
	function addUserCar() {
		var carId = $("#carId").val();
		var carColor = $("#usercarColor").val();
		var carNum = $("#usercarNum").val();
		if(carColor == ""){
			alert("请填写车辆颜色");
			return;
		}
		if(carNum == ""){
			alert("请填写车牌号");
			return;
		}
		var url = "<%=basePath%>web/saveUserCar";
		var data = {
			carId:carId,
			carColor:carColor,
			carNum:carNum
		};
		
		$.post(url,data,function(result){
			if(result.status == "success"){
				alert("添加成功");
				var  url= "<%=basePath%>web/loadUserCarList";
				var data = {};
				$("#selectMyCarCon").load(url,data,function(){
					hideAddUserCarDiv();
				});
			}
		});
	}
	//确定选择的车型
	function suerUserCar(){
		var userCarid = $(".selectedLikes").find("b.selectedLike").attr("id");
		var url = "<%=basePath%>web/getUserCarView";
		var data = {userCarId:userCarid};
		$.post(url,data,function(result){
			if(result.status == "success"){
				initChooseUserCar(result.message);
				hideChooseUserCar();
			}
		});
	}
	
</script>



