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
import Beans.accountPaymentBeans;
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
		
		if(accountData == null) {
			response.sendRedirect("/signinServlet");
			return;
		}
		
		if("一般".equals(accountData.getRole())) {
			response.sendRedirect("/financialServlet");
			return;
		}
		
		accountLogic acc_logic = new accountLogic();
		memberLogic men_logic = new memberLogic();
		List<noticeBeans> notice = acc_logic.getNotice(accountData.getGroup_id());
		
		membership_feeBeans membership_fee = men_logic.getMembershipFee(accountData.getGroup_id());
		
		List<accountPaymentBeans> accountPayment = men_logic.getPaymentUser(accountData.getGroup_id());
		
		List<accountBeans> memberList = men_logic.getMembership(accountData.getGroup_id());
		
		request.setAttribute("notice", notice);
		request.setAttribute("membership_fee", membership_fee);
		request.setAttribute("memberList", memberList);
		request.setAttribute("accountPayment", accountPayment);
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
				response.sendRedirect("/memberServlet");
				return;
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：LI-insertMembershipFee");
			    request.getRequestDispatcher("/member.jsp").forward(request, response);
			    return;
			}
			
		}else if("payment".equals(submit)) {
			String id = request.getParameter("id");
			String target_id = request.getParameter("user_id");
			String status = request.getParameter("answer");
			
			boolean updateFlag = men_logic.updatePaymentStatus(id,status,accountData.getName(),target_id,accountData.getGroup_id());
			
			
			if(updateFlag) {
				response.sendRedirect("/memberServlet");
				return;
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：LI-updatePaymentStatus");
			    request.getRequestDispatcher("/member.jsp").forward(request, response);
			    return;
			}
		}else if("role".equals(submit)) {
			String id = request.getParameter("id");
			String role = request.getParameter("role");
			
			boolean updateFlag = men_logic.updateRole(id,role,accountData.getName(),accountData.getGroup_id());
			
			if(updateFlag) {
				if(id == accountData.getId()) {
					//自分自身のロールを修正した場合
					response.sendRedirect("/logoutServlet");
				}else {
					response.sendRedirect("/memberServlet");
				}				
				return;
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：LI-updateRole");
			    request.getRequestDispatcher("/member.jsp").forward(request, response);
			    return;
			}
			
		}else if("delete".equals(submit)) {
			String id = request.getParameter("id");
			
			boolean deleteFlag = men_logic.deleteMemberList(id,accountData.getName(),accountData.getGroup_id());
			
			if(deleteFlag) {
				if(id == accountData.getId()) {
					//自分自身を削除した場合
					response.sendRedirect("/logoutServlet");
				}else {
					response.sendRedirect("/memberServlet");
				}	
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：LI-deleteMemberList");
			    request.getRequestDispatcher("/member.jsp").forward(request, response);
			    return;
			}
		}
	}

}
