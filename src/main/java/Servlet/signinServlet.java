package Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Logic.signinLogic;

@WebServlet("/signinServlet")
public class signinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/signin.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		
		
		signinLogic logic = new signinLogic();
		int completeFlag = logic.login(mail,password);
		
		if(completeFlag==2) {
			request.setAttribute("errorMessage", "メールアドレスまたはパスワードが一致しませんでした。");
		    request.getRequestDispatcher("/signin.jsp").forward(request, response);
		    
		}
		if(completeFlag == 0) {
			request.getRequestDispatcher("/financialServlet").forward(request, response);
		}else {
			request.getRequestDispatcher("/select_groupServlet").forward(request, response);
		}
	}

}
