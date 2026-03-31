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
import Beans.noticeBeans;
import Logic.accountLogic;
import Logic.categoryLogic;

@WebServlet("/categoryServlet")
public class categoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		if(accountData == null) {
			response.sendRedirect("/signinServlet");
			return;
		}
		
		if("一般".equals(accountData.getRole())) {
			response.sendRedirect("/financialServlet");
			return;
		}
		
		
		accountLogic acc_logic = new accountLogic();
		List<noticeBeans> notice = acc_logic.getNotice(accountData.getGroup_id());
		
		categoryLogic logic = new categoryLogic();
		List<categoryBeans> category = logic.getCategoryData(accountData.getGroup_id());
		
		request.setAttribute("notice", notice);
		request.setAttribute("category", category);
		request.getRequestDispatcher("/category.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		categoryLogic cat_logic = new categoryLogic();
		
		String submit = request.getParameter("submit");
		
		if("insert".equals(submit)) {
			String name = request.getParameter("name");
			String type = request.getParameter("type");
			
			boolean insertFlag = cat_logic.insertCategoryData(name,type,accountData.getGroup_id());
			
			if(insertFlag) {
				response.sendRedirect("/categoryServlet");
				return;
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：CA-insertCategoryData");
				response.sendRedirect("/categoryServlet");
				return;
			}
		}else if("edit".equals(submit)) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String type = request.getParameter("type");
			String status = request.getParameter("status");
			
			if(status != null) {
				status = "休止中";
			}else {
				status = "稼働中";
			}
			
			categoryBeans beans = new categoryBeans(id,accountData.getGroup_id(),name,type,status);
			
			boolean updateFlag = cat_logic.updateCategoryData(beans);
			
			if(updateFlag) {
				response.sendRedirect("/categoryServlet");
				return;
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：CA-updateCategoryData");
				response.sendRedirect("/categoryServlet");
				return;
			}
		}
	}

}
