<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/budget_report_list.css">
</head>
<body>
<jsp:include page="header.jsp" />

<section>
	<h1>予算報告書</h1>
	<button class="new-budget-btn">予算報告書の作成</button>
	<table>
		<thead><th>予算報告書</th></thead>
		<tbody>
			<c:forEach var="c" items="${budget_list}">
				<tr>
					<td>
					<form method="get" action="budget_report_detailServlet">
						<input type="hidden" name="reportID" value="${c.id}">
						<input type="submit" name="submit" class="detail-btn" value="${c.name}">
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
		<h1>予算報告書の作成</h1>
		<form action="budget_report_listServlet" method="post">
		<table>
		<tr>
			<td><label for="name">書類名<span class="req">*</span></label></td>
			<td><input type="text" name="name" required></td>
		</tr>
		<tr>
			<c:forEach var="c" items="${category_list}">
		        <td><label for="${c.name}">${c.name}<span class="req">*</span></label></td>
		        <td><input type="number" name="${c.name}" required><br></td>
		</tr>
		    </c:forEach>
		  
		 </table>
			<br>
			<button type="button" class="close-btn" id="close-btn">キャンセル</button>
			<button type="submit" name="submit" class="save-btn" value="status">保存</button>
		</form>
		</div>
	</div>
</div>
<script>
document.querySelectorAll('.new-budget-btn').forEach(btn => {
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