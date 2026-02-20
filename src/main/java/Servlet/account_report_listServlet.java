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
import Beans.account_reportBeans;
import Beans.budget_reportBeans;
import Logic.account_report_listLogic;
import Logic.budget_report_listLogic;

@WebServlet("/account_report_listServlet")
public class account_report_listServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		account_report_listLogic acc_logic = new account_report_listLogic();
		List<account_reportBeans> account_list = acc_logic.getAccountReportData(accountData.getGroup_id());
		
		budget_report_listLogic bud_logic = new budget_report_listLogic();
		List<budget_reportBeans> budget_list = bud_logic.getBudgetReportData(accountData.getGroup_id());

		request.setAttribute("account_list", account_list);
		request.setAttribute("budget_list", budget_list);
		request.getRequestDispatcher("/account_report_list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		String log = accountData.getName()+"さんが、会計報告書を作成しました。";
		
		account_report_listLogic logic = new account_report_listLogic();
		String name = request.getParameter("name");	
		String start = request.getParameter("start");
		String end = request.getParameter("end");	
		String budget = request.getParameter("budget");	
		
		boolean insertFlag = logic.insertReportData(accountData.getGroup_id(),name,start,end,budget,log);
	}

}
