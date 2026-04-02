package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Beans.accountBeans;
import Beans.accountPaymentBeans;
import Beans.membership_feeBeans;
import Dao.DBUtil;
import Dao.accountDao;
import Dao.logDao;
import Dao.membership_feeDao;
import Dao.noticeDao;
import Dao.paymentDao;

public class memberLogic {
	//最新の会費の取得
	public membership_feeBeans getMembershipFee(String group_id) {
		membership_feeDao dao = new membership_feeDao();
		return dao.getMembershipFee(group_id);
	}
	//会費の設定
	public boolean insertMembershipFee(membership_feeBeans fee, String log) {
		List<accountBeans> account = getMembership(fee.getGroup_id());
		
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
		
		MailUtil mail = new MailUtil();
		
		String subject = "【Spica】会費が設定されました。";
		
		String html = "<html>" +
				"<body style='font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;'>" +

				"<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px;'>" +

				"<h2 style='color:#333333;'>会費が設定されました。</h2>" +

				"<p style='font-size:14px; color:#555555;'>会費が設定されました。<br>金額："+fee.getFee()+"円<br>徴収開始日："+fee.getStart_date()+"<br>締切日："+fee.getEnd_date()+"</p>" +

				"</div>" +
				"</body>" +
				"</html>";

		String text = "新しい会費（"+fee.getFee()+"）が設定されました。期間："+fee.getStart_date()+"から"+fee.getEnd_date()+"まで。";
		
		Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String created_at = format.format(now);  
        
		membership_feeDao fee_dao = new membership_feeDao();
		paymentDao pay_dao = new paymentDao();
		logDao log_dao = new logDao();
		noticeDao notice_dao = new noticeDao();
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean fee_completeFlag = fee_dao.insertMembershipFee(con,fee);
			
			if(!fee_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean pay_completeFlag = pay_dao.makeFeeManagement(con,fee.getId(),ids,account);
					
			if(!pay_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean log_completeFlag = log_dao.insertLog(con,fee.getGroup_id(),log);
			
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
	
	//名簿取得
	public List<accountBeans> getMembership(String group_id){
		accountDao dao = new accountDao();
		return dao.getMembership(group_id);
	}
	
	//支払い状況の取得
	public List<accountPaymentBeans> getPaymentUser(String group_id){
		paymentDao dao = new paymentDao();
		return dao.getPaymentUser(group_id);
	}
	
	//支払い状況の更新
	public boolean updatePaymentStatus(String id, String status,String user_name,String target,String group_id) {
		paymentDao dao = new paymentDao();
		accountDao acc_dao = new accountDao();
		
		String to = acc_dao.getUserEmail(target);
		accountBeans target_name = acc_dao.login(to);
		
		
		//ログの前処理
		logDao log_dao = new logDao();
		String log = user_name+"が、「"+target_name.getName()+"」の支払い状況を「"+status+"」に変更しました。";

        //アプリ内通知の前処理
		noticeDao notice_dao = new noticeDao();
		signupLogic signup_logic = new signupLogic();
		String notice_id = signup_logic.RandomID();
		String notice = target_name+"の会費の支払い状況が「"+status+"」になりました。";
		Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String created_at = format.format(now);      
        
        //メール通知の前処理        
        MailUtil mail = new MailUtil();
		
		String subject = "【Spica】会費の支払い状況について";
		
		String html = "<html>" +
				"<body style='font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;'>" +

				"<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px;'>" +

				"<h2 style='color:#333333;'>会費の支払い状況について、お知らせします。</h2>" +

				"<p style='font-size:14px; color:#555555;'>"+target_name.getName()+"様の会費支払い状況："+status+"</p>" +

				"</div>" +
				"</body>" +
				"</html>";
		
		Connection con = null;
		try {
		    con = DBUtil.getConnection();
		    con.setAutoCommit(false);
		    
		    boolean update_completeFlag = dao.updatePaymentStatus(con,id,status);
		    
		    if(!update_completeFlag) {
		        con.rollback();
		        return false;
		    }
		    
		    boolean log_completeFlag = log_dao.insertLog(con,group_id, log);
            
	        if(!log_completeFlag) {
	            con.rollback();
	            return false;
	        }        
	        
	        boolean notice_completeFlag = notice_dao.insertNotice(con, notice_id, group_id,created_at,notice);
            
	        if(!notice_completeFlag) {
	            con.rollback();
	            return false;
	        }
		    
		    con.commit();
		    
		    mail.sendEmail(to,subject,html);
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
	
	//ロールの更新
	public boolean updateRole(String id, String role,String user_name,String target,String group_id) {
		accountDao dao = new accountDao();
		
		String to = dao.getUserEmail(target);
		accountBeans target_name = dao.login(to);
		
		//ログの前処理
		logDao log_dao = new logDao();
		String log = user_name+"が、「"+target_name.getName()+"」の支払い状況を「"+role+"」に変更しました。";

        //アプリ内通知の前処理
		noticeDao notice_dao = new noticeDao();
		signupLogic signup_logic = new signupLogic();
		String notice_id = signup_logic.RandomID();
		String notice = target_name+"が「"+role+"」になりました。";
		Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String created_at = format.format(now);      
        
        //メール通知の前処理        
        MailUtil mail = new MailUtil();
		
		String subject = "【Spica】ロールの変更について";
		
		String html = "<html>" +
				"<body style='font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;'>" +

				"<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px;'>" +

				"<h2 style='color:#333333;'>ロールの変更について、お知らせします。</h2>" +

				"<p style='font-size:14px; color:#555555;'>"+target_name.getName()+"様のロール："+role+"</p>" +

				"</div>" +
				"</body>" +
				"</html>";
		
		Connection con = null;
		try {
		    con = DBUtil.getConnection();
		    con.setAutoCommit(false);
		    
		    boolean update_completeFlag = dao.updateRole(con,id,role);
		    
		    if(!update_completeFlag) {
		        con.rollback();
		        return false;
		    }
		    
		    boolean log_completeFlag = log_dao.insertLog(con,group_id, log);
            
	        if(!log_completeFlag) {
	            con.rollback();
	            return false;
	        }        
	        
	        boolean notice_completeFlag = notice_dao.insertNotice(con, notice_id, group_id,created_at,notice);
            
	        if(!notice_completeFlag) {
	            con.rollback();
	            return false;
	        }
		    
		    con.commit();
		    
		    mail.sendEmail(to,subject,html);
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
	
	//強制退会
	public boolean deleteMemberList(String id,String user_name,String group_id) {
		accountDao dao = new accountDao();
		
		String to = dao.getUserEmail(id);
		
		accountBeans target_name = dao.login(to);

		//ログの前処理
		logDao log_dao = new logDao();
		String log = user_name+"が、「"+target_name.getName()+"」を強制退会しました。";
		
        //メール通知の前処理        
        MailUtil mail = new MailUtil();
		
		String subject = "【Spica】所属団体について";
		
		String html = "<html>" +
				"<body style='font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;'>" +

				"<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px;'>" +

				"<h2 style='color:#333333;'>所属団体に関して、お知らせいたします。</h2>" +

				"<p style='font-size:14px; color:#555555;'>所属していた団体役員により、退会となりました。</p>" +

				"</div>" +
				"</body>" +
				"</html>";
		
		
		Connection con = null;
		try {
		    con = DBUtil.getConnection();
		    con.setAutoCommit(false);
		    
		    boolean delete_completeFlag = dao.deleteMemberList(con,id);
		    
		    if(!delete_completeFlag) {
		        con.rollback();
		        return false;
		    }
		    
		    boolean log_completeFlag = log_dao.insertLog(con,group_id, log);
            
	        if(!log_completeFlag) {
	            con.rollback();
	            return false;
	        }        
	        
		    con.commit();
		    
		    mail.sendEmail(to,subject,html);
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
