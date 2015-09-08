/*页面校验*/
$(function() {
	var validator = $("#myform").validate( {
		debug : true,
		errorClass : "errorStyle",
		submitHandler : function(form) {
			form.submit();
		},
		rules : {
			"name" : {
				required : true
			}
		},
		messages : {
			"name" : {
				required : "角色名称不能为空"
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
