<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="edu.usc.cs.group8.ImHungry.Recipe" %>
    <%@ page import="edu.usc.cs.group8.ImHungry.Restaurant" %>
    <%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	    <!-- Required meta tags -->
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    <link rel="stylesheet" type="text/css" href="results_page.css" />
	    
	    <!-- New for Pagination -->
		<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<!--	<link href="PaginationTesting.css" rel="stylesheet"> -->
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script type="text/javascript" src="https://www.solodev.com/assets/pagination/jquery.twbsPagination.js"></script>

	    <!-- Bootstrap CSS -->
 	    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">	
	
		<title>Results for <%= session.getAttribute("query") %></title>
		
		<style>
			.center {
			  text-align: center;
			}
			
			.pagination {
			  display: inline-block;
			}
			
			.pagination a {
			  border: 1px solid #ddd; /* Gray */
			  color: black;
			  float: left;
			  padding: 8px 16px;
			  text-decoration: none;
			  
			}
			
			.pagination a.active {
			  background-color: #4CAF50;
			  color: white;
			}
			
			.pagination a:hover:not(.active) {background-color: #ddd;}
		</style>
	</head>
	
	<body>
		<%
			String username = (String) session.getAttribute("username");
		%>
		<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/ssbootstrap.min.css" rel="stylesheet" id="bootstrap-css">
		<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
  		<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="js/jquery-1.11.3.min.js"></script>
	
	
		<script type="text/javascript">
		$(document).ready(function() {
			
		$('#pagination-demo').twbsPagination({
		totalPages: 5,
		// the current page that show on start
		startPage: 1,
		// maximum visible pages
		visiblePages: 5,
		initiateStartPageClick: true,
		// template for pagination links
		href: false,
		// variable name in href template for page number
		hrefVariable: '{{number}}',
		// Text labels
		first: 'First',
		prev: 'Previous',
		next: 'Next',
		last: 'Last',
		// carousel-style pagination
		loop: false,
		// callback function
		onPageClick: function (event, page) {
		   $('.page-active').removeClass('page-active');
		  $('#page'+page).addClass('page-active');
		},
		// pagination Classes
		paginationClass: 'pagination',
		nextClass: 'next',
		prevClass: 'prev',
		lastClass: 'last',
		firstClass: 'first',
		pageClass: 'page',
		activeClass: 'active',
		disabledClass: 'disabled'
		});
		
		});
		
		
		
		//draggable items in the dropdown menu: "Reorder any of the three lists"
		var dragSrcEl = null;

		function handleDragStart(e) {
		  // Target (this) element is the source node.
		  dragSrcEl = this;
		
		  e.dataTransfer.effectAllowed = 'move';
		  e.dataTransfer.setData('text/html', this.outerHTML);
		
		  this.classList.add('dragElem');
		}
		function handleDragOver(e) {
		  if (e.preventDefault) {
		    e.preventDefault(); // Necessary. Allows us to drop.
		  }
		  this.classList.add('over');
		
		  e.dataTransfer.dropEffect = 'move';  // See the section on the DataTransfer object.
		
		  return false;
		}
		
		function handleDragEnter(e) {
		  // this / e.target is the current hover target.
		}
		
		function handleDragLeave(e) {
		  this.classList.remove('over');  // this / e.target is previous target element.
		}
		
		function handleDrop(e) {
		  // this/e.target is current target element.
		
		  if (e.stopPropagation) {
		    e.stopPropagation(); // Stops some browsers from redirecting.
		  }
		
		  // Don't do anything if dropping the same column we're dragging.
		  if (dragSrcEl != this) {
		    // Set the source column's HTML to the HTML of the column we dropped on.
		    //alert(this.outerHTML);
		    dragSrcEl.innerHTML = this.innerHTML;
		    //this.innerHTML = e.dataTransfer.getData('text/html');
		    this.parentNode.removeChild(dragSrcEl);
		    var dropHTML = e.dataTransfer.getData('text/html');
		    this.insertAdjacentHTML('beforebegin',dropHTML);
		    var dropElem = this.previousSibling;
		    addDnDHandlers(dropElem);
		    
		  }
		  this.classList.remove('over');
		  return false;
		}
		
		function handleDragEnd(e) {
		  // this/e.target is the source node.
		  this.classList.remove('over');
		
		  //[].forEach.call(cols, function (col) {
		    //col.classList.remove('over');
		  //});
		}
		
		function addDnDHandlers(elem) {
		  elem.addEventListener('dragstart', handleDragStart, false);
		  elem.addEventListener('dragenter', handleDragEnter, false)
		  elem.addEventListener('dragover', handleDragOver, false);
		  elem.addEventListener('dragleave', handleDragLeave, false);
		  elem.addEventListener('drop', handleDrop, false);
		  elem.addEventListener('dragend', handleDragEnd, false);
		
		}
		
		var cols = document.querySelectorAll('#dropdownItems .dropdown-item');
		[].forEach.call(cols, addDnDHandlers);
		
 
		
		</script>
			
		<!-- <div class="container">
			
		</div> -->
		
		<div class = "div_for_entire_content">
		
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
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt" crossorigin="anonymous">
		
			<div class="container">
		    	<br/>    
			    <!-- <div class="jumbotron page" id="page1">id="page1"  
				   <div class="container">
				      <h1 class="display-3">Adding Pagination to your Website</h1><br>
				   <p class="lead">In this article we teach you how to add pagination, an excellent way to navigate large amounts of content, to your website using a jQuery Bootstrap Plugin.</p><br>
				   <p><a class="btn btn-lg btn-success" href="#" role="button">Learn More</a></p>
				   </div>
				</div> -->
				
				<div class="center">
					<div class="pagination">
					  <a href="#">&laquo;</a>
					  <% 
					  int num_results = Integer.parseInt((String)session.getAttribute("num_results"));
					  int num_pages = num_results/5;
					  if (num_results%5 > 0){
						  num_pages ++;
					  }
					  for (int i = 1; i < num_pages + 1; i++){
						  %> <a href="#" onclick="location.href = 'results_page.jsp?page_number=<%=i %>' "><%=i %></a>
					  <%} %>
					  <a href="#">&raquo;</a>
					</div>
				</div>
				
				<ul id="pagination-demo" class="pagination"></ul>
		
		    
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
									int end_index = page_number * 5;
									int start_index = end_index - 5;
									
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
										%> <tr><th><a href="restaurant_page.jsp?restaurant_id=<%= i%>&username=<%= username %>"><%=restaurant_name%></a> </th> <th>Drive Time: <%=driveTime%> min </th> <th><%=address %> </th> <th><%
												for (int j = 0; j < restaurant.getPriceRange(); j++ ){%>
													$
												<% } %></th></tr> <%
									}
									%>
								<!-- 
								<tfoot class="hide-if-no-paging">
									<td colspan="5">
										<div class="pagination"></div>
									</td>
								</tfoot>
								 -->
								 
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
	    
	    
	    
	    
  	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	
	</body>
</html>
