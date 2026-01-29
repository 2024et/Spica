package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Beans.projectBeans;

public class projectDao {
	//稼働中のプロジェクトの全件取得
		public List<projectBeans> getProjectData(String group_id) {
			List<projectBeans> list = new ArrayList<>();
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
			try {
				Connection con = DBUtil.getConnection();
				String sql = "SELECT * FROM balance_project WHERE group_id = ? AND status = '稼働中';";
				
				stmt = con.prepareStatement(sql);
				stmt.setString(1, group_id);
				rs = stmt.executeQuery();
				while (rs.next()) {
	            	String id = rs.getString("id");
	            	LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();
	            	String name = rs.getString("name");
	            	String status = rs.getString("status");
	            	
	            	projectBeans beans = new projectBeans(
	                        id,
	                        group_id,
	                        created_at,
	                        name,
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
