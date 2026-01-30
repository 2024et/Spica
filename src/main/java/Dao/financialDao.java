package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.balanceBeans;

public class financialDao {
	//収支データの取得
	public List<balanceBeans> getBalanceData(String group_id){
		List<balanceBeans> list = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT fi.id AS id, fi.group_id AS group_id, fi.created_at AS created_at, fi.name AS name, fi.item AS item, fi.amount AS amount, tr.project AS project, tr.category AS category, tr.memo AS memo, tr.type AS type FROM finance_record AS fi INNER JOIN transaction AS tr ON fi.id = tr.id WHERE fi.group_id = ?;";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, group_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
            	String id = rs.getString("id");
            	String created_at = rs.getString("created_at");
            	String name = rs.getString("name");
            	String item = rs.getString("item");
            	int amount = rs.getInt("amount");
            	String project = rs.getString("project");
            	String category = rs.getString("category");
            	String memo = rs.getString("memo");            	
            	String type = rs.getString("type");
            	
            	balanceBeans beans = new balanceBeans(
                        id,
                        group_id,
                        created_at,
                        name,
                        item,
                        amount,
                        project,
                        category,
                        memo,
                        type
                    );
            	list.add(beans);  
            }
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
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
	
	//収支データの削除
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
