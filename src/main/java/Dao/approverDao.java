package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Beans.approverBeans;
import Beans.proceed_documentsBeans;

public class approverDao {
	//書類の承認
	public boolean insertApproverData(approverBeans beans) {
		PreparedStatement stmt = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "INSERT INTO approver (id,document_id,role,status) VALUES (?,?,?,?)";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, beans.getId());
			stmt.setString(2, beans.getDocument_id());
			stmt.setString(3, beans.getRole());
			stmt.setString(4, beans.getStatus());			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//書類の承認(トランザクション)
	public boolean insertDisApproverData(Connection con,approverBeans beans) {
		String sql = "INSERT INTO approver (id,document_id,role,status) VALUES (?,?,?,?)";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, beans.getId());
			stmt.setString(2, beans.getDocument_id());
			stmt.setString(3, beans.getRole());
			stmt.setString(4, beans.getStatus());			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//承認削除
	public boolean deleteApprover(Connection con,proceed_documentsBeans beans) {
		String sql = "DELETE FROM approver WHERE document_id = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, beans.getId());
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
