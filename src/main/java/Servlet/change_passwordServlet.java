package Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Logic.change_passwordLogic;

@WebServlet("/change_passwordServlet")
public class change_passwordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
	    request.getRequestDispatcher("/change_password.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String password_1 = request.getParameter("password_1");
		String password_2 = request.getParameter("password_2");
		
		//パスワードが2つとも正しいか確認
		if(!password_1.equals(password_2)) {
			request.setAttribute("errorMessage", "パスワードが一致しませんでした。再度やり直してください。");
		    request.getRequestDispatcher("/change_password.jsp").forward(request, response);
		    return;
		}
		
		//更新
		change_passwordLogic logic = new change_passwordLogic();
		boolean completeFlag = logic.updatePassword(id,password_1);
		
		if(completeFlag) {
			request.setAttribute("Message", "パスワードの更新が完了しました。");
		    request.getRequestDispatcher("/signinServlet").forward(request, response);
		}else {
			request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。");
		    request.getRequestDispatcher("/change_password.jsp").forward(request, response);
		}
	}

}
