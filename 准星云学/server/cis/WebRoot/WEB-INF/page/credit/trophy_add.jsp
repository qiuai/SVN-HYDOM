<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
        <meta name="description" content="">
        
        <meta name="author" content="">
        <title>奖品添加</title>
        <link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resource/chain/css/style.default.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/morris.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resource/chain/css/select2.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
		<script src="${pageContext.request.contextPath}/resource/art/artDialog.js?skin=blue"></script>
        <script src="${pageContext.request.contextPath}/resource/art/plugins/iframeTools.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="${pageContext.request.contextPath}/resource/kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="${pageContext.request.contextPath}/resource/kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="${pageContext.request.contextPath}/resource/kindeditor/plugins/code/prettify.js"></script>
		<script>
		var editor;
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="trophy.detail"]', {
				resizeType : 1,
				width : "420px", //编辑器的宽度为500px
     	        height : "180px", //编辑器的高度为100px
     	        minWidth:"500px",
				allowPreviewEmoticons : false,
				allowImageUpload : false,
				items : [
					'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
					'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'link']
			});
		});
		</script>
        
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resource/chain/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/respond.min.js"></script>
        <![endif]-->

    <body>
        <header>    
        <%@ include file="/WEB-INF/page/common/head.jsp" %>
        </header>
        
        <section>
            <div class="mainwrapper">
        	<%@ include file="/WEB-INF/page/common/left.jsp" %>
                
                <div class="mainpanel">
                    <div class="pageheader">
                        <div class="media">
                            <div class="pageicon pull-left">
                                <i class="fa  fa-list-alt"></i>
                            </div>
                            <div class="media-body">
                                <ul class="breadcrumb">
                                    <li><a href=""><i class="glyphicon glyphicon-home"></i></a></li>
                                    <li>trophy add</li>
                                </ul>
                                <h4>奖品添加</h4>
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->
                    
                    <div class="contentpanel">
                    	<div class="content-m"  >
                         <div>奖品添加</div>
                         <div style="border-bottom: 1px solid #d5d5d5;margin-bottom: 10px;">&nbsp</div>
                         <s:form action="trophy_add" id="myform" name="myform" namespace="/manage/credit" method="post" enctype="multipart/form-data" > 
	    					<div class="form-horizontal">
		    					 <div class="form-group">
								    <label  class="col-sm-2 control-label">奖品名称</label>
								    <div class="col-sm-4">
								      <input type="text" class="form-control" name="trophy.name" placeholder="奖品名称">
								      <span></span>
								    </div>
								    <label  class="col-sm-2 control-label">兑换状态</label>
								    <div class="col-sm-4">
								      <select name="trophy.state" class="form-control"  >
								      	<option value="1">可以兑换</option>
								      	<option value="0">停止兑换</option>
								      </select>
								    </div>
								  </div>
		    					 <div class="form-group">
								    <label  class="col-sm-2 control-label">价值</label>
								    <div class="col-sm-4">
								      <input type="text" class="form-control"  name="trophy.money"  placeholder="价值">
								      <span></span>
								    </div>
								    <label  class="col-sm-2 control-label">所需积分</label>
								    <div class="col-sm-4">
								      <input type="text" class="form-control" name="trophy.score" placeholder="所需积分" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" >
								      <span></span>
								    </div>
								 </div>
		    					 <div class="form-group">
								    <label  class="col-sm-2 control-label">库存</label>
								    <div class="col-sm-4">
								      <input type="text" class="form-control" name="trophy.stock" placeholder="库存" onkeyup="this.value=this.value.replace(/[^\d]/g,'') " onafterpaste="this.value=this.value.replace(/[^\d]/g,'')">
								      <span></span>
								    </div>
								    <label  class="col-sm-2 control-label">类别</label>
								    <div class="col-sm-4">
								      <select name="typeid" class="form-control"  >
								      	<c:forEach items="${types}" var="entry">  
								      		<option value="${entry.id }">${entry.name}</option>
								      	</c:forEach>
								      </select>
								    </div>
								 </div>
		    					 <div class="form-group">
								    <label  class="col-sm-2 control-label">上传图片</label>
								    <div class="col-sm-10">
								      <input type="file" name="img" class="form-control"> 
								    </div>
								 </div>
		    					 <div class="form-group">
								    <label  class="col-sm-2 control-label">详细说明</label>
								    <div class="col-sm-10">
								    <textarea  name="trophy.detail" ></textarea>
    					 			</div>
								  </div>
	    					 
	    					 	<div style="line-height: 50px;text-align: center;">
	    					 		<span><input type="reset" value="重置" class="btn btn-primary"/></span>
	    					 		<span><input type="submit" value="提交" class="btn btn-primary"/></span>
	    					 	</div>
	    					</div>
    					 </s:form>
						</div>  <!-- content  -->

                    </div><!-- contentpanel -->
                    <div class="bottomwrapper" >
						<%@ include file="/WEB-INF/page/common/bottom.jsp" %>
                    </div>
                </div><!-- mainpanel -->
            </div><!-- mainwrapper -->
        </section>

        <script src="${pageContext.request.contextPath}/resource/chain/js/jquery-1.11.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/modernizr.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/pace.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/retina.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/jquery.cookies.js"></script>
        
        <script src="${pageContext.request.contextPath}/resource/chain/js/flot/jquery.flot.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/flot/jquery.flot.resize.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/flot/jquery.flot.spline.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/jquery.sparkline.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/morris.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/raphael-2.1.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/bootstrap-wizard.min.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/select2.min.js"></script>

        <!-- 验证框架 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.validate.min.js"></script>
  	    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.maskedinput-1.0.js"></script>
  	    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/validate/trophy.js"></script>
		
		<script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
    </body>
</html>
