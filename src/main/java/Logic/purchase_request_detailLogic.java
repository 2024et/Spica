package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Beans.chatBeans;
import Beans.purchase_requestBeans;
import Dao.DBUtil;
import Dao.chatDao;
import Dao.financialDao;
import Dao.logDao;
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
		purchase_requestDao pr_dao = new purchase_requestDao();
		logDao log_dao = new logDao();
		try {
			Connection con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean tr_completeFlag = pr_dao.updateReqStatusData_request(con,id,status);
			System.out.println(tr_completeFlag);
					
			if(!tr_completeFlag) {
				System.out.println("Request失敗");
				con.rollback();
				return false;
			}
			
			boolean log_completeFlag = log_dao.insertLog(con,group_id,log);
			System.out.println(log_completeFlag);
			
			if(!log_completeFlag) {
				System.out.println("Log失敗");
				con.rollback();				
				return false;
			}
			
			con.commit();
			return true;
			
		} catch (SQLException e) {
			System.out.println("エラー"+e);
			e.printStackTrace();			
			return false;
		}
	}
	//チャット送信
	public boolean sendMessage(String user_id, String request_id, String Message) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		signupLogic logic = new signupLogic();
		String id = logic.RandomID();
		chatDao dao = new chatDao();
		return dao.sendMessage(id,user_id, request_id,now.format(dtf), Message);
	}
}
