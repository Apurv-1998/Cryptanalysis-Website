

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class uploadServlet
 */
@WebServlet("/uploadServlet")
@MultipartConfig(maxFileSize = 16177215)
public class uploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public uploadServlet() {
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
		// TODO Auto-generated method stub
		response.setContentType("test/html");
		PrintWriter pw = response.getWriter();
		
		
		
		String filename = request.getParameter("fileName");
		String content = request.getParameter("description");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date curDate = new Date();  
	    String uploadtime = (formatter.format(curDate));
	    
	    String topic = request.getParameter("fileTopic");
	    
	    InputStream inputStream = null;
	    
	    Part filePart = request.getPart("fileContent");
	    
	    if(filePart!=null) {
	    	System.out.println(filePart.getName());
	    	System.out.println(filePart.getSize());
	    	System.out.println(filePart.getContentType());
	    	
	    	inputStream = filePart.getInputStream();
	    }
	    
	    String message = null;
	    
	    try {
	    	
	    	DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/cryptanalysis","root","");
            
            PreparedStatement ps = con.prepareStatement("insert into file_table (filename,content,topic,uploadtime,file) values (?,?,?,?,?)");
            
            ps.setString(1, filename);
            ps.setString(2, content);
            ps.setString(3, topic);
            ps.setString(4, uploadtime);
            System.out.println(uploadtime);
            if(inputStream!=null)
            	ps.setBlob(5,inputStream);
            
            int i = ps.executeUpdate();
            
            if(i>0) {
            	message = "Successfully Uploaded To The Database";
            	
            }else {
            	message = "Unsuccessful Attempt";
            	request.setAttribute("Message", message);
            	getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
            }
	    	
	    	
	    }catch(Exception e) {System.out.println(e);}
	    
	    request.setAttribute("Message", message);
    	getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
	    
	}
	
	

}
