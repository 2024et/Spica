<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | 備品購入希望詳細</title>
<link rel="stylesheet" href="css/purchase_request_detail.css">
</head>
<body>
<jsp:include page="header.jsp" />

<section>	
	<div class="info">
	<h3>備品購入希望申請</h3>
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
		<button type="submit" name="submit" class="delete-btn" value="delete">削除</button>
		<button class="form-edit-btn" id="form-edit-btn">編集(申請者)</button>
		<button class="status-edit-btn" id="status-edit-btn">編集(役員)</button>
	</div>
</section>

<section>
	<div class="chat">
		<div class="contents">
		</div>
		<form action="purchase_request_detailServlet" method="post">
			<input type="text" name="message">
			<button type="submit" name="submit" class="send-btn" value="send">送信</button>
		</form>
	</div>
</section>


<div id="fo-ed-wrapper">
	<div id="fo-ed-inside">
		<div id="message">
		
		<form action="purchase_request_detailServlet" method="post">
			<button type="button" class="fo-ed-btn" id="fo-ed-btn">キャンセル</button>
			<button type="submit" name="submit" class="form-save-btn" value="form">保存</button>
		</form>
		</div>
	</div>
</div>
<div id="st-ed-wrapper">
	<div id="st-ed-inside">
		<div id="message">
		<form action="purchase_request_detailServlet" method="post">
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
document.querySelectorAll('.st-ed-btn').forEach(btn => {
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
 return alert("本当に削除しますか？");
}

</script>

<footer>
    <p>©2026 EBATA TAKUMI</p>
</footer>
</body>
</html>