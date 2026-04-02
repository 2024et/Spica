package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import Beans.accountBeans;
import Beans.purchase_requestBeans;
import Dao.DBUtil;
import Dao.financialDao;
import Dao.logDao;
import Dao.noticeDao;
import Dao.purchase_requestDao;

public class purchase_request_formLogic {
	//備品購入申請
	public boolean insertRequestData(purchase_requestBeans beans, String log) {
		financialDao fi_dao = new financialDao();
		purchase_requestDao pr_dao = new purchase_requestDao();
		logDao log_dao = new logDao();
		noticeDao notice_dao = new noticeDao();
		String type = "申請";
		String text = "備品購入希望申請が提出されました。";
		signupLogic signup_logic = new signupLogic();
		String notice_id = signup_logic.RandomID();
		String notice_id_group = signup_logic.RandomID();   
		
		try {
			Connection con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean fi_completeFlag = fi_dao.insertRequestData_financial(con,beans,type);
			
			if(!fi_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean tr_completeFlag = pr_dao.insertrequestData_request(con,beans);
					
			if(!tr_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean log_completeFlag = log_dao.insertLog(con,beans.getGroup_id(),log);
			
			if(!log_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean notice_completeFlag = notice_dao.insertNotice(con,notice_id, beans.getUser_id(), text);
			
			if(!notice_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean notice_completeFlag_g = notice_dao.insertNotice(con, notice_id_group, beans.getGroup_id(), text);
			
			if(!notice_completeFlag_g) {
				con.rollback();
				return false;
			}
			
			con.commit();
			
			managementLogic men_logic = new managementLogic();
			List<accountBeans> account = men_logic.getBoardMember(beans.getGroup_id());
			
			MailUtil mail = new MailUtil();
			
			String subject = "【Spica】備品購入希望申請書が提出されました。";
			
			String html = "<html>" +
					"<body style='font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;'>" +

					"<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px;'>" +

					"<h2 style='color:#333333;'>備品購入希望申請書が提出されました。</h2>" +

					"<p style='font-size:14px; color:#555555;'>備品購入希望申請書が提出されました。対応をお願いいたします。</p>" +

					"</div>" +
					"</body>" +
					"</html>";
			for(accountBeans to : account) {
				mail.sendEmail(to.getEmail(),subject,html);
			}
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
