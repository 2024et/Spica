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
import Beans.noticeBeans;
import Beans.projectBeans;
import Logic.accountLogic;
import Logic.projectLogic;

@WebServlet("/projectServlet")
public class projectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		accountLogic acc_logic = new accountLogic();
		List<noticeBeans> notice = acc_logic.getNotice(accountData.getGroup_id());
		
		projectLogic logic = new projectLogic();
		List<projectBeans> project = logic.getProjectData(accountData.getGroup_id());
		
		request.setAttribute("notice", notice);
		request.setAttribute("project", project);
		request.getRequestDispatcher("/project.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		projectLogic pro_logic = new projectLogic();
		
		String submit = request.getParameter("submit");
		
		if("insert".equals(submit)) {
			String name = request.getParameter("name");
			
			boolean insertFlag = pro_logic.insertProjectData(name,accountData.getGroup_id());
			
			if(insertFlag) {
				response.sendRedirect(request.getContextPath() + "/projectServlet");
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。");
				response.sendRedirect(request.getContextPath() + "/projectServlet");
			}
		}else if("edit".equals(submit)) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String status = request.getParameter("status");
			
			if(status != null) {
				status = "休止中";
			}else {
				status = "稼働中";
			}
			
			projectBeans beans = new projectBeans(id,name,status);
			
			boolean updateFlag = pro_logic.updateProjectData(beans);
			
			if(updateFlag) {
				response.sendRedirect(request.getContextPath() + "/projectServlet");
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。");
				response.sendRedirect(request.getContextPath() + "/projectServlet");
			}
		}
	}

}
