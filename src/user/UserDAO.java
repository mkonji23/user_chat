package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {
	DataSource dataSource;

	public UserDAO() {
		try {
			InitialContext initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			this.dataSource = (DataSource) envContext.lookup("jdbc/UserChat");
		} catch (Exception var3) {
			var3.printStackTrace();
		}

	}

	public int login(String userID, String userPassword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM USER WHERE userID = ?";

		try {
			conn = this.dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				return 0;
			}

			if (rs.getString("userPassword").equals(userPassword)) {
				return 1;
			}

			return 2;
		} catch (Exception var18) {
			var18.printStackTrace();
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
			} catch (Exception var17) {
				var17.printStackTrace();
			}

		}

		return -1;
	}

	public int registerCheck(String userID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM USER WHERE userID = ?";

		try {
			conn = this.dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if (rs.next() || userID.equals("")) {
				return 0;
			}
		} catch (Exception var16) {
			var16.printStackTrace();
			return -1;
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
			} catch (Exception var15) {
				var15.printStackTrace();
			}

		}

		return 1;
	}

	public int register(String userID, String userPassword, String userName, String userAge, String userGender,
			String userEmail, String userProfile) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int val = -1;
		String SQL = "INSERT INTO USER VALUES(?,?,?,?,?,?,?)";

		try {
			conn = this.dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(3, userPassword);
			pstmt.setString(2, userName);
			pstmt.setInt(4, Integer.parseInt(userAge));
			pstmt.setString(5, userGender);
			pstmt.setString(6, userEmail);
			pstmt.setString(7, userProfile);
			pstmt.executeUpdate();
			val = 1;
		} catch (Exception var21) {
			var21.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (Exception var20) {
				var20.printStackTrace();
			}

		}

		return val;
	}
}