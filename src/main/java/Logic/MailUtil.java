package Logic;

import java.io.InputStream;
import java.util.Properties;

public class MailUtil {
	private static final Properties prop = new Properties();

    static {
        try {
            InputStream is = MailUtil.class
                    .getClassLoader()
                    .getResourceAsStream("mail.properties");

            if (is == null) {
                throw new RuntimeException("mail.properties が見つかりません");
            }

            prop.load(is);

        } catch (Exception e) {
            throw new RuntimeException("mailUtil 初期化失敗", e);
        }
    }
    
    public static String getUsername() {
    	return prop.getProperty("mail.username");
    }
    
    public static String getPassword() {
    	return prop.getProperty("mail.password");
    }
}
