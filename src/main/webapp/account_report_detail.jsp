<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | 会計報告書詳細</title>
<link rel="stylesheet" href="css/account_report_detail.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<jsp:include page="header.jsp" />

<section>
	<h1>${reports.name}</h1>
	<p>${reports.start_period}～${reports.end_period}</p>
	<h3>(収入の部)</h3>
	<table>
		<thead><tr><th>項目</th><th>予算額</th><th>決算額</th><th>比較増減</th></tr></thead>
		<tbody>
			<c:forEach var="c" items="${report_income}">
				<tr>
					<td>${c.name}</td>
					<td>${c.budget}</td>
					<td>${c.amount}</td>
					<td>${c.fluctuation}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<h3>(支出の部)</h3>
	<table>
		<thead><tr><th>項目</th><th>予算額</th><th>決算額</th><th>比較増減</th></tr></thead>
		<tbody>
			<c:forEach var="c" items="${report_expend}">
				<tr>
					<td>${c.name}</td>
					<td>${c.budget}</td>
					<td>${c.amount}</td>
					<td>${c.fluctuation}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<p>上記の通り、報告いたします。</p>
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
</html>