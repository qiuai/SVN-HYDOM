<%@ page language="java" pageEncoding="UTF-8"%>
<c:if test="${pageView.totalrecord>0}">
  		<span class="count">共计<b style="color: blue"> ${pageView.totalrecord } </b>条数据</span>
  		<span class="wrap">
		<c:if test="${pageView.currentPage>1}">
		<a href='javascript:topage(${pageView.currentPage-1})'>上一页</a>
		</c:if>
		<c:if test="${pageView.currentPage<=1}">
		<span class="prev">上一页</span >
		</c:if>
	
		<span <c:if test="${page==1}">class="curpage"</c:if>>
			<a href='javascript:topage(1)' >1</a>
		</span>
		<c:if test="${pageView.totalPage>1}" >
			<span <c:if test="${page==2}">class="curpage"</c:if>>
				<a href='javascript:topage(2)' >2</a>
			</span>
		</c:if>
		
		<c:if test="${pageView.pageIndex.startindex>3}" ><span><a>...</a></span></c:if>
		
		<c:if test="${pageView.totalPage>1}" >
		<c:forEach begin="${pageView.pageIndex.startindex}" end="${pageView.pageIndex.endindex}" var="per">
			<span  <c:if test="${page==per}">class="curpage"</c:if> >
				<a href='javascript:topage(${per})'>${per}</a>
			</span>
		</c:forEach>
		</c:if>
	
		<c:if test="${pageView.pageIndex.endindex<pageView.totalPage-2}" ><span><a>...</a></span></c:if>
		
		<c:if  test="${pageView.totalPage>=4}">
		<span <c:if test="${page==pageView.totalPage-1}">class="curpage"</c:if>>
			<a href='javascript:topage(${pageView.totalPage-1})' >${pageView.totalPage-1}</a>
		</span>
		</c:if>
		
		<c:if test="${pageView.totalPage>=3}">
			<span <c:if test="${page==pageView.totalPage}">class="curpage"</c:if> >
			<a href='javascript:topage(${pageView.totalPage})' >${pageView.totalPage}</a>
			</span>
		</c:if>
		
		<c:if test="${pageView.currentPage>=pageView.totalPage}">
		<span class="next">下一页</span >
		</c:if>
		<c:if test="${pageView.currentPage<pageView.totalPage}">
		<a href='javascript:topage(${pageView.currentPage+1})'>下一页</a>
		</c:if>
		</span style="border-left:10px;">
		<span class="gopage">
		到 <input type="text" value="${pageView.currentPage}" id="inputPage"  maxlength="5" style="width:40px;height: 25px;vertical-align:baseline;"> 页
		<input type="button" onclick="javascript:go(${pageView.totalPage})" class="btn" value="确定" style="vertical-align:baseline;"> 
		</span>
</c:if>