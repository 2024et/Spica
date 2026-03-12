package Servlet;

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
			//書類の作成
			String name = request.getParameter("name");
			
			Part filePart = request.getPart("file");
	        String fileName = filePart.getSubmittedFileName(); // 元のファイル名
	        InputStream fileStream = filePart.getInputStream(); // バイナリストリーム

	        boolean insertFlag = man_logic.insertDocumentData(name, fileName, fileStream,accountData.getGroup_id());
	        
			if(insertFlag) {
				response.sendRedirect(request.getContextPath() + "/managementServlet");
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：MA-insertDocumentData");
			    request.getRequestDispatcher("/management.jsp").forward(request, response);
			}
	        
		}else if("approver".equals(submit)) {
			//書類の承認
			String document_id = request.getParameter("document_id");
			String answer = request.getParameter("answer");
			
			signupLogic signup_logic = new signupLogic();
			String id = signup_logic.RandomID();
			approverBeans beans = new approverBeans(id,document_id,accountData.getRole(),answer);
			boolean approverlFlag = false;
			if("NG".equals(answer)) {
				String comment = request.getParameter("comment");
				approverlFlag = man_logic.disApproverDocument(beans,comment);
			}else {
				approverlFlag = man_logic.approverDocument(beans);
			}
			
			if(approverlFlag) {
				response.sendRedirect(request.getContextPath() + "/managementServlet");
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：MA-approverDocument");
			    request.getRequestDispatcher("/management.jsp").forward(request, response);
			}
		}else if("edit".equals(submit)) {
			InputStream fileStream = null;
			String fileName = null;
			String document_id = request.getParameter("document_id");
			String name = request.getParameter("name");
			String reset = request.getParameter("approver_reset");
			
			Part filePart = request.getPart("file");
			if(filePart != null && filePart.getSize() > 0) {
		        fileName = filePart.getSubmittedFileName();
		        fileStream = filePart.getInputStream();
			}
			proceed_documentsBeans beans = new proceed_documentsBeans(document_id,accountData.getGroup_id(),name);
			boolean updateFlag = man_logic.updateDocumentData(beans,fileName,fileStream,reset);
			if(updateFlag) {
				response.sendRedirect(request.getContextPath() + "/managementServlet");
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：MA-updateDocumentData");
			    request.getRequestDispatcher("/management.jsp").forward(request, response);
			}
		}else if("delete".equals(submit)) {
			String id = request.getParameter("document_id");
			
			proceed_documentsBeans beans = new proceed_documentsBeans(id);
			
			boolean deleteFlag = man_logic.deleteDocumentData(beans);
			
			if(deleteFlag) {
				response.sendRedirect(request.getContextPath() + "/managementServlet");
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：MA-deleteDocumentData");
			    request.getRequestDispatcher("/management.jsp").forward(request, response);
			}
		}else if("submited".equals(submit)) {
			String id = request.getParameter("document_id");
			
			boolean submitedFlag = man_logic.submitedDocumentData(id);
			
			if(submitedFlag) {
				response.sendRedirect(request.getContextPath() + "/managementServlet");
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：MA-submitedDocumentData");
			    request.getRequestDispatcher("/management.jsp").forward(request, response);
			}
			
		}else if("no_submit".equals(submit)) {
			String id = request.getParameter("document_id");
			
			boolean no_submitedFlag = man_logic.noSubmitedDocumentData(id);
			
			if(no_submitedFlag) {
				response.sendRedirect(request.getContextPath() + "/managementServlet");
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：MA-noSubmitedDocumentData");
			    request.getRequestDispatcher("/management.jsp").forward(request, response);
			}
		}
	}

}
