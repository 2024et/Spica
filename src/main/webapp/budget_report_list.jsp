<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | 予算計画書一覧</title>
<link rel="stylesheet" href="css/budget_report_list.css">
<link rel="stylesheet" href="css/agent.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="images/spica.ico">
</head>
<body>
<jsp:include page="header.jsp" />

<section>
	<h1>予算報告書</h1>
	<c:choose>
	    <c:when test="${accountData.role == '会計' || accountData.role == '顧問'}">
			<button class="new-budget-btn">予算計画書の作成</button>
	    </c:when>
	</c:choose>
	
	<table>
		<thead><tr><th>予算計画書</th></tr></thead>
		<tbody>
		<c:choose>
            <c:when test="${empty budget_list}">
                <tr>
                    <td>予算計画書は作られていません。</td>
                </tr>
            </c:when>
            <c:otherwise>
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
			</c:otherwise>
		</c:choose>
		</tbody>
	</table>
</section>
<div id="wrapper">
	<div id="inside">
		<div id="message">
		<h1>予算計画書の作成</h1>
		<form action="budget_report_listServlet" method="post">
		<table>
		<tr>
			<td><label for="name">書類名<span class="req">*</span></label></td>
			<td><input type="text" name="name" required maxlength="100"></td>
		</tr>
		<tr>
			<c:forEach var="c" items="${category_list}">
		        <td><label for="${c.name}">${c.name}<span class="req">*</span></label></td>
		        <td><input type="number" name="${c.name}" required min="0" max="9999999"><br></td>
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
<script src="js/agent.js"></script>
<script
 src="https://udify.app/embed.min.js"
 id="bhUdFy45eInwe4F2"
 defer>
</script>
<footer>
    <p>©2026 EBATA TAKUMI</p>
</footer>
</body>
</html>