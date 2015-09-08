<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">
        <span aria-hidden="true">&times;</span>
    </button>
    <h4 class="modal-title">兑换奖品详细</h4>
</div>

<div class="modal-body">
    <p></p>
    <table  class="table table-bordered table-striped">
    	<tr>
			<td>用户ID</td>    	
			<td>${trophyRecord.account.id}</td>    	
    	</tr>
    	<tr>
			<td>奖品名称</td>    	
			<td>${trophyRecord.trophy.name }</td>    	
    	</tr>
    	<tr>
			<td>奖品价值</td>    	
			<td>${trophyRecord.trophy.money }</td>    	
    	</tr>
    	<tr>
			<td>兑换数量</td>    	
			<td>${trophyRecord.number}</td>    	
    	</tr>
    	<tr>
			<td>消费积分</td>    	
			<td>${trophyRecord.score}</td>    	
    	</tr>
    	<tr>
			<td>兑换时间</td>    
		    <td><fmt:formatDate value="${trophyRecord.postTime}" pattern="yyyy-MM-dd HH:mm:ss"/>  </td> 
    	</tr>
    	<tr>
			<td>发放时间</td>    	
		    <td><fmt:formatDate value="${trophyRecord.processTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td> 
    	</tr>
    </table>
</div>

<div class="modal-footer">
    <button type="button" class="btn btn-primary btn-sm"  data-dismiss="modal">关闭</button>
</div>
