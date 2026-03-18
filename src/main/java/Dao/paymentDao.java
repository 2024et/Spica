package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import Beans.accountBeans;

public class paymentDao {
	//名簿の作成
	public boolean makeFeeManagement(Connection con, String id, List<String>ids, List<accountBeans> accounts) {
		String placeholder = "(?,?,?,?)";
		String placeholders = String.join(", ",Collections.nCopies(accounts.size(), placeholder));
		
		String sql = "INSERT INTO payment (id,user_id,membership_fee_id,status) VALUES " + placeholders;
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			int index = 1;
			for (int i = 0; i < accounts.size(); i++) {
	            stmt.setString(index++, ids.get(i));
	            stmt.setString(index++, accounts.get(i).getId());
	            stmt.setString(index++, id);
	            stmt.setString(index++, "未払い");
	        }
	        stmt.executeUpdate();
	        return true;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
