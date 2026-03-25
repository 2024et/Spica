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
import java.util.Properties;

import Beans.accountBeans;
import Beans.approverBeans;
import Beans.documentApproverlDTOBeans;
import Beans.proceed_documentsBeans;
import Dao.DBUtil;
import Dao.accountDao;
import Dao.approverDao;
import Dao.proceed_documentDao;

public class managementLogic {
	//書類の作成
	public boolean insertDocumentData(String name, String fileName, InputStream fileStream, String group_id) {
		Properties props = new Properties();
	    try (InputStream is = getClass().getClassLoader()
	                            .getResourceAsStream("file.properties")) {
	        props.load(is);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    String uploadDir = props.getProperty("file.url");
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
	    
	    boolean insertFlag = dao.insertDocumentData(beans);
	    
	    memberLogic mem_logic = new memberLogic();
		List<accountBeans> account = mem_logic.getMembership(group_id);
		
		MailUtil mail = new MailUtil();
		
		String subject = "【Spica】書類が追加されました。";
		
		String text = "<html>" +
				"<body style='font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;'>" +

				"<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px;'>" +

				"<h2 style='color:#333333;'>書類が追加されました。</h2>" +

				"<p style='font-size:14px; color:#555555;'>書類名「"+name+"」が追加されました。承認をお願いいたします。</p>" +

				"</div>" +
				"</body>" +
				"</html>";
		
	    if(insertFlag) {
	    	for(accountBeans to : account) {
				mail.sendEmail(to.getEmail(),subject,text);
			}
	    	return true;
	    }else {
	    	return false;
	    }
	}
	//未提出書類の取得
	public List<documentApproverlDTOBeans> getProcessDocumentData(String group_id) {	
		proceed_documentDao dao = new proceed_documentDao();
		return dao.getProceedDocumentsData(group_id); 		
	}
	//提出済み書類の取得
	public List<documentApproverlDTOBeans> getSubmitedDocuemntData(String group_id){
		proceed_documentDao dao = new proceed_documentDao();
		return dao.getSubmitedDocumentsData(group_id);
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
	
	//書類の編集
	public boolean updateDocumentData(proceed_documentsBeans beans,String fileName, InputStream fileStream, String reset) {
		String filePath = null;
		if(fileStream != null) {
			Properties props = new Properties();
		    try (InputStream is = getClass().getClassLoader()
		                            .getResourceAsStream("file.properties")) {
		        props.load(is);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }

		    String uploadDir = props.getProperty("file.url");
		    fileName = System.currentTimeMillis() + "_" + fileName;
		    filePath = uploadDir + fileName;

		    File file = new File(filePath);
		    try {
				Files.copy(fileStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		approverDao ar_dao = new approverDao();
		proceed_documentDao pd_dao = new proceed_documentDao();
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			if(reset != null) {
				boolean ar_completeFlag = ar_dao.deleteApprover(con,beans);
				
				if(!ar_completeFlag) {
					con.rollback();
					return false;
				}
			}

			
			boolean pd_completeFlag = pd_dao.updateDocumentData(con,beans,filePath,reset);
					
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
	//書類・承認の削除
	public boolean deleteDocumentData(proceed_documentsBeans beans) {
		approverDao ar_dao = new approverDao();
		proceed_documentDao pd_dao = new proceed_documentDao();
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			
			boolean ar_completeFlag = ar_dao.deleteApprover(con,beans);
			
			if(!ar_completeFlag) {
				con.rollback();
				return false;
			}
			
			boolean pd_completeFlag = pd_dao.deleteDocumentData(con,beans);
					
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
	//書類の提出
	public boolean submitedDocumentData(String id) {
		proceed_documentDao dao = new proceed_documentDao();
		return dao.submitedDocumentData(id);
	}
	//書類の未提出化
	public boolean noSubmitedDocumentData(String id) {
		proceed_documentDao dao = new proceed_documentDao();
		return dao.noSubmitedDocumentData(id);
	}
	
	//役員名簿の取得
	public List<accountBeans> getBoardMember(String group_id){
		accountDao dao = new accountDao();
		return dao.getBoardMember(group_id);
	}
}
