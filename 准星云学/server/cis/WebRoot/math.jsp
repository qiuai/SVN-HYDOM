
<!DOCTYPE html>
<html lang="en-US" xmlns="http://www.w3.org/1999/xhtml" class="csstransforms csstransforms3d csstransitions"><!--<![endif]-->
<head>

<!-- META -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">

<!-- TITLE -->

<title>公式编辑器 -- Kity Formula</title>

<!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

<!-- WP HEAD -->

<link rel="stylesheet" href="http://gongshi.baidu.com/_src/settings.css" type="text/css" media="all">
<link rel="stylesheet" href="http://gongshi.baidu.com/_src/css" type="text/css" media="all">
<link rel="stylesheet" href="http://gongshi.baidu.com/_src/third-parties.css" type="text/css" media="all">
<link rel="stylesheet" href="http://gongshi.baidu.com/_src/style.css" type="text/css" media="all">
<link rel="stylesheet" href="http://gongshi.baidu.com/_src/base.css" type="text/css" media="all">
<link rel="stylesheet" href="http://gongshi.baidu.com/_src/notice.css" type="text/css" media="screen">
<link rel="stylesheet" href="http://gongshi.baidu.com/_src/editor.css" type="text/css" media="screen">
<link rel="stylesheet" href="http://gongshi.baidu.com/_src/gh-fork-ribbon.css">
<!--[if lt IE 9]>
    <link rel="stylesheet" href=".http://gongshi.baidu.com/_src/gh-fork-ribbon.ie.css">
<![endif]-->

<link rel="icon" href="./favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />

<script type="text/javascript" src=".http://gongshi.baidu.com/_src/K85ITMkv1y8v0M3MS84pTUkt1s8CosLS1KJKKKVTTFiNbm5melFiSapebmYeAA.js"></script>
</head>

<body id="body" class="page page-id-59 page-template-default w-parallax w-sticky no-touch fixed-width w-shrink wpb-js-composer js-comp-ver-4.2.2 vc_responsive sactive">
<script>
    function downloadReady ( editor ) {

        jQuery("#downloadBtn").click(function(){

            editor.execCommand( "get.image.data", function ( data ) {

                if(jQuery("#iframe-download").length === 0) {
                    jQuery('<iframe id="iframe-download" name="iframe-download" style="display:none;"></iframe>' +
                        '<form target="iframe-download" id="form-download" action="download-base64.php" method="post" style="display:none;">' +
                        '<input name="base64" id="base64" value="" />' +
                        '<input name="mimetype" value="image/png" />' +
                        '<input name="filename" value="latexEditor.png" /></form>').appendTo(document.body);
                }

                jQuery("#base64").val(data.img);
                jQuery('#form-download').submit();

            } );

        });

    }
</script>

<!-- fork me on github ribbon start -->
<div class="github-fork-ribbon-wrapper right">
    <div class="github-fork-ribbon">
        <a href="https://github.com/fex-team/kityformula">Fork me on GitHub</a>
    </div>
</div>
<!-- fork me on github ribbon end -->

<!-- Primary Header Start -->
<header id="secondary-header" style="height:106px">

	<div class="header-content">

		<div class="wrapper clearfix">

			<!-- Logo Start -->
			
			<a id="logo" href="./" style="width:234px;height:46px">
				<img class="default" src=".http://gongshi.baidu.com/_src/logo1.png" alt="kity-formula-logo">
				<img class="retina" src=".http://gongshi.baidu.com/_src/logo@2x.png" alt="kity-formula-logo">
			</a>
			<!-- Logo End -->

            <!-- Menu Start -->
            <nav id="main-menu" class="clearfix right w-search w-cart" style="height:106px" data-nav-text="--- Navigate ---">

				<ul id="menu-menu-1" class="clearfix top-menu">
					<li id="menu-item-51" class="parent menu-item">
							<a href="./">
								<span>首页</span>
							</a>
					</li>
					<!-- <li id="menu-item-51" class="parent menu-item">
						<a href="./getting-started.html">
							<span>Getting started</span>
						</a>
					</li> -->
					<li id="menu-item-51" class="parent menu-item">
						<a href="./list.html">
							<span>支持列表</span>
						</a>
					</li>
					<li id="menu-item-51" class="selected parent menu-item">
						<a href="./editor.html">
							<span>公式编辑器</span>
						</a>
					</li>
						<li id="menu-item-51" class="parent menu-item">
						<a href="./latex.html">
							<span>Latex解析器</span>
						</a>
					</li>
						<li id="menu-item-51" class="parent menu-item">
						<a href="wiki-eg.html">
							<span>应用示例</span>
						</a>
					</li>
				</ul>
			
			</nav>	
			<!-- Menu End -->

		</div>

	</div>

