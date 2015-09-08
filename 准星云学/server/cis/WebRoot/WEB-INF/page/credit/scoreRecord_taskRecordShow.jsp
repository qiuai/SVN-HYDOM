<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">
        <span aria-hidden="true">&times;</span>
    </button>
    <h4 class="modal-title">完成任务详细</h4>
</div>

<div class="modal-body">
   	<div >
			原始笔迹:
			<svg viewBox="${si.minX} ${si.minY} 500 200" width="100%" height="100px"  version="1.1" xmlns="http://www.w3.org/2000/svg">
				${si.svgdata}
			</svg>
		</div>	
		
		<hr/>
		<div>计算出的正确结果：</div>
        	\[\begin{matrix}
			${taskRecord.task.result}
			\end{matrix} \]
		<hr/>
		<div>用户识别结果：</div>
			\[\begin{matrix}
			${taskRecord.result}
			\end{matrix} \]

		<hr/>
	 	<table class="table table-bordered table-striped" >
	 		<tr>
	 			<td>任务ID</td>
	 			<td>${taskRecord.id}</td>
	 		</tr>
	 		<tr>
	 			<td>用户ID</td>
	 			<td>${taskRecord.account.id}</td>
	 		</tr>
	 		<tr>
	 			<td>任务分配时间</td>
	 			<td><fmt:formatDate value="${taskRecord.matchTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	 		</tr>
	 		<tr>
	 			<td>任务完成时间</td>
	 			<td><fmt:formatDate value="${taskRecord.postTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	 		</tr>
	 	</table>
    
</div>

<div class="modal-footer">
    <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal">关闭</button>
</div>
