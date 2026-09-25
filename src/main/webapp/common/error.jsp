<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>エラー</title>
</head>
<body>
<% String message = (String) request.getAttribute("errorMessage"); %>
<% if (message != null) { %>
   	<script>alert("<%= message %>");</script>
<% } %>
</body>
</html>