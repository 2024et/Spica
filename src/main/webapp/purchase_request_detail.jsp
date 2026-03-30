<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | 備品購入希望詳細</title>
<link rel="stylesheet" href="css/purchase_request_detail.css">
<link rel="stylesheet" href="css/chat.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<jsp:include page="header.jsp" />

<section>	
	<div class="info">
	<h3>備品購入希望申請詳細</h3>
		<table>
			<tbody>
				<tr><td>希望購入日：</td><td>${request.created_at}</td></tr>
				<tr><td>申請者：</td><td>${request.user_name}</td></tr>
				<tr><td>店名：</td><td>${request.name}</td></tr>
				<tr><td>品目：</td><td>${request.item}</td></tr>
				<tr><td>金額：</td><td>${request.amount}</td></tr>
				<tr><td>販売元URL：</td><td>${request.store_link}</td></tr>
				<tr><td>購入目的：</td><td>${request.purpose}</td></tr>
				<tr><td>ステータス：</td><td>${request.status}</td></tr>
			</tbody>
		</table>
	</div>
</section>
<section>
	<div class="buttons">
		<c:choose>
		    <c:when test="${accountData.role != '一般'}">
				<div class="buttons-left">
					<form action="purchase_request_detailServlet" method="post">
						<input type="hidden" name="id" value="${request.id}">	
						<button type="submit" name="submit" onclick="return confirmDelete();" class="delete-btn" value="delete">削除</button>
					</form>	
				</div>
		    </c:when>
		</c:choose>
		
		<div class="buttons-right">
			<c:choose>
			    <c:when test="${accountData.name == request.user_name}">
					<button class="form-edit-btn" id="form-edit-btn">編集(申請者)</button>
			    </c:when>
			</c:choose>
			<c:choose>
			    <c:when test="${accountData.role != '一般'}">
					<button class="status-edit-btn" id="status-edit-btn">編集(役員)</button>
			    </c:when>
			</c:choose>
			
		</div>
	</div>
</section>
<br>
<section>
	<div class="chat">
		<c:forEach var="c" items="${chat}">
		    <div class="contents">
		        <c:choose>
		            <c:when test="${c.user_id != accountData.id}">
		                <div class="message left">
		                    <div class="message_box">
		                        <div class="message_content">
		                            <div class="message_text">${c.message}</div>
		                        </div>
		                    </div>
		                </div>
		            </c:when>
		
		            <c:otherwise>

		                <div class="message right">
		                    <div class="message_box">
		                        <div class="message_content">
		                            <div class="message_text">${c.message}</div>
		                        </div>
		                    </div>
		                </div>
		            </c:otherwise>		
		        </c:choose>		
		        <div class="clear"></div>		
		    </div>
		</c:forEach>	
		<form action="purchase_request_detailServlet" method="post" class="send">
			<input type="hidden" name="id" value="${request.id}">
			<input type="text" name="message" class="send-message" maxlength="150" required>
			<button type="submit" name="submit" class="send-btn" value="send">送信</button>
		</form>
	</div>
</section>
<br><br><br>

<div id="fo-ed-wrapper">
	<div id="fo-ed-inside">
		<div id="message">
		<h1>申請内容の編集</h1>
		<form action="purchase_request_detailServlet" method="post">
		<input type="hidden" name="id" value="${request.id}">
		
			<table>
				<tr><td>購入希望日</td>
					<td><input type="date" id="date_input" name="selected_date" value="${request.created_at}"><span class="date-icon">📅</span></td>
				</tr>
				<tr><td>店名</td><td><input type="text" name="name" required value="${request.name}" maxlength="30"></td></tr>
				<tr><td>品目</td><td><input type="text" name="item" required value="${request.item}" maxlength="300"></td></tr>
				<tr><td>目的</td><td><textarea name="purpose" maxlength="200" required maxlength="200">${request.purpose}</textarea></td></tr>
				<tr><td>金額</td><td><input type="number" name="amount" required value="${request.amount}" min="0" max="9999999"></td></tr>
				<tr><td>販売元URL</td><td><input type="text" name="link" maxlength="200" value="${request.store_link}"></td></tr>
			</table>
			<button type="button" class="fo-ed-btn" id="fo-ed-btn">キャンセル</button>
			<button type="submit" name="submit" class="form-save-btn" value="form">保存</button>
		</form>
		</div>
	</div>
</div>
<div id="st-ed-wrapper">
	<div id="st-ed-inside">
		<div id="message">
		<h1>申請のステータス変更</h1>
		<form action="purchase_request_detailServlet" method="post">
			<input type="hidden" name="id" value="${request.id}">
		
			<label><input type="radio" name="radio" class="radio" value="待機中">待機中</label>
			<label><input type="radio" name="radio" class="radio" value="審議中">審議中</label>
			<label><input type="radio" name="radio" class="radio" value="手続き中">手続き中</label>
			<label><input type="radio" name="radio" class="radio" value="完了">完了</label>
			<label><input type="radio" name="radio" class="radio" value="却下">却下</label>
			<br>
			<button type="button" class="st-ed-btn" id="st-ed-btn">キャンセル</button>
			<button type="submit" name="submit" class="status-save-btn" value="status">保存</button>
		</form>
		</div>
	</div>
</div>


<script>

//申請内容の編集

document.querySelectorAll('.form-edit-btn').forEach(btn => {
	btn.addEventListener('click', (e) => {
	e.stopPropagation(); 
	const id = btn.dataset.id;
	
	const wrapper = document.getElementById("fo-ed-wrapper");
	if(wrapper){
		wrapper.style.display = "block";
	}
	});
});

document.querySelectorAll('.fo-ed-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
    e.stopPropagation();

    const wrapper = document.getElementById("fo-ed-wrapper");
    if(wrapper){
        wrapper.style.display = "none";
    }
    });
});
document.getElementById('fo-ed-wrapper')?.addEventListener('click', (e) => {
    if(e.target.id === 'fo-ed-wrapper'){
        e.target.style.display = 'none';
    }
});

//ステータス変更
document.querySelectorAll('.status-edit-btn').forEach(btn => {
	btn.addEventListener('click', (e) => {
	e.stopPropagation(); 
	const id = btn.dataset.id;
	
	const wrapper = document.getElementById("st-ed-wrapper");
	if(wrapper){
		wrapper.style.display = "block";
	}
	});
});

document.querySelectorAll('.st-ed-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
    e.stopPropagation();

    const wrapper = document.getElementById("st-ed-wrapper");
    if(wrapper){
        wrapper.style.display = "none";
    }
    });
});
document.getElementById('st-ed-wrapper')?.addEventListener('click', (e) => {
    if(e.target.id === 'st-ed-wrapper'){
        e.target.style.display = 'none';
    }
});

function confirmDelete(){
 return confirm("本当に削除しますか？");
}

</script>

<footer>
    <p>©2026 EBATA TAKUMI</p>
</footer>
</body>
</html>