/*页面校验*/
$(function() {
	var validator = $("#myform").validate( {
		debug : true,
		errorClass : "errorStyle",
		submitHandler : function(form) {
			form.submit();
		},
		rules : {
			"message.title" : {
				required : true
			},
			"message.content" : {
				required : true
			}
		},
		messages : {
			"message.title" : {
				required : "消息主题不能为空"
			},
			"message.content" : {
				required : "消息内容不能为空"
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
