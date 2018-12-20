<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register JSP</title>
</head>
<body>
   <form action="register.sc" method="post">
                  用  户  名：<input type="text" name="username" id="username" placeholder="以英文字母开头，只能包含英文字母、数字、下划线!" />
                  密        码：<input type="text" name="password" id="password" placeholder="密码最小为5位" />
                  重复密码：<input type="text" name="repassword" id="repassword" placeholder="请再次输入密码" />
      <input type="submit" value="注册">
   </form>
</body>
</html>