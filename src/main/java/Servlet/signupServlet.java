package Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Logic.signupLogic;

@WebServlet("/signupServlet")
public class signupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/signup.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String mail = request.getParameter("mail");
		String password_1 = request.getParameter("password_1");
		String password_2 = request.getParameter("password_2");
		
		//メールアドレスの重複を確認
		signupLogic mail_logic = new signupLogic();
		int mailDupliFlag = mail_logic.mailDupli(mail);
		if(mailDupliFlag==1) {
			request.setAttribute("errorMessage", "すでにこのメールアドレスは使用されています。別のメールアドレスを登録してください。");
		    request.getRequestDispatcher("/signup.jsp").forward(request, response);
		    return;
		}else if(mailDupliFlag==2) {
			request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：SU-mD1000");
		    request.getRequestDispatcher("/signup.jsp").forward(request, response);
		    return;
		}
		
		//パスワードが2つとも正しいか確認
		if(!password_1.equals(password_2)) {
			request.setAttribute("errorMessage", "パスワードが一致しませんでした。再度やり直してください。");
		    request.getRequestDispatcher("/signup.jsp").forward(request, response);
		    return;
		}
		
		//仮登録
		signupLogic temp_logic = new signupLogic();
		boolean signupFlag = temp_logic.tempAccount(name,mail,password_1);
		if(signupFlag) {
			request.setAttribute("Message", "入力されたメールアドレス宛に仮登録のご案内メールを送信しました。メール内記載のリンクにアクセスして本登録を完了させてください。");
		    request.getRequestDispatcher("/signup.jsp").forward(request, response);
		}else {
			request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：SU-tA1000");
		    request.getRequestDispatcher("/signup.jsp").forward(request, response);
		}
		
	}

}
