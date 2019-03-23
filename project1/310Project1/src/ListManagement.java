

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class ListManagement
 */
@WebServlet("/ListManagement")
public class ListManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		String listName = request.getParameter("listName");
		List<JSONObject> list = (List<JSONObject>) session.getAttribute(listName);
		System.out.println(list);
		response.getWriter().println(list);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		System.out.println("in list management post");
		HttpSession session = request.getSession();
		String listName = request.getParameter("listName");
		String restaurantId = request.getParameter("id");
		List<JSONObject> list = new ArrayList<>();
		JSONObject idToRestaurants = (JSONObject) session.getAttribute("idToRestaurants");
		System.out.println(listName);
		if(listName != null) {
			if(listName.equals("Favorites")) {
				if(session.getAttribute("Favorites") != null) {
					list = (List<JSONObject>) session.getAttribute("Favorites");
				}
			}
			else if(listName.equals("To Explore")) {
				if(session.getAttribute("To Explore") != null) {
					list = (List<JSONObject>) session.getAttribute("ToExplore");
				}
			}
			else if(listName.equals("Do Not Show")) {
				if(session.getAttribute("Do Not Show") != null) {
					list = (List<JSONObject>) session.getAttribute("DoNotShow");
				}
			}
			JSONObject item = (JSONObject) idToRestaurants.get(restaurantId);
			String action = request.getParameter("action");
			if(action.equals("add")) {
				if(!list.contains(item)) {
					list.add(item);
				}
			}
			else if(action.equals("delete")) {
				if(list.contains(item)) {
					list.remove(item);
				}
			}
			else if(action.equals("move")) {
				if(!list.contains(item)) {
					list.add(item);
				}
				String currListName = request.getParameter("currentList");
				System.out.println(currListName);
				List<JSONObject> currList = new ArrayList<>();
				currList = (List<JSONObject>) session.getAttribute(currListName);
				if(currList.contains(item)) {
					currList.remove(item);
				}
				session.setAttribute(currListName, currList);
				
				
			}
			session.setAttribute(listName, list);
		}
	}

}
