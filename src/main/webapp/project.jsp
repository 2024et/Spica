<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | プロジェクト管理</title>
<link rel="stylesheet" href="css/project.css">
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
		<h3>プロジェクト管理</h3>
		<button type="button" class="insert-btn">プロジェクトの新規作成</button>
	</div>
	<div class="category_table">
		<table>
			<thead><tr><th>プロジェクト</th><th>状態</th><th>操作</th></tr></thead>
			<tbody>
			<c:choose>
            <c:when test="${empty account_list}">
                <tr>
                    <td>プロジェクトがありません</td>
                </tr>
            </c:when>
            <c:otherwise>
				<c:forEach var="p" items="${project}">
					<tr>
						<td>${p.name}</td>
						<td>${p.status}</td>
						<td>
							<button class="setting-btn" data-id="${p.id}">設定</button>
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
			</c:choose>
			</tbody>
		</table>
	</div>
</section>


<div id="insert-wrapper">
	<div id="insert-inside">
		<div id="message">
			<h1>プロジェクトの作成</h1>
			<form action="projectServlet" method="post">
				<label for="name">プロジェクト名<span class="req">*</span></label><br>
				<input type="text" name="name" required maxlength="30"><br><br>
				
			
				<button type="button" class="insert-close-btn">キャンセル</button>
				<button type="submit" name="submit" class="btn" value="insert">保存</button>
			</form>
		</div>
	</div>
</div>

<c:forEach var="c" items="${project}">
<div id="edit-wrapper-${c.id}" class="edit-wrapper">
	<div id="edit-inside">
		<div id="message">
			<h1>プロジェクトの編集</h1>
			<form action="projectServlet" method="post">
			<input type="hidden" name="id" value="${c.id}" />
				<label for="name">プロジェクト名</label><br>
				<input type="text" name="name" value="${c.name}" maxlength="30"><br><br>
			
				<label>
				  <input type="checkbox" name="status" 
				    <c:if test="${c.status == '休止中'}">checked</c:if>
				  />休止状態
				</label><br>
				
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