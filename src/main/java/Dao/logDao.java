package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Logic.signupLogic;

public class logDao {
	//ログの記録
	public boolean insertLog(Connection con, String group_id, String log) {
        PreparedStatement stmt = null;
        signupLogic logic = new signupLogic();
        String id = logic.RandomID() + logic.RandomID();
        
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String created_at = format.format(now);         
        
		try {
			String sql = "INSERT INTO log_report (id,group_id,created_at,log) VALUES (?,?,?,?);";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, group_id);
			stmt.setString(3, created_at);
			stmt.setString(4, log);
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
}
