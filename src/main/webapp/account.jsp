<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | アカウント</title>
<link rel=stylesheet" href="style/account.css">
</head>
<body>
<jsp:include page="header.jsp" />
<div class="notice">
	<h3>あなたへのお知らせ</h3>
	<table>
		<thead><th>日付</th><th>内容</th></thead>
		<tbody>
			<c:forEach var="n" items="${notice}">
				<tr>
					<td>${n.created_at}</td>
					<td>${n.message}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div class="info">
	<h3>アカウント情報</h3>
	<table>
		<tbody>
			<tr><td>ユーザー名：${accountData.name}</td><td></td></tr>
			<tr><td>ユーザーID：${accountData.id}</td><td></td></tr>
			<tr><td>メールアドレス：${accountData.email}</td><td></td></tr>
			<tr><td>ロール権限：${accountData.role}</td><td></td></tr>
			<tr><td>所属：${accountData.group_name}</td><td></td></tr>
		</tbody>
	</table>
</div>

<button class="pass-change-btn" id="pass-change-btn">パスワードを変更</button>
<button class="acc-change-btn" id="acc-change-btn">アカウント情報を変更</button>

<button class="delete-btn" id="delete-btn">アカウントを削除する</button>

</body>
</html>