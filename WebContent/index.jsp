<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();//获得当前的项目根目录路径赋值给path
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>医院在线预约挂号系统</title>
</head>
<body>
 <jsp:forward page="/WEB-INF/view/login.jsp"></jsp:forward>
 </body>
</html>
