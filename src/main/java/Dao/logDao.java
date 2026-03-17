package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Beans.logBeans;
import Logic.signupLogic;

public class logDao {
	//ログの記録
	public boolean insertLog(Connection con, String group_id, String log) {
        signupLogic logic = new signupLogic();
        String id = logic.RandomID() + logic.RandomID();
        
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String created_at = format.format(now);         
        String sql = "INSERT INTO log_report (id,group_id,created_at,log) VALUES (?,?,?,?);";
		try  (PreparedStatement stmt = con.prepareStatement(sql)) {
			
			
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
	
	//ログの取得
	public List<logBeans> getLogData(String group_id){
		List<logBeans> list = new ArrayList<>();
		String sql = "SELECT * FROM log_report WHERE group_id = ? AND created_at >= DATE_SUB(NOW(), INTERVAL 3 MONTH);";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			stmt.setString(1, group_id);
			try (ResultSet rs = stmt.executeQuery()){
				while (rs.next()) {
	            	String id = rs.getString("id");
	            	String created_at = rs.getString("created_at");
	            	String log = rs.getString("log");
	            	
	            	logBeans beans = new logBeans(
	                        id,
	                        group_id,
	                        created_at,
	                        log
	                    );
	            	list.add(beans);  
	            }	
			}

			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
