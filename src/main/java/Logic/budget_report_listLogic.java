package Logic;

import java.util.List;

import Beans.budget_reportBeans;
import Dao.budget_reportDao;

public class budget_report_listLogic {
	//一覧取得
	public List<budget_reportBeans> getBudgetReportData(String group_id){
		budget_reportDao dao = new budget_reportDao();
		return dao.getBudgetReportData(group_id);
	}
}
