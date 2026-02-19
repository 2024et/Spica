package Logic;

import java.util.List;

import Beans.account_reportBeans;
import Dao.account_reportDao;

public class account_report_listLogic {
	//一覧取得
	public List<account_reportBeans> getAccountReportData(String group_id){
		account_reportDao dao = new account_reportDao();
		return dao.getAccountReportData(group_id);
	}
}
