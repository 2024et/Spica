package Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import Beans.accountBeans;
import Beans.purchase_requestBeans;
import Logic.purchase_request_detailLogic;

@WebServlet("/purchase_request_detailServlet")
public class purchase_request_detailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String requestID = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		requestID = request.getParameter("requestID");
		
		System.out.println("id:"+requestID);
		
		purchase_request_detailLogic logic = new purchase_request_detailLogic();
		purchase_requestBeans beans = logic.getRequestData(requestID);
		
		if(beans == null) {
			request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：AC-da1000");
			request.getRequestDispatcher("/purchase_request_list.jsp").forward(request, response);
		}
		
		request.setAttribute("request", beans);
		request.getRequestDispatcher("/purchase_request_detail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		String submit = request.getParameter("submit");
		purchase_request_detailLogic logic = new purchase_request_detailLogic();
		
		if("delete".equals(submit)) {
			System.out.println("aaaeeee");
			String log = accountData.getName()+"さんが、希望申請を取り消しました。";
			
			boolean deleteFlag = logic.deleteRequestData(requestID,accountData.getGroup_id(),log);
			
			if(deleteFlag) {
				response.sendRedirect(request.getContextPath() + "/purchase_request_listServlet");
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：AC-da1000");
				request.getRequestDispatcher("/purchase_request_detail.jsp").forward(request, response);
			}
		}
	}

}
