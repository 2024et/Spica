package Logic;

import java.util.List;

import Beans.accountBeans;
import Beans.noticeBeans;
import Dao.accountDao;

public class accountLogic {
	//通知取得
	public List<noticeBeans> getNotice(String id){
		accountDao dao = new accountDao();
		return dao.getNotice(id);
	}
	
	//パスワードの再設定
	public boolean changePassword(String id, String password) {
		accountDao dao = new accountDao();
		return dao.updatePassword(id,password);
	}
	
	//アカウント情報の変更
	public boolean changeInformation(String id,String name, String email, String code) {
		accountDao dao = new accountDao();
		return dao.updateInformation(id,name,email,code);
	}
	
	//ログイン
	public accountBeans login_system(String email) {
		accountDao dao = new accountDao();
		return dao.login(email);
		
	}
	
	//アカウント削除
	public boolean deleteAccount(String id) {
		accountDao dao = new accountDao();
		return dao.deleteAccount(id);
	}

}
