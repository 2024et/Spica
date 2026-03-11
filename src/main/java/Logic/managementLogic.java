package Logic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Beans.approverBeans;
import Beans.documentApproverlDTOBeans;
import Beans.proceed_documentsBeans;
import Dao.DBUtil;
import Dao.approverDao;
import Dao.proceed_documentDao;

public class managementLogic {
	//書類の作成
	public boolean insertDocumentData(String name, String fileName, InputStream fileStream, String group_id) {
		String uploadDir = "C:/SpicaUploads/"; //本番環境移行時に修正が必要!
	    fileName = System.currentTimeMillis() + "_" + fileName;
	    String filePath = uploadDir + fileName;

	    File file = new File(filePath);
	    try {
			Files.copy(fileStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	    
	    signupLogic signup_logic = new signupLogic();
	    String id = signup_logic.RandomID();
	    
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String created_at = now.format(dtf);
		
		String status = "未提出";
		String comment = "";
		proceed_documentsBeans beans = new proceed_documentsBeans(id,group_id,created_at,name,filePath,status,comment);
	    
	    proceed_documentDao dao = new proceed_documentDao();
	    return dao.insertDocumentData(beans);
	}
	//未提出書類の取得
	public List<documentApproverlDTOBeans> getProcessDocumentData(String group_id) {	
		proceed_documentDao dao = new proceed_documentDao();
		return dao.getProceedDocumentsData(group_id); 		
	}
	//書類の承認(コメント付)
	public boolean disApproverDocument(approverBeans beans, String comment) {
		approverDao ar_dao = new approverDao();
		proceed_documentDao pd_dao = new proceed_documentDao();
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean ar_completeFlag = ar_dao.insertDisApproverData(con,beans);
			
			if(!ar_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean pd_completeFlag = pd_dao.updateComment(con,beans,comment);
					
			if(!pd_completeFlag) {
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
	
	//書類の承認
	public boolean approverDocument(approverBeans beans) {
		approverDao dao = new approverDao();
		return dao.insertApproverData(beans);
	}
}
