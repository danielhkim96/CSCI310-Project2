package edu.usc.cs.group8.ImHungry;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
	static String DB_PASSWORD = "12345678Abc";
	
	LoginServlet() {
		super();
		
	}
    
	
    @Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ResultSet rs2 = null;
		String username = request.getParameter("email");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project2?user=" + DB_USERNAME + "&password=" + DB_PASSWORD + "&userSSL=false&serverTimezone=UTC");
			
			
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
			} else if (action.equals("logout")){
				//request.getSession().setAttribute("username", null);
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
		
		TreeMap<Integer, Result> tmFavorites = new 
	              TreeMap<Integer,Result>();
		TreeMap<Integer, Result> tmDoNotShow = new 
	              TreeMap<Integer,Result>();
		TreeMap<Integer, Result> tmToExplore = new 
	              TreeMap<Integer,Result>();
		TreeMap<Integer, String> tmGroceryList = new 
	              TreeMap<Integer,String>();
		
		TreeMap<Integer, String> tmInstructions = new 
	              TreeMap<Integer,String>();
		
		TreeMap<Integer, String> tmIngredients = new 
	              TreeMap<Integer,String>();
		
		 rs = null;
		 try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project2?user=" + DB_USERNAME + "&password=" + DB_PASSWORD + "&userSSL=false&serverTimezone=UTC");
				ps = conn.prepareStatement(
						"SELECT * FROM ListRestaurants WHERE username = ?");
				ps.setString(1, username);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					 
					String listName = rs.getString("listName");
					int listIndex = Integer.parseInt(rs.getString("listIndex"));
					String restName = rs.getString("restaurantName");
					String restAddress = rs.getString("restaurantAddress");
					String restaurantPhone = rs.getString("restaurantPhone");
					String restaurantURL = rs.getString("restaurantURL");
					String restaurantRating = rs.getString("restaurantRating");
					String restaurantDriveTime = rs.getString("restaurantDriveTime");
					
					double rating;
					int driveTime;
					
					if(restaurantRating.equals("")) {
						rating = 0.0;
					}
					else {
						rating = Double.parseDouble(restaurantRating);
					}
					
					if(restaurantDriveTime.equals("")) {
						driveTime = -1;
					}
					else {
						driveTime = Integer.parseInt(restaurantDriveTime);
					}
					Restaurant tempRest = new Restaurant( restName,driveTime,  restaurantURL,restAddress, restaurantPhone, -1, rating, "");
					if(listName.equals("FAVORITES")){
						//ListManager.getInstance().addToFavorites((Result)tempRest);
						tmFavorites.put(listIndex, (Result)tempRest);
					}
					else if(listName.equals("TO_EXPLORE")) {
						//ListManager.getInstance().addToToExplore((Result)tempRest);
						tmToExplore.put(listIndex, (Result)tempRest);
					}
					else if(listName.equals("DO_NOT_SHOW")) {
						//ListManager.getInstance().addToDoNotShow((Result)tempRest);
						tmDoNotShow.put(listIndex, (Result)tempRest);
					}
				}
				rs = null;
				ps = conn.prepareStatement(
						"SELECT * FROM ListRecipes WHERE username = ?");
				ps.setString(1, username);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					String listName = rs.getString("listName");
					int listIndex = Integer.parseInt(rs.getString("listIndex"));
					String recipeName = rs.getString("recipeName");
					String recipeImageURL = rs.getString("recipeImageURL");
					String recipeCookTime = rs.getString("recipeCookTime");
					String recipePrepTime = rs.getString("recipePrepTime");
					ArrayList<String> tempInstructions = new ArrayList<String>();
					ps = conn.prepareStatement(
							"SELECT * FROM Instructions WHERE username = ? AND recipeName = ?");
					ps.setString(1, username);
					ps.setString(2, recipeName);
					
					rs2 = ps.executeQuery();
					while(rs2.next()) {
						int instructionIndex = Integer.parseInt(rs2.getString("listIndex"));
						String instruction = rs2.getString("instruction");
						tmInstructions.put(instructionIndex, instruction);
					}
					Set<Map.Entry<Integer, String>> set = 
							tmInstructions.entrySet(); 
				        for(Map.Entry<Integer,String> me : set) 
				            tempInstructions.add(me.getValue());
					tmInstructions.clear();
					rs2 = null;
					
					ArrayList<String> tempIngredients = new ArrayList<String>();
					ps = conn.prepareStatement(
							"SELECT * FROM Ingredients WHERE username = ? AND recipeName = ?");
					ps.setString(1, username);
					ps.setString(2, recipeName);
					
					rs2 = ps.executeQuery();
					while(rs2.next()) {
						int ingredientIndex = Integer.parseInt(rs2.getString("listIndex"));
						String ingredient = rs2.getString("ingredient");
						tmIngredients.put(ingredientIndex, ingredient);
					}
					Set<Map.Entry<Integer, String>> set2 = 
							tmInstructions.entrySet(); 
				        for(Map.Entry<Integer,String> me : set2) 
				            tempInstructions.add(me.getValue());
					tmIngredients.clear();
					rs2 = null;
					
					Recipe tempRecipe = new Recipe(recipeName, recipePrepTime, recipeCookTime, recipeImageURL, tempIngredients, tempInstructions);
					if(listName.equals("FAVORITES")){
						//ListManager.getInstance().addToFavorites((Result)tempRest);
						tmFavorites.put(listIndex, (Result)tempRecipe);
					}
					else if(listName.equals("TO_EXPLORE")) {
						//ListManager.getInstance().addToToExplore((Result)tempRest);
						tmToExplore.put(listIndex, (Result)tempRecipe);
					}
					else if(listName.equals("DO_NOT_SHOW")) {
						//ListManager.getInstance().addToDoNotShow((Result)tempRest);
						tmDoNotShow.put(listIndex, (Result)tempRecipe);
					}
				}
				rs = null;
				ps = conn.prepareStatement(
						"SELECT * FROM Grocery WHERE username = ?");
				ps.setString(1, username);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					int listIndex = Integer.parseInt(rs.getString("listIndex"));
					String ingredientName = rs.getString("ingredientName");
					
					tmGroceryList.put(listIndex, ingredientName);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			 for(Map.Entry<Integer,Result> entry : tmFavorites.entrySet()) {
				 ListManager.getInstance().addToFavorites(entry.getValue());
			}
			 for(Map.Entry<Integer,Result> entry : tmDoNotShow.entrySet()) {
				 ListManager.getInstance().addToDoNotShow(entry.getValue());
				}
			 for(Map.Entry<Integer,Result> entry : tmToExplore.entrySet()) {
				 ListManager.getInstance().addToToExplore(entry.getValue());
				}
			 
			 for(Map.Entry<Integer,String> entry : tmGroceryList.entrySet()) {
				 ListManager.getInstance().addToGroceryList(entry.getValue());
				}
    }

}
