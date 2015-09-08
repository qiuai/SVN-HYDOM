var table;
var index = 0;
function setTable(tableName){
	table = document.getElementById(tableName);
}


function createTR(isAppend){
	var tr = create("tr");
	if(isAppend){
		table.appendChild(tr);
	}
	return tr;
}

function createTD(isAppend, obj){
	var td = create("td");
	if(isAppend){
		obj.appendChild(td);
	}
	return td;
}

function createTH(isAppend, obj){
	var th = create("th");
	if(isAppend){
		obj.appendChild(th);
	}
	return th;
}

function createINPUT(isAppend, obj, name){
	var input = create("input");
	setObjAttribute(input,"name",name + index);
	setObjAttribute(input,"id",name + index);
	if(isAppend){
		obj.appendChild(input);
	}
	return input;
}

function createTextarea(isAppend, obj, name){
	var input = create("textarea");
	setObjAttribute(input,"name",name + index);
	setObjAttribute(input,"id",name + index);
	setObjAttribute(input,"rows",8);
	setObjAttribute(input,"cols",40);
	if(isAppend){
		obj.appendChild(input);
	}
	return input;
}

function createRadio(isAppend, obj, name, value){
	var radio = create("input");
	setObjAttribute(radio,"type","radio");
	setObjAttribute(radio,"name",name + index);
	setObjAttribute(radio,"id",name + index);
	setObjAttribute(radio,"value",value);
	if(isAppend){
		obj.appendChild(radio);
	}
	return radio;
}

function createSELECT(isAppend, obj, name){
	var select = create("select");
	setObjAttribute(select,"name",name + index);
	setObjAttribute(select,"id",name + index);
	if(isAppend){
		obj.appendChild(select);
	}
	return select;
}

function addOption(obj, key, value){
	obj.options.add(new Option(key,value));
}

function addEvent(obj, eventName, value){
	obj.setAttribute(eventName, value);
}

function createLABLE(isAppend, obj, value){
	var lable = create("lable");
	lable.innerHTML = value;
	if(isAppend){
		obj.appendChild(lable);
	}
	return lable;
}

function create(type){
	var obj = document.createElement(type);
	return obj;
}

function setObjAttribute(obj, type, value){
	obj.setAttribute(type, value);
}