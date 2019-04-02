#Given("I search for {string} restults for {string}") do |string, string2|
#  visit 'http://localhost:8080/ImHungry/search_page.jsp'
#  fill_in 'search_query', :with => string2
#  fill_in 'num_results', :with => string
#  click_on("Submit")
#end

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

When("I select the first recipe") do
  pending # Write code here that turns the phrase above into concrete actions
end

When("I navigate to the Grocery page") do
  pending # Write code here that turns the phrase above into concrete actions
end

Then("I should see the ingredients from the recipe") do
  pending # Write code here that turns the phrase above into concrete actions
end

Given("I am on the search page") do
  pending # Write code here that turns the phrase above into concrete actions
end

When("I type {string} into the search bar") do |string|
  pending # Write code here that turns the phrase above into concrete actions
end

When("I type {string} into the number bar") do |string|
  pending # Write code here that turns the phrase above into concrete actions
end

When("I type {string} into the radius bar") do |string|
  pending # Write code here that turns the phrase above into concrete actions
end

When("click search") do
  pending # Write code here that turns the phrase above into concrete actions
end

Then("I should be on the {string} page") do |string|
  pending # Write code here that turns the phrase above into concrete actions
end

Then("I should NOT see a radius over {int}") do |int|
  pending # Write code here that turns the phrase above into concrete actions
end