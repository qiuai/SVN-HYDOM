<%@ page language="java" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<title>添加爱车</title>
	<link href="<%=basePath %>resource/page/css/carSteward/style.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="<%=basePath %>resource/page/js/jquery.js"></script>
	<!-- 日期插件需要的包 -->
	<link rel="stylesheet" href="<%=basePath %>resource/page/js/carSteward/development-bundle/themes/ui-lightness/jquery.ui.all.css">
	<script type="text/javascript" src="<%=basePath %>resource/page/js/carSteward/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=basePath %>resource/page/js/carSteward/development-bundle/ui/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="<%=basePath %>resource/page/js/carSteward/development-bundle/ui/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="<%=basePath %>resource/page/js/carSteward/development-bundle/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
	
	<style type="text/css">
		/******form*******/
		.divselectcon {clear: both; padding: 10px 0 20px; position: relative; }
		.divselect {float: left; }
		.divselectcon input {width:238px; height: 35px; margin: 0 20px; position:relative; z-index:10000; border: 1px solid #b1b1b1; border-top: 2px solid #b1b1b1; line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(images/corners2.png) no-repeat right center;}
		.divselectcon input.input1 {border:1px solid #ffae00; border-top: 2px solid #ffae00; margin-left: 0; width: 228px; height:35px;line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(images/corners1.png) no-repeat right center;}
		#button2 button {float: right; width: 169px; height: 43px; background: url(images/go.png) 0 0 no-repeat; border: none; position: absolute; top: 9px;  cursor: pointer; }
		.bdsug_copy{display:none;}		
	</style>
	<style type="text/css">
		.img_div{
			display: inline-block;
			height: 167px;
			width: 167px;
		}
		.img_div img{
			display: inline-block;
			height: 167px;
			width: 167px;
		}
		.input_search_result {
			position: absolute;
			height: auto;
			margin-left: 70px;
			margin-top: -12px;
			border: 1px solid #ddd;
			overflow-y: auto;
			width: 17.25%;
			background-color: white;
			display: none;
		}
		.input_search_result ul li {
			cursor: pointer;
		}
		.input_search_result ul li:HOVER {
			background-color: beige;
		}
		.errorStyle{
			color: red;
		}
		.selectedLikes{
			width: 80px;
			height: 30px;
		}
	</style>
	<script type="text/javascript">
		//日期插件
		$(function(){
			$.datepicker.setDefaults( $.datepicker.regional[ "zh-CN" ] );
		
			$("#roadDate").datepicker({
				changeMonth: true,
				changeYear: true
			});
			$("#roadDate").datepicker("option", "dateFormat", "yy/mm/dd");
		});
		
		function getValue(e){
			var keyWord = $(e).val();
			if($(e).hasClass("carBrand")){
				getCarBrand(keyWord);
			}else if($(e).hasClass("carType")){
				getCarType(null,$("#carBrandId").val(),keyWord);
			}else if($(e).hasClass("car")){
				getCar(null,$("#carTypeId").val());
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
					li += "<li onclick='getCarType(this,\""+result[i].id+"\",null)'>"+result[i].name+"</li>";
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
					li += "<li onclick='getCar(this,\""+result[i].id+"\")'>"+result[i].name+"</li>";
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
					li += "<li onclick='getCarId(this,\""+result[i].id+"\",\""+result[i].imgPath+"\")'>"+result[i].name+"</li>";
				}
				$("#car").html(li);
				$("#carType").parent().hide();
				$("#car").parent().show();
			},"json");
		}
		function getCarId(e,carId,imgPath){
			$("#carInput").val($(e).html());
			$("#carId").val(carId);
			$("#show_img").attr("src",'<%=basePath %>'+imgPath);
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
		function carSubmit(){
			checkNull($("#carId"));
			checkNull($("#carColor"));
			checkNull($("#carNum"));
			var flag = true;
			$(".repeat").each(function(){
				if($(this).val()!="success") flag = false;
			});
			if(flag){
				$("#inputForm").submit();
			}
		}
		function checkNull(v){
		if($(v).val() == ''){
			$("#"+$(v).attr("name")+"_error").html($(v).attr("CHname")+"不能为空");
			$(v).next().val("");
	    } else {
			$("#"+$(v).attr("name")+"_error").html("");
			$(v).next().val("success");
		}
		if($(v).attr("name")=="initial") $(v).val($(v).val().toUpperCase());
	}
	</script>
</head>

<body>
<!--上部开始-->
<jsp:include page="../header.jsp"/>
<!--上部结束-->
<hr>
<!--中部开始-->
<div class="box3">
<div class="mid box0">
	<div class="steward1-content">
		<div class="steward1-menu" id="steward1-menu">
			<div class="menu-title menu-order">
				<h3>订单中心</h3>
			</div>
			<ul class="menu-list">
				<li class="menu-item myOrder">
					<a href="${pageContext.request.contextPath}/user/order/list">我的订单</a>
				</li>
				<li class="menu-item cancelOrder">
					<a href="${pageContext.request.contextPath}/user/order/cancellist">已取消的订单</a>
				</li>
				<li class="menu-item myCoupon">
					<a href="${pageContext.request.contextPath}/user/myCoupon/list">我的优惠券</a>
				</li>
				<li class="menu-item couponPackage">
					<a href="${pageContext.request.contextPath}/user/couponPackage/list">我的会员卡</a>
				</li>
			</ul>
			<div class="menu-title menu-home active">
				<h3>我的车管家</h3>
			</div>
			<ul class="menu-list">
				<li class="menu-item myCarModel on">
					<a href="${pageContext.request.contextPath}/user/carSteward/list">我的车型库</a>
				</li>
			</ul>
			<div class="menu-title menu-center">
				<h3>账户中心</h3>
			</div>
			<ul class="menu-list">
				<li class="menu-item accountBal">
					<a href="${pageContext.request.contextPath}/user/balance/view">账户余额</a>
				</li>
				<li class="menu-item basicInfo">
					<a href="${pageContext.request.contextPath}/user/information/info">基本信息</a>
				</li>
				<li class="menu-item feedBack">
					<a href="${pageContext.request.contextPath}/user/feedback/add">意见反馈</a>
				</li>
			</ul>
		</div>
		<div class="steward1-detail" id="steward1-detail">
			<div id="myOrderContent" class="myOrderContent myOrderDetail" style="border: none; "></div>
			<div id="cancelOrderContent" class="cancelOrderContent myOrderDetail"></div>
			<div id="myCouponContent" class="myCouponContent myOrderDetail"></div>
			<div id="myCarModelContent" class="myCarModelContent myOrderDetail">
				<div class="orderDetails" style="border: none; ">
					<div class="orderDetailsTop">
						<div class="left">
							<a><img src="<%=basePath %>resource/page/images/steward_4.png" /></a><span class="span1">|</span><span>爱车基本信息</span>
						</div>
					</div>
					<div class="orderDetailsContent">
						<div class="orderDetailsContentLeft">
							<div class="img_div">
								<img src="<%=basePath %>${userCar.car.imgPath }" onerror="<%=basePath %>resource/page/images/steward_6.png" id="show_img"/>
							</div>
						</div>
						<div class="orderDetailsContentRight">
							<form id="inputForm" action="<%=basePath %>user/carSteward/save" method="post">
								<input type="hidden" name="id" value="${userCar.id }">
								<input type="hidden" name="defaultCar" value="${userCar.defaultCar }">
								<div style="display: inline-block;">
									品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：<input type="text" placeholder="选择品牌" 
									autocomplete="off" class="carSelectInput carBrand selectBrand selectInput"
										id="carBrandInput" onkeyup="getValue(this);" onclick="inputFocus(this)" value="${userCar.car.carBrand.name }"/>
									<div class="input_search_result">
										<ul id="carBrand" style="overflow-y: auto; height: 250px;"></ul>
									</div>
									<input type="hidden" id="carBrandId" value="${userCar.car.carBrand.id }"/>
								</div>
								<div style="display: inline-block;">
									车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系：<input type="text" placeholder="选择车系" 
									autocomplete="off" class="carSelectInput carType selectCars selectInput"
									id="carTypeInput" onkeyup="getValue(this);" onclick="inputFocus(this);getValue(this)" value="${userCar.car.carType.name }" />
									<div class="input_search_result">
										<ul id="carType" style="overflow-y: auto; height: 250px;"></ul>
									</div>
									<input type="hidden" id="carTypeId" value="${userCar.car.carType.id }"/>
								</div>
								<div style="display: inline-block;">
									车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：<input type="text" placeholder="选择车型" class="carSelectInput car selectType selectInput" 
									id="carInput" readonly="readonly" onclick="inputFocus(this);getValue(this)"  value="${userCar.car.name }" autocomplete="off" />
									<div class="input_search_result">
										<ul id="car" style="overflow-y: auto; height: 250px;"></ul>
									</div>
									<input type="hidden" id="carId" name="carId" value="${userCar.car.id }" CHname="车型"/>
									<input type="hidden" class="repeat"/>
									<span class="errorStyle" id="carId_error"></span>
								</div><br/>
								
								<label>车身颜色：
									<input id="carColor" name="carColor" value="${userCar.carColor }" type="text" class="selectColor selectInput" onblur="checkNull(this)" CHname="车身颜色"/>
									<input type="hidden" class="repeat"/>
									<span class="errorStyle" id="carColor_error"></span>
								</label><br/>
								<label>车&nbsp;牌&nbsp;&nbsp;号：
									<input id="carNum" name="carNum" value="${userCar.carNum }" type="text" class="selectPlate selectInput" onblur="checkNull(this)" CHname="车牌号"/>
									<input type="hidden" class="repeat"/>
									<span class="errorStyle" id="carNum_error"></span>
								</label><br />
								<label>排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：<input name="engines" value="${userCar.engines }" type="text" class="selectDisplacement selectInput" /></label><br />
								<label>油&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;耗：<input name="fuel" value="${userCar.fuel }" type="text" class="selectFuel selectInput" /></label><br />
								<label>行驶里程：<input name="drange" value="${userCar.drange }" type="text" class="selectMileage selectInput" placeholder="km（公里）" /></label><br />
								<label>上路时间：<input id="roadDate" name="roadDate" value="${userCar.roadDate }" type="text" class="selectTime selectInput" readonly="readonly" /></label><br />
								<input type="button" class="button" onclick="carSubmit()"/>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div id="accountBalContent" class="accountBalContent myOrderDetail"></div>
			<div id="basicInfoContent" class="basicInfoContent myOrderDetail"></div>
			<div id="changePassContent" class="changePassContent myOrderDetail"></div>
			<div id="feedBackContent" class="feedBackContent myOrderDetail"></div>
		</div>
	</div>
	
	<script>
	$(function(){
	function tabs(tabTit,on,active,tabCon){ 
		$(tabCon).each(function(){ 
			$(this).children().eq(3).show();  
		});
		$(tabTit).each(function(){
			//$(this).children().eq(3).addClass(on);
		});
		$(tabTit).children().click(function(){ 
			$(this).addClass(on).siblings().removeClass(on);
			var index = $(tabTit).children().index(this);
			$(tabCon).children().eq(index).show().siblings().hide();
			$(this).siblings().addClass(on);			
			$(this).siblings().removeClass(on);
			$(this).parent().siblings().children().removeClass(on);
			
			$(this).parent().siblings().removeClass(active);
			$(this).parent().prev().addClass(active);			
		});
	}	
	tabs(".menu-list","on","active","#steward1-detail");
	})


	$(function(){
		function tabs(tabTit,liSelected,tabCon){ 
			$(tabCon).each(function(){ 
				$(this).children().eq(0).show();  
			});
			$(tabTit).each(function(){
				//$(this).children().eq(0).addClass(liSelected);
			});
			$(tabTit).children().click(function(){ 
				$(this).addClass(liSelected).siblings().removeClass(liSelected);
				var index = $(tabTit).children().index(this);
				$(tabCon).children().eq(index).show().siblings().hide();
				$(this).siblings().addClass(liSelected);			
				$(this).siblings().removeClass(liSelected);
				$(this).parent().siblings().children().removeClass(liSelected);				
			});
		}
		tabs(".totalTitle","liSelected",".orderListsContent");
	})
	
			
	$(document).ready(function(){
		$(".selectInput").focus(function(){
			$(this).addClass("selected");
		});
		$(".selectInput").blur(function(){
			$(this).removeClass("selected");
		});
	});
		
	$(function(){  
 
		//判断浏览器是否支持placeholder属性
		supportPlaceholder='placeholder'in document.createElement('input'),
 
		placeholder=function(input){
	 
			var text = input.attr('placeholder'),
			defaultValue = input.defaultValue;
		 
			if(!defaultValue){
		 
			  input.val(text).addClass("phcolor");
			}
	 
			input.focus(function(){
		 
			  if(input.val() == text){
		   
				$(this).val("");
			  }
			});
	 
	  
			input.blur(function(){
		 
			  if(input.val() == ""){
			   
				$(this).val(text).addClass("phcolor");
			  }
			});
	 
			//输入的字符不为灰色
			input.keydown(function(){
		  
			  $(this).removeClass("phcolor");
			});
		  };
 
		  //当浏览器不支持placeholder属性时，调用placeholder函数
		  if(!supportPlaceholder){
		 
			$('input').each(function(){
		 
			  text = $(this).attr("placeholder");
		 
			  if($(this).attr("type") == "text"){
		 
				placeholder($(this));
			  }
			});
		  }
 
	});	
	</script>
</div>
</div>
<!--中部结束-->
<!--底部开始-->
<div class="down">	
	<div class="span5">
		<div class="box0">
			<div class="span5l">
				<div class="down_pic"><img src="<%=basePath %>resource/page/images/down_pic.png" alt="" />
				</div>
				<div class="down_txt">
					<ul>
						<li><a href="#">一动车保</a>|</li>
						<li><a href="#">关于我们</a>|</li>
						<li><a href="#">服务简介</a>|</li>
						<li><a href="#">市场合作</a>|</li>
						<li><a href="#">联系我们</a></li>
					</ul>
					<p>&copy;2015&nbsp;www.YIDONGCHEBAO.com&nbsp; 贵ICP备18888888号</p>
				</div>
			</div>
			<div class="span5r">
				<ul>
					<li class="l1">咨询热线：400-855-9999</li>
					<li class="l2">咨询QQ：4008559999</li>
					<li class="l3">公司地址：贵州省贵阳市高新区688号</li>
				</ul>
				<div class="weixin">
					<img src="<%=basePath %>resource/page/images/code.png" alt="" />
					<p>扫一扫有惊喜！</p>
				</div>
			</div>
		</div>
	</div>
</div>
<!--底部结束-->
</body>
</html>