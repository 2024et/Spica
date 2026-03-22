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
import Beans.budget_reportBeans;
import Logic.budget_report_detailLogic;

@WebServlet("/budget_report_detailServlet")
public class budget_report_detailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		if(accountData == null) {
			response.sendRedirect(request.getContextPath() + "/signinServlet");
			return;
		}
		
		String budgetID = request.getParameter("reportID");
		budget_report_detailLogic logic = new budget_report_detailLogic();
		String title = logic.getBudgetReport(budgetID);
		List<budget_reportBeans> budget = logic.getBudgetData(budgetID);
		
		request.setAttribute("title",title);
		request.setAttribute("budget", budget);
		request.getRequestDispatcher("/budget_report_detail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
