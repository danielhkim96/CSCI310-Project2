

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject; 




/**
 * Servlet implementation class APIAccess
 */
@WebServlet("/APIAccess")
public class APIAccess extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<JSONObject> restaurants = new ArrayList<>();
		HttpSession session = request.getSession();
		String query = (String) session.getAttribute("query");
		if(session.getAttribute("restaurants") == null) {
			int limit = Integer.parseInt((String) session.getAttribute("limit"));
			
			
			try {
				restaurants = YelpAPI.search(query, limit);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			int lastIdx = restaurants.size()-1;
			session.setAttribute("idToRestaurants", restaurants.get(lastIdx));
			restaurants.remove(lastIdx);
			// Store restaurants JSON in session
			session.setAttribute("restaurants", restaurants);
		}
		else {
			restaurants = (List<JSONObject>) session.getAttribute("restaurants");
		}
		
		// add favorites here
		List<JSONObject> favList = (List<JSONObject>) session.getAttribute("Favorites");
		List<JSONObject> result = Stream.concat(favList.stream(), restaurants.stream())
                						.collect(Collectors.toList());
		System.out.println(result);
		// return result, maybe take the union?
		String param = "getRestaurants";
		if(request.getParameter("type") != null) {
			param = request.getParameter("type");
		}
		if(param != null && param.equals("getRestaurants")) {
			response.getWriter().println(restaurants);
		}
		else if(param != null && param.equals("getQuery")) {
			response.getWriter().print(query);
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String query = (String) request.getParameter("q");
		System.out.println(query);
//		if(!session.getAttribute("query").equals(query)) {
			System.out.println("inside");			
			session.removeAttribute("restaurants");
		if(query != null || !query.isEmpty()) {
			session.setAttribute("query", query);
			session.setAttribute("limit", request.getParameter("limit"));
			System.out.println(request.getParameter("limit"));
			// maybe initialize all list pages to empty list<jsonobjects>
			if(session.getAttribute("Favorites") == null ||
					session.getAttribute("ToExplore") == null ||
					session.getAttribute("DoNotShow") == null) {
				session.setAttribute("Favorites", new ArrayList<JSONObject>());
				session.setAttribute("ToExplore", new ArrayList<JSONObject>());
				session.setAttribute("DoNotShow", new ArrayList<JSONObject>());
			}
			
			response.sendRedirect("results.html");	
		}
		else {
			System.out.println("empty query");
		}
		
		
	}
	
	private void HungryInit(HttpServletRequest request, HttpServletResponse response, String query) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("restaurants");
		System.out.println("restaurants session: " + session.getAttribute("restaurants"));
	
	}

}
