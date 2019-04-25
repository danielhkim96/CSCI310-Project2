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
	
	//PUT YOUR OWN MYSQL DATABASE USERNAME AND PASSWORD HERE
	static String DB_USERNAME = "root";
	static String DB_PASSWORD = "root";
	
	// get database name later
	private static final String DATABASE_CONNECTION_URL = "jdbc:mysql://localhost:3306/project2?user=" + DB_USERNAME + "&password=" + DB_PASSWORD + "&userSSL=false&serverTimezone=UTC";
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	
	
	// favorites
	// toExplore
	// doNotShow
	public IHManageList() throws ClassNotFoundException {
        super();
        // TODO Auto-generated constructor stub
		
        
       
    }
	
	/*
	 * This function determines what function is being called, gathers the necessary parameters, and passes them along.
	 * The display functionality determines what object is being presented and therefore needs access to the response
	 * object.
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("in doget");
		ArrayList<Recipe> recipes;
		ArrayList<Restaurant> restaurants;
		String userName;
		try{
			recipes = (ArrayList<Recipe>)(request.getSession().getAttribute("recipes"));
			restaurants = (ArrayList<Restaurant>)(request.getSession().getAttribute("restaurants"));
			userName = (String)(request.getSession().getAttribute("username"));
		} catch (Exception e){
			response.setStatus(response.SC_BAD_GATEWAY);
			response.getWriter().println("Unknown error occurred.");
			response.getWriter().flush();
			return;
		}
		
		
		String action = request.getParameter("action");
		String listID = request.getParameter("list_id");
		
		//based on request parameter, will add the recipe or restaurant to the list
		if (action.equals("ADD")) {
			String recipeID = request.getParameter("recipe_id");
			String restaurantID = request.getParameter("restaurant_id");
			addToList(listID, recipeID,restaurantID,recipes,restaurants, userName);
			if (recipeID != "") {
				request.getRequestDispatcher("recipe_page.jsp?recipe_id=" + recipeID).forward(request, response);
			} else {
				request.getRequestDispatcher("restaurant_page.jsp?restaurant_id=" + restaurantID).forward(request, response);
			}
		}
		
		if (action.equals("REMOVE")) {

			
			String flag = "";
			String itemIndex = request.getParameter("item_id");
			int currIndex = Integer.parseInt(itemIndex);
			Result r = null;
			if(listID.equals("FAVORITES")) {
				r = ListManager.getInstance().getFavorites().get(currIndex);
			}
			else if(listID.equals("TO_EXPLORE")) {
				r = ListManager.getInstance().getToExplore().get(currIndex);
			}
			else if(listID.equals("DO_NOT_SHOW")) {
				r = ListManager.getInstance().getDoNotShow().get(currIndex);
			}
			
			if(r instanceof Restaurant) {
				flag = "rest";
			}
			else {
				flag = "rec";
			}
			
			//System.out.println(recipeID);
			//String indicator = request.getParameter(")
			
			removeFromList(listID, itemIndex, userName, flag);
			request.getRequestDispatcher("list_management_page.jsp?list_id=" + listID).forward(request, response);
		}
		
		else if (action.equals("MOVE")) {
			//userName = (String)(request.getSession().getAttribute("username"));//repeating from doGet()
			
			String itemIndex = request.getParameter("item_id");
			String destinationID = request.getParameter("destination_id");
			moveToList(listID,destinationID,itemIndex, userName);
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
	public boolean moveToList(String listID, String destinationID, String listIndex, String userName) {
		boolean ret = false;
		if (listIndex != null && !listIndex.equals("")) {
		
			int index = Integer.parseInt(listIndex);
			Result r = null;
			Recipe tempRecipe = null;
			Restaurant tempRestaurant = null;
			int currIndex = 0;

			if(listID.equals("FAVORITES")) {
				r = ListManager.getInstance().getFavorites().get(index);
				if(r instanceof Recipe) {
					tempRecipe = (Recipe)ListManager.getInstance().getFavorites().get(index);
				}
				else {
					 tempRestaurant = (Restaurant)ListManager.getInstance().getFavorites().get(index);
				}
				ListManager.getInstance().removeFromFavorites(r);
				
			}
			else if(listID.equals("TO_EXPLORE")) {
				r = ListManager.getInstance().getToExplore().get(index);
				if(r instanceof Recipe) {
					tempRecipe = (Recipe)ListManager.getInstance().getToExplore().get(index);
				}
				else {
					 tempRestaurant = (Restaurant)ListManager.getInstance().getToExplore().get(index);
				}
				ListManager.getInstance().removeFromToExplore(r);
			}
			else if(listID.equals("DO_NOT_SHOW")) {
				r = ListManager.getInstance().getDoNotShow().get(index);
				if(r instanceof Recipe) {
					tempRecipe = (Recipe)ListManager.getInstance().getDoNotShow().get(index);
				}
				else {
					 tempRestaurant = (Restaurant)ListManager.getInstance().getDoNotShow().get(index);
				}
				
				ListManager.getInstance().removeFromDoNotShow(r);
			}
			
			if(destinationID.equals("FAVORITES")) {
				currIndex  = ListManager.getInstance().getFavorites().size();
			}
			else if(destinationID.equals("TO_EXPLORE")) {
				currIndex  = ListManager.getInstance().getToExplore().size();
			}
			else if(destinationID.equals("DO_NOT_SHOW")){
				currIndex  = ListManager.getInstance().getDoNotShow().size();
			}
			 
			if(r instanceof Recipe) {
				try {
					ps = conn.prepareStatement(
							"DELETE FROM ListRecipes WHERE username = '" + userName + "' AND " + "listIndex = '" + listIndex
							+ "' AND " + "listName = '" + listID + "';");
					ret = ps.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// delimit instructions and ingredients by |
				String instructions = "";
				for(int i = 0; i < tempRecipe.getInstructions().size(); ++i) {
					instructions += tempRecipe.getInstructions().get(i);
					if(i != tempRecipe.getInstructions().size()-1) {
						instructions += "|";
					}
				}
				
				String ingredients = "";
				for(int i = 0; i < tempRecipe.getIngredients().size(); ++i) {
					ingredients += tempRecipe.getIngredients().get(i);
					if(i != tempRecipe.getIngredients().size()-1) {
						ingredients += "|";
					}
				}
				try {
		    		Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
					ps = conn.prepareStatement(
							"INSERT INTO ListRecipes ("
							+ "listName, "
							+ "username, "
							+ "recipeName,"
							+ "listIndex, "
							+ "recipeImageURL, "
							+ "recipeCookTime, "
							+ "recipePrepTime, "
							+ "recipeInstructions, "
							+ "recipeIngredients"
							+ ") VALUES ('" 
							+ destinationID + "', '"
							+ userName + "', '"
							+ tempRecipe.getName() + "', '"
							+ Integer.toString(currIndex) + "', '"
							+ tempRecipe.getImgURL() + "', '"
							+ tempRecipe.getCookTime() + "', '"
							+ tempRecipe.getPrepTime() + "', '"
							+ instructions + "', '"
							+ ingredients
							+ "');");
					ret = ps.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					ps = conn.prepareStatement(
							"DELETE FROM ListRestaurants WHERE username = '" + userName + "' AND " +  "listIndex = '" + listIndex
							+ "' AND " + "listName = '" + listID + "';");
					ret = ps.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
		    		Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
					ps = conn.prepareStatement(
							"INSERT INTO ListRestaurants ("
							+ "listName, "
							+ "username, "
							+ "listIndex, "
							+ "restaurantName, "
							+ "restaurantAddress, "
							+ "restaurantPhone, "
							+ "restaurantURL, "
							+ "restaurantRating, "
							+ "restaurantDriveTime"
							+ ") VALUES ('" 
							+ destinationID + "', '"
							+ userName + "', '"
							+ Integer.toString(currIndex) + "', '"
							+ tempRestaurant.getName() + "', '"
							+ tempRestaurant.getAddress() + "', '"
							+ tempRestaurant.getPhoneNum() + "', '"
							+ tempRestaurant.getWebsiteURL() + "', '"
							+ Double.toString(tempRestaurant.getRating()) + "', '"
							+ Integer.toString(tempRestaurant.getDriveTime())
							+ "');");
					ret = ps.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (destinationID.equals("FAVORITES")) {
				ListManager.getInstance().addToFavorites(r);
			}
			if (destinationID.equals("TO_EXPLORE")) {
				ListManager.getInstance().addToToExplore(r);
			}
			if (destinationID.equals("DO_NOT_SHOW")) {
				ListManager.getInstance().addToDoNotShow(r);
			}
			
			
		}
		return ret;
	}
	
	/*
	 * Based on the specified list and item number, it removes it from the list.
	 */ 
	public boolean removeFromList(String listID, String listIndex, String userName, String flag) {
		boolean ret = false;
		int index = 0;
		if(listID.equals("GROCERY_LIST")) {
			index = Integer.parseInt(listIndex);
			try {
				ps = conn.prepareStatement(
						"DELETE FROM Grocery WHERE username = '" + userName + "' AND " + "listIndex = '" + listIndex
						 + "';");
				ret = ps.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ListManager.getInstance().removeFromGroceryList(index);
		}
		else if (listIndex != null && !listIndex.equals("")) {
			
			index = Integer.parseInt(listIndex);
			// how to identify if from listRestaurants or ListRecipes
			if (flag.equals("rec")){
				try {
					System.out.println("listIndex: " + listIndex);
					System.out.println("listName: " + listID);
					System.out.println(userName);
					ps = conn.prepareStatement(
							"DELETE FROM ListRecipes WHERE username = '" + userName + "' AND " + "listIndex = '" + listIndex
							+ "' AND " + "listName = '" + listID + "';");
					ret = ps.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else if (flag.equals("rest"))  {
				try {
					ps = conn.prepareStatement(
							"DELETE FROM ListRestaurants WHERE username = '" + userName + "' AND " +  "listIndex = '" + listIndex
							+ "' AND " + "listName = '" + listID + "';");
					ret = ps.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("after sql delete");
		}
		System.out.println(ret);
		//if(ret) {
			
			if (listID.equals("FAVORITES")) {
				System.out.print(index);
				ListManager.getInstance().removeFromFavorites(index);
			}
			if (listID.equals("TO_EXPLORE")) {
				ListManager.getInstance().removeFromToExplore(index);
			}
			if (listID.equals("DO_NOT_SHOW")) {
				ListManager.getInstance().removeFromDoNotShow(index);
			}
			// change this later

		//}
		return ret;
	}

	/*
	 * Based on the specified list and item number (gathered from the "recipeID" or "restaurantID" parameters)
	 * it adds an item to a list.
	 */
	public boolean addToList(String listID, String recipeID, String restaurantID, ArrayList<Recipe> recipes, ArrayList<Restaurant> restaurants, String userName) {
		boolean ret = false;
		int currListIndex = 0;
		if (listID.equals("FAVORITES")) {
			currListIndex = ListManager.getInstance().getFavorites().size();
		}
		if (listID.equals("TO_EXPLORE")) {
			currListIndex = ListManager.getInstance().getToExplore().size();
		}
		if (listID.equals("DO_NOT_SHOW")) {
			currListIndex = ListManager.getInstance().getDoNotShow().size();
		}
		// add grocery implementation later
		if(listID.equals("GROCERY_LIST")) {
			
			/*
			 * ingredientName	VARCHAR(100) 	PRIMARY KEY,
	username	VARCHAR(100)	NOT NULL,
			 */
			int index = Integer.parseInt(recipeID);
			for(int i = 0; i < recipes.get(index).getIngredients().size(); ++i) {
				currListIndex = ListManager.getInstance().getGroceryList().size();
				try {
		    		Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
					ps = conn.prepareStatement(
							"INSERT INTO Grocery ("
							+ "ingredientName, "
							+ "username, "
							+ "listIndex"
							+ ") VALUES ('" 
							+ recipes.get(index).getIngredients().get(i) + "', '"
							+ userName + "', '"
							+ Integer.toString(currListIndex)
							+ "');");
					ret = ps.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//ListManager.getInstance().map.put(recipes.get(index).getIngredients().get(i), ListManager.getInstance().getGroceryList().size());
				ListManager.getInstance().getGroceryList().add(recipes.get(index).getIngredients().get(i));
			}
			
			
		}
		else if (recipeID != null && !recipeID.equals("")) {
			int index = Integer.parseInt(recipeID);
			
			// delimit instructions and ingredients by |
			// cant add instructions with "'"
			// clicked back to results, null items
		

			try {
	    		Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
				ps = conn.prepareStatement(
						"INSERT INTO ListRecipes ("
						+ "listName, "
						+ "username, "
						+ "recipeName,"
						+ "listIndex, "
						+ "recipeImageURL, "
						+ "recipeCookTime, "
						+ "recipePrepTime, "
						+ "recipeInstructions"
						+ ") VALUES ('" 
						+ listID + "', '"
						+ userName + "', '"
						+ recipes.get(index).getName() + "', '"
						+ Integer.toString(currListIndex) + "', '"
						+ recipes.get(index).getImgURL() + "', '"
						+ recipes.get(index).getCookTime() + "', '"
						+ recipes.get(index).getPrepTime()
						+ "');");
				ret = ps.execute();
				for(int i = 0; i < recipes.get(index).getInstructions().size(); ++i) {
					ps = conn.prepareStatement(
							"INSERT INTO Instructions ("
							+ "instruction, "
							+ "username, "
							+ "recipeName,"
							+ "listIndex"
							
							+ ") VALUES ('" 
							+ recipes.get(index).getInstructions().get(i) + "', '"
							+ userName + "', '"
							+ recipes.get(index).getName() + "', '"
							+ Integer.toString(i) 
			
							+ "');");
					ret = ps.execute();
				}
				
				
				
				for(int i = 0; i < recipes.get(index).getIngredients().size(); ++i) {
					ps = conn.prepareStatement(
							"INSERT INTO Ingredients ("
							+ "ingredient, "
							+ "username, "
							+ "recipeName,"
							+ "listIndex"
							
							+ ") VALUES ('" 
							+ recipes.get(index).getIngredients().get(i) + "', '"
							+ userName + "', '"
							+ recipes.get(index).getName() + "', '"
							+ Integer.toString(i) 
			
							+ "');");
					ret = ps.execute();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
		} 
		else if (restaurantID != null && !restaurantID.equals("")) {
			int index = Integer.parseInt(restaurantID);
			
			try {
	    		Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
				ps = conn.prepareStatement(
						"INSERT INTO ListRestaurants ("
						+ "listName, "
						+ "username, "
						+ "listIndex, "
						+ "restaurantName, "
						+ "restaurantAddress, "
						+ "restaurantPhone, "
						+ "restaurantURL, "
						+ "restaurantRating, "
						+ "restaurantDriveTime"
						+ ") VALUES ('" 
						+ listID + "', '"
						+ userName + "', '"
						+ Integer.toString(currListIndex) + "', '"
						+ restaurants.get(index).getName() + "', '"
						+ restaurants.get(index).getAddress() + "', '"
						+ restaurants.get(index).getPhoneNum() + "', '"
						+ restaurants.get(index).getWebsiteURL() + "', '"
						+ Double.toString(restaurants.get(index).getRating()) + "', '"
						+ Integer.toString(restaurants.get(index).getDriveTime())
						+ "');");
				ret = ps.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
		return ret;
	}
}	