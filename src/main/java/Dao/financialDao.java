package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.balanceBeans;
import Beans.purchase_requestBeans;

public class financialDao {
	//収支データの取得
	public List<balanceBeans> getBalanceData(String group_id,String year){
		List<balanceBeans> list = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
       
        
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT fi.id AS id, fi.group_id AS group_id, fi.created_at AS created_at, fi.name AS name, fi.item AS item, fi.amount AS amount, tr.project AS project, tr.category AS category, tr.memo AS memo, tr.type AS type FROM finance_record AS fi INNER JOIN transaction AS tr ON fi.id = tr.id WHERE fi.group_id = ? AND fi.created_at >= ? ORDER BY fi.created_at DESC;";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, group_id);
			stmt.setString(2, year);
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
	public boolean insertBalanceData_financial(Connection con,balanceBeans beans, String type) {
        PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO finance_record (id,group_id,created_at,name,item,amount,type) VALUES (?,?,?,?,?,?,?);";
			
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
	
	//収支の編集
	public boolean editBalanceData_financial(balanceBeans beans,String type) {
        PreparedStatement stmt = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "UPDATE finance_record SET id = ?, group_id = ?, created_at = ?, name = ?, item = ?, amount = ?, type = ? WHERE id = ?;";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, beans.getId());
			stmt.setString(2, beans.getGroup_id());
			stmt.setString(3, beans.getCreated_at());
			stmt.setString(4, beans.getName());
			stmt.setString(5, beans.getItem());
			stmt.setInt(6, beans.getAmount());
			stmt.setString(7, type);
			stmt.setString(8, beans.getId());
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//収支の検索
	public List<balanceBeans> searchBalanceData_financial(String group_id, String startDate, String endDate, String category, String project, String name, String item, String type, String keyword) {
		List<balanceBeans> list = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
		List<String> columns = new ArrayList<>();
		List<Object> prams = new ArrayList<>();
		String sql = "SELECT fi.id AS id, fi.group_id AS group_id, fi.created_at AS created_at, fi.name AS name, fi.item AS item, fi.amount AS amount, tr.project AS project, tr.category AS category, tr.memo AS memo, tr.type AS type FROM finance_record AS fi INNER JOIN transaction AS tr ON fi.id = tr.id";
		
		columns.add("fi.group_id = ?");
		prams.add(group_id);
		
		if(startDate != null && !startDate.isEmpty()) {
			columns.add("fi.created_at >= ?");
			prams.add(startDate);
		}
		
		if(endDate != null && !endDate.isEmpty()) {
			columns.add("fi.created_at <= ?");
			prams.add(endDate);
		}
		
		if(category != null && !category.isEmpty()) {
			columns.add("tr.category = ?");
			prams.add(category);
		}
		
		if(project != null && !project.isEmpty()) {
			columns.add("tr.project = ?");
			prams.add(project);
		}
		
		if(name != null && !name.isEmpty()) {
			columns.add("fi.name = ?");
			prams.add(name);
		}
		
		if(item != null && !item.isEmpty()) {
			columns.add("fi.item = ?");
			prams.add(item);
		}
		
		if(type != null && !type.isEmpty()) {
			columns.add("tr.type = ?");
			prams.add(type);
		}
		
		if(keyword != null && !keyword.isEmpty()) {
			columns.add("(fi.name LIKE ? OR fi.item LIKE ? OR tr.category LIKE ? OR tr.project LIKE ?)");
			String likeKeyword = "%" + keyword + "%";
			prams.add(likeKeyword);
			prams.add(likeKeyword);
			prams.add(likeKeyword);
			prams.add(likeKeyword);
		}
		
		sql += " WHERE " + String.join(" AND ", columns) + " ORDER BY fi.created_at DESC;";
		
		
		try {
			Connection con = DBUtil.getConnection();
			
			stmt = con.prepareStatement(sql);
			for(int i = 0; i < prams.size(); i++) {
				stmt.setObject(i+1, prams.get(i));
			}
			
			rs = stmt.executeQuery();
			while (rs.next()) {
            	String id = rs.getString("id");
            	String created_at = rs.getString("created_at");
            	String s_name = rs.getString("name");
            	String s_item = rs.getString("item");
            	int amount = rs.getInt("amount");
            	String s_project = rs.getString("project");
            	String s_category = rs.getString("category");
            	String memo = rs.getString("memo");            	
            	String s_type = rs.getString("type");
            	
            	balanceBeans beans = new balanceBeans(
                        id,
                        group_id,
                        created_at,
                        s_name,
                        s_item,
                        amount,
                        s_project,
                        s_category,
                        memo,
                        s_type
                    );
            	list.add(beans);  
            }
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	//収支データの削除
	public boolean deleteBalanceData_financial(Connection con,String id) {
        PreparedStatement stmt = null;
		try {
			String sql = "DELETE FROM finance_record WHERE id = ?";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//購入希望申請一覧取得
	public List<purchase_requestBeans> getRequestData(String group_id){
		List<purchase_requestBeans> list = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
       
        
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT fi.id AS id, fi.group_id AS group_id, fi.created_at AS created_at, fi.name AS name, fi.item AS item, fi.amount AS amount, pr.user_id AS user_id, ac.user_name AS user_name, pr.store_link AS store_link, pr.purpose AS purpose, pr.status AS status FROM finance_record AS fi INNER JOIN purchase_request AS pr ON fi.id = pr.id INNER JOIN account AS ac ON pr.user_id = ac.id WHERE fi.group_id = ? ORDER BY fi.created_at DESC;";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, group_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
            	String id = rs.getString("id");
            	String created_at = rs.getString("created_at");
            	String name = rs.getString("name");
            	String item = rs.getString("item");
            	int amount = rs.getInt("amount");
            	String user_id = rs.getString("user_id");
            	String user_name = rs.getString("user_name");
            	String store_link = rs.getString("store_link");
            	String purpose = rs.getString("purpose");            	
            	String status = rs.getString("status");
            	
            	purchase_requestBeans beans = new purchase_requestBeans(
                        id,
                        group_id,
                        created_at,
                        name,
                        item,
                        amount,
                        user_id,
                        user_name,
                        store_link,
                        purpose,
                        status
                    );
            	list.add(beans);  
            }
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//備品購入希望申請
	public boolean insertRewuestData_financial(Connection con,purchase_requestBeans beans, String type) {
        PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO finance_record (id,group_id,created_at,name,item,amount,type) VALUES (?,?,?,?,?,?,?);";
			
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
	//購入希望申請詳細取得
	public purchase_requestBeans getRequestData_detail(String request_id){
        PreparedStatement stmt = null;
        ResultSet rs = null;
       
        
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT fi.id AS id, fi.group_id AS group_id, fi.created_at AS created_at, fi.name AS name, fi.item AS item, fi.amount AS amount, pr.user_id AS user_id, ac.user_name AS user_name, pr.store_link AS store_link, pr.purpose AS purpose, pr.status AS status FROM finance_record AS fi INNER JOIN purchase_request AS pr ON fi.id = pr.id INNER JOIN account AS ac ON pr.user_id = ac.id WHERE fi.id = ?;";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, request_id);
			rs = stmt.executeQuery();
			if (rs.next()) {
            	String id = rs.getString("id");
            	String group_id = rs.getString("group_id");
            	String created_at = rs.getString("created_at");
            	String name = rs.getString("name");
            	String item = rs.getString("item");
            	int amount = rs.getInt("amount");
            	String user_id = rs.getString("user_id");
            	String user_name = rs.getString("user_name");
            	String store_link = rs.getString("store_link");
            	String purpose = rs.getString("purpose");            	
            	String status = rs.getString("status");
            	
            	return new purchase_requestBeans(
                        id,
                        group_id,
                        created_at,
                        name,
                        item,
                        amount,
                        user_id,
                        user_name,
                        store_link,
                        purpose,
                        status
                    );		
			}else {
				return null;
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//備品購入希望申請の削除
	public boolean deleteRequestData_financial(Connection con,String id) {
        PreparedStatement stmt = null;
		try {
			String sql = "DELETE FROM finance_record WHERE id = ?";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//備品購入希望申請の変更
	public boolean updateRequestData_financial(Connection con,purchase_requestBeans beans) {
        PreparedStatement stmt = null;
		try {
			String sql = "UPDATE finance_record SET created_at  = ?,name  = ?,item  = ?,amount  = ? WHERE id = ?;";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, beans.getCreated_at());
			stmt.setString(2, beans.getName());
			stmt.setString(3, beans.getItem());
			stmt.setInt(4, beans.getAmount());
			stmt.setString(5, beans.getId());
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