</header>
	<!-- Primary Header End -->

<!-- Page Title Start -->
<div id="page-title">
	<div class="wrapper clearfix">
		<h1 class="title">公式编辑器</h1><nav id="breadcrumbs" class="right"><span class="desc">您现在的位置:</span><a href="./">主页</a><span>公式编辑器</span></nav>			</div>
</div>
<!-- Page Title End -->

	
	<!-- Main Wrapper Start -->
<div id="content" class="clearfix">
	<div class="wrapper clearfix">
		<div class="clearfix krown-column-row" style="padding: 0 0 50px 0px;">
			<div class="row-content clearfix">
				<div class="span12 krown-column-container clearfix column_container">
						<div class="clearfix ">
							<div class="wpb_wrapper">
								<!-- 公式资源 -->

                                <iframe id="editor" src="ed.html" frameborder="0" width="800" height="510"></iframe>
                                <button id="downloadBtn">导出为图片</button><span id="saveHint">*点击保存为 png 图片</span>
							</div>
					</div>
				</div>
			</div>


			</div>
		</div>

	</div>
	<!-- Main Wrapper End -->

	<!-- Primary Footer Start -->
	
		<footer id="primary-footer" class="clearfix">

			<div class="wrapper clearfix">

				<div class="krown-column-row">

				
					<div class="krown-column-container span3">
						<div id="text-3" class="widget widget_text clearfix"><h4 class="widget-title">关于我们</h4>			<div class="textwidget">FEX 是百度「Web 前端研发部」的内部名称，其中 FE 是 Front End 的缩写，X 代表我们不仅关注前端技术，还更重视全端及全栈的能力。</div>
		</div>					</div>

			

					

			<div class="krown-column-container span3">
				<div id="text-4" class="widget widget_text clearfix"><h4 class="widget-title">联系我们</h4><div class="textwidget">
					<p><a target="_blank" href="mailto:f-cube@baidu.com">f-cube@baidu.com</a><br></p>
					</div>
				</div>					
			</div>

				
				</div>

			</div>

		</footer>

		<!-- Primary Footer End -->

	<!-- Secondary Footer Start -->
	
		<aside id="secondary-footer">
			<div class="wrapper clearfix">
            	<div class="left">© 2014 baidu , All rights reserved. </div>
            </div>
        </aside>

		<!-- Secondary Footer End -->

<!-- GTT Button -->
<a id="top" href="#"></a> 


<script type="text/javascript" src="http://gongshi.baidu.com/_src/ZYxBDoAgDAQ_JOCXKlQtlopWgvzeqCfjcTMzqzOEtbqaDYnnElBdVJcwECBjQjk-w4AEkxka7jaRdL3T9-CJopqpPphJj58Qt4J7syOIb8N62gx--QiZy0Sid3gB.js"></script>
<script type="text/javascript" src="http://gongshi.baidu.com/_src/TctBCsAgDADBD9Xql4IJNCXRYCzi74vSQ6_LbIp-AdYRb4-eG1v3U7kc6deVkIGElEoPUDCYwKS23aeGBS5ZHiRfR666cSOTudwL.js"></script>
<script>
    if ( !(document.body.addEventListener) ) {
        jQuery("#downloadBtn").css("display", "none");
        jQuery("#saveHint").css("display", "none");
    }
</script>
</body>
</html>