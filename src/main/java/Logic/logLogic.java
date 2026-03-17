package Logic;

import java.util.List;

import Beans.logBeans;
import Dao.logDao;

public class logLogic {
	//ログ取得
	public List<logBeans> getLogData(String group_id){
		logDao dao = new logDao();
		return dao.getLogData(group_id);
	}
}
