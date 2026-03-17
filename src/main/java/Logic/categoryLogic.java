package Logic;

import java.util.List;

import Beans.categoryBeans;
import Dao.categoryDao;

public class categoryLogic {
	//カテゴリデータ取得
	public List<categoryBeans> getCategoryData(String group_id) {
        categoryDao dao = new categoryDao();
        return dao.getCategoryData(group_id);
    }
	
	//カテゴリの新規登録
	public boolean insertCategoryData(String name, String type, String group_id) {
		signupLogic logic = new signupLogic();
		String id = logic.RandomID();
		String status = "稼働中";
		categoryBeans beans = new categoryBeans(id,group_id,name,type,status);
		categoryDao dao = new categoryDao();
		return dao.insertCategoryData(beans);
	}
	
	//カテゴリの更新
	public boolean updateCategoryData(categoryBeans beans) {
		categoryDao dao = new categoryDao();
		return dao.updateCategoryData(beans);
	}
}
