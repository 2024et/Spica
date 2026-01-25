package Logic;

import Dao.accountDao;
import Dao.organizationsDao;

public class select_groupLogic {
	//既存団体に参加
	public boolean joinGroup(String id, String code) {
		
		organizationsDao org_dao = new organizationsDao();
		boolean searchFlag = org_dao.searchGroup(code);
		
		if(!searchFlag) {
			return false;
		}
		
		accountDao act_dao = new accountDao();
		return act_dao.joinGroup(id,code);
	}
	
	//入力内容の確認
	public boolean checkName(String name) {
		if (!name.matches("^[a-zA-Z0-9]$")) {
		    return false;
		}
		return true;
	}
	
	//新規団体の作成
	public boolean makeGroup(String user_id,String name) {
		
		
		signupLogic logic = new signupLogic();
		String id = logic.RandomID();
		String code = logic.RandomID();
		
		organizationsDao org_dao = new organizationsDao();
		
		boolean makeFlag = org_dao.makeGroup(id,code,name);
		
		if(!makeFlag) {
			return false;
		}
		
		accountDao act_dao = new accountDao();
		boolean joinFlag = act_dao.joinGroup(user_id,code);
		
		
		return joinFlag;
	}
	
	
}
