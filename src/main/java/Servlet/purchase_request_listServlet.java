package Servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import Beans.accountBeans;
import Beans.purchase_requestBeans;
import Logic.purchase_request_listLogic;

@WebServlet("/purchase_request_listServlet")
public class purchase_request_listServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		purchase_request_listLogic logic = new purchase_request_listLogic();
		List<purchase_requestBeans> list = logic.getRequestData(accountData.getGroup_id());
		
		request.setAttribute("request_list", list);
		request.getRequestDispatcher("/purchase_request_list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
