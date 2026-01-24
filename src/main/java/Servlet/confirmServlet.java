package Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Logic.confirmLogic;

@WebServlet("/confirmServlet")
public class confirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public confirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		confirmLogic logic = new confirmLogic();
		boolean completeFlag = logic.register(id);
		if(completeFlag) {
			request.setAttribute("Message", "本登録が完了しました。");
			request.getRequestDispatcher("/signinServlet").forward(request, response);
		}else {
			request.setAttribute("errorMessage", "本登録に失敗しました。");
			request.getRequestDispatcher("/signupServlet").forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
