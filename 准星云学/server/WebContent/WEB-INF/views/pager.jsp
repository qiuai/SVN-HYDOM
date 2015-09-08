<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath() %>/res/js/jquery.pager.js"></script>
<script type="text/javascript">
$().ready( function() {
	$("#pager").pager({
		pagenumber: ${pager.pageNumber},
		pagecount: ${pager.pageCount},
		buttonClickCallback: $.gotoPage
	});
});
</script>
<span id="pager"></span>
<input type="hidden" name="pageNumber" id="pageNumber" value="${pager.pageNumber}" />
<input type="hidden" name="orderBy" id="orderBy" value="${pager.orderBy}" />
<input type="hidden" name="orderType" id="order" value="${pager.orderType}" />