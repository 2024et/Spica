package Logic;

import java.util.List;

import Beans.budget_reportBeans;
import Dao.budget_reportDao;

public class budget_report_detailLogic {
	//書類名取得
	public String getBudgetReport(String id) {
		budget_reportDao dao = new budget_reportDao();
		return dao.getBudgetReportData_list(id);
	}
	
	//予算額の取得
	public List<budget_reportBeans> getBudgetData(String id){
		budget_reportDao dao = new budget_reportDao();
		return dao.getBudgetData(id);
	}
}
