package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Beans.accountBeans;
import Beans.budget_reportBeans;
import Beans.categoryBeans;
import Dao.DBUtil;
import Dao.budget_reportDao;
import Dao.logDao;
import Dao.noticeDao;

public class budget_report_listLogic {
	//一覧取得
	public List<budget_reportBeans> getBudgetReportData(String group_id){
		budget_reportDao dao = new budget_reportDao();
		return dao.getBudgetReportData(group_id);
	}
	
	//カテゴリー取得
	public List<categoryBeans> getCategoryData(String group_id){
		financialLogic logic = new financialLogic();
		List<categoryBeans> category_list = logic.getCategoryData(group_id);
		List<categoryBeans> category_expend_list = new ArrayList<>();
		for(categoryBeans beans : category_list) {
			if(beans.getType().equals("支出")&&beans.getStatus().equals("稼働中")) {
				category_expend_list.add(beans);
			}
		}
		return category_expend_list;
	}
	//予算作成
	public boolean insertBudgetData(String name, String group_id, String log, Map<String, Integer> list) {
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
		
		String text = "予算計画書が公開されました。";
		
		signupLogic logic = new signupLogic();
		String budget_id = logic.RandomID();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String created_at = now.format(dtf);
		
		MailUtil mail = new MailUtil();
		
		String subject = "【Spica】予算計画書が公開されました。";
		
		String html = "<html>" +
				"<body style='font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;'>" +

				"<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px;'>" +

				"<h2 style='color:#333333;'>予算計画書が公開されました。</h2>" +

				"<p style='font-size:14px; color:#555555;'>所属団体の予算計画書が公開されました。</p>" +

				"</div>" +
				"</body>" +
				"</html>";
		
		budget_reportDao dao = new budget_reportDao();
		logDao log_dao = new logDao();
		noticeDao notice_dao = new noticeDao();
		Connection con = null;
		
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean br_completeFlag = dao.insertBudgetData_report(con,budget_id,group_id,created_at,name);
			
			if(!br_completeFlag) {
				con.rollback();
				return false;
			}
			
			for(Map.Entry<String, Integer> entry : list.entrySet()) {
				String id = logic.RandomID();
				boolean cb_completeFlag = dao.insertBudgetData_category(con,id,budget_id,entry.getKey(),entry.getValue());
				
				if(!cb_completeFlag) {
					con.rollback();
					return false;
				}
			}

			
			boolean log_completeFlag = log_dao.insertLog(con,group_id,log);
			
			if(!log_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean notice_completeFlag = notice_dao.insertNotices(con, ids, account, created_at,text);
			
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
