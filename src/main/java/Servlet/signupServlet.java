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
		
		signupLogic logic = new signupLogic();
		
		//メールアドレスの重複を確認
		int mailDupliFlag = logic.mailDupli(mail);
		if(mailDupliFlag==1) {
			request.setAttribute("errorMessage", "すでにこのメールアドレスは使用されています。別のメールアドレスを登録してください。");
		    request.getRequestDispatcher("/signup.jsp").forward(request, response);
		    return;
		}else if(mailDupliFlag==2) {
			request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：SU-mailDupli");
		    request.getRequestDispatcher("/signup.jsp").forward(request, response);
		    return;
		}
		
		//パスワードが2つとも正しいか確認
		if(!password_1.equals(password_2)) {
			request.setAttribute("errorMessage", "パスワードが一致しませんでした。再度やり直してください。");
		    request.getRequestDispatcher("/signup.jsp").forward(request, response);
		    return;
		}
		
		//パスワードチェック
		boolean check = logic.checkPassword(password_1);
		if(!check) {
			request.setAttribute("errorMessage", "パスワードが条件を満たしていません。再度やり直してください。\nパスワードは、英数字8桁以上30文字以下である必要があります。");
		    request.getRequestDispatcher("/signup.jsp").forward(request, response);
		    return;	
		}
		
		//仮登録		
		boolean signupFlag = logic.tempAccount(name,mail,password_1);
		if(signupFlag) {
			request.setAttribute("Message", "入力されたメールアドレス宛に仮登録のご案内メールを送信しました。メール内記載のリンクにアクセスして本登録を完了させてください。");
		    request.getRequestDispatcher("/signup.jsp").forward(request, response);
		    return;
		}else {
			request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：SU-tempAccount");
		    request.getRequestDispatcher("/signup.jsp").forward(request, response);
		    return;
		}
		
	}

}
