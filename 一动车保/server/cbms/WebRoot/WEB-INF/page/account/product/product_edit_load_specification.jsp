<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>

<%-- <div class="form-group">
	<label class="col-sm-4 control-label">规格</label>
	<div class="col-sm-8">
		<c:forEach var="value" items="${specifications}">
			<label><input type="checkbox" value="${value.id }" />${value.name }</label>
		</c:forEach>
	</div>
</div> --%>
<c:if test="${specifications.size()>0 }">
	<button type="button" onclick="addSpecificationValue();"
			style="margin-bottom: 10px;">添加规格值</button>
	
	<div class="panel-body nopadding">
		<div class="table-responsive">
			<table id="addSpecificationListTable" class="table table-info">
				<tr>
					<c:forEach var="value" items="${specifications}">
						<th class="${value.id }">${value.name }</th>
					</c:forEach>
					<th>操作</th>
				</tr>
				<tr class="specification_value specification_default" style="display: none;">
					<c:forEach var="value" items="${specifications}">
						<td class="${value.id }"><select class="${value.id }">
								<c:forEach var="subValue" items="${value.specificationValues }">
									<option value="${subValue.id }">${subValue.name }</option>
								</c:forEach>
						</select></td>
					</c:forEach>
					<td class="">
						<div class="file_div">
							<a href="javascript:void(0);" onclick="deletSpecification(this);">[删除]</a> 
						</div>
						<input name="specificationValueIds" type="hidden" value="" />
					</td>
				</tr>
				<tr>
					<c:forEach var="value" items="${product.specificationValues }">
						<td class="${value.specification.id }">${value.id }</td>
					</c:forEach>
				</tr>
			</table>
		</div>
	</div>
</c:if>
<script type="text/javascript">

	var productSpecification = '${productSpecification}';
	var num = '${productSpecificationNum}';
	
	var defaultTHML = "";
	$(document).ready(function(){
		defaultTHML = $("tr.specification_default").html();
		$("tr.specification_default").remove();
		
		var productSpecificationDates = eval("("+productSpecification+")");
		
		for(var i = 0; i < num*1 ; i++){
			addSpecificationValueOld();
		}
		
		$("tr.date_value_specification_old").find("input[name='specificationValueIds']").attr("name","oldSpecificationValueIds");
		
		var trs = $("tr.date_value_specification_old");
		for(var i = 0; i < trs.length; i++){
			var tr = $(trs[i]);
			var productSpecificationId = productSpecificationDates[i].productId;
			var productSpecificationDate = productSpecificationDates[i].specification;
			for(var n in productSpecificationDate){
				var date = productSpecificationDate[n];
				tr.find("select[class='"+date.id+"']").find("option[value='"+date.value+"']").prop("selected",true);
			}
			tr.attr("productSpecificationId",productSpecificationId);
		}
		
		var tr = $(trs[0]);
		tr.find("div.file_div").html("当前规格");
		
	});
	
	function addSpecificationValueOld(){
		var html = "<tr class='specification_value date_value_specification_old'>"+defaultTHML+"</tr>";
		$("#addSpecificationListTable").append(html);
		
		//$("tr.specification_default").find();
	}
	
	function addSpecificationValue(){
		var html = "<tr class='specification_value date_value_specification'>"+defaultTHML+"</tr>";
		$("#addSpecificationListTable").append(html);
		
		//$("tr.specification_default").find();
	}
	
	function deletSpecification(obj){
		if(!confirm("是否删除该商品？")){
			return;
		}
		var tr = $(obj).closest("tr.specification_value")
		var productId = tr.attr("productSpecificationId");
		var url = base + "manage/product/delete";
		var data = {
			ids :productId	
		};
		$.post(url,data,function(resulte){
			if(resulte.status == "success"){
				tr.remove();	
			}
		});
	}
	
	function setSpecificationInputValue(){
		var tr = $("tr.date_value_specification");
		for(var i = 0; i<tr.length; i++){
			var selects = $(tr[i]).find("select");
			var specificationValue = "";
			for(var v = 0; v<selects.length; v++){
				var select = $(selects[v]).val();
				if(specificationValue!=""){
					specificationValue +=",";
				}
				specificationValue += select;
			}
			$(tr[i]).find("input[name='specificationValueIds']").val(specificationValue);
		}
	}
	
	function setOldSpecificationInputValue(){
		var tr = $("tr.date_value_specification_old");
		for(var i = 0; i<tr.length; i++){
			var selects = $(tr[i]).find("select");
			var specificationValue = "";
			for(var v = 0; v<selects.length; v++){
				var select = $(selects[v]).val();
				if(specificationValue!=""){
					specificationValue +=",";
				}
				specificationValue += select;
			}
			$(tr[i]).find("input[name='oldSpecificationValueIds']").val(specificationValue);
		}
	}
	
</script>
