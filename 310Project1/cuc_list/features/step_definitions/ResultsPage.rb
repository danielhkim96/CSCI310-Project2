## Scenario Search a query then return to search page
Given("I am on the Search Page") do
  visit 'localhost:8080/project1/SearchPage.html'
end

Given("I have entered a search query {string}") do |string|
  fill_in 'search', :with => string
end

Given("{int} for the number of results to be displayed") do |int|
  fill_in 'limit', with: int
end

Given("I clicked the {string} button") do |string|
  find('#searchBtn').click
end

## Scenario: Return back to Search Page
Given("I am on the Results Page") do
  sleep(2)
  expect(page).to have_current_path('/project1/results.html')
end

When("I click Return to Search") do
  find("#retToSearchBtn").click
end

Then("I should be on the Search Page") do
  uri = URI.parse(current_url)
  uri.path == "localhost:8080/project1/SearchPage.html"
end

## Scenario: Correct content
Then("the page will have a title: {string}") do |string|
  html = page.evaluate_script("document.getElementById('title').innerHTML.toLowerCase()")
  html.should include(string.downcase)
end

Then("the page will have a collage of photos relevant to the query") do
  expect(page).to have_css("canvas#myCanvas")
end

Then("the page will have a button labeled {string}") do |string|
  html = page.evaluate_script("document.getElementById('manageBtn').innerHTML")
  html.should include('Manage List')
  html = page.evaluate_script("document.getElementById('retToSearchBtn').innerHTML")
  html.should include('Return to Search')
end

Then("the page will have a dropdown with predefined lists") do
  expect(page).to have_select 'dropdown',
                  with_options: ['Favorites', 'To Explore', 'Do Not Show']
end

Then("the page will have two columns of results titled: Restaurants, Recipes") do
  expect(page).to have_css('#restaurantTable')
  expect(page).to have_css('#recipeTable')
end

Then("the page will have {int} restaurant and recipe results in each column") do |int|
  x = page.evaluate_script("document.getElementsByTagName('tr').length")
  x == 4
end

Then("each restaurant item on the page will have an address, name, minutes, rating, and price") do
  expect(page).to have_css('.restaurantAddress')
  expect(page).to have_css('.restaurantName')
  expect(page).to have_css('.restaurantTime')
  expect(page).to have_css('.restaurantRating')
  expect(page).to have_css('.restaurantPrice')
end

Then("each recipe item on the page will have a name, prep time, cook time, and price") do
  expect(page).to have_css('.recipeName')
  expect(page).to have_css('.recipePrepTime')
  expect(page).to have_css('.recipeCookTime')
  expect(page).to have_css('.recipePrice')
end

When("I select {string} from the dropdown") do |string|
  select string
end

When("I click the {string} button") do |string|
  click_on string
end

Then("I will be on the {string} list page") do |string|
  expect(page).to have_css('#listName')
end

When("I click on a restaurant with name {string}") do |string|
  click_on string
end

Then("I will be on the Restaurant Page of restaurant {string}") do |string|
  expect(page).to have_css('#name')
end

When("I click on a recipe with name {string}") do |string|
  click_on string
end

Then("I will be on the Recipe Page of recipe {string}") do |string|
  expect(page).to have_css('#cookTime')
end

Then("I will be on the Results Page") do
  expect(page).to have_current_path('/project1/results.html')
end