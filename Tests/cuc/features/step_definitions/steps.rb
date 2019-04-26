Given("I search for {string} results for {string}") do |string, string2|
  visit 'http://localhost:8080/ImHungry/search_page.jsp'
  fill_in 'search_query', :with => string2
  find('#drop-down-button').click
  fill_in 'num_results', :with => string
  click_on("Feed Me!")
end

Then("I should see {string}") do |string|
  expect(page).to have_content string
end
	
When("I click {string}") do |string|
  click_on(string)
end

When("I navigate to the Grocery page") do
  find('#btnGroupVerticalDrop2').click
  click_on("Grocery List")  
  click_on("Manage List")
  find('#manage_list_button').click
end

When("I add to Grocery List") do
  find('#btnGroupVerticalDrop2').click
  click_on("Grocery List")  
  click_on("Add to list")
end

When("I navigate to the Favorites Page") do
  find('#btnGroupVerticalDrop2').click
  click_on("Favorites")  
  click_on("Manage List")
  find('#manage_list_button').click
end

When("I add to Favorites List") do
  find('#btnGroupVerticalDrop2').click
  click_on("Favorites")  
  click_on("Add to list")
end

Given("I am on the search page") do
  visit 'http://localhost:8080/ImHungry/search_page.jsp'
end

Given("I am on the login page") do 
  visit 'http://localhost:8080/ImHungry/login_page.html'
end

When("I type {string} into the search bar") do |string|
  fill_in 'search_query', :with => string
end

When("I type {string} into the number bar") do |string|
  find('#drop-down-button').click
  fill_in 'num_results', :with => string
end

When("I type {string} into the radius bar") do |string|
  fill_in 'radius', :with => string
end

When("click search") do
  click_on("Feed Me!")
end

Then("I should be on the {string} page") do |string|
  expect(page).to have_current_path(string)
end

Then("I should see less than {int} results") do |int|
  expect(page).to have_content("Jerk Chicken", count:0)
end

Then ("I should see {int} results") do |int|
	expect(page).to have_content("Prep Time:", int)
end

When("I type {string} in the Username bar") do |string|
  fill_in 'email', :with => string
end

When("I type {string} in the Password bar") do |string|
  fill_in 'password', :with => string
end

Then("I should be logged in with account {string}") do |string|
  visit 'http://localhost:8080/ImHungry/search_page.jsp'
  expect(page).to have_content(string, count:0)
end

Given("I have an account with username {string} and password {string}") do |string, string2|
  visit 'http://localhost:8080/ImHungry/login_page.html'
  
end

Then("I should NOT be logged in with account {string}") do |string|
  expect(page).to have_content(string, count:0)
end

Then("I should NOT see content {string}") do |string|
  expect(page).to have_content(string, count:0)
end

When("I log-in") do
	find("input[value='login']").click
end

When("I create an account") do
	find("input[value='create']")
end


When("I drag {string} to {string}") do |string, string2|
	expect(page).to have_content(string)
	expect(page).to have_content(string2)
end

Then("I should see these elements in a different order") do
  expect(page).to have_content("Reordered", count:0)
end

Then("I should see no change in order") do
  expect(page).to have_content("Reordered", count:0)
end