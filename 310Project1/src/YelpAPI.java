import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class YelpAPI {
	private static double latitude = 34.0206;
	private static double longitude = -118.2854;
	private static int radius = 8500;
	private static String python3 = "/usr/local/bin/python3"; // should be python3 path
	private static String yelpSiteScraper = "/Users/patrickjamespacheco/Dropbox/eclipse_workspace/cs310-hungry/yelp.py"; // location of script
	public static List<JSONObject> search(String query, int limit) throws Exception {
		// Grab results from Yelp Api and store in a JSON Object
		String url = "https://api.yelp.com/v3/businesses/search?latitude="+latitude+"&longitude="+longitude+"&radius="+radius+"&term="+query;
        URL yelp = new URL(url);
        URLConnection yc = yelp.openConnection();
        yc.setRequestProperty("Authorization", "Bearer m3PWo7Mfa4JlW4nu2b87M8lq7O5lyQo3hIbO4SFibB22s-toYW2yH7GPxKLRL468dtI-2qrRUN0qcN_9trcrp6FxkEj1Eg5DqEMupJkOrw7EFgAfNK128x1Gb3ZeXHYx");
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;
        JSONObject results = null;
        while ((inputLine = in.readLine()) != null) {
//		            System.out.println(inputLine);
            JSONParser parser = new JSONParser();
            results = new JSONObject();
            try {
				results = (JSONObject) parser.parse(inputLine);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				
			}
        }
        
        
        // Grab the list of restaurants and store in a list to sort by distance
        List<JSONObject> res = new ArrayList<>();
        res = (ArrayList<JSONObject>) results.get("businesses");
        res.sort((JSONObject r1, JSONObject r2) -> (int) ((Double)r1.get("distance") - (Double)r2.get("distance")));

        JSONParser parser = new JSONParser();
        List<JSONObject> frontend = new ArrayList<>();
        JSONObject idToRestaurant = new JSONObject();
        // only acquire up to limit
        for(int i = 0; i < limit; ++i) {
        	JSONObject restaurant = res.get(i);
        	JSONObject found = new JSONObject();
        	
        	
        	try {
				results = (JSONObject) parser.parse(restaurant.get("location").toString());
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}
        	List<String> address = (List<String>) results.get("display_address"); 

        	found.put("name", restaurant.get("name"));
        	found.put("address", address.get(0) + ", " + address.get(address.size()-1));
        	found.put("phone", restaurant.get("display_phone"));
        	found.put("rating", restaurant.get("rating"));
        	found.put("price", restaurant.get("price"));
        	// python script too slow  for getting restaurant website
//        	String restaurantURL = null;
//        	try {
//        		String restaurantYelpPage = (String) restaurant.get("url");
//            	Process p = Runtime.getRuntime().exec(python3 + " " + yelpSiteScraper + " " + restaurantYelpPage);
//                in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//                restaurantURL = in.readLine();
//                
//        	} catch (IOException e) {
//        		System.out.println(e.getMessage());
//        	}
        	
        	found.put("url", restaurant.get("url"));
        	found.put("type", "restaurant");
        	// estiamted time to restaurant
        	double time =  (double) restaurant.get("distance") / 67.7;
        	found.put("time", (int) time + " minutes");
        	
        	found.put("id", restaurant.get("id"));
        	idToRestaurant.put(found.get("id"), found);;
        	
        	frontend.add(found);
        }
        frontend.add(idToRestaurant);
        
        System.out.println(frontend);
        System.out.println(idToRestaurant);
        
        in.close();
        return frontend;
	}
}
