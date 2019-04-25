package edu.usc.cs.group8.ImHungry;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class IHSearchTest {
	
	@Mock
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@Mock 
	HttpSession session;
	
	@Mock 
	RequestDispatcher RD;
	
	@Mock 
	IHSearch MockSearch;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
    public void testDoGet() throws Exception {
		when(request.getParameter("search_query")).thenReturn("spaghetti");
		when(request.getParameter("num_results")).thenReturn("10");
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        IHSearch IHS = spy(new IHSearch());
        Mockito.when(IHS.doRestaurantSearch("spaghetti","10", "10")).thenReturn(new ArrayList<Restaurant>());
        Mockito.when(IHS.doImageSearch("spaghetti")).thenReturn(new ArrayList<String>());
        Mockito.when(IHS.doRestaurantSearch("spaghetti","10")).thenReturn(new ArrayList<Restaurant>());
        Mockito.when(IHS.doRecipeSearch("spaghetti","10")).thenReturn(new ArrayList<Recipe>());
         
        when(response.getWriter()).thenReturn(pw);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("results_page.jsp")).thenReturn(RD);

        IHS.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertTrue(result.equals(new String("Full Name: Vinod Kashyap")));
    }
	
	@Test
    public void testDoGetSearchesReturnNull() throws Exception {
		when(request.getParameter("search_query")).thenReturn("spaghetti");
		when(request.getParameter("num_results")).thenReturn("10");
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        IHSearch IHS = spy(new IHSearch());
        Mockito.when(IHS.doImageSearch("spaghetti")).thenReturn(null);
        Mockito.when(IHS.doRestaurantSearch("spaghetti","10")).thenReturn(null);
        Mockito.when(IHS.doRecipeSearch("spaghetti","10")).thenReturn(null);
         
        when(response.getWriter()).thenReturn(pw);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("results_page.jsp")).thenReturn(RD);
 
        IHS.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        System.out.println("Hiro: the result is: " + result);
        //assertEquals(result, new String("Full Name: Vinod Kashyap"));
    }
	
	/*
	@Test
    public void testDoGetImageSearchReturnsNull() throws Exception {
		when(request.getParameter("search_query")).thenReturn("spaghetti");
		when(request.getParameter("num_results")).thenReturn("10");
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        IHSearch IHS = spy(new IHSearch());
        Mockito.when(IHS.doImageSearch("spaghetti")).thenReturn(null);
        when(MockSearch.doRestaurantSearch("spaghetti","10")).thenReturn(new ArrayList<Restaurant>());
        when(MockSearch.doRecipeSearch("spaghetti","10")).thenReturn(new ArrayList<Recipe>());
         
        when(response.getWriter()).thenReturn(pw);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("results_page.jsp")).thenReturn(RD);
 
        IHS.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        //assertEquals(result, new String("Full Name: Vinod Kashyap"));
    }*/
	
	@Test
    public void testDoGetRestaurantSearchReturnsNull() throws Exception {
		when(request.getParameter("search_query")).thenReturn("spaghetti");
		when(request.getParameter("num_results")).thenReturn("10");
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        IHSearch IHS = spy(new IHSearch());
        Mockito.when(IHS.doImageSearch("spaghetti")).thenReturn(new ArrayList<String>());
        when(MockSearch.doRestaurantSearch("spaghetti","10")).thenReturn(null);
        when(MockSearch.doRestaurantSearch("spaghetti","10", "10")).thenReturn(null);
        when(MockSearch.doRecipeSearch("spaghetti","10")).thenReturn(new ArrayList<Recipe>());
         
        when(response.getWriter()).thenReturn(pw);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("results_page.jsp")).thenReturn(RD);
 
        IHS.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        //assertEquals(result, new String("Full Name: Vinod Kashyap"));
    }

	/*
	@Test
    public void testDoGetRecipeSearchReturnsNull() throws Exception {
		when(request.getParameter("search_query")).thenReturn("spaghetti");
		when(request.getParameter("num_results")).thenReturn("10");
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        IHSearch IHS = spy(new IHSearch());
        Mockito.when(IHS.doImageSearch("spaghetti")).thenReturn(new ArrayList<String>());
        when(MockSearch.doRestaurantSearch("spaghetti","10")).thenReturn(new ArrayList<Restaurant>());
        when(MockSearch.doRecipeSearch("spaghetti","10")).thenReturn(null);
         
        when(response.getWriter()).thenReturn(pw);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("results_page.jsp")).thenReturn(RD);
 
        IHS.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        //assertEquals(result, new String("Full Name: Vinod Kashyap"));
    }

	*/
	@Test
    public void testRestaurantSearchSorted() throws Exception {
       IHSearch IHS = new IHSearch();
       ArrayList<Restaurant> restaurants = IHS.doRestaurantSearch("falafel", "10", "10");
       System.out.println(Integer.toString(restaurants.size()));
       for(int i = 0; i < restaurants.size(); ++i) {
    	   System.out.println("h: " + restaurants.get(i).getName());
       }
       IHS.sortRestaurants(restaurants);
       assertEquals(10,restaurants.size());
       for(int i =0;i<restaurants.size()-1; i++ ) {
    	   assertTrue(restaurants.get(i).getDriveTime()<=restaurants.get(i+1).getDriveTime());
       }

    }
	
	/*
	@Test
    public void testRecipeSearchSorted() throws Exception {
       IHSearch search = new IHSearch();
       ArrayList<Recipe> recipes = search.doRecipeSearch("spaghetti", "10");
       search.sortRecipes(recipes);
       assertEquals(recipes.size(),10);
       for(int i =0;i<recipes.size()-1; i++ ) {
    	   assertTrue(recipes.get(i).getPrepTime() <= recipes.get(i+1).getPrepTime() || recipes.get(i+1).getPrepTime() == 0);
       }
    }*/
	
	@Test
	public void getRestaurantReturnsCorrectRestaurantsWithRadius() {
		IHSearch tester = new IHSearch();
		ArrayList<Restaurant> testRestaurants = new ArrayList<Restaurant>();
		
		testRestaurants = tester.doRestaurantSearch("Chicken", "5", ".75");
		
		System.out.println("Restaurant size: " + testRestaurants.size());
		for(int i = 0; i < testRestaurants.size(); i++) {
			System.out.println(testRestaurants.get(i).getAddress());
		}	
		int x = 4;
		boolean addressMatch = "3758 S Figueroa St, Los Angeles, CA 90007, USA".equals(testRestaurants.get(0).getAddress());
		assertTrue(addressMatch);
		addressMatch = "2809 S Figueroa St, Los Angeles, CA 90007, USA".equals(testRestaurants.get(1).getAddress())
					|| "2809 S Figueroa St, Los Angeles, CA 90007, USA".equals(testRestaurants.get(2).getAddress());
		assertTrue(addressMatch);
		addressMatch = "2828 S Figueroa St, Los Angeles, CA 90007, USA".equals(testRestaurants.get(2).getAddress())
					|| "2828 S Figueroa St, Los Angeles, CA 90007, USA".equals(testRestaurants.get(3).getAddress());
		assertTrue(addressMatch);
		assertTrue(testRestaurants.size() <= x);
		
		testRestaurants = tester.doRestaurantSearch("Taco", "3", "100");
		
		System.out.println("Restaurant size: " + testRestaurants.size());
		for(int i = 0; i < testRestaurants.size(); i++) {
			System.out.println(testRestaurants.get(i).getAddress());
		}
		
		assertTrue("835 W Jefferson Blvd #1735, Los Angeles, CA 90089, USA".equals(testRestaurants.get(0).getAddress()));
		assertTrue("3748 S Figueroa St, Los Angeles, CA 90007, USA".equals(testRestaurants.get(1).getAddress()));
		assertEquals(3, testRestaurants.size(), "None of the restaurants are limited by radius");		
	}
	
	@Test
	public void testHaversineFunction() {
		double HaversineTest = IHSearch.haversine(1000, 2000, 3000, 4000);
		assertEquals(15619, HaversineTest, 100, "Checking Haversine works with all positive latitude and longitude");
		
		HaversineTest = IHSearch.haversine(-100, -200, -300, -400);
		assertEquals(15619, HaversineTest, 100, "Checking Haversine works with all negative latitude and longitude");
	}
}
