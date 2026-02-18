<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | 備品購入希望一覧</title>
<link rel="stylesheet" href="css/purchase_request_list.css">
</head>
<body>
<jsp:include page="header.jsp" />

<section>
	<h1>備品購入希望状況</h1>
	<button onclick="location.href='purchase_request_formServlet'" class="new-request-btn">新しい希望申請を出す</button>
	<table>
		<thead><th>申請者名</th><th>申請品目</th><th>ステータス</th><th>詳細</th></thead>
		<tbody>
			<c:forEach var="c" items="${request_list}">
				<tr>
					<td>${c.user_name}</td>
					<td>${c.name}</td>
					<td>${c.status}</td>
					<td>
					<form method="get" action="purchase_request_detailServlet">
						<input type="hidden" name="requestID" value="${c.id}">
						<input type="submit" name="submit" class="detail-btn" value="詳細">
					</form>
					</td>
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
</html>