package Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import Logic.select_groupLogic;

@WebServlet("/select_groupServlet")
public class select_groupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/select_group.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		
		String submit = request.getParameter("submit");
		select_groupLogic logic = new select_groupLogic();
		boolean completeFlag;
		
		if("join".equals(submit)) {
			//既存団体に参加
			String code = request.getParameter("invite");
			
			completeFlag = logic.joinGroup(userId,code);
			
			if(completeFlag) {
				request.getRequestDispatcher("/financialServlet").forward(request, response);
				return;
			}else {
				request.setAttribute("join_errorMessage", "招待コードが正しくありません。");
			    request.getRequestDispatcher("/select_group.jsp").forward(request, response);
			    return;
			}
			
		}else if("make".equals(submit)) {
			//新規団体の作成
			String name = request.getParameter("name");
			
			boolean checkName = logic.checkName(name);
			
			if(!checkName) {
				request.setAttribute("make_errorMessage", "入力内容が不正です。");
			    request.getRequestDispatcher("/select_group.jsp").forward(request, response);
			    return;
			}
			
			completeFlag = logic.makeGroup(userId,name);
			
			if(completeFlag) {
				request.getRequestDispatcher("/financialServlet").forward(request, response);
				return;
			}else {
				request.setAttribute("make_errorMessage", "予期しないエラーが発生しました。再度やり直してください。");
			    request.getRequestDispatcher("/select_group.jsp").forward(request, response);
			    return;
			}
			
		}else {
			request.getRequestDispatcher("/select_group.jsp").forward(request, response);
		}
	}

}
