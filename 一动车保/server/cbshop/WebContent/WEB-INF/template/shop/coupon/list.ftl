<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "brandList"]
	<title>[#if seo.title??][@seo.title?interpret /][#else]${message("shop.brand.title")}[/#if]</title>
	
	
	[#if seo.keywords??]
		<meta name="keywords" content="[@seo.keywords?interpret /]" />
	[/#if]
	[#if seo.description??]
		<meta name="description" content="[@seo.description?interpret /]" />
	[/#if]
[/@seo]
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/product.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/brand.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lazyload.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/header_view.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $logo = $("#list img");

	$logo.lazyload({
		threshold: 100,
		effect: "fadeIn"
	});
	
});
</script>
</head>
<body>
	[#include "/shop/include/header.ftl" /]
	<div class="container brandList">
		<div class="span6">
			<div class="hotProduct">
				<div class="title">${message("shop.product.hotProduct")}</div>
				<ul>
					[@product_list count = 5 orderBy="monthSales desc"]
						[#list products as product]
							<li[#if !product_has_next] class="last"[/#if]>
								<div>
									<span class="shopSpan">
										<a href="${base}${product.path}" title="${product.name}"><img src="[#if product.image??]${product.image}[#else]${setting.defaultThumbnailProductImage}[/#if]"/></a>
									</span>
									<div><a href="${base}${product.path}" title="${product.name}">${abbreviate(product.name, 30)}</a></div>
									[#if product.scoreCount > 0]
									<div>
										<div>${message("Product.score")}: </div>
										<div class="score${(product.score * 2)?string("0")}"></div>
										<div>${product.score?string("0.0")}</div>
									</div>
									[/#if]
									<div>${message("Product.price")}: <strong>${currency(product.price, true, true)}</strong></div>
									<div>${message("Product.monthSales")}: <em>${product.monthSales}</em></div>
								</div>
							</li>
						[/#list]
					[/@product_list]
				</ul>
			</div>
		</div>
		<div class="span18 last">
			<div class="path">
				<ul>
					<li>
						<a href="${base}/">${message("shop.path.home")}</a>
					</li>
					<li class="last">${message("shop.coupon.title")}</li>
				</ul>
			</div>
			<div id="list" class="list clearfix">
				[#if page.content?has_content]
					<ul>
						[#list page.content?chunk(4) as row]
							[#list row as coupon]
								<li[#if !row_has_next] class="last"[/#if]>
									<a href="${base}/coupon/index.jhtml?id=${coupon.id}">
										<img src="${base}/upload/image/blank.gif"[#if coupon.path??] data-original="${coupon.path}"[/#if] />
										<span title="${coupon.name}">${coupon.name}</span>
									</a>
								</li>
							[/#list]
						[/#list]
					</ul>
				[#else]
					${message("shop.coupon.noResult")}
				[/#if]
			</div>
			[@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "{pageNumber}.jhtml"]
				[#include "/shop/include/pagination.ftl"]
			[/@pagination]
		</div>
	</div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>