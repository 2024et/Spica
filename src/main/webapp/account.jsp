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
<section>
	<div class="notice">
	<h3>あなたへのお知らせ</h3>
	<div class="notice-table">
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
	</div>
</section>

<section>	
	<div class="info">
	<h3>アカウント情報</h3>
		<table>
			<tbody>
				<tr><td>ユーザー名：</td><td>${accountData.name}</td></tr>
				<tr><td>ユーザーID：</td><td>${accountData.id}</td></tr>
				<tr><td>メールアドレス：</td><td>${accountData.email}</td></tr>
				<tr><td>ロール権限：</td><td>${accountData.role}</td></tr>
				<tr><td>所属：</td><td>${accountData.group_name}</td></tr>
			</tbody>
		</table>
	</div>
</section>

<section>
	<div class="buttons">
		<button class="pass-change-btn" id="pass-change-btn">パスワードを変更</button>
		<button class="acc-change-btn" id="acc-change-btn">アカウント情報を変更</button>
	</div>
</section>

<% String error = (String) request.getAttribute("errorMessage"); %>
<% if (error != null) { %>
	<p class="error-message"><%= error %></p>
<% } %>

<br>
<form action="accountServlet" method="post">
	<button type="submit" name="submit" class="delete-btn" value="delete" onclick="confirmDelete();">アカウントを削除する</button>
</form>
<div id="pw-ch-wrapper">
	<div id="pw-ch-inside">
		<div id="message">
			<h1>パスワードの再設定</h1>
			<form action="accountServlet" method="post">
				<table>
					<tr>
						<td>現在のパスワード<span>*</span></td>
						<td><input type="password" name="currentPassword" required></td>
					</tr>
					<tr>
						<td>新しいパスワード<span>*</span></td>
						<td><input type="password" name="newPassword_1" required></td>
					</tr>
					<tr>
						<td>確認用パスワード<span>*</span></td>
						<td><input type="password" name="newPassword_2" required></td>
					</tr>
				</table>
				<a href="requestServlet">現在のパスワードを忘れた方</a><br>
				<button type="button" class="pass-close-btn">キャンセル</button>
				<button type="submit" name="submit" class="change-btn" value="pass">保存</button>
			</form>
		</div>
	</div>
</div>


<div id="ac-ch-wrapper">
	<div id="ac-ch-inside">
		<div id="message">
			<h1>アカウント情報変更</h1>
			<form action="accountServlet" method="post">
							<table>
					<tr>
						<td>ユーザー名</td>
						<td><input type="text" name="name"></td>
					</tr>
					<tr>
						<td>メールアドレス</td>
						<td><input type="email" name="email"></td>
					</tr>
					<tr>
						<td>所属団体</td>
						<td><input type="text" name="code"></td>
					</tr>
				</table>
				<p>※ユーザーIDの変更はできません。</p>
				<p>※所属団体は1アカウントにつき1団体しか入れません。</p>
				<button type="button" class="acc-close-btn">キャンセル</button>
				<button type="submit" name="submit" class="change-btn" value="acc">保存</button>
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

function confirmDelete(){
 return alert("本当に削除しますか？");
}

</script>

<footer>
    <p>©2026 EBATA TAKUMI</p>
</footer>
</body>
</html>