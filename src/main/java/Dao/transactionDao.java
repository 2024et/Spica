package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Beans.balanceBeans;

public class transactionDao {

	//新規収支の追加
	public boolean insertBalanceData_transaction(Connection con,balanceBeans beans) {
        PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO transaction (id,project,category,memo,type) VALUES (?,?,?,?,?);";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, beans.getId());
			stmt.setString(2, beans.getProject());
			stmt.setString(3, beans.getCategory());
			stmt.setString(4, beans.getMemo());
			stmt.setString(5, beans.getType());
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//収支の編集
	public boolean editBalanceData_transaction(balanceBeans beans) {
        PreparedStatement stmt = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "UPDATE transaction SET id = ?, project = ?, category = ?, memo = ?, type = ? WHERE id = ?;";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, beans.getId());
			stmt.setString(2, beans.getProject());
			stmt.setString(3, beans.getCategory());
			stmt.setString(4, beans.getMemo());
			stmt.setString(5, beans.getType());
			stmt.setString(6, beans.getId());
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
