<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | パスワードの再設定</title>
<link rel="stylesheet" href="css/change_password.css">
</head>
<body>
<%
String id = (String)request.getAttribute("id");
%>
<h1>Spica</h1>
<h3>学生団体向け会計管理Webアプリ</h3>

<h4>パスワードの再設定</h4>
<p>新しいパスワードを入力してください。</p>
<form action="change_passwordServlet" method="post" >
	
	<label for="passwprd_1">パスワード<span class="req">*</span></label><br>
	<input type="password" name="password_1" required><br><br>
	
	<label for="passwprd_2">確認用パスワード<span class="req">*</span></label><br>
	<input type="password" name="password_2" required><br><br>
	
	<input type="hidden" name="id" value="<%= id %>">
	
	
	<% String error = (String) request.getAttribute("errorMessage"); %>
    <% if (error != null) { %>
    	<p class="error-message"><%= error %></p>
    <% } %>
	
	<input type="submit" name="submit" class="update-btn" value="　更新　">
</form>
<footer>
    <p>©2026 EBATA TAKUMI</p>
</footer>
</body>
</html>