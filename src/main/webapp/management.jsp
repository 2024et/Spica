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
						<button class="approvel-btn" data-id="${d.id}">
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
						<button class="approvel-btn" data-id="${d.id}">
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
						<button class="approvel-btn" data-id="${d.id}">
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
						<button class="approvel-btn" data-id="${d.id}">
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
			<button type="button" class="edit-btn" data-id="${d.id}">設定</button>
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



<c:forEach var="c" items="${process_documents}">
	<div id="approvel-wrapper-${c.id}" class="approvel-wrapper">
		<div id="approvel-inside">
			<div id="message">
				<h1>承認設定</h1>
				<form action="managementServlet" method="post">
				<input type="hidden" name="document_id" value="${c.id}" />
					<p>「${c.name}」に承認しますか？</p>
					<label>
					  <input type="radio" name="answer" value="OK" onclick="toggleReason(this)"> はい
					</label>
					
					<label>
					  <input type="radio" name="answer" value="NG" onclick="toggleReason(this)"> いいえ
					</label><br><br>
					
					<div class="reasonBox" style="display:none;">
					  <p>不承認理由を記載してください。</p>
					  <textarea name="comment" rows="4" cols="40" maxlength="300"></textarea>
					</div>
				
					<button type="button" class="approvel-close-btn" data-id="${c.id}">キャンセル</button>
					<button type="submit" name="submit" class="btn" value="approver">保存</button>
				</form>
			</div>
		</div>
	</div>
</c:forEach>
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

<c:forEach var="c" items="${process_documents}">
<div id="edit-wrapper-${c.id}" class="edit-wrapper">
	<div id="edit-inside">
		<div id="message">
			<h1>書類の編集</h1>
			<form action="managementServlet" method="post" enctype="multipart/form-data">
			<input type="hidden" name="document_id" value="${c.id}" />
				<label for="name">書類名</label><br>
				<input type="text" name="name" value="${c.name}"><br><br>
				
				<label for="file">pdfのアップロード</label><br>
				<input type="file" name="file"><br><br>
			
				<label><input type="checkbox" name="approver_reset"/>承認状況をリセットする。</label><br>
				
				<button type="button" class="delete-btn" >削除</button>
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

document.querySelectorAll('.edit-btn').forEach(btn => {
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

document.querySelectorAll('.approvel-btn').forEach(btn => {
	btn.addEventListener('click', (e) => {
	e.stopPropagation(); 
	const id = btn.dataset.id;
	
	const wrapper = document.getElementById("approvel-wrapper-"+id);
	if(wrapper){
		wrapper.style.display = "block";
	}
	});
});

document.querySelectorAll('.approvel-close-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
    e.stopPropagation();
    const id = btn.dataset.id;
    const wrapper = document.getElementById("approvel-wrapper-"+id);
    if(wrapper){
        wrapper.style.display = "none";
    }
    });
});

function toggleReason(radio){
	  const form = radio.closest("form");
	  const box = form.querySelector(".reasonBox");

	  if(radio.value === "no"){
	    box.style.display = "block";
	  }else{
	    box.style.display = "none";
	  }
	}
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