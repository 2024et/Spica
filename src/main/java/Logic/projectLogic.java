package Logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Beans.projectBeans;
import Dao.projectDao;

public class projectLogic {
	//プロジェクトデータ取得
	public List<projectBeans> getProjectData(String group_id) {
        projectDao dao = new projectDao();
        return dao.getProjectData(group_id);
    }
	
	//プロジェクトの新規登録
	public boolean insertProjectData(String name, String group_id) {
		signupLogic logic = new signupLogic();
		String id = logic.RandomID();
		String status = "稼働中";
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String created_at = now.format(dtf);
		projectBeans beans = new projectBeans(id,group_id,created_at,name,status);
		projectDao dao = new projectDao();
		return dao.insertProjectData(beans);
	}
}
