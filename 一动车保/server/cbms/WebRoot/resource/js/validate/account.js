/*页面校验*/
$(function() {
	var validator = $("#myform").validate( {
		debug : true,
		ignore: ".ignore",
		errorClass : "errorStyle",
		submitHandler : function(form) {
			form.submit();
		},
		rules : {
			"username" : {
				required : true
			},
			"repeat" : {
				required : true
			},
			"password" : {
				required : true
			},
			"nickname" : {
				required : true
			}
		},
		messages : {
			"username" : {
				required : "用户名不能为空"
			},
			"repeat" : {
				required : ""
			},
			"password" : {
				required : "密码不能为空"
			},
			"nickname" : {
				required : "昵称不能为空"
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
