<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | アカウント</title>
<link rel="stylesheet" href="css/account.css">
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

<div id="pw-ch-wrapper">
	<div id="pw-ch-inside">
		<div id="message">
			<h1>パスワード変更</h1>
			<form action="accountServlet" method="post">
				<button type="button" class="pass-close-btn">キャンセル</button>
			</form>
		</div>
	</div>
</div>


<div id="ac-ch-wrapper">
	<div id="ac-ch-inside">
		<div id="message">
			<h1>アカウント情報変更</h1>
			<form action="accountServlet" method="post">
				<button type="button" class="acc-close-btn">キャンセル</button>
			</form>
		</div>
	</div>
</div>

<script>

//パスワード変更

document.querySelectorAll('.pass-change-btn').forEach(btn => {
	btn.addEventListener('click', (e) => {
	e.stopPropagation(); 
	const id = btn.dataset.id;
	
	const wrapper = document.getElementById("pw-ch-wrapper");
	if(wrapper){
		wrapper.style.display = "block";
	}
	});
});

document.querySelectorAll('.pass-close-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
    e.stopPropagation();

    const wrapper = document.getElementById("pw-ch-wrapper");
    if(wrapper){
        wrapper.style.display = "none";
    }
    });
});
document.getElementById('pw-ch-wrapper')?.addEventListener('click', (e) => {
    if(e.target.id === 'pw-ch-wrapper'){
        e.target.style.display = 'none';
    }
});

//アカウント情報変更
document.querySelectorAll('.acc-change-btn').forEach(btn => {
	btn.addEventListener('click', (e) => {
	e.stopPropagation(); 
	const id = btn.dataset.id;
	
	const wrapper = document.getElementById("ac-ch-wrapper");
	if(wrapper){
		wrapper.style.display = "block";
	}
	});
});

document.querySelectorAll('.acc-close-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
    e.stopPropagation();

    const wrapper = document.getElementById("ac-ch-wrapper");
    if(wrapper){
        wrapper.style.display = "none";
    }
    });
});
document.getElementById('ac-ch-wrapper')?.addEventListener('click', (e) => {
    if(e.target.id === 'ac-ch-wrapper'){
        e.target.style.display = 'none';
    }
});



</script>

</body>
</html>