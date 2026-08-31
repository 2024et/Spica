<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | パスワードの再設定</title>
<link rel="stylesheet" href="css/change_password.css">
<link rel="stylesheet" href="css/agent.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="images/spica.ico">
</head>
<body>
<h1>Spica</h1>
<h3>学生団体向け会計管理Webアプリ</h3>

<h4>パスワードの再設定</h4>
<p>新しいパスワードを入力してください。</p>
<form action="change_passwordServlet" method="post" >
	
	<label for="passwprd_1">パスワード<span class="req">*</span>（英数字8文字以上30文字以下）</label><br>
	<div class="password-wrapper">
		<input type="password" name="password_1" id="password" required minlength="8" maxlength="30">
		<span class="toggle-btn" onclick="togglePassword()">🔓</span>
	</div><br><br>
	
	<label for="passwprd_2">確認用パスワード<span class="req">*</span></label><br>
	<div class="password-wrapper">
		<input type="password" name="password_2" id="password" required minlength="8" maxlength="30">
		<span class="toggle-btn" onclick="togglePassword()">🔓</span>
	</div><br><br>
	
	<input type="hidden" name="id" value="${id}">
	
	
	<% String error = (String) request.getAttribute("errorMessage"); %>
    <% if (error != null) { %>
    	<p class="error-message"><%= error %></p>
    <% } %>
	
	<input type="submit" name="submit" class="update-btn" value="　更新　">
</form>
<script>

document.querySelectorAll(".toggle-btn").forEach(btn => {
  btn.addEventListener("click", () => {
    const input = btn.previousElementSibling;
    input.type = input.type === "password" ? "text" : "password";
  });
});
</script>
<script src="js/agent.js"></script>
<script
 src="https://udify.app/embed.min.js"
 id="bhUdFy45eInwe4F2"
 defer>
</script>
<footer>
    <p>©2026 EBATA TAKUMI</p>
</footer>
</body>
</html>