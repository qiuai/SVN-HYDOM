<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>

            <div class="headerwrapper">
                <div class="header-left">
                    <a href="index.html" class="logo">
                        <img src="${pageContext.request.contextPath}/resource/chain/images/logo.png" alt="" /> 
                    </a>
                    <div class="pull-right">
                        <a href="" class="menu-collapse">
                            <i class="fa fa-bars"></i>
                        </a>
                    </div>
                </div><!-- header-left -->
                
                <div class="header-right">
                    <div class="pull-right" >
                        <div class="btn-group btn-group-option" style="margin-left: 100px">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                              <i class="fa fa-caret-down"></i>
                            </button>
                            <ul class="dropdown-menu pull-right" role="menu">
                              <li><a href='<s:url action="myProfile" namespace="/manage/account" />' ><i class="glyphicon glyphicon-user"></i> My Profile</a></li>
                              <li><a href='<s:url action="myProfileEditUI" namespace="/manage/account" />' ><i class="glyphicon glyphicon-cog"></i> Account Settings</a></li>
                              <li class="divider"></li>
                              <li><a href='<s:url action="signout" namespace="/" />' ><i class="glyphicon glyphicon-log-out"></i>Sign Out</a></li>
                            </ul>
                        </div><!-- btn-group -->
                        
                    </div><!-- pull-right -->
                    
                </div><!-- header-right -->
                
            </div><!-- headerwrapper -->
