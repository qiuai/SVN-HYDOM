<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!-- 图片上传 -->
<script type="text/javascript"
	src="<%=basePath%>resource/swfupload/js/swfupload.js"></script>
<script type="text/javascript"
	src="<%=basePath%>resource/swfupload/js/swfupload.queue.js"></script>
<script type="text/javascript"
	src="<%=basePath%>resource/swfupload/js/fileprogress.js"></script>
<script type="text/javascript"
	src="<%=basePath%>resource/swfupload/js/handlers.js"></script>
<script type="text/javascript">
	<%--上传图片  --%>
	var swfu;
	var checkUpload = false;
	$().ready(function(){
		var url = "<%=basePath%>servlet/imgUpload?type=0";<%-- type 1是文件 0是图片  --%>
		swfu = new SWFUpload({
			//提交路径
			upload_url: url,// 上传处理Action
			//上传文件的名称
			file_post_name: "file",
			
			// 下面自己按照字面意思理解
			file_size_limit : "100 MB",// 上传文件大小限制
			file_types : "*.*",// 上传文件类型限制
			file_types_description : "*.jpg,*.png,*.jpeg",// 上传对话框中的文件类型描述
			file_upload_limit : "0",
			
			// 事件处理
			file_queue_error_handler : fileQueueError,
			file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
			file_queued_handler : function(file){
				console.log(this);
				var progress = new FileProgress(file, this.customSettings.progressTarget);
			},
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : function(file, serverData){
				//console.log(serverData);
				var json = eval("("+serverData+")");
				if(json.code == 0){
					var img = "<%=basePath%>"+json.url;
					$("input[name='imgPath']").val(json.url);
					$("#show_img").attr("src",img);
				};
			},
			upload_complete_handler : uploadComplete,
	
			// 按钮的处理
			button_image_url : "<%=basePath%>resource/swfupload/images/upload_image_button.gif",// 上传按钮图片
			button_placeholder_id : "spanButtonPlaceholder",
			button_width: 105,// 上传按钮图片宽度
			button_height: 25,// 上传按钮图片高度
			button_text : "选择文件",// 上传按钮文本
			button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',// 上传按钮文本样式
			button_text_top_padding: 4,// 上传按钮图片padding_top值
			button_text_left_padding: 23,// 上传按钮图片padding_left值
			button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
			button_cursor: SWFUpload.CURSOR.HAND,
			
			// Flash Settings
			flash_url : "<%=basePath%>resource/swfupload/flash/swfupload.swf",// Flash地址
			// Debug Settings
			debug : false
	 	});
	});
</script>