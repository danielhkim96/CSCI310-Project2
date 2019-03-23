import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RecipeAPI {
	
	public static List<JSONObject> search(String query, int n) throws Exception {
		String url = "https://api.edamam.com/search?q="+query+
					 "&app_id=6d289c9c&app_key=4f4866fd21b0b0a16481f81de1bd60aa&from=0&to="+n;
		URL api = new URL(url);
		URLConnection rc = api.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(rc.getInputStream()));
		String inputLine = "", jsonString = "";
		while ((inputLine = in.readLine()) != null) {
			jsonString += inputLine;
		}
		JSONObject json = new JSONObject();
		JSONParser parser = new JSONParser();
		List<JSONObject> results = new ArrayList<>();
		JSONObject idToRecipe = new JSONObject();
		try {
			json = (JSONObject) parser.parse(jsonString);
			List<JSONObject> recipes = (List<JSONObject>) json.get("hits");
			for (int i = 0; i < n; i++) {
				JSONObject recipe = (JSONObject) recipes.get(i).get("recipe");
				JSONObject parsedRecipe = new JSONObject();
				parsedRecipe.put("id", recipe.get("uri"));
				parsedRecipe.put("title", recipe.get("label"));
				parsedRecipe.put("imageURL", recipe.get("image"));
				parsedRecipe.put("cookTime", recipe.get("totalTime"));
				parsedRecipe.put("url", recipe.get("url"));
				
				List<JSONObject> ingredients = new ArrayList<>();
				for (JSONObject ingredient : (List<JSONObject>) recipe.get("ingredients")) {
					String text = (String) ingredient.get("text");
					JSONObject parsedIngr = new JSONObject();
					parsedIngr.put("text", text);
					ingredients.add(parsedIngr);
				}
				parsedRecipe.put("ingredients", ingredients);
				idToRecipe.put(parsedRecipe.get("id"), parsedRecipe);
				results.add(parsedRecipe);
			}
			results.add(idToRecipe);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		return results;
	}
	
}
