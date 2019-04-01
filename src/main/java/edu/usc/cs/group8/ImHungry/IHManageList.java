package edu.usc.cs.group8.ImHungry;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



/*
 * Servlet: IHManageList
 * This servlet takes care of any of the list management processes the front end needs to take care of.
 * Author: Kevin Calaway
 * USC ID: 9724507315
 * Email: calaway@usc.edu
 */
@WebServlet("/IHManageList")
public class IHManageList extends HttpServlet {


	/**
	 * search_query for text
	 * num_results for number of results
	 */
	private static final long serialVersionUID = 1L;
	
	// get database name later
	/*private static final String DATABASE_CONNECTION_URL = "jdbc:mysql://localhost:3306/DATABASENAME?user=root&password=&useSSL=false";
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement ps = null;*/
	
	
	// favorites
	// toExplore
	// doNotShow
	public IHManageList() throws ClassNotFoundException {
        super();
        // TODO Auto-generated constructor stub
        
        /*
    	try {
    		
    		Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			ps = conn.prepareStatement(
					"INSERT INTO List ("
					+ "listName"
					+ ") VALUES ('" 
					+ "favorites"
					+ "');");
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			ps = conn.prepareStatement(
					"INSERT INTO List ("
					+ "listName"
					+ ") VALUES ('" 
					
					+ "toExplore"
					+ "');");
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			ps = conn.prepareStatement(
					"INSERT INTO List ("
				
					+ "listName"
					+ ") VALUES ('" 
				
					+ "doNotShow"
					+ "');");
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    }
	
	/*
	 * This function determines what function is being called, gathers the necessary parameters, and passes them along.
	 * The display functionality determines what object is being presented and therefore needs access to the response
	 * object.
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		ArrayList<Recipe> recipes;
		ArrayList<Restaurant> restaurants;
		try{
			recipes = (ArrayList<Recipe>)(request.getSession().getAttribute("recipes"));
			restaurants = (ArrayList<Restaurant>)(request.getSession().getAttribute("restaurants"));
		} catch (Exception e){
			response.setStatus(response.SC_BAD_GATEWAY);
			response.getWriter().println("Unknown error occurred.");
			response.getWriter().flush();
			return;
		}
		/*
		 * restaurantID		INT(11)			PRIMARY KEY,
	restaurantName		VARCHAR(100)	NOT NULL,
	restaurantAddress	VARCHAR(100)	NOT NULL,
	restaurantPhone		VARCHAR(100)	NOT NULL,
	restaurantURL		VARCHAR(100)	NOT NULL
		 */
		
		/*
		 * recipeID			INT(11)			PRIMARY KEY,
	recipeName			VARCHAR(100)	NOT NULL,
	recipeImageURL		VARCHAR(100) 	NOT NULL,
	recipeCookTime		VARCHAR(50) 	NOT NULL,
	recipePrepTime		VARCHAR(50)		NOT NULL,
	recipeInstructions	VARCHAR(1000)	NOT NULL
		 */
		/*
		for(int i = 0; i < recipes.size(); ++i) {
			try {
	    		Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
				ps = conn.prepareStatement(
						"INSERT INTO Recipe ("
						+ "recipeID"
						+ "recipeName"
						+ "recipeImageURL"
						+ "recipeCookTime"
						+ "recipePrepTime"
						+ "recipePrepTime"
						+ ") VALUES ('" 
					
						+ "doNotShow"
						+ "');");
				ps.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		String listID = request.getParameter("list_id");
		String action = request.getParameter("action");
		
		//based on request parameter, will add the recipe or restaurant to the list
		if (action.equals("ADD")) {
			String recipeID = request.getParameter("recipe_id");
			String restaurantID = request.getParameter("restaurant_id");
			addToList(listID,recipeID,restaurantID,recipes,restaurants);
			if (recipeID != "") {
				request.getRequestDispatcher("recipe_page.jsp?recipe_id=" + recipeID).forward(request, response);
			} else {
				request.getRequestDispatcher("restaurant_page.jsp?restaurant_id=" + restaurantID).forward(request, response);
			}
		}
		
		if (action.equals("REMOVE")) {
			String itemID = request.getParameter("item_id");
			//String indicator = request.getParameter(")
			removeFromList(listID,itemID);
			request.getRequestDispatcher("list_management_page.jsp?list_id=" + listID).forward(request, response);
		}
		
		else if (action.equals("MOVE")) {
			String itemID = request.getParameter("item_id");
			String destinationID = request.getParameter("destination_id");
			moveToList(listID,destinationID,itemID);
			request.getRequestDispatcher("list_management_page.jsp?list_id=" + listID).forward(request, response);
		}
		
		/*
		 * This chunk of code cannot call out because it relies on the request object;
		 * However, all it does is display an item when clicked on a list.
		 */
		else if (action.equals("DISPLAY")) {
			String itemID = request.getParameter("item_id");
			if (itemID != null && !itemID.equals("")) {
				int index = Integer.parseInt(itemID);
				if (listID.equals("FAVORITES")) {
					if (ListManager.getInstance().getFavorites().get(index) instanceof Recipe) {
						request.getRequestDispatcher("recipe_page.jsp?list_id=FAVORITES&item_id=" + index).forward(request, response);
					}
					else if (ListManager.getInstance().getFavorites().get(index) instanceof Restaurant) {
						request.getRequestDispatcher("restaurant_page.jsp?list_id=FAVORITES&item_id=" + index).forward(request, response);
					}
				}
				if (listID.equals("TO_EXPLORE")) {
					if (ListManager.getInstance().getToExplore().get(index) instanceof Recipe) {
						request.getRequestDispatcher("recipe_page.jsp?list_id=TO_EXPLORE&item_id=" + index).forward(request, response);
					}
					else if (ListManager.getInstance().getToExplore().get(index) instanceof Restaurant) {
						request.getRequestDispatcher("restaurant_page.jsp?list_id=TO_EXPLORE&item_id=" + index).forward(request, response);
					}
				}
				if (listID.equals("DO_NOT_SHOW")) {
					if (ListManager.getInstance().getDoNotShow().get(index) instanceof Recipe) {
						request.getRequestDispatcher("recipe_page.jsp?list_id=DO_NOT_SHOW&item_id=" + index).forward(request, response);
					}
					else if (ListManager.getInstance().getDoNotShow().get(index) instanceof Restaurant) {
						request.getRequestDispatcher("restaurant_page.jsp?list_id=DO_NOT_SHOW&item_id=" + index).forward(request, response);
					}
				}
			}
		}
	}
	
