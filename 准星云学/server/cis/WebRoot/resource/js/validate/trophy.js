/*页面校验*/
$(function() {
	var validator = $("#myform").validate( {
		debug : true,
		errorClass : "errorStyle",
		submitHandler : function(form) {
			form.submit();
		},
		rules : {
			"trophy.name" : {
				required : true
			},
			"trophy.money" : {
				required : true
			},
			"trophy.score" : {
				required : true
			},
			"trophy.stock" : {
				required : true
			}
		},
		messages : {
			"trophy.name" : {
				required : "奖品名称不能为空"
			},
			"trophy.money" : {
				required : "奖品价值不能为空"
			},
			"trophy.score" : {
				required : "积分不能为空"
			},
			"trophy.stock" : {
				required : "库存数不能为空"
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
