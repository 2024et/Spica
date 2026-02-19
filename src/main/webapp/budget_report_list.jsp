<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp" />

<section>
	<h1>予算報告書</h1>
	<button onclick="location.href='purchase_request_formServlet'" class="new-request-btn">予算報告書の作成</button>
	<table>
		<thead><th>予算報告書</th></thead>
		<tbody>
			<c:forEach var="c" items="${budget_report}">
				<tr>
					<td>
					<form method="get" action="budget_report_detailServlet">
						<input type="hidden" name="reportID" value="${c.id}">
						<input type="submit" name="submit" class="detail-btn" value="${c.name}">
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