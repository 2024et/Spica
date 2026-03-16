<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | カテゴリ管理</title>
<link rel="stylesheet" href="css/category.css">
</head>
<body>
<jsp:include page="header.jsp" />

<section>
	<div class="notice">
	<h3>お知らせ</h3>
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
		<h3>カテゴリ管理</h3>
		<button type="button" class="insert-btn">カテゴリの新規作成</button>
	</div>
	<div class="category_table">
		<table>
			<thead><tr><th>カテゴリ</th><th>入出金</th><th>状態</th><th>操作</th></tr></thead>
			<tbody>
				<c:forEach var="c" items="${category}">
					<tr>
						<td>${c.name}</td>
						<td>${c.type}</td>
						<td>${c.status}</td>
						<td>
							<button class="setting-btn" data-id="${d.id}">設定</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</section>

<div id="insert-wrapper">
	<div id="insert-inside">
		<div id="message">
			<h1>カテゴリの作成</h1>
			<form action="categoryServlet" method="post">
				<label for="name">カテゴリ名<span class="req">*</span></label><br>
				<input type="text" name="name" required><br><br>
				
				<label>
				  <input type="radio" name="type" value="収入"> 収入
				</label>
				
				<label>
				  <input type="radio" name="type" value="支出"> 支出
				</label><br><br>
			
				<button type="button" class="insert-close-btn">キャンセル</button>
				<button type="submit" name="submit" class="btn" value="insert">保存</button>
			</form>
		</div>
	</div>
</div>

<c:forEach var="c" items="${category}">
<div id="edit-wrapper-${c.id}" class="edit-wrapper">
	<div id="edit-inside">
		<div id="message">
			<h1>カテゴリの編集</h1>
			<form action="categoryServlet" method="post">
			<input type="hidden" name="document_id" value="${c.id}" />
				<label for="name">カテゴリ名</label><br>
				<input type="text" name="name" value="${c.name}"><br><br>

				<label>
				  <input type="radio" name="type" value="収入"> 収入
				</label>
				
				<label>
				  <input type="radio" name="type" value="支出"> 支出
				</label><br><br>
			
			
				<label><input type="checkbox" name="approver_reset"/>休止状態</label><br>
				
				<button type="button" class="edit-close-btn" data-id="${c.id}">キャンセル</button>
				<button type="submit" name="submit" class="btn" value="edit">保存</button>
			</form>
		</div>
	</div>
</div>
</c:forEach>

<script>
document.querySelectorAll('.insert-btn').forEach(btn => {
	btn.addEventListener('click', (e) => {
	e.stopPropagation(); 
	const id = btn.dataset.id;
	
	const wrapper = document.getElementById("insert-wrapper");
	if(wrapper){
		wrapper.style.display = "block";
	}
	});
});

document.querySelectorAll('.insert-close-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
    e.stopPropagation();

    const wrapper = document.getElementById("insert-wrapper");
    if(wrapper){
        wrapper.style.display = "none";
    }
    });
});
document.getElementById('insert-wrapper')?.addEventListener('click', (e) => {
    if(e.target.id === 'insert-wrapper'){
        e.target.style.display = 'none';
    }
});

document.querySelectorAll('.setting-btn').forEach(btn => {
	btn.addEventListener('click', (e) => {
	e.stopPropagation(); 
	const id = btn.dataset.id;
	
	const wrapper = document.getElementById("edit-wrapper-"+id);
	if(wrapper){
		wrapper.style.display = "block";
	}
	});
});

document.querySelectorAll('.edit-close-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
    e.stopPropagation();
    const id = btn.dataset.id;
    const wrapper = document.getElementById("edit-wrapper-"+id);
    if(wrapper){
        wrapper.style.display = "none";
    }
    });
});


</script>

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