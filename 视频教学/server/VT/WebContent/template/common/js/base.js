

if(!window.XMLHttpRequest) {

	// 解决IE6透明PNG图片BUG
	DD_belatedPNG.fix(".png");
	
	// 解决IE6不缓存背景图片问题
	document.execCommand("BackgroundImageCache", false, true);
}

function load(){
	$("#background,#progressBar").show();
}
function unload(){
	$("#background,#progressBar").hide();
}

function checkStr(name,obj){
	obj.value = trim(obj);
	if(obj.value == ""){
		$.dialog({type: "error", content: name + "必填", modal: false, autoCloseTime: 2000});
		obj.value="";
		obj.focus();
		return false;
	}
	if(obj.value.match(/[^\u4E00-\u9FA5^a-z^A-Z^0-9^ ]/g)){
		$.dialog({type: "error", content: name + "只允许包含中文、英文和数字", modal: false, autoCloseTime: 2000});
		obj.focus();
		return false;
	}
	return true;
}
function checkNum(name,obj){
	obj.value = trim(obj);
	if(obj.value == ""){
		$.dialog({type: "error", content: name + "必填", modal: false, autoCloseTime: 2000});
		obj.value="";
		obj.focus();
		return false;
	}
	if(obj.value.match(/[^0-9.-]/g)){
		$.dialog({type: "error", content: name + "必须为数字", modal: false, autoCloseTime: 2000});
		obj.focus();
		return false;
	}
	var dian=0;
	var zhi=0;
	for (var i=0; i < obj.value.length; i++){
		var ch=obj.value.charAt(i);
		if(ch=='.'){
			dian=dian+1;
		}else{
			zhi=zhi+1;
		}
	}
	if( dian >1) {
		$.dialog({type: "error", content: "请输入正确的" + name, modal: false, autoCloseTime: 2000});
		obj.focus();
		return false;
	}
	return true;
}
function trim(obj){
	return obj.value.replace(/(^\s*)|(\s*$)/g, "");
}
// 添加收藏夹
function addFavorite(url, title) {
	try{
		if (document.all) {
			window.external.addFavorite(url, title);
		} else if (window.sidebar) {
			window.sidebar.addPanel(title, url, "");
		}else{
			alert("加入收藏失败，请尝试按Ctrl+D进行添加");
		}
	}catch(e){
		alert("加入收藏失败，请尝试按Ctrl+D进行添加");
	}
}

// 浮点数加法运算
function floatAdd(arg1, arg2) {
	var r1, r2, m;
	try{
		r1 = arg1.toString().split(".")[1].length;
	} catch(e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch(e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	return (arg1 * m + arg2 * m) / m;
}

// 浮点数减法运算
function floatSub(arg1, arg2) {
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch(e) {
		r1 = 0
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch(e) {
		r2 = 0
	}
	m = Math.pow(10, Math.max(r1, r2));
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

// 浮点数乘法运算
function floatMul(arg1, arg2) {    
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length;
	} catch(e) {}
	try {
		m += s2.split(".")[1].length;
	} catch(e) {}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

// 浮点数除法运算
function floatDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;    
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch(e) {}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch(e) {}
	with(Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * pow(10, t2 - t1);
	}
}

// 设置数值精度
function setScale(value, scale, roundingMode) {
	if (roundingMode.toLowerCase() == "roundhalfup") {
		return (Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale)).toFixed(scale);
	} else if (roundingMode.toLowerCase() == "roundup") {
		return (Math.ceil(value * Math.pow(10, scale)) / Math.pow(10, scale)).toFixed(scale);
	} else {
		return (Math.floor(value * Math.pow(10, scale)) / Math.pow(10, scale)).toFixed(scale);
	}
}

// 格式化商品价格货币
function priceCurrencyFormat(price) {
	price = setScale(price, shopxx.priceScale, shopxx.priceRoundType);
	return shopxx.currencySign + price;
}

// 格式化商品价格货币（包含货币单位）
function priceUnitCurrencyFormat(price) {
	price = setScale(price, shopxx.priceScale, shopxx.priceRoundType);
	return shopxx.currencySign + price + shopxx.currencyUnit;
}

// 格式化订单金额货币
function orderCurrencyFormat(price) {
	price = setScale(price, shopxx.orderScale, shopxx.orderRoundType);
	return shopxx.currencySign + price;
}

// 格式化订单金额货币（包含货币单位）
function orderUnitCurrencyFormat(price) {
	price = setScale(price, shopxx.orderScale, shopxx.orderRoundType);
	return shopxx.currencySign + price + shopxx.currencyUnit;
}
