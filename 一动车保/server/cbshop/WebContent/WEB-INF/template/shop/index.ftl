<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "index"]
	<title>[#if seo.title??]
		[@seo.title?interpret /]
	[#else]
		${message("shop.index.title")}
	[/#if]</title>
	
	[#if seo.keywords??]
		<meta name="keywords" content="[@seo.keywords?interpret /]" />
	[/#if]
	[#if seo.description??]
		<meta name="description" content="[@seo.description?interpret /]" />
	[/#if]
[/@seo]
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<link href="${base}/resources/shop/slider/slider.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/index_new.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/style_new.css" rel="stylesheet" type="text/css">
<link href="${base}/resources/shop/css/common_new.css" rel="stylesheet" type="text/css">
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lazyload.js"></script>
<script type="text/javascript" src="${base}/resources/shop/slider/slider.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/banner_slider.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $slider = $("#slider");
	var $newArticleTab = $("#newArticle .tab");
	var $promotionProductTab = $("#promotionProduct .tab");
	var $promotionProductInfo = $("#promotionProduct .info");
	var $hotProductTab = $("#hotProduct .tab");
	var $newProductTab = $("#newProduct .tab");
	var $hotProductImage = $("#hotProduct img");
	var $newProductImage = $("#newProduct img");
	
	
	
	var $gaiProductTab = $("#gaiProduct .tab");
	var $gaiProductImage = $("#gaiProduct img");
	
	$slider.nivoSlider({
		effect: "random",
		animSpeed: 1000,
		pauseTime: 6000,
		controlNav: true,
		keyboardNav: false,
		captionOpacity: 0.4
	});
	
	$newArticleTab.tabs("#newArticle .tabContent", {
		tabs: "li",
		event: "mouseover",
		initialIndex: 1
	});
	
	$promotionProductTab.tabs("#promotionProduct .tabContent", {
		tabs: "li",
		event: "mouseover"
	});
	
	$hotProductTab.tabs("#hotProduct .tabContent", {
		tabs: "li",
		event: "mouseover"
	});
	
	$newProductTab.tabs("#newProduct .tabContent", {
		tabs: "li",
		event: "mouseover"
	});
	
	$gaiProductTab.tabs("#gaiProduct .tabContent", {
		tabs: "li",
		event: "mouseover"
	});
	
	
	function promotionInfo() {
		$promotionProductInfo.each(function() {
			var $this = $(this);
			var beginDate = $this.attr("beginTimeStamp") != null ? new Date(parseFloat($this.attr("beginTimeStamp"))) : null;
			var endDate = $this.attr("endTimeStamp") != null ? new Date(parseFloat($this.attr("endTimeStamp"))) : null;
			if (beginDate == null || beginDate <= new Date()) {
				if (endDate != null && endDate >= new Date()) {
					var time = (endDate - new Date()) / 1000;
					$this.html("${message("shop.index.remain")}:<em>" + Math.floor(time / (24 * 3600)) + "<\/em> ${message("shop.index.day")} <em>" + Math.floor((time % (24 * 3600)) / 3600) + "<\/em> ${message("shop.index.hour")} <em>" + Math.floor((time % 3600) / 60) + "<\/em> ${message("shop.index.minute")}");
				} else if (endDate != null && endDate < new Date()) {
					$this.html("${message("shop.index.ended")}");
				} else {
					$this.html("${message("shop.index.going")}");
				}
			}
		});
	}
	
	promotionInfo();
	setInterval(promotionInfo, 60 * 1000);
	
	$hotProductImage.lazyload({
		threshold: 100,
		effect: "fadeIn",
		skip_invisible: false
	});
	
	$newProductImage.lazyload({
		threshold: 100,
		effect: "fadeIn",
		skip_invisible: false
	});
	
	$gaiProductImage.lazyload({
		threshold: 100,
		effect: "fadeIn",
		skip_invisible: false
	});
});
</script>
</head>
<body>
	[#include "/shop/include/header.ftl" /]
	<div class="container indexContainer index">
		<div class="menu">
			<div class="menu_con">
				<div class="menu_list">
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
				<div class="menu_right">
					<div class="menu_right_top">
						<div class="banner_pic">
							[@ad_position id = 3 /] [#--这个是中间图片轮播部分--]
						</div>
						<div class="span6 last banner_ad" style="width: 242px;padding-left: 10px;">
							<div id="newArticle" class="newArticle">
								[@article_category_root_list count = 3 ]
									<ul class="tab">
										[#list articleCategories as articleCategory]
											<li>
												<a href="${base}${articleCategory.path}" target="_blank">${articleCategory.name}</a>
											</li>
										[/#list]
									</ul>
									[#list articleCategories as articleCategory]
										[@article_list articleCategoryId = articleCategory.id count = 5 isTop="true"]
											<ul class="tabContent">
												[#list articles as article]
													<li>
														<a href="${base}${article.path}" title="${article.title}" target="_blank">${abbreviate(article.title, 30)}</a>
													</li>
												[/#list]
											</ul>
										[/@article_list]
									[/#list]
								[/@article_category_root_list]
							</div>
							[@ad_position id = 4 /] 
						</div>
					</div>
					<div class="menu_right_buttom">
						<div class="small_banner">
							<div class="small_banner_leftpng small_banner_position" onclick="leftOnclick();"><img src="${base}/resources/shop/images/pu_left.png"></div>
							<div class="small_banner_div">
								<div class="small_banner_slider">
									[@brand_list type = "image" count = 14]
										[#list brands as brand]
											<div class="brand">
												<a href="${base}${brand.path}" title="${brand.name}"><img src="${brand.logo}" alt="${brand.name}" /></a>
											</div>	
										[/#list]
									[/@brand_list]
								</div>
			 				</div>
			 				<div class="small_banner_rightpng small_banner_position" onclick="rigthOnclick();"><img src="${base}/resources/shop/images/pu_right.png"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="tuan">
			<div class="tuan_text_pic">
				[@coupon_list count = 4 isEnabled="true" orderBy="beginDate desc"]
					[#list coupons as coupon]
						<div class="tuan_list1">
							[#--<div class="tuan_left">
								<div class="tuan_top">
									<h1><a href="${base}/coupon/index.jhtml?id=${coupon.id}">${coupon.name}</a></h1>
									<p>开始时间:</p>
									<p>${coupon.beginDate}</p>
									<p>结束时间:</p>
									<p>${coupon.endDate}</p>
									<p>所需积分:</p>
									<p>${coupon.point}</p>
								</div>
							</div>--]
							<div class="tuan_right">
								<a href="${base}/coupon/index.jhtml?id=${coupon.id}"><img src="${coupon.path}" alt="${coupon.name}" title="${coupon.name}"/></a>
							</div>
						</div>
					[/#list]
				[/@coupon_list]
			</div>
			<div class="tuan_act">
				[@article_list count = 1 tagIds = "6"]
					[#list articles as articles]
						<div class="act_text">
							<h1><a href="${base}/article/detail/${articles.id}.jhtml">${articles.title}</a></h1>
							<p>${articles.seoDescription}</p>
							<button>报名参加</button>
						</div>
						<div class="act_pic">
							[@tag_list count = 1 id = "6"]
								[#list tags as tag]
									<img src="${tag.icon}">
								[/#list]
							[/@tag_list]
						</div>
					[/#list]
				[/@article_list]
			</div>
		</div>
		<div class="span24">
			[@ad_position id=5 /]
		</div>
		<div class="span24">
			<div id="hotProduct" class="hotProduct clearfix">
				[@product_category_root_list count = 5]
					<div class="title">
						<strong>${message("shop.index.hotProduct")}</strong>
						<a href="${base}/product/list.jhtml?tagIds=1" target="_blank"></a>
					</div>
					<ul class="tab">
						[#list productCategories as productCategory]
							<li>
								<a href="${base}${productCategory.path}?tagIds=1" target="_blank">${productCategory.name}</a>
							</li>
						[/#list]
					</ul>
					<div class="hotProductAd">
						[@ad_position id = 6 /]
					</div>
					[#list productCategories as productCategory]
						<ul class="tabContent">
							[@product_list productCategoryId = productCategory.id tagIds = 1 count = 8]
								[#list products as product]
									<li>
										<a href="${base}${product.path}" title="${product.name}" target="_blank"><img src="${base}/upload/image/blank.gif" data-original="[#if product.image??]${product.image}[#else]${setting.defaultThumbnailProductImage}[/#if]" /></a>
									</li>
								[/#list]
							[/@product_list]
						</ul>
					[/#list]
				[/@product_category_root_list]
			</div>		
		</div>
		
		<div class="span24">
			<div id="newProduct" class="newProduct clearfix">
					[@product_category_root_list count = 5]
					<div class="title">
						<strong>${message("shop.index.newProduct")}</strong>
						<a href="${base}/product/list.jhtml?tagIds=2" target="_blank"></a>
					</div>
					<ul class="tab">
						[#list productCategories as productCategory]
							<li>
								<a href="${base}${productCategory.path}?tagIds=2" target="_blank">${productCategory.name}</a>
							</li>
						[/#list]
					</ul>
					<div class="newProductAd">
						[@ad_position id = 7 /]
					</div>
					[#list productCategories as productCategory]
						<ul class="tabContent">
							[@product_list productCategoryId = productCategory.id tagIds = 2 count = 8]
								[#list products as product]
									<li>
										<a href="${base}${product.path}" title="${product.name}" target="_blank"><img src="${base}/upload/image/blank.gif" data-original="[#if product.image??]${product.image}[#else]${setting.defaultThumbnailProductImage}[/#if]" /></a>
									</li>
								[/#list]
							[/@product_list]
						</ul>
					[/#list]
				[/@product_category_root_list]
			</div>
		</div>
		<div class="span24">
			<div id="gaiProduct" class="gaiProduct clearfix">
				[@product_category_root_list count = 5]
					<div class="title">
						<strong>${message("shop.index.gaiProduct")}</strong>
						<a href="${base}/product/list.jhtml?tagIds=5" target="_blank"></a>
					</div>
					<ul class="tab">
						[#list productCategories as productCategory]
							<li>
								<a href="${base}${productCategory.path}?tagIds=5" target="_blank">${productCategory.name}</a>
							</li>
						[/#list]
					</ul>
					<div class="newProductAd">
						[@ad_position id = 10 /]
					</div>
					[#list productCategories as productCategory]
						<ul class="tabContent">
							[@product_list productCategoryId = productCategory.id tagIds = 5 count = 8]
								[#list products as product]
									<li>
										<a href="${base}${product.path}" title="${product.name}" target="_blank"><img src="${base}/upload/image/blank.gif" data-original="[#if product.image??]${product.image}[#else]${setting.defaultThumbnailProductImage}[/#if]" /></a>
									</li>
								[/#list]
							[/@product_list]
						</ul>
					[/#list]
				[/@product_category_root_list]
			</div>
		</div>
		<div class="span24">
			<div class="friendLink">
				<h1>优惠新车</h1>
				[@product_list tagIds="7" orderBy="price desc" count=3]
					[#list products as product]
						<div class="youhui">
							<div class="youhui_left">
								<img src="[#if product.image??]${product.image}[#else]${setting.defaultThumbnailProductImage}[/#if]">
							</div>
							<div class="youhui_right">
								<p>${product.name}</p>
								<div class="you_prise">
									<span>${currency(product.price, true)}</span>
									[#--<span>￥308382.21</span><label>￥308422</label>--]
								</div>
								<div class="you_prise">
									<label>
										[#if setting.isShowMarketPrice]
											<del>${currency(product.marketPrice, true)}</del>
										[/#if]
									</label>
									[#--<span>￥308382.21</span><label>￥308422</label>--]
								</div>
							</div>
						</div>
					[/#list]
				[/@product_list]
			</div>
		</div>
	</div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>