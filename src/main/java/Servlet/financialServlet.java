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
		
		List<balanceBeans> balance = logic.getBalanceData(accountData.getGroup_id());
		
		request.setAttribute("category", category);
		request.setAttribute("project", project);
		request.setAttribute("balance", balance);
		request.getRequestDispatcher("/financial.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		String submit = request.getParameter("submit");
		financialLogic logic = new financialLogic();
		
		String id, date, category, project, store, item, type, memo;
		int amount;
		String group_id = accountData.getGroup_id();
		
		if("save".equals(submit)) {
			//収支新規記録
			date = request.getParameter("date");
			category = request.getParameter("category");
			project = request.getParameter("project");
			store = request.getParameter("store");
			item = request.getParameter("item");
			type = request.getParameter("type");
			amount = Integer.parseInt(request.getParameter("number"));
			memo = request.getParameter("memo");
			
			signupLogic id_logic = new signupLogic();
			
			id = id_logic.RandomID();
			
			
			balanceBeans beans = new balanceBeans(id,group_id,date,store,item,amount,project,category,memo,type);
			
			boolean insertFlag = logic.insertBalanceData(beans);
			
			if(insertFlag) {
				response.sendRedirect(request.getContextPath() + "/financialServlet");
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。");
				response.sendRedirect(request.getContextPath() + "/financialServlet");
			}
		}else if("edit".equals(submit)) {
			//収支編集
			id = request.getParameter("id");
			date = request.getParameter("date");
			category = request.getParameter("category");
			project = request.getParameter("project");
			store = request.getParameter("store");
			item = request.getParameter("item");
			type = request.getParameter("type");
			amount = Integer.parseInt(request.getParameter("number"));
			memo = request.getParameter("memo");
			balanceBeans beans = new balanceBeans(id,group_id,date,store,item,amount,project,category,memo,type);
			
			boolean editFlag = logic.editBalanceData(beans);
			
			if(editFlag) {
				response.sendRedirect(request.getContextPath() + "/financialServlet");
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。");
				response.sendRedirect(request.getContextPath() + "/financialServlet");
			}
			
		}else if("search".equals(submit)) {
			List<categoryBeans> data_category = logic.getCategoryData(accountData.getGroup_id());
			List<projectBeans> data_project = logic.getProjectData(accountData.getGroup_id());
			request.setAttribute("category", data_category);
			request.setAttribute("project", data_project);
			
			//収支検索
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			category = request.getParameter("category");
			project = request.getParameter("project");
			store = request.getParameter("store");
			item = request.getParameter("item");
			type = request.getParameter("type");
			String keyword = request.getParameter("keyword");
			
			List<balanceBeans> searchData = logic.searchBalanceData(group_id,startDate,endDate,category,project,store,item,type,keyword);
			request.setAttribute("balance", searchData);
			request.getRequestDispatcher("/financial.jsp").forward(request, response);
		}else if("delete".equals(submit)) {
			//収支削除
			id = request.getParameter("id");
			
			boolean deleteFlag = logic.deleteBalanceData(id,group_id);
		}else {
			response.sendRedirect(request.getContextPath() + "/financialServlet");
		}
	}

}
