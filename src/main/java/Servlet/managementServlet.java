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
import Beans.documentApproverlDTOBeans;
import Beans.noticeBeans;
import Logic.accountLogic;
import Logic.managementLogic;

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
		
		request.setAttribute("accountData", accountData);
		request.setAttribute("process_documents", process_documents);
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
	        
		}
	}

}
