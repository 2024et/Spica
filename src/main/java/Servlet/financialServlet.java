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
import Beans.balanceBeans;
import Beans.categoryBeans;
import Beans.projectBeans;
import Logic.financialLogic;
import Logic.signupLogic;

@WebServlet("/financialServlet")
public class financialServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		financialLogic logic = new financialLogic();
		List<categoryBeans> category = logic.getCategoryData(accountData.getGroup_id());
		List<projectBeans> project = logic.getProjectData(accountData.getGroup_id());
		
		request.setAttribute("category", category);
		request.setAttribute("project", project);
		request.getRequestDispatcher("/financial.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		String submit = request.getParameter("submit");
		financialLogic logic = new financialLogic();
		
		if("save".equals(submit)) {
			//収支新規記録
			String date = request.getParameter("date");
			String category = request.getParameter("category");
			String project = request.getParameter("project");
			String store = request.getParameter("store");
			String item = request.getParameter("item");
			String type = request.getParameter("type");
			int amount = Integer.parseInt(request.getParameter("number"));
			String memo = request.getParameter("memo");
			
			signupLogic id_logic = new signupLogic();
			
			String id = id_logic.RandomID();
			String group_id = accountData.getGroup_id();
			
			balanceBeans beans = new balanceBeans(id,group_id,date,store,item,amount,project,category,memo,type);
			
			boolean insertFlag = logic.insertBalanceData(beans);
			
			if(insertFlag) {
				request.getRequestDispatcher("/financial.jsp").forward(request, response);
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。");
				request.getRequestDispatcher("/financial.jsp").forward(request, response);
			}
		}
	}

}
