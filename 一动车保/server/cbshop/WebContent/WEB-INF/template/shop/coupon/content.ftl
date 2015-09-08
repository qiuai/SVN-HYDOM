<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "brandContent"]
	<title>[#if seo.title??][@seo.title?interpret /][#else]${brand.name}[/#if]</title>
	
	
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
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/header_view.js"></script>
</head>
<body>
	[#include "/shop/include/header.ftl" /]
	<div class="container brandContent">
		<div class="span6">
			<div class="hotProduct">
				<div class="title">${message("shop.coupon.hotCoupon")}</div>
				<ul>
					[@product_list count = 5 orderBy="monthSales desc"]
						[#list products as product]
							<li[#if !product_has_next] class="last"[/#if]>
								<div>
									<span class="shopSpan">
										<a href="${base}${product.path}" title="${product.name}"><img src="[#if product.image??]${product.image}[#else]${setting.defaultThumbnailProductImage}[/#if]"/></a>
									</span>
									<div><a href="${base}${product.path}" title="${product.name}">${abbreviate(product.name, 30)}</a></div>
									[#--<a href="${base}${product.path}" title="${product.name}">${abbreviate(product.name, 30)}</a>--]
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
					<li style="color:black;">
						${message("shop.path.home")} [#--<a href="${base}/"></a>--]
					</li>
					<li style="color:black;">
						${message("shop.coupon.title")}[#--	<a href="${base}/coupon/list/1.jhtml"></a>--]
					</li>
				</ul>
			</div>
			<div class="top">
				[#if coupon.path != ""]
					<img src="${coupon.path}" alt="${coupon.name}" />
				[/#if]
				<strong>${coupon.name}</strong>
				[#if brand.url??]
					${message("shop.brand.site")}: <a href="${brand.url}" target="_blank">${brand.url}</a>
				[/#if]
			</div>
			<div class="introduction">
				<div class="title">
					<strong>${message("Brand.introduction")}</strong>
					<span>&nbsp;</span>
				</div>
				${coupon.introduction}
			</div>
		</div>
	</div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>