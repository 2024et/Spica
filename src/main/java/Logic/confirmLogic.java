package Logic;

import Beans.accountBeans;
import Dao.accountDao;

public class confirmLogic {
	public boolean register(String id) {
		accountDao dao = new accountDao();
		accountBeans temp_list = dao.getTempData(id);
		
		return dao.pushRegister(temp_list);
		
	}
}
