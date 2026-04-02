package Logic;

import java.util.List;

import Beans.accountBeans;
import Beans.noticeBeans;
import Dao.accountDao;

public class accountLogic {
	//通知取得
	public List<noticeBeans> getNotice(String id){
		accountDao dao = new accountDao();
		return dao.getNotice(id);
	}
	
	//パスワードの再設定
	public boolean changePassword(String id, String password) {
		accountDao dao = new accountDao();
		boolean completeFlag = dao.updatePassword(id,password);
		if(completeFlag) {
			MailUtil mail = new MailUtil();
			
			String subject = "【Spica】アカウント情報変更について";
			
			String html = "<html>" +
					"<body style='font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;'>" +

					"<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px;'>" +

					"<h2 style='color:#333333;'>アカウント情報が変更されました。</h2>" +

					"<p style='font-size:14px; color:#555555;'>変更内容：パスワード</p>" +

					"</div>" +
					"</body>" +
					"</html>";
			String to = dao.getUserEmail(id);
			
			mail.sendEmail(to,subject,html);
			return true;
		}else{
			return false;
		}
	}
	
	//アカウント情報の変更
	public boolean changeInformation(String id,String name, String email, String code) {
		accountDao dao = new accountDao();
		boolean completeFlag = dao.updateInformation(id,name,email);
		if(completeFlag) {
			String updateInfo = "";
			if(name != null && !name.isEmpty()) {
				updateInfo += "<br>ユーザー名："+name;
			}
			if(email != null && !email.isEmpty()) {
				updateInfo += "<br>メールアドレス："+email;
			}
			
			MailUtil mail = new MailUtil();
			
			String subject = "【Spica】アカウント情報変更について";
			
			String html = "<html>" +
					"<body style='font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;'>" +

					"<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px;'>" +

					"<h2 style='color:#333333;'>アカウント情報が変更されました。</h2>" +

					"<p style='font-size:14px; color:#555555;'>変更後：<br>"+ updateInfo +"</p>" +

					"</div>" +
					"</body>" +
					"</html>";
			String to = dao.getUserEmail(id);
			
			mail.sendEmail(to,subject,html);
			return true;
		}else {
			return false;
		}		
	}
	
	//所属変更
	public boolean updateGroupChange(String id) {
		signupLogic logic = new signupLogic();
		String new_id = logic.RandomID();
		accountDao dao = new accountDao();
		String role = "一般";
		return dao.changeOrganization(id,new_id,role);
		
	}
	
	//ログイン
	public accountBeans login_system(String email) {
		accountDao dao = new accountDao();
		return dao.login(email);
		
	}
	
	//アカウント削除
	public boolean deleteAccount(String id) {
		accountDao dao = new accountDao();
		String to = dao.getUserEmail(id);
		boolean completeFlag = dao.deleteAccount(id);
		if(completeFlag) {
			MailUtil mail = new MailUtil();
			
			String subject = "【Spica】アカウントの削除について";
			
			String html = "<html>" +
					"<body style='font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;'>" +

					"<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px;'>" +

					"<h2 style='color:#333333;'>Spicaアカウントから削除されました。</h2>" +

					"<p style='font-size:14px; color:#555555;'>ご退会の申し出を承りました。またのご利用を心よりお待ちしております。</p>" +

					"</div>" +
					"</body>" +
					"</html>";
			
			mail.sendEmail(to,subject,html);
			return true;
		}else {
			return false;
		}
	}

}
