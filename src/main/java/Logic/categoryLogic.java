package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Beans.categoryBeans;
import Dao.DBUtil;
import Dao.categoryDao;
import Dao.logDao;
import Dao.noticeDao;

public class categoryLogic {
	//カテゴリデータ取得
	public List<categoryBeans> getCategoryData(String group_id) {
        categoryDao dao = new categoryDao();
        return dao.getCategoryData(group_id);
    }
	
	//カテゴリの新規登録
	public boolean insertCategoryData(String name, String type, String group_id,String user_name) {
		//登録データの前処理
		signupLogic logic = new signupLogic();
		String id = logic.RandomID();
		String status = "稼働中";
		categoryBeans beans = new categoryBeans(id,group_id,name,type,status);
		categoryDao dao = new categoryDao();
		
		//ログの前処理
		logDao log_dao = new logDao();
		String log = user_name+"が、カテゴリ「"+name+"」を作成しました。";
		
		//アプリ内通知の前処理
		noticeDao notice_dao = new noticeDao();
		signupLogic signup_logic = new signupLogic();
		String notice_id = signup_logic.RandomID();
		String notice = "新規カテゴリ「"+name+"」が追加されました。";
		Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String created_at = format.format(now);         
		
		Connection con = null;
		try {
		    con = DBUtil.getConnection();
		    con.setAutoCommit(false);
		    
		    boolean insert_completeFlag = dao.insertCategoryData(con,beans);
		    
		    if(!insert_completeFlag) {
		        con.rollback();
		        return false;
		    }
		    
		    boolean log_completeFlag = log_dao.insertLog(con, group_id, log);
            
	        if(!log_completeFlag) {
	            con.rollback();
	            return false;
	        }
	        
	        boolean notice_completeFlag = notice_dao.insertNotice(con, notice_id, group_id,created_at,notice);
            
	        if(!notice_completeFlag) {
	            con.rollback();
	            return false;
	        }
	        
		    
		    con.commit();
		    return true;
		    
		} catch (SQLException e) {
		    e.printStackTrace();
		    return false;
		}finally {
		    try {
		        if(con != null) con.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	}
	
	//カテゴリの更新
	public boolean updateCategoryData(categoryBeans beans,String user_name) {
		categoryDao dao = new categoryDao();
		logDao log_dao = new logDao();
		String log = user_name+"が、カテゴリ「"+beans.getName()+"」を"+beans.getStatus()+"にしました。";
		
		//アプリ内通知の前処理
		noticeDao notice_dao = new noticeDao();
		signupLogic signup_logic = new signupLogic();
		String notice_id = signup_logic.RandomID();
		String notice = "カテゴリ「"+beans.getName()+"」に変更がありました。";
		Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String created_at = format.format(now);     
		
		Connection con = null;
		try {
		    con = DBUtil.getConnection();
		    con.setAutoCommit(false);
		    
		    boolean update_completeFlag = dao.updateCategoryData(con,beans);
		    
		    if(!update_completeFlag) {
		        con.rollback();
		        return false;
		    }
		    
		    boolean log_completeFlag = log_dao.insertLog(con,beans.getGroup_id(), log);
            
	        if(!log_completeFlag) {
	            con.rollback();
	            return false;
	        }        
	        
	        boolean notice_completeFlag = notice_dao.insertNotice(con, notice_id, beans.getGroup_id(),created_at,notice);
            
	        if(!notice_completeFlag) {
	            con.rollback();
	            return false;
	        }
		    
		    
		    con.commit();
		    return true;
		    
		} catch (SQLException e) {
		    e.printStackTrace();
		    return false;
		}finally {
		    try {
		        if(con != null) con.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	}
}
