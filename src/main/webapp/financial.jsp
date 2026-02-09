<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica</title>
<link rel="stylesheet" href="css/financial.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<jsp:include page="header.jsp" />
<div class="finance">
	<div class="fiscal">
		<h3>今年度状況</h3>
		<div class="data_area">
			<div class="balance_graph"><canvas id="thisYearChart"></canvas></div>
			<div class="data">
				<h2 class="income">収入合計：￥${income}</h2>
				<h2 class="expend">支出合計：￥${expend}</h2>
				<h2 class="balance">残高合計：￥${balance_thisyear}</h2>
			</div>
		</div>
	</div>
	
	<div class="search">
		<form action="financialServlet" method="post" id="search">
		<h3>検索</h3>
		<div class="searchArea">
			<table class="searchTable">
				<tr>
					<td>日付</td>
					<td><input type="date" name="startDate" value="${searchArchive.startDate}">から<input type="date" name="endDate" value="${searchArchive.endDate}"></td>
					<td>収支区分</td>
					<td>
						<select  class="textbox" name="type">
							<option value="">==未選択==</option>
							<option value="income" ${searchArchive.type == 'income' ? 'selected' : ''}>収入</option>
							<option value="expend" ${searchArchive.type == 'expend' ? 'selected' : ''}>支出</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>カテゴリ</td>
					<td>
						<select  class="textbox" name="category">
							<option value="">==未選択==</option>
							<c:forEach var="c" items="${category}">
								<option value="${c.name}"
									<c:if test="${c.name == searchArchive.category}">selected</c:if>>${c.name}
								</option>
							</c:forEach>
						</select>
					</td>
					<td>プロジェクト</td>
					<td>
						<select  class="textbox" name="project">
							<option value="">==未選択==</option>
							<c:forEach var="p" items="${project}">
								<option value="${p.name}"
									<c:if test="${p.name == searchArchive.project}">selected</c:if>>${p.name}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>店名</td>
					<td><input type="text" name="store" class="textbox" value="${searchArchive.store}"></td>
					<td>品目</td>
					<td><input type="text" name="item" class="textbox" value="${searchArchive.item}"></td>
				</tr>
				<tr>
					<td>キーワード</td>
					<td><input type="text" name="keyword" class="textbox" value="${searchArchive.keyword}"></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</div>
		<div class="searchBtns">
			<button type="submit" name="button" class="reset-btn" onclick="resetSearchArea">リセット</button>
			<button type="submit" name="submit" class="display-btn" value="search">表示</button>
		</div>
		</form>
	</div>
</div>

<canvas class="spend_graph" id="searchBalanceGraph"></canvas>

<table class="dataTable" border="1">
	<thead><th>日付</th><th>プロジェクト</th><th>カテゴリ</th><th>店名</th><th>品目</th><th>入金</th><th>出金</th><th>詳細</th></thead>
	<tbody>
		<c:forEach var="c" items="${balance}">
		<tr onmouseover="openPopup('${c.id}',event)">
			<td>${c.created_at}</td>
			<td>${c.project}</td>
			<td>${c.category}</td>
			<td>${c.name}</td>
			<td>${c.item}</td>
		    <c:choose>
		        <c:when test="${c.type eq 'income'}">
		            <td>${c.amount}</td>
		        </c:when>
		        <c:otherwise>
		            <td></td>
		        </c:otherwise>
		    </c:choose>
		    <c:choose>
		        <c:when test="${c.type eq 'expend'}">
		            <td>${c.amount}</td>
		        </c:when>
		        <c:otherwise>
		            <td></td>
		        </c:otherwise>
		    </c:choose>
		    <td>${c.memo}</td>
		   </tr>
		</c:forEach>
		
	</tbody>
