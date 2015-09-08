<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.statistics.view")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<style type="text/css">
*{
	font: 12px tahoma, Arial, Verdana, sans-serif;
}
html, body {
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
<script type="text/javascript">
$().ready(function() {

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.statistics.view")}
	</div>
	[#if setting.isCnzzEnabled && setting.cnzzSiteId?has_content && setting.cnzzPassword?has_content]
		<iframe frameborder="0" width="100%" height="100%" src="http://intf.cnzz.com/user/companion/shopxx_login.php?site_id=${setting.cnzzSiteId}&password=${setting.cnzzPassword}"></iframe>
	[/#if]
	[#if setting.isLa51Enabled && setting.la51SiteId?has_content && setting.la51Password?has_content]
		<iframe frameborder="0" width="100%" height="100%" src="http://www.51.la/report/0_help.asp?id=${setting.la51SiteId}&t=chalogin&lookpass=${setting.la51Password}"></iframe>
	[/#if]
</body>
</html>