<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="edu.usc.cs.group8.ImHungry.Recipe" %>
    <%@ page import="edu.usc.cs.group8.ImHungry.Restaurant" %>
    <%@ page import="edu.usc.cs.group8.ImHungry.ListManager" %>
    <%@ page import="java.net.HttpURLConnection" %>
    <%@ page import="javax.servlet.http.HttpServlet" %>
    <%@ page import="javax.servlet.http.HttpServletRequest" %>
    <%@ page import="javax.servlet.http.HttpServletResponse" %>    
    <%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    
		<title>List Management Page</title>
		
		<script src="https://code.jquery.com/jquery-3.4.0.min.js" integrity="sha256-BJeo0qm959uMBGb65z40ejJYGSgR7REI4+CW1fNKwOg=" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js" integrity="sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU=" crossorigin="anonymous"></script>

		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<link rel="stylesheet" type="text/css" href="list_management_page.css" />
	</head>
	
	<body>
		<%
			String username = (String)session.getAttribute("username");
		%>
		
		<div class = "div_for_entire_content">
			<div class = "list_name_header"> 			
				<h1><%
				//The following will get the list_name of the current page
				String list_name = request.getParameter("list_id");
				%>
				<%=list_name.replace("_"," ") %> List</h1>
 			</div>
		<!-- <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt" crossorigin="anonymous"> -->
		
			<div class="container">
			<br/>
		    	<!-- Button Group for Dropdown for Predefined Lists, Back to Results Page and Back to Search Page-->	
				<div class="overall_information">
					<!-- The following are the menu list; dropdown menu; and buttons -->
					<div class="btn-group-vertical" id="button_stuff">
						<button id="btnGroupVerticalDrop2" type="button" class="btn btn-secondary dropdown-toggle btn-success" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></button>
						      <div class="dropdown-menu" aria-labelledby="btnGroupVerticalDrop2">
						        <a class="dropdown-item" href="javascript:void(0);">Favorites</a>
						        <a class="dropdown-item" href="javascript:void(0);">To Explore</a>
						        <a class="dropdown-item" href="javascript:void(0);">Do Not Show</a>
						        <a class="dropdown-item" href="javascript:void(0);">Grocery List</a>
						        
						      </div>
			      			<script>
			      				//The following code will get the name of the list that the user has chosen from the dropdown menu
			  					//And the name of the list will be of use when passing back to the backend or redirecting to another frontend page
			      				var list_has_been_chosen = false;
			      				var chosen_list = "";
							     $(function(){
								    $(".dropdown-item").click(function(){	
								    	console.log("HELLO");
								      $("#btnGroupVerticalDrop2").text($(this).text());
								      $("#btnGroupVerticalDrop2").val($(this).text());
								      list_has_been_chosen = true;
								      chosen_list = $(this).text();
								   });
								});
							</script>
							
							<!-- Manage List Button is the button to be clicked after user has selected a list from the dropdown menu
							 And by clicking the manage list button, user will be redirected to the page that s/he selected-->
							<button class="btn btn-success" id="manage_list_button">Manage List</button>
							<!-- Redirect to the List Management Page -->
								<script type="text/javascript">
									document.getElementById("manage_list_button").onclick = function(){
										if (list_has_been_chosen) {
											var list_name = "";
											var username = '<%= username %>';
											if (chosen_list == "Favorites"){
												list_name = "FAVORITES";
											}
											else if (chosen_list == "To Explore"){
												list_name = "TO_EXPLORE";
											}
											else if (chosen_list == "Do Not Show"){
												list_name = "DO_NOT_SHOW";
											}
											else if (chosen_list == "Grocery List"){
												list_name = "GROCERY_LIST"
											}
												
											//Redirect the user to the chosen list
											location.href = "list_management_page.jsp?list_id=" + list_name + "&username=" + username;
										}
									}
								</script>
							
							<!-- Back to Results button, when clicked, will take the user back 
							the result page with the previous search items -->
							<button id="back_to_results_button" class="btn btn-success">Back to Results</button>
								<!-- Back to Results -->
					        	<script type="text/javascript">
								    document.getElementById("back_to_results_button").onclick = function () {
								        location.href = "results_page.jsp";
								    };
								</script>
								
							<!-- Back to Search button, when clicked, will take the user back to the search page -->
							<button id="back_to_search_button" class="btn btn-success">Back to Search</button>
								<!-- Back to Search -->
								<script type="text/javascript">
									document.getElementById("back_to_search_button").onclick = function(){
										location.href = "search_page.jsp";
									};
								</script>
			      	</div>
			      	
			      	<!-- The following list will display all the items that the user has put into this list earlier -->
			      	<div class = "list_results">
			      		<table id="list_results_table">
			      		<tbody>
			      			<%
			      				//The following will get the corresponding singleton list object based on the current list user is at
			      				ArrayList list = null;
			      				if (list_name.equals("FAVORITES")){
			      					list = ListManager.getInstance().getFavorites();
			      				}
			      				else if (list_name.equals("TO_EXPLORE")){
			      					list = ListManager.getInstance().getToExplore();
			      				}
			      				else if (list_name.equals("DO_NOT_SHOW")){
			      					list = ListManager.getInstance().getDoNotShow();
			      				}
			      				else if (list_name.equals("GROCERY_LIST")){
			      					list = ListManager.getInstance().getGroceryList();
			      				}
			      				else return;
			      				
			      				//The following will iterate through each item and determine if the item is a recipe or a restaurant. 
			      				for (int i = 0; i < list.size(); i++){
			      					if (list.get(i) instanceof Recipe){	//If it is a recipe, then required recipe information will be displayed in one row in the table
			      						Recipe recipe = (Recipe)list.get(i);
			      						String recipe_name = recipe.getName();
			      						String cookTime, prepTime;
			      						if (recipe.getCookTime() == 0){
			      							cookTime = "No cook time available.";
			      						}
			      						else cookTime = recipe.getCookTime() + " min";
			      						if (recipe.getPrepTime() == 0){
			      							prepTime = "No prep time available.";
			      						}
			      						else prepTime = recipe.getPrepTime() + " min";
			      									      						
			      						if (list_name.contains("#")){
			      							list_name = list_name.substring(0,list_name.length()-1);
			      							System.out.println("list name: " + list_name);
			      						}
			      						String redirect_link = "http://localhost:8080/ImHungry/IHManageList?list_id=" + list_name + "&action=DISPLAY&item_id=" + Integer.toString(i) + "&username=" + username; 
			      						%><tr><td style="display: none"><%=i %></td><td><a href=<%= redirect_link%>><%=recipe_name %> </td> <td>Prep Time: <%=prepTime %></td> <td>Cook Time: <%=cookTime %></td></a>
			      						<%
			      					}
			      					else if (list.get(i) instanceof Restaurant){ //If it is a restaurant, then required restaurant information will be displayed in one row in the table
			      						Restaurant restaurant = (Restaurant)list.get(i);
			      						String restaurant_name = restaurant.getName();
			      						int driveTime = restaurant.getDriveTime();
			      						String address = restaurant.getAddress();
			      						
										System.out.println("list name: " + list_name);
			      						
			      						if (list_name.contains("#")){
			      							list_name = list_name.substring(0,list_name.length()-1);
			      							System.out.println("list name: " + list_name);
			      						}
			      						String redirect_link = "IHManageList?list_id=" + list_name + "&action=DISPLAY&item_id=" + Integer.toString(i) + "&username=" + username; 

			      						%> <tr><td style="display: none"><%=i %></td><td><a href=<%= redirect_link %>><%=restaurant_name %> </td> <td>Drive Time: <%=driveTime %> min </td> <td><%=address %> </td> <td><%
											for (int j = 0; j < restaurant.getPriceRange(); j++ ){%>
												$
											<% } %></td></a>
										<%
			      					}
			      					//NEW FOR GROCERY_LIST, if it is neither a Recipe nor Restaurant, then it is an ingredient
			      					else {
			      						String ingredient = (String)list.get(i);
			      						%>
			      							<tr><td style="display: none"><%=i %></td><td><%=ingredient %></td></tr>
			      						<%
			      					}
			      				}
			      			
			      			%>
			      			</tbody>
			      		</table>     
			      		
			      		<script>
						    $('tbody').sortable();
						</script>
			      		
			      		<br>
			      		<!-- If the user clicks on the delete button, then the selected item from the radio buttons
			      		 will be deleted from the current list -->
			      		<button type="submit" id="delete_button">Delete the Selected Item from the list</button>
			      		<!-- The following JavaScript function deals with Deleting the Selected Item from the Current List -->
			      		<script type="text/javascript">
				      		$("table tr").click(function() {
				      		   $(this).addClass('selected');
				      		   $(this).siblings().removeClass('selected');
				      		});
				      		
				      		$("#delete_button").click(function() {
				      			var index = $("tr.selected").find("td:first").html();
				      			var username = '<%= username %>';
				      			
				      			var list_name = getUrlVars()["list_id"];
				      			var redirect_link = "IHManageList?list_id=" + list_name + "&username=" + username + "&action=REMOVE&item_id=" + index.toString() ;
				      			console.log(redirect_link);
			      				location.href = redirect_link;
				      		});
			      			
			      			//Helper function to get the value of the attribute in the url
			      			function getUrlVars() {
			      			    var vars = {};
			      			    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
			      			        vars[key] = value;
			      			    });
			      			    return vars;
			      			}	
			      		</script>
			      		
			      		<!-- If the user clicks on the MOVE button, then the selected item from the radio buttons
			      		 will be moved from the current list to the list selected from the dropdown menu. 
			      		 If nothing is selected from the dropdown menu, then the MOVE will not happen -->
			      		<button type="submit" id="move_button">Move the Selected Item to the List chosen from the Dropdown Menu</button>
			      		<!-- The following JavaScript function deals with moving the selected item from the current list to the list chosen from the dropdown menu -->
			      		<script type="text/javascript">
			      			document.getElementById("move_button").onclick=function(){
			      				console.log("In function");
			      				var list_name = getUrlVars()["list_id"]
			      				
			      				//Get the destination list name
			      				var destination_list_name = "";
								if (chosen_list == "Favorites"){
									destination_list_name = "FAVORITES";
								}
								else if (chosen_list == "To Explore"){
									console.log("In to explore");
									destination_list_name = "TO_EXPLORE";
								}
								else if (chosen_list == "Do Not Show"){
									destination_list_name = "DO_NOT_SHOW";
								}
			      				var username = '<%= username %>';
								var radios = document.getElementsByTagName('input');
			      				var item_index;
			      				for (var i = 0; i < radios.length; i++){
			      					if (radios[i].type === 'radio' && radios[i].checked) {
			      				        // get the item_index from the radio button input
			      				        item_index = radios[i].value;       
			      				    }
			      				}
			      				//send the MOVE request to the backend servlet and let the backend deal with the move logic and session storage.
			      				/*var redirect_link = "IHManageList?list_id=" + list_name + "&destination_id=" + destination_list_name + "&action=MOVE&item_id=" + item_index.toString();
 			      				*/
			      				var redirect_link = "IHManageList?list_id=" + list_name + "&destination_id=" + destination_list_name + "&action=MOVE&item_id=" + item_index.toString() + "&username=" + username;

			      				location.href = redirect_link;
			      			}
			      		</script>
			      	</div>
				</div>
			</div>
		</div>
	</body>
</html>
