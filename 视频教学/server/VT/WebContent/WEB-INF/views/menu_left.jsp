<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导航栏</title>
<link href="<%=base%>template/admin/css/left.css" rel="stylesheet" type="text/css" />
<link href="<%=base%>template/admin/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=base%>template/admin/css/bootstrap.min.css">
<style type="text/css">
.menu_title SPAN {
	FONT-WEIGHT: bold; LEFT: 3px; COLOR: #ffffff; POSITION: relative; TOP: 2px 
}
.menu_title2 SPAN {
	FONT-WEIGHT: bold; LEFT: 3px; COLOR: #FFCC00; POSITION: relative; TOP: 2px
}

</style>
<script type="text/javascript">
	function exit(){
		if (confirm("确定退出?") == true) {
			window.location.href="<%=base%>user/logout.do";
		}
	}
	
	function showsubmenu(sid){
		whichEl = eval("submenu" + sid);
		imgmenu = eval("imgmenu" + sid);
		if (whichEl.style.display == "none")
		{
		eval("submenu" + sid + ".style.display=\"\";");
		}
		else
		{
		eval("submenu" + sid + ".style.display=\"none\";");
		}
	}
	
	
</script>
</head>
<body style="overflow-x:hidden;">
<table width="190" height="100%" border="0" cellpadding="0" cellspacing="0" class="left_main">
  <tr>
  	<td height="40">
    	<div class="pro_search" ><a style="text-decoration: none;" href="index.do" target="mainFrame">管理信息</a></div>
    </td>
  </tr>
  <tr>
    <td valign="top"><table width="190" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td><table width="190" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="23" id="imgmenu4" class="menu_title" onMouseOver="this.className='menu_title2';" onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="190" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="nav_li_3_1"><a href="../user/list.do" target="mainFrame"><i class="icon-inbox"></i><div class="menu-text">教师管理</div></a></td>
              </tr>
            </table></td>
          </tr>  
        </table></td>
      </tr>
      <tr>
      	<td><table width="190" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="23" id="imgmenu4" class="menu_title" onMouseOver="this.className='menu_title2';" onClick="showsubmenu(4)" onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="190" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="nav_li_3_1"><a href="../user/list.do?isteacher=false" target="mainFrame"><i class="icon-inbox"></i><div class="menu-text">学生管理</div></a></td>
              </tr>
            </table></td>
          </tr>  
        </table></td>
      </tr>
       <tr>
      	<td><table width="190" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="23" id="imgmenu4" class="menu_title" onMouseOver="this.className='menu_title2';" onClick="showsubmenu(4)" onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="190" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="nav_li_3_1"><a href="../course/list.do" target="mainFrame"><i class="icon-inbox"></i><div class="menu-text">课程管理</div></a></td>
              </tr>
            </table></td>
          </tr>  
        </table></td>
      </tr>
      
       <tr>
      	<td><table width="190" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="23" id="imgmenu4" class="menu_title" onMouseOver="this.className='menu_title2';" onClick="showsubmenu(4)" onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="190" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="nav_li_3_1"><a href="../video/list.do" target="mainFrame"><i class="icon-inbox"></i><div class="menu-text">视频管理</div></a></td>
              </tr>
            </table></td>
          </tr>  
        </table></td>
      </tr>
      
      <tr>
      	<td><table width="190" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="23" id="imgmenu4" class="menu_title" onMouseOver="this.className='menu_title2';" onClick="showsubmenu(4)" onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="190" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="nav_li_3_1"><a href="javascript:void(0)" target="mainFrame"><i class="icon-inbox"></i><div class="menu-text">教学统计</div></a></td>
              </tr>
            </table></td>
          </tr>  
        </table></td>
      </tr>
      
      <tr>
        <td><table width="190" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="23" id="imgmenu6" class="menu_title" onMouseOver="this.className='menu_title2';"  onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="190" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="nav_li_3_1"><a href="javascript:void(0)" target="mainFrame"><i class="icon-list-alt"></i><div class="menu-text">版本管理</div></a></td>
              </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
      <tr>

      	<td>
	      	<table width="190" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td height="23" id="imgmenu7" class="menu_title" onMouseOver="this.className='menu_title2';"  onMouseOut="this.className='menu_title';" style="cursor:hand">
	            	<table width="190" border="0" cellspacing="0" cellpadding="0">
	              	<tr>
	                	<td class="nav_li_3_1""><a href="javascript:exit()"><i class="icon-power-off"></i><div class="menu-text">退出登录</div></a></td>
	              	</tr>
	            	</table>
	            </td>
	          </tr>
	        </table>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>