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
import Beans.chatBeans;
import Beans.purchase_requestBeans;
import Logic.purchase_request_detailLogic;

@WebServlet("/purchase_request_detailServlet")
public class purchase_request_detailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		if(accountData == null) {
			response.sendRedirect("/signinServlet");
			return;
		}
		
		String requestID = request.getParameter("requestID");
		
		purchase_request_detailLogic logic = new purchase_request_detailLogic();
		purchase_requestBeans beans = logic.getRequestData(requestID);
		
		List<chatBeans> chat = logic.getChat(requestID);
		
		if(beans == null || chat == null) {
			request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：FD-getRequestData/getChat");
			request.getRequestDispatcher("/purchase_request_list.jsp").forward(request, response);
		}
		
		request.setAttribute("chat", chat);
		request.setAttribute("request", beans);
		request.getRequestDispatcher("/purchase_request_detail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		accountBeans accountData = (accountBeans) session.getAttribute("accountData");
		
		String submit = request.getParameter("submit");
		String requestID = request.getParameter("id");
		String log = null;
		purchase_request_detailLogic logic = new purchase_request_detailLogic();
		
		if("delete".equals(submit)) {
			//申請の取り消し
			log = accountData.getName()+"さんが、希望申請を取り消しました。";
			
			boolean deleteFlag = logic.deleteRequestData(requestID,accountData.getGroup_id(),log);
			
			if(deleteFlag) {
				response.sendRedirect("/purchase_request_listServlet");
				return;
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：FD-deleteRequestData");
				request.getRequestDispatcher("/purchase_request_detail.jsp").forward(request, response);
				return;
			}
		}else if("form".equals(submit)) {
			//申請内容の編集
			log = accountData.getName()+"さんが、希望申請を編集しました。";
			
			String selected_date = "";
			selected_date = request.getParameter("selected_date");
			String name = request.getParameter("name");
			String item = request.getParameter("item");
			String purpose = request.getParameter("purpose");
			int amount = Integer.parseInt(request.getParameter("amount"));
			String link = request.getParameter("link");
			String status = null;
			
			purchase_requestBeans updateBeans = new purchase_requestBeans(requestID,accountData.getGroup_id(),selected_date,name,item,amount,accountData.getId(),accountData.getName(),link,purpose,status);
			
			boolean updateFlag = logic.updateRequestData(updateBeans,log);
			
			if(updateFlag) {
				response.sendRedirect("/purchase_request_detailServlet?requestID=" + requestID);
				return;
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：FD-updateRequestData");
				request.getRequestDispatcher("/purchase_request_detail.jsp").forward(request, response);
				return;
			}
		}else if("status".equals(submit)) {
			//申請のステータス変更
			log = accountData.getName()+"さんが、希望申請ステータスを変更しました。";
			
			String status = request.getParameter("radio");
			
			boolean statusFlag = logic.updateStatus(requestID,status,accountData.getGroup_id(),log);
			
			if(statusFlag) {
				response.sendRedirect("/purchase_request_detailServlet?requestID=" + requestID);
				return;
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：FD-updateStatus");
				request.getRequestDispatcher("/purchase_request_detail.jsp").forward(request, response);
				return;
			}
		}else if("send".equals(submit)) {
			//チャット
			String message = request.getParameter("message");
			boolean sendFlag = logic.sendMessage(accountData.getId(),requestID,message,accountData.getGroup_id());
			
			if(sendFlag) {
				response.sendRedirect("/purchase_request_detailServlet?requestID=" + requestID);
				return;
			}else {
				request.setAttribute("errorMessage", "予期しないエラーが発生しました。再度やり直してください。エラーコード：FD-sendMessage");
				request.getRequestDispatcher("/purchase_request_detail.jsp").forward(request, response);
				return;
			}
		}
	}

}
