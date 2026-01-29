package Logic;

import java.util.List;

import Beans.categoryBeans;
import Dao.categoryDao;

public class financialLogic {
	
	//カテゴリの取得
	public List<categoryBeans> getCategoryData(String group_id) {
		categoryDao dao = new categoryDao();
		return dao.getCategoryData(group_id);
	}
}
