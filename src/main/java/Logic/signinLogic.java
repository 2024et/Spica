package Logic;

import Beans.accountBeans;
import Dao.accountDao;

public class signinLogic {
	public int login(String email, String password) {
		accountDao dao = new accountDao();
		accountBeans beans = dao.login(email);
		
		if(beans==null) {
			return 2;
		}
		
		//ハッシュ化
		signupLogic logic = new signupLogic();
		String hashed_pwd = logic.hashPassword(password);
		
		if(!hashed_pwd.equals(beans.getPass())) {
			return 2;
		}
		System.out.println("role:"+beans.getRole());
		if(!beans.getRole().equals("")) {
			return 0;
		}else {
			return 1;
		}
		
	}
}
