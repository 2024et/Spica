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
import Logic.signupLogic;

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
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		memberLogic men_logic = new memberLogic();
		
		String submit = request.getParameter("submit");
		
		if("insert".equals(submit)) {
			int fee = Integer.parseInt(request.getParameter("fee"));
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			
			signupLogic signup_logic = new signupLogic();
			
			String id = signup_logic.RandomID();
			
			membership_feeBeans beans = new membership_feeBeans(id,accountData.getGroup_id(),start,end,fee);
			
			String log = accountData.getName()+"さんが新しい会費("+fee+"円, 期間："+start+"～"+end+")を設定しました。";
			
			boolean insertFlag = men_logic.insertMembershipFee(beans,log);
			
			if(insertFlag) {
				response.sendRedirect(request.getContextPath() + "/memberServlet");
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：ME-insertMembershipFee");
			    request.getRequestDispatcher("/member.jsp").forward(request, response);
			}
			
		}
	}

}
