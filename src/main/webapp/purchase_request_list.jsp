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
	<button onclick="" css="new_request_btn">新しい希望申請を出す</button>
	<table>
		<thead><th>申請者名</th><th>申請品目</th><th>ステータス</th><th>詳細</th></thead>
		<tbody>
			<c:forEach var="c" items="${request_list}">
				<tr>
					<td>${c.user_namet}</td>
					<td>${c.name}</td>
					<td>${c.status}</td>
					<td>
					<form method="post" action="purchase_request_detailServlet">
						<input type="hidden" name="requestID" value="${c.Id}">
						<input type="submit" name="submit" value="詳細">
					</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</section>



<footer>
    <p>©2026 EBATA TAKUMI</p>
</footer>
</body>
</html>