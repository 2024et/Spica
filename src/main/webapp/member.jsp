<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | 会費・名簿管理</title>
<link rel="stylesheet" href="css/member.css">
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
		<h3>会費管理</h3>
		<button type="button" class="insert-btn">会費設定</button>
	</div>
	<div class="fee_management">
		<h4>現在設定中の会費</h4>
		<p>${membership_fee.fee}円　期間：${membership_fee.start_date} ～ ${membership_fee.end_date}</p>
	</div>
</section>

<section>
	<div class="section_menu">
		<h3>支払い状況の管理</h3>
		<select id="yearSelect" class="yearSelect" onchange="filterDocuments(this.value)">
		</select>
	</div>
	<div id="submitedList"></div>
</section>

<section>
	<div class="menu_subtitle">
		<h3>名簿</h3>
	</div>
	<div class="member_table">
		<table>
			<thead><tr><th>会員名</th><th>メールアドレス</th><th>ロール</th><th>操作</th></tr></thead>
			<tbody>
				<c:forEach var="c" items="${member}">
					<tr>
						<td>${c.name}</td>
						<td>${c.email}</td>
						<td>${c.role}</td>
						<td>
							<button class="member-setting-btn" data-id="${c.id}">設定</button>
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
			<h1>会費の設定</h1>
			<form action="memberServlet" method="post">
				<label for="fee">金額<span class="req">*</span></label><br>
				<input type="number" name="fee" required><br><br>
				
				<label for="start">期間(開始日)<span class="req">*</span></label><br>
				<input type="date" name="start" required><br><br>
				
				<label for="end">期間(締切日)<span class="req">*</span></label><br>
				<input type="date" name="end" required><br><br>
			
				<button type="button" class="insert-close-btn">キャンセル</button>
				<button type="submit" name="submit" class="btn" value="insert">保存</button>
			</form>
		</div>
	</div>
</div>

<c:forEach var="c" items="${payment}">
	<div id="approvel-wrapper-${c.id}" class="approvel-wrapper">
		<div id="approvel-inside">
			<div id="message">
				<h1>支払い状況の管理</h1>
				<form action="memberServlet" method="post">
				<input type="hidden" name="document_id" value="${c.id}" />
					<p>ユーザー名：「${c.name}」さんの支払い状況...</p>
					<label>
					  <input type="radio" name="answer" value="OK" onclick="toggleReason(this)"> 支払い済み
					</label>
					
					<label>
					  <input type="radio" name="answer" value="NG" onclick="toggleReason(this)"> 未払い
					</label><br><br>
				
					<button type="button" class="approvel-close-btn" data-id="${c.id}">キャンセル</button>
					<button type="submit" name="submit" class="btn" value="approver">保存</button>
				</form>
			</div>
		</div>
	</div>
</c:forEach>

<c:forEach var="c" items="${member}">
	<div id="approvel-wrapper-${c.id}" class="approvel-wrapper">
		<div id="approvel-inside">
			<div id="message">
				<h1>会員の設定</h1>
				<form action="memberServlet" method="post">
				<input type="hidden" name="document_id" value="${c.id}" />
				<p>ロール</p>
					<label>
					  <input type="radio" name="role" value="一般" onclick="toggleReason(this)"> 一般
					</label>
					
					<label>
					  <input type="radio" name="role" value="その他役員" onclick="toggleReason(this)"> その他役員
					</label>
					
					<label>
					  <input type="radio" name="role" value="会計" onclick="toggleReason(this)"> 会計
					</label>
					
					<label>
					  <input type="radio" name="role" value="副代表" onclick="toggleReason(this)"> 副代表
					</label>
					
					<label>
					  <input type="radio" name="role" value="代表" onclick="toggleReason(this)"> 代表
					</label><br><br>
				
					<button type="submit" name="submit" class="delete-btn" onclick="confirmDelete();" value="delete">削除</button>
					<button type="button" class="approvel-close-btn" data-id="${c.id}">キャンセル</button>
					<button type="submit" name="submit" class="btn" value="approver">保存</button>
				</form>
			</div>
		</div>
	</div>
</c:forEach>













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