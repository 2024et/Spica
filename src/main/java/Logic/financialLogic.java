package Logic;

import java.util.List;

import Beans.categoryBeans;
import Beans.projectBeans;
import Dao.categoryDao;
import Dao.projectDao;

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
}
