<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${pageView.totalrecord > 0}">
<div style="height: 32px;position: absolute;right: 20px;display: inline-block;">
	<div style="float: left;height: 32px;">
		<ul class="pagination  pagination-md" style="margin: 0px;"> 
			<c:if test="${(pageView.currentPage-1)<1}" >
				<li  class="disabled" > <a href='#' >Prev</a> </li>
			</c:if>
			<c:if test="${(pageView.currentPage-1)>=1}" >
				<li> <a href='javascript:topage(${pageView.currentPage-1})' >Prev</a> </li>
			</c:if>
			<li> <a href='javascript:topage(1)' class="re" ${pageView.currentPage==1?'style="background-color: #efefef;"':''} >1</a> </li>
			<c:if test="${pageView.pageIndex.startindex>2}" >
				<li> <a href='#' >...</a> </li> 
			</c:if>
	 		<c:forEach begin="${pageView.pageIndex.startindex}" end="${pageView.pageIndex.endindex}" var="per">
				<li> <a href='javascript:topage(${per})' ${pageView.currentPage==per?'style="background-color: #efefef;"':''}>${per}</a> </li>  
			</c:forEach>
			
			<c:if test="${pageView.pageIndex.endindex<pageView.totalPage-1}" >
				<li> <a href='#' >...</a> </li>
			</c:if>
			
			<c:if test="${pageView.totalPage>=2}">
				<li> <a href='javascript:topage(${pageView.totalPage})' ${pageView.currentPage==pageView.totalPage?'style="background-color: #efefef;"':''} >${pageView.totalPage}</a> </li>
			</c:if>
			
			<c:if test="${(pageView.currentPage+1)>pageView.totalPage}" >
				<li class="disabled"> <a href='#' >Next</a> </li>
			</c:if>
			<c:if test="${(pageView.currentPage+1)<=pageView.totalPage}" >
				<li > <a href='javascript:topage(${pageView.currentPage+1})' >Next</a> </li>
			</c:if>
		</ul>
	</div>
	<span style="display: inline-block;float: left;padding-top: 3px;padding-left: 5px;">
			共计<b style="color: blue"> ${pageView.totalrecord }</b>条数据
			到 <input type="text" value="${pageView.currentPage}" id="inputPage"  maxlength="5" style="width:40px;height: 25px;vertical-align:baseline;" name="page"> 页
			<input type="button" onclick="javascript:go(${pageView.totalPage})" class="btn btn-primary btn-xs" value="GO" style="vertical-align:baseline;"> 
	</span>
</div>
</c:if>
<c:if test="${pageView.totalrecord <= 0 }">
	<div class="alert alert-info" style="text-align: center;color: #CC0001;">
	    对不起，没有相关记录!
	</div>
</c:if>

