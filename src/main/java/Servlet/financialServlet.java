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
import Beans.categoryBeans;
import Beans.projectBeans;
import Logic.financialLogic;

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
		String submit = request.getParameter("submit");
		
		if("save".equals(submit)) {
			
		}
	}

}
