<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!--上部开始-->
<div class="top topfixed">
	<div class="top_header box0">
		<div class="logo"><a href="<%=basePath%>"><img src="<%=basePath %>resource/page/images/top_logo.png" alt="logo" /></a></div>
		<ul class="top_right"><%-- class="b1" b2 b3 b4 --%>
			<li class="first">
				<c:choose>
					<c:when test="${!empty sessionScope.member_session }">
						<a href="javascript:void(0);" style="cursor: default;">[
						<span onclick="gotoMemberCenter('${sessionScope.member_session.phone }')" 
							style="margin-right: 5px;margin-left: 5px;cursor: pointer;">${sessionScope.member_session.phone }</span>
							|<span onclick="destorySession();" style="margin-right: 5px;margin-left: 5px;cursor: pointer;">注销</span>]</a><b class="b1"></b>
					</c:when>
					<c:otherwise>
						<a href="javascript:showLogin();">[<span>登录</span>|<span>注册</span>]</a><b class="b1"></b>
					</c:otherwise>
				</c:choose>
			</li>
			<li class="second"><a href="<%=basePath%>web/news/list">新闻动态</a><b class="b2"></b></li>
			<li class="third"><a href="<%=basePath%>web/server/detail?id=1">服务范围</a><b class="b3"></b></li>
			<li class="last"><a href="<%=basePath%>web/server/appdown">APP下载</a><b class="b4"></b></li>
		</ul>
	</div>
	<!--登录-->
	<div id="loginDIV" style="display: none;">
		<div class="bg1"></div>
		<div class="bg2"></div>
		<div class="login">
			<div class="member_login">
				<a href="#" class="member_login_left"><img src="<%=basePath %>resource/page/images/login_1.png" alt="" /></a>
				<a href="javascript:closeLoginDiv();" class="close"><img src="<%=basePath %>resource/page/images/login_2.png" alt="" /></a>
			</div>
			<div class="login_con">
				<h3>短信登录</h3>
				<p>验证即登录，未注册将自动创建一动车保账号</p>
				<div >
					<input type="text" class="login_tel" placeholder="请输入手机号" />
					<div style="margin: 0px;position: relative;">
						<input type="text" class="pass1 phoneCode" placeholder="动态密码" />
						<input type="button" class="pass2" value="获取动态密码" onclick="getCode()" id="sendCodeBtn"/>
						<label style="position: absolute;top:55px;left: 0px;color:red" id="errorMessage">
							
						</label>
					</div>
					<input type="button" class="sub" value="" onclick="login();"/>
				</div>
				<div class="login_bottom">
					<div class="login_bottom_left">阅读并接收<a href="#">《一动车保用户协议》</a></div>
					<div class="login_bottom_right"><a href="javascript:gotoIndexPage();">&gt;&gt;返回首页</a></div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="topHight"></div>
	<!--box banner start-->
	<!--box banner end-->
<!--上部结束-->
<script type="text/javascript">
	//显示登录窗口
	function showLogin(){
		errorMessage("");
		$("#loginDIV").show();
	}
	//关闭登录窗口
	function closeLoginDiv(){
		resetCodeBtn();
		errorMessage("");
		$("#loginDIV").hide(300);
	}
	//获取验证码
	function getCode(){
		errorMessage("");
		$("#sendCodeBtn").prop("disabled",true);
		var mobile = $(".login_tel").val();
		
		if(!checkPhoneCode(mobile)){
			errorMessage("请输入正确的手机号码");
			return;
		}
		
		var url = "<%=basePath%>web/getcode";
		var data = {
				mobile:mobile
		};
		
		var options = {
				type:"post",
				url:url,
				data:data,
				cache:false,
				async:true,
				dataType:"json",
				success:function(result){
					if(result.status=="success"){
						successMessage("验证码已发送成功");
						after60Second();
					}else{
						errorMessage(result.message);
						$("#sendCodeBtn").prop("disabled",false);
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					errorMessage("请求出错");
					$("#sendCodeBtn").prop("disabled",false);
				}
		};
		$.ajax(options);
	}
	
	//循环 设置倒计时
	var intterval2;
	function after60Second(){
		var timeOut = 30;
		var str = "秒后,再次发送";
		intterval2 = setInterval(function(){
			timeOut = timeOut*1 - 1;
			if(timeOut <= 0){
				$("#sendCodeBtn").val("获取动态密码");
				resetCodeBtn();
			}else{
				$("#sendCodeBtn").val(timeOut+str);
			}
			
		},1000);
	}
	
	//充值登录框信息
	function resetCodeBtn(){
		$("#sendCodeBtn").val("获取动态密码");
		$("#sendCodeBtn").prop("disabled",false);
		clearInterval(intterval2);
	}
	
	//验证手机
	function checkPhoneCode(value){
		//var pattern = /^1[34578]\d{9}$/;
		var pattern = /^1\d{10}$/;
		//alert(pattern.test(value));
		if(pattern.test(value)){
			return true;
		}
		return false;
	}
	//设置错误信息
	function errorMessage(value){
		var html = "<span style='color:red'>"+value+"</span>";
		$("#errorMessage").html(html);
	}
	//成功消息
	function successMessage(value){
		var html = "<span style='color:green'>"+value+"</span>";
		$("#errorMessage").html(html);
	}
	//跳转到个人中心
	function gotoMemberCenter(id){
		window.location.href="<%=basePath%>web/gotoMemberCenter?mobile="+id;
	}
	//登录
	function login(){
		var mobile = $(".login_tel").val();
		var code = $(".phoneCode").val();
		
		if(!checkPhoneCode(mobile)){
			errorMessage("请输入正确的手机号码");
			return;
		}
		
		if(code == ""){
			errorMessage("请填写验证码");
			return;
		}
		
		var url = "<%=basePath%>web/login";
		var data = {
			mobile : mobile,
			code:code
		};
		
		$.post(url,data,function(result){
			if(result.status == "success"){
				window.location.reload(true);
			}else{
				errorMessage(result.message);
			}
		},"json");
	}
	
	function gotoIndexPage(){
		window.location.href="<%=basePath%>";
	}
	
	function destorySession(){
		var url = "<%=basePath%>web/destory";
		$.post(url,{},function(result){
			if(result.status == "success"){
				window.location.reload(false);
			}
		},"json");
	}
</script>