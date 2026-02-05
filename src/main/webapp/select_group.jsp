<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | 団体選択</title>
<link rel="stylesheet" href="css/select_group.css">
</head>
<body>
<h1>Spica</h1>
<h3>学生団体向け会計管理Webアプリ</h3>

<h4>団体選択</h4>

<h5>既存団体に参加する方</h5>
<form action="select_groupServlet" method="post" >
	
	<label for="invite">招待コード<span class="req">*</span></label><br>
	<input type="text" name = "invite" required><br><br>
	
	<% String join_error = (String) request.getAttribute("join_errorMessage"); %>
    <% if (join_error != null) { %>
    	<p class="error-message"><%= join_error %></p>
    <% } %>
	
	<button type="submit" name="submit" class="btn" value="join">参加</button>
</form>
<hr>
<h5>団体を新規作成する方</h5>
<form action="select_groupServlet" method="post" >
	
	<label for="name">団体名(記号は利用できません)<span class="req">*</span></label><br>
	<input type="text" name = "name" required><br><br>
	
	<% String make_error = (String) request.getAttribute("make_errorMessage"); %>
    <% if (make_error != null) { %>
    	<p class="error-message"><%= make_error %></p>
    <% } %>
	
	<button type="submit" name="submit" class="btn" value="make">新規作成</button>
</form>
<footer>
    <p>©2026 EBATA TAKUMI</p>
</footer>
</body>
</html>