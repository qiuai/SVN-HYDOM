<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="icon" href="<%=base%>favicon.ico" type="image/x-icon" />
<link href="<%=base %>resources/swfupload/css/default.css" rel="stylesheet" type="text/css" />
<link href="<%=base %>resources/swfupload/css/swfupload.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=base %>resources/swfupload/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=base %>resources/swfupload/js/swfupload.js"></script>
<script type="text/javascript" src="<%=base %>resources/swfupload/js/swfupload.queue.js"></script>
<script type="text/javascript" src="<%=base %>resources/swfupload/js/fileprogress.js"></script>
<script type="text/javascript" src="<%=base %>resources/swfupload/js/handlers.js"></script>