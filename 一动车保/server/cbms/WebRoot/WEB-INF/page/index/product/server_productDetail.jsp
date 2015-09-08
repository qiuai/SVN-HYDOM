<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style type="text/css">
.detail_left p {
	float: left;
	width: 50%;
}

#ma5_select_pic_1 img {
	height: 180px;
	width: 100%;
}

#productColor_1 img {
	height: 40px;
	width: 40px;
}
</style>

<div class="description">
	<div class="description_head">
		<div class="description_head_left">
			<a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/resource/page/images/gouimg5.png" alt="" /><span>选择商品</span></a>
		</div>
		<div class="description_head_right">
			<a href="javascript:void(0);" class="closeDescription"><img
				src="${pageContext.request.contextPath}/resource/page/images/login_2.png" alt="" /></a>
		</div>
	</div>
	<div class="description_top">
		<div class="description_top_left">
			<div class="ma5_select_pic" id="ma5_select_pic_1">
				<c:forEach var="img" items="${product.productImages }">
					<img src="<%=basePath%>${img.source}" alt="" style="display: none;" />
				</c:forEach>
				<!-- <img src="images/ma5_12.png" alt="" style="display: none;" /> -->
			</div>
			<div>
				<ul class="ma5_select" id="ma5_select">
					<c:forEach var="img" items="${product.productImages }" varStatus="s">
						<li class="" onMouseOver="ma5Select(${s.index})"><img
							src="<%=basePath%>${img.source}" alt="" /></li>
					</c:forEach>
					<!-- <li class="" onMouseOver="ma5Select(0)"><img
						src="images/ma5_2.png" alt="" /></li> -->
				</ul>
			</div>
		</div>
		<script type="text/javascript">
			//ma5左侧tab
			function ma5Select(m) {
				
				var li = $("#ma5_select").find("li");   //document.getElementById("ma5_select").getElementsByTagName("li");
				var img = $("#ma5_select_pic_1").find("img"); //document.getElementById("ma5_select_pic").getElementsByTagName("img");
						
				for (var i = 0; i <= li.length; i++) {
					if(i == m){
						$(li[i]).addClass("img-hover");
						$(img[i]).css("display","block");
					}else{
						$(li[i]).removeClass("img-hover");
						$(img[i]).css("display","none");
					}
					
					//li[i].className = i == m ? "img-hover" : "";
					//img[i].style.display = i == m ? "block" : "none";
				}
			}
		</script>
		<div class="description_top_right">
			<h2>${product.name }</h2>
			<div class="detail">
				<div class="detail_left" style="width: 100%;">
					<c:forEach var="parameter" items="${parameters }">
						<p>
							${parameter.parameter.name }：<strong>${parameter.parameterValue }</strong>
						</p>
					</c:forEach>
				</div>
			</div>
			<div class="fen">
				<ul>
					<li>价格</li>
					<li class="price">￥${product.marketPrice }</li>
					<li><del>￥${product.cost }</del></li>
					<li class="grade"><em></em><em></em><em></em><em></em><em
						class="no"></em><b>4.5</b>分</li>
				</ul>
			</div>
			
			<c:forEach var="beans" items="${allProductMap }">
				<c:choose>
					<c:when test="${beans.key.type eq 1}">
						<%-- 1 文本 2 图片 --%>
						<div class="product_size">
							<span>${beans.key.name}:</span>
							<ul id="productSize">
								<c:forEach var="sbBean" items="${beans.value }">
									<li class="selected">${sbBean.name }</li>
								</c:forEach>
							</ul>
							<div class="clearfloat"></div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="product_color">
							<span>${beans.key.name}:</span>
							<ul id="productColor_1">
								<c:forEach var="sbBean" items="${beans.value }">
									<li class="selected"><img
										src="<%=basePath%>${sbBean.image}" alt="" /></li>
								</c:forEach>
							</ul>
							<div class="clearfloat"></div>
						</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<div class="quantity changeProductButton">
				<h2>购买数量：</h2>
				<div class="quantity1">
					<input type="text" id="spin5" value="1"
						style="border: 1px solid #ddd; width: 80px; height: 26px; " class="productCount2111"/>
				</div>
			</div>
			<button class="button5 changeProductButton" type="button" onclick="detailChangeProduct();">购买</button>
		</div>
		<script type="text/javascript">
		
		
		function forcheck22(ss){
			 var type="^[0-9]*[1-9][0-9]*$"; 
			 var re = new RegExp(type); 
			 if(ss.match(re)==null){ 
				 return false;
			}
			 return true;
		}
		
			var name = "${product.name}";
			var id = "${product.id}";
			var price = "${product.marketPrice}";
			var productCategoryId = "${product.productCategory.id}";
			var productCategoryName = "${product.productCategory.name}";
			var serviceId = "${product.productCategory.serviceType.id}";
			var type = "${type}";
			
			$(document).ready(function(){
				var table = $(".productDetailTableDIV").find("table");
				var product_trs = $(table).find("tr.product_tr");
				for(var i = 0; i < product_trs.length; i++){
					var productId = $(product_trs[i]).attr("id");
					console.log(productId);
					if(id == productId){
						$(".changeProductButton").hide();
					}
				}
			});
			
			function detailChangeProduct(){
				var count = $(".productCount2111").val();
				if(!forcheck22(count)){
					alert("请填写大于0的整数");
					return;
				}
				var data = {
						name :name,
						id:id,
						price:price,
						count:count,
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
	</div>
	<div class="description_bottom">
		<ul class="description_bottom_nav" id="description_bottom_nav">
			<li class="tab-hover li_tab_hover" onclick="tab2(0,this)">商品详情</li>
			<li class="li_tab_hover" onclick="tab2(1,this)">商品参数</li>
			<li class="li_tab_hover" onclick="tab2(2,this)">商品评论（<span  id="commentTotalSpan">0</span>）
			</li>
		</ul>
		<div id="des_con">
			<div class="description_bottom_con">
				<div class="description1" >
					<ul>
						<c:forEach var="parameter" items="${parameters }">
							<li>
								<p>${parameter.parameter.name }：<strong>${parameter.parameterValue }</strong></p>
							</li>
						</c:forEach>
						<li style="clear: both;"></li>
					</ul>
				</div>
				<div class="description2">
					${introduction }
				</div>
			</div>
			<div class="description_bottom_con" style="display: none; ">
				<div class="description1" >
					<ul>
						<c:forEach var="parameter" items="${parameters }">
							<li>
								<p>${parameter.parameter.name }：<strong>${parameter.parameterValue }</strong></p>
							</li>
						</c:forEach>
						<li style="clear: both;"></li>
					</ul>
				</div>
			</div>
			<script type="text/javascript">
			/* 分页三方法 */
				$(document).ready(function(){
					ma5Select(0);
					showDetailHTML();
					hideProductList();
					getProductComment();
				});
				//跳转分页
				function toCommentPage(page) {
					//var form = document.getElementById("pageList");
					$("#inputComentPage").val(page);
					/*console.log(form);
					form.page.value = page;*/
					getProductComment();
				}
	
				// 跳转到指定页面
				function goComment(totalPage) {
					var inputPageValue = $("#inputComentPage").val();
					if (inputPageValue > totalPage) {
						alert("超过最大页数: " + totalPage);
					} else if (inputPageValue < 1) {
						alert("页码数必须大于等于1");
					} else {
						//var form = document.getElementById("pageList");
						//form.page.value = inputPageValue;
						getProductComment();
					}
				}
				// 设置页码为1
				function confirmComentQuery() {
					//var form = document.getElementById("pageList");
					//form.page.value = 1;
					$("#inputComentPage").val("1");
					getProductComment();
				}
				
				function getProductComment(){
					var productId = "${product.id}";
					console.log(productId);
					var url = "<%=basePath%>web/serviceProduct/getProductComment";
					var radioValue = $("input[name='comment']:checked").val();
					var data = {
						productId:productId,
						hasImg:radioValue,
						page:$("#inputComentPage").val()
					}
					$("#commentDetail").load(url,data,function(){});
				}
				
			</script>
			<div class="description_bottom_con" style="display: none; ">
				<div class="form2">
					<label><input type="radio" name="comment" checked value="0" onclick="confirmComentQuery();"/>全部</label> <label><input
						type="radio" name="comment" value="1"  onclick="confirmComentQuery();"/>仅显示有照片的评论（<span id="commentHasImg">0</span>）</label>
				</div>
				<div class="comment1" id="commentDetail">
					
				</div>
			</div>
		</div>
		<script>
			function tab2(m,e) {
				var div = $("div.description_bottom_con");
				div.hide();
				$(div[m]).show();
				
				var lis = $("li.li_tab_hover");
				for(var i = 0;i<lis.length;i++){
					$(lis[i]).removeClass("tab-hover");
				}
				$(e).addClass("tab-hover");
				/* var li = document.getElementById("description_bottom_nav")
						.getElementsByTagName("li");
				var div = document.getElementById("des_con")
						.getElementsByTagName("div");
				for (var i = 0; i < li.length; i++) {
					li[i].className = i == m ? "tab-hover" : "";
					div[i].style.display = i == m ? "block" : "none";
				} */
			}

			$(function() {
				$(".closeDescription").click(function() {
					$(".description").hide();
					//$("#bg51").hide();
					showProductList();
				});
			});
		</script>
	</div>
</div>