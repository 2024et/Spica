package Logic;

import java.util.List;

import Beans.balanceBeans;
import Beans.categoryBeans;
import Beans.projectBeans;
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
		
		//finance_recordへのデータ追加
		financialDao fi_dao = new financialDao();
		String type = "収支";
		boolean fi_completeFlag = fi_dao.insertBalanceData_financial(beans,type);
		
		if(!fi_completeFlag) {
			return false;
		}
		
		//transactionへのデータ追加
		transactionDao tr_dao = new transactionDao();
		boolean tr_completeFlag = tr_dao.insertBalanceData_transaction(beans);
		
		if(!tr_completeFlag) {
			//transactionと同時に登録しないといけないため、transaction失敗時はfinance_recordから該当データを削除する。
			boolean deleteFlag = fi_dao.deleteBalanceData(beans);
			if(!deleteFlag) {
				System.out.println("深刻なエラーが発生");
				/////////////////////処理を考える////////////////////////////
			}
			return false;
		}
		return true;	
	}
	
	//収支編集
	public boolean editBalanceData(balanceBeans beans) {
		//finance_recordへデータの更新
		financialDao fi_dao = new financialDao();
		String type = "収支";
		boolean fi_completeFlag = fi_dao.editBalanceData_financial(beans,type);
		
		if(!fi_completeFlag) {
			return false;
		}
		
		//transactionへのデータの更新
		transactionDao tr_dao = new transactionDao();
		boolean tr_completeFlag = tr_dao.editBalanceData_transaction(beans);
		
		if(!tr_completeFlag) {
			//transactionと同時に更新しないといけないため、transaction失敗時はfinance_recordからも該当データを削除する。
			boolean deleteFlag = fi_dao.deleteBalanceData(beans);
			if(!deleteFlag) {
				System.out.println("深刻なエラーが発生");
				////////////////////処理を考える//////////////////////////
			}
			return false;
		}
		return true;
	}
	
	
}
