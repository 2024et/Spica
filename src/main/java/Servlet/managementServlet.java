package Servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import Beans.accountBeans;
import Beans.approverBeans;
import Beans.documentApproverlDTOBeans;
import Beans.noticeBeans;
import Beans.proceed_documentsBeans;
import Logic.accountLogic;
import Logic.managementLogic;
import Logic.signupLogic;

@WebServlet("/managementServlet")
@MultipartConfig
public class managementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		if(accountData == null) {
			response.sendRedirect("/signinServlet");
			return;
		}
		
		if("一般".equals(accountData.getRole())) {
			response.sendRedirect("/financialServlet");
			return;
		}
		
		accountLogic acc_logic = new accountLogic();
		managementLogic man_logic = new managementLogic();
		List<noticeBeans> notice = acc_logic.getNotice(accountData.getGroup_id());
		
		List<documentApproverlDTOBeans> process_documents = man_logic.getProcessDocumentData(accountData.getGroup_id());
		
		List<documentApproverlDTOBeans> submited_documents = man_logic.getSubmitedDocuemntData(accountData.getGroup_id());
		
		request.setAttribute("accountData", accountData);
		request.setAttribute("process_documents", process_documents);
		request.setAttribute("submited_documents", submited_documents);
		request.setAttribute("notice", notice);
		request.getRequestDispatcher("/management.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		managementLogic man_logic = new managementLogic();
		
		String submit = request.getParameter("submit");
		
		if("insert".equals(submit)) {
		    String name = request.getParameter("name");

		 
		    Part filePart = request.getPart("file");
		    String fileName = filePart.getSubmittedFileName();

		    byte[] fileBytes;
		    try (InputStream is = filePart.getInputStream()) {
		        fileBytes = is.readAllBytes();
		    }

		    boolean fileChecker;
		    try (InputStream checkStream = new ByteArrayInputStream(fileBytes)) {
		        fileChecker = man_logic.fileChecker(checkStream);
		    }

		    if (!fileChecker) {
		        request.setAttribute("errorMessage", "PDFファイルを選択してください。");
		        response.sendRedirect("/managementServlet");
		        return;
		    }

		    boolean insertFlag;
		    try (InputStream saveStream = new ByteArrayInputStream(fileBytes)) {
		        insertFlag = man_logic.insertDocumentData(
		            name, fileName, saveStream,
		            accountData.getGroup_id(), accountData.getName());
		    }

		    if(insertFlag) {
		        response.sendRedirect("/managementServlet");
		        return;
		    } else {
		        request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：MA-insertDocumentData");
		        response.sendRedirect("/managementServlet");
		        return;
		    }
		}else if("approver".equals(submit)) {
			//書類の承認
			String document_id = request.getParameter("document_id");
			String document_name = request.getParameter("document_name");
			String answer = request.getParameter("answer");
			
			signupLogic signup_logic = new signupLogic();
			String id = signup_logic.RandomID();
			approverBeans beans = new approverBeans(id,document_id,accountData.getRole(),answer);
			boolean approverlFlag = false;
			if("NG".equals(answer)) {
				//承認しない
				String comment = request.getParameter("comment");
				comment += "（"+ accountData.getName() + "）";
				approverlFlag = man_logic.disApproverDocument(beans,comment,accountData.getName(),document_name,accountData.getGroup_id());
			}else {
				//承認する
				approverlFlag = man_logic.approverDocument(beans,accountData.getName(),document_name,accountData.getGroup_id());
			}
			
			if(approverlFlag) {
				response.sendRedirect("/managementServlet");
				return;
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：MA-approverDocument");
				response.sendRedirect("/managementServlet");
			    return;
			}
		}else if("edit".equals(submit)) {
			//書類の編集
			String document_id = request.getParameter("document_id");
			String name = request.getParameter("name");
			String reset = request.getParameter("approver_reset");
			
		    Part filePart = request.getPart("file");
		    byte[] fileBytes = null;
		    String fileName = null;

		    if (filePart != null && filePart.getSize() > 0) {
		        fileName = filePart.getSubmittedFileName();

		        try (InputStream is = filePart.getInputStream()) {
		            fileBytes = is.readAllBytes();
		        }

		        boolean fileChecker;
		        try (InputStream checkStream = new ByteArrayInputStream(fileBytes)) {
		            fileChecker = man_logic.fileChecker(checkStream);
		        }

		        if (!fileChecker) {
		            request.setAttribute("errorMessage", "PDFファイルを選択してください。");
		            response.sendRedirect("/managementServlet");
		            return;
		        }
		    }
		    
		    proceed_documentsBeans beans = new proceed_documentsBeans(document_id, accountData.getGroup_id(), name);

		    boolean updateFlag;
		    if (fileBytes != null) {
		        try (InputStream saveStream = new ByteArrayInputStream(fileBytes)) {
		            updateFlag = man_logic.updateDocumentData(beans, fileName, saveStream, reset, accountData.getName());
		        }
		    } else {
		        updateFlag = man_logic.updateDocumentData(beans, null, null, reset, accountData.getName());
		    }

			if(updateFlag) {
				response.sendRedirect("/managementServlet");
				return;
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：MA-updateDocumentData");
				response.sendRedirect("/managementServlet");
			    return;
			}
		}else if("delete".equals(submit)) {
			//書類の削除
			String id = request.getParameter("document_id");
			String document_name = request.getParameter("document_name");
			
			proceed_documentsBeans beans = new proceed_documentsBeans(id,accountData.getGroup_id());
			
			boolean deleteFlag = man_logic.deleteDocumentData(beans,accountData.getName(),document_name);
			
			if(deleteFlag) {
				response.sendRedirect("/managementServlet");
				return;
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：MA-deleteDocumentData");
				response.sendRedirect("/managementServlet");
			    return;
			}
		}else if("submited".equals(submit)) {
			//書類の提出切り替え
			String id = request.getParameter("document_id");
			
			boolean submitedFlag = man_logic.submitedDocumentData(id);
			
			if(submitedFlag) {
				response.sendRedirect("/managementServlet");
				return;
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：MA-submitedDocumentData");
				response.sendRedirect("/managementServlet");
			    return;
			}
			
		}else if("no_submit".equals(submit)) {
			//書類の未提出切り替え
			String id = request.getParameter("document_id");
			
			boolean no_submitedFlag = man_logic.noSubmitedDocumentData(id);
			
			if(no_submitedFlag) {
				response.sendRedirect("/managementServlet");
				return;
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：MA-noSubmitedDocumentData");
				response.sendRedirect("/managementServlet");
			    return;
			}
		}
	}

}
