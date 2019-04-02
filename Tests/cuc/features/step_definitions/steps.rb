Given("I search for {string} results for {string}") do |string, string2|
  visit 'http://localhost:8080/ImHungry/search_page.jsp'
  fill_in 'search_query', :with => string2
  fill_in 'num_results', :with => string
  click_on("Feed Me!")
end

Then("I should see {int} results") do |int|
  expect(page).to have_content("Distance", count:int)
end

Then("I should see {string}") do |string|
  expect(page).to have_content string
end
	
When("I click {string}") do |string|
  click_on(string)
end

When("I navigate to the Grocery page") do
  click_on("Back to Results")
  select "Grocery List", :from => "btnGroupVerticalDrop2"
  click_on("Manage List")
end

When("I add to Grocery List") do
  select "Grocery List", :from => "btnGroupVerticalDrop2"
  click_on("Add to list")
end

Given("I am on the search page") do
  visit 'http://localhost:8080/ImHungry/search_page.jsp'
end

When("I type {string} into the search bar") do |string|
  fill_in 'search_query', :with => string
end

When("I type {string} into the number bar") do |string|
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