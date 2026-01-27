<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica</title>
<link rel="stylesheet" href="css/financial.css">
</head>
<body>
<jsp:include page="header.jsp" />

<div class="fiscal">
	<h2>今年度 予算合計：￥</h2>
	<h2>今年度 執行済合計：￥</h2>
	<h2>今年度 残高：￥</h2>
</div>

<div class="search">
	<form action="financialServlet" method="post">
	<h3>検索</h3>
	<div class="searchArea">
		<div class="left">
			<label>日付</label>
			<input type="date" name="startDate">から<input type="date" name="endDate">
			<br>
			<label>カテゴリ</label>
			
			<br>
			<label>店名</label>
			<input type="text" name="store_name">
			<br>
			
			<label>キーワード</label>
			<input type="text" name="keyword">
		</div>
		<div class="right">
			<label>収支区分</label>
			
			<br>
			<label>プロジェクト名</label>
			
			<br>
			
			<label>品目</label>
			<input type="text" name="item">
			<br>
			
			<button type="submit" name="submit" class="reset-btn" value="reset">リセット</button>
			<button type="submit" name="submit" class="display-btn" value="display">表示</button>
			
		</div>
	</div>

	</form>
</div>

</body>
</html>