package edu.usc.cs.group8.ImHungry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IHManageListTest {
	@Mock
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@Mock 
	HttpSession session;
	
	@Mock 
	RequestDispatcher RD;
	
	/*
	@Mock 
	IHSearch MockSearch;*/
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testDoGet() throws Exception {
		ListManager.getInstance().reset();
		
		IHSearch search = new IHSearch();
		
		HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher RD = mock(RequestDispatcher.class);


        when(request.getParameter("action")).thenReturn("ADD");
        when(request.getParameter("list_id")).thenReturn("FAVORITES");
        when(request.getParameter("recipe_id")).thenReturn("0");
        when(request.getParameter("restaurant_id")).thenReturn("");
        
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("recipes")).thenReturn(search.doRecipeSearch("spaghetti", "5"));
        when(request.getSession().getAttribute("restaurants")).thenReturn(search.doRestaurantSearch("spaghetti", "5"));
        
        when(request.getRequestDispatcher("recipe_page.jsp?list_id=FAVORITES&item_id=0")).thenReturn(RD);
        when(request.getRequestDispatcher("restaurant_page.jsp?list_id=FAVORITES&item_id=1")).thenReturn(RD);
        when(request.getRequestDispatcher("recipe_page.jsp?list_id=TO_EXPLORE&item_id=0")).thenReturn(RD);
        when(request.getRequestDispatcher("restaurant_page.jsp?list_id=TO_EXPLORE&item_id=1")).thenReturn(RD);
        when(request.getRequestDispatcher("recipe_page.jsp?list_id=DO_NOT_SHOW&item_id=0")).thenReturn(RD);
        when(request.getRequestDispatcher("restaurant_page.jsp?list_id=DO_NOT_SHOW&item_id=1")).thenReturn(RD);
        when(request.getRequestDispatcher("recipe_page.jsp?recipe_id=0")).thenReturn(RD);
        when(request.getRequestDispatcher("restaurant_page.jsp?restaurant_id=0")).thenReturn(RD);
		when(request.getRequestDispatcher("list_management_page.jsp?list_id=FAVORITES")).thenReturn(RD);
		when(request.getRequestDispatcher("list_management_page.jsp?list_id=TO_EXPLORE")).thenReturn(RD);
		when(request.getRequestDispatcher("list_management_page.jsp?list_id=DO_NOT_SHOW")).thenReturn(RD);
        
        IHManageList manager = new IHManageList();
        manager.doGet(request, response);
        
        when(request.getParameter("action")).thenReturn("MOVE");
        when(request.getParameter("list_id")).thenReturn("FAVORITES");
        when(request.getParameter("destination_id")).thenReturn("TO_EXPLORE");
        when(request.getParameter("item_id")).thenReturn("0");
        
        manager.doGet(request, response);
        
        when(request.getParameter("action")).thenReturn("REMOVE");
        when(request.getParameter("list_id")).thenReturn("TO_EXPLORE");
        when(request.getParameter("item_id")).thenReturn("0");
        
        manager.doGet(request, response);
        
        ListManager.getInstance().reset();
        
        when(request.getParameter("action")).thenReturn("ADD");
        when(request.getParameter("list_id")).thenReturn("FAVORITES");
        when(request.getParameter("recipe_id")).thenReturn("0");
        when(request.getParameter("restaurant_id")).thenReturn("");
        
        manager.doGet(request, response);
        
        when(request.getParameter("action")).thenReturn("DISPLAY");
        when(request.getParameter("list_id")).thenReturn("FAVORITES");
        when(request.getParameter("item_id")).thenReturn("0");
        
        manager.doGet(request, response);
        
        when(request.getParameter("action")).thenReturn("ADD");
        when(request.getParameter("list_id")).thenReturn("FAVORITES");
        when(request.getParameter("recipe_id")).thenReturn("");
        when(request.getParameter("restaurant_id")).thenReturn("0");
        
        manager.doGet(request, response);
        
        when(request.getParameter("action")).thenReturn("DISPLAY");
        when(request.getParameter("list_id")).thenReturn("FAVORITES");
        when(request.getParameter("item_id")).thenReturn("1");
        
        manager.doGet(request, response);
        
        when(request.getParameter("action")).thenReturn("ADD");
        when(request.getParameter("list_id")).thenReturn("TO_EXPLORE");
        when(request.getParameter("recipe_id")).thenReturn("0");
        when(request.getParameter("restaurant_id")).thenReturn("");
        
        manager.doGet(request, response);
        
        when(request.getParameter("action")).thenReturn("DISPLAY");
        when(request.getParameter("list_id")).thenReturn("TO_EXPLORE");
        when(request.getParameter("item_id")).thenReturn("0");
        
        manager.doGet(request, response);
        
        when(request.getParameter("action")).thenReturn("ADD");
        when(request.getParameter("list_id")).thenReturn("TO_EXPLORE");
        when(request.getParameter("recipe_id")).thenReturn("");
        when(request.getParameter("restaurant_id")).thenReturn("0");
        
        manager.doGet(request, response);
        
        when(request.getParameter("action")).thenReturn("DISPLAY");
        when(request.getParameter("list_id")).thenReturn("TO_EXPLORE");
        when(request.getParameter("item_id")).thenReturn("1");
        
        manager.doGet(request, response);
        
        when(request.getParameter("action")).thenReturn("ADD");
        when(request.getParameter("list_id")).thenReturn("DO_NOT_SHOW");
        when(request.getParameter("recipe_id")).thenReturn("0");
        when(request.getParameter("restaurant_id")).thenReturn("");
        
        manager.doGet(request, response);
        
        when(request.getParameter("action")).thenReturn("DISPLAY");
        when(request.getParameter("list_id")).thenReturn("DO_NOT_SHOW");
        when(request.getParameter("item_id")).thenReturn("0");
        
        manager.doGet(request, response);
        
        when(request.getParameter("action")).thenReturn("ADD");
        when(request.getParameter("list_id")).thenReturn("DO_NOT_SHOW");
        when(request.getParameter("recipe_id")).thenReturn("");
        when(request.getParameter("restaurant_id")).thenReturn("0");
        
        manager.doGet(request, response);
        
        when(request.getParameter("action")).thenReturn("DISPLAY");
        when(request.getParameter("list_id")).thenReturn("DO_NOT_SHOW");
        when(request.getParameter("item_id")).thenReturn("1");

        manager.doGet(request, response);
        when(request.getParameter("action")).thenReturn("REMOVE");
        when(request.getParameter("list_id")).thenReturn("DO_NOT_SHOW");
        when(request.getParameter("item_id")).thenReturn("0");
        
	}

	@Test
	public void testAddToList() throws ClassNotFoundException {
		ListManager.getInstance().reset();
		
		ArrayList<Restaurant> testRestaurants2 = new ArrayList<Restaurant>();
		
		ArrayList<Recipe> testRecipe2 = new ArrayList<Recipe>();
		Restaurant temp1 = new Restaurant("mcd", "");
		testRestaurants2.add(temp1);
		ArrayList<String> tempIngredients = new ArrayList<String>();
		tempIngredients.add("syrup");
		ArrayList<String> tempInstructions = new ArrayList<String>();
		tempInstructions.add("cut banana");
		Recipe temp2 = new Recipe("waffle", "", "", "asdfasdg",  tempIngredients, tempInstructions);
		testRecipe2.add(temp2);

		
		IHManageList manager = new IHManageList();
		assertEquals(true, manager.addToList("TO_EXPLORE", "0", "", testRecipe2, testRestaurants2, "test@usc.edu"));
		
		
		assertEquals(true, manager.addToList("GROCERY_LIST", "0", "", testRecipe2, testRestaurants2, "test@usc.edu"));
		assertEquals(false, manager.addToList("GROCERY_LIST", "0", "", testRecipe2, testRestaurants2, "test@usc.edu"));
		manager.removeFromList("GROCERY_LIST", "0", "test@usc.edu", "rest");
		assertEquals(true, manager.addToList("TO_EXPLORE", "", "0", testRecipe2, testRestaurants2, "test@usc.edu"));
		assertEquals(true, manager.addToList("DO_NOT_SHOW", "0", "", testRecipe2, testRestaurants2, "test@usc.edu"));
		assertEquals(true, manager.addToList("FAVORITES", "0", "", testRecipe2, testRestaurants2, "test@usc.edu"));
	}
	
	@Test
	public void testRemoveFromList() throws ClassNotFoundException {
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

		IHManageList manager = new IHManageList();
		assertEquals(true, manager.addToList("GROCERY_LIST", "0", "", testRecipe2, testRestaurants2, "test@usc.edu"));
		assertEquals(true, manager.addToList("TO_EXPLORE", "", "0", testRecipe2, testRestaurants2, "test@usc.edu"));
		assertEquals(true, manager.addToList("DO_NOT_SHOW", "0", "", testRecipe2, testRestaurants2, "test@usc.edu"));
		assertEquals(true, manager.addToList("FAVORITES", "0", "", testRecipe2, testRestaurants2, "test@usc.edu"));
		int j = 0;
		for(int i = 0; i < ListManager.getInstance().getToExplore().size(); ++i) {
			if(ListManager.getInstance().getToExplore().get(i).getName().equals("mcd")){
				j = i;
				break;
			}
		}
		assertEquals(true, manager.removeFromList("TO_EXPLORE", Integer.toString(j), "test@usc.edu", "rest"));
		assertEquals(true, manager.removeFromList("GROCERY_LIST", "0",  "test@usc.edu", "rest"));
		for(int i = 0; i < ListManager.getInstance().getDoNotShow().size(); ++i) {
			if(ListManager.getInstance().getDoNotShow().get(i).getName().equals("chicken")){
				j = i;
				break;
			}
		}
		assertEquals(true, manager.removeFromList("DO_NOT_SHOW", Integer.toString(j), "test@usc.edu", "rec"));
		for(int i = 0; i < ListManager.getInstance().getFavorites().size(); ++i) {
			if(ListManager.getInstance().getFavorites().get(i).getName().equals("chicken")){
				j = i;
				break;
			}
		}
		assertEquals(true, manager.removeFromList("FAVORITES", Integer.toString(j), "test@usc.edu", "rec"));
	}
	
	
	@Test
	public void testMoveToList() throws ClassNotFoundException {
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

		IHManageList manager = new IHManageList();
		assertEquals(true, manager.addToList("GROCERY_LIST", "0", "", testRecipe2, testRestaurants2, "test@usc.edu"));
		assertEquals(true, manager.addToList("TO_EXPLORE", "", "0", testRecipe2, testRestaurants2, "test@usc.edu"));
		assertEquals(true, manager.addToList("DO_NOT_SHOW", "0", "", testRecipe2, testRestaurants2, "test@usc.edu"));
		assertEquals(true, manager.addToList("FAVORITES", "0", "", testRecipe2, testRestaurants2, "test@usc.edu"));
		assertEquals(true, manager.addToList("TO_EXPLORE", "0", "", testRecipe2, testRestaurants2, "test@usc.edu"));
		assertEquals(true, manager.addToList("DO_NOT_SHOW", "", "0", testRecipe2, testRestaurants2, "test@usc.edu"));
		assertEquals(true, manager.addToList("FAVORITES", "", "0", testRecipe2, testRestaurants2, "test@usc.edu"));
		
		int j = 0;
		for(int i = 0; i < ListManager.getInstance().getToExplore().size(); ++i) {
			if(ListManager.getInstance().getToExplore().get(i).getName().equals("mcd")){
				j = i;
				break;
			}
		}
		assertEquals(true, manager.moveToList("TO_EXPLORE", "FAVORITES", Integer.toString(j), "test@usc.edu"));
		//assertEquals(true, manager.removeFromList("GROCERY_LIST", "0",  "test@usc.edu", "rest"));
		for(int i = 0; i < ListManager.getInstance().getDoNotShow().size(); ++i) {
			if(ListManager.getInstance().getDoNotShow().get(i).getName().equals("chicken")){
				j = i;
				break;
			}
		}
		assertEquals(true, manager.moveToList("DO_NOT_SHOW", "To_EXPLORE", Integer.toString(j), "test@usc.edu"));

		///assertEquals(true, manager.removeFromList("DO_NOT_SHOW", Integer.toString(j), "test@usc.edu", "rec"));
		for(int i = 0; i < ListManager.getInstance().getFavorites().size(); ++i) {
			if(ListManager.getInstance().getFavorites().get(i).getName().equals("chicken")){
				j = i;
				break;
			}
		}
		assertEquals(true, manager.moveToList("FAVORITES", "DO_NOT_SHOW", Integer.toString(j), "test@usc.edu"));
		
		for(int i = 0; i < ListManager.getInstance().getToExplore().size(); ++i) {
			if(ListManager.getInstance().getToExplore().get(i).getName().equals("chicken")){
				j = i;
				break;
			}
		}
		assertEquals(true, manager.moveToList("TO_EXPLORE", "FAVORITES", Integer.toString(j), "test@usc.edu"));
		//assertEquals(true, manager.removeFromList("GROCERY_LIST", "0",  "test@usc.edu", "rest"));
		for(int i = 0; i < ListManager.getInstance().getDoNotShow().size(); ++i) {
			if(ListManager.getInstance().getDoNotShow().get(i).getName().equals("mcd")){
				j = i;
				break;
			}
		}
		assertEquals(true, manager.moveToList("DO_NOT_SHOW", "To_EXPLORE", Integer.toString(j), "test@usc.edu"));

		///assertEquals(true, manager.removeFromList("DO_NOT_SHOW", Integer.toString(j), "test@usc.edu", "rec"));
		for(int i = 0; i < ListManager.getInstance().getFavorites().size(); ++i) {
			if(ListManager.getInstance().getFavorites().get(i).getName().equals("mcd")){
				j = i;
				break;
			}
		}
		assertEquals(true, manager.moveToList("FAVORITES", "DO_NOT_SHOW", Integer.toString(j), "test@usc.edu"));
		//assertEquals(true, manager.removeFromList("FAVORITES", Integer.toString(j), "test@usc.edu", "rec"));
	}

}
