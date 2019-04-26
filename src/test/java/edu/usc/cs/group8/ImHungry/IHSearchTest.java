package edu.usc.cs.group8.ImHungry;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        
        ArrayList<Restaurant> testRestaurant1;
        
        IHSearch IHS = spy(new IHSearch());
        testRestaurant1 = IHS.doRestaurantSearch("pizza","3", "3");
        testRestaurant1.sort(new RestaurantComparator());
        Mockito.when(IHS.doRestaurantSearch("pizza","3", "3")).thenReturn(new ArrayList<Restaurant>());
        Mockito.when(IHS.doImageSearch("pizza")).thenReturn(new ArrayList<String>());
        Mockito.when(IHS.doRestaurantSearch("pizza","3")).thenReturn(new ArrayList<Restaurant>());
        Mockito.when(IHS.doRecipeSearch("pizza","3")).thenReturn(new ArrayList<Recipe>());
        
        for (int i = 0; i < testRestaurant1.size(); i++ ) {
        	System.out.println(testRestaurant1.get(i).getName());
        }
         
        when(response.getWriter()).thenReturn(pw);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("results_page.jsp")).thenReturn(RD);

        IHS.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        System.out.println("result: " + result);
        assertTrue(testRestaurant1.get(0).getName().equals("California Pizza Kitchen"));
        assertTrue(testRestaurant1.get(1).getName().equals("Rance's Chicago Pizza"));
    }

	
	@Test
    public void testDoGetRestaurantSearch() throws Exception {
		when(request.getParameter("search_query")).thenReturn("spaghetti");
		when(request.getParameter("num_results")).thenReturn("10");
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        ArrayList<Restaurant> testRestaurant1;
        
        IHSearch IHS = spy(new IHSearch());
        testRestaurant1 = IHS.doRestaurantSearch("pizza","3");
        testRestaurant1.sort(new RestaurantComparator());
        
        for (int i = 0; i < testRestaurant1.size(); i++) {
        	System.out.println("restaurant: " + testRestaurant1.get(i).getName());
        }
        
        Mockito.when(IHS.doImageSearch("pizza")).thenReturn(new ArrayList<String>());
        when(MockSearch.doRestaurantSearch("pizza","3")).thenReturn(null);
        when(MockSearch.doRestaurantSearch("pizza","3", "3")).thenReturn(null);
        when(MockSearch.doRecipeSearch("pizza","3")).thenReturn(new ArrayList<Recipe>());
         
        when(response.getWriter()).thenReturn(pw);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("results_page.jsp")).thenReturn(RD);
 
        IHS.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertTrue(testRestaurant1.get(0).getName().equals("California Pizza Kitchen"));
        assertTrue(testRestaurant1.get(1).getName().equals("Rance's Chicago Pizza"));
    }

	
	@Test
    public void testDoGetRecipeSearch() throws Exception {
		when(request.getParameter("search_query")).thenReturn("spaghetti");
		when(request.getParameter("num_results")).thenReturn("10");
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        IHSearch IHS = spy(new IHSearch());
        ArrayList<Recipe> testRecipe = IHS.doRecipeSearch("pizza", "3");
        testRecipe.sort(new RecipeComparator());
        for (int i = 0; i < testRecipe.size(); i++) {
        	System.out.println("recipe: " + testRecipe.get(i).getName());
        }
        Mockito.when(IHS.doImageSearch("pizza")).thenReturn(new ArrayList<String>());
        when(MockSearch.doRestaurantSearch("pizza","3")).thenReturn(new ArrayList<Restaurant>());
        when(MockSearch.doRecipeSearch("pizza","3")).thenReturn(null);
         
        when(response.getWriter()).thenReturn(pw);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("results_page.jsp")).thenReturn(RD);
 
        IHS.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertTrue(testRecipe.get(0).getName().equals("Homemade Pizza"));
        assertTrue(testRecipe.get(1).getName().equals("Homemade Pizza"));
   
    }

	
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
	
	
	@Test
    public void testRecipeSearchSorted() throws Exception {
       IHSearch search = new IHSearch();
       ArrayList<Recipe> recipes = search.doRecipeSearch("pizza", "2");
       search.sortRecipes(recipes);
       assertEquals(recipes.size(),2);
       int i = 0;
       while (true) {
    	   assertTrue(recipes.get(i).getPrepTime() <= recipes.get(i+1).getPrepTime());
    	   break;
       }
    }
	
	@Test
	public void getRestaurantReturnsCorrectRestaurantsWithRadius() {
		IHSearch tester = new IHSearch();
		ArrayList<Restaurant> testRestaurants = new ArrayList<Restaurant>();
		
		testRestaurants = tester.doRestaurantSearch("Chicken", "5", ".75");
		testRestaurants.sort(new RestaurantComparator());
		System.out.println("Restaurant size: " + testRestaurants.size());
		for(int i = 0; i < testRestaurants.size(); i++) {
			System.out.println(testRestaurants.get(i).getAddress());
		}
		int x = 4;
		boolean addressMatch = testRestaurants.get(0).getAddress().contains("S Figueroa St, Los Angeles, CA 90007, USA");
		assertTrue(addressMatch);
		addressMatch = (testRestaurants.get(1).getAddress().contains("S Figueroa St, Los Angeles, CA 90007, USA"));
		assertTrue(addressMatch);
		addressMatch = testRestaurants.get(2).getAddress().contains("S Figueroa St, Los Angeles, CA 90007, USA");
		assertTrue(addressMatch);
		assertTrue(testRestaurants.size() <= x);
		
		testRestaurants = tester.doRestaurantSearch("Taco", "3", "100");
		testRestaurants.sort(new RestaurantComparator());
		System.out.println("Restaurant size: " + testRestaurants.size());
		for(int i = 0; i < testRestaurants.size(); i++) {
			System.out.println(testRestaurants.get(i).getAddress());
		}
		
		assertTrue(testRestaurants.get(0).getAddress().contains("Los Angeles, CA"));
		assertTrue(testRestaurants.get(1).getAddress().contains("Los Angeles, CA"));
		assertEquals(3, testRestaurants.size());		
	}
	
	@Test
	public void testHaversineFunction() {
		double HaversineTest = IHSearch.haversine(1000, 2000, 3000, 4000);
		assertEquals(15619, HaversineTest, 100, "Checking Haversine works with all positive latitude and longitude");
		
		HaversineTest = IHSearch.haversine(-100, -200, -300, -400);
		assertEquals(15619, HaversineTest, 100, "Checking Haversine works with all negative latitude and longitude");
	}
}
