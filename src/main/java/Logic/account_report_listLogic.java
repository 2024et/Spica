package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Beans.account_reportBeans;
import Dao.DBUtil;
import Dao.account_reportDao;
import Dao.logDao;

public class account_report_listLogic {
	//一覧取得
	public List<account_reportBeans> getAccountReportData(String group_id){
		account_reportDao dao = new account_reportDao();
		return dao.getAccountReportData(group_id);
	}
	
	//新規登録
	public boolean insertReportData(String group_id,String name, String start, String end, String budget_id, String log) {
		signupLogic logic = new signupLogic();
		String id = logic.RandomID();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String created_at = now.format(dtf);
		
		account_reportDao acc_dao = new account_reportDao();
		logDao log_dao = new logDao();
		
		account_reportBeans beans = new account_reportBeans(id,group_id,budget_id,created_at,name,start,end);
		
		try {
			Connection con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean acc_completeFlag = acc_dao.insertReportData(con,beans);
			
			if(!acc_completeFlag) {
				con.rollback();
				return false;
			}

			
			boolean log_completeFlag = log_dao.insertLog(con,group_id,log);
			
			if(!log_completeFlag) {
				con.rollback();
				return false;
			}
			
			con.commit();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
