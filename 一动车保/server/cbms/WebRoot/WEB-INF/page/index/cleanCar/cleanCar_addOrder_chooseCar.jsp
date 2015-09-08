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
</style>

		<div class="mabg2" id="mabg2"></div>
		<div class="mabg3" id="mabg3"></div>
		<div class="selectCarCon" id="selectCarCon">
			<div class="selectCarTop">
				<b></b>
				<h2>我的爱车</h2>
				<a href="javascript:hideChooseCar();" class="close"><img src="${pageContext.request.contextPath}/resource/page/images/login_2.png" /></a>
			</div>
			<div class="selectBrand padding4" style="position: relative;">
				<input type="text" placeholder="选择品牌" class="carLike carBrand"  onkeyup="getValue(this);" onclick="inputFocus(this)" id="carBrandInput"/>
				<div class="input_search_result">
					<ul id="carBrand"></ul>
				</div>
				<input type="hidden" id="carBrandId"/>
			</div>
			<div class="selectCar padding4" style="position: relative;">
				<input type="text" placeholder="选择车系" class="carLike carType" id="carTypeInput" onkeyup="getValue(this);" onclick="inputFocus(this)"/>
				<div class="input_search_result">
					<ul id="carType"></ul>
				</div>
				<input type="hidden" id="carTypeId"/>
			</div>
			<div class="selectType padding4" style="position: relative;">
				<input type="text" placeholder="选择车型" class="carLike car" id="carInput" onkeyup="getValue(this);" onclick="inputFocus(this)"/>
				<div class="input_search_result">
					<ul id="car"></ul>
				</div>
				<input type="hidden" id="carId"/>
			</div>
			<div class="sure">
				<a href="javascript:sureCar();"><img src="${pageContext.request.contextPath}/resource/page/images/ma32.png" /></a>
			</div>
		</div>		
		<script type="text/javascript">
		
		function getValue(e){
			var keyWord = $(e).val();
			if($(e).hasClass("carBrand")){
				getCarBrand(keyWord);
			}else if($(e).hasClass("carType")){
				getCarType(null,$("#carBrandId").val(),keyWord);
			}
		}
		//获取品牌
		function getCarBrand(keyWord){
			var url ="${pageContext.request.contextPath}/web/car/getCarBrand";
			var data = {
				keyWord : keyWord
			};
			$.post(url,data,function(result){
				var li = "";
				for(var i in result){
					li += "<li onclick='getCarType(this,\""+result[i].id+"\",null)' imgPath='"+result[i].imgPath+"' brandName='"+result[i].name+"' value='"+result[i].id+"'>"+result[i].name+"</li>";
				}
				$("#carBrand").html(li);
				$("#carBrand").parent().show();
			},"json");
		}
		//获取车系
		function getCarType(e,carBrandId,keyWord){
			if(e!=null){
				$("#carBrandInput").val($(e).html());
			}
			$("#carBrandId").val(carBrandId);
			var url ="${pageContext.request.contextPath}/web/car/getCarType";
			var data = {
				carBrandId : carBrandId,
				keyWord : keyWord
			};
			$.post(url,data,function(result){
				var li = "";
				for(var i in result){
					li += "<li onclick='getCar(this,\""+result[i].id+"\")' typeName='"+result[i].name+"' value='"+result[i].id+"'>"+result[i].name+"</li>";
				}
				$("#carType").html(li);
				$("#carBrand").parent().hide();
				$("#carType").parent().show();
			},"json");
		}
		//获取车型
		function getCar(e,carTypeId){
			if(e!=null){
				$("#carTypeInput").val($(e).html());
			}
			$("#carTypeId").val(carTypeId);
			var url ="${pageContext.request.contextPath}/web/car/getCar";
			var data = {
				carTypeId : carTypeId
			};
			$.post(url,data,function(result){
				var li = "";
				for(var i in result){
					li += "<li onclick='getCarId(this,\""+result[i].id+"\")' value='"+result[i].id+"' carName='"+result[i].name+"'>"+result[i].name+"</li>";
				}
				$("#car").html(li);
				$("#carType").parent().hide();
				$("#car").parent().show();
			},"json");
		}
		function getCarId(e,carId){
			$("#carInput").val($(e).html());
			$("#carId").val(carId);
			$(e).parent().parent().hide();
		}
		function inputFocus(e){
			$(".carSelectInput").removeClass("input1");
			$(e).addClass("input1");
			$(".input_search_result").hide();
			if($(e).next().children().children().size() != 0){
				$(e).next().show();
			}
			if($(e).hasClass("carBrand")){
				getCarBrand(null);
			}
		}
		function sureCar(){
			var carId = $("#carId").val();
			
			if(carId == ""){
				hideChooseCar();
				return;
			}
			
			var carName = $("#car").find("li[value='"+carId+"']").attr("carName");
			var carTypeId = $("#carTypeId").val();
			var carTypeName = $("#carType").find("li[value='"+carTypeId+"']").attr("typeName");
			var carBrandId = $("#carBrandId").val();
			var carBrandOption = $("#carBrand").find("li[value='"+carBrandId+"']");
			var carBrandName = $(carBrandOption).attr("brandName");
			var carBrandImg = $(carBrandOption).attr("imgPath");
			var data = {
				brandName:carBrandName,
				brandImg:carBrandImg,
				carTypeName:carTypeName,
				carName:carName,
				carId:carId
			};
			installCarView(data);
			hideChooseCar();
		}
		</script>
