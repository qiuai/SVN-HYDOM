/*页面校验*/
$(function() {
	var validator = $("#myform").validate( {
		debug : true,
		errorClass : "errorStyle",
		submitHandler : function(form) {
			form.submit();
		},
		rules : {
			"appVersion.version" : {
				required : true
			},
			"app" : {
				required : true
			}
		},
		messages : {
			"appVersion.version" : {
				required : "版本号不能为空"
			},
			"app" : {
				required : "请选择要上传的app"
			}
		},
		errorPlacement : function(error, element) {
			error.appendTo(element.next("span"));
		},
		highlight : function(element, errorClass) {
			$(element).addClass(errorClass);
		}
	});
});
