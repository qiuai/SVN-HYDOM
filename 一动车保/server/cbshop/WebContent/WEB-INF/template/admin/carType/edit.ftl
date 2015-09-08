<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.carType.edit")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	[@flash_message /]
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.carType.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${carType.id}" />
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("CarType.name")}:
				</th>
				<td>
					<input type="text" name="name" class="text" value="${carType.name}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("CarType.carBrand") }
				</th>
				<td>
					<select id="carBrandId" name="carBrandId">
						[#list carBrands as carBrand]
							<option value="${carBrand.id}" [#if carType.carBrand.id == carBrand.id] selected="selected"[/#if]>${message(carBrand.name)}</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("CarType.parent") }
				</th>
				<td>
					<select id="parentId" name="parentId">
						<option  value="0">顶级分类</option>
						[#list parents as parent]
							<option value="${parent.id}" [#if carType.parent.id == parent.id] selected="selected"[/#if]>${message(parent.name)}</option>
						[/#list]
					</select>
				</td>
			</tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>