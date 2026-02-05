package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Beans.balanceBeans;
import Beans.categoryBeans;
import Beans.projectBeans;
import Dao.DBUtil;
import Dao.categoryDao;
import Dao.financialDao;
import Dao.logDao;
import Dao.projectDao;
import Dao.transactionDao;

public class financialLogic {
	
	//カテゴリの取得
	public List<categoryBeans> getCategoryData(String group_id) {
		categoryDao dao = new categoryDao();
		return dao.getCategoryData(group_id);
	}
	
	//プロジェクトの取得
	public List<projectBeans> getProjectData(String group_id) {
		projectDao dao = new projectDao();
		return dao.getProjectData(group_id);
	}
	
	//収支データの収集
	public List<balanceBeans> getBalanceData(String group_id){
		financialDao dao = new financialDao();
		String year = this_year();
		if(year.equals("error")) {
			return null;
		}
		String this_year = year + "-04-01";
		return dao.getBalanceData(group_id,this_year);
	}
	
	//今年度を取得
	public String this_year() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date today;
		try {
			today = format.parse(format.format(now));
	        int year = 0;
	        SimpleDateFormat mm = new SimpleDateFormat("MM");
	        String dateStr = mm.format(today);
	        int month = Integer.parseInt(dateStr);
	        
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(today);
	        
	        if(month < 4) {
	        	calendar.add(Calendar.YEAR, -1);
	        	year = calendar.get(Calendar.YEAR);
	        }else {
	        	year = calendar.get(Calendar.YEAR);
	        }
	        
	        return String.valueOf(year);
	        
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println(e);
			return "error";
		}
	}
	
	//収入合計算出
	public int incomeSum(List<balanceBeans> list) {
		int sum = 0;
		for(balanceBeans beans : list) {
			if("income".equals(beans.getType())) {
				sum += beans.getAmount();
			}
		}
		return sum;
	}
	
	//支出合計算出
	public int expendSum(List<balanceBeans> list) {
		int sum = 0;
		for(balanceBeans beans : list) {
			if("expend".equals(beans.getType())) {
				sum += beans.getAmount();
			}
		}
		return sum;
	}
	
	//残高算出
	public int balance(int income, int expend) {
		return income - expend;
	}
	
	//収支新規登録
	public boolean insertBalanceData(balanceBeans beans,String log) {
		financialDao fi_dao = new financialDao();
		transactionDao tr_dao = new transactionDao();
		logDao log_dao = new logDao();
		String type = "収支";
		try {
			Connection con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean fi_completeFlag = fi_dao.insertBalanceData_financial(con,beans,type);
			
			if(!fi_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean tr_completeFlag = tr_dao.insertBalanceData_transaction(con,beans);
					
			if(!tr_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean log_completeFlag = log_dao.insertLog(con,beans.getGroup_id(),log);
			
			if(!log_completeFlag) {
				con.rollback();
				return false;
			}
			
			con.commit();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//収支編集
	public boolean editBalanceData(balanceBeans beans, String log) {
		financialDao fi_dao = new financialDao();
		transactionDao tr_dao = new transactionDao();
		logDao log_dao = new logDao();
		String type = "収支";
		try {
			Connection con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean fi_completeFlag = fi_dao.editBalanceData_financial(beans,type);
			
			if(!fi_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean tr_completeFlag = tr_dao.editBalanceData_transaction(beans);
					
			if(!tr_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean log_completeFlag = log_dao.insertLog(con,beans.getGroup_id(),log);
			
			if(!log_completeFlag) {
				con.rollback();
				return false;
			}
			
			con.commit();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//収支検索
	public List<balanceBeans> searchBalanceData(String group_id, String startDate, String endDate, String category, String project, String store, String item, String type, String keyword) {
		financialDao dao = new financialDao();
		return dao.searchBalanceData_financial(group_id,startDate,endDate,category,project,store,item,type,keyword);
		
	}
	
	//収支削除
	public boolean deleteBalanceData(String id,String group_id,String log) {
		financialDao fi_dao = new financialDao();
		transactionDao tr_dao = new transactionDao();
		logDao log_dao = new logDao();
		
		try {
			Connection con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean fi_completeFlag = fi_dao.deleteBalanceData_financial(con,id);
			
			if(!fi_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean tr_completeFlag = tr_dao.deleteBalanceData_transaction(con,id);
					
			if(!tr_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean log_completeFlag = log_dao.insertLog(con,group_id,log);
			
			if(!log_completeFlag) {
				con.rollback();
				return false;
			}
			
			con.commit();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
}
