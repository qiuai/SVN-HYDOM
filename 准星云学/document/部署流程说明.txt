部署环境
请根据开发环境，结合操作系统实际情况进行选择
开发环境如下：
1.jdk6
2.apache-tomcat-6.0.43-windows-x86
3.mysql5.6
****************************************************

部署流程
1.设置Tomcat编码为UTF-8【重要】
${CATALINA_HOME}/conf/server.xml文件设置
#################################################
<Connector port="8080" protocol="HTTP/1.1"  
useBodyEncodingForURI="true" URIEncoding="UTF-8"
connectionTimeout="20000" redirectPort="8443" />
#################################################

2.创建数据库cis
（1）字符集：utf8 -- UTF-8 Unicode【重要】
（2）排序规则建议：utf8_unicode_ci

3.运行 “初始化.sql”脚本文件

4.dbcp.properties文件初始设置如下，请根据实际情况调整。
#################################################
url=jdbc\:mysql\://localhost\:3306/cis
username=root
password=123456
initialSize=10
maxActive=1000
maxIdle=100
minIdle=100
#################################################

5.日志文件配置
log4j.properties
（1）异常日志默认放在${cis.root}/WEB-INF/log/exception.log
（2）其它日志放在G:/server目录下，如果不存在这样的目录，请修改相关配置。【重要】

6.发布war包到Tomcat容器
7.启动Tomcat，访问：http://localhost:8080/cis/   
帐号：admin 密码：123456
****************************************************