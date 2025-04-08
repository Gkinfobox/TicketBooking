

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/Account")
public class Account extends HttpServlet {
	private static final long serialVersionUID = 1L;


	private String dburl = "jdbc:mysql://localhost:3306/ticketbooking";
	private String dbuname = "root";
	private String dbpwd = "Gkumar@12";
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		String username = request.getParameter("username");
		String gender = request.getParameter("Gender");
		String mobile = request.getParameter("mno");
		String gmail = request.getParameter("mail");
		String uname = request.getParameter("loginname");
		String pass = request.getParameter("Pass");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try (Connection con = DriverManager.getConnection(dburl,dbuname,dbpwd);
				PreparedStatement ps = con.prepareStatement("insert into ticketbooking.records (username,gender,mobile,gmail,uname,pass) values (?,?,?,?,?,?)"))
		{
			ps.setString(1, username);
			ps.setString(2, gender);
			ps.setString(3, mobile);
			ps.setString(4, gmail);
			ps.setString(5, uname);
			ps.setString(6, pass);
			ps.executeUpdate();
			
			response.sendRedirect("loginsuccess.jsp");
			
		}
		catch(Exception e)
		{
			response.sendRedirect("loginerror.jsp");
			System.out.println(e);
		}
	}

}
