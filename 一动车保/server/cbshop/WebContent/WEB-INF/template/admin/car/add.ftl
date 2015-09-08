<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.car.add")}</title>


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
	
	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required"
		}
	});
	
});
</script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#carBrandId").change(function(){
			var h = "add.jhtml?carBrandId="+$(this).val()+"&name="+$("#name").val();
			location.href=h;
		});
	});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.car.add")}
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Car.name") }
				</th>
				<td>
					<input type="text" name="name" id="name" class="text" value="${name }" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Car.brand") }
				</th>
				<td>
					<select id="carBrandId" name="carBrandId">
						[#list brands as brand]
							<option value="${brand.id}" [#if brand.id ==carBrandId ] selected="selected" [/#if]>${message(brand.name)}</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("Car.carType") }
				</th>
				<td>
					<select id="carTypeId" name="carTypeId">
						[#list carTypes as carType]
							<option value="${carType.id}">${message(carType.name)}</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
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