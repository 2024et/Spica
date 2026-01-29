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
        PreparedStatement stmt = null;
        ResultSet rs = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT * FROM category WHERE group_id = ? AND status = '稼働中';";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, group_id);
			rs = stmt.executeQuery();
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
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
