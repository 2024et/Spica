package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import Beans.accountBeans;

public class noticeDao {
	
	//通知の記録
	public boolean insertNotice(Connection con,String id,String to,String at, String message) {
		String sql = "INSERT INTO notice (id,user_id,created_at,message) VALUES (?,?,?,?)";
		try(
		        PreparedStatement stmt = con.prepareStatement(sql);
		    )  {
						
			stmt.setString(1, id);
			stmt.setString(2, to);
			stmt.setString(3, at);
			stmt.setString(4, message);			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//通知の記録(複数)
	public boolean insertNotices(Connection con, List<String>ids, List<accountBeans> accounts,String created_at, String message) {               
        String placeholder = "(?,?,?,?)";
		String placeholders = String.join(", ",Collections.nCopies(accounts.size(), placeholder));
		
		String sql = "INSERT INTO notice (id,user_id,created_at,message) VALUES"+ placeholders;
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			int index = 1;
			for (int i = 0; i < accounts.size(); i++) {
	            stmt.setString(index++, ids.get(i));
	            stmt.setString(index++, accounts.get(i).getId());
	            stmt.setString(index++, created_at);
	            stmt.setString(index++, message);
	        }
	        stmt.executeUpdate();
	        return true;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
