package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.chatBeans;

public class chatDao {
	//チャットの取得
	public List<chatBeans> getChat(String requestID){
		List<chatBeans> list = new ArrayList<>();
		String sql = "SELECT c.id AS id, c.user_id AS user_id, a.user_name AS user_name, c.created_at AS created_at, c.message AS message FROM chat AS c LEFT JOIN account AS a ON c.user_id = a.id WHERE request_id = ? ORDER BY created_at;";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			
			stmt.setString(1, requestID);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
	            	String id = rs.getString("id");
	            	String user_id = rs.getString("user_id");
	            	String user_name = rs.getString("user_name");
	            	String created_at = rs.getString("created_at");
	            	String message = rs.getString("message");
	            	
	            	chatBeans beans = new chatBeans(
	                        id,
	                        user_id,
	                        user_name,
	                        requestID,
	                        created_at,
	                        message
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
	//チャット送信
	public boolean sendMessage(String id, String user_id, String request_id, String created_at, String message) {
        PreparedStatement stmt = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "INSERT INTO chat (id,user_id,request_id,created_at,message) VALUES (?,?,?,?,?)";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, user_id);
			stmt.setString(3, request_id);
			stmt.setString(4, created_at);
			stmt.setString(5, message);
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
