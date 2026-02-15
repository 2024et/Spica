package Logic;

import java.util.List;

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

}
