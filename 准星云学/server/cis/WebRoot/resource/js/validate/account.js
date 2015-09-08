/*页面校验*/
$(function() {
	var validator = $("#myform").validate( {
		debug : true,
		ignore: ".ignore",
		errorClass : "errorStyle",
		submitHandler : function(form) {
			var pwdBeforeMd5= document.getElementById("password").value;
			document.getElementById("password").value = hex_md5(pwdBeforeMd5);
			if(	document.getElementById("oripwd")!=null){
				document.getElementById("oripwd").value = hex_md5(document.getElementById("oripwd").value);
			}
			form.submit();
		},
		rules : {
			"account.username" : {
				required : true
			},
			"repeat" : {
				required : true
			},
			"account.password" : {
				required : true
			},
			"account.phone" : {
				required : true
			},
			"account.nickname" : {
				required : true
			}
		},
		messages : {
			"account.username" : {
				required : "用户名不能为空"
			},
			"repeat" : {
				required : ""
			},
			"account.password" : {
				required : "密码不能为空"
			},
			"account.phone" : {
				required : "电话号不能为空"
			},
			"account.nickname" : {
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
