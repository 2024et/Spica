package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import Beans.balanceBeans;
import Beans.categoryBeans;
import Beans.projectBeans;
import Dao.DBUtil;
import Dao.categoryDao;
import Dao.financialDao;
import Dao.projectDao;
import Dao.transactionDao;

public class financialLogic {
	
	//カテゴリの取得
	public List<categoryBeans> getCategoryData(String group_id) {
		categoryDao dao = new categoryDao();
		return dao.getCategoryData(group_id);
	}
	
	//プロジェクトの取得
	public List<projectBeans> getProjectData(String group_id) {
		projectDao dao = new projectDao();
		return dao.getProjectData(group_id);
	}
	
	//収支データの収集
	public List<balanceBeans> getBalanceData(String group_id){
		financialDao dao = new financialDao();
		return dao.getBalanceData(group_id);
	}
	
	//収支新規登録
	public boolean insertBalanceData(balanceBeans beans) {
		financialDao fi_dao = new financialDao();
		transactionDao tr_dao = new transactionDao();
		String type = "収支";
		try {
			Connection con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean fi_completeFlag = fi_dao.insertBalanceData_financial(con,beans,type);
			
			if(!fi_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean tr_completeFlag = tr_dao.insertBalanceData_transaction(con,beans);
					
			if(!tr_completeFlag) {
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
	
	//収支編集
	public boolean editBalanceData(balanceBeans beans) {
		financialDao fi_dao = new financialDao();
		transactionDao tr_dao = new transactionDao();
		String type = "収支";
		try {
			Connection con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean fi_completeFlag = fi_dao.editBalanceData_financial(beans,type);
			
			if(!fi_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean tr_completeFlag = tr_dao.editBalanceData_transaction(beans);
					
			if(!tr_completeFlag) {
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
	
	//収支検索
	public List<balanceBeans> searchBalanceData(String group_id, String startDate, String endDate, String category, String project, String store, String item, String type, String keyword) {
		financialDao dao = new financialDao();
		return dao.searchBalanceData_financial(group_id,startDate,endDate,category,project,store,item,type,keyword);
		
	}
	
	//収支削除
	public boolean deleteBalanceData(String id) {
		financialDao fi_dao = new financialDao();
		transactionDao tr_dao = new transactionDao();
		
		try {
			Connection con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean fi_completeFlag = fi_dao.deleteBalanceData_financial(con,id);
			
			if(!fi_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean tr_completeFlag = tr_dao.deleteBalanceData_transaction(con,id);
					
			if(!tr_completeFlag) {
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
