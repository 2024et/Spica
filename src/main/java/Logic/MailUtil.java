package Logic;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailUtil {
	private static final Properties Loadprop = new Properties();

    static {
        try {
            InputStream is = MailUtil.class
                    .getClassLoader()
                    .getResourceAsStream("mail.properties");

            if (is == null) {
                throw new RuntimeException("mail.properties が見つかりません");
            }

            Loadprop.load(is);

        } catch (Exception e) {
            throw new RuntimeException("mailUtil 初期化失敗", e);
        }
    }
    
    
    public void sendEmail(String to, String subject, String text) {
    	Properties prop = new Properties();
    	prop.put("mail.smtp.auth","true");
    	prop.put("mail.smtp.starttls.enable","true");
    	prop.put("mail.smtp.starttls.required", "true");
    	prop.put("mail.smtp.host",Loadprop.getProperty("mail.smtp.host"));
    	prop.put("mail.smtp.port","587");
    	

    
    	final String username = Loadprop.getProperty("mail.username");
    	final String password = Loadprop.getProperty("mail.password");
    	
    	Session session =Session.getInstance(prop, new Authenticator() {
    		@Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
    	});
    	session.setDebug(true);
    	
        MimeMessage message = new MimeMessage(session);
        try {
			message.setFrom(new InternetAddress(username));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	        message.setSubject(subject);
	        message.setText(text);

	        // 送信
	        Transport.send(message);
	        System.out.println("Email sent successfully!");
		} catch (MessagingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("エラー:"+e);
		}

    }
}
