package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Beans.budget_reportBeans;
import Beans.categoryBeans;
import Dao.DBUtil;
import Dao.budget_reportDao;
import Dao.logDao;

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
		signupLogic logic = new signupLogic();
		String budget_id = logic.RandomID();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String created_at = now.format(dtf);
		
		budget_reportDao dao = new budget_reportDao();
		logDao log_dao = new logDao();
		
		try {
			Connection con = DBUtil.getConnection();
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
			
			con.commit();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
