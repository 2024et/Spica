<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | ログイン</title>
<link rel="stylesheet" href="css/signin.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<h1>Spica</h1>
<h3>学生団体向け会計管理Webアプリ</h3>

<h4>ログイン</h4>

<form action="signinServlet" method="post" >
	
	<label for="mail">メールアドレス<span class="req">*</span></label><br>
	<input type="email" name = "mail" required maxlength="30"><br><br>
	
	<label for="passwprd_1">パスワード<span class="req">*</span></label><br>
	<div class="password-wrapper">
		<input type="password" name="password" id="password" required maxlength="30">
		<span class="toggle-btn" onclick="togglePassword()">🔓</span>
	</div>
	<br><br>
	
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
<script>

document.querySelectorAll(".toggle-btn").forEach(btn => {
  btn.addEventListener("click", () => {
    const input = btn.previousElementSibling;
    input.type = input.type === "password" ? "text" : "password";
  });
});
</script>

<footer>
    <p>©2026 EBATA TAKUMI</p>
</footer>

<% String message = (String) request.getAttribute("Message"); %>
<% if (message != null) { %>
   	<script>alert("<%= message %>");</script>
<% } %>
</body>
</html>