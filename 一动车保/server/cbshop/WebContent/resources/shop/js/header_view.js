$().ready(function(){
	$(".menus").hover(function(){
		$(".nva_postion_child").show().hover(function () { clearTimeout(window.timer); },function () { $(this).hide(); });
	},function(){
		 window.timer = setTimeout(function () { $(".nva_postion_child").hide(); }, 200); //延时隐藏，以便鼠标移动到div上时clear掉计时器
	});
});
