<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | 会費・名簿管理</title>
<link rel="stylesheet" href="css/member.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="images/spica.ico">
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
	            <c:when test="${empty notice}">
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
		<h3>会費管理</h3>
		<c:choose>
		    <c:when test="${accountData.role != 'その他役員'}">
				<button type="button" class="insert-btn">会費設定</button>
		    </c:when>
		</c:choose>
		
	</div>
	<div class="fee_management">
		<h4>現在設定中の会費</h4>
		<p>${membership_fee.fee}円 期間：${membership_fee.start_date} ～ ${membership_fee.end_date}</p>
	</div>
</section>

<section>
	<div class="section_menu">
		<h3>支払い状況の管理</h3>
		<select id="sectionSelect" class="sectionSelect" onchange="filterDocuments(this.value)">
		</select>
	</div>
	<div class="payment_table">
		<table>
			<thead><tr><th>会員名</th><th>支払い状況</th><th>ロール</th><th>操作</th></tr></thead>
			<tbody id="paymentList"></tbody>
		</table>
	</div>

</section>


<script>

const payment_documents = [
    <c:forEach var="c" items="${accountPayment}">
    {
        id: "${c.id}",
        user_id: "${c.user_id}",
        name: "${c.name}",
        date: "${c.date}",
        status: "${c.status}",
        role: "${c.role}"  
    },
    </c:forEach>
];

console.log(payment_documents);
window.onload = function() {
    const dates = [...new Set(payment_documents.map(doc => doc.date))].filter(d => d).sort((a, b) => b.localeCompare(a)); 

    const today = new Date();
    const todayStr = today.getFullYear() + "-" + String(today.getMonth() + 1).padStart(2, "0") + "-01";

    dates.forEach(date => {
        const option = document.createElement("option");
        option.value = date;
        option.textContent = "期日：" + date;
        document.getElementById("sectionSelect").appendChild(option);
    });

    const defaultDate = dates.find(d => d >= todayStr) ?? dates[dates.length - 1];
    document.getElementById("sectionSelect").value = defaultDate;
    filterDocuments(defaultDate);
};


function filterDocuments(selectedDate) {
    const filtered = payment_documents
        .filter(doc => doc.date === selectedDate)
        .sort((a, b) => a.date.localeCompare(b.date));

    const container = document.getElementById("paymentList");

    if (filtered.length === 0) {
        container.innerHTML = "<tr><td colspan='4'>該当するユーザーはいません。</td></tr>";
        return;
    }

    let html = "";
    filtered.forEach(doc => {
        html +=
            '<tr>' +
                '<td>' + doc.name + '</td>' +
                '<td>' + doc.status + '</td>' +
                '<td>' + doc.role + '</td>' +
                '<td><button class="payment-btn" data-id="' + doc.id + '">設定</button></td>' +
            '</tr>';
    });

    container.innerHTML = html;
}
</script>

<section>
	<div class="menu_subtitle">
		<h3>名簿</h3>
	</div>
	<div class="member_table">
		<table>
			<thead><tr><th>会員名</th><th>メールアドレス</th><th>ロール</th><th>操作</th></tr></thead>
			<tbody>
				<c:forEach var="c" items="${memberList}">
					<tr>
						<td>${c.name}</td>
						<td>${c.email}</td>
						<td>${c.role}</td>
						<td>
							<c:choose>
							    <c:when test="${accountData.role == '副代表' || accountData.role == '代表' || accountData.role == '顧問'}">
									<button class="member-setting-btn" data-id="${c.id}">設定</button>
							    </c:when>
							</c:choose>							
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
				<input type="number" name="fee" required min="0" max="9999999"><br><br>
				
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

<c:forEach var="c" items="${accountPayment}">
	<div id="payment-wrapper-${c.id}" class="payment-wrapper">
		<div id="payment-inside">
			<div id="message">
				<h1>支払い状況の管理</h1>
				<form action="memberServlet" method="post">
					<input type="hidden" name="id" value="${c.id}" />
					<input type="hidden" name="user_id" value="${c.user_id}" />
					<p>ユーザー名：「${c.name}」さんの支払い状況...</p>
					<label>
					  <input type="radio" name="answer" value="支払済み" 
					  <c:if test="${c.status == '支払済み'}">checked</c:if>/> 支払い済み
					</label>
					
					<label>
					  <input type="radio" name="answer" value="未払い" 
					  <c:if test="${c.status == '未払い'}">checked</c:if>/> 未払い
					</label><br><br>
				
					<button type="button" class="payment-close-btn" data-id="${c.id}">キャンセル</button>
					<button type="submit" name="submit" class="btn" value="payment">保存</button>
				</form>
			</div>
		</div>
	</div>
</c:forEach>

<c:forEach var="c" items="${memberList}">
	<div id="member-wrapper-${c.id}" class="member-wrapper">
		<div id="member-inside">
			<div id="message">
				<h1>会員の設定</h1>
				<form action="memberServlet" method="post">
				<input type="hidden" name="id" value="${c.id}" />
				<p>ロール</p>
					<label>
					  <input type="radio" name="role" value="一般" 
					  <c:if test="${c.role == '一般'}">checked</c:if>/> 一般
					</label>
					
					<label>
					  <input type="radio" name="role" value="その他役員" 
					  <c:if test="${c.role == 'その他役員'}">checked</c:if>/> その他役員
					</label>
					
					<label>
					  <input type="radio" name="role" value="会計" 
					  <c:if test="${c.role == '会計'}">checked</c:if>/> 会計
					</label>
					
					<label>
					  <input type="radio" name="role" value="副代表" 
					  <c:if test="${c.role == '副代表'}">checked</c:if>/> 副代表
					</label>
					
					<label>
					  <input type="radio" name="role" value="代表" 
					  <c:if test="${c.role == '代表'}">checked</c:if>/> 代表
					</label>
					
					<label>
					  <input type="radio" name="role" value="顧問" 
					  <c:if test="${c.role == '顧問'}">checked</c:if>/> 顧問
					</label><br><br>
					
					<c:choose>
					    <c:when test="${accountData.role == '代表' || accountData.role == '顧問'}">
							<button type="submit" name="submit" class="delete-btn" onclick="return confirmDelete();" value="delete">強制退会</button>
					    </c:when>
					</c:choose>
				
					
					<button type="button" class="member-close-btn" data-id="${c.id}">キャンセル</button>
					<button type="submit" name="submit" class="btn" value="role">保存</button>
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

document.getElementById("paymentList").addEventListener("click", (e) => {
    if (e.target.classList.contains("payment-btn")) {
        const id = e.target.dataset.id;

        const wrapper = document.getElementById("payment-wrapper-" + id);
        if (wrapper) {
            wrapper.style.display = "block";
        }
    }
});

document.querySelectorAll('.payment-close-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
    e.stopPropagation();
    const id = btn.dataset.id;
    const wrapper = document.getElementById("payment-wrapper-"+id);
    if(wrapper){
        wrapper.style.display = "none";
    }
    });
});

document.querySelectorAll('.member-setting-btn').forEach(btn => {
	btn.addEventListener('click', (e) => {
	e.stopPropagation(); 
	const id = btn.dataset.id;
	
	const wrapper = document.getElementById("member-wrapper-"+id);
	if(wrapper){
		wrapper.style.display = "block";
	}
	});
});

document.querySelectorAll('.member-close-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
    e.stopPropagation();
    const id = btn.dataset.id;
    const wrapper = document.getElementById("member-wrapper-"+id);
    if(wrapper){
        wrapper.style.display = "none";
    }
    });
});
function confirmDelete(){
	 return confirm("本当に退会させますか？");
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