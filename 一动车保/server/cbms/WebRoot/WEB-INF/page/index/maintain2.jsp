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

<link href="<%=basePath%>resource/page/css/change.css" type="text/css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resource/chain/js/laydate/need/laydate.css">
<script
	src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/js/common.other.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/chain/js/laydate/laydate.js"></script>
<script type="text/javascript"
	src="<%=basePath%>resource/page/js/function.js"></script>
<script type="text/javascript"
	src="<%=basePath%>resource/page/js/jquery-spin.js"></script>

<style type="text/css">
/******form*******/
.divselectcon {
	clear: both;
	padding: 10px 0 20px;
	position: relative;
}

.divselect {
	float: left;
}

.divselectcon input {
	width: 238px;
	height: 35px;
	margin: 0 20px;
	position: relative;
	z-index: 10000;
	border: 1px solid #b1b1b1;
	border-top: 2px solid #b1b1b1;
	line-height: 35px;
	display: inline-block;
	color: #807a62;
	font-style: normal;
	background: url(images/corners2.png) no-repeat right center;
}

.divselectcon input.input1 {
	border: 1px solid #ffae00;
	border-top: 2px solid #ffae00;
	margin-left: 0;
	width: 228px;
	height: 35px;
	line-height: 35px;
	display: inline-block;
	color: #807a62;
	font-style: normal;
	background: url(images/corners1.png) no-repeat right center;
}

#button2 button {
	float: right;
	width: 169px;
	height: 43px;
	background: url(images/go.png) 0 0 no-repeat;
	border: none;
	position: absolute;
	top: 9px;
	cursor: pointer;
}

.bdsug_copy {
	display: none;
}

.phcolor {
	color: #999;
}

#bg {
	width: 100%;
	height: 845px;
	top: 0px;
	left: 0px;
	position: absolute;
	filter: Alpha(opacity = 50);
	opacity: 0.5;
	background: #000000;
	display: none;
	z-index: 99998;
}

