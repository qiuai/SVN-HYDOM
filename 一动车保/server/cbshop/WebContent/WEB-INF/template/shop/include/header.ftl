<script type="text/javascript">
$().ready(function() {

	var $headerLogin = $("#headerLogin");
	var $headerRegister = $("#headerRegister");
	var $headerUsername = $("#headerUsername");
	var $headerLogout = $("#headerLogout");
	var $headerView = $("#headerView");
	var $productSearchForm = $("#productSearchForm");
	var $keyword = $("#productSearchForm input");
	var defaultKeyword = "${message("shop.header.keyword")}";
	
	var username = getCookie("username");
	if (username != null) {
		$headerUsername.text("${message("shop.header.welcome")}, " + username).show();
		$headerLogout.show();
		$headerView.show();
		$headerLogin.hide();
	} else {
		$headerLogin.show();
		$headerView.css("display","none");
	}
	
	$keyword.focus(function() {
		if ($keyword.val() == defaultKeyword) {
			$keyword.val("");
		}
	});
	
	$keyword.blur(function() {
		if ($keyword.val() == "") {
			$keyword.val(defaultKeyword);
		}
	});
	
	$productSearchForm.submit(function() {
		if ($.trim($keyword.val()) == "" || $keyword.val() == defaultKeyword) {
			return false;
		}
	});

});
</script>
<div class="container header">
	<div class="header_top">
		<div class="header_top_con">
			<div class="attention">
				<ul>
					<li><img src="${base}/resources/shop/images/header_start.png"></li>
					<li><a href="#">收藏淘车网</a></li>
				</ul>
			</div>
			<div class="header_other">
				<ul>
					<li id="headerLogin">您好，欢迎来到淘车网！<a href="${base}/login.jhtml">[登录]</a><a href="${base}/register.jhtml">[免费注册]</a></li>
					<li id="headerUsername" class="headerUsername display"></li>
					<li id="headerView" class="display"><a href="${base}/member/index.jhtml">个人中心</a></li>
					<li id="headerLogout" class="headerLogout display">
						<a href="${base}/logout.jhtml">[${message("shop.header.logout")}]</a>
					</li>
					<li class="app_tc pd">
						<i><!-- <img src="images/phone_icon.png"> --></i>
						<a href="#">手机淘车</a>
						<b></b>
					</li>
					<li class="fw pd">
					<a href="#">客户服务</a>
					<div class="hover_text">
						<p><a href="#">帮助中心</a></p>
						<p><a href="#">售后服务</a></p>
						<p><a href="#">在线克服</a></p>
						<p><a href="#">投诉中心</a></p>
						<p><a href="#">客服邮箱</a></p>
					</div>
					<b></b></li>
					<li class="fw pd no_b"><a href="#">网站导航</a><b></b></li>
				</ul>
			</div>
		</div>
			<div class="logo_search">
			<div class="logo">
				<img src="${base}/resources/shop/images/logo.jpg">
			</div>
			<div class="search">
				<div class="box">
					<form id="productSearchForm" action="${base}/product/search.jhtml" method="get">
						<div class="search_in">
							<input type="text" name="keyword"  value="${productKeyword!message("shop.header.keyword")}" maxlength="30"/>
						</div>
						<div class="search_btn">
							<button type="submit">搜 索</button>
						</div>
					</form>
				</div>
				<div class="hot_search">
					[#if setting.hotSearches?has_content]
						${message("shop.header.hotSearch")}:
						[#list setting.hotSearches as hotSearch]
							<a href="${base}/product/search.jhtml?keyword=${hotSearch?url}">${hotSearch}</a>
						[/#list]
					[/#if]
				</div>
			</div>
			<div class="zp">
					[#--<img src="${base}/resources/shop/images/zp.jpg">--]
			</div>
			<div class="shop_car" style="background: url(${base}/resources/shop/images/shop_car.png)no-repeat;">
				<a href="${base}/cart/list.jhtml">${message("shop.header.cart")}</a>
			</div>
		</div>
	</div>
	<div class="nav_index">
		<div class="nav nva_postion">
			<div class="menus">
				全部商品分类
			</div>
			<ul>
				[@navigation_list position = "middle" count= 6]
					[#list navigations as navigation]
						<li[#if navigation.url = url] class="current"[/#if]>
									<a href="${navigation.url}"[#if navigation.isBlankTarget]target="_blank"[/#if]>${navigation.name}</a>
						</li>
					[/#list]		
				[/@navigation_list]
			</ul>
			<div class="nav_icon">
				<img src="${base}/resources/shop/images/nav_icon.png">
			</div>
		</div>
		<div class="nva_postion_child" id="navs_list">
			[@product_category_root_list][#-- 首页菜单部分  --]
				[#list productCategories as category]
					<div class="list1 left_list">
						<h2><a href="${base}${category.path}">${category.name}</a></h2>
						<ul>
							[@product_category_children_list productCategoryId = category.id count = 6]
								[#list productCategories as productCategory]
									<li><a href="${base}${productCategory.path}">${productCategory.name}</a></li>
								[/#list]
							[/@product_category_children_list]	
						</ul>
					</div>
				[/#list]
			[/@product_category_root_list]
		</div>
	</div>
</div>



