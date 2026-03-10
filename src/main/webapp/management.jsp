<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | 役員ページ</title>
<link rel="stylesheet" href="css/management.css">
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
	<div class="menu">
	<h3>メニュー</h3>
		<a href="categoryServlet" class="btn">カテゴリ管理</a>
		<a href="projectServlet" class="btn">プロジェクト管理</a>
		<a href="memberServlet" class="btn">会費・名簿管理</a>
		<a href="logServlet" class="btn">操作ログ</a>
	</div>
</section>

<section>
	<h3>進行中の書類</h3>
	<button type="button" class="insert-btn">新規書類の作成</button>
	<div class="process">
	<c:forEach var="d" items="${process_documents}">
		<div class="item">
			<a href="">${d.name}</a>
			<div class="info">
				<div class="left">
					<div class="item-btn">
						<button class="approval-btn">
							<c:choose>
							    <c:when test="${d.accountant == 'OK'}">
									〇
							    </c:when>
							    <c:when test="${d.accountant == 'NG'}">
									×
							    </c:when>
							    <c:when test="${d.accountant == ''}">
									
							    </c:when>
							</c:choose>
						</button>
						<p>会計</p>
					</div>
					<div class="item-btn">
						<button class="approval-btn">
							<c:choose>
							    <c:when test="${d.vice_president == 'OK'}">
									〇
							    </c:when>
							    <c:when test="${d.vice_president == 'NG'}">
									×
							    </c:when>
							    <c:when test="${d.vice_president == ''}">
									
							    </c:when>
							</c:choose>
						</button>
						<p>副代表</p>
					</div>
					<div class="item-btn">
						<button class="approval-btn">
							<c:choose>
							    <c:when test="${d.president == 'OK'}">
									〇
							    </c:when>
							    <c:when test="${d.president == 'NG'}">
									×
							    </c:when>
							    <c:when test="${d.president == ''}">
									
							    </c:when>
							</c:choose>
						</button>
						<p>代表</p>
					</div>
					<div class="item-btn">
						<button class="approval-btn">
							<c:choose>
							    <c:when test="${d.advisor == 'OK'}">
									〇
							    </c:when>
							    <c:when test="${d.advisor == 'NG'}">
									×
							    </c:when>
							    <c:when test="${d.advisor == ''}">
									
							    </c:when>
							</c:choose>
						</button>
						<p>顧問</p>
					</div>
				</div>
	
				<div class="right">
					<p>${d.comment}</p>
				</div>
			</div>
			<button type="button" class="edit-btn">設定</button>
		</div>
	</c:forEach>
	</div>
</section>

<section>
	<h3>提出済みの書類</h3>
	<div class="submited">
		<div class="item">
		<a href="">書類名</a>
		<a href="" class="btn">未提出</a>
		</div>
	</div>
</section>




<div id="approval-wrapper">
	<div id="approval-inside">
		<div id="message">
			<h1>承認設定</h1>
			<form action="managementServlet" method="post">
				<p>に承認しますか？</p>
			
			
				<button type="button" class="approval-close-btn">キャンセル</button>
				<button type="submit" name="submit" class="btn" value="">保存</button>
			</form>
		</div>
	</div>
</div>

<div id="insert-wrapper">
	<div id="insert-inside">
		<div id="message">
			<h1>書類作成</h1>
			<form action="managementServlet" method="post" enctype="multipart/form-data">
				<label for="name">書類名<span class="req">*</span></label><br>
				<input type="text" name="name" required><br><br>
				
				<label for="file">pdfのアップロード<span class="req">*</span></label><br>
				<input type="file" name="file" required accept=".pdf"><br><br>
			
				<button type="button" class="insert-close-btn">キャンセル</button>
				<button type="submit" name="submit" class="btn" value="insert">保存</button>
			</form>
		</div>
	</div>
</div>


<div id="edit-wrapper">
	<div id="edit-inside">
		<div id="message">
			<h1>書類の編集</h1>
			<form action="managementServlet" method="post" enctype="multipart/form-data">
				<label for="name">書類名</label><br>
				<input type="text" name="name"><br><br>
				
				<label for="file">pdfのアップロード</label><br>
				<input type="file" name="file"><br><br>
			
			
				<button type="button" class="delete-btn">削除	</button>
				<button type="button" class="edit-close-btn">キャンセル</button>
				<button type="submit" name="submit" class="btn" value="">保存</button>
			</form>
		</div>
	</div>
</div>


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

document.querySelectorAll('.edit-btn').forEach(btn => {
	btn.addEventListener('click', (e) => {
	e.stopPropagation(); 
	const id = btn.dataset.id;
	
	const wrapper = document.getElementById("edit-wrapper");
	if(wrapper){
		wrapper.style.display = "block";
	}
	});
});

document.querySelectorAll('.edit-close-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
    e.stopPropagation();

    const wrapper = document.getElementById("edit-wrapper");
    if(wrapper){
        wrapper.style.display = "none";
    }
    });
});
document.getElementById('edit-wrapper')?.addEventListener('click', (e) => {
    if(e.target.id === 'edit-wrapper'){
        e.target.style.display = 'none';
    }
});

document.querySelectorAll('.approval-btn').forEach(btn => {
	btn.addEventListener('click', (e) => {
	e.stopPropagation(); 
	const id = btn.dataset.id;
	
	const wrapper = document.getElementById("approval-wrapper");
	if(wrapper){
		wrapper.style.display = "block";
	}
	});
});

document.querySelectorAll('.approval-close-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
    e.stopPropagation();

    const wrapper = document.getElementById("approval-wrapper");
    if(wrapper){
        wrapper.style.display = "none";
    }
    });
});
document.getElementById('approval-wrapper')?.addEventListener('click', (e) => {
    if(e.target.id === 'approval-wrapper'){
        e.target.style.display = 'none';
    }
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