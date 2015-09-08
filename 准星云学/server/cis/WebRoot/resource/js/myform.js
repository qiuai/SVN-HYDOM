//跳转分页
function topage(page) {
	var form = document.getElementById("pageList");
	form.page.value = page;
	form.submit();
}

// 跳转到指定页面
function go(totalPage) {
	var inputPageValue = document.getElementById("inputPage").value;
	if(isNaN(inputPageValue)){
		alert("请输入纯数字");
	}else if (inputPageValue > totalPage) {
		alert("超过最大页数: " + totalPage);
	} else if (inputPageValue < 1) {
		alert("页码数必须大于等于1");
	} else {
		var form = document.getElementById("pageList");
		form.page.value = inputPageValue;
		form.submit();
	}
}
// 设置页码为1
function confirmQuery() {
	var form = document.getElementById("pageList");
	form.page.value = 1;
	form.submit();
}
