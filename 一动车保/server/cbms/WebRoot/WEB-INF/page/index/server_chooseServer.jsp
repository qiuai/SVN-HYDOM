<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>一动车保</title>
<link href="<%=basePath%>resource/page/css/style.css" type="text/css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/chain/js/laydate/need/laydate.css">
<script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/common.other.js"></script>
<script src="${pageContext.request.contextPath}/resource/chain/js/laydate/laydate.js"></script>
<script type="text/javascript"
	src="<%=basePath%>resource/page/js/function.js"></script>
<script type="text/javascript"
	src="<%=basePath%>resource/page/js/jquery-spin.js"></script>
	
<style type="text/css">
img.brandImage{
	width: 75px;
	height: 30px;
}
</style>
<script type="text/javascript">

	var onclickProductId = "";
	
	$(document).ready(function(){
		//绑定事件
		bindElement();
	});
	
	function forcheck(ss){
		 var type="^[0-9]*[1-9][0-9]*$"; 
		 var re = new RegExp(type); 
		 if(ss.match(re)==null){ 
			 return false;
		}
		 return true;
	}
	
	function nextForm(){
		
		var array = new Array();
		var tables = $("table.tables");
		for(var i = 0; i<tables.length; i++){
			var table = $(tables[i]);
			var service = table.find("tr.serviceType");
			var serviceId = service.attr("serviceTypeId");
			
			//没有商品时显示纯服务价格
			var serverPrice = 0;
			var productTotals = $("span.productTotal");
			if(productTotals.length==0){
				if(i==0){//服务费只算一次
					serverPrice = $(".serviceType").attr("onlyserviceprice");
				}else{
					serverPrice = 0;
				}
			}else{
				serverPrice = service.attr("serviceTypePrice");
			}

			var serverName = service.find("span.serviceTypeImgSpan").text();
			var serviceData = {
				serverId : serviceId,//服务ID
				serverName : serverName,//服务名称
				serverPrice : serverPrice
			};
			
			var products = new Array();
			var product_trs = table.find("tr.product_tr");
			for(var n = 0; n < product_trs.length; n++){
				var product_tr = $(product_trs[n]);
				var productId = product_tr.attr("id");
				var productPrice = product_tr.find("span.productPrice").text();
				var productCount = product_tr.find("input.productCount").val();
				
				if(!forcheck(productCount)){
					alert("请填写大于0的整数");
					return;
				}
				
				var productTotal = product_tr.find("span.productTotal").text();
				var productName = product_tr.find("a").text();
				var productData = {
					id : productId,//ID
					name : productName,//名字
					count : productCount,//数量
					price : productPrice,//单价
					sum : productTotal
				};
				products.push(productData);
			}
			serviceData.products = products;
			array.push(serviceData);
		}
		
		
	/* 	
		product = "<tr class='product_tr' id='"+value.id+"'>"+
		"<td class='td1'><a href='javascritp:void(0);'>"+value.name+"</a></td>"+
		"<td class='td2'><i>￥<span class='productPrice'>"+value.price+"</span></i></td>"+
		"<td class='td3'><input type='text' id='spin1' value='"+value.count+"' class='productCount'/></td>"+
		"<td class='td4'><i>￥<span class='productTotal'>"+value.price+"</span></i></td>"+
		"<td class='td5'><span class='chooseServerRef' onclick='changeProduct(\""+value.id+"\",\"1\", \""+value.serviceId+"\");'></span><span class='chooseServerDel' onclick='delHTML(this,1);'></span></td>"+
	 "</tr>";	 */
		console.log(array);
		var content = JSON.stringify(array);
		$("#serviceContent").val(content);
		if($("li.selectMaintainDoor").length <= 0){
			alert("请选择服务");
			return;
		}
		
		//判断是否有车辆颜色
		
		var carColor = $("input[name='carColor']").val();
		if(carColor == ""){
			alert("车辆颜色不能为空");
			return;
		}
		
		
		//判断是否还有车牌号
		var carNum = $("input[name='carNum']").val();
		if(carNum == ""){
			alert("车牌号不能为空");
			return;
		}
		
		$("#formInput").submit();
	}
</script>
</head>

