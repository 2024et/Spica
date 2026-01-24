package Logic;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import Dao.accountDao;



public class signupLogic {
	
	//メールの重複確認
	public int mailDupli(String mail) {
		accountDao dao = new accountDao();
		return dao.mailDupli(mail);
	}
	
	//仮登録
	public boolean tempAccount(String name, String mail, String password) {
		String id = RandomID();
		
		String hashed_password = hashPassword(password);
		
		if(hashed_password.equals("error")) {
			return false;
		}
		
		accountDao dao = new accountDao();
		boolean dbRegistFlag = dao.tempAccount(id,name,mail,hashed_password);
		if(!dbRegistFlag) {
			return false;
		}
		
		//メール処理
		boolean mailSendFlag = sendEmail(mail,id);
		
		return mailSendFlag;
	}
	
	//ID発行
	public String RandomID() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder randomString = new StringBuilder();
		for(int i=0; i< 10; i++) {
			int index = random.nextInt(characters.length());
			randomString.append(characters.charAt(index));
		}
		return randomString.toString();
	}
	
	//ハッシュ化
	public String hashPassword(String password) {
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			byte[] sha1_result = sha256.digest(password.getBytes());
			return String.format("%040x", new BigInteger(1, sha1_result));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	//メール送信処理
	public boolean sendEmail(String mail,String id) {		
		MailUtil util = new MailUtil();
		
		String subject = "【メールアドレス確認のお願い】Spica";
		
		String html =
				"<html>" +
				"<body style='font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;'>" +

				"<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px;'>" +

				"<h2 style='color:#333333;'>Spicaへの登録ありがとうございます。</h2>" +

				"<p style='font-size:14px; color:#555555;'>以下の確認URLをタップして、登録を完了してください。<br>登録を完了することで、Spicaの利用を開始できます！</p>" +

				"<a href='http://takumi-e.com:8080/Spica/confirmServlet?id=" + id +
				"'>http://takumi-e.com:8080/Spica/confirmServlet?id=" + id + "</a>"+

				"</div>" +
				"</body>" +
				"</html>";

		util.sendEmail(mail, subject, html);
		
		return true;
	}
}
