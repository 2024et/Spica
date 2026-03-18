package Logic;

import Beans.membership_feeBeans;
import Dao.membership_feeDao;

public class memberLogic {
	//最新の会費の取得
	public membership_feeBeans getMembershipFee(String group_id) {
		membership_feeDao dao = new membership_feeDao();
		return dao.getMembershipFee(group_id);
	}

}
