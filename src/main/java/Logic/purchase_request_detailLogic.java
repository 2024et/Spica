package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Beans.accountBeans;
import Beans.chatBeans;
import Beans.purchase_requestBeans;
import Dao.DBUtil;
import Dao.accountDao;
import Dao.chatDao;
import Dao.financialDao;
import Dao.logDao;
import Dao.noticeDao;
import Dao.purchase_requestDao;

public class purchase_request_detailLogic {
	
	//詳細の取得
	public purchase_requestBeans getRequestData(String id) {
		financialDao dao = new financialDao();
		return dao.getRequestData_detail(id);
	}
	
	//チャットの取得
	public List<chatBeans> getChat(String requestID){
		chatDao dao = new chatDao();
		return dao.getChat(requestID);
	}
	
	//申請の削除
	public boolean deleteRequestData(String id,String group_id, String log) {
		financialDao fi_dao = new financialDao();
		purchase_requestDao pr_dao = new purchase_requestDao();
		logDao log_dao = new logDao();
		try {
			Connection con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean fi_completeFlag = fi_dao.deleteRequestData_financial(con,id);
			
			if(!fi_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean tr_completeFlag = pr_dao.deleteRequestData_request(con,id);
					
			if(!tr_completeFlag) {
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
	
	//申請の変更
	public boolean updateRequestData(purchase_requestBeans beans,String log) {
		financialDao fi_dao = new financialDao();
		purchase_requestDao pr_dao = new purchase_requestDao();
		logDao log_dao = new logDao();
		try {
			Connection con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean fi_completeFlag = fi_dao.updateRequestData_financial(con,beans);
			
			if(!fi_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean tr_completeFlag = pr_dao.updateRequestData_request(con,beans);
					
			if(!tr_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean log_completeFlag = log_dao.insertLog(con,beans.getGroup_id(),log);
			
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
	
	//ステータスの変更
	public boolean updateStatus(String id,String status,String group_id, String log) {
		purchase_requestBeans detail = getRequestData(id);
		accountDao acc_dao = new accountDao();
		String applicant = null;
		if(detail.getUser_name() != null && !detail.getUser_name().isEmpty()) {
			applicant = acc_dao.getUserEmail(detail.getUser_id());
		}
		
		MailUtil mail = new MailUtil();
		
		String subject = "【Spica】申請中の購入希望について";
		
		String text = "<html>" +
				"<body style='font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;'>" +

				"<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px;'>" +

				"<h2 style='color:#333333;'>申請中の購入希望備品についてステータスの変更がありました。</h2>" +

				"<p style='font-size:14px; color:#555555;'>変更されたステータス：「"+ status +"」</p>" +

				"</div>" +
				"</body>" +
				"</html>";
		
        signupLogic signup_logic = new signupLogic();
        String notice_id = signup_logic.RandomID();
		purchase_requestDao pr_dao = new purchase_requestDao();
		logDao log_dao = new logDao();
		noticeDao notice_dao = new noticeDao();
		try {
			Connection con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean tr_completeFlag = pr_dao.updateReqStatusData_request(con,id,status);
			System.out.println(tr_completeFlag);
					
			if(!tr_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean log_completeFlag = log_dao.insertLog(con,group_id,log);
			
			if(!log_completeFlag) {
				con.rollback();				
				return false;
			}
			
			String message = "申請中の購入希望備品についてステータスの変更がありました。";
			
			if(detail.getUser_id() != null && !detail.getUser_id().isEmpty()) {
				boolean notice_completeFlag = notice_dao.insertNotice(con,notice_id, detail.getUser_id(), message);
				
				if(!notice_completeFlag) {
					con.rollback();				
					return false;
				}
			}
			
			con.commit();
			
			if(applicant != null && !applicant.isEmpty()){
			    mail.sendEmail(applicant, subject, text);
			}
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	//チャット送信
	public boolean sendMessage(String user_id, String request_id, String Message, String group_id) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		signupLogic logic = new signupLogic();
		String id = logic.RandomID();
		chatDao dao = new chatDao();
		boolean sendFlag = dao.sendMessage(id,user_id, request_id,now.format(dtf), Message);
		
		if(sendFlag) {
			purchase_requestBeans detail = getRequestData(request_id);
			String applicant_id = detail.getUser_id();
			accountDao acc_dao = new accountDao();
			String applicant = acc_dao.getUserEmail(applicant_id);
			
			managementLogic man_logic = new managementLogic();
			List<accountBeans> board = man_logic.getBoardMember(group_id);
			
			MailUtil mail = new MailUtil();
			
			String subject = "【Spica】メッセージ通知";
			
			String text = "<html>" +
					"<body style='font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;'>" +

					"<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px;'>" +

					"<h2 style='color:#333333;'>新しいやり取りが追加されました。</h2>" +

					"<p style='font-size:14px; color:#555555;'>「"+ Message +"」</p>" +

					"</div>" +
					"</body>" +
					"</html>";
			
			for(accountBeans to : board) {
				mail.sendEmail(to.getEmail(), subject, text);
			}
			
			if(applicant != null) {
				mail.sendEmail(applicant,subject,text);
			}			
			
			return true;
		}else {
			return false;
		}
	}
}
