<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | 備品購入希望申請</title>
<link rel="stylesheet" href="css/purchase_request_form.css">
</head>
<body>
<jsp:include page="header.jsp" />
<section>
	<h1>購入希望申請フォーム</h1>
	<form action="purchase_request_formServlet" method="post">
		<table>
			<tr><td>購入希望日<span class="req">*</span></td>
				<td>
				  <div class="option-date">
				    <label>
				      <input type="radio" name="option" value="あり" id="opt_ari" checked> あり
				    </label>
				
				    <div class="date-wrapper" id="date_wrapper">
				      <input type="date" id="date_input" name="selected_date">
				      <span class="date-icon">📅</span>
				    </div>
				
				    <label>
				      <input type="radio" name="option" value="なし" id="opt_nashi"> なし
				    </label>
				  </div>
			    </td>
			</tr>
			<tr><td>店名<span class="req">*</span></td><td><input type="text" name="name" required></td></tr>
			<tr><td>品目<span class="req">*</span></td><td><input type="text" name="item" required></td></tr>
			<tr><td>目的<span class="req">*</span></td><td><textarea name="purpose" maxlength="200" required></textarea></td></tr>
			<tr><td>金額<span class="req">*</span></td><td><input type="number" name="amount" required></td></tr>
			<tr><td>販売元URL</td><td><input type="text" name="link" maxlength="200"></td></tr>
		</table>
		<input type="submit" name="submit" class="send-btn" value="送信">
		<button onclick="location.href='purchase_request_listServlet'" class="back-btn">戻る</button>
	</form>
</section>
<% String error = (String) request.getAttribute("errorMessage"); %>
<% if (error != null) { %>
	<p class="error-message"><%= error %></p>
<% } %>
<br><br>


<script>
const radios = document.querySelectorAll('input[name="option"]');
const dateWrapper = document.getElementById('date_wrapper');
const dateInput = document.getElementById('date_input');

radios.forEach(radio => {
  radio.addEventListener('change', () => {
    if (radio.value === 'あり') {
      dateWrapper.classList.remove('disabled');
      dateInput.removeAttribute('disabled');
    } else {
      dateWrapper.classList.add('disabled');
      dateInput.setAttribute('disabled', 'disabled');
      dateInput.value = ''; 
    }
  });
});
</script>

<footer>
    <p>©2026 EBATA TAKUMI</p>
</footer>
</body>
</html>