	/*
	 * Based on the provided source and destination lists, this function moves the specified item.
	 */
	public void moveToList(String listID, String destinationID, String itemID) {
		if (itemID != null && !itemID.equals("")) {
			int index = Integer.parseInt(itemID);
			if (listID.equals("FAVORITES") && !destinationID.equals("FAVORITES")) {
				Result r = ListManager.getInstance().getFavorites().get(index);
				ListManager.getInstance().removeFromFavorites(r);
				if (destinationID.equals("TO_EXPLORE")) {
					ListManager.getInstance().addToToExplore(r);
				}
				if (destinationID.equals("DO_NOT_SHOW")) {
					ListManager.getInstance().addToDoNotShow(r);
				}
			}
			if (listID.equals("TO_EXPLORE") && !destinationID.equals("TO_EXPLORE")) {
				Result r = ListManager.getInstance().getToExplore().get(index);
				ListManager.getInstance().removeFromToExplore(r);
				if (destinationID.equals("FAVORITES")) {
					ListManager.getInstance().addToFavorites(r);
				}
				if (destinationID.equals("DO_NOT_SHOW")) {
					ListManager.getInstance().addToDoNotShow(r);
				}
			}
			if (listID.equals("DO_NOT_SHOW") && !destinationID.equals("DO_NOT_SHOW")) {
				Result r = ListManager.getInstance().getDoNotShow().get(index);
				ListManager.getInstance().removeFromDoNotShow(r);
				if (destinationID.equals("FAVORITES")) {
					ListManager.getInstance().addToFavorites(r);
				}
				if (destinationID.equals("TO_EXPLORE")) {
					ListManager.getInstance().addToToExplore(r);
				}
			}
		}
		
	}

	/*
	 * Based on the specified list and item number, it removes it from the list.
	 */ 
	public void removeFromList(String listID, String itemID) {
		if (itemID != null && !itemID.equals("")) {
			// how to identify if from listRestaurants or ListRecipes
			int index = Integer.parseInt(itemID);
			/*
			ps = conn.prepareStatement(
					"DELETE FROM Follow WHERE user_id=" + user_id + " AND " + "follower_id=" + follower_id);
			ps.execute();*/
			if (listID.equals("FAVORITES")) {
				ListManager.getInstance().removeFromFavorites(index);
			}
			if (listID.equals("TO_EXPLORE")) {
				ListManager.getInstance().removeFromToExplore(index);
			}
			if (listID.equals("DO_NOT_SHOW")) {
				ListManager.getInstance().removeFromDoNotShow(index);
			}
			if (listID.equals("GROCERY_LIST")) {
				ListManager.getInstance().removeFromGroceryList(index);
			}
		}
		
	}

	/*
	 * Based on the specified list and item number (gathered from the "recipeID" or "restaurantID" parameters)
	 * it adds an item to a list.
	 */
	public void addToList(String listID, String recipeID, String restaurantID, ArrayList<Recipe> recipes, ArrayList<Restaurant> restaurants) {
		if (recipeID != null && !recipeID.equals("")) {

			int index = Integer.parseInt(recipeID);
			/*
			try {
	    		Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
				ps = conn.prepareStatement(
						"INSERT INTO ListRecipes ("
						+ "listID, "
						+ "recipeID"
						+ ") VALUES ('" 
						+ Integer.parseInt(listID) + "', '"
						+ index + "', '"
						+ "');");
				ps.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			if (listID.equals("FAVORITES")) {
				ListManager.getInstance().addToFavorites(recipes.get(index));
			}
			if (listID.equals("TO_EXPLORE")) {
				ListManager.getInstance().addToToExplore(recipes.get(index));
			}
			if (listID.equals("DO_NOT_SHOW")) {
				ListManager.getInstance().addToDoNotShow(recipes.get(index));
			}
			if (listID.equals("GROCERY_LIST")) {
				for(int i = 0; i < recipes.get(index).getIngredients().size(); ++i) {
					ListManager.getInstance().addToGroceryList(recipes.get(index).getIngredients().get(i));
				}
			}
		} if (restaurantID != null && !restaurantID.equals("")) {
			int index = Integer.parseInt(restaurantID);
			/*
			try {
	    		Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
				ps = conn.prepareStatement(
						"INSERT INTO ListRestaurants ("
						+ "listID, "
						+ "restaurantID"
						+ ") VALUES ('" 
						+ Integer.parseInt(listID) + "', '"
						+ index + "', '"
						+ "');");
				ps.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			if (listID.equals("FAVORITES")) {
				ListManager.getInstance().addToFavorites(restaurants.get(index));
			}
			if (listID.equals("TO_EXPLORE")) {
				ListManager.getInstance().addToToExplore(restaurants.get(index));
			}
			if (listID.equals("DO_NOT_SHOW")) {
				ListManager.getInstance().addToDoNotShow(restaurants.get(index));
			}
		}
		return;
	}
}	