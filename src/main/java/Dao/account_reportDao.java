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
			String sql = "SELECT * FROM account_report WHERE id = ?;";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, group_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
            	String id = rs.getString("id");
            	String budget_id = rs.getString("budget_id");
            	String created_at = rs.getString("created_at");
            	String name = rs.getString("name");
            	String start_period = rs.getString("start_period");
            	String end_period = rs.getString("end_period");
            	
            	account_reportBeans beans = new account_reportBeans(
                        id,
                        group_id,
                        budget_id,
                        created_at,
                        name,
                        start_period,
                        end_period
                    );
            	list.add(beans);  
            }
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
