package Logic;

import java.util.List;

import Beans.purchase_requestBeans;
import Dao.financialDao;

public class purchase_request_listLogic {
	//一覧取得
	public List<purchase_requestBeans> getRequestData(String group_id){
		financialDao dao = new financialDao();
		return dao.getRequestData(group_id);
	}
}
