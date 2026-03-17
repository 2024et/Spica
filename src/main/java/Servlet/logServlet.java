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
import Beans.logBeans;
import Beans.noticeBeans;
import Logic.accountLogic;
import Logic.logLogic;

@WebServlet("/logServlet")
public class logServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		accountLogic acc_logic = new accountLogic();
		List<noticeBeans> notice = acc_logic.getNotice(accountData.getGroup_id());
		
		logLogic logic = new logLogic();
		List<logBeans> log = logic.getLogData(accountData.getGroup_id());
		
		request.setAttribute("log", log);
		request.setAttribute("notice", notice);
		request.getRequestDispatcher("/log.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/log.jsp").forward(request, response);
	}

}
