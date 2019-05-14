package chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ChatDAO {
	DataSource dataSource;

	public ChatDAO() {
		try {
			InitialContext initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			this.dataSource = (DataSource) envContext.lookup("jdbc/UserChat");
		} catch (Exception var3) {
			var3.printStackTrace();
		}

	}

	public ArrayList<ChatDTO> getChatListByID(String fromID, String toID, String chatID) {
		ArrayList<ChatDTO> chatList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM CHAT WHERE ((fromID = ? AND toID =?) OR (fromID = ? AND toID= ?)) AND chatID >? ORDER BY CHATTIME";

		try {
			conn = this.dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, Integer.parseInt(chatID));
			rs = pstmt.executeQuery();
			chatList = new ArrayList();

			while (rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt("chatID"));
				chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt")
						.replaceAll(">", "&gt").replaceAll("/n", "<br>"));
				chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt")
						.replaceAll(">", "&gt").replaceAll("/n", "<br>"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt")
						.replaceAll(">", "&gt").replaceAll("/n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if (chatTime >= 12) {
					timeType = "오후";
					chatTime -= 12;
				}

				chat.setChatTimes(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + "시 "
						+ rs.getString("chatTime").substring(14, 16) + "분");
				chatList.add(chat);
			}
		} catch (Exception var20) {
			var20.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (Exception var19) {
				var19.printStackTrace();
			}

		}

		return chatList;
	}

	public ArrayList<ChatDTO> getChatListByRencent(String fromID, String toID, int number) {
		ArrayList<ChatDTO> chatList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM CHAT WHERE ((fromID = ? AND toID =?) OR (fromID = ? AND toID= ?)) AND chatID >(SELECT MAX(chatID)-? FROM  CHAT)ORDER BY CHATTIME";

		try {
			conn = this.dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, number);
			rs = pstmt.executeQuery();
			chatList = new ArrayList();

			while (rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt("chatID"));
				chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt")
						.replaceAll(">", "&gt").replaceAll("/n", "<br>"));
				chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt")
						.replaceAll(">", "&gt").replaceAll("/n", "<br>"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt")
						.replaceAll(">", "&gt").replaceAll("/n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if (chatTime >= 12) {
					timeType = "오후";
					chatTime -= 12;
				}

				chat.setChatTimes(rs.getString("chatTime").substring(0, 11) + " " + chatTime + " "
						+ rs.getString("chatTime").substring(14, 16) + " ");
				chatList.add(chat);
			}
		} catch (Exception var20) {
			var20.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (Exception var19) {
				var19.printStackTrace();
			}

		}

		return chatList;
	}

	public int submit(String fromID, String toID, String chatContent) {
		ArrayList<ChatDTO> chatList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String SQL = "INSERT INTO CHAT VALUES(NULL,?,?,?,NOW())";

		try {
			conn = this.dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, chatContent);
			int var10 = pstmt.executeUpdate();
			return var10;
		} catch (Exception var18) {
			var18.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (Exception var17) {
				var17.printStackTrace();
			}

		}

		return -1;
	}
}