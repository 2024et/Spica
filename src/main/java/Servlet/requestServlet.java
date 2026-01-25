package Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Logic.requestLogic;

@WebServlet("/requestServlet")
public class requestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/request.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mail = request.getParameter("mail");
		
		requestLogic logic = new requestLogic();
		int checkMail = logic.searchMail(mail);
		
		//メールアドレスが存在しない場合もエラー扱いとして表示
		if(checkMail == 0 || checkMail == 2) {
			request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。");
		    request.getRequestDispatcher("/request.jsp").forward(request, response);
		}
		
		boolean completeFlag = logic.sendMail(mail);
		
		if(completeFlag) {
			request.setAttribute("Message", "パスワード再設定リクエストをメールにて送信しました。メールをご確認ください。");
		    request.getRequestDispatcher("/request.jsp").forward(request, response);
		}else {
			request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。");
		    request.getRequestDispatcher("/request.jsp").forward(request, response);
		}
	}

}
