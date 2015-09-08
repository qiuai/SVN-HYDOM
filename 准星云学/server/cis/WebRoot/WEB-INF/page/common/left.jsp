<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<div class="leftpanel">
                    <div class="media profile-left">
                        <a class="pull-left profile-thumb" href='<s:url action="myProfile" namespace="/manage/account" />' >
                            <img class="img-circle" src="${pageContext.request.contextPath}/resource/chain/images/photos/profile.png" alt="">
                        </a>
                        <div class="media-body">
                            <h4 class="media-heading">${loginAccount.nickname}</h4>
                            <small class="text-muted" style="font-size: 12px;"><fmt:formatDate value="${loginAccount.lastSigninTime}" pattern="HH:mm:ss dd/MM/yyyy"/></small>
                        </div>
                    </div><!-- media -->
                    
                    <h3 class="leftpanel-title">Navigation</h3>
                    <ul class="nav nav-pills nav-stacked">
                        <li <c:if test="${m==null}">class="active"</c:if> ><a href='<s:url action="index" namespace="/manage" />'><i class="fa fa-home"></i> <span>首页</span></a></li>
                       
                        <li <c:if test="${param.m==1||m==1}">class="active parent"</c:if>  class="parent" ><a href=""><i class="fa fa-bars"></i> <span>工单管理</span></a>
                            <ul class="children">
                            	<td:permission privilegeValue="manage/task/job_list.action">
  								<li><a href='<s:url action="job_list" namespace="/manage/task" />'>工单分配</a></li> 
                            	</td:permission>
                            </ul>
                        </li>
                        
                        <li <c:if test="${param.m==2||m==2}">class="active parent"</c:if> class="parent"><a href=""><i class="fa fa-user"></i> <span>用户管理</span></a>
                            <ul class="children"> 
                            	<td:permission privilegeValue="manage/account/user_list.action">
                                <li><a href='<s:url action="user_list" namespace="/manage/account" />'>用户查看</a></li>
                            	</td:permission>
                            	<td:permission privilegeValue="manage/credit/scoreRecord_list.action">
                                <li><a href='<s:url action="scoreRecord_list" namespace="/manage/credit" />'>积分中心</a></li>
                            	</td:permission>
                            	<td:permission privilegeValue="manage/credit/trophyRecord_list.action">
                                <li><a href='<s:url action="trophyRecord_list" namespace="/manage/credit" />'>兑换管理</a></li>
                            	</td:permission>
                            </ul>
                        </li>
                        
                        <li <c:if test="${param.m==3||m==3}">class="active parent"</c:if> class="parent" ><a href=""><i class="fa fa-suitcase"></i> <span>奖品管理</span></a>
                            <ul class="children">
                            	<td:permission privilegeValue="manage/credit/trophy_list.action">
  								<li><a href='<s:url action="trophy_list" namespace="/manage/credit" />'>奖品列表</a></li> 
                            	</td:permission>
                            	<td:permission privilegeValue="manage/credit/trophy_addUI.action">
  								<li><a href='<s:url action="trophy_addUI" namespace="/manage/credit" />'>奖品添加</a></li> 
                            	</td:permission>
                            	<td:permission privilegeValue="manage/credit/trophyType_list.action">
  								<li><a href='<s:url action="trophyType_list" namespace="/manage/credit" />'>奖品分类管理</a></li> 
                            	</td:permission>
                            </ul>
                        </li>
                        
                        <li <c:if test="${param.m==4||m==4}">class="active parent"</c:if> class="parent"><a href=""><i class="fa fa-edit"></i> <span>系统管理</span></a>
                            <ul class="children">
                            	<td:permission privilegeValue="manage/account/account_list.action">
                                <li><a href='<s:url action="account_list" namespace="/manage/account" />'>系统帐号</a></li>
                            	</td:permission>
                            	<td:permission privilegeValue="manage/extra/message_list.action">
                                <li><a href='<s:url action="message_list" namespace="/manage/extra" />'>消息管理</a></li>
                            	</td:permission>
                            	<td:permission privilegeValue="manage/extra/sense_list.action">
                                <li><a href='<s:url action="sense_list" namespace="/manage/extra" />'>意见反馈</a></li>
                            	</td:permission>
                            	<td:permission privilegeValue="manage/account/group_list.action">
                                <li><a href='<s:url action="group_list" namespace="/manage/account" />'>角色定义</a></li>
                            	</td:permission>
                            	<td:permission privilegeValue="manage/extra/appversion_list.action">
                                <li><a href='<s:url action="appversion_list" namespace="/manage/extra" />'>App版本管理</a></li>
                            	</td:permission>
                            </ul>
                        </li>
                        
                        <li <c:if test="${param.m==5||m==5}">class="active parent"</c:if> class="parent"><a href=""><i class="fa fa-cog"></i> <span>系统设置</span></a>
                            <ul class="children">
                            	<td:permission privilegeValue="manage/extra/config_show.action#scid=match">
                                <li><a href='<s:url action="config_show" namespace="/manage/extra" />?scid=match'>工单设置</a></li>
                            	</td:permission>
                            	<td:permission privilegeValue="manage/extra/config_show.action#scid=manual">
                                <li><a href='<s:url action="config_show" namespace="/manage/extra" />?scid=manual'>积分说明</a></li>
                            	</td:permission>
                            	<td:permission privilegeValue="manage/extra/config_show.action#scid=about">
                                <li><a href='<s:url action="config_show" namespace="/manage/extra" />?scid=about'>关于我们</a></li>
                                </td:permission>
                            	<td:permission privilegeValue="manage/extra/config_show.action#scid=phone">
                                <li><a href='<s:url action="config_show" namespace="/manage/extra" />?scid=phone'>联系我们</a></li>
                                </td:permission>
                            </ul>
                        </li>
                    </ul>
                    
</div><!-- leftpanel -->