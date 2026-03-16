package Logic;

import Beans.categoryBeans;
import Dao.categoryDao;

public class categoryLogic {
	public boolean insertCategoryData(String name, String type, String group_id) {
		signupLogic logic = new signupLogic();
		String id = logic.RandomID();
		String status = "稼働中";
		categoryBeans beans = new categoryBeans(id,group_id,name,type,status);
		categoryDao dao = new categoryDao();
		return dao.insertCategoryData(beans);
	}
}
