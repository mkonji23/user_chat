package user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/UserLoginServlet"})
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html);charset=UTF-8");
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		if (userID != null && !userID.equals("") && userPassword != null && !userPassword.equals("")) {
			int result = (new UserDAO()).login(userID, userPassword);
			if (result == 1) {
				request.getSession().setAttribute("userID", userID);
				request.getSession().setAttribute("messageType", "성공 메시지");
				request.getSession().setAttribute("messageContent", "로그인에 성공했습니다.");
				response.sendRedirect("index.jsp");
			} else if (result == 2) {
				request.getSession().setAttribute("messageType", "오류 메시지");
				request.getSession().setAttribute("messageContent", "비밀번호를 확인하세요.");
				response.sendRedirect("login.jsp");
			} else if (result == 0) {
				request.getSession().setAttribute("messageType", "오류 메시지");
				request.getSession().setAttribute("messageContent", "아이디가 존재하지않습니다.");
				response.sendRedirect("login.jsp");
			} else if (result == -1) {
				request.getSession().setAttribute("messageType", "오류 메시지");
				request.getSession().setAttribute("messageContent", "db오류 발생.");
				response.sendRedirect("login.jsp");
			}

		} else {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "아이디 비밀번호를 입력하세요.");
			response.sendRedirect("login.jsp");
		}
	}
}