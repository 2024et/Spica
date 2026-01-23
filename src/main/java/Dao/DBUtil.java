package Dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static final Properties prop = new Properties();

    static {
        try {
            InputStream is = DBUtil.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            if (is == null) {
                throw new RuntimeException("db.properties が見つかりません");
            }

            prop.load(is);

            Class.forName(prop.getProperty("db.driver"));

        } catch (Exception e) {
            throw new RuntimeException("DBUtil 初期化失敗", e);
        }
    }
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            prop.getProperty("db.url"),
            prop.getProperty("db.user"),
            prop.getProperty("db.password")
        );
    }
}
