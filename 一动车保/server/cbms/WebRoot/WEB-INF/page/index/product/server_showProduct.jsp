<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">
	var type = "${type}";
	var serviceId = "${serviceId}";
	var productCategoryId = "${product.productCategory.id}";
	var productCategoryName = "${product.productCategory.name}";
	$(document).ready(function() {
		//绑定事件
		showHTML();
		searchProduct();
	});
	
	function searchProduct(){
		var brandId = $("#brandId").find("option:selected").val();
		/* var attributeIndex = $("#attributeSelect").attr("attributeIndex");
		var attributeValue = $("#attributeSelect").find("option:selected").val(); */
		var product = $("#nowProduct").attr("nowProductId");
		var data = {
				productId:product,
		};
		
		if(brandId != ""){
			data.brandId = brandId;
		}
		if($("#attributeSelect").length > 0){
			var attributeIndex = $("#attributeSelect").attr("attributeIndex");
			var attributeValue = $("#attributeSelect").find("option:selected").val();
			if(attributeValue != ""){
				data.attributeNum = attributeIndex;
				data.attribute = attributeValue;
			}
		}
		console.log(data);
		productListUL(data);
	}
	
	function productListUL(data){
		var url = "<%=basePath%>web/serviceProduct/changeProductList";
		var carId = $("#carHiddenId").val();
		data.carId = carId;
		data.page = $("#inputPage").val();
		data.type = type;
		data.serviceId = serviceId;
		/* var data = {
			productId:productId,
			carId:carId
		}; */
		$("#productListUL").load(url,data,function(){});
	}
	
	function gotoProductDetail(value,type){
		var url = "<%=basePath%>web/serviceProduct/productDetail";
		var data = {
				productId:value,
				type:type
		};
		$("#productDetail").load(url,data,function(){});
	}
	
	/* 分页三方法 */
	
	//跳转分页
	function topage(page) {
		//var form = document.getElementById("pageList");
		$("#inputPage").val(page);
		/*console.log(form);
		form.page.value = page;*/
		searchProduct();
	}

	// 跳转到指定页面
	function go(totalPage) {
		var inputPageValue = $("#inputPage").val();
		if (inputPageValue > totalPage) {
			alert("超过最大页数: " + totalPage);
		} else if (inputPageValue < 1) {
			alert("页码数必须大于等于1");
		} else {
			//var form = document.getElementById("pageList");
			//form.page.value = inputPageValue;
			searchProduct();
		}
	}
	// 设置页码为1
	function confirmQuery() {
		//var form = document.getElementById("pageList");
		//form.page.value = 1;
		$("#inputPage").val("1");
		searchProduct();
	}
	//显示列表页面  替换按钮
	
	function showPageBtn(id,price,name,type){
		var data = {
			name :name,
			id:id,
			price:price,
			count:1,
			productCategoryId:productCategoryId,
			productCategoryName:productCategoryName,
			serviceId:serviceId,
			type:type
		};
		
		if(type == "1"){//更换商品
			changeProductHTML(data);
		}else{//添加商品
			addServiceProduct(data);
		}		
		
	}
	
</script>

	<!--弹出层开始-->
			<div id="bg22"></div>
			<div id="bg11"></div>
			<div class="popup" id="popup">	
				<ul class="popup_top">
					<li class="popup_top_left"><a href="#"><img src="${pageContext.request.contextPath}/resource/page/images/gouimg5.png" alt="" /><span>选择商品</span></a></li>
					<li class="popup_top_right">
						<div class="divselect2" id="divselect2" style="margin: 16px;">
								<span>品牌</span>
								<select id="brandId" onchange="searchProduct();">
									<option value="">品牌</option>
									<c:forEach var="value" items="${productBrands }">
										<option value="${value.id }">${value.name }</option>
									</c:forEach>
								</select>
								<c:if test="${attributes.size() gt 0}">
									<c:forEach var="value" items="${attributes }" begin="0" end="1">
										<span>${value.name }</span>
										<select id="attributeSelect" attributeIndex="${value.propertyIndex }" onchange="searchProduct();">
											<option value="">筛选条件</option>
											<c:forEach var="sbvalue" items="${value.options }">
												<option value="${sbvalue }">${sbvalue}</option>
											</c:forEach>
										</select>
									</c:forEach>
								</c:if>
						</div>
					</li>
					<li class="t_3"><a href="javascript:;" class="closePop"><img src="${pageContext.request.contextPath}/resource/page/images/t_3.png" alt="" /></a></li>
				</ul>
				<c:if test="${!empty product}">
					<div class="popup_curr">
						<div class="popup_curr_left">
							<div class="curr_pic" nowProductId="${product.id }" id="nowProduct">
								<h2>当前产品</h2>
								<a href="#"><img src="<%=basePath%>${product.imgPath}" alt="" /></a>
							</div>
						</div>
						<div class="popup_curr_right">
							<p class="p1">
								<a href="#">${product.name }</a>
							</p>
							<p class="p2">
								<a href="#">￥${product.marketPrice }</a>
							</p>
						</div>
					</div>
				</c:if>
				<div id="productListUL" style="height: 610px;">
					
				</div>
				<!-- <ul class="popup_con_list" id="productListUL">
					<li>
						<dl>
							<dd class="dd_0"><a href="#"><img src="images/t_4_5.png" alt="" /></a></dd>
							<dd class="dd_1"><a href="#"><span>Shell壳牌机油蓝喜力蓝壳HX7 5W-40 SN 4L半合成
	汽车机油润滑油</span></a></dd>
							<dd class="dd_2"><a href="#"><span>￥995.00</span></a></dd>
							<dd class="dd_3"><a href="#"><img src="images/t_5_1.png" alt="" /></a></dd>
						</dl>
					</li>
				</ul> -->
			</div>
<script>
//关闭
$(function(){
	$(".closePop").click(function(){
		$("#bg22").hide();
		hideProductList();
	});
})
function hideProductList(){
	$("#popup").hide();
	$("#bg11").hide();
}
function showProductList(){
	$("#popup").show();
	$("#bg11").show();
}
</script>