import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/loginservlet")  
public class loginservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private String dburl = "jdbc:mysql://localhost:3306/ticketbooking";
    private String dbuname = "root";
    private String dbpwd = "Gkumar@12";
   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String uname = request.getParameter("uname");
        String pass = request.getParameter("pass");
        
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

        try (Connection con = DriverManager.getConnection(dburl, dbuname, dbpwd);
             PreparedStatement ps = con.prepareStatement("SELECT * FROM records WHERE uname=? AND pass=?")) {

            ps.setString(1, uname);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                
                String userName = rs.getString("username");
                String mobileNumber = rs.getString("mobile");
                String email = rs.getString("gmail");

                // Store user details in session
                HttpSession session = request.getSession();
                session.setAttribute("userName", userName);  // Use descriptive attribute names
                session.setAttribute("mobileNumber", mobileNumber);
                session.setAttribute("email", email);

                // Forward to home page after successful login
                RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
                dispatcher.forward(request, response);
            } else {
                // If login fails, redirect to login error page
                response.sendRedirect("loginerror.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();  // This prints errors in the console for debugging
            response.sendRedirect("error.jsp");  // Redirect to an error page (optional)
        }
    }
}
