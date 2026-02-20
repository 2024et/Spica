package Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import Beans.accountBeans;
import Beans.budget_reportBeans;
import Beans.categoryBeans;
import Logic.budget_report_listLogic;

@WebServlet("/budget_report_listServlet")
public class budget_report_listServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		budget_report_listLogic logic = new budget_report_listLogic();
		List<budget_reportBeans> budget_list = logic.getBudgetReportData(accountData.getGroup_id());
		List<categoryBeans> category_list = logic.getCategoryData(accountData.getGroup_id()); 

		request.setAttribute("budget_list", budget_list);
		request.setAttribute("category_list", category_list);
		request.getRequestDispatcher("/budget_report_list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		budget_report_listLogic logic = new budget_report_listLogic();
		
		List<categoryBeans> category_list = logic.getCategoryData(accountData.getGroup_id()); 
		
		String name = request.getParameter("name");		
		
		Map<String, Integer> inputValues = new HashMap<>();
		for(categoryBeans list : category_list) {
			int value = Integer.parseInt(request.getParameter(list.getName()));
			inputValues.put(list.getName(),value);
		}
		String log = accountData.getName()+"さんが、新しい予算報告書を作成しました。";
		
		boolean insertFlag = logic.insertBudgetData(name,accountData.getGroup_id(),log, inputValues);
		
		if(insertFlag) {
			response.sendRedirect(request.getContextPath() + "/budget_report_listServlet");
		}else {
			request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：AC-cp1000");
		    request.getRequestDispatcher("/budget_report_list.jsp").forward(request, response);
		}
	}

}
