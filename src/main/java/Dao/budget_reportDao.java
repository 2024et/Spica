package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.budget_reportBeans;

public class budget_reportDao {
	//レポート一覧取得
	public List<budget_reportBeans> getBudgetReportData(String group_id){
		List<budget_reportBeans> list = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
       
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT id, name FROM budget_report WHERE group_id = ?";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, group_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
            	String name = rs.getString("name");
            	
            	budget_reportBeans beans = new budget_reportBeans(
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
	
	//reportに登録
	public boolean insertBudgetData_report(Connection con,String id,String group_id,String created_at, String name) {
        PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO budget_report (id,group_id,created_at,name) VALUES (?,?,?,?);";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, group_id);
			stmt.setString(3, created_at);
			stmt.setString(4, name);
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//budgetに登録
	public boolean insertBudgetData_category(Connection con,String id,String budget_id,String name, Integer amount) {
        PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO category_budget (id,budget_report_id,name,amount) VALUES (?,?,?,?);";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, budget_id);
			stmt.setString(3, name);
			stmt.setInt(4, amount);
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
