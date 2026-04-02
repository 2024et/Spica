package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Beans.accountBeans;
import Beans.account_reportBeans;
import Dao.DBUtil;
import Dao.account_reportDao;
import Dao.logDao;
import Dao.noticeDao;

public class account_report_listLogic {
	//一覧取得
	public List<account_reportBeans> getAccountReportData(String group_id){
		account_reportDao dao = new account_reportDao();
		return dao.getAccountReportData(group_id);
	}
	
	//新規登録
	public boolean insertReportData(String group_id,String name, String start, String end, String budget_id, String log) {
		memberLogic mem_logic = new memberLogic();
		List<accountBeans> account = mem_logic.getMembership(group_id);
		
		if(account == null) {
			System.out.println("account Null");
			return false;
		}
		
		List<String> ids = new ArrayList<>();
		
		signupLogic signup_logic = new signupLogic();
		
		for(int i = 0; i < account.size(); i++) {
			String id = signup_logic.RandomID();
			ids.add(id);
		}
		
		String text = "会計報告書が公開されました。";
		
		signupLogic logic = new signupLogic();
		String id = logic.RandomID();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String created_at = now.format(dtf);
		
		account_reportDao acc_dao = new account_reportDao();
		logDao log_dao = new logDao();
		noticeDao notice_dao = new noticeDao();
		
		MailUtil mail = new MailUtil();
		
		String subject = "【Spica】会計報告書が公開されました。";
		
		String html = "<html>" +
				"<body style='font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;'>" +

				"<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px;'>" +

				"<h2 style='color:#333333;'>会計報告書が公開されました。</h2>" +

				"<p style='font-size:14px; color:#555555;'>所属団体の会計報告書が公開されました。</p>" +

				"</div>" +
				"</body>" +
				"</html>";
		
		account_reportBeans beans = new account_reportBeans(id,group_id,budget_id,created_at,name,start,end);
		Connection con = null;
		try {
			con = DBUtil.getConnection();
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
			
			boolean notice_completeFlag = notice_dao.insertNotices(con, ids, account,text);
			
			if(!notice_completeFlag) {
				con.rollback();
				return false;
			}
			
			con.commit();
			
			for(accountBeans to : account) {
				mail.sendEmail(to.getEmail(),subject,html);
			}
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
	        try {
	            if(con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
