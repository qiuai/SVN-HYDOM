<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="icon" href="<%=base%>favicon.ico" type="image/x-icon" />
<link href="<%=base %>template/common/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=base %>template/common/css/jquery.datepicker.css" rel="stylesheet" type="text/css" />
<link href="<%=base %>template/common/css/jquery.zoomimage.css" rel="stylesheet" type="text/css" />
<link href="<%=base%>template/admin/css/list.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" href="<%=base%>/template/admin/css/pager.css"
	type="text/css" />
	<link href="<%=base%>template/admin/css/welcome.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=base %>template/common/js/jquery-2.0.0.js"></script>
<script type="text/javascript" src="<%=base %>template/common/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=base %>template/common/js/jquery.pager.js"></script>
<!--[if lte IE 6]>
<script type="text/javascript" src="<%=base %>template/common/js/belatedPNG.js"></script>
<![endif]-->
<script type="text/javascript" src="<%=base %>template/common/js/base.js"></script>
<script type="text/javascript">
var baseUrl="<%=base%>";
</script>
<style type="text/css">
.success_info{
	display: none;
}
#inputForm label.error {
	margin-left: 5px;
	background:url(../template/admin/images/wrong.png) #fff2f2 no-repeat;
	background-position:5px 9px;
	border:1px solid #f15755;
	float:left;
	width:auto;
	height:32px;
	line-height:32px;
	color:#505050;
	text-align:left;
	text-indent:27px;
	display: none;
}
#inputForm .info {
	margin-left: 5px;
	background-color: #fffeee;
	border: 1px solid #ffca88;
	float:left;
	width:auto;
	height:32px;
	line-height:32px;
	color: gray;
	text-align:left;
	padding-left: 5px;
	padding-right: 5px;
}
</style>