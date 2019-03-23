

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class getRestaurantInformation
 */
@WebServlet("/getRestaurantInformation")
public class getRestaurantInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getRestaurantInformation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		//get the JSONObject containing all restaurants, and use the id to map it to required restaurant
		JSONObject allRestaurants = (JSONObject) session.getAttribute("idToRestaurants");
		String restaurantID = (String) request.getParameter("id");
	
		JSONObject restaurantFound = (JSONObject) allRestaurants.get(restaurantID);
		
		//use the address to generate the Google Maps directions URl and store it in the object
		String address = (String) restaurantFound.get("address");
		String directionsURL = getDirectionsURL(address);
		restaurantFound.put("directionsURL", directionsURL);
		
		response.getWriter().println(restaurantFound);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public String getDirectionsURL(String clickedAddress) {
		//use the address passed and the predefined origin address to generate the directions URL
		String originURLLink = "https://www.google.com/maps/dir/?api=1&origin=Tommy+Trojan+Los+Angeles+CA&destination=";
		String travelModeURLLink = "&travelmode=driving";
		String finalLink = "";
		
		clickedAddress = URLifyString(clickedAddress);
		
		finalLink = originURLLink + clickedAddress + travelModeURLLink;
		return finalLink;
	}

	private String URLifyString(String address) {
		//trim the given address 
        address = address.trim(); 
          
        //replace all space (unicode is \\s) to + 
        address = address.replaceAll("\\s", "+");
        
        //replace all commas to %2C
        address = address.replaceAll(",", "%2C");
        
        return address;
	}
}
