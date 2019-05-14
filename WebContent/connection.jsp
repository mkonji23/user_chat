
<html>
<head>
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="javax.naming.*"%>
</head>
<body>
<%
	//1. JNDI 서버 객체 새성
	InitialContext ic= new InitialContext();
	//2. lookup()
	DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/UserChat");
	Connection conn = ds.getConnection();
	Statement stmt = conn.createStatement();
	ResultSet rset = stmt.executeQuery("SELECT VERSION();");
	
	while(rset.next()){
		out.println("MySQL Version: " +rset.getString("version()"));
	}
	rset.close();
	stmt.close();
	conn.close();
	ic.close();
	
	 


%>
</body>
</html>