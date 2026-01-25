package Logic;

import Dao.accountDao;

public class change_passwordLogic {
	public boolean updatePassword(String id, String password) {
		signupLogic logic = new signupLogic();
		String hashed_password = logic.hashPassword(password);
		accountDao dao = new accountDao();
		return dao.updatePassword(id,hashed_password);
	}
}
