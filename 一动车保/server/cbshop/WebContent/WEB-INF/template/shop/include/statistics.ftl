[#if setting.isCnzzEnabled && setting.cnzzSiteId?has_content && setting.cnzzPassword?has_content]
	<span style="display: none;">
		<script type="text/javascript" src="http://pw.cnzz.com/c.php?id=${setting.cnzzSiteId}&l=2" charset="gb2312"></script>
	</span>
[/#if]
[#if setting.isLa51Enabled && setting.la51SiteId?has_content && setting.la51Password?has_content]
	<span style="display: none;">
<script language="javascript" type="text/javascript" src="http://js.users.51.la/${setting.cnzzSiteId}.js"></script>
<noscript>
	<a href="http://www.51.la/?${setting.cnzzSiteId}" target="_blank">
	<img alt="&#x6211;&#x8981;&#x5566;&#x514D;&#x8D39;&#x7EDF;&#x8BA1;" src="http://img.users.51.la/${setting.cnzzSiteId}.asp" style="border:none" />
	</a>
</noscript>
	</span>
[/#if]