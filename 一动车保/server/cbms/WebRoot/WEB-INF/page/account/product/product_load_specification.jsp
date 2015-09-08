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
<c:if test="${specifications.size() > 0 }">
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
			<tr class="specification_value specification_default">
				<c:forEach var="value" items="${specifications}">
					<td class="${value.id }"><select>
							<c:forEach var="subValue" items="${value.specificationValues }">
								<option value="${subValue.id }">${subValue.name }</option>
							</c:forEach>
					</select></td>
				</c:forEach>
				<td class="">
					<div class="file_div">
						<a href="javascript:void(0);" onclick="deletSpecification(this);">[删除]</a> <input
							name="specificationValueIds" type="hidden" value="" />
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>
</c:if>
<script type="text/javascript">
	var defaultTHML = "";
	$(document).ready(function(){
		defaultTHML = $("tr.specification_default").html();
	});
	
	function addSpecificationValue(){
		var html = "<tr class='specification_value'>"+defaultTHML+"</tr>";
		$("#addSpecificationListTable").append(html);
		
		//$("tr.specification_default").find();
	}
	
	function deletSpecification(obj){
		$(obj).closest("tr.specification_value").remove();
	}
	
</script>
