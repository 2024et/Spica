package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class organizationsDao {
	//団体の存在確認
	public boolean searchGroup(String code) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT * FROM organizations WHERE invited_code = ?;";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, code);
			rs = stmt.executeQuery();
			if(rs.next()) {
				
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//団体作成
	public boolean makeGroup(String id, String code, String name) {
        PreparedStatement stmt = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "INSERT INTO organizations (id,invited_code,name) VALUES (?,?,?)";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, code);
			stmt.setString(3, name);
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
