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
		boolean mailSendFlag = sendEmail(mail);
		
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
	public boolean sendEmail(String mail) {
		System.out.println("きた。");
		
		System.out.println("メールアドレス："+MailUtil.getUsername());
		System.out.println("パスワード："+MailUtil.getPassword());
		
		return true;
	}
}
