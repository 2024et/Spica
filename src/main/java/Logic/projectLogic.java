package Logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Beans.projectBeans;
import Dao.projectDao;

public class projectLogic {
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
