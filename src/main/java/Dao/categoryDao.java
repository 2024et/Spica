package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.categoryBeans;

public class categoryDao {
	//稼働中のカテゴリの全件取得
	public List<categoryBeans> getCategoryData(String group_id) {
		List<categoryBeans> list = new ArrayList<>();
		String sql = "SELECT * FROM category WHERE group_id = ? AND status = '稼働中';";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			stmt.setString(1, group_id);
			try (ResultSet rs = stmt.executeQuery()){
				while (rs.next()) {
	            	String id = rs.getString("id");
	            	String name = rs.getString("name");
	            	String type = rs.getString("type");
	            	String status = rs.getString("status");
	            	
	            	categoryBeans beans = new categoryBeans(
	                        id,
	                        group_id,
	                        name,
	                        type,
	                        status
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
	//カテゴリの新規登録
	public boolean insertCategoryData(categoryBeans beans) {
		PreparedStatement stmt = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "INSERT INTO category (id,group_id,name,type,status) VALUES (?,?,?,?,?)";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, beans.getId());
			stmt.setString(2, beans.getGroup_id());
			stmt.setString(3, beans.getName());
			stmt.setString(4, beans.getType());
			stmt.setString(5, beans.getStatus());
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
