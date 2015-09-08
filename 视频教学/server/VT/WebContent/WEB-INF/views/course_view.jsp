<%@ page language="java" import="java.util.*;" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String base = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>课程详情</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />

<link href="<%=base%>template/admin/css/welcome.css" rel="stylesheet"
	type="text/css" />
<link href="<%=base%>template/admin/css/user_view.css" rel="stylesheet" />
<script type="text/javascript"
	src="<%=base%>template/common/js/jquery-2.0.0.js"></script>
<script type="text/javascript">
	

	
</script>

<style type="text/css">
#filelist {
	margin-top: 3px;
	margin-left: 135px;
}

.divOne {
	height: auto;
	width: 99%;
	/* margin-bottom: 15px; */
}

.divTwo {
	height: 5px;
	width: 99%;
	border: 1px solid #f0f0f0;
}

.divThree {
	height: 5px;
	background-color: green;
}

#container span {
	/* display: none; */
	cursor: pointer;
	margin-left: 5px;
}

#drag-area {
	border: 1px solid #ccc;
	height: 150px;
	line-height: 150px;
	text-align: center;
	color: #aaa;
	width: auto;
	margin: 10px auto;
}

.add_mian ul li {
	line-height: 20px;
}
</style>
</head>

<body class="input">

	<div class="tongyong_main">
		<div class="map">
			<div class="pro_map">管理信息 > 课程详情</div>
		</div>
	</div>

	<div class="content">

		<div class="list_main">

			<div class="list_main01">
				<div class="list_main02">
					<div class="top_info">
						<div class="top_info_01">基本信息</div>
					</div>
					<div class="add_mian">
						<ul>
							<li>
								<span class="add_wenzi">课程名称：</span>
								<span class="add_xhao">${course.name }</span>
							</li>
							<li>
								<span class="add_wenzi">课程背景图片：</span>
								<span class="add_xhao"></span>
								<img src="${course.url }" style="width: 60px; height: 60px;" alt="" />
							</li>
							<li>
								<span class="add_wenzi">老师：</span>
								<span class="add_xhao">${course.user.nickname }</span>
							</li>
							<li>
								<span class="add_wenzi">主要内容：</span>
								<span class="add_xhao">${course.mainContent }</span>
							</li>
							<li>
								<span class="add_wenzi">开始时间：</span>
								<span class="add_xhao"><fmt:formatDate value="${course.startDate }" pattern="yyyy-MM-dd" /></span>
							</li>
							<li>
								<span class="add_wenzi">收费：</span>
								<span class="add_xhao">${course.money }</span>
							</li>
							<li>
								<span class="add_wenzi">房间模式：</span>
								<span class="add_xhao">
									<c:if test="${course.types==1 }">直播</c:if>
									<c:if test="${course.types==0 }">录播</c:if>
								</span>
							</li>
							<li>
								<span class="add_wenzi">最多人数：</span>
								<span class="add_xhao">${course.maxStuNum }</span>
							</li>
							<li>
								<span class="add_wenzi">该房间总收入：</span>
								<span class="add_xhao">${course.countMoney }</span>
							</li>
							<li>
								<span class="add_wenzi">是否热门：</span>
								<span class="add_xhao">
									<c:if test="${course.isHot==true }">是</c:if>
									<c:if test="${course.isHot==false }">否</c:if>
								</span>
							</li>
							<li>
								<span class="add_wenzi">状态：</span>
								<span class="add_xhao">
									<c:if test="${course.status==1 }">正在上课</c:if>
									<c:if test="${course.status==0 }">未上课</c:if>
								</span>
							</li>
							<li>
								<span class="add_wenzi">是否可申请分享音频：</span>
								<span class="add_xhao">
									<c:if test="${course.microphoneenable==true }">是</c:if>
									<c:if test="${course.microphoneenable==false }">否</c:if>
								</span>
							</li>
							<li>
								<span class="add_wenzi">是否可申请分享视频：</span>
								<span class="add_xhao">
									<c:if test="${course.screenshareenable==true }">是</c:if>
									<c:if test="${course.screenshareenable==false }">否</c:if>
								</span>
							</li>
							<li>
								<span class="add_wenzi">评分：</span>
								<span class="add_xhao">${course.score }</span>
							</li>
						<%-- 	<li>
								<span class="add_wenzi">该房间总收入：</span>
								<span class="add_xhao">${course.countMoney }</span>
							</li> --%>
							<li>
								<span class="add_wenzi">备注：</span>
								<span class="add_xhao">${course.remark }</span>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- </form>    -->
		</div>
	</div>
</body>
</html>


