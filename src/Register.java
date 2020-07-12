

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Register() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 System.out.println("It is happening");
	        response.setContentType("text/html");
	        PrintWriter pw = response.getWriter();
	        
	        String first_name = request.getParameter("fname");
	        String last_name = request.getParameter("lname");
	        String mail_id = request.getParameter("mail");
	        String birth_date = request.getParameter("date");
	        String password = request.getParameter("pass");
	        String mobile_number = request.getParameter("mobile");
	        
	        try{
	        	DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	            Class.forName("com.mysql.jdbc.Driver");
	            
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/cryptanalysis","root","");
	            
	            PreparedStatement ps = con.prepareStatement("insert into register_user values(?,?,?,?,?,?)");
	            
	            ps.setString(1, first_name);
	            ps.setString(2, last_name);
	            ps.setString(3, mail_id);
	            ps.setString(4, birth_date);
	            ps.setString(5, password);
	            ps.setString(6, mobile_number);
	            
	            int i = ps.executeUpdate();
	            if(i>0) {
	                response.sendRedirect("dataUser.html");
	            }
	            else
	                response.sendRedirect("registration.html");
	        }catch(Exception e){System.out.println(e);}
	        pw.close();
		
	}

}
