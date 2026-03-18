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
import Beans.membership_feeBeans;
import Beans.noticeBeans;
import Logic.accountLogic;
import Logic.memberLogic;

@WebServlet("/memberServlet")
public class memberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		accountLogic acc_logic = new accountLogic();
		memberLogic men_logic = new memberLogic();
		List<noticeBeans> notice = acc_logic.getNotice(accountData.getGroup_id());
		
		membership_feeBeans membership_fee = men_logic.getMembershipFee(accountData.getGroup_id());
		
		
		request.setAttribute("notice", notice);
		request.setAttribute("membership_fee", membership_fee);
		request.getRequestDispatcher("/member.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
