package Logic;

import java.util.List;

import Beans.chatBeans;
import Dao.chatDao;

public class purchase_request_detailLogic {
	//チャットの取得
	public List<chatBeans> getChat(String requestID){
		chatDao dao = new chatDao();
		return dao.getChat(requestID);
	}
}
