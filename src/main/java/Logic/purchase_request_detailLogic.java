package Logic;

import java.sql.Connection;
import java.sql.SQLException;
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
		System.out.println("oooo");
		financialDao dao = new financialDao();
		System.out.println("aaaa");
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
}
