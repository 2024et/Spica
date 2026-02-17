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
import Logic.purchase_request_formLogic;
import Logic.signupLogic;

@WebServlet("/purchase_request_formServlet")
public class purchase_request_formServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/purchase_request_form.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		String option = request.getParameter("option");
		String selected_date = null;
		if("あり".equals(option)) {
			selected_date = request.getParameter("selected_date");
		}
		String name = request.getParameter("name");
		String item = request.getParameter("item");
		String purpose = request.getParameter("purpose");
		int amount = Integer.parseInt(request.getParameter("amount"));
		String link = request.getParameter("link");
		
		signupLogic signup_logic = new signupLogic();
		String id = signup_logic.RandomID(); 
		String status = "待機中";
		
		String log = accountData.getName()+"さんが備品購入申請を提出しました。";
		
		purchase_requestBeans beans = new purchase_requestBeans(id,accountData.getGroup_id(),selected_date,name,item,amount,accountData.getId(),accountData.getName(),link,purpose,status);
		
		purchase_request_formLogic logic = new purchase_request_formLogic();
		boolean insertFlag = logic.insertRequestData(beans,log);
		
		if(insertFlag) {
			response.sendRedirect(request.getContextPath() + "/purchase_request_listServlet");
		}else {
			request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：AC-cp1000");
		    request.getRequestDispatcher("/purchase_request_form.jsp").forward(request, response);
		}
		
	}

}
