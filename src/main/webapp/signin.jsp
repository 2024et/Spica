<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | ログイン</title>
<link rel="stylesheet" href="css/signin.css">
</head>
<body>
<h1>Spica</h1>
<h3>学生団体向け会計管理Webアプリ</h3>

<h4>ログイン</h4>

<form action="signinServlet" method="post" >
	
	<label for="mail">メールアドレス<span class="req">*</span></label><br>
	<input type="email" name = "mail" required><br><br>
	
	<label for="passwprd_1">パスワード<span class="req">*</span></label><br>
	<input type="password" name="password" required><br><br>
	
	<% String error = (String) request.getAttribute("errorMessage"); %>
    <% if (error != null) { %>
    	<p class="error-message"><%= error %></p>
    <% } %>
    
    <a href="requestServlet">パスワードを忘れた方</a><br><br>
	
	<input type="submit" name="submit" class="signin-btn" value="ログイン">
</form>
<hr>
<h4>アカウントをお持ちでない方</h4>
<div class="center-btn">
	<a href="signupServlet" class="signup-btn">新規登録</a>
</div>
<footer>
    <p>©2026 EBATA TAKUMI</p>
</footer>

<% String message = (String) request.getAttribute("Message"); %>
<% if (message != null) { %>
   	<script>alert("<%= message %>");</script>
<% } %>
</body>
</html>