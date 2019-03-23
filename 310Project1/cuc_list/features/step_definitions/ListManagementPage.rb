Then("I will be on the {string} List Management Page") do |string|
    html = page.evaluate_script("document.getElementById('listName').innerHTML")
    html.should include(string)
end

Then("{string} will be in the list") do |string|
  html = page.evaluate_script("document.getElementsByTagName('h4')[0].innerHTML")
  html.should include('<a href="rest?id=" tdcdiaasehz0fub65_yvqw"="">Pizza Studio</a>')
end

Given("I am on the {string} List Management Page") do |string|
  visit('http://localhost:8080/project1/manage.html?list=Favorites')
end

Then("I will be on the Search Page") do
  expect(page).to have_current_path('/project1/SearchPage.html')
end