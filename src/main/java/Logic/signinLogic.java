package Logic;

import Beans.accountBeans;
import Dao.accountDao;

public class signinLogic {
	private accountBeans beans;
	public int login(String email, String password) {
		accountDao dao = new accountDao();
		beans = dao.login(email);
		
		if(beans==null) {
			return 2;
		}
		
		//ハッシュ化
		signupLogic logic = new signupLogic();
		String hashed_pwd = logic.hashPassword(password);
		
		
		if(!hashed_pwd.equals(beans.getPass())) {

			return 2;
		}
		if(!beans.getGroup_id().equals("")) {
			return 0;
		}else {
			return 1;
		}
		
	}
	
	public accountBeans getBeans() {
		return beans;
	}
}
