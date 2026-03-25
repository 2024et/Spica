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
	<input type="text" name = "name" required maxlength="30"><br><br>
	
	<label for="mail">メールアドレス<span class="req">*</span></label><br>
	<input type="email" name = "mail" required maxlength="30"><br><br>
	
	<label for="passwprd_1">パスワード<span class="req">*</span></label><br>
	<div class="password-wrapper">
		<input type="password" name="password_1" id="password" required maxlength="30">
		<span class="toggle-btn" onclick="togglePassword()">🔓</span>
	</div><br><br>
	
	<label for="passwprd_2">確認用パスワード<span class="req">*</span></label><br>
		<div class="password-wrapper">
		<input type="password" name="password_2" id="password" required maxlength="30">
		<span class="toggle-btn" onclick="togglePassword()">🔓</span>
	</div><br><br>
	
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