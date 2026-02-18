package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Beans.purchase_requestBeans;

public class purchase_requestDao {
	//備品購入希望申請
	public boolean insertrequestData_request(Connection con,purchase_requestBeans beans) {
        PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO purchase_request (id,user_id,store_link,purpose,status) VALUES (?,?,?,?,?);";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, beans.getId());
			stmt.setString(2, beans.getUser_id());
			stmt.setString(3, beans.getStore_link());
			stmt.setString(4, beans.getPurpose());
			stmt.setString(5, beans.getStatus());
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	//備品購入希望申請の削除
		public boolean deleteRequestData_request(Connection con,String id) {
	        PreparedStatement stmt = null;
			try {
				String sql = "DELETE FROM purchase_request WHERE id = ?;";
				
				stmt = con.prepareStatement(sql);
				stmt.setString(1,id);
				
				
				int result = stmt.executeUpdate();
				if(result > 0) {return true;}
				else {return false;}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
}
