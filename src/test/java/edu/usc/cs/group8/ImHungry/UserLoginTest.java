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
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void passwordIncorrect(String username, String wrongPassword)
	{
		
	}
	
	@Test
	public void passwordCorrect(String username, String rightPassword)
	{
		
	}
	
	@Test
	public void createNewAccount(String username, String password)
	{
		
	}

}
