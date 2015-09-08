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
        <title>系统参数设置</title>
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
			editor = K.create('textarea[name="config.valueContent"]', {
				resizeType : 1,
				width : "500px", //编辑器的宽度为500px
     	        height : "200px", //编辑器的高度为100px
     	        minWidth:"500px",
				allowPreviewEmoticons : false,
				allowImageUpload : false,
				items : [
					'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
					'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
					'insertunorderedlist', '|', 'emoticons', 'link']
			});
		});
		</script>
		<style type="text/css">
			.ke-container ke-container-default{
				width:500px;
			}
	    </style>
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
                                    <li>system settings</li>
                                </ul>
                                <h4>系统设置</h4>
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->
                    
                    <div class="contentpanel">
                    	<div class="content-m" >
                         <div>系统设置</div>
                         <div style="float: right;">设置</div>
                         <div style="border-bottom: 1px solid #d5d5d5">&nbsp</div>
                         <s:form action="config_edit" name="myform" namespace="/manage/extra" method="post" id="pageList"> 
                         <s:hidden name="m" />
                         <s:hidden name="scid" />
    					 <c:if test="${config.id=='match'}">
    					 <div id="configblock" class="form-horizontal">
    					 	<div style="line-height: 50px;">
    					 		<span>工单区块设置</span>
    					 	</div>
    					 	
    					 	<div class="form-horizontal" style="width: 400px;">
		    					 <div class="form-group">
								    <label  class="col-sm-3 control-label">分配上限</label>
								    <div class="col-sm-9">
								      <input id="valueInt"  name="config.valueInt" type="text"  value="${config.valueInt}" class="form-control">
								    </div>
						   		 </div>
		    					 <div class="form-group">
								    <label  class="col-sm-3 control-label">分配初值</label>
								    <div class="col-sm-9">
								      <input  id="valueShort" name="config.valueShort" type="text"  value="${config.valueShort}" class="form-control">
								    </div>
						   		 </div>
		    					 <div class="form-group">
								    <label  class="col-sm-3 control-label">正确比例</label>
								    <div class="col-sm-9">
									    <div class="input-group">
									      <div class="input-group-addon">百分比</div>
								     	 <input style="text-align: right;" id="valueDouble" name="config.valueDouble" type="text"  value="${percent}"  class="form-control" >
									      <div class="input-group-addon">%</div>
									    </div>
								    </div>
						   		 </div>
		    					 <div class="form-group">
								    <label  class="col-sm-3 control-label">超时时间 </label>
								    <div class="col-sm-9">
								    	<div class="input-group">
								     	 <input style="text-align: right;"  id="valueLong" name="config.valueLong" type="text"  value="${config.valueLong}"  class="form-control">
								    	 <div class="input-group-addon">ms</div>
								    	</div>
								    </div>
						   		 </div>
						   	</div>
    					 </div><!-- configblock  -->
    					 </c:if>
    					 
    					 <c:if test="${config.id=='manual'}">
    					 <div id="configblock" >
    					 	<div style="line-height: 50px;">
    					 		<span>积分说明</span>
    					 	</div>
    					 	<div  style="width: 500px;">
	    					 	<div class="form-group">
								    <div class="input-group col-md-6">
								    	 <div class="input-group-addon">积分设置</div>
								     	 <input style="text-align: right;"  id="valueLong" name="config.valueDouble"  type="text"   value="${config.valueDouble}"  class="form-control">
								    	 <div class="input-group-addon">分</div>
								    </div>
								    <div class="input-group col-md-6">
								    	<div>每个区块识别正确可以获得的积分</div>
								    </div>
							   	</div>
							   	<hr/>
							   	<div class="form-group">
	    					 		<textarea name="config.valueContent" cols="55" rows="8" style="width:500px;height:200px;visibility:hidden;">
	    					 			${config.valueContent }
	    					 		</textarea>
	    					 	</div>
	    					 </div>
    					 </div><!-- configblock  -->
    					 
    					 </c:if>
    					 <c:if test="${config.id=='about'}">
    					 <div id="configblock" >
    					 	<div style="line-height: 50px;">
    					 		<span>关于我们</span>
    					 	</div>
    					 	<div style="line-height: 50px;">
    					 		<textarea name="config.valueContent" cols="50" rows="8" style="width:500px;height:200px;visibility:hidden;">
    					 			${config.valueContent }
    					 		</textarea>
    					 	</div>
    					 </div><!-- configblock  -->
    					 
    					 </c:if>
    					 <c:if test="${config.id=='phone'}">
    					 <div id="configblock" >
    					 	<div style="line-height: 50px;">
    					 		<span>联系我们</span>
    					 	</div>
    					 	<div style="margin-bottom: 10px;">
    					 		  <label>客服电话</label>
							      <input name="config.valueString" style="line-height: 30px;padding: 1px 8px;" type="text"  value="${config.valueString}" placeholder="客服电话" >
						   	</div>
    					 	<div style="line-height: 50px;">
    					 		<textarea name="config.valueContent" cols="50" rows="8" style="width:500px;height:200px;visibility:hidden;">
    					 			${config.valueContent }
    					 		</textarea>
    					 	</div>
    					 </div><!-- configblock  -->
    					 </c:if>
    					 
    					 	<div style="line-height: 50px;text-align: center;">
    					 		<span><input type="reset" value="重置" class="btn btn-primary"/></span>
    					 		<span><input type="submit" value="提交" class="btn btn-primary"/></span>
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

        <script src="${pageContext.request.contextPath}/resource/chain/js/custom.js"></script>
        <script src="${pageContext.request.contextPath}/resource/chain/js/dashboard.js"></script>

    </body>
</html>
