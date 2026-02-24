package Servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import Beans.accountBeans;
import Beans.account_report_summaryBeans;
import Beans.balanceBeans;
import Beans.budget_reportBeans;
import Beans.categoryBeans;
import Logic.account_report_detailLogic;
import Logic.budget_report_detailLogic;
import Logic.financialLogic;

@WebServlet("/account_report_detailServlet")
public class account_report_detailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reportID = request.getParameter("reportID");
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		account_report_detailLogic acr_logic = new account_report_detailLogic();
		budget_report_detailLogic bud_logic = new budget_report_detailLogic();
		financialLogic fi_logic = new financialLogic();

		String budget_id = bud_logic.getBudgetID(reportID);
		//実際の収支データ取得
		List<balanceBeans> balanceData = acr_logic.getAccountReportData(reportID);
		//予算案の取得
		List<budget_reportBeans> budgetData = bud_logic.getBudgetData(budget_id);
		//カテゴリの取得
		List<categoryBeans> categoryData = fi_logic.getCategoryData(accountData.getGroup_id());
		//集計
		Map<String,Integer> totallingBalanceData = acr_logic.totallingBalance(balanceData, categoryData);
		List<account_report_summaryBeans> report_income = acr_logic.totallingIncome(totallingBalanceData, budgetData, categoryData);
		List<account_report_summaryBeans> report_expend = acr_logic.totallingExpend(totallingBalanceData, budgetData, categoryData);
		
		
		request.setAttribute("report_income", report_income);
		request.setAttribute("report_expend", report_expend);
		request.getRequestDispatcher("/account_report_detail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
