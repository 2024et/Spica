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
		String sql = "SELECT id, name FROM budget_report WHERE group_id = ? ORDER BY created_at DESC";

		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			
			stmt.setString(1, group_id);
			 try (ResultSet rs = stmt.executeQuery()){
				while (rs.next()) {
					String id = rs.getString("id");
	            	String name = rs.getString("name");
	            	
	            	budget_reportBeans beans = new budget_reportBeans(
	                        id,
	                        name
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
	
	//reportに登録
	public boolean insertBudgetData_report(Connection con,String id,String group_id,String created_at, String name) {

        String sql = "INSERT INTO budget_report (id,group_id,created_at,name) VALUES (?,?,?,?);";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			
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

        String sql = "INSERT INTO category_budget (id,budget_report_id,name,amount) VALUES (?,?,?,?);";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			
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
	//書類名取得
	public String getBudgetReportData_list(String id){
		String sql = "SELECT name FROM budget_report WHERE id = ?";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			String name = null;
			stmt.setString(1, id);
			 try (ResultSet rs = stmt.executeQuery()){
				if(rs.next()) {
	            	name = rs.getString("name");
	            }
			 }

			return name;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//予算額の取得
	public List<budget_reportBeans> getBudgetData(String budget_report_id) {
		List<budget_reportBeans> list = new ArrayList<>();
		String sql = "SELECT name, amount FROM category_budget WHERE budget_report_id = ?";

		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			
			stmt.setString(1, budget_report_id);
			 try (ResultSet rs = stmt.executeQuery()){
				while (rs.next()) {
	            	String name = rs.getString("name");
	            	int amount = rs.getInt("amount");
	            	
	            	budget_reportBeans beans = new budget_reportBeans(
	                        name,
	                        amount
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
	
	//IDの取得
	public String getBudgetID(String acc_id){
		String sql = "SELECT budget_id FROM account_report WHERE id = ?";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			String budget_id = null;
			stmt.setString(1, acc_id);
			 try (ResultSet rs = stmt.executeQuery()){
				if(rs.next()) {
	            	budget_id = rs.getString("budget_id");
	            }
			 }

			return budget_id;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
