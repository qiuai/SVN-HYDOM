<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body><h1>没有权限</h1>
<form action="/VT/api/upload.do" method="post" enctype="multipart/form-data">
<input type="text"  value="{'type':401}" name="httpdata">
<input type="file" name="file"><button>上传</button>
</form>
</body>
</html>