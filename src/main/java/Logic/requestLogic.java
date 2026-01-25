package Logic;

import Dao.accountDao;

public class requestLogic {
	//メールアドレスチェック
	public int searchMail(String mail) {
		accountDao dao = new accountDao();
		return dao.mailDupli(mail);
	}
	
	//id取得
	public String getUserID(String mail) {
		accountDao dao = new accountDao();
		return dao.getUserID(mail);
	}

	//メール送信
	public boolean sendMail(String mail) {
		String id = getUserID(mail);
		if(id==null) {
			return false;
		}
		MailUtil util = new MailUtil();
		
		String subject = "【パスワードの再設定リクエスト】Spica";
		
		String html =
				"<html>" +
				"<body style='font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;'>" +

				"<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px;'>" +

				"<h2 style='color:#333333;'>パスワードの再設定</h2>" +

				"<p style='font-size:14px; color:#555555;'>Spicaアカウントのパスワード再設定リクエストを承りました。<br>"
				+ "このリクエストメールにてお心当たりがない場合は、無視してください。<br><br>"
				+ "以下のリンクをタップしてパスワードの再設定を行ってください。</p>" +

				"<a href='http://takumi-e.com:8080/Spica/change_passwordServlet?id=" + id +
				"'>http://takumi-e.com:8080/Spica/change_passwordServlet?id=" + id + "</a>"+

				"</div>" +
				"</body>" +
				"</html>";

		boolean completeFlag = util.sendEmail(mail, subject, html);
		
		return completeFlag;
	}
}
