<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!--底部开始-->
<div class="down">	
	<div class="span5">
		<div class="box0">
			<div class="span5l">
				<div class="down_pic"><img src="<%=basePath %>resource/page/images/down_pic.png" alt="" />
				</div>
				<div class="down_txt">
					<ul>
						<li><a href="#">一动车保</a>|</li>
						<li><a href="<%=basePath%>web/server/detail?id=0">关于我们</a>|</li>
						<li><a href="<%=basePath%>web/server/detail?id=2">服务简介</a>|</li>
						<li><a href="<%=basePath%>web/server/detail?id=3">市场合作</a>|</li>
						<li><a href="<%=basePath%>web/server/detail?id=4">联系我们</a></li>
					</ul>
					<p>&copy;2015&nbsp;www.YIDONGCHEBAO.com&nbsp; 黔ICP备15011946号-1</p>
				</div>
			</div>
			<div class="span5r">
				<ul>
					<li class="l1">0851-8223 9999 转 6001-6006</li>
				<!-- 	<li class="l2">咨询QQ：4008559999</li> -->
					<li class="l3">公司地址：贵州省贵阳市高新区688号</li>
				</ul>
				<div class="weixin">
					<img src="<%=basePath %>resource/page/images/code.png" alt="" />
					<p>扫一扫有惊喜！</p>
				</div>
			</div>
		</div>
	</div>
</div>
<!--底部结束-->