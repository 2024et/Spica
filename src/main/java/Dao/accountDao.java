package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.accountBeans;
import Beans.noticeBeans;

public class accountDao {
	

	//メールの重複チェック
	public int mailDupli(String mail) {
        String sql = "SELECT 1 FROM account WHERE user_email = ?;";
		try(
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    )  {
			
			stmt.setString(1, mail);
			try (ResultSet rs = stmt.executeQuery()){
				if(!rs.next()) {
					return 0;
				}else {
					return 1;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return 2;
		}
	}
	//仮登録
	public boolean tempAccount(String id,String name, String mail, String password) {
        String sql = "INSERT INTO temp_account (id,user_name,password,user_email) VALUES (?,?,?,?)";
		try(
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    )  {
			
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
        accountBeans beans;
        String sql = "SELECT * FROM temp_account WHERE id = ?;";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			
			stmt.setString(1, id);
			try (ResultSet rs = stmt.executeQuery()){
				if(rs.next()) {
					
					String name =rs.getString("user_name");
					String pass =rs.getString("password");
					String email =rs.getString("user_email");
					
					String group_name = "";
					String group_id = "";
					String role = "";
					
					beans = new accountBeans(id,group_id,group_name,name,pass,email,role);
					return beans;
				}else {
					return null;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//本登録
	public boolean pushRegister(accountBeans beans) {
        String sql = "INSERT INTO account (id,group_id,user_name,password,user_email,role_type) VALUES (?,?,?,?,?,?)";
		try(
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    )  {
			
			
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
        accountBeans beans;
        String sql = "SELECT ac.id AS id, ac.group_id AS group_id, og.name AS group_name, ac.user_name AS user_name, ac.password AS password, ac.role_type AS role_type FROM account AS ac LEFT JOIN organizations AS og ON ac.group_id = og.invited_code WHERE ac.user_email = ?;";
		
		try(
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    )  {
			
			stmt.setString(1, email);
			try (ResultSet rs = stmt.executeQuery()){
				if(rs.next()) {
					
					String id =rs.getString("id");
					String group_id = rs.getString("group_id");
					String group_name = rs.getString("group_name");
					String name =rs.getString("user_name");
					String pass =rs.getString("password");
					String role = rs.getString("role_type");
					
					beans = new accountBeans(id,group_id,group_name,name,pass,email,role);
					return beans;
				}else {
					return null;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//IDの取得
	public String getUserID(String email) {
        String sql = "SELECT id FROM account WHERE user_email = ?;";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			
			stmt.setString(1, email);
			try (ResultSet rs = stmt.executeQuery()){
				if(rs.next()) {
					
					return rs.getString("id");
				}else {
					return null;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//パスワードの更新
	public boolean updatePassword(String id, String password) {
        String sql = "UPDATE account SET password = ? WHERE id = ?";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			
			stmt.setString(1, password);
			stmt.setString(2, id);
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	//団体に参加
	public boolean joinGroup(String id, String code) {
        String sql = "UPDATE account SET group_id = ? WHERE id = ?";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			
			stmt.setString(1, code);
			stmt.setString(2, id);
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	//通知の取得
	public List<noticeBeans> getNotice(String user_id){
		List<noticeBeans> list = new ArrayList<>();
        String sql = "SELECT * FROM notice WHERE user_id = ? ORDER BY created_at DESC;";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			
			stmt.setString(1, user_id);
			try (ResultSet rs = stmt.executeQuery()){
				while (rs.next()) {
	            	String id = rs.getString("id");
	            	String created_at = rs.getString("created_at");
	            	String message = rs.getString("message");
	            	
	            	noticeBeans beans = new noticeBeans(
	                        id,
	                        user_id,
	                        created_at,
	                        message
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
	
	//アカウント情報の変更
	public boolean updateInformation(String id, String name, String email, String code) {
        PreparedStatement stmt = null;
        
		List<String> columns = new ArrayList<>();
		List<Object> prams = new ArrayList<>();
		String sql = "UPDATE account SET ";
		if(name != null && !name.isEmpty()) {
			columns.add("user_name = ?");
			prams.add(name);
		}
		if(email != null && !email.isEmpty()) {
			columns.add("user_email = ?");
			prams.add(email);
		}
		if(code != null && !code.isEmpty()) {
			columns.add("group_id = ?");
			prams.add(code);
		}
		
		sql += String.join(" , ", columns);
		
		sql += (" WHERE id = ?");
		prams.add(id);
		

		
		try {
			Connection con = DBUtil.getConnection();
			
			stmt = con.prepareStatement(sql);
			for(int i = 0; i < prams.size(); i++) {
				stmt.setObject(i+1, prams.get(i));
			}			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	//アカウント削除
	public boolean deleteAccount(String id) {
        String sql = "DELETE FROM account WHERE id = ?";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			
			stmt.setString(1, id);
			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
