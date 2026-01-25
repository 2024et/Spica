package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Beans.accountBeans;

public class accountDao {
	

	//メールの重複チェック
	public int mailDupli(String mail) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT 1 FROM account WHERE user_email = ?;";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, mail);
			rs = stmt.executeQuery();
			if(!rs.next()) {
				return 0;
			}else {
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 2;
		}
	}
	//仮登録
	public boolean tempAccount(String id,String name, String mail, String password) {
        PreparedStatement stmt = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "INSERT INTO temp_account (id,user_name,password,user_email) VALUES (?,?,?,?)";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, name);
			stmt.setString(3, password);
			stmt.setString(4, mail);
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	//仮登録データの取得
	public accountBeans getTempData(String id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        accountBeans beans;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT * FROM temp_account WHERE id = ?;";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			if(rs.next()) {
				
				String name =rs.getString("user_name");
				String pass =rs.getString("password");
				String email =rs.getString("user_email");
				
				String group_id = "";
				String role = "";
				
				beans = new accountBeans(id,group_id,name,pass,email,role);
				return beans;
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//本登録
	public boolean pushRegister(accountBeans beans) {
        PreparedStatement stmt = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "INSERT INTO account (id,group_id,user_name,password,user_email,role_type) VALUES (?,?,?,?,?,?)";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, beans.getId());
			stmt.setString(2, beans.getGroup_id());
			stmt.setString(3, beans.getName());
			stmt.setString(4, beans.getPass());
			stmt.setString(5, beans.getEmail());
			stmt.setString(6, beans.getRole());
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	//ログイン
	public accountBeans login(String email) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        accountBeans beans;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT * FROM account WHERE user_email = ?;";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if(rs.next()) {
				
				String id =rs.getString("id");
				String group_id = rs.getString("group_id");
				String name =rs.getString("user_name");
				String pass =rs.getString("password");
				String role = rs.getString("role_type");
				
				beans = new accountBeans(id,group_id,name,pass,email,role);
				return beans;
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//IDの取得
	public String getUserID(String email) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT id FROM account WHERE user_email = ?;";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if(rs.next()) {
				
				return rs.getString("id");
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
