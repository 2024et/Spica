<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/account_report_list.css">
</head>
<body>
<jsp:include page="header.jsp" />

<section>
	<h1>会計報告書</h1>
	<button class="new-account-btn">会計報告書の作成</button>
	<table>
		<thead><tr><th>会計報告書</th></tr></thead>
		<tbody>
			<c:forEach var="acc_repo" items="${account_list}">
				<tr>
					<td>
					<form method="get" action="account_report_listServlet">
						<input type="hidden" name="reportID" value="${acc_repo.id}">
						<input type="submit" name="submit" class="detail-btn" value="${acc_repo.name}">
					</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</section>
<div id="wrapper">
	<div id="inside">
		<div id="message">
		<h1>会計報告書の作成</h1>
		<form action="account_report_listServlet" method="post">
		<table>
		<tr>
			<td><label for="name">書類名<span class="req">*</span></label></td>
			<td><input type="text" name="name" required></td>
		</tr>
		<tr>
			<td><label for="start">対象期間：開始日<span class="req">*</span></label></td>
			<td><input type="date" name="start" required></td>
		</tr>
		<tr>
			<td><label for="end">対象期間：終了日<span class="req">*</span></label></td>
			<td><input type="date" name="end" required></td>
		</tr>
		
		<tr>
			<td><label for="start">対象予算<span class="req">*</span></label></td>
			<td>
				<select  class="textbox" name="budget" required>
					<option value="">==未選択==</option>
					<c:forEach var="list" items="${budget_list}">
						<option value="${list.id}">${list.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		  
		 </table>
			<br>
			<button type="button" class="close-btn" id="close-btn">キャンセル</button>
			<button type="submit" name="submit" class="save-btn" value="status">保存</button>
		</form>
		</div>
	</div>
</div>
<script>
document.querySelectorAll('.new-account-btn').forEach(btn => {
	btn.addEventListener('click', (e) => {
	e.stopPropagation(); 
	const id = btn.dataset.id;
	
	const wrapper = document.getElementById("wrapper");
	if(wrapper){
		wrapper.style.display = "block";
	}
	});
});

document.querySelectorAll('.close-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
    e.stopPropagation();

    const wrapper = document.getElementById("wrapper");
    if(wrapper){
        wrapper.style.display = "none";
    }
    });
});
document.getElementById('wrapper')?.addEventListener('click', (e) => {
    if(e.target.id === 'wrapper'){
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