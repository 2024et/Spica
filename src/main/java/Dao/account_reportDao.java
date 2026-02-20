package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.account_reportBeans;

public class account_reportDao {
	//一覧取得
	public List<account_reportBeans> getAccountReportData(String group_id) {
		List<account_reportBeans> list = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
       
        
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT id,name FROM account_report WHERE group_id = ?;";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, group_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
            	String id = rs.getString("id");
            	String name = rs.getString("name");
            	
            	account_reportBeans beans = new account_reportBeans(
                        id,
                        name
                    );
            	list.add(beans);  
            }
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//新規登録
	public boolean insertReportData(Connection con,account_reportBeans beans) {
        PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO account_report (id,group_id,budget_id,created_at,name,start_period,end_period) VALUES (?,?,?,?,?,?,?);";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, beans.getId());
			stmt.setString(2, beans.getGroup_id());
			stmt.setString(3, beans.getBudget_id());
			stmt.setString(4, beans.getCreated_at());
			stmt.setString(5, beans.getName());
			stmt.setString(6, beans.getStart_period());
			stmt.setString(7, beans.getEnd_period());
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
