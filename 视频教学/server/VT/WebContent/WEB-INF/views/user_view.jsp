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
<title>版本管理</title>
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
			<div class="pro_map">管理信息 > 用户详情</div>
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
							<li><span class="add_wenzi">头像：</span> <span class="add_xhao"></span>
								<img src="user.avatar" style="width: 60px; height: 60px;" alt="" />
							</li>
							<li><span class="add_wenzi">用户名：</span> <span
								class="add_xhao">${ user.username}</span></li>
							<li><span class="add_wenzi">昵称：</span> <span class="add_xhao">${user.nickname}</span>

							</li>
<li><span class="add_wenzi">性别：</span> <span class="add_xhao"><c:if test="${user.sex==0}">女</c:if><c:if test="${user.sex==1}">男</c:if><c:if test="${user.sex==null||user.sex==2}">未知</c:if></span>

							</li>
							<li><span class="add_wenzi">是否教师：</span> <input
								style="margin-top: 10px; margin-left: 10px;"
								<c:if test="${user.isteacher eq true }"> checked="checked" </c:if>
								type="checkbox" name="isPublish" value="true" /></li>
								
							<li><span class="add_wenzi"><c:if test="${user.isteacher==true}">收入：</c:if><c:if test="${user.isteacher==false}">余额：</c:if></span> <span class="add_xhao"><c:if test="${isteacher==true}">${user.income}</c:if><c:if test="${isteacher==false}">${user.money}</c:if></span>

							</li>

							<li><span class="add_wenzi">城市：</span> <span class="add_xhao">${user.city}</span>
							</li>

							<li><span class="add_wenzi">学校：</span> <span class="add_xhao">${user.school}</span>
							</li>
							<li><span class="add_wenzi">电话：</span> <span class="add_xhao">${user.tel}</span></li>
							<li><span class="add_wenzi">email：</span> <span
								class="add_xhao">${user.email}</span> </li>
								<li><span class="add_wenzi">地址：</span> <span class="add_xhao">${user.address}</span>
							<li><span class="add_wenzi">创建时间：</span> <span
								class="add_xhao"> <fmt:formatDate
										value="${user.createDate}" pattern="yyyy-MM-dd" /></span></li>
						</ul>
					</div>
				</div>
			</div>
			<!-- </form>    -->
		</div>
	</div>
</body>
</html>


