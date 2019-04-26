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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


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
       // when(rs.next()).thenReturn(true).thenReturn(true);
    }
	
	@Test
	public void testLogin() throws ServletException, IOException, SQLException 
	{
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
		/*HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);*/
		/*
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher RD = mock(RequestDispatcher.class);*/
		LoginServlet LS = new LoginServlet();
		
		when(request.getParameter("email")).thenReturn("test@usc.edu");
		when(request.getParameter("password")).thenReturn("test");
		when(request.getParameter("action")).thenReturn("login");
        when(request.getSession()).thenReturn(session);
        
        when(response.getWriter()).thenReturn(pw);
        when(request.getRequestDispatcher("search_page.jsp")).thenReturn(RD);
        when(request.getRequestDispatcher("login_page.html")).thenReturn(RD);

        
        
		LS.service(request,response);
				
		when(request.getParameter("email")).thenReturn("test@usc.edu");
		when(request.getParameter("password")).thenReturn("wrong");
		when(request.getParameter("action")).thenReturn("login");
		LS.service(request,response);
		
		when(request.getParameter("email")).thenReturn("wrong");
		when(request.getParameter("password")).thenReturn("test");
		when(request.getParameter("action")).thenReturn("login");
		LS.service(request,response);
		
		when(request.getParameter("email")).thenReturn("test@usc.edu");
		when(request.getParameter("password")).thenReturn("test");
		when(request.getParameter("action")).thenReturn("register");
		LS.service(request,response);
	}
	
	@Test
	public void testRegister() throws ServletException, IOException, SQLException 
	{
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
		
		LoginServlet LS = spy(new LoginServlet());
		when(conn.prepareStatement(any(String.class))).thenReturn(ps);
		when(ps.executeQuery()).thenReturn(rs);
		int update = 1;
		when(ps.executeUpdate()).thenReturn(update);
		
		when(request.getParameter("email")).thenReturn("halfond@usc.edu");
		when(request.getParameter("password")).thenReturn("test");
		when(request.getParameter("action")).thenReturn("register");
		
		when(request.getSession()).thenReturn(session);
        
        when(response.getWriter()).thenReturn(pw);
        when(request.getRequestDispatcher("search_page.jsp")).thenReturn(RD);
        when(request.getRequestDispatcher("login_page.html")).thenReturn(RD);

		LS.service(request,response);
	}
	
	@Test
	public void testLogout() throws ServletException, IOException 
	{
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
		LoginServlet LS = spy(new LoginServlet());
		when(request.getParameter("action")).thenReturn("logout");
		
		when(request.getSession()).thenReturn(session);
        
        when(response.getWriter()).thenReturn(pw);
        when(request.getRequestDispatcher("search_page.jsp")).thenReturn(RD);
        when(request.getRequestDispatcher("login_page.html")).thenReturn(RD);
		LS.service(request,response);
	}
	
	@Test
	public void testDataPersistence() throws ServletException, IOException, ClassNotFoundException 
	{
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
		
		IHManageList manager = new IHManageList();
		// test@usc.edu as user parameter in functions
		ArrayList<Restaurant> testRestaurants2 = new ArrayList<Restaurant>();
		
		ArrayList<Recipe> testRecipe2 = new ArrayList<Recipe>();
		Restaurant temp1 = new Restaurant("mcd", "");
		testRestaurants2.add(temp1);
		ArrayList<String> tempIngredients = new ArrayList<String>();
		tempIngredients.add("egg");
		ArrayList<String> tempInstructions = new ArrayList<String>();
		tempInstructions.add("cut banana");
		Recipe temp2 = new Recipe("chicken", "", "", "asdfasdg",  tempIngredients, tempInstructions);
		testRecipe2.add(temp2);
		manager.addToList("GROCERY_LIST", "0", "", testRecipe2, testRestaurants2, "test@usc.edu");
		manager.addToList("TO_EXPLORE", "", "0", testRecipe2, testRestaurants2, "test@usc.edu");
		manager.addToList("DO_NOT_SHOW", "0", "", testRecipe2, testRestaurants2, "test@usc.edu");
		manager.addToList("FAVORITES", "0", "", testRecipe2, testRestaurants2, "test@usc.edu");
		manager.addToList("TO_EXPLORE", "0", "", testRecipe2, testRestaurants2, "test@usc.edu");
		manager.addToList("DO_NOT_SHOW", "", "0", testRecipe2, testRestaurants2, "test@usc.edu");
		manager.addToList("FAVORITES", "", "0", testRecipe2, testRestaurants2, "test@usc.edu");
		// populate database using this function
		
		LoginServlet LS = new LoginServlet();
		// logout
		when(request.getParameter("action")).thenReturn("logout");
		when(request.getSession()).thenReturn(session);    
        when(response.getWriter()).thenReturn(pw);
        when(request.getRequestDispatcher("search_page.jsp")).thenReturn(RD);
        when(request.getRequestDispatcher("login_page.html")).thenReturn(RD);
		LS.service(request,response);
		
		// log back in 
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("email")).thenReturn("test@usc.edu");
		when(request.getParameter("password")).thenReturn("test");
		when(request.getParameter("action")).thenReturn("login");
		
		when(response.getWriter()).thenReturn(pw);
        when(request.getRequestDispatcher("search_page.jsp")).thenReturn(RD);
        when(request.getRequestDispatcher("login_page.html")).thenReturn(RD);
		LS.service(request,response);
		
		
	}

}
