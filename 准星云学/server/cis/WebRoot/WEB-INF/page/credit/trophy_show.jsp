<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="utf-8">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
		<title>奖品查看</title>
        <link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/morris.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/select2.css" rel="stylesheet" />
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resource/chain/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/respond.min.js"></script>
        <![endif]-->
	</head>
<body style="min-width: 600px;">
<div class="content-m" style="padding: 15px">
    <div>奖品查看</div>
    <div style="border-bottom: 1px solid #d5d5d5; margin-bottom: 10px;">&nbsp</div>
    <div class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">奖品名称</label>
            <div class="col-sm-4">
                <input readonly type="text" class="form-control" value="${trophy.name}">
            </div>
            <label class="col-sm-2 control-label">兑换状态</label>
            <div class="col-sm-4">
                <input readonly type="text" class="form-control" name="trophy.state" value='${trophy.state==1?"可以兑换":"停止兑换"}'>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">价值</label>
            <div class="col-sm-4">
                <input readonly type="text" class="form-control" name="trophy.money" value="${trophy.money}">
            </div>
            <label class="col-sm-2 control-label">所需积分</label>
            <div class="col-sm-4">
                <input readonly type="text" class="form-control" name="trophy.score" value="${trophy.score}">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">库存</label>
            <div class="col-sm-4">
                <input readonly type="text" class="form-control" name="trophy.stock" value="${trophy.stock}">
            </div>
            <label class="col-sm-2 control-label">类别</label>
            <div class="col-sm-4">
                <input readonly type="text" class="form-control" name="trophy.stock" value="${trophy.trophyType.name}">
            </div>
        </div>
        <c:if test="${trophy.image!=null}">
	        <div class="form-group">
		   		 <img class="col-sm-12 img-responsive img-thumbnail"   src="${pageContext.request.contextPath}/${trophy.image}"/>
	        </div>
        </c:if>
        <div class="form-group" style="padding: 10px;">
  			  <p>${trophy.detail}</p>
        </div>
    </div>
</div>
</body>
</html>
