package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.approverBeans;
import Beans.documentApproverlDTOBeans;
import Beans.proceed_documentsBeans;

public class proceed_documentDao {
	//書類の作成
	public boolean insertDocumentData(Connection con,proceed_documentsBeans beans) {
		String sql = "INSERT INTO proceed_document (id,group_id,created_at,name,pdf_path,status,comment) VALUES (?,?,?,?,?,?,?)";
		
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, beans.getId());
			stmt.setString(2, beans.getGroup_id());
			stmt.setString(3, beans.getCreated_at());
			stmt.setString(4, beans.getName());
			stmt.setString(5, beans.getPath());
			stmt.setString(6, beans.getStatus());
			stmt.setString(7, beans.getComment());
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	//書類の一覧取得
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
	
	public List<documentApproverlDTOBeans> getSubmitedDocumentsData(String group_id){
		List<documentApproverlDTOBeans> list = new ArrayList<>();
		String sql = "SELECT id, name, created_at, pdf_path FROM proceed_document WHERE group_id = ? AND status = ?";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			
			stmt.setString(1, group_id);
			stmt.setString(2, "提出");
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
	            	
	            	documentApproverlDTOBeans beans = new documentApproverlDTOBeans(
	            			 rs.getString("id"),
	            			 group_id,
	            			 rs.getString("created_at"),
	            			 rs.getString("name"),
	            			 rs.getString("pdf_path")
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
	//書類の承認(コメント挿入)
	public boolean updateComment(Connection con,approverBeans beans, String comment) {
		String sql = "UPDATE proceed_document SET comment = ? WHERE id = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, comment);
			stmt.setString(2, beans.getDocument_id());		
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {
				return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//書類の編集
	public boolean updateDocumentData(Connection con,proceed_documentsBeans beans, String filePath, String reset) {

		String sql = "UPDATE proceed_document SET name = ?";
		
		boolean pdf_flag = false;
		boolean com_flag = false;
		if(filePath != null) {
			sql += ",pdf_path = ?";
			pdf_flag = true;
		}
		if(reset != null) {
			sql += ",comment = ?";
			com_flag = true;
		}
		sql += " WHERE id = ?";
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, beans.getName());
			if(pdf_flag && !com_flag) {
				stmt.setString(2,filePath);
				stmt.setString(3,beans.getId());
			}else if(!pdf_flag && com_flag) {
				stmt.setString(2,"");
				stmt.setString(3,beans.getId());
			}else if(pdf_flag && com_flag) {
				stmt.setString(2,filePath);
				stmt.setString(3,"");
				stmt.setString(4,beans.getId());
			}else {
				stmt.setString(2,beans.getId());
			}

			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//書類の削除
	public boolean deleteDocumentData(Connection con,proceed_documentsBeans beans) {
		String sql = "DELETE FROM proceed_document WHERE id = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, beans.getId());
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//書類の提出
	public boolean submitedDocumentData(String id) {
		String sql = "UPDATE proceed_document SET status = ? WHERE id = ?";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			stmt.setString(1, "提出");
			stmt.setString(2, id);
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	//書類の未提出化
	public boolean noSubmitedDocumentData(String id) {
		String sql = "UPDATE proceed_document SET status = ? WHERE id = ?";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			stmt.setString(1, "未提出");
			stmt.setString(2, id);
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
