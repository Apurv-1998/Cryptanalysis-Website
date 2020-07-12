

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
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
 * Servlet implementation class downloadServlet
 */
@WebServlet("/downloadServlet")
public class downloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public downloadServlet() {
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
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		
		String filename = request.getParameter("fileName");
		
		File newFile = new File("C:/Users/hp/Downloads"+filename);
		
		String path = "C:/Users/hp/Downloads/"+filename;
		
		//getting the recaptcha request
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		System.out.println("Response "+gRecaptchaResponse);
		
		boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
		String message = null;
		
		if(verify) {
			System.out.println("Human");
			try {
				
				DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	            Class.forName("com.mysql.jdbc.Driver");
	            
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/cryptanalysis","root","");
	            
	            PreparedStatement ps = con.prepareStatement("select * from file_table where filename = ?");
	            
	            ps.setString(1, filename);
	            
	            ResultSet rs = ps.executeQuery();
				
	            if(rs.next()) {
	            	Blob blob = rs.getBlob("file");
	            	InputStream input = blob.getBinaryStream();
	            	OutputStream output = new FileOutputStream(path);
	            	
	            	int bytesRead = -1;
	            	
	            	byte[] buffer = new byte[4096];
	            	
	            	while((bytesRead = input.read(buffer))!=-1) {
	            		output.write(buffer,0,bytesRead);
	            	}
	            	input.close();
	            	output.close();
	            	message = "File Saved in "+path;
	            	request.setAttribute("Message", message);
	            	getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
	            }
	            else {
	            	message = "File Not Found";
	            	request.setAttribute("Message", message);
	            	getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
	            }
				
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		else {
			System.out.println("Not Verified");
		}
		
		
	}

}
