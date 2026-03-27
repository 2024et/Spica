package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.account_reportBeans;

public class account_reportDao {
	//一覧リスト取得
	public List<account_reportBeans> getAccountReportData(String group_id) {
		List<account_reportBeans> list = new ArrayList<>();

        String sql = "SELECT id,name FROM account_report WHERE group_id = ? ORDER BY created_at DESC;";
        
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    )  {
			stmt.setString(1, group_id);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
	            	String id = rs.getString("id");
	            	String name = rs.getString("name");
	            	
	            	account_reportBeans beans = new account_reportBeans(
	                        id,
	                        name
	                    );
	            	list.add(beans);  
	            }
			}		
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	//新規登録
	public boolean insertReportData(Connection con,account_reportBeans beans) {

        String sql = "INSERT INTO account_report (id,group_id,budget_id,created_at,name,start_period,end_period) VALUES (?,?,?,?,?,?,?);";
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, beans.getId());
			stmt.setString(2, beans.getGroup_id());
			stmt.setString(3, beans.getBudget_id());
			stmt.setString(4, beans.getCreated_at());
			stmt.setString(5, beans.getName());
			stmt.setString(6, beans.getStart_period());
			stmt.setString(7, beans.getEnd_period());
			
			
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//会計報告の詳細情報取得
	public account_reportBeans getRange(String id) {
        String sql = "SELECT id,group_id,name,start_period,end_period FROM account_report WHERE id = ?;";
        
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    )  {
			stmt.setString(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					String group_id = rs.getString("group_id");
					String name = rs.getString("name");
	            	String start = rs.getString("start_period");
	            	String end = rs.getString("end_period");
	            	return new account_reportBeans(id,group_id,name,start,end);
	            }else {
	            	return null;
	            }
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
