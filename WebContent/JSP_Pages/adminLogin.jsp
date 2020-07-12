<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Login</title>
</head>
<body>

	<% 
	
		String user_name = request.getParameter("userName");
		String password = request.getParameter("pass");
		
		String message = null;
		
		try{
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/cryptanalysis","root","");
            
            PreparedStatement ps = con.prepareStatement("select * from admin_login where user_name = ? and password = ?");
            
            ps.setString(1,user_name);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
            	System.out.println("Admin Login Successful");
            	response.sendRedirect("../admin_submodule.html");
            }
            else{
            	message = "You Are Not Admin";
            	request.setAttribute("Message", message);
            	getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
            }
            	
            
			
		}catch(Exception e){System.out.println(e);}
	
		
	
	
	
	
	%>

</body>
</html>