.serviceLi div.service_img_div {
	cursor: pointer;
}
</style>
<style type="text/css">
		.phcolor {
			color: #999;
		}
		
		#bg22 {
			width: 100%;
			height: 845px;
			top: 0px;
			left: 0px;
			position: absolute;
			filter: Alpha(opacity = 50);
			opacity: 0.5;
			background: #000000; <!--
			display: none; -->
			z-index: 99998;
		}
		
		#bg11 {
			position: fixed;
			z-index: 99998;
			width: 662px;
			height: 689px;
			left: 38%;
			top: 25%;
			margin: -169px 0 0 -180px;
			background-color: #000; /* display:none;  */
			opacity: 0.3;
			filter: alpha(opacity = 30);
		}
		.divselectcon {clear: both; padding: 10px 0 20px; position: relative; }
		.divselect {float: left; }
		.divselectcon input {width:238px; height: 35px; margin: 0 20px; position:relative; z-index:10000; border: 1px solid #b1b1b1; border-top: 2px solid #b1b1b1; line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(${pageContext.request.contextPath}/resource/page/images/corners2.png) no-repeat right center;}
		.divselectcon input.input1 {border:1px solid #ffae00; border-top: 2px solid #ffae00; margin-left: 0; width: 228px; height:35px;line-height:35px; display:inline-block; color:#807a62; font-style:normal; background:url(${pageContext.request.contextPath}/resource/page/images/corners1.png) no-repeat right center;}
		#button2 button {float: right; width: 169px; height: 43px; background: url(${pageContext.request.contextPath}/resource/page/images/go.png) 0 0 no-repeat; border: none; position: absolute; top: 9px;  cursor: pointer; }
		.bdsug_copy{display:none;}	
</style>
<script type="text/javascript">
	

function pupclose2() {
	//alert("none");
	$(".popup").hide();
	$("#bg11").hide();
	$("#bg22").hide();
}
function change(id){
	$("#allbrands").html("");
	
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/ServiceProduct/productbrand',
		data:'serviceTypeId='+id,
		dataType:'json',
		success:function(msg){
			console.log(msg);
			var value="<option value=''>全部品牌</option>";
			for (var i = 0; i < msg.length; i++) {
				value+="<option value='"+msg[i].id+"'>"+msg[i].name+"</option>";
			}
			$("#allbrands").append(value);
			
		}
	})
	$("#allattrs").html("");
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/ServiceProduct/attributeoptions',
		data:'serviceTypeId='+id,
		dataType:'json',
		success:function(msg){
			console.log(msg);
			var value="<option value=''>全部"+msg[0].attrname+"</option>";
			for (var i = 0; i < msg.length; i++) {
				value+="<option value='"+msg[i].name+"'>"+msg[i].name+"</option>";
			}
			$("#allattrs").append(value);
			$("input[name='attrId']").val(msg[0].attrId);
		}
	})
	$("#curPath").attr("src","${pageContext.request.contextPath}/"+$("input[name='imgpath']").val());
	$("#curName").html($("#buy_con_li2").html());
	$("#curPrice").html($(".buy_con_li3 span").html());
	
	queryproduct(1);
	$(".popup").show();
	$("#bg11").show();
	$("#bg22").show();
}
function queryproduct(page){
	var carId=$("input[name='carId']").val();
	var serviceTypeId=$("input[name='serviceTypeId']").val();
	var optionvalue=$("#allattrs").val();
	var brandId=$("#allbrands").val();
	var attrId=$("input[name='attrId']").val();
	var currentPage=page;
	var data='carId='+carId+'&serviceTypeId='+serviceTypeId+'&attrId='+attrId+'&currentPage='+currentPage;
	if(brandId!=""&&brandId!=null){
		data+='&brandId='+brandId;
	}
	if(optionvalue!=""&&optionvalue!=null){
		data+='&optionvalue='+optionvalue;
	}
	$(".popup_con_list").html("");
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/ServiceProduct/queryProducts',
		data:data,
		dataType:'json',
		success:function(msg){
			var value="";
			for (var i = 0; i < msg.length; i++) {
				value+="<li class='list_1'><dl><dd class='dd_0'><a href='#'><img src='${pageContext.request.contextPath}/"+msg[i].ImgPath+"' alt='' /></a>";
				value+="</dd><dd class='dd_1'><a href='#'><span>"+msg[i].name+"</span></a></dd>"
				value+="<dd class='dd_2'><a href='#'><span>"+msg[i].price+"</span></a></dd><dd class='dd_3'>"
				value+="<a href='#'><img id='"+msg[i].id+"' name='"+msg[i].name+"' price='"+msg[i].price+"' imgpath='"+msg[i].ImgPath+"' src='${pageContext.request.contextPath}/resource/page/images/t_5.png' alt='' /></a></dd></dl></li>"
			}
			
			$(".popup_con_list").append(value);
			
			var pagenum=msg[0].totalPage;
			var currentPage=msg[0].currentPage;
			$(".pagenum").remove();
			var vals="";
			for (var i = 0; i < pagenum; i++) {
				if(currentPage==i+1){
					vals+="<li class='pagenum page_li1'><a href='javascript:queryproduct("+(i+1)+")'>"+(i+1)+"</a></li>";
				}else{
					vals+="<li class='pagenum'><a href='javascript:queryproduct("+(i+1)+")'>"+(i+1)+"</a></li>";
				}
				
			}
			$(".pre a").attr("href","javascript:queryproduct("+((currentPage-1)<=0?1:currentPage-1)+")")
			$(".next a").attr("href","javascript:queryproduct("+(currentPage>=pagenum?currentPage:currentPage+1)+")")
			$(".pre").after(vals);
			console.log(msg);
			$(".list_1 img").bind("click",function(){
				$("#curPath").attr("src","${pageContext.request.contextPath}/"+$(this).attr("ImgPath"));
				$("#curName").html($(this).attr("name"));
				$("#curPrice").html($(this).attr("price"));
				$("#buy_con_li2").html($(this).attr("name"));
				$(".buy_con_li3 span").html($(this).attr("price"));
			})
		}
		
	})
}
	

	$(document).ready(function() {
		$(".popup").hide();
		$("#bg11").hide();
		$("#bg22").hide();
		//绑定事件
		bindElement();
		$("#buy_con_li2").click(function() {
			$("#bg").css("display", "block");
			$(".popup").css("display", "block");
		});
		$("#allbrands,#allattrs").bind("change",function(){
			queryproduct(1);
		})
		
	});
	function bindElement() {
		$(document).on("click", "div.service_img_div", function() {
			$(".service_img_div").find("img").removeClass("selected");
			$(this).find("img").addClass("selected");

			var serviceId = $(this).attr("serviceId");
			var typeInt = $(this).attr("typeInt");
			var money = $(this).attr("money");
			var name = $(this).attr("name");
			var carId = $("input[name='carId']").val();
			
			var data = {
				serviceId : serviceId,
				typeInt : typeInt,
				money : money,
				name : name,
				carId:carId,
			};
			console.log(data.money);
			getServiceType(data);

		});
	}

	function getServiceType(data) {
		$(".buy_div").html("");
		$("input[name='serviceTypeId']").val(data.serviceId);
		if (data.typeInt == 1) {//商品服务
			$(".buy_title").show();
			$(".buy_total2_left").show();
			findproduct(data.serviceId,data.carId,data.money);
			$("input[name='serviceTypeId']").val(data.serviceId);
			
			
		} else if (data.typeInt == 2) {//保养服务
			$(".buy_title").hide();
			$(".buy_total2_left").hide();
			addBuyValue(data);
		}
		getCount();
	}
	function findproduct(serviceId,carId,money){
		$(".buy_con").remove();
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/ServiceProduct/findproduct',
			data:'serviceTypeId='+serviceId+"&carId="+carId,
			dataType:'json',
			success:function(msg){
				var value ="<ul class='buy_con'><li class='buy_con_li1'>"+msg.name+"</li>";
				value+="<li class='buy_con_li2'><a href='javascript:change(\""+serviceId+"\");' id='buy_con_li2'>"+msg.fullName+"</a></li>"
				value+="<li class='buy_con_li3'><span>"+msg.price+"</span></li>";
				value+="<li class='buy_con_li4'><input type='input' id='spin1' value='1' style='border: 1px solid #ddd;'/></li>";
				$(".buy_title").after(value);
				$("input[name='imgpath']").val(msg.imgPath);
				$(".countMoney").text("￥" +(Number(msg.price)+Number(money)));
				$(".productCount").text($("#spin1").val());
				$("#spin1").bind("change",function(){
					$(".productCount").text($(this).val());
					$(".countMoney").text("￥" +(Number($(".buy_con_li3 span").html())*$(this).val()+Number(money )));
				})
			}
				
			
		})
	}
	function addBuyValue(data) {
		$(".buy_con").remove();
		var value = "<ul class='buy_con'><li class='buy_con_li1'>" + data.name
				+ "</li>";
		value += "<li class='buy_con_li2'>服务费</li>";
		value += "<li class='buy_con_li3'>" + data.money + "</li></ul>";
		$(".buy_div").html(value);
	}

	function getCount() {
		var liCount = $(".buy_div").find("ul");
		$(".productCount").text(liCount.length);
	}

	function nextForm() {
		var serviceTypeId = $("input[name='serviceTypeId']").val();
		if (serviceTypeId == "") {
			alert("请选择服务");
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
		<input name="imgpath" type="hidden" />
		<div class="shopping">
			<ul class="steps">
				<li class="b_li1"><a href="#"><span>01.</span>确定您的车型</a></li>
				<li class="b_li2"><a href="#"><span>02.</span>选择保养项目和产品</a></li>
				<li class="b_li3"><a href="#"><span>03.</span>确定服务类型</a></li>
			</ul>
			<div class="shopping_con">

				<div class="shopping_con_top">
					<img src="<%=basePath %>${car.carBrand.imgPath }" alt="" /> <span>
						${car.name }</span> <b class="buy_b2"></b>
				</div>
				<div style="padding-left: 30px;padding-top: 5px;">
					<h2>请选择上门保养项目</h2>
				</div>

				<ul class="shopping_con_list box2" style="height: auto">

					<c:forEach var="serviceType" items="${serviceTypes }">
						<li class="serviceLi">
							<div class="service_img_div" serviceId="${serviceType.id }"
								typeInt="${serviceType.type }"
								money="<fmt:formatNumber value="${serviceType.price }" pattern="0.00" />"
								name="${serviceType.name }">
								<img src="<%=basePath %>${serviceType.imgPath}" alt=""
									title="${serviceType.name }" />
							</div>
						</li>
						<%-- 	<label><input type="radio" value="${serviceType.id }" name="serviceTypeId"/>${serviceType.name }</label></li> --%>
					</c:forEach>

					<%-- <li class="buy_list5"><a href="#"><img
							src="${pageContext.request.contextPath}/resource/page/images/buy_7.png" alt="" /></a></li> --%>

				</ul>
				<div class="shopping_con_bottom box2" id="serviceContent">
					<ul class="buy_title">
						<li><span>商品</span></li>
						<li class="buy_title_li1"><span>详情</span></li>
						<li><span>价格</span></li>
						<li><span>数量</span></li>
					</ul>
					<div class="buy_div">
					</div>
					<div class="buy_total1">
						<p>
							共<span class="productCount">0</span>件&nbsp;&nbsp;商品总价：<span
								class="countMoney">0.00</span>
						</p>
					</div>
				</div>
				<div class="buy_total2">
					<div class="buy_total2_left">
						<a href="javascript:change()"><img
							src="${pageContext.request.contextPath}/resource/page/images/buy_more.png"
							alt="" /><span>更换商品</span></a>
					</div>
					<div class="buy_total2_right">
						<p>
							合计金额：<span class="countMoney">0.00</span>
						</p>
						<form action="<%=basePath%>web/addOrder" method="post"
							id="formInput">
							<input name="carId" value="${car.id }" type="hidden" /> <input
								name="serviceTypeId" value="" type="hidden" />
								<input name="attrId" value="" type="hidden" /> 
							<button type="button" onclick="nextForm();"></button>
						</form>
					</div>
				</div>
			</div>

			<!--弹出层开始-->


			<div id="bg22"></div>
			<div id="bg11"></div>
			<div class="popup">
				<ul class="popup_top">
					<li class="popup_top_left"><a href="#"><img
							src="${pageContext.request.contextPath}/resource/page/images/buy_more.png" alt="" /><span>更换商品</span></a></li>
					<li class="popup_top_right">
						<form class="divselect2" id="divselect2">
							<select id="allbrands" style="width: 173px;height: 32px;">
								
							</select>
							<select id="allattrs" style="width: 173px;height: 32px;">
								
							</select>
						</form>
					</li>
					<li class="t_3"><a href="javascript:;" onClick="pupclose2()"><img
							src="${pageContext.request.contextPath}/resource/page/images/t_3.png" alt="" /></a></li>
				</ul>
				<div class="popup_curr">
					<div class="popup_curr_left">
						<div class="curr_pic">
							<h2>当前产品</h2>
							<a href="#"><img id="curPath" src="" alt="" /></a>
						</div>
					</div>
					<div class="popup_curr_right">
						<p class="p1">
							<a href="#" id="curName"></a>
						</p>
						<p class="p2">
							<a href="#" id="curPrice"></a>
						</p>
					</div>
				</div>
				<ul class="popup_con_list ">
					
				</ul>
				<ul class="page">
					<li class="pre"><a href="#"><span>&lt;&lt;上一页</span></a></li>
					
					<li class="next"><a href="#"><span>下一页&gt;&gt;</span></a></li>
				</ul>
			</div>
			<script>
				//弹出函数
				
			</script>
		</div>
	</div>
	<!--中部结束-->
	<!--底部开始-->
	<jsp:include page="../web/footer.jsp" />
	
</body>
</html>