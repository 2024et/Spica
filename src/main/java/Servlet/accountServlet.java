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
import Logic.accountLogic;
import Logic.signinLogic;
import Logic.signupLogic;

@WebServlet("/accountServlet")
public class accountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		accountLogic logic = new accountLogic();
		List<noticeBeans> notice = logic.getNotice(accountData.getId());
		
		request.setAttribute("notice", notice);
		request.getRequestDispatcher("/account.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
	
		accountLogic acc_logic = new accountLogic();
		
		
		String submit = request.getParameter("submit");
		if("pass".equals(submit)) {
			//パスワードの再設定
			String current = request.getParameter("currentPassword");
			String new_1 = request.getParameter("newPassword_1");
			String new_2 = request.getParameter("newPassword_2");
			
			//現在のパスワード認証
			signinLogic signin_logic = new signinLogic();
			int loginFlag = signin_logic.login(accountData.getEmail(),current);
			
			if(loginFlag == 2) {
				request.setAttribute("errorMessage", "現在のパスワードが違います。再度やり直してください。");
			    request.getRequestDispatcher("/account.jsp").forward(request, response);
			    return;
			}
			
			//パスワードのチェック
			if(!new_1.equals(new_2)) {
				request.setAttribute("errorMessage", "パスワードが一致しませんでした。再度やり直してください。");
			    request.getRequestDispatcher("/account.jsp").forward(request, response);
			    return;
			}
			
			//更新
			signupLogic signup_logic = new signupLogic();
			
			String hashed_password = signup_logic.hashPassword(new_2);
			
			boolean updateFlag = acc_logic.changePassword(accountData.getId(),hashed_password);
			
			if(!updateFlag) {
				request.setAttribute("errorMessage", "パスワードの更新に失敗しました。");
			    request.getRequestDispatcher("/account.jsp").forward(request, response);
			    return;
			}
			
			//再ログイン
			int reLoginFlag = signin_logic.login(accountData.getEmail(), hashed_password);
			if(reLoginFlag == 2) {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。");
			    request.getRequestDispatcher("/account.jsp").forward(request, response);
			    return;
			}
			
			//再取得
			accountBeans beans = signin_logic.getBeans();
			session.setAttribute("accountData", beans);
			
			response.sendRedirect(request.getContextPath() + "/accountServlet");
			
		}else if("acc".equals(submit)) {
			//アカウント情報の変更
			
		}else {
			
		}
		
	}

}
