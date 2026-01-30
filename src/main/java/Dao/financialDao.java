package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Beans.balanceBeans;

public class financialDao {
	
	//新規収支の追加
	public boolean insertBalanceData_financial(balanceBeans beans, String type) {
        PreparedStatement stmt = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "INSERT INTO finance_record (id,group_id,created_at,name,item,amount,type) VALUES (?,?,?,?,?,?,?)";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, beans.getId());
			stmt.setString(2, beans.getGroup_id());
			stmt.setString(3, beans.getCreated_at());
			stmt.setString(4, beans.getName());
			stmt.setString(5, beans.getItem());
			stmt.setInt(6, beans.getAmount());
			stmt.setString(7, type);
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteBalanceData(balanceBeans beans) {
        PreparedStatement stmt = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "DELETE FROM finance_record WHERE id = ?";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, beans.getId());
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
