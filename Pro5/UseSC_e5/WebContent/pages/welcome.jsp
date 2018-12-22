<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome JSP</title>
</head>
<body>
   <%String username = request.getAttribute("username").toString(); %>
         您好，<%=username %>! 欢迎来到UseSC_conf！
</body>
</html>