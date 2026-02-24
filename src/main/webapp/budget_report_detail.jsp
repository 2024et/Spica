<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | 予算報告書詳細</title>
<link rel="stylesheet" href="css/budget_report_detail.css">
</head>
<body>
<jsp:include page="header.jsp" />

<section>
	<h1>${title}</h1>
	
	<table>
		<thead><tr><th>項目</th><th>予算額</th></tr></thead>
		<tbody>
			<c:forEach var="c" items="${budget}">
				<tr>
					<td>${c.category_name}</td>
					<td>${c.amount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</section>

<br><br>
<% String error = (String) request.getAttribute("errorMessage"); %>
<% if (error != null) { %>
	<p class="error-message"><%= error %></p>
<% } %>

<footer>
    <p>©2026 EBATA TAKUMI</p>
</footer>
</body>
</body>
</html>