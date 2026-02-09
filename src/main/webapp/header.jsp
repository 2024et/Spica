<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spica | ヘッダー</title>
<link rel="stylesheet" href="css/header.css">
</head>
<body>
<header>
<div class="header">
	<div class="information">
		<h4><a href="financialServlet" class="app_name">Spica | 会計管理Webアプリ</a></h4>
		<h4 class="group_name">団体名：${accountData.group_name}</h4>
		<h4 class="user_name">ユーザー名：${accountData.name}</h4>
	</div>
	<div class="navigation">
		<div class="nav_up">
			<a class="empty"></a>
			<a class="empty"></a>
			<a class="empty"></a>
			<a href="" class="admin">管理者</a>
			<a href="" class="general">ログアウト</a>
		</div>
		<div class="nav_down">
			<a href="" class="general">収支一覧</a>
			<a href="" class="general">会計報告</a>
			<a href="" class="general">予算報告</a>
			<a href="" class="general">購入申請</a>
			<a href="accountServlet" class="general">アカウント</a>
		</div>
	</div>
</div>
</header>
</body>
</html>