<body>
	<!--上部开始-->
	<jsp:include page="../web/header.jsp" />
	<!--上部结束-->
	<hr>
	<!--中部开始-->
	<div class="mid box0">
		<div class="shopping">
			<ul class="steps_2">
				<li class="b_li1"><a href="#"><span>01.</span>确定您的车型</a></li>
				<li class="b_li2 stepColor"><a href="#"><span>02.</span>订单信息</a></li>
				<li class="b_li3"><a href="#"><span>03.</span>核对订单信息</a></li>
				<li class="b_li3"><a href="#"><span>04.</span>下单成功</a></li>
			</ul>
			<div class="shopping_con">
				<%-- <input name="serviceType.id" type="hidden" value="${serviceType.id }" id="serviceType" /> --%>
				<form action="<%=basePath%>web/serverAddOrder" method="post" id="formInput">
					<input name="carId" type="hidden" value="${cleanCar.car.id }" id="carHiddenId"/>
					<input name="userCarId" type="hidden" value="${cleanCar.userCar.id }" />
					<input name="content" type="hidden" value="" id="serviceContent"/>
					<input name="memberCouponId" type="hidden" value="" id="memberCouponId"/>
					<input name="chooseServer" type="hidden" value="1" id="chooseServer"/>
					<c:choose>
						<c:when test="${empty cleanCar.userCar}"><%-- 没有默认车型  点击重选 跳转到选择车型页面 --%>
							<div class="shopping_con_top">
								<div class="text">
									<a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/${cleanCar.carBrand.imgPath}" id="carBrandImg" class="brandImage"/></a>
									<p id="carName">${cleanCar.carBrand.name}&nbsp;${cleanCar.carType.name}&nbsp;${cleanCar.car.name }</p>
								</div>
								<a href="javascript:showChooseCar();"><img src="${pageContext.request.contextPath}/resource/page/images/buy_2.png" alt="" /></a>
								<div class="carColor"><input name="carColor" type="text" value="${cleanCar.carColor }"/></div>
								<div class="carNum"><input name="carNum" type="text" value="${cleanCar.carNum }"/></div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="shopping_con_top"><%-- 没有默认车型  点击重选 选择其车管家中的车辆信息 --%>
								<div class="text" id="userCarText">
									<a href="#"><img src="${pageContext.request.contextPath}/${cleanCar.carBrand.imgPath}" class="brandImage"/></a>
									<p id="carName">${cleanCar.carBrand.name}&nbsp;${cleanCar.carType.name}&nbsp;${cleanCar.car.name }</p>
									<p class="pc">车身颜色：<span>${cleanCar.carColor }</span></p>
									<p class="pc">车牌号：<span>${cleanCar.carNum }</span></p>
									<a href="javascript:loadUserCarList();"><img src="${pageContext.request.contextPath}/resource/page/images/buy_2.png" alt="" /></a>
								</div>
								<input name="carColor" type="hidden" value="${cleanCar.carColor }"/>
								<input name="carNum" type="hidden" value="${cleanCar.carNum }"/>
							</div>
						</c:otherwise>
					</c:choose>
				</form>
				<script type="text/javascript">
					<%-- 文件夹cleanCar 中页面  --%>
					function installCarView(data){
						$("#carHiddenId").val(data.carId);
						$("#carBrandImg").attr("src","<%=basePath%>"+data.brandImg);
						var fullName = data.brandName +"&nbsp;" + data.carTypeName + "&nbsp;" + data.carName;
						$("#carName").html(fullName);
					}
					function showChooseCar(){
						$("#chooseCar").show();
					}
					
					function hideChooseCar(){
						$("#chooseCar").hide();
					}
					
					//加载列表
					function loadUserCarList(){
						var  url= "<%=basePath%>web/loadUserCarList";
						var data = {};
						$("#selectMyCarCon").load(url,data,function(){
							showChooseUserCar();
							selectMyCar();
						});
					}
					
					//用户存在 并且有默认车型的时候
					function showChooseUserCar(){
						$("#chooseUserCar").show();
					}
					
					function hideChooseUserCar(){
						$("#chooseUserCar").hide();
					}
					
					function initChooseUserCar(value){
						var html = "<a href='#'><img src='${pageContext.request.contextPath}/"+value.brandImg+"' class='brandImage'/></a>"+
						"<p id='carName'>"+value.brandName+"&nbsp;"+value.typeName+"&nbsp;"+value.carName+"</p>"+
						"<p class='pc'>车身颜色：<span>"+value.carColor+"</span></p>"+
						"<p class='pc'>车牌号：<span>"+value.carNum+"</span></p>"+
						"<a href='javascript:loadUserCarList();'><img src='${pageContext.request.contextPath}/resource/page/images/buy_2.png' alt='' /></a>";
						$("#userCarText").html(html);
						
						$("#carHiddenId").val(value.carId);
						$("input[name='carColor']").val(value.carColor);
						$("input[name='carNum']").val(value.carNum);
					}
				</script>
				<div style="padding-left: 30px;padding-top: 5px;">
					<h2>请选择上门保养项目</h2>
				</div>
				<div class="main_1">
					<ul class="maintainDoor">
						<%-- <c:forEach var="serviceType" items="${serviceTypes }">
		   					<li class="serviceLi selectMaintainDoor" serviceId="${serviceType.id }" typeInt="${serviceType.type }" money="<fmt:formatNumber value="${serviceType.price }" pattern="0.00" />" name="${serviceType.name }">
		   						<div class="service_img_div"><img src="<%=basePath %>${serviceType.imgPath}" alt="" title="${serviceType.name }"/></div>
		   						<b class="gouimg2"></b>
		   					</li>
		   					<label><input type="radio" value="${serviceType.id }" name="serviceTypeId"/>${serviceType.name }</label></li>
		   				</c:forEach> --%>
		   				
		   				<c:forEach var="serviceType" items="${serviceTypes }">
		   					<li class="serviceLi" serviceId="${serviceType.id }" typeInt="${serviceType.type }" money="<fmt:formatNumber value="${serviceType.price }" pattern="0.00" />" name="${serviceType.name }"><a href="#"><img src="<%=basePath %>${serviceType.imgPath}" alt="" title="${serviceType.name }"/></a><b></b></li>
		   				</c:forEach>
		   				
						<%--<li class="selectMaintainDoor"><a href="#"><img src="images/buy_4.png" /></a><b class="gouimg2"></b></li> --%>
						
					</ul>
				</div>
				<script type="text/javascript">
					//服务绑定事件
					function bindElement(){
						//保养服务选择
						
						$(document).on("click","li.serviceLi",function(){
							var li = $(this);
							var b = li.find("b");
							if(li.hasClass("selectMaintainDoor")){//取消
								li.removeClass("selectMaintainDoor");
								b.removeClass("gouimg2");
								
								var serviceId = $(this).attr("serviceId");
								$("."+serviceId).remove();
								totalSum();
							}else{//勾选
								li.addClass("selectMaintainDoor");
								b.addClass("gouimg2");
								var serviceId = $(this).attr("serviceId");
								var carId = $("#carHiddenId").val();
								//var typeInt = $(this).attr("typeInt");
								//var money = $(this).attr("money");
								//var name = $(this).attr("name");
								var data = {
										serviceId : serviceId
								};
								getServiceType(data);
							}
						});
						
						//添加商品
						$(document).on("click","span.chooseServerAdd",function(){
							var serviceId = $(this).closest("tr.serviceType").attr("serviceTypeId");
							changeProduct("","2",serviceId);
						});
						
						//商品数量
						$(document).on("blur","input.productCount",function(){
							var tr = $(this).closest("tr.product_tr");
							var inputCount = tr.find("input.productCount").val();
							
							if(!forcheck(inputCount)){
								alert("数量请填写大于0的整数");
								return;
							}
							
							var inputPrice = tr.find("span.productPrice").text();
							var sum = parseFloat(parseFloat(inputCount) * parseFloat(inputPrice)).toFixed(2);
							tr.find("span.productTotal").text(sum);
							totalSum();
						});
						
					}
					//获取服务
					function getServiceType(data){
						var url = "<%=basePath%>web/serviceProduct/findProduct";
						doPost(url,data);
					}
					
					
					//请求总方法
					function doPost(url,data){
						var carId = "${cleanCar.car.id}";
						data.carId = carId;
						console.log(data);
						$.post(url,data,function(result){
							if(result.status == "success"){
								addProductDetailTabel(result.message);
							}
						},"json");
					}
					
					//点击服务后 拼装列表table
					function addProductDetailTabel(value){
						
						var hasProduct = "";
						if(value.serviceType.hasProduct == "true"){//有添加按钮
							hasProduct = "<span class='chooseServerAdd' onClick=''></span>";
						}else{//无添加按钮
							hasProduct = "<span class='chooseServerNoAdd'></span>";
						}
						
						var serviceTR = "<tr class='titlebg serviceType' servicePrice='"+value.serviceType.servicePrice+"' onlyServicePrice='"+value.serviceType.onlyServicePrice+"' serviceTypeId='"+value.serviceType.serviceId+"' serviceTypePrice='"+value.serviceType.servicePrice+"'>"+
										"<td class='td1 tdtitle'><span class='serviceTypeImgSpan'>"+value.serviceType.serviceName+"</span></td>"+
										"<td class='td2'><i>￥"+value.serviceType.servicePrice+"</i></td>"+
										"<td class='td3'></td>"+
										"<td class='td4'></td>"+
										"<td class='td5'>"+hasProduct+"<span class='chooseServerDel' onclick='delHTML(this,2);'></span></td>"+
										"</tr>";
						var productTR = productHTML(value.product);
						
						var html = "<table class='tables "+value.serviceType.serviceId+"' serverId='"+value.serviceType.serviceId+"'><tbody>" + serviceTR + productTR + "</tbody></table>";
						$(".productDetailTableDIV").append(html);
						totalSum();
					}
					
					//添加商品标签
					function productHTML(value){
						var product = "";
						if(value == "error"){
							return "";
						}
						if(value.id == ""){
							product = "<tr>"+
									"<td class='td1'>"+value.name+"</td>"+
									"<td class='td2'></td>"+
									"<td class='td3'></td>"+
									"<td class='td4'></td>"+
									"<td class='td5'></td>"+
								 "</tr>";	
						}else{
							product = "<tr class='product_tr "+value.serviceId+"' id='"+value.id+"'>"+
								"<td class='td1'><a href='javascritp:void(0);'>"+value.name+"</a></td>"+
								"<td class='td2'><i>￥<span class='productPrice'>"+value.price+"</span></i></td>"+
								"<td class='td3'><input type='text' id='spin1' value='"+value.count+"' class='productCount'/></td>"+
								"<td class='td4'><i>￥<span class='productTotal'>"+value.price+"</span></i></td>"+
								"<td class='td5'><span class='chooseServerRef' onclick='changeProduct(\""+value.id+"\",\"1\", \""+value.serviceId+"\");'></span><span class='chooseServerDel' onclick='delHTML(this,1);'></span></td>"+
							 "</tr>";	
						}
							
						return product;
					}
					
					//更换商品
					var onclickProductId = "";
					function changeProduct(productId,type,serviceId){
					
						//type 1 表示更换 2表示添加
						onclickProductId = productId;
						var data = {
							productId:productId,
							type:type,
							serviceId:serviceId
						};
						getProductList(data);
					}
					
					function getProductList(data){
						console.log(data);
						var url = "<%=basePath%>web/serviceProduct/showProductList";
						if(data.type == "2"){
							url = "<%=basePath%>web/serviceProduct/showAddProductList";
						}
						var carId = $("#carHiddenId").val();
						data.carId = carId;
						/* var data = {
							productId:productId,
							carId:carId
						}; */
						$("#changeProduct").load(url,data,function(){});
					}
					
					//点击更换后的操作
					function changeProductHTML(data){
						console.log(data);
						/* var data = {
								name :name,
								id:id,
								price:price,
								productCategoryId:productCategoryId,
								productCategoryName:productCategoryName
							}; */
						var tr = $("tr#"+onclickProductId);
						
						var td = "<td class='td1'><a href='javascritp:void(0);'>"+data.name+"</a></td>"+
						"<td class='td2'><i>￥<span class='productPrice'>"+data.price+"</span></i></td>"+
						"<td class='td3'><input type='text' id='spin1' value='"+data.count+"' class='productCount'/></td>"+
						"<td class='td4'><i>￥<span class='productTotal'>"+data.price+"</span></i></td>"+
						"<td class='td5'><span class='chooseServerRef' onclick='changeProduct(\""+data.id+"\",\""+data.type+"\", \""+data.serviceId+"\");'></span><span class='chooseServerDel' onclick='delHTML(this,1);'></span></td>";
						tr.html(td);	
						tr.attr("id",data.id);
						closeAll();
						totalSum();
					}
					
					//添加商品
					function addServiceProduct(value){
						var table = $("table."+value.serviceId).find("tbody");
						
						var product = productHTML(value);
						
						table.append(product);
						//关闭覆层
						closeAll();
						//计算总额
						totalSum();
					}
					
					//删除 1type product 2type service
					function delHTML(e,type){
						if(type == 1){
							if(!confirm("确定移除该商品?")){
								return;
							}
							var product = $(e).closest("tr.product_tr");
							product.remove();
						}
						if(type == 2){
							if(!confirm("确定移除该服务?")){
								return;
							}
							var table = $(e).closest("table.tables");
							var serverId = table.attr("serverId");
							var  li = $("li.serviceLi[serviceId='"+serverId+"']");
							li.removeClass("selectMaintainDoor");
							li.find("b").removeClass("gouimg2");
							table.remove();
						}
						totalSum();
					}
				</script>
				<%-- <ul class="shopping_con_list box2" style="height: auto">
					
					<c:forEach var="serviceType" items="${serviceTypes }">
	   					<li class="serviceLi">
	   						<div class="service_img_div" serviceId="${serviceType.id }" typeInt="${serviceType.type }" money="<fmt:formatNumber value="${serviceType.price }" pattern="0.00" />" name="${serviceType.name }"><img src="<%=basePath %>${serviceType.imgPath}" alt="" title="${serviceType.name }"/></div>
	   					</li>
	   					<label><input type="radio" value="${serviceType.id }" name="serviceTypeId"/>${serviceType.name }</label></li>
	   				</c:forEach>
				
					<li class="buy_list5"><a href="#"><img
							src="${pageContext.request.contextPath}/resource/page/images/buy_7.png" alt="" /></a></li>
					
				</ul> --%>
				<div class="productDetail">
					<div class="orderInformation">
						<ul class="title">
							<li class="li1">商品</li>
							<li class="li2">价格</li>
							<li>数量</li>
							<li>小计</li>
							<li class="li5">操作</li>
						</ul>
						<div class="productDetailTableDIV">
							<!-- <table class="table1 tables">
								<tbody>
									<tr class="titlebg">
										<td class="td1 tdtitle"><span class="serviceTypeImgSpan">换轮胎</span> <a href="#"><img src="images/orderInformation1.png" />换轮胎</a></td>
										<td class="td2"><i>￥50.00</i></td>
										<td class="td3"></td>
										<td class="td4"></td>
										<td class="td5"><span class="chooseServerAdd"></span><span class="chooseServerDel"></span></td>
									</tr>
									<tr>
										<td class="td1"><a href="#">马牌轮胎 ContiPremiumContact2 CPC2 225/55R17 101W TL XL Continental</a></td>
										<td class="td2"><i>￥900.00</i></td>
										<td class="td3">
											<input type="text" id="spin1" value="1" style="border: 1px solid #ddd;"/>
										</td>
										<td class="td4"><i>￥900.00</i></td>
										<td class="td5"><span class="chooseServerRef"></span><span class="chooseServerDel"></span></td>
									</tr>
								</tbody>
							</table> -->
						</div>
					</div>
				</div>
				<script type="text/javascript">
					function productSum(){
						
						var sum = "0.00";
						var productTotals = $("span.productTotal");
						$("#productCount").text(productTotals.length);
						for(var i=0;i<productTotals.length;i++){
							var productTotal = $(productTotals[i]).text();
							sum = parseFloat(parseFloat(sum) + parseFloat(productTotal)).toFixed(2);
						}
						$("#productSum").text(sum);
						
						//纯服务时商品价格不显示
						if(productTotals.length==0){
							$("#productPriceDiv").hide();
							document.getElementById("serviceCheckbox").checked=true;
							document.getElementById("serviceCheckbox").disabled=true;
							$(".li2").css('opacity','0');
							$(".td2").css('opacity','0');
						}else{
							$("#productPriceDiv").show();
							document.getElementById("serviceCheckbox").disabled=false;
							$(".li2").css('opacity','1');
							$(".td2").css('opacity','1');
						}
						
						//服务不含商品时显示纯服务价格
// 						$(".serviceType").each(function(){
// 							if(!$(".product_tr").hasClass($(this).attr("servicetypeid"))){
// 								$(this).attr("servicetypeprice",$(this).attr("onlyserviceprice"));
// 								$(this).find(".td2 i").html("￥"+$(this).attr("onlyserviceprice"));
// 							}else{
// 								$(this).attr("servicetypeprice",$(this).attr("servicePrice"));
// 								$(this).find(".td2 i").html("￥"+$(this).attr("servicePrice"));
// 							}
// 						});
						
						return sum;
					}
					
					function serviceSum(){
						var sum = "0.00";
						if($("#serviceCheckbox").prop("checked")){
							var services = $("tr.serviceType");
							for(var i=0;i<services.length;i++){
								var servicePrice = $(services[i]).attr("serviceTypePrice");
								sum = parseFloat(parseFloat(sum) + parseFloat(servicePrice)).toFixed(2);
							}
							//选中 1 
							$("#chooseServer").val(1);
						}else{
							//没选中  0
							$("#chooseServer").val(0);
						}
						
						//所有服务都不含商品时显示纯服务价格
						var productTotals = $("span.productTotal");
						if(productTotals.length==0){
							sum = $(".serviceType").attr("onlyserviceprice");
						}
						
						$("#serviceSum").text(sum);
						return sum;
					}
					
					//优惠卷选择
				/* 	function couponSum(obj){
						var selectedOption = $(obj).find("option:selected");
						var type= selectedOption.attr("type");
						var price = selectedOption.attr("price");
						var couponPrice = "0.00";
						if(type == "1"){//满额打折
							var sumPrice = getSum();//获取中和
							couponPrice = parseFloat( (1 - parseFloat(price)) * parseFloat(sumPrice)).toFixed(2);//优惠金额
						}else if(type == "2"){//满额减免
							couponPrice = parseFloat(price).toFixed(2);//优惠金额
						}else if(type == "3"){//免额
							couponPrice = parseFloat(price).toFixed(2);//优惠金额
						}
						$("#couponSapnTotal").text(couponPrice);
						$("#memberCouponId").val(selectedOption.val());
						return couponPrice;
					} */
					
					function totalSum(){
						var couponTotal = "0";//$("#couponSapnTotal").text();
						var sum = parseFloat(parseFloat(productSum()) + parseFloat(serviceSum()) - parseFloat(couponTotal)).toFixed(2);
						$("#orderTotal").text(sum);
					}
										
				</script>
				<div class="ma_buy_total0" style="border-top: none; margin-top: -20px; margin-bottom: 40px; ">
					<div class="ma_buy_total_right">
						<div id="productPriceDiv">
							<div class="ma_buy_1 ma_buy_111">共<i id="productCount">0</i>件 商品金额：</div>
							<div class="ma_buy_2" >￥<span id="productSum">0.00</span></div>
						</div>
						<div>
							<div class="ma_buy_13"><label for="checkbox"><input type="checkbox" class="checkbox" id="serviceCheckbox" checked="checked" onclick="totalSum();" disabled/>一动车保服务：</label></div>
							<div class="ma_buy_2">￥<span id="serviceSum">0.00</span></div>
						</div>
						<%-- <div>
							<div class="ma_buy_1 ma_buy_12" id="coupons">
								<span>优惠券：</span>
								<span class="selecet">
									<select onchange="couponSum(this);">
										<option value="" price="0" type="3">无</option>
										<option value="" price="0.9" type="1">打折</option>
										<option value="" price="10" type="2">减免</option>
										<c:forEach var="value" items="${coupons }">
											<option value="${value.id }" price="${value.coupon.discount }" type="${value.coupon.type }">${value.coupon.name }</option>
										</c:forEach>			
									</select>
								</span>
							</div>
							<div class="ma_buy_2" style="width: 120px; right: -40px; ">-￥<span id="couponSapnTotal">0.00</span></div>
						</div> --%>
					</div>
				</div>
				<div class="buy_total2">
					<div class="buy_total2_right">
						<p>实付金额：<span>￥<span id="orderTotal">0.00</span></span></p>
						<button type="button" onclick="nextForm();"></button>
					</div>
				</div>
			</div>	
			</div>
		</div>
		<!--弹出层开始-->
			<div id="changeProduct" style="display: none;">
				
			</div>
			<div id="productDetail" style="display: none;">
				
			</div>
			<script>
				function showHTML(){
					$("#changeProduct").show();
					//$("#bg").css("display", "block");
					//$(".popup").css("display", "block");
				}
				
				function hideHTML(){
					$("#changeProduct").hide();
					//$("#bg").hide();
					//$(".popup").hide();
				}
				
				function showDetailHTML(){
					$("#productDetail").css("display", "block");
				}
				
				function hideDetailHTML(){
					$("#productDetail").css("display", "none");
				}
				
				function closeAll(){
					hideDetailHTML();
					hideHTML();
				};
				
			</script>
	<!--中部结束-->
	<!--底部开始-->
	<jsp:include page="../web/footer.jsp" />
	<!-- 选车两 弹出层-->
	<c:choose>
		<c:when test="${empty cleanCar.userCar}"><%-- 没有默认车型  点击重选 跳转到选择车型页面 --%>
			<div id="chooseCar" style="display: none;">
				<jsp:include page="cleanCar/cleanCar_addOrder_chooseCar.jsp" />
			</div>
		</c:when>
		<c:otherwise>
			<div id="chooseUserCar" style="display: none;">
				<jsp:include page="cleanCar/cleanCar_addOrder_chooseUserCar.jsp" />
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>