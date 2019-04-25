package edu.usc.cs.group8.ImHungry;

import org.junit.Before;
import org.junit.Test;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


public class UserLoginTest {
	
	@Mock
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@Mock 
	HttpSession session;
	
	@Mock
	RequestDispatcher RD;
	
	@Mock
	LoginServlet login;
	
	@Mock
	Connection conn;
	
	@Mock
	PreparedStatement ps;
	
	@Mock
    ResultSet rs;
	
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(rs.next()).thenReturn(true).thenReturn(true);
    }
	
	@Test
	public void testLogin() throws ServletException, IOException, SQLException 
	{
		LoginServlet LS = spy(new LoginServlet());
		when(request.getParameter("username")).thenReturn("test@usc.edu");
		when(request.getParameter("password")).thenReturn("test");
		when(request.getParameter("action")).thenReturn("login");
		LS.service(request,response);
		
		when(request.getParameter("username")).thenReturn("test@usc.edu");
		when(request.getParameter("password")).thenReturn("wrong");
		when(request.getParameter("action")).thenReturn("login");
		LS.service(request,response);
		
		when(request.getParameter("username")).thenReturn("wrong");
		when(request.getParameter("password")).thenReturn("test");
		when(request.getParameter("action")).thenReturn("login");
		LS.service(request,response);
	}
	
	@Test
	public void testRegister() throws ServletException, IOException, SQLException 
	{
		LoginServlet LS = spy(new LoginServlet());
		when(conn.prepareStatement(any(String.class))).thenReturn(ps);
		when(ps.executeQuery()).thenReturn(rs);
		int update = 1;
		when(ps.executeUpdate()).thenReturn(update);
		
		when(request.getParameter("username")).thenReturn("halfond@usc.edu");
		when(request.getParameter("password")).thenReturn("test");
		when(request.getParameter("action")).thenReturn("register");
		LS.service(request,response);
	}
	
	@Test
	public void testLogout() throws ServletException, IOException 
	{
		LoginServlet LS = spy(new LoginServlet());
		when(request.getParameter("action")).thenReturn("logout");
		LS.service(request,response);
	}

}
