<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | 新規登録</title>
<link rel="stylesheet" href="css/signup.css">
</head>
<body>
<h1>Spica</h1>
<h3>学生団体向け会計管理Webアプリ</h3>

<h4>新規登録</h4>

<form action="signupServlet" method="post" >
	<label for="name">ユーザー名<span class="req">*</span></label><br>
	<input type="text" name = "name" required><br><br>
	
	<label for="mail">メールアドレス<span class="req">*</span></label><br>
	<input type="email" name = "mail" required><br><br>
	
	<label for="passwprd_1">パスワード<span class="req">*</span></label><br>
	<input type="password" name="password_1" required><br><br>
	
	<label for="passwprd_2">確認用パスワード<span class="req">*</span></label><br>
	<input type="password" name="password_2" required><br><br>
	
	<% String error = (String) request.getAttribute("errorMessage"); %>
    <% if (error != null) { %>
    	<p class="error-message"><%= error %></p>
    <% } %>
	
	<input type="submit" name="submit" class="signup-btn" value="新規登録">
</form>
<hr>
<h4>アカウントをお持ちの方</h4>
<div class="center-btn">
	<a href="signinServlet" class="signin-btn">ログイン</a>
</div>


<% String message = (String) request.getAttribute("Message"); %>
<% if (message != null) { %>
   	<script>alert("<%= message %>");</script>
<% } %>
</body>
</html>