package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.projectBeans;

public class projectDao {
	//稼働中のプロジェクトの全件取得
	public List<projectBeans> getProjectData(String group_id) {
		List<projectBeans> list = new ArrayList<>();
		String sql = "SELECT * FROM balance_project WHERE group_id = ?;";
		try(
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    )  {			
			
			stmt.setString(1, group_id);
			try (ResultSet rs = stmt.executeQuery()){
				while (rs.next()) {
	            	String id = rs.getString("id");
	            	String created_at = rs.getString("created_at");
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
			}
			
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//プロジェクトの新規作成
	public boolean insertProjectData(Connection con,projectBeans beans) {
		String sql = "INSERT INTO balance_project (id,group_id,created_at,name,status) VALUES (?,?,?,?,?)";
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			
			
			stmt.setString(1, beans.getId());
			stmt.setString(2, beans.getGroup_id());
			stmt.setString(3, beans.getCreated_at());
			stmt.setString(4, beans.getName());
			stmt.setString(5, beans.getStatus());
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	//カテゴリの更新
	public boolean updateProjectData(Connection con,projectBeans beans) {

		String sql = "UPDATE balance_project SET name = ?,status = ? WHERE id = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			
			stmt.setString(1, beans.getName());
			stmt.setString(2, beans.getStatus());
			stmt.setString(3, beans.getId());			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
