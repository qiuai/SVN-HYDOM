<div class="container footer">
	<div class="span24">
		<div class="bottomNav">
		
			[@navigation_list position = "bottomTop" count= 6 type="bottomTop"]
				[#list navigations as navigation]
					<div class="intro">
						<div class="intro_img">
							<img src="${base}/resources/shop/images/footer_icon1.jpg">
						</div>
						<div class="intro_text">
							<ul>
								<li class="intro_list">${navigation.name}</li>
								[@article_list articleCategoryId = navigation.articleID count = 5 isTop="false"]
									[#list articles as article]
										<li>
											<a href="${base}${article.path}" title="${article.title}" target="_blank">${abbreviate(article.title, 30)}</a>
										</li>
									[/#list]
								[/@article_list]
							</ul>
						</div>
					</div>	
				[/#list]
			[/@navigation_list]
		</div>
	</div>
	<div class="span24">
		<div class="pro">
			<img src="${base}/resources/shop/images/pro.jpg">
		</div>
	</div>
	<span class="span24">
		<ul class="link">
			[@navigation_list position = "bottom"]
				[#list navigations as navigation]
					<li>
						<a href="${navigation.url}" title="${navigation.name}" target="_blank"  [#if !navigation_has_next] class="link_last"[/#if]>${abbreviate(navigation.name, 30)}</a>
					</li>
				[/#list]
			[/@navigation_list]
		</ul>
	</span>
	<span class="span24">
		<div class="foot">
			<img src="${base}/resources/shop/images/footer.jpg">
		</div>
	</span>
</div>