package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import Beans.projectBeans;
import Dao.DBUtil;
import Dao.logDao;
import Dao.noticeDao;
import Dao.projectDao;

public class projectLogic {
	//プロジェクトデータ取得
	public List<projectBeans> getProjectData(String group_id) {
        projectDao dao = new projectDao();
        return dao.getProjectData(group_id);
    }
	
	//プロジェクトの新規登録
	public boolean insertProjectData(String name, String group_id,String user_name) {
		//登録データの前処理
		signupLogic logic = new signupLogic();
		String id = logic.RandomID();
		String status = "稼働中";
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String created_at = now.format(dtf);
		projectBeans beans = new projectBeans(id,group_id,created_at,name,status);
		projectDao dao = new projectDao();
		
		//ログの前処理
		logDao log_dao = new logDao();
		String log = user_name+"が、プロジェクト「"+name+"」を作成しました。";

        //アプリ内通知の前処理
		noticeDao notice_dao = new noticeDao();
		signupLogic signup_logic = new signupLogic();
		String notice_id = signup_logic.RandomID();
		String notice = "新規プロジェクト「"+name+"」が追加されました。";      

		Connection con = null;
		try {
		    con = DBUtil.getConnection();
		    con.setAutoCommit(false);
		    
		    boolean insert_completeFlag = dao.insertProjectData(con,beans);
		    
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
	public boolean updateProjectData(projectBeans beans,String user_name) {
		projectDao dao = new projectDao();
		
		//ログの前処理
		logDao log_dao = new logDao();
		String log = user_name+"が、プロジェクト「"+beans.getName()+"」を作成しました。";

        //アプリ内通知の前処理
		noticeDao notice_dao = new noticeDao();
		signupLogic signup_logic = new signupLogic();
		String notice_id = signup_logic.RandomID();
		String notice = "新規カテゴリ「"+beans.getName()+"」が追加されました。";
		Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String created_at = format.format(now);         
        
        Connection con = null;
		try {
		    con = DBUtil.getConnection();
		    con.setAutoCommit(false);
		    
		    boolean update_completeFlag = dao.updateProjectData(con,beans);
		    
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
