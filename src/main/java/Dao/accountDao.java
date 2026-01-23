package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class accountDao {
	

	
	public int mailDupli(String mail) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT 1 FROM account WHERE user_email = ?;";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, mail);
			rs = stmt.executeQuery();
			if(!rs.next()) {
				return 0;
			}else {
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 2;
		}
	}
	
	public boolean tempAccount(String id,String name, String mail, String password) {
        PreparedStatement stmt = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "INSERT INTO temp_account (id,user_name,password,user_email) VALUES (?,?,?,?)";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, name);
			stmt.setString(3, password);
			stmt.setString(4, mail);
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
