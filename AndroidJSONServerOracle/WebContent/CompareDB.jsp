<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR" import="org.json.simple.*" import="java.sql.*"
	import="java.util.*"%>

<%
	String numbers = request.getParameter("numbers");

JSONObject jsonMain = new JSONObject();
JSONArray jArray = new JSONArray();
JSONObject jObject = new JSONObject();


Class.forName();
try {
	Connection conn = DriverManager.getConnection();
	Statement stmt = conn.createStatement();
	String query = ;

	ResultSet rs = stmt.executeQuery(query);
 
				int i=0;
			     while(rs.next()){ 
			    	    String _numberss = rs.getString("NUMBERS");
			    		
			    	    if(numbers.equals(_numberss)){
			    			
				   		i = 1;
						jObject.put("msg1", numbers);
					}
				}

				if (i == 0) {
					jObject.put("msg1", "fail");

				}

				stmt.close();
				conn.close();

				jArray.add(0, jObject);

				jsonMain.put("List", jArray);

				out.println(jsonMain.toJSONString());

			} catch (Exception e) {
			}
		%>