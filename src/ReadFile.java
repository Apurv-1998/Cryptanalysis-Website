

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class read_file_in_browser
 */
@WebServlet("/ReadFile")
public class ReadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadFile() {
        super();
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
		
		response.setContentType("application/pdf");
		
		String id = request.getParameter("fileId");
		
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		System.out.println("Captcha Response "+gRecaptchaResponse);
		
		
		boolean verify =  VerifyRecaptcha.verify(gRecaptchaResponse);
		
		
		String message = null;
		
		
		if(id==null) {
			message = "Add Valid File Id";
			request.setAttribute("Message", message);
        	getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
		}
		else {
			if(verify) {
				System.out.println("Human");

				ServletOutputStream sos;
				response.setHeader("Content-disposition", "inline; filename="+id+".pdf");
				sos = response.getOutputStream();
				try {
					DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		            Class.forName("com.mysql.jdbc.Driver");
		            
		            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/cryptanalysis","root","");
		            
		            PreparedStatement ps = con.prepareStatement("select file from file_table where id = ?");
		            
		            ps.setString(1, id.trim());
		            
		            ResultSet rs = ps.executeQuery();
		            
		            if(rs.next()) {
		            	sos.write(rs.getBytes("file"));
		            }
		            else {
		            	message = "File With Entered Id DoesNot Exist";
		            	request.setAttribute("Message", message);
		            	getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
		            }
					
					
				}catch(Exception e) {
					System.out.println(e);
				}
				
				sos.flush();
				sos.close();
			}
			else {
				message = "Captcha Verification Failed";
				request.setAttribute("Message", message);
            	getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
			}
		}
	}

}
