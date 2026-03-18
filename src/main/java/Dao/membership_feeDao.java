package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Beans.membership_feeBeans;

public class membership_feeDao {
	//最新の会費を取得
	public membership_feeBeans getMembershipFee(String group_id) {
		String sql = "SELECT * FROM membership_fee WHERE group_id = ? ORDER BY end_date DESC LIMIT 1;";
		try (
		        Connection con = DBUtil.getConnection();
		        PreparedStatement stmt = con.prepareStatement(sql);
		    ) {
			
			stmt.setString(1, group_id);
			try (ResultSet rs = stmt.executeQuery()){
				if(rs.next()) {
					membership_feeBeans beans = new membership_feeBeans(
							rs.getString("id"),
							group_id,
							rs.getString("start_date"),
							rs.getString("end_date"),
							rs.getInt("fee")
							);
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
	
	//会費の登録
	public boolean insertMembershipFee(Connection con,membership_feeBeans beans) {
		String sql = "INSERT INTO membership_fee (id,group_id,start_date,end_date,fee) VALUES (?,?,?,?,?);";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, beans.getId());
			stmt.setString(2, beans.getGroup_id());
			stmt.setString(3, beans.getStart_date());
			stmt.setString(4, beans.getEnd_date());
			stmt.setInt(5, beans.getFee());			
			
			int result = stmt.executeUpdate();
			if(result > 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
