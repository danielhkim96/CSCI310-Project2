<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="edu.usc.cs.group8.ImHungry.Recipe" %>
    <%@ page import="edu.usc.cs.group8.ImHungry.Restaurant" %>
    <%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <!-- Required meta tags -->
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    
	    <title>Results Page</title>
	    
	    <script src="https://code.jquery.com/jquery-3.4.0.min.js" integrity="sha256-BJeo0qm959uMBGb65z40ejJYGSgR7REI4+CW1fNKwOg=" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	    <link rel="stylesheet" type="text/css" href="results_page.css" />
	</head>
	<body>
		<%
			String username = (String) session.getAttribute("username");
			int ITEMS_EACH_PAGE = 5;
		%>
		
		<div class="container">
			<!-- The following div will display the collage with random rotation angle -->
			<div id=collage>
		    	<% Random r = new Random(); %>
		    	<% for(int i = 0; i < ((ArrayList<String>)(session.getAttribute("images"))).size(); i+=1) { %> 
			            <img class="collage_image" src=<%=((ArrayList<String>)(session.getAttribute("images"))).get(i)%> style="max-width:30%; max-height:30%; object-fit: contain; transform: rotate(<%= r.nextInt(31) - 15 %>deg)">
			    <% } %>
			    
			    <div class="btn-group-vertical" id="button_stuff">
					<button id="btnGroupVerticalDrop2" type="button" class="btn btn-secondary dropdown-toggle btn-success" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></button>
					<div id="dropdownItems" class="dropdown-menu" aria-labelledby="btnGroupVerticalDrop2">
	        			<a class="dropdown-item" draggable="true" href="#">Favorites</a>
	        			<a class="dropdown-item" draggable="true" href="#">To Explore</a>
	        			<a class="dropdown-item" draggable="true" href="#">Do Not Show</a>
	        			<a class="dropdown-item" draggable="true" href="#">Grocery List</a>
	      			</div>

	      			<script>
	      				//This is a helper fuction that will help to make the dropdown menu look nicer
	      				//Specifically, the name of the list will be displayed on the button after selected
	      				var list_has_been_chosen = false;
	      				var chosen_list = "";
						$(function(){
						    $(".dropdown-item").click(function(){					
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
									list_name = "GROCERY_LIST";
								}
								
								//Redirect the user to the chosen list
								location.href = "list_management_page.jsp?list_id=" + list_name + "&username=" + username;
							}
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
	    	</div>
			<div class = "text-center"> 
				<!-- This is the header for the result page -->
				<h1>Results for <%= session.getAttribute("query") %></h1>
			</div>
			<div class="content">
				<div class="pagination-wrapper">
					<ul class="pagination justify-content-center">
						<li class="page-prev">
							<a class="page-link" href="#" tabindex="-1">Previous</a>
						</li>
						<li class="page-next">
							<a class="page-link" href="#">Next</a>
						</li>
					</ul>
				</div>
				<%
					int pageNum = 1;
					if (request.getParameter("page_number") != null) {
						pageNum = Integer.parseInt((String) request.getParameter("page_number"));
					}
				%>
				<script>
					$(document).ready(function() {
						var numResults = <%= Integer.parseInt((String) session.getAttribute("num_results")) %>;
						var pageSize = 5;
						var totalPages = Math.ceil(numResults / pageSize);
						var currPage = <%= pageNum %>;
						
					    var start, end, nav = "";
					    if (totalPages <= 5) {
					    	start = 1;
					    	end = totalPages;
					    } else {
					    	start = Math.max(1, currPage - 2);
					    	end = Math.min(currPage + 2, totalPages);
					    	
					    	if (start === 1) {
					    		end = 5;
					    	} else if (end === totalPages) {
					    		start = totalPages - 4;
					    	}
					    }
					    
					    for (var i = start; i <= end; i++) {
					    	nav += "<li class='page-item'><a class='page-link' href='#'>" + i + "</a></li>";
					    }
					    
					    $(".page-prev").after(nav);
					    if (start === 1) {
					    	$(".page-item:eq(" + (currPage - 1) + ")").addClass("active");
					    } else if (end === totalPages) {
					    	$(".page-item:eq(" + (4 - (totalPages - currPage)) + ")").addClass("active");
					    } else {
					    	$(".page-item:eq(" + ((end - start) / 2) + ")").addClass("active");
					    }
					    
					    showPage = function() {
					        window.location.href = "http://localhost:8080/ImHungry/results_page.jsp?page_number=" + currPage + "#";
					    }

					    $(".pagination li.page-item").click(function() {
					        $(".pagination li").removeClass("active");
					        $(this).addClass("active");
					        currPage = parseInt($(this).text());
					        showPage();
					    });

					    $(".pagination li.page-prev").click(function() {
					        if ($(this).next().is('.active')) return;
					        $('.page-item.active').removeClass('active').prev().addClass('active');
					        currPage = currPage > 1 ? (currPage - 1) : 1;
					        showPage();
					    });

					    $(".pagination li.page-next").click(function() {
					        if ($(this).prev().is('.active')) return;
					        $('.page-item.active').removeClass('active').next().addClass('active');
					        currPage = currPage < totalPages ? (currPage + 1) : totalPages;
					        showPage();
					    });
					});
				</script>
		    
		    	<!-- Button Group for Back to Search Page and Dropdown for Predefined Lists-->	
				<div class="overall_information">
				
					<!-- The following div is an outer container div for the two tables -- restaurant results table
					and recipe results table -->
					<div class="recipe_and_restaurant_results">
					
						<!-- The following div is the container for the restaurant results table-->
						<div class="restaurant_results">
							<h2>Restaurant Results</h2>
							<table id="restaurant_results_table">
							 <!--  class="footable" data-page-size="10" -->
								<%
									//Getting restaurant results array list from session
									if (session.getAttribute("restaurants") == null){
										return;
									}
									ArrayList<Restaurant> list_of_restaurant_results = (ArrayList<Restaurant>)(session.getAttribute("restaurants"));
									int page_number = 1;
									if (request.getParameter("page_number") != null){
										page_number = Integer.parseInt(request.getParameter("page_number"));
									};
									int end_index = page_number * ITEMS_EACH_PAGE;
									int start_index = end_index - ITEMS_EACH_PAGE;
									
									//TODO: error checking on the end_index;
									
									for (int i = start_index; i < end_index; i++){
										if (i >= list_of_restaurant_results.size()){
											break;
										}
										//The following will get the detailed restaurant information needed to be displayed
										Restaurant restaurant = list_of_restaurant_results.get(i);
										String restaurant_name = restaurant.getName();
										int driveTime = restaurant.getDriveTime();
										String address = restaurant.getAddress();
										%> <tr><th><a href="restaurant_page.jsp?restaurant_id=<%= i%>&username=<%= username %>"><%=restaurant_name%></a> </th> <th>Drive Time: <%=driveTime%> min </th> <th><%=address %> </th>
										<th><%
												for (int j = 0; j < restaurant.getPriceRange(); j++ ){%>
													$
												<% } %></th></tr> <%
									}
									%>
							</table>
						</div>
					
						<!-- The following div is the container for the recipe results table -->
						<div class="recipe_results">
							<h2>Recipe Results</h2>
							<table id="recipe_results_table">
								<%
								//Getting recipe results array list from session
								if (session.getAttribute("recipes") == null){
									return;
								}
								ArrayList<Recipe> list_of_recipe_results = (ArrayList<Recipe>)(session.getAttribute("recipes"));									
								
								for (int i = start_index; i < end_index; i++){
									if (i >= list_of_recipe_results.size()){
										break;
									}
									//The following will get the detailed recipe information needed to be displayed
									Recipe recipe = list_of_recipe_results.get(i);
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
									%> <tr><th><a href="recipe_page.jsp?recipe_id=<%= i%>&username=<%= username %>"><%=recipe_name%></a></th> <th>Prep Time: <%=prepTime %></th> <th>Cook Time: <%=cookTime %></th> </tr> <%
								}
								%>
							</table>
						</div>
					</div>			
		        </div>  
	        </div>	    
	    </div>
	</body>
</html>
