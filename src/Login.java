

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 2L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Login is Happening");
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		String first_name = request.getParameter("fname");
		String last_name = request.getParameter("lname");
		String password = request.getParameter("pass");
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/cryptanalysis","root","");
			PreparedStatement ps = con.prepareStatement("select * from register_user where first_name=? and last_name=? and password=?");
			
			ps.setString(1, first_name);
			ps.setString(2, last_name);
			ps.setString(3, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				System.out.println("Entering the if-part");
				String name = first_name+" "+last_name;
				System.out.println(name);
				request.setAttribute("Name", name);
				
				getServletContext().getRequestDispatcher("/Header.jsp").forward(request, response);
			}
			else {
				System.out.println("Entering the else part");
				response.sendRedirect("error.html");
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
		
		pw.close();
	}

}
