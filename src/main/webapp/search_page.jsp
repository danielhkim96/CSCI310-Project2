<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<title>Search Page</title>
	
	<script src="https://code.jquery.com/jquery-3.4.0.min.js" integrity="sha256-BJeo0qm959uMBGb65z40ejJYGSgR7REI4+CW1fNKwOg=" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="search_page.css" />
	</head>
	<body>
		<div class="container">
			<nav class="navbar fixed-top">
				<div class="container-fluid">
					<a href="login_page.html">
						<span class="btn btn-login">Login</span>
					</a>
					<a href="#">
						<span class="btn btn-logout">Logout</span>
					</a>
				</div>
			</nav>

			<div class="title">I'm Hungry</div>
			<div class="row justify-content-center">
				<form class="form-inline" action="IHSearch" id="search_form" name="search">
				<div class="input-group" id="adv-search">
					<input type="text" class="form-control" name="search_query" placeholder="Search" required/>
					<div class="input-group-btn">
						<div class="btn-group" role="group">
							<div class="dropdown dropdown-lg">
								<button type="button" id="drop-down-button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
									<span class="caret"></span>
								</button>
								<div class="dropdown-menu dropdown-menu-right" role="menu">
									<div class="dropdown-wrapper">
										<div class="form-group row">
											<label for="number" class="col-sm-5 col-form-label">Number of Results</label>
											<input type="number" class="form-control" min="1" value="5" onkeypress="return [45, 46, 69, 101].indexOf(event.charCode) == -1" id="results" name="num_results" required/>
										</div>
										<div class="form-group row">
											<label for="distance" class="col-sm-5 col-form-label">Distance in Miles</label>
											<input type="number" class="form-control" min="0" onkeypress="return [45, 69, 101].indexOf(event.charCode) == -1" id="radius" name="radius"/>
										</div>
									</div>
								</div>
							</div>
							<button type="submit" class="btn btn-search" id="submit_button">Feed Me!</button>
						</div>
					</div>
					<input type="hidden" id="username" name="username" value=""/>
				</div>
				</form>
			</div>
		</div>
		
		<script type="text/javascript">
			$(document).ready(function() {
				var username = document.getElementById("username");
				username.value = window.localStorage.getItem("username");
				if (username.value === "") {
					$('.btn.btn-login').show();
					$('.btn.btn-logout').hide();
				} else {
					$('.btn.btn-login').hide();
					$('.btn.btn-logout').show();
				}
				
				$('.btn.btn-logout').on('click', function() {
					window.localStorage.clear();
					username.value = "";
					$('.btn.btn-login').show();
					$('.btn.btn-logout').hide();
					alert("Successfully logged out.");
				});
			});
			// Restrict input for the given textbox 
			 function setInputFilter(textbox, inputFilter) {
			 	["input", "keydown", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop"].forEach(function(event) {
					textbox.addEventListener(event, function() {
						if (inputFilter(this.value)) {
							this.oldValue = this.value;
							this.oldSelectionStart = this.selectionStart;
							this.oldSelectionEnd = this.selectionEnd;
						} else if (this.hasOwnProperty("oldValue")) {
							this.value = this.oldValue;
							this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
						}
					});
				});
			}
			// Restrict number of results to integers >= 1
			setInputFilter(document.getElementById("results"), function(value) {
				return /^\d*$/.test(value) && (value === "" || parseInt(value) >= 1);
			});
			//Restrict radius to floats > 0
			setInputFilter(document.getElementById("radius"), function(value) {
				return /^\d*(\.\d)?$/.test(value) && (value === "" || parseFloat(value) >= 0);
			});
		
		    document.getElementById("submit_button").onclick = function () {
			if (document.forms['search'].search_query.value === "") {
					alert("Please enter a search term.");
					return false;
				}
			document.getElementById("search_form").submit();
		    };
		</script>
	</body>
</html>