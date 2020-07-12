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
<title>Files In the Database</title>
</head>
<body bgcolor="yellow">

	<%
		try{
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/cryptanalysis","root","");
            
            PreparedStatement ps = con.prepareStatement("select * from file_table");
            
            ResultSet rs = ps.executeQuery();
            
         %>
         <table border = 1 align = center style="text-align: center">
         	<thead>
         		<tr>
         			<th>File Id</th>
         			<th>File_Name</th>
         			<th>File-Description</th>
         			<th>File-Topic</th>
         			<th>File Created On </th>
         			<th>Download Link</th>
        		</tr>
        	</thead>
        	<tbody>
        		<%
        			while(rs.next()){
        				%>
        				<tr>
        					<td><%= rs.getString("id") %></td>
        					<td><%= rs.getString("filename") %></td>
        					<td><%= rs.getString("content") %></td>
        					<td><%= rs.getString("topic") %></td>
        					<td><%= rs.getString("uploadtime") %></td>
        					<td><a href = "../download_file.jsp">download</a></td>
        				</tr>
        			<% } %>
        	</tbody>
        	</table><br>	
			
	 	<% }catch(Exception e){
	 		System.out.println(e);%><br><%
	 	}
	
	
	%>

</body>
</html>