</table>
<c:forEach var="c" items="${balance}">
	<div id="popup_${c.id}" class="popupDetail" onmouseout="closePopup_Delay('${c.id}')">
		<h1>収支詳細</h1>
		<p>日付：${c.created_at}</p>
		<p>カテゴリ：${c.category}</p>
		<p>プロジェクト：${c.project}</p>
		<p>店名：${c.name}</p>
		<p>品目：${c.item}</p>
		<p>入出金：${c.type}</p>
		<p>金額：${c.amount}</p>
		<p>メモ：${c.memo}</p>
		<button type="button" class="ad_EditBtn reset-btn" data-id="${c.id}">編集</button>
		<button type="button" class="close-btn" onclick="closePopup('${c.id}')" >閉じる</button>
	</div>
</c:forEach>


<div id="ad-is-wrapper">
	<div id="ad-is-inside">
		<div id="message">
			<h1>収支記録</h1>
			<form action="financialServlet" method="post">
				<table class="searchTable">
					<tr>
						<td>日付</td>
						<td><input type="date" name="date" required></td>
						<td>カテゴリ</td>
						<td>
							<select  class="textbox" name="category" required>
								<option value="">==未選択==</option>	
								<c:forEach var="c" items="${category}">
									<option value="${c.name}">${c.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>プロジェクト</td>
						<td>
							<select  class="textbox" name="project">
								<option value="">==未選択==</option>
								<c:forEach var="p" items="${project}">
									<option value="${p.name}">${p.name}</option>
								</c:forEach>
							</select>
						</td>
						<td>店名</td>
						<td><input type="text" name="store" class="textbox" ></td>
					</tr>
					<tr>
						<td>品目</td>
						<td><input type="text" name="item" class="textbox" maxlength="300" ></td>
						<td>入出金</td>
						<td>
							<select  class="textbox" name="type" required>
								<option value="">==未選択==</option>
								<option value="income">収入</option>
								<option value="expend">支出</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>金額</td>
						<td><input type="number" name="number" class="textbox" required></td>
						<td>メモ</td>
						<td><textarea class="textbox" name="memo"></textarea></td>
					</tr>
				</table>
				<button type="button" id="close" class="close-btn" value="cancel">キャンセル</button>
				<button type="submit" name="submit" class="display-btn" value="save">保存</button>
			</form>
		</div>
	</div>
</div>

<c:forEach var="c" items="${balance}">
	<div class="ad-ed-wrapper" id="ad-ed-wrapper-${c.id}">
		<div id="ad-ed-inside">
			<div id="message">
				<h1>収支編集</h1>
				<form action="financialServlet" method="post">
				<input type="hidden" name="id" value="${c.id}" />
					<table class="searchTable">
						<tr>
							<td>日付</td>
							<td><input type="date" name="date" value="${c.created_at}"></td>
							<td>カテゴリ</td>
							<td>
								<select  class="textbox" name="category">
									<option value="${c.category}">${c.category}</option>
									<c:forEach var="ca" items="${category}">
										<option value="${ca.name}">${ca.name}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>プロジェクト</td>
							<td>
								<select  class="textbox" name="project">
									<option value="${c.project}">${c.project}</option>
									<c:forEach var="p" items="${project}">
										<option value="${p.name}">${p.name}</option>
									</c:forEach>
								</select>
							</td>
							<td>店名</td>
							<td><input type="text" name="store" class="textbox" value="${c.name}"></td>
						</tr>
						<tr>
							<td>品目</td>
							<td><input type="text" name="item" class="textbox" value="${c.item}"></td>
							<td>入出金</td>
							<td>
								<select  class="textbox" name="type">
									<option value="${c.type}">${c.type}</option>
									<option value="income">収入</option>
									<option value="expend">支出</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>金額</td>
							<td><input type="number" name="number" class="textbox" value="${c.amount}"></td>
							<td>メモ</td>
							<td><textarea class="textbox" name="memo">${c.memo}</textarea></td>
						</tr>
					</table>
					<button type="submit" name="submit" class="reset-btn" onclick="confirmDelete();" value="delete">削除</button>
					<button type="button" class="edit-close-btn close-btn" data-id="${c.id}">キャンセル</button>
					<button type="submit" name="submit" class="display-btn" value="edit">保存</button>
				</form>
			</div>
		</div>
	</div>
</c:forEach>

<div id="ad_InsertBtn"><p>収支新規登録</p></div>

<footer>
    <p>©2026 EBATA TAKUMI</p>
</footer>

<script>
console.log(
		  'edit-close-btn count =',
		  document.querySelectorAll('.edit-close-btn').length
		);

	let currentPopup = null;

	function openPopup(id, event){
		const popup = document.getElementById("popup_"+id);
		if(currentPopup != null){
			closePopup(currentPopup);
		}
		if(popup){
		    popup.style.left = event.pageX + "px";
		    popup.style.top  = event.pageY + "px";
			popup.style.display = "block";
			currentPopup = id;
		}
	}

	function closePopup_Delay(id){
		let hideTimer = setTimeout(() =>{
			closePopup(id);
		},10000);
	}

	function closePopup(id){
		const popup = document.getElementById("popup_"+id);
		if(popup){
			popup.style.display = "none";	
			currentPopup = null;
		}
	}

	function confirmDelete(){
	 return alert("本当に削除しますか？");
	}

	function resetSearchArea(){
		document.querySelector("search").reset();
	}

	//今年度残高推移グラフ
	const thisYearLabels = [];
	const thisYearData = [];

	<c:forEach var="b" items="${thisYearBalanceGraph}">
    thisYearLabels.push("${b.key}");
    thisYearData.push(${b.value});
	</c:forEach>

	const ctx = document.getElementById('thisYearChart');

	new Chart(ctx, {
	    type: 'line',
	    data: {
	        labels: thisYearLabels,
	        datasets: [{
	            label: '残高推移',
	            data: thisYearData,
	            borderWidth: 2
	        }]
	    }
	});

	//検索結果収支推移グラフ
	const searchLabels = [];
	const searchDataIncome = [];
	const searchDataExpend = [];
	
	const ctx2 = document.getElementById("searchBalanceGraph");

	<c:forEach var="b" items="${searchIncome}">
		searchLabels.push("${b.key}");
		searchDataIncome.push(${b.value});
	</c:forEach>
	
	<c:forEach var="b" items="${searchExpend}">
		searchDataExpend.push(${b.value});
	</c:forEach>
	
	const searchBalanceGraph = new Chart(ctx2, {
	    type: 'bar',
	    data: {
		    labels:searchLabels,
	        datasets: [
	            {
	                label: '収入',
	                data: searchDataIncome,
	                backgroundColor: 'rgba(75, 192, 192, 0.6)',
	                borderColor: 'rgb(75, 192, 192)',
	                borderWidth: 1
	            },
	            {
	                label: '支出',
	                data: searchDataExpend,
	                backgroundColor: 'rgba(255, 99, 132, 0.6)',
	                borderColor: 'rgb(255, 99, 132)',
	                borderWidth: 1
	            }
	        ]
	    },
	    options: {
	        responsive: true,
	        scales: {
	            y: {
	                beginAtZero: true
	            }
	        }
	    }
	});
	
	

	const adInsertBtn = document.getElementById('ad_InsertBtn');
	const adiswrapper = document.getElementById('ad-is-wrapper');
	const close = document.getElementById('close');

	adInsertBtn.addEventListener('click',()=>{
		adiswrapper.style.display = "block";
	});

	adiswrapper.addEventListener('click',e=>{
		if(e.target.id === adiswrapper.id || e.target.id === close.id ){
			adiswrapper.style.display = "none";
		}
	});
	
	document.querySelectorAll('.ad_EditBtn').forEach(btn => {
		  btn.addEventListener('click', (e) => {
		    e.stopPropagation(); 
		    const id = btn.dataset.id;
		   
		    const wrapper = document.getElementById("ad-ed-wrapper-"+id);
		    if(wrapper){
		      wrapper.style.display = "block";
		      closePopup(id);
		    }
		  });
		});

	document.querySelectorAll('.edit-close-btn').forEach(btn => {
	  btn.addEventListener('click', (e) => {
	    e.stopPropagation();

	    const id = btn.dataset.id;

	    const wrapper = document.getElementById("ad-ed-wrapper-"+id);
	    if(wrapper){
	      wrapper.style.display = "none";
	    }
	  });
	});

		




	
</script>


</body>
</html>