package Logic;

import java.sql.Connection;
import java.sql.SQLException;

import Beans.purchase_requestBeans;
import Dao.DBUtil;
import Dao.financialDao;
import Dao.logDao;
import Dao.purchase_requestDao;

public class purchase_request_formLogic {
	//備品購入申請
	public boolean insertRequestData(purchase_requestBeans beans, String log) {
		financialDao fi_dao = new financialDao();
		purchase_requestDao pr_dao = new purchase_requestDao();
		logDao log_dao = new logDao();
		String type = "申請";
		try {
			Connection con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean fi_completeFlag = fi_dao.insertRewuestData_financial(con,beans,type);
			
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
			
			con.commit();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
