<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | パスワードの再設定</title>
<link rel="stylesheet" href="css/request.css">
</head>
<body>
<h1>Spica</h1>
<h3>学生団体向け会計管理Webアプリ</h3>

<h4>パスワードの再設定</h4>
<p>登録されているメールアドレスにパスワード再設定用のURLを送信します。</p>
<form action="requestServlet" method="post" >
	
	<label for="mail">メールアドレス<span class="req">*</span></label><br>
	<input type="email" name = "mail" required><br><br>
	
	
	<% String error = (String) request.getAttribute("errorMessage"); %>
    <% if (error != null) { %>
    	<p class="error-message"><%= error %></p>
    <% } %>
	
	<input type="submit" name="submit" class="send-btn" value="　送信　">
</form>
<hr>
<h4>パスワードが分かる方</h4>
<div class="center-btn">
	<a href="signinServlet" class="signin-btn">ログイン</a>
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