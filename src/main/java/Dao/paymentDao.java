package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Beans.accountBeans;
import Beans.accountPaymentBeans;

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
	
	//支払い状況の取得
	public List<accountPaymentBeans> getPaymentUser(String group_id){
		List<accountPaymentBeans> list = new ArrayList<>();
		String sql = "SELECT p.id AS id, a.user_name AS user_name, m.end_date AS date, p.status AS status, a.role_type AS role FROM payment AS p INNER JOIN account AS a ON p.user_id = a.id INNER JOIN membership_fee AS m ON p.membership_fee_id = m.id WHERE a.group_id = ?;";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			stmt.setString(1, group_id);
			try (ResultSet rs = stmt.executeQuery()){
				while (rs.next()) {
	            	
	            	accountPaymentBeans beans = new accountPaymentBeans(
	            			rs.getString("id"),
	                        rs.getString("user_name"),
	                        rs.getString("date"),
	                        rs.getString("status"),
	                        rs.getString("role")
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
	
	//支払い状況の更新
	public boolean updatePaymentStatus(String id, String status) {
		PreparedStatement stmt = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "UPDATE payment SET status = ? WHERE id = ?";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, status);
			stmt.setString(2, id);			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
