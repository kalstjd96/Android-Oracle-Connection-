<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR" import="org.json.simple.*"%>

<%@ page import="java.sql.*"%>

<%
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");

	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject = new JSONObject();

	Class.forName();
	try {
		Connection conn = DriverManager.getConnection();
		Statement stmt = conn.createStatement();
		String query = 

		ResultSet rs = stmt.executeQuery(query);

		int i = 0;
		while (rs.next()) {

			String _id = rs.getString("ID");
			String _pw = rs.getString("PWD");
			if (id.equals(_id) && pw.equals(_pw)) {
				i = 1;
				jObject.put("msg1", "success");
				jObject.put("msg2", "two");
				jObject.put("msg3", "three");
			}
		}

		if (i == 0) {
			jObject.put("msg1", "fail");
			jObject.put("msg2", "two");
			jObject.put("msg3", "three");

		}

		stmt.close();
		conn.close();

		jArray.add(0, jObject);

		jsonMain.put("List", jArray);

		out.println(jsonMain.toJSONString());

	} catch (Exception e) {
	}
%>