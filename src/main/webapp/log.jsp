<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | ログ</title>
<link rel="stylesheet" href="css/log.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<jsp:include page="header.jsp" />

<section>
	<div class="notice">
	<h3>お知らせ</h3>
	<div class="notice-table">
		<table>
			<thead><tr><th>日付</th><th>内容</th></tr></thead>
			<tbody>
			<c:choose>
	            <c:when test="${empty account_list}">
	                <tr>
	                    <td>通知はありません</td>
	                </tr>
	            </c:when>
	            <c:otherwise>
					<c:forEach var="n" items="${notice}">
						<tr>
							<td>${n.created_at}</td>
							<td>${n.message}</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>	
			</tbody>
		</table>
	</div>
	</div>
</section>

<section>
	<h3 class="menu_subtitle">メニュー</h3>
	<div class="menu">	
		<a href="categoryServlet" class="btn">カテゴリ管理</a>
		<a href="projectServlet" class="btn">プロジェクト管理</a>
		<a href="memberServlet" class="btn">会費・名簿管理</a>
		<a href="logServlet" class="btn">操作ログ</a>
	</div>
</section>
<hr>

<section>
	<div class="menu_subtitle">
		<h3>ログ一覧</h3>
		<p>過去3か月分のログ情報を閲覧できます。</p>
	</div>
	<div class="log_table">
		<table>
			<thead><tr><th>日付</th><th>内容</th></tr></thead>
			<tbody>
				<c:forEach var="c" items="${log}">
					<tr>
						<td>${c.created_at}</td>
						<td>${c.log}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</section>
<br><br>
<footer>
    <p>©2026 EBATA TAKUMI</p>
</footer>
</body>
</html>