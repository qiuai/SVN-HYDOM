<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<c:forEach var="value" items="${parameters}">
	<div class="form-group parameters" >
		<input type="hidden" name="parameterIds" value="${value.id }"/>
		<label class="col-sm-4 control-label">${value.name }</label>
		<div class="col-sm-8">
			<input type="text" name="parameterValues" class="form-control" maxlength="200" id="${value.id }"/>
		</div>
	</div>
</c:forEach>
<input type="hidden" name="parameterValuesInput" value=""/>
<script type="text/javascript">
	var array = '${productPara}';
	console.log(array);
	$(document).ready(function(){
		setParameterValue();
	});
	function setParameterValue(){
		if(array != ""){
			var ar = eval("("+array+")");
			for(var i in ar){
				var data = ar[i];
				$("#"+data.id).val(data.value);
			}
		}
	}
	
	function setParameterDate(){
		var array = new Array();
		var divs = $("div.parameters");
		for(var i = 0;i<divs.length;i++){
			var div = $(divs[i]);
			var id = div.find("input[name='parameterIds']").val();
			var name = div.find("input[name='parameterValues']").val();
			var data = {
				id:id,
				name:name
			};
			array.push(data);
		}
		var dataStr = JSON.stringify(array);
		$("input[name='parameterValuesInput']").val(dataStr);
	}
	
	
</script>

