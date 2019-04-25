package edu.usc.cs.group8.ImHungry;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	
	//PUT YOUR OWN MYSQL DATABASE USERNAME AND PASSWORD HERE
	static String DB_USERNAME = "root";
	static String DB_PASSWORD = "12345";
    
	
    @Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project2?user=" + DB_USERNAME + "&password=" + DB_PASSWORD + "&userSSL=false&serverTimezone=UTC");
			
			String username = request.getParameter("email");
			String password = request.getParameter("password");
			String action = request.getParameter("action");
			
			PrintWriter out = response.getWriter();
			
			if (action.equals("register")) {
				ps = conn.prepareStatement("INSERT INTO User (username, password) VALUES (?,?)");
				ps.setString(1, username);
				ps.setString(2, password);
				int update = ps.executeUpdate();
				if (update == 1) {
					out.println("Registration successful");
					request.getSession().setAttribute("username", username);
					response.sendRedirect(request.getContextPath() + "/search_page.jsp");
				} else {
					out.println("Registration failed");
				}
			} else if (action.equals("login")) {
				ps = conn.prepareStatement("SELECT * FROM User WHERE username = ?");
				ps.setString(1, username);
				
				rs = ps.executeQuery();
				if (rs.next()) {
					if (password.equals(rs.getString("password"))) {
						out.println("Login successful");
						request.getSession().setAttribute("username", username);
						response.sendRedirect(request.getContextPath() + "/search_page.jsp");
					} else {
						out.println("Incorrect password");
					}
				}
			} else {
				request.getSession().invalidate();
			}
			
//			ps = conn.prepareStatement("SELECT * FROM User");
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				String un = rs.getString("username");
//				String pw = rs.getString("password");
//				System.out.println("Username: " + un);
//				System.out.println("Password: " + pw + "\n");
//			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
    }

}
