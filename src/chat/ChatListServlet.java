package chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/ChatListServlet"})
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String listType = request.getParameter("listType");
		if (fromID != null && !fromID.equals("") && toID != null && !toID.equals("") && listType != null
				&& !listType.equals("")) {
			if (listType.equals("ten")) {
				response.getWriter()
						.write(this.getTen(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8")));
			} else {
				try {
					response.getWriter().write(
							this.getID(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8"), listType));
				} catch (Exception var7) {
					response.getWriter().write("");
				}
			}
		} else {
			response.getWriter().write("");
		}

	}

	public String getTen(String fromID, String toID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getChatListByRencent(fromID, toID, 10);
		if (chatList.size() == 0) {
			return "";
		} else {
			for (int i = 0; i < chatList.size(); ++i) {
				result.append("[{\"value\": \"" + ((ChatDTO) chatList.get(i)).getFromID() + "\"},");
				result.append("{\"value\": \"" + ((ChatDTO) chatList.get(i)).getToID() + "\"},");
				result.append("{\"value\": \"" + ((ChatDTO) chatList.get(i)).getChatContent() + "\"},");
				result.append("{\"value\": \"" + ((ChatDTO) chatList.get(i)).getChatTimes() + "\"}]");
				if (i != chatList.size() - 1) {
					result.append(",");
				}
			}

			result.append("],\"last\":\"" + ((ChatDTO) chatList.get(chatList.size() - 1)).getChatID() + "\"}");
			return result.toString();
		}
	}

	public String getID(String fromID, String toID, String chatID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getChatListByID(fromID, toID, chatID);
		if (chatList.size() == 0) {
			return "";
		} else {
			for (int i = 0; i < chatList.size(); ++i) {
				result.append("[{\"value\":\"" + ((ChatDTO) chatList.get(i)).getFromID() + "\"},");
				result.append("{\"value\":\"" + ((ChatDTO) chatList.get(i)).getToID() + "\"},");
				result.append("{\"value\":\"" + ((ChatDTO) chatList.get(i)).getChatContent() + "\"},");
				result.append("{\"value\":\"" + ((ChatDTO) chatList.get(i)).getChatTimes() + "\"}]");
				if (i != chatList.size() - 1) {
					result.append(",");
				}
			}

			result.append("],\"last\":\"" + ((ChatDTO) chatList.get(chatList.size() - 1)).getChatID() + "\"}");
			return result.toString();
		}
	}
}