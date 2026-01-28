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
<div class="finance">
	<div class="fiscal">
		<h3>今年度状況</h3>
		<div class="data_area">
			<div class="graph"><canvas></canvas></div>
			<div class="data">
				<h2 class="income">収入合計：￥</h2>
				<h2 class="expend">支出合計：￥</h2>
				<h2 class="balance">残高合計：￥</h2>
			</div>
		</div>
	</div>
	
	<div class="search">
		<form action="financialServlet" method="post">
		<h3>検索</h3>
		<div class="searchArea">
			<table class="searchTable">
				<tr>
					<td>日付</td>
					<td><input type="date" name="startDate">から<input type="date" name="endDate"></td>
					<td>収支区分</td>
					<td>
						<select  class="textbox">
							<option value="income">収入</option>
							<option value="expend">支出</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>カテゴリ</td>
					<td>
						<select  class="textbox">
							
						</select>
					</td>
					<td>プロジェクト</td>
					<td>
						<select  class="textbox">
							
						</select>
					</td>
				</tr>
				<tr>
					<td>店名</td>
					<td><input type="text" name="store" class="textbox"></td>
					<td>品目</td>
					<td><input type="text" name="item" class="textbox"></td>
				</tr>
				<tr>
					<td>キーワード</td>
					<td><input type="text" name="keyword" class="textbox"></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</div>
		<div class="searchBtns">
			<button type="submit" name="submit" class="reset-btn" value="reset">リセット</button>
			<button type="submit" name="submit" class="display-btn" value="display">表示</button>
		</div>
		</form>
	</div>
</div>


</body>
</html>