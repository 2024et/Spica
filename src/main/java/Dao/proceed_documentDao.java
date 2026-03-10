package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.documentApproverlDTOBeans;
import Beans.proceed_documentsBeans;

public class proceed_documentDao {
	public boolean insertDocumentData(proceed_documentsBeans beans) {
		PreparedStatement stmt = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "INSERT INTO proceed_document (id,group_id,created_at,name,pdf_path,status) VALUES (?,?,?,?,?,?)";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, beans.getId());
			stmt.setString(2, beans.getGroup_id());
			stmt.setString(3, beans.getCreated_at());
			stmt.setString(4, beans.getName());
			stmt.setString(5, beans.getPath());
			stmt.setString(6, beans.getStatus());
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public List<documentApproverlDTOBeans> getProceedDocumentsData(String group_id){
		List<documentApproverlDTOBeans> list = new ArrayList<>();
		String sql = "SELECT p.id, p.created_at, p.name, p.pdf_path, p.comment, MAX(CASE WHEN a.role = '会計' THEN a.status END) AS accountant, MAX(CASE WHEN a.role = '副代表'  THEN a.status END) AS vice_president, MAX(CASE WHEN a.role = '代表' THEN a.status END) AS president, MAX(CASE WHEN a.role = '顧問' THEN a.status END) AS advisor FROM proceed_document AS p LEFT JOIN approver AS a ON p.id = a.document_id WHERE p.group_id = ? AND p.status = ? GROUP BY p.id, p.created_at, p.name, p.pdf_path ";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			
			stmt.setString(1, group_id);
			stmt.setString(2, "未提出");
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
	            	
	            	documentApproverlDTOBeans beans = new documentApproverlDTOBeans(
	            			 rs.getString("id"),
	            			 group_id,
	            			 rs.getString("created_at"),
	            			 rs.getString("name"),
	            			 rs.getString("pdf_path"),
	            			 rs.getString("comment"),
	            			 rs.getString("accountant"),
	            			 rs.getString("vice_president"),
	            			 rs.getString("president"),
	            			 rs.getString("advisor")
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
}
